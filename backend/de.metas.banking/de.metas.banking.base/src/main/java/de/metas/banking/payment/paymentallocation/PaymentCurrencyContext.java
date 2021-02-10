/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.payment.paymentallocation;

import de.metas.currency.FixedConversionRate;
import de.metas.money.CurrencyConversionTypeId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PaymentCurrencyContext
{
	@Nullable
	CurrencyConversionTypeId currencyConversionTypeId;

	@Nullable
	FixedConversionRate fixedConversionRate;

	public static PaymentCurrencyContext ofCurrencyConversionTypeId(@Nullable final CurrencyConversionTypeId currencyConversionTypeId)
	{
		return builder().currencyConversionTypeId(currencyConversionTypeId).build();
	}
}
