package com.client.system.info.api.dao;

import java.util.List;

import javax.sql.DataSource;

import com.client.system.info.api.dto.SystemInfoDTO;

public interface SystemInfoDAO {

	public void setJdbcTemplate(final DataSource dataSource);

	public List<SystemInfoDTO> loadAllSystemInfo();

	SystemInfoDTO createSystemInfo(SystemInfoDTO systemInfo);

}
