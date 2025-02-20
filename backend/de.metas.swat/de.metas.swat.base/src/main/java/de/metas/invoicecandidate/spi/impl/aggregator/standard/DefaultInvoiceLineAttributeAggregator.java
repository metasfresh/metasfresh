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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import lombok.NonNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DefaultInvoiceLineAttributeAggregator implements IInvoiceLineAttributeAggregator
{
	private Set<IInvoiceLineAttribute> currentIntersectionResult = null;
	private final Set<IInvoiceLineAttribute> currentUnionResult = new LinkedHashSet<>();

	@Override
	public List<IInvoiceLineAttribute> aggregate()
	{
		final ImmutableSet<IInvoiceLineAttribute> finalIntersectionResult = currentIntersectionResult == null ? ImmutableSet.of() : ImmutableSet.copyOf(currentIntersectionResult);

		final LinkedHashSet<IInvoiceLineAttribute> result = new LinkedHashSet<>(currentUnionResult);
		result.addAll(finalIntersectionResult);
		
		return ImmutableList.copyOf(result);
	}

	@Override
	public void addToIntersection(@NonNull final Set<IInvoiceLineAttribute> invoiceLineAttributesToAdd)
	{
		// NOTE: we also consider empty sets because those shall also count.
		// Adding an empty set will turn our result to be an empty set, for good.

		// Case: current result is not set, so we are actually adding the first set
		if (currentIntersectionResult == null)
		{
			currentIntersectionResult = new LinkedHashSet<>(invoiceLineAttributesToAdd);
		}
		// Case: already have a current result, so we just need to intersect it with the set we got as parameter.
		else
		{
			currentIntersectionResult = Sets.intersection(currentIntersectionResult, invoiceLineAttributesToAdd);
		}
	}

	@Override
	public void addToUnion(@NonNull final List<IInvoiceLineAttribute> invoiceLineAttributesToAdd)
	{
		currentUnionResult.addAll(invoiceLineAttributesToAdd);
	}
}
