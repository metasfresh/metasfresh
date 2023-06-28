package de.metas.ui.web.view;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewKeyColumnNamesMap;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.view.descriptor.SqlViewSelectData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.PlainContextAware;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
	private final ImmutableMap<String, DocumentFieldWidgetType> widgetTypesByFieldName;
	private final SqlViewSelectData sqlViewSelect;
	private final ViewRowIdsOrderedSelectionFactory viewRowIdsOrderedSelectionFactory;
	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;
	private final DocumentQueryOrderByList defaultOrderBys;

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
	public SqlViewRowsWhereClause getSqlWhereClause(
			@NonNull final ViewId viewId,
			@NonNull final DocumentFilterList filters,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final SqlOptions sqlOpts)
	{
		// Filter by view/rowIds
		SqlViewRowsWhereClause sqlWhereClause = viewRowIdsOrderedSelectionFactory.getSqlWhereClause(viewId, rowIds);

		// Set rowsMatchingFilter
		{
			final SqlDocumentFilterConverterContext context = SqlDocumentFilterConverterContext.builder()
					.viewId(viewId)
					.build();

			final FilterSql filterSql = filterConverters.getSql(filters, sqlOpts, context);
			final SqlAndParams rowsMatchingFilter = filterSql.toSqlAndParams(sqlOpts).orElse(null);

			sqlWhereClause = sqlWhereClause.withRowsMatchingFilter(rowsMatchingFilter);
		}

		return sqlWhereClause;
	}

	@Override
	public Map<String, DocumentFieldWidgetType> getWidgetTypesByFieldName()
	{
		return widgetTypesByFieldName;
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelection(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId viewId,
			final DocumentFilterList filters,
			final boolean applySecurityRestrictions,
			final SqlDocumentFilterConverterContext context)
	{
		return viewRowIdsOrderedSelectionFactory.createOrderedSelection(viewEvalCtx,
				viewId,
				filters,
				defaultOrderBys,
				applySecurityRestrictions,
				context);
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelectionFromSelection(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewRowIdsOrderedSelection fromSelection,
			final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		return viewRowIdsOrderedSelectionFactory.createOrderedSelectionFromSelection(viewEvalCtx, fromSelection, filters, orderBys, filterConverterCtx);
	}

	@Override
	public void deleteSelection(@NonNull final String selectionId)
	{
		viewRowIdsOrderedSelectionFactory.deleteSelection(selectionId);
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
				throw new EntityNotFoundException("No document found for rowId=" + rowId + " in viewId=" + viewId);
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

	private ImmutableList<IViewRow> loadViewRows(
			@NonNull final ResultSet rs,
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId viewId,
			final int limit) throws SQLException
	{
		final JSONOptions jsonOpts = viewEvalCtx.toJSONOptions();
		final Map<DocumentId, ViewRow.Builder> rowBuilders = new LinkedHashMap<>();
		final Set<DocumentId> rootRowIds = new HashSet<>();
		while (rs.next())
		{
			final ViewRow.Builder rowBuilder = loadViewRow(rs, viewId.getWindowId(), jsonOpts);
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
				.map(ViewRow.Builder::build)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private ViewRow.Builder loadViewRow(
			@NonNull final ResultSet rs,
			final WindowId windowId,
			final JSONOptions jsonOpts) throws SQLException
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

		final DocumentId rowId = retrieveRowId(rs, jsonOpts);
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
			final Object value = fieldLoader.retrieveValue(rs, jsonOpts.getAdLanguage());
			viewRowBuilder.putFieldValue(fieldName, value);
		}

		if (rowCustomizer != null)
		{
			rowCustomizer.customizeViewRow(viewRowBuilder);
		}

		return viewRowBuilder;
	}

	@Nullable
	private DocumentId retrieveRowId(final ResultSet rs, final JSONOptions jsonOpts) throws SQLException
	{
		if (keyColumnNamesMap.isSingleKey())
		{
			return retrieveRowId_SingleKey(rs, jsonOpts);
		}
		else
		{
			return retrieveRowId_MultiKey(rs, jsonOpts.getAdLanguage());
		}
	}

	@Nullable
	private DocumentId retrieveRowId_SingleKey(final ResultSet rs, final JSONOptions jsonOpts) throws SQLException
	{
		final String keyColumnName = keyColumnNamesMap.getSingleKeyColumnName();
		final SqlViewRowFieldLoader fieldLoader = rowFieldLoaders.get(keyColumnName);
		final Object rowIdObj = fieldLoader.retrieveValue(rs, jsonOpts.getAdLanguage());
		return convertToRowId(rowIdObj);
	}

	@Nullable
	private static DocumentId convertToRowId(@Nullable final Object rowIdObj)
	{
		if (rowIdObj == null || JSONNullValue.isNull(rowIdObj))
		{
			return null;
		}
		else if (rowIdObj instanceof DocumentId)
		{
			return (DocumentId)rowIdObj;
		}
		else if (rowIdObj instanceof Integer)
		{
			return DocumentId.of((Integer)rowIdObj);
		}
		else if (rowIdObj instanceof String)
		{
			return DocumentId.of(rowIdObj.toString());
		}
		else if (rowIdObj instanceof LookupValue)
		{
			// case: usually this is happening when a view's column which is Lookup is also marked as KEY.
			final LookupValue lookupValue = (LookupValue)rowIdObj;
			return DocumentId.of(lookupValue.getIdAsString());
		}
		else if (rowIdObj instanceof JSONLookupValue)
		{
			// case: usually this is happening when a view's column which is Lookup is also marked as KEY.
			final JSONLookupValue jsonLookupValue = (JSONLookupValue)rowIdObj;
			return DocumentId.of(jsonLookupValue.getKey());
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert id '" + rowIdObj + "' (" + rowIdObj.getClass() + ") to " + DocumentId.class);
		}
	}

	@Nullable
	private DocumentId retrieveRowId_MultiKey(final ResultSet rs, final String adLanguage) throws SQLException
	{
		final List<Object> rowIdParts = new ArrayList<>(keyColumnNamesMap.getKeyPartsCount());
		boolean onlyNullValues = true;

		for (final String keyColumnName : keyColumnNamesMap.getKeyColumnNames())
		{
			final SqlViewRowFieldLoader fieldLoader = rowFieldLoaders.get(keyColumnName);
			// Check.assumeNotNull(fieldLoader, "fieldLoader shall exist for {}", keyColumnName);

			final Object rowIdPartObj = fieldLoader.retrieveValue(rs, adLanguage);
			if (JSONNullValue.isNull(rowIdPartObj))
			{
				rowIdParts.add(null);
			}
			else
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
		else if (rowIdPartObj instanceof LookupValue)
		{
			return ((LookupValue)rowIdPartObj).getIdAsString();
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
	public List<IViewRow> retrievePage(final ViewEvaluationCtx viewEvalCtx,
									   final ViewRowIdsOrderedSelection orderedSelection,
									   final int firstRow,
									   final int pageLength) throws DBException
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
			return loadViewRows(rs, viewEvalCtx, viewId, pageLength);
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
	public List<DocumentId> retrieveRowIdsByPage(final ViewEvaluationCtx viewEvalCtx,
												 final ViewRowIdsOrderedSelection orderedSelection,
												 final int firstRow,
												 final int pageLength)
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
			final JSONOptions jsonOpts = JSONOptions.newInstance(); // not important

			while (rs.next())
			{
				final DocumentId rowId = retrieveRowId(rs, jsonOpts);
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

			return loadViewRows(rs, viewEvalCtx, viewId, -1/* limit */);
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
	public <T> List<T> retrieveModelsByIds(
			@NonNull final ViewId viewId,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final Class<T> modelClass)
	{
		return retrieveModelsByIdsAsStream(viewId, rowIds, modelClass)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	@NonNull
	public <T> Stream<T> retrieveModelsByIdsAsStream(
			@NonNull final ViewId viewId,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final Class<T> modelClass)
	{
		if (rowIds.isEmpty())
		{
			return Stream.empty();
		}

		final SqlViewRowsWhereClause sqlWhereClause = getSqlWhereClause(viewId, DocumentFilterList.EMPTY, rowIds, SqlOptions.usingTableAlias(getTableAlias()));
		if (sqlWhereClause.isNoRecords())
		{
			logger.warn("Could get the SQL where clause for {}/{}. Returning empty", viewId, rowIds);
			return Stream.empty();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(modelClass, getTableName(), PlainContextAware.createUsingOutOfTransaction())
				.filter(sqlWhereClause.toQueryFilter())
				.create()
				.iterateAndStream();
	}

	@Override
	public ViewRowIdsOrderedSelection removeRowIdsNotMatchingFilters(
			@NonNull final ViewRowIdsOrderedSelection selection,
			@NonNull final DocumentFilterList filters,
			@NonNull final Set<DocumentId> rowIds)
	{
		if (rowIds.isEmpty())
		{
			return selection;
		}

		final ViewId viewId = selection.getViewId();
		final Set<DocumentId> matchingRowIds = retrieveRowIdsMatchingFilters(viewId, filters, rowIds);
		final Set<DocumentId> notMatchingRowIds = Sets.difference(rowIds, matchingRowIds);
		if (notMatchingRowIds.isEmpty())
		{
			return selection;
		}

		return viewRowIdsOrderedSelectionFactory.removeRowIdsFromSelection(selection, DocumentIdsSelection.of(notMatchingRowIds));
	}

	public Set<DocumentId> retrieveRowIdsMatchingFilters(
			@NonNull final ViewId viewId,
			@NonNull final DocumentFilterList filters,
			@NonNull final Set<DocumentId> rowIds)
	{
		if (rowIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final SqlViewRowsWhereClause sqlWhereClause = getSqlWhereClause(
				viewId,
				filters,
				DocumentIdsSelection.of(rowIds),
				SqlOptions.usingTableName(getTableName()));
		if (sqlWhereClause.isNoRecords())
		{
			return ImmutableSet.of();
		}

		final SqlAndParams sql = SqlAndParams.builder()
				.append("SELECT ").append(keyColumnNamesMap.getKeyColumnNamesCommaSeparated())
				.append("\n FROM " + getTableName())
				.append("\n WHERE (\n").append(sqlWhereClause.toSqlAndParams()).append("\n)")
				.build();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.getSql(), ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sql.getSqlParams());
			rs = pstmt.executeQuery();

			final HashSet<DocumentId> matchingRowIds = new HashSet<>();
			while (rs.next())
			{
				final DocumentId rowId = keyColumnNamesMap.retrieveRowId(rs);
				if (rowId != null)
				{
					matchingRowIds.add(rowId);
				}
			}

			return matchingRowIds;
		}
		catch (final Exception ex)
		{
			throw new DBException(ex, sql.getSql(), sql.getSqlParams());
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public List<Object> retrieveFieldValues(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final String selectionId,
			@NonNull final String fieldName,
			final int limit)
	{
		final SqlViewRowFieldLoader fieldLoader = rowFieldLoaders.get(fieldName);
		final SqlAndParams sql = sqlViewSelect.selectFieldValues(viewEvalCtx, selectionId, fieldName, limit);

		final String adLanguage = viewEvalCtx.getAdLanguage();
		return DB.retrieveRows(
				sql.getSql(),
				sql.getSqlParams(),
				rs -> fieldLoader.retrieveValue(rs, adLanguage));
	}
}
