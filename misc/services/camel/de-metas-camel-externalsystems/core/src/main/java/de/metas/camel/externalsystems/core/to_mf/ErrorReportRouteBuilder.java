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

import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.rest_api.v1.JsonError;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_EXTERNAL_SYSTEM_URI;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ErrorReportRouteBuilder extends RouteBuilder
{
	private final DateTimeFormatter FILE_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSSS");

	public final static String ERROR_WRITE_TO_FILE = "Error-Route-writeToFile";
	public final static String ERROR_WRITE_TO_ADISSUE = "Error-Route-writeToAdIssue";

	@Override
	public void configure()
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.log("Exception thrown inside the error reporting route! ");

		from(direct(MF_ERROR_ROUTE_ID))
				.routeId(MF_ERROR_ROUTE_ID)
				.streamCaching()
				.multicast()
					.parallelProcessing(true)
					.to(direct(ERROR_WRITE_TO_FILE), direct(ERROR_WRITE_TO_ADISSUE))
				.end();

		from(direct(ERROR_WRITE_TO_FILE))
				.routeId(ERROR_WRITE_TO_FILE)
				.log("Route invoked")
				.process(this::prepareErrorFile)
				.to("{{metasfresh.error-report.folder}}");

		from(direct(ERROR_WRITE_TO_ADISSUE))
				.routeId(ERROR_WRITE_TO_ADISSUE)
				.log("Route invoked")
				.process(this::prepareJsonErrorRequest)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonError.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_EXTERNAL_SYSTEM_URI + "}}/${header." + HEADER_PINSTANCE_ID + "}/externalstatus/error");
		//@formatter:on
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
			content.append(" Error Stacktrace: ").append(sw);
		}

		exchange.getIn().setBody(content.toString());
		exchange.getIn().setHeader(Exchange.FILE_NAME, FILE_TIMESTAMP_FORMATTER.format(ZonedDateTime.now()) + "_error.txt");
	}

	private void prepareJsonErrorRequest(@NonNull final Exchange exchange)
	{
		final String pInstanceId = exchange.getIn().getHeader(HEADER_PINSTANCE_ID, String.class);

		if (pInstanceId == null)
		{
			throw new RuntimeException("No PInstanceId available!");
		}

		final JsonErrorItem.JsonErrorItemBuilder errorBuilder = JsonErrorItem
				.builder()
				.orgCode(exchange.getIn().getHeader(HEADER_ORG_CODE, String.class));

		final Exception exception = CoalesceUtil.coalesce(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class),
														  exchange.getIn().getHeader(Exchange.EXCEPTION_CAUGHT, Exception.class));
		if (exception == null)
		{
			errorBuilder.message("No error message available!");
		}
		else
		{
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);

			errorBuilder.message(exception.getLocalizedMessage());
			errorBuilder.stackTrace(sw.toString());

			final Optional<StackTraceElement> sourceStackTraceElem = exception.getStackTrace() != null
					? Optional.ofNullable(exception.getStackTrace()[0])
					: Optional.empty();

			sourceStackTraceElem.ifPresent(stackTraceElement -> {
				errorBuilder.sourceClassName(sourceStackTraceElem.get().getClassName());
				errorBuilder.sourceMethodName(sourceStackTraceElem.get().getMethodName());
			});
		}
		exchange.getIn().setBody(JsonError.ofSingleItem(errorBuilder.build()));
	}
}
