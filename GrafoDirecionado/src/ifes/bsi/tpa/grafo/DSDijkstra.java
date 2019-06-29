/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import java.util.LinkedList;

/**
 *
 * @author helle
 */
public class DSDijkstra extends DataSet{
    private int[] vet_custos;
    private String[] vet_antecessores;

    public DSDijkstra(int[] vet_custos, String[] vet_antecessores) {
        this.vet_antecessores = vet_antecessores;
        this.vet_custos = vet_custos;
    }

    @Override
    public LinkedList<Vertex> caminho(String origem, String destino){
        return null;

    }

    @Override
    public int custo (String origem, String destino) {
        return 0;
    }


}
