package org.wintrisstech.sensors;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PulseInput;
import ioio.lib.api.PulseInput.PulseMode;
import ioio.lib.api.exception.ConnectionLostException;
import org.wintrisstech.erik.iaroc.Dashboard;

/**
 * An UltraSonicSensors instance is used to access three ultrasonic sensors
 * (left, front, and right) and read the measurements from these sensors.
 *
 * @author Erik Colban
 */
public class UltraSonicSensors
{
    private static final String TAG = "UltraSonicSensor";
    private static final float CONVERSION_FACTOR = 17280.0F; //cm / s
    private static final int NUM_SAMPLES = 2;
    private static final int LEFT_ULTRASONIC_INPUT_PIN = 35;
    private static final int FRONT_ULTRASONIC_INPUT_PIN = 36;
    private static final int RIGHT_ULTRASONIC_INPUT_PIN = 37;
    private static final int STROBE_FRONT_ULTRASONIC_OUTPUT_PIN = 16;
    private static final int STROBE_LEFT_ULTRASONIC_OUTPUT_PIN = 15;
    private static final int STROBE_RIGHT_ULTRASONIC_OUTPUT_PIN = 17;
    private final PulseInput leftUltrasonicInput;
    private final PulseInput frontUltrasonicInput;
    private final PulseInput rightUltrasonicInput;
    private DigitalOutput frontUltrasonicStrobe;
    private DigitalOutput rightUltrasonicStrobe;
    private DigitalOutput leftUltrasonicStrobe;
    private int leftDistance;
    private int frontDistance = 10;
    private int rightDistance;
    private Dashboard dashboard;

    /**
     * Constructor of a UltraSonicSensors instance.
     *
     * @param ioio the IOIO instance used to communicate with the sensor
     * @throws ConnectionLostException
     *
     */
    public UltraSonicSensors(IOIO ioio, Dashboard dashboard) throws ConnectionLostException
    {
        this.dashboard = dashboard;
        this.leftUltrasonicInput = ioio.openPulseInput(LEFT_ULTRASONIC_INPUT_PIN, PulseMode.POSITIVE);
        this.frontUltrasonicInput = ioio.openPulseInput(FRONT_ULTRASONIC_INPUT_PIN, PulseMode.POSITIVE);
        this.rightUltrasonicInput = ioio.openPulseInput(RIGHT_ULTRASONIC_INPUT_PIN, PulseMode.POSITIVE);
        this.frontUltrasonicStrobe = ioio.openDigitalOutput(STROBE_FRONT_ULTRASONIC_OUTPUT_PIN);
    }

    /**
     * Makes a reading of the ultrasonic sensors and stores the results locally.
     * To access these readings, use {@link #getLeftDistance()},
     * {@link #getFrontDistance()}, and {@link #getRightDistance()}.
     *
     * @throws ConnectionLostException
     * @throws InterruptedException
     */
    public void readUltrasonicSensors() throws ConnectionLostException, InterruptedException
    {
//        frontUltrasonicStrobe.write(false);
//        frontUltrasonicStrobe.write(true);
//        frontUltrasonicStrobe.write(false);
//        frontDistance = (int)(frontUltrasonicInput.getDuration() * 18000);
//        leftUltrasonicStrobe.write(false);
//        leftUltrasonicStrobe.write(true);
//        leftUltrasonicStrobe.write(false);
//        leftDistance = (int)(frontUltrasonicInput.getDuration() * 18000);
//        rightUltrasonicStrobe.write(false);
//        rightUltrasonicStrobe.write(true);
//        rightUltrasonicStrobe.write(false);
//        rightDistance = (int)(frontUltrasonicInput.getDuration() * 18000);
    }

    public synchronized int getLeftDistance()
    {
        return leftDistance;
    }

    public synchronized int getFrontDistance()
    {
        return frontDistance;
    }

    public synchronized int getRightDistance()
    {
        return rightDistance;
    }

    /**
     * Closes all the connections to the used pins
     */
    public void closeConnection()
    {
        leftUltrasonicInput.close();
        frontUltrasonicInput.close();
        rightUltrasonicInput.close();
        frontUltrasonicStrobe.close();
    }
}
