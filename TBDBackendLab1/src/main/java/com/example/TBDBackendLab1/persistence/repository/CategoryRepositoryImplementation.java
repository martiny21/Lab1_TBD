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
                "VALUES (:categoryName)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Long generatedId = (Long) con.createQuery(sql, true)
                    .addParameter("category_name", category.getCategoryName())
                    .executeUpdate()
                    .getKey();

            category.setIdCategory(generatedId);
            return category;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public CategoryEntity getById(Long id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM categoty WHERE id=:id_category")
                    .addParameter("id_category",id)
                    .executeAndFetchFirst(CategoryEntity.class);
        }
    }
}
