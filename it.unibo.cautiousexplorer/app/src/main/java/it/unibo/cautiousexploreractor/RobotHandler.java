package it.unibo.cautiousexploreractor;

import it.unibo.supports2021.ActorBasicJava;
import it.unibo.supports2021.IssWsHttpJavaSupport;
import org.json.JSONObject;

public class RobotHandler extends ActorBasicJava {
    final String forwardMsg   = "{\"robotmove\":\"moveForward\", \"time\": 350}";
    final String backwardMsg  = "{\"robotmove\":\"moveBackward\", \"time\": 350}";
    final String turnLeftMsg  = "{\"robotmove\":\"turnLeft\", \"time\": 300}";
    final String turnRightMsg = "{\"robotmove\":\"turnRight\", \"time\": 300}";
    final String haltMsg      = "{\"robotmove\":\"alarm\", \"time\": 100}";

    private RobotLogic robotLogic;
    private IssWsHttpJavaSupport support;

    public RobotHandler(String name, IssWsHttpJavaSupport support, RobotLogic robotLogic) {
        super(name);
        this.support = support;
        this.robotLogic = robotLogic;
    }
    /*
==================================================================================
INPUT HANDLING
==================================================================================
*/
    @Override
    protected void handleInput(String msg ) {     //called when a msg is in the queue
        System.out.println( "RobotHandler | input=" + msg);
        if( ! msg.startsWith("{") ) handleAril(msg);
        else msgDriven( new JSONObject(msg) );
    }

    //TODO un giorno questo potrebbe andare in un altro componente che si occupa della traduzione aril -> cril
    private void handleAril(String msg) {
        switch(msg){
            case "w":
                support.forward(forwardMsg);
                break;
            case "s":
                support.forward(backwardMsg);
                break;
            case "l":
                support.forward(turnLeftMsg);
                break;
            case "r":
                support.forward(turnRightMsg);
                break;
            default:
                System.out.println("RobotHandler: non conosco questo comando: "+msg);
                break;
        }
    }

    protected void msgDriven( JSONObject infoJson){
        if( infoJson.has("endmove") )     {
            robotLogic.send(infoJson.toString());
            //fsm(infoJson.getString("move"), infoJson.getString("endmove"));
        }
        else if( infoJson.has("sonarName") ) handleSonar(infoJson);
        else if( infoJson.has("collision") ) handleCollision(infoJson);
        //else if( infoJson.has("robotcmd") )  handleRobotCmd(infoJson);
    }

    protected void handleSonar( JSONObject sonarinfo ){
        String sonarname = (String)  sonarinfo.get("sonarName");
        int distance     = (Integer) sonarinfo.get("distance");
        //System.out.println("RobotApplication | handleSonar:" + sonarname + " distance=" + distance);
    }
    protected void handleCollision( JSONObject collisioninfo ){
        //we should handle a collision  when there are moving obstacles
        //in this case we could have a collision even if the robot does not move
        //String move   = (String) collisioninfo.get("move");
        //System.out.println("RobotApplication | handleCollision move=" + move  );
    }

    /*
    protected void handleRobotCmd( JSONObject robotCmd ){
        String cmd = (String)  robotCmd.get("robotcmd");
        System.out.println("===================================================="    );
        System.out.println("ResumableBoundaryWalkerActor | handleRobotCmd cmd=" + cmd  );
        System.out.println("===================================================="    );
        if( cmd.equals("STOP") ) {
            tripStopped = true;
        }
        if( cmd.equals("RESUME") && tripStopped ){
            tripStopped = false;
            fsm("resume", "");  //initially curState=start
        }
    }*/

    //------------------------------------------------
    protected void doStep(){
        support.forward( forwardMsg);
        delay(1000); //to avoid too-rapid movement
    }
    protected void turnLeft(){
        support.forward( turnLeftMsg );
        delay(500); //to avoid too-rapid movement
    }
    protected void turnRight(){
        support.forward( turnRightMsg );
        delay(500); //to avoid too-rapid movement
    }
}
