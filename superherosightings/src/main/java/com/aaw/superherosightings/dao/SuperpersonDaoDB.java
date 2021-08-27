/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.dao.SuperpowerDaoDB.SuperpowerMapper;
import com.aaw.superherosightings.dao.SupertypeDaoDB.SupertypeMapper;
import com.aaw.superherosightings.model.Location;
import com.aaw.superherosightings.model.Organization;
import com.aaw.superherosightings.model.Superperson;
import com.aaw.superherosightings.model.Superpower;
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
public class SuperpersonDaoDB implements SuperpersonDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Superperson getSuperpersonById(int id) {
        try{
            final String SELECT_SUPERPERSON_BY_ID = "SELECT * FROM superperson WHERE superpersonId = ?";
            Superperson superperson = jdbc.queryForObject(SELECT_SUPERPERSON_BY_ID, new SuperpersonMapper(), id);
            superperson.setOrganizations(getOrganizationsForSuperperson(superperson));
            superperson.setSuperpower(getSuperpowerForSuperperson(superperson));
            superperson.setSupertype(getSupertypeForSuperperson(superperson));
            return superperson;
        } catch (DataAccessException ex){
            return null;
        }
    }
    
    private List<Organization> getOrganizationsForSuperperson(Superperson superperson){
        final String SELECT_ORGANIZATIONS_FOR_SUPERPERSON = "SELECT * FROM organization o "
                + "INNER JOIN membership m ON o.orgId = m.orgId "
                + "WHERE m.superpersonId = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERPERSON, new OrganizationDaoDB.OrganizationMapper(), superperson.getSuperpersonId());
    }
    
    private Superpower getSuperpowerForSuperperson(Superperson superperson){
        final String SELECT_SUPERPOWER_FOR_SUPERPERSON = "SELECT * FROM superpower spo "
                + "INNER JOIN superperson spe ON spe.superpowerId = spo.superpowerId "
                + "WHERE spe.superpersonId = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_SUPERPERSON, new SuperpowerMapper(), superperson.getSuperpersonId());
    }

    private Supertype getSupertypeForSuperperson(Superperson superperson){
        final String SELECT_SUPERTYPE_FOR_SUPERPERSON = "SELECT * FROM supertype st "
                + "INNER JOIN superperson sp ON sp.supertypeId = st.supertypeId "
                + "WHERE sp.superpersonId = ?";
        return jdbc.queryForObject(SELECT_SUPERTYPE_FOR_SUPERPERSON, new SupertypeMapper(), superperson.getSuperpersonId());
    }
    
    @Override
    public List<Superperson> getAllSuperpeople() {
        final String SELECT_ALL_SUPERPEOPLE = "SELECT * FROM superperson";
        List<Superperson> superpeople = jdbc.query(SELECT_ALL_SUPERPEOPLE, new SuperpersonMapper());
        for (Superperson superperson : superpeople){
            superperson.setOrganizations(getOrganizationsForSuperperson(superperson));
            superperson.setSuperpower(getSuperpowerForSuperperson(superperson));
            superperson.setSupertype(getSupertypeForSuperperson(superperson));
        }
        return superpeople;
    }

    @Override
    @Transactional
    public Superperson addSuperperson(Superperson superperson) {
        
        // Add superperson to superperson table
        final String INSERT_SUPERPERSON = "INSERT INTO superperson(superpersonName, superpersonDescription, supertypeId, superpowerId) "
                + "VALUES(?, ?, ?, ?)";
        jdbc.update(INSERT_SUPERPERSON,
                superperson.getSuperpersonName(),
                superperson.getSuperpersonDescription(),
                superperson.getSupertype().getSupertypeId(),
                superperson.getSuperpower().getSuperpowerId());
        int newSuperpersonId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superperson.setSuperpersonId(newSuperpersonId);
        
        // Add superperson to membership table
        for (Organization org : superperson.getOrganizations()){
            final String INSERT_MEMBER = "INSERT INTO membership (superpersonId, orgId) "
                    + "VALUES(?, ?)";
            jdbc.update(INSERT_MEMBER, superperson.getSuperpersonId(), org.getOrgId());
        }
        
        return superperson;
        
    }

    @Override
    public void updateSuperperson(Superperson superperson) {
        
        // Update superperson table
        final String UPDATE_SUPERPERSON = "UPDATE superperson SET superpersonName = ?, "
                + "superpersonDescription = ?, supertypeId = ?, superpowerId = ? "
                + "WHERE superpersonId = ?";
        jdbc.update(UPDATE_SUPERPERSON, 
                superperson.getSuperpersonName(),
                superperson.getSuperpersonDescription(),
                superperson.getSupertype().getSupertypeId(),
                superperson.getSuperpower().getSuperpowerId(),
                superperson.getSuperpersonId());
        
        // Delete superperson from membership table
        final String DELETE_MEMBER = "DELETE FROM membership WHERE superpersonId = ?";
        jdbc.update(DELETE_MEMBER, superperson.getSuperpersonId());
        
        // Add superperson to membership table
        for (Organization org : superperson.getOrganizations()){
            final String INSERT_MEMBER = "INSERT INTO membership (superpersonId, orgId) "
                    + "VALUES(?, ?)";
            jdbc.update(INSERT_MEMBER, superperson.getSuperpersonId(), org.getOrgId());
        }
        
    }

    @Override
    @Transactional
    public void deleteSuperpersonById(int id) {
        final String DELETE_MEMBERSHIP = "DELETE FROM membership WHERE superpersonId = ?";
        jdbc.update(DELETE_MEMBERSHIP, id);
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE superpersonId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        final String DELETE_SUPERPERSON = "DELETE FROM superperson WHERE superpersonId = ?";
        jdbc.update(DELETE_SUPERPERSON, id);
    }

    @Override
    public List<Superperson> getSuperpeopleForLocation(Location location) {
        final String SELECT_MEMBERS_FOR_ORGANIZATION = "SELECT sp.* FROM superperson sp "
                + "INNER JOIN sighting si ON si.superpersonId = sp.superpersonId "
                + "WHERE si.locationId = ?";
        return jdbc.query(SELECT_MEMBERS_FOR_ORGANIZATION, new SuperpersonMapper(), location.getLocationId());
    }

    @Override
    public List<Superperson> getSuperpeopleForOrganization(Organization organization) {
        final String SELECT_MEMBERS_FOR_ORGANIZATION = "SELECT s.* FROM superperson s "
                + "INNER JOIN membership m ON m.superpersonId = s.superpersonId "
                + "WHERE m.orgId = ?";
        List<Superperson> superpeople = jdbc.query(SELECT_MEMBERS_FOR_ORGANIZATION, new SuperpersonMapper(), organization.getOrgId());
        for (Superperson superperson : superpeople){
            superperson.setOrganizations(getOrganizationsForSuperperson(superperson));
            superperson.setSuperpower(getSuperpowerForSuperperson(superperson));
            superperson.setSupertype(getSupertypeForSuperperson(superperson));
        }
        return superpeople;
    }
    
    public static final class SuperpersonMapper implements RowMapper<Superperson> {

        @Override
        public Superperson mapRow(ResultSet rs, int index) throws SQLException {
            Superperson superperson = new Superperson();
            superperson.setSuperpersonId(rs.getInt("superpersonId"));
            superperson.setSuperpersonName(rs.getString("superpersonName"));
            superperson.setSuperpersonDescription(rs.getString("superpersonDescription"));
            return superperson;
        }
    }

}
