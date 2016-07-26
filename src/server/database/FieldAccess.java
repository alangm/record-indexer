package server.database;

import shared.model.*;

import java.sql.*;
import java.rmi.ServerException;
import java.util.*;

public class FieldAccess
{
	Database db;
	
	public FieldAccess()
	{
		db = new Database();
	}
	public FieldAccess(Database db)
	{
		this.db = db;
	}
	
	public Field insert(Field field) throws ServerException, SQLException
	{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		
		try
		{
			String sql = "insert into field (title, xcoord,"
					   + "width, helphtml, knowndata)"
					   + "values (?, ?, ?, ?, ?)";
			stmt = db. getConnection().prepareStatement(sql);
			stmt.setString	(	1, field.getTitle()				);
			stmt.setInt		(	2, field.getXcoord()			);
			stmt.setInt		(	3, field.getWidth()				);
			stmt.setString	(	4, field.getHelphtml()			);
			stmt.setObject	(	5, field.getKnownData()			);
			
			if(stmt.executeUpdate() == 1)
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				field.setId(id);	// Where does this field go??
			}
			else
			{
				throw new ServerException("Could not insert Field");
			}
		}
		catch(ServerException e) {
			throw new ServerException("Could not insert Field", e);
		}
		catch(SQLException e) {
			throw new SQLException("Could not insert Field", e);
		}
		finally {
			Database.safeClose(stmt);
		}
		
		return field;
	}

	public void delete(Field field) throws ServerException
	{
		PreparedStatement stmt = null;
		
		try
		{	
			String sql = "delete from field where id = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, field.getId());
			
			if(stmt.executeUpdate() != 1)
			{
				throw new ServerException("Could not delete field");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	/*public List<Field> getAll() throws ServerException, SQLException
	{
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean commit = true;
		
		try {
			Database.initialize();
			db.startTransaction();
			String sql = "select id, title, xcoord, width, helphtml, knowndata from field";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int xcoord = rs.getInt(3);
				int width = rs.getInt(4);
				String helphtml = rs.getString(5);
				Array knowndata = rs.getArray("knowndata");
				
				result.add(new Field(title, xcoord, width, helphtml, knowndata));
			}
			db.endTransaction(commit);
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage(), e);
		} catch (ServerException e) {
			throw new ServerException(e.getMessage(), e);
		}finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}
		
		return result;
		
	}*/
}
