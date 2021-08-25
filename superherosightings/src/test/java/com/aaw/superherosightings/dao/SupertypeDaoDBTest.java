/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aaw.superherosightings.dao;

import com.aaw.superherosightings.model.Supertype;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class SupertypeDaoDBTest {
    
    @Autowired
    SupertypeDao supertypeDao;
    
    public SupertypeDaoDBTest() {
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
     * Test of getSupertypeById method, of class SupertypeDaoDB.
     */
    @Test
    public void testGetSupertypeById() {
        Supertype supertype1 = new Supertype();
        supertype1.setSupertypeName("Superhero");
        supertype1.setSupertypeId(1);
        
        Supertype supertype2 = new Supertype();
        supertype2.setSupertypeName("Supervillain");
        supertype2.setSupertypeId(2);
        
        Supertype supertype1FromDao = supertypeDao.getSupertypeById(1);
        assertEquals(supertype1, supertype1FromDao);
        
        Supertype supertype2FromDao = supertypeDao.getSupertypeById(2);
        assertEquals(supertype2, supertype2FromDao);
    }

    /**
     * Test of getAllSupertypes method, of class SupertypeDaoDB.
     */
    @Test
    public void testGetAllSupertypes() {
        Supertype supertype1 = new Supertype();
        supertype1.setSupertypeName("Superhero");
        supertype1.setSupertypeId(1);
        
        Supertype supertype2 = new Supertype();
        supertype2.setSupertypeName("Supervillain");
        supertype2.setSupertypeId(2);
        
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        assertEquals(2, supertypes.size());
        assertTrue(supertypes.contains(supertype1));
        assertTrue(supertypes.contains(supertype2));
        
    }
    
}
