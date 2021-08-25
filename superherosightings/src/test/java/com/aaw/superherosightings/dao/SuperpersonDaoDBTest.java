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
public class SuperpersonDaoDBTest {
    
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
    private Superpower superpower1;
    
    public SuperpersonDaoDBTest() {
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
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addSuperperson and getSuperpersonById methods, of class SuperpersonDaoDB.
     */
    @Test
    public void testAddAndGetSuperpersonById() {
        
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        List<Organization> organizations = organizationDao.getAllOrganizations();
        superperson1.setOrganizations(organizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
        Superperson superpersonFromDao = superpersonDao.getSuperpersonById(superperson1.getSuperpersonId());
        assertNotNull(superpersonFromDao);
        assertEquals(superperson1, superpersonFromDao);
        
    }

    /**
     * Test of getAllSuperpeople method, of class SuperpersonDaoDB.
     */
    @Test
    public void testGetAllSuperpeople() {
        
        // Create first superperson
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        List<Organization> organizations = organizationDao.getAllOrganizations();
        superperson1.setOrganizations(organizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
        // Create second superperson
        Superperson superperson2 = new Superperson();
        superperson2.setSuperpersonDescription("Rather average flier");
        superperson2.setSuperpersonName("Bald Eagle");
        superperson2.setSuperpower(superpower1);
        superperson2.setSupertype(supertypeDao.getSupertypeById(1));
        superperson2.setOrganizations(superperson1.getOrganizations());
        superperson2 = superpersonDao.addSuperperson(superperson2);
        
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        assertEquals(2, superpeople.size());
        assertTrue(superpeople.contains(superperson1));
        assertTrue(superpeople.contains(superperson2));
        
    }

    /**
     * Test of updateSuperperson method, of class SuperpersonDaoDB.
     */
    @Test
    public void testUpdateSuperperson() {
        
        // Create superperson
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        List<Organization> organizations = organizationDao.getAllOrganizations();
        superperson1.setOrganizations(organizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
        // Update superperson
        superperson1.setSuperpersonDescription("Rather average flier");
        superperson1.setSuperpersonName("Bald Eagle");
        
        Superpower superpower2 = new Superpower();
        superpower2.setSuperpowerName("Invincibility");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        superperson1.setSuperpower(superpower2);
        
        Organization organization2 = new Organization();
        Address address2 = new Address();
        address2.setAddress("456 Real St");
        address2.setCity("Tucson");
        address2.setState("AZ");
        address2.setZip("80210");
        organization2.setAddress(address2);
        organization2.setEmail("press@villains.com");
        organization2.setOrgDescription("Supervillains of flight");
        organization2.setOrgName("Evil Bird Squad");
        organization2.setPhone("808-808-8080");
        organization2.setSupertype(supertypeDao.getSupertypeById(2));
        organization2 = organizationDao.addOrganization(organization2);
        superperson1.setOrganizations(organizationDao.getAllOrganizations());
        
        superpersonDao.updateSuperperson(superperson1);
        
        Superperson superpersonFromDao = superpersonDao.getSuperpersonById(superperson1.getSuperpersonId());
        assertNotNull(superpersonFromDao);
        assertEquals(superperson1, superpersonFromDao);
        
    }

    /**
     * Test of deleteSuperpersonById method, of class SuperpersonDaoDB.
     */
    @Test
    public void testDeleteSuperpersonById() {
        
        // Create superperson
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        List<Organization> organizations = organizationDao.getAllOrganizations();
        superperson1.setOrganizations(organizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
        Location location1 = new Location();
        location1.setAddress(address1);
        location1.setLatitude(45.426441);
        location1.setLongitude(-122.766197);
        location1.setLocationDescription("Home of the Tigers");
        location1.setLocationName("Tigard");
        location1 = locationDao.addLocation(location1);
        
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.now());
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        assertNotNull(sighting1);
        
        superpersonDao.deleteSuperpersonById(superperson1.getSuperpersonId());
        Superperson superpersonFromDao = superpersonDao.getSuperpersonById(superperson1.getSuperpersonId());
        assertNull(superpersonFromDao);
        
        Sighting sightingFromDao = sightingDao.getSightingById(sighting1.getSightingId());
        assertNull(sightingFromDao);
        
        organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations){
            List<Superperson> members = organization.getMembers();
            assertFalse(members.contains(superperson1));
        }
    }

    /**
     * Test of getSuperpeopleForLocation method, of class SuperpersonDaoDB.
     */
    @Test
    public void testGetSuperpeopleForLocation() {
        
        // Create superpeople expected at location
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        List<Organization> organizations = organizationDao.getAllOrganizations();
        superperson1.setOrganizations(organizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
        Superperson superperson2 = new Superperson();
        superperson2.setSuperpersonDescription("Rather average flier");
        superperson2.setSuperpersonName("Bald Eagle");
        superperson2.setSuperpower(superpower1);
        superperson2.setSupertype(supertypeDao.getSupertypeById(1));
        superperson2.setOrganizations(superperson1.getOrganizations());
        superperson2 = superpersonDao.addSuperperson(superperson2);
        
        // Create superperson not expected at location
        Superperson superperson3 = new Superperson();
        superperson3.setSuperpersonDescription("Seeks revenge through flight");
        superperson3.setSuperpersonName("Flying Avenger");
        superperson3.setSuperpower(superpower1);
        superperson3.setSupertype(supertypeDao.getSupertypeById(1));
        superperson3.setOrganizations(superperson1.getOrganizations());
        superperson3 = superpersonDao.addSuperperson(superperson3);
        
        // Create Locations        
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
        
        // Add sightings for superpeople
        Sighting sighting1 = new Sighting();
        sighting1.setSuperperson(superperson1);
        sighting1.setSightingDatetime(LocalDateTime.now());
        sighting1.setLocation(location1);
        sighting1 = sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperperson(superperson2);
        sighting2.setSightingDatetime(LocalDateTime.now());
        sighting2.setLocation(location1);
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setSuperperson(superperson3);
        sighting3.setSightingDatetime(LocalDateTime.now());
        sighting3.setLocation(location2);
        sighting3 = sightingDao.addSighting(sighting3);
        
        // Get superpeople for location1
        List<Superperson> superpeopleForLocation = superpersonDao.getSuperpeopleForLocation(location1);
        assertEquals(2, superpeopleForLocation.size());
        assertTrue(superpeopleForLocation.contains(superperson1));
        assertTrue(superpeopleForLocation.contains(superperson2));
        assertFalse(superpeopleForLocation.contains(superperson3));
    }

    /**
     * Test of getSuperpeopleForOrganization method, of class SuperpersonDaoDB.
     */
    @Test
    public void testGetSuperpeopleForOrganization() {
        
        Organization organization1 = new Organization();
        organization1.setAddress(address1);
        organization1.setEmail("press@birds.com");
        organization1.setOrgDescription("Superheroes of flight");
        organization1.setOrgName("Bird Squad");
        organization1.setPhone("555-555-5555");
        organization1.setSupertype(supertypeDao.getSupertypeById(1));
        organization1 = organizationDao.addOrganization(organization1);
        List<Organization> expectedOrganizations = new ArrayList<>();
        expectedOrganizations.add(organization1);
        
        Organization organization2 = new Organization();
        organization2.setAddress(address1);
        organization2.setEmail("press@fish.com");
        organization2.setOrgDescription("Superheroes of the sea");
        organization2.setOrgName("Marine Squad");
        organization2.setPhone("000-000-0000");
        organization2.setSupertype(supertypeDao.getSupertypeById(1));
        organization2 = organizationDao.addOrganization(organization2);
        List<Organization> unexpectedOrganizations = new ArrayList<>();
        unexpectedOrganizations.add(organization2);
        
        // Create superpeople expected at organization
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        superperson1.setOrganizations(expectedOrganizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
        Superperson superperson2 = new Superperson();
        superperson2.setSuperpersonDescription("Rather average flier");
        superperson2.setSuperpersonName("Bald Eagle");
        superperson2.setSuperpower(superpower1);
        superperson2.setSupertype(supertypeDao.getSupertypeById(1));
        superperson2.setOrganizations(expectedOrganizations);
        superperson2 = superpersonDao.addSuperperson(superperson2);
        
        // Create superperson not expected at organization
        Superperson superperson3 = new Superperson();
        superperson3.setSuperpersonDescription("Seeks revenge through flight");
        superperson3.setSuperpersonName("Flying Avenger");
        superperson3.setSuperpower(superpower1);
        superperson3.setSupertype(supertypeDao.getSupertypeById(1));
        superperson3.setOrganizations(unexpectedOrganizations);
        superperson3 = superpersonDao.addSuperperson(superperson3);
        
        // Get superheroes by organizations
        List<Superperson> superpeopleFromDao = superpersonDao.getSuperpeopleForOrganization(organization1);
        assertEquals(2, superpeopleFromDao.size());
        assertTrue(superpeopleFromDao.contains(superperson1));
        assertTrue(superpeopleFromDao.contains(superperson2));
        assertFalse(superpeopleFromDao.contains(superperson3));
    }
    
}
