/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.logic.tasks;

import robot.behavior.RobotDrive;
import robot.logic.LogicTask;
import robot.sensors.RobotSensors;

/**
 *
 * @author Nathan
 */
public class TTurnDegrees extends LogicTask {
    //// CONSTANTS -------------------------------------------------------------
    
    public static final double COUNT_OVER_DEGREES = 1.0;
    
    //// PRIVATE VARIABLES -----------------------------------------------------
    
    private double _direction;
    private double _speed;
    private double _tickCount = 0;
	
    //// CONSTRUCTOR -----------------------------------------------------------
    
    /**
     * Turns the robot a constant number of degrees.
     * @param amount The amount to turn: positive or negative.
     * @param speed The speed to turn at (wheel speed); sign ignored.
     * @param tolerance The degree tolerance of the turn.
     */
    public TTurnDegrees(double amount, double speed, double tolerance) {
        tolerance = Math.abs(tolerance * 0.5);
        _direction = (amount > 0 ? 1.0 : -1.0);
        _speed = Math.abs(speed) * _direction;
        _tickCount = (amount - tolerance * _direction) * COUNT_OVER_DEGREES;
    }

    //// INITIALIZE ------------------------------------------------------------
    
    public void initialize() {
        //RobotSensors.encoderDriveLeft.start();
        RobotSensors.encoderDriveLeft.reset();
        //RobotSensors.encoderDriveRight.start();
        RobotSensors.encoderDriveRight.reset();
    }
	
	//// UPDATE ----------------------------------------------------------------

    public void update() {
        double v = RobotSensors.encoderDriveLeft.get(); //TODO: Insure signs and make average
        if (Math.abs(v) > Math.abs(_tickCount)) {
            RobotDrive.turn(0);
            _done = true;
        } else {
            RobotDrive.turn(_speed);
        }
    }
	
	//// FINISH ----------------------------------------------------------------

    public int finish() {
		RobotDrive.turn(0);
        return _done ? SUCCESS : FAILURE;
    }
}