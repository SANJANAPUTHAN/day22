package day22;

import java.util.Set;

public interface CustomerMasterDAO {
	public int insertCustomer(CustomerMasterDTO customerMasterDTO);
	public int deleteCustomer(int customerno);
	public int updateCustomer(CustomerMasterDTO customerMasterDTO);
	public CustomerMasterDTO getCustomerMaster(int customerno);
	public Set<CustomerMasterDTO> getCustomerMasterAll();
}
