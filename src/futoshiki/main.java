/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshiki;
import java.awt.Dimension;
/**
 *
 * @author hajar
 */
public class main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
       /*Instancier la classe menu et afficher Jframe pour choisir les premiers parametres
        Taille de la grille et le niveau de difficulte
        */
       menu game = new menu("Menu");
       game.setLocationRelativeTo(null);
       game.setSize(new Dimension(350, 100));
       game.setVisible(true);
       
       
       
    }
    
    
    
    
    
}
