package server.database;

import shared.model.*;
import server.database.*;

import java.rmi.ServerException;
import java.sql.SQLException;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserAccessTest
{
	Database db = new Database();
	UserAccess userDAO = new UserAccess(db);
	
	@Test
	public void test() throws ServerException, SQLException
	{
		User user1 = new User(-1, "alangm", "moody33", "Alan", "Moody", "alan@byu.edu", 0, 0);
		
		userDAO.insert(user1);
		
		user1.setLastName("MOODY");
		user1.setPassword("password");
		
		userDAO.update(user1);
		
		User u = userDAO.findUser("alangm", "password");
		
		userDAO.delete(user1);
	}

}
