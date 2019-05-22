package models.services;

import java.sql.Connection;
import java.util.ArrayList;

import models.AbstractLog;
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
	 * Construtor exclusivo para referencia da conexao com o banco de dados e recuperar logs.
	 * @param conn : conexao com o banco de dados
	 */
	public ServiceLogAcc(Connection conn)
	{
		this.conn = conn;
	}
	
	/**
	 * Inclui um LogAcc no banco de dados.
	 */
	public void incluir(AbstractLog log)
	{
		dao.setLog(log);
		dao.incluir(conn);
	}
	
	/**
	 * Recupera um LogAcc do banco de dados por ID.
	 * @return lista de LogAcc
	 */
	public AbstractLog carregaID()
	{
		return dao.carregaID(conn);
	}
	
	/**
	 * Recupera a lista dos ultimos X LogAcc do banco de dados.
	 * @return lista de LogAcc
	 */
	public ArrayList<AbstractLog> listaUltimos(int num)
	{
		return dao.listaUltimos(conn, num);
	}
	
	/**
	 * Recupera a lista de todos os LogAcc do banco de dados.
	 * @return lista de LogAcc
	 */
	public ArrayList<AbstractLog> listaTodos()
	{
		return dao.listaTodos(conn);
	}
}