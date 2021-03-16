package day22;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

interface PDFconvserion
{
	public void convertToPDF() throws Exception;
}
public class PDFConversionUtility implements PDFconvserion {
	public void convertToPDF() throws Exception {
		String FILE = "InvoiceBill.pdf";
		Document document=new Document();
		PdfWriter.getInstance(document, new FileOutputStream(new File(FILE)));
	    Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	    Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.RED);
	    Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
	    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
	    document.open();
	    Paragraph preface=new Paragraph();
	    preface.add(new Paragraph("CUSTOMER DETAILS",catFont));
	    document.add(preface);
	    preface.clear();
	    System.out.println("Enter invoice number");
		Scanner sc=new Scanner(System.in);
		int invno=sc.nextInt();
		InvoiceMasterDTO invoice=new InvoiceMasterDTO();
		InvoiceMasterImpl impl=new InvoiceMasterImpl();
		invoice = impl.getInvoiceMaster(invno);
		String inv[]=invoice.toString().split(" ");
		LocalDate date=LocalDate.parse(inv[1]);
		int customerno=Integer.parseInt(inv[2]);
	    preface.add(new Paragraph("INVNO  :  "+invoice.getInvno(),redFont));
	    document.add(preface);
	    preface.clear();
	    preface.add(new Paragraph("INVDATE  :  "+invoice.getInvdate(),redFont));
	    document.add(preface);
	    preface.clear();
	    preface.add(new Paragraph("CUSTOMERNAME  :  "+invoice.getCustomerno(),redFont));
	    document.add(preface);
	    preface.clear();
	    preface.add(new Paragraph(" "));
	    document.add(preface);
	    float[] columnWidths = {1.5f, 5f, 2f, 2f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);		
		insertCell(table, "Item No", Element.ALIGN_LEFT, 1, subFont);
		insertCell(table, "Item Name", Element.ALIGN_LEFT, 1, subFont);
		insertCell(table, "ItemPrice", Element.ALIGN_LEFT, 1, subFont);
		insertCell(table, "ItemUnits", Element.ALIGN_LEFT, 1, subFont);
		table.setHeaderRows(1);
		
		ItemMasterImpl imp=new ItemMasterImpl();
		Set<ItemMasterDTO> set=imp.getItemMasterAll();
		Iterator<ItemMasterDTO> iterator=set.iterator();
		while(iterator.hasNext())
		{
			String[] str=iterator.next().toString().split(" ");
			insertCell(table,str[0], Element.ALIGN_RIGHT, 1,smallBold);
		    insertCell(table,str[1], Element.ALIGN_LEFT, 1, smallBold);
		    insertCell(table,str[2], Element.ALIGN_LEFT, 1, smallBold);
		    insertCell(table, str[3], Element.ALIGN_LEFT, 1, smallBold);
		}
		document.add(table);
		preface.clear();
	    document.close();
		
	}
	public void insertCell(PdfPTable table, String text, int align, int colspan, Font font)
	{
		  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		  cell.setHorizontalAlignment(align);
		  cell.setColspan(colspan);
		  if(text.trim().equalsIgnoreCase("")){
		   cell.setMinimumHeight(10f);
		  }
		  table.addCell(cell);		  
	}
}
