package tacos.messaging;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import tacos.Order;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

	private JmsTemplate jmsTemplate;

	@Autowired
	public JmsOrderMessagingService(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void sendOrder(Order order) {

		jmsTemplate.convertAndSend(order, this::getMessagePostProcessor);

		// or
		// jmsTemplate.send(session -> session.createObjectMessage(order));

	}

	private Message getMessagePostProcessor(Message message) throws JMSException {
		message.setStringProperty("X_ORDER_SOURCE", "WEB");
		return message;
	}

}
