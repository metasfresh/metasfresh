/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.to_mf;

import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ErrorReportRouteBuilder extends RouteBuilder
{
	public final String ERROR_ROUTE_ID = "Error-Route";
	private final DateTimeFormatter FILE_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSSS");

	@Override
	public void configure()
	{
		from(StaticEndpointBuilders.direct(ERROR_ROUTE_ID)) //FIXME: temporary
				.routeId(ERROR_ROUTE_ID)
				.process(this::prepareErrorFile)
				.to("{{metasfresh.error-report.folder}}"); // TODO later: add AD_Issue creating Metasfresh-REST-EP
	}

	private void prepareErrorFile(@NonNull final Exchange exchange)
	{
		final StringBuilder content = new StringBuilder();

		content.append(" Exchange body when error occurred: ")
				.append(exchange.getIn().getBody(String.class))
				.append("\n");

		final Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		if (exception == null)
		{
			content.append(" No info available!");
		}
		else
		{
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);

			content.append(" Error Message: ").append(exception.getLocalizedMessage()).append("\n");
			content.append(" Error Stacktrace: ").append(sw.toString());
		}

		exchange.getIn().setBody(content.toString());
		exchange.getIn().setHeader(Exchange.FILE_NAME, FILE_TIMESTAMP_FORMATTER.format(ZonedDateTime.now()) + "_error.txt");
	}
}
