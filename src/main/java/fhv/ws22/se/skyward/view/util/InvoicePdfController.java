package fhv.ws22.se.skyward.view.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.ChargeableItemDto;
import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.view.AbstractController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class InvoicePdfController extends AbstractController {
   public static void createInvoice(BookingDto booking, InvoiceDto invoice, List<ChargeableItemDto> chargeableItems, String path) {
       String date = invoice.getInvoiceDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
       path = path + "/" + date + "_invoice_" + invoice.getBilledCustomer().getLastName() + "_" + invoice.getBilledCustomer().getFirstName() + ".pdf";
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
       if (invoice.getIsPaid().toString() == "false") {
           table4.addCell("Paid? : " + "\n No");

       } else {
           table4.addCell("Paid? : " + "\n Yes");
       }

       PdfPTable chargeableItemTable = new PdfPTable(3);
       chargeableItemTable.addCell(new Paragraph("Items", fontSize_13));
       chargeableItemTable.addCell(new Paragraph("Price", fontSize_13));
       chargeableItemTable.addCell(new Paragraph("Nights/Quantity", fontSize_13));
       for (ChargeableItemDto chargeableItem : chargeableItems) {
           chargeableItemTable.addCell(chargeableItem.getName());
           chargeableItemTable.addCell(chargeableItem.getPrice() + " €");
           chargeableItemTable.addCell(chargeableItem.getQuantity().toString());
       }

       PdfPTable table5 = new PdfPTable(1);
       table5.getDefaultCell().setBorder(Rectangle.NO_BORDER);
       BigDecimal totalPrice = new BigDecimal(0);
       for (ChargeableItemDto chargeableItem : chargeableItems) {
           totalPrice = totalPrice.add(chargeableItem.getPrice().multiply(BigDecimal.valueOf(chargeableItem.getQuantity())));
       }
       table5.addCell("\n" + "Total Price : " + totalPrice + " €" + "\n ");




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
           document.add(new Paragraph("\n"));
           document.add(chargeableItemTable);
           document.add(new Paragraph("\n"));
           document.add(table4);
           document.add(new Paragraph("\n"));
           document.add(table5);

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