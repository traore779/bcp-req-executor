package com.bcp.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import com.bcp.model.QueryObjectModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bcp.dto.QueryObjectDto;
import com.bcp.service.QueryService;


@RestController
public class QueriesController {
	
	@Autowired
	private QueryService queryService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "api/requetes/afficher")
	public List<QueryObjectDto> getQueries() {
		List<QueryObjectModel> queries = queryService.getListeRequetes();
		return queries.stream().map(this::convertToDto).collect(Collectors.toList());
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


	// convert queryModel to queryDto
	private QueryObjectDto convertToDto(QueryObjectModel queryObjectModel){
		QueryObjectDto queryObjectDto = this.modelMapper.map(queryObjectModel, QueryObjectDto.class);
		return queryObjectDto;
	}

	// convert queryDto to queryModel
	private QueryObjectModel convertToDto(QueryObjectDto queryObjectDto){
		QueryObjectModel queryObjectModel = this.modelMapper.map(queryObjectDto, QueryObjectModel.class);
		return queryObjectModel;
	}
}
