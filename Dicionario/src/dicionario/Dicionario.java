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
        TADDicionarioV1 vet = new TADDicionarioV1(4);
        RegDadosV1 dado1 = new RegDadosV1("Oi", "Hi");
        RegDadosV1 dado2 = new RegDadosV1("Olá", "Hello");
        RegDadosV1 dado3 = new RegDadosV1("Olá", "Hey");
        vet.insertItem("Oi", dado1);
        vet.insertItem("Olá", dado2);
        vet.insertItem("Olá", dado3);
        
        List saida = new LinkedList<RegDadosV1>();
        saida = vet.elements();
         
        for( Object i : saida) {
            System.out.println(((RegDadosV1)i).getPalPt() + ((RegDadosV1)i).getPalEng());
        }
        //System.out.println(saida);
        
        LinkedList<String> saida1 = vet.keys();
        
        saida1.forEach((i) -> {
            System.out.println(i);
        });
        
        
    }
    
}
