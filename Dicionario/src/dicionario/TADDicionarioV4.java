/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
/**
 *
 * @author helle
 */
public class TADDicionarioV4 {
    private LinkedList vetBuckets[] = null; //lista principal
    private double fator_de_carga = 0.75; 
    private int qtd_entradas = 0;
    private Hash_engine he = null;
    private boolean achou = false;
    
    public TADDicionarioV4(int qtdEntradas) {
        int tam = (int)(qtdEntradas/fator_de_carga);
        
        vetBuckets = new LinkedList[tam];
        
        for( int i = 0; i < tam; i++) {
            vetBuckets[i] = new LinkedList<TDicItem>();
        }
        
        if(he == null) {
            he = new HashEngineDefault();
        }
        else {
            this.he = he;
        }
    }
    
    public TADDicionarioV4() {
        vetBuckets = new LinkedList[100];
        
        for( int i = 0; i < 100; i++) {
            vetBuckets[i] = new LinkedList<TDicItem>();
        }
        
        if(he == null) {
            he = new HashEngineDefault();
        }
        else {
            this.he = he;
        }
    }
    
    public TADDicionarioV4(Hash_engine he) {
        int tam = 100;
        vetBuckets = new LinkedList[tam];
        
        for(int i = 0; i < tam; i++) {
            vetBuckets[i] = new LinkedList<TDicItem>();
        }
        
        if(he == null) {
            he = new HashEngineDefault();
        }
        else {
            this.he = he;
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
    
    // 31 elevado
    private long hashFuncPol(String s) {
        long soma = 0;
        
        for(int i = 0; i < s.length(); i++) {
            soma = soma + 31^(int)s.charAt(i);
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
    
    public LinkedList<TDicItem> elements() {
        LinkedList<TDicItem> iterador = new LinkedList<TDicItem>();
        
        if(isEmpty()) {
            return iterador;
        }
        else {
            for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                if(vetBuckets[posVet].size() > 0) {
                    for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                        iterador.add(((TDicItem)vetBuckets[posVet].get(posList)));
                    }
                }
            }
            
            return iterador;
        }
    }
    
    public int buscaDicItem(LinkedList<TDicItem> lst, Object str) {
        int posList = 0;
        
        while(posList < lst.size()) {
            if(((TDicItem)(lst.get(posList))).getChave().equals(str)) {
                return posList;
            }
            posList++;
        }
        
        return -1;
    }
    
    public void insertItem(Object chave, Object valor) {
        Object aux = findElement(chave);
        
        long cod_hash = he.hash_func(valor);
        //garante que meu indice nunca seja maior que o tamanho do vetor
        int indice = (int)cod_hash % getSizeVetBuckets();
        
        if(aux == null) {
            vetBuckets[indice].add(new TDicItem(chave, valor));
            qtd_entradas++;   
        }
        else {
            int pos = buscaDicItem(vetBuckets[indice], chave);
            if(pos != -1) {
                ((TDicItem)(vetBuckets[indice].get(pos))).setValor(valor);
            }
        } 
    }
    
    public Object findElement(Object chave) {
        long cod_hash = he.hash_func(chave);
        int indice = (int)cod_hash % getSizeVetBuckets();
        
        int posList = 0;
        while(posList < vetBuckets[indice].size()) {
            if((((TDicItem)vetBuckets[indice].get(posList))).getChave().equals(chave)) {
                achou = true;
                return ((TDicItem)(vetBuckets[indice].get(posList))).getValor();
            }
                
            posList++;
        }
        
        achou = false;
        return null;
    }
    
    /* enunciado pede para retornar regdados*/
    public Object removeElement(Object chave) {
        Object aux = findElement(chave);
        
        if(aux == null) {
            return null;
        }
        else {
            long cod_hash = he.hash_func(chave);
            int indice = (int)cod_hash % getSizeVetBuckets();

            int posList = 0;
            while(posList < vetBuckets[indice].size()) {
                posList++;
            }
            
            vetBuckets[indice].remove(posList-1);
            qtd_entradas--;
            
            return aux;
        }
    }
    
    public LinkedList<Object> keys() {
        LinkedList<Object> iterador = new LinkedList<Object>();
        
        if(isEmpty()) {
            return null;
        }
        else {
            for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                if(vetBuckets[posVet].size() > 0) {
                    for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                        iterador.add(((TDicItem)vetBuckets[posVet].get(posList)).getChave());
                    }
                }
            }
            return iterador;
        }    
    }
    
    private int lstMaiorLst() {
        int maior = 0;
        
        for(int i = 0; i < getSizeVetBuckets(); i++) {
            if(vetBuckets[i] != null) {
                if(vetBuckets[i].size() > maior) {
                    maior = vetBuckets[i].size();
                }
            }
        }
        
        return maior;
    }
    
    private void redimensiona(){
        int newTam = 2*vetBuckets.length;
        LinkedList[] novoVetBuckets = new LinkedList[newTam];
        
        for(int i = 0; i < getSizeVetBuckets(); i++) {
            if(vetBuckets[i] != null) {
                for(int j = 0; j <vetBuckets[i].size(); j++) {
                    Object aux = (TDicItem)vetBuckets[i].get(j);
                    
                    long cod_hash = he.hash_func(((TDicItem)vetBuckets[i].get(j)).getChave());
                    int indice = (int)cod_hash % novoVetBuckets.length;
                    
                    novoVetBuckets[indice].add(aux);
                }
            }
        }
        
        vetBuckets = novoVetBuckets;
    }
    
    
    
    
    
    
    
    
    
    
    /**************************************************
     * Exercicios pedidos no laboratorio
     **************************************************/
    // retorna a qtd de colisoes por posicao do vetor de buckets
    public int[] getColisoes() {
        
        if(isEmpty()) {
            return null;
        }
        else {
            int vetColisoes[] = new int[getSizeVetBuckets()];
            
            for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                vetColisoes[posVet] = vetBuckets[posVet].size();
            }
            
            return vetColisoes;
        }
    }
    
    //gera diagrama usando o matplotlib, relação colisao eixo y e pos no vetbuckets em grafico de barras
    public void exibeDiagrama(int vet[]) throws IOException { //vou gerar .csv pois nao tenho matplotlib
        
        FileWriter writer = null;
        try {
            writer = new FileWriter(".\\diagrama.csv", true);
            
            for(int posVet = 0;posVet < vet.length; posVet++) {
                writer.append(vet[posVet] + "," + posVet + "\n");
            }
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            writer.close();
        }
    }
}
        