/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 *
 * @author helle
 */
public class HashBernstein extends Hash_engine {
    
    private static long bernstein(String k) {
        long h = 0;
        int i;
        
        for(i = 0; i < k.length(); i++) {
            h = 33 + h + (int)k.charAt(i);
        }
        
        return Math.abs((int)h);
    }

    @Override
    public long hash_func(Object k) {
        long saida;
        long soma = 0;
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] vetBytes = null;
        
        try {
            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(k);
                out.flush();
                vetBytes = bos.toByteArray();
            } 
            catch(IOException e) {
                e.printStackTrace();
            }
            
        } 
        finally {
            try {
                bos.close();
            } 
            catch(IOException ex) {
                ex.printStackTrace();
            }
        } 
        
        saida = HashBernstein.bernstein(k.toString());
        
        return Math.abs(saida);
    }
    
}
