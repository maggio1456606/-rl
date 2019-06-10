/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.myrestclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;

/**
 *
 * @author workl
 */
public class Client {
    private final static String BASE_URL = "http://localhost:4242/bank/accounts/";
    private static CloseableHttpClient client;
    
    public static void createClient() {
        client = HttpClients.createDefault();
    }
    
    public static void closeClient() throws IOException {
        client.close();
    }
    
    //GET Requests.
    
    private static BankAccount getBankAccount(int aid) throws IOException {
       URL url = new URL(BASE_URL + aid);
       InputStream input = url.openStream();
       BankAccount bankAccount = JAXB.unmarshal(new InputStreamReader(input), BankAccount.class);
        
       return bankAccount;
    }
    
     private static Customer getCustomer (int aid,int uid) throws IOException {
        URL url = new URL(BASE_URL + aid + "/customers/" + uid);
        InputStream input = url.openStream();
        Customer customer = JAXB.unmarshal(new InputStreamReader(input), Customer.class);
    
        return customer;
    }
     
     private static BankAccount createBankAccount(BankAccount ba) throws FileNotFoundException, IOException {
     HttpPost httpPost = new HttpPost(BASE_URL + "");
     final File initialFile = new File("C:\\Users\\workl\\OneDrive\\Documenti\\NetBeansProjects\\MyRestClient\\src\\main\\java\\com\\maggio\\myrestclient\\cr_bankaccount.xml");
        final InputStream targetStream = new FileInputStream(initialFile);
        httpPost.setEntity(new InputStreamEntity(targetStream));
        httpPost.setHeader("Content-Type", "text/xml");
        HttpResponse response = client.execute(httpPost);
        return ba;
     }
    
    //POST Requests.
    
    //U(pdate) Bank Account.
    private static BankAccount updateBankAccount(int aid) throws IOException {
        BankAccount ba = getBankAccount(aid);
        
        HttpPut httpPut = new HttpPut(BASE_URL + aid);
        
       
        final File initialFile = new File("C:\\Users\\workl\\OneDrive\\Documenti\\NetBeansProjects\\MyRestClient\\src\\main\\java\\com\\maggio\\myrestclient\\up_bankaccount.xml");
        final InputStream targetStream = new FileInputStream(initialFile);
        httpPut.setEntity(new InputStreamEntity(targetStream));
        httpPut.setHeader("Content-Type", "text/xml");
        HttpResponse response = client.execute(httpPut);
        
        return ba;
    }
    
    public static void main(String[]args) throws IOException {
        createClient();
        System.out.println("Start Client..");
                
            System.out.println("GET BankAccount aid=2 :");
            BankAccount bankAccount = getBankAccount(2);
            
            System.out.println(bankAccount);
            System.out.println();
            
            System.out.println("GET Customer aid=2 uid=2  :");
            Customer customer = getCustomer(2,2);
            
            System.out.println(customer);
            System.out.println();
            
            System.out.println("UPDATE BankAccount aid=2 :");
            BankAccount ba = updateBankAccount(2);
            
            System.out.println(ba);
            System.out.println();
            
            System.out.println("CREATE BankAccount aid=4 :");
            BankAccount b = new BankAccount();
            b.setId(5);
            b.setAmount(8123);
            
            
            BankAccount ba1 = createBankAccount(b);
            
            System.out.println(ba1);
            System.out.println();
            
        closeClient();
    }
    
}
