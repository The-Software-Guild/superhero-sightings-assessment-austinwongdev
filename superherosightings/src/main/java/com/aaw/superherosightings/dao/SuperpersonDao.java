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
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface SuperpersonDao {
    
    Superperson getSuperpersonById(int id);
    List<Superperson> getAllSuperpeople();
    Superperson addSuperperson(Superperson superperson);
    void updateSuperperson(Superperson superperson);
    void deleteSuperpersonById(int id);
    
    List<Superperson> getSuperpeopleForLocation(Location location);
    List<Superperson> getSuperpeopleForOrganization(Organization organization);
    
}
