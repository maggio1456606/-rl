/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.jmssimpleasynchclientstockmarket;

import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author 1456606@maggio
 */

public class AsynchroClient {
    public static void main(String[]args) throws NamingException, JMSException {
        Context jndiContext = null;
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        String destinationName = "dynamicTopics/Quotazioni";
        
        /* * Create a JNDI API InitialContext object */
        
        Properties props = new Properties();
        props.setProperty
                            (
                                Context.INITIAL_CONTEXT_FACTORY,
                                "org.apache.activemq.jndi.ActiveMQInitialContextFactory"    
                            );
        props.setProperty
                            (
                                Context.PROVIDER_URL,
                                "tcp://localhost:61616"
                            );
        
        jndiContext = new InitialContext(props);
        
        connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
        
        destination = (Destination) jndiContext.lookup(destinationName);
        
        connection = connectionFactory.createConnection();
        
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        //asyncrhro-client
        MessageConsumer messageConsumer = session.createConsumer(destination);
        MessageListener listener = new MyListener();
        messageConsumer . setMessageListener(listener);
        
        connection.start();
    }
}