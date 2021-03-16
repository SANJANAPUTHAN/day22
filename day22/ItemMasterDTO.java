package day22;

public class ItemMasterDTO {
	private int itemno;
	private String itemname;
	private float itemprice;
	private String itemunit;
	public int getItemno() {
		return itemno;
	}
	public void setItemno(int itemno) {
		this.itemno = itemno;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public float getItemprice() {
		return itemprice;
	}
	public void setItemprice(float itemprice) {
		this.itemprice = itemprice;
	}
	public String getItemunit() {
		return itemunit;
	}
	public void setItemunit(String itemunit) {
		this.itemunit = itemunit;
	}
	@Override
	public String toString() {
		return itemno+" "+itemname+" "+itemprice+" "+itemunit;
	}
	
	
}
