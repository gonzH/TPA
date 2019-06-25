/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import _my_tools.ArquivoTxt;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import taddic.TADDicChain;
/**
 *
 * @author helle
 */
public class TADGrafoDV3 {
    private int [][] mat = null;
    private String nome;
    private int quantVertices = 0;
    private int quantEdges = 0;
    private int geraIDedge = 1;
    private int geraIDvertice = 0;
    
    private TADDicChain dicLblVertex = new TADDicChain(null);
    private TADDicChain dicLblEdge = new TADDicChain(null);
    
    private int primVertice = 0;
    private int ultiVertice = 0;
    //list of deleted vertex
    private LinkedList<Integer> lstEliminados = new LinkedList<Integer>();
    
    /**
     * Cria um TAD Grafo dirigido a partir de um label identificador, utilizando
     * o modelo de matriz de adjacências. A matriz nasce com um tamanho 
     * pré-definido de 9192 por 9192.
     * @param nome - Este é o texto que dá nome ao grafo.
     */
    public TADGrafoDV3(String nome){
        mat = new int[9192][9192];
        this.nome = nome;
    }
    
    /**
     * Cria um TAD Grafo dirigido a partir de um label identificador e do número
     * de máximo de vértices do grafo dirigido. A matriz de adjacências é sempre
     * uma matriz quadrada de dimensões totalNos x totalNos.
     * @param nome - Este é o label que dá nome ao grafo.
     * @param totalNos - Total de vértices previsto para o grafo.
     */
    public TADGrafoDV3(String nome, int totalNos){
        mat = new int[totalNos][totalNos];
        this.nome = nome;
    }

    /**
     * Imprime a matriz de adjacências. Método de apoio à construção do TAD. 
     * Usado para verificar a evolução do modelo e fazer depuração dos métodos 
     * de inclusão e remoção de vértices e arcos.
     */
    public void printmat(){
        for(int i = primVertice; i < ultiVertice+1; i++) {
            if(!lstEliminados.contains(i)) {
                for(int k = primVertice; k <= ultiVertice; k++) {
                    if(!lstEliminados.contains(i)) {
                        System.out.print(String.format("%d ", mat[i][k]));
                    }
                }
                
            System.out.println();    
            }
        }
    }
    
    /**
     * Imprime o grafo no formato texto mostrando as arestas e seus terminais. 
     * A impressão organiza a saída em uma aresta por linha.
     */
    public void printgrafo() {
        
        if(numVertices() > 1) {
            ArrayList<String> al = new ArrayList<String>();
            String s, labelOrigem = "", labelDestino = "", labelEdge = "";

            Vertex v;
            Edge e;

            LinkedList<Object> lstVs = dicLblVertex.keys();
            LinkedList<Object> lstEs = dicLblEdge.keys();

            for(int i = primVertice; i <= ultiVertice; i++) {
                s = "";

                if(!lstEliminados.contains(i)) {
                    for(int j = 0; j < lstVs.size(); j++) {
                        v = (Vertex)dicLblVertex.findElement(lstVs.get(j));
                        if(v.getId() == i) {
                            labelOrigem = v.getLabel();
                            break;
                        }
                    }

                    for(int k = primVertice; k <= ultiVertice; k++) {
                        if(!lstEliminados.contains(k)) {
                            for(int m = 0; m < lstVs.size(); m++) {
                                v = (Vertex)dicLblVertex.findElement(lstVs.get(m));
                                if(v.getId() == k) {
                                    labelDestino = v.getLabel();
                                    break;
                                }
                            }

                            int idEdge = mat[i][k];

                            if(idEdge != 0) {
                                for(int m = 0; m < lstEs.size(); m++) {
                                    e = (Edge)dicLblEdge.findElement(lstEs.get(m));
                                    if(e.getId() == idEdge) {
                                        labelEdge = e.getLabel();
                                        break;
                                    }
                                }

                                s = labelOrigem + "--" + labelEdge + "-->" + labelDestino;
                                al.add(s);
                            }
                        }
                    }
                }
            } //for int i...

            //Island vertex treatment
            for(int i = 0; i < lstVs.size(); i++) {
                String lbl = (String)lstVs.get(i);
                if(degree(lbl) == 0) {
                    al.add(lbl);
                }
            }
        
            Collections.sort(al);

            for(int n = 0; n < al.size(); n ++) {
                System.out.println(al.get(n));
            }
        }
        else {
            System.out.println("Grafo não possui vertices suficiente!");
        }
    }
    
