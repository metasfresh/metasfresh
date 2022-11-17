/*
 * #%L
 * de-metas-camel-metasfresh
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

package de.metas.camel.externalsystems.metasfresh.restapi;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.metasfresh.ProcessingRoutes;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfig;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfigProvider;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.MassUpsertStatisticsCollector;
import de.metas.camel.externalsystems.metasfresh.restapi.model.JsonMassUpsertRequest;
import de.metas.camel.externalsystems.metasfresh.restapi.processor.CloseParserProcessor;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.InputStream;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.file.WorkFile.IN_PROGRESS_PREFIX;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.FILE_NAME_HEADER;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.MASS_JSON_REQUEST_PROCESSING_LOCATION;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.MASS_JSON_REQUEST_PROCESSING_LOCATION_DEFAULT;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.MASS_UPLOAD_STATISTICS_COLLECTOR_PROPERTY;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.file;

@Component
public class MassJsonRequestDispatcherRoute extends RouteBuilder
{
	public static final String MASS_JSON_REQUEST_ROUTE_ID = "metasfresh-readMassJsonRequest";
	public static final String PARSE_MASS_JSON_REQUEST_PROCESSOR_ID = "metasfresh-parseMassJsonRequestProcessorId";

	private static final String MASS_PROCESSING_TARGET_ROUTE = "TargetRoute";

	private final ObjectMapper jsonMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private final FeedbackConfigProvider feedbackConfigProvider;

	public MassJsonRequestDispatcherRoute(@NonNull final FeedbackConfigProvider feedbackConfigProvider)
	{
		this.feedbackConfigProvider = feedbackConfigProvider;
	}

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		CamelRouteUtil.setupProperties(getContext());

		final String massJsonRequestProcessingLocation = CamelRouteUtil.resolveProperty(getContext(),
																						MASS_JSON_REQUEST_PROCESSING_LOCATION,
																						MASS_JSON_REQUEST_PROCESSING_LOCATION_DEFAULT);
		//@formatter:off
		from(file(massJsonRequestProcessingLocation + "?moveFailed=.error&antExclude=" + IN_PROGRESS_PREFIX + "*"))
				.doTry()
					.routeId(MASS_JSON_REQUEST_ROUTE_ID).id(PARSE_MASS_JSON_REQUEST_PROCESSOR_ID)
					.process(this::parseMassJsonRequest)
					.toD("direct:${header." + MASS_PROCESSING_TARGET_ROUTE + "}", false)
				.endDoTry()
				.doFinally()
					.process(new CloseParserProcessor())
				.end();
		//@formatter:on
	}

	/**
	 * Expecting exchange body to be {@link JsonMassUpsertRequest}.
	 */
	private void parseMassJsonRequest(@NonNull final Exchange exchange)
	{
		final JsonParser parser = createParser(exchange);

		validateRootIsObject(parser);
		final String batchId = getBatchId(parser);
		final String itemType = getItemType(parser);

		exchange.getIn().setHeader(MASS_PROCESSING_TARGET_ROUTE, ProcessingRoutes.ofItemType(itemType).getTargetRoute());

		moveParserCursorInsideItemBody(parser);

		exchange.getIn().setBody(parser, JsonParser.class);

		registerMassUploadStatisticsCollector(exchange, batchId);
	}

	@NonNull
	private JsonParser createParser(@NonNull final Exchange exchange)
	{
		final InputStream is = exchange.getIn().getBody(InputStream.class);
		final JsonFactory factory = new JsonFactory();

		try
		{
			return factory.createParser(is);
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not create JsonParser!", exception);
		}
	}

	private void moveParserCursorInsideItemBody(@NonNull final JsonParser parser)
	{
		try
		{
			parser.nextToken();
			final String fieldName = parser.getCurrentName();
			if (!fieldName.equals(JsonMassUpsertRequest.FIELD_NAME_ITEM_BODY))
			{
				throw new RuntimeCamelException("Error parsing the request: next fieldName shall be " + JsonMassUpsertRequest.FIELD_NAME_ITEM_BODY + " but was: " + fieldName);
			}
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not parse the JSON!", exception);
		}
	}

	@NonNull
	private String getItemType(@NonNull final JsonParser parser)
	{
		try
		{
			parser.nextToken();
			final String fieldName = parser.getCurrentName();
			if (fieldName.equals(JsonMassUpsertRequest.FIELD_NAME_ITEM_TYPE))
			{
				parser.nextToken();

				return jsonMapper.readValue(parser, String.class);
			}
			else
			{
				throw new RuntimeCamelException("Error parsing the request: next fieldName shall be " + JsonMassUpsertRequest.FIELD_NAME_ITEM_TYPE + " but was: " + fieldName);
			}
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not parse the JSON!", exception);
		}
	}

	@NonNull
	private String getBatchId(@NonNull final JsonParser parser)
	{
		try
		{
			parser.nextToken();
			final String fieldName = parser.getCurrentName();
			if (fieldName.equals(JsonMassUpsertRequest.FIELD_NAME_BATCH_ID))
			{
				parser.nextToken();
				return jsonMapper.readValue(parser, String.class);
			}
			else
			{
				throw new RuntimeCamelException("Error parsing the request: next fieldName shall be " + JsonMassUpsertRequest.FIELD_NAME_BATCH_ID + " but was: " + fieldName);
			}
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not parse the JSON!", exception);
		}
	}

	private void registerMassUploadStatisticsCollector(
			@NonNull final Exchange exchange,
			@NonNull final String batchId)
	{
		final String externalSystemConfigValue = FilenameUtil.getExternalSystemConfigValue(exchange.getIn().getHeader(FILE_NAME_HEADER, String.class));
		final FeedbackConfig feedbackConfig = feedbackConfigProvider.getFeedbackConfig(externalSystemConfigValue).orElse(null);

		final MassUpsertStatisticsCollector massUpsertStatisticsCollector = MassUpsertStatisticsCollector.builder()
				.batchId(batchId)
				.feedbackConfig(feedbackConfig)
				.build();

		exchange.setProperty(MASS_UPLOAD_STATISTICS_COLLECTOR_PROPERTY, massUpsertStatisticsCollector);
	}

	private static void validateRootIsObject(@NonNull final JsonParser parser)
	{
		try
		{
			if (parser.nextToken() != JsonToken.START_OBJECT)
			{
				parser.close();
				throw new RuntimeCamelException("Error parsing the request: root should be object");
			}
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not parse the JSON!", exception);
		}
	}

}
