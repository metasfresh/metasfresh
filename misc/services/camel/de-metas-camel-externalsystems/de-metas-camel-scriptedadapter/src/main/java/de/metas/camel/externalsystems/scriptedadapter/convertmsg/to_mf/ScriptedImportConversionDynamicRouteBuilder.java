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
import de.metas.camel.externalsystems.common.CamelRoutesGroup;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.CamelServiceRouteIdWithRequestType;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.ScriptedImportedConversionToMfRequest;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.processor.ScriptedImportConversionProcessor;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.EXCEPTION_PREFIX;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.FIELD_ERROR_MESSAGE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@RequiredArgsConstructor
public class ScriptedImportConversionDynamicRouteBuilder extends RouteBuilder
{
	private static final Logger logger = Logger.getLogger(ScriptedImportConversionDynamicRouteBuilder.class.getName());

	public static final String SCRIPTED_IMPORT_CONVERSION_PROCESSOR_ID = "ScriptedImportConversionProcessorId";

	@NonNull private final String endpointName;
	@NonNull private final String scriptIdentifier;
	@NonNull private final JavaScriptRepo javaScriptRepo;
	@NonNull private final JavaScriptExecutorService javaScriptExecutorService;
	@NonNull private final ProducerTemplate producerTemplate;

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from("direct:" + getRouteId())
				.routeId(getRouteId())
				.group(CamelRoutesGroup.START_ON_DEMAND.getCode())
				.process(new ScriptedImportConversionProcessor(javaScriptExecutorService, scriptIdentifier, javaScriptRepo)).id(SCRIPTED_IMPORT_CONVERSION_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to process for ${routeId}")
					.otherwise()
						.split(body(), new ResponseAggregationStrategy())
							.stopOnException()
							.process(this::handleItemInList)
						.end()
					.endChoice()
				.end();
		//@formatter:on
	}

	private void handleItemInList(@NonNull final Exchange exchange)
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
			logger.log(Level.WARNING, "Exception caught when handling request: " + request, e);
			exchange.getMessage().setBody(EXCEPTION_PREFIX + Optional.ofNullable(e.getCause())
					.map(Throwable::getMessage)
					.orElse(e.getMessage()));
		}
	}

	@NonNull
	private String resolveCamelEndpointUri(@NonNull final CamelServiceRouteIdWithRequestType camelRouteIdWithRequestType)
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

	@NonNull
	private String getRouteId()
	{
		return endpointName;
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
