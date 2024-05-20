package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.CustomUser;
import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Chat;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.service.ChatService;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/chats")
public class ChatsController {
	@Autowired
	ChatService chatService;

	@Autowired
	EventService eventService;

	@Autowired
	UserService userService;

	@GetMapping(value = "talk/{eventId}")
	public String talk(@PathVariable Integer eventId, Chat chat, Model model, RedirectAttributes ra, @AuthenticationPrincipal CustomUser user) {
		List<Chat> chats = chatService.findAll();
		User loginUser = user.getUser();
		model.addAttribute("chats", chats);
		model.addAttribute("user", loginUser);
		return "chats/talk";
	}

	@PostMapping(value = "talk/{eventId}")
	public String send(@PathVariable Integer eventId, @Valid Chat chat, BindingResult result, Model model, RedirectAttributes ra, @AuthenticationPrincipal CustomUser user) {
		FlashData flash;
		try {
			User loginUser = user.getUser();
			chat.setUser(loginUser);
			if (result.hasErrors()) {
				List<Chat> chats = chatService.findAll();
				model.addAttribute("chats", chats);
				model.addAttribute("user", loginUser);
				return "chats/talk";
			}
			Event event = eventService.findById(eventId);
			chat.setEvent(event);
			
			chatService.save(chat);
			flash = new FlashData().success("コメントを投稿しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			flash = new FlashData().danger("エラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/chats/talk/" + eventId;
	}
}