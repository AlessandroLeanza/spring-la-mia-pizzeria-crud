package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Pizza;
import com.example.demo.repository.PizzaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaRepository repository;
	
	@GetMapping
	public String index(Model model) {
		List <Pizza> pizzeList = repository.findAll();
		model.addAttribute("list", pizzeList);
		return "/Pizza/index";
	}
	
	@GetMapping ("/create")
	public String create(Model model) {
		model.addAttribute("form", new Pizza());
		return "/Pizza/create";
	}
	
	@PostMapping("/create")
    public String store(@Valid @ModelAttribute("form") Pizza pizzaForm, 
    		BindingResult bindingresult, Model model) {
        
        if (bindingresult.hasErrors()) {
            return "/Pizza/create";
        }

        repository.save(pizzaForm);

        return "redirect:/pizza";

    }
	
	@GetMapping ("/show/{id}")
	public String show(@PathVariable ("id")Integer id, Model model) {
		model.addAttribute("show", repository.getReferenceById(id));
		return "Pizza/show";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("pizza", repository.getReferenceById(id));
		
		return "/pizza/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(
			@Valid @ModelAttribute("pizza") Pizza pizza,
			BindingResult bindingResult,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/pizza/edit";
		}
		
		repository.save(pizza);
		
		return "redirect:/pizza";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		
		repository.deleteById(id);
		
		return "redirect:/pizza";
	}
}
