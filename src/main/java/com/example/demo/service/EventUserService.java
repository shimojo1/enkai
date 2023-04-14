package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.EventUserDao;
import com.example.demo.entity.Event;
import com.example.demo.entity.EventUser;
import com.example.demo.entity.User;

@Service
public class EventUserService implements BaseService<EventUser> {
	@Autowired
	private EventUserDao dao;

	@Override
	public List<EventUser> findAll() {
		return dao.findAll();
	}

	@Override
	public EventUser findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(EventUser eventUser) {
		dao.save(eventUser);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	public List<EventUser> findByEvent(Event event) throws DataNotFoundException {
		return dao.findByEvent(event);
	}

	public EventUser findByEventAndUser(Event event, User user) throws DataNotFoundException {
		return dao.findByEventAndUser(event, user);
	}

	public void deleteByEventIdAndUserId(Integer eventId, Integer userId) throws DataNotFoundException {
		dao.deleteByEventIdAndUserId(eventId, userId);
	}
}