package service;

import dao.DailyRanksOrdersDaoImp;
import entites.DailyRanksOrders;

public class DailyRanksOrdersServiceImp implements DailyRanksOrdersService {
    @Override
    public void insertDailyRanksOrdersService(DailyRanksOrders dailyRanksOrders) {
        new DailyRanksOrdersDaoImp().insertDailyRanksOrdersDao(dailyRanksOrders);
    }

    @Override
    public Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber) {
        return  new DailyRanksOrdersDaoImp().fetchDailyRanksOrdersDao(tagId,pageNumber);
    }
}