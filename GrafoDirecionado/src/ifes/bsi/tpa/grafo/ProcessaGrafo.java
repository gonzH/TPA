/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math.*;
import taddic.TADDicChain;
import taddic.TDicItem;
/**
 *
 * @author helle
 */
public class ProcessaGrafo {
    private TADGrafoDV3 graph;
    private LinkedList<Vertex> lstVertexGraph;
    private LinkedList<Edge> lstEdgeGraph;
    
    public ProcessaGrafo(TADGrafoDV3 graph) {
        this.graph = graph;
        this.lstVertexGraph = this.graph.vertices();
        this.lstEdgeGraph = this.graph.edges();
    }
    
    
    //FIFO queue
    public LinkedList<Vertex> bfs(String vertexLabel) {
        Vertex mainVertex = this.graph.getVertex(vertexLabel);
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
        Vertex mainVertex = this.graph.getVertex(vertexLabelInicial);
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
    
    public DSFloydW cmFWarshall() {
        int quantVertex = this.graph.numVertices(); 
        int[][] stdCostMatrix = getStandardCostMatrix();
        int[][] newCostMatrix = new int[quantVertex][quantVertex];
        TADDicChain dicLabels = dicLabels();
        
        for(int k = 0; k < quantVertex; k++) {
            for(int i = 0; i < quantVertex; i++) {
                for(int j = 0; j < quantVertex; j++) {
                    if(stdCostMatrix[i][k] + stdCostMatrix[k][j] < stdCostMatrix[i][j] && (stdCostMatrix[i][k] != Integer.MAX_VALUE && stdCostMatrix[k][j] != Integer.MAX_VALUE )) {
                        stdCostMatrix[i][j] = stdCostMatrix[i][k] + stdCostMatrix[k][j];
                        newCostMatrix[i][j] = k;
                    }
                    //stdCostMatrix[i][j] = Math.min(stdCostMatrix[i][j], stdCostMatrix[i][k] + stdCostMatrix[k][j]);
                }
            }
        }

        return new DSFloydW(stdCostMatrix, dicLabels);
    }
    
    public DSDijkstra cmDijkstra(String origem) {
        Vertex originVertex = this.graph.getVertex(origem);
        int pos = originVertex.getId(); //position of origin vertex important due vertex and edge match
        LinkedList<Vertex> lstVertex = this.lstVertexGraph;
        
        int quantVertex = this.graph.numVertices();
        int[] weight = new int[quantVertex];
        
        String[] path = new String[quantVertex];
        LinkedList<Integer> neighbors = new LinkedList<>();
        
        for(int i = 0; i < quantVertex; i++) {
            weight[i] = Integer.MAX_VALUE;
            neighbors.add(i);
        }
        
        //setting up origin
        weight[pos] = 0;
        path[pos] = originVertex.getLabel();
        
        while(!neighbors.isEmpty()) {
            neighbors.remove((Integer)pos);
            int[] weighClone = weight.clone();
            String[] pathClone = path.clone();
            LinkedList<Vertex> allNeighbors = this.graph.adjacentVertices(lstVertex.get(pos).getLabel());
            
            for(int i = 0; i < allNeighbors.size(); i++) {
                Vertex neighbor = allNeighbors.get(i);
                Edge e = this.graph.getEdge(lstVertex.get(pos).getLabel(), neighbor.getLabel());
                
                if(e != null && neighbors.contains(neighbor.getId())) {
                    if(e.getCusto() + weight[pos] < weight[neighbor.getId()]) {
                        weighClone[neighbor.getId()] = e.getCusto() + weight[pos];
                        pathClone[neighbor.getId()] = path[pos]+':'+neighbor.getLabel();
                    }
                }
            }
            
            weight = weighClone;
            path = pathClone;
            

            if(neighbors.size() == 1) {
                pos = neighbors.get(0);
            }
            else if(neighbors.size() > 1) {
                int minorCost = Integer.MAX_VALUE;
                int newPos = 0;
                for(int i = 0; i < weighClone.length; i++) {
                    if(weighClone[i] < minorCost && neighbors.contains(i)) {
                        minorCost = weighClone[i];
                        newPos = i;
                    }
                }
                pos = newPos;
            }
        }
        return new DSDijkstra(weight, path);
    }
    
    public DSDijkstra cmBFord(String origem) {
        
        
        return null;
    }
    
    
    /* -----------------------------------------------------------------------*/
    /* aux methods */
    
    private int[][] getStandardCostMatrix() {
        int quantVertex = this.graph.numVertices(); 
        int[][] stdCostMatrix = new int[quantVertex][quantVertex];
        
        //checking the relations cost between vertexes of the graph
        int line = -1;
        for( Vertex origin : this.lstVertexGraph) {
            line++;
            int column = -1;
            for( Vertex destiny : this.lstVertexGraph) {
                column++;
                if(origin.getId() != destiny.getId()) {
                    Edge e = this.graph.getEdge(origin.getLabel(), destiny.getLabel());
                    
                    if(e != null) {
                        stdCostMatrix[line][column] = e.getCusto();
                    }
                    else {
                        //represent infinity value of algorithm
                        stdCostMatrix[line][column] = Integer.MAX_VALUE;
                    }
                    
                }
                else {
                    stdCostMatrix[line][column] = 0;
                }
            }
        }
        
        return stdCostMatrix;
    } 
    
    private TADDicChain dicLabels() {
        TADDicChain dic = new TADDicChain(null);
        
        for( Vertex v : this.lstVertexGraph) {
            dic.insertItem(v.getLabel(), v.getId());
        }
        
        return dic;
    }
    
}

