package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;

@Repository
public class EventDao implements BaseDao<Event> {
	@Autowired
	EventRepository repository;

	@Override
	public List<Event> findAll() {
		return repository.findAll();
	}

	@Override
	public Event findById(Integer id) throws DataNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Event event) {
		this.repository.save(event);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Event event = this.findById(id);
			this.repository.deleteById(event.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}

	public List<Event> findByUserId(Integer userId) {
		return this.repository.findByUserId(userId);
	}
}