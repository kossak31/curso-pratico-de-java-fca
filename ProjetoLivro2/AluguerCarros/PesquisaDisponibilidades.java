/**
 * Write a description of class PesquisaDisponibilidades here.
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

public class PesquisaDisponibilidades extends JFrame {

    //Declara e cria os componentes
    JLabel jlTipoCarro = new JLabel ("Tipo carro");
    JTextField jtfTipoCarro = new JTextField(10);
    JLabel jlDataLev = new JLabel ("Dt. lev.");
    JTextField jtfDataLev = new JTextField(6);
    JLabel jlHoraLev = new JLabel ("H. lev.");
    JTextField jtfHoraLev = new JTextField(5);
    JLabel jlDataEntrega = new JLabel ("Dt. entrega");
    JTextField jtfDataEntrega = new JTextField(6);
    JLabel jlHoraEntrega = new JLabel ("H. entrega");
    JTextField jtfHoraEntrega = new JTextField(5);
    JButton jbPesquisar = new JButton("Pesquisar");
    DefaultTableModel tmCarros = new DefaultTableModel (null, new String[]{"Nº carro", "Marca", "Modelo", "Preço"});
    JTable jtCarros = new JTable(tmCarros);
    JScrollPane jspCarros = new JScrollPane(jtCarros);
    JLabel jlVazia1 = new JLabel("");
    JLabel jlVazia2 = new JLabel("");
    JLabel jlVazia3 = new JLabel("");
    
    //Construtor
    public PesquisaDisponibilidades() {    
        
        //Define as porpriedades da janela
        setTitle("Disponibilidades");
        setSize(500,330);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        //Define o tamanho dos componentes
        jspCarros.setPreferredSize(new Dimension(470,200));
        jlVazia1.setPreferredSize(new Dimension(270,20));
        jlVazia2.setPreferredSize(new Dimension(230,20));
        jlVazia3.setPreferredSize(new Dimension(85,20));
        
        //Adiciona os componentes à janela
        add(jlTipoCarro);
        add(jtfTipoCarro);
        add(jlVazia1);
        add(jlDataLev);
        add(jtfDataLev);
        add(jlHoraLev);
        add(jtfHoraLev);
        add(jlVazia2);
        add(jlDataEntrega);
        add(jtfDataEntrega);
        add(jlHoraEntrega);
        add(jtfHoraEntrega);
        add(jlVazia3);
        add(jbPesquisar);
        add(jspCarros);
        
        /*Registo do listener ActionListener junto do botão.
        Quando for gerado um evento por este componente, é
        criada uma instância da classe EventoJBPesqusiar,
        onde está o código que deve ser executado quando tal acontece*/
        jbPesquisar.addActionListener(new EventoJBPesquisar());
    
    //Limpa os dados que possam ter ficado desde a última utilização da janela
    //Os métodos invocados estão implementados em baixo
    limpaCampos();
    removeLinhasTabela();
    }
    
    public static void main(String[] args) {
        new PesquisaDisponibilidades();
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbPesquisar
    private class EventoJBPesquisar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            
            //Invoca o método implementado em baixo
            removeLinhasTabela();
            boolean encontrouDisponibilidades = false;
            if (jtfDataLev.getText().equals("") || jtfHoraLev.getText().equals("") || jtfDataEntrega.getText().equals("") || jtfHoraEntrega.getText().equals(""))
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            else {
                try {
                    PreparedStatement pstmt;
                    ResultSet rs;
                    String sqlPesquisaDisponibilidades = "SELECT * FROM carros, alugueres WHERE cod_tipocarro = ? AND carros.num_carro = alugueres.num_carro" +
                    "AND (((data_levantamento>? OR (data_levantamento=? AND hora_levantamento>?)) AND (data_levantamento>? OR (data_levantamento=? AND hora_levantamento>?)))" +
                    "OR ((data_entrega<? OR (data_entrega=? AND hora_entrega<?)) AND (data_entrega<? OR (data_entrega=? AND hora_entrega<?))))";                   
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlPesquisaDisponibilidades);
                    pstmt.setInt(1, Integer.parseInt(jtfTipoCarro.getText()));
                    pstmt.setDate(2, java.sql.Date.valueOf(jtfDataLev.getText()));
                    pstmt.setDate(3, java.sql.Date.valueOf(jtfDataLev.getText()));
                    pstmt.setTime(4, java.sql.Time.valueOf(jtfHoraLev.getText()));
                    pstmt.setDate(5, java.sql.Date.valueOf(jtfDataEntrega.getText()));
                    pstmt.setDate(6, java.sql.Date.valueOf(jtfDataEntrega.getText()));
                    pstmt.setTime(7, java.sql.Time.valueOf(jtfHoraEntrega.getText()));
                    pstmt.setDate(8, java.sql.Date.valueOf(jtfDataLev.getText()));
                    pstmt.setDate(9, java.sql.Date.valueOf(jtfDataLev.getText()));
                    pstmt.setTime(10, java.sql.Time.valueOf(jtfHoraLev.getText()));
                    pstmt.setDate(11, java.sql.Date.valueOf(jtfDataEntrega.getText()));
                    pstmt.setDate(12, java.sql.Date.valueOf(jtfDataEntrega.getText()));
                    pstmt.setTime(13, java.sql.Time.valueOf(jtfHoraEntrega.getText()));
                    rs = pstmt.executeQuery();
                    int i=0;
                    String[] campos = new String[] {null, null, null, null};
                    while (rs.next()) {
                        encontrouDisponibilidades = true;
                        tmCarros.addRow(campos);
                        tmCarros.setValueAt(rs.getInt("num_carro"), i, 0);
                        tmCarros.setValueAt(rs.getString("marca"), i, 1);
                        tmCarros.setValueAt(rs.getString("modelo"), i, 2);
                        tmCarros.setValueAt(rs.getBigDecimal("preco_diario"), i, 3);
                        i++;
                    }
                    if (encontrouDisponibilidades == false) {
                        JOptionPane.showMessageDialog(null, "Não existe nenhum carro para alugar no período indicado!");
                        //Invoca o método implementado em baixo
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
    
    private void removeLinhasTabela() {
        while (tmCarros.getRowCount() > 0)
            tmCarros.removeRow(0);
    }
    
    private void limpaCampos() {
        jtfTipoCarro.setText("");
        jtfDataLev.setText("");
        jtfHoraLev.setText("");
        jtfDataEntrega.setText("");
        jtfHoraEntrega.setText("");
    }
}