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
import com.aaw.superherosightings.model.Address;
import com.aaw.superherosightings.model.Location;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import static org.apache.tomcat.jni.Buffer.address;
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
public class LocationController {
    
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
    
    @GetMapping("location")
    public String displayLocations(Model model){
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "location";
    }
    
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request){
        Address address = new Address();
        address.setAddress(request.getParameter("address"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));
        address.setZip(request.getParameter("ZIP"));
        Location location = new Location();
        location.setLatitude(Double.parseDouble(request.getParameter("latitude")));
        location.setLongitude(Double.parseDouble(request.getParameter("longitude")));
        location.setLocationDescription(request.getParameter("locationDescription"));
        location.setLocationName(request.getParameter("locationName"));
        location.setAddress(address);
        locationDao.addLocation(location);
        return "redirect:/location";
    }
    
    @GetMapping("editLocation")
    public String editLocation(int id, Model model){
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String editLocation(Location location, HttpServletRequest request){
        Address address = new Address();
        address.setAddress(request.getParameter("addressLine"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));
        address.setZip(request.getParameter("ZIP"));
        location.setAddress(address);
        locationDao.updateLocation(location);
        return "redirect:/locationDetail?id="+location.getLocationId();
    }
    
    @GetMapping("confirmDeleteLocation")
    public String confirmDeleteLocation(int id, Model model){
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "confirmDeleteLocation";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(int id){
        locationDao.deleteLocationById(id);
        return "redirect:/location";
    }
    
    @GetMapping("locationDetail")
    public String locationDetail(int id, Model model){
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "locationDetail";
    }
}
