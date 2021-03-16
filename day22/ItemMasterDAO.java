package day22;

import java.util.Set;

public interface ItemMasterDAO {
	public int insertItem(ItemMasterDTO itemMasterDTO);
	public int deleteItem(int itemno);
	public int updateItem(ItemMasterDTO itemMasterDTO);
	public ItemMasterDTO getItemMaster(int itemno);
	public Set<ItemMasterDTO> getItemMasterAll();
}
