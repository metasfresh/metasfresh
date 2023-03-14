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
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

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
	public void process(final Exchange exchange)
	{
		final JsonNode processResponse = exchange.getIn().getBody(JsonNode.class);

		final ExportAcctDetailsRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange,
																								   ROUTE_PROPERTY_EXPORT_ACCT_ROUTE_CONTEXT,
																								   ExportAcctDetailsRouteContext.class);

		processLogger.logMessage("TargetURL: \n" + routeContext.getCredentials().getPostAcctDocumentsURL()
										 + "\n\nPostgREST response: \n" + processResponse.toString(),
								 exchange.getIn().getHeader(ExternalSystemCamelConstants.HEADER_PINSTANCE_ID, Integer.class));
	}
}
