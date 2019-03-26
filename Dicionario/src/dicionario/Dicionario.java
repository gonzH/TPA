/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author helle
 */
public class Dicionario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TADDicionario vet = new TADDicionario(10);
        RegDados dado1 = new RegDados("Oi", "Hi");
        RegDados dado2 = new RegDados("Hello", "Olá");
        RegDados dado3 = new RegDados("Hey", "Olá");
        vet.insertItem("Oi", dado1);
        vet.insertItem("Hello", dado2);
        vet.insertItem("Hey", dado3);
        
        List saida = new LinkedList<RegDados>();
        saida = vet.elements();
        
        for( Object i : saida) {
            System.out.println(((RegDados)i).getPalPt() + ((RegDados)i).getPalEng());
        }
        //System.out.println(saida);
        
        
        
        
    }
    
}
