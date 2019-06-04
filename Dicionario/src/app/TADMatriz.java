/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import taddic.TADDicChain;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author helle
 */
public class TADMatriz {
    
    private int linhas;
    private int colunas;
    private TADDicChain dados;

    public TADMatriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.dados = new TADDicChain(null);
    }
    
    public Float getElem(int i, int j) {
        Float elem = null;
        
        if(isInside(i, j)) {
            String tupla = i+","+j;
            elem =(Float)this.dados.findElement(tupla);
            
            if (elem == null) {
                elem = 0F;
            }
        }
        
        return elem;
    }

    public Float setElem(int i, int j, Float valor) {
        if(isInside(i, j)) {
            String tupla = i+","+j;
            
            if(valor!=0F)
                dados.insertItem(tupla, valor);
            else
                dados.insertItem(tupla, null);
        }
        return valor;     
    }
   

    public TADMatriz soma(TADMatriz m) {
        TADMatriz newMatrix = null;
        
        if(this.linhas == m.quantLinhas() && this.colunas == m.quantColunas()) {
            newMatrix = new TADMatriz(linhas, colunas);
            
            for(int i = 0; i < newMatrix.linhas; i++)
                for(int j = 0; j < newMatrix.colunas; j++ )
                    newMatrix.setElem(i, j, this.getElem(i, j) + m.getElem(i, j));
        }
        
        return newMatrix;
    }

    /*Multiplica elementos matriz por k */
    public void vezesK(float k) {
        LinkedList<String> kPos = dados.keys();
        
        for (String tupla : kPos) {
            int i = Integer.parseInt(tupla.substring(0, 1));
            int j = Integer.parseInt(tupla.substring(2, 3));
            Float valor = getElem(i,j);
            this.setElem(i,j, valor*k);
        }
    }
    
    /*Multiplica a matriz pela matriz m se possível
    retorna uma terceira matriz com o resultado da multiplicação. */
    public TADMatriz multi(TADMatriz m) {
        TADMatriz nMatriz = null;

        if(this.quantColunas() == m.quantLinhas()) {

            nMatriz =new TADMatriz(this.quantLinhas(), m.quantColunas());

            for (int i = 0; i < this.quantLinhas(); i++) {
                for (int j = 0; j < m.quantColunas(); j++) {
                    for (int k = 0; k < this.quantColunas(); k++) {
                        Float valor = nMatriz.getElem(i, j) + (this.getElem(i, k) * m.getElem(k, j));
                        nMatriz.setElem(i, j, valor);
                    }
                }
            }
        }
        
        return nMatriz;
    }
    
    public int quantLinhas() {
        return this.linhas;
    }

    public int quantColunas() {
        return this.colunas;
    }

    public TADMatriz transposta() {
         TADMatriz matriz = new TADMatriz(colunas,linhas);
         
         for(int i=0;i<quantLinhas();i++) {
            for(int j=0;j<quantColunas();j++) {
                matriz.setElem(j, i, this.getElem(i, j));
            }
        }
         
        return matriz;
    }

    public static TADMatriz carrega(String nome_arq) throws FileNotFoundException{
        File arq = new File (nome_arq);
        Scanner s = new Scanner(arq);
        String linha;
        LinkedList<String> lst = new LinkedList();
        int linhas = 0;
        int colunas = 0;

        while(s.hasNextLine()) {
            linhas++;   
            linha = s.nextLine(); 
            String[] vet = linha.split(" ");  

            for (int i = 0; i < vet.length; i++) 
                if (!vet[i].isEmpty())
                    lst.addLast(vet[i]);

            if (linhas == 1) {
                colunas = lst.size();
            }
        }
        TADMatriz matriz = new TADMatriz(linhas, colunas);
        int posLst = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Float num = Float.parseFloat(lst.get(posLst));
                matriz.setElem(i, j, num);
                posLst++;
            }
        }
        return matriz;
    }

    public String salva(String nome_arq) throws IOException{
        File resultado = new File(nome_arq);
        FileWriter fw = new FileWriter(resultado);
        PrintWriter gravarArq = new PrintWriter(fw);
        
     
        for (int i = 0; i < this.quantLinhas(); i++) {
            for (int j = 0; j < this.quantColunas(); j++) {
                gravarArq.print(this.getElem(i, j) + " ");
            }
            gravarArq.println();

        }
        fw.close();
        return nome_arq;
    }
    
    
    private boolean isInside(int i, int j) {
        return ((i >= 0 && i<this.quantLinhas()) && (j >= 0 && j<this.quantColunas()));
    }
    
    public LinkedList diagPrincipal() {
        LinkedList diag = null;
        if (quantLinhas() == quantColunas()) {
             diag = new LinkedList();
             
            for (int i = 0;i<quantLinhas();i++)
                for(int j = 0; j<quantColunas();j++)
                    if (i==j)
                        diag.add(this.getElem(i,j));
        }
        else
            System.out.println("Matriz não quadrada");
        
        return diag;
    }
    
    public LinkedList diagSecundaria() {
        LinkedList diagS = null;
        
        if (quantLinhas() == quantColunas()) {
            diagS = new LinkedList();
            int n = quantColunas()-1;
            
            for (int i = 0;i<quantLinhas();i++)
                for(int j = quantColunas()-1; j>=0;j--)
                    if (i+j == n)
                        diagS.add(this.getElem(i,j));
        }
        else
            System.out.println("Matriz não quadrada");
        return diagS;
    }

}
