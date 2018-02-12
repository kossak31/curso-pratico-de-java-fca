/**
 * Write a description of class PesquisaCliente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class PesquisaCliente extends JFrame {

    //Declara e cria os componentes
    JLabel jlCliente = new JLabel ("Cliente");
    JTextField jtfCliente = new JTextField(20);
    JButton jbPesquisar = new JButton("Pesquisar");
    DefaultTableModel tmClientes = new DefaultTableModel (null, new String[]{"Nº", "Nome", "Doc. Id.", "C. condução", "Contacto"});
    JTable jtClientes = new JTable(tmClientes);
    JScrollPane jspClientes = new JScrollPane(jtClientes);
    JButton jbAlterar = new JButton("Alterar");
    JButton jbEliminar= new JButton("Eliminar");
    
    //Construtor
    public PesquisaCliente() {    
        
        //Define as porpriedades da janela
        setTitle("Cliente");
        setSize(530,310);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        //Define o tamanho dos componentes
        jtfCliente.setPreferredSize(new Dimension(45,25));
        jspClientes.setPreferredSize(new Dimension(500,200));
                
        //Adiciona os componentes à janela
        add(jlCliente);
        add(jtfCliente);
        add(jbPesquisar);
        add(jspClientes);
        add(jbAlterar);
        add(jbEliminar);
        
        /*Registo do listener ActionListener junto dos botões.
        Quando for gerado um evento por estes componentes, é
        criada uma instância da classe EventoJBPesquisar, EventoJBAlterar
        ou EventoJBEliminar, onde está o código que deve ser executado quando
        tal acontece*/
        jbPesquisar.addActionListener(new EventoJBPesquisar());
        jbAlterar.addActionListener(new EventoJBAlterar());
        jbEliminar.addActionListener(new EventoJBEliminar());
        
        //Limpa os dados que possam ter ficado desde a última utilização da janela
        jtfCliente.setText("");
        removeLinhasTabela(); //Método implementado em baixo
    }
    
    public static void main(String[] args) {
        new PesquisaCliente();
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbPesquisar
    private class EventoJBPesquisar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            
            //Invoca o método implementado em baixo
            removeLinhasTabela();
            boolean encontrouNome = false;
            if (jtfCliente.getText().equals(""))
                JOptionPane.showMessageDialog(null, "Preencha o campo cliente!");
            else {
                try {
                    PreparedStatement pstmt;
                    ResultSet rs;
                    String sqlPesquisaCliente = "SELECT * FROM clientes WHERE nome LIKE ?";                   
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlPesquisaCliente);
                    pstmt.setString(1, '%' + jtfCliente.getText() + '%');
                    rs = pstmt.executeQuery();
                    int i=0;
                    String[] campos = new String[] {null, null, null, null, null};
                    while (rs.next()) {
                        encontrouNome = true;
                        tmClientes.addRow(campos);
                        tmClientes.setValueAt(rs.getInt("num_cliente"), i, 0);
                        tmClientes.setValueAt(rs.getString("nome"), i, 1);
                        tmClientes.setValueAt(rs.getInt("doc_id"), i, 2);
                        tmClientes.setValueAt(rs.getInt("cc"), i, 3);
                        tmClientes.setValueAt(rs.getString("contacto"), i, 4);
                        i++;
                    }
                    if (encontrouNome == false) {
                        JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum cliente com esse nome!");
                        jtfCliente.setText("");
                    }
                    ligacaoBD.fecharLigacao(con);
                }
                catch(SQLException sqle) {
                    System.out.println("Não foi possível efetuar a operação sobre a BD!");
                    sqle.printStackTrace();
                }
            }
        }
    }
    
    private void removeLinhasTabela() {
        while (tmClientes.getRowCount() > 0)
            tmClientes.removeRow(0);
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbAlterar
    private class EventoJBAlterar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            
            if (jtClientes.getSelectedRow() == -1)
                JOptionPane.showMessageDialog(null, "Selecione um cliente!");
            else {    
                try {
                    PreparedStatement pstmt;
                    String sqlAlteraCliente = "UPDATE clientes SET nome = ?, doc_id = ?, cc = ?, contacto = ? WHERE num_cliente = ?";                   
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlAlteraCliente);
                    int numLinhaSelecionada = jtClientes.getSelectedRow();
                    pstmt.setString(1, String.valueOf(tmClientes.getValueAt(numLinhaSelecionada,1)));
                    pstmt.setInt(2, Integer.parseInt(String.valueOf(tmClientes.getValueAt(numLinhaSelecionada,2))));
                    pstmt.setInt(3, Integer.parseInt(String.valueOf(tmClientes.getValueAt(numLinhaSelecionada,3))));
                    pstmt.setString(4, String.valueOf(tmClientes.getValueAt(numLinhaSelecionada,4)));
                    pstmt.setInt(5, Integer.parseInt(String.valueOf(tmClientes.getValueAt(numLinhaSelecionada,0))));
                    pstmt.executeUpdate();
                    ligacaoBD.fecharLigacao(con);
                    JOptionPane.showMessageDialog(null,"Os dados do cliente foram alterados com sucesso!");
                }
                catch(SQLException sqle) {
                    System.out.println("Não foi possível efetuar a operação sobre a BD!");
                    sqle.printStackTrace();
                }
            }
        }
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbEliminar
    private class EventoJBEliminar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            
            if (jtClientes.getSelectedRow() == -1)
                JOptionPane.showMessageDialog(null, "Selecione um cliente!");
            else {   
                try {
                    PreparedStatement pstmt;
                    String sqlEliminaCliente = "DELETE FROM clientes WHERE num_cliente = ?";                   
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlEliminaCliente);
                    int numLinhaSelecionada = jtClientes.getSelectedRow();
                    pstmt.setInt(1, Integer.parseInt(String.valueOf(tmClientes.getValueAt(numLinhaSelecionada,0))));
                    pstmt.executeUpdate();
                    ligacaoBD.fecharLigacao(con);
                    JOptionPane.showMessageDialog(null,"O cliente foi eliminado com sucesso!");
                }
                catch(SQLException sqle) {
                    System.out.println("Não foi possível efetuar a operação sobre a BD!");
                    sqle.printStackTrace();
                }
            }
        }
    }
}