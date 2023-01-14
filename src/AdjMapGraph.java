import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class AdjMapGraph<V,E> implements IGraph<V,E> {
    private class Vertex<V> implements IVertex<V>{
        private V Id;
        private boolean isDirected;
//        private Position<IVertex<V>> pos;
        private V name;
        private V dateOfBirth;
        private V univertsityLocation;
        private V field;
        private V workplace;
        private ArrayList<V> specialists;
        private List<IVertex<V>> connectionID;

        private Map<IVertex<V>, IEdge<E>> outgoing, incoming;

        public Vertex(V id,V name,V dateOfBirth, V univertsityLocation, V field, V workplace , ArrayList<V> arraySpe,boolean isDirected) {
            this.Id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.univertsityLocation = univertsityLocation;
            this.field = field;
            this.workplace = workplace;
            this.specialists = new ArrayList<>();
            specialists = arraySpe;
            connectionID = new LinkedList<>();
            outgoing = new HashMap<IVertex<V>, IEdge<E>>();
            if (isDirected)
                incoming = new HashMap<IVertex<V>, IEdge<E>>();
            else
                incoming = outgoing;
        }
        public void setConnection(List<IVertex<V>> connection){ this.connectionID = connection;}
        public V getElement() {
            return Id;
        }
        public V getName() { return name; }
        public V getDateOfBirth() { return dateOfBirth; }
        public V getUniversityLoca() { return univertsityLocation; }
        public V getField() { return field; }
        public V getWorkPlace() { return workplace; }
        public ArrayList<V> getSpecialist() { return specialists; }
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
        private ArrayList<IVertex<V>> endpoints;
        public Edge(IVertex<V> u, IVertex<V> v, E element){
            this.element = element;
            endpoints = new ArrayList<IVertex<V>>();
            endpoints.add(u);
            endpoints.add(v);
        }
        public E getElement() { return element; }
        public ArrayList<IVertex<V>> getEndpoints(){return endpoints;}

    }
    private boolean isDirected;
    private int Size;
    private LinkedList<Vertex<V>> vertices;
    private Map<V,Vertex<V>> vertices2;
    private LinkedList<Edge<E>> edges;
    public AdjMapGraph(boolean isDirected){
        this.isDirected = isDirected;
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
        vertices2 = new HashMap<>();
        Size = 0;
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
    public IVertex<V> insertVertex( V  id,V name,V dateOfBirth, V univertsityLocation, V field, V workplace,ArrayList arraysp){
        Vertex<V> v = new Vertex<>(id,name,dateOfBirth,univertsityLocation,field,workplace,arraysp,isDirected);
        vertices2.put(id,v);
        vertices.add(v);
        Size++;
        return (IVertex<V>) v;
    }
    public IVertex<V> getVertex(V Id){
        return vertices2.get(Id);
    }
    public IEdge<E> insertEdge(IVertex<V> u, IVertex<V> v, E element) throws IllegalArgumentException{
        if (getEdge(u,v)==null) {
            Edge<E> e = new Edge<>(u, v, element);
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
        Size-=1;
    }
    public void removeEdge(IEdge<E> edge) throws IllegalArgumentException{
        Edge<E> e = validate(edge);
        ArrayList<IVertex<V>> vertices = e.getEndpoints();
        Vertex<V> v0 = validate(vertices.get(0));
        Vertex<V> v1 = validate(vertices.get(1));
        v0.getOutgoing().remove(v1);
        v1.getOutgoing().remove(v0);
        edges.remove(edges.indexOf(e));
    }
    public int getNewId(){
        int NEW = Size;
        return ++NEW;}
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
        System.out.println(start.getElement());
        queue1.add(start);
        while (!queue1.isEmpty()){
            IVertex<V> current = queue1.poll();
            for (IEdge<E> edge : outEdges(current)){
                IVertex<V> adjacent = opposite(current,edge);
                if (!known.contains(adjacent)){
                    known.add(adjacent);
                    System.out.println(adjacent.getElement());
                    forest.put(adjacent,edge);
                    queue1.add(adjacent);
                }
            }
        }

    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (IVertex<V> v : vertices()) {
            sb.append("[ User Id: "+ v.getElement()+" ][ Full name: "+v.getName()+" ]");
            sb.append("\n[ University: "+ v.getUniversityLoca()+" ][ Field: "+v.getField()+" ]");
            sb.append("\n[ Work place: "+ v.getWorkPlace()+" ][ Skills: { ");
            ArrayList<V> array = new ArrayList<>();
            array = v.getSpecialist();
            for (int i=0 ; i<array.size(); i++){
                sb.append((i+1)+"."+array.get(i)+" ");
            }
            sb.append("}]\n");
            if (isDirected){
                sb.append("\t[outgoing]");
            }
            sb.append("\n" +  "--> Connection List: "+outDegree(v) +" <--\n");
            for (IEdge<E> e : outEdges(v)){
                sb.append("\t\t");
                sb.append(String.format("(User %s to User %s via edge %s)",v.getElement(),opposite(v,e).getElement(),e.getElement()));
                sb.append("\n");
                if (isDirected){
                    sb.append("\t[incoming]");
                    sb.append("\t" + inDegree(v)+ " Connection Person:\n");
                    for (IEdge<E> e1 : inEdges(v)){
                        sb.append("\t\t");
                        sb.append(String.format("(User %s to User %s via edge %s)",v.getElement(),opposite(v,e1).getElement(), e.getElement()));
                        sb.append("\n");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public void ReadJsonFile(AdjMapGraph<V,String> graph){
        JSONParser parser = new JSONParser();
        try {
            Object o = new JSONParser().parse(new FileReader("users2.json"));
            JSONArray array = (JSONArray) o;
            for (int i=0 ; i<array.size() ; i++){
                JSONObject object = (JSONObject) array.get(i);
                String id = (String) object.get("id");
                String name = (String) object.get("name");
                String dateOfBirth = (String) object.get("dateOfBirth");
                String universityLocation = (String) object.get("universityLocation");
                String field = (String) object.get("field");
                String workplace = (String) object.get("workplace");
                JSONArray specialties = (JSONArray) object.get("specialties");
                ArrayList<String> arraySpe = new ArrayList<>();
                for (int j=0 ; j<specialties.size(); j++){
                    arraySpe.add(j,(String)specialties.get(j));
                }
                JSONArray connectionId = (JSONArray) object.get("connectionId");
                String[] arrayConnect = new String[connectionId.size()];
                for (int x=0; x<connectionId.size() ; x++){
                    arrayConnect[x] = (String) connectionId.get(x);
                }
                graph.insertVertex((V)id,(V)name,(V)dateOfBirth,(V)universityLocation,(V)field,(V)workplace,arraySpe);
            }
            ReadAndSetEdges(graph);
        }
        catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (ParseException e){e.printStackTrace();}
        catch (Exception e){e.printStackTrace();}
    }
    public void ReadAndSetEdges(AdjMapGraph<V, String> graph){
        JSONParser parser = new JSONParser();
        try {
            Object o = new JSONParser().parse(new FileReader("users2.json"));
            JSONArray array = (JSONArray) o;
            for (int i=0 ; i<array.size() ; i++){
                JSONObject object = (JSONObject) array.get(i);
                String id = (String) object.get("id");
                JSONArray connectionId = (JSONArray) object.get("connectionId");
                Vertex<V> current = validate(vertices2.get(id));
                IVertex<V> IVcurrent  = vertices2.get(id);
                List<IVertex<V>> connectionList = new LinkedList<>();
                for (int x=0; x<connectionId.size() ; x++){
                    String element = "E"+(i+1)+"-"+(x+1);
                    String currCode = (String) connectionId.get(x);
                    IVertex<V> connect = vertices2.get(currCode);
                    connectionList.add(connect);
                    graph.insertEdge(IVcurrent,connect,element);
                }
                current.setConnection(connectionList);
            }
        }
        catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (ParseException e){e.printStackTrace();}
        catch (Exception e){e.printStackTrace();}

    }
    public void printVertex(String id){
        Vertex<V> vertex = validate(vertices2.get(id));
        System.out.println("--> Your Profile <--");
        System.out.println("[ Your Id: "+vertex.Id+" ]");
        String[] name = new String[2];
        String str =(String) vertex.name;
        name = str.split(" ");
        System.out.println("[ Your name: "+name[0]+" ]");
        System.out.println("[ Your Family name: "+name[1]+" ]");
        System.out.println("[ Date of Birth: "+vertex.dateOfBirth+" ]");
        System.out.println("[ University Location: "+vertex.univertsityLocation+" ]");
        System.out.println("[ Your major : "+vertex.field+" ]");
        System.out.println("[ Your work place: "+vertex.workplace+" ]");
        System.out.println("[ Specialties ]");
        ArrayList<V> spel = new ArrayList<>();
        spel= vertex.specialists;
        for (int i=0 ; i<spel.size() ; i++ ){
            System.out.println("  { "+(i+1)+". "+spel.get(i)+" }");
        }
        System.out.println("[ Connection persons ]");
        List<IVertex<V>> connectList = new LinkedList<>();
        connectList = vertex.connectionID;
        for (int j=0 ; j<connectList.size() ; j++){
            Vertex<V> current = validate(connectList.get(j));
            System.out.println(" [ "+(j+1)+". "+current.name+" ]");
            System.out.println("  < Id: "+ current.Id+" >");
            System.out.println("  < Field:"+ current.field+" >");
            System.out.println("  < UniverSity: "+current.univertsityLocation+" >");
        }
    }
    public int addNewVertex(AdjMapGraph<V,V> graph){
        int id = graph.getNewId();

        return id;
    }
    public boolean checkUser(int id, String name){
        String ID = Integer.toString(id);
        if (vertices2.containsKey((V) ID)){
            Vertex<V> vertex = validate(vertices2.get((V)ID));
            String[] array = new String[2];
            String fullName=(String) vertex.name;
            array = fullName.split(" ");
            if (array[0].equals(name)){
                return true;
            }
            else
                return false;
        }else
        return false;
    }

}
