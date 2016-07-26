package server.database;

import shared.model.*;
import static org.junit.Assert.*;

import org.junit.Test;

import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.*;

public class RecordAccessTest
{
	Database db = new Database();
	RecordAccess recordDAO = new RecordAccess(db);

	@Test
	public void test() throws ServerException, SQLException
	{
		ArrayList<String> values = new ArrayList<String>();
		values.add("Alan");
		values.add("Moody");
		
		Record record1 = new Record(values);
		
		recordDAO.insert(record1);
		
		recordDAO.delete(record1);
	}

}