    /**
     * Retorna uma lista com os objetos aresta pertencentes ao grafo.
     * @return Retorna uma lista contendo todos os objetos vértices associados 
     *         às colunas/linhas ativas da matriz de adjacências.
     */
    public LinkedList<Vertex> vertices() {
        LinkedList<Vertex> linkedList = new LinkedList<Vertex>();
        LinkedList<Object> lstKs = dicLblVertex.elements();
        
        for(int i = 0; i < lstKs.size(); i++) {
            Vertex objV = (Vertex)lstKs.get(i);
            linkedList.add(objV);
        }
        
        return linkedList;
    }
    
    /**
     * Retorna uma lista com os objetos aresta pertencentes ao grafo.
     * @return Retorna uma lista contendo todos os objetos arestas/arcos 
     *         associados aos elementos da matriz de adjacências.
     */
    public LinkedList<Edge> edges() {
        LinkedList<Edge> e = new LinkedList<Edge>();
        int pos = 0;
        
        LinkedList<Object> objE = dicLblEdge.elements();
        
        for (Object object : objE) {
            e.add((Edge)object);
        }
        
        return e;
    }
    
    /**
     * Retorna o total de vértices que compõe o grafo.
     * @return Retorna a quantidade de vértices ativos.
     */
    public int numVertices(){
        return this.quantVertices;
    }
    
    /**
     * Retorna o total de arestas que compõe o grafo.
     * @return Retorna a quantidade de arcos/arestas do grafo.
     */
    public int numEdges(){
        return this.quantEdges;
    }
    
