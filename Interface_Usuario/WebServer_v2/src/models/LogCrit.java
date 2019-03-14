package models;

public class LogCrit extends AbstractLog
{
    
    /**
     * Classe do Model de LogCrit do design pattern MVC + Abstract Factory.
     *
     * @param prefix: prefixo do log
     * @method imprime: retorna o texto do log
     */
    
    private String prefix = "CRITICAL: ";

    @Override
    public String imprime()
    {
        String message = getData().toString() + prefix + getText();
        return message;
    }

}
