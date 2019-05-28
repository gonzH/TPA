/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package benchProjeto;

import dicionario.*;
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
public class TADDicChain {
    private LinkedList vetBuckets[] = null; //lista principal
    private double fator_de_carga = 0.75; 
    private int qtd_entradas = 0;
    private Hash_engine he = null;
    private boolean achou = false;
    private long lastHash;
    
  
    public TADDicChain(Hash_engine he) {
        int tam = 1024;
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
    
    public TADDicChain(int tam, Hash_engine he) {
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
    
    public void insertItem(Object chave, Object valor) {
        
        if(lenMaiorLst() >= (int)getSizeVetBuckets() * 0.30) {           
            redimensiona();
        }

        //ideia do lastHash, atributo do dicionario que guarda o ultimo hash calculado
        Object aux = findElement(chave);

        //garante que meu indice nunca seja maior que o tamanho do vetor
        int indice = (int)this.lastHash % getSizeVetBuckets();

        if(NO_SUCH_KEY()) {
            TDicItem dicItem = new TDicItem(chave, valor);
            dicItem.setCache_hash(this.lastHash);
            vetBuckets[indice].add(dicItem);
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
        this.lastHash = he.hash_func(chave);
        int indice = (int)this.lastHash % getSizeVetBuckets();
        
        int posList = 0;
        while(posList < vetBuckets[indice].size()) {
            if(((TDicItem)vetBuckets[indice].get(posList)).getChave().equals(chave)) {
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
            return null;
        }
        else {
//            long cod_hash = he.hash_func(chave);
            
            int indice = (int)this.lastHash % getSizeVetBuckets();
            
            int posItem = buscaDicItem(vetBuckets[indice], chave);
            vetBuckets[indice].remove(posItem);
            qtd_entradas--;
            
            return aux;
        }
    }
    
    public boolean isEmpty() {
        if(size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
   
    /* funcoes do livro */
    public int size() {
        return qtd_entradas;
    }
    
    /**
     * Quando findElements() não encontra a chave buscada
     * @return
     */
    public boolean NO_SUCH_KEY() {
        return !achou;
    }
    
    public LinkedList<Object> keys() {
        if(isEmpty()) {
            return null;
        }
        else {
            LinkedList<TDicItem> iterador = getItens();
            LinkedList<Object> saida = new LinkedList<Object>();
            
            for(TDicItem t : iterador) {
                saida.add(t.getChave());
            }
            
            return saida;
        
        }
    }
    
    public LinkedList<Object> elements() {
        if(isEmpty()) {
            return null;
        }
        else {
            LinkedList<TDicItem> iterador = getItens();
            LinkedList<Object> saida = new LinkedList<Object>();
            
            for(TDicItem t : iterador) {
                saida.add(t.getValor());
            }
            
            return saida;
        
        }
        
    }
    
    /**
     * Clonagem superficial de um dicionário existente, apenas o dado não é clonado
     * @return dicionarioClonado
     */
    public TADDicChain clone() {
        TADDicChain dicClone = new TADDicChain(he);
        
        for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
            for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                Object chave = ((TDicItem)vetBuckets[posVet].get(posList)).getChave();
                Object valor = ((TDicItem)vetBuckets[posVet].get(posList)).getValor();
                
                dicClone.insertItem(chave, valor);
            }
        }
        
        return dicClone;
    }
    
    /**
     * Dicionários são iguais se possuirem a mesma quantidade de entrada, função
     * de hashing, chaves e valores associados
     * @param outroDic
     * @return true/false
     */
    public boolean equals(TADDicChain outroDic) {
        if(he == outroDic.getHashEngine()) {
            if(this.size() == outroDic.size()) {
                for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
                    for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                        Object chave = ((TDicItem)(vetBuckets[posVet].get(posList))).getChave();
                        Object dado = ((TDicItem)(vetBuckets[posVet].get(posList))).getValor();
                        
                        Object outroDado = outroDic.findElement(chave);
                        if(outroDic.NO_SUCH_KEY() || (dado != outroDado)) {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    } 
    
    private void redimensiona(){
        int newTam = (int)(1.5 * getSizeVetBuckets());
        LinkedList[] novoVetBuckets = new LinkedList[newTam];
        
        for( int i = 0; i < newTam; i++) {
            novoVetBuckets[i] = new LinkedList<TDicItem>();
        }
        
        for(int posVet = 0; posVet < getSizeVetBuckets(); posVet++) {
            if(vetBuckets[posVet] != null) {
                for(int posList = 0; posList < vetBuckets[posVet].size(); posList++) {
                    Object aux = vetBuckets[posVet].get(posList);
                    
                    long cod_hash = ((TDicItem)aux).getCache_hash();
                    int indice = (int)cod_hash % novoVetBuckets.length;
                    
                    novoVetBuckets[indice].add(aux);
                }
            }
        }
        
        vetBuckets = novoVetBuckets;
    }
    
    public LinkedList getItens() {
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
    
    public int getSizeVetBuckets() {
        return vetBuckets.length;
    }

    private int buscaDicItem(LinkedList<TDicItem> lst, Object str) {
        int posList = 0;
        
        while(posList < lst.size()) {
            if(((TDicItem)(lst.get(posList))).getChave().equals(str)) {
                return posList;
            }
            posList++;
        }
        
        return -1;
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

    public Hash_engine getHashEngine() {
        return this.he;
    }
  
    /**************************************************
     * Exercicios pedidos no laboratorio
     **************************************************/
    // retorna a qtd de colisoes por posicao do vetor de buckets
    public int[] getVetColisoes() {
        
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
}
