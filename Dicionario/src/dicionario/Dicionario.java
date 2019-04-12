/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import dicionario.v1.RegDadosV1;
import dicionario.v1.TADDicionarioV1;
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
        
        //TESTES DA VERSAO 4 DO DICIONARIO
        TADDicionarioV4 dic = new TADDicionarioV4(10);
        
        dic.insertItem(56423165, new RegHost(1234, "Hellesandro", 12, 34));
        dic.insertItem(56423165, new RegHost(5678, "Salzman", 56, 78));
        dic.insertItem(56423165, new RegHost(9101, "Serenna", 91, 01));
        
        List saida = new LinkedList<RegHost>();
        saida = dic.elements();
        
        for( Object i : saida ) {
            System.out.println("Nome: " + ((RegHost)i).getNome() +);
        }
        
        
        
        
        
        
        
        
        
        
        //TESTES DA VERSAO 2 DO DICIONARIO
        
        /*TADDicionarioV1 vet = new TADDicionarioV1(4);
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
        });*/
        
        
    }
    
}