    /**
     * Retorna o texto identificador do grafo.
     * @return Retorna o label associado ao grafo.
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Define/altera o label identificador do grafo.
     * @param nome - string contendo o label identificador do grafo.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public boolean valido(int v){
        return((v >= primVertice) && (v<=ultiVertice) && !(lstEliminados.contains(v)));
    }
    
    /**
     * Procura e retorna o objeto Edge associado ao label.
     * @param label - label da aresta que se deseja obter.
     * @return Retorna o objeto Edge associado ao label passado como argumento. 
     *         Retorna null caso não exista nenhum objeto Edge associado ao label;
     */
    public Edge getEdge(String label) {
        Edge e = (Edge)dicLblEdge.findElement(label);
        
        if(dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        
        return e;
    }
    
    /**
     * Procura e retorna o objeto Edge associada a aresta que possui os vértices
     * origem e destino como terminais.
     * @param origem - label do objeto Vertex associado ao vértice terminal da 
     *                 aresta procurada
     * @param destino - label do objeto Vertex associado ao vértice terminal da 
     *                  aresta procurada.
     * @return Retorna o objeto aresta associado aos vértices terminais de labels
     *         origem e destino. Retorna null caso não exista nenhum objeto Vertex
     *         associado ao label origem ou ao label destino.
     */
    public Edge getEdge(String origem, String destino) {
        Vertex vDestino = (Vertex)dicLblVertex.findElement(destino);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        Vertex vOrigem = (Vertex)dicLblVertex.findElement(origem);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        int idEdge = this.mat[vOrigem.getId()][vDestino.getId()];
        
        if(idEdge == 0) {
            return null;
        }
        else {
            Edge e = this.intToEdge(idEdge);
            return e;
        }

    }
    
    /**
     * Procura e retorna o objeto Vertex associado ao label de valor lbl.
     * @param label - label do vértice que se deseja obter.
     * @return Retorna o objeto vértice associado ao label passado como 
     *         argumento. Retorna null caso não exista nenhum objeto Vertex 
     *         associado ao label.
     */
    public Vertex getVertex(String label) {
        Vertex vertice = (Vertex)this.dicLblVertex.findElement(label);
        
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        else {
            return vertice;
        }
    }
    
    public Vertex intToVertex(int id) {
        LinkedList<Object> lst = dicLblVertex.elements();
        
        for(int i = 0; i < lst.size(); i++) {
            Vertex v = (Vertex)lst.get(i);
            if(id == v.getId()) {
                return v;
            }
        }
        
        return null;
    }
    
    public Edge intToEdge(int id) {
        LinkedList<Object> lst = dicLblEdge.elements();
        
        for(int i = 0; i < lst.size(); i++) {
            Edge e = (Edge)lst.get(i);
            if(id == e.getId()) {
                return e;
            }
        }
        
        return null;
    }
    
    /**
     * Encontra e retorna os objetos Vertex que formam os terminais da aresta de
     * label
     * @param labelE - String label da aresta cujos terminais estão sendo 
     *                 procurados.
     * @return Retorna um vetor contendo os dois objetos Vertex que representam 
     *         os nós terminais da aresta/arco de label. Se não existir 
     *         nenhuma aresta/arco associada a labelE, null é retornado.
     */
    public Vertex[] endVertices(String labelE){ 
        Edge oE = (Edge)dicLblEdge.findElement(labelE);
        
        if(dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        
        int idE = oE.getId();
        
        for(int i = primVertice; i <= ultiVertice; i++){
            if(!(lstEliminados.contains(i))){
                for(int j = primVertice; j <= ultiVertice; j++){
                    if(mat[i][j] == idE){
                        Vertex[] v = new Vertex[2];
                        v[0] = intToVertex(i);
                        v[1] = intToVertex(j);
                        return v; 
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Encontra e retorna o objeto Vertex que é o destino da aresta de label e 
     * saindo do vértice de label v.
     * @param v - String contendo o label de um vértice do grafo que é considerado
     *            a origem da aresta de label e.
     * @param e - String contendo o label de uma aresta/arco que possui como 
     *            origem o vértice de label v e como destino o vértice a ser 
     *            retonado por este método.
     * @return Retorna o objeto vértice que é o destino da aresta de label e que
     *         se origna do vértice de label v. Retorna null se não existir o 
     *         vértice de label v ou a aresta de label e. Ou quando vértice e 
     *         aresta existirem mas não estiverem conectados.
     */
    public Vertex opposite(String v, String e){
        Vertex objV = (Vertex)dicLblVertex.findElement(v);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        Edge objE = (Edge)dicLblEdge.findElement(e);
        if(dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            if((!lstEliminados.contains(i)) && (mat[objV.getId()][i] == objE.getId())) {
                LinkedList<Object> lstVs = dicLblVertex.keys();
                
                for(int m = 0; m< lstVs.size(); m++) {
                    Vertex oU = (Vertex)dicLblVertex.findElement(lstVs.get(m));
                    if(oU.getId() == i) {
                        return oU;
                    }
                }
            }
        }
        
        return null;
    }
    
    

    /**
     * Calcula e retorna a quantidade de arestas que saem do vértice de label
     * @param label - String contendo o label de um vértice do grafo.
     * @return Retorna o grau de saída do vértice associada ao label. Se não 
     *         existir um vértice associado a label, null é retornado. O grau de
     *         saída é a quantidade de arestas dirigidas que saem do vértice de 
     *         label. Este vértice é considerado a origem das suas arestas de saída.
     */
    public Integer outDegree(String label){
        // v, linha, i, coluna: todos as arestas saindo de v.
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        else {
            int line = v.getId();
            int grade = 0;
            
            for(int i = primVertice; i <= ultiVertice; i++) {
                if((mat[line][i] != 0) && !lstEliminados.contains(i)) {
                    grade++;
                }
            }
            
            return grade;
        }
    }
    
    

    /**
     * Calcula e retorna a quantidade de arestas que chegam até o vértice de label
     * @param label - String contendo o label de um vértice do grafo.
     * @return Retorna o grau de entrada do vértice associada ao label. 
     *         Se não existir um vértice associado a label, null é retornado. 
     *         O grau de entrada é a quantidade de arestas dirigidas que saem do
     *         vértice de label lbl. Este vértice é considerado a origem das 
     *         suas arestas de saída.
     */
    public Integer inDegree(String label) {
        // v, coluna, i, linha: todos as arestas entrando de v.
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        else {
            int column = v.getId();
            int grade = 0;
            
            for(int i = primVertice; i <= ultiVertice; i++) {
                if((mat[i][column] != 0) && !lstEliminados.contains(i)) {
                    grade++;
                }
            }
            
            return grade;
        }
    }
    
    /**
     * Acrescenta ao grafo uma aresta de label label armazenando um objeto de 
     * dados (da aplicação) dado. A nova aresta possui como terminais os vértices
     * de labels origeme destino, respectivamente. Se já existir uma aresta com 
     * o mesmo label, então uma operação de atualização do dado da aplicação é 
     * executada. O ID do novo vértice é gerado por geraIDedge. Quando se trata 
     * de uma nova aresta, a função altera o registro de quantidade de arestas 
     * presentes no grafo (quantEdges).
     * @param origem - String contendo o label do vértice origem da nova aresta.
     * @param destino - String contendo o label do vértice destino da nova aresta.
     * @param label - String contendo o label da nova aresta do grafo.
     * @param obj - Object contendo a referência para um objeto de dados definido
     *              pelo cliente do grafo.
     * @return
     */
    public Edge insertEdge(String origem, String destino, String label, Object obj) {
        //verifying that the vertex's exist
        Vertex vOrigem = (Vertex)dicLblVertex.findElement(origem);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        Vertex vDestino = (Vertex)dicLblVertex.findElement(destino);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        /* 
         finding an edge to insert, if it dont exists we'll create a new edge,
         if it already exists then just insert         
        */ 
        Edge edgeToInsert = (Edge)dicLblEdge.findElement(label);
        
        //Inclusion of a new arch
        if(dicLblEdge.NO_SUCH_KEY()) {
            edgeToInsert = new Edge(label, obj);
            edgeToInsert.setId(geraIDedge++);
            
            dicLblEdge.insertItem(label, edgeToInsert);
            
            mat[vOrigem.getId()][vDestino.getId()] = edgeToInsert.getId();
            quantEdges++;
        } //Update of a existent arch
        else {
            edgeToInsert.setDado(obj);
        }
        
        return edgeToInsert; 
    }
    
    /**
     * Remove do grafo a aresta de label edge. Se nenhuma aresta existir para 
     * este label, null é retornado. Dependendo do valor do ID associado ao 
     * vértice, os atributos primVertice e ultiVertice devem ser atualizados. A 
     * função deve alterar o registro de quantidade de vértices ativos, quantEdges.
     * A aresta é eliminada assinanlando-se zero ao elemento da matriz de 
     * coordenada igual aos vértices terminais da aresta, origem, destino.
     * @param edge - String contendo o label de uma aresta do grafo
     * @return Retorna um Object contendo o dado da aplicação que estava sendo 
     *         armazenado pela aresta removida. Retorna null se nenhuma aresta 
     *         de label edge foi encontrada.
     */
    public Object removeEdge(String edge){
        Edge e  = (Edge)dicLblEdge.findElement(edge);
        if(dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        
        int idE = e.getId();
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            if(!lstEliminados.contains(i)) {
                for(int j = primVertice; j <= ultiVertice; j++) {
                    if(mat[i][j] == idE) {
                        mat[i][j] = 0;
                        quantEdges--;
                        dicLblEdge.removeElement(edge);
                        return e.getDado();
                    } 
                } 
            }      
        }
        
        /* Anomalia: o arco de label existe mas o seu id não se encontra */
        return null;
    }
    
    /**
     * Remove do grafo o vértice de label label. Se nenhum vértice existir para 
     * este label, null é retornado. Dependendo do valor do ID associado ao vértice,
     * os atributos primVertice e ultiVertice devem ser atualizados. A função deve
     * alterar o registro de quantidade de vértices ativos. O ID do vértice deve
     * ser incluído lstEliminados para futuro uso com um novo vértice. Todas as
     * arestas associadas ao vértice também devem ser removidas.
     * @param label - String contendo o label de um vértice do grafo.
     * @return Retorna um Object contendo o dado da aplicação que estava sendo 
     *         armazenado pelo vértice removido.
     */
    public Object removeVertex(String label) {
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        int idV = v.getId();
        
        //checking if the id of vertex about to be removed is the actual first 
        //vertex and then reset to the next int available if its true
        if(idV == primVertice) {
            for(int i = primVertice+1; i <= ultiVertice; i++) {
                if(!lstEliminados.contains(i)) {
                    primVertice = i;
                    break;
                }
            }
        }

        //same structure of the last if conditional but for the last id
        if(idV == ultiVertice) {
            for(int i = ultiVertice-1; i >= primVertice; i--) {
                if(!lstEliminados.contains(i)) {
                    ultiVertice = i;
                    break;
                }
            }
        }
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            //Fill removed vertex line with 0's that means the vertex does not 
            //have any connection with other vertex, also does not exist
            if(mat[idV][i] != 0) {
                mat[idV][i] = 0;
                quantEdges--;
            }
            
            //Fill removed vertex column with 0's that means the vertex does not
            //have any connection with other vertex, also does not exist
            //and prevent from decrementing quantEdges already decremented
            if((mat[i][idV] != 0) && (mat[idV][i] != mat[i][idV])) {
                quantEdges--;
                mat[i][idV] = 0;
                
            }
        }
        
        quantVertices--;
        lstEliminados.add(idV);
        return dicLblVertex.removeElement(label);   
    }
    
    /**
     * Verifica se dois vértices estão conectados por uma aresta.
     * @param origem - String contendo o label de um vértice do grafo.
     * @param destino - String contendo o label de um vértice do grafo.
     * @return true se os vértices origem e destino forem os terminais de alguma
     * aresta. Retorna false caso contrário. Retorna false também no caso de 
     * nenhum vértice de label u e/ou v ter sido encontrado.
     */
    public boolean areAdjacent(String origem, String destino){
        Vertex vOrigem = (Vertex)dicLblVertex.findElement(origem);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return false;
        }
        
        Vertex vDestino = (Vertex)dicLblVertex.findElement(destino);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return false;
        }
        
        return mat[vOrigem.getId()][vDestino.getId()] != 0;
    }
    
    private int geraIDVertex() {
        int id;
        
        if(lstEliminados.size() == 0) {
            id = geraIDvertice++;
        }
        else {
            /* if some vertex is removed,
            the next id will receive an id that used to have a vertex
            this mechanism ensures that the spaces of vertex removed are filled
            */
            id = lstEliminados.get(0);
            lstEliminados.remove();
        }
        
        /* control of the margin */
        if(id < primVertice)
            primVertice = id;
        
        if(id > ultiVertice)
            ultiVertice = id;
        
        return id;
    }
    
    /**
     * Acrescenta ao grafo um vértice de label label armazenando um objeto de 
     * dados (da aplicação) dado. Se já existir um vértice com o mesmo label, 
     * então o método atualiza o objeto de dados desse vértice paradadovalor do 
     * argumento dado. O ID do novo vértice é gerado por geraIDvertex. Quando se
     * trata de um novo vértice, a função altera o registro de quantidade de 
     * vértices ativos. Dependendo do valor do ID, os atributos primVertice e 
     * ultiVertice devem ser atualizados.
     * @param label - String contendo o label de um vértice do grafo.
     * @param dado - Object contendo a referência para um objeto de dados definido
     *               pelo cliente do grafo.
     * @return Retorna um objeto Vertex representando o novo vértice adicionado 
     *         ao grafo.
     */
    public Vertex insertVertex(String label, Object dado) {
        int idVertex = geraIDVertex();
        
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        
        //Including a new vertex
        if(dicLblVertex.NO_SUCH_KEY()) {
            v = new Vertex(label, dado);
            v.setId(idVertex);
            dicLblVertex.insertItem(label, v);
            quantVertices++;
        }
        else { //updating a existent vertex
            v.setDado(dado);
        }
        
        return v;
    }
    
    /**
     * Calcula e retorna a quantidade de arestas conectadas ao vértice de label
     * @param label - String contendo o label de um vértice do grafo.
     * @return Retorna o grau do vértice associada ao label passado como argumento
     *         de entrada. Se não existir um vértice associado a label, null é 
     *         retornado. O grau do vértice é a quantidade de arestas dirigidas 
     *         associadas ao vértice. Este grau é a soma dos graus de entrada e 
     *         saída.
     */
    public Integer degree(String label) {
        Integer in = inDegree(label);
        Integer out = outDegree(label);
        
        if((in == null) || (out == null)) {
            return null;
        }
        else {
            return in + out;
        }
    }
    
    /**
     * Descobre e retorna o objeto vértice onde entra a aresta de label.
     * @param labelE - String contendo o label de uma aresta do grafo.
     * @return Retorna o objeto Vertex que é o destino da aresta de label. 
     *         Se não existir uma aresta associada ao label, null é retornado.
     */
    public Vertex destination(String labelE) { 
        Vertex[] vet = endVertices(labelE);
        
        if(vet != null) {
            return vet[1];
        }
        else {
            return null;
        }
    }
    
    /**
     * Descobre e retorna o objeto vértice de onde sai a aresta de label
     * @param labelE - String contendo o label de uma aresta do grafo.
     * @return Retorna o objeto Vertex que é a origem da aresta de label.
     *         Se não existir uma aresta associada ao label, null é retornado.
     */
    public Vertex origin(String labelE) { 
        Vertex[] vet = endVertices(labelE);
        
        if(vet != null) {
            return vet[0];
        }
        else {
            return null;
        }
    }
    
    /**
     * Descobre e retorna todas as arestas que chegam ao vértice de label
     * @param labelV - String contendo o label de um vértice do grafo.
     * @return Retorna uma lista de objetos Edge que posuem como destino o vértice
     *         de label.  Se nenhum vértice estiver associado a labelV, null é 
     *         retornado.
     */
    public LinkedList<Edge> inIncidentEdges(String labelV) { 
        Vertex v = (Vertex)dicLblVertex.findElement(labelV);
        
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        LinkedList<Edge> lst = new LinkedList<Edge>();
        int id = v.getId();
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            if((!lstEliminados.contains(i)) && (mat[id][i] != 0)) {
                lst.add(intToEdge(mat[id][i]));
            }
        }
        
        return lst;
    
    }
    
    /**
     * Descobre e retorna todas as arestas que saem do vértice de label
     * @param labelV - String contendo o label de um vértice do grafo.
     * @return Retorna uma lista de objetos Edge que posuem como origem o vértice
     *  do label.  Se nenhum vértice estiver associado a label, null é retornado.
     */
    public LinkedList<Edge> outIncidentEdges(String labelV) { 
        Vertex v = (Vertex)dicLblVertex.findElement(labelV);
        
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        LinkedList<Edge> lst = new LinkedList<Edge>();
        int id = v.getId();
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            if((!lstEliminados.contains(i)) && (mat[i][id] != 0)) {
                lst.add(intToEdge(mat[i][id]));
            }
        }
        
        return lst;
    }
    
    /**
     * Descobre e retorna os vértices origem das arestas que entram no vértice de label
     * @param labelV - String contendo o label de um vértice do grafo.
     * @return Retorna uma lista de objetos Vertex que são alcançados por meio de
     *         arestas que entram no vértice associado ao labelV. Se nenhum 
     *          vértice estiver associado ao labelV, null é retornado.
     */
    public LinkedList<Vertex> inAdjacenteVertices(String labelV) { 
        Vertex v = (Vertex)dicLblVertex.findElement(labelV);
        
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        LinkedList<Vertex> lstInVertex = new LinkedList<Vertex>();
        int id = v.getId();
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            if((!lstEliminados.contains(i)) && (mat[i][id] != 0)) {
                lstInVertex.add(intToVertex(i));
            }
        }
        
        return lstInVertex;
    }
    
    /**
     * Descobre e retorna os vértices destino das arestas que saem no vértice de
     * labelV
     * @param labelV - String contendo o label de um vértice do grafo.
     * @return Retorna uma lista de objetos Vertex que são alcançados por meio 
     *         de arestas que saem do vértice associado ao labelV. Se nenhum 
     *         vértice estiver associado ao labelV, null é retornado.
     */
    public LinkedList<Vertex> outAdjacenteVertices(String labelV) { 
        Vertex v = (Vertex)dicLblVertex.findElement(labelV);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        LinkedList<Vertex> lstOutVertex = new LinkedList<Vertex>();
        int id = v.getId();
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            if((!lstEliminados.contains(i)) && (mat[id][i] != 0)) {
                lstOutVertex.add(intToVertex(i));
            }
        }
        
        return lstOutVertex;
    
    }
    
