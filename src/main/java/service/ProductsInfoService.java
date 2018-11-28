package service;

import entites.ProductsInfo;

import java.util.List;

public interface ProductsInfoService {
    void insertProductsInfoService(ProductsInfo productsInfo);
    void updateProductsInfoService(Object[] product, String tagId);
    String fetchRanksProductsInfoDao(String tagId, long pId);
}