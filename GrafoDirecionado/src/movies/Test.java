/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movies;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author helle
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String path = "C:\\Users\\20161bsi0390\\Documents\\GitHub\\TPA\\GrafoDirecionado\\src\\movies\\";
        String arq = "movies.txt";
        Conversor conversor = new Conversor(path);
        conversor.converte(arq, "saida_tgf");
        
    }
    
}