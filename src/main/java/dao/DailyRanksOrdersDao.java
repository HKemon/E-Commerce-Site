package dao;

import entites.DailyRanksOrders;

public interface DailyRanksOrdersDao {
    void insertDailyRanksOrdersDao(DailyRanksOrders dailyRanksOrders);
    Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber);
}