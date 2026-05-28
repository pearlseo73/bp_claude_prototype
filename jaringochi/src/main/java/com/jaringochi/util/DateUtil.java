package com.jaringochi.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * 날짜 관련 유틸리티 클래스
 */
public class DateUtil {

    /**
     * 해당 날짜가 속한 주의 월요일을 반환
     */
    public static LocalDate getWeekStart(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    /**
     * 해당 날짜가 속한 주의 일요일을 반환
     */
    public static LocalDate getWeekEnd(LocalDate date) {
        return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    /**
     * 해당 날짜가 속한 월의 첫째 날을 반환
     */
    public static LocalDate getMonthStart(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 해당 날짜가 속한 월의 마지막 날을 반환
     */
    public static LocalDate getMonthEnd(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 주간 예산을 월간 예산으로 변환 (평균 4.345주/월 기준)
     */
    public static int weeklyToMonthly(int weekly) {
        return (int) (weekly * 4.345);
    }

    /**
     * 월간 예산을 주간 예산으로 변환 (평균 4.345주/월 기준)
     */
    public static int monthlyToWeekly(int monthly) {
        return (int) (monthly / 4.345);
    }
}
