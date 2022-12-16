package fhv.ws22.se.skyward.view.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.view.AbstractController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class InvoicePdfController extends AbstractController {
   public static void createInvoice(BookingDto booking, InvoiceDto invoice, String path) {
       path = path + "/invoice.pdf";
       Document document = new Document();
       try {
           PdfWriter.getInstance(document, new FileOutputStream(path));
       } catch (DocumentException | FileNotFoundException e) {
           e.printStackTrace();
       }
       document.setPageSize(PageSize.A4);
       document.open();


       Font fontSize_13 =  FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13f);
       Font fontSize_25 =  FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25f);


       float twcol = 150f;
       float twcol150 = twcol+350f;
       float twcolWidth[] = {twcol150, twcol} ;


       PdfPTable table = new PdfPTable(twcolWidth);
       PdfPCell cell1 = new PdfPCell(new Paragraph("\nInvoice", fontSize_25));
       cell1.setBorder(Rectangle.NO_BORDER);
       table.addCell(cell1);


       PdfPTable table1 = new PdfPTable(twcolWidth);
       table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
       table1.addCell("Invoice Number : " + invoice.getInvoiceNumber() + "\n" + "Reservation Number : " + booking.getBookingNumber());
       table1.addCell(invoice.getCompanyName() + "\n" + invoice.getHotelAddress().getStreet() + "\n" + invoice.getHotelAddress().getHouseNumber() + invoice.getHotelAddress().getZipCode() + " " + invoice.getHotelAddress().getCity() + "\n" + invoice.getHotelAddress().getCountry());


       PdfPTable table2 = new PdfPTable(1);
       table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
       table2.addCell("\n" + invoice.getCustomerAddress().getStreet() + " " + invoice.getCustomerAddress().getHouseNumber()
               + "\n" + invoice.getCustomerAddress().getZipCode() + " " + invoice.getCustomerAddress().getCity() + "\n" + invoice.getCustomerAddress().getCountry() + "\n ");


       PdfPTable table3 = new PdfPTable(1);
       table3.addCell("\n             Check-in :                                                               Check-out" + "\n             " + booking.getCheckInDateTime().toLocalDate().toString() + "                                                             " + booking.getCheckOutDateTime().toLocalDate().toString() + "\n ");


       PdfPTable table4 = new PdfPTable(3);
       table4.getDefaultCell().setBorder(Rectangle.NO_BORDER);
       table4.addCell("Invoice Date: " + "\n" + invoice.getInvoiceDateTime().toLocalDate().toString());
       table4.addCell("Payment type :" + "\nPayment" );
       if(invoice.getIsPaid().toString() == "false") {
           table4.addCell("Payed? : " + "\n No");

       }else {
           table4.addCell("Payed? : " + "\n Yes");
       }


       try {
           Path imgPath = Paths.get(ClassLoader.getSystemResource("SkyWardIcon.png").toURI());
           Image img = Image.getInstance(imgPath.toAbsolutePath().toString());
           img.scalePercent(11);
           PdfPCell imageCell = new PdfPCell(img);
           imageCell.setBorder(Rectangle.NO_BORDER);
           table.addCell(imageCell);

           document.add(table);


           document.add(new Paragraph("               Name : " + invoice.getBilledCustomer().getFirstName() + " " + invoice.getBilledCustomer().getLastName() + "\n  ", fontSize_13 ));
           document.add(table1);
           document.add(table2);
           document.add(table3);
           document.add(table4);

      } catch (DocumentException e) {
           e.printStackTrace();}
       catch (URISyntaxException e) {
           throw new RuntimeException(e);
       } catch (MalformedURLException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       document.close();


       // open the pdf file
         try {
              Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path);
         } catch (IOException e) {
              e.printStackTrace();
         }
   }
}