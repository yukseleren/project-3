import java.util.Comparator;
import java.util.PriorityQueue;

public class ATC {
    public String name;
    public ACC myACC;

    public ATC(String name) {
        this.name = name;
    }

    public Flight onProcess = null;

    public PriorityQueue<Flight> ATCReady = new PriorityQueue<Flight>();


    public void ATCRunning(int time,int time2) {

        if (time2!=0){
            if (!ATCReady.isEmpty()) {
                if (onProcess == null && ATCReady.peek().time <= time+time2) {
                    onProcess = ATCReady.remove();
                    if(onProcess.time>time){
                        time2 = time - onProcess.time + time2;
                        time = onProcess.time;
                    }
                }
            }
            if (onProcess != null) {

                if (onProcess.operations.get(0) <= time2) {
                    int runtime = onProcess.operations.get(0);
                    if(onProcess.time > time){
                        onProcess.time += runtime;
                    } else onProcess.time = time + runtime;
                    onProcess.operations.remove(0);
                    switch (onProcess.operations.size()) {
                        case 17, 15, 13, 7, 5, 3:
                            onProcess.time += onProcess.operations.get(0);
                            onProcess.operations.remove(0);
                            ATCReady.offer(onProcess);
                            onProcess = null;
                            this.ATCRunning(time + runtime, time2 - runtime);
                            return;
                        case 11, 1:
                            myACC.ACCReady.offer(onProcess);
                            if(myACC.currentFlight != null ){
                                onProcess = null;
                                this.ATCRunning(time + runtime, time2 - runtime);

                            } else onProcess = null;

                            break;
                    }


                } else if (onProcess.operations.get(0) > time2) {
                    if (onProcess.time < time){
                        onProcess.time = time + time2;
                    }
                    else onProcess.time += time2;

                    onProcess.operations.set(0, onProcess.operations.get(0) - time2);
                } else {
                    if (onProcess.time < time){
                        onProcess.time = time + time2;
                    }
                    else onProcess.time += time2;
                    onProcess.operations.remove(0);
                    switch (onProcess.operations.size()) {
                        case 17, 15, 13, 7, 5, 3:
                            onProcess.time += onProcess.operations.get(0);
                            onProcess.operations.remove(0);
                            ATCReady.offer(onProcess);
                            onProcess = null;
                            break;
                        case 11, 1:
                            myACC.ACCReady.offer(onProcess);
                            onProcess = null;
                            break;
                    }
                }


            }
        }
    }
}
