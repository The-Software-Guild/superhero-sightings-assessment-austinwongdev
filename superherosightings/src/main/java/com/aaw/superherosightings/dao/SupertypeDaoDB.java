/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Supertype;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Austin Wong
 */

@Repository
public class SupertypeDaoDB implements SupertypeDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Supertype getSupertypeById(int id) {
        try {
            final String SELECT_SUPERTYPE_BY_ID = "SELECT * FROM supertype WHERE supertypeId = ?";
            return jdbc.queryForObject(SELECT_SUPERTYPE_BY_ID, new SupertypeMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Supertype> getAllSupertypes() {
        final String SELECT_ALL_SUPERTYPES = "SELECT * FROM supertype";
        return jdbc.query(SELECT_ALL_SUPERTYPES, new SupertypeMapper());
    }

    public static final class SupertypeMapper implements RowMapper<Supertype> {

        @Override
        public Supertype mapRow(ResultSet rs, int index) throws SQLException {
            Supertype supertype = new Supertype();
            supertype.setSupertypeId(rs.getInt("supertypeId"));
            supertype.setSupertypeName(rs.getString("supertypeName"));
            return supertype;
        }
    }
}
