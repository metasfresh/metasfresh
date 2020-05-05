package de.metas.banking.bankstatement.match.api.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.compiere.model.I_C_Payment;

import de.metas.banking.bankstatement.match.api.IPaymentBatchFactory;
import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.bankstatement.match.spi.IPaymentBatchProvider;
import de.metas.banking.bankstatement.match.spi.impl.PaySelectionPaymentBatchProvider;
import de.metas.util.Check;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PaymentBatchFactory implements IPaymentBatchFactory
{
	private final CopyOnWriteArrayList<IPaymentBatchProvider> providers = new CopyOnWriteArrayList<>();

	public PaymentBatchFactory()
	{
		super();

		addPaymentBatchProvider(new PaySelectionPaymentBatchProvider());
	}

	@Override
	public IPaymentBatch retrievePaymentBatch(final I_C_Payment payment)
	{
		Check.assumeNotNull(payment, "payment not null");

		for (final IPaymentBatchProvider provider : providers)
		{
			final IPaymentBatch paymentBatch = provider.retrievePaymentBatch(payment);
			if (paymentBatch != null)
			{
				return paymentBatch;
			}
		}

		return null;
	}

	@Override
	public void addPaymentBatchProvider(final IPaymentBatchProvider provider)
	{
		Check.assumeNotNull(provider, "provider not null");
		providers.addIfAbsent(provider);
	}

}
