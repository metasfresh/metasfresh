package de.metas.camel.ebay.processor;


import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.ebay.processor.ProcessorHelper.getPropertyOrThrowError;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.camel.ebay.EbayImportOrdersRouteContext;
import de.metas.camel.externalsystems.ebay.api.model.Order;

public class OrderFilterProcessor implements Processor {
	
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Filter order by state !!! NOT IMPLEMENTED!!!");
		
		final EbayImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, EbayImportOrdersRouteContext.class);
		
		final Order order = exchange.getIn().getBody(Order.class);
		if (order == null)
		{
			throw new RuntimeException("Empty body!");
		}
		
		log.debug("Checking order {} for further steps", order.getOrderId());
		
		importOrdersRouteContext.setOrder(order);
		
		boolean filtered = false; 
		
		if(!filtered) {
			
			importOrdersRouteContext.setOrder(order);
			exchange.getIn().setBody(order);
			
		} else {
			//order was filtered
			exchange.getIn().setBody(null);
		}
		
	}

}
