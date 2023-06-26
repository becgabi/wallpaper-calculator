package org.example.service;

import org.example.model.CubeShapedRoom;
import org.example.model.RectangularPrismRoom;
import org.example.model.Room;

public class RoomFactory {

    /**
     * Creates the corresponding instance of room based on the size of the sides.
     *
     * @param length Length of the room.
     * @param width  Width of the room.
     * @param height Height of the room.
     * @return Corresponding Room instance.
     */
    public Room newRoom(int length, int width, int height) {
        if (length < 0 || width < 0 || height < 0) {
            throw new IllegalArgumentException("Every room side should be a positive number.");
        }

        if (length == width && width == height) {
            return new CubeShapedRoom(length);
        } else {
            return new RectangularPrismRoom(length, width, height);
        }
    }
}
