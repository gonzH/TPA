/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import java.util.LinkedList;
import taddic.TADDicChain;

/**
 *
 * @author helle
 */
public class DSFloydW extends DataSet {
    private int[][] mat_custos;
    private TADDicChain dic_vertex_label_int = null;

    public DSFloydW (int[][] mat_custos) {
        this.mat_custos = mat_custos;
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
