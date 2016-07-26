package servertester.controllers;

import java.util.*;
import java.io.*;

import servertester.views.*;
import server.*;
import client.*;
import shared.communication.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("8080");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser()
	{
		String host = getView().getHost();
		int port = Integer.parseInt(getView().getPort());
		ClientCommunicator cc = new ClientCommunicator(host, port);			// Create a new ClientCommunicator

		String username = getView().getParameterValues()[0];
		String password = getView().getParameterValues()[1];
		
		ValidateUser_Params params = new ValidateUser_Params(username, password);	// Create a Params Object
		getView().setRequest(params.toString());									// Show Request in GUI
		ValidateUser_Result vuResult = cc.validateUser(params);						// Call ClientCommunicator
		getView().setResponse(vuResult.toString());									// Show Response in GUI
	}
	
	private void getProjects()
	{
		String host = getView().getHost();
		int port = Integer.parseInt(getView().getPort());
		ClientCommunicator cc = new ClientCommunicator(host, port);			// Create a new ClientCommunicator

		String username = getView().getParameterValues()[0];
		String password = getView().getParameterValues()[1];
		
		GetProjects_Params params = new GetProjects_Params(username, password);
		getView().setRequest(params.toString());
		GetProjects_Result gpResult = cc.getProjects(params);
		getView().setResponse(gpResult.toString());
	}
	
	private void getSampleImage()
	{
		String host = getView().getHost();
		int port = Integer.parseInt(getView().getPort());
		ClientCommunicator cc = new ClientCommunicator(host, port);			// Create a new ClientCommunicator

		String username = getView().getParameterValues()[0];
		String password = getView().getParameterValues()[1];
		int projectID = Integer.parseInt(getView().getParameterValues()[2]);
		
		GetSampleImage_Params params = new GetSampleImage_Params(username, password, projectID);
		getView().setRequest(params.toString());
		GetSampleImage_Result gsiResult = cc.getSampleImage(params);
		getView().setResponse(gsiResult.toString());
	}
	
	private void downloadBatch() {
	}
	
	private void getFields() {
	}
	
	private void submitBatch() {
	}
	
	private void search() {
	}

}

