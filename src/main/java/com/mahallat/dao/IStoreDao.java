package com.mahallat.dao;
import java.util.List;
import com.mahallat.entity.Store;;

public interface IStoreDao {
	List<Store> getAllStores();
	Store one(int id);
}
