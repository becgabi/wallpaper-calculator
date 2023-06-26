package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

@Slf4j
public class RoomSideValidator {

    /**
     * Checks whether the sides of the room are valid.
     *
     * @param sides String[] (length, width, height).
     * @return boolean Whether the side list is valid or not.
     */
    public boolean validSides(String[] sides) {
        if (sides == null) {
            log.error("The side data of the room is missing.");
            return false;

        } else if (sides.length != 3 || StringUtils.isAnyBlank(sides)) {
            log.error("The room does not have 3 sides.");
            return false;

        } else if (isNotPositiveInteger(sides[0])) {
            log.error("The length is not a correct positive integer: {}.", sides[0]);
            return false;

        } else if (isNotPositiveInteger(sides[1])) {
            log.error("The width is not a correct positive integer: {}.", sides[1]);
            return false;

        } else if (isNotPositiveInteger(sides[2])) {
            log.error("The height is not a correct positive integer: {}.", sides[2]);
            return false;
        }

        return true;
    }

    private boolean isNotPositiveInteger(String side) {
        return !StringUtils.isNumeric(side) || new BigInteger(side).bitLength() >= 32;
    }

}
