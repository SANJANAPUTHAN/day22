package day22;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import day22.CreateInvoice.CreationUtility;


public class InvoiceServer extends UnicastRemoteObject implements Invoice{

	public InvoiceServer() throws RemoteException {
		
	}

	@Override
	public void initapp(int userchoice) throws Exception {
		
		switch(userchoice)
		{
		case 1:
			CreationUtility create=new CreationUtility();
			create.createInvoice();
			break;
		case 2:
			ShippingUtility shipping=new ShippingUtility();
			System.out.println(shipping.getShipmentDetails());
			break;
		case 3:
			ExcelConversionUtility excel=new ExcelConversionUtility();
			excel.convertToExcel();
			break;
		case 4:
			PDFConversionUtility pdf=new PDFConversionUtility();
			pdf.convertToPDF();
			break;
		default:
			System.out.println("Enter proper choice...");
			break;
		}
	}
}

	
	

	
	