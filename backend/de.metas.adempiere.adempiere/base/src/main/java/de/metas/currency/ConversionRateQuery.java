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

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * The narrowing query {@link ConversionRateRepository} reads {@code C_Conversion_Rate} by: the
 * {@code (org, from, to, type, validFrom)} coordinates, each nullable ({@code null} = do not narrow on that column).
 */
@Value
@Builder(toBuilder = true)
public class ConversionRateQuery
{
	/** The restriction-free query (matches every row); rejected by getByQuery. */
	public static final ConversionRateQuery EMPTY = builder().build();

	@Nullable OrgId orgId;
	@Nullable CurrencyId fromCurrencyId;
	@Nullable CurrencyId toCurrencyId;
	@Nullable CurrencyConversionTypeId conversionTypeId;
	@Nullable LocalDate validFrom;

	/** The exact-key narrowing query for a natural key (all key coordinates set). */
	@NonNull
	public static ConversionRateQuery of(@NonNull final ConversionRateKey key)
	{
		return builder()
				.orgId(key.getOrgId())
				.fromCurrencyId(key.getFromCurrencyId())
				.toCurrencyId(key.getToCurrencyId())
				.conversionTypeId(key.getConversionTypeId())
				.validFrom(key.getValidFrom())
				.build();
	}
}
