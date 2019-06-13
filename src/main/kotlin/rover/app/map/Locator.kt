package rover.app.map

import rover.api.CardinalPoint
import rover.api.Movement
import rover.api.Point
import rover.api.Position
import rover.app.exception.CellNotFreeException

open class Locator(private val map: Map) {

    open fun move(movement: Movement, position: Position) {
        val nextPosition = position.copy()

        when (movement) {
            Movement.FORWARD -> moveForward(nextPosition)
            Movement.BACKWARD -> moveBackward(nextPosition)
            Movement.LEFT -> moveLeft(nextPosition)
            Movement.RIGHT -> moveRight(nextPosition)
        }

        normalizePosition(nextPosition)
        checkIfValidPosition(nextPosition)

        position.fillWith(nextPosition)
    }

    private fun moveForward(position: Position) {
        when(position.cardinalPoint) {
            CardinalPoint.NORTH -> position.point.y -= 1
            CardinalPoint.WEST -> position.point.x -= 1
            CardinalPoint.SOUTH -> position.point.y += 1
            CardinalPoint.EAST -> position.point.x += 1
        }
    }

    private fun moveBackward(position: Position) {
        when(position.cardinalPoint) {
            CardinalPoint.NORTH -> position.point.y += 1
            CardinalPoint.WEST -> position.point.x += 1
            CardinalPoint.SOUTH -> position.point.y -= 1
            CardinalPoint.EAST -> position.point.x -= 1
        }
    }

    private fun moveLeft(position: Position) {
        when(position.cardinalPoint) {
            CardinalPoint.NORTH -> position.cardinalPoint = CardinalPoint.WEST
            CardinalPoint.WEST -> position.cardinalPoint = CardinalPoint.SOUTH
            CardinalPoint.SOUTH -> position.cardinalPoint = CardinalPoint.EAST
            CardinalPoint.EAST -> position.cardinalPoint = CardinalPoint.NORTH
        }
    }

    private fun moveRight(position: Position) {
        when(position.cardinalPoint) {
            CardinalPoint.NORTH -> position.cardinalPoint = CardinalPoint.EAST
            CardinalPoint.WEST -> position.cardinalPoint = CardinalPoint.NORTH
            CardinalPoint.SOUTH -> position.cardinalPoint = CardinalPoint.WEST
            CardinalPoint.EAST -> position.cardinalPoint = CardinalPoint.SOUTH
        }
    }

    private fun normalizePosition(position: Position) {
        position.apply {
            point.x = point.x.modulo(map.numberOfColumns())
            point.y = point.y.modulo(map.numberOfRows())
        }
    }

    private fun checkIfValidPosition(position: Position) {
        if (!map.isCellFree(position.point)) {
            throw CellNotFreeException()
        }
    }

    private fun Position.copy(): Position {
        return Position(Point(point.x, point.y), cardinalPoint)
    }

    private fun Position.fillWith(position: Position) {
        this.point.x = position.point.x
        this.point.y = position.point.y
        this.cardinalPoint = position.cardinalPoint
    }

    private fun Int.modulo(number: Int): Int {
        var result = this.rem(number)
        if (result < 0) {
            result += number
        }
        return result
    }
}