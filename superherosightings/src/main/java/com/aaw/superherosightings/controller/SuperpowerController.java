/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 26, 2021
 * purpose: 
 */

package com.aaw.superherosightings.controller;

import com.aaw.superherosightings.dao.LocationDao;
import com.aaw.superherosightings.dao.OrganizationDao;
import com.aaw.superherosightings.dao.SightingDao;
import com.aaw.superherosightings.dao.SuperpersonDao;
import com.aaw.superherosightings.dao.SuperpowerDao;
import com.aaw.superherosightings.model.Superpower;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Austin Wong
 */

@Controller
public class SuperpowerController {

    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperpowerDao superpowerDao;
    
    @Autowired
    SuperpersonDao superpersonDao;
    
    @GetMapping("superpower")
    public String displaySuperpeople(Model model){
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpower";
    }
    
    @PostMapping("addSuperpower")
    public String addSuperpower(String superpowerName) {
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName(superpowerName);
        superpowerDao.addSuperpower(superpower);
        return "redirect:/superpower";
    }
    
    @PostMapping("editSuperpower")
    public String editSuperpower(Superpower superpower){
        superpowerDao.updateSuperpower(superpower);
        return "redirect:/superpowerDetail?id="+superpower.getSuperpowerId();
    }
    
    @GetMapping("editSuperpower")
    public String editSuperpower(int id, Model model){
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }
    
    @GetMapping("confirmDeleteSuperpower")
    public String confirmDeleteSuperpower(int id, Model model){
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "confirmDeleteSuperpower";
    }
    
    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(int id){
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpower";
    }
    
    @GetMapping("superpowerDetail")
    public String superpowerDetail(int id, Model model){
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "superpowerDetail";
    }
}
