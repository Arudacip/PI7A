package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import controllers.sockets.SocketAdmin;
import controllers.utils.ConnectorDB;
import models.AbstractLog;
import models.LogAcc;
import models.LogSrv;
import models.factory.AbstractFactoryLog;
import models.factory.FactoryLogAcc;
import models.factory.FactoryLogSrv;
import views.ViewServiceAdmin;
import views.buttons.ButtonTypes;

/**
 * Classe em design pattern Singleton, que gera o Controller principal do servidor, em design pattern MVC.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 *
 * @param INSTANCE : [CONSTANT] instancia Singleton do Controller
 * @param PORTA_CLIENT : [CONSTANT] porta de acesso default para clientes
 * @param PORTA_SERVER : [CONSTANT] porta de acesso default para administradores
 * @param STOPPED : [CONSTANT] status do service web parado
 * @param UNKOWN : [CONSTANT] status do service web desconhecido
 * @param SRV : [CONSTANT] LogType para logs de SERVIDOR
 * @param ACC : [CONSTANT] LogType para logs de ACESSO
 * @param VERBOSE : [CONSTANT] define se os logs de sistema e servico devem ser <b>verbose</b>
 * @param PORTA : porta em uso no servidor
 * @param servidor : ponteiro para o servidor
 * 
 * @param srvlog : lista de logs de SERVIDOR
 * @param acclog : lista de logs de ACESSO
 * @param mainlog : lista de logs completa
 * 
 * @param viewSAUI : ponteiro para o View de ServiceAdmin
 */

public final class ControllerMain implements ActionListener
{

    // VARIAVEIS DE AMBIENTE
	private static final ControllerMain INSTANCE = new ControllerMain();
	private static final int PORTA_DEFAULT = 80;
    public static final int STARTED = 1;
    public static final int STOPPED = 2;
    public static final int UNKOWN = 3;
	public static final int SRV = 1;
	public static final int ACC = 2;
    public static final boolean VERBOSE = true;
    public static int PORTA;
    private SocketAdmin servidor;
	private AbstractLog currentLog;
    private Connection conn;
	
    // MODELS
    private ArrayList<AbstractLog> srvlog;
    private ArrayList<AbstractLog> acclog;
    private ArrayList<AbstractLog> mainlog;

    // VIEWS
    private static ViewServiceAdmin viewSAUI;
    
    private ControllerMain()
    {
    	Properties prop;
		try {
			// Recupera a porta de acesso ao serverweb
			prop = getProp();
			PORTA = Integer.parseInt(prop.getProperty("prop.server.porta"));
			System.out.println("SYSINFO: Porta encontrada na config: " + Integer.parseInt(prop.getProperty("prop.server.porta")));
			
			// Recupera as configs de BD e instancia a o ConnectorDB
			conn = null;
			ConnectorDB db = new ConnectorDB();
			conn = db.getConnection();
			conn.setAutoCommit(false);
		} catch (IOException ioe) {
			// Trata o erro, se ocorrer
			System.out.println("SYSERROR: " + ioe.getMessage());
			PORTA = PORTA_DEFAULT;
		} catch (SQLException sqle) {
			// Trata o erro, se ocorrer
			System.out.println("SYSERROR: " + sqle.getMessage());
		}
    }
    
    /**
     * Retorna a instancia unica do Controller principal do servidor.
     * @return Instancia unica do Controller
     */
    public static ControllerMain getInstance()
    {
    	return INSTANCE;
    }
    
    /**
     * Prepara as variaveis de ambiente necessarias para funcionamento e cria o service web HTTP no servidor.
     */
    public void createService()
    {
        // Cria os Models
    	srvlog = new ArrayList<AbstractLog>();
    	acclog = new ArrayList<AbstractLog>();
    	mainlog = new ArrayList<AbstractLog>();
    	
        AbstractFactoryLog[] factories = new AbstractFactoryLog[3];
        factories[0] = new FactoryLogSrv();
        factories[1] = new FactoryLogAcc();
        // TODO: fabricas montarem os logs recuperados no database
        //AbstractLog log = null;
		//ServiceLogSrv service = new ServiceLogSrv(conn, log);
        //for (AbstractFactoryLog fabrica : factories)
        //{
        	// AbstractLogDAO.getLogs(conn);
            //log = fabrica.retornaLogs(new Date(System.currentTimeMillis()), "Aberto.");
            //mainlog.add(log);
        //}
        servidor = new SocketAdmin();
        System.out.println(servidor.toString());
    }
    
