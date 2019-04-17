package controllers.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaria de conexao com o Banco de Dados. Atualmente funcional para apenas BDs MySQL.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 * 
 * @param DRIVER : Driver utilizado no Banco de Dados
 */

public class ConnectorDB
{
	private final static String DRIVER = "com.mysql.jdbc.Driver"; 
	
	static
	{
		try
		{
			Class.forName(DRIVER);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Cria a conexao com o banco de dados, conforme os parametros necessarios, que devem ser informados ao utilitario de acordo com
	 * as necesidades do projeto.
	 * 
	 * @param jdbc : JDBC utilizado no BD
	 * @param server : endereco IP ou DNS do servidor de Banco de Dados
	 * @param database : nome do BD a ser usado
	 * @param user : usuario para conectar ao BD
	 * @param password : senha para conectar ao BD
	 * @return Conexao criada e funcional para o Banco de Dados do servidor
	 * @throws SQLException
	 */
	public Connection getConnection(String jdbc, String server, String database, String user, String password) throws SQLException {
		return DriverManager.getConnection("jdbc:"+jdbc+"://"+server+"/"+database+"?user="+user+"&"+"password="+password);
	}
}
