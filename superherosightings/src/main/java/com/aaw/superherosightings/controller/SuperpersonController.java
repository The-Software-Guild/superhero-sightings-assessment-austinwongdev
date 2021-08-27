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
import com.aaw.superherosightings.dao.SupertypeDao;
import com.aaw.superherosightings.model.Organization;
import com.aaw.superherosightings.model.Superperson;
import com.aaw.superherosightings.model.Superpower;
import com.aaw.superherosightings.model.Supertype;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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
public class SuperpersonController {

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
    
    @Autowired
    SupertypeDao supertypeDao;
    
    Set<ConstraintViolation<Superperson>> violations = new HashSet<>();
    Set<Error> customErrors = new HashSet<>();
    
    @GetMapping("superperson")
    public String displaySuperpeople(Model model){
        model.addAttribute("customErrors", customErrors);
        model.addAttribute("errors", violations);
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        model.addAttribute("supertypes", supertypes);
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        model.addAttribute("superpeople", superpeople);
        return "superperson";
    }
    
    @PostMapping("addSuperperson")
    public String addSuperperson(Superperson superperson, HttpServletRequest request){
        
        customErrors.clear();
        
        // Gather organizations
        String[] orgIds = request.getParameterValues("organizationsVal");
        List<Organization> organizations = new ArrayList<>();
        if (orgIds == null || orgIds.length == 0){
            customErrors.add(new Error("Superperson must belong to at least 1 organization"));
        }
        else{
            for (String orgId : orgIds){
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(orgId)));
            }
        }
        superperson.setOrganizations(organizations);
        
        // Get Supertype
        int supertypeId = Integer.parseInt(request.getParameter("supertypeVal"));
        Supertype supertype = supertypeDao.getSupertypeById(supertypeId);
        superperson.setSupertype(supertype);
        
        // Get Superpower
        int superpowerId = Integer.parseInt(request.getParameter("superpowerVal"));
        Superpower superpower = superpowerDao.getSuperpowerById(superpowerId);
        superperson.setSuperpower(superpower);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superperson);
        
        if (violations.isEmpty() && customErrors.isEmpty()){
            superpersonDao.addSuperperson(superperson);
        }
        
        return "redirect:/superperson";
        
    }
    
    @GetMapping("editSuperperson")
    public String editSuperperson(int id, Model model){
        model.addAttribute("customErrors", customErrors);
        model.addAttribute("errors", violations);
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        model.addAttribute("supertypes", supertypes);
        Superperson superperson = superpersonDao.getSuperpersonById(id);
        model.addAttribute("superperson", superperson);
        List<Organization> superpersonOrganizations = organizationDao.getOrganizationsForSuperperson(superperson);
        model.addAttribute("superpersonOrganizations", superpersonOrganizations);
        return "editSuperperson";
    }
    
    @PostMapping("editSuperperson")
    public String editSuperperson(Superperson superperson, HttpServletRequest request){
        
        customErrors.clear();
        
        // Gather organizations
        String[] orgIds = request.getParameterValues("organizationsVal");
        List<Organization> organizations = new ArrayList<>();
        if (orgIds == null || orgIds.length == 0){
            customErrors.add(new Error("Superperson must belong to at least 1 organization."));
        }
        else{
            for (String orgId : orgIds){
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(orgId)));
            }
        }
        superperson.setOrganizations(organizations);
        
        // Get Supertype
        int supertypeId = Integer.parseInt(request.getParameter("supertypeVal"));
        Supertype supertype = supertypeDao.getSupertypeById(supertypeId);
        superperson.setSupertype(supertype);
        
        // Get Superpower
        int superpowerId = Integer.parseInt(request.getParameter("superpowerVal"));
        Superpower superpower = superpowerDao.getSuperpowerById(superpowerId);
        superperson.setSuperpower(superpower);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superperson);
        
        if (violations.isEmpty() && customErrors.isEmpty()){
            superpersonDao.updateSuperperson(superperson);
            return "redirect:/superpersonDetail?id="+superperson.getSuperpersonId();
        }
        
        return "redirect:/editSuperperson?id="+superperson.getSuperpersonId();
    }
    
    @GetMapping("superpersonDetail")
    public String superpersonDetail(int id, Model model){
        Superperson superperson = superpersonDao.getSuperpersonById(id);
        model.addAttribute("superperson", superperson);
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        List<Organization> superpersonOrganizations = organizationDao.getOrganizationsForSuperperson(superperson);
        model.addAttribute("organizations", superpersonOrganizations);
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        model.addAttribute("supertypes", supertypes);
        return "superpersonDetail";
    }
    
    @GetMapping("confirmDeleteSuperperson")
    public String confirmDeleteSuperperson(int id, Model model){
        Superperson superperson = superpersonDao.getSuperpersonById(id);
        model.addAttribute("superperson", superperson);
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        List<Organization> superpersonOrganizations = organizationDao.getOrganizationsForSuperperson(superperson);
        model.addAttribute("organizations", superpersonOrganizations);
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        model.addAttribute("supertypes", supertypes);
        return "confirmDeleteSuperperson";
    }
    
    @GetMapping("deleteSuperperson")
    public String deleteSuperperson(int id){
        superpersonDao.deleteSuperpersonById(id);
        return "redirect:/superperson";
    }
    
}
