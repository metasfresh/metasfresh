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

import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.sap.api.model.creditlimit.CreditLimitRow;
import de.metas.common.bpartner.v2.request.creditLimit.JsonRequestCreditLimitDelete;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.sap.SAPConstants.ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT;
import static de.metas.camel.externalsystems.sap.common.ExternalIdentifierFormat.formatExternalId;

public class CreditLimitUpsertProcessor implements Processor
{
	private final static String CREDIT_TYPE = "S001";

	@NonNull
	final JsonExternalSystemRequest externalSystemRequest;

	@NonNull
	private final ProcessLogger processLogger;

	public CreditLimitUpsertProcessor(
			@NonNull final JsonExternalSystemRequest externalSystemRequest,
			@NonNull final ProcessLogger processLogger)
	{
		this.externalSystemRequest = externalSystemRequest;
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final CreditLimitRow currentCreditLimitRow = exchange.getIn().getBody(CreditLimitRow.class);

		if (!qualifiesForImport(currentCreditLimitRow))
		{
			exchange.getIn().setBody(null);
			return;
		}

		processCreditLimitRow(exchange, currentCreditLimitRow);
	}

	private void processCreditLimitRow(@NonNull final Exchange exchange, @NonNull final CreditLimitRow currentCreditLimitRow)
	{
		final CreditLimitContext creditLimitRouteContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT, CreditLimitContext.class);

		final boolean added = creditLimitRouteContext.getCreditLimitUpsertGroupBuilder().addCreditLimitRow(currentCreditLimitRow, creditLimitRouteContext.getOrgCode());

		if (added)
		{
			exchange.getIn().setBody(null);
			return;
		}

		final JsonRequestCreditLimitDelete jsonRequestCreditLimitDelete = prepareDeleteRequest(creditLimitRouteContext.getOrgCode(), creditLimitRouteContext.getCreditLimitUpsertGroupBuilder().getBPartnerIdentifierNotNull());
		exchange.getIn().setBody(jsonRequestCreditLimitDelete, JsonRequestCreditLimitDelete.class);

		creditLimitRouteContext.setBpUpsertCamelRequest(creditLimitRouteContext.getCreditLimitUpsertGroupBuilder().build());

		final InitCreditLimitGroup initCreditLimitGroup = InitCreditLimitGroup.builder()
				.orgCode(creditLimitRouteContext.getOrgCode())
				.creditLimitRow(currentCreditLimitRow)
				.build();

		creditLimitRouteContext.setCreditLimitUpsertGroupBuilder(SyncCreditLimitRequestBuilder.of(initCreditLimitGroup));
	}

	public static void processLastCreditLimitGroup(@NonNull final Exchange exchange)
	{
		final CreditLimitContext creditLimitRouteContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT, CreditLimitContext.class);

		creditLimitRouteContext.setBpUpsertCamelRequest(creditLimitRouteContext.getCreditLimitUpsertGroupBuilder().build());

		final JsonRequestCreditLimitDelete jsonRequestCreditLimitDelete = CreditLimitUpsertProcessor.prepareDeleteRequest(creditLimitRouteContext.getOrgCode(), creditLimitRouteContext.getCreditLimitUpsertGroupBuilder().getBPartnerIdentifierNotNull());

		exchange.getIn().setBody(jsonRequestCreditLimitDelete, JsonRequestCreditLimitDelete.class);
	}

	@NonNull
	public static JsonRequestCreditLimitDelete prepareDeleteRequest(@NonNull final String orgCode, @NonNull final String groupIdentifier)
	{
		return JsonRequestCreditLimitDelete.builder()
				.orgCode(orgCode)
				.partnerIdentifier(formatExternalId(groupIdentifier))
				.build();
	}

	private boolean qualifiesForImport(@NonNull final CreditLimitRow creditLimitRow)
	{
		if(!creditLimitRow.getCreditType().equals(CREDIT_TYPE))
		{
			processLogger.logMessage("Skipped row due to invalid credit type: " + creditLimitRow.getCreditType(), externalSystemRequest.getAdPInstanceId().getValue());

			return false;
		}
		if (creditLimitRow.getCreditLine() == null)
		{
			processLogger.logMessage("Skipping row due to missing credit line!", externalSystemRequest.getAdPInstanceId().getValue());

			return false;
		}

		return true;
	}
}
