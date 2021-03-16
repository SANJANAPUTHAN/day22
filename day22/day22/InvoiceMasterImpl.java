package day22;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class InvoiceMasterImpl implements InvoiceMasterDAO {

	Connection conn=null;
	PreparedStatement stmt=null;	
	@Override
	public int insertInvoice(InvoiceMasterDTO invMasterDTO) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("insert into invoicetable values(?,?,?)");
			stmt.setInt(1, invMasterDTO.getInvno());
			stmt.setString(2, String.valueOf(invMasterDTO.getInvdate()));
			stmt.setInt(3, invMasterDTO.getCustomerno());
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
	public int deleteInvoice(int invno) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("delete from invoicetable where invno= (?)");
			stmt.setInt(1, invno);
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
	public int updateInvoice(InvoiceMasterDTO invMasterDTO) {
		int count=0;
		try
		{
			conn=DBUtility.getConnection();
			stmt = conn.prepareStatement("update invoicetable set invdate =(?), customerno=(?) where invno= (?)");
			stmt.setString(1, String.valueOf(invMasterDTO.getInvdate()));
			stmt.setInt(2, invMasterDTO.getCustomerno());
			stmt.setInt(3, invMasterDTO.getInvno());
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
	public InvoiceMasterDTO getInvoiceMaster(int invno) {
		InvoiceMasterDTO invoice=new InvoiceMasterDTO();
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("select * from invoicetable where invno=?");
			stmt.setInt(1, invno);
			ResultSet rs=stmt.executeQuery();	
			while(rs.next())
			{
				invoice.setInvno(rs.getInt("invno"));				
				invoice.setInvdate(LocalDate.parse(rs.getString("invdate")));
				invoice.setCustomerno(rs.getInt("customerno"));
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
	public Set<InvoiceMasterDTO> getInvoiceMasterAll() {
		InvoiceMasterDTO invoice;
		Set<InvoiceMasterDTO> set=new HashSet<InvoiceMasterDTO>();
		try
		{
			conn=DBUtility.getConnection();
			stmt=conn.prepareStatement("select * from invoicetable");
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				invoice=new InvoiceMasterDTO();
				invoice.setInvno(rs.getInt("invno"));
				invoice.setInvdate(LocalDate.parse(rs.getString("invdate")));
				invoice.setCustomerno(rs.getInt("customerno"));
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
