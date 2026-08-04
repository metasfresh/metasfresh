/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.rest_api.v2.currencyconversion;

import de.metas.RestUtils;
import de.metas.common.rest_api.v2.currencyconversion.JsonNewestConversionRate;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsertItem;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * The single place that translates between the currency-conversion JSON DTOs and the domain
 * {@link ConversionRate}, modelled on {@code de.metas.rest_api.v2.ordercandidates.impl.JsonConverters#fromJson}:
 * <b>all</b> resolution (currency code -> {@link CurrencyId}, org, conversion type, {@link ClientAndOrgId},
 * and org-timezone {@code validFrom}/{@code validTo} date conversion) happens here, so the service operates on
 * fully-resolved domain objects only.
 * <p>
 * An unknown/inactive currency, an unknown org code, or an unknown conversion-type code raises a
 * {@code markAsUserValidationError} exception so the offending request item becomes a per-record {@code ERROR}
 * rather than aborting the batch or auto-creating master data.
 */
@Component
@RequiredArgsConstructor
public class JsonConversionRateConverters
{
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final CurrencyConversionRepository currencyConversionRepository;

	/**
	 * Resolves a single JSON upsert item into the fully-resolved domain {@link ConversionRate}. The client is
	 * always {@link ClientId#METASFRESH} (the sole {@code ClientId.METASFRESH} reference of the whole feature, so
	 * that the service never threads a client): the request carries only an org code, never a client.
	 */
	@NonNull
	public ConversionRate fromJson(@NonNull final JsonRequestConversionRateUpsertItem item)
	{
		final OrgId orgId = resolveOrgId(item.getOrgCode());
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId);
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);

		final CurrencyId fromCurrencyId = currencyConversionRepository.getActiveCurrencyId(item.getFromCurrencyCode());
		final CurrencyId toCurrencyId = currencyConversionRepository.getActiveCurrencyId(item.getToCurrencyCode());
		final CurrencyConversionTypeId conversionTypeId = resolveConversionTypeId(
				item.getConversionTypeCode(),
				clientAndOrgId,
				item.getValidFrom(),
				orgZoneId);

		return ConversionRate.builder()
				.clientAndOrgId(clientAndOrgId)
				.fromCurrencyId(fromCurrencyId)
				.toCurrencyId(toCurrencyId)
				.conversionTypeId(conversionTypeId)
				.validFrom(item.getValidFrom())
				.validTo(item.getValidTo())
				.multiplyRate(item.getMultiplyRate())
				.orgZoneId(orgZoneId)
				.build();
	}

	/**
	 * Maps a stored {@code C_Conversion_Rate} row to its response DTO. {@code ValidFrom} is read back through the
	 * <b>org's</b> zone (matching the store path), so store-and-read use the same org zone consistently.
	 */
	@NonNull
	public JsonNewestConversionRate toJsonNewestConversionRate(@NonNull final I_C_Conversion_Rate rate)
	{
		final CurrencyId fromCurrencyId = CurrencyId.ofRepoId(rate.getC_Currency_ID());
		final CurrencyId toCurrencyId = CurrencyId.ofRepoId(rate.getC_Currency_ID_To());
		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoId(rate.getC_ConversionType_ID());
		final ZoneId orgZoneId = orgDAO.getTimeZone(OrgId.ofRepoId(rate.getAD_Org_ID()));

		return JsonNewestConversionRate.builder()
				.fromCurrencyCode(currencyDAO.getCurrencyCodeById(fromCurrencyId).toThreeLetterCode())
				.toCurrencyCode(currencyDAO.getCurrencyCodeById(toCurrencyId).toThreeLetterCode())
				.conversionTypeCode(currencyDAO.getConversionTypeMethodById(conversionTypeId).getCode())
				.validFrom(TimeUtil.asLocalDate(rate.getValidFrom(), orgZoneId))
				.multiplyRate(rate.getMultiplyRate())
				.divideRate(rate.getDivideRate())
				.build();
	}

	@NonNull
	private OrgId resolveOrgId(@Nullable final String orgCode)
	{
		// A blank orgCode means the shared, cross-org rate: org 0 (OrgId.ANY).
		// RestUtils.retrieveOrgIdOrDefault falls back to the context org (Env.getOrgId()) when blank, not to
		// OrgId.ANY, so only its non-blank path (resolve by AD_Org.Value) is reused here.
		if (Check.isBlank(orgCode))
		{
			return OrgId.ANY;
		}
		return RestUtils.retrieveOrgIdOrDefault(orgCode);
	}

	@NonNull
	private CurrencyConversionTypeId resolveConversionTypeId(
			@Nullable final String conversionTypeCode,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final LocalDate validFrom,
			@NonNull final ZoneId orgZoneId)
	{
		if (Check.isBlank(conversionTypeCode))
		{
			return currencyDAO.getDefaultConversionTypeId(
					clientAndOrgId.getClientId(),
					clientAndOrgId.getOrgId(),
					validFrom.atStartOfDay(orgZoneId).toInstant());
		}

		final ConversionTypeMethod method;
		try
		{
			method = ConversionTypeMethod.forCode(conversionTypeCode.trim());
		}
		catch (final IllegalArgumentException ex)
		{
			throw new AdempiereException("@Invalid@ @C_ConversionType_ID@: " + conversionTypeCode)
					.markAsUserValidationError();
		}
		return currencyDAO.getConversionTypeId(method);
	}
}
