/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.creditlimit;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.SAPConstants.ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class UpsertCreditLimitRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	public static final String UPSERT_CREDIT_LIMIT_ROUTE_ID = "SAP-upsertCreditLimit";

	@VisibleForTesting
	public static final String UPSERT_CREDIT_LIMIT_PROCESSOR_ID = "SAP-upsertCreditLimitProcessorId";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(UPSERT_CREDIT_LIMIT_ROUTE_ID))
				.routeId(UPSERT_CREDIT_LIMIT_ROUTE_ID)
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to delete existing credit limit records for <Org,BPartnerIdentifier> : ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_DELETE_BPARTNER_CREDIT_LIMIT_CAMEL_URI + "}}")
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert credit limits via upsertBPartner EP: ${body}")
				.process(exchange -> {
					final CreditLimitContext creditLimitRouteContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT, CreditLimitContext.class);

					exchange.getIn().setBody(creditLimitRouteContext.getBpUpsertCamelRequest(), BPUpsertCamelRequest.class);
				}).id(UPSERT_CREDIT_LIMIT_PROCESSOR_ID)
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
				.end();

		// @formatter:on
	}
}
