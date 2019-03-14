package controllers.utils;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.JFrame;

public class ComponentMapper
{
    
    /**
     * Classe da View de ServiceAdmin do design pattern MVC.
     *
     * 
     * @param componentMap: mapa de componentes da view
     * @method createComponentMap: constroi o mapa de componentes
     * @method getComponentByName: retorna o componente por nome
     */
    
    @SuppressWarnings("rawtypes")
	private static HashMap componentMap;
    
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
    
    public static Component getComponentByName(String name)
    {
        if (componentMap.containsKey(name))
        {
            return (Component) componentMap.get(name);
        }
        else return null;
    }
    
}
