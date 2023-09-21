package org.example.model;

/**
 * @param length Length of the room in feet which is equal to width and height.
 */
public record CubeShapedRoom(int length) implements Room, Comparable<CubeShapedRoom> {

    /**
     * Calculate surface area of the cube-shaped room with an additional side.
     *
     * @return int (total square feet)
     */
    @Override
    public int getSurfaceArea() {
        return 7 * length * length;
    }

    @Override
    public int compareTo(CubeShapedRoom o) {
        return o.length() - this.length;
    }
}
