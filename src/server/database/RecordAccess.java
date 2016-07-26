package server.database;

import shared.model.*;

import java.rmi.ServerException;
import java.sql.*;

public class RecordAccess
{
	Database db;
	
	public RecordAccess(Database db)
	{
		this.db = db;
	}
	
	public Record insert(Record record) throws ServerException
	{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		
		try
		{
			String sql = "insert into record (recordvalues) values (?)";	// There is a problem here
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setObject(1, record.getValues());
			
			if(stmt.executeUpdate() == 1)
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				record.setId(id);		// WHERE DOES THIS GO??
			}
			else
			{
				throw new ServerException("Could not insert record");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (ServerException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) 	Database.safeClose(stmt);
			if (keyRS != null)	Database.safeClose(keyRS);
		}
		
		return record;
	}

	public void delete(Record record) throws ServerException
	{
		PreparedStatement stmt = null;
		
		try
		{
			String sql = "delete from record where id = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, record.getId());
			
			if(stmt.executeUpdate() != 1)
			{
				throw new ServerException("Could not delete record");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
