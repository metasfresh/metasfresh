package de.metas.dunning.invoice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.invoice.InvoiceId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.dunning
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

@Service
public class InvoiceDueDateProviderService
{
	private final List<InvoiceDueDateProvider> invoiceDueDateProviders;
	private final PaymentTermBasedDueDateProvider defaultInvoicedueDateProvider;

	public InvoiceDueDateProviderService(
			@NonNull final Optional<List<InvoiceDueDateProvider>> invoiceDueDateProviders,
			@NonNull final PaymentTermBasedDueDateProvider defaultInvoicedueDateProvider)
	{
		this.invoiceDueDateProviders = invoiceDueDateProviders
				.orElse(ImmutableList.of())
				.stream()
				.filter(p -> !p.equals(defaultInvoicedueDateProvider))
				.collect(ImmutableList.toImmutableList());

		this.defaultInvoicedueDateProvider = defaultInvoicedueDateProvider;
	}

	public LocalDate provideDueDateFor(@NonNull final InvoiceId invoiceId)
	{
		for (final InvoiceDueDateProvider provider : invoiceDueDateProviders)
		{
			final LocalDate dueDate = provider.provideDueDateOrNull(invoiceId);
			if (dueDate != null)
			{
				return dueDate;
			}
		}

		return defaultInvoicedueDateProvider.provideDueDateOrNull(invoiceId);
	}
}
