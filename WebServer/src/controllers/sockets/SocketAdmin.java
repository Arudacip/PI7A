package controllers.sockets;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

import controllers.ControllerMain;
import views.ViewServiceAdmin;

public class SocketAdmin implements Runnable
{
	
	// TODO: Javadoc

    private static final File WEB_ROOT = new File("./resources/");
    private static final String DEFAULT_FILE = "index.html";
    private static final String FILE_NOT_FOUND = "404.html";
    private static final String NOT_SUPPORTED = "notsupported.html";
    private ControllerMain controller = null;
	private ServerSocket servidor;
	private Socket cliente;
	
	// Construtor p	rincipal
	public SocketAdmin(ControllerMain controller)
	{
        this.controller = controller;
	}
	
	// Construtor auxiliar
	public SocketAdmin(Socket cliente)
	{
		this.cliente = cliente;
	}

	public void start(int porta) throws IOException
	{
		servidor = new ServerSocket(porta);
	}
	
	public boolean stop()
	{
		try
		{
			servidor.close();
			return true;
		} catch (IOException e)
		{
			// TODO: tratar exception
			e.printStackTrace();
			return false;
		}
	}
	
	public int getStatus()
	{
		int status = ViewServiceAdmin.UNKOWN;
		try
		{
			boolean isdown = servidor.isClosed();
			if (!isdown)
			{
				status = ViewServiceAdmin.STARTED;
			} else
			{
				status = ViewServiceAdmin.STOPPED;
			}
		} catch (Exception e)
		{
			// TODO tratar exception
			e.printStackTrace();
			status = ViewServiceAdmin.UNKOWN;
		}
		return status;
	}
	
	public Socket esperarCliente()
	{
		cliente = null;
		try
		{
			while (true)
			{
				cliente = servidor.accept();
				SocketAdmin server = new SocketAdmin(cliente);
				if (ControllerMain.VERBOSE)
				{
					System.out.println("Conexao aberta. (" + cliente.toString() + ")");
					System.out.println("Cliente na porta: " + cliente.getPort());
				}
				controller.generateLog(ControllerMain.INFO, "Cliente "+cliente.toString()+" conectado na porta "+ControllerMain.PORTA+".");
				// create dedicated thread to manage the client connection
				Thread thread = new Thread(server);
				thread.start();
			}
		} catch (IOException e)
		{
			// TODO tratar exception
			e.printStackTrace();
		}
		return cliente;
	}
	
