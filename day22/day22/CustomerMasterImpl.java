package day22;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class CustomerMasterImpl implements CustomerMasterDAO{

	Connection conn=null;
	PreparedStatement stmt=null;
	@Override
	public int insertCustomer(CustomerMasterDTO customerMasterDTO) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("insert into customertable values(?,?,?,?,?)");
			stmt.setInt(1, customerMasterDTO.getCustomerno());
			stmt.setString(2, customerMasterDTO.getCustomername());
			stmt.setString(3, customerMasterDTO.getCustomerAddress());
			stmt.setString(4, customerMasterDTO.getCustomerEmail());
			stmt.setString(5, customerMasterDTO.getCustomerphone());
			count=stmt.executeUpdate();
			stmt.close();
			DBUtility.closeConnection(null);
		}
		catch (Exception e) 
		{
			DBUtility.closeConnection(e);
			return count;
		}
		
		return count;
	}

	@Override
	public int deleteCustomer(int customerno) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("delete from customertable where customerno= (?)");
			stmt.setInt(1, customerno);
			count=stmt.executeUpdate();
			stmt.close();
			DBUtility.closeConnection(null);
			
		}
		catch(Exception e)
		{
			DBUtility.closeConnection(e);
			return count;
		}
		return count;
	}

	@Override
	public int updateCustomer(CustomerMasterDTO customerMasterDTO) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt = conn.prepareStatement("update customertable set customername =(?), customeraddress=(?) , customeremail=(?),customerphone=(?) where customerno= (?)");
			stmt.setString(1, customerMasterDTO.getCustomername());
			stmt.setString(2, customerMasterDTO.getCustomerAddress());
			stmt.setString(3, customerMasterDTO.getCustomerEmail());
			stmt.setString(4, customerMasterDTO.getCustomerphone());
			stmt.setInt(5, customerMasterDTO.getCustomerno());
			count=stmt.executeUpdate();
			stmt.close();
			DBUtility.closeConnection(null);
		}
		catch(Exception e)
		{
			DBUtility.closeConnection(e);
			return count;
			
		}
		return count;
	}

	@Override
	public CustomerMasterDTO getCustomerMaster(int customerno) {
		CustomerMasterDTO invoice=new CustomerMasterDTO();
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("select * from customertable where customerno=?");
			stmt.setInt(1, customerno);
			ResultSet rs=stmt.executeQuery();	
			while(rs.next())
			{
				invoice.setCustomerno(rs.getInt("customerno"));		
				invoice.setCustomername("customername");
				invoice.setCustomerAddress(rs.getString("customeraddress"));
				invoice.setCustomerEmail(rs.getString("customeremail"));
				invoice.setCustomerphone(rs.getString("customerphone"));
				
			}
			stmt.close();
			DBUtility.closeConnection(null);
			
		}
		catch (Exception e)
		{
			//System.out.println(e);
			DBUtility.closeConnection(e);
			return invoice;
		}
		return invoice;
	}

	@Override
	public Set<CustomerMasterDTO> getCustomerMasterAll() {
		CustomerMasterDTO invoice;
		Set<CustomerMasterDTO> set=new HashSet<>();
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("select * from customertable");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				invoice=new CustomerMasterDTO();
				invoice.setCustomerno(rs.getInt("customerno"));	
				invoice.setCustomername("customername");
				invoice.setCustomerAddress(rs.getString("customeraddress"));
				invoice.setCustomerEmail(rs.getString("customeremail"));
				invoice.setCustomerphone(rs.getString("customerphone"));
				set.add(invoice);
			}
			
			stmt.close();
			DBUtility.closeConnection(null);
			
		}
		catch (Exception e)
		{
			DBUtility.closeConnection(e);
			return set;
		}
		return set;
	
	}

}
