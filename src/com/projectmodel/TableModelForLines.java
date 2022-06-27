
package com.projectmodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableModelForLines extends AbstractTableModel{
    private ArrayList<Line> lines;
    private String[] cols = {"NUM", "Item Name", "Item Price", "Count", "Item Total"};

    public TableModelForLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }
    @Override
    public String getColumnName(int column){
        return cols[column];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Line line = lines.get(rowIndex);
        switch(columnIndex){
            case 0:
                return line.getInvoice().getNumber();
            case 1:
                return line.getItem();
            case 2:
                return line.getPrice();
            case 3:
                return line.getNumberOfItems();
            case 4: 
                return line.getLineTot();
            default: 
                return " ";
        }
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
    
}
