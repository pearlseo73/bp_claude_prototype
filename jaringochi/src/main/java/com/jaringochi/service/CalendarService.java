package com.jaringochi.service;

import com.jaringochi.domain.AccountBookEntry;
import com.jaringochi.dto.CalendarDayDto;
import com.jaringochi.repository.EntryRepository;
import com.jaringochi.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 캘린더 서비스: 월간 일별 수입/지출 집계 및 날짜별 상세.
 */
public class CalendarService {
    private final EntryRepository entryRepository = new EntryRepository();

    /** 해당 월의 1일~말일 일별 집계 (index 0 == 1일) */
    public List<CalendarDayDto> getMonthData(long userId, int year, int month) {
        LocalDate first = LocalDate.of(year, month, 1);
        LocalDate start = DateUtil.getMonthStart(first);
        LocalDate end = DateUtil.getMonthEnd(first);
        int daysInMonth = end.getDayOfMonth();

        CalendarDayDto[] days = new CalendarDayDto[daysInMonth];
        for (int d = 1; d <= daysInMonth; d++) {
            days[d - 1] = new CalendarDayDto(d, 0, 0, false);
        }

        List<AccountBookEntry> entries = entryRepository.findByUserIdAndDateRange(userId, start, end);
        for (AccountBookEntry e : entries) {
            int idx = e.getEntryDate().getDayOfMonth() - 1;
            CalendarDayDto dto = days[idx];
            if ("INCOME".equals(e.getType())) {
                dto.setTotalIncome(dto.getTotalIncome() + e.getAmount());
            } else {
                dto.setTotalExpense(dto.getTotalExpense() + e.getAmount());
            }
            dto.setHasEntries(true);
        }
        return new ArrayList<>(List.of(days));
    }

    /** 특정 날짜의 거래 상세 */
    public List<AccountBookEntry> getDayEntries(long userId, LocalDate date) {
        return entryRepository.findByUserIdAndDateRange(userId, date, date);
    }
}
