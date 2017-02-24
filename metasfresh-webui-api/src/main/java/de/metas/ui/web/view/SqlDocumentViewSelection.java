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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.DocumentViewAsPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.view.descriptor.SqlDocumentViewBinding;
import de.metas.ui.web.view.descriptor.SqlDocumentViewBinding.SqlDocumentViewFieldValueLoader;
import de.metas.ui.web.view.descriptor.SqlDocumentViewBinding.ViewFieldsBinding;
import de.metas.ui.web.view.event.DocumentViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.DocumentReferencesService;
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

class SqlDocumentViewSelection implements IDocumentViewSelection
{
	public static final Builder builder(final DocumentEntityDescriptor entityDescriptor)
	{
		return new Builder(entityDescriptor);
	}

	private static final Logger logger = LogManager.getLogger(SqlDocumentViewSelection.class);

	private final AtomicBoolean closed = new AtomicBoolean(false);

	private final int adWindowId;
	private final String tableName;
	private final String keyColumnName;

	private final transient String sqlSelectPage;
	private final transient String sqlSelectById;
	private final transient SqlDocumentViewFieldValueLoader sqlFieldLoaders;

	private final transient IDocumentViewOrderedSelectionFactory orderedSelectionFactory;
	private final transient DocumentViewOrderedSelection defaultSelection;
	private final transient ConcurrentHashMap<ImmutableList<DocumentQueryOrderBy>, DocumentViewOrderedSelection> selectionsByOrderBys = new ConcurrentHashMap<>();

	//
	// Filters
	private final transient DocumentFilterDescriptorsProvider filterDescriptors;
	/** Sticky filters (i.e. active filters which cannot be changed) */
	private final List<DocumentFilter> stickyFilters;
	/** Active filters */
	private final List<DocumentFilter> filters;

	//
	// Related actions
	private final transient ProcessDescriptorsFactory processDescriptorFactory;

	//
	// Attributes
	private final transient IDocumentViewAttributesProvider attributesProvider;

	//
	// Misc
	private transient String _toString;

	//
	// Caching
	private final transient CCache<DocumentId, IDocumentView> cache_documentViewsById;

	private SqlDocumentViewSelection(final Builder builder)
	{
		super();

		adWindowId = builder.getAD_Window_ID();
		tableName = builder.getTableName();
		keyColumnName = builder.getKeyColumnName();

		sqlSelectPage = builder.buildSqlSelectPage();
		sqlSelectById = builder.buildSqlSelectById();
		sqlFieldLoaders = builder.getDocumentViewFieldValueLoaders();

		orderedSelectionFactory = builder.getOrderedSelectionFactory();
		defaultSelection = builder.buildInitialSelection();
		selectionsByOrderBys.put(defaultSelection.getOrderBys(), defaultSelection);

		//
		// Filters
		filterDescriptors = builder.getFilterDescriptors();
		stickyFilters = ImmutableList.copyOf(builder.getStickyFilters());
		filters = ImmutableList.copyOf(builder.getFilters());

		//
		// Related actions
		processDescriptorFactory = builder.getProcessDescriptorFactory();

		//
		// Attributes
		attributesProvider = DocumentViewAttributesProviderFactory.instance.createProviderOrNull(DocumentType.Window, adWindowId);

		//
		// Cache
		cache_documentViewsById = CCache.newLRUCache( //
				tableName + "#DocumentViewById#viewId=" + defaultSelection.getUuid() // cache name
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
					.add("viewId", defaultSelection.getUuid())
					.add("AD_Window_ID", adWindowId)
					.add("tableName", tableName)
					.add("defaultSelection", defaultSelection)
					// .add("sql", sqlSelectPage) // too long..
					// .add("fieldLoaders", fieldLoaders) // no point to show them because all are lambdas
					.toString();
		}
		return _toString;
	}

	@Override
	public String getViewId()
	{
		return defaultSelection.getUuid();
	}

	@Override
	public int getAD_Window_ID()
	{
		return adWindowId;
	}

	private String getTableName()
	{
		return tableName;
	}

