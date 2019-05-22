package models.utils;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 * Classe utilitaria de mapeamento dos componentes visuais das Views do servidor.
 * @author Grupo ECP7AN-MCA1-09 - Bruno Gama, Guilherme Sant'Clair, Luis Felipe, Rafael Cassiolato, Raiza Morata.
 * 
 * @param componentMap : mapa de componentes do servidor
 */

public class ComponentMapper
{
    
    @SuppressWarnings("rawtypes")
	private static HashMap componentMap;
    
    /**
     * Constroi o mapa de componentes.
     * 
     * @param ui : interface apresentada na View
     */
    @SuppressWarnings("unchecked")
    public static void createComponentMap(JFrame ui)
    {
        componentMap = new HashMap<String,Component>();
        Component[] components = ui.getContentPane().getComponents();
        for (Component component : components)
        {
            componentMap.put(component.getName(), component);
        }
    }
    
    /**
     * Retorna qualquer componente visual de qualquer View que utilize o ComponentMapper, de acordo com o nome fornecido.
     * 
     * @param name : nome do componente, como definido nos objetos de cada um durante criacao
     * @return O componente visual da interface, de acordo com o nome fornecido
     */
    public static Component getComponentByName(String name)
    {
        if (componentMap.containsKey(name))
        {
            return (Component) componentMap.get(name);
        }
        else return null;
    }
    
}
