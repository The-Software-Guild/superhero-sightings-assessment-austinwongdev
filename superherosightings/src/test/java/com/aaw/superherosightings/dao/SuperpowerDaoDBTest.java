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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
public class SuperpowerDaoDBTest {
    
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
    
    private Address address1;
    private Organization organization1;
    
    public SuperpowerDaoDBTest() {
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
        
        address1 = new Address();
        address1.setAddress("123 Fake St");
        address1.setCity("Salem");
        address1.setState("MA");
        address1.setZip("80210");
        
        organization1 = new Organization();
        organization1.setAddress(address1);
        organization1.setEmail("press@birds.com");
        organization1.setOrgDescription("Superheroes of flight");
        organization1.setOrgName("Bird Squad");
        organization1.setPhone("555-555-5555");
        organization1.setSupertype(supertypeDao.getSupertypeById(1));
        organizations = new ArrayList<>();
        organizations.add(organization1);
        organizationDao.addOrganization(organization1);

    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addSuperpower and getSuperpowerById methods, of class SuperpowerDaoDB.
     */
    @Test
    public void testAddAndGetSuperpowerById() {
        
        Superpower superpower1 = new Superpower();
        superpower1.setSuperpowerName("Invisibility");
        superpower1 = superpowerDao.addSuperpower(superpower1);
        
        Superpower superpowerFromDao = superpowerDao.getSuperpowerById(superpower1.getSuperpowerId());
        assertNotNull(superpowerFromDao);
        assertEquals(superpower1, superpowerFromDao);
        
    }

    /**
     * Test of getAllSuperpowers method, of class SuperpowerDaoDB.
     */
    @Test
    public void testGetAllSuperpowers() {
        
        Superpower superpower1 = new Superpower();
        superpower1.setSuperpowerName("Invisibility");
        superpower1 = superpowerDao.addSuperpower(superpower1);
        
        Superpower superpower2 = new Superpower();
        superpower2.setSuperpowerName("Flying");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        List<Superpower> superpowersFromDao = superpowerDao.getAllSuperpowers();
        assertEquals(2, superpowersFromDao.size());
        assertTrue(superpowersFromDao.contains(superpower1));
        assertTrue(superpowersFromDao.contains(superpower2));
        
    }

    /**
     * Test of updateSuperpower method, of class SuperpowerDaoDB.
     */
    @Test
    public void testUpdateSuperpower() {
        
        Superpower superpower1 = new Superpower();
        superpower1.setSuperpowerName("Invisibility");
        superpower1 = superpowerDao.addSuperpower(superpower1);
        
        superpower1.setSuperpowerName("Flying");
        superpowerDao.updateSuperpower(superpower1);
        Superpower superpowerFromDao = superpowerDao.getSuperpowerById(superpower1.getSuperpowerId());
        assertEquals(superpower1, superpowerFromDao);
    }

    /**
     * Test of deleteSuperpowerById method, of class SuperpowerDaoDB.
     */
    @Test
    public void testDeleteSuperpowerById() {
        
        Superpower superpower1 = new Superpower();
        superpower1.setSuperpowerName("Invisibility");
        superpower1 = superpowerDao.addSuperpower(superpower1);
        final int superpower1Id = superpower1.getSuperpowerId();
        Superpower superpowerFromDao = superpowerDao.getSuperpowerById(superpower1.getSuperpowerId());
        assertNotNull(superpowerFromDao);
        
        superpowerDao.deleteSuperpowerById(superpower1.getSuperpowerId());
        superpowerFromDao = superpowerDao.getSuperpowerById(superpower1.getSuperpowerId());
        assertNull(superpowerFromDao);
        
        superpowerDao.addSuperpower(superpower1);
        
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        superperson1.setOrganizations(organizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
        assertThrows(Exception.class, 
                () -> superpowerDao.deleteSuperpowerById(superpower1Id),
                "Should throw exception due to superperson having superpower");
    }
    
}
