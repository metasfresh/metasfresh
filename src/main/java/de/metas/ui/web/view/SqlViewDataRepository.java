package de.metas.ui.web.view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.security.permissions.WindowMaxQueryRecordsConstraint;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;
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

class SqlViewDataRepository implements IViewDataRepository
{
	private static final Logger logger = LogManager.getLogger(SqlViewDataRepository.class);

	private final SqlViewBinding sqlBindings;

	SqlViewDataRepository(@NonNull final SqlViewBinding sqlBindings)
	{
		this.sqlBindings = sqlBindings;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(sqlBindings).toString();
	}

	@Override
	public String getTableName()
	{
		return sqlBindings.getTableName();
	}

	@Override
	public String getSqlWhereClause(final ViewId viewId, final Collection<DocumentId> rowIds)
	{
		return sqlBindings.getSqlWhereClause(viewId.getViewId(), rowIds);
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
		final String sql = sqlBindings.getSqlCreateSelectionFrom(sqlParams, viewEvalCtx, viewId, filters, queryLimit);

		//
		// Execute it, so we insert in our T_WEBUI_ViewSelection
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final long rowsCount = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		stopwatch.stop();
		logger.trace("Created selection {}, rowsCount={}, duration={} \n SQL: {} -- {}", viewId, rowsCount, stopwatch, sql, sqlParams);

		return ViewRowIdsOrderedSelection.builder()
				.setViewId(viewId)
				.setSize(rowsCount)
				.setOrderBys(sqlBindings.getDefaultOrderBys())
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

		final String sqlOrderBys = SqlDocumentOrderByBuilder.newInstance(fieldName -> sqlBindings.getFieldByFieldName(fieldName).getSqlOrderBy())
				.buildSqlOrderBy(orderBys)
				.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

		final String sqlCreateFromViewId = sqlBindings.getSqlCreateSelectionFromSelection();
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
	public IViewRow retrieveById(final ViewEvaluationCtx viewEvalCtx, final ViewId viewId, final DocumentId rowId)
	{
		final WindowId windowId = viewId.getWindowId();
		final String viewSelectionId = viewId.getViewId();
		final String adLanguage = viewEvalCtx.getAD_Language();

		final String sql = sqlBindings.getSqlSelectById().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

		final Object[] sqlParams = new Object[] { viewSelectionId, rowId.toInt() };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(1 + 1);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			IViewRow firstDocument = null;
			while (rs.next())
			{
				final IViewRow document = loadViewRow(rs, windowId, adLanguage);
				if (document == null)
				{
					continue;
				}

				if (firstDocument != null)
				{
					logger.warn("More than one document found for rowId={} in {}. Returning only the first one: {}", rowId, this, firstDocument);
					return firstDocument;
				}
				else
				{
					firstDocument = document;
				}
			}

			if (firstDocument == null)
			{
				throw new EntityNotFoundException("No document found for rowId=" + rowId);
			}

			return firstDocument;
		}
		catch (final SQLException | DBException e)
		{
			throw DBException.wrapIfNeeded(e)
					.setSqlIfAbsent(sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private IViewRow loadViewRow(final ResultSet rs, final WindowId windowId, final String adLanguage) throws SQLException
	{
		final ViewRow.Builder viewRowBuilder = ViewRow.builder(windowId);

		for (final SqlViewRowFieldBinding field : sqlBindings.getFields())
		{
			final String fieldName = field.getFieldName();
			final boolean keyColumn = field.isKeyColumn();
			final SqlViewRowFieldLoader fieldLoader = field.getFieldLoader();
			final Object value = fieldLoader.retrieveValueAsJson(rs, adLanguage);

			if (keyColumn)
			{
				if (value == null)
				{
					logger.warn("No ID found for current row. Skipping the row.");
					return null;
				}

				viewRowBuilder.setRowIdFromObject(value);
			}

			viewRowBuilder.putFieldValue(fieldName, value);
		}

		return viewRowBuilder.build();
	}

	@Override
	public DocumentFilterDescriptorsProvider getViewFilterDescriptors()
	{
		return sqlBindings.getViewFilterDescriptors();
	}

	@Override
	public List<IViewRow> retrievePage(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection orderedSelection, final int firstRow, final int pageLength) throws DBException
	{
		logger.debug("Getting page: firstRow={}, pageLength={} - {}", firstRow, pageLength, this);

		Check.assume(firstRow >= 0, "firstRow >= 0 but it was {}", firstRow);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		logger.debug("Using: {}", orderedSelection);
		final WindowId windowId = orderedSelection.getWindowId();
		final String viewSelectionId = orderedSelection.getSelectionId();
		final String adLanguage = viewEvalCtx.getAD_Language();

		final int firstSeqNo = firstRow + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRow + pageLength;

		final String sql = sqlBindings.getSqlSelectByPage().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
		final Object[] sqlParams = new Object[] { viewSelectionId, firstSeqNo, lastSeqNo };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final ImmutableList.Builder<IViewRow> pageBuilder = ImmutableList.builder();
			while (rs.next())
			{
				final IViewRow row = loadViewRow(rs, windowId, adLanguage);
				if (row == null)
				{
					continue;
				}

				pageBuilder.add(row);
			}

			final List<IViewRow> page = pageBuilder.build();
			return page;
		}
		catch (final SQLException | DBException e)
		{
			throw DBException.wrapIfNeeded(e)
					.setSqlIfAbsent(sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final ViewId viewId, final Collection<DocumentId> rowIds, final Class<T> modelClass)
	{
		if (rowIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final String sqlWhereClause = getSqlWhereClause(viewId, rowIds);
		if (Check.isEmpty(sqlWhereClause, true))
		{
			logger.warn("Could get the SQL where clause for {}/{}. Returning empty", viewId, rowIds);
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class).createQueryBuilder(modelClass, getTableName(), PlainContextAware.createUsingOutOfTransaction())
				.filter(new TypedSqlQueryFilter<>(sqlWhereClause))
				.create()
				.list(modelClass);
	}

}
