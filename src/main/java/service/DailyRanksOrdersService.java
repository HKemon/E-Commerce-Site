package service;

import entites.DailyRanksOrders;

public interface DailyRanksOrdersService {
    void insertDailyRanksOrdersService(DailyRanksOrders dailyRanksOrders);
    Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber);
}