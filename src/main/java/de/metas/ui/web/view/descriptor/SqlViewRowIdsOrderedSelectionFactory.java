package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.Evaluatees;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.view.IViewRowIdsOrderedSelectionFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.sql.SqlDocumentOrderByBuilder;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

		final List<Object> sqlParams = new ArrayList<>();
		final String sqlFinal = buildSqlCreateFromViewId(sqlParams, newSelectionId, fromSelectionId, orderBys);
		final int rowsCount = DB.executeUpdateEx(sqlFinal, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);

		return ViewRowIdsOrderedSelection.builder()
				.setViewId(newViewId)
				.setSize(rowsCount)
				.setOrderBys(orderBys)
				.setQueryLimit(fromView.getQueryLimit())
				.build();
	}

	private String buildSqlCreateFromViewId(final List<Object> sqlParams, final String newSelectionId, final String fromSelectionId, final List<DocumentQueryOrderBy> orderBys)
	{
		final String sqlOrderBys = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy)
				.buildSqlOrderBy(orderBys)
				.evaluate(Evaluatees.empty(), OnVariableNotFound.Fail); // assume constant string
		final String sqlFinal = sqlCreateFromViewId.replace(SqlViewBinding.PLACEHOLDER_OrderBy, sqlOrderBys);
		sqlParams.add(newSelectionId);
		sqlParams.add(fromSelectionId);
		return sqlFinal;
	}

	private final IStringExpression getFieldOrderBy(final String fieldName)
	{
		final String fieldSql = sqlOrderBysByFieldName.get(fieldName);
		if (fieldSql == null)
		{
			throw new DBException("No SQL field mapping found for: " + fieldName + ". Available fields are: " + sqlOrderBysByFieldName.keySet());
		}

		return ConstantStringExpression.of(fieldSql.trim());
	}
}
