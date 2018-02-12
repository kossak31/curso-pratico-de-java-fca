/**
 * Write a description of class ProcuraTelefone here.
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Dimension;
import javax.swing.JOptionPane;

public class ProcurarTelefone extends JFrame {

    //Declara e cria os componentes   
    JLabel jlNome = new JLabel ("Nome: ");
    JTextField jtfNome = new JTextField(20);
    JLabel jlTelefone = new JLabel ("Telefone: ");
    JTextField jtfTelefone = new JTextField(20);
    JLabel jlTelemovel = new JLabel ("Telemóvel: ");
    JTextField jtfTelemovel = new JTextField(20);
    JLabel jlEmail = new JLabel ("E-mail: ");
    JTextField jtfEmail = new JTextField(20);
    JButton jbProcurar = new JButton("Procurar");
    JLabel jlVazia = new JLabel("");
    
    //Construtor
    public ProcurarTelefone() {    
        
        //Define as propriedades da janela
        setTitle("Procura por telefone");
        setSize(350,190);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        //Define o tamanho dos rótulos
        jlNome.setPreferredSize(new Dimension(65,20));
        jlTelefone.setPreferredSize(new Dimension(65,20));
        jlTelemovel.setPreferredSize(new Dimension(65,20));
        jlEmail.setPreferredSize(new Dimension(65,20));
        jlVazia.setPreferredSize(new Dimension(325,10));
        
        /*Define as caixas de texto como não editáveis,
        uma vez que não será necessário a introdução de dados nestes campos*/
        jtfNome.setEditable(false);
        jtfTelemovel.setEditable(false);
        jtfEmail.setEditable(false);
        
        //Adiciona os componentes à janela
        add(jlNome);
        add(jtfNome);
        add(jlTelefone);
        add(jtfTelefone);
        add(jlTelemovel);
        add(jtfTelemovel);
        add(jlEmail);
        add(jtfEmail);
        add(jlVazia);
        add(jbProcurar);
        
        /*Registo do listener ActionListener junto do botão.
        Quando for gerado um evento por este componente, é
        criada uma instância da classe EventoJBProcurarNome,
        onde está o código que deve ser executado quando tal acontece*/
        jbProcurar.addActionListener(new EventoJBProcurarNome());
    }
    
    public static void main(String[] args) {
        new ProcurarTelefone();
    }
    
    private void limpaCampos() {
        jtfNome.setText("");
        jtfTelefone.setText("");
        jtfTelemovel.setText("");
        jtfEmail.setText("");
    }
        
    private class EventoJBProcurarNome implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            
            boolean encontrouTelefone = false;
            if (jtfTelefone.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Preencha o campo telefone!");
                limpaCampos();
            }
            else {
                try {
                    PreparedStatement pstmt;
                    ResultSet rs;
                    String sqlProcuraTelefone = "SELECT * FROM contactos WHERE telefone = ?";                   
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlProcuraTelefone);
                    pstmt.setInt(1, Integer.parseInt(jtfTelefone.getText()));
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        encontrouTelefone = true;
                        jtfNome.setText(rs.getString("nome"));
                        jtfTelemovel.setText(String.valueOf(rs.getInt("telemovel")));
                        jtfEmail.setText(rs.getString("email"));
                    }
                    if (encontrouTelefone == false) {
                        JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum contacto com esse telefone!");
                        limpaCampos();
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
}