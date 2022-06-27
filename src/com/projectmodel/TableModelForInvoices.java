
package com.projectmodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableModelForInvoices extends AbstractTableModel{
    private ArrayList <Invoice> invoices;
    private String[] cols = {"NUM", "Date", "Client", "Total"};
    public TableModelForInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }
    @Override
    public String getColumnName(int col){
        return cols[col];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice invoice = invoices.get (rowIndex)  ;
        switch (columnIndex){
            
            case 0:
                return invoice.getNumber();
            case 1:
                return invoice.getDate();
            case 2:
                return invoice.getClient();
            case 3:
                return invoice.getTotal();
            default: 
                return " ";
        }
                      
                }

}
