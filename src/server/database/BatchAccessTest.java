package server.database;

import shared.model.*;
import static org.junit.Assert.*;

import org.junit.Test;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.*;

public class BatchAccessTest
{
	Database db = new Database();
	BatchAccess batchDAO = new BatchAccess(db);

	@Test
	public void test() throws ServerException, SQLException
	{
		ArrayList<String> records = new ArrayList<String>();
		records.add("Well hello there!");
		Batch image = new Batch(102, "filename123", records, 0, 1);
		
		batchDAO.insert(image);
		
		batchDAO.delete(image);
	}

}
