package com.bcp.service;

import java.util.List;


import com.bcp.dto.QueryObjectDto;
import org.springframework.stereotype.Service;

@Service
public interface QueryService {

  void queryExecute (long id) throws Exception;
  List<QueryObjectDto> getListeRequetes();
  void executeRequest(long id) throws Exception;

}
