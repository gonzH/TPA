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
            
            g.insertEdge("A", "D", "ad", ""); g.getEdge("ad").setCusto(7);
            g.insertEdge("A", "B", "ab", ""); g.getEdge("ab").setCusto(3);
            g.insertEdge("B", "A", "ba", ""); g.getEdge("ba").setCusto(8);
            g.insertEdge("B", "C", "bc", ""); g.getEdge("bc").setCusto(2);
            g.insertEdge("C", "A", "ca", ""); g.getEdge("ca").setCusto(5);
            g.insertEdge("C", "D", "cd", ""); g.getEdge("cd").setCusto(1);
            g.insertEdge("D", "A", "da", ""); g.getEdge("da").setCusto(2);

            ProcessaGrafo pg = new ProcessaGrafo(g);

            int[][] fwm = pg.FloydWarshall();
            
            for(int i = 0; i < fwm.length; i++) {
                for(int j = 0; j < fwm.length; j++) {
                    System.out.print(String.format("%d ", fwm[i][j]));
                }
                System.out.println("");
            }
            
            DSFloydW dsfw = new DSFloydW(fwm);
            
            
			
	} /* fim de main */

} /* fim da classe ProcGrafoBench */
