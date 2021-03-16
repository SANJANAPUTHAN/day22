package day22;


import java.rmi.Naming;
import java.util.Scanner;

public class InvoiceClient
{
	public static void main(String[] args) throws Exception
	{
		Invoice invoice=(Invoice)Naming.lookup("rmi://localhost:2098/invoiceapp");
		while(true)
		{
			System.out.println("Enter the choice");
			System.out.println("1. Invoice entry");
			System.out.println("2. Invoice Shipment Details");
			System.out.println("3. Invoice to EXCEL Report");
			System.out.println("4. Invoice to PDF REport");
			Scanner sc=new Scanner(System.in);
			int option=sc.nextInt();
			
			invoice.initapp(option);
		}
		
	}
}
