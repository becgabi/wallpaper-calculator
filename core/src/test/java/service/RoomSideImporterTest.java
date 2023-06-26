package service;

import org.example.model.CubeShapedRoom;
import org.example.model.RectangularPrismRoom;
import org.example.model.Room;
import org.example.service.RoomFactory;
import org.example.service.RoomSideImporter;
import org.example.service.RoomSideValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomSideImporterTest {

    @InjectMocks
    private RoomSideImporter roomSideImporter;

    @Mock
    private RoomFactory mockRoomFactory;

    @Mock
    private RoomSideValidator mockRoomSideValidator;

    @BeforeEach
    void setUp() {
        roomSideImporter = new RoomSideImporter(mockRoomFactory, mockRoomSideValidator);
    }

    @Test
    void importRoomSides_whenFileExistsAndHasValidData_shouldReturnRoomList() {
        // given
        String pathString = "src/test/resources/valid-input.txt";

        when(mockRoomSideValidator.validSides(any())).thenReturn(true);

        RectangularPrismRoom rectangularPrismRoom = new RectangularPrismRoom(1, 2, 3);
        when(mockRoomFactory.newRoom(1, 2, 3)).thenReturn(rectangularPrismRoom);

        CubeShapedRoom cubeShapedRoom = new CubeShapedRoom(2);
        when(mockRoomFactory.newRoom(2, 2, 2)).thenReturn(cubeShapedRoom);

        // when
        List<Room> rooms = roomSideImporter.importRoomSides(pathString);

        // then
        assertEquals(2, rooms.size());
        assertTrue(rooms.contains(rectangularPrismRoom));
        assertTrue(rooms.contains(cubeShapedRoom));
    }

    @Test
    void importRoomSides_whenFileExistsAndHasInvalidData_shouldReturnEmptyList() {
        // given
        String pathString = "src/test/resources/invalid-input.txt";
        when(mockRoomSideValidator.validSides(any())).thenReturn(false);

        // when
        List<Room> rooms = roomSideImporter.importRoomSides(pathString);

        // then
        assertNotNull(rooms);
        assertEquals(0, rooms.size());
    }

    @Test
    void importRoomSides_whenFileExistsAndIsEmpty_shouldReturnEmptyList() {
        // given
        String pathString = "src/test/resources/empty-input.txt";

        // when
        List<Room> rooms = roomSideImporter.importRoomSides(pathString);

        // then
        assertNotNull(rooms);
        assertEquals(0, rooms.size());
    }

    @Test
    void importRoomSides_whenFileDoesNotExist_shouldThrowException() {
        // given
        String pathString = "src/test/resources/non_existent_file.txt";

        // when, then
        Exception exception = assertThrows(IllegalStateException.class,
                () -> roomSideImporter.importRoomSides(pathString)
        );

        assertEquals("The given file does not exist: " + pathString, exception.getMessage());
    }

    @Test
    void importRoomSides_whenIOExceptionIsThrown_shouldThrowException() {
        // given
        String pathString = "src/test/resources/invalid-input.txt";
        Path path = Path.of(".", pathString);

        try (MockedStatic<Files> utilities = mockStatic(Files.class)) {
            utilities.when(() -> Files.lines(path)).thenThrow(IOException.class);

            // when, then
            Exception exception = assertThrows(IllegalStateException.class,
                    () -> roomSideImporter.importRoomSides(pathString)
            );

            assertEquals("An IO error occurred during processing the file: " + pathString, exception.getMessage());
        }
    }
}
