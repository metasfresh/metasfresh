package de.metas.ui.web.view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.view.descriptor.SqlViewBinding.ViewFieldsBinding;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;
import de.metas.ui.web.window.model.sql.SqlDocumentQueryBuilder;

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

class SqlView implements IView
{
	public static final Builder builder(final DocumentEntityDescriptor entityDescriptor)
	{
		return new Builder(entityDescriptor);
	}

	private static final Logger logger = LogManager.getLogger(SqlView.class);

	private final AtomicBoolean closed = new AtomicBoolean(false);
	
	private final ViewId parentViewId;

	private final String tableName;
	private final String keyColumnName;

	private final transient String sqlSelectPage;
	private final transient String sqlSelectById;
	private final transient SqlViewRowFieldLoader sqlFieldLoaders;

	private final transient IViewRowIdsOrderedSelectionFactory orderedSelectionFactory;
	private final transient ViewRowIdsOrderedSelection defaultSelection;
	private final transient ConcurrentHashMap<ImmutableList<DocumentQueryOrderBy>, ViewRowIdsOrderedSelection> selectionsByOrderBys = new ConcurrentHashMap<>();

	//
	// Filters
	private final transient DocumentFilterDescriptorsProvider filterDescriptors;
	/** Sticky filters (i.e. active filters which cannot be changed) */
	private final List<DocumentFilter> stickyFilters;
	/** Active filters */
	private final List<DocumentFilter> filters;

	//
	// Attributes
	private final transient IViewRowAttributesProvider attributesProvider;

	//
	// Misc
	private transient String _toString;

	//
	// Caching
	private final transient CCache<DocumentId, IViewRow> cache_rowsById;

