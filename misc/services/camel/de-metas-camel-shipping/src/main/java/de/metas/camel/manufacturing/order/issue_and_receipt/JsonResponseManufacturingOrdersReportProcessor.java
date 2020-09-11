package de.metas.camel.manufacturing.order.issue_and_receipt;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.metas.camel.shipping.receipt.ReceiptResponseProcessor;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;

/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

class JsonResponseManufacturingOrdersReportProcessor implements Processor
{
	private final Log log = LogFactory.getLog(ReceiptResponseProcessor.class);

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		// to be extended when we will send feedback to mf
		final JsonResponseManufacturingOrdersReport response = exchange.getIn().getBody(JsonResponseManufacturingOrdersReport.class);

		log.info("Successfully processed: " + response);
	}

}
