/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.jmsserverstockmarket;

/*
* The student is required to write a client Java subscribing to the topic,
* in order to asyncronously retrieve messages and to show 
* (on the consoleoutput, in a panel, etc.) such messages.
*
* Then the student , after acquiring the knowledge on such messages, 
* should modify the client in order to FILTER such messages, e.g., 
* by selectingonly those ones dealing with a certain Nome (e.g., Barilla).
*/

import javax.jms.*;
import javax.naming.*;

import java.util.*;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;


public class NotificatoreAcquisto implements MessageListener {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(NotificatoreAcquisto.class);

    Properties properties = null;
    Context jndiContext = null;
	private TopicConnectionFactory connectionFactory = null;
	private TopicConnection connection = null;
	private TopicSession session = null;
	private Topic destination = null;
	private TopicSubscriber subscriber = null;
	private TopicPublisher publisher = null;

	private Random randomGen = new Random();

	public void start() throws NamingException, JMSException {

        InitialContext ctx = null;

        try {
                properties = new Properties();
                properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
                properties.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
                jndiContext = new InitialContext(properties);
        } catch (NamingException e) {
            LOG.info("ERROR in JNDI: " + e.toString());
            System.exit(1);
        }

		ctx = new InitialContext(properties);
		this.connectionFactory = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
		this.destination = (Topic) ctx.lookup("dynamicTopics/Ordini");

        //here I create the two arrow from and to Ordini
		this.connection = this.connectionFactory.createTopicConnection();
		this.session = this.connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		this.subscriber = this.session.createSubscriber(this.destination, null, true);
		this.publisher = this.session.createPublisher(this.destination);
		this.connection.start();

		Logger.getLogger(this.getClass().getName()).info("In attesa di richieste di acquisto...");

		subscriber.setMessageListener(this);
	}


    @Override public void onMessage(Message mex) {
		TextMessage message;
		String utente = null;
		String nome = null;
		float prezzo;
		int quantita;
		boolean status = randomGen.nextFloat() < 0.5;
		try {
            //I should know that the message is Text type, the interface doesn't tell me it
			message = (TextMessage) mex;
			utente = message.getStringProperty("Utente");
			nome = message.getStringProperty("Nome");
			prezzo = message.getFloatProperty("Prezzo");
			quantita = message.getIntProperty("Quantita");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {
            //I read the message in the previuos part and I respond to it
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			publisher = session.createPublisher(destination);

			message = session.createTextMessage();
			message.setStringProperty("Utente", utente);
			message.setStringProperty("Nome", nome);
			message.setBooleanProperty("Status", status);
			message.setIntProperty("Quantita", quantita);
			message.setFloatProperty("Prezzo", prezzo);

			Logger.getLogger(
					this.getClass().getName()
				).info(
					"************************************************" + "\n" +
					"Notifica richiesta di acquisto" + "\n" +
					"ID utente: " + utente + "\n" +
					"Titolo: " + nome + "\n" +
					"Quantit\u00e0: " + quantita + "\n" +
					"Prezzo: " + prezzo + "\n" +
					"Accettato: " + status + "\n" +
					"************************************************"
				);

			publisher.send(message); //I send the reply
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}