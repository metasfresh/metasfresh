package de.metas.invoicecandidate.spi.impl.aggregator.standard;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import de.metas.invoicecandidate.api.IInvoiceLineAttribute;

/**
 * Implementation of {@link IInvoiceLineAttributeAggregator} which retains only common attributes.
 *
 * i.e. if an {@link IInvoiceLineAttribute} is added
 * and we already have another {@link IInvoiceLineAttribute} with same attribute name but different attribute value,
 * then that attribute won't be part of the aggregation result because it's considered a duplicate.
 *
 * @author tsa
 *
 */
public class CommonInvoiceLineAttributeAggregator implements IInvoiceLineAttributeAggregator
{
	private Set<IInvoiceLineAttribute> currentResult = null;

	@Override
	public Set<IInvoiceLineAttribute> aggregate()
	{
		if (currentResult == null)
		{
			return ImmutableSet.of();
		}
		else
		{
			return ImmutableSet.copyOf(currentResult);
		}
	}

	@Override
	public IInvoiceLineAttributeAggregator addAll(final Set<IInvoiceLineAttribute> invoiceLineAttributesToAdd)
	{
		// NOTE: we also consider empty sets because those shall also count.
		// Adding an empty set will turn our result to be an empty set, for good.

		// Case: current result is not set, so we are actually adding the first set
		if (currentResult == null)
		{
			currentResult = ImmutableSet.copyOf(invoiceLineAttributesToAdd);
		}
		// Case: already have a current result, so we just need to intersect it with the set we got as parameter.
		else
		{
			currentResult = Sets.intersection(currentResult, invoiceLineAttributesToAdd);
		}

		return this;
	}
}
