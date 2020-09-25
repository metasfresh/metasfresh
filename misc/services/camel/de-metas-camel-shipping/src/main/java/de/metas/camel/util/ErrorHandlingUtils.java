package de.metas.camel.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import de.metas.camel.shipping.shipment.SiroShipmentConstants;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class ErrorHandlingUtils
{
	private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSSS");

	public static void setupWriteErrorLogFile(@NonNull final RouteBuilder routeBuilder, @NonNull final String toEndpoint)
	{
		routeBuilder.onException(Exception.class)
				.process(ErrorHandlingUtils::convertExceptionToFileContent)
				.log(LoggingLevel.WARN, "Processing failed: \n${body}")
				.to(toEndpoint);

	}

	public static void convertExceptionToFileContent(final Exchange exchange)
	{
		final String sourceFilename = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
		final String errorFilename = buildErrorFilename(sourceFilename);
		final String errorFileContent = buildErrorFileContent(exchange);

		exchange.getIn().setHeader(Exchange.FILE_NAME, errorFilename);
		exchange.getIn().setBody(errorFileContent);
	}

	private static String buildErrorFilename(@Nullable final String sourceFilename)
	{
		final String timestamp = TIMESTAMP_FORMATTER.format(ZonedDateTime.now());

		if (sourceFilename == null || sourceFilename.isBlank())
		{
			return "error-" + timestamp + ".txt";
		}

		final int pos = sourceFilename.lastIndexOf(".");
		if (pos > 0)
		{
			return sourceFilename.substring(0, pos) + "-" + timestamp + ".txt";
		}
		else
		{
			return sourceFilename + "-" + timestamp + ".txt";
		}
	}

	private static String buildErrorFileContent(@NonNull final Exchange exchange)
	{
		final StringBuilder content = new StringBuilder();

		content.append("Request: ").append(exchange.getIn().getBody(String.class));

		final var throwable = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
		if (throwable != null)
		{
			content.append("\nError type: ").append(throwable.getClass());
			content.append("\nError message:").append(throwable.getLocalizedMessage());

			if (throwable instanceof org.apache.camel.http.base.HttpOperationFailedException)
			{
				final var exception = (org.apache.camel.http.base.HttpOperationFailedException)throwable;
				content.append("\nHTTP Code: " + exception.getStatusCode());
				content.append("\nHTTP URI: " + exception.getUri());
				content.append("\nHTTP Response body: " + exception.getResponseBody());
				content.append("\nHTTP Response headers: " + exception.getResponseHeaders());
			}
		}
		else
		{
			content.append("\nError: Unknown error for " + exchange);
		}

		final Map<String, Object> headers = exchange.getIn().getHeaders();
		if (!headers.isEmpty())
		{
			content.append("\n\nHeaders:");
			for (final Entry<String, Object> e : headers.entrySet())
			{
				final String key = e.getKey();
				final Object value;
				if (SiroShipmentConstants.AUTHORIZATION.equals(key))
				{
					value = "<obfuscated>";
				}
				else
				{
					value = e.getValue();
				}

				content.append("\n\t" + key + ": " + value);
			}
		}

		if (throwable != null)
		{
			content.append("\n\nStacktrace: \n" + dumpStackTraceToString(throwable));
		}

		return content.toString();
	}

	private static String dumpStackTraceToString(final Throwable e)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

}
