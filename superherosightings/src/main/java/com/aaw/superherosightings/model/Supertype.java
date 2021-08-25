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
public class Supertype {

    private int supertypeId;
    private String supertypeName;

    public int getSupertypeId() {
        return supertypeId;
    }

    public void setSupertypeId(int supertypeId) {
        this.supertypeId = supertypeId;
    }

    public String getSupertypeName() {
        return supertypeName;
    }

    public void setSupertypeName(String supertypeName) {
        this.supertypeName = supertypeName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.supertypeId;
        hash = 71 * hash + Objects.hashCode(this.supertypeName);
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
        final Supertype other = (Supertype) obj;
        if (this.supertypeId != other.supertypeId) {
            return false;
        }
        if (!Objects.equals(this.supertypeName, other.supertypeName)) {
            return false;
        }
        return true;
    }
    
}
