package de.metas.ui.web.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryFilter;
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
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;
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
	public static final SqlViewRowIdsOrderedSelectionFactory of(final SqlEntityBinding sqlEntityBinding)
	{
		return new SqlViewRowIdsOrderedSelectionFactory(sqlEntityBinding);
	}

	private static final Logger logger = LogManager.getLogger(SqlViewRowIdsOrderedSelectionFactory.class);

	private final SqlEntityBinding sqlEntityBinding;

	private SqlViewRowIdsOrderedSelectionFactory(@NonNull final SqlEntityBinding sqlEntityBinding)
	{
		this.sqlEntityBinding = sqlEntityBinding;
	}

	private SqlViewSelectionQueryBuilder newSqlViewSelectionQueryBuilder()
	{
		return SqlViewSelectionQueryBuilder.newInstance(sqlEntityBinding);
	}

	@Override
	public String getSqlWhereClause(final ViewId viewId, final Collection<DocumentId> rowIds)
	{
		return newSqlViewSelectionQueryBuilder().buildSqlWhereClause(viewId.getViewId(), rowIds);
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelection(final ViewEvaluationCtx viewEvalCtx, final WindowId windowId, final List<DocumentFilter> filters, final List<DocumentQueryOrderBy> orderBys)
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
		final String sql = newSqlViewSelectionQueryBuilder().buildSqlCreateSelectionFrom(sqlParams, viewEvalCtx, viewId, filters, orderBys, queryLimit);

		//
		// Execute it, so we insert in our T_WEBUI_ViewSelection
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final long rowsCount = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		stopwatch.stop();
		logger.trace("Created selection {}, rowsCount={}, duration={} \n SQL: {} -- {}", viewId, rowsCount, stopwatch, sql, sqlParams);

		return ViewRowIdsOrderedSelection.builder()
				.setViewId(viewId)
				.setSize(rowsCount)
				.setOrderBys(orderBys)
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

		final String sqlFinal = newSqlViewSelectionQueryBuilder().buildSqlCreateSelectionFromSelection(viewEvalCtx, orderBys);

		final int rowsCount = DB.executeUpdateEx(sqlFinal, new Object[] { newSelectionId, fromSelectionId }, ITrx.TRXNAME_ThreadInherited);

		return ViewRowIdsOrderedSelection.builder()
				.setViewId(newViewId)
				.setSize(rowsCount)
				.setOrderBys(orderBys)
				.setQueryLimit(fromSelection.getQueryLimit())
				.build();
	}

	@Override
	public ViewRowIdsOrderedSelection addRowIdsToSelection(final ViewRowIdsOrderedSelection selection, final Collection<DocumentId> rowIds)
	{
		if (rowIds == null || rowIds.isEmpty())
		{
			// nothing changed
			return selection;
		}

		//
		// Add
		boolean hasChanges = false;
		final String selectionId = selection.getSelectionId();
		for (final DocumentId rowId : rowIds)
		{
			final List<Object> sqlParams = new ArrayList<>();
			final String sqlAdd = newSqlViewSelectionQueryBuilder().buildSqlAddRowIdsFromSelection(sqlParams, selectionId, rowId);
			final int added = DB.executeUpdateEx(sqlAdd, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
			if (added <= 0)
			{
				continue;
			}

			hasChanges = true;
		}
		if (!hasChanges)
		{
			// nothing changed
			return selection;
		}

		//
		// Retrieve current size
		// NOTE: we are querying it instead of adding how many we added to current "size" because it might be that the size is staled
		final int size = retrieveSize(selectionId);

		return selection.toBuilder()
				.setSize(size)
				.build();
	}

	@Override
	public ViewRowIdsOrderedSelection removeRowIdsFromSelection(final ViewRowIdsOrderedSelection selection, final Collection<DocumentId> rowIds)
	{
		if (rowIds == null || rowIds.isEmpty())
		{
			// nothing changed
			return selection;
		}

		//
		// Delete
		{
			final String sqlDelete = newSqlViewSelectionQueryBuilder().buildSqlDeleteRowIdsFromSelection(selection.getSelectionId(), rowIds);
			final int deleted = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
			if (deleted <= 0)
			{
				// nothing changed
				return selection;
			}
		}

		//
		// Retrieve current size
		// NOTE: we are querying it instead of subtracting "deleted" from current "size" because it might be that the size is staled
		final int size = retrieveSize(selection.getSelectionId());

		return selection.toBuilder()
				.setSize(size)
				.build();
	}

	private final int retrieveSize(final String selectionId)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final String sqlCount = newSqlViewSelectionQueryBuilder().buildSqlRetrieveSize(sqlParams, selectionId);
		final int size = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sqlCount, sqlParams);
		return size <= 0 ? 0 : size;
	}

	public boolean containsAnyOfRowIds(final String selectionId, final Collection<DocumentId> rowIds)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final String sqlCount = newSqlViewSelectionQueryBuilder().buildSqlCount(sqlParams, selectionId, rowIds);
		final int count = DB.executeUpdateEx(sqlCount, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		return count > 0;
	}

	public <T> IQueryFilter<T> createQueryFilter(final String selectionId)
	{
		return newSqlViewSelectionQueryBuilder().buildInSelectionQueryFilter(selectionId);
	}
}
