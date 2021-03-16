package day22;

public class CustomerMasterDTO {
	
	private int customerno;
	private String customername;
	private String customerAddress;
	private String customerEmail;
	private String customerphone;
	public int getCustomerno() {
		return customerno;
	}
	public void setCustomerno(int customerno) {
		this.customerno = customerno;
	}
	
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerphone() {
		return customerphone;
	}
	public void setCustomerphone(String customerphone) {
		this.customerphone = customerphone;
	}
	@Override
	public String toString() {
		return "CustomerMasterDTO [customerno=" + customerno + ", customerAddress=" + customerAddress
				+ ", customerEmail=" + customerEmail + ", customerphone=" + customerphone + "]";
	}
	
	
}
