import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Connection {
	
	URLConnection connection=null;
	
	public URLConnection getConnectoin() {
		return connection;
	}

	public void setConnectoin(URLConnection connection) {
		this.connection = connection;
	}

	public void createConnection(String link) {
		try {
			URL url=new URL(link);
			connection = url.openConnection();
		} 
		catch (MalformedURLException e) {} 
		catch (IOException e) {}
		
	}
	public BufferedReader getStream()  {
		InputStream stream=null;
		BufferedReader reader=null;
		try {
			stream = connection.getInputStream();	
			reader=new BufferedReader(new InputStreamReader(stream));
		} catch (IOException e) {
			
		}
		return reader;
	}

}
