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
 * A single stored {@code C_Conversion_Rate} row as a typed domain POJO: the natural-key coordinates
 * ({@code org, from, to, type, validFrom}) plus the two rate values ({@code multiplyRate}, {@code divideRate}).
 * <p>
 * Read-side counterpart to {@link ConversionRateCreateRequest} (the write-side row-to-persist): the
 * {@link ConversionRateRepository} maps the raw {@code I_C_Conversion_Rate} model into this POJO — doing the
 * {@code CurrencyId.ofRepoId} / {@code validFrom}-in-org-zone conversions inside the repo — so callers work
 * against typed ids and a {@link LocalDate} {@code validFrom} rather than repo-ints and a {@code Timestamp}.
 */
@Value
@Builder
public class ConversionRate
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
