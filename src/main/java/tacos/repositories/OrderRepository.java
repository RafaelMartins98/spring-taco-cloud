package tacos.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tacos.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findByDeliveryZip(String deliveryZip);
	
	List<Order> readByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
	
	
}
