/*
 * This file is part of GradleRIO-Redux-example, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Octobots <https://github.com/Octobots9084>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.octobots.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotTalonsrx extends TimedRobot {
    private WPI_TalonSRX motor1;
    //private final DutyCycleEncoder dutyCycleEncoder = new DutyCycleEncoder(7);

    @Override
    public void teleopInit() {
        // Create talonfx (need to test talonsrx)
        this.motor1 = new WPI_TalonSRX(8);

        this.motor1.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 12, 12, 0.05)); //How much current can be supplied to the motor

        /* Factory Default all hardware to prevent unexpected behaviour */
        this.motor1.configFactoryDefault();
        /* Config the sensor used for Primary PID and sensor direction */
        this.motor1.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.CTRE_MagEncoder_Absolute,
                Constants.kPIDLoopIdx,
                Constants.kTimeoutMs);

        /* Ensure sensor is positive when output is positive */
        this.motor1.setSensorPhase(Constants.kSensorPhase);

        /**
         * Set based on what direction you want forward/positive to be.
         * This does not affect sensor phase. 
         */
        this.motor1.setInverted(Constants.kMotorInvert);
        /*
         * Talon SRX does not need sensor phase set for its integrated sensor
         * This is because it will always be correct if the selected feedback device is integrated sensor (default value)
         * and the user calls getSelectedSensor* to get the sensor's position/velocity.
         *
         * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#sensor-phase
         */
        // this.motor1.setSensorPhase(true);

        /* Config the peak and nominal outputs, 12V means full */
        this.motor1.configNominalOutputForward(0, Constants.kTimeoutMs);
        this.motor1.configNominalOutputReverse(0, Constants.kTimeoutMs);
        this.motor1.configPeakOutputForward(1, Constants.kTimeoutMs);
        this.motor1.configPeakOutputReverse(-1, Constants.kTimeoutMs);

        /**
         * Config the allowable closed-loop error, Closed-Loop output will be
         * neutral within this range. See Table in Section 17.2.1 for native
         * units per rotation.
         */
        this.motor1.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

        /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
        this.motor1.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
        this.motor1.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
        this.motor1.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
        this.motor1.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);
        // Set selected sensor for the talon (Unclear whether needed to get the absolute position of the internal sensor (needs testing))
        //this.motor1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 1, 30);

        // Set coast mode (could be why setting position isn't working?)
        //this.motor1.setNeutralMode(NeutralMode.Coast);

        // Apparently set sensor phase does nothing (need to test on robot)
        //this.motor1.setSensorPhase(false);

        // Create external encoder
        //this.dutyCycleEncoder.setDistancePerRotation(Math.PI * 2);
        //this.motor1.getSensorCollection().setIntegratedSensorPositionToAbsolute(0);
    }

    public void teleopPeriodic() {
        //this.motor1.set(ControlMode.PercentOutput,0.2);
        this.motor1.set(ControlMode.Position, 1000);

        // Output talonfx absolute position
//        SmartDashboard.putNumber("Absolute Position", this.motor1.getSensorCollection().getIntegratedSensorAbsolutePosition());
//        SmartDashboard.putNumber("Position", this.motor1.getSensorCollection().getIntegratedSensorPosition());
        SmartDashboard.putNumber("Absolute Position", this.motor1.getSensorCollection().getQuadraturePosition());

        // Output external encoder absolute position
        //SmartDashboard.putNumber("Duty Cycle Encoder", this.dutyCycleEncoder.getAbsolutePosition());
    }



}

