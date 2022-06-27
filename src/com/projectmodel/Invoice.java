
package com.projectmodel;

import java.util.ArrayList;


public class Invoice {
    private int number;
    private String date;
    private String client;
    private ArrayList<Line> lines;
    public Invoice() {
    }

    public Invoice(int number, String date, String client) {
        this.number = number;
        this.date = date;
        this.client = client;
    }
    public  double getTotal(){
        //loop to calculate the invoice total
        double tot=0;
        for (Line line : getLines()){
            tot +=line.getLineTot();
        }
        return tot;
    }
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Line> getLines() {
        //lazy loading
             if (lines==null){
            lines= new ArrayList<>();
        }
        return lines;
    }

    @Override
    public String toString() {
        return "Invoice{" + "number=" + number + ", date=" + date + ", client=" + client + ", lines=" + lines + '}';
    }
    
}
