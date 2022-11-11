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

package de.metas.camel.externalsystems.metasfresh.bpartner.processor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.metasfresh.bpartner.UpsertBPartnersRouteContext;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.IOException;
import java.util.List;

import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.IS_CONTINUE_PARSING_PROPERTY;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.PARSER_PROPERTY;
import static de.metas.camel.externalsystems.metasfresh.bpartner.FileBPartnersRouteBuilder.ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT;

public class ParseBPartnersProcessor implements Processor
{
	private static final int MAX_BATCH_SIZE = 10;

	@Override
	public void process(final Exchange exchange) throws IOException
	{
		final JsonParser parser = exchange.getProperty(PARSER_PROPERTY, JsonParser.class);

		boolean isContinueParsing = parser.nextToken() != JsonToken.END_ARRAY;

		if (!isContinueParsing)
		{
			exchange.setProperty(IS_CONTINUE_PARSING_PROPERTY, false);
			parser.close();
			return;
		}

		final ImmutableList.Builder<JsonRequestBPartnerUpsertItem> jsonRequestBPartnerUpsertItems = ImmutableList.builder();

		for (int counter = 0; counter < MAX_BATCH_SIZE; counter++)
		{
			final JsonRequestBPartnerUpsertItem jsonRequestBPartnerUpsertItem = JsonObjectMapperHolder
					.sharedJsonObjectMapper()
					.readValue(parser, JsonRequestBPartnerUpsertItem.class);

			jsonRequestBPartnerUpsertItems.add(jsonRequestBPartnerUpsertItem);

			if (parser.nextToken() == JsonToken.END_ARRAY)
			{
				isContinueParsing = false;
				parser.close();
				break;
			}
		}

		final UpsertBPartnersRouteContext upsertBPartnersRouteContext = exchange.getProperty(ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT, UpsertBPartnersRouteContext.class);

		final BPUpsertCamelRequest camelRequest = getBPUpsertCamelRequest(upsertBPartnersRouteContext.getOrgCode(), jsonRequestBPartnerUpsertItems.build());
		exchange.getIn().setBody(camelRequest);

		upsertBPartnersRouteContext.increaseReadItemsCount(camelRequest.getJsonRequestBPartnerUpsert().getRequestItems().size());

		exchange.setProperty(IS_CONTINUE_PARSING_PROPERTY, isContinueParsing);
	}

	@NonNull
	private static BPUpsertCamelRequest getBPUpsertCamelRequest(
			@NonNull final String orgCode,
			@NonNull final List<JsonRequestBPartnerUpsertItem> upsertItemList)
	{
		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItems(upsertItemList)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(orgCode)
				.build();
	}
}
