/*
 * #%L
 * de-metas-camel-ebay-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.ebay.processor.order;

import static de.metas.camel.externalsystems.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.ebay.ProcessorHelper.getPropertyOrThrowError;

import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.camel.externalsystems.common.DateAndImportStatus;
import de.metas.camel.externalsystems.ebay.EbayConstants.OrderFulfillmentStatus;
import de.metas.camel.externalsystems.ebay.EbayImportOrdersRouteContext;
import de.metas.camel.externalsystems.ebay.EbayUtils;
import de.metas.camel.externalsystems.ebay.api.model.Order;

public class OrderFilterProcessor implements Processor
{

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(Exchange exchange) throws Exception
	{
		log.info("Filter order by state");

		final EbayImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, EbayImportOrdersRouteContext.class);

		final Order order = exchange.getIn().getBody(Order.class);
		if (order == null)
		{
			throw new RuntimeException("Empty body!");
		}

		log.debug("Checking order {} for further steps", order.getOrderId());
		

		//only import new orders.
		if( OrderFulfillmentStatus.NOT_STARTED.name().equalsIgnoreCase(order.getOrderFulfillmentStatus()) ) {
			
			importOrdersRouteContext.setOrder(order);
			exchange.getIn().setBody(order);
			
			//remember order TS for future calls.
			LocalDate created = order.getCreationDate() != null ? EbayUtils.toLocalDate(order.getCreationDate()) : null;
			if(created != null) {
				importOrdersRouteContext.setNextImportStartingTimestamp(DateAndImportStatus.of(true, created.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			}
		}
		else
		{
			// order was filtered
			exchange.getIn().setBody(null);
			
			//remember order TS for future calls.
			LocalDate created = order.getCreationDate() != null ? EbayUtils.toLocalDate(order.getCreationDate()) : null;
			if(created != null) {
				importOrdersRouteContext.setNextImportStartingTimestamp(DateAndImportStatus.of(true, created.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			}
		}

	}

}
