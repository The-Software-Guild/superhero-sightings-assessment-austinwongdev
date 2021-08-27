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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    
    Set<ConstraintViolation<Superpower>> violations = new HashSet<>();
    Set<Error> customErrors = new HashSet<>();
    
    @GetMapping("superpower")
    public String displaySuperpowers(Model model){
        
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        
        refreshCustomErrors();
        model.addAttribute("errors", violations);
        model.addAttribute("customErrors", customErrors);
        model.addAttribute("superpowers", superpowers);
        
        return "superpower";
    }
    
    private void refreshCustomErrors(){
        customErrors = customErrors.stream().filter(e -> e.isOld() == false).collect(Collectors.toSet());
        customErrors.stream().forEach(e -> e.setOld(true));
    }
    
    @PostMapping("addSuperpower")
    public String addSuperpower(String superpowerName) {
        
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName(superpowerName);
        
        // Validate
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superpower);
        if(violations.isEmpty()){
            superpowerDao.addSuperpower(superpower);
        }
        
        return "redirect:/superpower";
    }
    
    @PostMapping("editSuperpower")
    public String editSuperpower(@Valid Superpower superpower, BindingResult result){
        
        if (result.hasErrors()){
            return "editSuperpower";
        }
        
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
        
        model.addAttribute("customErrors", customErrors);
        model.addAttribute("superpower", superpower);
        
        return "confirmDeleteSuperpower";
    }
    
    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(int id){
        if (!superpowerDao.deleteSuperpowerById(id)){
            customErrors.add(new Error("Superpower in use. Must edit or delete superpeople with superpower first."));
        } else{
            customErrors.clear();
        }
        return "redirect:/superpower";
    }
    
    @GetMapping("superpowerDetail")
    public String superpowerDetail(int id, Model model){
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "superpowerDetail";
    }
}
