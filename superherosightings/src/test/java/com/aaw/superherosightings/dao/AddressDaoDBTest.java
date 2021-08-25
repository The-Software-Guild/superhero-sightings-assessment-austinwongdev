/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Address;
import com.aaw.superherosightings.model.Location;
import com.aaw.superherosightings.model.Organization;
import com.aaw.superherosightings.model.Sighting;
import com.aaw.superherosightings.model.Superperson;
import com.aaw.superherosightings.model.Superpower;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Austin Wong
 */

@SpringBootTest
public class AddressDaoDBTest {
    
    @Autowired
    AddressDao addressDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SuperpersonDao superpersonDao;
    
    @Autowired
    SupertypeDao supertypeDao;
    
    @Autowired
    SuperpowerDao superpowerDao;
    
    public AddressDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization: organizations){
            organizationDao.deleteOrganizationById(organization.getOrgId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting: sightings){
            sightingDao.deleteSightingById(sighting.getSightingId());
        }
        
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        for (Superperson superperson : superpeople){
            superpersonDao.deleteSuperpersonById(superperson.getSuperpersonId());
        }
        
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for (Superpower superpower : superpowers){
            superpowerDao.deleteSuperpowerById(superpower.getSuperpowerId());
        }
        
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations){
            locationDao.deleteLocationById(location.getLocationId());
        }
        
        List<Address> addresses = addressDao.getAllAddresses();
        for (Address address : addresses){
            addressDao.deleteAddressById(address.getAddressId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addAddress and getAddressById method, of class AddressDaoDB.
     */
    @Test
    public void testAddAndGetAddress() {
        Address address1 = new Address();
        address1.setAddress("123 Fake St");
        address1.setCity("Salem");
        address1.setState("MA");
        address1.setZip("80210");
        
        Address fromDao = addressDao.addAddress(address1);
        assertNotNull(fromDao.getAddressId(), "Should have address ID");
        
        address1.setAddressId(fromDao.getAddressId());
        assertEquals(address1, fromDao);
        
        fromDao = addressDao.getAddressById(address1.getAddressId());
        assertEquals(address1, fromDao);
    }
    
    /**
     * Test of getAllAddresses method, of class AddressDaoDB.
     */
    @Test
    public void testGetAllAddresses(){
        Address address1 = new Address();
        address1.setAddress("123 Fake St");
        address1.setCity("Salem");
        address1.setState("MA");
        address1.setZip("80210");
        address1 = addressDao.addAddress(address1);
        
        Address address2 = new Address();
        address2.setAddress("123 Fake St");
        address2.setCity("Salem");
        address2.setState("MA");
        address2.setZip("80210");
        address2 = addressDao.addAddress(address2);
        
        List<Address> addresses = addressDao.getAllAddresses();
        assertEquals(2, addresses.size());
        
        assertTrue(addresses.contains(address1));
        assertTrue(addresses.contains(address2));
    }

    /**
     * Test of updateAddress method, of class AddressDaoDB.
     */
    @Test
    public void testUpdateAddress() {
        Address address1 = new Address();
        address1.setAddress("123 Fake St");
        address1.setCity("Salem");
        address1.setState("MA");
        address1.setZip("80210");
        
        address1 = addressDao.addAddress(address1);
        address1.setAddress("456 New St");
        address1.setZip("99999");
        
        addressDao.updateAddress(address1);
        
        Address fromDao = addressDao.getAddressById(address1.getAddressId());
        assertEquals(address1, fromDao);
    }

    /**
     * Test of deleteAddressById method, of class AddressDaoDB.
     */
    @Test
    public void testDeleteAddressById() {
        Address address1 = new Address();
        address1.setAddress("123 Fake St");
        address1.setCity("Salem");
        address1.setState("MA");
        address1.setZip("80210");
        
        address1 = addressDao.addAddress(address1);
        Address fromDao = addressDao.getAddressById(address1.getAddressId());
        assertNotNull(fromDao);
        
        addressDao.deleteAddressById(fromDao.getAddressId());
        fromDao = addressDao.getAddressById(address1.getAddressId());
        assertNull(fromDao);
    }
    
}
