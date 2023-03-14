/*
 * #%L
 * de-metas-camel-sap
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.sap.export;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.sap.export.generalledger.lobservices.RequestBuilder;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_TARGET_URI;
import static de.metas.camel.externalsystems.sap.export.ExportAcctDetailsRouteBuilder.ROUTE_PROPERTY_EXPORT_ACCT_ROUTE_CONTEXT;

public class PrepareSAPRequestProcessor implements Processor
{
	@NonNull
	private final ProcessLogger processLogger;

	public PrepareSAPRequestProcessor(final @NonNull ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws ParserConfigurationException, TransformerException
	{
		final JsonNode processResponse = exchange.getIn().getBody(JsonNode.class);

		if (!processResponse.isArray())
		{
			throw new RuntimeException("Unexpected response! see: " + processResponse);
		}

		final ExportAcctDetailsRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange,
																								   ROUTE_PROPERTY_EXPORT_ACCT_ROUTE_CONTEXT,
																								   ExportAcctDetailsRouteContext.class);

		final ImmutableList<JsonNode> documentTypeGLItems = ImmutableList.copyOf(processResponse.iterator());

		if (documentTypeGLItems.isEmpty())
		{
			processLogger.logMessage("No accounting records retrieved! see request: " + routeContext.getInvokePostgRESTRequest(),
									 exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class));
		}

		exchange.getIn().setHeader(HEADER_TARGET_URI, routeContext.getCredentials().getPostAcctDocumentsURL());

		final String requestBody = RequestBuilder.getXMLRequest(documentTypeGLItems);

		exchange.getIn().setBody(requestBody);
	}
}
