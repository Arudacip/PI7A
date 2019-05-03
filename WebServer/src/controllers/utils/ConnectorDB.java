package controllers.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import controllers.ControllerMain;

/**
 * Classe utilitaria de conexao com o Banco de Dados. Atualmente funcional para apenas BDs MySQL.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 * 
 * @param DRIVER : Driver utilizado no Banco de Dados
 */

public class ConnectorDB
{
	private final static String DRIVER; 
	
	static
	{
		try
		{
			Properties prop = ControllerMain.getProp();
			DRIVER = prop.getProperty("prop.server.driver");
			Class.forName(DRIVER);
		}
		catch (ClassNotFoundException | IOException e) {
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
	 * @throws IOException 
	 */
	public Connection getConnection() throws SQLException, IOException {
		String jdbc, address, database, user, password;
		Properties prop = ControllerMain.getProp();
		jdbc = prop.getProperty("prop.server.jdbc");
		address = prop.getProperty("prop.server.address");
		database = prop.getProperty("prop.server.database");
		user = prop.getProperty("prop.server.jdbc");
		password = prop.getProperty("prop.server.password");
		return DriverManager.getConnection("jdbc:"+jdbc+"://"+address+"/"+database+"?user="+user+"&"+"password="+password);
	}
}
