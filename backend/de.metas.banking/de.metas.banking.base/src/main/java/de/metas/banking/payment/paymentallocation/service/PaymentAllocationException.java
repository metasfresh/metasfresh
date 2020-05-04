package de.metas.banking.payment.paymentallocation.service;

/*
 * #%L
 * de.metas.banking.swingui
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


import org.adempiere.exceptions.AdempiereException;

/**
 * Exception thrown by {@link PaymentAllocationBuilder} in case of any failure.
 * 
 * @author tsa
 *
 */
public class PaymentAllocationException extends AdempiereException
{
	public static final PaymentAllocationException wrapIfNeeded(final Throwable throwable)
	{
		final Throwable cause = extractCause(throwable);
		if (cause instanceof PaymentAllocationException)
		{
			return (PaymentAllocationException)cause;
		}

		return new PaymentAllocationException(cause.getLocalizedMessage(), cause);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -6596237632566193452L;

	public PaymentAllocationException(final String message)
	{
		super(message);
	}

	public PaymentAllocationException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
