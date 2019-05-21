package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.ControllerMain;
import models.AbstractLog;
import models.LogSrv;

/**
 * Classe do Model DAO do LogSrv do design pattern MVC + Abstract Factory.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param log: log de servidor a registrar
 */

public class DAOLogSrv
{
	private AbstractLog log;
	
	/**
	 * Fornece o log de servidor.
	 * @param log : log de servidor a registrar
	 */
	public void setLog(AbstractLog log)
	{
		this.log = log;
	}
	
	/**
	 * Incluir no DB o log de servidor
	 * @param conn : conexao com o DB
	 */
	public void incluir(Connection conn)
	{
		String sqlInsert = "INSERT INTO logservidor(hora_data, acao) VALUES (?, ?)";
		
		// inclui o log na base
		try (PreparedStatement stm = conn.prepareStatement(sqlInsert);)
		{
			// Converte o valor de data SQL Timestamp
			java.util.Date date = log.getData();
			java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());
			// Define valores no stm
			stm.setTimestamp(1, sqlTime);
			stm.setString(2, log.getText());
			stm.execute();
		} catch (SQLException e1)
		{
			//System.out.println(e1.getMessage() + " - " + e1.getSuppressed());
			try
			{
				conn.rollback();
			} catch (SQLException e2)
			{
				if (ControllerMain.DEBUG)
				{
					System.out.print(e2.getMessage());
				}
			}
		} catch (Exception e)
		{
			if (ControllerMain.DEBUG)
			{
				System.out.println(e.getMessage());
			}
			try
			{
				conn.rollback();
			} catch (SQLException e1)
			{
				if (ControllerMain.DEBUG)
				{
					System.out.print(e1.getMessage());
				}
			}
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			if (ControllerMain.DEBUG)
			{
				System.out.println("SYSERROR: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Carrega um log de servidor a partir do ID
	 * @param conn : conexao com o DB
	 * @return log solicitado.
	 */
	public AbstractLog carregaID(Connection conn)
	{
		String sqlSelect = "SELECT * FROM logservidor WHERE logservidor.id = ?";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			stm.setInt(1, log.getID());
			try (ResultSet rs = stm.executeQuery();)
			{
				if (rs.next())
				{
					log = new LogSrv(rs.getTimestamp("hora_data"), rs.getString("acao"));
				}
			} catch (SQLException e)
			{
				if (ControllerMain.DEBUG)
				{
					System.out.print(e.getStackTrace());
				}
			}
		} catch (Exception e1)
		{
			if (ControllerMain.DEBUG)
			{
				System.out.print(e1.getStackTrace());
			}
		}
		return log;
	}
	
	/**
	 * Lista os ultimos logs de servidor
	 * @param conn : conexao com o DB
	 * @param num : numero de logs a listar
	 * @return resultado da querie em ArrayList.
	 */
	public ArrayList<AbstractLog> listaUltimos(Connection conn, int num)
	{
		ArrayList<AbstractLog> logs = new ArrayList<AbstractLog>();
		AbstractLog currentlog;
		String sqlSelect = "SELECT * FROM (SELECT * FROM logservidor ORDER BY id DESC LIMIT "+num+") sub ORDER BY id ASC;";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			try (ResultSet rs = stm.executeQuery();)
			{
				while (rs.next())
				{
					currentlog = new LogSrv(rs.getTimestamp("hora_data"), rs.getString("acao"));
					logs.add(currentlog);
				}
			} catch (SQLException e)
			{
				if (ControllerMain.DEBUG)
				{
					System.out.print(e.getMessage());
				}
			}
		} catch (Exception e1)
		{
			if (ControllerMain.DEBUG)
			{
				System.out.print(e1.getMessage());
			}
		}
		return logs;
	}
	
	/**
	 * Lista todos logs de servidor registrados no DB
	 * @param conn : conexao com o DB
	 * @return resultado da querie em ArrayList.
	 */
	public ArrayList<AbstractLog> listaTodos(Connection conn)
	{
		ArrayList<AbstractLog> logs = new ArrayList<AbstractLog>();
		AbstractLog currentlog;
		String sqlSelect = "SELECT * FROM logservidor;";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			try (ResultSet rs = stm.executeQuery();)
			{
				while (rs.next()) {
					currentlog = new LogSrv(rs.getTimestamp("hora_data"), rs.getString("acao"));
					logs.add(currentlog);
				}
			} catch (SQLException e) {
				if (ControllerMain.DEBUG)
				{
					System.out.print(e.getMessage());
				}
			}
		} catch (Exception e1) {
			if (ControllerMain.DEBUG)
			{
				System.out.print(e1.getMessage());
			}
		}
		return logs;
	}
}
