package de.metas.ui.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder;
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder.SqlCreateSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
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
	public static final SqlViewRowIdsOrderedSelectionFactory of(final SqlViewBinding viewBinding)
	{
		return new SqlViewRowIdsOrderedSelectionFactory(viewBinding);
	}

	private static final Logger logger = LogManager.getLogger(SqlViewRowIdsOrderedSelectionFactory.class);

	private final SqlViewBinding viewBinding;

	private SqlViewRowIdsOrderedSelectionFactory(@NonNull final SqlViewBinding viewBinding)
	{
		this.viewBinding = viewBinding;
	}

	private SqlViewSelectionQueryBuilder newSqlViewSelectionQueryBuilder()
	{
		return SqlViewSelectionQueryBuilder.newInstance(viewBinding);
	}

	@Override
	public String getSqlWhereClause(final ViewId viewId, final DocumentIdsSelection rowIds)
	{
		return newSqlViewSelectionQueryBuilder().buildSqlWhereClause(viewId.getViewId(), rowIds);
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelection(final ViewEvaluationCtx viewEvalCtx, final ViewId viewId, final List<DocumentFilter> filters, final List<DocumentQueryOrderBy> orderBys)
	{
		final UserRolePermissionsKey permissionsKey = viewEvalCtx.getPermissionsKey();
		final IUserRolePermissions permissions = Services.get(IUserRolePermissionsDAO.class).retrieveUserRolePermissions(permissionsKey);
		final int queryLimit = permissions.getConstraint(WindowMaxQueryRecordsConstraint.class)
				.or(WindowMaxQueryRecordsConstraint.DEFAULT)
				.getMaxQueryRecordsPerRole();

		//
		//
		final SqlCreateSelection sqlCreates = newSqlViewSelectionQueryBuilder().buildSqlCreateSelectionFrom(viewEvalCtx, viewId, filters, orderBys, queryLimit);
		logger.trace("Creating selection using {}", sqlCreates);

		//
		// Create selection lines if any => insert into T_WEBUI_ViewSelectionLine
		if (sqlCreates.getSqlCreateSelectionLines() != null)
		{
			final SqlAndParams sqlCreateSelectionLines = sqlCreates.getSqlCreateSelectionLines();
			final Stopwatch stopwatch = Stopwatch.createStarted();
			final long linesCount = DB.executeUpdateEx(sqlCreateSelectionLines.getSql(), sqlCreateSelectionLines.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
			logger.trace("Created selection lines {}, linesCount={}, duration={}", viewId, linesCount, stopwatch);
		}

		//
		// Create selection rows => insert into T_WEBUI_ViewSelection
		final long rowsCount;
		{
			final SqlAndParams sqlCreateSelection = sqlCreates.getSqlCreateSelection();
			final Stopwatch stopwatch = Stopwatch.createStarted();
			rowsCount = DB.executeUpdateEx(sqlCreateSelection.getSql(), sqlCreateSelection.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
			logger.trace("Created selection {}, rowsCount={}, duration={}", viewId, rowsCount, stopwatch);
		}

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

		final int rowsCount;
		final SqlViewSelectionQueryBuilder viewQueryBuilder = newSqlViewSelectionQueryBuilder();
		if (viewQueryBuilder.hasGroupingFields())
		{
			final SqlAndParams sqlCreateSelectionLines = viewQueryBuilder.buildSqlCreateSelectionLinesFromSelectionLines(viewEvalCtx, newViewId, fromSelectionId);
			final int linesCount = DB.executeUpdateEx(sqlCreateSelectionLines.getSql(), sqlCreateSelectionLines.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);

			if (linesCount > 0)
			{
				final SqlAndParams sqlCreateSelection = viewQueryBuilder.buildSqlCreateSelectionFromSelectionLines(viewEvalCtx, newViewId, orderBys);
				rowsCount = DB.executeUpdateEx(sqlCreateSelection.getSql(), sqlCreateSelection.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
			}
			else
			{
				rowsCount = 0;
			}
		}
		else
		{
			final SqlAndParams sqlCreateSelection = viewQueryBuilder.buildSqlCreateSelectionFromSelection(viewEvalCtx, newViewId, fromSelectionId, orderBys);
			rowsCount = DB.executeUpdateEx(sqlCreateSelection.getSql(), sqlCreateSelection.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
		}

		return ViewRowIdsOrderedSelection.builder()
				.setViewId(newViewId)
				.setSize(rowsCount)
				.setOrderBys(orderBys)
				.setQueryLimit(fromSelection.getQueryLimit())
				.build();
	}

	@Override
	public ViewRowIdsOrderedSelection addRowIdsToSelection(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			// nothing changed
			return selection;
		}
		else if (rowIds.isAll())
		{
			throw new IllegalArgumentException("Cannot add ALL to selection");
		}

		//
		// Add
		boolean hasChanges = false;
		final String selectionId = selection.getSelectionId();
		// TODO: add all rowIds in one query!!! Not so urgent because usually there are added just a couple of rowIds, not much
		for (final DocumentId rowId : rowIds.toSet())
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
	public ViewRowIdsOrderedSelection removeRowIdsFromSelection(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
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

	@Override
	public boolean containsAnyOfRowIds(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return false;
		}

		final List<Object> sqlParams = new ArrayList<>();
		final String sqlCount = newSqlViewSelectionQueryBuilder().buildSqlCount(sqlParams, selection.getSelectionId(), rowIds);
		final int count = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sqlCount, sqlParams.toArray());
		return count > 0;
	}

	public <T> IQueryFilter<T> createQueryFilter(final String selectionId)
	{
		return newSqlViewSelectionQueryBuilder().buildInSelectionQueryFilter(selectionId);
	}

	@Override
	public void deleteSelection(@NonNull final ViewId viewId)
	{
		final String selectionId = viewId.getViewId();
		final SqlViewSelectionQueryBuilder viewQueryBuilder = newSqlViewSelectionQueryBuilder();

		// Delete selection lines
		{
			final String sql = viewQueryBuilder.buildSqlDeleteSelectionLines(selectionId);
			final int countDeleted = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
			logger.trace("Delete {} selection lines for {}", countDeleted, selectionId);
		}

		// Delete selection rows
		{
			final String sql = viewQueryBuilder.buildSqlDeleteSelection(selectionId);
			final int countDeleted = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
			logger.trace("Delete {} selection rows for {}", countDeleted, selectionId);
		}
	}

	@Override
	public void scheduleDeleteSelections(final Set<String> viewIds)
	{
		SqlViewSelectionToDeleteHelper.scheduleDeleteSelections(viewIds);
	}
}
