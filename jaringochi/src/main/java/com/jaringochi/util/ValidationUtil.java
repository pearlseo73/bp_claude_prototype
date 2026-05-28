package com.jaringochi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 입력 유효성 검증 유틸리티 클래스
 */
public class ValidationUtil {

    /**
     * 문자열이 비어있는지 확인 (null 또는 공백만 있는 경우 true)
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * 양수인지 확인
     */
    public static boolean isPositive(int n) {
        return n > 0;
    }

    /**
     * 날짜 문자열이 yyyy-MM-dd 형식의 유효한 날짜인지 확인
     */
    public static boolean isValidDate(String dateStr) {
        if (isEmpty(dateStr)) {
            return false;
        }
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
