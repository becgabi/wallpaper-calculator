package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Room;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.String.format;

@Slf4j
public class RoomSideImporter {

    /**
     * Separator of sides in the file to be imported.
     */
    public static final String SEPARATOR = "x";

    private final RoomFactory factory;

    private final RoomSideValidator validator;

    public RoomSideImporter(RoomFactory factory, RoomSideValidator validator) {
        this.factory = factory;
        this.validator = validator;
    }

    /**
     * Checks whether given file exists and imports the valid room sides.
     *
     * @param pathString Path of the file to import.
     * @return imported room list.
     */
    public List<Room> importRoomSides(String pathString) {
        Objects.requireNonNull(pathString, "The given file parameter is missing");

        Path path = Path.of(".", pathString);

        validateFileExistence(pathString, path);

        try (Stream<String> lines = Files.lines(path)) {
            List<Room> roomList = lines
                    .map(RoomSideImporter::splitSides)
                    .filter(validator::validSides)
                    .map(RoomSideImporter::convertToIntegerList)
                    .map(sides -> factory.newRoom(sides.get(0), sides.get(1), sides.get(2)))
                    .toList();

            logImportResult(roomList.size());

            return roomList;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("An IO error occurred during processing the file: %s",
                    pathString
            ));
        }
    }

    private static List<Integer> convertToIntegerList(String[] sides) {
        return Arrays.stream(sides).map(BigInteger::new).map(BigInteger::intValue).toList();
    }

    private static void validateFileExistence(String pathString, Path path) {
        if (Files.notExists(path)) {
            throw new IllegalStateException(String.format("The given file does not exist: %s", pathString));
        }
    }

    private static void logImportResult(int size) {
        log.info("{} room data has been imported successfully.", format("%,d", size));
    }

    private static String[] splitSides(String line) {
        return line.split(SEPARATOR);
    }
}
