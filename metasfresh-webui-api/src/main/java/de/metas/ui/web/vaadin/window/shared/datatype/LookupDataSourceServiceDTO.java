package de.metas.ui.web.vaadin.window.shared.datatype;

import java.util.List;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public abstract class LookupDataSourceServiceDTO
{
	public static final LookupDataSourceServiceDTO cast(final Object valueObj)
	{
		return (LookupDataSourceServiceDTO)valueObj;
	}

	public static final int SIZE_InvalidFilter = -100;

	/**
	 * @param filter
	 * @return size or {@link #SIZE_InvalidFilter} if the filter is not valid
	 */
	public abstract int sizeIfValidFilter(final String filter);

	/**
	 * 
	 * @param filter
	 * @param firstRow
	 * @param pageLength
	 * @return {@link LookupValue}s list or <code>null</code> if the filter is not valid
	 */
	public abstract List<LookupValue> findEntitiesIfValidFilter(final String filter, final int firstRow, final int pageLength);
}
