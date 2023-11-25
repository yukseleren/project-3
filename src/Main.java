import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(args[0]));
            String [] q = lines.get(0).split(" ");

            int a = Integer.parseInt(q[0]);
            int b = Integer.parseInt(q[1]);
            File myfile = new File(args[1]);
            if (myfile.createNewFile()) {
                System.out.println("File created: " + myfile.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter bst = new FileWriter(myfile);


            for (int i = 1; i<a+1;i++){
                ArrayList<Flight> flightlist = new ArrayList<>();
                String [] x = lines.get(i).split(" ");
                String ACCName = x[0];
                ArrayList<ATC> tempATC = new ArrayList<>();
                 for (int o=1;o<x.length;o++ ){
                    ATC Temp = new ATC(x[o]);
                    tempATC.add(Temp);
                }
                for (int j = a+1; j<a+b+1;j++){
                    String [] y = lines.get(j).split(" ");
                    if(ACCName.equals(y[2])){
                        ATC head = null;
                        ATC tail = null;
                        for (ATC atc : tempATC){
                            String name = atc.name;
                            if(y[3].equals(name)){
                                head = atc;
                            }
                            if (y[4].equals(name)){
                                tail = atc;
                            }
                        }
                        ArrayList<Integer> operations = new ArrayList<>();
                        for (int s = 5;s<y.length;s++){
                            operations.add(Integer.parseInt(y[s]));
                        }

                        Flight myflight = new Flight(Integer.parseInt(y[0]),y[1],head,tail,operations);
                        flightlist.add(myflight);
                    }

                }
                ACC newACC = new ACC(ACCName,flightlist,tempATC);
                newACC.ACCRunning();
                bst.write(newACC.Output +  "\n");
            }
            bst.close();

        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}