package com.maggio.myrestclient;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 1456606@maggio
 */
@XmlRootElement(name = "BankAccount")
public class BankAccount {
    private int id;
    private float amount;
    private Customer customer;

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        if(customer!=null)
            return customer;
        else 
            throw new RuntimeException("Customer is null!");
    }

    public void setCustomer(Customer customer) {
        if(customer!=null)
            this.customer = customer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
       if(o!=null && o.getClass().equals(this.getClass())) {
           BankAccount ba = (BankAccount) o;
           return 
                   this.id == ba.id &&
                   this.amount == ba.amount &&
                   this.customer == ba.customer;
       }
       return false;
    }

    @Override
    public int hashCode() {
        final int hc = 92;
        
        return  
                hc  *
                (int) (this.id + 
                this.amount +
                this.customer.hashCode());
    }

    @Override
    public String toString() {
        return
                "[ " + this.id + ", " +
                       this.amount + ", " +
                       this.customer +" ]";
    }
    
    
    
    
    
    
    
    
    
}
