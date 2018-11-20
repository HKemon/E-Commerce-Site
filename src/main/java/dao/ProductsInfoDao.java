package dao;

import entites.ProductsInfo;

public interface ProductsInfoDao {
    void insertProductsInfoDao(ProductsInfo productsInfo);
    void updateProductsInfoDao(Object[] product, String tagId);
    String fetchRanksProductsInfoDao(String tagId, long id);
}