	@Override
	public void run()
	{
		// Gerenciando a conexao do cliente individaual
		BufferedReader in = null;
		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
		String fileRequested = null;
		
		try
		{
			// get reader for characters from the client via input stream on the socket
			in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			// get character output stream to client (for headers)
			out = new PrintWriter(cliente.getOutputStream());
			// get binary output stream to client (for requested data)
			dataOut = new BufferedOutputStream(cliente.getOutputStream());
			
			// get first line of the request from the client
			String input = in.readLine();
			// we parse the request with a string tokenizer
			StringTokenizer parse = new StringTokenizer(input);
			// we get the HTTP method of the client
			String method = parse.nextToken().toUpperCase();
			// we get file requested
			fileRequested = parse.nextToken().toLowerCase();
			
			// supporting only GET and HEAD methods, this checks if the method is different then supported
			if (!method.equals("GET")  &&  !method.equals("HEAD"))
			{
				if (ControllerMain.VERBOSE)
				{
					System.out.println("501 Nao implementado: metodo " + method + ".");
				}
				controller.generateLog(ControllerMain.INFO, "501 - Nao implementado.");
				
				// return the not supported file to the client
				File file = new File(WEB_ROOT, NOT_SUPPORTED);
				int fileLength = (int) file.length();
				String contentMimeType = "text/html";
				// read content to return to client
				byte[] fileData = lerArquivoDados(file, fileLength);
					
				// send HTTP Headers with data to client
				out.println("HTTP/1.1 501 Nao Implementado");
				out.println("Server: Servidor Java HTTP - ECP7AN-MCA1-09");
				out.println("Date: " + new Date());
				out.println("Content-type: " + contentMimeType);
				out.println("Content-length: " + fileLength);
				out.println(); // blank line between headers and content. Very important!
				out.flush(); // flush character output stream buffer
				// file
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
				
			} else
			{
				// GET or HEAD method
				if (fileRequested.endsWith("/"))
				{
					fileRequested += DEFAULT_FILE;
				}
				
				File file = new File(WEB_ROOT, fileRequested);
				int fileLength = (int) file.length();
				String content = getContentType(fileRequested);
				
				// metodo de conexao GET no protocolo HTTP 
				if (method.equals("GET"))
				{
					byte[] fileData = lerArquivoDados(file, fileLength);
					
					// send HTTP Headers
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Servidor Java HTTP - ECP7AN-MCA1-09");
					out.println("Date: " + new Date());
					out.println("Content-type: " + content);
					out.println("Content-length: " + fileLength);
					out.println(); // blank line between headers and content. Very important!
					out.flush(); // flush character output stream buffer
					
					dataOut.write(fileData, 0, fileLength);
					dataOut.flush();
				}
				
				if (ControllerMain.VERBOSE)
				{
					System.out.println("HTTP 200: Arquivo " + fileRequested + " e tipo " + content + " enviados.");
				}
				controller.generateLog(ControllerMain.INFO, "HTTP 200: Arquivo " + fileRequested + " e tipo " + content + " enviados.");
			}
			
		} catch (FileNotFoundException fnfe)
		{
			try
			{
				arquivoNaoEncontrado(out, dataOut, fileRequested);
			} catch (IOException ioe)
			{
				String message = ioe.getMessage();
				System.out.println("SYSError - FileNotFoundException: " + message);
				controller.generateLog(ControllerMain.CRIT, "SYSError - FileNotFoundException: " + message);
			}
			
		} catch (IOException ioe)
		{
			String message = ioe.getMessage();
			System.out.println("SYSError - Erro de servidor: " + message);
			controller.generateLog(ControllerMain.CRIT, "SYSError - Erro de servidor: " + message);
		} finally
		{
			try
			{
				in.close();
				out.close();
				dataOut.close();
				cliente.close(); // we close socket connection
			} catch (Exception e)
			{
				String message = e.getMessage();
				System.out.println("SYSError - Erro ao fechar conexao: " + message);
				controller.generateLog(ControllerMain.CRIT, "SYSError - Erro ao fechar conexao: " + message);
			}
			if (ControllerMain.VERBOSE)
			{
				System.out.println("Conexao fechada com sucesso.");
				controller.generateLog(ControllerMain.INFO, "Conexao fechada com sucesso.");
			}
		}
	}
	
	private byte[] lerArquivoDados(File file, int fileLength) throws IOException
	{
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		try
		{
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally
		{
			if (fileIn != null) 
				fileIn.close();
		}
		return fileData;
	}
	
	// retorna os MIME Types suportados
	private String getContentType(String fileRequested)
	{
		if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
			return "text/html";
		else
			return "text/plain";
	}
	
	private void arquivoNaoEncontrado(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException
	{
		File file = new File(WEB_ROOT, FILE_NOT_FOUND);
		int fileLength = (int) file.length();
		String content = "text/html";
		byte[] fileData = lerArquivoDados(file, fileLength);
		
		out.println("HTTP/1.1 404 Arquivo Nao Encontrado");
		out.println("Server: Servidor Java HTTP - ECP7AN-MCA1-09");
		out.println("Date: " + new Date());
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println(); // blank line between headers and content. Very important!
		out.flush(); // flush character output stream buffer
		
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
		
		if (ControllerMain.VERBOSE)
		{
			System.out.println("HTTP 404: Arquivo  " + fileRequested + " nao encontrado");
			controller.generateLog(ControllerMain.CRIT, "HTTP 404: Arquivo  " + fileRequested + " nao encontrado");
		}
	}
}