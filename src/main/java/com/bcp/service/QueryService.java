package com.bcp.service;

import java.util.List;


import com.bcp.model.QueryObjectModel;
import org.springframework.stereotype.Service;

@Service
public interface QueryService {

  void queryExecute (long id) throws Exception;
  List<QueryObjectModel> getListeRequetes();
  void executeRequest(long id) throws Exception;

}
