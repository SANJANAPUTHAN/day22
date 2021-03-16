package day22;

import java.rmi.Remote;

public interface Invoice extends Remote{
	public void initapp(int userchoice) throws Exception; 
}
