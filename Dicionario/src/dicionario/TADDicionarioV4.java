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
        int tam = 5;
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
    /*private long hashFunc(String s) {
        long soma = 0;
        
        for(int i = 0; i < s.length(); i++) {
            soma = soma + (int)s.charAt(i);
        }
        
        System.out.println("Hash gerado: " + soma);
        return soma;
    }*
    
    // 31 elevado
    private long hashFuncPol(String s) {
        long soma = 0;
        
        for(int i = 0; i < s.length(); i++) {
            soma = soma + 31^(int)s.charAt(i);
        }
        
        System.out.println("Hash gerado: " + soma);
        return soma;
    }*/
    
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
        if(lenMaiorLst() >= (int)getSizeVetBuckets() * 0.30) {
            System.out.println("Redimensionando...");
            System.out.println("Tamanho atual vetBuckets: " + getSizeVetBuckets());
            System.out.println("Tamanho maior lista vetBuckets original: " + lenMaiorLst());
            
            redimensiona();
            
            System.out.println("Novo tamanho atual vetBuckets: " + getSizeVetBuckets());
            System.out.println("Tamanho maior lista novo vetBuckets: " + lenMaiorLst());
        }
        
        Object aux = findElement(chave);
        
        long cod_hash = he.hash_func(chave);
        //garante que meu indice nunca seja maior que o tamanho do vetor
        int indice = (int)cod_hash % getSizeVetBuckets();
        
        if(NO_SUCH_KEY()) {
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
    
    /**
     * remove um elemento em uma dada posição, se a chave a ser removida não
     * existe, então nada é feito.
     * @param chave
     * @return null/ objeto removido
     */
    public Object removeElement(Object chave) {
        Object aux = findElement(chave);
        
        if(NO_SUCH_KEY()) {
            achou = false;
            return null;
        }
        else {
            long cod_hash = he.hash_func(chave);
            int indice = (int)cod_hash % getSizeVetBuckets();
            
            int posItem = buscaDicItem(vetBuckets[indice], chave);
            vetBuckets[indice].remove(posItem);
            qtd_entradas--;
            achou = true;
            
            return aux;
        }
    }
    
    public LinkedList<Object> keys() {
        if(isEmpty()) {
            return null;
        }
        else {
            LinkedList<Object> iterador = new LinkedList<Object>();
            
            for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                if(vetBuckets[posVet].size() > 0) {
                    for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                        iterador.add(((TDicItem)vetBuckets[posVet].get(posList)).getChave());
                    } // for(int posList ...
                } // if(vetBuckets[posVe...
            } // for(int posVet = 0; posVe...
            return iterador;
        } // else {   
    }
    
    private int lenMaiorLst() {
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
        int newTam = 2 * getSizeVetBuckets();
        LinkedList[] novoVetBuckets = new LinkedList[newTam];
        
        for( int i = 0; i < newTam; i++) {
            novoVetBuckets[i] = new LinkedList<TDicItem>();
        }
        
        for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
            if(vetBuckets[posVet] != null) {
                for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                    Object aux = vetBuckets[posVet].get(posList);
                    
                    long cod_hash = he.hash_func(((TDicItem)aux).getChave());
                    int indice = (int)cod_hash % novoVetBuckets.length;
                    
                    novoVetBuckets[indice].add(aux);
                }
            }
        }
        
        vetBuckets = novoVetBuckets;
    }
    
    public Hash_engine getHashEngine() {
        return this.he;
    }
    
    /**
     * Quando findElements() não encontra a chave buscada
     * @return
     */
    public boolean NO_SUCH_KEY() {
        return !achou;
    }
    
    /**
     * Dicionários são iguais se possuirem a mesma quantidade de entrada, função
     * de hashing, chaves e valores associados
     * @param outroDic
     * @return true/false
     */
    public boolean equals(TADDicionarioV4 outroDic) {
        if(he == outroDic.getHashEngine()) {
            if(this.size() == outroDic.size()) {
                for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                    for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                        Object chave = ((TDicItem)(vetBuckets[posVet].get(posList))).getChave();
                        Object dado = ((TDicItem)(vetBuckets[posVet].get(posList))).getValor();
                        
                        Object outroDado = outroDic.findElement(chave);
                        if(outroDic.NO_SUCH_KEY() || (dado != outroDado)) {
                            return false;
                        } // if(outroDic.NO_SUCH_K...
                    } // for(int posList = 0...
                } // for(int posVet = 0; p...
            } // if(this.size() == out...
        } // if(he == outroDic...
        
        return true;
    } 
    
    /**
     * Clonagem superficial de um dicionário existente, apenas o dado não é clonado
     * @return dicionarioClonado
     */
    public TADDicionarioV4 clone() {
        TADDicionarioV4 dicClone = new TADDicionarioV4(he);
        
        for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
            for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                Object chave = ((TDicItem)vetBuckets[posVet].get(posList)).getChave();
                Object valor = ((TDicItem)vetBuckets[posVet].get(posList)).getValor();
                dicClone.insertItem(chave, valor);
            }
        }
        
        return dicClone;
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
        