    /**
     * Constroi as Views, em design pattern MVC.
     */
    public void createView()
    {
        viewSAUI = new ViewServiceAdmin();
        viewSAUI.setVisible(true);
    }
    
    /**
     * Inicializar o service web em uma Thread, separadamente da View.
     */
    private void startService()
    {
    	// Usa uma Thread, para nao travar a view
    	new Thread()
    	{
            @Override
            public void run()
            {
            	generateLog(SRV, "Tentando iniciar o servidor...");
                try
                {
                	servidor.start(PORTA);
                	generateLog(SRV, "Servidor iniciado!");
                	// tem que ser checado aqui caso contrario da null pointer exception
                    checkStatus();
                } catch (IOException ex) {
                    ex.printStackTrace();
                	generateLog(SRV, "Falha ao iniciar do servidor!");
                	// tem que ser checado aqui caso contrario da null pointer exception
                    checkStatus();
                }
                servidor.esperarCliente();
            }
        }.start();
    }
    
    /**
     * Parar o service web.
     */
    private void stopService()
    {
        generateLog(SRV, "Tentando parar o servidor...");
        boolean sucesso = servidor.stop();
        if (sucesso)
        {
        	generateLog(SRV, "Servidor parado!");
        	// tem que ser checado aqui caso contrario da null pointer exception
            checkStatus();
        } else
        {
        	generateLog(SRV, "Falha na parada do servidor!");
        	// tem que ser checado aqui caso contrario da null pointer exception
            checkStatus();
        }
    }
    
    /**
     * Reiniciar o service web.
     */
    private void restartService()
    {
    	generateLog(SRV, "Reiniciando o servidor.");
        stopService();
        startService();
    }
    
    /**
     * Verificar o status do service web.
     */
    private void checkStatus()
    {
    	int valor = servidor.getStatus();
    	switch (valor)
    	{
    	case ControllerMain.STARTED:
    		viewSAUI.setStatus(ControllerMain.STARTED);
    		break;
    	case ControllerMain.STOPPED:
            viewSAUI.setStatus(ControllerMain.STOPPED);
    		break;
    	case ControllerMain.UNKOWN:
            viewSAUI.setStatus(ControllerMain.UNKOWN);
    		break;
    	default:
            viewSAUI.setStatus(ControllerMain.UNKOWN);
    		break;
    	}
    }
    
    /**
     * Recebe os eventos da View e executa as acoes necessarias de acordo com o caso.
     */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Iniciar
        if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Start))
        {
            startService();
        }
        // Reiniciar
        else if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Restart))
        {
        	restartService();
        }
        // Parar
        else if (e.getSource() == viewSAUI.getIteractions().getButton(ButtonTypes.Stop))
        {
            stopService();
        }
	}
	
	/**
	 * Gera os objetos AbstractLog do service web, de acordo com os tipos e parametros necessarios.
	 * 
	 * @param tipo : tipo de AbstractLog
	 * @param texto : mensagem do log
	 */
	public void generateLog(int tipo, String texto)
	{
		switch(tipo)
		{
		case SRV:
			currentLog = new LogSrv(new Date(System.currentTimeMillis()), texto);
			srvlog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			//if (ControllerMain.VERBOSE)
			//{
			//	System.out.println("SERVER: "+currentLog.imprime());
			//}
			break;
			
		case ACC:
			currentLog = new LogAcc(new Date(System.currentTimeMillis()), texto);
			acclog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			//if (ControllerMain.VERBOSE)
			//{
			//	System.out.println("ACCESS: "+currentLog.imprime());
			//}
			break;
			
		default:
			currentLog = new LogSrv(new Date(System.currentTimeMillis()), "Erro desconhecido.");
			srvlog.add(currentLog);
			mainlog.add(currentLog);
			viewSAUI.addLog(currentLog);
			//if (ControllerMain.VERBOSE)
			//{
			//	System.out.println("SERVER: "+currentLog.imprime());
			//}
			break;
		}
	}

	/**
	 * Retorna as propriedades definidas no arquivo de configuracao do webserver.
	 * @return Propriedades do webserver
	 * @throws IOException
	 */
	public static Properties getProp() throws IOException
	{
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("./info.properties");
        props.load(file);
        return props;
    }
}
