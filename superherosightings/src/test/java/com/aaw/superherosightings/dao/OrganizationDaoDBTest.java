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
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class OrganizationDaoDBTest {
    
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
    
    public OrganizationDaoDBTest() {
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
     * Test of addOrganization and getOrganizationById methods, of class OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganizationById() {
        Organization organization1 = new Organization();
        organization1.setAddress(address1);
        organization1.setEmail("press@birds.com");
        organization1.setOrgDescription("Superheroes of flight");
        organization1.setOrgName("Bird Squad");
        organization1.setPhone("555-555-5555");
        organization1.setSupertype(supertypeDao.getSupertypeById(1));
        organization1.setMembers(new ArrayList<Superperson>());
        organization1 = organizationDao.addOrganization(organization1);
        
        Organization organizationFromDao = organizationDao.getOrganizationById(organization1.getOrgId());
        assertNotNull(organizationFromDao);
        assertEquals(organization1, organizationFromDao);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization organization1 = new Organization();
        organization1.setAddress(address1);
        organization1.setEmail("press@birds.com");
        organization1.setOrgDescription("Superheroes of flight");
        organization1.setOrgName("Bird Squad");
        organization1.setPhone("555-555-5555");
        organization1.setSupertype(supertypeDao.getSupertypeById(1));
        organization1.setMembers(new ArrayList<Superperson>());
        organization1 = organizationDao.addOrganization(organization1);
        
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
        organization2.setMembers(new ArrayList<Superperson>());
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization1));
        assertTrue(organizations.contains(organization2));
    }
    
    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Organization organization1 = new Organization();
        organization1.setAddress(address1);
        organization1.setEmail("press@birds.com");
        organization1.setOrgDescription("Superheroes of flight");
        organization1.setOrgName("Bird Squad");
        organization1.setPhone("555-555-5555");
        organization1.setSupertype(supertypeDao.getSupertypeById(1));
        organization1.setMembers(new ArrayList<Superperson>());
        organization1 = organizationDao.addOrganization(organization1);
        
        address1.setAddress("456 Real St");
        address1.setCity("Tucson");
        address1.setState("AZ");
        address1.setZip("80210");
        organization1.setAddress(address1);
        organization1.setEmail("press@villains.com");
        organization1.setOrgDescription("Supervillains of flight");
        organization1.setOrgName("Evil Bird Squad");
        organization1.setPhone("808-808-8080");
        organization1.setSupertype(supertypeDao.getSupertypeById(2));
        organizationDao.updateOrganization(organization1);
        
        Organization fromDao = organizationDao.getOrganizationById(organization1.getOrgId());
        assertEquals(organization1, fromDao);
        
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() {
        Organization organization1 = new Organization();
        organization1.setAddress(address1);
        organization1.setEmail("press@birds.com");
        organization1.setOrgDescription("Superheroes of flight");
        organization1.setOrgName("Bird Squad");
        organization1.setPhone("555-555-5555");
        organization1.setSupertype(supertypeDao.getSupertypeById(1));
        organization1.setMembers(new ArrayList<Superperson>());
        organization1 = organizationDao.addOrganization(organization1);
        
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
        organization2.setMembers(new ArrayList<Superperson>());
        organization2 = organizationDao.addOrganization(organization2);
        final int organization2Id = organization2.getOrgId();
        
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        superperson1.setOrganizations(organizationDao.getAllOrganizations());
        superperson1 = superpersonDao.addSuperperson(superperson1);
        assertTrue(superperson1.getOrganizations().contains(organization1));
        
        Address addressFromDao = addressDao.getAddressById(organization1.getAddress().getAddressId());
        assertNotNull(addressFromDao);
        
        organizationDao.deleteOrganizationById(organization1.getOrgId());
        Organization organizationFromDao = organizationDao.getOrganizationById(organization1.getOrgId());
        assertNull(organizationFromDao);
        
        superperson1 = superpersonDao.getSuperpersonById(superperson1.getSuperpersonId());
        final int org1Id = organization1.getOrgId();
        assertFalse(superperson1.getOrganizations().stream().anyMatch(o -> o.getOrgId() == org1Id));
        final int org2Id = organization2.getOrgId();
        assertTrue(superperson1.getOrganizations().stream().anyMatch(o -> o.getOrgId() == org2Id));
        
        addressFromDao = addressDao.getAddressById(addressFromDao.getAddressId());
        assertNull(addressFromDao);
        
    }

    /**
     * Test of getOrganizationsForSuperperson method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetOrganizationsForSuperperson() {
        
        // Create organizations that superperson1 is a member of
        Organization organization1 = new Organization();
        organization1.setAddress(address1);
        organization1.setEmail("press@birds.com");
        organization1.setOrgDescription("Superheroes of flight");
        organization1.setOrgName("Bird Squad");
        organization1.setPhone("555-555-5555");
        organization1.setSupertype(supertypeDao.getSupertypeById(1));
        organization1.setMembers(new ArrayList<Superperson>());
        organization1 = organizationDao.addOrganization(organization1);
        
        Organization organization2 = new Organization();
        Address address2 = new Address();
        address2.setAddress("456 Real St");
        address2.setCity("Tucson");
        address2.setState("AZ");
        address2.setZip("80210");
        organization2.setAddress(address2);
        organization2.setEmail("press@justiceleague.com");
        organization2.setOrgDescription("Team of Heroes Dedicated to Justice");
        organization2.setOrgName("Justice League");
        organization2.setPhone("808-808-8089");
        organization2.setSupertype(supertypeDao.getSupertypeById(1));
        organization2.setMembers(new ArrayList<Superperson>());
        organization2 = organizationDao.addOrganization(organization2);
        
        // Create organization that superperson1 is NOT a member of
        Organization organization3 = new Organization();
        Address address3 = new Address();
        address3.setAddress("456 Real St");
        address3.setCity("Tucson");
        address3.setState("AZ");
        address3.setZip("80210");
        organization3.setAddress(address3);
        organization3.setEmail("press@villains.com");
        organization3.setOrgDescription("Supervillains of flight");
        organization3.setOrgName("Evil Bird Squad");
        organization3.setPhone("808-808-8080");
        organization3.setSupertype(supertypeDao.getSupertypeById(2));
        organization3.setMembers(new ArrayList<Superperson>());
        organization3 = organizationDao.addOrganization(organization3);
        
        // Create superperson
        Superperson superperson1 = new Superperson();
        superperson1.setSuperpersonDescription("Can fly to the stratosphere");
        superperson1.setSuperpersonName("Hawkman");
        superperson1.setSuperpower(superpower1);
        superperson1.setSupertype(supertypeDao.getSupertypeById(1));
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        organizations.add(organization2);
        superperson1.setOrganizations(organizations);
        superperson1 = superpersonDao.addSuperperson(superperson1);
        
        // Refresh organizations
        organization1 = organizationDao.getOrganizationById(organization1.getOrgId());
        organization2 = organizationDao.getOrganizationById(organization2.getOrgId());
        organization3 = organizationDao.getOrganizationById(organization3.getOrgId());
        
        List<Organization> expectedOrganizations = organizationDao.getOrganizationsForSuperperson(superperson1);
        assertEquals(2, expectedOrganizations.size());
        assertTrue(expectedOrganizations.contains(organization1));
        assertTrue(expectedOrganizations.contains(organization2));
        assertFalse(expectedOrganizations.contains(organization3));
    }
    
}
