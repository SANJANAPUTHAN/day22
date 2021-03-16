package day22;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

interface ExcelConversion
{
	public void convertToExcel();
}
public class ExcelConversionUtility implements ExcelConversion{
	public void convertToExcel()
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
}
