package org.wintrisstech.erik.iaroc;

import android.os.SystemClock;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;
import org.wintrisstech.sensors.UltraSonicSensors;

/**
 * A Lada is an implementation of the IRobotCreateInterface, inspired by Vic's
 * awesome API. It is entirely event driven.
 * version 1.0 added Android compass 5/25/13
 * version 2.0 added batch sonar read for short trigger pulses
 * @author Erik
 */
public class Lada extends IRobotCreateAdapter
{
    private final Dashboard dashboard;
    public UltraSonicSensors sonar;

    public Lada(IOIO ioio, IRobotCreateInterface create, Dashboard dashboard) throws ConnectionLostException//constrictor
    {
        super(create);
        this.dashboard = dashboard;
        sonar = new UltraSonicSensors(ioio);
    }

    public void initialize()
    {
        dashboard.log("===========Android Sensor Version 2.0===========");
        dashboard.log("Battery Charge = " + getBatteryCharge() + ", 2,755 = Full charge");
    }

    public void loop()
    {
        SystemClock.sleep(1000);
        //dashboard.log("Azimuth " + dashboard.getAzimuth());
        try
        {
            sonar.readUltrasonicSensors();
        } catch (Exception ex)
        {
            dashboard.log("sonar hiccup");
        }
        dashboard.log(sonar.getLeftDistance() + "\t" + sonar.getRightDistance() + "\t" + sonar.getFrontDistance());
        dashboard.log("loop");
    }
}
