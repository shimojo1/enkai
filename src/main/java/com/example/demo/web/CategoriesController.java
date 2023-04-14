package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.entity.Category;
import com.example.demo.service.BaseService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/categories")
public class CategoriesController {
	@Autowired
	BaseService<Category> categoryService;

	/*
	 * 一覧表示
	 */
	@GetMapping(path = {"", "/"})
	public String list(Model model) {
		// 全件取得
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		return "categories/list";
	}

	/*
	 * 新規作成画面表示
	 */
	@GetMapping(value = "/create")
	public String form(Category category, Model model) {
		model.addAttribute("category", category);
		return "categories/create";
	}

	/*
	 * 新規登録
	 */
	@PostMapping(value = "/create")
	public String register(@Valid Category category, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "categories/create";
			}
			// 新規登録
			categoryService.save(category);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/categories";
	}
	
	/*
	 * 編集画面表示
	 */
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			// 存在確認
			Category category = categoryService.findById(id);
			model.addAttribute("category", category);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/categories";
		}
		return "categories/edit";
	}

	/*
	 * 更新
	 */
	@PostMapping(value = "/edit/{id}")
	public String update(@PathVariable Integer id, @Valid Category category, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "categories/edit";
			}
			categoryService.findById(id);
			// 更新
			categoryService.save(category);
			flash = new FlashData().success("更新しました");
		} catch (Exception e) {
			flash = new FlashData().danger("該当データがありません");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/categories";
	}
}