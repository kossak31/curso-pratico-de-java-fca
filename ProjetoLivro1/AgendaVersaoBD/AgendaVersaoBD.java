/**
 * Write a description of class AgendaVersaoBD here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class AgendaVersaoBD extends JFrame {

    JMenuBar barraMenu = new JMenuBar();
    JMenu menuContactos = new JMenu("Contactos");
    JMenuItem jmiNovo = new JMenuItem("Novo");
    JMenuItem jmiListar = new JMenuItem("Listar");
    JMenu menuProcurar = new JMenu("Procurar");
    JMenuItem jmiProcNome = new JMenuItem("Por nome");
    JMenuItem jmiProcTelf = new JMenuItem("Por telefone");
    JMenu jmSair = new JMenu("Sair");
    ImageIcon img = new ImageIcon("agenda.jpg");
    JLabel jlImagem = new JLabel(img);
    JLabel jlTexto = new JLabel("Desenvolvido por Carla Jesus");
    NovoContacto novoContacto;
    ListarContactos listarContactos;
    ProcurarNome procurarNome;
    ProcurarTelefone procurarTelefone;
            
    public AgendaVersaoBD() {
        setTitle("Agenda");
        setSize(300,275);
        setLocation(50,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new FlowLayout());
        setJMenuBar(barraMenu);
        barraMenu.add(menuContactos);
        menuContactos.add(jmiNovo);
        menuContactos.add(jmiListar);
        barraMenu.add(menuProcurar);
        menuProcurar.add(jmiProcNome);
        menuProcurar.add(jmiProcTelf);
        barraMenu.add(jmSair);
        
        add(jlImagem);
        add(jlTexto);
        
        jmiNovo.addActionListener(new EventoJMenuItem());
        jmiListar.addActionListener(new EventoJMenuItem());
        jmiProcNome.addActionListener(new EventoJMenuItem());
        jmiProcTelf.addActionListener(new EventoJMenuItem());
        jmSair.addMouseListener(new EventoJMenuSair());
    }
    
    public static void main(String[] args) {
        new AgendaVersaoBD();
    }
    
    private class EventoJMenuItem implements ActionListener {
    
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == jmiNovo) {
                 novoContacto = new NovoContacto();
                 novoContacto.setVisible(true);
            }
            else if (ev.getSource() == jmiListar) {
                listarContactos = new ListarContactos();
                listarContactos.setVisible(true);
            }    
            else if (ev.getSource() == jmiProcNome) {
                procurarNome = new ProcurarNome();
                procurarNome.setVisible(true);
            }
            else if (ev.getSource() == jmiProcTelf) {
                procurarTelefone = new ProcurarTelefone();
                procurarTelefone.setVisible(true);
            }
        }
    }
    
    private class EventoJMenuSair implements MouseListener {
    
        public void mouseClicked(MouseEvent ev) {
            System.exit(0);
        }
        
        public void mouseEntered (MouseEvent ev) {}
        
        public void mouseExited(MouseEvent ev) {}
        
        public void mouseReleased(MouseEvent ev) {}
        
        public void mousePressed(MouseEvent ev) {}
    }
}