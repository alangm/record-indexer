package server.database;

import shared.model.*;

import java.util.*;
import java.rmi.ServerException;
import java.sql.*;

public class UserAccess
{
	Database db;
	
	public UserAccess(Database db)
	{
		this.db = db;
	}

	public User insert(User user) throws ServerException
	{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		
		try
		{	
			String sql = "insert into user (username, password, firstname, "
						+ "lastname, email, recordsindexed, batch_id) values "
						+ "(?,?,?,?,?,?,?)";
			
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString	(	1, user.getUserName()		);
			stmt.setString	(	2, user.getPassword()		);
			stmt.setString	(	3, user.getFirstName()		);
			stmt.setString	(	4, user.getLastName()		);
			stmt.setString	(	5, user.getEmail()			);
			stmt.setInt		(	6, user.getRecordsIndexed()	);
			stmt.setInt		(	7, user.getBatchID()		);
			
			if(stmt.executeUpdate() == 1)
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setId(id);	// Where does this user go?? -- I'm going to return it
			}
			else
			{
				throw new ServerException("Could not insert user");
			}
		}
		catch(ServerException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		
		return user;
	}

	public void update(User user) throws ServerException
	{
		PreparedStatement stmt = null;
		
		try
		{	
			String sql = "update user set username = ?, password = ?, firstname = ?,"
					   + "lastname = ?, email = ?, recordsindexed = ?, batch_id = ?"
					   + "where id = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getRecordsIndexed());
			stmt.setInt(7, user.getBatchID());
			stmt.setInt(8, user.getId());
			
			if (stmt.executeUpdate() != 1)
			{
				throw new ServerException("Could not update user");
			}
		}
		catch(ServerException e) {
			e.printStackTrace();
		}
		catch (SQLException e ) {
			e.printStackTrace();
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	public void delete(User user) throws ServerException
	{
		PreparedStatement stmt = null;
		
		try
		{
			String sql = "delete from user where id = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, user.getId());
			if (stmt.executeUpdate() != 1)
			{
				throw new ServerException("Could not delete user");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.safeClose(stmt);
		}
	}
	
	public User findUser(String u, String p)
	{
		User result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			String sql = "select * from user where username = ? and password = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, u);
			stmt.setString(2, p);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				String email = rs.getString(6);
				int recordsindexed = rs.getInt(7);
				int batch_id = rs.getInt(8);
				
				result = new User(id, username, password, firstname, lastname,
						email, recordsindexed, batch_id);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}
		
		return result;
	}
}
