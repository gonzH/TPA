/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

/**
 *
 * @author helle
 */
public class TDicItem {
    private Object chave;
    private Object valor;
    private long cache_hash;
    
    public TDicItem(Object chave, Object valor) {
        this.chave = chave;
        this.valor = valor;
    }
    
    public Object getChave() {
        return chave;
    }

    public long getCache_hash() {
        return cache_hash;
    }

    public void setCache_hash(long cache_hash) {
        this.cache_hash = cache_hash;
    }
    
    public Object getValor() {
        return valor;
    }
    
    public void setChave(Object chave) {
        this.chave = chave;
    }
    
    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    
}
