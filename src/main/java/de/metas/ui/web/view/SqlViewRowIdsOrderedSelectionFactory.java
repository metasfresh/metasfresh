package de.metas.ui.web.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.security.permissions.WindowMaxQueryRecordsConstraint;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;

import de.metas.logging.LogManager;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;
import de.metas.ui.web.window.model.sql.SqlDocumentOrderByBuilder;
import lombok.NonNull;

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

public class SqlViewRowIdsOrderedSelectionFactory implements ViewRowIdsOrderedSelectionFactory
{

	private static final Logger logger = LogManager.getLogger(SqlViewRowIdsOrderedSelectionFactory.class);

	private final String sqlCreateFromViewId;
	private final List<DocumentQueryOrderBy> defaultOrderBys;
	private final SqlViewBinding _sqlBindings;

	SqlViewRowIdsOrderedSelectionFactory(@NonNull final SqlViewBinding sqlBindings)
	{
		this._sqlBindings = sqlBindings;
		sqlCreateFromViewId = sqlBindings.getSqlCreateSelectionFromSelection();

		defaultOrderBys = sqlBindings.getDefaultOrderBys();
	}

	private String getSqlCreateSelectionFrom( //
			final List<Object> sqlParams //
			, final ViewEvaluationCtx viewEvalCtx //
			, final ViewId newViewId //
			, final List<DocumentFilter> filters //
			, final int queryLimit //
	)
	{
		return _sqlBindings.getSqlCreateSelectionFrom(sqlParams, viewEvalCtx, newViewId, filters, queryLimit);
	}

	private final IStringExpression getFieldOrderBy(final String fieldName)
	{
		return _sqlBindings.getFieldOrderBy(fieldName);
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelection(final ViewEvaluationCtx viewEvalCtx, final WindowId windowId, final List<DocumentFilter> filters)
	{
		final ViewId viewId = ViewId.random(windowId);

		final UserRolePermissionsKey permissionsKey = viewEvalCtx.getPermissionsKey();
		final IUserRolePermissions permissions = Services.get(IUserRolePermissionsDAO.class).retrieveUserRolePermissions(permissionsKey);
		final int queryLimit = permissions.getConstraint(WindowMaxQueryRecordsConstraint.class)
				.or(WindowMaxQueryRecordsConstraint.DEFAULT)
				.getMaxQueryRecordsPerRole();

		//
		//
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = getSqlCreateSelectionFrom(sqlParams, viewEvalCtx, viewId, filters, queryLimit);

		//
		// Execute it, so we insert in our T_WEBUI_ViewSelection
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final long rowsCount = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		stopwatch.stop();
		logger.trace("Created selection {}, rowsCount={}, duration={} \n SQL: {} -- {}", viewId, rowsCount, stopwatch, sql, sqlParams);

		return ViewRowIdsOrderedSelection.builder()
				.setViewId(viewId)
				.setSize(rowsCount)
				.setOrderBys(defaultOrderBys)
				.setQueryLimit(queryLimit)
				.build();
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelectionFromSelection(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection fromSelection, final List<DocumentQueryOrderBy> orderBys)
	{
		final WindowId windowId = fromSelection.getWindowId();
		final String fromSelectionId = fromSelection.getSelectionId();

		final ViewId newViewId = ViewId.random(windowId);
		final String newSelectionId = newViewId.getViewId();

		final String sqlOrderBys = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy)
				.buildSqlOrderBy(orderBys)
				.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

		final String sqlFinal = sqlCreateFromViewId.replace(SqlViewBinding.PLACEHOLDER_OrderBy, sqlOrderBys);

		final int rowsCount = DB.executeUpdateEx(sqlFinal, new Object[] { newSelectionId, fromSelectionId }, ITrx.TRXNAME_ThreadInherited);

		return ViewRowIdsOrderedSelection.builder()
				.setViewId(newViewId)
				.setSize(rowsCount)
				.setOrderBys(orderBys)
				.setQueryLimit(fromSelection.getQueryLimit())
				.build();
	}
	
	@Override
	public String getSqlWhereClause(final ViewId viewId, final Collection<DocumentId> rowIds)
	{
		return _sqlBindings.getSqlWhereClause(viewId.getViewId(), rowIds);
	}

}
