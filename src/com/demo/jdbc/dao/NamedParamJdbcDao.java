package com.demo.jdbc.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class NamedParamJdbcDao extends NamedParameterJdbcDaoSupport {
 
	public int getCircleCount() {
		return 1;
		// this.getNamedParameterJdbcTemplate()
				
	}
}
