import java.util.*;

public class ACC {


        private int time;
        public String name;
        private String Output1 = "";
        private Hashtable<Integer, String> hmap = new Hashtable<>();

    public ACC(String name,List<Flight> flights, ArrayList<ATC> ATCList) {
        this.name = name;
        this.time = 0;
        this.flights = flights;
        this.ATCList = ATCList;
        for (int i=0;i< ATCList.size();i++){
            ATCList.get(i).myACC = this;
            int val = 0;
            for(int j=0;j<ATCList.get(i).name.length();j++){
                char character = ATCList.get(i).name.charAt(j);
                int ascii = (int) character;
                 val += ascii * Math.pow(31,j) ;
            }
            hmap.put(val%1000,ATCList.get(i).name);

        }
        for(Map.Entry m:hmap.entrySet()){
            String key = m.getKey().toString();
            while (key.length() != 3){
                key = "0" + key;
            }
            Output1 += m.getValue() + "" + key + " ";
        }
        for (int i=0;i< flights.size();i++){
            this.ACCReady.offer(flights.get(i));
        }


    }

        private List<Flight> flights;
        public String Output;
        private ArrayList<ATC> ATCList;
        /*Comparator<Flight> comparator = new TimeComparator();
        public PriorityQueue<Flight> ACCReady = new PriorityQueue<>(comparator);*/
        public PriorityQueue<Flight> ACCReady = new PriorityQueue<>();

        public Flight currentFlight = null;

    public void ACCRunning(){



            while(!flights.isEmpty()){

            if(ACCReady.isEmpty()){
                for (int s =0;s<ATCList.size();s++){
                    ATCList.get(s).ATCRunning(time,30);
                }
                if (!ACCReady.isEmpty()){
                    if (ACCReady.peek().time < time + 30) {
                        int diff = time + 30 - ACCReady.peek().time;
                        time = ACCReady.peek().time;
                        for (int s =0;s<ATCList.size();s++){
                            if(ATCList.get(s).onProcess != null){
                                ATCList.get(s).onProcess.time = time;
                                ATCList.get(s).onProcess.operations.set(0,ATCList.get(s).onProcess.operations.get(0)+diff);
                            }
                        }
                    }
                }

            }

                if(!ACCReady.isEmpty()){
                    if(currentFlight==null &&time >= ACCReady.peek().time)
                        {
                            currentFlight = ACCReady.remove();

                        }
                }
                    if (currentFlight != null){

                        if(currentFlight.operations.get(0) <= 30){

                            for (int s =0;s<ATCList.size();s++){
                                ATCList.get(s).ATCRunning(time,currentFlight.operations.get(0));
                            }

                            time += currentFlight.operations.get(0);
                            currentFlight.time = time;
                            currentFlight.operations.remove(0);
                            switch (currentFlight.operations.size()) {
                                case 20, 10:
                                    currentFlight.priority = true;
                                    currentFlight.time += currentFlight.operations.get(0);
                                    currentFlight.operations.remove(0);
                                    ACCReady.offer(currentFlight);
                                    currentFlight = null;
                                    break;
                                case 18:
                                    currentFlight.priority = true;
                                    currentFlight.head.ATCReady.offer(currentFlight);
                                    currentFlight = null;
                                    break;
                                case 8:
                                    currentFlight.priority = true;
                                    currentFlight.tail.ATCReady.offer(currentFlight);
                                    currentFlight = null;
                                    break;
                                case 0:
                                    flights.remove(currentFlight);
                                    if (flights.size() == 0) {
                                        Output=name+ " "+ time + " " + Output1;
                                    }
                                    currentFlight = null;
                                    break;
                            }
                        } else if (currentFlight.operations.get(0) > 30) {
                            for (int s =0;s<ATCList.size();s++){
                                ATCList.get(s).ATCRunning(time,30);
                            }
                            time += 30;
                            currentFlight.time = time ;
                            currentFlight.operations.set(0,currentFlight.operations.get(0)-30);
                            currentFlight.priority = false;
                            ACCReady.offer(currentFlight);
                            currentFlight = null;
                        }

                    } else{

                        if (!ACCReady.isEmpty())    {
                            if (ACCReady.peek().time < time + 30) {
                                for (int s =0;s<ATCList.size();s++){
                                    ATCList.get(s).ATCRunning(time,ACCReady.peek().time-time);
                                }
                                time = ACCReady.peek().time;
                            } else {

                                for (int s =0;s<ATCList.size();s++){
                                    ATCList.get(s).ATCRunning(time,30);
                                }
                                if(ACCReady.peek().time < time + 30){
                                    int diff = time + 30 - ACCReady.peek().time;
                                    time = ACCReady.peek().time;
                                    for (int s =0;s<ATCList.size();s++){
                                        if(ATCList.get(s).onProcess != null){
                                            ATCList.get(s).onProcess.time = time;
                                            ATCList.get(s).onProcess.operations.set(0,ATCList.get(s).onProcess.operations.get(0)+diff);
                                        }
                                    }
                                } else time += 30;

                            }
                        } else {

                            time+=30;
                        }
                    }
            }
        }
    }



