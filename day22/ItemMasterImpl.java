package day22;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class ItemMasterImpl implements ItemMasterDAO{

	Connection conn=null;
	PreparedStatement stmt=null;
	@Override
	public int insertItem(ItemMasterDTO itemMasterDTO) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("insert into itemtable values(?,?,?,?)");
			stmt.setInt(1, itemMasterDTO.getItemno());
			stmt.setString(2, itemMasterDTO.getItemname());
			stmt.setFloat(3, itemMasterDTO.getItemprice());
			stmt.setString(4, itemMasterDTO.getItemunit());
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
	public int deleteItem(int itemno) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("delete from itemtable where itemno= (?)");
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
	public int updateItem(ItemMasterDTO itemMasterDTO) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt = conn.prepareStatement("update itemtable set itemname =(?), itemprice=(?) , itemunit = (?) where itemno= (?)");
			
			stmt.setString(1, itemMasterDTO.getItemname());
			stmt.setFloat(2, itemMasterDTO.getItemprice());
			stmt.setString(3, itemMasterDTO.getItemunit());
			stmt.setInt(4, itemMasterDTO.getItemno());
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
	public ItemMasterDTO getItemMaster(int itemno) {
		ItemMasterDTO invoice=new ItemMasterDTO();
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("select * from itemtable where itemno=?");
			stmt.setInt(1, itemno);
			ResultSet rs=stmt.executeQuery();	
			while(rs.next())
			{
				invoice.setItemno(rs.getInt("itemno"));			
				invoice.setItemname(rs.getString("itemname"));
				invoice.setItemprice(rs.getFloat("itemprice"));
				invoice.setItemunit(rs.getString("itemunit"));
				
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
	public Set<ItemMasterDTO> getItemMasterAll() {
		ItemMasterDTO invoice;
		Set<ItemMasterDTO> set=new HashSet<>();
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("select * from itemtable");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				
				invoice=new ItemMasterDTO();
				invoice.setItemno(rs.getInt("itemno"));			
				invoice.setItemname(rs.getString("itemname"));
				invoice.setItemprice(rs.getFloat("itemprice"));
				invoice.setItemunit(rs.getString("itemunit"));
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
