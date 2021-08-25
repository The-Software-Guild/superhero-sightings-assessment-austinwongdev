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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
public class SightingDaoDBTest {
    
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
    
    private Superperson superperson1;
    private Superpower superpower1;
    private Address address1;
    private Location location1;
    
    public SightingDaoDBTest() {
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
        
        superpower1 = new Superpower();
        superpower1.setSuperpowerName("Flying");
        superpower1 = superpowerDao.addSuperpower(superpower1);
        
        Organization organization1 = new Organization();
        organization1.setAddress(address1);
        organization1.setEmail("press@birds.com");
        organization1.setOrgDescription("Superheroes of flight");
        organization1.setOrgName("Bird Squad");
        organization1.setPhone("555-555-5555");
        organization1.setSupertype(supertypeDao.getSupertypeById(1));
        organizations = new ArrayList<>();
        organizations.add(organization1);
        organizationDao.addOrganization(organization1);
        
        superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        superperson1.setOrganizations(organizations);
        superpersonDao.addSuperperson(superperson1);
        
        location1 = new Location();
        location1.setAddress(address1);
        location1.setLatitude(45.426441);
        location1.setLongitude(-122.766197);
        location1.setLocationDescription("Home of the Tigers");
        location1.setLocationName("Tigard");
        location1 = locationDao.addLocation(location1);
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addSighting and getSightingById methods, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSightingById() {
        
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.of(2021, Month.MARCH, 13, 9, 22));
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        assertNotNull(sighting1);
        
        Sighting fromDao = sightingDao.getSightingById(sighting1.getSightingId());
        assertEquals(sighting1, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.of(2021, Month.MARCH, 13, 9, 22));
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperperson(superperson1);
        sighting2.setSightingDatetime(LocalDateTime.of(2021, Month.APRIL, 8, 14, 59));
        sighting2.setLocation(location1);
        sighting2 = sightingDao.addSighting(sighting2);
        assertNotEquals(sighting1, sighting2);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting1));
        assertTrue(sightings.contains(sighting2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        
        // Original sighting
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.of(2021, Month.MARCH, 13, 9, 22));
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        
        // Prepare updates
        Location location2 = new Location();
        Address address2 = new Address();
        address2.setAddress("456 Real St");
        address2.setCity("Tucson");
        address2.setState("AZ");
        address2.setZip("80210");
        location2.setAddress(address2);
        location2.setLatitude(59.426441);
        location2.setLongitude(-128.766197);
        location2.setLocationDescription("Home of the Lions");
        location2.setLocationName("Lionsville");
        location2 = locationDao.addLocation(location2);
        
        Superperson superperson2 = new Superperson();
        superperson2.setSuperpersonDescription("Rather average flier");
        superperson2.setSuperpersonName("Bald Eagle");
        superperson2.setSuperpower(superpower1);
        superperson2.setSupertype(supertypeDao.getSupertypeById(1));
        superperson2.setOrganizations(superperson1.getOrganizations());
        superpersonDao.addSuperperson(superperson2);
        
        // Update sighting
        sighting1.setSightingDatetime(LocalDateTime.of(2021, Month.APRIL, 8, 8, 8));
        sighting1.setLocation(location2);
        sighting1.setSuperperson(superperson2);
        sightingDao.updateSighting(sighting1);
        
        // Check updates
        Sighting sightingFromDao = sightingDao.getSightingById(sighting1.getSightingId());
        assertNotNull(sightingFromDao);
        assertEquals(sighting1, sightingFromDao);
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.of(2021, Month.MARCH, 13, 9, 22));
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        sighting1 = sightingDao.getSightingById(sighting1.getSightingId());
        assertNotNull(sighting1);
        
        sightingDao.deleteSightingById(sighting1.getSightingId());
        Sighting sightingFromDao = sightingDao.getSightingById(sighting1.getSightingId());
        assertNull(sightingFromDao);
        
    }

    /**
     * Test of getSightingsByDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsByDate() {
        
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.of(2021, Month.MARCH, 13, 9, 22));
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperperson(superperson1);
        sighting2.setSightingDatetime(LocalDateTime.of(2021, Month.MARCH, 13, 14, 59));
        sighting2.setLocation(location1);
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setSuperperson(superperson1);
        sighting3.setSightingDatetime(LocalDateTime.of(2021, Month.APRIL, 1, 14, 59));
        sighting3.setLocation(location1);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> sightingsForDate = sightingDao.getSightingsByDate(LocalDate.of(2021, Month.MARCH, 13));
        assertEquals(2, sightingsForDate.size());
        assertTrue(sightingsForDate.contains(sighting1));
        assertTrue(sightingsForDate.contains(sighting2));
        assertFalse(sightingsForDate.contains(sighting3));
        
    }

    /**
     * Test of getMostRecentSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetMostRecentSightings() {
        
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.of(2021, Month.MARCH, 13, 9, 22));
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperperson(superperson1);
        sighting2.setSightingDatetime(LocalDateTime.of(2021, Month.MARCH, 13, 14, 59));
        sighting2.setLocation(location1);
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setSuperperson(superperson1);
        sighting3.setSightingDatetime(LocalDateTime.of(2021, Month.APRIL, 1, 14, 59));
        sighting3.setLocation(location1);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> mostRecentSightings = sightingDao.getMostRecentSightings(2);
        assertEquals(2, mostRecentSightings.size());
        assertTrue(mostRecentSightings.contains(sighting2));
        assertTrue(mostRecentSightings.contains(sighting3));
        assertFalse(mostRecentSightings.contains(sighting1));
        
    }
    
}
