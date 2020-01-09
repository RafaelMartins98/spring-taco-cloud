package tacos.kitchen.messaging.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

@Component
public class JmsOrderReceiver implements OrderReceiver {

	private JmsTemplate jmsTemplate;;

	@Autowired
	public JmsOrderReceiver(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public Order receiveOrder() {
		//Pull Method, in order words, synchronous listening
		return (Order) jmsTemplate.receiveAndConvert();
	}

}