    /**
     * Descobre e retorna todas as arestas conectadas ao vértice de label
     * @param labelV  - String contendo o label de um vértice do grafo.
     * @return Retorna uma lista de objetos Edge conectados ao vértice de label.
     *         A lista engloba arestas de saída e arestas de entrada no vértice 
     *         labelV. Se nenhum vértice estiver associado a labelV, null é 
     *         retornado.
     */
    public LinkedList<Edge> incidentEdges(String labelV) { 
        LinkedList<Edge> lst = inIncidentEdges(labelV);
        lst.addAll(outIncidentEdges(labelV));
        
        return lst;
    }
    
    /** 
     * Descobre e retorna os vértices adjacentes ao vértice de label
     * @param labelV - String contendo o label de um vértice do grafo.
     * @return Retorna uma lista de objetos Vertex que são alcançados por meio 
     *         de arestas conectadas ao vértice associado ao labelV. A lista 
     *         engloba arestas de entrada e de saída. Se nenhum vértice estiver 
     *         associado ao labelV, null é retornado.
     */
    public LinkedList<Vertex> adjacentVertices(String labelV) { 
        LinkedList<Vertex> lst = inAdjacenteVertices(labelV);
        lst.addAll(outAdjacenteVertices(labelV));
        
        return lst;
    }
    
