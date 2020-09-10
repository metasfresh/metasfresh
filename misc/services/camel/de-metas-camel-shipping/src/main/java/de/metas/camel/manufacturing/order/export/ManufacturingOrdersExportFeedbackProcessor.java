package de.metas.camel.manufacturing.order.export;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.metas.camel.shipping.CommonUtil;
import de.metas.common.manufacturing.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.rest_api.JsonError;
import lombok.NonNull;

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

class ManufacturingOrdersExportFeedbackProcessor implements Processor
{
	static final String FEEDBACK_POJO = "JsonRequestSetOrdersExportStatusBulk";

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void process(@NonNull final Exchange exchange)
	{
		final var results = exchange.getIn().getHeader(FEEDBACK_POJO, JsonRequestSetOrdersExportStatusBulk.class);
		if (results == null)
		{
			return; // nothing we can do
		}

		final var throwable = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
		if (throwable != null)
		{
			log.debug("Add throwable from exchange to the JSON-feedback to metasfresh:", throwable);
			final JsonError error = CommonUtil.createJsonError(throwable);
			exchange.getIn().setBody(results.withError(error));
		}
		else
		{
			exchange.getIn().setBody(results);
		}
	}
}
