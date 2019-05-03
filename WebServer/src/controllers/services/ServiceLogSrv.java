package controllers.services;

import java.sql.Connection;
import java.util.ArrayList;

import models.AbstractLog;
import models.LogSrv;
import models.dao.DAOLogSrv;

/**
 * Classe do Controller Service a tratar do LogSrv.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param dao : DAO do log
 * @param conn : conexao com o banco de dados
 */

public class ServiceLogSrv
{
	
	private DAOLogSrv dao = new DAOLogSrv();
	private Connection conn = null;
	
	/**
	 * Construtor exclusivo para referencia da conexao com o banco de dados e registro do LogSrv.
	 * @param conn : conexao com o banco de dados
	 * @param log : objeto do LogSrv
	 */
	public ServiceLogSrv(Connection conn, LogSrv log)
	{
		dao.setLog(log);
	}
	
	/**
	 * Inclui um LogSrv no banco de dados.
	 */
	public void incluir()
	{
		dao.incluir(conn);
	}
	
	/**
	 * Recupera a lista de LogSrv do banco de dados.
	 * @return lista de LogSrv
	 */
	public ArrayList<AbstractLog> carregar()
	{
		return dao.carregar(conn);
	}
	
}