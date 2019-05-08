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

/**
 * Classe que cria o server socket principal do servidor, assim como controla e trata as threads dos usuarios, sob demanda.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 * 
 * @param WEB_ROOT : [CONSTANT] raiz do service web
 * @param DEFAULT_FILE : [CONSTANT] path do arquivo de indice
 * @param NOT_FOUND : [CONSTANT] path do arquivo de erro 404
 * @param NOT_SUPPORTED : [CONSTANT] path do arquivo de erro 405
 * @param UNKNOWN : [CONSTANT] path do arquivo de erro 400 
 * @param servidor : ponteiro para o servidor
 * @param cliente : ponteiro para o cliente da thread
 */

public class SocketAdmin implements Runnable
{

    private static final File WEB_ROOT = new File("./resources/");
    private static final String DEFAULT_FILE = "index.html";
    private static final String NOT_FOUND = "404.html";
    private static final String NOT_SUPPORTED = "405.html";
	private static final String UNKNOWN = "400.html";
	private ServerSocket servidor;
	private Socket cliente;
	
	/**
	 * Construtor principal do SocketAdmin.
	 */
	public SocketAdmin()
	{
	}
	
	/**
	 * Construtor auxiliar do SocketAdmin.
	 * @param cliente : ciente a ser conectado ao service web
	 */
	public SocketAdmin(Socket cliente)
	{
		this.cliente = cliente;
	}
	
	/**
	 * Inicia o server socket para receber as requisicoes dos usuarios.
	 * @param porta : porta onde o service vai "ouvir"
	 * @throws IOException
	 */
	public void start(int porta) throws IOException
	{
		servidor = new ServerSocket(porta);
	}
	
	/**
	 * Parar o server socket do service web.
	 * @return Se o server socket parou ou nao
	 */
	public boolean stop()
	{
		try
		{
			servidor.close();
			return true;
		} catch (IOException e)
		{
			// Trata a exception
			if (ControllerMain.VERBOSE)
			{
				System.out.println("SYSERROR: " + e.getMessage());
				ControllerMain.getInstance().generateLog(ControllerMain.SRV, e.getMessage());
			}
			return false;
		}
	}
	
	/**
	 * Checar o status do service web, e portanto do server socket.
	 * @return Se o server socket esta parado ou nao
	 */
	public int getStatus()
	{
		int status = ControllerMain.UNKOWN;
		try
		{
			boolean isdown = servidor.isClosed();
			if (!isdown)
			{
				status = ControllerMain.STARTED;
			} else
			{
				status = ControllerMain.STOPPED;
			}
		} catch (Exception e)
		{
			// TODO tratar exception
			e.printStackTrace();
			status = ControllerMain.UNKOWN;
		}
		return status;
	}
	
