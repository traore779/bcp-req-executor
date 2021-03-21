package com.bcp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcp.model.QueryObjectModel;

public interface QueryRepository extends JpaRepository<QueryObjectModel,Long>{

}
