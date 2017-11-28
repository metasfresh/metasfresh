package de.metas.ui.web.window.descriptor;

import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import lombok.Builder;
import lombok.Value;

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
	private final int seqNo;
	private final boolean rangeFilter;
	private final boolean showFilterIncrementButtons;

	public static final String AUTOFILTER_INITIALVALUE_DATE_NOW = DocumentFilterParamDescriptor.AUTOFILTER_INITIALVALUE_DATE_NOW;
	private final Object autoFilterInitialValue;

	@Builder
	public DocumentFieldDefaultFilterDescriptor(
			final int seqNo,
			final boolean rangeFilter,
			final boolean showFilterIncrementButtons,
			final Object autoFilterInitialValue)
	{
		this.seqNo = seqNo > 0 ? seqNo : Integer.MAX_VALUE;
		this.rangeFilter = rangeFilter;
		this.showFilterIncrementButtons = showFilterIncrementButtons;
		this.autoFilterInitialValue = autoFilterInitialValue;
	}
}
