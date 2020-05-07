package de.metas.ui.web.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import de.metas.cache.CCache;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentSaveStatus;
import de.metas.ui.web.window.model.DocumentValidStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import de.metas.util.collections.PagedIterator.Page;
import lombok.NonNull;

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

/**
 * Default {@link IView} implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DefaultView implements IEditableView
{
	public static Builder builder(final IViewDataRepository viewDataRepository)
	{
		return new Builder(viewDataRepository);
	}

	private static final Logger logger = LogManager.getLogger(DefaultView.class);

	private final IViewDataRepository viewDataRepository;

	private final ViewId viewId;
	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final ViewId parentViewId;
	private final DocumentId parentRowId;
	private final JSONViewDataType viewType;
	private final ViewProfileId profileId;
	private final ImmutableSet<DocumentPath> referencingDocumentPaths;

	private final ViewEvaluationCtx viewEvaluationCtx;
	private final ExtendedMemorizingSupplier<ViewRowIdsOrderedSelections> selectionsRef;
	private final AtomicBoolean defaultSelectionDeleteBeforeCreate = new AtomicBoolean(false);

	//
	// Filters
	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;
	/** Sticky filters (i.e. active filters which cannot be changed) */
	private final ImmutableList<DocumentFilter> stickyFilters;
	/** Regular filters */
	private final ImmutableList<DocumentFilter> filters;
	private transient ImmutableList<DocumentFilter> _allFilters;
	private final boolean applySecurityRestrictions;

	//
	// Misc
	private transient String _toString;

	//
	// Caching
	private final transient CCache<DocumentId, IViewRow> cache_rowsById;

	private final IViewInvalidationAdvisor viewInvalidationAdvisor;

	//
	// View refreshing on change events
	private final boolean refreshViewOnChangeEvents;
	private final ChangedRowIdsCollector changedRowIdsToCheck = new ChangedRowIdsCollector();

	private DefaultView(final Builder builder)
	{
		viewId = builder.getViewId();
		viewDataRepository = builder.getViewDataRepository();
		parentViewId = builder.getParentViewId();
		parentRowId = builder.getParentRowId();
		viewType = builder.getViewType();
		profileId = builder.getProfileId();
		referencingDocumentPaths = builder.getReferencingDocumentPaths();
		viewInvalidationAdvisor = builder.getViewInvalidationAdvisor();

		//
		// Filters
		viewFilterDescriptors = builder.getViewFilterDescriptors();
		stickyFilters = builder.getStickyFilters();
		filters = builder.getFilters();
		refreshViewOnChangeEvents = builder.isRefreshViewOnChangeEvents();

		//
		// Selection
		{
			viewEvaluationCtx = ViewEvaluationCtx.newInstanceFromCurrentContext();

			this.applySecurityRestrictions = builder.isApplySecurityRestrictions();
			selectionsRef = ExtendedMemorizingSupplier.of(() -> {
				if (defaultSelectionDeleteBeforeCreate.get())
				{
					viewDataRepository.deleteSelection(viewId);
				}

				final ViewRowIdsOrderedSelection defaultSelection = viewDataRepository.createOrderedSelection(
						getViewEvaluationCtx(),
						viewId,
						ImmutableList.copyOf(Iterables.concat(stickyFilters, filters)),
						applySecurityRestrictions,
						SqlDocumentFilterConverterContext.EMPTY);

				return new ViewRowIdsOrderedSelections(defaultSelection);
			});
		}

		//
		// Cache
		cache_rowsById = CCache.newLRUCache( //
				viewDataRepository.getTableName() + "#rowById#viewId=" + viewId.getViewId() // cache name
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
			final ViewRowIdsOrderedSelection defaultSelection = selectionsRef.get().getDefaultSelection();
			// NOTE: keep it short
			_toString = MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("viewId", defaultSelection.getViewId())
					.add("tableName", viewDataRepository.getTableName())
					.add("parentViewId", parentViewId)
					.add("defaultSelection", defaultSelection)
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
	public DocumentId getParentRowId()
	{
		return parentRowId;
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public JSONViewDataType getViewType()
	{
		return viewType;
	}

	@Override
	public ViewProfileId getProfileId()
	{
		return profileId;
	}

	@Override
	public ITranslatableString getDescription()
	{
		return TranslatableStrings.empty();
	}

	@Override
	public ImmutableSet<DocumentPath> getReferencingDocumentPaths()
	{
		return referencingDocumentPaths;
	}

	/**
	 * Returns the table name as provided by our internal {@link IViewDataRepository}.
	 */
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId ignored)
	{
		return viewDataRepository.getTableName();
	}

	@Override
	public long size()
	{
		final ViewRowIdsOrderedSelection defaultSelection = selectionsRef.get().getDefaultSelection();
		return defaultSelection.getSize();
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		final ViewRowIdsOrderedSelection defaultSelection = selectionsRef.get().getDefaultSelection();
		return defaultSelection.getOrderBys();
	}

	@Override
	public int getQueryLimit()
	{
		final ViewRowIdsOrderedSelection defaultSelection = selectionsRef.get().getDefaultSelection();
		return defaultSelection.getQueryLimit();
	}

	@Override
	public boolean isQueryLimitHit()
	{
		final ViewRowIdsOrderedSelection defaultSelection = selectionsRef.get().getDefaultSelection();
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

	public List<DocumentFilter> getAllFilters()
	{
		ImmutableList<DocumentFilter> allFilters = _allFilters;
		if (allFilters == null)
		{
			_allFilters = allFilters = ImmutableList.<DocumentFilter> builder()
					.addAll(getFilters())
					.addAll(getStickyFilters())
					.build();
		}
		return allFilters;
	}

	@Override
	public void close(final ViewCloseAction reason)
	{
		if (closed.getAndSet(true))
		{
			return; // already closed
		}

		final ViewRowIdsOrderedSelections selections = selectionsRef.forget();
		viewDataRepository.scheduleDeleteSelections(selections.getSelectionIds());

		logger.debug("View closed with reason={}: {}", reason, this);
	}

	@Override
	public void invalidateAll()
	{
		cache_rowsById.reset();
	}

	@Override
	public void invalidateRowById(final DocumentId rowId)
	{
		cache_rowsById.remove(rowId);
	}

	@Override
	public void invalidateSelection()
	{
		defaultSelectionDeleteBeforeCreate.set(true);
		final ViewRowIdsOrderedSelections selections = selectionsRef.forget();
		if (selections != null)
		{
			viewDataRepository.scheduleDeleteSelections(selections.getSelectionIds());
		}

		invalidateAll();

		ViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(this);
	}

	private void assertNotClosed()
	{
		if (closed.get())
		{
			throw new IllegalStateException("View already closed: " + getViewId());
		}
	}

	private ViewEvaluationCtx getViewEvaluationCtx()
	{
		return viewEvaluationCtx;
	}

	@Override
	public ViewResult getPage(
			final int firstRow,
			final int pageLength,
			final ViewRowsOrderBy orderBy)
	{
		assertNotClosed();
		checkChangedRows();

		final ViewEvaluationCtx evalCtx = getViewEvaluationCtx();
		final ViewRowIdsOrderedSelection orderedSelection = getOrderedSelection(orderBy.toDocumentQueryOrderByList());

		final List<IViewRow> rows = viewDataRepository.retrievePage(evalCtx, orderedSelection, firstRow, pageLength);

		// Add to cache
		rows.forEach(row -> cache_rowsById.put(row.getId(), row));

		return ViewResult.builder()
				.view(this)
				.firstRow(firstRow)
				.pageLength(pageLength)
				.orderBys(orderedSelection.getOrderBys())
				.rows(rows)
				.columnInfos(extractViewResultColumns(rows))
				.build();
	}

	private List<ViewResultColumn> extractViewResultColumns(@NonNull final List<IViewRow> rows)
	{
		if (rows.isEmpty())
		{
			return ImmutableList.of();
		}

		return viewDataRepository.getWidgetTypesByFieldName()
				.entrySet()
				.stream()
				.map(e -> extractViewResultColumnOrNull(e.getKey(), e.getValue(), rows))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
	}

	private ViewResultColumn extractViewResultColumnOrNull(
			@NonNull final String fieldName,
			@NonNull final DocumentFieldWidgetType widgetType,
			@NonNull final List<IViewRow> rows)
	{
		if (widgetType == DocumentFieldWidgetType.Integer)
		{
			return null;
		}
		else if (widgetType.isNumeric())
		{
			final int maxPrecision = rows.stream()
					.map(row -> row.getFieldValueAsBigDecimal(fieldName, BigDecimal.ZERO))
					.mapToInt(valueBD -> NumberUtils.stripTrailingDecimalZeros(valueBD).scale())
					.max()
					.orElse(0);

			return ViewResultColumn.builder()
					.fieldName(fieldName)
					.widgetType(widgetType)
					.maxPrecision(maxPrecision)
					.build();
		}
		else
		{
			return null;
		}
	}

	@Override
	public ViewResult getPageWithRowIdsOnly(
			final int firstRow,
			final int pageLength,
			@NonNull final ViewRowsOrderBy orderBy)
	{
		assertNotClosed();
		checkChangedRows();

		final ViewEvaluationCtx evalCtx = getViewEvaluationCtx();
		final ViewRowIdsOrderedSelection orderedSelection = getOrderedSelection(orderBy.toDocumentQueryOrderByList());

		final List<DocumentId> rowIds = viewDataRepository.retrieveRowIdsByPage(evalCtx, orderedSelection, firstRow, pageLength);

		return ViewResult.builder()
				.view(this)
				.firstRow(firstRow)
				.pageLength(pageLength)
				.orderBys(orderedSelection.getOrderBys())
				.rowIds(rowIds)
				.build();
	}

	@Override
	public IViewRow getById(final DocumentId rowId)
	{
		assertNotClosed();
		return getOrRetrieveById(rowId);
	}

	private IViewRow getOrRetrieveById(final DocumentId rowId)
	{
		checkChangedRows();

		return cache_rowsById.getOrLoad(rowId, () -> retrieveRowById(rowId));
	}

	private IViewRow retrieveRowById(final DocumentId rowId)
	{
		final ViewEvaluationCtx evalCtx = getViewEvaluationCtx();
		return viewDataRepository.retrieveById(evalCtx, getViewId(), rowId);
	}

	private ViewRowIdsOrderedSelection getOrderedSelection(final List<DocumentQueryOrderBy> orderBys)
	{
		return selectionsRef.get()
				.computeIfAbsent(
						orderBys,
						(defaultSelection, orderBysImmutable) -> viewDataRepository.createOrderedSelectionFromSelection(getViewEvaluationCtx(), defaultSelection, orderBysImmutable));
	}

	@Override
	public String getSqlWhereClause(final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
	{
		return viewDataRepository.getSqlWhereClause(getViewId(), getAllFilters(), rowIds, sqlOpts);
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		assertNotClosed();

		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.get()
				.findEntities(ctx);
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		assertNotClosed();

		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.get()
				.findEntities(ctx, query);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return false;
	}

	@Override
	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return Stream.empty();
		}
		else if (rowIds.isAll())
		{
			assertNotClosed();
			checkChangedRows();

			final ViewEvaluationCtx evalCtx = getViewEvaluationCtx();
			final ViewRowIdsOrderedSelection orderedSelection = selectionsRef.get().getDefaultSelection();

			return IteratorUtils.<IViewRow> newPagedIterator()
					.firstRow(0)
					.maxRows(1000) // MAX rows to fetch
					.pageSize(100) // fetch 100items/chunk
					.pageFetcher((firstRow, pageSize) -> Page.ofRowsOrNull(viewDataRepository.retrievePage(evalCtx, orderedSelection, firstRow, pageSize)))
					.build()
					.stream();
		}
		else
		{
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
					.filter(Predicates.notNull());
		}
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		return viewDataRepository.retrieveModelsByIds(getViewId(), rowIds, modelClass);
	}

	@Override
	public void notifyRecordsChanged(final TableRecordReferenceSet recordRefs)
	{
		final Set<DocumentId> rowIds = viewInvalidationAdvisor.findAffectedRowIds(recordRefs, this);
		if (rowIds.isEmpty())
		{
			return;
		}

		//
		// Schedule rows to be checked and added or removed from current view
		if (refreshViewOnChangeEvents)
		{
			changedRowIdsToCheck.addChangedRows(rowIds);
		}

		// Invalidate local rowsById cache
		cache_rowsById.removeAll(rowIds);

		// Collect event
		// TODO: check which rowIds are contained in this view and fire events only for those
		ViewChangesCollector.getCurrentOrAutoflush().collectRowsChanged(this, rowIds);
	}

	private void checkChangedRows()
	{
		if (!refreshViewOnChangeEvents)
		{
			return;
		}

		changedRowIdsToCheck.process(rowIds -> checkChangedRows(rowIds));
	}

	private ViewRowIdsOrderedSelection checkChangedRows(final Set<DocumentId> rowIds)
	{
		return selectionsRef
				.get()
				.computeDefaultSelection(defaultSelection -> viewDataRepository.removeRowIdsNotMatchingFilters(defaultSelection, getAllFilters(), rowIds));
	}

	@Override
	public void patchViewRow(final RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final DocumentId rowId = ctx.getRowId();
		final DocumentCollection documentsCollection = ctx.getDocumentsCollection();
		final DocumentPath documentPath = getById(rowId).getDocumentPath();

		Services.get(ITrxManager.class)
				.runInThreadInheritedTrx(() -> documentsCollection.forDocumentWritable(documentPath, NullDocumentChangesCollector.instance, document -> {
					//
					// Process changes and the save the document
					document.processValueChanges(fieldChangeRequests, ReasonSupplier.NONE);
					document.saveIfValidAndHasChanges();

					//
					// Important: before allowing the document to be stored back in documents collection,
					// we need to make sure it's valid and saved.
					final DocumentValidStatus validStatus = document.getValidStatus();
					if (!validStatus.isValid())
					{
						throw new AdempiereException(validStatus.getReason());
					}
					final DocumentSaveStatus saveStatus = document.getSaveStatus();
					if (!saveStatus.isSavedOrDeleted())
					{
						throw new AdempiereException(saveStatus.getReason());
					}

					//
					return null; // nothing/not important
				}));

		invalidateRowById(rowId);
		ViewChangesCollector.getCurrentOrAutoflush().collectRowChanged(this, rowId);

		documentsCollection.invalidateRootDocument(documentPath);
	}

	@Override
	public LookupValuesList getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		final DocumentId rowId = ctx.getRowId();
		final DocumentCollection documentsCollection = ctx.getDocumentsCollection();
		final DocumentPath documentPath = getById(rowId).getDocumentPath();

		return documentsCollection.forDocumentReadonly(documentPath, document -> document.getFieldLookupValuesForQuery(fieldName, query));
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		final DocumentId rowId = ctx.getRowId();
		final DocumentCollection documentsCollection = ctx.getDocumentsCollection();
		final DocumentPath documentPath = getById(rowId).getDocumentPath();

		return documentsCollection.forDocumentReadonly(documentPath, document -> document.getFieldLookupValues(fieldName));
	}

	//
	//
	//
	//
	//
	private static class ChangedRowIdsCollector
	{
		private final HashSet<DocumentId> rowIds = new HashSet<>();

		public synchronized void process(@NonNull final Consumer<Set<DocumentId>> consumer)
		{
			if (rowIds.isEmpty())
			{
				return;
			}

			consumer.accept(rowIds);
			rowIds.clear();
		}

		public synchronized void addChangedRows(@NonNull final Collection<DocumentId> rowIdsToAdd)
		{
			rowIds.addAll(rowIdsToAdd);
		}
	}

	//
	//
	//
	//
	//

	@FunctionalInterface
	private interface ViewRowIdsOrderedSelectionFactory
	{
		ViewRowIdsOrderedSelection create(ViewRowIdsOrderedSelection defaultSelection, List<DocumentQueryOrderBy> orderBys);
	}

	//
	//
	//

	private static final class ViewRowIdsOrderedSelections
	{
		private ViewRowIdsOrderedSelection defaultSelection;
		private final HashMap<ImmutableList<DocumentQueryOrderBy>, ViewRowIdsOrderedSelection> selectionsByOrderBys = new HashMap<>();

		public ViewRowIdsOrderedSelections(@NonNull final ViewRowIdsOrderedSelection defaultSelection)
		{
			this.defaultSelection = defaultSelection;
		}

		public synchronized ViewRowIdsOrderedSelection getDefaultSelection()
		{
			return defaultSelection;
		}

		public synchronized ViewRowIdsOrderedSelection computeDefaultSelection(@NonNull final UnaryOperator<ViewRowIdsOrderedSelection> mapper)
		{
			final ViewRowIdsOrderedSelection newDefaultSelection = mapper.apply(defaultSelection);
			if (newDefaultSelection == null)
			{
				throw new AdempiereException("null default selection is not allowed");
			}

			if (!defaultSelection.equals(newDefaultSelection))
			{
				this.defaultSelection = newDefaultSelection;
				selectionsByOrderBys.clear();
			}

			return defaultSelection;
		}

		public synchronized ViewRowIdsOrderedSelection computeIfAbsent(final List<DocumentQueryOrderBy> orderBys, @NonNull final ViewRowIdsOrderedSelectionFactory factory)
		{
			if (orderBys == null || orderBys.isEmpty())
			{
				return defaultSelection;
			}

			if (Objects.equals(defaultSelection.getOrderBys(), orderBys))
			{
				return defaultSelection;
			}

			return selectionsByOrderBys.computeIfAbsent(ImmutableList.copyOf(orderBys), orderBysImmutable -> factory.create(defaultSelection, orderBysImmutable));
		}

		public synchronized ImmutableSet<String> getSelectionIds()
		{
			final ImmutableSet.Builder<String> selectionIds = ImmutableSet.builder();
			selectionIds.add(defaultSelection.getSelectionId());
			for (final ViewRowIdsOrderedSelection selection : new ArrayList<>(selectionsByOrderBys.values()))
			{
				selectionIds.add(selection.getSelectionId());
			}

			return selectionIds.build();
		}
	}

	//
	//
	// Builder
	//
	//
	public static final class Builder
	{
		private ViewId viewId;
		private JSONViewDataType viewType;
		private ViewProfileId profileId;
		private Set<DocumentPath> referencingDocumentPaths;
		private ViewId parentViewId;
		private DocumentId parentRowId;
		private final IViewDataRepository viewDataRepository;

		private LinkedHashMap<String, DocumentFilter> _stickyFiltersById;
		private LinkedHashMap<String, DocumentFilter> _filtersById = new LinkedHashMap<>();
		private boolean refreshViewOnChangeEvents = false;

		private IViewInvalidationAdvisor viewInvalidationAdvisor = DefaultViewInvalidationAdvisor.instance;

		private boolean applySecurityRestrictions = true;

		private Builder(@NonNull final IViewDataRepository viewDataRepository)
		{
			this.viewDataRepository = viewDataRepository;
		}

		public DefaultView build()
		{
			return new DefaultView(this);
		}

		public Builder setParentViewId(final ViewId parentViewId)
		{
			this.parentViewId = parentViewId;
			return this;
		}

		public Builder setParentRowId(final DocumentId parentRowId)
		{
			this.parentRowId = parentRowId;
			return this;
		}

		private DocumentId getParentRowId()
		{
			return parentRowId;
		}

		public Builder setViewId(final ViewId viewId)
		{
			this.viewId = viewId;
			return this;
		}

		@NonNull
		public ViewId getViewId()
		{
			return viewId;
		}

		public Builder setViewType(final JSONViewDataType viewType)
		{
			this.viewType = viewType;
			return this;
		}

		public JSONViewDataType getViewType()
		{
			return viewType;
		}

		public Builder setProfileId(ViewProfileId profileId)
		{
			this.profileId = profileId;
			return this;
		}

		public ViewProfileId getProfileId()
		{
			return profileId;
		}

		public Builder setReferencingDocumentPaths(final Set<DocumentPath> referencingDocumentPaths)
		{
			this.referencingDocumentPaths = referencingDocumentPaths;
			return this;
		}

		private ImmutableSet<DocumentPath> getReferencingDocumentPaths()
		{
			return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
		}

		private ViewId getParentViewId()
		{
			return parentViewId;
		}

		private IViewDataRepository getViewDataRepository()
		{
			return viewDataRepository;
		}

		private DocumentFilterDescriptorsProvider getViewFilterDescriptors()
		{
			return viewDataRepository.getViewFilterDescriptors();
		}

		public Builder addStickyFilter(@Nullable final DocumentFilter stickyFilter)
		{
			if (stickyFilter == null)
			{
				return this;
			}

			if (_stickyFiltersById == null)
			{
				_stickyFiltersById = new LinkedHashMap<>();
			}
			_stickyFiltersById.put(stickyFilter.getFilterId(), stickyFilter);

			return this;
		}

		public Builder addStickyFilters(final List<DocumentFilter> stickyFilters)
		{
			if (stickyFilters == null || stickyFilters.isEmpty())
			{
				return this;
			}

			stickyFilters.forEach(this::addStickyFilter);

			return this;
		}

		private ImmutableList<DocumentFilter> getStickyFilters()
		{
			return _stickyFiltersById == null ? ImmutableList.of() : ImmutableList.copyOf(_stickyFiltersById.values());
		}

		public Builder setFilters(final List<DocumentFilter> filters)
		{
			_filtersById.clear();
			filters.forEach(filter -> _filtersById.put(filter.getFilterId(), filter));
			return this;
		}

		public Builder setFiltersFromJSON(final List<JSONDocumentFilter> jsonFilters)
		{
			setFilters(JSONDocumentFilter.unwrapList(jsonFilters, getViewFilterDescriptors()));
			return this;
		}

		private ImmutableList<DocumentFilter> getFilters()
		{
			return _filtersById.isEmpty() ? ImmutableList.of() : ImmutableList.copyOf(_filtersById.values());
		}

		public boolean hasFilters()
		{
			return !_filtersById.isEmpty();
		}

		public Builder addFiltersIfAbsent(final Collection<DocumentFilter> filters)
		{
			filters.forEach(filter -> _filtersById.putIfAbsent(filter.getFilterId(), filter));
			return this;
		}

		public Builder refreshViewOnChangeEvents(boolean refreshViewOnChangeEvents)
		{
			this.refreshViewOnChangeEvents = refreshViewOnChangeEvents;
			return this;
		}

		public boolean isRefreshViewOnChangeEvents()
		{
			return refreshViewOnChangeEvents;
		}

		public Builder viewInvalidationAdvisor(@NonNull final IViewInvalidationAdvisor viewInvalidationAdvisor)
		{
			this.viewInvalidationAdvisor = viewInvalidationAdvisor;
			return this;
		}

		private IViewInvalidationAdvisor getViewInvalidationAdvisor()
		{
			return viewInvalidationAdvisor;
		}

		public Builder applySecurityRestrictions(final boolean applySecurityRestrictions)
		{
			this.applySecurityRestrictions = applySecurityRestrictions;
			return this;
		}

		private boolean isApplySecurityRestrictions()
		{
			return applySecurityRestrictions;
		}
	}
}
