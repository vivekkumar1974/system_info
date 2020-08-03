package com.client.system.info.api.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.client.system.info.api.dto.SystemInfoDTO;

/**
 * System Info DAO Implementation.
 *
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class SystemInfoDAOImpl implements SystemInfoDAO {

	static final Log LOG = LogFactory.getLog(SystemInfoDAOImpl.class);

	static final String BASE_SQL = "SELECT SERIAL_NUMER, OS_NAME, RAM_SIZE, MANUFACTURER, STORAGE_SIZE FROM pitneybowes_demo.SYSTEM_INFO; "; // $NON-NLS-2$

	static final String INSERT_SYSTEM_INFO = "INSERT INTO pitneybowes_demo.SYSTEM_INFO (SERIAL_NUMER, OS_NAME, RAM_SIZE, MANUFACTURER, STORAGE_SIZE) "
			+ " VALUES (:serialNumber, :osName, :ramSize, :manufacturer, :storageSize);";

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	@Autowired
	public void setJdbcTemplate(final DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * Fetched all System info data from DB
	 */
	@Override
	public List<SystemInfoDTO> loadAllSystemInfo() {
		String sql = BASE_SQL;
		try {
			return jdbcTemplate.query(sql, new SystemInfoRowMapper());
		} catch (EmptyResultDataAccessException exception) {
			return Collections.emptyList();
		}

	}

	/**
	 * Create System Info record in DB
	 */
	@Override
	public SystemInfoDTO createSystemInfo(SystemInfoDTO systemInfo) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("serialNumber", systemInfo.getSerialNumber());
		paramMap.addValue("osName", systemInfo.getOsName());
		paramMap.addValue("ramSize", systemInfo.getRamSize());
		paramMap.addValue("manufacturer", systemInfo.getManufacturer());
		paramMap.addValue("storageSize", systemInfo.getStorageSize());

		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(INSERT_SYSTEM_INFO, paramMap, holder);

		return systemInfo;
	}

}

class SystemInfoRowMapper implements RowMapper<SystemInfoDTO> {

	@Override
	public SystemInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SystemInfoDTO systemInfo = new SystemInfoDTO();
		systemInfo.setSerialNumber(rs.getString("SERIAL_NUMER"));
		systemInfo.setOsName(rs.getString("OS_NAME"));
		systemInfo.setRamSize(rs.getString("RAM_SIZE"));
		systemInfo.setManufacturer(rs.getString("MANUFACTURER"));
		systemInfo.setStorageSize(rs.getString("STORAGE_SIZE"));

		return systemInfo;
	}

}
