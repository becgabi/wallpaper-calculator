package service;

import org.example.model.CubeShapedRoom;
import org.example.model.RectangularPrismRoom;
import org.example.model.Room;
import org.example.service.RoomFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomFactoryTest {

    private RoomFactory roomFactory;

    @BeforeEach
    void setUp() {
        roomFactory = new RoomFactory();
    }

    @Test
    void testNewRoom_withPositiveSides_ShouldReturnCubeShapedRoom() {
        // given
        int length = 10;

        // when
        Room room = roomFactory.newRoom(length, length, length);

        // then
        assertInstanceOf(CubeShapedRoom.class, room);
        assertEquals(length, ((CubeShapedRoom) room).length());
    }

    @Test
    void testNewRoom_withDifferentSides_ShouldReturnRectangularPrismRoom() {
        // given
        int length = 10;
        int width = 20;
        int height = 30;

        // when
        Room room = roomFactory.newRoom(length, width, height);

        // then
        assertInstanceOf(RectangularPrismRoom.class, room);
        assertEquals(length, ((RectangularPrismRoom) room).length());
        assertEquals(width, ((RectangularPrismRoom) room).width());
        assertEquals(height, ((RectangularPrismRoom) room).height());
    }

    @Test
    void testNewRoom_withNegativeLength_ShouldThrowException() {
        // given
        int length = -10, width = 20, height = 30;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> roomFactory.newRoom(length, width, height));
    }

    @Test
    void testNewRoom_withNegativeWidth_ShouldThrowException() {
        // given
        int length = 10, width = -20, height = 30;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> roomFactory.newRoom(length, width, height));
    }

    @Test
    void testNewRoom_withNegativeHeight_ShouldThrowException() {
        // given
        int length = 10, width = 20, height = -30;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> roomFactory.newRoom(length, width, height));
    }
}
