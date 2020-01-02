package tacos.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.entities.Order;
import tacos.entities.User;

public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findByDeliveryZip(String deliveryZip);

	List<Order> readByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

	List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
