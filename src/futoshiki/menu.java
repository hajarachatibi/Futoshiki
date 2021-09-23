/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshiki;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 *
 * @author hajar
 */
public class menu extends JFrame {
    
    public menu(String name)
    {
                //setter les attributs essentielles pour notre JFrame
                super(name);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setEnabled(false);
		getContentPane().setLayout(new BorderLayout(0, 0));
                //Creation d'une JPanel
		JPanel panel = new JPanel();
                panel.setBackground(new Color(241, 194, 208));
                panel.setSize(new Dimension(350, 200));
                getContentPane().setSize(new Dimension(350, 200));
		getContentPane().add(panel, BorderLayout.NORTH);
		
		
		//ComboBox pour choisir la taille de la grille 
		JComboBox taille = new JComboBox();
		taille.setForeground(Color.WHITE);
                taille.setBackground(new Color(121, 48, 70 ));
		taille.setFont(new Font("SimSun", Font.BOLD | Font.ITALIC, 15));
		taille.setModel(new DefaultComboBoxModel(new String[] {"4x4", "5x5", "6x6", "7x7", "8x8", "9x9"}));
		taille.setSize(taille.getWidth()+50, taille.getHeight());
		panel.add(taille);
		
                //ComboBox pour choisir le niveau de difficulte
		JComboBox niveau = new JComboBox();
		niveau.setForeground(Color.WHITE);
                niveau.setBackground(new Color(121, 48, 70 ));
		niveau.setFont(new Font("SimSun", Font.BOLD | Font.ITALIC, 11));
		niveau.setModel(new DefaultComboBoxModel(new String[] {"facile", "moyen", "difficile"}));
		panel.add(niveau);
		
                //Bouton pour Commencer la partie
		JButton btn_Start = new JButton("Commencer");
		btn_Start.setForeground(Color.WHITE);
                btn_Start.setBackground(new Color(121, 48, 70 ));
		btn_Start.setFont(new Font("SimSun", Font.BOLD | Font.ITALIC, 11));
		btn_Start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                            //Lire la taille choisi par l'utilisateur 
                            String size = (String)taille.getSelectedItem();
                            // Envoyer la taille choisi au JFrame Prinipal 
                            Principal.size = size;
                            // Lire le niveau de difficulte choisi par l'utilisateur
                            String nv = niveau.getSelectedItem().toString();
                            // Envoyer le niveau choisi au JFrame Prinipal 
                            Principal.niveau = nv;
                            // Inctancier la classe Principal et l'afficher
                            Principal p = new Principal("Futoshiki");
                            p.setLocationRelativeTo(null);
                            setVisible(false);
                            p.setVisible(true);
                        }
		});
                panel.add(btn_Start);
    }
    
}
