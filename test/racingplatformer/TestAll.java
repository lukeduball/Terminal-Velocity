/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racingplatformer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import racingplatformer.gameobject.vehicles.AIControllerTest;
import racingplatformer.gameobject.vehicles.FrontVehiclePositionTest;
import racingplatformer.gameobject.vehicles.PlayerControllerTest;
import racingplatformer.gameobject.vehicles.VehicleTest;
import racingplatformer.math.PerlinNoiseTest;
import racingplatformer.race.ChunkTest;
import racingplatformer.race.FinishLineTest;
import racingplatformer.race.RaceTest;
import racingplatformer.renderengine.ScreenTest;
import racingplatformer.utilities.TimerTest;

/**
 *
 * @author Luke
 */
@RunWith(Suite.class)
@Suite.SuiteClasses
({
    AIControllerTest.class,
    FrontVehiclePositionTest.class,
    VehicleTest.class,
    PlayerControllerTest.class,
    PerlinNoiseTest.class,
    ChunkTest.class,
    FinishLineTest.class,
    RaceTest.class,
    ScreenTest.class,
    TimerTest.class
})
public class TestAll {
    
}
