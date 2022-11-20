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
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private WPI_TalonFX motor1;
    private final DutyCycleEncoder dutyCycleEncoder = new DutyCycleEncoder(7);

    @Override
    public void teleopInit() {
        // Create talonfx (need to test talonsrx)
        this.motor1 = new WPI_TalonFX(15, "can1");

        // Set selected sensor for the talon (Unclear whether needed to get the absolute position of the internal sensor (needs testing))
        this.motor1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 1, 30);

        // Set coast mode (could be why setting position isn't working?)
        this.motor1.setNeutralMode(NeutralMode.Coast);

        // Apparently set sensor phase does nothing (need to test on robot)
        this.motor1.setSensorPhase(false);

        // Create external encoder
        this.dutyCycleEncoder.setDistancePerRotation(Math.PI * 2);

    }

    public void teleopPeriodic() {
        //this.motor1.set(ControlMode.PercentOutput,0.2);
        this.motor1.set(ControlMode.Position, 10000);

        // Output talonfx absolute position
        SmartDashboard.putNumber("Position", this.motor1.getSensorCollection().getIntegratedSensorAbsolutePosition());

        // Output external encoder absolute position
        SmartDashboard.putNumber("Duty Cycle Encoder", this.dutyCycleEncoder.getAbsolutePosition());
    }



}

