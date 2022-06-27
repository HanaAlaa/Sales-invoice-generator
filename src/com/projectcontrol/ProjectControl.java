
package com.projectcontrol;

import com.projectmodel.Invoice;
import com.projectmodel.Line;
import com.projectmodel.TableModelForInvoices;
import com.projectmodel.TableModelForLines;
import com.viewsales.LineDialog;
import com.viewsales.ProjectSalesInvoiceFrame;
import com.viewsales.SalesInvoiceDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProjectControl implements ActionListener, ListSelectionListener{
    
    private ProjectSalesInvoiceFrame frame;
    private SalesInvoiceDialog salesInvoiceDialog;
    private LineDialog lineDialog;
    
    public  ProjectControl(ProjectSalesInvoiceFrame frame){
    this.frame = frame;    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand =  e.getActionCommand();
        System.out.println("Action is to " + actionCommand);
        switch(actionCommand) {
            case "Create New Invoice":
                createNewInvoice();
            case "Delete Current Invoice":
                deleteCurrentInvoice();
                break; 
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Current Item":
                deleteCurrentItem();
                break;
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case  "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
        
    }
      @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getSalesInvoiceTable().getSelectedRow();
        if (selectedIndex!=-1){
           
        /**System.out.println("Selection: "+ frame.getSalesInvoiceTable().getSelectedRow());**/
        Invoice current = frame.getInvoices().get(selectedIndex);
        frame.getInvoiceNoLabel().setText(" "+current.getNumber()); //to trick the program into thinking its a string without having to convert
        frame.getInvoiceDateLabel().setText(current.getDate());
        frame.getClientNameLabel().setText(current.getClient());
        frame.getInvoiceTotalLabel().setText(" "+current.getTotal());
        //now we need to show data on the second table
        TableModelForLines tableModelForLines = new TableModelForLines(current.getLines());
        frame.getLineTable().setModel(tableModelForLines);
        tableModelForLines.fireTableDataChanged();
        }
    }

    private void createNewInvoice() {
        salesInvoiceDialog = new SalesInvoiceDialog(frame);
        salesInvoiceDialog.setVisible(true);
        
    }

    private void deleteCurrentInvoice() {
       int selectedRow = frame.getSalesInvoiceTable().getSelectedRow();
       if (selectedRow!=-1){
           frame.getInvoices().remove(selectedRow);
           frame.getTableForModelInvoices().fireTableDataChanged();
       }
    }

    private void createNewItem() {
        lineDialog = new LineDialog(frame);
        lineDialog.setVisible(true);
        
    }

    private void deleteCurrentItem() {
        int selectedRow = frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            TableModelForLines tableModelForLines = (TableModelForLines) frame.getLineTable().getModel();
            tableModelForLines.getLines().remove(selectedRow);
            tableModelForLines.fireTableDataChanged();
            frame.getTableForModelInvoices().fireTableDataChanged();;
        }
       }

    private void loadFile() {
        JFileChooser fc= new JFileChooser();
        try {
            //make dialog aware that the parent is its frame
        int result= fc.showOpenDialog(frame);
        if(result == JFileChooser.APPROVE_OPTION) {
            File headerFile = fc.getSelectedFile();
            Path headerPath = Paths.get(headerFile.getAbsolutePath());
            List<String> headerLines = Files.readAllLines(headerPath);
            System.out.println("Invoices read");
            ArrayList <Invoice> invoicesArray  = new ArrayList<>();
            //loop to read each line
            for(String headerLine:headerLines)
            {
                String [] partsOfHeaderLines = headerLine.split(","); //split whenever we encounter a comma
                int invoiceID= Integer.parseInt(partsOfHeaderLines[0]);
                String invoiceDate= partsOfHeaderLines[1];
                String clientName= partsOfHeaderLines[2];
                Invoice invoice= new Invoice(invoiceID, invoiceDate, clientName);
                invoicesArray.add(invoice);
            }
                System.out.println("checkpoint");
                //make dialog aware that the parent is its frame
                result = fc.showOpenDialog(frame);
            if (result==JFileChooser.APPROVE_OPTION)
            {
                File lineFile = fc.getSelectedFile();
                Path linePath = Paths.get(lineFile.getAbsolutePath());
                List<String>lineLines= Files.readAllLines(linePath);
                System.out.println("Lines read");
                for(String lineLine : lineLines)
                           {
                               String [] partsOfLineLines = lineLine.split(","); //split whenever we encounter a comma
                               int invoiceID= Integer.parseInt(partsOfLineLines[0]);
                               String objectName= partsOfLineLines[1];
                               double objectPrice= Double.parseDouble(partsOfLineLines[2]);
                               int numberOfItems = Integer.parseInt(partsOfLineLines[3]);
                               Invoice numOfInv = null;
                               for(Invoice invoice : invoicesArray){
                                   if(invoice.getNumber()==invoiceID){
                                       numOfInv=invoice;
                                       break;
                                   }
                               }
                               Line line = new Line(objectName, objectPrice, numberOfItems, numOfInv);
                               numOfInv.getLines().add(line);
            }
            }
            frame.setInvoices(invoicesArray);
            TableModelForInvoices tableModelForInvoices= new TableModelForInvoices(invoicesArray);
            frame.setTableForModelInvoices(tableModelForInvoices);
            frame.getSalesInvoiceTable().setModel(tableModelForInvoices);
            frame.getTableForModelInvoices().fireTableDataChanged();
        }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void saveFile() {
        ArrayList<Invoice> invoices = frame.getInvoices();
    }

    private void createInvoiceOK() {
        String Date= salesInvoiceDialog.getInvDateField().getText();
        String Name = salesInvoiceDialog.getCustNameField().getText();
        int ID= frame.getNextID();
        Invoice invoice= new Invoice(ID, Date, Name);
        frame.getInvoices().add(invoice);
        frame.getTableForModelInvoices().fireTableDataChanged();
        salesInvoiceDialog.setVisible(false);
        salesInvoiceDialog.dispose();
        salesInvoiceDialog=null;
    }

    private void createInvoiceCancel() {
        salesInvoiceDialog.setVisible(false);
        salesInvoiceDialog.dispose();
        salesInvoiceDialog= null;   //so it doesnt take memory space
        
    }

    private void createLineOK() {
        String itemName = lineDialog.getItemNameField().getText();
        String countX = lineDialog.getItemCountField().getText();
        String itemPrice = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countX);
        double price = Double.parseDouble(itemPrice);
        int selectedInvoice = frame.getSalesInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            Invoice invoice = frame.getInvoices().get(selectedInvoice);
            Line line = new Line(itemName, price, count, invoice);
            invoice.getLines().add(line);
            TableModelForLines tableModelForLines = (TableModelForLines) frame.getLineTable().getModel();
            tableModelForLines.fireTableDataChanged();
            frame.getTableForModelInvoices().fireTableDataChanged();
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
        
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog=null;
    }
    
}
