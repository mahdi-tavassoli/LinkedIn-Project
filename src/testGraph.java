import java.io.IOException;
import java.util.*;
public class testGraph {
    public static void main(String[] args) throws IOException {
        AdjMapGraph<String,String> graph = new AdjMapGraph<String, String>(false);
        graph.ReadJsonFile(graph);
        graph.formatRequests();
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
                                    boolean bool = true;
                                    int uniP = 450;
                                    int field = 400;
                                    int wPlace = 400;
                                    int skillP = 500;
                                    int stageP = 400;
                                    while (bool){
                                        System.out.println("[[1] App's Suggestion ]");
                                        System.out.println("[[2] Filter Suggestion ]");
                                        System.out.println("[[3] Exit ]");
                                        int number = input.nextInt();
                                        switch (number){
                                            case 1:
                                                graph.SuggestedList(graph,Integer.toString(ID), uniP, field,wPlace ,skillP ,stageP);
                                                bool = false;
                                                break;
                                            case 2:
                                                Map<Integer,Integer> filter = new HashMap<>();
                                                filter.put(1,-1);
                                                filter.put(2,-1);
                                                filter.put(3,-1);
                                                filter.put(4,-1);
                                                filter.put(5,-1);
                                                int choices = 5;
                                                int counter = 0;
                                                System.out.println("[ Filter Base On: ]");
                                                System.out.println("[ You Can Choice 3 Items ]");
                                                System.out.println("[1] [ University ]");
                                                System.out.println("[2] [ Field ]");
                                                System.out.println("[3] [ WorkPlace ]");
                                                System.out.println("[4] [ Skills ]");
                                                System.out.println("[5] [ Connection Levels ]");
                                                System.out.println("[6] [ Exit ]");
                                                while (choices!=0){
                                                    int current = input.nextInt();
                                                    if (filter.containsKey(current)){
                                                        filter.replace(current,choices);
                                                        choices--;
                                                        counter++;
                                                        if (current==1)
                                                            System.out.println("["+counter+"][ University ]");
                                                        else if(current==2)
                                                            System.out.println("["+counter+"][ Field ]");
                                                        else if (current == 3)
                                                            System.out.println("["+counter+"][ WorkPlace ]");
                                                        else if (current==4)
                                                            System.out.println("["+counter+"][ Skills ]");
                                                        else
                                                            System.out.println("["+counter+"]");
                                                    }
                                                    else if (current==6){
                                                        System.out.println("The filter will be applied based on "+counter+" your selections :)");
                                                        break;
                                                    }
                                                    else
                                                        System.out.println(" You have entered wrong number :/");
                                                }
                                                graph.SuggestedList(graph,Integer.toString(ID), uniP+(filter.get(1)*75), field+(filter.get(2)*75),wPlace+(filter.get(3)*75) ,skillP+(filter.get(4)*75) ,stageP+(filter.get(5)*75));
                                                bool = false;
                                                break;
                                            case 3:
                                                System.out.println("--> Exit From New Suggested Connection List <--");
                                                bool = false;
                                                break;
                                            default:
                                                System.out.println("This is not a valid Menu Option! Please Select Another");
                                                break;
                                        }
                                    }
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
                    System.out.println("--> Please Enter Your Email Address: [Example: emial@gmail.com ] ");
                    String email = input.next();
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
                    IVertex<String> vertex = graph.insertVertex(newID,fullName,dateOfBrith,email,university,Field,workPlace,skills);
//                    graph.addNewVertexJSON(newID,fullName,dateOfBrith,email,university,Field,workPlace,skills);
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
