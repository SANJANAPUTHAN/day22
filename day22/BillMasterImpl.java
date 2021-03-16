package day22;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class BillMasterImpl implements BillMasterDAO{

	Connection conn=null;
	PreparedStatement stmt=null;	
	@Override
	public int insertBill(BillMasterDTO billMasterDTO) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("insert into billtable values(?,?,?)");
			stmt.setInt(1, billMasterDTO.getInvno());
			stmt.setInt(2, billMasterDTO.getItemno());
			stmt.setInt(3, billMasterDTO.getItemquantity());
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
	public int deleteBill(int itemno) 
	{
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("delete from billtable where itemno= (?)");
			stmt.setInt(1, itemno);
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
	public int updateBill(BillMasterDTO billMasterDTO)
	{
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt = conn.prepareStatement("update billtable set itemno =(?), itemquantity=(?) where invno= (?)");
			stmt.setInt(1, billMasterDTO.getItemno());
			stmt.setInt(2, billMasterDTO.getItemquantity());
			stmt.setInt(3, billMasterDTO.getInvno());
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
	public BillMasterDTO getBillMaster(int itemno) 
	{
		BillMasterDTO invoice=new BillMasterDTO();
	try
	{
		conn=DBUtility.getConnection();
		stmt=conn.prepareStatement("select * from billtable where itemno=?");
		stmt.setInt(1, itemno);
		ResultSet rs=stmt.executeQuery();	
		while(rs.next())
		{
			invoice.setInvno(rs.getInt("invno"));				
			invoice.setItemno(rs.getInt("itemno"));
			invoice.setItemquantity(rs.getInt("itemquantity"));
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
	public Set<BillMasterDTO> getBillMasterAll() {
		BillMasterDTO invoice;
		Set<BillMasterDTO> set=new HashSet<BillMasterDTO>();
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("select * from billtable");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				invoice=new BillMasterDTO();
				invoice.setInvno(rs.getInt("invno"));				
				invoice.setItemno(rs.getInt("itemno"));
				invoice.setItemquantity(rs.getInt("itemquantity"));
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
