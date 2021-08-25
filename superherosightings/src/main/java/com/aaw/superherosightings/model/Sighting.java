/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 25, 2021
 * purpose: 
 */

package com.aaw.superherosightings.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Austin Wong
 */
public class Sighting {

    private int sightingId;
    private Superperson superperson;
    private Location location;
    private LocalDateTime sightingDatetime;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public Superperson getSuperperson() {
        return superperson;
    }

    public void setSuperperson(Superperson superperson) {
        this.superperson = superperson;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getSightingDatetime() {
        return sightingDatetime;
    }

    public void setSightingDatetime(LocalDateTime sightingDatetime) {
        this.sightingDatetime = sightingDatetime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.sightingId;
        hash = 23 * hash + Objects.hashCode(this.superperson);
        hash = 23 * hash + Objects.hashCode(this.location);
        hash = 23 * hash + Objects.hashCode(this.sightingDatetime);
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
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.superperson, other.superperson)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.sightingDatetime, other.sightingDatetime)) {
            return false;
        }
        return true;
    }
    
    
}
