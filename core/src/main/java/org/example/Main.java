package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.model.CubeShapedRoom;
import org.example.model.Room;
import org.example.service.RoomFactory;
import org.example.service.RoomManager;
import org.example.service.RoomSideImporter;
import org.example.service.RoomSideValidator;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Slf4j
public class Main {

    public static void main(String[] args) {
        RoomSideImporter importer = new RoomSideImporter(new RoomFactory(), new RoomSideValidator());
        RoomManager roomManager = new RoomManager();

        String pathString = args[0];

        // Import room sides
        List<Room> surfaceAreaList = importer.importRoomSides(pathString);

        // Convert room list to occurrence map
        Map<Room, Long> roomOccurrenceMap = roomManager.convertRoomListToOccurrenceMap(surfaceAreaList);

        // Calculate and print surface area
        BigInteger totalSurfaceArea = roomManager.calculateTotalSurfaceArea(roomOccurrenceMap);
        printTotalSurfaceArea(totalSurfaceArea);

        // List and print cube-shaped rooms
        List<CubeShapedRoom> cubeShapedRooms = roomManager.getCubeShapedRooms(roomOccurrenceMap);
        printCubeShapedRooms(cubeShapedRooms);

        // List and print multiple occurring rooms
        List<Room> multipleOccurringRooms = roomManager.getMultipleOccurringRooms(roomOccurrenceMap);
        printMultipleOccurringRooms(multipleOccurringRooms);
    }

    private static void printTotalSurfaceArea(BigInteger totalSurfaceArea) {
        log.info("Total surface area in square feet: {}.", format("%,d", totalSurfaceArea));
    }

    private static void printCubeShapedRooms(List<CubeShapedRoom> cubeShapedRooms) {
        if (cubeShapedRooms.isEmpty()) {
            log.info("There is no cube-shaped room among the imported data.");
        } else {
            log.info("All rooms from input that have a cubic shape in descending order:");
            log.info(cubeShapedRooms.toString());
        }
    }

    private static void printMultipleOccurringRooms(List<Room> multipleOccurringRooms) {
        if (multipleOccurringRooms.isEmpty()) {
            log.info("There is no room that is appearing more than once among the imported data.");
        } else {
            log.info("All rooms from input that are appearing more than once:");
            log.info(multipleOccurringRooms.toString());
        }
    }
}
