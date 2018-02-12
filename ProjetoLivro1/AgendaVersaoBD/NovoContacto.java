/**
 * Write a description of class NovoContacto here.
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

public class NovoContacto extends JFrame {

    //Declara e cria os componentes   
    JLabel jlNome = new JLabel ("Nome: ");
    JTextField jtfNome = new JTextField(20);
    JLabel jlTelefone = new JLabel ("Telefone: ");
    JTextField jtfTelefone = new JTextField(20);
    JLabel jlTelemovel = new JLabel ("Telemóvel: ");
    JTextField jtfTelemovel = new JTextField(20);
    JLabel jlEmail = new JLabel ("E-mail: ");
    JTextField jtfEmail = new JTextField(20);
    JButton jbGuardar = new JButton("Guardar");
    JButton jbCancelar= new JButton("Cancelar");
    JLabel jlVazia = new JLabel("");
    
    //Construtor
    public NovoContacto() {    
        
        //Define as propriedades da janela
        setTitle("Novo contacto");
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
        add(jbGuardar);
        add(jbCancelar);
        
        /*Registo do listener ActionListener junto dos botões.
        Quando for gerado um evento por estes componentes, é
        criada uma instância da classe EventoJBGuardar ou EventoJBCancelar,
        onde está o código que deve ser executado quando tal acontece*/
        jbGuardar.addActionListener(new EventoJBGuardar());
        jbCancelar.addActionListener(new EventoJBCancelar());
    }
    
    /*Este método é necessário para poder utilizar o método showMessageDialog com o parâmetro "this",
    ou seja, a caixa de diálogo aparecerá no centro da janela a partir da qual foi invocada.
    Em alternativa o método pode ser invocado dentro da classe EventoJBGuardar mas, nesse caso,
    terá que utilizar o parãmetro "null", ou seja, a caixa de diálogo aparecerá no centro do. ecrã.
    Esta linha de código está em comentário dentro da classe EventoJBGuardar.*/
    private void mostraCaixaDialogo() {
        JOptionPane.showMessageDialog(this,"Os dados foram guardados com sucesso!");
    }
    
    private void limpaCampos() {
        jtfNome.setText("");
        jtfTelefone.setText("");
        jtfTelemovel.setText("");
        jtfEmail.setText("");
    }
    
    private class EventoJBGuardar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {

             if (jtfNome.getText().equals("") || jtfTelefone.getText().equals("") || jtfTelemovel.getText().equals("") || jtfEmail.getText().equals(""))
                JOptionPane.showMessageDialog(null,"Todos os campos são de preenchimento obrigatório! Se não conhece um dos dados coloque a palavra 'Indisponível'.");
            else {
                try {
                    PreparedStatement pstmt;
                    //O valor null utiliza-se para indicar que o primeiro campo da tabela é de numeração automática (auto increment)
                    String sqlNovoContacto = "INSERT INTO contactos VALUES(null,?,?,?,?)";
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlNovoContacto);
                    pstmt.setString(1, jtfNome.getText());
                    //O método de classe parseInt da classe Integer permite converter o valor do tipo String num valor do tipo int
                    pstmt.setInt(2, Integer.parseInt(jtfTelefone.getText()));
                    pstmt.setInt(3, Integer.parseInt(jtfTelemovel.getText()));
                    pstmt.setString(4, jtfEmail.getText());
                    pstmt.executeUpdate();
                    ligacaoBD.fecharLigacao(con);
                    //Invoca o método implementado em cima
                    mostraCaixaDialogo();
                    //JOptionPane.showMessageDialog(null,"Os dados foram guardados com sucesso!");
                    //Invoca o método implementado em cima
                    limpaCampos();
                }
                catch(SQLException sqle) {
                    System.out.println("Não foi possível efetuar a operação sobre a BD!");
                    sqle.printStackTrace();
                }
            }
        }
    }
    
    private class EventoJBCancelar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            limpaCampos();
        }
    }
}