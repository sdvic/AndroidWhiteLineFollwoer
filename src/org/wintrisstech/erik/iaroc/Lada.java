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
        this.dashboard = dashboard;
        sonar = new UltraSonicSensors(ioio, dashboard);
        
        song(0, new int[]
                {
                    58, 10
                });
    }

    public void initialize() throws ConnectionLostException
    {
        dashboard.log("===========Android White Line Follower Version 0.3===========");
        dashboard.log("Battery Charge = " + getBatteryCharge() + ", 2,755 = Full charge");
    }

    public void loop() throws ConnectionLostException
    {SystemClock.sleep(1000);
        dashboard.log(dashboard.getAzimuth() + "");
//        try
//        {
//            dashboard.log("in loop");
////                    driveDirect(100, 100);
////                    readSensors(SENSORS_GROUP_ID6);
//                    //dashboard.log(sonar.readUltrasonicSensors() + "");
//                    SystemClock.sleep(1000);
//        } catch (InterruptedException ex)
//        {
//            Logger.getLogger(Lada.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}