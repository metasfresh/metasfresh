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
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

/**
 * The natural key of a {@code C_Conversion_Rate} direction:
 * {@code (AD_Org_ID, C_Currency_ID, C_Currency_ID_To, C_ConversionType_ID, ValidFrom)}.
 */
@Value
@Builder(toBuilder = true)
public class ConversionRateKey
{
	@NonNull OrgId orgId;
	@NonNull CurrencyId fromCurrencyId;
	@NonNull CurrencyId toCurrencyId;
	@NonNull CurrencyConversionTypeId conversionTypeId;
	@NonNull LocalDate validFrom;

	/** The opposite direction ({@code to -> from}); all other key columns unchanged. */
	@NonNull
	public ConversionRateKey getReverseKey()
	{
		return toBuilder()
				.fromCurrencyId(toCurrencyId)
				.toCurrencyId(fromCurrencyId)
				.build();
	}
}
