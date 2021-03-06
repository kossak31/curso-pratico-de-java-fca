/**
 * Write a description of class NovoExtra here.
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

public class NovoExtra extends JFrame {

    //Declara e cria os componentes   
    JLabel jlNome = new JLabel ("Extra");
    JTextField jtfNome = new JTextField(20);
    JLabel jlPreco = new JLabel ("Preço");
    JTextField jtfPreco = new JTextField(20);
    JButton jbGuardar = new JButton("Guardar");
    JButton jbLimpar= new JButton("Limpar");
    JLabel jlVazia = new JLabel("");
    
    //Construtor
    public NovoExtra() {    
        
        //Define as porpriedades da janela
        setTitle("Novo extra");
        setSize(320,140);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        //Define o tamanho dos rótulos
        jlNome.setPreferredSize(new Dimension(45,20));
        jlPreco.setPreferredSize(new Dimension(45,20));
        jlVazia.setPreferredSize(new Dimension(315,10));
        
        //Adiciona os componentes à janela
        add(jlNome);
        add(jtfNome);
        add(jlPreco);
        add(jtfPreco);
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
        new NovoExtra();
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbGuardar
    private class EventoJBGuardar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {

            if (jtfNome.getText().equals("") || jtfPreco.getText().equals(""))
                JOptionPane.showMessageDialog(null,"Todos os campos são de preenchimento obrigatório!");
            else {
                try {
                    PreparedStatement pstmt;
                    //O valor null utiliza-se para indicar que o primeiro campo da tabela é de numeração automática (auto increment)
                    String sqlNovoExtra = "INSERT INTO extras VALUES(null,?,?)";
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlNovoExtra);
                    pstmt.setString(1, jtfNome.getText());
                    pstmt.setString(2, jtfPreco.getText());
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
        jtfPreco.setText("");
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbLimpar
    private class EventoJBLimpar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            limpaCampos();
        }
    }
}