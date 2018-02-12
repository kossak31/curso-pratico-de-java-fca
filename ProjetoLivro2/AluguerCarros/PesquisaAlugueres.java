/**
 * Write a description of class PesquisaAlugueres here.
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
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class PesquisaAlugueres extends JFrame {

    //Declara e cria os componentes
    JLabel jlDataLev = new JLabel ("Data levantamento");
    JTextField jtfDataLev = new JTextField(10);
    JButton jbPesquisar = new JButton("Pesquisar");
    DefaultTableModel tmAlugueres = new DefaultTableModel (null, new String[]{"Nº aluguer", "Nº cliente", "Nº carro", "Hora lev."});
    JTable jtAlugueres = new JTable(tmAlugueres);
    JScrollPane jspAlugueres = new JScrollPane(jtAlugueres);
    JLabel jlExtras = new JLabel("----- Extras -----");
    DefaultTableModel tmExtras = new DefaultTableModel (null, new String[]{"Código", "Nome"});
    JTable jtExtras = new JTable(tmExtras);
    JScrollPane jspExtras = new JScrollPane(jtExtras);
    
    //Construtor
    public PesquisaAlugueres() {    
        
        //Define as porpriedades da janela
        setTitle("Alugueres");
        setSize(500,410);
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);
        
        //Define o tamanho dos componentes
        jtfDataLev.setPreferredSize(new Dimension(20,25));
        jspAlugueres.setPreferredSize(new Dimension(460,200));
        jspExtras.setPreferredSize(new Dimension(460,100));
        
        //Adiciona os componentes à janela
        add(jlDataLev);
        add(jtfDataLev);
        add(jbPesquisar);
        add(jspAlugueres);
        add(jlExtras);
        add(jspExtras);
        
        /*Registo do listener ActionListener e do listener MouseListener
        junto dos botões. Quando for gerado um evento por estes componentes, é
        criada uma instância da classe EventoJBGPesquisar ou EventoJTPesquisar,
        onde está o código que deve ser executado quando tal acontece*/
        jbPesquisar.addActionListener(new EventoJBPesquisar());
        jtAlugueres.addMouseListener(new EventoJTPesquisar());
        
        //Limpa os dados que possam ter ficado desde a última utilização da janela
        //Os métodos invocados estão implementados em baixo
        jtfDataLev.setText("");
        //Os métodos invocados estão implementados em baixo
        removeLinhasTabelaAlugueres();
        removeLinhasTabelaExtras();
    }
    
    public static void main(String[] args) {
        new PesquisaAlugueres();
    }
    
    //Classe interna que contém o código que é executado quando se pressiona o botão jbPesquisar
    private class EventoJBPesquisar implements ActionListener {
        
        public void actionPerformed(ActionEvent ev) {
            
            //Invoca os métodos implementados em baixo
            removeLinhasTabelaAlugueres();
            removeLinhasTabelaExtras();
            boolean encontrouAlugueres = false;
            if (jtfDataLev.getText().equals(""))
                JOptionPane.showMessageDialog(null, "Preencha o campo data de levantamento!");
            else {
                try {
                    PreparedStatement pstmt;
                    ResultSet rs;
                    String sqlPesquisaAlugueres = "SELECT * FROM alugueres WHERE data_levantamento = ?";                   
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt = con.prepareStatement(sqlPesquisaAlugueres);
                    pstmt.setDate(1, java.sql.Date.valueOf(jtfDataLev.getText()));
                    rs = pstmt.executeQuery();
                    int i=0;
                    String[] camposAlugueres = new String[] {null, null, null, null, null};
                    while (rs.next()) {
                        encontrouAlugueres = true;
                        tmAlugueres.addRow(camposAlugueres);
                        tmAlugueres.setValueAt(rs.getInt("num_aluguer"), i, 0);
                        tmAlugueres.setValueAt(rs.getInt("num_cliente"), i, 1);
                        tmAlugueres.setValueAt(rs.getInt("num_carro"), i, 2);
                        tmAlugueres.setValueAt(rs.getString("hora_levantamento"), i, 3);
                        i++;
                    }
                    if (encontrouAlugueres == false) {
                        JOptionPane.showMessageDialog(null, "Não existe nenhum aluguer na data indicada!");
                        jtfDataLev.setText("");
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
    
    private void removeLinhasTabelaAlugueres() {
        while (tmAlugueres.getRowCount() > 0)
            tmAlugueres.removeRow(0);
    }

    private void removeLinhasTabelaExtras() {
        while (tmExtras.getRowCount() > 0)
            tmExtras.removeRow(0);
    }
    
    //Classe interna que contém o código que é executado quando se seleciona uma linha na tabela jtAlugueres
    private class EventoJTPesquisar implements MouseListener {
        
        public void mouseClicked(MouseEvent ev) {
            
            //Invoca o método implementado em cima
            removeLinhasTabelaExtras();
            if (jtAlugueres.getSelectedRow() != -1) {
                try {
                    PreparedStatement pstmt1, pstmt2;
                    ResultSet rsExtras, rsNomeExtras;
                    String sqlPesquisaExtras = "SELECT * FROM extras_alugueres WHERE num_aluguer = ?";                   
                    String sqlPesquisaNomeExtras = "SELECT * FROM extras WHERE cod_extra = ?";                   
                    LigacaoBD ligacaoBD = new LigacaoBD();
                    Connection con = ligacaoBD.obterLigacao();
                    pstmt1 = con.prepareStatement(sqlPesquisaExtras);
                    pstmt1.setInt(1, Integer.parseInt(String.valueOf(tmAlugueres.getValueAt(jtAlugueres.getSelectedRow(),0))));
                    rsExtras = pstmt1.executeQuery();
                    pstmt2 = con.prepareStatement(sqlPesquisaNomeExtras);
                    int i=0;
                    String[] camposExtras = new String[] {null, null};
                    while(rsExtras.next()) {
                        tmExtras.addRow(camposExtras);
                        tmExtras.setValueAt(rsExtras.getInt("cod_extra"), i, 0);
                        pstmt2.setInt(1, rsExtras.getInt("cod_extra"));
                        rsNomeExtras = pstmt2.executeQuery();
                        if (rsNomeExtras.next())
                            tmExtras.setValueAt(rsNomeExtras.getString("nome_extra"), i, 1);
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
        
        public void mousePressed(MouseEvent ev) {}
        
        public void mouseReleased(MouseEvent ev) {}
        
        public void mouseEntered(MouseEvent ev) {}
        
        public void mouseExited(MouseEvent ev) {}
    }
}