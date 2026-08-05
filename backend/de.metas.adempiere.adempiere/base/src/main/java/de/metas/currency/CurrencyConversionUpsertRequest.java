/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.currency;

import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * The immutable, fully-resolved single-direction rate to persist: a {@code C_Conversion_Rate} row modelled
 * as its natural key ({@code clientAndOrgId, from, to, conversionType, validFrom}) plus the payload
 * ({@code multiplyRate}, {@code validTo}).
 * <p>
 * The {@code DivideRate} is <b>not</b> a field — it is derived from {@link #getMultiplyRate()} via
 * {@link #getDivideRate()} (the canonical {@link CurrencyConversionRates#reciprocal(BigDecimal)}). Distinct
 * from {@link CurrencyRate}, which is the conversion-execution type ({@code convertAmount}): that carries a
 * single rate + precisions + a conversionDate but no {@code validFrom}/{@code validTo}/org and is the wrong
 * shape for a row-to-persist. This type deliberately does <b>not</b> reuse the near-collision name
 * {@code CurrencyRate}.
 * <p>
 * The resolved {@link #orgZoneId} (the org's timezone, from {@code OrgDAO.getTimeZone}) is carried on the object
 * so that <b>all</b> {@code validFrom}/{@code validTo} {@code LocalDate -> Timestamp} conversions go through the
 * one org zone — {@link #getValidFromTimestamp()} / {@link #getValidToTimestamp()} are the single place the
 * {@link ConversionRateRepository} store path reads them from.
 */
@Value
@Builder(toBuilder = true)
public class CurrencyConversionUpsertRequest
{
	@NonNull ClientAndOrgId clientAndOrgId;
	@NonNull CurrencyId fromCurrencyId;
	@NonNull CurrencyId toCurrencyId;
	@NonNull CurrencyConversionTypeId conversionTypeId;
	@NonNull LocalDate validFrom;
	@Nullable LocalDate validTo;
	@NonNull BigDecimal multiplyRate;
	@NonNull ZoneId orgZoneId;

	/** {@code DivideRate = 1 / MultiplyRate} at the canonical scale/rounding. */
	@NonNull
	public BigDecimal getDivideRate()
	{
		return CurrencyConversionRates.reciprocal(multiplyRate);
	}

	/** {@code validFrom} as a {@code Timestamp} at the start of day in the org's timezone. */
	@NonNull
	public Timestamp getValidFromTimestamp()
	{
		return TimeUtil.asTimestamp(validFrom, orgZoneId);
	}

	/** {@code validTo} as a {@code Timestamp} at the start of day in the org's timezone, or {@code null} if open. */
	@Nullable
	public Timestamp getValidToTimestamp()
	{
		return validTo != null ? TimeUtil.asTimestamp(validTo, orgZoneId) : null;
	}

	/** The persistence natural key of this direction (org-scoped; excludes {@code AD_Client_ID}). */
	@NonNull
	public ConversionRateKey getKey()
	{
		return ConversionRateKey.builder()
				.orgId(clientAndOrgId.getOrgId())
				.fromCurrencyId(fromCurrencyId)
				.toCurrencyId(toCurrencyId)
				.conversionTypeId(conversionTypeId)
				.validFrom(validFrom)
				.build();
	}

	/** The persistence key of the opposite direction ({@code to -> from}). */
	@NonNull
	public ConversionRateKey getReverseKey()
	{
		return getKey().getReverseKey();
	}
}
