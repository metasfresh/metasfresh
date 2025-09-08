package de.metas.ui.web.view;

import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Comparator;

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
	public static ViewRowsOrderBy of(final DocumentQueryOrderByList orderBys, @NonNull final JSONOptions jsonOpts)
	{
		return new ViewRowsOrderBy(orderBys, jsonOpts);
	}

	public static ViewRowsOrderBy parseString(final String orderBysListStr, @NonNull final JSONOptions jsonOpts)
	{
		final DocumentQueryOrderByList orderBys = DocumentQueryOrderByList.parse(orderBysListStr);
		return of(orderBys, jsonOpts);
	}

	public static ViewRowsOrderBy empty(@NonNull final JSONOptions jsonOpts)
	{
		return new ViewRowsOrderBy(DocumentQueryOrderByList.EMPTY, jsonOpts);
	}

	private final DocumentQueryOrderByList orderBys;
	private final JSONOptions jsonOpts;

	private ViewRowsOrderBy(final DocumentQueryOrderByList orderBys, @NonNull final JSONOptions jsonOpts)
	{
		this.orderBys = orderBys != null ? orderBys : DocumentQueryOrderByList.EMPTY;
		this.jsonOpts = jsonOpts;
	}

	public boolean isEmpty()
	{
		return orderBys.isEmpty();
	}

	public DocumentQueryOrderByList toDocumentQueryOrderByList()
	{
		return orderBys;
	}

	public ViewRowsOrderBy withOrderBys(final DocumentQueryOrderByList orderBys)
	{
		if (DocumentQueryOrderByList.equals(this.orderBys, orderBys))
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
		return orderBys.toComparator(IViewRow::getFieldValueAsComparable, jsonOpts);
	}

	public <T extends IViewRow> Comparator<T> toComparatorOrNull()
	{
		return !orderBys.isEmpty() ? toComparator() : null;
	}

}
