package classes;

import entites.DailyRanksOrders;
import entites.ProductsInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import service.DailyRanksOrdersServiceImp;
import service.ProductsInfoServiceImp;

import java.util.Date;

import static util.ProjectUtils.*;

public class ArrayToEntityObjects {
    private DailyRanksOrders dailyRanksOrders = new DailyRanksOrders();

    public void iterateArray(String tagId, String url, int pageNum) {
        JSONArray arrayRankOrder = new JSONArray();
        String ranks = "";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Boolean arrayListReturn = fetchDailyRanksOrdersDao(tagId, pageNum);
        System.out.println(arrayListReturn);
        if (arrayListReturn) {
            for (Object[] aData : data) {
                JSONObject obj = null;
                boolean insertInOrderRank = true;
                if (aData[0] != null) {
                    obj = new JSONObject();
                    for (int j = 0; j < aData.length; j++) {
                        if (j == 0)
                            obj.put("r", aData[j]);  // r - rank
                        else if (j == 2) {
                            obj.put("p", aData[j]);  // p - productId
                            ranks = fetchObjectInProductsInfo(tagId, (Long) aData[j]);
                        } else if (j == 5) {
                            obj.put("o", aData[j]);  // o - order
                            if ((int) aData[j] < minimumOrder) {
                                accessNextPage = false;
                            }
                        }
                    }

                    try {
                        Thread.sleep(750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (ranks.equals("")) {
                        if ((int) aData[5] > minimumOrder) {
                            insertObjectInProductsInfo(aData, tagId);
                        } else {
                            insertInOrderRank = false;
                            accessNextPage = false;
                        }
                    } else {
                        try {
                            JSONArray jsonArr = (JSONArray) parser.parse(ranks);
                            JSONObject jsonObj = (JSONObject) jsonArr.get(jsonArr.size() - 1);
                            if (!jsonObj.get("d").equals(yearMonthDate)) {
                                updateObjectInProductsInfo(aData, tagId);
                            } else {
                                System.out.println("This Product is Already in Database !!!! YOOOOO");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (insertInOrderRank)
                    arrayRankOrder.add(obj);
            }
            insertObjectInDailyRanksOrders(arrayRankOrder, tagId, url, pageNum);
        } else System.out.println("Already in the database");
    }

    private void updateObjectInProductsInfo(Object[] product, String tagId) {
        new ProductsInfoServiceImp().updateProductsInfoService(product, tagId);
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

    private void insertObjectInDailyRanksOrders(JSONArray arrayRankOrder, String tagId, String url, int pageNum) {
        dailyRanksOrders.setTagId(tagId);
        dailyRanksOrders.setPageURL(url);
        dailyRanksOrders.setDates(dateFormat.format(new Date()));
        dailyRanksOrders.setPageNumber(pageNum);
        dailyRanksOrders.setRankOrder(arrayRankOrder.toJSONString());
        new DailyRanksOrdersServiceImp().insertDailyRanksOrdersService(dailyRanksOrders);
    }
}