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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import metier.Backtracking;
import metier.Graph;
import metier.SET;
import metier.ST;

/**
 *
 * @author hajar
 */
public class Principal extends JFrame {
    
    	private static final long serialVersionUID = 1L;
        
	// Les cases du jeu (classe caseInput)
	private caseInput[][] cases;
	public char[][] contraintes_Horizontales,contraintes_Verticales;
	
	// CheckBox pour les algorithmes
	private JCheckBox algo_MRV;
	private JCheckBox algo_Degree;
	private JCheckBox algo_LCV ;
	private JCheckBox algo_Fc ;
	private JCheckBox algo_Ac ;
        
        //la taille 
        public static String size;
        public void setTaille(String taille)
        {
            this.size = taille;
        }
        // le niveau de difficulte
        public static String niveau;
        public void setNiveau(String niveau)
        {
            this.niveau = niveau;
        }
	// la grille
	public caseInput[][] getcases()
	{
		return cases;
	}

	public void setcases(caseInput[][] cases)
	{
		this.cases = cases;
	}
        
        //Constructeur
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Principal(String name)
	{
		super(name);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setEnabled(false);
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
                panel.setBackground(new Color(241, 194, 208));
		getContentPane().add(panel, BorderLayout.NORTH);
                JFrame p = this;
		
                // panel du jeu
		JPanel panel_jeu = new JPanel();
                panel_jeu.setBackground(new Color(241, 194, 208));

				//supprimer la grille
				panel_jeu.removeAll();
				panel_jeu.repaint();
				
				//la taille choisi
				int n = size.charAt(0)-'0';

				//nombre totale des lignes et colonne 
				int l = (n*2)-1; 
                                
				//La taille du panel (dynamique) selon la taille choisi
				int s = n*50 + (n-1)*35; 
				
                                //tables des contraintes
				contraintes_Horizontales = new char[n][n-1];
				contraintes_Verticales  = new char[n-1][n];
				
				panel_jeu.setLayout(null);
				panel_jeu.setPreferredSize(new Dimension(s,s));
				
				//jeu
				int X = panel_jeu.getX()+30;
				int Y = panel_jeu.getY();
				
				cases = new caseInput[l][l];
				
				for(int i=0;i<l;i++)
				{
					for(int j=0;j<l;j++)
					{
                                            //Les lignes paires pour les nombres
                                            // Les lignes impaires pour les symboles
                                            //si les lignes sont pair
						if(i%2==0)
						{
							//si les colonnes pairs --> nombres  
							if(j%2==0)
							{  
								caseInput numCases = new caseInput(caseInput.NUMBER);
								numCases.setBounds(X+5+j*30,Y+5+i*30,30,30);
								numCases.setText("");
								numCases.setFont(new Font("Tahoma", Font.BOLD, 13));
								numCases.setHorizontalAlignment(SwingConstants.CENTER);
								
								//on l'ajoute au cellules 
								cases[i][j] = numCases;
								//on l'ajoute au panel
								panel_jeu.add(numCases);
								
								
							}else //si les colonnes impairs --> contraintes horizontales
							{   
								caseInput contrHCases = new caseInput(caseInput.Hor_Constraint);
								contrHCases.setBounds(X+10+j*30,Y+5+i*30,20,20);
								contrHCases.setText("");
								contrHCases.setEditable(false);
								contrHCases.setFont(new Font("Tahoma", Font.BOLD, 13));
								contrHCases.setHorizontalAlignment(SwingConstants.CENTER);

								cases[i][j] = contrHCases;
								
								panel_jeu.add(contrHCases);
							}
						}else
						{   //si le ligne est impair et la colonne est pair  --> contraintes vertivales
							if(j%2==0)
							{
								caseInput contrVCases = new caseInput(caseInput.Ver_Constraint);
								contrVCases.setBounds(X+5+j*30,Y+10+i*30,20,20);
								contrVCases.setText("");
								contrVCases.setEditable(false);
								contrVCases.setFont(new Font("Tahoma", Font.BOLD, 13));
								contrVCases.setHorizontalAlignment(SwingConstants.CENTER);
								
								cases[i][j] = contrVCases;
								
								panel_jeu.add(contrVCases);
							}else // pour les lignes impairs et colonnes impaires --> cases vides
							{
								caseInput vT = new caseInput(caseInput.EMPTY);
								cases[i][j] = vT;
							}
						}
					}
				}
				
				//Pour remplir la grille
				switch(n)
				{
				//taille 4x4
					case 4:
						if(niveau.equals("facile")) //facile
						{
							cases[0][1].setText("<");
                                                        cases[1][4].setText("^");
                                                        cases[1][6].setText("^");
                                                        cases[6][1].setText("<");
                                                        cases[5][6].setText("^");
                                                        cases[6][6].setText("2");
						}
						if(niveau.equals("moyen")) //moyen
						{
							cases[0][2].setText("3");
							cases[1][4].setText("v");
							cases[3][4].setText("v");
							cases[3][6].setText("v");
							cases[5][0].setText("^");
							cases[6][5].setText(">");
						}
						if(niveau.equals("difficile")) //difficile 
						{
							cases[0][0].setText("3");
							cases[0][6].setText("1");
							cases[3][2].setText("^");
							cases[3][4].setText("^");
							cases[6][5].setText(">");
						}
						
						break;
					//pour 5x5
					case 5:
						if(niveau.equals("facile")) //facile
						{
							cases[0][3].setText(">");
							cases[0][7].setText("<");
			                cases[4][3].setText(">");
			                cases[5][4].setText("v");
			                cases[6][0].setText("4");
			                cases[6][5].setText(">");
			                cases[8][1].setText(">");
			                cases[8][2].setText("3");
						}
						if(niveau.equals("moyen")) //moyen
						{
							cases[0][0].setText("3");
							cases[0][1].setText(">");
                                                        cases[2][1].setText("<");
                                                        cases[3][8].setText("v");
                                                        cases[4][5].setText(">");
                                                        cases[5][6].setText("v");
                                                        cases[6][2].setText("1");
                                                        cases[8][7].setText(">");
                                                        cases[8][8].setText("3");
						}
						if(niveau.equals("difficile")) //difficile
						{
							cases[0][1].setText("<");
							cases[0][6].setText("2");
							cases[2][3].setText(">");
							cases[3][4].setText("v");
							cases[4][1].setText(">");
							cases[6][1].setText(">");
							cases[6][5].setText("<");
							cases[6][8].setText("4");
							cases[7][4].setText("v");
							cases[8][5].setText("<");
						}
						break;
					//pour 6x6
					case 6:
						if(niveau.equals("facile")) //facile 
						{
							cases[0][5].setText(">");
							cases[0][6].setText("4");
							cases[1][2].setText("^");
							cases[1][10].setText("^");
							cases[3][2].setText("v");
							cases[4][7].setText(">");
							cases[4][9].setText(">");
							cases[5][6].setText("^");
							cases[6][1].setText(">");
							cases[6][9].setText(">");
							cases[7][10].setText("v");
							cases[8][7].setText(">");
							cases[8][9].setText(">");
							cases[9][2].setText("^");
							cases[10][5].setText("<");
							cases[10][9].setText(">");
							
						}
						if(niveau.equals("moyen")) //moyen 
						{
							cases[1][0].setText("v");
							cases[2][2].setText("4");
							cases[2][3].setText("<");
							cases[2][9].setText(">");
							cases[4][0].setText("4");
							cases[4][1].setText(">");
							cases[4][4].setText("1");
							cases[5][2].setText("v");
							cases[5][8].setText("^");
							cases[6][0].setText("6");
							cases[6][7].setText("<");
							cases[7][8].setText("^");
							cases[8][9].setText("<");
							cases[10][1].setText(">");
							cases[10][10].setText("2");
							
						}
						if(niveau.equals("difficile")) //difficile
						{
							cases[1][0].setText("v");
							cases[1][6].setText("^");
							cases[2][5].setText("<");
							cases[3][2].setText("v");
							cases[3][8].setText("^");
							cases[4][9].setText(">");
							cases[5][2].setText("v");
							cases[5][4].setText("^");
							cases[5][8].setText("^");
							cases[6][5].setText("<");
							cases[7][2].setText("v");
							cases[8][5].setText("<");
							cases[8][9].setText(">");
							cases[9][0].setText("v");
							cases[9][8].setText("^");
							cases[10][0].setText("5");
							cases[10][6].setText("1");
							cases[10][9].setText(">");
						}
						break;
					//pour 7x7
					case 7 :
						if(niveau.equals("facile"))
						{
							cases[0][0].setText("6");
							cases[0][1].setText(">");
							cases[1][2].setText("v");
							cases[1][10].setText("v");
							cases[2][1].setText("<");
							cases[2][3].setText(">");
							cases[2][11].setText(">");
							cases[3][2].setText("v");
							cases[3][6].setText("v");
							cases[4][4].setText("5");
							cases[5][8].setText("^");
							cases[5][12].setText("v");
							cases[6][7].setText(">");
							cases[8][8].setText("3");
							cases[9][10].setText("v");
							cases[10][1].setText(">");
							cases[10][4].setText("3");
							cases[10][6].setText("7");
							cases[10][9].setText(">");
							cases[11][0].setText("v");
							cases[11][4].setText("v");
							cases[11][8].setText("^");
							cases[11][10].setText("v");
							
						}
						if(niveau.equals("moyen"))
						{
							cases[0][9].setText("<");
							cases[2][4].setText("2");
							cases[2][9].setText("<");
							cases[3][0].setText("^");
							cases[3][2].setText("^");
							cases[3][4].setText("v");
							cases[6][2].setText("6");
							cases[6][3].setText("<");
							cases[6][7].setText("<");
							cases[6][11].setText("<");
							cases[7][0].setText("v");
							cases[7][2].setText("^");
							cases[8][7].setText("<");
							cases[8][8].setText("5");
							cases[8][9].setText(">");
							cases[10][0].setText("4");
							cases[10][1].setText("<");
							cases[10][5].setText(">");
							cases[10][11].setText("<");
							cases[12][0].setText("5");
							cases[12][4].setText("3");
							cases[12][9].setText("<");
						}
						if(niveau.equals("difficile"))
						{
							cases[0][8].setText("4");
							cases[0][11].setText(">");
							cases[0][12].setText("3");
							cases[1][2].setText("v");
							cases[1][4].setText("v");
							cases[1][6].setText("^");
							cases[3][0].setText("^");
							cases[3][2].setText("v");
							cases[4][2].setText("3");
							cases[4][5].setText("<");
							cases[4][11].setText(">");
							cases[4][12].setText("2");
							cases[5][4].setText("v");
							cases[6][1].setText("<");
							cases[6][6].setText("1");
							cases[6][10].setText("3");
							cases[8][9].setText("<");
							cases[9][4].setText("v");
							cases[9][6].setText("^");
							cases[9][8].setText("^");
							cases[10][2].setText("5");
							cases[12][5].setText(">");
							cases[12][6].setText("4");
							cases[12][9].setText("<");
							cases[12][11].setText("<");
						}
						
						break;
					//pour 8x8
					case 8 :
						if(niveau.equals("facile"))
						{
							cases[0][0].setText("6");
							cases[0][1].setText(">");
							cases[0][5].setText(">");
							cases[1][4].setText("^");
							cases[1][6].setText("^");
							cases[2][9].setText(">");
							cases[3][0].setText("v");
							cases[3][8].setText("^");
							cases[4][1].setText("<");
							cases[4][3].setText("<");
							cases[4][7].setText(">");
							cases[4][11].setText(">");
							cases[4][13].setText("<");
							cases[5][0].setText("v");
							cases[7][14].setText("^");
							cases[8][1].setText(">");
							cases[8][3].setText("<");
							cases[8][7].setText(">");
							cases[9][8].setText("v");
							cases[9][10].setText("v");
							cases[10][5].setText(">");
							cases[10][10].setText("3");
							cases[10][13].setText(">");
							cases[11][2].setText("^");
							cases[11][6].setText("v");
							cases[11][12].setText("^");
							cases[12][3].setText(">");
							cases[12][7].setText(">");
							cases[12][12].setText("5");
							cases[13][2].setText("^");
							cases[13][12].setText("v");
							cases[14][0].setText("3");
							cases[14][2].setText("5");
							cases[14][3].setText(">");
							cases[14][5].setText(">");
							cases[14][9].setText("<");
							cases[14][10].setText("7");
						}
						if(niveau.equals("moyen"))
						{
							cases[0][3].setText("<");
							cases[0][5].setText("<");
							cases[0][6].setText("6");
							cases[0][9].setText(">");
							cases[0][12].setText("2");
							cases[1][0].setText("^");
							cases[1][8].setText("^");
							cases[1][12].setText("v");
							cases[2][4].setText("6");
							cases[2][5].setText(">");
							cases[2][9].setText("<");
							cases[4][5].setText(">");
							cases[4][11].setText(">");
							cases[5][10].setText("^");
							cases[5][12].setText("v");
							cases[6][3].setText(">");
							cases[6][7].setText(">");
							cases[7][0].setText("v");
							cases[7][2].setText("v");
							cases[7][8].setText("^");
							cases[7][14].setText("v");
							cases[8][3].setText(">");
							cases[8][9].setText("<");
							cases[9][0].setText("v");
							cases[9][14].setText("^");
							cases[10][1].setText("<");
							cases[11][4].setText("v");
							cases[12][9].setText("<");
							cases[13][6].setText("v");
							cases[13][12].setText("^");
							cases[14][1].setText("<");
							cases[14][3].setText("<");
							cases[14][10].setText("2");
							cases[14][12].setText("5");
							cases[14][13].setText(">");
						}
						if(niveau.equals("difficile"))
						{
							cases[0][3].setText("<");
							cases[0][5].setText("<");
							cases[0][6].setText("6");
							cases[0][9].setText(">");
							cases[0][12].setText("2");
							cases[1][0].setText("^");
							cases[1][8].setText("^");
							cases[2][4].setText("6");
							cases[2][5].setText(">");
							cases[2][9].setText("<");
							cases[4][5].setText(">");
							cases[4][11].setText(">");
							cases[5][10].setText("^");
							cases[5][12].setText("v");
							cases[6][3].setText(">");
							cases[6][7].setText(">");
							cases[7][0].setText("v");
							cases[7][2].setText("v");
							cases[7][8].setText("^");
							cases[7][14].setText("v");
							cases[8][3].setText(">");
							cases[8][9].setText("<");
							cases[9][0].setText("v");
							cases[9][14].setText("^");
							cases[10][1].setText("<");
							cases[11][4].setText("v");
							cases[12][9].setText("<");
							cases[13][6].setText("v");
							cases[13][12].setText("^");
							cases[14][1].setText("<");
							cases[14][3].setText("<");
							cases[14][10].setText("2");
							cases[14][12].setText("5");
							cases[14][13].setText(">");
							
						}
						
						break;
					//pour 9x9	
					case 9 :
						if(niveau.equals("facile"))
						{
							cases[0][1].setText(">");
							cases[0][2].setText("6");
							cases[0][9].setText(">");
							cases[0][16].setText("5");
							cases[1][0].setText("^");
							cases[1][6].setText("v");
							cases[1][12].setText("v");
							cases[2][7].setText(">");
							cases[2][15].setText("<");
							cases[3][2].setText("^");
							cases[3][12].setText("v");
							cases[4][5].setText(">");
							cases[4][7].setText("<");
							cases[4][12].setText("3");
							cases[5][0].setText("^");
							cases[5][6].setText("v");
							cases[6][0].setText("2");
							cases[6][5].setText("<");
							cases[6][7].setText(">");
							cases[6][11].setText("<");
							cases[6][13].setText(">");
							cases[6][15].setText(">");
							cases[7][4].setText("v");
							cases[7][6].setText("^");
							cases[7][12].setText("^");
							cases[7][16].setText("v");
							cases[8][15].setText("<");
							cases[9][0].setText("^");
							cases[9][2].setText("v");
							cases[9][6].setText("^");
							cases[10][2].setText("4");
							cases[10][4].setText("6");
							cases[10][7].setText("<");
							cases[11][16].setText("v");
							cases[12][11].setText(">");
							cases[13][4].setText("^");
							cases[13][6].setText("^");
							cases[13][10].setText("^");
							cases[13][16].setText("^");
							cases[14][9].setText(">");
							cases[14][11].setText("<");
							cases[15][16].setText("^");

						}
						if(niveau.equals("moyen"))
						{
							cases[0][0].setText("5");
							cases[0][7].setText(">");
							cases[1][2].setText("v");
							cases[1][10].setText("v");
							cases[1][12].setText("v");
							cases[1][14].setText("v");
							cases[2][11].setText(">");
							cases[3][0].setText("v");
							cases[3][4].setText("v");
							cases[3][14].setText("v");
							cases[4][3].setText("<");
							cases[4][7].setText("<");
							cases[4][9].setText(">");
							cases[4][15].setText(">");
							cases[6][7].setText("<");
							cases[7][0].setText("^");
							cases[7][4].setText("v");
							cases[7][10].setText("v");
							cases[7][14].setText("^");
							cases[8][2].setText("7");
							cases[8][3].setText("<");
							cases[8][7].setText("<");
							cases[8][13].setText(">");
							cases[9][0].setText("^");
							cases[9][8].setText("^");
							cases[9][10].setText("v");
							cases[10][2].setText("5");
							cases[10][5].setText(">");
							cases[10][11].setText(">");
							cases[11][4].setText("v");
							cases[11][16].setText("^");
							cases[12][5].setText(">");
							cases[12][7].setText(">");
							cases[12][11].setText(">");
							cases[12][12].setText("6");
							cases[13][12].setText("v");
							cases[14][4].setText("3");
							cases[15][0].setText("^");
							cases[15][4].setText("^");
							cases[15][10].setText("^");
							cases[15][14].setText("^");
							cases[16][8].setText("9");
							cases[16][14].setText("4");
								
						}
						if(niveau.equals("difficile"))
						{
							cases[0][0].setText("8");
							cases[0][2].setText("2");
							cases[0][9].setText(">");
							cases[0][15].setText("<");
							cases[1][8].setText("^");
							cases[2][1].setText(">");
							cases[2][5].setText(">");
							cases[2][8].setText("5");
							cases[3][6].setText("v");
							cases[4][2].setText("6");
							cases[4][6].setText("2");
							cases[4][16].setText("8");
							cases[5][4].setText("v");
							cases[6][6].setText("4");
							cases[7][8].setText("^");
							cases[7][10].setText("^");
							cases[7][16].setText("^");
							cases[8][8].setText("4");
							cases[9][10].setText("^");
							cases[10][3].setText("<");
							cases[11][4].setText("^");
							cases[11][6].setText("^");
							cases[11][10].setText("v");
							cases[11][12].setText("^");
							cases[11][16].setText("^");
							cases[12][3].setText(">");
							cases[12][4].setText("6");
							cases[13][0].setText("^");
							cases[13][8].setText("^");
							cases[14][0].setText("2");
							cases[14][2].setText("4");
							cases[14][3].setText(">");
							cases[14][7].setText("<");
							cases[14][10].setText("9");
							cases[14][16].setText("1");
							cases[15][8].setText("^");
							cases[16][0].setText("5");
							cases[16][1].setText(">");
							cases[12][7].setText("<");
							cases[12][8].setText("7");
							cases[12][9].setText(">");
							cases[12][11].setText(">");
						}
						break;
					
				}
				
                // mettre les cases remplies et les cases qui contiennent des contraintes disabled 
				for(int i=0;i<cases.length;i++)
					for(int j=0;j<cases.length;j++)
					{
						if(!cases[i][j].getText().isEmpty())
							cases[i][j].setEditable(false);
						
						if(cases[i][j].getType() == caseInput.Hor_Constraint || cases[i][j].getType() == caseInput.Ver_Constraint)
							cases[i][j].setBackground(new java.awt.Color(166, 189, 219));
					}

				panel_jeu.repaint();
				panel_jeu.revalidate();

				getContentPane().add(panel_jeu, BorderLayout.CENTER);
				pack();

		// Les algorithmes
		
		JLabel algoLabel = new JLabel("ALgorithmes à appliquer : ");
                algoLabel.setBackground(Color.WHITE);
		algoLabel.setForeground(new Color(121, 48, 70 ));
		algoLabel.setFont(new Font("SimSun", Font.BOLD, 11));
		panel.add(algoLabel);
		
		algo_MRV = new JCheckBox("MRV");
                algo_MRV.setBackground(new Color(121, 48, 70 ));
                algo_MRV.setForeground(Color.WHITE);
		panel.add(algo_MRV);
		
		algo_Degree = new JCheckBox("Degree");
                algo_Degree.setBackground(new Color(121, 48, 70 ));
                algo_Degree.setForeground(Color.WHITE);
		panel.add(algo_Degree);
		
		algo_LCV = new JCheckBox("LCV");
                algo_LCV.setBackground(new Color(121, 48, 70 ));
                algo_LCV.setForeground(Color.WHITE);
		panel.add(algo_LCV);
		
		algo_Fc = new JCheckBox("FC");
                algo_Fc.setBackground(new Color(121, 48, 70 ));
                algo_Fc.setForeground(Color.WHITE);
		panel.add(algo_Fc);
		
		algo_Ac = new JCheckBox("AC");
                algo_Ac.setBackground(new Color(121, 48, 70 ));
                algo_Ac.setForeground(Color.WHITE);
		panel.add(algo_Ac);
                
                
                //bouton recommencer
		JButton nvpartie = new JButton("Recommencer");
                nvpartie.setBackground(new Color(121, 48, 70 ));
                nvpartie.setForeground(Color.WHITE);
		nvpartie.setFont(new Font("SimSun", Font.BOLD | Font.ITALIC, 11));
		nvpartie.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
                            // Instancier la classe menu et l'afficher
                            menu mn = new menu("menu du jeu");
                            mn.setLocationRelativeTo(null);
                            p.setVisible(false);// jframe actuelle
                            mn.setVisible(true);
                            mn.setLocationRelativeTo(null);
                            mn.setSize(new Dimension(350, 100));
                            mn.setVisible(true);
			}
		});
		panel.add(nvpartie);
                
                //bouton de la solution
		JButton solution = new JButton("Solution");
                solution.setBackground(new Color(121, 48, 70 ));
                solution.setForeground(Color.WHITE);
		solution.setFont(new Font("SimSun", Font.BOLD | Font.ITALIC, 11));
		solution.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				
				solution();

				panel_jeu.repaint();
				panel_jeu.revalidate();
				pack();
				setVisible(true);
			}
		});
		panel.add(solution);
                
                //bouton de la verification
		JButton verifier = new JButton("Verifier");
                verifier.setBackground(new Color(121, 48, 70 ));
                verifier.setForeground(Color.WHITE);
		verifier.setFont(new Font("SimSun", Font.BOLD | Font.ITALIC, 11));
		verifier.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				boolean correct = verification(cases);
				if(correct)
					JOptionPane.showMessageDialog(null, "Votre solution est : "+ (correct? "correct!!" : "incorrect") );
			}
		});
		panel.add(verifier);
		
		panel_jeu.repaint();
		panel_jeu.revalidate();
		pack();
		setVisible(true);
	}
	
	// fonction de verification de solution de l'utilisateur
	public boolean verification(caseInput[][] cases)
	{
		for(int i=0; i<cases.length; i++)
		{
			for(int j=0; j<cases.length;j++)
			{		// si les cases sont vides
				if(cases[i][j].getType() == caseInput.NUMBER)
				{
					if(cases[i][j].getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "il reste encore des cases à remplir !");
						return false;
					}
					
					try
					{	// si la valeur ajouter est sup a la taille de grille ou inf a 1
						int val = Integer.parseInt(cases[i][j].getText());
						if(val > (cases.length+1)/2 || val < 1)
						{
							JOptionPane.showMessageDialog(null, "la valeur "+val+" dans la case "+i+" , "+j + " est invalide!!");
							return false;
						}
						if(j<cases.length-1)
						{ 		// si il existe une valeur dans la meme ligne ou colonne 
							for(int k=j+2;k<cases.length;k+=2)
							{
								int val2 = Integer.parseInt(cases[i][k].getText());
								if(val == val2)
								{
									JOptionPane.showMessageDialog(null, "la valeur "+val+" dans la case "+i+" , "+j + " existe deja dans la meme ligne!!");
									return false;
								}
							}	
						}
						if(i<cases.length-1)
						{
							for(int k=i+2;k<cases.length;k+=2)
							{
								int val2 = Integer.parseInt(cases[k][j].getText());
								if(val == val2)
								{
									JOptionPane.showMessageDialog(null, "la valeur "+val+" dans la case "+i+" , "+j + " existe deja dans la meme colonne!!");
									return false;
								}
										
							}
						}
						
					}catch(NumberFormatException e)
					{
						e.printStackTrace();
					}
					
				}
				
				// verification des contraines < et >
				if((cases[i][j].getType() == caseInput.Hor_Constraint || cases[i][j].getType() == caseInput.Ver_Constraint) && !cases[i][j].getText().isEmpty())
				{
					switch(cases[i][j].getType())
					{
					case caseInput.Hor_Constraint : 
						boolean condH = cases[i][j].getText().equals(">");
						try 
						{
							int val1 = Integer.parseInt(cases[i][j-1].getText());
							int val2 = Integer.parseInt(cases[i][j+1].getText());
							
							
							if( (condH && (val1 < val2)) || (!condH && (val1 > val2)) )
							{
								JOptionPane.showMessageDialog(null, val1+" la valeur dans la case "+i+","+(j-1) + "doit etre "+cases[i][j].getText()+" a"+val2+" dans la case "+i+","+(j+1) + " !!!");
								return false;
							}
						}catch(NumberFormatException e)
						{
							e.printStackTrace();
						}
						break;
					case caseInput.Ver_Constraint : 
						boolean condV = cases[i][j].getText().equals("v");
						try 
						{
							int val1 = Integer.parseInt(cases[i-1][j].getText());
							int val2 = Integer.parseInt(cases[i+1][j].getText());
							if( (condV && (val1 < val2)) || (!condV && (val1 > val2)) )
							{
								JOptionPane.showMessageDialog(null, val1+" la valeur dans la case "+i+","+(j-1) + "doit etre "+cases[i][j].getText()+" a"+val2+" dans la case "+i+","+(j+1) + " !!!");
								return false;
							}
						}catch(NumberFormatException e)
						{
							e.printStackTrace();
						}
						break;
						default : System.out.println("error"); return false;
					}
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void solution()
	{
	       int l = cases.length;
	       int n = (l+1)/2;  //taille de la grille
	       //Creation du graphe
	       Graph g = new Graph();
	       
       
                   // Contraintes au niveau des lignes
                for(int i=0;i<n;i++)       // Ligne 
                   for(int j=0;j<n-1;j++)    // Colonne
                        for(int k=j+1;k<n;k++){
                            String var1 ="x"+i+""+j;
                            String var2 ="x"+i+""+k;

                            g.addEdge(var1, var2);
                        }

                // Contaraintes au niveau des colonnes
                for(int i=0;i<n;i++)       // Colonne 
                   for(int j=0;j<n-1;j++)      // Ligne
                        for(int k=j+1;k<n;k++){
                            String var1 ="x"+j+""+i;
                            String var2 ="x"+k+""+i;

                            g.addEdge(var1, var2);
                        }
            
	       	// containtes < >
	       for(int i=0;i<l;i++)
	       {
	    	   for(int j=0;j<l;j++)
	    	   {
                       //si il s'agit d'une contraine horizontale on l'ajoute au table contraintes_Horizontales
	    		   if(cases[i][j].getType()==caseInput.Hor_Constraint && !cases[i][j].getText().isEmpty())
	    		   {
    				   contraintes_Horizontales[i/2][j/2] = cases[i][j].getText().charAt(0);
	    		   }
                           //si il s'agit d'une contraine verticale on l'ajoute au table contraintes_Verticales
	    		   if(cases[i][j].getType()==caseInput.Ver_Constraint && !cases[i][j].getText().isEmpty())
	    		   {
	    			   contraintes_Verticales[i/2][j/2] = cases[i][j].getText().charAt(0);
	    		   } 
	    	   }
	       }
	       
	       for(int i=0;i<n;i++)
	       {
	    	for(int j=0;j<n;j++)
	    	{
                    //
	    		if( (i< n-1) && (contraintes_Verticales[i][j]=='^' || contraintes_Verticales[i][j] == 'v'))
	    		{
	    			boolean condv = contraintes_Verticales[i][j] == 'v';
	    			//si V ou ^
	    			String var1 = condv? "s" + i + "" + j : "s" + (i+1) + "" + j;
	    			String var2 = condv? "x" + (i+1) + "" + j : "x" + i + "" + j;
	    			//ajouter au graphe 
	    			g.addEdge(var1, var2);
	    			
	    			var1 = var1.replace("s", "x");
	    			var2 = var2.replace("x", "i");
	    			//ajouter au graphe 
	    			g.addEdge(var1, var2);
	    		}
	    		
	    		if(( j < n-1 ) && (contraintes_Horizontales[i][j]=='>' || contraintes_Horizontales[i][j] == '<'))
	    		{
	    			//si > ou <
	    			boolean condsup = contraintes_Horizontales[i][j] == '>';
	    			
	    			String val1 = condsup? "s" + i + "" + j : "s" + i + "" + (j+1);
	    			String val2 = condsup? "x" + i + "" + (j+1) : "x" + i + "" + j;

	    			//ajouter au graphe 
	    			g.addEdge(val1, val2);

	    			val1 = val1.replace("s", "x");
	    			val2 = val2.replace("x", "i");
	    			//ajouter au graphe 
	    			g.addEdge(val1, val2);
	    			
	    		}
	    	}
	       }
	       
	    // Tables des domaines
	       ST<String, SET<String>> domainTable = new ST<String, SET<String>>();

	    // Les Domaines
	       Object[][] domains = new Object[n][n];
	       
	       for(int i=0;i<l;i++)
	       {
	    	   for(int j=0;j<l;j++)
	    	   {
	    		   if(!cases[i][j].getText().isEmpty() && cases[i][j].getType()==caseInput.NUMBER)
	    		   {
	    			   //si la case est deja remplie
	    			   domains[i/2][j/2] = new SET<String>();
                                    ((SET<String>)domains[i/2][j/2]).add(cases[i][j].getText()); 
	    		   }
	    		   else if(cases[i][j].getText().isEmpty() && cases[i][j].getType()==caseInput.NUMBER)
	    		   {
                               //si la case est vide
	    			   domains[i/2][j/2] = new SET<String>();
	    			   for(int k=1; k<=n; k++)// k< taille de grille () dynamique
    				   {
	    				   ((SET<String>)domains[i/2][j/2]).add(""+k);
    				   }
	    		   } 
	    	   }
	       }

	       //ajouter les domaine
	       for(int i=0;i<n;i++)       
	       {
	    	   for(int j=0;j<n;j++)
	    	   {
	    		   domainTable.put("x"+i+""+j, ((SET<String>)domains[i][j]));
	    	   }
	               
	       }
	       
	       
	       //config
	       ST<String, String> config = new ST<String, String>();
	       
	       for(int i=0;i<n;i++)       // Ligne 
	    	   for(int j=0;j<n;j++)   // Colonne
	           config.put("x"+i+""+j,""); // Variables non affectees
	       
	       System.out.println("\nCalcul en cours ... "); 
	       Backtracking backtracking = new Backtracking(this);
	       //choix des algos
	       backtracking.WITHMRV =  algo_MRV.isSelected();        
	       backtracking.WITHDEGRES = algo_Degree.isSelected();       
	       backtracking.WITHLCV = algo_LCV.isSelected();       
	       backtracking.WITHFC = algo_Fc.isSelected();         
	       backtracking.WITHAC1 = algo_Ac.isSelected();
	       
	       ST<String, String> result = backtracking.backtracking(config, domainTable, g);

	       // La solution
	       for(int i=0;i<n;i++)
	       {
	    	   for(int j=0;j<n;j++)
	    	   {
	    		   cases[i*2][j*2].setText(config.get("x"+i+""+j));
	    	   }
	       }
	}
    
}
