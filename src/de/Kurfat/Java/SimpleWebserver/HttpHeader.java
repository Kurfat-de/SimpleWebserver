package de.Kurfat.Java.SimpleWebserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class HttpHeader {
	
	private String type;
	private String path;
	private String protocol;
	private String domain;
	private String userAgent;
	private String accept;
	private String acceptLanguage;
	private String acceptEncoding;
	private String connection;

	public HttpHeader(Socket socket) throws IOException , NoSuchElementException{
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		List<String> lines = new ArrayList<String>();
		try {
			while (true) {
				String line = in.readLine();
				if(line == null || line.isEmpty()) break;
				lines.add(line);
			}
		}catch (SocketException e) {
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(lines.size() == 0 || lines.get(0).startsWith("GET") == false) throw new UnsupportedOperationException("Is not http request!");
		StringTokenizer tokenizer = new StringTokenizer(lines.get(0));
		type = tokenizer.nextToken();
		path = tokenizer.nextToken();
		protocol = tokenizer.nextToken();
		for(int i = 1; i < lines.size(); i++) {
			String line = lines.get(i);
			if(line.startsWith("Host: ")) domain = line.replace("Host: ", "");
			if(line.startsWith("User-Agent: ")) userAgent = line.replace("User-Agent: ", "");
			if(line.startsWith("Accept: ")) accept = line.replace("Accept: ", "");
			if(line.startsWith("Accept-Language: ")) acceptLanguage = line.replace("Accept-Language: ", "");
			if(line.startsWith("Accept-Encoding: ")) acceptEncoding = line.replace("Accept-Encoding: ", "");
			if(line.startsWith("Connection: ")) connection = line.replace("Connection: ", "");
		}
	}

	public String getType() {
		return type;
	}
	public String getPath() {
		return path;
	}
	public String getProtocol() {
		return protocol;
	}
	public String getDomain() {
		return domain;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public String getAccept() {
		return accept;
	}
	public String getAcceptLanguage() {
		return acceptLanguage;
	}
	public String getAcceptEncoding() {
		return acceptEncoding;
	}
	public String getConnection() {
		return connection;
	}
	
}
