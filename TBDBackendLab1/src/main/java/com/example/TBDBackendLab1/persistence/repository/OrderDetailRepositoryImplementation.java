package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.persistence.entity.OrderDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class OrderDetailRepositoryImplementation  {

    @Autowired
    private Sql2o sql2o;

}
