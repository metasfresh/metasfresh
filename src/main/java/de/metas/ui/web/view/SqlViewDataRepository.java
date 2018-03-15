package de.metas.ui.web.view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
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
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewKeyColumnNamesMap;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.view.descriptor.SqlViewSelectData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.sql.SqlOptions;
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
	private final String tableAlias;
	private final SqlViewKeyColumnNamesMap keyColumnNamesMap;
	private final Map<String, DocumentFieldWidgetType> widgetTypesByFieldName;
	private final SqlViewSelectData sqlViewSelect;
	private final ViewRowIdsOrderedSelectionFactory viewRowIdsOrderedSelectionFactory;
	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;
	private final List<DocumentQueryOrderBy> defaultOrderBys;

	private final boolean hasIncludedRows;
	private final ImmutableMap<String, SqlViewRowFieldLoader> rowFieldLoaders;
	private final ViewRowCustomizer rowCustomizer;

	private final SqlDocumentFilterConverter filterConverters;


	SqlViewDataRepository(@NonNull final SqlViewBinding sqlBindings)
	{
		tableName = sqlBindings.getTableName();
		tableAlias = sqlBindings.getTableAlias();
		keyColumnNamesMap = sqlBindings.getSqlViewKeyColumnNamesMap();
		widgetTypesByFieldName = sqlBindings.getWidgetTypesByFieldName();
		sqlViewSelect = sqlBindings.getSqlViewSelect();
		viewFilterDescriptors = sqlBindings.getViewFilterDescriptors();
		viewRowIdsOrderedSelectionFactory = SqlViewRowIdsOrderedSelectionFactory.of(sqlBindings);
		defaultOrderBys = sqlBindings.getDefaultOrderBys();

		this.hasIncludedRows = sqlBindings.hasGroupingFields();
		this.rowFieldLoaders = sqlBindings.getFields()
				.stream()
				.collect(ImmutableMap.toImmutableMap(SqlViewRowFieldBinding::getFieldName, SqlViewRowFieldBinding::getFieldLoader));
		this.rowCustomizer = sqlBindings.getRowCustomizer();

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

	private String getTableAlias()
	{
		return tableAlias;
	}

	@Override
	public String getSqlWhereClause(final ViewId viewId, final List<DocumentFilter> filters, final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
	{
		final StringBuilder sqlWhereClause = new StringBuilder();

		// Convert filters to SQL
		{
			final String sqlFilters = filterConverters.getSql(SqlParamsCollector.notCollecting(), filters, sqlOpts);
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
			if (!Check.isEmpty(sqlFilterByRowIds, true))
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
	public Map<String, DocumentFieldWidgetType> getWidgetTypesByFieldName()
	{
		return widgetTypesByFieldName;
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelection(final ViewEvaluationCtx viewEvalCtx, final ViewId viewId, final List<DocumentFilter> filters)
	{
		return viewRowIdsOrderedSelectionFactory.createOrderedSelection(viewEvalCtx, viewId, filters, defaultOrderBys);
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelectionFromSelection(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection fromSelection, final List<DocumentQueryOrderBy> orderBys)
	{
		return viewRowIdsOrderedSelectionFactory.createOrderedSelectionFromSelection(viewEvalCtx, fromSelection, orderBys);
	}

	@Override
	public void deleteSelection(final ViewId viewId)
	{
		viewRowIdsOrderedSelectionFactory.deleteSelection(viewId);
	}

	@Override
	public void scheduleDeleteSelections(final Set<String> viewIds)
	{
		viewRowIdsOrderedSelectionFactory.scheduleDeleteSelections(viewIds);
	}

	@Override
	public IViewRow retrieveById(final ViewEvaluationCtx viewEvalCtx, final ViewId viewId, final DocumentId rowId)
	{
		final SqlAndParams sqlAndParams = sqlViewSelect.selectById()
				.viewEvalCtx(viewEvalCtx)
				.viewId(viewId)
				.rowId(rowId)
				.build();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			final int limit = 2;
			pstmt = DB.prepareStatement(sqlAndParams.getSql(), ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(limit);
			DB.setParameters(pstmt, sqlAndParams.getSqlParams());

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
					.setSqlIfAbsent(sqlAndParams.getSql(), sqlAndParams.getSqlParams());
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
		if (hasIncludedRows && !rootRowIds.isEmpty())
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
		final boolean isRecordMissing = DisplayType.toBoolean(rs.getString(SqlViewSelectData.COLUMNNAME_IsRecordMissing));
		if (isRecordMissing)
		{
			return null;
		}

		final ViewRow.Builder viewRowBuilder = ViewRow.builder(windowId);

		final DocumentId parentRowId = keyColumnNamesMap.retrieveRowId(rs, SqlViewSelectData.COLUMNNAME_Paging_Parent_Prefix, false);
		if (parentRowId != null)
		{
			viewRowBuilder.setParentRowId(parentRowId);
			viewRowBuilder.setType(DefaultRowType.Line);
		}
		else
		{
			viewRowBuilder.setType(DefaultRowType.Row);
		}

		final DocumentId rowId = retrieveRowId(rs, adLanguage);
		if (rowId == null)
		{
			logger.warn("No ID found for current row. Skipping the row.");
			return null;
		}
		viewRowBuilder.setRowId(rowId);

		for (final Map.Entry<String, SqlViewRowFieldLoader> fieldNameAndLoader : rowFieldLoaders.entrySet())
		{
			final String fieldName = fieldNameAndLoader.getKey();
			final SqlViewRowFieldLoader fieldLoader = fieldNameAndLoader.getValue();
			final Object value = fieldLoader.retrieveValueAsJson(rs, adLanguage);
			viewRowBuilder.putFieldValue(fieldName, value);
		}

		if (rowCustomizer != null)
		{
			rowCustomizer.customizeViewRow(viewRowBuilder);
		}

		return viewRowBuilder;
	}

	private DocumentId retrieveRowId(final ResultSet rs, final String adLanguage) throws SQLException
	{
		if (keyColumnNamesMap.isSingleKey())
		{
			return retrieveRowId_SingleKey(rs, adLanguage);
		}
		else
		{
			return retrieveRowId_MultiKey(rs, adLanguage);
		}
	}

	private DocumentId retrieveRowId_SingleKey(final ResultSet rs, final String adLanguage) throws SQLException
	{
		final String keyColumnName = keyColumnNamesMap.getSingleKeyColumnName();
		final SqlViewRowFieldLoader fieldLoader = rowFieldLoaders.get(keyColumnName);
		final Object jsonRowIdObj = fieldLoader.retrieveValueAsJson(rs, adLanguage);
		if (JSONNullValue.isNull(jsonRowIdObj))
		{
			return null;
		}
		else if (jsonRowIdObj instanceof DocumentId)
		{
			return (DocumentId)jsonRowIdObj;
		}
		else if (jsonRowIdObj instanceof Integer)
		{
			return DocumentId.of((Integer)jsonRowIdObj);
		}
		else if (jsonRowIdObj instanceof String)
		{
			return DocumentId.of(jsonRowIdObj.toString());
		}
		else if (jsonRowIdObj instanceof JSONLookupValue)
		{
			// case: usually this is happening when a view's column which is Lookup is also marked as KEY.
			final JSONLookupValue jsonLookupValue = (JSONLookupValue)jsonRowIdObj;
			return DocumentId.of(jsonLookupValue.getKey());
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert id '" + jsonRowIdObj + "' (" + jsonRowIdObj.getClass() + ") to integer");
		}
	}

	private DocumentId retrieveRowId_MultiKey(final ResultSet rs, final String adLanguage) throws SQLException
	{
		final List<Object> rowIdParts = new ArrayList<>(keyColumnNamesMap.getKeyPartsCount());
		boolean onlyNullValues = true;

		for (final String keyColumnName : keyColumnNamesMap.getKeyColumnNames())
		{
			final SqlViewRowFieldLoader fieldLoader = rowFieldLoaders.get(keyColumnName);
			// Check.assumeNotNull(fieldLoader, "fieldLoader shall exist for {}", keyColumnName);

			final Object rowIdPartObj = fieldLoader.retrieveValueAsJson(rs, adLanguage);
			if (JSONNullValue.isNull(rowIdPartObj))
			{
				rowIdParts.add(null);
			}
			{
				rowIdParts.add(convertToRowIdPart(rowIdPartObj));
				onlyNullValues = false;
			}
		}

		if (onlyNullValues)
		{
			return null;
		}

		return DocumentId.ofComposedKeyParts(rowIdParts);
	}

	private static String convertToRowIdPart(@NonNull final Object rowIdPartObj)
	{
		if (rowIdPartObj instanceof Integer)
		{
			return rowIdPartObj.toString();
		}
		else if (rowIdPartObj instanceof String)
		{
			return rowIdPartObj.toString();
		}
		else if (rowIdPartObj instanceof JSONLookupValue)
		{
			return ((JSONLookupValue)rowIdPartObj).getKey();
		}
		else
		{
			throw new AdempiereException("Cannot convert " + rowIdPartObj + " (" + rowIdPartObj.getClass() + ") to rowId part");
		}
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
		logger.debug("Using: {}", orderedSelection);

		final ViewId viewId = orderedSelection.getViewId();
		final SqlAndParams sqlAndParams = sqlViewSelect.selectByPage()
				.viewEvalCtx(viewEvalCtx)
				.viewId(viewId)
				.firstRowZeroBased(firstRow)
				.pageLength(pageLength)
				.build();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlAndParams.getSql(), ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlAndParams.getSqlParams());

			rs = pstmt.executeQuery();
			final List<IViewRow> page = loadViewRows(rs, viewEvalCtx, viewId, pageLength);
			return page;
		}
		catch (final SQLException | DBException e)
		{
			throw DBException.wrapIfNeeded(e)
					.setSqlIfAbsent(sqlAndParams.getSql(), sqlAndParams.getSqlParams());
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
		logger.debug("Using: {}", orderedSelection);

		final ViewId viewId = orderedSelection.getViewId();
		final SqlAndParams sqlAndParams = sqlViewSelect.selectRowIdsByPage()
				.viewEvalCtx(viewEvalCtx)
				.viewId(viewId)
				.firstRowZeroBased(firstRow)
				.pageLength(pageLength)
				.build();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlAndParams.getSql(), ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlAndParams.getSqlParams());

			rs = pstmt.executeQuery();

			final ImmutableList.Builder<DocumentId> rowIds = ImmutableList.builder();
			final String adLanguage = null; // N/A, not important

			while (rs.next())
			{
				final DocumentId rowId = retrieveRowId(rs, adLanguage);
				if (rowId == null)
				{
					continue;
				}
				rowIds.add(rowId);
			}
			return rowIds.build();
		}
		catch (final SQLException | DBException e)
		{
			throw DBException.wrapIfNeeded(e)
					.setSqlIfAbsent(sqlAndParams.getSql(), sqlAndParams.getSqlParams());
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

		final SqlAndParams sqlAndParams = sqlViewSelect.selectIncludedLines()
				.viewEvalCtx(viewEvalCtx)
				.viewId(viewId)
				.rowIds(rowIds)
				.build();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlAndParams.getSql(), ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlAndParams.getSqlParams());

			rs = pstmt.executeQuery();

			final List<IViewRow> lines = loadViewRows(rs, viewEvalCtx, viewId, -1/* limit */);
			return lines;
		}
		catch (final SQLException | DBException e)
		{
			throw DBException.wrapIfNeeded(e)
					.setSqlIfAbsent(sqlAndParams.getSql(), sqlAndParams.getSqlParams());
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
		final String sqlWhereClause = getSqlWhereClause(viewId, filters, rowIds, SqlOptions.usingTableAlias(getTableAlias()));
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
