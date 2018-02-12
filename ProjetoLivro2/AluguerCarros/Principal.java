/**
 * Write a description of class Principal here.
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
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Principal extends JFrame {

    JMenuBar barraMenu = new JMenuBar();
    JMenu menuNovo = new JMenu("Novo");
    JMenuItem jmiNCarro = new JMenuItem("Carro");
    JMenuItem jmiNCliente = new JMenuItem("Cliente");
    JMenuItem jmiNAluguer = new JMenuItem("Aluguer");
    JMenuItem jmiNTipoCarro = new JMenuItem("Tipo carro");
    JMenuItem jmiNExtra = new JMenuItem("Extra");
    JMenu menuPesquisar = new JMenu("Pesquisar");
    JMenuItem jmiPAlugueres = new JMenuItem("Alugueres");
    JMenuItem jmiPDisponibilidades = new JMenuItem("Disponibilidades");
    JMenuItem jmiPCliente = new JMenuItem("Cliente");
    JMenu jmSair = new JMenu("Sair");
    ImageIcon img = new ImageIcon("aluguer_carros.png");
    JLabel jlImagem = new JLabel(img);
    JLabel jlTexto = new JLabel("Desenvolvido por Carla Jesus");
    NovoCarro novoCarro;
    NovoCliente novoCliente;
    NovoAluguer novoAluguer;
    NovoTipoCarro novoTipoCarro;
    NovoExtra novoExtra;
    PesquisaAlugueres pesquisaAlugueres;
    PesquisaDisponibilidades pesquisaDisponibilidades;
    PesquisaCliente pesquisaCliente;
            
    public Principal() {
        setTitle("Aluguer de carros");
        setSize(550,590);
        setLocation(50,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new FlowLayout());
        setJMenuBar(barraMenu);
        barraMenu.add(menuNovo);
        menuNovo.add(jmiNCarro);
        menuNovo.add(jmiNCliente);
        menuNovo.add(jmiNAluguer);
        menuNovo.add(jmiNTipoCarro);
        menuNovo.add(jmiNExtra);
        barraMenu.add(menuPesquisar);
        menuPesquisar.add(jmiPAlugueres);
        menuPesquisar.add(jmiPDisponibilidades);
        menuPesquisar.add(jmiPCliente);
        barraMenu.add(jmSair);
        add(jlImagem);
        add(jlTexto);
        
        jmiNCarro.addActionListener(new EventoJMenuItem());
        jmiNCliente.addActionListener(new EventoJMenuItem());
        jmiNAluguer.addActionListener(new EventoJMenuItem());
        jmiNTipoCarro.addActionListener(new EventoJMenuItem());
        jmiNExtra.addActionListener(new EventoJMenuItem());
        jmiPAlugueres.addActionListener(new EventoJMenuItem());
        jmiPDisponibilidades.addActionListener(new EventoJMenuItem());
        jmiPCliente.addActionListener(new EventoJMenuItem());
        jmSair.addMouseListener(new EventoJMenuSair());
    }
    
    public static void main(String[] args) {
        new Principal();
    }
    
    private class EventoJMenuItem implements ActionListener {
    
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == jmiNCarro) {
                 novoCarro = new NovoCarro();
                 novoCarro.setVisible(true);
            }
            else if (ev.getSource() == jmiNCliente) {
                 novoCliente = new NovoCliente();
                 novoCliente.setVisible(true);
            }
            else if (ev.getSource() == jmiNAluguer) {
                 novoAluguer = new NovoAluguer();
                 novoAluguer.setVisible(true);
            }
            else if (ev.getSource() == jmiNTipoCarro) {
                 novoTipoCarro = new NovoTipoCarro();
                 novoTipoCarro.setVisible(true);
            }
            else if (ev.getSource() == jmiNExtra) {
                 novoExtra = new NovoExtra();
                 novoExtra.setVisible(true);
            }
            else if (ev.getSource() == jmiPAlugueres) {
                pesquisaAlugueres = new PesquisaAlugueres();
                pesquisaAlugueres.setVisible(true);
            }
            else if (ev.getSource() == jmiPDisponibilidades) {
                pesquisaDisponibilidades = new PesquisaDisponibilidades();
                pesquisaDisponibilidades.setVisible(true);
            }
            else if (ev.getSource() == jmiPCliente) {
                pesquisaCliente = new PesquisaCliente();
                pesquisaCliente.setVisible(true);
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