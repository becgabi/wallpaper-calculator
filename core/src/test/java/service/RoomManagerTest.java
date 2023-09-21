package service;

import org.example.model.CubeShapedRoom;
import org.example.model.RectangularPrismRoom;
import org.example.model.Room;
import org.example.service.RoomManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomManagerTest {

    private RoomManager roomManager;
    private List<Room> roomList;

    @BeforeEach
    void setUp() {
        roomManager = new RoomManager();
        roomList = new ArrayList<>();
    }

    @Test
    void testConvertRoomListToOccurrenceMap() {
        // given
        Room cubeShapedRoom1 = new CubeShapedRoom(5);
        Room cubeShapedRoom2 = new CubeShapedRoom(5);
        Room rectangularPrismRoom = new RectangularPrismRoom(7, 5, 2);
        roomList.add(cubeShapedRoom1);
        roomList.add(cubeShapedRoom2);
        roomList.add(rectangularPrismRoom);

        // when
        Map<Room, Long> result = roomManager.convertRoomListToOccurrenceMap(roomList);

        // then
        assertEquals(2, result.size());
        assertEquals(2L, result.get(cubeShapedRoom1));
        assertEquals(1L, result.get(rectangularPrismRoom));
    }

    @Test
    void testCalculateTotalSurfaceArea() {
        // given
        CubeShapedRoom cubeShapedRoom = new CubeShapedRoom(5);
        RectangularPrismRoom rectangularPrismRoom = new RectangularPrismRoom(7, 5, 2);
        Map<Room, Long> roomOccurrenceMap = Map.of(
                cubeShapedRoom, 1L,
                rectangularPrismRoom, 1L
        );

        // when
        BigInteger totalSurfaceArea = roomManager.calculateTotalSurfaceArea(roomOccurrenceMap);

        // then
        // The total surface area should be the sum of cubeShapedRoom's surface area (7*5*5)=175
        // and rectangularPrismRoom's surface area (2*7*5+2*5*2+2*2*7+10)=128.
        assertEquals(new BigInteger("303"), totalSurfaceArea);
    }

    @Test
    void testGetCubeShapedRooms() {
        // given
        Room cubeShapedRoom1 = new CubeShapedRoom(5);
        Room cubeShapedRoom2 = new CubeShapedRoom(7);
        Room rectangularPrismRoom = new RectangularPrismRoom(7, 5, 2);
        Map<Room, Long> roomOccurrenceMap = Map.of(
                cubeShapedRoom1, 1L,
                cubeShapedRoom2, 1L,
                rectangularPrismRoom, 1L
        );

        // when
        List<CubeShapedRoom> cubeShapedRooms = roomManager.getCubeShapedRooms(roomOccurrenceMap);

        // then
        assertEquals(2, cubeShapedRooms.size());
        assertTrue(cubeShapedRooms.contains(cubeShapedRoom1));
        assertTrue(cubeShapedRooms.contains(cubeShapedRoom2));
    }

    @Test
    void testGetMultipleOccurringRooms() {
        // given
        CubeShapedRoom cubeShapedRoom = new CubeShapedRoom(5);
        RectangularPrismRoom rectangularPrismRoom = new RectangularPrismRoom(7, 5, 2);
        Map<Room, Long> roomOccurrenceMap = Map.of(
                cubeShapedRoom, 2L,
                rectangularPrismRoom, 1L
        );

        // when
        List<Room> multipleOccurringRooms = roomManager.getMultipleOccurringRooms(roomOccurrenceMap);

        // then
        assertEquals(1, multipleOccurringRooms.size());
        assertTrue(multipleOccurringRooms.contains(cubeShapedRoom));
    }
}
