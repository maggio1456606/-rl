/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.myrestserver;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

/**
 *
 * @author 1456606@maggio
 */
public class MyServer {
    
    private static final String URL_ADDRESS = "http://localhost:4242/";
    
    public static void main(String[]args) {
        System.out.println("Server start..");
        
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        
        factoryBean.setResourceClasses(BankManager.class);
        factoryBean.setResourceProvider(new SingletonResourceProvider(new BankManager()));
        factoryBean.setAddress(URL_ADDRESS);	
        
        Server server = factoryBean.create();
        
        System.out.println("Server ready..");
    }
}