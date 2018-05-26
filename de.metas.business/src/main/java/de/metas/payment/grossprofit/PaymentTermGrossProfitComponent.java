package de.metas.payment.grossprofit;

import javax.annotation.Nullable;

import org.adempiere.util.Services;

import de.metas.lang.Percent;
import de.metas.money.Money;
import de.metas.money.grossprofit.GrossProfitComponent;
import de.metas.payment.api.IPaymentTermRepository;
import de.metas.payment.api.PaymentTermId;
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

public class PaymentTermGrossProfitComponent implements GrossProfitComponent
{
	private PaymentTermId paymentTermId;

	public PaymentTermGrossProfitComponent(@Nullable final PaymentTermId paymentTermId)
	{
		this.paymentTermId = paymentTermId;
	}

	@Override
	public Money applyToInput(@NonNull final Money input)
	{
		if (paymentTermId == null)
		{
			return input;
		}

		final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
		final Percent discount = paymentTermRepository.getPaymentTermDiscount(paymentTermId);

		final Money percentage = input.percentage(discount);
		return input.subtract(percentage);
	}

}
