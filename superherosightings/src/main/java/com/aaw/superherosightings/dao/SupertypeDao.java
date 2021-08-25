/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Supertype;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface SupertypeDao {
    
    Supertype getSupertypeById(int id);
    List<Supertype> getAllSupertypes();
    
}
