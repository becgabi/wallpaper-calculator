package org.example.service;

import org.example.model.CubeShapedRoom;
import org.example.model.Room;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static java.math.BigInteger.ZERO;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class RoomManager {

    /**
     * Returns a map in which each room instance is stored along with its occurrence.
     *
     * @return Occurrence map of rooms.
     */
    public Map<Room, Long> convertRoomListToOccurrenceMap(List<Room> roomList) {
        return roomList.stream().collect(groupingBy(identity(), counting()));
    }

    /**
     * Returns the surface area of the given room occurrence map.
     *
     * @param roomOccurrenceMap Occurrence map of rooms.
     * @return Surface area of all the rooms.
     */
    public BigInteger calculateTotalSurfaceArea(Map<Room, Long> roomOccurrenceMap) {
        return roomOccurrenceMap.entrySet()
                .stream()
                .map(entry -> entry.getKey().getSurfaceArea() * entry.getValue())
                .map(BigInteger::valueOf)
                .reduce(ZERO, BigInteger::add);
    }

    /**
     * Returns all rooms that have a cubic shape.
     *
     * @param roomOccurrenceMap Occurrence map of rooms.
     * @return Room list.
     */
    public List<CubeShapedRoom> getCubeShapedRooms(Map<Room, Long> roomOccurrenceMap) {
        return roomOccurrenceMap.keySet()
                .stream()
                .filter(CubeShapedRoom.class::isInstance)
                .map(CubeShapedRoom.class::cast)
                .sorted()
                .toList();
    }

    /**
     * Returns all rooms that are appearing more than once.
     *
     * @param roomOccurrenceMap Occurrence map of rooms.
     * @return Room list.
     */
    public List<Room> getMultipleOccurringRooms(Map<Room, Long> roomOccurrenceMap) {
        return roomOccurrenceMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
    }
}
