package de.metas.ui.web.view;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.model.DocumentQueryOrderBy;

/*
 * #%L
 * metasfresh-webui-api
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

@Immutable
public final class DocumentViewOrderedSelection
{
	private final String uuid;
	private final long size;
	private final ImmutableList<DocumentQueryOrderBy> orderBys;

	public DocumentViewOrderedSelection(final String uuid, final long size, final List<DocumentQueryOrderBy> orderBys)
	{
		super();
		this.uuid = uuid;
		this.size = size;
		this.orderBys = ImmutableList.copyOf(orderBys);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("uuid", uuid)
				.add("size", size)
				.add("orderBys", orderBys.isEmpty() ? null : orderBys)
				.toString();
	}

	public String getUuid()
	{
		return uuid;
	}

	public long getSize()
	{
		return size;
	}

	ImmutableList<DocumentQueryOrderBy> getOrderBys()
	{
		return orderBys;
	}
}