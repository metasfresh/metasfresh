package org.compiere.model;

import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.OptionalInt;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
public class GridFieldDefaultFilterDescriptor
{
	boolean defaultFilter;
	int defaultFilterSeqNo;
	String operator;
	boolean showFilterIncrementButtons;
	boolean showFilterInline;
	String defaultValue;

	boolean facetFilter;
	int facetFilterSeqNo;
	OptionalInt maxFacetsToFetch;

	@Builder
	public GridFieldDefaultFilterDescriptor(
			final boolean defaultFilter,
			final int defaultFilterSeqNo,
			final String operator,
			final boolean showFilterIncrementButtons,
			final boolean showFilterInline,
			final String defaultValue,
			//
			final boolean facetFilter,
			final int facetFilterSeqNo,
			final int maxFacetsToFetch)
	{
		if (!defaultFilter && !facetFilter)
		{
			throw new AdempiereException("defaultFilter or facetFilter shall be true");
		}

		//
		// Default filter
		if (defaultFilter)
		{
			this.defaultFilter = true;
			this.defaultFilterSeqNo = defaultFilterSeqNo;
			this.operator = operator;
			this.showFilterIncrementButtons = showFilterIncrementButtons;
			this.showFilterInline = showFilterInline;
			this.defaultValue = defaultValue;
		}
		else
		{
			this.defaultFilter = false;
			this.defaultFilterSeqNo = 0;
			this.operator = null; // default
			this.showFilterIncrementButtons = false;
			this.showFilterInline = false;
			this.defaultValue = null;
		}

		//
		// Facet filter
		if (facetFilter)
		{
			this.facetFilter = true;
			this.facetFilterSeqNo = facetFilterSeqNo;
			this.maxFacetsToFetch = maxFacetsToFetch > 0 ? OptionalInt.of(maxFacetsToFetch) : OptionalInt.empty();
		}
		else
		{
			this.facetFilter = false;
			this.facetFilterSeqNo = 0;
			this.maxFacetsToFetch = OptionalInt.empty();
		}
	}
}
