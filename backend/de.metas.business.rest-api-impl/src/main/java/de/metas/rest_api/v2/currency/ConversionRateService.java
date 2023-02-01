/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.currency;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.conversionRate.JsonConversionRateResponse;
import de.metas.common.rest_api.v2.conversionRate.JsonConversionRateResponseItem;
import de.metas.common.rest_api.v2.conversionRate.JsonCurrencyRateCreateRequest;
import de.metas.common.rest_api.v2.conversionRate.JsonCurrencyRateCreateRequestItem;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.conversionRate.model.ConversionRate;
import de.metas.currency.conversionRate.model.ConversionRateCreateRequest;
import de.metas.currency.conversionRate.repository.ConversionRateRepository;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Conversion_Rate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class ConversionRateService
{
	@NonNull
	private final ConversionRateRepository conversionRateRepository;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public ConversionRateService(@NonNull final ConversionRateRepository conversionRateRepository)
	{
		this.conversionRateRepository = conversionRateRepository;
	}

	@NonNull
	public JsonConversionRateResponse createConversionRate(@NonNull final JsonCurrencyRateCreateRequest requestConversionRateUpsert)
	{
		return trxManager.callInNewTrx(() -> createConversionRateWithinTrx(requestConversionRateUpsert));
	}

	@NonNull
	private JsonConversionRateResponse createConversionRateWithinTrx(@NonNull final JsonCurrencyRateCreateRequest requestConversionRateUpsert)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(requestConversionRateUpsert.getOrgCode());

		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(requestConversionRateUpsert.getCurrencyCodeFrom());
		final CurrencyId currencyFromId = currencyBL.getByCurrencyCode(currencyCode).getId();

		final ImmutableList.Builder<JsonConversionRateResponseItem> responseItems = ImmutableList.builder();

		for (final JsonCurrencyRateCreateRequestItem requestItem : requestConversionRateUpsert.getRequestItems())
		{
			final ConversionRateCreateRequest createRequest = buildConversionRateCreateRequest(orgId, currencyFromId, requestItem);

			final ConversionRate createdConversionRate = conversionRateRepository.create(createRequest);

			responseItems.add(buildJsonConversionRateResponseItem(
					createdConversionRate,
					requestConversionRateUpsert.getCurrencyCodeFrom(),
					requestItem.getCurrencyCodeTo()));
		}

		return JsonConversionRateResponse.of(responseItems.build());
	}

	@NonNull
	private ConversionRateCreateRequest buildConversionRateCreateRequest(
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyIdFrom,
			@NonNull final JsonCurrencyRateCreateRequestItem conversionRateUpsertItem)
	{
		final ConversionRateCreateRequest.ConversionRateCreateRequestBuilder createRequest = ConversionRateCreateRequest.builder();

		createRequest.orgId(orgId);
		createRequest.currencyId(currencyIdFrom);

		//currencyTo
		if (conversionRateUpsertItem.isCurrencyCodeToSet())
		{
			if (conversionRateUpsertItem.getCurrencyCodeTo() == null)
			{
				throw new AdempiereException(I_C_Conversion_Rate.Table_Name + ".C_Currency_To_ID cannot be null!");
			}

			final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(conversionRateUpsertItem.getCurrencyCodeTo());
			final CurrencyId currencyToId = currencyBL.getByCurrencyCode(currencyCode).getId();

			createRequest.currencyToId(currencyToId);
		}

		// conversionType
		final CurrencyConversionTypeId conversionTypeId = currencyBL.getCurrencyConversionTypeIdOrDefault(orgId, conversionRateUpsertItem.getConversionType());

		createRequest.conversionTypeId(conversionTypeId);

		//validFrom
		if (conversionRateUpsertItem.isValidFromSet())
		{
			if (conversionRateUpsertItem.getValidFrom() == null)
			{
				throw new AdempiereException(I_C_Conversion_Rate.Table_Name + ".ValidFrom cannot be null!");
			}

			final ZoneId timeZone = orgDAO.getTimeZone(orgId);
			final Instant validFrom = conversionRateUpsertItem.getValidFrom().atStartOfDay(timeZone).toInstant();

			createRequest.validFrom(validFrom);
		}

		//divideRate
		if (conversionRateUpsertItem.isDivideRateSet())
		{
			if (conversionRateUpsertItem.getDivideRate() == null)
			{
				throw new AdempiereException(I_C_Conversion_Rate.Table_Name + "I.DivideRate cannot be null!");
			}

			createRequest.divideRate(conversionRateUpsertItem.getDivideRate());
			createRequest.multiplyRate(BigDecimal.ONE.divide(conversionRateUpsertItem.getDivideRate(), 12, RoundingMode.HALF_UP));
		}

		//validTo
		if (conversionRateUpsertItem.isValidToSet())
		{
			Instant validTo = null;
			if (conversionRateUpsertItem.getValidTo() != null)
			{
				final ZoneId timeZone = orgDAO.getTimeZone(orgId);
				validTo = conversionRateUpsertItem.getValidTo().atStartOfDay(timeZone).toInstant();
			}

			createRequest.validTo(validTo);
		}

		return createRequest.build();
	}

	@NonNull
	private JsonConversionRateResponseItem buildJsonConversionRateResponseItem(
			@NonNull final ConversionRate conversionRate,
			@NonNull final String currencyCodeFrom,
			@NonNull final String currencyCodeTo)
	{
		return JsonConversionRateResponseItem.builder()
				.conversionRateId(JsonMetasfreshId.of(conversionRate.getConversionRateId().getRepoId()))
				.currencyCodeFrom(currencyCodeFrom)
				.currencyCodeTo(currencyCodeTo)
				.divideRate(conversionRate.getDivideRate())
				.build();
	}
}
