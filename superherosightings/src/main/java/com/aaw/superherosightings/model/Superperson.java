/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.model;

import java.util.Objects;

/**
 *
 * @author Austin Wong
 */
public class Superperson {

    private int superpersonId;
    private String superpersonName;
    private String superpersonDescription;
    private Supertype supertype;
    private Superpower superpower;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.superpersonId;
        hash = 59 * hash + Objects.hashCode(this.superpersonName);
        hash = 59 * hash + Objects.hashCode(this.superpersonDescription);
        hash = 59 * hash + Objects.hashCode(this.supertype);
        hash = 59 * hash + Objects.hashCode(this.superpower);
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
        return true;
    }
    
    
}
