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
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

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
	public JsonConversionRateResponse createConversionRate(@NonNull final JsonCurrencyRateCreateRequest currencyRateCreateRequest)
	{
		return trxManager.callInThreadInheritedTrx(() -> createConversionRateWithinTrx(currencyRateCreateRequest));
	}

	@NonNull
	private JsonConversionRateResponse createConversionRateWithinTrx(@NonNull final JsonCurrencyRateCreateRequest currencyRateCreateRequest)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(currencyRateCreateRequest.getOrgCode());
		final ZoneId orgTimezone = orgDAO.getTimeZone(orgId);
		final CurrencyId currencyFromId = getCurrencyId(currencyRateCreateRequest.getCurrencyCodeFrom());

		final ImmutableList<JsonConversionRateResponseItem> rateResponseItems = currencyRateCreateRequest.getRequestItems()
				.stream()
				.map(requestItem -> buildConversionRateCreateRequest(orgTimezone, orgId, currencyFromId, requestItem))
				.map(conversionRateRepository::create)
				.map(this::buildJsonConversionRateResponseItem)
				.collect(ImmutableList.toImmutableList());

		return JsonConversionRateResponse.of(rateResponseItems);
	}

	@NonNull
	private ConversionRateCreateRequest buildConversionRateCreateRequest(
			@NonNull final ZoneId orgTimezone,
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyIdFrom,
			@NonNull final JsonCurrencyRateCreateRequestItem requestItem)
	{
		return ConversionRateCreateRequest.builder()
				.orgId(orgId)
				.currencyId(currencyIdFrom)
				.currencyToId(getCurrencyId(requestItem.getCurrencyCodeTo()))
				.conversionTypeId(currencyBL.getCurrencyConversionTypeIdOrDefault(orgId, requestItem.getConversionType()))
				.divideRate(requestItem.getDivideRate())
				.validFrom(TimeUtil.asInstant(requestItem.getValidFrom(), orgTimezone))
				.validTo(TimeUtil.asInstant(requestItem.getValidTo(), orgTimezone))
				.build();
	}

	@NonNull
	private JsonConversionRateResponseItem buildJsonConversionRateResponseItem(@NonNull final ConversionRate conversionRate)
	{
		return JsonConversionRateResponseItem.builder()
				.conversionRateId(JsonMetasfreshId.of(conversionRate.getConversionRateId().getRepoId()))
				.currencyCodeFrom(currencyBL.getCurrencyCodeById(conversionRate.getCurrencyId()).toThreeLetterCode())
				.currencyCodeTo(currencyBL.getCurrencyCodeById(conversionRate.getCurrencyToId()).toThreeLetterCode())
				.divideRate(conversionRate.getDivideRate())
				.validFrom(conversionRate.getValidFrom())
				.build();
	}

	@NonNull
	private CurrencyId getCurrencyId(@NonNull final String currencyCode)
	{
		return currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyCode)).getId();
	}
}
