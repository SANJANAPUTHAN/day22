package day22;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

interface CreateInvoice
{
	public void createInvoice();
public class CreationUtility implements CreateInvoice {
	public void createInvoice() 
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

}
