package dao;

import entites.DailyRanksOrders;
import org.hibernate.Session;
import util.HibernateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyRanksOrdersDaoImp implements DailyRanksOrdersDao {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    .setParameter("dates", dateFormat.format(new Date()))
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