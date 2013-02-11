package org.wintrisstech.erik.iaroc;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;
import org.wintrisstech.sensors.UltraSonicSensors;

/**
 * A Lada is an implementation of the IRobotCreateInterface, inspired by Vic's
 * awesome API. It is entirely event driven.
 *
 * @author Erik
 */
public class Lada extends IRobotCreateAdapter
{
    private static final String TAG = "Lada";
    private final Dashboard dashboard;
    public UltraSonicSensors sonar;
    public int irWallSignal; // Infrared wall sensor on Roomba.
    private int leftSignal;
    private int rightSignal;
    private int leftFrontSignal;
    private int rightFrontSignal;
    private int wheelSpeed = 500;
    private int relativeHeading = 0;
    private int irSensorThreshhold = 2000;
    public int turnSpan;

    public Lada(IOIO ioio, IRobotCreateInterface create, Dashboard dashboard) throws ConnectionLostException//constrictor
    {
        super(create);
        sonar = new UltraSonicSensors(ioio);
        this.dashboard = dashboard;
    }

    public void initialize() throws ConnectionLostException
    {
        dashboard.log("===========Android White Line Follower Version 0.0===========");
        dashboard.log("Battery Charge = " + getBatteryCharge() + ", 3,000 = Full charge");
        readSensors(SENSORS_GROUP_ID6);
        song(0, new int[] {58, 10});
    }

    public void loop() throws ConnectionLostException
    {
        driveDirect(100, 100);
        readSensors(SENSORS_GROUP_ID6); // Reads all sensors into int[] sensorValues.
        leftFrontSignal = getCliffFrontLeftSignal();
        rightFrontSignal = getCliffFrontRightSignal();
        leftSignal = getCliffLeftSignal();
        rightSignal = getCliffRightSignal();

        /**
         * *************************************************************************************
         * Handling left IR sensors.
             **************************************************************************************
         */
        if (leftFrontSignal > irSensorThreshhold) // Seeing left front IR sensor.  Too far right.
        {
            turnAngle(5); // Turn left 5 degrees.
        }
        if (leftSignal > irSensorThreshhold) // Seeing left front IR sensor.  Too far right.
        {
            turnAngle(20); // Turn left 20 degrees.
        }

        /**
         * *************************************************************************************
         * Handling right IR sensors.
             **************************************************************************************
         */
        if (rightFrontSignal > irSensorThreshhold) // Seeing right front IR sensor.  Too far left.
        {
            turnAngle(-5);// Turn right 5 degrees.
        }
        if (rightSignal > irSensorThreshhold) // Seeing right front IR sensor.  Too far left...turn right.
        {
            turnAngle(-20); // Turn right 20 degrees.
        }

        /**
         * *************************************************************************************
         * George checking for bumps. Turns right on left bump. Turns left on
         * right bump. Back up and turn right for head-on.
             **************************************************************************************
         */
        boolean bumpRightSignal = isBumpRight();
        boolean bumpLeftSignal = isBumpLeft();

        if (bumpRightSignal)
        {
            driveDirect(wheelSpeed, 0);//turn left
        }

        if (bumpLeftSignal && bumpRightSignal) // Front bump.
        {
            driveDirect(-wheelSpeed, -wheelSpeed / 2); // Back up.
            turnAngle(-30); // Turn right 30 degrees.
            driveDirect(wheelSpeed, wheelSpeed); // Continue forward.
        }
    }

    public void turnAngle(int angleToTurn) throws ConnectionLostException // >0 means left, <0 means right.
    {
        if (angleToTurn > 0)
        {
            driveDirect(wheelSpeed, 0); //turn left
            relativeHeading = 0;
            while (relativeHeading < angleToTurn)
            {
                readSensors(SENSORS_GROUP_ID6);
                relativeHeading += getAngle();
            }
        }

        if (angleToTurn < 0)
        {
            driveDirect(0, wheelSpeed);//turn right
            relativeHeading = 0;
            while (relativeHeading > angleToTurn)
            {
                readSensors(SENSORS_GROUP_ID6);
                relativeHeading += getAngle();
            }
        }

        driveDirect(wheelSpeed, wheelSpeed); // Go straight.
    }
}