package controllers.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import views.ViewServiceAdmin;

public class SocketAdmin
{
	
	// TODO: Javadoc
	
	private ServerSocket servidor;
	private Socket cliente;
	
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
	
	public Socket getCliente()
	{
		cliente = null;
		try
		{
			cliente = servidor.accept();
			System.out.println("Cliente na porta: " + cliente.getPort());
		} catch (IOException e)
		{
			// TODO tratar exception
			e.printStackTrace();
		}
		return cliente;
	}
}
