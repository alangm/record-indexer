package server.database;

import shared.model.*;

import java.lang.reflect.Array;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProjectAccessTest
{
	Database db = new Database();
	ProjectAccess projectDAO = new ProjectAccess(db);

	@Test
	public void test() throws ServerException, SQLException
	{	
		Object fieldsArray = new ArrayList<Field>();
		Object imagesArray = new ArrayList<Batch>();
		Project project1 = new Project(101, "Title!!", 4, 5, 6, fieldsArray, imagesArray);
		
		projectDAO.insert(project1);
		
		projectDAO.delete(project1);
		
		projectDAO.getAllProjects();
	}

}
