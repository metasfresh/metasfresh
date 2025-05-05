/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interest;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value(staticConstructor = "of")
public class InterestScore
{
	/**
	 * This is just something to make the score calculation values more manageable.
	 */
	public static final BigDecimal DIVISION_FACTOR = BigDecimal.valueOf(100);
	public static final int DEFAULT_SCALE = 2;
	@NonNull Money amount;
	@NonNull Integer numberOfDays;

	@NonNull
	public BigDecimal getScore()
	{
		return computeScore(amount, numberOfDays);
	}

	public void assertCurrency(@NonNull final CurrencyId currencyId)
	{
		amount.assertCurrencyId(currencyId);
	}

	@NonNull
	public static BigDecimal computeScore(@NonNull final Money amount, final int interestDays)
	{
		final BigDecimal amountAsBD = amount.toBigDecimal();
		final int scaleToUse = Integer.max(amountAsBD.scale(), DEFAULT_SCALE);

		return amountAsBD.multiply(BigDecimal.valueOf(interestDays))
				.divide(DIVISION_FACTOR, scaleToUse, RoundingMode.HALF_UP);
	}
}
