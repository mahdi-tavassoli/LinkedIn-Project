import java.io.IOException;
import java.util.*;
public class testGraph {
    public static void main(String[] args) throws IOException {
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
                            System.out.print("[ 4. New Suggested Connections ]\n");
                            System.out.print("[ 5. Your Requests ]\n");
                            System.out.print("[ 6. Exit from Account ]\n");
                            System.out.print("\nPlease Enter Your Menu Choice: ");
                            choice = input.nextInt();
                            switch (choice) {

                                case 1:
                                    System.out.println("--> Your Profile <--");
                                    graph.printVertex(Integer.toString(ID));
                                    break;
                                case 2:
                                    System.out.println("--> View All Members <--");
                                    System.out.println(graph.toString());
                                    break;
                                case 3:
                                    System.out.println("--> Search Member <--");
                                    graph.Search(ID);
                                    break;
                                case 4:
                                    System.out.println("--> New Suggested Connections List (Top20) <--");
                                    graph.SuggestedList(graph,Integer.toString(ID));
                                    break;
                                case 5:
                                    System.out.println("--> Requests <--");
                                    graph.readRequest(graph,Integer.toString(ID));
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
                    System.out.println("--> SIGN_UP <--");
                    String newID = Integer.toString(graph.getNewId()) ;
                    System.out.println("[ Your ID number will be: ("+newID+") ]");
                    System.out.print("--> Please Enter Your Name:\n");
                    String nameNewUser = input.next();
                    System.out.print("--> Please Enter Your Family Name:\n");
                    String familyNewUser = input.next();
                    String fullName = nameNewUser+" "+familyNewUser;
                    System.out.println("--> Please Enter Date Of Birth: [Example: yyyy/mm/dd ] ");
                    String dateOfBrith = input.next();
                    System.out.println("--> Please Enter University Location: [Example: AA] ");
                    String university = input.next();
                    System.out.println("--> Please Enter Your major: [Example: BB] ");
                    String Field = input.next();
                    System.out.println("--> Please Enter Your Work Place: ");
                    String workPlace = input.next();
                    System.out.println("[--> Your Skills <--]");
                    System.out.println("--> Please Enter The Number Of Your Specialties: ");
                    int counter = input.nextInt();
                    ArrayList<String> skills = new ArrayList<>();
                    System.out.println("[ PLease Enter Your Skills List In Order Of Priority ]");
                    for (int i=0 ; i<counter ; i++){
                        System.out.println("[ Please Entr Your Skill Number:("+(i+1)+") ]");
                        String current = input.next();
                        skills.add(i,current);
                    }
                    IVertex<String> vertex = graph.insertVertex(newID,fullName,dateOfBrith,university,Field,workPlace,skills);
                    System.out.println("[ Your Registration Was Successful By Id Number: ["+vertex.getElement()+"] And Full Name: ["+vertex.getName()+" ]] :)");
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
    }
}
