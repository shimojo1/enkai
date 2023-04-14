package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Chat;
import com.example.demo.repository.ChatRepository;

@Repository
public class ChatDao implements BaseDao<Chat> {
	@Autowired
	ChatRepository repository;

	@Override
	public List<Chat> findAll() {
		return repository.findAll();
	}

	@Override
	public Chat findById(Integer id) throws DataNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Chat chat) {
		this.repository.save(chat);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Chat chat = this.findById(id);
			this.repository.deleteById(chat.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
}