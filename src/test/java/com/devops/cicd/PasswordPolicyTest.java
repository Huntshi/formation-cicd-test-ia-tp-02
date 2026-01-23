package com.devops.cicd;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class PasswordPolicyTest {

    @Test
    void weakIfLessThan8Chars() {
        assertFalse(PasswordPolicy.isStrong("Ab1!"));
    }

    @Test
    void strongIfHasUpperLowerDigitAndSpecialAndMin8() {
        assertTrue(PasswordPolicy.isStrong("Azerty1!"));
    }

    @Test
    void notStrongIfNoSpecialChar() {
        assertFalse(PasswordPolicy.isStrong("Azerty12"));
    }

    @Test
    void notStrongIfNoDigit() {
        assertFalse(PasswordPolicy.isStrong("Azerty!!"));
    }

    @Test
    void notStrongIfNoUppercase() {
        assertFalse(PasswordPolicy.isStrong("azerty1!"));
    }

    @Test
    void notStrongIfNoLowercase() {
        assertFalse(PasswordPolicy.isStrong("AZERTY1!"));
    }

    @Test
    void notStrongIfNullOrBlank() {
        assertFalse(PasswordPolicy.isStrong(null));
        assertFalse(PasswordPolicy.isStrong(""));
        assertFalse(PasswordPolicy.isStrong("        "));
    }
}
