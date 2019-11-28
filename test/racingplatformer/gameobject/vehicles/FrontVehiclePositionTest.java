package racingplatformer.gameobject.vehicles;

import org.jbox2d.common.Vec2;
import org.junit.BeforeClass;
import org.junit.Test;
import racingplatformer.Game;
import racingplatformer.race.Race;
import static org.junit.Assert.*;

public class FrontVehiclePositionTest {

    private static Vehicle vehicle1;
    private static Vehicle vehicle2;
    private static Vehicle vehicle3;
    private static Vehicle vehicle4;

    private static float startX = 10;
    private static float startY = 10;

    @BeforeClass
    public static void initialize()
    {
        Game game = new Game();
        Race race = new Race(game);
        vehicle1 = new Porche(race, startX, startY, 1);
        vehicle2 = new MuscleCar(race,startX, startY, 2);
        vehicle3 = new RallyRacer(race, startX, startY, 3);
        vehicle4 = new MonsterTruck(race, startX, startY, 4);
    }

    @Test
    public void testPorcheGetFrontOfVehiclePosition(){
        assertEquals(vehicle1.getFrontOfVehiclePosition(), new Vec2(startX,startY).add(new Vec2(vehicle1.halfWidth, 0)));
    }

    @Test
    public void testMuscleCarGetFrontOfVehiclePosition(){
        assertEquals(vehicle2.getFrontOfVehiclePosition(), new Vec2(startX,startY).add(new Vec2(vehicle2.halfWidth, 0)));
    }

    @Test
    public void testRallyRacerGetFrontOfVehiclePosition(){
        assertEquals(vehicle3.getFrontOfVehiclePosition(), new Vec2(startX,startY).add(new Vec2(vehicle3.halfWidth, 0)));
    }

    @Test
    public void testMonsterTruckGetFrontOfVehiclePosition(){
        assertEquals(vehicle4.getFrontOfVehiclePosition(), new Vec2(startX,startY).add(new Vec2(vehicle4.halfWidth, 0)));
    }
}
