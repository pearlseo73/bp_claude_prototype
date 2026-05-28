package com.jaringochi.service;

import com.jaringochi.repository.EntryRepository;
import com.jaringochi.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 통계 서비스: 월별 수입/지출 추이 및 카테고리별 비율.
 */
public class StatsService {
    private final EntryRepository entryRepository = new EntryRepository();

    /** 최근 N개월 라벨 (예: "1월") */
    public List<String> getTrendLabels(int months) {
        List<String> labels = new ArrayList<>();
        LocalDate base = LocalDate.now();
        for (int i = months - 1; i >= 0; i--) {
            labels.add(base.minusMonths(i).getMonthValue() + "월");
        }
        return labels;
    }

    public List<Integer> getMonthlyTrend(long userId, String type, int months) {
        List<Integer> values = new ArrayList<>();
        LocalDate base = LocalDate.now();
        for (int i = months - 1; i >= 0; i--) {
            LocalDate m = base.minusMonths(i);
            LocalDate start = DateUtil.getMonthStart(m);
            LocalDate end = DateUtil.getMonthEnd(m);
            values.add(entryRepository.sumByUserIdAndTypeAndDateRange(userId, type, start, end));
        }
        return values;
    }

    public Map<String, Integer> getCategoryBreakdown(long userId, String type) {
        LocalDate now = LocalDate.now();
        LocalDate start = DateUtil.getMonthStart(now);
        LocalDate end = DateUtil.getMonthEnd(now);
        return entryRepository.sumByCategory(userId, type, start, end);
    }
}
