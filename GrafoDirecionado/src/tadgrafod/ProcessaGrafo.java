/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafod;

import java.util.LinkedList;
import java.util.Queue;

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
    
    //testar dps
    //public LinkedList<Vertex> bsf() {
    //Vertex mainVertex = this.graph.primVertice;
    
    //FIFO queue
    public LinkedList<Vertex> bsf(String vertexLabel) {
        Vertex mainVertex = this.graph.getVertice(vertexLabel);
        Queue fila = new LinkedList<Vertex>();
        Queue filaSaida = new LinkedList<Vertex>();
        
        fila.add(mainVertex);
        
        while(!fila.isEmpty()) {
            Vertex headQueue = (Vertex)fila.remove();
            LinkedList<Vertex> destinyVertex = this.graph.outAdjacenteVertices(headQueue.getLabel());
            if(!destinyVertex.isEmpty()) {
                if(!filaSaida.contains(headQueue)) {
                    filaSaida.add(headQueue);
                }
                
                for(Vertex destiny : destinyVertex) {
                    if(!filaSaida.contains(destiny)) {
                        fila.add(destiny);
                    }
                }
            }
            else {
                if(!filaSaida.contains(headQueue))
                    filaSaida.add(headQueue);
            }
        }
        
        return (LinkedList<Vertex>) filaSaida;
        
    }
    
    //public LinkedList<Vertex> dsf(String verticeLabel){    
}