    /**
     * Verifica se dois tad grafos são iguais. Dois tad grafos são considerados 
     * iguais se possuírem as mesmas quantidades de vértices e arestas, se os 
     * vertices e arestas possuírem o mesmo label, se as arestas possuírem 
     * vertices terminais de mesmo label. Objetos de dados não são incluídos na 
     * verificação.
     * @param oOutroG - objeto TADGrafoDV3 que será comparado com o objeto corrente,
     *                  this.
     * @return true se o grafo corrente this for igual ao grafo passado como 
     *         argumento, false caso contrário.
     */
    public boolean equals​(TADGrafoDV3 oOutroG) {
        if(this.numEdges() == oOutroG.numEdges() && this.numVertices() == oOutroG.numVertices()) {
            for(int i = primVertice; i <= ultiVertice; i++) {
                for(int k = primVertice; k < ultiVertice; k++) {
                    Vertex chave = intToVertex(k);
                    Object outroVertex = oOutroG.dicLblVertex.findElement(chave.getLabel());
                    Edge e = intToEdge(k);
                    Object outroEdge = oOutroG.dicLblEdge.findElement(e.getLabel());
                    
                    if(oOutroG.dicLblVertex.NO_SUCH_KEY()) {
                        return false;
                    }
                    if(oOutroG.dicLblEdge.NO_SUCH_KEY()) {
                        return false;
                    }
                    if(chave.getDado() != outroVertex) {
                        return false;
                    }
                    if(e.getDado() != outroEdge) {
                        return false;
                    }
				 
                }
            }
	}
	
	return true;
    }
    
