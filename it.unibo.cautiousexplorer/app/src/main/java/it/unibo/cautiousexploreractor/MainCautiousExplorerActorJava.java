package it.unibo.cautiousexploreractor;

import it.unibo.consolegui.ConsoleGuiActor;
import it.unibo.resumablebwWithActor.MainResumableRobotActorJava;
import it.unibo.resumablebwWithActor.ResumableBoundaryWalkerActor;
import it.unibo.supports2021.IssWsHttpJavaSupport;

public class MainCautiousExplorerActorJava {
    //Constructor
    public MainCautiousExplorerActorJava( ){
        IssWsHttpJavaSupport support = IssWsHttpJavaSupport.createForWs("localhost:8091" );

        //while( ! support.isOpen() ) ActorBasicJava.delay(100);

        ResumableBoundaryWalkerActor ra = new ResumableBoundaryWalkerActor("rwa", support);
        support.registerActor(ra);

        ConsoleGuiActor console = new ConsoleGuiActor();
        console.registerActor(ra);
        //console.registerActor(new NaiveObserverActor("naiveObs") );

        //ra.send("startApp");

        System.out.println("MainRobotActorJava | CREATED  n_Threads=" + Thread.activeCount());
    }


    public static void main(String args[]){
        try {
            System.out.println("MainRobotActorJava | main start n_Threads=" + Thread.activeCount());
            new MainResumableRobotActorJava();
            //System.out.println("MainRobotActorJava  | appl n_Threads=" + Thread.activeCount());
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
