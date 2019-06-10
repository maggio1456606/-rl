/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.myrestclient;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 1456606@maggio
 */
@XmlRootElement(name = "Customer")
public class Customer {
    private int uid;
    private String name;
    
    public Customer() {}

    public String getName() {
        return name;
    }

    public int getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
       if(o!=null && o.getClass().equals(this.getClass())) {
           Customer c = (Customer) o;
           return 
                   c.uid == this.uid &&
                   c.name.equals(this.name);
       }
       return false;
    }

    @Override
    public int hashCode() {
        final int hc = 92;
        
        return
                hc  *
               (int) (this.uid +
                this.name.hashCode());
    }

    @Override
    public String toString() {
        return
                "[ " + this.uid + ", " +
                       this.name + " ]";
    }
    
    
    
}
