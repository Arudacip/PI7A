package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.ControllerMain;
import models.AbstractLog;
import models.LogAcc;
import models.ResultTable;

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
		String sqlInsert = "INSERT INTO logacesso(hora_data, arquivo, metodo_http, ip, codigo_resposta) VALUES (?, ?, ?, ?, ?)";
		
		LogAcc currentlog = (LogAcc) log;
		// inclui o log na base
		try (PreparedStatement stm = conn.prepareStatement(sqlInsert);)
		{
			// Converte o valor de data SQL Timestamp
			java.util.Date date = log.getData();
			java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
			// Define valores no stm
			stm.setTimestamp(1, sqlTime);
			stm.setString(2, currentlog.getArquivo());
			stm.setString(3, currentlog.getMethod());
			stm.setString(4, currentlog.getIP());
			stm.setString(5, currentlog.getCode());
			stm.execute();
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
	 * Lista os ultimos logs de acesso
	 * @param conn : conexao com o DB
	 * @return log solicitado.
	 */
	public AbstractLog carregaID(Connection conn)
	{
		String sqlSelect = "SELECT * FROM logacesso WHERE logacesso.id = ?";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			stm.setInt(1, log.getID());
			try (ResultSet rs = stm.executeQuery();)
			{
				if (rs.next())
				{
					log = new LogAcc(rs.getTimestamp("hora_data"), rs.getString("arquivo")+"#"
													+rs.getString("metodo_http")+"#"+rs.getString("ip")+"#"
													+rs.getString("codigo_resposta"));
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
	 * Lista os ultimos logs de acesso
	 * @param conn : conexao com o DB
	 * @param num : numero de logs a listar
	 * @return resultado da querie em ArrayList.
	 */
	public ArrayList<AbstractLog> listaUltimos(Connection conn, int num)
	{
		ArrayList<AbstractLog> logs = new ArrayList<AbstractLog>();
		LogAcc currentlog;
		String sqlSelect = "SELECT * FROM (SELECT * FROM logacesso ORDER BY id DESC LIMIT "+num+") sub ORDER BY id ASC;";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			try (ResultSet rs = stm.executeQuery();)
			{
				while (rs.next())
				{
					currentlog = new LogAcc(rs.getTimestamp("hora_data"), rs.getString("arquivo")+"#"
													+rs.getString("metodo_http")+"#"+rs.getString("ip")+"#"
													+rs.getString("codigo_resposta"));
					logs.add(currentlog);
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
		return logs;
	}
	
	/**
	 * Lista os arquivos mais acessados no servidor
	 * @param conn : conexao com o DB
	 * @param num : numero de logs a listar
	 * @return resultado da querie em ArrayList.
	 */
	public ArrayList<ResultTable> listaMaisAcessados(Connection conn, int num)
	{
		ArrayList<ResultTable> resultados = new ArrayList<ResultTable>();
		ResultTable currentresult;
		String sqlSelect = "SELECT arquivo, count(arquivo) AS ocorrencias FROM logacesso GROUP BY arquivo ORDER BY ocorrencias DESC LIMIT "+num+";";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			try (ResultSet rs = stm.executeQuery();)
			{
				while (rs.next())
				{
					currentresult = new ResultTable(rs.getString("arquivo"), rs.getString("ocorrencias"));
					resultados.add(currentresult);
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
		return resultados;
	}
	
	/**
	 * Lista os arquivos mais acessados no servidor
	 * @param conn : conexao com o DB
	 * @param num : numero de logs a listar
	 * @return resultado da querie em ArrayList.
	 */
	public ArrayList<ResultTable> listaIPsFrequentes(Connection conn, int num)
	{
		ArrayList<ResultTable> resultados = new ArrayList<ResultTable>();
		ResultTable currentresult;
		String sqlSelect = "SELECT ip, count(ip) AS ocorrencias FROM logacesso GROUP BY ip ORDER BY ocorrencias DESC LIMIT "+num+";";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			try (ResultSet rs = stm.executeQuery();)
			{
				while (rs.next())
				{
					currentresult = new ResultTable(rs.getString("ip"), rs.getString("ocorrencias"));
					resultados.add(currentresult);
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
		return resultados;
	}
	
	/**
	 * Lista todos os logs de acesso registrados no DB
	 * @param conn : conexao com o DB
	 * @return resultado da querie em ArrayList.
	 */
	public ArrayList<AbstractLog> listaTodos(Connection conn)
	{
		ArrayList<AbstractLog> logs = new ArrayList<AbstractLog>();
		LogAcc currentlog;
		String sqlSelect = "SELECT * FROM logacesso;";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);)
		{
			try (ResultSet rs = stm.executeQuery();)
			{
				while (rs.next())
				{
					currentlog = new LogAcc(rs.getTimestamp("hora_data"), rs.getString("arquivo")+"#"
													+rs.getString("metodo_http")+"#"+rs.getString("ip")+"#"
													+rs.getString("codigo_resposta"));
					logs.add(currentlog);
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
		return logs;
	}
}