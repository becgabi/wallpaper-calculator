package org.example;

import org.example.model.CubeShapedRoom;
import org.example.model.RectangularPrismRoom;
import org.example.model.Room;
import org.example.service.RoomFactory;
import org.example.service.RoomManager;
import org.example.service.RoomSideImporter;
import org.example.service.RoomSideValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomManagerIntegrationTest {

    private RoomManager manager;

    private static List<Room> rooms;

    @BeforeAll
    static void importRooms() {
        RoomSideImporter importer = new RoomSideImporter(new RoomFactory(), new RoomSideValidator());
        String pathString = "src/test/resources/sample-input.txt";
        rooms = importer.importRoomSides(pathString);
    }

    @BeforeEach
    void setUp() {
        manager = new RoomManager();
    }

    @Test
    void convertRoomListToOccurrenceMap() {
        // when
        Map<Room, Long> roomOccurrenceMap = manager.convertRoomListToOccurrenceMap(rooms);

        // then
        assertEquals(987, roomOccurrenceMap.size());
    }

    @Test
    void calculateTotalSurfaceArea() {
        // given
        Map<Room, Long> roomOccurrenceMap = manager.convertRoomListToOccurrenceMap(rooms);

        // when
        BigInteger surfaceArea = manager.calculateTotalSurfaceArea(roomOccurrenceMap);

        // then
        assertEquals(BigInteger.valueOf(1_592_486L), surfaceArea);
    }

    @Test
    void getCubeShapedRooms() {
        // given
        Map<Room, Long> roomOccurrenceMap = manager.convertRoomListToOccurrenceMap(rooms);

        // when
        List<CubeShapedRoom> cubeShapedRooms = manager.getCubeShapedRooms(roomOccurrenceMap);

        // then
        assertEquals(5, cubeShapedRooms.size());
        assertTrue(cubeShapedRooms.containsAll(List.of(
                new CubeShapedRoom(28),
                new CubeShapedRoom(15),
                new CubeShapedRoom(12),
                new CubeShapedRoom(9),
                new CubeShapedRoom(7)
        )));
    }

    @Test
    void getMultipleOccurringRooms() {
        // given
        Map<Room, Long> roomOccurrenceMap = manager.convertRoomListToOccurrenceMap(rooms);

        // when
        List<Room> multipleOccurringRooms = manager.getMultipleOccurringRooms(roomOccurrenceMap);

        // then
        assertEquals(13, multipleOccurringRooms.size());
        assertTrue(multipleOccurringRooms.containsAll(List.of(
                new RectangularPrismRoom(6, 18, 15),
                new RectangularPrismRoom(8, 28, 29),
                new RectangularPrismRoom(15, 10, 7),
                new RectangularPrismRoom(17, 15, 2),
                new RectangularPrismRoom(2, 25, 8),
                new RectangularPrismRoom(7, 3, 4),
                new RectangularPrismRoom(17, 25, 1),
                new RectangularPrismRoom(22, 3, 1),
                new RectangularPrismRoom(15, 26, 22),
                new RectangularPrismRoom(22, 27, 12),
                new RectangularPrismRoom(8, 8, 16),
                new RectangularPrismRoom(4, 3, 23),
                new RectangularPrismRoom(6, 8, 12)
        )));
    }
}
