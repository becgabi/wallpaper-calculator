package org.example.model;

import java.util.Arrays;
import java.util.Collections;

/**
 * @param length Length of the room in feet.
 * @param width  Width of the room in feet.
 * @param height Height of the room in feet.
 */
public record RectangularPrismRoom(int length, int width, int height) implements Room {

    /**
     * Calculate surface area of the rectangular prism room with an additional smallest side.
     *
     * @return int (total square feet)
     */
    @Override
    public int getSurfaceArea() {
        return 2 * length * width + 2 * width * height + 2 * height * length + getSizeOfSmallestSide();
    }

    private int getSizeOfSmallestSide() {
        return Collections.min(Arrays.asList(length, width, height));
    }
}
