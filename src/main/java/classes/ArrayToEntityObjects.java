package classes;

import entites.DailyRanksOrders;
import entites.ProductsInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.DailyRanksOrdersServiceImp;
import service.ProductsInfoServiceImp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArrayToEntityObjects {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DailyRanksOrders dailyRanksOrders = new DailyRanksOrders();

    public void iterateArray(Object[][] data, String tagId, int pageNum) {
        JSONArray arrayRankOrder = new JSONArray();
        String ranks = null;
        Boolean arrayListReturn = fetchDailyRanksOrdersDao(tagId, pageNum);

        if (arrayListReturn) {
            for (Object[] aData : data) {
                JSONObject obj = new JSONObject();
                for (int j = 0; j < aData.length; j++) {
                    if (j == 0)
                        obj.put("r", aData[j]);  // r - rank
                    else if (j == 2) {
                        obj.put("p", aData[j]);  // p - productId
                        ranks = fetchObjectInProductsInfo(tagId, (Long) aData[j]);
                    } else if (j == 5)
                        obj.put("o", aData[j]);  // o - order
                }
                if (ranks == null) {
                    insertObjectInProductsInfo(aData, tagId);
                } else {
                    updateObjectInProductsInfo(aData, tagId);
                }
                arrayRankOrder.add(obj);
            }
            insertObjectInDailyRanksOrders(arrayRankOrder, tagId, pageNum);
        } else System.out.println("Already in the database");
    }

    private void updateObjectInProductsInfo(Object[] product, String tagId) {
        new ProductsInfoServiceImp().updateProductsInfoService(product,tagId);
    }

    private void insertObjectInProductsInfo(Object[] product, String tagId) {
        JSONArray arrayProductInfo = new JSONArray();
        ProductsInfo productsInfo = new ProductsInfo();

        productsInfo.setTagId(tagId);
        productsInfo.setProductURL((String) product[1]);
        productsInfo.setProductId((Long) product[2]);
        productsInfo.setProductName((String) product[3]);
        productsInfo.setProductImage((String) product[4]);

        productsInfo.setCreatedDate(dateFormat.format(new Date()));

        String year = dateFormat.format(new Date()).split("-")[0];
        JSONObject obj = new JSONObject();
        obj.put("d", dateFormat.format(new Date()));   // d - date
        obj.put("r", product[0]);   // r -rank
        arrayProductInfo.add(obj);

        switch (year) {
            case "2018":
                productsInfo.setRanks_2018(arrayProductInfo.toJSONString());
                break;
            case "2019":
                productsInfo.setRanks_2019(arrayProductInfo.toJSONString());
                break;
            case "2020":
                productsInfo.setRanks_2020(arrayProductInfo.toJSONString());
                break;
            case "2021":
                productsInfo.setRanks_2021(arrayProductInfo.toJSONString());
                break;
        }
        new ProductsInfoServiceImp().insertProductsInfoService(productsInfo);
    }

    private String fetchObjectInProductsInfo(String tagId, long id) {
        return new ProductsInfoServiceImp().fetchRanksProductsInfoDao(tagId, id);
    }

    private Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber) {
        return new DailyRanksOrdersServiceImp().fetchDailyRanksOrdersDao(tagId, pageNumber);
    }

    private void insertObjectInDailyRanksOrders(JSONArray arrayRankOrder, String tagId, int pageNum) {
        dailyRanksOrders.setPageNumber(pageNum);
        dailyRanksOrders.setTagId(tagId);
        dailyRanksOrders.setDates(dateFormat.format(new Date()));
        dailyRanksOrders.setRankOrder(arrayRankOrder.toJSONString());
        new DailyRanksOrdersServiceImp().insertDailyRanksOrdersService(dailyRanksOrders);
    }
}