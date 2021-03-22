package com.bcp.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import com.bcp.model.QueryObjectModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.bcp.dto.QueryObjectDto;
import com.bcp.service.QueryService;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class QueriesController {
	
	@Autowired
	private QueryService queryService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "api/requetes")
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
		System.out.println("We are here..........");
		this.queryService.executeRequest(id);
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
