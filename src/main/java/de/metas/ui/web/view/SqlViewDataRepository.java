package de.metas.ui.web.view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder;
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

class SqlViewDataRepository implements IViewDataRepository
{
	private static final Logger logger = LogManager.getLogger(SqlViewDataRepository.class);

	private final String tableName;
	private final IStringExpression sqlSelectById;
	private final IStringExpression sqlSelectByPage;
	private final IStringExpression sqlSelectRowIdsByPage;
	private final IStringExpression sqlSelectLinesByRowIds;
	private final ViewRowIdsOrderedSelectionFactory viewRowIdsOrderedSelectionFactory;
	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;
	private final List<DocumentQueryOrderBy> defaultOrderBys;

	private final String keyFieldName;
	private final ImmutableMap<String, SqlViewRowFieldLoader> rowFieldLoaders;

	private final SqlDocumentFilterConverter filterConverters;

	SqlViewDataRepository(@NonNull final SqlViewBinding sqlBindings)
	{
		tableName = sqlBindings.getTableName();
		sqlSelectById = sqlBindings.getSqlSelectById();
		sqlSelectByPage = sqlBindings.getSqlSelectByPage();
		sqlSelectRowIdsByPage = sqlBindings.getSqlSelectRowIdsByPage();
		sqlSelectLinesByRowIds = sqlBindings.getSqlSelectLinesByRowIds();
		viewFilterDescriptors = sqlBindings.getViewFilterDescriptors();
		viewRowIdsOrderedSelectionFactory = SqlViewRowIdsOrderedSelectionFactory.of(sqlBindings);
		defaultOrderBys = sqlBindings.getDefaultOrderBys();

		String keyFieldName = null;
		final ImmutableMap.Builder<String, SqlViewRowFieldLoader> rowFieldLoaders = ImmutableMap.builder();
		for (final SqlViewRowFieldBinding field : sqlBindings.getFields())
		{
			final String fieldName = field.getFieldName();
			rowFieldLoaders.put(fieldName, field.getFieldLoader());

			if (field.isKeyColumn())
			{
				keyFieldName = fieldName;
			}
		}

		this.keyFieldName = keyFieldName;
		this.rowFieldLoaders = rowFieldLoaders.build();

		this.filterConverters = SqlDocumentFilterConverters.createEntityBindingEffectiveConverter(sqlBindings);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("tableName", tableName)
				.toString();
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public String getSqlWhereClause(final ViewId viewId, List<DocumentFilter> filters, final DocumentIdsSelection rowIds)
	{
		final StringBuilder sqlWhereClause = new StringBuilder();

		// Convert filters to SQL
		{
			final String sqlFilters = filterConverters.getSql(SqlParamsCollector.notCollecting(), filters);
			if (!Check.isEmpty(sqlFilters, true))
			{
				if (sqlWhereClause.length() > 0)
				{
					sqlWhereClause.append(" AND ");
				}
				sqlWhereClause.append("(").append(sqlFilters).append(")");
			}
		}
		
		// Filter by rowIds
		{
			final String sqlFilterByRowIds = viewRowIdsOrderedSelectionFactory.getSqlWhereClause(viewId, rowIds);
			if(!Check.isEmpty(sqlFilterByRowIds, true))
			{
				if (sqlWhereClause.length() > 0)
				{
					sqlWhereClause.append(" AND ");
				}
				sqlWhereClause.append("(").append(sqlFilterByRowIds).append(")");
			}
		}
		
		return sqlWhereClause.toString();
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelection(final ViewEvaluationCtx viewEvalCtx, final WindowId windowId, final List<DocumentFilter> filters)
	{
		return viewRowIdsOrderedSelectionFactory.createOrderedSelection(viewEvalCtx, windowId, filters, defaultOrderBys);
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelectionFromSelection(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection fromSelection, final List<DocumentQueryOrderBy> orderBys)
	{
		return viewRowIdsOrderedSelectionFactory.createOrderedSelectionFromSelection(viewEvalCtx, fromSelection, orderBys);
	}

	@Override
	public IViewRow retrieveById(final ViewEvaluationCtx viewEvalCtx, final ViewId viewId, final DocumentId rowId)
	{
		final String viewSelectionId = viewId.getViewId();

		final String sql = sqlSelectById.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

		final Object[] sqlParams = new Object[] { viewSelectionId, rowId.toInt() };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			final int limit = 2;
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(limit);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final List<IViewRow> documents = loadViewRows(rs, viewEvalCtx, viewId, limit);
			if (documents.isEmpty())
			{
				throw new EntityNotFoundException("No document found for rowId=" + rowId);
			}
			else if (documents.size() > 1)
			{
				logger.warn("More than one document found for rowId={} in {}. Returning only the first one from: {}", rowId, this, documents);
				return documents.get(0);
			}
			else
			{
				return documents.get(0);
			}
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

	private final ImmutableList<IViewRow> loadViewRows(final ResultSet rs, final ViewEvaluationCtx viewEvalCtx, final ViewId viewId, final int limit) throws SQLException
	{
		final Map<DocumentId, ViewRow.Builder> rowBuilders = new LinkedHashMap<>();
		final Set<DocumentId> rootRowIds = new HashSet<>();
		while (rs.next())
		{
			final ViewRow.Builder rowBuilder = loadViewRow(rs, viewId.getWindowId(), viewEvalCtx.getAD_Language());
			if (rowBuilder == null)
			{
				continue;
			}

			final DocumentId rowId = rowBuilder.getRowId();
			rowBuilders.put(rowId, rowBuilder);

			if (rowBuilder.isRootRow())
			{
				rootRowIds.add(rowId);
			}

			// Enforce limit
			if (limit > 0 && limit <= rowBuilders.size())
			{
				break;
			}
		}

		//
		// Load lines
		if (!rootRowIds.isEmpty())
		{
			retrieveRowLines(viewEvalCtx, viewId, DocumentIdsSelection.of(rootRowIds))
					.forEach(line -> {
						final DocumentId parentId = ViewRow.cast(line).getParentId();
						final ViewRow.Builder rowBuilder = rowBuilders.get(parentId);
						if (rowBuilder == null)
						{
							// shall not happen
							return;
						}

						rowBuilder.addIncludedRow(line);
					});
		}

		return rowBuilders.values().stream()
				.map(rowBuilder -> rowBuilder.build())
				.collect(ImmutableList.toImmutableList());
	}

	private ViewRow.Builder loadViewRow(final ResultSet rs, final WindowId windowId, final String adLanguage) throws SQLException
	{
		final ViewRow.Builder viewRowBuilder = ViewRow.builder(windowId);

		final int parentIdInt = rs.getInt(SqlViewSelectionQueryBuilder.COLUMNNAME_Paging_Parent_ID);
		if (!rs.wasNull())
		{
			viewRowBuilder.setParentRowId(DocumentId.of(parentIdInt));
			viewRowBuilder.setType(DefaultRowType.Line);
		}
		else
		{
			viewRowBuilder.setType(DefaultRowType.Row);
		}

		for (final Map.Entry<String, SqlViewRowFieldLoader> fieldNameAndLoader : rowFieldLoaders.entrySet())
		{
			final String fieldName = fieldNameAndLoader.getKey();
			final SqlViewRowFieldLoader fieldLoader = fieldNameAndLoader.getValue();
			final Object value = fieldLoader.retrieveValueAsJson(rs, adLanguage);

			if (Objects.equals(fieldName, keyFieldName))
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

		return viewRowBuilder;
	}

	@Override
	public DocumentFilterDescriptorsProvider getViewFilterDescriptors()
	{
		return viewFilterDescriptors;
	}

	@Override
	public List<IViewRow> retrievePage(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection orderedSelection, final int firstRow, final int pageLength) throws DBException
	{
		logger.debug("Getting page: firstRow={}, pageLength={} - {}", firstRow, pageLength, this);

		Check.assume(firstRow >= 0, "firstRow >= 0 but it was {}", firstRow);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		logger.debug("Using: {}", orderedSelection);
		final ViewId viewId = orderedSelection.getViewId();
		final String viewSelectionId = viewId.getViewId();

		final int firstSeqNo = firstRow + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRow + pageLength;

		final String sql = sqlSelectByPage.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
		final Object[] sqlParams = new Object[] { viewSelectionId, firstSeqNo, lastSeqNo };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final List<IViewRow> page = loadViewRows(rs, viewEvalCtx, viewId, pageLength);
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
	public List<DocumentId> retrieveRowIdsByPage(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection orderedSelection, final int firstRow, final int pageLength)
	{
		logger.debug("Getting page: firstRow={}, pageLength={} - {}", firstRow, pageLength, this);

		Check.assume(firstRow >= 0, "firstRow >= 0 but it was {}", firstRow);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		SqlViewRowFieldLoader rowIdFieldLoader = rowFieldLoaders.get(keyFieldName);
		if (rowIdFieldLoader == null)
		{
			throw new AdempiereException("No rowId loader found in " + this);
		}

		logger.debug("Using: {}", orderedSelection);
		final ViewId viewId = orderedSelection.getViewId();
		final String viewSelectionId = viewId.getViewId();

		final int firstSeqNo = firstRow + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRow + pageLength;

		final String sql = sqlSelectRowIdsByPage.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
		final Object[] sqlParams = new Object[] { viewSelectionId, firstSeqNo, lastSeqNo };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final ImmutableList.Builder<DocumentId> rowIds = ImmutableList.builder();
			final String adLanguage = null; // N/A, not important

			while (rs.next())
			{
				final Object rowIdObj = rowIdFieldLoader.retrieveValueAsJson(rs, adLanguage);
				if (rowIdObj == null)
				{
					continue;
				}

				final DocumentId rowId = ViewRow.convertToRowId(rowIdObj);
				rowIds.add(rowId);
			}
			return rowIds.build();
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

	private List<IViewRow> retrieveRowLines(final ViewEvaluationCtx viewEvalCtx, final ViewId viewId, final DocumentIdsSelection rowIds)
	{
		logger.debug("Getting row lines: rowId={} - {}", rowIds, this);

		logger.debug("Using: {}", viewId);
		final String viewSelectionId = viewId.getViewId();

		// TODO: apply the ORDER BY from orderedSelection
		final String sqlRecordIds = DB.buildSqlList(rowIds.toIntSet());
		final Evaluatee viewEvalCtxEffective = Evaluatees.ofSingleton(SqlViewSelectionQueryBuilder.Paging_Record_IDsPlaceholder.getName(), sqlRecordIds)
				.andComposeWith(viewEvalCtx.toEvaluatee());

		final String sql = sqlSelectLinesByRowIds.evaluate(viewEvalCtxEffective, OnVariableNotFound.Fail);
		final Object[] sqlParams = new Object[] { viewSelectionId };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final List<IViewRow> lines = loadViewRows(rs, viewEvalCtx, viewId, -1/* limit */);
			return lines;
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
	public <T> List<T> retrieveModelsByIds(final ViewId viewId, final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		if (rowIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<DocumentFilter> filters = ImmutableList.of();
		final String sqlWhereClause = getSqlWhereClause(viewId, filters, rowIds);
		if (Check.isEmpty(sqlWhereClause, true))
		{
			logger.warn("Could get the SQL where clause for {}/{}. Returning empty", viewId, rowIds);
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class).createQueryBuilder(modelClass, getTableName(), PlainContextAware.createUsingOutOfTransaction())
				.filter(TypedSqlQueryFilter.of(sqlWhereClause))
				.create()
				.list(modelClass);
	}

}
