/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafod;

import java.util.LinkedList;

/**
 *
 * @author 20161bsi0390
 */
public class Main {
    
    public static void main(String[] args) {
        
        
        TADGrafoD tadGrafoD = new TADGrafoD("Grafozada");
        tadGrafoD.insertVertex("A", 0);
        tadGrafoD.insertVertex("B", 1);
        tadGrafoD.insertVertex("C", 2);
        tadGrafoD.insertVertex("D", 3);
        tadGrafoD.insertVertex("E", 4);
        tadGrafoD.insertVertex("F", 5);
        tadGrafoD.insertVertex("G", 6);
        tadGrafoD.insertVertex("H", 7);
        
        
//        tadGrafoD.insertEdge("A", "B", "ab", 12);
//        tadGrafoD.insertEdge("A", "C", "ac", 12);
//        tadGrafoD.insertEdge("B", "D", "bd", 23);
//        tadGrafoD.insertEdge("B", "E", "be", 33);
//        tadGrafoD.insertEdge("B", "C", "bc", 33);
//        tadGrafoD.insertEdge("C", "F", "cf", 44);
//        tadGrafoD.insertEdge("C", "G", "cg", 44);
//        tadGrafoD.insertEdge("E", "D", "ed", 55);
//        tadGrafoD.insertEdge("F", "G", "fg", 44);

        tadGrafoD.insertEdge("A", "B", "ab", 12);
        tadGrafoD.insertEdge("A", "E", "ae", 23);
        tadGrafoD.insertEdge("B", "C", "bc", 34);
        tadGrafoD.insertEdge("B", "D", "bd", 45);
        tadGrafoD.insertEdge("E", "F", "ef", 56);
        tadGrafoD.insertEdge("E", "G", "eg", 67);
        tadGrafoD.insertEdge("G", "H", "gh", 78);
//        tadGrafoD.insertEdge("E", "B", "eb", 12);
          
        
        tadGrafoD.printmat();
        
        System.out.println("\n--------------------------------------------------\n");
        
        tadGrafoD.printgrafo();
        
        System.out.println("\n--------------------------------------------------\n");
        
//        tadGrafoD.removeVertex("A");
//        tadGrafoD.removeVertex("G");
//        tadGrafoD.removeVertex("E");
//        
//        tadGrafoD.printmat();
//        
//        System.out.println("\n--------------------------------------------------\n");
//        
//        tadGrafoD.printgrafo();
//        
//        System.out.println("\n--------------------------------------------------\n");
//        tadGrafoD.removeEdge("ab");
//        tadGrafoD.removeEdge("bc");
//        tadGrafoD.removeEdge("bd");
//        
//        tadGrafoD.insertVertex("S", null);
//        tadGrafoD.insertEdge("S", "F", "sf", 12);
//        
//
//        tadGrafoD.printmat();
//        
//        System.out.println("\n--------------------------------------------------\n");
//        
//        tadGrafoD.printgrafo();
        
        
        
        
        ProcessaGrafo processaGrafo = new ProcessaGrafo(tadGrafoD);
        LinkedList<Vertex> resultProcessa = processaGrafo.bfs("A");
        for(Vertex result : resultProcessa) {
            System.out.print(result.getLabel()+ " ");
        }
        System.out.println("");
      
        LinkedList<Vertex> resultProcessa1 = processaGrafo.dfs("A");
        for(Vertex result : resultProcessa1) {
            System.out.print(result.getLabel()+ " ");
        }
    }  
}
