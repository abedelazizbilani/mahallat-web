package com.mahallat.services;

import java.util.List;
import com.mahallat.entity.Store;;

public interface IStoreService {
	List<Store> getAllStores();
	Store one(int id);
}
