package com.mahallat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahallat.dao.SettingsDao;
import com.mahallat.entity.Settings;

@Service
public class SettingService implements ISettingsService{

	@Autowired
	private SettingsDao settingDao;
	@Override
	public List<Settings> getAllSettings() {
		return settingDao.getAllSettings();
	}

}
