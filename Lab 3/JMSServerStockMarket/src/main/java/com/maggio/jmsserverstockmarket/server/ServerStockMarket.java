/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.jmsserverstockmarket.server;

import com.maggio.jmsserverstockmarket.NotificatoreAcquisto;
import com.maggio.jmsserverstockmarket.ProduttoreQuotazioni;


public class ServerStockMarket {

    //I create the two object that run on the server side
	public static void main(String args[]) throws Exception {

        NotificatoreAcquisto n = new NotificatoreAcquisto();
        n.start();

        ProduttoreQuotazioni q = new ProduttoreQuotazioni();
        q.start();

	}
}
