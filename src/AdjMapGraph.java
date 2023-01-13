import java.util.*;
public class AdjMapGraph<V,E> implements IGraph<V,E> {
    private class Vertex<V> implements IVertex<V>{
        private V element;
        private boolean isDirected;
//        private Position<IVertex<V>> pos;
        private Map<IVertex<V>, IEdge<E>> outgoing, incoming;
        public Vertex(V element, boolean isDirected) {
            this.element = element;
            outgoing = new HashMap<IVertex<V>, IEdge<E>>();
            if (isDirected)
                incoming = new HashMap<IVertex<V>, IEdge<E>>();
            else
                incoming = outgoing;
        }
        public V getElement() {
            return element;
        }
//        public void setPosition(Position<IVertex<V>> p){pos = p;}
//        public Position<IVertex<V>> getPosition(){return pos;}
        public Map<IVertex<V>, IEdge<E>> getIncoming() throws IllegalArgumentException{
            if (isDirected)
                return incoming;
            else
                throw new IllegalArgumentException("Can not call getIncoming() on undirected graph :/");
        }
        public Map<IVertex<V>, IEdge<E>> getOutgoing(){
            return outgoing;
        }
    }
    private class Edge<E> implements IEdge<E> {
        private E element;
//        private Position<IEdge<E>> pos;
        private ArrayList<IVertex<V>> endpoints;
        public Edge(IVertex<V> u, IVertex<V> v, E element){
            this.element = element;
            endpoints = new ArrayList<IVertex<V>>();
            endpoints.add(u);
            endpoints.add(v);
        }
        public E getElement() { return element; }
        public ArrayList<IVertex<V>> getEndpoints(){return endpoints;}
//        public void setPosition(Position<IEdge<E>> p){pos = p;}
//        public Position<IEdge<E>> getPosition(){return pos;}
    }
    private boolean isDirected;
//    private PositionalList<IVertex<V>> vertices = new LinkedPositionalList<>();
//    private PositionalList<IEdge<E>> edges = new LinkedPositionalList<>();
    private LinkedList<Vertex<V>> vertices;
    private LinkedList<Edge<E>> edges;
    public AdjMapGraph(boolean isDirected){
        this.isDirected = isDirected;
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }
    @SuppressWarnings({"unchecked"})
    private <V> Vertex<V> validate(IVertex<V> v){
        if (!(v instanceof Vertex)){
            throw new IllegalArgumentException("Invalid vertex :/");
        }
        Vertex<V> vertex = (Vertex<V>) v;
        return vertex;
    }
    @SuppressWarnings({"unchecked"})
    private <E> Edge<E> validate(IEdge<E> e){
        if (!(e instanceof Edge)){
            throw  new IllegalArgumentException("Invalid edges :/");
        }
        Edge<E> edge = (Edge<E>) e;
        return edge;
    }
    public IVertex<V> insertVertex( V element){
        Vertex<V> v = new Vertex<>(element,isDirected);
        vertices.add(v);
//        v.setPosition(vertices.addLast(v));
        return (IVertex<V>) v;
    }
    public IEdge<E> insertEdge(IVertex<V> u, IVertex<V> v, E element) throws IllegalArgumentException{
        if (getEdge(u,v)==null) {
            Edge<E> e = new Edge<>(u, v, element);
//            e.setPosition(edges.addLast(e));
            edges.add(e);
            Vertex<V> origin = validate(u);
            Vertex<V> destination = validate(v);
            origin.getOutgoing().put(v, e);
            if (isDirected)
                destination.getIncoming().put(u,e);
            else
                destination.getOutgoing().put(u,e);
            return (IEdge<E>) e;
        }
        else
            throw  new IllegalArgumentException("There is already an edge between u and v ;/");
    }
    public void removeVertex(IVertex<V> vertex) throws IllegalArgumentException {
        Vertex<V> v = validate(vertex);
        for (IEdge<E> e : v.getOutgoing().values()){
            removeEdge(e);
        }
        for (IEdge<E> e : v.getIncoming().values()){
            removeEdge(e);
        }
        vertices.remove(vertices.indexOf(v));
    }
    public void removeEdge(IEdge<E> edge) throws IllegalArgumentException{
        Edge<E> e = validate(edge);
        ArrayList<IVertex<V>> vertices = e.getEndpoints();
        Vertex<V> v0 = validate(vertices.get(0));
        Vertex<V> v1 = validate(vertices.get(1));
        v0.getOutgoing().remove(v1);
        v1.getIncoming().remove(v0);
        edges.remove(edges.indexOf(e));
    }
    public int numEdges() { return edges.size(); }
    public int numVertices() {
        return vertices.size();
    }
    public Iterable<Vertex<V>> vertices(){ return  vertices; }
    public Iterable<Edge<E>> edges(){
        return edges;
    }
    public IEdge<E> getEdge(IVertex<V> u, IVertex<V> v){
        Vertex<V> vertex = validate(u);
        return vertex.getOutgoing().get(v);
    }
    public ArrayList<IVertex<V>> endpoints(IEdge<E> edge){
        Edge<E> e = validate(edge);
        return e.getEndpoints();
    }
    public IVertex<V> opposite(IVertex<V> vertex, IEdge<E> edge) throws IllegalArgumentException{
        Edge<E> e = validate(edge);
        ArrayList<IVertex<V>> endpoints = e.getEndpoints();
        if (endpoints.get(0).equals(vertex)){
            return endpoints.get(1);
        } else if (endpoints.get(1).equals(vertex))
            return endpoints.get(0);
        else
            throw new IllegalArgumentException("The vertex you passed is not incident to the edge you passed :/");
    }
    public int inDegree(IVertex<V> v){
        Vertex<V> vertex = validate(v);
        return vertex.getIncoming().size();
    }
    public int outDegree(IVertex<V> v){
        Vertex<V> vertex = validate(v);
        return vertex.getOutgoing().size();
    }
    public Iterable<IEdge<E>> inEdges(IVertex<V> v){
        return validate(v).getIncoming().values();
    }
    public Iterable<IEdge<E>> outEdges(IVertex<V> v){
        return validate(v).getOutgoing().values();
    }
    public void BFS(AdjMapGraph<V,E> graph, IVertex<V> start, Set<IVertex<V>> known, Map<IVertex<V>, IEdge<E>> forest){
        Queue<IVertex<V>> queue1 = new LinkedList<>();
        known.add(start);
        queue1.add(start);
        while (!queue1.isEmpty()){
            IVertex<V> current = queue1.poll();
            for (IEdge<E> edge : outEdges(current)){
                IVertex<V> adjacent = opposite(current,edge);
                if (!known.contains(adjacent)){
                    known.add(adjacent);
                    forest.put(adjacent,edge);
                    queue1.add(adjacent);
                }
            }
        }

    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (IVertex<V> v : vertices()) {
            sb.append("Vertex "+ v.getElement()+"\n");
            if (isDirected){
                sb.append("\t[outgoing]");
            }
            sb.append("\t" + outDegree(v) + " adjacent vertices:\n");
            for (IEdge<E> e : outEdges(v)){
                sb.append("\t\t");
                sb.append(String.format("(node %s to node %s via edge %s)",v.getElement(),opposite(v,e).getElement(),e.getElement()));
                sb.append("\n");
                if (isDirected){
                    sb.append("\t[incoming]");
                    sb.append("\t" + inDegree(v)+ " adjacent vertices:\n");
                    for (IEdge<E> e1 : inEdges(v)){
                        sb.append("\t\t");
                        sb.append(String.format("(node %s to node %s via edgw %s)",v.getElement(),opposite(v,e1).getElement(), e.getElement()));
                        sb.append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }
}
