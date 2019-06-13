package rover.app

import rover.api.MarsRoverService
import rover.api.Movement
import rover.api.Position
import rover.app.map.Locator
import rover.app.marsrover.MarsRover

open class MarsRoverServiceImpl(
        private val locator: Locator,
        private val marsRover: MarsRover): MarsRoverService {

    override fun move(movement: Movement) {
        locator.move(movement, marsRover.position)
    }

    override fun getRoverPosition(): Position {
        return marsRover.position
    }
}