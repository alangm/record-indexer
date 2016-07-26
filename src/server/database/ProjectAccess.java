package server.database;

import shared.model.*;

import java.rmi.ServerException;
import java.sql.*;
import java.util.ArrayList;

public class ProjectAccess
{
	Database db;
	
	public ProjectAccess(Database db)
	{
		this.db = db;
	}
	
	public Project insert(Project project)
	{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		
		try
		{
			String sql = "insert into project (title, recordsperimage, firstycoord,"
					   + "recordheight, fields, images) values (?,?,?,?,?,?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString	(	1, project.getTitle()			);
			stmt.setInt		(	2, project.getRecordsPerImage()	);
			stmt.setInt		(	3, project.getFirstYCoord()		);
			stmt.setInt		(	4, project.getRecordHeight()	);
			stmt.setObject	(	5, project.getFieldIDs()		);
			stmt.setObject	(	6, project.getImageIDs()		);
			
			if(stmt.executeUpdate() == 1)
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				project.setId(id); // Where does this project go...???
				//update(project);
			}
			else
			{
				throw new ServerException("Coult not insert project");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (ServerException e) {
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null) 	Database.safeClose(stmt);
			if (keyRS != null)	Database.safeClose(keyRS);
		}
		return project;
	}
	
	public void update(Project project)
	{
		PreparedStatement stmt = null;
		
		try
		{	
			String sql = "update project set title = ?, recordsperimage = ?, firstycoord = ?,"
					   + "recordheight = ?, fields = ?, images = ?"
					   + "where id = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsPerImage());
			stmt.setInt(3, project.getFirstYCoord());
			stmt.setInt(4, project.getRecordHeight());
			stmt.setObject(5, project.getFields());
			stmt.setObject(6, project.getImages());
			stmt.setInt(7, project.getId());
			
			if (stmt.executeUpdate() != 1)
			{
				throw new ServerException("Could not update project");
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

	public ArrayList<Project> getAllProjects()
	{
		ArrayList<Project> result = new ArrayList<Project>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			String sql = "select * from project";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsperimage = rs.getInt(3);
				int firstycoord = rs.getInt(4);
				int recordheight = rs.getInt(5);
				Object fields = rs.getObject(6);
				Object images = rs.getObject(7);
				
				result.add(new Project(id, title, recordsperimage, firstycoord,
									   recordheight, fields, images));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}

		return result;
	}

	public void delete(Project project) throws ServerException
	{
		PreparedStatement stmt = null;
		
		try
		{
			String sql = "delete from project where id = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, project.getId());
			
			if(stmt.executeUpdate() != 1)
			{
				throw new ServerException("Could not delete project");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public Batch getImage(int projectID)
	{
		return null;
	}
	
	public boolean submitBatch(int batchID)
	{
		return false;
	}
}
