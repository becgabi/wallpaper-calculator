package org.example;

import org.example.model.Room;
import org.example.service.RoomFactory;
import org.example.service.RoomSideImporter;
import org.example.service.RoomSideValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomSideImporterIntegrationTest {

    private RoomSideImporter importer;

    @BeforeEach
    void setUp() {
        importer = new RoomSideImporter(new RoomFactory(), new RoomSideValidator());
    }

    @Test
    void importRoomSides_SampleFile_CreatesRoomsCorrectly() {
        // given
        String pathString = "src/test/resources/sample-input.txt";

        // when
        List<Room> rooms = importer.importRoomSides(pathString);

        // then
        assertEquals(1000, rooms.size());
    }

}
