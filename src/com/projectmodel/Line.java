
package com.projectmodel;

 public class Line {
     private String item;
     private double price;
     private int numberOfItems;
     private Invoice invoice;

     public Line() {
    }


    public Line( String item, double price, int numberOfItems, Invoice invoice) {;
        this.item = item;
        this.price = price;
        this.numberOfItems = numberOfItems;
        this.invoice = invoice;
    }
    public double getLineTot(){
        return price*numberOfItems;
    }
    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Line{" + "number=" + invoice.getNumber() + ", item=" + item + ", price=" + price + ", numberOfItems=" + numberOfItems + ", invoice=" + invoice + '}';
    }

    public Invoice getInvoice() {
        return invoice;
    }
     
    
}
