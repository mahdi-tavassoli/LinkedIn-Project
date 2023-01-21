import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class AdjMapGraph<V,E> implements IGraph<V,E> {
    private class Vertex<V> implements IVertex<V>{
        private V Id;
        private boolean isDirected;
        private int point;
        private int ConnectionLevel;
        private V name;
        private V dateOfBirth;
        private V email;
        private V univertsityLocation;
        private V field;
        private V workplace;
        private ArrayList<V> specialists;
        private Map<IVertex<V>, IEdge<E>> outgoing, incoming;
        public Vertex(V id,V name,V dateOfBirth,V email, V univertsityLocation, V field, V workplace , ArrayList<V> arraySpe,boolean isDirected) {
            this.Id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.email = email;
            this.univertsityLocation = univertsityLocation;
            this.field = field;
            this.workplace = workplace;
            this.specialists = new ArrayList<>();
            specialists = arraySpe;
            this.point = 0;
            this.ConnectionLevel = 0;
            outgoing = new HashMap<IVertex<V>, IEdge<E>>();
            if (isDirected)
                incoming = new HashMap<IVertex<V>, IEdge<E>>();
            else
                incoming = outgoing;
        }
        public void resetPoints(){this.point = 0;}
        public int addPoints(int amount){ return this.point+=amount; }
        public int getPoint() { return point; }
        public void resetConnection(){this.ConnectionLevel = 0;}
        public int setConnection(int amount){ return this.ConnectionLevel=amount; }
        public int getConnectionLevel() { return ConnectionLevel; }
        public V getElement() {
            return Id;
        }
        public V getName() { return name; }
        public V getDateOfBirth() { return dateOfBirth; }
        public V getEmail() { return email; }
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
    public IVertex<V> insertVertex( V  id,V name,V dateOfBirth,V email, V univertsityLocation, V field, V workplace,ArrayList arraysp){
        Vertex<V> v = new Vertex<>(id,name,dateOfBirth,email,univertsityLocation,field,workplace,arraysp,isDirected);
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
    public heapPriorityQueue<Integer,String> FindClosetConnection( String start, Map<String,Integer> known,int uniP,int fieldP,int wPlaceP,int skilP,int stageP){
        Queue<IVertex<V>> queue1 = new LinkedList<>();
        IVertex<V> vertex = vertices2.get((V) start);
        heapPriorityQueue<Integer,String> closet = new heapPriorityQueue<>(Integer::compareTo);
        for (IEdge<E> edge : outEdges(vertex)){
            IVertex<V> pConnected = opposite(vertex,edge);
            queue1.add(pConnected);
        }
        int Currrentlevel = queue1.size();
        int nextLevel = 0;
        int stage = 0;
        while (!queue1.isEmpty()){
            IVertex<V> current = queue1.poll();
            Currrentlevel--;
            if (Currrentlevel==0){
                Currrentlevel = nextLevel;
                nextLevel = 0;
                stage++;
            }
            if (stage>4)
                break;
            for (IEdge<E> edge : outEdges(current)){
                if (stage>4)
                    break;
                IVertex<V> adjacent = opposite(current,edge);
                if (!known.containsKey(adjacent.getElement()) && ((String)adjacent.getElement()).compareTo(start)!=0 && isConnected(start,(String)adjacent.getElement())==false ){
                    Vertex<V> v = validate(adjacent);
                    int point = compareToPerson(vertex,adjacent, uniP, fieldP, wPlaceP,skilP);
                    v.addPoints(stageP-(stage*50));
                    v.setConnection(stage+1);
                    if (closet.size()==0){
                        known.put((String)adjacent.getElement(),v.point);
                        closet.insert(v.point,(String)adjacent.getElement());
                    }
                    else {
                        if (closet.size()>19){
                            if ((int) v.point>closet.min().getKey()){
                                known.put((String)adjacent.getElement(),v.point);
                                closet.removeMin();
                                closet.insert(v.point,(String)adjacent.getElement());
                            }

                        }else {
                            known.put((String)adjacent.getElement(),v.point);
                            closet.insert(v.point,(String)adjacent.getElement());
                        }
                    }
                    queue1.add(adjacent);
                    nextLevel++;
                }

            }

        }
        return closet;
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
            Object o = new JSONParser().parse(new FileReader("users1.json"));
            JSONArray array = (JSONArray) o;
            for (int i=0 ; i<array.size() ; i++){
                JSONObject object = (JSONObject) array.get(i);
                String id = (String) object.get("id");
                String name = (String) object.get("name");
                String dateOfBirth = (String) object.get("dateOfBirth");
                String email = (String) object.get("email");
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
                graph.insertVertex((V)id,(V)name,(V)dateOfBirth,(V) email,(V)universityLocation,(V)field,(V)workplace,arraySpe);
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
            Object o = new JSONParser().parse(new FileReader("users1.json"));
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
                    if (isConnected(currCode,id)==false)
                       graph.insertEdge(IVcurrent,connect,element);
                }
            }
        }
        catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (ParseException e){e.printStackTrace();}
        catch (Exception e){e.printStackTrace();}

    }
    public void printVertex(String id){
        Vertex<V> vertex = validate(vertices2.get(id));
        System.out.println("[ Your Id: "+vertex.Id+" ]");
        String[] name = new String[2];
        String str =(String) vertex.name;
        name = str.split(" ");
        System.out.println("[ Your name: "+name[0]+" ]");
        System.out.println("[ Your Family name: "+name[1]+" ]");
        System.out.println("[ Date of Birth: "+vertex.dateOfBirth+" ]");
        System.out.println("[ Email Address: "+vertex.email+" ]");
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
        int j=1;
        for (IEdge<E> e : outEdges(vertex)){
            Vertex<V> current = validate(opposite(vertex,e));
            System.out.println(" [ "+j+". "+current.name+" ]");
            System.out.println("  < Id: "+ current.Id+" >");
            System.out.println("  < Field:"+ current.field+" >");
            System.out.println("  < University: "+current.univertsityLocation+" >");
            j++;
        }
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
    public void Search(int id) throws IOException {
        String ID = Integer.toString(id);
        Scanner scanner = new Scanner(System.in);
        Vertex<V> vertex = validate(vertices2.get((V)ID));
        boolean searchBollean = true;
        while (searchBollean){
            System.out.println("Enter id for searching member or -1 to Exit: ");
            String number = scanner.next();
            if (number.equals("-1")){
                System.out.println("Exiting Search ..." );
                break;
            }
            else {
                if (checkUserID(number)==true && ID.equals(number)==false){
                    Vertex<V> person = validate(vertices2.get((V) number));
                    boolean connection = isConnected(ID,number);
                    if (connection==true){
                        System.out.println("[ Connected ]");
                        System.out.println("[--> Friend Profile ( "+person.Id+" )<--]");
                        String[] name = new String[2];
                        String str =(String) person.name;
                        name = str.split(" ");
                        System.out.println("[ Friend name: "+name[0]+" ]");
                        System.out.println("[ Friend Family name: "+name[1]+" ]");
                        System.out.println("[ University Location: "+person.univertsityLocation+" ]");
                        System.out.println("[ Friend major : "+person.field+" ]");
                        System.out.println("[ Friend work place: "+person.workplace+" ]");
                        System.out.println("[ Friend's Skills: ]");
                        ArrayList<V> spel = new ArrayList<>();
                        spel= person.specialists;
                        for (int i=0 ; i<spel.size() ; i++ ){
                            System.out.println("  { "+(i+1)+". "+spel.get(i)+" }");
                        }
                        System.out.println("[ Friend Connected List: ]");
                        System.out.print("{");
                        for (IEdge<E> e : outEdges(person)){
                            IVertex<V> current = opposite(person,e);
                            System.out.print("[ "+current.getElement()+" ]");
                        }
                        System.out.println("}");
                        System.out.println("[ For Breaking connection Just Enter 1]");
                    }else {
                        System.out.println("[ Unconnected ]");
                        System.out.println("[--> Private Profile ( "+person.Id+" )<--]");
                        String[] name = new String[2];
                        String str =(String) person.name;
                        name = str.split(" ");
                        System.out.println("[ Person's name: "+name[0]+" ]");
                        System.out.println("[ Person's Family name: "+name[1]+" ]");
                        System.out.println("[ University Location: "+person.univertsityLocation+" ]");
                        System.out.println("[ Person's major : "+person.field+" ]");
                        System.out.println("[ For Sending request to connect Just Enter 1]");
                    }
                    int requestOrbreake = scanner.nextInt();
                    if (requestOrbreake==1 && connection==true){
                        breakConnection(ID,number);
                        System.out.println("Your connection with ["+person.name+" ("+person.Id+") ] was broken.");

                    }else if (requestOrbreake==1 && connection==false){
                        sendRequest(ID,number, 0);
                        System.out.println("Your request sent to ["+person.name+" ("+person.Id+") ]");
                    }
                    else if(requestOrbreake!=1)
                        System.out.println("You entered wrong number :/");

                }else {
                    System.out.println("The id that you have entered not found :/");
                    System.out.println("Please try again :)");
                }

            }
        }
    }
    public void breakConnection(String idUser, String friend){
        IVertex<V> user = vertices2.get((V) idUser);
        IVertex<V> Friend = vertices2.get((V)friend);
        IEdge<E> e = getEdge(user,Friend);
        removeEdge(e);
        updateJson(idUser,friend,0);
    }
    public boolean isConnected(String idUser, String friend){
        IVertex<V> user = vertices2.get((V) idUser);
        Vertex<V> Friend = validate(vertices2.get((V)friend));
        for (IEdge<E> e: outEdges(user)){
            IVertex<V> connections = opposite(user,e);
            if (connections.getElement().equals(Friend.Id))
                return true;
        }
        return false;
    }
    public boolean checkUserID(String id){
        if (vertices2.containsKey((V)id)==true)
            return true;
        else
            return false;
    }
    public void readRequest(AdjMapGraph<V, String> graph,String ID) throws IOException {
        ArrayList<String> request = new ArrayList<>();
        IVertex<V> user = vertices2.get((V) ID);
        Scanner scanner = new Scanner(System.in);
        request = readFile(ID);
        if (request.size()==0){
            System.out.println("You have not received any request ;/");
        }else {
            String[] part = null;
            for (int i=0 ; i<request.size(); i++){
                String str = request.get(i);
                part = str.split("#");
                if (part[0].compareTo(ID)==0){
                    IVertex<V> vertex = vertices2.get((V) part[2]);
                    System.out.println("[ User: "+vertex.getName()+" ("+vertex.getElement()+") has not yet viewed your request! ]");
                }
                if (part[2].compareTo(ID)==0){
                    IVertex<V> vertex = vertices2.get((V) part[0]);
                    if (part[3].compareTo("0")==0){
                        System.out.println("[ User: "+vertex.getName()+" ("+vertex.getElement()+") wants to connect with you. ]");
                        System.out.println("[ 1.Accept ] [ 2.Reject]");
                        int number = scanner.nextInt();
                        if (number == 1){
                            graph.insertEdge(user,vertex,"E-N");
                            Delete_Request(part[0],part[2],0);
                            sendRequest(part[2],part[0],1);
                            System.out.println("[ You accepted the request from User: "+vertex.getName()+" ("+vertex.getElement()+") ]");
                        }else if (number == 2){
                            Delete_Request(part[0],part[2],0);
                            sendRequest(part[2],part[0],-1);
                            System.out.println("[ You rejected the request from User: "+vertex.getName()+" ("+vertex.getElement()+") ]");

                        }else if (number!=1 && number!=2){
                            System.out.println("[ * You entered wrong number * ]");
                        }

                    }
                    if (part[3].compareTo("1")==0){
                        System.out.println("[ User: "+vertex.getName()+" ("+vertex.getElement()+") has accepted your request ] :)");

                    }
                    if (part[3].compareTo("-1")==0){
                        System.out.println("[ User: "+vertex.getName()+" ("+vertex.getElement()+") has rejected your request ] :/");
                    }
                }

            }
        }

    }
    public ArrayList<String> readFile(String ID) throws IOException{
        ArrayList<String> request = new ArrayList<>();
        FileReader reader1=null;
        BufferedReader buffer1=null;
        String line1= null;
        String[] part = null;
        try{
            reader1 = new FileReader("request1.txt");
            buffer1 = new BufferedReader(reader1);
            while((line1 = buffer1.readLine())!=null){
                part = line1.split("#");
                if(part[2].compareTo(ID)==0 || part[0].compareTo(ID)==0){
                    request.add(line1);
                }

            }
        }
        finally {
            reader1.close();
            buffer1.close();
        }
        return request;
    }
    public int compareToPerson(IVertex<V> userAction, IVertex<V> user,int uniP,int fieldP,int wPlaceP,int skilP){
        int points = 0;
        Vertex<V> v = validate(user);
        v.resetPoints();
        v.resetConnection();
        String UAName =(String) userAction.getName();
        String[] UAname = UAName.split(" ");
        String UName = (String) user.getName();
        String[] Uname = UName.split(" ");
        String date1 = (String) userAction.getDateOfBirth();
        String date2 = (String) user.getDateOfBirth();
        String uni1 = (String) userAction.getUniversityLoca();
        String uni2 = (String) user.getUniversityLoca();
        String field1 = (String) userAction.getField();
        String field2 = (String) user.getField();
        String Wplace1 = (String) userAction.getWorkPlace();
        String Wplace2 = (String) user.getWorkPlace();
        ArrayList<V> skillList1 = userAction.getSpecialist();
        ArrayList<V> skillList2 = user.getSpecialist();
        if (UAname[0].compareTo(Uname[0])==0)
            points+=30;
        if (UAname[1].compareTo(Uname[1])==0)
            points+=30;
        if (date1.compareTo(date2)==0)
            points+=50;
        if (uni1.compareTo(uni2)==0)
            points+=uniP;
        if (field1.compareTo(field2)==0)
            points +=fieldP;
        if (Wplace1.compareTo(Wplace2)==0)
            points+=wPlaceP;
        int size = skillList1.size();
        for (int i=0 ; i<size; i++ ){
            int calculate = skilP - (i*20);
            for (int j=0 ; j<skillList2.size() ; j++){
                if (((String)skillList1.get(i)).compareTo((String)skillList2.get(j))==0)
                    points+=calculate;
            }
        }
        v.addPoints(points);
        return points;
    }
    public void sendRequest(String id1, String id2, int type) throws IOException{
        FileWriter writer = null;
        try{
            writer = new FileWriter("request1.txt",true);
            writer.write(id1+"#"+" : has sent you request to have connection with : "+"#"+id2+"#"+type+"#\n");
        }
        finally {
            writer.close();
        }

    }
    public void printSuggestion(String id){
        Vertex<V> person = validate(vertices2.get((V) id));
        System.out.println("[ Unconnected ]");
        System.out.println("[--> Private Profile ( "+person.Id+" )<--]");
        String[] name = new String[2];
        String str =(String) person.name;
        name = str.split(" ");
        System.out.println("[ Person's name: "+name[0]+" ]");
        System.out.println("[ Person's Family name: "+name[1]+" ]");
        System.out.println("[ University Location: "+person.univertsityLocation+" ]");
        System.out.println("[ Person's major : "+person.field+" ]");
        System.out.println("[ Person's work place : "+person.workplace+" ]");
        System.out.println("[ Points : "+person.point+" ]");
        System.out.println("[ Connection Level : "+person.ConnectionLevel+" ]");
        ArrayList<V> spel = new ArrayList<>();
        spel= person.specialists;
        for (int i=0 ; i<spel.size() ; i++ ){
            System.out.println("  { "+(i+1)+". "+spel.get(i)+" }");
        }
    }
    public void formatRequests() throws IOException{
        Formatter formatter = null;
        try {
            formatter = new Formatter("request1.txt");
        }finally {
            formatter.close();
        }
    }
    public void Delete_Request(String userAction,String user, int type) throws IOException{
        Formatter formatter1=null,formatter2 = null;
        FileWriter writer1=null,writer2 = null;
        FileReader reader1=null,reader2 = null;
        BufferedReader buffer1=null,buffer2 = null;
        String line1,line2 = null;
        String[] part = null;
        try{
            formatter1 = new Formatter("request2.txt");
            writer1 = new FileWriter("request2.txt",true);
            reader1 = new FileReader("request1.txt");
            buffer1 = new BufferedReader(reader1);
            while((line1 = buffer1.readLine())!=null){
                part = line1.split("#");
                if(part[0].compareTo(userAction)==0){
                    if(part[2].compareTo(user)==0){
                        if (part[3].compareTo(Integer.toString(type))!=0)
                            writer1.write(line1+"\n");
                    }
                    else
                        writer1.write(line1+"\n");
                }
                else
                    writer1.write(line1+"\n");
            }

        }
        finally {
            formatter1.close();
            writer1.close();
            reader1.close();
            buffer1.close();

        }
        try {
            formatter2 = new Formatter("request1.txt");
            writer2 = new FileWriter("request1.txt",true);
            reader2 = new FileReader("request2.txt");
            buffer2 = new BufferedReader(reader2);
            while((line2 = buffer2.readLine())!=null){
                writer2.write(line2+"\n");
            }
        }
        finally {
            formatter2.close();
            writer2.close();
            reader2.close();
            buffer2.close();

        }

    }
    public void SuggestedList(AdjMapGraph<V,E> graph ,String id,int uniP,int fieldP,int wPlaceP,int skilP,int stageP) throws IOException {
        Scanner scanner = new Scanner(System.in);
        IVertex<V> vertex = vertices2.get((V) id);
        Vertex<V> VERTEX = validate(vertex);
        heapPriorityQueue<Integer,String> heap = new heapPriorityQueue<>(Integer::compareTo);
        Map<String,Integer> Top10 = new HashMap<>();
        heap = FindClosetConnection(id,Top10, uniP, fieldP, wPlaceP, skilP, stageP);
        for (IVertex<V> current : vertices()){
            String user = (String) current.getElement();
            if (!isConnected(id,user) && !Top10.containsKey((V) user) && id.compareTo(user)!=0){
                int points = compareToPerson(vertex,current, uniP, fieldP, wPlaceP, skilP);
                int min = 0;
                if (!heap.isEmpty())
                 min = heap.min().getKey();
                if (points>min){
                    if (!heap.isEmpty())
                        Top10.remove(heap.removeMin().getValue());
                    heap.insert(points,user);
                    Top10.put(user,points);
                }
            }
        }
        int counter = heap.size();
        while (heap.size()!=0){
            System.out.println();
            System.out.println("[Number: ["+counter+"]]");
            printSuggestion(heap.removeMin().getValue());
            counter--;
        }
        while (true){
            System.out.println("[ Do you want send request to one of them? Just enter person's id ...]");
            System.out.println("[ Exit Enter[-1]]");
            int number = scanner.nextInt();
            if (number==-1){
                System.out.println("--> End Of New Suggested Connections List (Top20) <--");
                break;
            }
            else {
                String id2 = Integer.toString(number);
                if (Top10.containsKey(Integer.toString(number))){
                    IVertex<V> user = vertices2.get((V) id2);
                    sendRequest(id,id2,0);
                    Top10.remove(id2);
                    System.out.println("[ Your request sent to ["+user.getName()+" ("+user.getElement()+") ]]");

                }else {
                    System.out.println("[ The number that you entered not found or you have sent request before :/ ]");
                }
            }
        }

    }
    public void addNewVertexJSON( String  id,String name,String dateOfBirth,String email, String univertsityLocation, String field, String workplace,ArrayList arraysp){
        JSONObject newUser = new JSONObject();
        newUser.put("id", id);
        newUser.put("name", name);
        newUser.put("dateOfBirth",dateOfBirth);
        newUser.put("email", email);
        newUser.put("field",field);
        newUser.put("universityLocation", univertsityLocation);
        JSONArray specialties = new JSONArray();
        specialties.addAll(Arrays.asList(arraysp));
        newUser.put("specialties", specialties);
        JSONArray connectionId = new JSONArray();
        newUser.put("connectionId", connectionId);
        try {
            FileReader reader = new FileReader("users1.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            FileWriter file = new FileWriter("users1.json",false);
            file.write(newUser.toJSONString());
            file.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    public void updateJson(String actionUser,String id, int type){
        JSONParser parser = new JSONParser();
        try {
            Object o = new JSONParser().parse(new FileReader("users1.json"));
            JSONArray array = (JSONArray) o;
            for (int i=0 ; i<array.size() ; i++){
                JSONObject object = (JSONObject) array.get(i);
                String ID = (String) object.get("id");
                if ( ID.compareTo(actionUser)==0){
                    System.out.println("test");
                    JSONArray connectionId = (JSONArray) object.get("connectionId");
                    if (type==0){
                        connectionId.remove(id);

                    }else {
                        connectionId.add(id);
                    }
                }
                if (ID.compareTo(id)==0){
                    JSONArray connectionId = (JSONArray) object.get("connectionId");
                    if (type==0){
                        connectionId.remove(actionUser);
                    }else {
                        connectionId.add(actionUser);
                    }
                }
            }
        }
        catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (ParseException e){e.printStackTrace();}
        catch (Exception e){e.printStackTrace();}

    }

}
