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
public class DicionarioV2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TADDicionarioV2 vet = new TADDicionarioV2(4);
        
        vet.insertItem("Oi", new RegDadosV1("Oi", "Hi"));
        vet.insertItem("Olá", new RegDadosV1("Olá", "Hey"));
        vet.insertItem("Olá", new RegDadosV1("Olá", "Hello"));
        
        List saida = new LinkedList<TDicItem>();
        saida = vet.elements();
         
        for( Object i : saida) {
            System.out.println("Chave: " + ((TDicItem)i).getChave() + " | Valor: " + ((RegDadosV1)((TDicItem)i).getValor()).getPalEng());
            //System.out.println(((RegDadosV1)i).getPalPt() + ((RegDadosV1)i).getPalEng());
        }
        //System.out.println(saida);
        
        LinkedList<String> saida1 = vet.keys();
        
        saida1.forEach((i) -> {
            System.out.println(i);
        });
        
        
    }
    
}
