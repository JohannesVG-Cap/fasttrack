package com.airfranceklm.fasttrack.assignment.enums;

public enum HolidayStatus {
    DRAFT,
    REQUEST,
    SCHEDULED,
    ARCHIVED;


    public static boolean isValidForRemoval(HolidayStatus status) {
        return status == DRAFT || status == REQUEST || status == SCHEDULED;
    }
}
