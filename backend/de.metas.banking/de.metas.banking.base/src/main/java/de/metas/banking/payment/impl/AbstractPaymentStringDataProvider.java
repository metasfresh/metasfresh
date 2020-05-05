package de.metas.banking.payment.impl;

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


import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.IPaymentStringDataProvider;

public abstract class AbstractPaymentStringDataProvider implements IPaymentStringDataProvider
{
	private final IPaymentString paymentString;

	public AbstractPaymentStringDataProvider(final IPaymentString paymentString)
	{
		this.paymentString = paymentString;
	}

	@Override
	public IPaymentString getPaymentString()
	{
		return paymentString;
	}
}