    /**
     * Clona o tad grafo corrente em um novo grafo com a mesma estrutura e mesmo
     * conteúdo. Cria e retorna um grafo com as mesmas quantidades de vértices e
     * arestas, com vértices e arestas possuindo os mesmos labels do grafo 
     * original, com arestas possuindo os mesmo vértices terminais e, finalmente,
     * com o clone possuindo os mesmos objetos de dados ligados a seus 
     * respectivos vértices e arestas.
     * @return Retorna um objeto TADGrafoDV3 com vértices, arestas e dados 
     *         copiados do grafo original, this.
     */
    @Override
    public TADGrafoDV3 clone() {
	TADGrafoDV3 grafoClone = new TADGrafoDV3(this.nome+", O clone", this.numVertices());
    
	for(int i = primVertice; i <= ultiVertice; i++) {
	    for(int j = primVertice; j <= ultiVertice; j++) {
                if(!lstEliminados.contains(j) && (mat[i][j] != 0)) {
                    Vertex chave = intToVertex(j);
                    grafoClone.insertVertex(chave.getLabel(), chave.getDado());
                    
                    Edge e = intToEdge(mat[i][j]);
                    String origem = this.intToVertex(i).getLabel();
                    String destino = this.intToVertex(j).getLabel();
                    
                    grafoClone.insertEdge(origem, destino, e.getLabel(), e.getDado());
                }
	    }
        }
	
	return grafoClone;
    }
    
