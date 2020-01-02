package tacos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.entities.Order;
import tacos.entities.User;
import tacos.properties.holders.OrderProps;
import tacos.repositories.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@ConfigurationProperties(prefix = "taco.orders")
public class OrderContoller {

	private OrderRepository orderRepository;

	private OrderProps orderProps;

	@Autowired
	public OrderContoller(OrderRepository orderRepository, OrderProps orderProps) {
		this.orderRepository = orderRepository;
		this.orderProps = orderProps;
	}

	@GetMapping("/current")
	public String orderForm() {
		return "orders/orderForm";
	}

	@GetMapping
	public String orderForUser(@AuthenticationPrincipal User user, Model model) {
		
		model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, PageRequest.of(0, orderProps.getPageSize())));

		return "orders/orderList";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors erros, SessionStatus sessionStatus,
			@AuthenticationPrincipal User user) {

		if (erros.hasErrors()) {
			return "orders/orderForm";
		}

		order.setUser(user);
		orderRepository.save(order);
		sessionStatus.setComplete();

		log.info(orderForm().toString() + " has been successfully saved!");

		return "redirect:/";
	}

}
