package de.metas.ui.web.vaadin.window.shared.datatype;

import java.util.List;

import de.metas.ui.web.vaadin.window.model.LookupDataSource;

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

public class LookupDataSourceServiceDTO
{
	// TODO: the implementation of LookupDataSourceServiceDTO shall be simple serializable POJO and not an LookupDataSource wrapper 
	
	public static final LookupDataSourceServiceDTO of(final LookupDataSource lookupDataSource)
	{
		return new LookupDataSourceServiceDTO(lookupDataSource);
	}

	public static final LookupDataSourceServiceDTO cast(final Object valueObj)
	{
		return (LookupDataSourceServiceDTO)valueObj;
	}

	public static final int SIZE_InvalidFilter = -100;

	private final LookupDataSource lookupDataSource;

	private LookupDataSourceServiceDTO(final LookupDataSource lookupDataSource)
	{
		super();
		this.lookupDataSource = lookupDataSource;
	}

	/**
	 * @param filter
	 * @return size or {@link #SIZE_InvalidFilter} if the filter is not valid
	 */
	public int sizeIfValidFilter(final String filter)
	{
		if (!lookupDataSource.isValidFilter(filter))
		{
			return SIZE_InvalidFilter;
		}
		return lookupDataSource.size(filter);
	}

	/**
	 * 
	 * @param filter
	 * @param firstRow
	 * @param pageLength
	 * @return {@link LookupValue}s list or <code>null</code> if the filter is not valid
	 */
	public List<LookupValue> findEntitiesIfValidFilter(final String filter, final int firstRow, final int pageLength)
	{
		if (!lookupDataSource.isValidFilter(filter))
		{
			return null;
		}
		return lookupDataSource.findEntities(filter, firstRow, pageLength);
	}
}
