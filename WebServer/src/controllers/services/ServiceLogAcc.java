package controllers.services;

import java.sql.Connection;
import java.util.ArrayList;

import models.AbstractLog;
import models.LogAcc;
import models.dao.DAOLogAcc;

/**
 * Classe do Controller Service a tratar do LogAcc.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param dao : DAO do log
 * @param conn : conexao com o banco de dados
 */

public class ServiceLogAcc
{
	
	private DAOLogAcc dao = new DAOLogAcc();
	private Connection conn = null;
	
	/**
	 * Construtor exclusivo para referencia da conexao com o banco de dados e registro do LogAcc.
	 * @param conn : conexao com o banco de dados
	 * @param log : objeto do LogAcc
	 */
	public ServiceLogAcc(Connection conn, LogAcc log)
	{
		dao.setLog(log);
	}
	
	/**
	 * Inclui um LogAcc no banco de dados.
	 */
	public void incluir()
	{
		dao.incluir(conn);
	}
	
	/**
	 * Recupera a lista de LogAcc do banco de dados.
	 * @return lista de LogAcc
	 */
	public ArrayList<AbstractLog> carregar()
	{
		return dao.carregar(conn);
	}
	
}