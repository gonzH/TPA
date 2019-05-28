/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafod;

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
        tadGrafoD.insertEdge("A", "B", "ab", 12);
        tadGrafoD.insertEdge("B", "C", "bc", 23);
        tadGrafoD.insertEdge("C", "C", "cc", 33);
        
        tadGrafoD.printmat();
        
        System.out.println("");
        
        tadGrafoD.printgrafo();
    }
    
    
    
    
    
    
    
    
    
}
