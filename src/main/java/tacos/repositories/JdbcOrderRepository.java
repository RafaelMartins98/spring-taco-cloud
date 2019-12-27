package tacos.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.entities.Order;
import tacos.entities.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
		this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Order save(Order order) {

		order.setPlacedAt(new Date());
		order.setId(saveOrderDetails(order));

		order.getTacos().forEach(taco -> saveTacoOrder(taco, order.getId()));
		

		return order;
	}

	@SuppressWarnings("unchecked")
	private long saveOrderDetails(Order order) {
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);

		values.put("placedAt", order.getPlacedAt());

		return orderInserter.executeAndReturnKey(values).longValue();

	}

	private void saveTacoOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<>();

		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());

		orderTacoInserter.execute(values);
	}

}
