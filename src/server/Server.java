package server;

import java.io.*;
import java.net.*;
import java.rmi.ServerException;
import java.util.*;

import shared.communication.*;
import server.database.*;

import com.sun.net.httpserver.*;

public class Server
{
	private static int SERVER_PORT_NUMBER = 8080;			// Default value
	private static final int MAX_WAITING_CONNECTIONS = 10;	// Default value
	
	private HttpServer server;
	
	private ValidateUserHandler validateUserHandler = new ValidateUserHandler();
	private GetProjectsHandler getProjectsHandler = new GetProjectsHandler();
	private GetSampleImageHandler getSampleImageHandler = new GetSampleImageHandler();
	private DownloadBatchHandler downloadBatchHandler = new DownloadBatchHandler();
	private SubmitBatchHandler submitBatchHandler = new SubmitBatchHandler();
	private GetFieldsHandler getFieldsHandler = new GetFieldsHandler();
	private SearchHandler searchHandler = new SearchHandler();
	private DownloadFileHandler downloadFileHandler = new DownloadFileHandler();
	
	/**
	 * Constructor. Does nothing
	 * */
	private Server(){}

	public static void main(String[] args)
	{
		if(args.length > 0)
		{
			SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
		}
		
		new Server().run();
	}
	/**
	 * Runs the server. First initializes the database,
	 * then creates a new HttpServer, and calls start();
	 * */
	private void run()
	{
		try
		{
			Database.initialize();	// This should be the only time I need this...right?
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),MAX_WAITING_CONNECTIONS);

			server.setExecutor(null); // use the default executor
			
			server.createContext("/validateUser", validateUserHandler);
			server.createContext("/getProjects", getProjectsHandler);
			server.createContext("/getSampleImage", getSampleImageHandler);
			server.createContext("/downloadBatch", downloadBatchHandler);
			server.createContext("/submitBatch", submitBatchHandler);
			server.createContext("/getFields", getFieldsHandler);
			server.createContext("/search", searchHandler);
			server.createContext("/downloadFile", downloadFileHandler);
		}
		catch(IOException e)
		{
			System.out.println("Could not create HTTP server: " + e.getMessage());
		}
		
		server.start();
	}
}
