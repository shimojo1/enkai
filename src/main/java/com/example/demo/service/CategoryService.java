package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.BaseDao;
import com.example.demo.entity.Category;

@Service
public class CategoryService implements BaseService<Category> {
	@Autowired
	private BaseDao<Category> dao;

	@Override
	public List<Category> findAll() {
		return dao.findAll();
	}

	@Override
	public Category findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(Category category) {
		dao.save(category);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
}