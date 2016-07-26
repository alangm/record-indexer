package server;

import java.rmi.ServerException;
import java.util.*;

import server.database.*;
import shared.communication.*;
import shared.model.*;

public class Facade
{
	private Database db = new Database();	// Is holding each DAO... (that passes 'this')
	
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws ServerException
	{	
		db.startTransaction();

		User user = db.getUserDAO().findUser(params.getUsername(), params.getPassword());
		ValidateUser_Result result = new ValidateUser_Result(user);

		db.endTransaction(true);
		
		return result;
	}
	public GetProjects_Result getProjects(GetProjects_Params params) throws ServerException
	{
		
		GetProjects_Result result = null;
		ValidateUser_Params vup = new ValidateUser_Params(params.getUsername(), params.getPassword());
		
		if(!(validateUser(vup).getSuccess()))		// If we couldn't validate the user...then return false;
		{
			result = new GetProjects_Result();
			result.setSuccess(false);
			return result;
		}
		else
		{
			db.startTransaction();
			
			ArrayList<Project> projects = db.getProjectDAO().getAllProjects();
			result = new GetProjects_Result(projects);
	
			db.endTransaction(true);
		}
		
		return result;
	}
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws ServerException
	{
		GetSampleImage_Result result;
		
		ValidateUser_Params vup = new ValidateUser_Params(params.getUsername(), params.getPassword());
		if(!validateUser(vup).getSuccess())		// If we couldn't validate the user...then return false;
		{
			result = new GetSampleImage_Result();
			result.setSuccess(false);
			return result;
		}

		db.startTransaction();
		
		Batch image = db.getBatchDAO().getSampleImage(params.getProjectID());
		result = new GetSampleImage_Result(image);
		
		db.endTransaction(true);
		
		return result;
	}
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params)
	{
		Batch image = db.getProjectDAO().getImage(params.getProjectID());
		DownloadBatch_Result result = new DownloadBatch_Result(image);
		
		return result;
	}
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params)
	{
		boolean success = db.getProjectDAO().submitBatch(params.getBatchID());		// I guess...???
		SubmitBatch_Result result = new SubmitBatch_Result(success);
		
		return result;
	}
	public GetFields_Result getFields(GetFields_Params params)
	{
		return null;
	}
	public Search_Result search(Search_Params params)
	{
		return null;
	}
}
