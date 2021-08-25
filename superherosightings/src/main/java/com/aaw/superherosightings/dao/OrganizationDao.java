/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Organization;
import com.aaw.superherosightings.model.Superperson;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface OrganizationDao {

    Organization getOrganizationById(int id);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization organization);
    void updateOrganization(Organization organization);
    void deleteOrganizationById(int id);
    
    List<Organization> getOrganizationsForSuperperson(Superperson superperson);
    
}