    /**
     * Função estática que retorna um tad grafo dirigido a partir dos dados 
     * armazenados no arquivo de nome nome_arq_tgf. A função também recebe uma 
     * quantidade estimada de vértices a fim de viabilizar o processamento do 
     * formato tgf. Mais informações sobre o formato tgf em 
     * http://docs.yworks.com/yfiles/doc/developers-guide/tgf.html.
     * @param nome_arq_tgf  - String contendo o nome do arquivo tgf contendo os
     *                        dados do grafo.
     * @param quant_vertices - int contendo a quantidade estimada dos vértices 
     *                         no grafo. Parâmetro necessário para o dimensionamento da matriz de adjacências.
     * @return Retorna um objeto do tipo TADGrafoD.
     */
    public static TADGrafoDV3 carregaTGF(String nome_arq_tgf, int quant_vertices) throws IOException{
        TADGrafoDV3 grafo = new TADGrafoDV3("grafo", quant_vertices);
        try {
            FileReader fileReader = new FileReader(nome_arq_tgf);
            BufferedReader arq = new BufferedReader(fileReader);
            String linha = arq.readLine();
            boolean arestas = false;
            while(linha != null){
                if(linha.contains("#")){
                    arestas = true;
                    linha = arq.readLine();
                }
                String[] l = linha.split(" ");
                if(!arestas){    
                    String value = "";
                    for(int i=1;i<l.length;i++){
                        value += " " + l[i];
                    }
                    grafo.insertVertex(l[0], value);
                }
                else{
                    String edgeLabel = l[0]+'-'+l[1];
                    grafo.insertEdge(l[0], l[1], edgeLabel, "");
                }
                linha = arq.readLine();
            }
            arq.close();
            return grafo;
            
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        }
        return null;
}
  
    
           
}