	private SqlView(final Builder builder)
	{
		super();
		
		parentViewId = builder.getParentViewId();

		tableName = builder.getTableName();
		keyColumnName = builder.getKeyColumnName();

		sqlSelectPage = builder.buildSqlSelectPage();
		sqlSelectById = builder.buildSqlSelectById();
		sqlFieldLoaders = builder.getRowFieldLoaders();

		orderedSelectionFactory = builder.getOrderedSelectionFactory();
		defaultSelection = builder.buildInitialSelection();
		selectionsByOrderBys.put(defaultSelection.getOrderBys(), defaultSelection);

		//
		// Filters
		filterDescriptors = builder.getFilterDescriptors();
		stickyFilters = ImmutableList.copyOf(builder.getStickyFilters());
		filters = ImmutableList.copyOf(builder.getFilters());

		//
		// Attributes
		attributesProvider = ViewRowAttributesProviderFactory.instance.createProviderOrNull(defaultSelection.getWindowId());

		//
		// Cache
		cache_rowsById = CCache.newLRUCache( //
				tableName + "#rowById#viewId=" + defaultSelection.getSelectionId() // cache name
				, 100 // maxSize
				, 2 // expireAfterMinutes
		);

		logger.debug("View created: {}", this);
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			_toString = MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("viewId", defaultSelection.getViewId())
					.add("tableName", tableName)
					.add("parentViewId", parentViewId)
					.add("defaultSelection", defaultSelection)
					// .add("sql", sqlSelectPage) // too long..
					// .add("fieldLoaders", fieldLoaders) // no point to show them because all are lambdas
					.toString();
		}
		return _toString;
	}
	
	@Override
	public ViewId getParentViewId()
	{
		return parentViewId;
	}
	
	@Override
	public ViewId getViewId()
	{
		return defaultSelection.getViewId();
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	private String getKeyColumnName()
	{
		Preconditions.checkNotNull(keyColumnName, "View %s has key column", this);
		return keyColumnName;
	}

	@Override
	public long size()
	{
		return defaultSelection.getSize();
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return defaultSelection.getOrderBys();
	}

	@Override
	public int getQueryLimit()
	{
		return defaultSelection.getQueryLimit();
	}

	@Override
	public boolean isQueryLimitHit()
	{
		return defaultSelection.isQueryLimitHit();
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return stickyFilters;
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return filters;
	}

	@Override
	public void close()
	{
		if (closed.getAndSet(true))
		{
			return; // already closed
		}

		// nothing now.
		// TODO in future me might notify somebody to remove the temporary selections from database

		logger.debug("View closed: {}", this);
	}

	private final void assertNotClosed()
	{
		if (closed.get())
		{
			throw new IllegalStateException("View already closed: " + getViewId());
		}
	}

	@Override
	public ViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys) throws DBException
	{
		assertNotClosed();

		logger.debug("Getting page: firstRow={}, pageLength={} - {}", firstRow, pageLength, this);

		Check.assume(firstRow >= 0, "firstRow >= 0 but it was {}", firstRow);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		final ViewRowIdsOrderedSelection orderedSelection = getOrderedSelection(orderBys);
		logger.debug("Using: {}", orderedSelection);

		final String selectionId = orderedSelection.getSelectionId();
		final int firstSeqNo = firstRow + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRow + pageLength;

		final Object[] sqlParams = new Object[] { selectionId, firstSeqNo, lastSeqNo };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlSelectPage, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final ImmutableList.Builder<IViewRow> pageBuilder = ImmutableList.builder();
			while (rs.next())
			{
				final IViewRow row = loadViewRow(rs);
				if (row == null)
				{
					continue;
				}

				pageBuilder.add(row);
			}

			final List<IViewRow> page = pageBuilder.build();

			// Add to cache
			for (final IViewRow row : page)
			{
				cache_rowsById.put(row.getId(), row);
			}

			return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderedSelection.getOrderBys(), page);
		}
		catch (final SQLException | DBException e)
		{
			throw DBException.wrapIfNeeded(e)
					.setSqlIfAbsent(sqlSelectPage, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public IViewRow getById(final DocumentId rowId)
	{
		assertNotClosed();
		return getOrRetrieveById(rowId);
	}

	private final IViewRow getOrRetrieveById(final DocumentId rowId)
	{
		return cache_rowsById.getOrLoad(rowId, () -> retrieveById(rowId));
	}

	private final IViewRow retrieveById(final DocumentId rowId)
	{
		final Object[] sqlParams = new Object[] { getViewId().getViewId(), rowId.toInt() };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlSelectById, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(1 + 1);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			IViewRow firstDocument = null;
			while (rs.next())
			{
				final IViewRow document = loadViewRow(rs);
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
					.setSqlIfAbsent(sqlSelectById, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

	}

	private ViewRowIdsOrderedSelection getOrderedSelection(final List<DocumentQueryOrderBy> orderBys) throws DBException
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return defaultSelection;
		}

		if (Objects.equals(defaultSelection.getOrderBys(), orderBys))
		{
			return defaultSelection;
		}

		final ImmutableList<DocumentQueryOrderBy> orderBysImmutable = ImmutableList.copyOf(orderBys);
		return selectionsByOrderBys.computeIfAbsent(orderBysImmutable, (newOrderBys) -> orderedSelectionFactory.createFromView(defaultSelection, orderBysImmutable));
	}

	private IViewRow loadViewRow(final ResultSet rs) throws SQLException
	{
		final ViewRow.Builder viewRowBuilder = ViewRow.builder(getViewId().getWindowId());
		final boolean loaded = sqlFieldLoaders.loadViewRowField(viewRowBuilder, rs);
		if (!loaded)
		{
			return null;
		}

		return viewRowBuilder
				.setAttributesProvider(attributesProvider)
				.build();
	}

	@Override
	public String getSqlWhereClause(final Collection<DocumentId> rowIds)
	{
		final String sqlTableName = getTableName();
		final String sqlKeyColumnName = getKeyColumnName();

		final StringBuilder sqlWhereClause = new StringBuilder();
		sqlWhereClause.append("exists (select 1 from " + I_T_WEBUI_ViewSelection.Table_Name + " sel "
				+ " where "
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(getViewId().getViewId())
				+ " and sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + "=" + sqlTableName + "." + sqlKeyColumnName
				+ ")");

		if (!Check.isEmpty(rowIds))
		{
			final Set<Integer> rowIdsAsInts = DocumentId.toIntSet(rowIds);
			sqlWhereClause.append(" AND ").append(sqlKeyColumnName).append(" IN ").append(DB.buildSqlList(rowIdsAsInts));
		}

		return sqlWhereClause.toString();
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		assertNotClosed();

		return filterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.findEntities(ctx);
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		assertNotClosed();

		return filterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.findEntities(ctx, query);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return attributesProvider != null;
	}

	@Override
	public Stream<? extends IViewRow> streamByIds(final Collection<DocumentId> rowIds)
	{
		if (rowIds.isEmpty())
		{
			return Stream.empty();
		}

		// NOTE: we get/retrive one by one because we assume the "selected documents" were recently retrieved,
		// and the records recently retrieved have a big chance to be cached.
		return rowIds.stream()
				.distinct()
				.map(rowId -> {
					try
					{
						return getOrRetrieveById(rowId);
					}
					catch (final EntityNotFoundException e)
					{
						return null;
					}
				})
				.filter(row -> row != null);
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final Collection<DocumentId> rowIds, final Class<T> modelClass)
	{
		if (rowIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final String sqlWhereClause = getSqlWhereClause(rowIds);

		return Services.get(IQueryBL.class).createQueryBuilder(modelClass, getTableName(), PlainContextAware.createUsingOutOfTransaction())
				.filter(new TypedSqlQueryFilter<>(sqlWhereClause))
				.create()
				.list(modelClass);
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		final String viewTableName = getTableName();

		final Set<DocumentId> rowIds = recordRefs.stream()
				.filter(recordRef -> Objects.equals(viewTableName, recordRef.getTableName()))
				.map(recordRef -> DocumentId.of(recordRef.getRecord_ID()))
				.collect(GuavaCollectors.toImmutableSet());
		
		if(rowIds.isEmpty())
		{
			return;
		}

		// Invalidate local rowsById cache
		rowIds.forEach(cache_rowsById::remove);

		// Collect event
		ViewChangesCollector.getCurrentOrAutoflush().collectRowsChanged(this, rowIds);
	}

	//
	//
	// Builder
	//
	//

	public static final class Builder
	{
		private ViewId parentViewId;
		
		private final DocumentEntityDescriptor _entityDescriptor;
		private Set<String> _viewFieldNames;
		private List<DocumentFilter> _stickyFilters;
		private List<DocumentFilter> _filters;

		private SqlDocumentQueryBuilder _queryBuilder;


		private Builder(final DocumentEntityDescriptor entityDescriptor)
		{
			super();
			Check.assumeNotNull(entityDescriptor, "Parameter entityDescriptor is not null");
			_entityDescriptor = entityDescriptor;
		}

		public SqlView build()
		{
			return new SqlView(this);
		}
		
		public Builder setParentViewId(final ViewId parentViewId)
		{
			this.parentViewId = parentViewId;
			return this;
		}
		
		private ViewId getParentViewId()
		{
			return parentViewId;
		}

		private SqlViewRowFieldLoader getRowFieldLoaders()
		{
			return getViewFieldsBinding().getValueLoaders();
		}

		private SqlDocumentQueryBuilder getQueryBuilder()
		{
			if (_queryBuilder == null)
			{
				_queryBuilder = SqlDocumentQueryBuilder.newInstance(getEntityDescriptor())
						.addDocumentFilters(getStickyFilters())
						.addDocumentFilters(getFilters());
			}
			return _queryBuilder;
		}

		private DocumentEntityDescriptor getEntityDescriptor()
		{
			return _entityDescriptor;
		}

		private SqlViewBinding getBinding()
		{
			return getQueryBuilder().getEntityBinding().getViewBinding();
		}

		private DocumentFilterDescriptorsProvider getFilterDescriptors()
		{
			return getEntityDescriptor().getFiltersProvider();
		}
		
		public Builder setStickyFilter(@Nullable final DocumentFilter stickyFilter)
		{
			_stickyFilters = stickyFilter == null ? ImmutableList.of() : ImmutableList.of(stickyFilter);
			return this;
		}

		private List<DocumentFilter> getStickyFilters()
		{
			return _stickyFilters;
		}

		public Builder setFilters(final List<DocumentFilter> filters)
		{
			_filters = filters;
			return this;
		}

		public Builder setFiltersFromJSON(final List<JSONDocumentFilter> jsonFilters)
		{
			setFilters(JSONDocumentFilter.unwrapList(jsonFilters, getFilterDescriptors()));
			return this;
		}

		private List<DocumentFilter> getFilters()
		{
			return _filters;
		}

		public Builder setViewFieldsByCharacteristic(final Characteristic characteristic)
		{
			final Set<String> viewFieldNames = getEntityDescriptor().getFieldNamesWithCharacteristic(characteristic);
			if (viewFieldNames.isEmpty())
			{
				throw new IllegalStateException("No fields were found for characteristic: " + characteristic);
			}

			_viewFieldNames = ImmutableSet.copyOf(viewFieldNames);

			return this;
		}

		private Set<String> getViewFieldNames()
		{
			Check.assumeNotEmpty(_viewFieldNames, "viewFieldNames is not empty");
			return _viewFieldNames;
		}

		private String getTableName()
		{
			return getEntityDescriptor().getTableName();
		}

		private String getKeyColumnName()
		{
			return getBinding().getKeyColumnName();
		}

		private ViewFieldsBinding getViewFieldsBinding()
		{
			return getBinding().getViewFieldsBinding(getViewFieldNames());
		}

		private String getSqlPagedSelectAllFrom()
		{
			// TODO: cache it locally
			final SqlDocumentQueryBuilder queryBuilder = getQueryBuilder();
			final Evaluatee evalCtx = queryBuilder.getEvaluationContext();
			return getViewFieldsBinding()
					.getSqlPagedSelect()
					.evaluate(evalCtx, OnVariableNotFound.Fail);
		}

		private String buildSqlSelectPage()
		{
			return getSqlPagedSelectAllFrom()
					+ "\n WHERE " + SqlViewBinding.COLUMNNAME_Paging_UUID + "=?"
					+ "\n AND " + SqlViewBinding.COLUMNNAME_Paging_SeqNo + " BETWEEN ? AND ?"
					+ "\n ORDER BY " + SqlViewBinding.COLUMNNAME_Paging_SeqNo;
		}

		private String buildSqlSelectById()
		{
			return getSqlPagedSelectAllFrom()
					+ "\n WHERE " + SqlViewBinding.COLUMNNAME_Paging_UUID + "=?"
					+ "\n AND " + SqlViewBinding.COLUMNNAME_Paging_Record_ID + "=?";
		}

		public IViewRowIdsOrderedSelectionFactory getOrderedSelectionFactory()
		{
			final Evaluatee evalCtx = getQueryBuilder().getEvaluationContext();
			return getBinding().createOrderedSelectionFactory(evalCtx);
		}

		private ViewRowIdsOrderedSelection buildInitialSelection()
		{
			final SqlDocumentQueryBuilder queryBuilder = getQueryBuilder();
			return getBinding().createOrderedSelection(queryBuilder);
		}
	}
}
