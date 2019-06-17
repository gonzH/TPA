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
        tadGrafoD.insertVertex("A", 1);
        tadGrafoD.insertVertex("B", 2);
        tadGrafoD.insertVertex("C", 3);
        tadGrafoD.insertVertex("D", 4);
        tadGrafoD.insertVertex("E", 5);
        tadGrafoD.insertVertex("F", 6);
        tadGrafoD.insertVertex("G", 7);
        
        
        tadGrafoD.insertEdge("A", "B", "ab", 12);
        tadGrafoD.insertEdge("A", "C", "ac", 12);
        tadGrafoD.insertEdge("B", "D", "bd", 23);
        tadGrafoD.insertEdge("B", "E", "be", 33);
        tadGrafoD.insertEdge("B", "C", "bc", 33);
        tadGrafoD.insertEdge("C", "F", "cf", 44);
        tadGrafoD.insertEdge("C", "G", "cg", 44);
        tadGrafoD.insertEdge("E", "D", "ed", 55);
        tadGrafoD.insertEdge("F", "G", "fg", 44);
        
//        tadGrafoD.printmat();
//        
//        System.out.println("\n--------------------------------------------------\n");
//        
//        tadGrafoD.printgrafo();
        
        
        ProcessaGrafo processaGrafo = new ProcessaGrafo(tadGrafoD);
        LinkedList<Vertex> resultProcessa = processaGrafo.bsf("A");
        for(Vertex result : resultProcessa) {
            System.out.print(result.getLabel()+ " ");
        }
        System.out.println("");
        
//        LinkedList<Vertex> resultProcessa1 = processaGrafo.dsf("A");
//        for(Vertex result : resultProcessa) {
//            System.out.print(result.getLabel()+ " ");
//        }
    }  
}
