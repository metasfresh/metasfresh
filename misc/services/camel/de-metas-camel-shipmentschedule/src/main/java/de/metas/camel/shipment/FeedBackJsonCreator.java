/*
 * #%L
 * de-metas-camel-shipmentschedule
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

package de.metas.camel.shipment;

import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.shipmentschedule.JsonRequestShipmentCandidateResults;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class FeedBackJsonCreator implements Processor
{
	private final Log log = LogFactory.getLog(FeedBackJsonCreator.class);

	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final var results = exchange.getIn().getHeader(JsonToXmlRouteBuilder.FEEDBACK_POJO, JsonRequestShipmentCandidateResults.class);
		if (results == null)
		{
			return; // nothing we can do
		}

		final var throwable = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
		if (throwable != null)
		{
			final StringWriter sw = new StringWriter();
			throwable.printStackTrace(new PrintWriter(sw));
			final String stackTrace = sw.toString();

			final JsonError error =JsonError.ofSingleItem(
			 JsonErrorItem.builder()
					.message(throwable.getMessage())
					.detail("Exception-Class=" + throwable.getClass().getName())
					.stackTrace(stackTrace)
					.build());
			exchange.getIn().setBody(results.withError(error));
		}
		else
		{
			exchange.getIn().setBody(results);
		}
	}
}
