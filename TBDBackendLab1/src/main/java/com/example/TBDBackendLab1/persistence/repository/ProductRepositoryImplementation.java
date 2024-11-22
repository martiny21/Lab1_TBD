package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.persistence.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ProductRepositoryImplementation implements ProductRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public ProductEntity addProduct(ProductEntity product) {
        String sql = "INSERT INTO product (product_name, product_desc, price, stock, estate, category_id)" +
                "VALUES (:product_name, :product_desc, :price, :stock, :estate, :category_id)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Integer generatedId = (Integer) con.createQuery(sql, true)
                    .addParameter("product_name", product.getProduct_name())
                    .addParameter("product_desc", product.getProduct_desc())
                    .addParameter("price", product.getPrice())
                    .addParameter("stock", product.getStock())
                    .addParameter("estate", product.getEstate())
                    .addParameter("category_id", product.getCategory_id())
                    .executeUpdate()
                    .getKey();

            product.setProduct_id(generatedId);
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public ProductEntity getById(Integer product_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM product WHERE product_id=:product_id")
                    .addParameter("product_id",product_id)
                    .executeAndFetchFirst(ProductEntity.class);
        }
    }

    @Override
    public List<ProductEntity> getByCategory(Integer category_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM product WHERE category_id=:category_id")
                    .addParameter("category_id",category_id)
                    .executeAndFetch(ProductEntity.class);
        }
    }
}
