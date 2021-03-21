package com.bcp.service;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcp.dao.QueryRepository;

import com.bcp.dto.QueryObjectDto;
import com.bcp.model.QueryObjectModel;
import com.mysql.cj.jdbc.Driver;

import org.apache.ibatis.jdbc.ScriptRunner;

@Service
public class QueryServiceImpl implements QueryService {

	private Connection con = null;

	@Autowired
	private QueryRepository queryRepository;

	@PostConstruct
	public void connection() throws SQLException {
		DriverManager.registerDriver(new Driver());
		// Getting the connection
		String mysqlUrl = "jdbc:mysql://localhost:3306/bcpprojet?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
		con = DriverManager.getConnection(mysqlUrl, "root", "");
		System.out.println("Connection established......");
		// Initialize the script runner
	}

	@Override
	public List<QueryObjectDto> getListeRequetes() {

		List<QueryObjectModel> queriesList = queryRepository.findAll();
		List<QueryObjectDto> dtoList = new ArrayList<>();
		QueryObjectDto dto;
		for (QueryObjectModel model : queriesList) {
			dto = new QueryObjectDto();
			dto.setId(model.getId());
			dto.setDescription((model.getDescription()));
			dto.setRequest((model.getRequest()));
			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public void executeRequest(long id) throws Exception {
		Optional<QueryObjectModel> queryObjectModel = queryRepository.findById(id);

		PreparedStatement ps = con.prepareStatement(queryObjectModel.get().getRequest());
		//System.out.println(queryObjectModel.get().getRequest());
		ResultSet rs = ps.executeQuery();

		//Export vers le fichier csv ;;;;; veuillez changer le chemmin
		CSVWriter writer = new CSVWriter(new FileWriter("/home/traore/essai.csv"));
		writer.writeAll(rs, true);
	}


	@Override
	public void queryExecute(long id) throws Exception {

		Optional<QueryObjectModel> optional = queryRepository.findById(id);
		QueryObjectModel queryObjectModel = optional.get();
		// Creating a reader object
		Reader reader = new BufferedReader(new FileReader(queryObjectModel.getRequest()));
		System.out.println(reader.toString());
		ScriptRunner sr = new ScriptRunner(con);
		sr.runScript(reader);
	}

}
