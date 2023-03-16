/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.common.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;
import org.apache.camel.http.base.HttpOperationFailedException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;

@UtilityClass
public class ErrorProcessor
{
	private static final Logger logger = Logger.getLogger(ErrorProcessor.class.getName());

	@NonNull
	public JsonErrorItem getErrorItem(@NonNull final Exchange exchange)
	{
		final JsonErrorItem.JsonErrorItemBuilder errorBuilder = JsonErrorItem
				.builder()
				.orgCode(exchange.getIn().getHeader(HEADER_ORG_CODE, String.class));

		final Exception exception = CoalesceUtil.coalesce(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class),
														  exchange.getIn().getHeader(Exchange.EXCEPTION_CAUGHT, Exception.class));
		if (exception == null)
		{
			errorBuilder.message("No error message available!");
			return errorBuilder.build();
		}

		return getJsonErrorItemFor(exception, errorBuilder);
	}

	@NonNull
	public JsonError processMetasfreshHttpError(@NonNull final Exchange exchange)
	{
		final Exception exception = CoalesceUtil.coalesce(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class),
														  exchange.getIn().getHeader(Exchange.EXCEPTION_CAUGHT, Exception.class));

		if (!(exception instanceof HttpOperationFailedException))
		{
			throw new RuntimeException("This is most probably a dev error as this route should only be called for HttpOperationFailedException!"
											   + " But this time was called with: " + (exception != null ? exception.getClass().getName() : null));
		}

		final HttpOperationFailedException httpError = (HttpOperationFailedException)exception;

		if (httpError.getResponseBody() == null)
		{
			logger.log(Level.WARNING, "*** processHttpErrorEncounteredResponse: HttpOperationFailedException.ResponseBody == null! Defaulting to stacktrace...");
			return JsonError.ofSingleItem(getErrorItem(exchange));
		}

		return getMetasfreshRemoteJsonError(httpError.getResponseBody(), exchange);
	}

	public void prepareJsonErrorRequest(@NonNull final Exchange exchange)
	{
		final String pInstanceId = exchange.getIn().getHeader(HEADER_PINSTANCE_ID, String.class);

		if (pInstanceId == null)
		{
			exchange.getIn().setBody(null);
			return;
		}

		exchange.getIn().setBody(JsonError.ofSingleItem(getErrorItem(exchange)));
	}

	@NonNull
	private JsonError getMetasfreshRemoteJsonError(@NonNull final String httpResponse, @NonNull final Exchange exchange)
	{
		Optional<JsonError> jsonError = getJsonErrorFromV2Response(httpResponse);

		if (jsonError.isEmpty())
		{
			jsonError = getJsonErrorFromV1Response(httpResponse);
		}

		if (jsonError.isPresent())
		{
			return jsonError.get().toBuilder()
					.error(getErrorItem(exchange))
					.build();
		}

		logger.log(Level.WARNING, "*** processHttpErrorEncounteredResponse: couldn't process HttpOperationFailedException.ResponseBody! Defaulting to stacktrace...");
		return JsonError.ofSingleItem(getErrorItem(exchange));
	}

	@NonNull
	private Optional<JsonError> getJsonErrorFromV2Response(@NonNull final String response)
	{
		try
		{
			final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

			final JsonApiResponse apiResponse = objectMapper
					.readValue(response, JsonApiResponse.class);

			final JsonError jsonError = objectMapper.convertValue(apiResponse.getEndpointResponse(), JsonError.class)
					.toBuilder()
					.requestId(apiResponse.getRequestId())
					.build();

			return Optional.of(jsonError);
		}
		catch (final JsonProcessingException e)
		{
			return Optional.empty();
		}
		catch (final IllegalArgumentException illegalArgumentException)
		{
			logger.log(Level.WARNING, "*** getJsonErrorFromV2Response: couldn't cast JsonApiResponse.endpointResponse to JsonError!"
					+ " Defaulting to stacktrace... see exception:" + illegalArgumentException.getMessage());

			return Optional.empty();
		}
	}

	@NonNull
	private Optional<JsonError> getJsonErrorFromV1Response(@NonNull final String response)
	{
		try
		{
			final JsonError jsonError = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue(response, JsonError.class);

			return Optional.of(jsonError);
		}
		catch (final JsonProcessingException e)
		{
			return Optional.empty();
		}
	}

	@NonNull
	private JsonErrorItem getJsonErrorItemFor(@NonNull final Exception exception, @NonNull final JsonErrorItem.JsonErrorItemBuilder errorBuilder)
	{
		errorBuilder.message(getErrorMessagePrefix(exception) + exception.getLocalizedMessage());
		errorBuilder.stackTrace(getStackTrace(exception));

		getSourceStackTraceElement(exception).ifPresent(sourceStackTraceElement -> {
			errorBuilder.sourceClassName(sourceStackTraceElement.getClassName());
			errorBuilder.sourceMethodName(sourceStackTraceElement.getMethodName());
		});

		return errorBuilder.build();
	}

	@NonNull
	private String getErrorMessagePrefix(@NonNull final Exception exception)
	{
		if (exception instanceof HttpOperationFailedException)
		{
			final HttpOperationFailedException httpError = (HttpOperationFailedException)exception;

			return Optional.ofNullable(httpError.getResponseBody())
					.map(responseBody -> responseBody + "\n ")
					.orElse("");
		}

		return "";
	}

	@NonNull
	private String getStackTrace(@NonNull final Exception exception)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);

		return sw.toString();
	}

	@NonNull
	private Optional<StackTraceElement> getSourceStackTraceElement(@NonNull final Exception exception)
	{
		return exception.getStackTrace() != null && exception.getStackTrace().length > 0
				? Optional.ofNullable(exception.getStackTrace()[0])
				: Optional.empty();
	}
}
