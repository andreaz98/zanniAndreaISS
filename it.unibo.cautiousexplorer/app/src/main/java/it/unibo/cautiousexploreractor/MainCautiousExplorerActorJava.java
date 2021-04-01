package it.unibo.cautiousexploreractor;

//import it.unibo.resumablebwWithActor.RobotExploration;
import it.unibo.supports2021.IssWsHttpJavaSupport;

import java.awt.*;

public class MainCautiousExplorerActorJava {
    //Constructor
    public MainCautiousExplorerActorJava( ){
        IssWsHttpJavaSupport support = IssWsHttpJavaSupport.createForWs("localhost:8091" );

        //while( ! support.isOpen() ) ActorBasicJava.delay(100);

        RobotLogic rl = new RobotExploration("robot explorator");
        RobotHandler rh = new RobotHandler("robot handler",support, rl);

        rl.setRobotHandler(rh);

        support.registerActor(rh);

        //ConsoleGuiActor console = new ConsoleGuiActor();
        //console.registerActor(ra);
        //console.registerActor(new NaiveObserverActor("naiveObs") );

        //Diamo il via alle danze.
        support.forward("{\"robotmove\":\"moveForward\", \"time\": 350}");
        //ra.send("startApp");

        System.out.println("MainRobotActorJava | CREATED  n_Threads=" + Thread.activeCount());
    }


    public static void main(String args[]){
        try {
            System.out.println("MainRobotExplorationActorJava | main start n_Threads=" + Thread.activeCount());
            new MainCautiousExplorerActorJava();
            //System.out.println("MainRobotActorJava  | appl n_Threads=" + Thread.activeCount());
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
