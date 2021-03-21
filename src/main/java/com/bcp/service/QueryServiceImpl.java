package com.bcp.service;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
			dto.setRequest(model.getRequest());
			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public void executeRequest(long id) throws Exception {
		QueryObjectModel queryObjectModel = queryRepository.findById(id).get();
		String query = queryObjectModel.getRequest();

		if(query != null) {
			File file = new File("/home/traore/exemple1.csv");
			//System.out.println(file.createNewFile());
			//file.setWritable(true, false);
			String motsQuery[] = query.split(" ");
			StringBuilder requestBuilder = new StringBuilder(motsQuery[0] + " ");
			int i;
			for (i = 1; i < motsQuery.length; i++) {
				if (!motsQuery[i].toLowerCase().equals("from"))
					requestBuilder.append(motsQuery[i] + " ");
				else
					break;
			}

			requestBuilder.append("INTO " + file +
					" FIELD TERMINATED BY ', ' OPTIONALLY ENCLOSED BY '\"' " +
					"LINES TERMINATED BY '\\n' ");

			for (int j = i; j < motsQuery.length; j++)
				requestBuilder.append(motsQuery[j] + " ");

			String request = requestBuilder.toString() + ";";

			PreparedStatement ps = con.prepareStatement(request);
			System.out.println(request);
			//ResultSet rs = ps.executeQuery();
		}
		else
			System.out.println("Entrer une requete");
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
