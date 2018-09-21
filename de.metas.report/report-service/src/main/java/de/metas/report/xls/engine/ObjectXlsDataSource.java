package de.metas.report.xls.engine;

import java.util.Collection;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.report.jasper.server.base
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

public class ObjectXlsDataSource implements IXlsDataSource
{
	public static final <T> ObjectXlsDataSource of(final Collection<T> rows)
	{
		if (rows == null || rows.isEmpty())
		{
			return EMPTY;
		}
		return new ObjectXlsDataSource(rows);
	}

	public static final ObjectXlsDataSource EMPTY = new ObjectXlsDataSource(ImmutableList.of());

	private final Collection<?> rows;

	private ObjectXlsDataSource(final Collection<?> collection)
	{
		super();
		Check.assumeNotNull(collection, "collection not null");
		this.rows = collection;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("rows", rows)
				.toString();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Object> getRows()
	{
		return (Collection<Object>)rows;
	}

}
