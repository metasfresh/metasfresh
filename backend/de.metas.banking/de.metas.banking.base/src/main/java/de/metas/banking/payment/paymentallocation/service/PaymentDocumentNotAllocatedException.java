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
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;

/**
 * Exception thrown by {@link PaymentAllocationBuilder} when some payment documents could not be allocated.
 *
 * @author tsa
 *
 */
@SuppressWarnings("serial")
public class PaymentDocumentNotAllocatedException extends PaymentAllocationException
{
	private static final AdMessageKey MSG = AdMessageKey.of("PaymentAllocation.CannotAllocatePayableDocumentsException");

	private final ImmutableList<PaymentDocument> payments;

	PaymentDocumentNotAllocatedException(final Collection<PaymentDocument> payments)
	{
		super();
		this.payments = ImmutableList.copyOf(payments);
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		return TranslatableStrings.builder()
				.appendADMessage(MSG)
				.append(toCommaSeparatedDocumentNos(payments))
				.build();
	}

	private static String toCommaSeparatedDocumentNos(final List<PaymentDocument> payments)
	{
		return payments.stream()
				.map(PaymentDocument::getDocumentNo)
				.collect(Collectors.joining(", "));
	}

}