	/**
	 * Espera qualquer cliente conectar, e prepara o ambiente quando um cliente novo acessa o service web.
	 * @return Conexao de cliente recebida
	 */
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
					ControllerMain.getInstance().generateLog(ControllerMain.SRV, "Conexao aberta. (" + cliente.toString() + ")");
					System.out.println("Cliente na porta: " + cliente.getPort());
					ControllerMain.getInstance().generateLog(ControllerMain.SRV, "Cliente na porta: " + cliente.getPort());
				}
				// cria a thread dedicada para gerenciar o cliente
				Thread thread = new Thread(server);
				thread.start();
			}
		} catch (IOException e)
		{
			// Trata a exception
			if (ControllerMain.VERBOSE)
			{
				System.out.println("CRIT: " + e.getMessage());
				ControllerMain.getInstance().generateLog(ControllerMain.SRV, e.getMessage());
			}
		}
		return cliente;
	}
	
	/**
	 * Executa a thread individual de cada cliente.
	 */
	@Override
	public void run()
	{
		// Gerenciando a conexao do cliente individual
		BufferedReader in = null;
		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
		String fileRequested = null;
		String method = "";
		
		try
		{
			// cria reader para entradas de texto do cliente
			in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			// cria writer para saidas de texto
			out = new PrintWriter(cliente.getOutputStream());
			// cria writer para saidas de dados
			dataOut = new BufferedOutputStream(cliente.getOutputStream());
			
			String input = in.readLine();
			StringTokenizer parse = new StringTokenizer(input);
			method = parse.nextToken().toUpperCase();
			// separa o arquivo solicitado
			fileRequested = parse.nextToken().toLowerCase();
			String ip = cliente.getInetAddress().getHostAddress();
			
			if (ControllerMain.VERBOSE)
			{
				System.out.println("ACC: " +fileRequested+"#"+method+"#"+ip+"#"+"405");
				ControllerMain.getInstance().generateLog(ControllerMain.ACC, fileRequested+"#"+method+"#"+ip+"#"+"405");
			}
			
			// checa o metodo - suporta apenas GET and HEAD
			if (!method.equals("GET")  &&  !method.equals("HEAD"))
			{
				if (ControllerMain.VERBOSE)
				{
					System.out.println("ACC: "+fileRequested+"#"+method+"#"+ip+"#"+"405");
					ControllerMain.getInstance().generateLog(ControllerMain.ACC, fileRequested+"#"+method+"#"+ip+"#"+"405");
				}
				
				File file = new File(WEB_ROOT, NOT_SUPPORTED);
				int fileLength = (int) file.length();
				String contentMimeType = "text/html";
				byte[] fileData = lerArquivoDados(file, fileLength);
					
				// envia HTTP Headers e dados
				out.println("HTTP/1.1 405 Nao Suportado");
				out.println("Server: Servidor Java HTTP - ECP7AN-MCA1-09");
				out.println("Date: " + new Date());
				out.println("Content-type: " + contentMimeType);
				out.println("Content-length: " + fileLength);
				out.println();
				out.flush();
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
				
				if (ControllerMain.VERBOSE)
				{
					System.out.println("ACC: " + fileRequested+"#"+method+"#"+ip+"#"+"200");
					ControllerMain.getInstance().generateLog(ControllerMain.ACC, fileRequested+"#"+method+"#"+ip+"#"+"200");
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
					out.println();
					out.flush();
					
					dataOut.write(fileData, 0, fileLength);
					dataOut.flush();
				}
				
				if (ControllerMain.VERBOSE)
				{
					System.out.println("ACC: " + fileRequested+"#"+method+"#"+ip+"#"+"200");
					ControllerMain.getInstance().generateLog(ControllerMain.ACC, fileRequested+"#"+method+"#"+ip+"#"+"200");
				}
			}
			
		} catch (FileNotFoundException fnfe)
		{
			try
			{
				arquivoNaoEncontrado(out, dataOut, fileRequested, method);
			} catch (IOException ioe)
			{
				String message = ioe.getMessage();
				System.out.println("SYSError - FileNotFoundException: " + message);
				//ControllerMain.getInstance().generateLog(ControllerMain.CRIT, "SYSError - FileNotFoundException: " + message);
			}
			
		} catch (IOException ioe)
		{
			String message = ioe.getMessage();
			System.out.println("SYSError - Erro de servidor: " + message);
			//ControllerMain.getInstance().generateLog(ControllerMain.CRIT, "SYSError - Erro de servidor: " + message);
		}  catch (Exception une)
		{
			try
			{
				requisicaoNaoCompreendida(out, dataOut, fileRequested, method);
			} catch (IOException ioe)
			{
				String message = une.getMessage();
				System.out.println("SYSError - Unknown - Erro de servidor: " + message);
				//ControllerMain.getInstance().generateLog(ControllerMain.CRIT, "SYSError - Unknown - Erro de servidor: " + message);
			}
		} finally
		{
			try
			{
				in.close();
				out.close();
				dataOut.close();
				cliente.close(); // fecha a conexao do socket
			} catch (Exception e)
			{
				String message = e.getMessage();
				System.out.println("SYSError - Erro ao fechar conexao: " + message);
				//ControllerMain.getInstance().generateLog(ControllerMain.CRIT, "SYSError - Erro ao fechar conexao: " + message);
			}
			if (ControllerMain.VERBOSE)
			{
				System.out.println("Conexao fechada com sucesso.");
				//ControllerMain.getInstance().generateLog(ControllerMain.SRV, "Conexao fechada com sucesso.");
			}
		}
	}
	
	/**
	 * Le o arquivo apontado pela solicitacao nos recursos web do servidor, e os retorna para o service web.
	 * Os arquivos de recursos web estao em SocketAdmin.WEB_ROOT.
	 * 
	 * @param file : arquivo solicitado
	 * @param fileLength : tamanho do arquivo
	 * @return Arquivo solicitado
	 * @throws IOException
	 */
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
	
	/**
	 * Retorna os ContentTypes MIME Types suportados pelo service web.
	 * 
	 * @param fileRequested : arquivo solicitado
	 * @return ContentType do arquivo
	 */
	private String getContentType(String fileRequested)
	{
		if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
			return "text/html";
		else
			return "text/plain";
	}
	
	/**
	 * Processa uma requisicao de arquivo nao encontrado com erro HTML 404. 
	 * 
	 * @param out : writer de texto
	 * @param dataOut : writer de dados
	 * @param fileRequested : arquivo solicitado
	 * @param method 
	 * @throws IOException
	 */
	private void arquivoNaoEncontrado(PrintWriter out, OutputStream dataOut, String fileRequested, String method) throws IOException
	{
		File file = new File(WEB_ROOT, NOT_FOUND);
		int fileLength = (int) file.length();
		String content = "text/html";
		byte[] fileData = lerArquivoDados(file, fileLength);
		
		out.println("HTTP/1.1 404 Arquivo Nao Encontrado");
		out.println("Server: Servidor Java HTTP - ECP7AN-MCA1-09");
		out.println("Date: " + new Date());
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println();
		out.flush();
		
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
		
		String ip = cliente.getInetAddress().getHostAddress();
		
		if (ControllerMain.VERBOSE)
		{
			System.out.println("ACC: " + fileRequested+"#"+method+"#"+ip+"#"+"404");
			ControllerMain.getInstance().generateLog(ControllerMain.ACC, fileRequested+"#"+method+"#"+ip+"#"+"404");
		}
	}
	
	/**
	 * Processa uma requisicao nao compreendida com erro HTML 400. 
	 * 
	 * @param out : writer de texto
	 * @param dataOut : writer de dados
	 * @param fileRequested : arquivo solicitado
	 * @param method 
	 * @throws IOException
	 */
	private void requisicaoNaoCompreendida(PrintWriter out, OutputStream dataOut, String fileRequested, String method) throws IOException
	{
		File file = new File(WEB_ROOT, UNKNOWN);
		int fileLength = (int) file.length();
		String content = "text/html";
		byte[] fileData = lerArquivoDados(file, fileLength);
		
		out.println("HTTP/1.1 400 Requisicao Nao Compreendida");
		out.println("Server: Servidor Java HTTP - ECP7AN-MCA1-09");
		out.println("Date: " + new Date());
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println();
		out.flush();
		
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
		
		String ip = cliente.getInetAddress().getHostAddress();
		
		if (ControllerMain.VERBOSE)
		{
			System.out.println("ACC: "+fileRequested+"#"+method+"#"+ip+"#"+"400");
			ControllerMain.getInstance().generateLog(ControllerMain.ACC, fileRequested+"#"+method+"#"+ip+"#"+"400");
		}
	}
}