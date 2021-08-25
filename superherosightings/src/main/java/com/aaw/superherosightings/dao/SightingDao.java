/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface SightingDao {
    
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsByDate(LocalDate date);
    List<Sighting> getMostRecentSightings(int numSightings);
    
}
