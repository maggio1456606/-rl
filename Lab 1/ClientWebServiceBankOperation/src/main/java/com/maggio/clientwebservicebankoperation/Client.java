/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maggio.clientwebservicebankoperation;

import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author workl
 */
public class Client {
    
    private static java.util.List<java.lang.String> getClients() {
        com.maggio.aaawsserver.AaawsImplService service =  new com.maggio.aaawsserver.AaawsImplService();
        com.maggio.aaawsserver.AaawsInterface port = service.getAaawsImplPort();
        return port.getClients();
}
    
    private static java.util.List<java.lang.String> getOperationByClientID(int id){
        com.maggio.banksoapserver.BankImplService service = new com.maggio.banksoapserver.BankImplService();
        com.maggio.banksoapserver.BankInterface port = service.getBankImplPort();
        return port.getOperationsByClientID(id);
    }
    
      private static java.util.List<java.lang.String> getOperationDetailsByID(int id){
        com.maggio.banksoapserver.BankImplService service = new com.maggio.banksoapserver.BankImplService();
        com.maggio.banksoapserver.BankInterface port = service.getBankImplPort();
        return port.getOperationDetailsByID(id);
    }
    
    
    public static void main (String[]args) throws ParseException {
       
       java.util.List<java.lang.String> clients = getClients();
       
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
       
       System.out.println("***List of Clients***");
       
       for(String client : clients) {
           System.out.println(client);
       }
       
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
       
       java.util.List<java.lang.String> operations = getOperationByClientID(1);
       
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
       
       System.out.println("***List of Operations by ClientID:1***");
       
       for(String op : operations) {
           System.out.println(op);
       }
       
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
       
       java.util.List<java.lang.String> detailsOp = getOperationDetailsByID(2);
       
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
       
       System.out.println("***Detail by Op:2***");
       
       for(String det : detailsOp) {
           System.out.println(det);
       }
       
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
       
       final int PERIOD = 7;
              
       System.out.println("***Last Period Operations***");
       
       for(String client : clients) {
          int idcust = Integer.parseInt(client.split(",")[0]);
          String name = client.split(",")[1];
          
          operations = getOperationByClientID(idcust);
          for(String op : operations) {
              int idop = Integer.parseInt(op);
              
              
              detailsOp = getOperationDetailsByID(idop);
              if(detailsOp!=null) {
                  String desc = detailsOp.get(0).split(",")[4];
                  String date = detailsOp.get(0).split(",")[2];
                    Date datet = Calendar.getInstance().getTime();
                       
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String today = formatter.format(datet);
                    
                    GregorianCalendar newDate = new GregorianCalendar();
                    newDate.add(Calendar.DAY_OF_MONTH, -7);
                    String range = formatter.format(newDate.getTime());
                    
                    System.out.println("Today : " + today);
                    System.out.println("Range date : " + range);
                    
                    Date dquery = formatter.parse(date);
                    
                    System.out.println(range + " - " + date);
                    
                    if(newDate.getTime().after(dquery)) {
                        System.out.println("Prima");
                    }
                    else {
                        System.out.println("Dopo");
                    }
              }
          }
       }
       
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
        
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
       
       System.out.println("*** Names of all clients who have performed an operation in the last days with description 'Benzina Autostrada'***");
       
       ArrayList<String> list = new ArrayList();
       
       for(String client : clients) {
          int idcust = Integer.parseInt(client.split(",")[0]);
          String name = client.split(",")[1];
          
          operations = getOperationByClientID(idcust);
          for(String op : operations) {
              int idop = Integer.parseInt(op);
              
              
              detailsOp = getOperationDetailsByID(idop);
              if(detailsOp!=null) {
                  String desc = detailsOp.get(0).split(",")[4]; 
                  String date = detailsOp.get(0).split(",")[2];
                  
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    GregorianCalendar newDate = new GregorianCalendar();
                    newDate.add(Calendar.DAY_OF_MONTH, -7);
                    Date dquery = formatter.parse(date); 
                    
                  if(desc.equals("Benzina Autostrada") && !(newDate.getTime().after(dquery)) )
                  System.out.println(name + ", " + desc);
                  list.add(name+", "+desc);
              }
          }
       }
       
       System.out.println("******");
       System.out.println("******");
       System.out.println("******");
       
       System.out.println("");
       
    //create a jframe  
    JFrame frame = new JFrame("Clients with description 'Benzina Autostrada'");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(new ListClients(list.toArray(new String[list.size()])));
    frame.pack();
    frame.setVisible(true);
    }
}

class ListClients extends JPanel {

    public ListClients(String[] label) {
        this.setLayout(new BorderLayout());

        JList list = new JList(label);
        JScrollPane pane = new JScrollPane(list);

    DefaultListSelectionModel m = new DefaultListSelectionModel();
    m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    m.setLeadAnchorNotificationEnabled(false);
    list.setSelectionModel(m);

    add(pane, BorderLayout.CENTER);
    }
}
