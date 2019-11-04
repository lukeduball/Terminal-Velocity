/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import racingplatformer.race.Race;

/**
 *
 * @author Luke
 */
public class AIController extends Controller
{
    public AIController(Vehicle vehicle)
    {
        super(vehicle);
    }

    @Override
    public void moveVehicle(Race race)
    {
        if(this.parentVehicle.getRearWheelOnGround() && this.parentVehicle.getFrontWheelOnGround())
        {
            this.parentVehicle.getFrontWheelSpring().enableMotor(true);
            this.parentVehicle.getRearWheelSpring().enableMotor(true);
            this.parentVehicle.getFrontWheelSpring().setMotorSpeed(this.parentVehicle.getSpeed());
            this.parentVehicle.getRearWheelSpring().setMotorSpeed(this.parentVehicle.getSpeed());
        }
        else if(this.parentVehicle.getRearWheelOnGround())
        {
            this.parentVehicle.getFrame().applyAngularImpulse(0.5f);
        }
        else if(this.parentVehicle.getFrontWheelOnGround())
        {
            this.parentVehicle.getFrame().applyAngularImpulse(-0.5f);
        }
        else
        {
            double modularAngle = this.parentVehicle.getFrame().getAngle() % (Math.PI * 2);
            System.out.println(modularAngle);
            double equalibriumDifference = 0.0f - modularAngle;
            if(equalibriumDifference > 0)
            {
                this.parentVehicle.getFrame().applyAngularImpulse(0.5f);
            }
            else
            {
                this.parentVehicle.getFrame().applyAngularImpulse(-0.5f);
            }
        }
    }
    
    @Override
    public String getControllerLabel()
    {
        return "CPU";
    }
}
