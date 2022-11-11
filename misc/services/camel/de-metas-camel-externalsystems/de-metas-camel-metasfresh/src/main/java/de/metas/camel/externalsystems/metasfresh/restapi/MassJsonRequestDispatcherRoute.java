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
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.metasfresh.ProcessingRoutes;
import de.metas.camel.externalsystems.metasfresh.bpartner.UpsertBPartnersRouteContext;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfig;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfigProvider;
import de.metas.camel.externalsystems.metasfresh.restapi.model.JsonMassUpsertRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.FILE_NAME_HEADER;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.MASS_PROCESSING_TARGET_ROUTE;
import static de.metas.camel.externalsystems.metasfresh.bpartner.FileBPartnersRouteBuilder.ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.file;

@Component
public class MassJsonRequestDispatcherRoute extends RouteBuilder
{
	public static final String MASS_JSON_REQUEST_ROUTE_ID = "Metasfresh-readMassJsonRequest";
	public static final String PARSE_MASS_JSON_REQUEST_PROCESSOR_ID = "Metasfresh-parseMassJsonRequestProcessorId";

	private final ObjectMapper jsonMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private final FeedbackConfigProvider feedbackConfigProvider;

	public MassJsonRequestDispatcherRoute(@NonNull final FeedbackConfigProvider feedbackConfigProvider)
	{
		this.feedbackConfigProvider = feedbackConfigProvider;
	}

	@Override
	public void configure() throws Exception
	{
		from(file("${metasfresh.mass.json.request.directory.path}"))
				.routeId(MASS_JSON_REQUEST_ROUTE_ID).id(PARSE_MASS_JSON_REQUEST_PROCESSOR_ID)
				.process(this::parseMassJsonRequest)
				.toD("direct:${header." + MASS_PROCESSING_TARGET_ROUTE + "}", false);
	}

	/**
	 *  Expecting exchange body to be {@link JsonMassUpsertRequest}.
	 */
	private void parseMassJsonRequest(@NonNull final Exchange exchange)
	{
		final JsonParser parser = createParser(exchange);

		validateRootIsObject(parser);
		final String batchId = getBatchId(parser);
		final UpsertBPartnersRouteContext upsertBPartnersRouteContext = buildUpsertBPartnersRouteContext(exchange, batchId);
		exchange.setProperty(ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT, upsertBPartnersRouteContext);

		final String itemType = getItemType(parser);
		exchange.getIn().setHeader(MASS_PROCESSING_TARGET_ROUTE, ProcessingRoutes.ofItemType(itemType).getTargetRoute());

		moveParserCursorInsideItemBody(parser);
		exchange.getIn().setBody(parser, JsonParser.class);
	}

	private void moveParserCursorInsideItemBody(@NonNull final JsonParser parser)
	{
		try
		{
			parser.nextToken();
			final String fieldName = parser.getCurrentName();
			if (!fieldName.equals(JsonMassUpsertRequest.FIELD_NAME_ITEM_BODY))
			{
				throw new RuntimeCamelException("Error parsing the request: next fieldName shall be " + JsonMassUpsertRequest.FIELD_NAME_ITEM_BODY);
			}
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not parse the JSON!" + exception.getMessage());
		}
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
			throw new RuntimeCamelException("Could not create JsonParser!" + exception.getMessage());
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
				throw new RuntimeCamelException("Error parsing the request: next fieldName shall be " + JsonMassUpsertRequest.FIELD_NAME_ITEM_TYPE);
			}
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not parse the JSON!" + exception.getMessage());
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
				throw new RuntimeCamelException("Error parsing the request: next fieldName shall be " + JsonMassUpsertRequest.FIELD_NAME_BATCH_ID);
			}
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not parse the JSON!" + exception.getMessage());
		}
	}

	@NonNull
	private UpsertBPartnersRouteContext buildUpsertBPartnersRouteContext(
			@NonNull final Exchange exchange,
			@NonNull final String batchId)
	{
		final String externalSystemConfigValue = exchange.getIn().getHeader(FILE_NAME_HEADER, String.class);
		final Optional<FeedbackConfig> feedbackConfig = feedbackConfigProvider.getFeedbackConfig(externalSystemConfigValue);

		return UpsertBPartnersRouteContext.builder()
				.batchId(batchId)
				.errorsCollector(ImmutableList.builder())
				.externalSystemConfigValue(externalSystemConfigValue)
				.orgCode(orgCodeProvider.getOrgCode(externalSystemConfigValue))
				.feedbackConfig(feedbackConfig.orElse(null))
				.build();
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
			throw new RuntimeCamelException("Could not parse the JSON!" + exception.getMessage());
		}
	}
	
}
