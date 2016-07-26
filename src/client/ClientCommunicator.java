package client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;

public class ClientCommunicator
{
	private String SERVER_HOST = "localhost";
	private int SERVER_PORT = 8080;
	private String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private static final String HTTP_POST = "POST";
	
	
	public ClientCommunicator(String host, int port)
	{
		SERVER_HOST = host;
		SERVER_PORT = port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	
	/**
	 * Validates user credentials
	 * 
	 * @param params ValidateUser_Params object that contains the user's name and password
	 * @return A ValidateUser_Result object containing the user's full name and number of
	 * records indexed if TRUE, false if FALSE, failed if FAILED
	 * @throws Exception 
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws Exception
	{
		ValidateUser_Result result = null;
		try {
			result = (ValidateUser_Result)doPost("/validateUser", params);
		}
		catch(Exception e) {
			e.printStackTrace();
			result = new ValidateUser_Result();
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	
	/**
	 * Gives information about all of a specific user's current projects
	 * 
	 * @param params GetProjects_Params object containing the user's name and password
	 * @return A GetProjects_Result object containing names of batches within each given project
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) throws Exception
	{
		GetProjects_Result result = null;
		try {
			result = (GetProjects_Result)doPost("/getProjects", params);
		}
		catch(Exception e) {
			e.printStackTrace();
			result = new GetProjects_Result();
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	
	/**
	 * Returns a sample image for the specified project
	 * 
	 * @param params GetSampleImage_Params object containing the user's name, password, and
	 * a project ID corresponding to the current project
	 * @return Returns a GetSampleImage_Result object containing a sample image for the specified project
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws Exception
	{
		GetSampleImage_Result result = null;
		try {
			result = (GetSampleImage_Result)doPost("/getSampleImage", params);
		}
		catch(Exception e) {
			e.printStackTrace();
			result = new GetSampleImage_Result();
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	
	/**
	 * Downloads a batch for the user to index
	 * 
	 * @param params DownloadBatch_Params object containing the user's name, password, and
	 * a project ID corresponding to the current project
	 * @return DownloadBatch_Result object containing the newly assigned batch
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws Exception		// TODO
	{
		return null;
	}
	
	/**
	 * Submits a batch from the user to the server
	 * 
	 * @param params SubmitBatch_Params object containing the user's name, password, and
	 * a batch ID corresponding to the batch to be submitted
	 * @return SubmitBatch_Result object indicating true if the batch was submitted successfully
	 * or false otherwise
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws Exception		// TODO
	{
		//return (SubmitBatch_Result)doPost("/submitBatch", params);
		SubmitBatch_Result result = null;
		try {
			result = (SubmitBatch_Result)doPost("/submitBatch", params);
		}
		catch(Exception e) {
			result = new SubmitBatch_Result(false);
			// Set the success var here
		}
		return result;
	}
	
	/**
	 * retrieves information about the fields in a specific project
	 * 
	 * @param params GetFields_Params object containing the user's name, password, and
	 * a project ID corresponding to the current project
	 * @return GetFields_Result object containing information about each of the
	 * fields for the specified project
	 */
	public GetFields_Result getFields(GetFields_Params params) throws Exception		// TODO
	{
		GetFields_Result result = null;
		try {
			result = (GetFields_Result)doPost("/search", params);
		}
		catch(Exception e) {
			result = new GetFields_Result();
			// Set the success var here
		}
		return result;
	}
	
	/**
	 * Searches the indexed records for the specified strings
	 * 
	 * @param params GetFields_Params object containing the user's name, password,
	 * a comma-seperated list of fields to be searched, and a comma-seperated list of
	 * strings to search for
	 * @return GetFields_Result object
	 */
	public GetFields_Result search(GetFields_Params params)	// Double Check TODO
	{
		GetFields_Result result = null;
		try {
			result = (GetFields_Result)doPost("/search", params);
		}
		catch(Exception e) {
			result = new GetFields_Result();
			// Set the success var here
		}
		return result;
	}
	
	public Object doPost(String urlPath, Object data) throws Exception		// Serializes the params object, sends, and receives
	{
		URL url;
		XStream x = new XStream(new DomDriver());
		Object result = new Object();
		
		try
		{
			url = new URL(URL_PREFIX+urlPath);
			HttpURLConnection c = (HttpURLConnection)url.openConnection();
			
			c.setDoOutput(true);
			c.setDoInput(true);
			c.setRequestMethod(HTTP_POST);
			c.addRequestProperty("accept", "text/html");
			
			c.connect();
			
			x.toXML(data, c.getOutputStream());
			
			c.getOutputStream().close();
			
			if(c.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				result = x.fromXML(c.getInputStream());
				c.getInputStream().close();
			}
			else
			{
				System.out.println("Response code was bad");
			}
		}
		catch (MalformedURLException e) {
			//e.printStackTrace();
			//System.out.println("Returning Failed...");
			//return "FAILED";
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