	private String getKeyColumnName()
	{
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
	public DocumentViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys) throws DBException
	{
		assertNotClosed();

		logger.debug("Getting page: firstRow={}, pageLength={} - {}", firstRow, pageLength, this);

		Check.assume(firstRow >= 0, "firstRow >= 0 but it was {}", firstRow);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		final DocumentViewOrderedSelection orderedSelection = getOrderedSelection(orderBys);
		logger.debug("Using: {}", orderedSelection);

		final String uuid = orderedSelection.getUuid();
		final int firstSeqNo = firstRow + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRow + pageLength;

		final Object[] sqlParams = new Object[] { uuid, firstSeqNo, lastSeqNo };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlSelectPage, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final ImmutableList.Builder<IDocumentView> pageBuilder = ImmutableList.builder();
			while (rs.next())
			{
				final IDocumentView documentView = loadDocumentView(rs);
				if (documentView == null)
				{
					continue;
				}

				pageBuilder.add(documentView);
			}

			final List<IDocumentView> page = pageBuilder.build();

			// Add to cache
			for (final IDocumentView documentView : page)
			{
				cache_documentViewsById.put(documentView.getDocumentId(), documentView);
			}

			return DocumentViewResult.ofViewAndPage(this, firstRow, pageLength, orderedSelection.getOrderBys(), page);
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
	public IDocumentView getById(final DocumentId documentId)
	{
		assertNotClosed();
		return getOrRetrieveById(documentId);
	}

	private final IDocumentView getOrRetrieveById(final DocumentId documentId)
	{
		return cache_documentViewsById.getOrLoad(documentId, () -> retrieveById(documentId));
	}

	private final IDocumentView retrieveById(final DocumentId documentId)
	{
		final Object[] sqlParams = new Object[] { getViewId(), documentId.toInt() };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlSelectById, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(1 + 1);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			IDocumentView firstDocument = null;
			while (rs.next())
			{
				final IDocumentView document = loadDocumentView(rs);
				if (document == null)
				{
					continue;
				}

				if (firstDocument != null)
				{
					logger.warn("More than one document found for documentId={} in {}. Returning only the first one: {}", documentId, this, firstDocument);
					return firstDocument;
				}
				else
				{
					firstDocument = document;
				}
			}

			if (firstDocument == null)
			{
				throw new EntityNotFoundException("No document found for documentId=" + documentId);
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

	private DocumentViewOrderedSelection getOrderedSelection(final List<DocumentQueryOrderBy> orderBys) throws DBException
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

	private final DocumentView.Builder newDocumentViewBuilder()
	{
		return DocumentView.builder(adWindowId)
				.setAttributesProvider(attributesProvider);
	}

	private IDocumentView loadDocumentView(final ResultSet rs) throws SQLException
	{
		final DocumentView.Builder documentViewBuilder = newDocumentViewBuilder();
		final boolean loaded = sqlFieldLoaders.loadDocumentViewValue(documentViewBuilder, rs);
		if (!loaded)
		{
			return null;
		}

		return documentViewBuilder.build();
	}

	@Override
	public String getSqlWhereClause(final Collection<DocumentId> viewDocumentIds)
	{
		final String sqlTableName = getTableName();
		final String sqlKeyColumnName = getKeyColumnName();

		final StringBuilder sqlWhereClause = new StringBuilder();
		sqlWhereClause.append("exists (select 1 from " + I_T_WEBUI_ViewSelection.Table_Name + " sel "
				+ " where "
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(getViewId())
				+ " and sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + "=" + sqlTableName + "." + sqlKeyColumnName
				+ ")");

		if (!Check.isEmpty(viewDocumentIds))
		{
			final Set<Integer> viewDocumentIdsAsInts = DocumentId.toIntSet(viewDocumentIds);
			sqlWhereClause.append(" AND ").append(sqlKeyColumnName).append(" IN ").append(DB.buildSqlList(viewDocumentIdsAsInts));
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
	public Stream<WebuiRelatedProcessDescriptor> streamActions(final Collection<DocumentId> selectedDocumentIds)
	{
		assertNotClosed();

		final DocumentViewAsPreconditionsContext preconditionsContext = DocumentViewAsPreconditionsContext.newInstance(this, getTableName(), selectedDocumentIds);
		return processDescriptorFactory.streamDocumentRelatedProcesses(preconditionsContext);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return attributesProvider != null;
	}

	@Override
	public Stream<? extends IDocumentView> streamByIds(final Collection<DocumentId> documentIds)
	{
		if (documentIds.isEmpty())
		{
			return Stream.empty();
		}

		// NOTE: we get/retrive one by one because we assume the "selected documents" were recently retrieved,
		// and the records recently retrieved have a big chance to be cached.
		return documentIds.stream()
				.map(this::getOrRetrieveById)
				.filter(document -> document != null);
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final Collection<DocumentId> documentIds, final Class<T> modelClass)
	{
		if (documentIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final String sqlWhereClause = getSqlWhereClause(documentIds);

		return Services.get(IQueryBL.class).createQueryBuilder(modelClass, getTableName(), PlainContextAware.createUsingOutOfTransaction())
				.filter(new TypedSqlQueryFilter<>(sqlWhereClause))
				.create()
				.list(modelClass);
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		final String viewTableName = getTableName();

		final Set<DocumentId> documentIds = recordRefs.stream()
				.filter(recordRef -> Objects.equals(viewTableName, recordRef.getTableName()))
				.map(recordRef -> DocumentId.of(recordRef.getRecord_ID()))
				.collect(GuavaCollectors.toImmutableSet());

		DocumentViewChangesCollector.getCurrentOrAutoflush()
				.collectDocumentsChanged(this, documentIds);
	}

	//
	//
	// Builder
	//
	//

	public static final class Builder
	{
		// services
		private ProcessDescriptorsFactory processDescriptorsFactory;
		private DocumentReferencesService documentReferencesService;

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

		public SqlDocumentViewSelection build()
		{
			return new SqlDocumentViewSelection(this);
		}

		private SqlDocumentViewFieldValueLoader getDocumentViewFieldValueLoaders()
		{
			return getViewFieldsBinding()
					.getValueLoaders();
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

		private SqlDocumentViewBinding getBinding()
		{
			return getQueryBuilder().getEntityBinding().getDocumentViewBinding();
		}

		private DocumentFilterDescriptorsProvider getFilterDescriptors()
		{
			return getEntityDescriptor().getFiltersProvider();
		}

		private Builder setStickyFilter(@Nullable final DocumentFilter stickyFilter)
		{
			_stickyFilters = stickyFilter == null ? ImmutableList.of() : ImmutableList.of(stickyFilter);
			return this;
		}

		public Builder setStickyFilterByReferencedDocument(@Nullable final DocumentPath referencedDocumentPath)
		{
			if (referencedDocumentPath == null)
			{
				setStickyFilter(null);
			}
			else
			{
				final int targetWindowId = getEntityDescriptor().getAD_Window_ID();
				final DocumentReference reference = getDocumentReferencesService().getDocumentReference(referencedDocumentPath, targetWindowId);
				setStickyFilter(reference.getFilter());
			}
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

		private int getAD_Window_ID()
		{
			return getEntityDescriptor().getAD_Window_ID();
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
					+ "\n WHERE " + SqlDocumentViewBinding.COLUMNNAME_Paging_UUID + "=?"
					+ "\n AND " + SqlDocumentViewBinding.COLUMNNAME_Paging_SeqNo + " BETWEEN ? AND ?"
					+ "\n ORDER BY " + SqlDocumentViewBinding.COLUMNNAME_Paging_SeqNo;
		}

		private String buildSqlSelectById()
		{
			return getSqlPagedSelectAllFrom()
					+ "\n WHERE " + SqlDocumentViewBinding.COLUMNNAME_Paging_UUID + "=?"
					+ "\n AND " + SqlDocumentViewBinding.COLUMNNAME_Paging_Record_ID + "=?";
		}

		public IDocumentViewOrderedSelectionFactory getOrderedSelectionFactory()
		{
			final Evaluatee evalCtx = getQueryBuilder().getEvaluationContext();
			return getBinding().createOrderedSelectionFactory(evalCtx);
		}

		private DocumentViewOrderedSelection buildInitialSelection()
		{
			final SqlDocumentQueryBuilder queryBuilder = getQueryBuilder();
			return getBinding().createOrderedSelection(queryBuilder);
		}

		public Builder setServices(final ProcessDescriptorsFactory processDescriptorsFactory, final DocumentReferencesService documentReferencesService)
		{
			this.processDescriptorsFactory = processDescriptorsFactory;
			this.documentReferencesService = documentReferencesService;
			return this;
		}

		private ProcessDescriptorsFactory getProcessDescriptorFactory()
		{
			Check.assumeNotNull(processDescriptorsFactory, "Parameter processDescriptorsFactory is not null");
			return processDescriptorsFactory;
		}

		private DocumentReferencesService getDocumentReferencesService()
		{
			Check.assumeNotNull(documentReferencesService, "Parameter documentReferencesService is not null");
			return documentReferencesService;
		}
	}
}
