package it.unibo.cautiousexploreractor;

//import it.unibo.robotWithActorJava.RobotMovesInfo;
import it.unibo.supports2021.ActorBasicJava;

public abstract class RobotLogic extends ActorBasicJava {

    //protected enum State {start, walking, obstacle, end };
    //protected RobotLogic.State curState       =  RobotLogic.State.start ;
    protected RobotHandler robotHandler;

    public RobotLogic(String name) {
        super(name);
    }

    public void setRobotHandler(RobotHandler robotHandler){
        this.robotHandler = robotHandler;
    }
    //Behaviour
    protected abstract void fsm(String move, String endmove);


}
