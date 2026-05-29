package de.metas.payment.grossprofit;

import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.money.grossprofit.ProfitPriceActualComponent;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Builder
public class PaymentProfitPriceActualComponent implements ProfitPriceActualComponent
{
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final MoneyService moneyService;
	@Nullable private final PaymentTermId paymentTermId;

	/**
	 * Subtracts the expectable payment discount ("Skonto") from the given input.
	 */
	@Override
	public Money applyToInput(@NonNull final Money input)
	{
		if (paymentTermId == null)
		{
			return input;
		}

		final Percent discount = paymentTermService.getPaymentTermDiscount(paymentTermId);

		final Money discountAmt = moneyService.percentage(discount, input);
		return input.subtract(discountAmt);
	}

}
