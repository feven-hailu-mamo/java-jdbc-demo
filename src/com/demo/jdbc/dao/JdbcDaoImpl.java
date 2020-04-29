package com.demo.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.demo.jdbc.model.Circle;
 
@Component
public class JdbcDaoImpl {

	@Autowired
	private DataSource dataSource;
	
	//private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	//private SimpleJdbcTemplate simpleJdbcTemplate;
	
	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}
	public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
		//this.dataSource = dataSource;
		//this is a common practice
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	public Circle getCircle(int circleId) {
		Connection connection = null;
		try {
//		    String driver = "org.apache.derby.jdbc.ClientDriver";
//			Class.forName(driver).newInstance();		
			connection = DriverManager.getConnection("jdbc:derby://localhost:1527/db");
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM circle WHERE id = ?");
			ps.setInt(1, circleId);
			Circle circle = null;
			ResultSet result = ps.executeQuery();			
			if(result.next()) {
				circle = new Circle(circleId, result.getString("name"));
			}
			result.close();
			ps.close();			
			return circle;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
	}
	public Circle getCircleSpring(int circleId) {
		Connection connection = null;
		try {
			//using spring xml configuration
			connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM circle WHERE id = ?");
			ps.setInt(1, circleId);
			Circle circle = null;
			ResultSet result = ps.executeQuery();			
			if(result.next()) {
				circle = new Circle(circleId, result.getString("name"));
			}
			result.close();
			ps.close();			
			return circle;
		}
		catch(Exception e) {
			throw new RuntimeException();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		
	}
	
	public int getCircleCount() {
		String sql = "SELECT COUNT(*) FROM circle";
		//jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public String getCircleName(int circleId) {
		String sql = "SELECT NAME FROM circle WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, String.class);
	}
	
	public Circle getCircleForId(int circleId) {
		String sql = "SELECT * FROM circle WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, new CircleMapper());
	}
	
	public void insertCircle(Circle circle) {
		String sql = "INSERT INTO circle (ID, NAME) VALUES(?, ?)";
		jdbcTemplate.update(sql, new Object[] {circle.getId(), circle.getName()});
	}
	
	public void insertCirclenNamedParam(Circle circle) {
		String sql = "INSERT INTO circle (ID, NAME) VALUES(:id, :name)";//Named parameter
	    SqlParameterSource namedParameters = new MapSqlParameterSource("id", circle.getId()).addValue("name", circle.getName());
	    namedJdbcTemplate.update(sql, namedParameters);
	}
	
	private static final class CircleMapper implements RowMapper<Circle>{

		@Override
		public Circle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Circle circle = new Circle();
			circle.setId(resultSet.getInt("ID"));
			circle.setName(resultSet.getString("NAME"));
			return circle;
		}
		
	} 
	 
	
}
