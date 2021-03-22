package de.metas.camel.ebay.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import de.metas.camel.externalsystems.ebay.api.model.Order;

public class CreateMetasModelUpsertReqProcessor implements Processor {

	@Override
	public void process(final Exchange exchange)
	{
		final Order order = exchange.getIn().getBody(Order.class);

		//TBD.
		
		
	}
}
