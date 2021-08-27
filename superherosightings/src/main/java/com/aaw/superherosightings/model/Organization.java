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

/**
 *
 * @author Austin Wong
 */
public class Organization {

    private int orgId;
    private String orgName;
    private String orgDescription;
    private String email;
    private String phone;
    private Address address;
    private Supertype supertype;
    private List<Superperson> members;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Supertype getSupertype() {
        return supertype;
    }

    public void setSupertype(Supertype supertype) {
        this.supertype = supertype;
    }

    public List<Superperson> getMembers() {
        return members;
    }

    public void setMembers(List<Superperson> members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.orgId;
        hash = 67 * hash + Objects.hashCode(this.orgName);
        hash = 67 * hash + Objects.hashCode(this.orgDescription);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.phone);
        hash = 67 * hash + Objects.hashCode(this.address);
        hash = 67 * hash + Objects.hashCode(this.supertype);
        hash = 67 * hash + Objects.hashCode(this.members);
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
        final Organization other = (Organization) obj;
        if (this.orgId != other.orgId) {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) {
            return false;
        }
        if (!Objects.equals(this.orgDescription, other.orgDescription)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.supertype, other.supertype)) {
            return false;
        }
        
        Set<Integer> thisMemberIds = new HashSet<>();
        Set<Integer> otherMemberIds = new HashSet<>();
        
        for (Superperson supe : this.members){
            thisMemberIds.add(supe.getSuperpersonId());
        }
        for (Superperson supe : other.members){
            otherMemberIds.add(supe.getSuperpersonId());
        }
        
        if (!Objects.equals(thisMemberIds, otherMemberIds)) {
            return false;
        }
        
        return true;
    }

        
}
