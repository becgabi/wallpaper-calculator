package service;

import org.example.service.RoomSideValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomSideValidatorTest {

    private RoomSideValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new RoomSideValidator();
    }

    @Test
    void testValidSides_NullInput_ReturnsFalse() {
        assertFalse(this.validator.validSides(null));
    }

    @Test
    void testValidSides_EmptySides_ReturnsFalse() {
        // given
        String[] sides = {};

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_LessThanThreeSides_ReturnsFalse() {
        // given
        String[] sides = {"10", "20"};

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_MoreThanThreeSides_ReturnsFalse() {
        // given
        String[] sides = {"10", "20", "30", "40"};

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_BlankSide_ReturnsFalse() {
        // given
        String[] sides = {"10", "", "30"};

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_NonNumericLength_ReturnsFalse() {
        // given
        String[] sides = {"ten", "20", "30"};

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_NonNumericWidth_ReturnsFalse() {
        // given
        String[] sides = {"10", "twenty", "30"};

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_NonNumericHeight_ReturnsFalse() {
        // given
        String[] sides = {"10", "20", "thirty"};

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_NonPositiveInteger_ReturnsFalse() {
        // given
        String[] sides = {"-10", "20", "30"};

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_IntegerExceedsBitLength_ReturnsFalse() {
        // given
        String[] sides = {"4294967296", "20", "30"}; // 2^32

        // when, then
        assertFalse(this.validator.validSides(sides));
    }

    @Test
    void testValidSides_ValidSides_ReturnsTrue() {
        // given
        String[] sides = {"10", "20", "30"};

        // when, then
        assertTrue(this.validator.validSides(sides));
    }
}
