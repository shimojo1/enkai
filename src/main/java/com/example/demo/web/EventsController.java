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
import com.example.demo.entity.Event;
import com.example.demo.entity.EventUser;
import com.example.demo.entity.User;
import com.example.demo.service.EventService;
import com.example.demo.service.EventUserService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class EventsController {
	@Autowired
	EventService eventService;

	@Autowired
	EventUserService eventUserService;

	@Autowired
	UserService userService;

	@GetMapping(path = {"/", ""})
	public String list(Model model, @AuthenticationPrincipal CustomUser user) {
		if (user != null) {
			return "redirect:/admin";
		}
		// 全件取得
		List<Event> events = eventService.findAll();
		model.addAttribute("events", events);
		return "events/list";
	}

	@GetMapping(value = "/admin")
	public String adminList(Event event, Model model) {
		// 全件取得
		List<Event> events = eventService.findAll();
		model.addAttribute("events", events);
		return "events/list";
	}
	
	@GetMapping(value = "/admin/events/mylist")
	public String mylist(Model model, @AuthenticationPrincipal CustomUser user) {
		List<Event> events = null;
		User loginUser = user.getUser();
		events = eventService.findByUserId(loginUser.getId());
		model.addAttribute("events", events);
		return "events/mylist";
	}

	@GetMapping(value = "/admin/events/create")
	public String create(Event event, Model model) {
		model.addAttribute("event", event);
		return "events/create";
	}

	/*
	 * 新規登録
	 */
	@PostMapping(value = "/admin/events/create")
	public String register(@Valid Event event, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "events/create";
			}
			// 新規登録
			eventService.save(event);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin";
	}

	/*
	 * 編集画面表示
	 */
	@GetMapping(value = "/admin/events/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			// 存在確認
			Event event = eventService.findById(id);
			model.addAttribute("event", event);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/events/mylist";
		}
		return "events/edit";
	}

	/*
	 * 更新
	 */
	@PostMapping(value = "/admin/events/edit/{id}")
	public String update(@PathVariable Integer id, @Valid Event event, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "events/edit";
			}
			eventService.findById(id);
			// 更新
			eventService.save(event);
			flash = new FlashData().success("更新しました");
		} catch (Exception e) {
			flash = new FlashData().danger("該当データがありません");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/events/mylist";
	}

	@GetMapping(value = "/events/view/{id}")
	public String view(@PathVariable Integer id, Model model, RedirectAttributes ra, @AuthenticationPrincipal CustomUser user) {
		if (user != null) {
			return "redirect:/admin/events/view/" + id;
		}
		try {
			Event event = eventService.findById(id);
			model.addAttribute("event", event);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/";
		}
		return "events/view";
	}
	
	@GetMapping(value = "/admin/events/view/{id}")
	public String adminView(@PathVariable Integer id, Model model, RedirectAttributes ra, @AuthenticationPrincipal CustomUser user) {
		try {
			Event event = eventService.findById(id);
			model.addAttribute("event", event);
			User loginUser = user.getUser();
			EventUser eventUser = eventUserService.findByEventAndUser(event, loginUser);
			Boolean isParticipated = eventUser != null;
			model.addAttribute("isParticipated", isParticipated);
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/";
		}
		return "events/view";
	}

	@GetMapping("/admin/events/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) {
		FlashData flash;
		try {
			eventService.findById(id);
			eventService.deleteById(id);
			flash = new FlashData().success("イベントの削除が完了しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/events/mylist";
	}
}