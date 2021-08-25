/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Location;
import com.aaw.superherosightings.model.Superperson;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface LocationDao {

    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationById(int id);
    
    List<Location> getLocationsForSuperperson(Superperson superperson);
    
}
