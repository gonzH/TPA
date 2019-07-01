package ifes.bsi.tpa.grafo;

import java.io.IOException;

public class ProcGrafoBench {
	public static void main(String args[]) throws IOException {
//            String pasta_tgf = "F:\\GitDesktop\\TPA\\GrafoDirecionado\\src\\Base-Grafos\\";
//            String nome_grafo_tgf = "tgfmovies10.txt";
//            TADGrafoDV3 g = TADGrafoDV3.carregaTGF(pasta_tgf + nome_grafo_tgf, 12000);
            TADGrafoDV3 g = new TADGrafoDV3("g");
            g.insertVertex("A", "");
            g.insertVertex("B", "");
            g.insertVertex("C", "");
            g.insertVertex("D", "");
            g.insertVertex("E", "");
            g.insertVertex("F", "");
            
            
            g.insertEdge("A", "B", "ab", ""); g.getEdge("ab").setCusto(50);
            g.insertEdge("A", "D", "ad", ""); g.getEdge("ad").setCusto(10);
            g.insertEdge("A", "C", "ac", ""); g.getEdge("ac").setCusto(45);
            g.insertEdge("B", "D", "bd", ""); g.getEdge("bd").setCusto(15);
            g.insertEdge("B", "C", "bc", ""); g.getEdge("bc").setCusto(10);
            g.insertEdge("C", "E", "ce", ""); g.getEdge("ce").setCusto(30);
            g.insertEdge("D", "A", "da", ""); g.getEdge("da").setCusto(10);
            g.insertEdge("D", "E", "de", ""); g.getEdge("de").setCusto(15);
            g.insertEdge("E", "B", "eb", ""); g.getEdge("eb").setCusto(20);
            g.insertEdge("E", "C", "ec", ""); g.getEdge("ec").setCusto(35);
            g.insertEdge("F", "E", "fe", ""); g.getEdge("fe").setCusto(3);

            ProcessaGrafo pg = new ProcessaGrafo(g);

            DSFloydW dsfw = pg.cmFWarshall();
            
            int[][] fwm = dsfw.getMat_custos();
            System.out.println("Floyd Warshall");
            for(int i = 0; i < fwm.length; i++) {
                for(int j = 0; j < fwm.length; j++) {
                    System.out.print(String.format("%d ", fwm[i][j]));
                }
                System.out.println("");
            }
            
            System.out.println("--------------------------------");
            System.out.println("Dijkstra");
            DSDijkstra dsd = pg.cmDijkstra("A");
            
            for( int i : dsd.getVet_custos()) {
                System.out.print(i+", ");
            }
            System.out.println("");
            for(String s : dsd.getVet_antecessores()) {
                System.out.print(s+ ", ");
            }

			
	} /* fim de main */

} /* fim da classe ProcGrafoBench */
