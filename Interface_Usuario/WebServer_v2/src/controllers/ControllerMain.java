package controllers;

import java.util.ArrayList;
import java.util.Date;

import models.AbstractFactoryLog;
import models.AbstractLog;
import models.FactoryLogCrit;
import models.FactoryLogInfo;
import models.FactoryLogWarn;
import views.ViewServiceAdmin;

public class ControllerMain
{

    /**
     * Classe do Controller principal do design pattern MVC.
     *
     * @param infolog: logs de informacao
     * @param warnlog: logs de warning
     * @param critlog: logs de critical
     * @param mainlog: logs completos
     * @param viewSAUI: View do Service Admin
     * @method main: inicia o app
     */
    
    // Models
    private static ArrayList<AbstractLog> infolog = new ArrayList<AbstractLog>();
    private static ArrayList<AbstractLog> warnlog = new ArrayList<AbstractLog>();
    private static ArrayList<AbstractLog> critlog = new ArrayList<AbstractLog>();
    private static ArrayList<AbstractLog> mainlog = new ArrayList<AbstractLog>();

    // Views
    private static ViewServiceAdmin viewSAUI;

    public static void main(String args[])
    {
        // Cria Models
        AbstractFactoryLog[] factories = new AbstractFactoryLog[3];
        factories[0] = new FactoryLogInfo();
        factories[1] = new FactoryLogWarn();
        factories[2] = new FactoryLogCrit();
        AbstractLog log = null;
        for (AbstractFactoryLog fabrica : factories)
        {
            // fabricas montam os logs recuperados no database
            // AbstractLogDAO.getLogs(conn);
            log = fabrica.retornaLogs(new Date(System.currentTimeMillis()), "Aberto.");
            mainlog.add(log);
        }
        
        // Cria Views
        viewSAUI = new ViewServiceAdmin(infolog, warnlog, critlog, mainlog);
        viewSAUI.setVisible(true);
    }
}
