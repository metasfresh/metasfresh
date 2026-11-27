/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * All {@link InvoiceLineTaxLocationProvider}s; a plain JUnit test without spring context gets none unless it registers this bean itself.
 * The providers are asked in no particular order; any non-empty result is used.
 */
@Component
public class InvoiceLineTaxLocationProviders
{
	@NonNull private final ImmutableList<InvoiceLineTaxLocationProvider> providers;

	public InvoiceLineTaxLocationProviders(@NonNull final Optional<List<InvoiceLineTaxLocationProvider>> providers)
	{
		this.providers = providers.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
	}

	public static InvoiceLineTaxLocationProviders of(@NonNull final List<InvoiceLineTaxLocationProvider> providers)
	{
		return new InvoiceLineTaxLocationProviders(Optional.of(providers));
	}

	/**
	 * @return the spring bean; before the spring context is up (or in a plain JUnit test without registered instance) an instance without providers
	 */
	public static InvoiceLineTaxLocationProviders get()
	{
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(InvoiceLineTaxLocationProviders.class, () -> new InvoiceLineTaxLocationProviders(Optional.empty()));
	}

	@VisibleForTesting
	public static InvoiceLineTaxLocationProviders newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return get();
	}

	public Optional<TaxLocationAndFromCountry> getTaxLocation(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		for (final InvoiceLineTaxLocationProvider provider : providers)
		{
			final Optional<TaxLocationAndFromCountry> taxLocation = provider.getTaxLocation(invoiceLine);
			if (taxLocation.isPresent())
			{
				return taxLocation;
			}
		}
		return Optional.empty();
	}
}
