package server.database;

import java.rmi.ServerException;
import java.sql.*;
import java.io.*;

import shared.model.*;

public class Database
{
	private static final String DATABASE_DIRECTORY 	= "database";
	private static final String DATABASE_FILE 		= "RecordIndexerDatabase.sqlite";
	private static final String DATABASE_URL 		= "jdbc:sqlite:" + DATABASE_DIRECTORY + File.separator + DATABASE_FILE;
	
	private Connection connection;
	
	private UserAccess userDAO;
	private ProjectAccess projectDAO;
	private BatchAccess batchDAO;
	private FieldAccess fieldDAO;
	private RecordAccess recordDAO;
	
	
	public Database()
	{
		connection = null;
		userDAO = new UserAccess(this);
		projectDAO = new ProjectAccess(this);
		batchDAO = new BatchAccess(this);
		fieldDAO = new FieldAccess(this);
		recordDAO = new RecordAccess(this);
	}
	
	public Connection getConnection(){
		return connection;
	}
	public UserAccess getUserDAO(){
		return userDAO;
	}
	public ProjectAccess getProjectDAO(){
		return projectDAO;
	}
	public BatchAccess getBatchDAO(){
		return batchDAO;
	}
	public FieldAccess getFieldDAO(){
		return fieldDAO;
	}
	public RecordAccess getRecordDAO(){
		return recordDAO;
	}
	
	public static void initialize() throws ServerException
	{
		try
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{
			throw new ServerException("Could not load database driver", e);
		}
	}
	
	public void startTransaction() throws ServerException
	{
		try
		{
			System.out.println(connection == null);		// Connection needs to be null coming into here
			//assert (connection == null);
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			throw new ServerException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}
	public boolean inTransaction()
	{
		return (connection != null);
	}
	public void endTransaction(boolean commit)
	{
		if (connection != null)
		{		
			try
			{
				if (commit)
				{
					connection.commit();
				}
				else
				{
					connection.rollback();
				}
			}
			catch (SQLException e)
			{
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally
			{
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				System.out.println("Could not close connection");
			}
		}
	}
	public static void safeClose(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				System.out.println("Could not close statement");
			}
		}
	}
	public static void safeClose(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				System.out.println("Could not close prepared statement");
			}
		}
	}
	public static void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				System.out.println("Could not close result set");
			}
		}
	}
}
