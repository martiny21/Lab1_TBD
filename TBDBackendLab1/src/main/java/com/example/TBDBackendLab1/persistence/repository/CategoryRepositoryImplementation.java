package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class CategoryRepositoryImplementation implements CategoryRepository{

    @Autowired
    private Sql2o sql2o;
    @Override
    public CategoryEntity addCategory(CategoryEntity category) {
        String sql = "INSERT INTO category (category_name)" +
                "VALUES (:category_name)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Integer generatedId = (Integer) con.createQuery(sql, true)
                    .addParameter("categoryName", category.getCategory_name())
                    .executeUpdate()
                    .getKey();

            category.setCategory_id(generatedId);
            return category;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public CategoryEntity getById(Integer category_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM category WHERE category_id=:category_id")
                    .addParameter("category_id",category_id)
                    .executeAndFetchFirst(CategoryEntity.class);
        }
    }
}
