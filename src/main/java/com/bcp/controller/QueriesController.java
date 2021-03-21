package com.bcp.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.bcp.dto.QueryListResponse;
import com.bcp.dto.QueryObjectDto;
import com.bcp.service.QueryService;
import com.bcp.service.QueryServiceImpl;


@RestController
public class QueriesController {
	
	@Autowired
	private QueryService queryService;

	@GetMapping(value = "api/requetes/afficher")
	public QueryListResponse afficher() {
		QueryListResponse q = new QueryListResponse();
		q.setRequetes(queryService.getListeRequetes());
		return q;
	}
		
	@PostMapping("api/requetes/execute/{id}")
	public String queryExecute(@PathVariable(value ="id") int id) throws Exception {
		queryService.queryExecute(id);
		return "Done";
	}

	@GetMapping("api/requetes/executer/{id}")
	public void executeRequest(@PathVariable(value = "id") long id) throws Exception{
		queryService.executeRequest(id);
	}
}
