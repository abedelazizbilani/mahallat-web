package com.mahallat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mahallat.dao.IStoreDao;
import com.mahallat.entity.Store;
import java.util.List;

@Service
public class StoreService implements IStoreService{
	@Autowired
	private IStoreDao storeDAO;
	
	@Override
	public List<Store> getAllStores(){
		return storeDAO.getAllStores();
	}
	
	@Override
	public Store one(int id) {
		Store obj = storeDAO.one(id);
		return obj;
	}
	
}
