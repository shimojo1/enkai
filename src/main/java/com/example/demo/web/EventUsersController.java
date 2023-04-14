package com.example.demo.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.MaxCapacityReachedException;
import com.example.demo.entity.Event;
import com.example.demo.entity.EventUser;
import com.example.demo.entity.User;
import com.example.demo.service.EventService;
import com.example.demo.service.EventUserService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admin/eventusers")
public class EventUsersController {
	@Autowired
	EventUserService eventUserService;

	@Autowired
	EventService eventService;

	@Autowired
	UserService userService;

	@GetMapping(value = "/create/{eventId}")
	public String register(@PathVariable Integer eventId, Model model, RedirectAttributes ra,  @AuthenticationPrincipal UserDetails user) {
		FlashData flash;
		try {
			Event event = eventService.findById(eventId);
			List<EventUser> eventUsers = eventUserService.findByEvent(event);
			Integer participants = eventUsers.size();
			if (participants >= event.getMaxParticipant()) {
				throw new MaxCapacityReachedException();
			}
			User loginUser = userService.findByEmail(user.getUsername());
			EventUser newEventUser = new EventUser();
			newEventUser.setEvent(event);
			newEventUser.setUser(loginUser);
			eventUserService.save(newEventUser);			
			flash = new FlashData().success("イベントに参加しました");
		} catch (MaxCapacityReachedException e) {
			flash = new FlashData().danger("最大参加者数を超えたため参加できませんでした");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			flash = new FlashData().danger("エラーが発生しました");
		}				
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/events/view/" + eventId;
	}

	@GetMapping(value = "/delete/{eventId}")
	public String delete(@PathVariable Integer eventId, Model model, RedirectAttributes ra, @AuthenticationPrincipal UserDetails user) {
		FlashData flash;
		try {
			User loginUser = userService.findByEmail(user.getUsername());
			Integer userId = loginUser.getId();
			eventUserService.deleteByEventIdAndUserId(eventId, userId);		
			flash = new FlashData().success("イベントを辞退しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flash = new FlashData().danger("エラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/events/view/" + eventId;
	}
}
