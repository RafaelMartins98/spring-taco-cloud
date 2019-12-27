package tacos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.entities.Order;
import tacos.repositories.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderContoller {

	private OrderRepository orderRepository;

	@Autowired
	public OrderContoller(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors erros, SessionStatus sessionStatus) {

		if (erros.hasErrors()) {
			return "orderForm";
		}
		
		orderRepository.save(order);
		sessionStatus.setComplete();

		log.info(orderForm().toString() + " has been successfully saved!");
		
		return "redirect:/";
	}

}
