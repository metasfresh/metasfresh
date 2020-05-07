package org.compiere.model;

import lombok.Builder;
import lombok.Value;

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
public final class GridFieldDefaultFilterDescriptor
{
	private final int seqNo;
	private final boolean rangeFilter;
	private final boolean showFilterIncrementButtons;
	private final String defaultValue;

	@Builder
	public GridFieldDefaultFilterDescriptor(
			final int seqNo,
			final boolean rangeFilter,
			final boolean showFilterIncrementButtons,
			final String defaultValue)
	{
		this.seqNo = seqNo;
		this.rangeFilter = rangeFilter;
		this.showFilterIncrementButtons = showFilterIncrementButtons;
		this.defaultValue = defaultValue;
	}
}
