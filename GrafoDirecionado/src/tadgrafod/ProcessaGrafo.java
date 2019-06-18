/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafod;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author helle
 */
public class ProcessaGrafo {
    private TADGrafoD graph;
    private LinkedList<Vertex> lstVertexGraph;
    private LinkedList<Edge> lstEdgeGraph;
    
    public ProcessaGrafo(TADGrafoD graph) {
        this.graph = graph;
        this.lstVertexGraph = this.graph.vertices();
        this.lstEdgeGraph = this.graph.edges();
    }
    
    
    //FIFO queue
    public LinkedList<Vertex> bfs(String vertexLabel) {
        Vertex mainVertex = this.graph.getVertice(vertexLabel);
        Queue fila = new LinkedList<Vertex>();
        Queue filaBFS = new LinkedList<Vertex>();
        
        fila.add(mainVertex);
        
        while(!fila.isEmpty()) {
            Vertex headQueue = (Vertex)fila.remove();
            LinkedList<Vertex> lstNeighborVertex = this.graph.outAdjacenteVertices(headQueue.getLabel());
            
            if(!lstNeighborVertex.isEmpty()) {
                if(!filaBFS.contains(headQueue)) {
                    filaBFS.add(headQueue);
                }
                
                for(Vertex neighbor : lstNeighborVertex) {
                    if(!filaBFS.contains(neighbor)) {
                        fila.add(neighbor);
                    }
                }
            }
            else {
                if(!filaBFS.contains(headQueue))
                    filaBFS.add(headQueue);
            }
        }
        
        return (LinkedList<Vertex>) filaBFS;
        
    }
    
    public LinkedList<Vertex> dfs(String vertexLabelInicial){ 
        Vertex mainVertex = this.graph.getVertice(vertexLabelInicial);
        LinkedList<Vertex> stackVisitados = new LinkedList<Vertex>();
        LinkedList<Vertex> stackDFS = new LinkedList<Vertex>();
        
        stackVisitados.add(mainVertex);
        
        while(!stackVisitados.isEmpty()) {
            Vertex topVertex = stackVisitados.pollLast();
            LinkedList<Vertex> lstNeighborVertex = this.graph.outAdjacenteVertices(topVertex.getLabel()); 
            
            if(!lstNeighborVertex.isEmpty()) {
                if(!stackDFS.contains(topVertex)) {
                    stackDFS.add(topVertex);
                }
                
                for(Vertex neighbor : lstNeighborVertex) {
                    if(!stackDFS.contains(neighbor)) {
                        stackVisitados.add(neighbor);
                    }
                }
            }
            else {
                if(!stackDFS.contains(topVertex)) {
                    stackDFS.add(topVertex);
                }
            }
            
        }
 
        return stackDFS;
    }   
}
