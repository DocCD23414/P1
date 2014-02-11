import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class FollowLine {
	private DifferentialPilot pilot;
	private LightSensor left = new LightSensor(SensorPort.S2);
	private LightSensor right = new LightSensor(SensorPort.S1);

	public FollowLine(double x, double y) {
		pilot = new DifferentialPilot(x, y, Motor.B, Motor.C);
	}

	public void run() {
		pilot.setTravelSpeed(6.0);
		pilot.setRotateSpeed(60.0);

		Button.waitForAnyPress();
		left.setFloodlight(true);
		right.setFloodlight(true);

		Delay.msDelay(50);

		pilot.forward();
		Delay.msDelay(2000);
		while (pilot.isMoving()) {
			int leftvalue = left.getNormalizedLightValue();
			int rightvalue = right.getNormalizedLightValue();
			if (leftvalue < 450) {
				pilot.rotate(-36.0);
				pilot.forward();
			}
			if (rightvalue < 450) {
				pilot.rotate(36.0);
				pilot.forward();
			}
			Delay.msDelay(200);
			if (Button.ENTER.isDown() || Button.ESCAPE.isDown()
					|| Button.LEFT.isDown() || Button.RIGHT.isDown()) {
				pilot.stop();
			}
		}
	}

	public void grid() {
		pilot.setTravelSpeed(6.0);
		pilot.setRotateSpeed(60.0);

		Button.waitForAnyPress();
		left.setFloodlight(true);
		right.setFloodlight(true);

		Delay.msDelay(50);

		pilot.forward();
		Delay.msDelay(2000);
		while (pilot.isMoving()) {
			int leftvalue = left.getNormalizedLightValue();
			int rightvalue = right.getNormalizedLightValue();
			if (leftvalue < 450 && rightvalue > 450) {
				pilot.setRotateSpeed(200.0);
				pilot.rotate(-36.0);
				pilot.setRotateSpeed(60.0);
				pilot.forward();
			}
			if (rightvalue < 450 && leftvalue > 450) {
				pilot.setRotateSpeed(200.0);
				pilot.rotate(36.0);
				pilot.setRotateSpeed(60.0);
				pilot.forward();
			}
			if (leftvalue < 450 && rightvalue < 450) {
				Random random = new Random();
				int turn = random.nextInt(3);
				if (turn == 0) {
					pilot.setRotateSpeed(200.0);
					pilot.travel(5.0);
					pilot.rotate(500.0);
					pilot.setRotateSpeed(60.0);
					pilot.forward();
				} else if (turn==1){
					pilot.setRotateSpeed(200.0);
					pilot.travel(5.0);
					pilot.rotate(-500.0);
					pilot.setRotateSpeed(60.0);
					pilot.forward();
				}
				else {
					pilot.travel(5.0);
					pilot.forward();
				}
			}
			//Delay.msDelay(200);
			if (Button.ENTER.isDown() || Button.ESCAPE.isDown()
					|| Button.LEFT.isDown() || Button.RIGHT.isDown()) {
				pilot.stop();
			}
		}
	}

	public static void main(String[] args) {
		FollowLine line = new FollowLine(5.0, 2.0);
		line.grid();
	}
}
