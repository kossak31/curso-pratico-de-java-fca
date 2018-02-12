/**
 * Write a description of class NovoAluguer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.math.BigDecimal;

public class NovoAluguer extends JFrame {

    //Declara e cria os componentes
    JLabel jlCliente = new JLabel ("Cliente");
    JTextField jtfCliente = new JTextField(20);
    JLabel jlCarro = new JLabel ("Carro");
    JTextField jtfCarro = new JTextField(15);
    JLabel jlLevantamento = new JLabel("Levantamento: ");
    JLabel jlDataLev = new JLabel("Data");
    JTextField jtfDataLev = new JTextField(6);
    JLabel jlHoraLev = new JLabel ("Hora");
    JTextField jtfHoraLev = new JTextField(5);
    JLabel jlEntrega = new JLabel("Entrega: ");
    JLabel jlDataEntrega = new JLabel ("Data");
    JTextField jtfDataEntrega = new JTextField(6);
    JLabel jlHoraEntrega = new JLabel ("Hora");
    JTextField jtfHoraEntrega = new JTextField(5);
    JLabel jlExtra = new JLabel ("Extra");
    JComboBox jcbExtra = new JComboBox();
    JButton jbAdicionar = new JButton("Adicionar");
    DefaultTableModel tmExtras = new DefaultTableModel (null, new String[]{"Código", "Extra", "Preço"});
    JTable jtExtras = new JTable(tmExtras);
    JScrollPane jspExtras = new JScrollPane(jtExtras);
    JButton jbCalcularTotal = new JButton("Calcular");
    JLabel jlPrecoTotal = new JLabel ("Total");
    JTextField jtfPrecoTotal = new JTextField(5);
    JButton jbGuardar = new JButton("Guardar");
    JButton jbLimpar= new JButton("Limpar");
    JLabel jlVazia1 = new JLabel("");
    JLabel jlVazia2 = new JLabel("");
    JLabel jlVazia3 = new JLabel("");
    JLabel jlVazia4 = new JLabel("");
    
    //Construtor
    public NovoAluguer() {    
        
        //Define as porpriedades da janela
        setTitle("Novo aluguer");
        setSize(530,305);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        //Define o tamanho dos componentes
        jlCliente.setPreferredSize(new Dimension(45,20));
        jlCarro.setPreferredSize(new Dimension(40,20));
        jlLevantamento.setPreferredSize(new Dimension(100,20));
        jlDataLev.setPreferredSize(new Dimension(30,20));
        jlHoraLev.setPreferredSize(new Dimension(30,20));
        jlVazia1.setPreferredSize(new Dimension(180,20));
        jlEntrega.setPreferredSize(new Dimension(100,20));
        jlDataEntrega.setPreferredSize(new Dimension(30,20));
        jlHoraEntrega.setPreferredSize(new Dimension(30,20));
        jlVazia2.setPreferredSize(new Dimension(180,20));
        jlExtra.setPreferredSize(new Dimension(30,20));
        jcbExtra.setPreferredSize(new Dimension(168,20));
        jbAdicionar.setPreferredSize(new Dimension(90,20));
        jlVazia3.setPreferredSize(new Dimension(190,20));
        jspExtras.setPreferredSize(new Dimension(500,100));
        jlVazia4.setPreferredSize(new Dimension(300,20));
        jbCalcularTotal.setPreferredSize(new Dimension(90,20));
        
        //Adiciona os componentes à janela
        add(jlCliente);
        add(jtfCliente);
        add(jlCarro);
        add(jtfCarro);
        add(jlLevantamento);
        add(jlDataLev);
        add(jtfDataLev);
        add(jlHoraLev);
        add(jtfHoraLev);
        add(jlVazia1);
        add(jlEntrega);
        add(jlDataEntrega);
        add(jtfDataEntrega);
        add(jlHoraEntrega);
        add(jtfHoraEntrega);
        add(jlVazia2);
        add(jlExtra);
        add(jcbExtra);
        add(jbAdicionar);
        add(jlVazia3);
        add(jspExtras);
        add(jlVazia4);
        add(jbCalcularTotal);
        add(jlPrecoTotal);
        add(jtfPrecoTotal);
        add(jbGuardar);
        add(jbLimpar);
        
        jtfPrecoTotal.setText("0.00");
        
        //Preenche a caixa de combinação jcbExtra
        jcbExtra.removeAllItems();
        String sqlExtras = "SELECT nome_extra FROM extras";
        try {
            LigacaoBD ligacaoBD = new LigacaoBD();
            Connection con = ligacaoBD.obterLigacao();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlExtras);
            while (rs.next())
                jcbExtra.addItem(rs.getString("nome_extra"));
            ligacaoBD.fecharLigacao(con);    
        }
        catch(SQLException sqle) {
            System.out.println("Não foi possível efetuar a operação sobre a BD!");
            sqle.printStackTrace();
        }
        
        /*Registo do listener ActionListener junto dos botões.
        Quando for gerado um evento por estes componentes, é
        criada uma instância da classe EventoJBAdicionar ou EventoJBCalcular,
        EventoJBGuardar ou EventoJBLimpar, onde está o código que
        deve ser executado quando tal acontece*/
        jbAdicionar.addActionListener(new EventoJBAdicionar());
        jbCalcularTotal.addActionListener(new EventoJBCalcular());
        jbGuardar.addActionListener(new EventoJBGuardar());
        jbLimpar.addActionListener(new EventoJBLimpar());
    }
    
    public static void main(String[] args) {
        new NovoAluguer();
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbAdicionar
    private class EventoJBAdicionar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {

            String sqlPesquisaPrecoExtras = "SELECT cod_extra, preco_extra FROM extras WHERE nome_extra LIKE ?";
            try {
                PreparedStatement pstmt;
                LigacaoBD ligacaoBD = new LigacaoBD();
                Connection con = ligacaoBD.obterLigacao();
                pstmt = con.prepareStatement(sqlPesquisaPrecoExtras);
                pstmt.setString(1, String.valueOf(jcbExtra.getSelectedItem()));
                ResultSet rs = pstmt.executeQuery();
                String[] campos = new String[] {null, null, null};
                if (rs.next()) {
                    tmExtras.addRow(campos);
                    tmExtras.setValueAt(jcbExtra.getSelectedItem(),tmExtras.getRowCount()-1,1);
                    tmExtras.setValueAt(rs.getBigDecimal("cod_extra"),tmExtras.getRowCount()-1,0);
                    tmExtras.setValueAt(rs.getBigDecimal("preco_extra"),tmExtras.getRowCount()-1,2);
                }
                ligacaoBD.fecharLigacao(con);
            }
            catch(SQLException sqle) {
                System.out.println("Não foi possível efetuar a operação sobre a BD!");
                sqle.printStackTrace();
            }
        }
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbCalcular
    private class EventoJBCalcular implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {

            BigDecimal total = new BigDecimal(0);
            //Invoca o método implementado em baixo
            long numD = calculaDiferencaDatas();
            BigDecimal precoD = new BigDecimal(0);
            String sqlPesquisaPrecoDiario = "SELECT preco_diario FROM carros WHERE num_carro = ?";
            try {
                PreparedStatement pstmt;
                LigacaoBD ligacaoBD = new LigacaoBD();
                Connection con = ligacaoBD.obterLigacao();
                pstmt = con.prepareStatement(sqlPesquisaPrecoDiario);
                pstmt.setInt(1,Integer.parseInt(jtfCarro.getText()));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    precoD = rs.getBigDecimal("preco_diario");
                    total = new BigDecimal(numD).multiply(precoD);
                }
                ligacaoBD.fecharLigacao(con);
                int i=0;
                BigDecimal precoExtra = new BigDecimal(0); 
                while (i < tmExtras.getRowCount()) {
                    precoExtra = new BigDecimal(String.valueOf(tmExtras.getValueAt(i,2)));
                    total = total.add(precoExtra);
                    i++;
                }
                jtfPrecoTotal.setText(String.valueOf(total));
                
            }
            catch(SQLException sqle) {
                System.out.println("Não foi possível efetuar a operação sobre a BD!");
                sqle.printStackTrace();
            }
            
        }
    }
    
    private long calculaDiferencaDatas() {
        
        long numDias = 0;  
        long diferencaDiasMilisegundos = java.sql.Date.valueOf(jtfDataEntrega.getText()).getTime() - java.sql.Date.valueOf(jtfDataLev.getText()).getTime();     
        numDias = (diferencaDiasMilisegundos/1000/60/60/24);
        long horaLevMilisegundos = java.sql.Time.valueOf(jtfHoraLev.getText()).getTime();
        long horaEntregaMilisegundos = java.sql.Time.valueOf(jtfHoraEntrega.getText()).getTime();
        /*Os dias de alugueres são em períodos de 24 horas.
        Se o cliente levanta o carro às 10 horas terá que o entregar na data combinada até ás 10 horas.
        Poderá fazê-lo antes dessa hora mas se o fizer depois terá que pagar masi um dia.*/
        if (horaEntregaMilisegundos > horaLevMilisegundos)
            numDias++;
        return numDias;
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbGuardar
    private class EventoJBGuardar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {

            if (jtfCliente.getText().equals("") || jtfCarro.getText().equals("") || jtfDataLev.getText().equals("") ||
                 jtfHoraLev.getText().equals("") || jtfDataEntrega.getText().equals("") || jtfHoraEntrega.getText().equals(""))
                JOptionPane.showMessageDialog(null,"Todos os campos são de preenchimento obrigatório!");
            else {
                try {
                    PreparedStatement pstmt1, pstmt2;
                    //O valor null utiliza-se para indicar que o primeiro campo da tabela é de numeração automática (auto increment)
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    String sqlNovoAluguer = "INSERT INTO alugueres VALUES(null,?,?,?,?,?,?,?)";
                    pstmt1 = con.prepareStatement(sqlNovoAluguer);
                    //O método de classe parseInt da classe Integer permite converter o valor do tipo String num valor do tipo int
                    pstmt1.setInt(1, Integer.parseInt(jtfCliente.getText()));
                    pstmt1.setInt(2, Integer.parseInt(jtfCarro.getText()));
                    //O método de classe valueOf da classe Date permite converter o valor do tipo String num valor do tipo Date
                    //Existem duas classes Date, uma do pacote java.util e outra do pacote java.sql, por esse motivo colocou-se o nome do pacote atrás da classe
                    pstmt1.setDate(3, java.sql.Date.valueOf((jtfDataLev.getText())));
                    //O método de classe valueOf da classe Time permite converter o valor do tipo String num valor do tipo Time
                    pstmt1.setTime(4, java.sql.Time.valueOf((jtfHoraLev.getText())));
                    pstmt1.setDate(5, java.sql.Date.valueOf((jtfDataEntrega.getText())));
                    pstmt1.setTime(6, java.sql.Time.valueOf((jtfHoraEntrega.getText())));
                    pstmt1.setBigDecimal(7, new BigDecimal(jtfPrecoTotal.getText()));
                    pstmt1.executeUpdate();
                    
                    //Determina o num_aluguer gerado
                    String sqlUltimoNumAluguer = "SELECT MAX(num_aluguer) AS ultimo_num FROM alugueres";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sqlUltimoNumAluguer);
                    int numAluguer = 0;
                    if (rs.next())
                        numAluguer = rs.getInt("ultimo_num");
                        
                    //Guarda os extras selecionados
                    String sqlExtrasSelecionados = "INSERT INTO extras_alugueres VALUES(?,?)";
                    pstmt2 = con.prepareStatement(sqlExtrasSelecionados);
                    for (int i = 0; i < tmExtras.getRowCount(); i++) {
                        pstmt2.setInt(1, numAluguer);
                        pstmt2.setInt(2, Integer.parseInt(String.valueOf(tmExtras.getValueAt(i,0))));
                        pstmt2.executeUpdate();
                    }
                    
                    ligacaoBD.fecharLigacao(con);
                    JOptionPane.showMessageDialog(null,"Os dados foram guardados com sucesso!");
                    //Invoca os métodos implementados em baixo
                    limpaCampos();
                    removeLinhasTabela();
                }
                catch(SQLException sqle) {
                    System.out.println("Não foi possível efetuar a operação sobre a BD!");
                    sqle.printStackTrace();
                }
            }
        }
    }
    
    private void limpaCampos() {
        jtfCliente.setText("");
        jtfCarro.setText("");
        jtfDataLev.setText("");
        jtfHoraLev.setText("");
        jtfDataEntrega.setText("");
        jtfHoraEntrega.setText("");
        jtfPrecoTotal.setText("");
    }
    
    private void removeLinhasTabela() {
        while (tmExtras.getRowCount() > 0)
            tmExtras.removeRow(0);
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbLimpar
    private class EventoJBLimpar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            limpaCampos();
            removeLinhasTabela();
        }
    }
}