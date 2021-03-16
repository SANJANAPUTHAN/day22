package day22;

import java.time.LocalDate;

public class InvoiceMasterDTO {
	private int invno;
	private LocalDate invdate;
	private int customerno;
	public int getInvno() {
		return invno;
	}
	public void setInvno(int invno) {
		this.invno = invno;
	}
	public LocalDate getInvdate() {
		return invdate;
	}
	public void setInvdate(LocalDate invdate) {
		this.invdate = invdate;
	}
	public int getCustomerno() {
		return customerno;
	}
	public void setCustomerno(int customerno) {
		this.customerno = customerno;
	}
	@Override
	public String toString() {
		return invno+" "+invdate+" "+customerno;
	}
	
	
}
