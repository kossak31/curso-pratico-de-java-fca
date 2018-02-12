/**
 * Write a description of class ListarContactos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ListSelectionModel;

public class ListarContactos extends JFrame {

    //Declara e cria os componentes   
    JLabel jlTitulo = new JLabel ("Contactos");
    DefaultTableModel tmContactos = new DefaultTableModel (null, new String[]{"Nome", "Telefone", "Telemóvel", "E-mail"});
    JTable jtContactos = new JTable(tmContactos);
    JScrollPane jspContactos = new JScrollPane(jtContactos);
    
    //Construtor
    public ListarContactos() {    
        
        //Define as propriedades da janela
        setTitle("Listagem de contactos");
        setSize(500,200);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        jspContactos.setPreferredSize(new Dimension(475,125));
        TableColumnModel tcm = jtContactos.getColumnModel();
        TableColumn tc0 = tcm.getColumn(0);
        //Define a largura de cada coluna (100+100+100+175=475 que corresponde à largura do JScrollpane)
        tc0.setMaxWidth(100);
        TableColumn tc1 = tcm.getColumn(1);
        tc1.setMaxWidth(100);
        TableColumn tc2 = tcm.getColumn(2);
        tc2.setMaxWidth(100);
        TableColumn tc3 = tcm.getColumn(3);
        tc3.setMaxWidth(175);
        //Impede que a seleção de mais do que uma linha da tabela em simultâneo
        jtContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        
        //Adiciona os componentes à janela
        add(jlTitulo);
        add(jspContactos);
        
        try {
            Statement stmt;
            ResultSet rs;
            String sqlListaContactos = "SELECT * FROM contactos";
            int i = 0;
            String [] campos = new String[] {null, null, null, null};
            LigacaoBD ligacaoBD = new LigacaoBD();
            Connection con = ligacaoBD.obterLigacao();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlListaContactos);
            while (rs.next()) {
                //Adiciona uma linha vazia à tabela
                tmContactos.addRow(campos);
                //Preenche as células da linha vazia. A numeração das colunas começa em 0
                tmContactos.setValueAt(rs.getString("nome"),i,0);
                tmContactos.setValueAt(rs.getInt("telefone"),i,1);
                tmContactos.setValueAt(rs.getInt("telemovel"),i,2);
                tmContactos.setValueAt(rs.getString("email"),i,3);
                i++;
            }
            ligacaoBD.fecharLigacao(con);
        }
        catch(SQLException sqle) {
            System.out.println("Não foi possível efetuar a operação sobre a BD!");
            sqle.printStackTrace();
        }
    }
}