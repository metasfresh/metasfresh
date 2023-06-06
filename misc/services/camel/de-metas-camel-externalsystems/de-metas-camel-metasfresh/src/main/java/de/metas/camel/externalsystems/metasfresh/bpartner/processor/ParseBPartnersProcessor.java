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
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.metasfresh.restapi.FilenameUtil;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;

import java.io.IOException;

import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.FILE_NAME_HEADER;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.IS_CONTINUE_PARSING_PROPERTY;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.PARSER_PROPERTY;

public class ParseBPartnersProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws IOException
	{
		final JsonParser parser = exchange.getProperty(PARSER_PROPERTY, JsonParser.class);

		final boolean isContinueParsing = parser.nextToken() != JsonToken.END_ARRAY;

		if (!isContinueParsing)
		{
			exchange.setProperty(IS_CONTINUE_PARSING_PROPERTY, false);
			exchange.getIn().setBody(null);
			parser.close();
			return;
		}

		final JsonRequestBPartnerUpsertItem jsonRequestBPartnerUpsertItem;
		try
		{
			jsonRequestBPartnerUpsertItem = JsonObjectMapperHolder
					.sharedJsonObjectMapper()
					.readValue(parser, JsonRequestBPartnerUpsertItem.class);
		}
		catch (final ValueInstantiationException e)
		{
			exchange.getIn().setBody(null);
			throw new RuntimeCamelException("Unable to create JSON-Object at location " + e.getLocation(), e);
		}
		catch (final Exception exception)
		{
			//exchange.setProperty(IS_CONTINUE_PARSING_PROPERTY, false); don't stop parsing because of this one error
			//parser.close();
			exchange.getIn().setBody(null);
			throw exception;
		}

		final String orgCode = FilenameUtil.getOrgCode(exchange.getIn().getHeader(FILE_NAME_HEADER, String.class));

		final BPUpsertCamelRequest camelRequest = getBPUpsertCamelRequest(orgCode, jsonRequestBPartnerUpsertItem);
		exchange.getIn().setBody(camelRequest);
		exchange.setProperty(IS_CONTINUE_PARSING_PROPERTY, true);
	}

	@NonNull
	private static BPUpsertCamelRequest getBPUpsertCamelRequest(
			@NonNull final String orgCode,
			@NonNull final JsonRequestBPartnerUpsertItem jsonRequestBPartnerUpsertItem)
	{
		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItems(ImmutableList.of(jsonRequestBPartnerUpsertItem))
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(orgCode)
				.build();
	}
}
