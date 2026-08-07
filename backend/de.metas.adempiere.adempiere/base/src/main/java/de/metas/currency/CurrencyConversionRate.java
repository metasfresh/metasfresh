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
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A stored {@code C_Conversion_Rate} row as a typed domain POJO — natural-key coordinates plus the rate values.
 * Read-side counterpart to {@link CurrencyConversionUpsertRequest}; {@link ConversionRateRepository} maps the raw
 * {@code I_C_Conversion_Rate} model into this (typed ids, {@link LocalDate} {@code validFrom}).
 */
@Value
@Builder
public class CurrencyConversionRate
{
	@NonNull OrgId orgId;
	@NonNull CurrencyId fromCurrencyId;
	@NonNull CurrencyId toCurrencyId;
	@NonNull CurrencyConversionTypeId conversionTypeId;
	@NonNull LocalDate validFrom;
	@Nullable LocalDate validTo;
	@NonNull BigDecimal multiplyRate;
	@NonNull BigDecimal divideRate;
}
