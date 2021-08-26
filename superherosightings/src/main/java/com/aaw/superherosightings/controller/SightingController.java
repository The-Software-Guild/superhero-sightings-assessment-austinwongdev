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
import com.aaw.superherosightings.model.Location;
import com.aaw.superherosightings.model.Sighting;
import com.aaw.superherosightings.model.Superperson;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class SightingController {

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
    
    @GetMapping("sighting")
    public String displaySighting(Model model){
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        model.addAttribute("superpeople", superpeople);
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "sighting";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request){
        Sighting sighting = new Sighting();
        
        int superpersonId = Integer.parseInt(request.getParameter("superpersonVal"));
        Superperson superperson = superpersonDao.getSuperpersonById(superpersonId);
        sighting.setSuperperson(superperson);
        
        int locationId = Integer.parseInt(request.getParameter("locationVal"));
        Location location = locationDao.getLocationById(locationId);
        sighting.setLocation(location);
        
        LocalDate date = LocalDate.parse(request.getParameter("dateVal"));
        LocalTime time = LocalTime.parse(request.getParameter("timeVal"));
        LocalDateTime sightingDateTime = LocalDateTime.of(date, time);
        sighting.setSightingDatetime(sightingDateTime);
        
        sightingDao.addSighting(sighting);
        return "redirect:/sighting";
    }
    
    @GetMapping("editSighting")
    public String editSighting(int id, Model model){
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        model.addAttribute("superpeople", superpeople);
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        model.addAttribute("sightingDate", sighting.getSightingDatetime().toLocalDate());
        model.addAttribute("sightingTime", sighting.getSightingDatetime().toLocalTime());
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String editSighting(Sighting sighting, HttpServletRequest request){
        int superpersonId = Integer.parseInt(request.getParameter("superpersonVal"));
        Superperson superperson = superpersonDao.getSuperpersonById(superpersonId);
        sighting.setSuperperson(superperson);
        
        int locationId = Integer.parseInt(request.getParameter("locationVal"));
        Location location = locationDao.getLocationById(locationId);
        sighting.setLocation(location);
        
        LocalDate date = LocalDate.parse(request.getParameter("dateVal"));
        LocalTime time = LocalTime.parse(request.getParameter("timeVal"));
        LocalDateTime sightingDateTime = LocalDateTime.of(date, time);
        sighting.setSightingDatetime(sightingDateTime);
        
        sightingDao.updateSighting(sighting);
        
        return "redirect:/sightingDetail?id="+sighting.getSightingId();
    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(int id, Model model){
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        model.addAttribute("superpeople", superpeople);
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        model.addAttribute("sightingDate", sighting.getSightingDatetime().toLocalDate());
        model.addAttribute("sightingTime", sighting.getSightingDatetime().toLocalTime());
        return "sightingDetail";
    }
    
    @GetMapping("confirmDeleteSighting")
    public String confirmDeleteSighting(int id, Model model){
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        model.addAttribute("superpeople", superpeople);
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        model.addAttribute("sightingDate", sighting.getSightingDatetime().toLocalDate());
        model.addAttribute("sightingTime", sighting.getSightingDatetime().toLocalTime());
        return "confirmDeleteSighting";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(int id){
        sightingDao.deleteSightingById(id);
        return "redirect:/sighting";
    }
    
    @GetMapping()
    public String displayIndex(Model model){
        List<Sighting> sightings = sightingDao.getMostRecentSightings(10);
        model.addAttribute("sightings", sightings);
        return "index";
    }
}
