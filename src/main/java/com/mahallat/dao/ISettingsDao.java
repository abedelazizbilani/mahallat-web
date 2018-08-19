package com.mahallat.dao;

import java.util.List;import org.apache.coyote.http2.Setting;

import com.mahallat.entity.Settings;

public interface ISettingsDao {
	List<Settings> getAllSettings();
}
