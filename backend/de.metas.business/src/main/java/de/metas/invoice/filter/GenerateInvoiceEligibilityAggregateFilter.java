/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.invoice.filter;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateInvoiceEligibilityAggregateFilter
{
	private final List<IGenerateInvoiceEligibilityFilter> filterList;

	public GenerateInvoiceEligibilityAggregateFilter(@NonNull final List<IGenerateInvoiceEligibilityFilter> filterList)
	{
		this.filterList = filterList;
	}

	public boolean isEligible(@NonNull final Object model)
	{
		final List<IGenerateInvoiceEligibilityFilter> matchingFilters = filterList
				.stream()
				.filter(eligibilityFilter -> eligibilityFilter.getSupportedTypes().contains(model.getClass()))
				.collect(ImmutableList.toImmutableList());

		if (matchingFilters.isEmpty())
		{
			return true;
		}

		return matchingFilters.stream().allMatch(eligibilityFilter -> eligibilityFilter.isEligible(model));
	}
}
