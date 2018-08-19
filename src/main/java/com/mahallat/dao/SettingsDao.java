package com.mahallat.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mahallat.entity.Settings;

@Transactional(rollbackFor = Exception.class)
@Repository("settingDao")
public class SettingsDao implements ISettingsDao{
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Settings> getAllSettings() {
		String hql = "From Settings as setting where setting.active = 1";
		return (List<Settings>) entityManager.createQuery(hql).getResultList();
	}

}
