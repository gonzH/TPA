/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

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
        TADDicionarioV4 dic = new TADDicionarioV4(5);
        
        dic.insertItem(123456, new RegHost(1234, "Chakuro", 12, 34));
        dic.insertItem(910111, new RegHost(5678, "Lykos", 56, 78));
        dic.insertItem(314151, new RegHost(9101, "Ouni", 91, 01));
        dic.insertItem(718192, new RegHost(1112, "Suoh", 11, 12));
        System.out.println("Inserção completa!");
        
        RegHost reg1 = (RegHost) dic.findElement(12345678);
        
        
         if(reg1 != null){
            System.out.println(reg1.getIp()+", "+reg1.getNome());
        }
        else{
            System.out.println("Erro 404 not found!");
        }
        
        System.out.println("Tamanho do dicionario antes de alterar Chakuro" + dic.size());
        dic.insertItem(123456, new RegHost(1234,"Chakuro",33,4));
        System.out.println("Tamanho do dicionario após alterar chave Chakuro" + dic.size());
        
        System.out.println("Removendo host Suoh do dicionario..");
        dic.removeElement(718192);
        System.out.println("Tamanho do dicionario após a remoção do host Suoh" + dic.size());
        reg1 = (RegHost)dic.findElement(718192);
        
        if(reg1 != null){
            System.out.println(reg1.getIp()+", "+reg1.getNome());
        }
        else{
            System.out.println("Erro 404 not found!");
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
