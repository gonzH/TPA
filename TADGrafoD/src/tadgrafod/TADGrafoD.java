/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafod;

import java.util.LinkedList;
import dicionario;
/**
 *
 * @author helle
 */
public class TADGrafoD {
    private int [][] mat = null;
    private String nome;
    private int quantVertx = 0;
    private int quantEdges = 0;
    private int geraIDedge = 1;
    private int geraIDVertex = 1;
    private int primeiroVertex = 0;
    private int ultimoVertex = 0;
    private LinkedList lstDeletados = null;
    TADDicChain dicLblVertex = new TADDicChain();
    
    public TADGrafoD(String nome){
        mat = new int[10][10];
        this.nome = nome;
        
        lstDeletados = new LinkedList();
    }
    
    public void printmat(){
        for(int i=primeiroVertex; i<ultimoVertex; i++){
            if(lstDeletados.contains(i)){
                for(int k=0;k<mat[0].length; k++){
                    System.out.println(String.format("%d",mat[i][k]));
                }
            System.out.println();    
            }
        }
    }
    
    public int numVertices(){
        return quantVertx;
    }
    
    public int numEdges(){
        return quantEdges;
    }
    
    public boolean valido(int v){
        return((v >= primeiroVertex) && (v<=ultimoVertex) && !(lstDeletados.contains(v)));
    }
    
    public Integer getEdge(int u, int v){
        if(valido(u) && valido(v)){
            return mat[u][v];
        }
        else{
            return null;
        }
    }
    
    public int[] endVertices(int e){
        for(int i =primeiroVertex;i<=ultimoVertex;i++){
            if(valido(i)){
                for(int k=primeiroVertex;k<=ultimoVertex;k++){
                    if(mat[i][k] == e){
                        int[] v = new int[2];
                        v[0] = i;
                        v[1] = k;
                        return v;
                    }
                }
            }
        }
        
        return null;
    }
    
    public Integer opposite(int v, int e){
        if(valido(v)){
            for(int i=primeiroVertex; i<=ultimoVertex; i++){
                if(mat[v][i]==e){
                    return i;
                }
            return null;    
            }
        }
        else{
            return null;
        }
        
        return null;
    }
    
    public int outDegree(int v){
        int grau = 0;
        for(int i=primeiroVertex; i<=ultimoVertex; i++){
            if(mat[v][i] != 0 && valido(v)){
                grau++;
            }
        }
        return grau;
    }
    
    public Vertex insereVertex(String label, Object o){
        int idV = geraIDVertex++;
        
        if(idV > ultimoVertex){
            ultimoVertex = idV;
        }
        if(idV < primeiroVertex){
            primeiroVertex = idV;
        }
        
        
        Vertex V = (Vertex)dicLblVertex.findElement(label);
        if(dicLblVertex.NO_SUCH_KEY()){
            V = new Vertex(label, o);
            V.setId(idV);
            dicLblVertex.insertItem(label,V);
            quantVertx++;
        }
        else{
            V.setDado(o);
        }
        
        return V;
    }
    
    public int insereEdge(int u, int v){
        if(valido(u) && valido(v)){
            mat[u][v] = geraIDedge++;
            quantEdges++;
            return mat[u][v];
            
        }
        return -1;
    }
    
    public void removeEdge(int e){
        for(int i=primeiroVertex; i<=ultimoVertex; i++){
            for(int k=primeiroVertex; k<=ultimoVertex; k++){
                if(mat[i][k] == e){
                    mat[i][k] = 0;
                    quantEdges--;
                    
                }
            }
        }
    }
    
    public Integer removeVertex(int v){
        if(valido(v)){
            lstDeletados.add(v);
            
            if(v == primeiroVertex){
                for(int i=primeiroVertex+1; i<=ultimoVertex;i++){
                    if(!lstDeletados.contains(i)){
                        primeiroVertex = i;
                        break;
                    }
                    
                }
            }
            
            if(v == ultimoVertex){
                for(int i=ultimoVertex-1; i>=primeiroVertex; i--){
                    if(!lstDeletados.contains(i)){
                        ultimoVertex = i;
                        break;
                    }
                }
            }
            
            for(int i=primeiroVertex+1; i<=ultimoVertex; i++){
                if(mat[v][i] != 0){
                    mat[v][i]=0;
                    quantEdges--;
                }
                
                if(((mat[i][v] != 0)) && ((mat[v][i] != mat[i][v]))){
                    mat[i][v]=0;
                    quantEdges--;
                    
                }
             
                
            }
            
            
            
            quantVertx--;
            return v;    
        }
        else{
            return null;
        }
    }
    
    public boolean areaAdjacent(int u, int v){
        if(valido(u) && valido(v)){
            return mat[u][v] != 0;
        }
        else{
            return false;
        }
    }


    
    
           
}


///TO DO 
///public vertez insertVertex(String label, Object o)
///public Edge insertEdge(String vu, String vv, String label, Object o)