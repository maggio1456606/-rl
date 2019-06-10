/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.jmssubscribertopicasyncrhonousstockmarket.client;

import com.maggio.jmssubscribertopicasyncrhonousstockmarket.listener.MyListener;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author 1456606@maggio
 */

public class SubscriberAsynchroClient {
        public static void main(String[]args) throws NamingException, JMSException {
        Context context;
        
        String destinationName = "dynamicTopics/Quotazioni";
        
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
        
        context = new InitialContext(props);
        
        TopicConnectionFactory tcf = (TopicConnectionFactory) context.lookup("ConnectionFactory");
        Topic topic = (Topic) context.lookup(destinationName);
        
        TopicConnection tc = tcf.createTopicConnection();
                
        TopicSession ts = tc.createTopicSession
                                                        (
                                                            true, 
                                                            Session.AUTO_ACKNOWLEDGE
                                                        );
        
        TopicSubscriber subscriber = ts.createSubscriber(topic);
        
        subscriber.setMessageListener(new MyListener());
        tc.start();
    }
}
