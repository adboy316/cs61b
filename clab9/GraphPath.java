//import java.util.HashMap;
//import java.util.Set;
//
//public class GraphPath {
//
//    Graph g;
//
//    private HashMap<String, Boolean> marked;
//    private String[] edgeTo;
//    private String s;
//    String[] targetArray;
//
//
//
//
//    public  GraphPath(Graph G, String s) {
//        this.g = G;
//        this.s = s;
//        marked = new boolean[g.labels().size()];
//        edgeTo = new String[g.labels().size()];
//        Set<String> labelString = g.labels();
//        targetArray = labelString.toArray(new String[labelString.size()]);
//
//        dfs(G, s);
//
//    }
//
//
//
//
//
//    private void dfs(Graph G, String v) {
//
//        marked[v] = true;
//        for ( w : G) {
//            if (!marked[w]) {
//                edgeTo[w] = v;
//                dfs(G, w);
//            }
//        }
//
//
//    }
//
//
//        public static void main(String[] args) {
//        Graph g = new Graph();
//        g.connect("A", "B");
//        g.connect("A", "C");
//        g.connect("A", "D");
//        g.connect("C", "D");
//        //GraphPath gp = new GraphPath(g, "A");
//
//
//
//    }
//
//}
