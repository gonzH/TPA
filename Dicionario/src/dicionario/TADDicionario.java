/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

import java.util.*;
import dicionario.RegDados.*;
/**
 *
 * @author helle
 */
public class TADDicionario {
    private LinkedList vetBuckets[] = null; //lista principal
    private double fator_de_carga = 0.75; 
    private int qtd_entradas = 0;
    
    public TADDicionario(int qtdEntradas) {
        int tam = (int)(qtdEntradas/fator_de_carga);
        
        vetBuckets = new LinkedList[tam];
        
        for( int i = 0; i < tam; i++) {
            vetBuckets[i] = new LinkedList<String>();
        }
    }
    
    public TADDicionario() {
        vetBuckets = new LinkedList[100];
        
        for( int i = 0; i < 100; i++) {
            vetBuckets[i] = new LinkedList<String>();
        }
    }
    
    public int getSizeVetBuckets() {
        return vetBuckets.length;
    }
    
    /* funcao de calculo de hash */
    private long hashFunc(String s) {
        long soma = 0;
        
        for(int i = 0; i < s.length(); i++) {
            soma = soma + (int)s.charAt(i);
        }
        
        System.out.println("Hash gerado: " + soma);
        return soma;
    }
    
    /* funcoes do livro */
    public int size() {
        return qtd_entradas;
    }
    
    public boolean isEmpty() {
        if(qtd_entradas == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public List elements() {
        List iterador = new LinkedList<RegDados>();
        
        if(qtd_entradas == 0) {
            return iterador;
        }
        else {
            for(int posVet = 0; posVet < vetBuckets.length; posVet++) {
                if(vetBuckets[posVet].size() > 0) {
                    for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                        iterador.add(vetBuckets[posVet].get(posList));
                    }
                }
            }
            
            return iterador;
        }
    }
    
    public void insertItem(String chave, RegDados valor) {
        RegDados aux = findElement(chave);
        
        if(aux == null) {
            long cod_hash = hashFunc(chave);
            //garante que meu indice nunca seja maior que o tamanho do vetor
            int indice = (int)cod_hash % vetBuckets.length;
            
            vetBuckets[indice].add(valor);
            qtd_entradas++;   
        }
        else {
            aux.setPalEng(valor.getPalEng());
        } 
    }
    
    public RegDados findElement(String chave) {
        long cod_hash = hashFunc(chave);
        int indice = (int)cod_hash % vetBuckets.length;
        
        int posList = 0;
        while(posList < vetBuckets[indice].size()) {
            if((((RegDados)vetBuckets[indice].get(posList))).getPalPt().equalsIgnoreCase(chave))
                return (RegDados)vetBuckets[indice].get(posList);
            posList++;
        }
        
        return null;
    }
    
    /* enunciado pede para retornar regdados*/
    public RegDados removeElement(String chave) {
        RegDados aux = findElement(chave);
        
        if(aux == null) {
            return null;
        }
        else {
            long cod_hash = hashFunc(chave);
            int indice = (int)cod_hash % vetBuckets.length;

            int posList = 0;
            while(posList < vetBuckets[indice].size()) {
                posList++;
            }
            
            vetBuckets[indice].remove(posList-1);
            qtd_entradas--;
            
            return aux;
        }
    }
    
    
    
    
}
