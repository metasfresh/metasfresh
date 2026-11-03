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

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Single source of the canonical {@code 1 / rate} formula for the {@code C_Conversion_Rate}
 * {@code MultiplyRate}/{@code DivideRate} pair, shared by the field callout and the REST upsert.
 */
@UtilityClass
public class CurrencyConversionRates
{
	private static final int RATE_SCALE = 12;
	private static final RoundingMode RATE_ROUNDING = RoundingMode.HALF_UP;

	/**
	 * {@code 1 / rate} at scale {@value #RATE_SCALE}, {@link #RATE_ROUNDING}.
	 *
	 * @throws ArithmeticException if {@code rate} is zero (callers must guard with {@code signum() == 0} first).
	 */
	@NonNull
	public static BigDecimal reciprocal(@NonNull final BigDecimal rate)
	{
		return BigDecimal.ONE.divide(rate, RATE_SCALE, RATE_ROUNDING);
	}
}
