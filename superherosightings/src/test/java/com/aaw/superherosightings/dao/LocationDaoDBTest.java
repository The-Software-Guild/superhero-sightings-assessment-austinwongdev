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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class LocationDaoDBTest {
    
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
    private Superperson superperson1;
    private Superpower superpower1;
    
    public LocationDaoDBTest() {
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
        organization1.setMembers(new ArrayList<Superperson>());
        organization1 = organizationDao.addOrganization(organization1);
        organizations = new ArrayList<>();
        organizations.add(organization1);
        
        superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        superperson1.setOrganizations(organizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addLocation and getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocation() {
        Location location1 = new Location();
        location1.setAddress(address1);
        location1.setLatitude(45.426441);
        location1.setLongitude(-122.766197);
        location1.setLocationDescription("Home of the Tigers");
        location1.setLocationName("Tigard");
        
        Location fromDao = locationDao.addLocation(location1);
        assertNotNull(fromDao);
        
        location1.setLocationId(fromDao.getLocationId());
        assertEquals(location1, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location1 = new Location();
        location1.setAddress(address1);
        location1.setLatitude(45.426441);
        location1.setLongitude(-122.766197);
        location1.setLocationDescription("Home of the Tigers");
        location1.setLocationName("Tigard");
        location1 = locationDao.addLocation(location1);
        
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
        
        List<Location> locations = locationDao.getAllLocations();
        assertEquals(2, locations.size());
        
        assertTrue(locations.contains(location1));
        assertTrue(locations.contains(location2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location1 = new Location();
        location1.setAddress(address1);
        location1.setLatitude(45.426441);
        location1.setLongitude(-122.766197);
        location1.setLocationDescription("Home of the Tigers");
        location1.setLocationName("Tigard");
        location1 = locationDao.addLocation(location1);
        
        address1.setAddress("456 Real St");
        address1.setCity("Tucson");
        address1.setState("AZ");
        address1.setZip("80210");
        location1.setLatitude(47.280991);
        location1.setLongitude(-120.788888);
        location1.setLocationDescription("Home of the Lions");
        location1.setLocationName("Lionsville");
        locationDao.updateLocation(location1);
        Location fromDao = locationDao.getLocationById(location1.getLocationId());
        
        assertEquals(location1, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        
        Location location1 = new Location();
        location1.setAddress(address1);
        location1.setLatitude(45.426441);
        location1.setLongitude(-122.766197);
        location1.setLocationDescription("Home of the Tigers");
        location1.setLocationName("Tigard");
        location1 = locationDao.addLocation(location1);
        Location locationFromDao = locationDao.getLocationById(location1.getLocationId());
        assertEquals(location1, locationFromDao);
        
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.now());
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        assertNotNull(sighting1);
        
        locationDao.deleteLocationById(location1.getLocationId());
        locationFromDao = locationDao.getLocationById(location1.getLocationId());
        assertNull(locationFromDao);
        
        Address addressFromDao = addressDao.getAddressById(location1.getAddress().getAddressId());
        assertNull(addressFromDao);
        
        Sighting sightingFromDao =sightingDao.getSightingById(sighting1.getSightingId());
        assertNull(sightingFromDao);
        
    }

    /**
     * Test of getLocationsForSuperperson method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationsForSuperperson() {
        
        // Create locations for superperson1
        Location location1 = new Location();
        location1.setAddress(address1);
        location1.setLatitude(45.426441);
        location1.setLongitude(-122.766197);
        location1.setLocationDescription("Home of the Tigers");
        location1.setLocationName("Tigard");
        location1 = locationDao.addLocation(location1);
        assertNotNull(location1);
        
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
        assertNotNull(location2);
        
        // Create location NOT associated with superperson1
        Location location3 = new Location();
        Address address3 = new Address();
        address3.setAddress("789 Other St");
        address3.setCity("Chicago");
        address3.setState("IL");
        address3.setZip("81111");
        location3.setAddress(address3);
        location3.setLatitude(10.426441);
        location3.setLongitude(-10.766197);
        location3.setLocationDescription("Who knows where");
        location3.setLocationName("Unknown location");
        location3 = locationDao.addLocation(location3);
        assertNotNull(location3);
        
        // Create a second superperson
        Superperson superperson2 = new Superperson();
        superperson2.setSuperpersonDescription("Rather average flier");
        superperson2.setSuperpersonName("Bald Eagle");
        superperson2.setSuperpower(superpower1);
        superperson2.setSupertype(supertypeDao.getSupertypeById(1));
        superperson2.setOrganizations(superperson1.getOrganizations());
        superpersonDao.addSuperperson(superperson2);
        
        // Create sightings for superperson1
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.now());
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        assertNotNull(sighting1);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperperson(superperson1);
        sighting2.setSightingDatetime(LocalDateTime.now());
        sighting2.setLocation(location2);
        sighting2 = sightingDao.addSighting(sighting2);
        assertNotNull(sighting2);
        
        // Create sightings NOT associated with superperson1
        Sighting sighting3 = new Sighting();
        sighting3.setSuperperson(superperson2);
        sighting3.setSightingDatetime(LocalDateTime.now());
        sighting3.setLocation(location3);
        sighting3 = sightingDao.addSighting(sighting3);
        assertNotNull(sighting3);
        
        List<Location> locationsFromDao = locationDao.getLocationsForSuperperson(superperson1);
        assertEquals(2, locationsFromDao.size());
        assertTrue(locationsFromDao.contains(location1));
        assertTrue(locationsFromDao.contains(location2));
        assertFalse(locationsFromDao.contains(location3));
        
    }
    
}
