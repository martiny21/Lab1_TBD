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
        String sql = "INSERT INTO categoria (nombre)" +
                "VALUES (:nombre)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Integer generatedId = (Integer) con.createQuery(sql, true)
                    .addParameter("nombre", category.getNombre())
                    .executeUpdate()
                    .getKey();

            category.setId_categoria(generatedId);
            return category;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public CategoryEntity getById(Integer id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM categoria WHERE id_categoria=:id_categoria")
                    .addParameter("id_categoria",id)
                    .executeAndFetchFirst(CategoryEntity.class);
        }
    }
}
