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
public class Superpower {

    private int superpowerId;
    private String superpowerName;

    public int getSuperpowerId() {
        return superpowerId;
    }

    public void setSuperpowerId(int superpowerId) {
        this.superpowerId = superpowerId;
    }

    public String getSuperpowerName() {
        return superpowerName;
    }

    public void setSuperpowerName(String superpowerName) {
        this.superpowerName = superpowerName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.superpowerId;
        hash = 59 * hash + Objects.hashCode(this.superpowerName);
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
        final Superpower other = (Superpower) obj;
        if (this.superpowerId != other.superpowerId) {
            return false;
        }
        if (!Objects.equals(this.superpowerName, other.superpowerName)) {
            return false;
        }
        return true;
    }
    
    
}
