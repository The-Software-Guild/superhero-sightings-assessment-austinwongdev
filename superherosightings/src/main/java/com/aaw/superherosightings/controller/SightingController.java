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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    
    Set<Error> errors = new HashSet<>();
    
    @GetMapping("sighting")
    public String displaySighting(Model model){
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        List<Location> locations = locationDao.getAllLocations();
        
        model.addAttribute("errors", errors);
        model.addAttribute("sightings", sightings);
        model.addAttribute("superpeople", superpeople);
        model.addAttribute("locations", locations);
        
        return "sighting";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request, Model model){
        Sighting sighting = new Sighting();
        
        errors.clear();
        
        // Must have a superperson
        String superpersonIdStr = request.getParameter("superpersonVal");
        if (superpersonIdStr == null){
            Error error = new Error("Sighting must include a superperson.");
            errors.add(error);
        }
        else{
            int superpersonId = Integer.parseInt(superpersonIdStr);
            Superperson superperson = superpersonDao.getSuperpersonById(superpersonId);
            sighting.setSuperperson(superperson);
        }
        
        // Must have a location
        String locationIdStr = request.getParameter("locationVal");
        if (locationIdStr == null){
            Error error = new Error("Sighting must include a location.");
            errors.add(error);
        }
        else{
            int locationId = Integer.parseInt(request.getParameter("locationVal"));
            Location location = locationDao.getLocationById(locationId);
            sighting.setLocation(location);
        }
        
        // Must have a past/present datetime
        String dateStr = request.getParameter("dateVal");
        String timeStr = request.getParameter("timeVal");
        if (dateStr == null || timeStr == null || dateStr.isEmpty() || timeStr.isEmpty()){
            Error error = new Error("Sighting must include a date and time.");
            errors.add(error);
        }
        else{
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);
            LocalDateTime sightingDateTime = LocalDateTime.of(date, time);
            if (sightingDateTime.isAfter(LocalDateTime.now())){
                Error error = new Error("Sighting must be in past or present.");
                errors.add(error);
            }
            else{
                sighting.setSightingDatetime(sightingDateTime);
            }
        }
        
        // Success
        if (errors.isEmpty()){
            sightingDao.addSighting(sighting);
        }
        return "redirect:/sighting";
    }
    
    @GetMapping("editSighting")
    public String editSighting(int id, Model model){
        
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        List<Location> locations = locationDao.getAllLocations();
        Sighting sighting = sightingDao.getSightingById(id);
        
        model.addAttribute("superpeople", superpeople);
        model.addAttribute("locations", locations);
        model.addAttribute("sighting", sighting);
        model.addAttribute("sightingDate", sighting.getSightingDatetime().toLocalDate());
        model.addAttribute("sightingTime", sighting.getSightingDatetime().toLocalTime());
        
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String editSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model){
        
        // Must have a superperson
        String superpersonIdStr = request.getParameter("superpersonVal");
        if (superpersonIdStr == null){
            FieldError error = new FieldError("sighting", "superperson", "Sighting must include a superperson.");
            result.addError(error);
        }
        else{
            int superpersonId = Integer.parseInt(superpersonIdStr);
            Superperson superperson = superpersonDao.getSuperpersonById(superpersonId);
            sighting.setSuperperson(superperson);
        }
        
        // Must have a location
        String locationIdStr = request.getParameter("locationVal");
        if (locationIdStr == null){
            FieldError error = new FieldError("sighting", "location", "Sighting must include a location.");
            result.addError(error);
        }
        else{
            int locationId = Integer.parseInt(request.getParameter("locationVal"));
            Location location = locationDao.getLocationById(locationId);
            sighting.setLocation(location);
        }
        
        // Must have a past/present datetime
        String dateStr = request.getParameter("dateVal");
        String timeStr = request.getParameter("timeVal");
        if (dateStr == null || timeStr == null || dateStr.isEmpty() || timeStr.isEmpty()){
            FieldError error = new FieldError("sighting", "sightingDatetime", "Sighting must include a date and time.");
            result.addError(error);
        }
        else{
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);
            LocalDateTime sightingDateTime = LocalDateTime.of(date, time);
            if (sightingDateTime.isAfter(LocalDateTime.now())){
                FieldError error = new FieldError("sighting", "sightingDatetime", "Sighting must be in past or present.");
                result.addError(error);
            }
            else{
                sighting.setSightingDatetime(sightingDateTime);
            }
        }
        
        // Failure -> Reset fields
        if (result.hasErrors()){
            model.addAttribute("sighting", sighting);
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("superpeople", superpersonDao.getAllSuperpeople());
            sighting = sightingDao.getSightingById(sighting.getSightingId());
            model.addAttribute("sightingDate", sighting.getSightingDatetime().toLocalDate());
            model.addAttribute("sightingTime", sighting.getSightingDatetime().toLocalTime());
            return "editSighting";
        }
        
        // Success
        sightingDao.updateSighting(sighting);
        return "redirect:/sightingDetail?id="+sighting.getSightingId();
    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(int id, Model model){
        
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        List<Location> locations = locationDao.getAllLocations();
        Sighting sighting = sightingDao.getSightingById(id);
        
        model.addAttribute("superpeople", superpeople);
        model.addAttribute("locations", locations);
        model.addAttribute("sighting", sighting);
        model.addAttribute("sightingDate", sighting.getSightingDatetime().toLocalDate());
        model.addAttribute("sightingTime", sighting.getSightingDatetime().toLocalTime());
        
        return "sightingDetail";
    }
    
    @GetMapping("confirmDeleteSighting")
    public String confirmDeleteSighting(int id, Model model){
        
        List<Superperson> superpeople = superpersonDao.getAllSuperpeople();
        List<Location> locations = locationDao.getAllLocations();
        Sighting sighting = sightingDao.getSightingById(id);
        
        model.addAttribute("superpeople", superpeople);
        model.addAttribute("locations", locations);
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
