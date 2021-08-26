/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Organization;
import com.aaw.superherosightings.model.Superperson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Override
    public Organization getOrganizationById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        return jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public Organization addOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        
        final String DELETE_MEMBERSHIP = "DELETE FROM membership WHERE orgId = ?";
        jdbc.update(DELETE_MEMBERSHIP, id);
        
        final String DELETE_ADDRESS_AND_ORGANIZATION = "DELETE organization, address FROM address INNER JOIN organization ON organization.addressId = address.addressId WHERE orgId = ?";
        jdbc.update(DELETE_ADDRESS_AND_ORGANIZATION, id);
    }

    @Override
    public List<Organization> getOrganizationsForSuperperson(Superperson superperson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
