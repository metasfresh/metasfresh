/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.CamelServiceRouteIdWithRequestType;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.ScriptedImportedConversionToMfRequest;
import de.metas.common.util.Check;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.EXCEPTION_PREFIX;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.FIELD_ERROR_MESSAGE;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD;

/**
 * Shared archiving + dispatch behaviour for the two ScriptedImportConversion transports (SFTP polling
 * and REST POST): split the transform's response into items, dispatch each item to the resolved Camel
 * endpoint, and archive the original payload to a LOCAL, transport-agnostic processed/error folder — see
 * {@code ExternalSystem_Endpoint.ProcessedDirectory}/{@code ErrorDirectory}.
 * <p>
 * The one behavioural difference between transports is the archive file name: a subclass derives it via
 * {@link #archiveFileName(Exchange)} (the real remote file name for SFTP, a synthesized name for REST,
 * which has no remote file).
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
abstract class AbstractScriptedImportConversionArchivingRouteBuilder extends RouteBuilder
{
	@NonNull protected final String endpointName;
	@NonNull protected final String scriptIdentifier;
	@NonNull protected final JavaScriptRepo javaScriptRepo;
	@NonNull protected final JavaScriptExecutorService javaScriptExecutorService;
	@NonNull protected final ProducerTemplate producerTemplate;

	/** LOCAL, transport-agnostic archive folder for the payload on success. Never a remote path. */
	@NonNull protected final String processedDir;
	/** LOCAL, transport-agnostic archive folder for the payload on failure. Never a remote path. */
	@NonNull protected final String errorDir;

	/** Derives the archive file name for {@code exchange} — the one difference between transports. */
	protected abstract String archiveFileName(@NonNull Exchange exchange);

	protected void archiveLocallyOnSuccess(@NonNull final Exchange exchange)
	{
		archiveLocally(exchange, processedDir);
	}

	protected void archiveLocallyOnError(@NonNull final Exchange exchange)
	{
		archiveLocally(exchange, errorDir);
	}

	private void archiveLocally(@NonNull final Exchange exchange, @NonNull final String directory)
	{
		final String payload = exchange.getProperty(PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD, String.class);
		if (payload == null)
		{
			// nothing was ever read from the source (failure occurred before the body was captured)
			return;
		}

		ScriptedImportConversionLocalArchiver.archive(directory, archiveFileName(exchange), payload);
	}

	protected void handleItemInList(@NonNull final Exchange exchange)
	{
		final ScriptedImportedConversionToMfRequest request = exchange.getIn().getBody(ScriptedImportedConversionToMfRequest.class);

		try
		{
			final CamelServiceRouteIdWithRequestType camelRouteIdWithRequestType = CamelServiceRouteIdWithRequestType.ofRouteId(request.getCamelServiceRouteID());
			final Object payload = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue(request.getRequestBody(), camelRouteIdWithRequestType.getRequestType());

			final String response = producerTemplate.requestBody(resolveCamelEndpointUri(camelRouteIdWithRequestType), payload, String.class);
			exchange.getMessage().setBody(response);
		}
		catch (final Exception e)
		{
			log.warn("Exception caught when handling request: {}", request, e);
			exchange.getMessage().setBody(getErrorMessage(e));
		}
	}

	@NonNull
	protected String getErrorMessage(@NonNull final Exception e)
	{
		return Optional.ofNullable(e.getCause())
				.map(root -> {
					if (root instanceof HttpOperationFailedException httpOperationFailedException)
					{
						return httpOperationFailedException.getResponseBody();
					}
					return EXCEPTION_PREFIX + root.getMessage();
				})
				.orElse(EXCEPTION_PREFIX + e.getMessage());
	}

	@NonNull
	protected String resolveCamelEndpointUri(@NonNull final CamelServiceRouteIdWithRequestType camelRouteIdWithRequestType)
	{
		if (camelRouteIdWithRequestType.isProperty())
		{
			return Optional.ofNullable(getCamelContext().resolvePropertyPlaceholders("{{" + camelRouteIdWithRequestType.getRouteId() + "}}"))
					.orElseThrow(() -> new RuntimeCamelException("Missing property: " + camelRouteIdWithRequestType.getRouteId()));
		}
		else
		{
			return "direct:" + camelRouteIdWithRequestType.getRouteId();
		}
	}

	/**
	 * Aggregates each split item’s response into a single List<Object>.
	 */
	@VisibleForTesting
	public static class ResponseAggregationStrategy implements AggregationStrategy
	{
		private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		@Override
		@NonNull
		public Exchange aggregate(@Nullable final Exchange oldExchange,
								  @NonNull final Exchange newExchange)
		{
			if (oldExchange == null)
			{
				final List<Object> list = new ArrayList<>();
				getJsonObject(newExchange).ifPresent(list::add);
				newExchange.getMessage().setBody(list);
				return newExchange;
			}
			else
			{
				@SuppressWarnings("unchecked") final List<Object> list = oldExchange.getIn().getBody(List.class);
				getJsonObject(newExchange).ifPresent(list::add);
				oldExchange.getMessage().setBody(list);
				return oldExchange;
			}
		}

		@NonNull
		private Optional<Object> getJsonObject(@NonNull final Exchange newExchange)
		{
			final String responseStr = newExchange.getIn().getBody(String.class);

			if (Check.isEmpty(responseStr))
			{
				return Optional.empty();
			}

			if (responseStr.startsWith(EXCEPTION_PREFIX))
			{
				return Optional.of(responseStr);
			}

			try
			{
				return Optional.of(mapper.readValue(responseStr, Object.class));
			}
			catch (final JsonProcessingException e)
			{
				return Optional.of(Map.of(FIELD_ERROR_MESSAGE, e.getMessage()));
			}
		}
	}
}
