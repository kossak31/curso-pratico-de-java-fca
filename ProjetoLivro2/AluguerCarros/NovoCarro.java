/**
 * Write a description of class NovoCarro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.math.BigDecimal;

public class NovoCarro extends JFrame {

    //Declara e cria os componentes
    JPanel jpPainel1 = new JPanel();
    JPanel jpPainel2 = new JPanel();
    JLabel jlMarca = new JLabel ("Marca");
    JTextField jtfMarca = new JTextField(15);
    JLabel jlModelo = new JLabel ("Modelo");
    JTextField jtfModelo = new JTextField(15);
    JLabel jlMatricula = new JLabel ("Matricula");
    JTextField jtfMatricula = new JTextField(15);
    JLabel jlDataMatricula = new JLabel ("Data");
    JTextField jtfDataMatricula = new JTextField(15);
    JLabel jlKms = new JLabel ("Kms");
    JTextField jtfKms = new JTextField(15);
    JLabel jlCombustivel = new JLabel ("Combustível");
    JComboBox jcbCombustivel = new JComboBox();
    JLabel jlTipoTransmissao = new JLabel ("Transmissão");
    JComboBox jcbTipoTransmissao = new JComboBox();
    JLabel jlNumPortas = new JLabel ("Nº portas");
    JTextField jtfNumPortas = new JTextField(15);
    JLabel jlNumPassageiros = new JLabel ("Nº passageiros");
    JTextField jtfNumPassageiros = new JTextField(15);
    JLabel jlTipoCarro = new JLabel ("Tipo carro");
    JComboBox jcbTipoCarro = new JComboBox();
    JLabel jlPrecoDiario = new JLabel ("Preço diário");
    JTextField jtfPrecoDiario = new JTextField(15);
    JButton jbGuardar = new JButton("Guardar");
    JButton jbLimpar= new JButton("Limpar");
    JLabel jlVazia = new JLabel("");
    
    //Construtor
    public NovoCarro() {    
        
        //Define as porpriedades da janela
        setTitle("Novo carro");
        setSize(610,225);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        //Define o tamanho dos rótulos
        jlMarca.setPreferredSize(new Dimension(75,20));
        jlModelo.setPreferredSize(new Dimension(75,20));
        jlMatricula.setPreferredSize(new Dimension(75,20));
        jlDataMatricula.setPreferredSize(new Dimension(75,20));
        jlKms.setPreferredSize(new Dimension(75,20));
        jlCombustivel.setPreferredSize(new Dimension(75,20));
        jlTipoTransmissao.setPreferredSize(new Dimension(90,20));
        jlNumPortas.setPreferredSize(new Dimension(90,20));
        jlNumPassageiros.setPreferredSize(new Dimension(90,20));
        jlTipoCarro.setPreferredSize(new Dimension(90,20));
        jlPrecoDiario.setPreferredSize(new Dimension(90,20));
        jlVazia.setPreferredSize(new Dimension(330,10));
        
        //Define o tamanho das caixas de combinação
        jcbCombustivel.setPreferredSize(new Dimension(168,20));
        jcbTipoTransmissao.setPreferredSize(new Dimension(168,20));
        jcbTipoCarro.setPreferredSize(new Dimension(168,20));
        
        //Define o tamanho dos painéis
        jpPainel1.setPreferredSize(new Dimension(280,180));
        jpPainel2.setPreferredSize(new Dimension(280,180));
        
        //Adiciona os componentes aos painéis
        jpPainel1.add(jlMarca);
        jpPainel1.add(jtfMarca);
        jpPainel1.add(jlModelo);
        jpPainel1.add(jtfModelo);
        jpPainel1.add(jlMatricula);
        jpPainel1.add(jtfMatricula);
        jpPainel1.add(jlDataMatricula);
        jpPainel1.add(jtfDataMatricula);
        jpPainel1.add(jlKms);
        jpPainel1.add(jtfKms);
        jpPainel1.add(jlCombustivel);
        jpPainel1.add(jcbCombustivel);
        jpPainel2.add(jlTipoTransmissao);
        jpPainel2.add(jcbTipoTransmissao);
        jpPainel2.add(jlNumPortas);
        jpPainel2.add(jtfNumPortas);
        jpPainel2.add(jlNumPassageiros);
        jpPainel2.add(jtfNumPassageiros);
        jpPainel2.add(jlTipoCarro);
        jpPainel2.add(jcbTipoCarro);
        jpPainel2.add(jlPrecoDiario);
        jpPainel2.add(jtfPrecoDiario);
        jpPainel2.add(jlVazia);
        jpPainel2.add(jbGuardar);
        jpPainel2.add(jbLimpar);
        
        //Adiciona os painéis à janela
        add(jpPainel1);
        add(jpPainel2);
        
        //Preenche a caixa de combinação jcbTipoCarro
        jcbCombustivel.removeAllItems();
        jcbCombustivel.addItem("Gasóleo");
        jcbCombustivel.addItem("Gasolina");
        jcbCombustivel.addItem("GPL");
        jcbCombustivel.addItem("Elétrico");
        jcbCombustivel.addItem("Híbrido");
        
        //Preenche a caixa de combinação jcbTipoTransmissao
        jcbTipoTransmissao.removeAllItems();
        jcbTipoTransmissao.addItem("Manual");
        jcbTipoTransmissao.addItem("Automática");
        
        //Preenche a caixa de combinação jcbTipoCarro
        jcbTipoCarro.removeAllItems();
        String sqlTiposCarro = "SELECT nome_tipocarro FROM tipo_carro";
        try {
            LigacaoBD ligacaoBD = new LigacaoBD();
            Connection con = ligacaoBD.obterLigacao();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlTiposCarro);
            while (rs.next())
                jcbTipoCarro.addItem(rs.getString("nome_tipocarro"));
            ligacaoBD.fecharLigacao(con);    
        }
        catch(SQLException sqle) {
            System.out.println("Não foi possível efetuar a operação sobre a BD!");
            sqle.printStackTrace();
        }
        
        /*Registo do listener ActionListener junto dos botões.
        Quando for gerado um evento por estes componentes, é
        criada uma instância da classe EventoJBGuardar ou EventoJBLimpar,
        onde está o código que deve ser executado quando tal acontece*/
        jbGuardar.addActionListener(new EventoJBGuardar());
        jbLimpar.addActionListener(new EventoJBLimpar());
    }
    
    public static void main(String[] args) {
        new NovoCarro();
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbGuardar
    private class EventoJBGuardar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {

            if (jtfMarca.getText().equals("") || jtfModelo.getText().equals("") || jtfMatricula.getText().equals("") ||
                jtfDataMatricula.getText().equals("") || jtfKms.getText().equals("") || jtfNumPortas.getText().equals("") ||
                jtfNumPassageiros.getText().equals("") || jtfPrecoDiario.getText().equals(""))
                JOptionPane.showMessageDialog(null,"Todos os campos são de preenchimento obrigatório! Se não conhece um dos dados coloque a palavra 'Indisponível'.");
            else {
                try {
                    PreparedStatement pstmt;
                    //O valor null utiliza-se para indicar que o primeiro campo da tabela é de numeração automática (auto increment)
                    String sqlNovoCarro = "INSERT INTO carros VALUES(null,?,?,?,?,?,?,?,?,?,?,?)";
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlNovoCarro);
                    pstmt.setString(1, jtfMarca.getText());
                    pstmt.setString(2, jtfModelo.getText());
                    pstmt.setString(3, jtfMatricula.getText());
                    //O método de classe valueOf da classe Date permite converter o valor do tipo String num valor do tipo Date
                    //Existem duas classes Date, uma do pacote java.util e outra do pacote java.sql, por esse motivo colocou-se o nome do pacote atrás da classe
                    pstmt.setDate(4, java.sql.Date.valueOf(jtfDataMatricula.getText()));
                    //O método de classe parseInt da classe Integer permite converter o valor do tipo String num valor do tipo int
                    pstmt.setInt(5, Integer.parseInt(jtfKms.getText()));
                    pstmt.setString(6, String.valueOf(jcbCombustivel.getSelectedItem()));
                    pstmt.setString(7, String.valueOf(jcbTipoTransmissao.getSelectedItem()));
                    pstmt.setInt(8, Integer.parseInt(jtfNumPortas.getText()));
                    pstmt.setInt(9, Integer.parseInt(jtfNumPassageiros.getText()));
                    pstmt.setInt(10, pesquisaCodTipoCarro());
                    pstmt.setBigDecimal(11, new BigDecimal(jtfPrecoDiario.getText()));
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
        jtfMarca.setText("");
        jtfModelo.setText("");
        jtfMatricula.setText("");
        jtfDataMatricula.setText("");
        jtfKms.setText("");
        jtfNumPortas.setText("");
        jtfNumPassageiros.setText("");
        jtfPrecoDiario.setText("");
    }
    
    private int pesquisaCodTipoCarro() {
        int codTC = 0;
        String sqlPesquisaCodTipoCarro = "SELECT cod_tipocarro FROM tipo_carro WHERE nome_tipocarro LIKE ?";
        try {
            PreparedStatement pstmt;
            LigacaoBD ligacaoBD = new LigacaoBD();
            Connection con = ligacaoBD.obterLigacao();
            pstmt = con.prepareStatement(sqlPesquisaCodTipoCarro);
            pstmt.setString(1, String.valueOf(jcbTipoCarro.getSelectedItem()));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                codTC = rs.getInt("cod_tipocarro");
            ligacaoBD.fecharLigacao(con);
        }
        catch(SQLException sqle) {
            System.out.println("Não foi possível efetuar a operação sobre a BD!");
            sqle.printStackTrace();
        }
        return codTC;
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbLimpar
    private class EventoJBLimpar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            limpaCampos();
        }
    }
}