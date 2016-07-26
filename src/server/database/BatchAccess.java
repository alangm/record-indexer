package server.database;

import shared.model.*;

import java.rmi.ServerException;
import java.sql.*;

public class BatchAccess
{
	Database db;
	
	public BatchAccess(Database db)
	{
		this.db = db;
	}
	
	public Batch insert(Batch image) throws ServerException
	{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		
		try
		{	
			String sql = "insert into image (file, records, status, projectid) values (?,?,?,?)";
			
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setObject(1, image.getFile());
			stmt.setObject(2, image.getRecords());
			stmt.setInt(3, image.getStatus());
			stmt.setInt(4, image.getProjectID());
			
			if(stmt.executeUpdate() == 1)
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				image.setId(id);
			}
			else
			{
				throw new ServerException("Could not insert image");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch(ServerException e) {
			e.printStackTrace();
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		
		return image;
	}

	public void update(Batch image)
	{
		PreparedStatement stmt = null;
		
		try
		{	
			String sql = "update image set file = ?, records = ?, status = ?, projectid = ?"
					   + "where id = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, image.getFileName());
			stmt.setObject(2, image.getRecords());
			stmt.setInt(3, image.getStatus());
			stmt.setInt(4, image.getProjectID());
			stmt.setInt(5, image.getId());
			
			if (stmt.executeUpdate() != 1)
			{
				throw new ServerException("Could not update batch");
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
	
	public void delete(Batch image) throws SQLException, ServerException
	{
		PreparedStatement stmt = null;
		
		try
		{
			String sql = "delete from image where id = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, image.getId());
			if(stmt.executeUpdate() != 1)
			{
				throw new ServerException("could not delete batch");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		} finally {
			Database.safeClose(stmt);
		}
	}

	public Batch getSampleImage(int projectID)
	{
		Batch result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			String sql = "select * from image where projectid = ?";
			
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, projectID);
			
			rs = stmt.executeQuery();
			if(rs.next())	// Point it to the first row...
			{
				// Create an image and return it
				int id = rs.getInt(1);
				String file = rs.getString(2);
				Object records = rs.getObject(3);
				int status = rs.getInt(4);
				int projectid = rs.getInt(5);
				result = new Batch(id, file, records, status, projectid);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}
			
		return result;			// null will mean that it was an invalid projectID
	}
	
	/*Will need a search method. Look up the syntax...
	 * select blah from ( table join table ) where ___ = ?   ... etc...
	 * " SELECT ___ FROM ( a JOIN b ) WHERE fieldID IN (comma,seperated,list) AND number(4,6,7) "
	 * table.column
	 * Send back a search object
	 * 
	 * 
	 * 
	 * batch should have a project id, url
	 * 
	 * */
}
