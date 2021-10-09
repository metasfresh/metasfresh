package de.metas.ui.web.view;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.permissions.WindowMaxQueryRecordsConstraint;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewKeyColumnNamesMap;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder;
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder.SqlCreateSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

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
	public static SqlViewRowIdsOrderedSelectionFactory of(final SqlViewBinding viewBinding)
	{
		return new SqlViewRowIdsOrderedSelectionFactory(viewBinding);
	}

	private static final Logger logger = LogManager.getLogger(SqlViewRowIdsOrderedSelectionFactory.class);
	private final IUserRolePermissionsDAO userRolePermissionsRepo = Services.get(IUserRolePermissionsDAO.class);

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
	public SqlViewRowsWhereClause getSqlWhereClause(
			@NonNull final ViewId viewId,
			@NonNull final DocumentIdsSelection rowIds)
	{
		return newSqlViewSelectionQueryBuilder().buildSqlWhereClause(viewId.getViewId(), rowIds);
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelection(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId viewId,
			final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final boolean applySecurityRestrictions,
			final SqlDocumentFilterConverterContext context)
	{
		final QueryLimit queryLimit = extractQueryLimit(viewEvalCtx);

		//
		//
		final SqlCreateSelection sqlCreates = newSqlViewSelectionQueryBuilder()
				.applySecurityRestrictions(applySecurityRestrictions)
				.buildSqlCreateSelectionFrom(viewEvalCtx, viewId, filters, orderBys, queryLimit, context);
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
				.viewId(viewId)
				.size(rowsCount)
				.orderBys(orderBys)
				.queryLimit(queryLimit)
				.build();
	}

	private QueryLimit extractQueryLimit(final ViewEvaluationCtx viewEvalCtx)
	{
		final UserRolePermissionsKey permissionsKey = viewEvalCtx.getPermissionsKey();
		final IUserRolePermissions permissions = userRolePermissionsRepo.getUserRolePermissions(permissionsKey);
		return QueryLimit.ofInt(
				permissions.getConstraint(WindowMaxQueryRecordsConstraint.class)
						.orElse(WindowMaxQueryRecordsConstraint.DEFAULT)
						.getMaxQueryRecordsPerRole());
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelectionFromSelection(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewRowIdsOrderedSelection fromSelection,
			@NonNull final DocumentFilterList filters,
			@NonNull final DocumentQueryOrderByList orderBys,
			@NonNull final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		final WindowId windowId = fromSelection.getWindowId();
		final String fromSelectionId = fromSelection.getSelectionId();
		final ViewId newViewId = ViewId.random(windowId);

		final int rowsCount;
		final SqlViewSelectionQueryBuilder viewQueryBuilder = newSqlViewSelectionQueryBuilder();
		if (viewQueryBuilder.hasGroupingFields())
		{
			// TODO: implement filters support when using groupping fields
			if (!filters.isEmpty())
			{
				throw new AdempiereException("Filters are not yet supported when using groupping fields")
						.appendParametersToMessage()
						.setParameter("filters", filters);
			}

			final SqlAndParams sqlCreateSelectionLines = viewQueryBuilder.buildSqlCreateSelectionLinesFromSelectionLines(newViewId, fromSelectionId);
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
			final SqlAndParams sqlCreateSelection = viewQueryBuilder.buildSqlCreateSelectionFromSelection(viewEvalCtx, newViewId, fromSelectionId, filters, orderBys, filterConverterCtx);
			rowsCount = DB.executeUpdateEx(sqlCreateSelection.getSql(), sqlCreateSelection.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
		}

		return ViewRowIdsOrderedSelection.builder()
				.viewId(newViewId)
				.size(rowsCount)
				.orderBys(orderBys)
				.queryLimit(fromSelection.getQueryLimit())
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
			final SqlAndParams sqlAdd = newSqlViewSelectionQueryBuilder().buildSqlAddRowIdsFromSelection(selectionId, rowId);
			final int added = DB.executeUpdateEx(sqlAdd.getSql(), sqlAdd.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
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

		return selection.withSize(size);
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
			final SqlAndParams sqlDelete = newSqlViewSelectionQueryBuilder().buildSqlDeleteRowIdsFromSelection(selection.getSelectionId(), rowIds);
			if(sqlDelete == null)
			{
				return selection;
			}

			final int deleted = DB.executeUpdateEx(sqlDelete.getSql(), sqlDelete.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
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

		return selection.withSize(size);
	}

	private final int retrieveSize(final String selectionId)
	{
		final SqlAndParams sqlCount = newSqlViewSelectionQueryBuilder().buildSqlRetrieveSize(selectionId);
		final int size = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sqlCount.getSql(), sqlCount.getSqlParams());
		return size <= 0 ? 0 : size;
	}

	@Override
	public boolean containsAnyOfRowIds(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return false;
		}

		final SqlAndParams sqlCount = newSqlViewSelectionQueryBuilder().buildSqlCount(selection.getSelectionId(), rowIds);
		final int count = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sqlCount.getSql(), sqlCount.getSqlParamsArray());
		return count > 0;
	}

	@Override
	public void deleteSelections(@NonNull final Set<String> selectionIds)
	{
		if (selectionIds.isEmpty())
		{
			return;
		}

		final SqlViewSelectionQueryBuilder viewQueryBuilder = newSqlViewSelectionQueryBuilder();

		// Delete selection lines
		{
			final SqlAndParams sql = viewQueryBuilder.buildSqlDeleteSelectionLines(selectionIds);
			final int countDeleted = DB.executeUpdateEx(sql.getSql(), sql.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
			logger.trace("Delete {} selection lines for {}", countDeleted, selectionIds);
		}

		// Delete selection rows
		{
			final SqlAndParams sql = viewQueryBuilder.buildSqlDeleteSelection(selectionIds);
			final int countDeleted = DB.executeUpdateEx(sql.getSql(), sql.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);
			logger.trace("Delete {} selection rows for {}", countDeleted, selectionIds);
		}
	}

	@Override
	public void scheduleDeleteSelections(@NonNull final Set<String> selectionIds)
	{
		SqlViewSelectionToDeleteHelper.scheduleDeleteSelections(selectionIds);
	}

	public static Set<DocumentId> retrieveRowIdsForLineIds(
			@NonNull SqlViewKeyColumnNamesMap keyColumnNamesMap,
			final ViewId viewId,
			final Set<Integer> lineIds)
	{
		final SqlAndParams sqlAndParams = SqlViewSelectionQueryBuilder.buildSqlSelectRowIdsForLineIds(keyColumnNamesMap, viewId.getViewId(), lineIds);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlAndParams.getSql(), ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlAndParams.getSqlParams());
			rs = pstmt.executeQuery();

			final ImmutableSet.Builder<DocumentId> rowIds = ImmutableSet.builder();
			while (rs.next())
			{
				final DocumentId rowId = keyColumnNamesMap.retrieveRowId(rs, "", false);
				rowIds.add(rowId);
			}
			return rowIds.build();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sqlAndParams.getSql(), sqlAndParams.getSqlParams());
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
