/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.jmssubscribertopicasyncrhonousstockmarket.listener;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author 1456606@maggio
 */

public class MyListener implements MessageListener{
private static final String nomeQuotazione = "Barilla";
private static final String nomeQuotazione2 = "Vodafone";

    @Override
    public void onMessage(Message msg) {
        System.out.println("::Received Message!");
        
        
        
        try {
            String quotazione = msg.getStringProperty("Nome");
            float value = msg.getFloatProperty("Valore");
            
            if(quotazione.equals(nomeQuotazione) || quotazione.equals(nomeQuotazione2)) {
                System.out.println(quotazione+" "+value);
                JOptionPane.showMessageDialog(null, quotazione+" "+value,"Aggiornamento " + new Date(), JOptionPane.INFORMATION_MESSAGE);
       
            }
           
            
        } catch (JMSException ex) {
            Logger.getLogger(MyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
                    System.out.println("\n\n"); 
    }
    
}
