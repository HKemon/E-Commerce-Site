package dao;

import entites.DailyRanksOrders;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static util.ProjectUtils.dateFormat;
import static util.ProjectUtils.yearMonthDate;


public class DailyRanksOrdersDaoImp implements DailyRanksOrdersDao {

    @Override
    public void insertDailyRanksOrdersDao(DailyRanksOrders dailyRanksOrders) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(dailyRanksOrders);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber) {
        List dailyRanksOrder = new ArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "From DailyRanksOrders p where dates=:dates and tagId=:tagId and pageNumber=:pageNumber";
            dailyRanksOrder = session.createQuery(HQL)
                    .setParameter("dates", yearMonthDate)
                    .setParameter("tagId", tagId)
                    .setParameter("pageNumber", pageNumber)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dailyRanksOrder.isEmpty();
    }
}