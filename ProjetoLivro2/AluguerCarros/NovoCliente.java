/**
 * Write a description of class NovoCliente here.
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
import java.awt.Dimension;
import javax.swing.JOptionPane;

public class NovoCliente extends JFrame {

    //Declara e cria os componentes   
    JLabel jlNome = new JLabel ("Nome");
    JTextField jtfNome = new JTextField(20);
    JLabel jlDocId = new JLabel ("Doc. id.");
    JTextField jtfDocId = new JTextField(20);
    JLabel jlCc = new JLabel ("C. cond.");
    JTextField jtfCc = new JTextField(20);
    JLabel jlContacto = new JLabel ("Contacto");
    JTextField jtfContacto = new JTextField(20);
    JButton jbGuardar = new JButton("Guardar");
    JButton jbLimpar= new JButton("Limpar");
    JLabel jlVazia = new JLabel("");
    
    //Construtor
    public NovoCliente() {    
        
        //Define as porpriedades da janela
        setTitle("Novo cliente");
        setSize(350,190);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        //Define o tamanho dos rótulos
        jlNome.setPreferredSize(new Dimension(65,20));
        jlDocId.setPreferredSize(new Dimension(65,20));
        jlCc.setPreferredSize(new Dimension(65,20));
        jlContacto.setPreferredSize(new Dimension(65,20));
        jlVazia.setPreferredSize(new Dimension(325,10));
        
        //Adiciona os componentes à janela
        add(jlNome);
        add(jtfNome);
        add(jlDocId);
        add(jtfDocId);
        add(jlCc);
        add(jtfCc);
        add(jlContacto);
        add(jtfContacto);
        add(jlVazia);
        add(jbGuardar);
        add(jbLimpar);
        
        /*Registo do listener ActionListener junto dos botões.
        Quando for gerado um evento por estes componentes, é
        criada uma instância da classe EventoJBGuardar ou EventoJBLimpar,
        onde está o código que deve ser executado quando tal acontece*/
        jbGuardar.addActionListener(new EventoJBGuardar());
        jbLimpar.addActionListener(new EventoJBLimpar());
    }
    
    public static void main(String[] args) {
        new NovoCliente();
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbGuardar
    private class EventoJBGuardar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {

             if (jtfNome.getText().equals("") || jtfDocId.getText().equals("") || jtfCc.getText().equals("") || jtfContacto.getText().equals(""))
                JOptionPane.showMessageDialog(null,"Todos os campos são de preenchimento obrigatório! Se não conhece um dos dados coloque a palavra 'Indisponível'.");
            else {
                try {
                    PreparedStatement pstmt;
                    //O valor null utiliza-se para indicar que o primeiro campo da tabela é de numeração automática (auto increment)
                    String sqlNovoCliente = "INSERT INTO contatos VALUES(null,?,?,?,?)";
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlNovoCliente);
                    pstmt.setString(1, jtfNome.getText());
                    //O método de classe parseInt da classe Integer permite converter o valor do tipo String num valor do tipo int
                    pstmt.setInt(2, Integer.parseInt(jtfDocId.getText()));
                    pstmt.setInt(3, Integer.parseInt(jtfCc.getText()));
                    pstmt.setString(4, jtfContacto.getText());
                    pstmt.executeUpdate();
                    ligacaoBD.fecharLigacao(con);
                    JOptionPane.showMessageDialog(null,"Os dados foram guardados com sucesso!");
                    //Invoca o método implementado em baixo
                    limpaCampos();
                }
                catch(SQLException sqle) {
                    System.out.println("Não foi possível efetuar a operação sobre a BD!");
                    sqle.printStackTrace();
                }
            }
        }
    }
    
    private void limpaCampos() {
        jtfNome.setText("");
        jtfDocId.setText("");
        jtfCc.setText("");
        jtfContacto.setText("");
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbLimpar
    private class EventoJBLimpar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            limpaCampos();
        }
    }
}