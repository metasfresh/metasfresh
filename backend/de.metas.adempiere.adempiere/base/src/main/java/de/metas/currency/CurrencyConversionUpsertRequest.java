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
 * The immutable, fully-resolved single-direction rate to persist: a {@code C_Conversion_Rate} row as its natural
 * key ({@code clientAndOrgId, from, to, conversionType, validFrom}) plus payload ({@code multiplyRate}, {@code validTo}).
 * {@code DivideRate} is derived from {@link #getMultiplyRate()}, not stored as a field. The resolved
 * {@link #orgZoneId} carries the org timezone so all {@code validFrom}/{@code validTo} conversions go through one zone.
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

	/** The persistence natural key of this direction. */
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
