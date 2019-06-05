package models.utils;

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
	public Connection getConnection(Properties prop) throws SQLException, IOException, InterruptedException 
	{
		// Define valores default STUB
		String jdbc = "mariadb";
		String address = "127.0.0.1";
		String dbporta = "3306";
		String database = "pi7a";
		String user = "webserverpi";
		String password = "a2A4V1s5Fz";
		if (ControllerMain.DEBUG)
		{
			System.out.println("jdbc:"+jdbc+"://"+address+":"+dbporta+"/"+database+"?user="+user+"&"+"password="+password);
		}
		// Ler propriedades do arquivo de conf
		jdbc = prop.getProperty("prop.server.jdbc");
		address = prop.getProperty("prop.server.address");
		dbporta = prop.getProperty("prop.server.dbporta");
		database = prop.getProperty("prop.server.database");
		user = prop.getProperty("prop.server.user");
		password = prop.getProperty("prop.server.password");
		// PARA DEBUG
		
		/*jdbc = "mysql";
		address = "127.0.0.1";
		dbporta = "3306";
		database = "mx5nzdqvcg";
		user = "root";
		password = "a2A4V1s5Fz";*/
		
		if (ControllerMain.DEBUG)
		{
			System.out.println("jdbc:"+jdbc+"://"+address+":"+dbporta+"/"+database+"?user="+user+"&"+"password="+password);
		}
		Connection conn = null;
		
		boolean connected = false;
		int errors = 0;
		while(!connected) // Espera por conexao com o DB
		{
		    try
		    {
		    	conn = DriverManager.getConnection("jdbc:"+jdbc+"://"+address+":"+dbporta+"/"+database+"?user="+user+"&"+"password="+password);
		        if (conn != null)
		        {
		        	connected = true;
		        } else
		        {
			        System.out.println("Timeout 01");
		        	// Timeout. Tente novamente.
		        }
		    } catch(Exception e)
		    {
		        if (errors == 0)
		        {
		        	// Exception. Tente novamente.
		        }
		        ++errors;
		        System.out.println("Timeout 02");
		        Thread.sleep(1000);
		    }
		}
		return conn;
	}
}
