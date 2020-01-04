package tacos.web.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Order;
import tacos.data.OrderRepository;

@RestController
@RequestMapping(path = "/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderApiController {

	private OrderRepository orderRepository;

	public OrderApiController(OrderRepository repo) {
		this.orderRepository = repo;
	}

	@GetMapping(produces = "application/json")
	public Iterable<Order> allOrders() {
		return orderRepository.findAll();
	}
	
	@GetMapping("{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Order> findOrderById(@PathVariable("orderId") Long orderId) {
		
		Optional<Order> order = orderRepository.findById(orderId);
		
		if(order.isPresent()) {
			return new ResponseEntity<>(order.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes = "application/json")
	@PutMapping("{orderId}")
	@ResponseStatus(HttpStatus.CREATED)
	public Order saveFullOrder(@RequestBody Order order) {
		return orderRepository.save(order);
	}

	@PatchMapping(path = "/{orderId}", consumes = "application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {

		Order order = orderRepository.findById(orderId).get();

		// Simple example, it needs some refactoring

		if (patch.getDeliveryName() != null) {
			order.setDeliveryName(patch.getDeliveryName());
		}
		if (patch.getDeliveryStreet() != null) {
			order.setDeliveryStreet(patch.getDeliveryStreet());
		}
		if (patch.getDeliveryCity() != null) {
			order.setDeliveryCity(patch.getDeliveryCity());
		}
		if (patch.getDeliveryState() != null) {
			order.setDeliveryState(patch.getDeliveryState());
		}
		if (patch.getDeliveryZip() != null) {
			order.setDeliveryZip(patch.getDeliveryState());
		}
		if (patch.getCcNumber() != null) {
			order.setCcNumber(patch.getCcNumber());
		}
		if (patch.getCcExpiration() != null) {
			order.setCcExpiration(patch.getCcExpiration());
		}
		if (patch.getCcCVV() != null) {
			order.setCcCVV(patch.getCcCVV());
		}

		return orderRepository.save(order);
	}

	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") Long orderId) {
		orderRepository.deleteById(orderId);
	}

}
