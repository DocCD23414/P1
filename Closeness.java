import lejos.nxt.Button;

import lejos.nxt.Motor;

import lejos.nxt.SensorPort;

import lejos.nxt.UltrasonicSensor;

import lejos.robotics.navigation.DifferentialPilot;

import lejos.util.Delay;



public class Closeness
{

	private UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S3);


	DifferentialPilot pilot;


	private Closeness(double x, double y)
	{

		pilot = new DifferentialPilot(x, y, Motor.B, Motor.C);

	}



	public void run()
	{

		Button.waitForAnyPress();

		Delay.msDelay(1000);

		pilot.forward();

		while (pilot.isMoving())
		{

			int dist = sonic.getDistance();

			pilot.setTravelSpeed(dist - 6); //keeps robot a set distance away from an obstacle


			if (Button.ENTER.isDown() || Button.ESCAPE.isDown()

			|| Button.LEFT.isDown() || Button.RIGHT.isDown())
			{

				pilot.stop();

			}

		}

	}



	public static void main(String[] args)
	{

		Closeness run = new Closeness(5.0, 2.0);

		run.run();

	}

}
