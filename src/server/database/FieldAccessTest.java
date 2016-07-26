package server.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.*;

import javax.xml.parsers.ParserConfigurationException;

import shared.model.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.xml.sax.SAXException;

public class FieldAccessTest
{
	FieldAccess fieldDAO = new FieldAccess();
	
	@Test
	public void test() throws SQLException, ParserConfigurationException,
							  SAXException, IOException
	{
		Field field1 = new Field(4, 365, "age.html", "1890_first_names.txt");
		
		fieldDAO.insert(field1);
		
		fieldDAO.delete(field1);
	}

}
