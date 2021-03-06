/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Austin Wong
 */
public class Superperson {
    
    private int superpersonId;
    
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 50, message = "Name must be less than 50 characters.")
    private String superpersonName;
    
    @NotBlank(message = "Description must not be empty.")
    @Size(max = 50, message = "Description must be less than 100 characters.")
    private String superpersonDescription;
    
    private Supertype supertype;
    private Superpower superpower;
    private List<Organization> organizations;

    public int getSuperpersonId() {
        return superpersonId;
    }

    public void setSuperpersonId(int superpersonId) {
        this.superpersonId = superpersonId;
    }

    public String getSuperpersonName() {
        return superpersonName;
    }

    public void setSuperpersonName(String superpersonName) {
        this.superpersonName = superpersonName;
    }

    public String getSuperpersonDescription() {
        return superpersonDescription;
    }

    public void setSuperpersonDescription(String superpersonDescription) {
        this.superpersonDescription = superpersonDescription;
    }

    public Supertype getSupertype() {
        return supertype;
    }

    public void setSupertype(Supertype supertype) {
        this.supertype = supertype;
    }

    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) {
        this.superpower = superpower;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.superpersonId;
        hash = 71 * hash + Objects.hashCode(this.superpersonName);
        hash = 71 * hash + Objects.hashCode(this.superpersonDescription);
        hash = 71 * hash + Objects.hashCode(this.supertype);
        hash = 71 * hash + Objects.hashCode(this.superpower);
        hash = 71 * hash + Objects.hashCode(this.organizations);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Superperson other = (Superperson) obj;
        if (this.superpersonId != other.superpersonId) {
            return false;
        }
        if (!Objects.equals(this.superpersonName, other.superpersonName)) {
            return false;
        }
        if (!Objects.equals(this.superpersonDescription, other.superpersonDescription)) {
            return false;
        }
        if (!Objects.equals(this.supertype, other.supertype)) {
            return false;
        }
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        
        Set<Integer> thisOrgIds = new HashSet<>();
        Set<Integer> otherOrgIds = new HashSet<>();
        
        for (Organization org : this.organizations){
            thisOrgIds.add(org.getOrgId());
        }
        for (Organization org : other.organizations){
            otherOrgIds.add(org.getOrgId());
        }
        
        if (!Objects.equals(thisOrgIds, otherOrgIds)) {
            return false;
        }
        return true;
    }

    
}
