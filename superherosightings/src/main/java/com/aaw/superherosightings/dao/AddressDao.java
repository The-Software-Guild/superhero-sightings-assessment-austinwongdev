/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Address;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface AddressDao {
   
    Address getAddressById(int id);
    List<Address> getAllAddresses();
    Address addAddress(Address address);
    void updateAddress(Address address);
    void deleteAddressById(int id);
    
}
