import java.util.*;

public class testGraph {
    public static void main(String[] args) {
        AdjMapGraph<String,String> graph = new AdjMapGraph<String, String>(false);
        IVertex<String> v1 = graph.insertVertex("A");
        IVertex<String> v2 = graph.insertVertex("B");
        IVertex<String> v3 = graph.insertVertex("C");
        IVertex<String> v4 = graph.insertVertex("D");
        IVertex<String> v5= graph.insertVertex("E");
        IVertex<String> v6 = graph.insertVertex("F");
        IVertex<String> v7 = graph.insertVertex("G");
        IVertex<String> v8 = graph.insertVertex("H");
        IVertex<String> v9 = graph.insertVertex("I");
        IVertex<String> v10 = graph.insertVertex("J");
        IVertex<String> v11= graph.insertVertex("K");
        IVertex<String> v12= graph.insertVertex("L");
        IVertex<String> v13= graph.insertVertex("M");
        IVertex<String> v14= graph.insertVertex("N");
        IVertex<String> v15= graph.insertVertex("O");
        IVertex<String> v16= graph.insertVertex("P");


        IEdge<String> e1 = graph.insertEdge(v1,v2,"E1");
        IEdge<String> e2 = graph.insertEdge(v1,v6,"E2");
        IEdge<String> e3 = graph.insertEdge(v1,v5,"E3");
        IEdge<String> e4 = graph.insertEdge(v2,v3,"E4");
        IEdge<String> e5 = graph.insertEdge(v6,v9,"E5");
        IEdge<String> e6 = graph.insertEdge(v5,v9,"E6");
        IEdge<String> e7 = graph.insertEdge(v2,v6,"E7");
        IEdge<String> e8 = graph.insertEdge(v3,v4,"E8");
        IEdge<String> e9 = graph.insertEdge(v3,v7,"E9");
        IEdge<String> e10 = graph.insertEdge(v5,v6,"E10");
        IEdge<String> e11 = graph.insertEdge(v9,v10,"E11");
        IEdge<String> e12 = graph.insertEdge(v9,v13,"E12");
        IEdge<String> e13 = graph.insertEdge(v9,v14,"E13");
        IEdge<String> e14 = graph.insertEdge(v13,v14,"E14");
        IEdge<String> e15 = graph.insertEdge(v10,v7,"E16");
        IEdge<String> e16 = graph.insertEdge(v7,v4,"E17");
        IEdge<String> e17 = graph.insertEdge(v10,v11,"E18");
        IEdge<String> e18 = graph.insertEdge(v11,v14,"E19");
        IEdge<String> e19 = graph.insertEdge(v11,v15,"E20");
        IEdge<String> e20 = graph.insertEdge(v11,v7,"E21");
        IEdge<String> e21 = graph.insertEdge(v4,v8,"E22");
        IEdge<String> e22 = graph.insertEdge(v8,v12,"E23");
        IEdge<String> e23 = graph.insertEdge(v12,v16,"E24");
        IEdge<String> e24 = graph.insertEdge(v7,v12,"E25");



        System.out.println(graph.toString());
        Set<IVertex<String>> known = new HashSet<IVertex<String>>();
        Map<IVertex<String>,IEdge<String>> forest = new HashMap<>();
        graph.BFS(graph,v1,known,forest);
        System.out.println(known.size());







    }
}
