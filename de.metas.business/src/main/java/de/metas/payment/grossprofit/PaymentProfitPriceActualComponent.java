package de.metas.payment.grossprofit;

import javax.annotation.Nullable;

import de.metas.lang.Percent;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.money.grossprofit.ProfitPriceActualComponent;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Services;
import lombok.NonNull;

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

public class PaymentProfitPriceActualComponent implements ProfitPriceActualComponent
{
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class); // TODO: move service/repo out

	private final PaymentTermId paymentTermId;

	private final MoneyService moneyService;

	public PaymentProfitPriceActualComponent(
			@Nullable final PaymentTermId paymentTermId,
			@NonNull final MoneyService moneyService)
	{
		this.moneyService = moneyService;
		this.paymentTermId = paymentTermId;
	}

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

		final Percent discount = paymentTermRepository.getPaymentTermDiscount(paymentTermId);

		final Money discountAmt = moneyService.percentage(discount, input);
		return input.subtract(discountAmt);
	}

}
