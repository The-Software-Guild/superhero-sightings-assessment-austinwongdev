/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
