/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.dao.LocationDaoDB.LocationMapper;
import com.aaw.superherosightings.dao.SuperpersonDaoDB.SuperpersonMapper;
import com.aaw.superherosightings.model.Location;
import com.aaw.superherosightings.model.Sighting;
import com.aaw.superherosightings.model.Superperson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    SuperpersonDao superpersonDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Override
    public Sighting getSightingById(int id) {
        try{
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting "
                    + "WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            sighting.setSuperperson(getSuperpersonForSighting(id));
            return sighting;
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        for (Sighting sighting : sightings){
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
            sighting.setSuperperson(getSuperpersonForSighting(sighting.getSightingId()));
        }
        return sightings;
    }
    
    private Location getLocationForSighting(int id){
//        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
//                + "INNER JOIN sighting s ON s.locationId = l.locationId "
//                + "WHERE s.sightingId = ?";
//        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
        final String SELECT_LOCATION_ID_FOR_SIGHTING = "SELECT locationId FROM sighting WHERE sightingId = ?";
        int locationId = jdbc.queryForObject(SELECT_LOCATION_ID_FOR_SIGHTING, Integer.class, id);
        return locationDao.getLocationById(locationId);
    }
    
    private Superperson getSuperpersonForSighting(int id){
//        final String SELECT_SUPERPERSON_FOR_SIGHTING = "SELECT sp.* FROM superperson sp "
//                + "INNER JOIN sighting s ON s.superpersonId = sp.superpersonId "
//                + "WHERE s.sightingId = ?";
//        return jdbc.queryForObject(SELECT_SUPERPERSON_FOR_SIGHTING, new SuperpersonMapper(), id);
        final String SELECT_SUPERPERSON_ID_FOR_SIGHTING = "SELECT superpersonId FROM sighting WHERE sightingId = ?";
        int superpersonId = jdbc.queryForObject(SELECT_SUPERPERSON_ID_FOR_SIGHTING, Integer.class, id);
        return superpersonDao.getSuperpersonById(superpersonId);
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting (superpersonId, locationId, sightingDatetime) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getSuperperson().getSuperpersonId(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingDatetime());
        int newSightingId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newSightingId);
        return sighting;        
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET superpersonId = ?, "
                + "locationId = ?, sightingDatetime = ? "
                + "WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getSuperperson().getSuperpersonId(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingDatetime(),
                sighting.getSightingId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getMostRecentSightings(int numSightings) {
        final String SELECT_RECENT_SIGHTINGS = "SELECT * FROM sighting "
                + "ORDER BY sightingDatetime DESC LIMIT ?";
        List<Sighting> sightings = jdbc.query(SELECT_RECENT_SIGHTINGS, new SightingMapper(), numSightings);
        for (Sighting sighting : sightings){
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
            sighting.setSuperperson(getSuperpersonForSighting(sighting.getSightingId()));
        }
        return sightings;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setSightingDatetime(rs.getTimestamp("sightingDatetime").toLocalDateTime());
            return sighting;
        }
    }
    
}
