package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import models.AbstractLog;
import zones.models.deprecated.LogInfo;

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
	public void incluir(Connection conn) {
		String sqlInsert = "INSERT INTO LogServidor(hora_data, acao) VALUES (?, ?)";
		
		// inclui o log na base
		try (PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			stm.setLong(1, log.getData().getTime());
			stm.setString(2, log.getText());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
			}
		}
	}
	
	/**
	 * Busca os logs de servidor
	 * @param conn : conexao com o DB
	 */
	public ArrayList<AbstractLog> carregar(Connection conn) {
		ArrayList<AbstractLog> logs = new ArrayList<AbstractLog>();
		AbstractLog currentlog;
		String sqlSelect = "SELECT * FROM LogServidor";
		//String sqlSelect = "SELECT * FROM LogServidor WHERE LogServidor.id = ?";
		
		try (PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			//stm.setInt(1, log.getID());
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					currentlog = new LogInfo(new Date(rs.getLong("hora_data")), rs.getString("acao"));
					logs.add(currentlog);
				}
			} catch (SQLException e) {
				System.out.print(e.getStackTrace());
			}
		} catch (Exception e1) {
			System.out.print(e1.getStackTrace());
		}
		return logs;
	}
}
