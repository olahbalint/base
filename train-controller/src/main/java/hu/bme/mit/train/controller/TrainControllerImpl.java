package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

import java.sql.Time;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private boolean emBrake = false;


	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
		emergencyBrake(emBrake, referenceSpeed);
		fillTable();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}

	private void emergencyBrake(boolean emBrake, int joystickPosition){
		if (emBrake)
			while(referenceSpeed > 0){
				joystickPosition = -1;
			}
	}

	@Override
	public void getEmergencyStatus() {
		
	}

	@Override
	public void fillTable(){
		Table<String, String, String> tachographTable = HashBasedTable.create();
		tachographTable.put("0","5","10");
	}
}
