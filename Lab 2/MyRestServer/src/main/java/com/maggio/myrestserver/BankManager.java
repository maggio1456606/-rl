/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.myrestserver;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


/**
 *
 * @author 1456606@maggio
 */

@Path("bank")
@Produces("text/xml")
public class BankManager {
    private Map<Integer,BankAccount> accounts;
    
    public BankManager() {
        accounts = new HashMap<>();
        
        Customer c1 = new Customer () ;
        Customer c2 = new Customer () ;
        Customer c3 = new Customer () ;
        
        c1.setUid(1);
        c1.setName("Luca");
        c2.setUid(2);
        c2.setName("Mario");
        c3.setUid(3);
        c3.setName("Vincenzo");
        
        BankAccount a1 = new BankAccount();
        BankAccount a2 = new BankAccount();
        BankAccount a3 = new BankAccount();
        
        a1.setId(1);
        a1.setAmount(10000);
        a1.setCustomer(c1);
        
        a2.setId(2);
        a2.setAmount(50000);
        a2.setCustomer(c2);
        
        a3.setId(3);
        a3.setAmount(200000);
        a3.setCustomer(c3);
        
        
        accounts.put(1, a1);
        accounts.put(2, a2);
        accounts.put(3, a3);
        
        
    }
    
    //CRUD functions
    
    
    
    //R(ead)
    @GET
    @Path("accounts/{aid}")
    public BankAccount getBankAccount(@PathParam("aid") int aid) {
        return findById(aid);
    }
    
    private BankAccount findById(int id) {
        for (Map.Entry<Integer, BankAccount> account : accounts.entrySet()) {
            if (account.getKey() == id) {
                return account.getValue();
            }
        }
        return null;
    }
    
    //C(reate)
    @POST
    @Path("")
    public Response createBankAccount(BankAccount ba) {
    for (Map.Entry<Integer, BankAccount> element : accounts.entrySet()) {
        if (element.getValue().getId() == ba.getId()) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
    accounts.put(accounts.size()+1,ba);
    return Response.ok(ba).build();
}
    
    //U(pdate)
    @PUT
    @Path("accounts/{aid}")
    public Response updateBankAccount(@PathParam("aid") int aid, BankAccount newBankAccount) {
        BankAccount existingAccount = findById(aid);        
        if (existingAccount == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (existingAccount.equals(newBankAccount)) {
            return Response.notModified().build();    
        }
        accounts.put(aid, newBankAccount);
        return Response.ok().build();
}
    
    @Path("accounts/{aid}/customers")
    public BankAccount pathToCustomer (@PathParam("aid") int aid) {
        return findById(aid);
    }
    
    
}
