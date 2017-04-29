package de.metas.ui.web.view.descriptor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.util.DB;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.view.IViewRowIdsOrderedSelectionFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.window.datatypes.WindowId;
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

public class SqlViewRowIdsOrderedSelectionFactory implements IViewRowIdsOrderedSelectionFactory
{
	private final String sqlCreateFromViewId;
	private final Map<String, String> sqlOrderBysByFieldName;

	public SqlViewRowIdsOrderedSelectionFactory(final String sqlCreateFromViewId, final Map<String, String> sqlOrderBysByFieldName)
	{
		super();
		this.sqlCreateFromViewId = sqlCreateFromViewId;
		this.sqlOrderBysByFieldName = ImmutableMap.copyOf(sqlOrderBysByFieldName);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("sql", sqlCreateFromViewId)
				.add("sqlOrderBysByFieldName", sqlOrderBysByFieldName)
				.toString();
	}

	@Override
	public ViewRowIdsOrderedSelection createFromView(final ViewRowIdsOrderedSelection fromView, final List<DocumentQueryOrderBy> orderBys)
	{
		final WindowId windowId = fromView.getWindowId();
		final String fromSelectionId = fromView.getSelectionId();
		
		final ViewId newViewId = ViewId.random(windowId);
		final String newSelectionId = newViewId.getViewId();
		
		final String sqlOrderBys = buildOrderBys(orderBys); // NOTE: we assume it's not empty!
		final String sqlFinal = sqlCreateFromViewId.replace(SqlViewBinding.PLACEHOLDER_OrderBy, sqlOrderBys);
		final int rowCount = DB.executeUpdateEx(sqlFinal, new Object[] { newSelectionId, fromSelectionId }, ITrx.TRXNAME_ThreadInherited);
		
		return ViewRowIdsOrderedSelection.builder()
				.setViewId(newViewId)
				.setSize(rowCount)
				.setOrderBys(orderBys)
				.setQueryLimit(fromView.getQueryLimit(), fromView.isQueryLimitHit())
				.build();
	}

	private final String buildOrderBys(final List<DocumentQueryOrderBy> orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return "";
		}

		return orderBys
				.stream()
				.map(orderBy -> buildOrderBy(orderBy))
				.filter(orderBy -> !Check.isEmpty(orderBy, true))
				.collect(Collectors.joining(", "));
	}

	private final String buildOrderBy(final DocumentQueryOrderBy orderBy)
	{
		final String fieldName = orderBy.getFieldName();
		final String fieldSql = sqlOrderBysByFieldName.get(fieldName);
		if (fieldSql == null)
		{
			throw new DBException("No SQL field mapping found for: " + fieldName + ". Available fields are: " + sqlOrderBysByFieldName.keySet());
		}

		return "(" + fieldSql + ") " + (orderBy.isAscending() ? " ASC" : " DESC");
	}
}
