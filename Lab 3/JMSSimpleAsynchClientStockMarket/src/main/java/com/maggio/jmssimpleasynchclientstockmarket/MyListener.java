/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.jmssimpleasynchclientstockmarket;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author workl
 */
public class MyListener implements MessageListener {

    @Override
    public void onMessage(Message msg) {
       System.out.println("::Received message!");
        try {
            System.out.println(msg.getStringProperty("Nome")+" "+msg.getFloatProperty("Valore"));
        } catch (JMSException ex) {
            Logger.getLogger(MyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
                    System.out.println("\n\n"); 
    }
    
}
