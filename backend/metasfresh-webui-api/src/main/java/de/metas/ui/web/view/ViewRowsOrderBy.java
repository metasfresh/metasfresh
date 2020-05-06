package de.metas.ui.web.view;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderBys;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
@ToString
public final class ViewRowsOrderBy
{
	public static ViewRowsOrderBy of(final List<DocumentQueryOrderBy> orderBys, @NonNull final JSONOptions jsonOpts)
	{
		return new ViewRowsOrderBy(orderBys, jsonOpts);
	}

	public static ViewRowsOrderBy parseString(final String orderBysListStr, @NonNull final JSONOptions jsonOpts)
	{
		final List<DocumentQueryOrderBy> orderBys = DocumentQueryOrderBy.parseOrderBysList(orderBysListStr);
		return of(orderBys, jsonOpts);
	}

	public static ViewRowsOrderBy empty(@NonNull final JSONOptions jsonOpts)
	{
		return new ViewRowsOrderBy(ImmutableList.of(), jsonOpts);
	}

	private final ImmutableList<DocumentQueryOrderBy> orderBys;
	private final JSONOptions jsonOpts;

	private ViewRowsOrderBy(final List<DocumentQueryOrderBy> orderBys, @NonNull final JSONOptions jsonOpts)
	{
		this.orderBys = orderBys != null ? ImmutableList.copyOf(orderBys) : ImmutableList.of();
		this.jsonOpts = jsonOpts;
	}

	public boolean isEmpty()
	{
		return orderBys.isEmpty();
	}

	public ImmutableList<DocumentQueryOrderBy> toDocumentQueryOrderByList()
	{
		return orderBys;
	}

	public ViewRowsOrderBy withOrderBys(final List<DocumentQueryOrderBy> orderBys)
	{
		if (Objects.equals(this.orderBys, orderBys))
		{
			return this;
		}
		else
		{
			return new ViewRowsOrderBy(orderBys, jsonOpts);
		}
	}

	public <T extends IViewRow> Comparator<T> toComparator()
	{
		return DocumentQueryOrderBys.asComparator(orderBys, jsonOpts);
	}

	public <T extends IViewRow> Comparator<T> toComparatorOrNull()
	{
		return !orderBys.isEmpty() ? toComparator() : null;
	}

}
