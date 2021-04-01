package it.unibo.cautiousexploreractor;

//import it.unibo.robotWithActorJava.RobotMovesInfo;
import org.json.JSONObject;

public class RobotExploration extends RobotLogic{
    private int stepNum          = 1;
    //private RobotMovesInfo moves = new RobotMovesInfo(true);

    private boolean tripStopped = false;

    protected enum State {start, walking, obstacle, end, exploration };
    protected RobotExploration.State curState       =  State.start;

    public RobotExploration(String name) {
        super(name);
    }

    @Override
    protected void handleInput(String s) {
        JSONObject infoJson = new JSONObject(s);
        fsm( infoJson.getString("move"), infoJson.getString("endmove") );
    }

    @Override
    protected void fsm(String move, String endmove) {
        //DFS without obstacles

        System.out.println( myname + " | fsm state=" + curState + " tripStopped=" + tripStopped
                + " stepNum=" + stepNum + " move=" + move + " endmove=" + endmove);
        switch( curState ) {
            case start: {
                if( move.equals("resume") ){    //March30: better to include it
                    //moves.showRobotMovesRepresentation();
                    robotHandler.send("w"); //doStep();
                    curState = State.walking;
                    break;
                }
            }
            case walking: {
                if( move.equals("resume")){
                    robotHandler.send("w"); //doStep();
                } else if (move.equals("moveForward") && endmove.equals("true")) {
                    //curState = State.walk;
                    //moves.updateMovesRep("w");
                    if( ! tripStopped   )  robotHandler.send("w"); //doStep();
                    else{ System.out.println("please resume ..."); }
                } else if (move.equals("moveForward") && endmove.equals("false")) {
                    curState = State.obstacle;
                    robotHandler.send("l"); //turnLeft();
                } else {System.out.println("IGNORE answer of turnLeft");
                }
                break;
            }//walk

            case obstacle :
                if( move.equals("resume") ){
                    curState = RobotExploration.State.walking;
                    robotHandler.send("w"); //doStep();
                }else if( move.equals("turnLeft") && endmove.equals("true")) {
                    if( stepNum < 4  ) {
                        stepNum++;
                        //moves.updateMovesRep("l");
                        //moves.showRobotMovesRepresentation();
                        if( ! tripStopped ) {
                            curState = RobotExploration.State.walking;
                            robotHandler.send("w"); //doStep();
                        }else System.out.println("please resume ...");
                    }else{  //at home again
                        curState = RobotExploration.State.end;
                        robotHandler.send("l"); //turnLeft(); //to force state transition
                    }
                } break;

            case end : {
                if( move.equals("turnLeft") ) {
                    System.out.println("BOUNDARY WALK END");
                    //moves.showRobotMovesRepresentation();
                    robotHandler.send("r");//turnRight();    //to compensate last turnLeft
                }else{
                    System.out.println("RESET ... "  );
                    stepNum        = 1;
                    curState       =  RobotExploration.State.start;
                    tripStopped    =  true;
                    //moves           = new RobotMovesInfo(true);
                    //moves.cleanMovesRepresentation();
                    //moves.showRobotMovesRepresentation();
                }
                break;
            }
            default: {
                System.out.println("error - curState = " + curState);
            }
        }
    }
    
    /*
    * System.out.println( myname + " | fsm state=" + curState + " tripStopped=" + tripStopped
                + " stepNum=" + stepNum + " move=" + move + " endmove=" + endmove);
        switch( curState ) {
            case start: {
                if( move.equals("resume") ){    //March30: better to include it
                    moves.showRobotMovesRepresentation();
                    doStep();
                    curState = ResumableBoundaryWalkerActor.State.walking;
                    break;
                }
            }
            case walking: {
                if( move.equals("resume")){
                    doStep();
                } else if (move.equals("moveForward") && endmove.equals("true")) {
                    //curState = State.walk;
                    moves.updateMovesRep("w");
                    if( ! tripStopped   )  doStep();
                    else{ System.out.println("please resume ..."); }
                } else if (move.equals("moveForward") && endmove.equals("false")) {
                    curState = ResumableBoundaryWalkerActor.State.obstacle;
                    turnLeft();
                } else {System.out.println("IGNORE answer of turnLeft");
                }
                break;
            }//walk

            case obstacle :
                if( move.equals("resume") ){
                    curState = ResumableBoundaryWalkerActor.State.walking;
                    doStep();
                }else if( move.equals("turnLeft") && endmove.equals("true")) {
                    if( stepNum < 4  ) {
                        stepNum++;
                        moves.updateMovesRep("l");
                        moves.showRobotMovesRepresentation();
                        if( ! tripStopped ) {
                            curState = ResumableBoundaryWalkerActor.State.walking;
                            doStep();
                        }else System.out.println("please resume ...");
                    }else{  //at home again
                        curState = ResumableBoundaryWalkerActor.State.end;
                        turnLeft(); //to force state transition
                    }
                } break;

            case end : {
                if( move.equals("turnLeft") ) {
                    System.out.println("BOUNDARY WALK END");
                    moves.showRobotMovesRepresentation();
                    turnRight();    //to compensate last turnLeft
                }else{
                    System.out.println("RESET ... "  );
                    stepNum        = 1;
                    curState       =  ResumableBoundaryWalkerActor.State.start;
                    tripStopped    =  true;
                    //moves           = new RobotMovesInfo(true);
                    moves.cleanMovesRepresentation();
                    moves.showRobotMovesRepresentation();
                }
                break;
            }
            default: {
                System.out.println("error - curState = " + curState);
            }
        }
    }
    * 
    * 
    * 
    * */
}
