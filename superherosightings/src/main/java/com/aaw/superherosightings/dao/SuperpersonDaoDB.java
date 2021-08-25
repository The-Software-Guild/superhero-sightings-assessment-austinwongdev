/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Location;
import com.aaw.superherosightings.model.Organization;
import com.aaw.superherosightings.model.Superperson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Superperson> getAllSuperpeople() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Superperson addSuperperson(Superperson superperson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSuperperson(Superperson superperson) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSuperpersonById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Superperson> getSuperpeopleForLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Superperson> getSuperpeopleForOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
