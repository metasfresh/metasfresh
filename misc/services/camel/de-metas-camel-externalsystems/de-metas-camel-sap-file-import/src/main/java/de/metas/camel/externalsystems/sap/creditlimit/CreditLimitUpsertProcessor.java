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

import com.google.common.collect.ImmutableSet;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.sap.creditlimit.info.UpsertCreditLimitRequest;
import de.metas.camel.externalsystems.sap.model.creditlimit.CreditLimitRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.sap.SAPConstants.ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT;

public class CreditLimitUpsertProcessor implements Processor
{
	@NonNull
	private final JsonExternalSystemRequest externalSystemRequest;

	@NonNull
	private final ProcessLogger processLogger;

	@NonNull
	private final ImmutableSet<String> acceptedCreditTypes;

	public CreditLimitUpsertProcessor(
			@NonNull final JsonExternalSystemRequest externalSystemRequest,
			@NonNull final ProcessLogger processLogger,
			@NonNull final ImmutableSet<String> acceptedCreditTypes)
	{
		this.externalSystemRequest = externalSystemRequest;
		this.processLogger = processLogger;
		this.acceptedCreditTypes = acceptedCreditTypes;
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

		final UpsertCreditLimitRequestBuilder upsertCreditLimitRequestBuilder = creditLimitRouteContext.getUpsertCreditLimitRequestBuilder();

		if (upsertCreditLimitRequestBuilder == null)
		{
			creditLimitRouteContext.initCreditLimitRequestBuilderFor(currentCreditLimitRow);

			exchange.getIn().setBody(null);
			return;
		}

		final boolean added = upsertCreditLimitRequestBuilder.addCreditLimitRow(currentCreditLimitRow);

		if (added)
		{
			exchange.getIn().setBody(null);
			return;
		}

		final UpsertCreditLimitRequest upsertCreditLimitRequest = creditLimitRouteContext.getUpsertCreditLimitRequestBuilder().build();
		exchange.getIn().setBody(upsertCreditLimitRequest);

		creditLimitRouteContext.initCreditLimitRequestBuilderFor(currentCreditLimitRow);
	}

	public static void processLastCreditLimitGroup(@NonNull final Exchange exchange)
	{
		final CreditLimitContext creditLimitRouteContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT, CreditLimitContext.class);

		if (creditLimitRouteContext.getUpsertCreditLimitRequestBuilder() == null)
		{
			exchange.getIn().setBody(null);
			return;
		}

		exchange.getIn().setBody(creditLimitRouteContext.getUpsertCreditLimitRequestBuilder().build());
	}

	private boolean qualifiesForImport(@NonNull final CreditLimitRow creditLimitRow)
	{
		if (!acceptedCreditTypes.contains(creditLimitRow.getCreditType()))
		{
			processLogger.logMessage("Skipped row due to invalid credit type: " + creditLimitRow.getCreditType() + "!"
											 + " RawBPartnerCode: " + creditLimitRow.getCreditAccount().getRawPartnerCode(),
									 JsonMetasfreshId.toValue(externalSystemRequest.getAdPInstanceId()));

			return false;
		}

		if (Check.isBlank(creditLimitRow.getCreditLine()))
		{
			processLogger.logMessage("Skipping row due to missing credit line!! RawBPartnerCode: " + creditLimitRow.getCreditAccount().getRawPartnerCode(),
									 JsonMetasfreshId.toValue(externalSystemRequest.getAdPInstanceId()));

			return false;
		}

		if (Check.isBlank(creditLimitRow.getCurrencyCode()))
		{
			processLogger.logMessage("Skipping row due to missing currency code! RawBPartnerCode: " + creditLimitRow.getCreditAccount().getRawPartnerCode(),
									 JsonMetasfreshId.toValue(externalSystemRequest.getAdPInstanceId()));

			return false;
		}

		return true;
	}
}
