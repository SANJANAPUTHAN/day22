package day22;

public class BillMasterDTO {
	private int invno;
	private int itemno;
	private int itemquantity;
	public int getInvno() {
		return invno;
	}
	public void setInvno(int invno) {
		this.invno = invno;
	}
	public int getItemno() {
		return itemno;
	}
	public void setItemno(int itemno) {
		this.itemno = itemno;
	}
	public int getItemquantity() {
		return itemquantity;
	}
	public void setItemquantity(int itemquantity) {
		this.itemquantity = itemquantity;
	}
	@Override
	public String toString() {
		return "BillMasterDTO [invno=" + invno + ", itemno=" + itemno + ", itemquantity=" + itemquantity + "]";
	}
	
}
