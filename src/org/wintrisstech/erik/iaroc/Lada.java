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

    public Lada(IOIO ioio, IRobotCreateInterface create, Dashboard dashboard) throws ConnectionLostException//constrictor
    {
        super(create);
        sonar = new UltraSonicSensors(ioio);
        this.dashboard = dashboard;

        song(0, new int[]
                {
                    58, 10
                });
    }

    public void initialize() throws ConnectionLostException
    {
        dashboard.log("===========Android White Line Follower Version 0.0===========");
        dashboard.log("Battery Charge = " + getBatteryCharge() + ", 3,000 = Full charge");
        readSensors(SENSORS_GROUP_ID6);
    }

    public void loop() throws ConnectionLostException
    {
        driveDirect(100, 100);
        readSensors(SENSORS_GROUP_ID6);
    }

}