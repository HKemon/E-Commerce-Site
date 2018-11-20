package service;

import dao.ProductsInfoDaoImp;
import entites.ProductsInfo;

public class ProductsInfoServiceImp implements ProductsInfoService {
    @Override
    public void insertProductsInfoService(ProductsInfo productsInfo) {
        new ProductsInfoDaoImp().insertProductsInfoDao(productsInfo);
    }

    @Override
    public void updateProductsInfoService(Object[] product, String tagId) {
        new ProductsInfoDaoImp().updateProductsInfoDao(product,tagId);
    }

    @Override
    public String fetchRanksProductsInfoDao(String tagId, long pId) {
        return new ProductsInfoDaoImp().fetchRanksProductsInfoDao(tagId, pId);
    }
}