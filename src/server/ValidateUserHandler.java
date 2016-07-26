package server;

import shared.communication.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.ServerException;

//import javax.xml.ws.handler.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.sun.net.httpserver.*;

public class ValidateUserHandler implements HttpHandler
{
	Facade facade = new Facade();
	
	@SuppressWarnings({ "restriction" })
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		try
		{
			XStream x = new XStream(new DomDriver());		// Like JSON but better I guess...
			ValidateUser_Params params = (ValidateUser_Params) x.fromXML(exchange.getRequestBody());	// Get Serialized Object
			
			ValidateUser_Result result = facade.validateUser(params);		// Get info from facade
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			x.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		catch(ServerException e)
		{
			exchange.sendResponseHeaders(500, -1);
			exchange.getResponseBody().close();
			e.printStackTrace();
			return;
		}	
	}
}
