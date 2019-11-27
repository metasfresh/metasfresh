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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;

/**
 * Exception thrown by {@link PaymentAllocationBuilder} when some payable documents could not be allocated.
 * 
 * @author tsa
 *
 */
public class PayableDocumentNotAllocatedException extends PaymentAllocationException
{
	private static final long serialVersionUID = 1L;
	private static final String MSG = "PaymentAllocation.CannotAllocatePayableDocumentsException";

	private final Collection<PayableDocument> payables;

	PayableDocumentNotAllocatedException(final Collection<PayableDocument> payables)
	{
		super("");
		this.payables = ImmutableList.copyOf(payables);
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();

		if (payables != null && !payables.isEmpty())
		{
			for (final PayableDocument payable : payables)
			{
				if (payable == null)
				{
					continue;
				}
				if (!message.isEmpty())
				{
					message.append(", ");
				}
				message.append(payable.getDocumentNo());
			}
		}

		message.insertFirstADMessage(MSG);
		return message.build();
	}

}
