package de.Kurfat.Java.SimpleWebserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;

public class Webserver extends Thread{

	private int port;
	private String path;
	private String defaultFileName;
	private boolean debug;
	private ServerSocket serverSocket;
	
	public Webserver(int port, String path, String defaultFileName, boolean debug) {
		this.port = port;
		this.path = path;
		this.defaultFileName = defaultFileName;
		this.debug = debug;
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			while (Thread.interrupted() == false) {
				try {
					Socket socket = serverSocket.accept();
					HttpHeader header = new HttpHeader(socket);
					String filename = path + header.getPath();
					File file = new File(filename);
					if(file.exists() == false) {
						if(debug) System.out.println("Client \"" + socket.getInetAddress().getHostAddress() + "\" can't read \"" + filename + "\"");
						continue;
					}
					write(file, socket.getOutputStream());
					if(debug) System.out.println("Client \"" + socket.getInetAddress().getHostAddress() + "\" read \"" + filename + "\"");
				} catch (SocketException | NoSuchElementException e) {
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void write(byte[] bytes, OutputStream out) throws FileNotFoundException, IOException {
		DataOutputStream dataOut = new DataOutputStream(out);
		dataOut.writeBytes("HTTP/1.0 200 Document Follows\r\n");
		dataOut.writeBytes("Content-Length: " + bytes.length + "\r\n");
		dataOut.writeBytes("\r\n");
		dataOut.write(bytes, 0, bytes.length);
	}
	private void write(FileInputStream in, OutputStream out) throws FileNotFoundException, IOException {
		int bytesLenght = in.available();
		byte[] bytes = new byte[bytesLenght];
		in.read(bytes);
		in.close();
		write(bytes, out);
	}
	private void write(File file, OutputStream out) throws FileNotFoundException, IOException {
		if(file.isDirectory()) file = new File(file.getPath() + "/" + defaultFileName);
		FileInputStream in  = new FileInputStream(file);
		write(in, out);
	}
	
}
