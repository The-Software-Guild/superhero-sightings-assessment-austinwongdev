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
import com.aaw.superherosightings.model.Address;
import com.aaw.superherosightings.model.Organization;
import com.aaw.superherosightings.model.Superperson;
import com.aaw.superherosightings.model.Supertype;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
public class OrganizationController {
    
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
    
    @GetMapping("organization")
    public String displayOrganization(Model model){
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        List<Superperson> members = superpersonDao.getAllSuperpeople();
        model.addAttribute("supertypes", supertypes);
        model.addAttribute("organizations", organizations);
        model.addAttribute("members", members);
        return "organization";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request){
        Address address = new Address();
        address.setAddress(request.getParameter("addressLine"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));
        address.setZip(request.getParameter("zip"));
        organization.setAddress(address);
        int supertypeId = Integer.parseInt(request.getParameter("supertypeVal"));
        Supertype supertype = supertypeDao.getSupertypeById(supertypeId);
        organization.setSupertype(supertype);
        String[] memberIds = request.getParameterValues("memberIds");
        List<Superperson> members = new ArrayList<>();
        if (memberIds != null && memberIds.length > 0){
            for (String superpersonId : memberIds){
                members.add(superpersonDao.getSuperpersonById(Integer.parseInt(superpersonId)));
            }
        }
        organization.setMembers(members);
        organizationDao.addOrganization(organization);
        return "redirect:/organization";
    }
    
    @GetMapping("organizationDetail")
    public String organizationDetail(int id, Model model){
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        List<Superperson> members = superpersonDao.getAllSuperpeople();
        model.addAttribute("supertypes", supertypes);
        model.addAttribute("members", members);
        
        return "organizationDetail";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(int id, Model model){
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        List<Superperson> members = superpersonDao.getAllSuperpeople();
        model.addAttribute("supertypes", supertypes);
        model.addAttribute("members", members);
        
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String editOrganization(Organization organization, HttpServletRequest request){
        Address address = new Address();
        address.setAddress(request.getParameter("addressLine"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));
        address.setZip(request.getParameter("zip"));
        organization.setAddress(address);
        int supertypeId = Integer.parseInt(request.getParameter("supertypeVal"));
        Supertype supertype = supertypeDao.getSupertypeById(supertypeId);
        organization.setSupertype(supertype);
        String[] memberIds = request.getParameterValues("memberIds");
        List<Superperson> members = new ArrayList<>();
        if (memberIds != null && memberIds.length > 0){
            for (String superpersonId : memberIds){
                members.add(superpersonDao.getSuperpersonById(Integer.parseInt(superpersonId)));
            }
        }
        organization.setMembers(members);
        organizationDao.updateOrganization(organization);
        return "redirect:/organizationDetail?id="+organization.getOrgId();
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(int id){
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organization";
    }
    
    @GetMapping("confirmDeleteOrganization")
    public String confirmDeleteOrganization(int id, Model model){
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        List<Supertype> supertypes = supertypeDao.getAllSupertypes();
        List<Superperson> members = superpersonDao.getAllSuperpeople();
        model.addAttribute("supertypes", supertypes);
        model.addAttribute("members", members);
        
        return "confirmDeleteOrganization";
    }
}
