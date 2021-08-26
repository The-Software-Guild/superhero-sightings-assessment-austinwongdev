/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.dao.SuperpersonDaoDB.SuperpersonMapper;
import com.aaw.superherosightings.dao.SupertypeDaoDB.SupertypeMapper;
import com.aaw.superherosightings.model.Address;
import com.aaw.superherosightings.model.Organization;
import com.aaw.superherosightings.model.Superperson;
import com.aaw.superherosightings.model.Supertype;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Austin Wong
 */

@Repository
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    AddressDao addressDao;
    
    @Override
    public Organization getOrganizationById(int id) {
        try{
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization "
                    + "WHERE orgId = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            organization.setAddress(getAddressForOrganization(id));
            organization.setSupertype(getSupertypeForOrganization(id));
            organization.setMembers(getMembersForOrganization(id));
            return organization;
        } catch (DataAccessException ex){
            return null;
        }
    }
    
    private Address getAddressForOrganization(int id){
        final String SELECT_ADDRESS_FOR_ORGANIZATION = "SELECT a.* FROM address a "
                + "INNER JOIN organization o ON o.addressId = a.addressId WHERE o.orgId = ?";
        return jdbc.queryForObject(SELECT_ADDRESS_FOR_ORGANIZATION, new AddressDaoDB.AddressMapper(), id);
    }
    
    private Supertype getSupertypeForOrganization(int id){
        final String SELECT_SUPERTYPE_FOR_ORGANIZATION = "SELECT s.* FROM supertype s "
                + "INNER JOIN organization o ON s.supertypeId= o.supertypeId WHERE o.orgId = ?";
        return jdbc.queryForObject(SELECT_SUPERTYPE_FOR_ORGANIZATION, new SupertypeMapper(), id);
    }
    
    private List<Superperson> getMembersForOrganization(int id){
        final String SELECT_MEMBERS_FOR_ORGANIZATION = "SELECT s.* FROM superperson s "
                + "INNER JOIN membership m ON m.superpersonId = s.superpersonId "
                + "WHERE m.orgId = ?";
        return jdbc.query(SELECT_MEMBERS_FOR_ORGANIZATION, new SuperpersonMapper(), id);
    }
    
    @Override
    public List<Organization> getOrganizationsForSuperperson(Superperson superperson){
        final String SELECT_ORGANIZATIONS_FOR_SUPERPERSON = "SELECT * FROM organization o "
                + "INNER JOIN membership m ON o.orgId = m.orgId "
                + "WHERE m.superpersonId = ?";
        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERPERSON, new OrganizationMapper(), superperson.getSuperpersonId());
        for (Organization org : organizations){
            org.setSupertype(getSupertypeForOrganization(org.getOrgId()));
            org.setAddress(getAddressForOrganization(org.getOrgId()));
            org.setMembers(getMembersForOrganization(org.getOrgId()));
        }
        return organizations;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        for (Organization organization : organizations){
            int id = organization.getOrgId();
            organization.setAddress(getAddressForOrganization(id));
            organization.setSupertype(getSupertypeForOrganization(id));
            organization.setMembers(getMembersForOrganization(id));
        }
        return organizations;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        
        // Update Address Table
        Address address = organization.getAddress();
        organization.setAddress(addressDao.addAddress(address));
        
        // Update Organization Table
        final String INSERT_ORGANIZATION = "INSERT INTO organization(orgName, orgDescription, email, phone, addressId, supertypeId) "
                + "VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getOrgName(),
                organization.getOrgDescription(),
                organization.getEmail(),
                organization.getPhone(),
                organization.getAddress().getAddressId(),
                organization.getSupertype().getSupertypeId());
        int newOrgId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrgId(newOrgId);
        
        // Update Membership Table
        for (Superperson member : organization.getMembers()){
            final String INSERT_MEMBER = "INSERT INTO membership (superpersonId, orgId) "
                    + "VALUES(?,?)";
            jdbc.update(INSERT_MEMBER,
                    member.getSuperpersonId(),
                    organization.getOrgId());
        }
        
        return organization;
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        
        // Update address table
        Address address = organization.getAddress();
        final String SELECT_ADDRESS_ID_FOR_ORGANIZATION = "SELECT addressId FROM organization WHERE orgId = ?";
        int addressId = jdbc.queryForObject(SELECT_ADDRESS_ID_FOR_ORGANIZATION, Integer.class, organization.getOrgId());
        address.setAddressId(addressId);
        addressDao.updateAddress(address);
        
        // Update organization table
        final String UPDATE_ORGANIZATION = "UPDATE organization "
                + "SET orgName = ?, orgDescription = ?, email = ?, phone = ?, "
                + "supertypeId = ? "
                + "WHERE orgId = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getOrgName(),
                organization.getOrgDescription(),
                organization.getEmail(),
                organization.getPhone(),
                organization.getSupertype().getSupertypeId(),
                organization.getOrgId());
        
        // Delete members from membership table
        final String DELETE_MEMBERSHIP = "DELETE FROM membership WHERE orgId = ?";
        jdbc.update(DELETE_MEMBERSHIP, organization.getOrgId());
        
        // Add members to membership table
        for (Superperson member : organization.getMembers()){
            final String INSERT_MEMBER = "INSERT INTO membership (superpersonId, orgId) "
                    + "VALUES(?,?)";
            jdbc.update(INSERT_MEMBER,
                    member.getSuperpersonId(),
                    organization.getOrgId());
        }
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        
        final String DELETE_MEMBERSHIP = "DELETE FROM membership WHERE orgId = ?";
        jdbc.update(DELETE_MEMBERSHIP, id);
        
        final String DELETE_ADDRESS_AND_ORGANIZATION = "DELETE organization, address FROM address INNER JOIN organization ON organization.addressId = address.addressId WHERE orgId = ?";
        jdbc.update(DELETE_ADDRESS_AND_ORGANIZATION, id);
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrgId(rs.getInt("orgId"));
            organization.setEmail(rs.getString("email"));
            organization.setOrgDescription(rs.getString("orgDescription"));
            organization.setOrgName(rs.getString("orgName"));
            organization.setPhone(rs.getString("phone"));
            
            return organization;
        }
    }
    
}
