/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshiki;

import javax.swing.JTextField;

/**
 *
 * @author hajar
 */
public class caseInput extends JTextField 
{
        /*Type de la case:
        soit nombre, soit contrainte horizontale ou verticale soit vide
        */
	private int type;
        
        final static public int NUMBER = 0;
	final static public int Hor_Constraint = 1;
	final static public int Ver_Constraint = 2;
	final static public int EMPTY = -1;
        
        //la fonction setType
	
	public void setType(int type) 
	{
		this.type = type;
	}
        
        //la fonction GetType
	public int getType()
	{
		return type;
	}
        //Constructeur
	public caseInput(int type)
	{
		super();
		this.type = type;
	}
}
