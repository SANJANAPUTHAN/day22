package day22;

import java.util.Set;

public interface BillMasterDAO {
	public int insertBill(BillMasterDTO billMasterDTO);
	public int deleteBill(int itemno);
	public int updateBill(BillMasterDTO billMasterDTO);
	public BillMasterDTO getBillMaster(int itemno);
	public Set<BillMasterDTO> getBillMasterAll();
}
