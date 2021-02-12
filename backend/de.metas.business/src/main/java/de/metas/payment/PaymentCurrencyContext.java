/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.payment;

import de.metas.currency.FixedConversionRate;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class PaymentCurrencyContext
{
	@Nullable CurrencyConversionTypeId currencyConversionTypeId;

	@Nullable CurrencyId paymentCurrencyId;
	@Nullable CurrencyId sourceCurrencyId;
	@Nullable BigDecimal currencyRate;

	public static final PaymentCurrencyContext NONE = builder().build();

	public boolean isFixedConversionRate()
	{
		return paymentCurrencyId != null && sourceCurrencyId != null && currencyRate != null;
	}

	@Nullable
	public FixedConversionRate toFixedConversionRateOrNull()
	{
		if (paymentCurrencyId != null && sourceCurrencyId != null && currencyRate != null)
		{
			return FixedConversionRate.builder()
					.fromCurrencyId(paymentCurrencyId)
					.toCurrencyId(sourceCurrencyId)
					.multiplyRate(currencyRate)
					.build();
		}
		else
		{
			return null;
		}
	}
}
