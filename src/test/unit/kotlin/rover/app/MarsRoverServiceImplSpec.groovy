package rover.app

import rover.api.CardinalPoint
import rover.api.Movement
import rover.api.Point
import rover.api.Position
import rover.app.map.Locator
import rover.app.marsrover.MarsRover
import spock.lang.Specification

class MarsRoverServiceImplSpec extends Specification{

    private def POSITION = new Position(new Point(1,1), CardinalPoint.EAST)
    private def MOVEMENT = Movement.FORWARD

    private Locator locator = Mock(Locator)
    private MarsRover marsRover = Mock(MarsRover)

    private MarsRoverServiceImpl marsRoverService = new MarsRoverServiceImpl(locator, marsRover)

    def "move"() {
        given:
            marsRover.position >> POSITION
        when:
            marsRoverService.move(MOVEMENT)
        then:
            1 * locator.move(MOVEMENT, POSITION)
    }

    def "getMarsRoverPosition"() {
        given:
            marsRover.position >> POSITION
        when:
            def result = marsRoverService.getRoverPosition()
        then:
            result == POSITION

    }
}