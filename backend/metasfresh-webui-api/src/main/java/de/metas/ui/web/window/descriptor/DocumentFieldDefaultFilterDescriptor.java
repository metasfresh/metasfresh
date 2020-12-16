package de.metas.ui.web.window.descriptor;

import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.OptionalInt;

/*
 * #%L
 * metasfresh-webui-api
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
public class DocumentFieldDefaultFilterDescriptor
{
	boolean defaultFilter;
	int defaultFilterSeqNo;
	boolean rangeFilter;
	boolean showFilterIncrementButtons;
	Object autoFilterInitialValue;
	boolean showFilterInline;

	boolean facetFilter;
	int facetFilterSeqNo;
	OptionalInt maxFacetsToFetch;

	@Builder
	public DocumentFieldDefaultFilterDescriptor(
			final int seqNo,
			//
			final boolean defaultFilter,
			final int defaultFilterSeqNo,
			final boolean rangeFilter,
			final boolean showFilterIncrementButtons,
			final boolean showFilterInline,
			@Nullable final Object autoFilterInitialValue,
			//
			final boolean facetFilter,
			final int facetFilterSeqNo,
			@Nullable final OptionalInt maxFacetsToFetch)
	{
		if (!defaultFilter && !facetFilter)
		{
			throw new AdempiereException("defaultFilter or facetFilter shall be true");
		}

		if (defaultFilter)
		{
			this.defaultFilter = true;
			this.defaultFilterSeqNo = defaultFilterSeqNo > 0 ? defaultFilterSeqNo : Integer.MAX_VALUE;
			this.rangeFilter = rangeFilter;
			this.showFilterIncrementButtons = showFilterIncrementButtons;
			this.autoFilterInitialValue = autoFilterInitialValue;
			this.showFilterInline = showFilterInline;
		}
		else
		{
			this.defaultFilter = false;
			this.defaultFilterSeqNo = Integer.MAX_VALUE;
			this.rangeFilter = false;
			this.showFilterIncrementButtons = false;
			this.autoFilterInitialValue = null;
			this.showFilterInline = false;
		}

		if (facetFilter)
		{
			this.facetFilter = true;
			this.facetFilterSeqNo = facetFilterSeqNo > 0 ? facetFilterSeqNo : Integer.MAX_VALUE;
			this.maxFacetsToFetch = maxFacetsToFetch != null ? maxFacetsToFetch : OptionalInt.empty();
		}
		else
		{
			this.facetFilter = false;
			this.facetFilterSeqNo = Integer.MAX_VALUE;
			this.maxFacetsToFetch = OptionalInt.empty();
		}
	}
}
