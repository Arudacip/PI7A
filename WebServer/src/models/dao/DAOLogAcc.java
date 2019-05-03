package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import models.AbstractLog;
import models.LogAcc;

/**
 * Classe do Model DAO do LogAcc do design pattern MVC + Abstract Factory.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param log: log de acesso a registrar
 */

public class DAOLogAcc
{
	
	private AbstractLog log;
	
	/**
	 * Fornece o log de acesso.
	 * @param log : log de acesso a registrar
	 */
	public void setLog(AbstractLog log)
	{
		this.log = log;
	}
	
	/**
	 * Incluir no DB o log de acesso
	 * @param conn : conexao com o DB
	 */
	public void incluir(Connection conn)
	{
		String sqlInsert = "INSERT INTO LogAcesso(hora_data, arquivo, metodo_http, ip, codigo_resposta) VALUES (?, ?, ?, ?, ?)";
		
		LogAcc currentlog = (LogAcc) log;
		// inclui o log na base
		try (PreparedStatement stm = conn.prepareStatement(sqlInsert);)
		{
			stm.setLong(1, currentlog.getData().getTime());
			stm.setString(2, currentlog.getArquivo());
			stm.setString(3, currentlog.getMethod());
			stm.setString(4, currentlog.getIP());
			stm.setString(5, currentlog.getCode());
			stm.execute();
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				conn.rollback();
			} catch (SQLException e1)
			{
				System.out.print(e1.getStackTrace());
			}
		}
	}
	
	/**
	 * Busca os logs de acesso
	 * @param conn : conexao com o DB
	 */
	public ArrayList<AbstractLog> carregar(Connection conn)
	{
		ArrayList<AbstractLog> logs = new ArrayList<AbstractLog>();
		LogAcc currentlog;
		String sqlSelect = "SELECT * FROM LogAcesso";
		//String sqlSelect = "SELECT * FROM LogAcesso WHERE LogServidor.id = ?";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			//stm.setInt(1, log.getID());
			try (ResultSet rs = stm.executeQuery();)
			{
				if (rs.next())
				{
					currentlog = new LogAcc(new Date(rs.getLong("hora_data")), rs.getString("arquivo")+"#"
													+rs.getString("metodo_http")+"#"+rs.getString("ip")+"#"
													+rs.getString("codigo_resposta"));
					logs.add(currentlog);
				}
			} catch (SQLException e)
			{
				System.out.print(e.getStackTrace());
			}
		} catch (Exception e1)
		{
			System.out.print(e1.getStackTrace());
		}
		return logs;
	}
}
