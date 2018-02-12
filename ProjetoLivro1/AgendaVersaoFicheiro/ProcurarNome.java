/**
 * Write a description of class ProcuraNome here.
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
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.Dimension;
import javax.swing.JOptionPane;

public class ProcurarNome extends JFrame {

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
    public ProcurarNome() {    
        
        //Define as propriedades da janela
        setTitle("Procurar por nome");
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
        jtfTelefone.setEditable(false);
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
    
    private void limpaCampos() {
        jtfNome.setText("");
        jtfTelefone.setText("");
        jtfTelemovel.setText("");
        jtfEmail.setText("");
    }
        
    private class EventoJBProcurarNome implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            
            boolean encontrouNome = false;
            String nome, telf, telm, email;
            if (jtfNome.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Preencha o campo nome!");
                limpaCampos();
            }
            else {
                try {
                    /*Cria uma stream, que permite ler sequências de carateres em Unicode,
                    associada ao ficheiro "agenda.txt".*/
                    FileReader fr = new FileReader("agenda.txt");
                    BufferedReader br = new BufferedReader(fr);
                    /*Quando o método ready retornar o valor booleano "false"
                    significa que foi atingido o fim do ficheiro.*/
                    while ((encontrouNome == false) && br.ready()) {
                        //O método readLine permite ler dados e mudar para a linha seguinte
                        nome = br.readLine();
                        telf = br.readLine();
                        telm = br.readLine();
                        email = br.readLine();
                        /*O método trim retira espaços em branco que possam exsitir
                        antes ou depois do nome introduzido na caixa de texto.*/
                        if (nome.equals(jtfNome.getText().trim())) {
                            encontrouNome = true;
                            /*O método setText atribuí às caixas de texto os dados lidos a parttir do ficheiro
                            e que estão guardados nas variáveis locais telf, telm e email.*/
                            jtfTelefone.setText(telf);
                            jtfTelemovel.setText(telm);
                            jtfEmail.setText(email);
                        }
                    }
                    if (encontrouNome == false) {
                        JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum contacto com esse nome!");
                        limpaCampos();
                    }
                    br.close();
                }
                catch(IOException ioe) {
                    System.out.println("Não foi possível ler o ficheiro!");
                    ioe.printStackTrace();
                }
            }
        }
    }
}