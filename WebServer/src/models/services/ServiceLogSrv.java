package models.services;

import java.sql.Connection;
import java.util.ArrayList;

import models.AbstractLog;
import models.ResultTable;
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
	 * Construtor exclusivo para referencia da conexao com o banco de dados e recuperar logs.
	 * @param conn : conexao com o banco de dados
	 */
	public ServiceLogSrv(Connection conn)
	{
		this.conn = conn;
	}
	
	/**
	 * Inclui um LogSrv no banco de dados.
	 */
	public void incluir(AbstractLog log)
	{
		dao.setLog(log);
		dao.incluir(conn);
	}
	
	/**
	 * Recupera um LogSrv do banco de dados por ID.
	 * @return lista de LogSrv
	 */
	public AbstractLog carregaID()
	{
		return dao.carregaID(conn);
	}
	
	/**
	 * Recupera a lista dos ultimos X LogSrv do banco de dados.
	 * @return lista de LogSrv
	 */
	public ArrayList<AbstractLog> listaUltimos(int num)
	{
		return dao.listaUltimos(conn, num);
	}
	
	/**
	 * Recupera a lista dos ultimos X LogSrv do banco de dados.
	 * @return lista de LogSrv
	 */
	public ArrayList<ResultTable> contaUltimos(int num)
	{
		return dao.contaUltimos(conn, num);
	}
	
	/**
	 * Recupera a lista de todos os LogSrv do banco de dados.
	 * @return lista de LogSrv
	 */
	public ArrayList<AbstractLog> listaTodos()
	{
		return dao.listaTodos(conn);
	}
}