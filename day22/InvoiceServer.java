package day22;

import java.io.File;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class InvoiceServer extends UnicastRemoteObject implements Invoice{

	public InvoiceServer() throws RemoteException {
		
	}

	@Override
	public void initapp(int userchoice) throws Exception {
		
		switch(userchoice)
		{
		case 1:
			createInvoice();
			break;
		case 2:
			System.out.println(getShipmentDetails());
			break;
		case 3:
			convertToExcel();
			break;
		case 4:
			convertToPDF();
			break;
		default:
			System.out.println("Enter proper choice...");
			break;
		}
	}

	private void convertToPDF() throws Exception {
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
	private void convertToExcel()
	{
		XSSFWorkbook workbook=new XSSFWorkbook();		
		XSSFSheet sheet=workbook.createSheet("InvoiceBill");
		int rownum=0;
		Row row=sheet.createRow(rownum);
		System.out.println("Enter invoice number");
		Scanner sc=new Scanner(System.in);
		int invno=sc.nextInt();
		InvoiceMasterDTO invoice=new InvoiceMasterDTO();
		InvoiceMasterImpl impl=new InvoiceMasterImpl();
		invoice = impl.getInvoiceMaster(invno);
		String inv[]=invoice.toString().split(" ");
		LocalDate date=LocalDate.parse(inv[1]);
		int customerno=Integer.parseInt(inv[2]);
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		XSSFFont font = sheet.getWorkbook().createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short) 8);
	    cellStyle.setFont(font);	 
	    Cell cellTitle = row.createCell(rownum);	 
	    cellTitle.setCellStyle(cellStyle);
	    cellTitle.setCellValue("INVOICENUMBER");	 
	    Cell cellAuthor = row.createCell(1);
	    cellAuthor.setCellStyle(cellStyle);
	    cellAuthor.setCellValue("INVOICEDATE");	
	    Cell cellCustomer = row.createCell(2);
	    cellCustomer.setCellStyle(cellStyle);
	    cellCustomer.setCellValue("CUSTOMERNUMBER");	
	    rownum++;
	    row = sheet.createRow(rownum);
		Cell cell = row.createCell(0);
	    cell.setCellValue(invoice.getInvno());	 
	    cell = row.createCell(1);
	    cell.setCellValue(invoice.getInvdate());
	    cell = row.createCell(2);
	    cell.setCellValue(invoice.getCustomerno());
	    cellStyle.setFont(font);	
	    rownum++;
	    row = sheet.createRow(rownum);
	    cellTitle = row.createCell(0);	 
	    cellTitle.setCellStyle(cellStyle);
	    cellTitle.setCellValue("ITEMNUMBER");	 
	    cellAuthor = row.createCell(1);
	    cellAuthor.setCellStyle(cellStyle);
	    cellAuthor.setCellValue("ITEMNAME");	 
	    Cell cellPrice = row.createCell(2);
	    cellPrice.setCellStyle(cellStyle);
	    cellPrice.setCellValue("ITEMPRICE");	    
	    Cell cellquantity = row.createCell(3);
	    cellquantity.setCellStyle(cellStyle);
	    cellquantity.setCellValue("ITEMUNIT");
	    rownum++;
	    
		ItemMasterImpl imp=new ItemMasterImpl();
		Set<ItemMasterDTO> set=imp.getItemMasterAll();
		Iterator<ItemMasterDTO> iterator=set.iterator();
		while(iterator.hasNext())
		{
			String str[]=iterator.next().toString().split(" ");
			row=sheet.createRow(rownum);
			Cell itemno= row.createCell(0);
			itemno.setCellValue(str[0]);
			Cell itemname=row.createCell(1);
			itemname.setCellValue(str[1]);
			Cell itemprice=row.createCell(2);
			itemprice.setCellValue(str[2]);
			Cell itemunit=row.createCell(3);
			itemunit.setCellValue(str[3]);
			rownum++;
		}
		try (FileOutputStream outputStream = new FileOutputStream("InvoiceBill.xlsx")) 
		{
            workbook.write(outputStream);
        } 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private String getShipmentDetails()
	{
		System.out.println("Enter invoice number");
		Scanner sc=new Scanner(System.in);
		int invno=sc.nextInt();
		InvoiceMasterDTO invoice=new InvoiceMasterDTO();
		InvoiceMasterImpl impl=new InvoiceMasterImpl();
		invoice = impl.getInvoiceMaster(invno);
		String inv[]=invoice.toString().split(" ");
		LocalDate date=LocalDate.parse(inv[1]);
		int customerno=Integer.parseInt(inv[2]);		
		System.out.println("Enter arrival time: ");
		LocalTime arrivaltime=LocalTime.parse(sc.next());
		System.out.println("Enter distance in kilometers: ");
		int distance=sc.nextInt();
		System.out.println("Enter speed in hours: ");
		int travelspeed=sc.nextInt();
		System.out.println("Enter breaktime in intervals of hours: ");
		int travelbreak=sc.nextInt();
		LocalTime t;
		int day;
		if(checkSpecialconstraints(date))
		{
			date=setspecialconstraints(date);
			arrivaltime=setConstraintTime(arrivaltime);
		}
		else if((checkconstraint(date))!=0)
		{
			day=checkconstraint(date);
			date=setconstraintDate(date, day);
			arrivaltime=setConstraintTime(arrivaltime);
			
		}
		float time=getTime(distance,travelspeed);
		if(travelbreak!=0)
		{
			time=getbreak(time,travelbreak);
		}
		float minutes=time%1;
		minutes=(float)(time-Math.floor(time));
		minutes=minutes*100;
		ArrayList<String> datetime=getdatetime(arrivaltime,time,date);
		LocalTime nextTime=LocalTime.parse(datetime.get(1));
		nextTime=nextTime.plus((long) minutes,ChronoUnit.MINUTES);
		LocalDate d=LocalDate.parse(datetime.get(0));
		return "The parcel will reach at "+nextTime+"(approximately)  "+d+" "+d.getDayOfWeek();
	}
	private static LocalDate setspecialconstraints(LocalDate start_date) 
	{
		
			int day=1;
			start_date=setconstraintDate(start_date, day);
			if(checkconstraint(start_date)!=0)
			{
				day=checkconstraint(start_date);
				start_date=setconstraintDate(start_date, day);				
			}
			
		
		return start_date;
	}
	private static boolean checkSpecialconstraints(LocalDate start_date)
	{
		if(start_date.isEqual(LocalDate.of(start_date.getYear(), 1, 1))||start_date.isEqual(LocalDate.of(start_date.getYear(), 8, 15))||
				start_date.isEqual(LocalDate.of(start_date.getYear(), 10, 2))||start_date.isEqual(LocalDate.of(start_date.getYear(), 1, 26)))
		{
			return true;
		}
		return false;
	}
	private static LocalTime setConstraintTime(LocalTime time)
	{
		return LocalTime.parse("06:00:00");
	}
	private static int checkconstraint(LocalDate start_date) {
		if(start_date.getDayOfWeek()==DayOfWeek.SUNDAY)
		{
			return 1;
		}
		else if(start_date.getDayOfWeek()==DayOfWeek.SATURDAY)
		{
			return 2;
		}
		return 0;
	}

	private static LocalDate setconstraintDate(LocalDate date,int day)
	{
		date=date.plus(day,ChronoUnit.DAYS);
		return date;
		
	}
	private static ArrayList<String> getdatetime(LocalTime start_time, float time, LocalDate start_date) {

		int hours=start_time.getHour();
		ArrayList<String> datetime=new ArrayList<String>();
		if(hours<12 && time<24)
		{
			start_time=getTime(start_time,time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		else if((hours<12 || hours>12) && time>24)
		{
			while(time>24)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				if(checkSpecialconstraints(start_date))
				{
					start_date=setspecialconstraints(start_date);
					start_time=setConstraintTime(start_time);
				}
				else if((checkconstraint(start_date))!=0)
				{
					int day=checkconstraint(start_date);
					start_date=setconstraintDate(start_date, day);
					time=time+(start_time.getHour()-6);
					start_time=setConstraintTime(start_time);
					if(time<24)
					{
						break;
					}
					
				}
				start_time=start_time.plus(24,ChronoUnit.HOURS);
				time=time-24;
			}
			if(24-hours < time)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				
			}
			start_time=getTime(start_time, time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		else if(hours>12 && time<24)
		{
			if(24-hours < time)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				if(checkSpecialconstraints(start_date))
				{
					start_date=setspecialconstraints(start_date);
					start_time=setConstraintTime(start_time);
				}
				else if((checkconstraint(start_date))!=0)
				{
					int day=checkconstraint(start_date);
					time=time-(24-hours);
					start_date=setconstraintDate(start_date, day);
					start_time=setConstraintTime(start_time);
					
					
				}
				
			}
			start_time=getTime(start_time, time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		
		return datetime;
	}

	private static LocalTime getTime(LocalTime start_time,float time) {
		start_time=start_time.plus((long)time,ChronoUnit.HOURS);
		return start_time;
	}
	private static float getbreak(float time, int breaktime) {
		if(breaktime<time)
		{
			int cal=(int)time/breaktime;
			time+=cal;
		}
		return time;
	}
	private static float getTime(int distance, int travelspeed)
	{
		return (float)distance/(float)travelspeed;
	}

	private void createInvoice() 
	{
            
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter invoice number");
		int invno=sc.nextInt();
		System.out.println("Enter invoice date");
		String date=sc.next();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate invdate=LocalDate.parse(date,formatter);
		System.out.println("Enter itemnumber");
		int itemnumber=sc.nextInt();
		System.out.println("Enter itemname");
		String itemname=sc.next();
		System.out.println("Enter itemprice");
		float itemprice=sc.nextFloat();
		System.out.println("Enter itemunit");
		String itemunit=sc.next();
		System.out.println("Enter itemquantity");
		int itemquantity=sc.nextInt();
		System.out.println("Enter customer number");
		int customerno=sc.nextInt();
		System.out.println("Enter customer name");
		String customername=sc.next();
		System.out.println("Enter customer address");
		String customeraddress=sc.next();
		System.out.println("Enter customer email");
		String customeremail=sc.next();
		System.out.println("Enter customer phone");
		String customerphone=sc.next();
		
		InvoiceMasterImpl invoiceobj=new InvoiceMasterImpl();
		InvoiceMasterDTO invoice=new InvoiceMasterDTO();
		invoice.setInvno(invno);
		invoice.setInvdate(invdate);
		invoice.setCustomerno(customerno);
		invoiceobj.insertInvoice(invoice);
		
		BillMasterDTO bill=new BillMasterDTO();
		BillMasterImpl billobj=new BillMasterImpl();
		bill.setInvno(invno);
		bill.setItemno(itemnumber);
		bill.setItemquantity(itemquantity);
		billobj.insertBill(bill);
		
		ItemMasterDTO item=new ItemMasterDTO();
		ItemMasterImpl itemobj=new ItemMasterImpl();
		item.setItemname(itemname);
		item.setItemno(itemnumber);
		item.setItemprice(itemprice);
		item.setItemunit(itemunit);
		itemobj.insertItem(item);
		
		CustomerMasterDTO customer=new CustomerMasterDTO();
		CustomerMasterImpl customerobj = new CustomerMasterImpl();
		customer.setCustomerAddress(customeraddress);
		customer.setCustomerEmail(customeremail);
		customer.setCustomername(customername);
		customer.setCustomerno(customerno);
		customer.setCustomerphone(customerphone);
		customerobj.insertCustomer(customer);
		
	}
	

}
