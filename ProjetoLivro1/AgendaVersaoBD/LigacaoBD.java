/**
 * Write a description of class LigacaoBD here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LigacaoBD {
    
    public Connection obterLigacao() {

        System.out.println("Teste ao acesso a uma BD MySQL\n");
        Connection con = null;
        try {
            //Indica que será utilizado o driver Connector/J
            Class.forName("com.mysql.jdbc.Driver");
            
            /* Liga ao servidor de BD local, com o utilizador "root"
               e sem password, acedendo à BD "bd1" */
            con = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "1234");
            System.out.println("Ligação efectuada com sucesso\n");
            
        }
        //Trata a excepção lançada pelo método forName da classe Class
        catch(ClassNotFoundException cnfe) {
            System.out.println("ClassNotFoundException");
        }
        //Trata a excepção lançada pelo método getConnection da classe DriverManager 
        catch(SQLException sqle) {
            System.out.println("SQLException");
        }
        return con;
    }
    
    public void fecharLigacao (Connection con) {
        
        try {
            con.close();
            System.out.println("\nLigação fechada com sucesso\n");
        }    
        catch(SQLException sqle) {
            System.out.println("SQLException");
        }
    }
}