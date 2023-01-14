import java.util.*;
public class testGraph {
    public static void main(String[] args) {
        AdjMapGraph<String,String> graph = new AdjMapGraph<String, String>(false);
        graph.ReadJsonFile(graph);
        Scanner input = new Scanner(System.in);
        boolean logIn = true;
        int selection;
        int ID = 0;
        while (logIn){
            boolean mainLoop = true;
            System.out.println("--> Log In & Sign Up Menu <-- \n");
            System.out.print("[ 1. Log-in]\n");
            System.out.print("[ 2. Sign-up ]\n");
            System.out.print("[ 3. Exit ]\n");
            System.out.print("\nPlease Enter Your Menu Choice: ");
            selection = input.nextInt();
            switch (selection){
                case 1:
                    System.out.println("--> LOG-IN <--");
                    System.out.print("Please Enter Your ID: \n");
                    int idNumber = input.nextInt();
                    System.out.print("Please Enter Your name: \n");
                    String name = input.next();
                    if (graph.checkUser(idNumber,name)==true){
                        System.out.println("Correct");
                        System.out.println("You Are Logged up :)");
                        ID = idNumber;
                        int choice;
                        while(mainLoop) {
                            System.out.println("--> Linkedin Menu <--\n");
                            System.out.print("[ 1. View Profile ] \n");
                            System.out.print("[ 2. View All Members ]\n");
                            System.out.print("[ 3. Search Member ]\n");
                            System.out.print("[ 4. Suggested New Connection]\n");
                            System.out.print("[ 5. Your Request ]\n");
                            System.out.print("[ 6. Exit from Account]\n");
                            System.out.print("\nPlease Enter Your Menu Choice: ");
                            choice = input.nextInt();
                            switch (choice) {

                                case 1:
                                    graph.printVertex(Integer.toString(ID));
                                    break;
                                case 2:
                                    System.out.println("--> View All Members <--");
                                    System.out.println(graph.toString());
                                    break;

                                case 3:
                                    System.out.println();

                                    break;

                                case 4:

                                    break;

                                case 5:

                                    break;
                                case 6:
                                    System.out.println("Exiting Program...");
                                    mainLoop = false;
                                    break;
                                default:
                                    System.out.println("This is not a valid Menu Option! Please Select Another");
                                    break;

                            }
                        }
                    }
                    else{
                        System.out.println("Not Found :/");
                        System.out.println("Please Enter Correct Information :)");
                    }
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("Exiting Program...");
                    logIn = false;
                    break;
                default:
                    System.out.println("This is not a valid Menu Option! Please Select Another");
                    break;
            }
        }



//        System.out.println(graph.toString());
//        graph.printVertex("1");
//        Set<IVertex<String>> known = new HashSet<IVertex<String>>();
//        Map<IVertex<String>,IEdge<String>> forest = new HashMap<>();
//        graph.BFS(graph,v1,known,forest);
//        System.out.println(known.size());

    }
}
