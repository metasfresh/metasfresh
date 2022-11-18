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
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.CreditLimitDeleteRequest;
import de.metas.camel.externalsystems.sap.creditlimit.info.UpsertCreditLimitRequest;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class UpsertCreditLimitRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	public static final String UPSERT_CREDIT_LIMIT_ROUTE_ID = "SAP-upsertCreditLimit";

	@VisibleForTesting
	public static final String UPSERT_CREDIT_LIMIT_PROCESSOR_ID = "SAP-upsertCreditLimitProcessorId";

	private static final String UPSERT_CREDIT_LIMIT_PROPERTY_NAME = "UpsertCreditLimitRequest";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(UPSERT_CREDIT_LIMIT_ROUTE_ID))
				.routeId(UPSERT_CREDIT_LIMIT_ROUTE_ID)

				.process(UpsertCreditLimitRouteBuilder::saveUpsertRequestAsProperty)

				.process(UpsertCreditLimitRouteBuilder::prepareDeleteRequest)
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to delete existing credit limit records for <Org,BPartnerIdentifier> : ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_DELETE_BPARTNER_CREDIT_LIMIT_CAMEL_URI + "}}")

				.process(UpsertCreditLimitRouteBuilder::prepareBPUpsertCamelRequest).id(UPSERT_CREDIT_LIMIT_PROCESSOR_ID)
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert credit limits via upsertBPartner EP: ${body}")
				.toD("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
				.end();
		// @formatter:on
	}

	private static void prepareBPUpsertCamelRequest(@NonNull final Exchange exchange)
	{
		final UpsertCreditLimitRequest upsertCreditLimitRequest = exchange.getProperty(UPSERT_CREDIT_LIMIT_PROPERTY_NAME, UpsertCreditLimitRequest.class);

		final JsonRequestBPartnerUpsertItem requestBPartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(upsertCreditLimitRequest.getBpartnerIdentifier())
				.bpartnerComposite(JsonRequestComposite.builder()
										   .orgCode(upsertCreditLimitRequest.getOrgCode())
										   .creditLimits(upsertCreditLimitRequest.getJsonRequestCreditLimitUpsert())
										   .syncAdvise(SyncAdvise.CREATE_OR_MERGE)
										   .build())
				.build();

		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(ImmutableList.of(requestBPartnerUpsertItem))
				.build();

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(upsertCreditLimitRequest.getOrgCode())
				.build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}

	private static void prepareDeleteRequest(@NonNull final Exchange exchange)
	{
		final UpsertCreditLimitRequest upsertCreditLimitRequest = exchange.getProperty(UPSERT_CREDIT_LIMIT_PROPERTY_NAME, UpsertCreditLimitRequest.class);

		final CreditLimitDeleteRequest creditLimitDeleteRequest = CreditLimitDeleteRequest.builder()
				.orgCode(upsertCreditLimitRequest.getOrgCode())
				.bpartnerIdentifier(upsertCreditLimitRequest.getBpartnerIdentifier())
				.includingProcessed(true)
				.build();

		exchange.getIn().setBody(creditLimitDeleteRequest);
	}

	private static void saveUpsertRequestAsProperty(@NonNull final Exchange exchange)
	{
		final UpsertCreditLimitRequest upsertCreditLimitRequest = exchange.getIn().getBody(UpsertCreditLimitRequest.class);

		if (upsertCreditLimitRequest == null)
		{
			throw new RuntimeException("upsertCreditLimitRequest missing from exchange! See actual body=" + exchange.getIn().getBody());
		}

		exchange.setProperty(UPSERT_CREDIT_LIMIT_PROPERTY_NAME, upsertCreditLimitRequest);
	}
}
