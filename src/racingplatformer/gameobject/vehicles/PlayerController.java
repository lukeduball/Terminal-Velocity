/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer.gameobject.vehicles;

import racingplatformer.Game;
import racingplatformer.race.Race;

/**
 *
 * @author Luke
 */
public class PlayerController extends Controller
{
    private int playerID;
    
    public PlayerController(Vehicle vehicle, int id)
    {
        super(vehicle);
        this.playerID = id;
    }

    @Override
    public void moveVehicle(Race race)
    {
        if(race.isMappedKeyDown(this.playerID, Game.FORWARD))
        {
            System.out.println(playerID);
            this.parentVehicle.getFrontWheelSpring().enableMotor(true);
            this.parentVehicle.getRearWheelSpring().enableMotor(true);
            this.parentVehicle.getRearWheelSpring().setMotorSpeed(this.parentVehicle.getSpeed());
            this.parentVehicle.getFrontWheelSpring().setMotorSpeed(this.parentVehicle.getSpeed());
        }
        else if(race.isMappedKeyDown(this.playerID, Game.BACKWARD))
        {
            this.parentVehicle.getFrontWheelSpring().enableMotor(true);
            this.parentVehicle.getRearWheelSpring().enableMotor(true);
            this.parentVehicle.getRearWheelSpring().setMotorSpeed(-this.parentVehicle.getSpeed());
            this.parentVehicle.getFrontWheelSpring().setMotorSpeed(-this.parentVehicle.getSpeed());
        }
        else
        {
            this.parentVehicle.getRearWheelSpring().enableMotor(false);
            this.parentVehicle.getFrontWheelSpring().enableMotor(false);
        }

        if(race.isMappedKeyDown(this.playerID, Game.TILT_UP))
        {
            this.parentVehicle.getFrame().applyAngularImpulse(-.5f);
        }

        if(race.isMappedKeyDown(this.playerID, Game.TILT_DOWN))
        {
            this.parentVehicle.getFrame().applyAngularImpulse(.5f);
        }
    }
    
    @Override
    public String getControllerLabel()
    {
        return "Player "+playerID;
    }
}
