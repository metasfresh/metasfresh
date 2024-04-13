package de.metas.ui.web.view;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.standard.FacetFilterViewCacheMap;
import de.metas.ui.web.document.references.WebuiDocumentReferenceId;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentFieldLogicExpressionResultRevaluator;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.ui.web.window.model.sql.SqlValueConverters;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import de.metas.util.collections.PagedIterator.Page;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.OldAndNewValues;
import org.adempiere.util.lang.SynchronizedMutable;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.POInfo;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
 */
public final class DefaultView implements IEditableView
{
	public static Builder builder(final SqlViewDataRepository viewDataRepository)
	{
		return new Builder(viewDataRepository);
	}

	public static DefaultView cast(@NonNull final IView view)
	{
		return (DefaultView)view;
	}

	private static final Logger logger = LogManager.getLogger(DefaultView.class);

	@Getter
	private final SqlViewDataRepository viewDataRepository;

	@Getter
	private final ViewId viewId;
	private final AtomicBoolean closed = new AtomicBoolean(false);
	@Getter
	private final ViewId parentViewId;
	@Getter
	private final DocumentId parentRowId;
	@Getter
	private final JSONViewDataType viewType;
	@Getter
	private final ViewProfileId profileId;

	private final ViewHeaderPropertiesProvider headerPropertiesProvider;
	private final SynchronizedMutable<ViewHeaderProperties> headerPropertiesHolder;

	@Getter
	private final ImmutableSet<DocumentPath> referencingDocumentPaths;
	private final WebuiDocumentReferenceId documentReferenceId;

	@Getter
	private final ViewEvaluationCtx viewEvaluationCtx;
	private final ViewRowIdsOrderedSelectionsHolder selectionsRef;

	//
	// Filters
	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;
	/**
	 * Sticky filters (i.e. active filters which cannot be changed)
	 */
	@Getter
	private final DocumentFilterList stickyFilters;
	/**
	 * Regular filters
	 */
	@Getter
	private final DocumentFilterList filters;
	private transient DocumentFilterList _allFilters; // lazy
	@Getter
	private final FacetFilterViewCacheMap facetFiltersCacheMap = FacetFilterViewCacheMap.newInstance();

	//
	// Misc
	private transient String _toString; // lazy

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
		headerPropertiesProvider = CoalesceUtil.coalesce(builder.headerPropertiesProvider, NullViewHeaderPropertiesProvider.instance);
		headerPropertiesHolder = SynchronizedMutable.of(null);
		referencingDocumentPaths = builder.getReferencingDocumentPaths();
		documentReferenceId = builder.getDocumentReferenceId();
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

			selectionsRef = ViewRowIdsOrderedSelectionsHolder.builder()
					.viewDataRepository(viewDataRepository)
					.viewId(viewId)
					.applySecurityRestrictions(builder.isApplySecurityRestrictions())
					.stickyFilters(stickyFilters)
					.filters(filters)
					.viewEvaluationCtxSupplier(this::getViewEvaluationCtx)
					.build();
		}

		//
		// Cache
		cache_rowsById = CCache.<DocumentId, IViewRow>builder()
				.cacheMapType(CacheMapType.LRU)
				.cacheName("ViewRows#" + viewId)
				.additionalTableNameToResetFor(viewDataRepository.getTableName())
				.initialCapacity(100) // i.e. max size
				.expireMinutes(2)
				.build();

		logger.debug("View created: {}", this);
	}

	@Override
	public String toString()
	{
		String toString = _toString;
		if (toString == null)
		{
			final ViewRowIdsOrderedSelection defaultSelection = selectionsRef.getDefaultSelection();
			// NOTE: keep it short
			toString = _toString = MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("viewId", defaultSelection.getViewId())
					.add("tableName", viewDataRepository.getTableName())
					.add("parentViewId", parentViewId)
					.add("defaultSelection", defaultSelection)
					.toString();
		}
		return toString;
	}

	@Override
	public ITranslatableString getDescription()
	{
		return TranslatableStrings.empty();
	}

	@Override
	public ViewHeaderProperties getHeaderProperties()
	{
		return headerPropertiesHolder.computeIfNull(() -> headerPropertiesProvider.computeHeaderProperties(this));
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
		return selectionsRef.getSize();
	}

	@Override
	public DocumentQueryOrderByList getDefaultOrderBys()
	{
		return selectionsRef.getDefaultOrderBys();
	}

	@Override
	public int getQueryLimit()
	{
		return selectionsRef.getQueryLimit();
	}

	@Override
	public boolean isQueryLimitHit()
	{
		return selectionsRef.isQueryLimitHit();
	}

	@Override
	public EmptyReason getEmptyReason() {return selectionsRef.getEmptyReason();}

	public ViewRowIdsOrderedSelection getDefaultSelectionBeforeFacetsFiltering()
	{
		return selectionsRef.getDefaultSelectionBeforeFacetsFiltering();
	}

	public DocumentFilterDescriptorsProvider getFilterDescriptors()
	{
		return viewDataRepository.getViewFilterDescriptors();
	}

	private DocumentFilterList getAllFilters()
	{
		DocumentFilterList allFilters = _allFilters;
		if (allFilters == null)
		{
			_allFilters = allFilters = getFilters().mergeWith(getStickyFilters());
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

		selectionsRef.forgetCurrentSelections();

		logger.debug("View closed with reason={}: {}", reason, this);
	}

	@Override
	public void afterDestroy()
	{
		selectionsRef.forgetCurrentSelections();
	}

	@Override
	public void invalidateAll()
	{
		cache_rowsById.reset();
		headerPropertiesHolder.setValue(null);
	}

	@Override
	public void invalidateRowById(final DocumentId rowId)
	{
		cache_rowsById.remove(rowId);
		headerPropertiesHolder.setValue(null);
	}

	@Override
	public void invalidateSelection()
	{
		selectionsRef.forgetCurrentSelections();
		headerPropertiesHolder.setValue(null);

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

	@Override
	public ViewResult getPage(
			final int firstRow,
			final int pageLength,
			final ViewRowsOrderBy orderBy)
	{
		assertNotClosed();
		checkChangedRows(AddRemoveChangedRowIdsCollector.NOT_RECORDING);

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
				.emptyReason(orderedSelection.getEmptyReason())
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
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
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
		checkChangedRows(AddRemoveChangedRowIdsCollector.NOT_RECORDING);

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
		checkChangedRows(AddRemoveChangedRowIdsCollector.NOT_RECORDING);

		return cache_rowsById.getOrLoad(rowId, () -> retrieveRowById(rowId));
	}

	private IViewRow retrieveRowById(final DocumentId rowId)
	{
		final ViewEvaluationCtx evalCtx = getViewEvaluationCtx();
		return viewDataRepository.retrieveById(evalCtx, getViewId(), rowId);
	}

	private ViewRowIdsOrderedSelection getOrderedSelection(final DocumentQueryOrderByList orderBys)
	{
		return selectionsRef.getOrderedSelection(orderBys);
	}

	@Override
	public SqlViewRowsWhereClause getSqlWhereClause(final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
	{
		return viewDataRepository.getSqlWhereClause(getViewId(), getAllFilters(), rowIds, sqlOpts);
	}

	@Override
	public LookupValuesPage getFilterParameterDropdown(final String filterId, final String filterParameterName, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		assertNotClosed();

		final Evaluatee ctxEffective = ctx.mapParameterValues(parameterValues -> normalizeFilterParameterValues(parameterValues, filterId)).toEvaluatee();

		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.orElseThrow(() -> new AdempiereException("No lookup found for filterId=" + filterId + ", filterParameterName=" + filterParameterName))
				.findEntities(ctxEffective);
	}

	@Override
	public LookupValuesPage getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		assertNotClosed();

		final Evaluatee ctxEffective = ctx.mapParameterValues(parameterValues -> normalizeFilterParameterValues(parameterValues, filterId)).toEvaluatee();

		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.orElseThrow(() -> new AdempiereException("No lookup found for filterId=" + filterId + ", filterParameterName=" + filterParameterName))
				.findEntities(ctxEffective, query);
	}

	private Map<String, Object> normalizeFilterParameterValues(
			@Nullable final Map<String, Object> parameterValues,
			@NonNull final String filterId)
	{
		if (parameterValues == null || parameterValues.isEmpty())
		{
			return ImmutableMap.of();
		}

		final DocumentFilterDescriptor filterDescriptor = getFilterDescriptors().getByFilterId(filterId);

		final String tableName = getViewDataRepository().getTableName();
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		if (poInfo == null)
		{
			// shall not happen
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<String, Object> result = ImmutableMap.builder();
		for (final String parameterName : parameterValues.keySet())
		{
			if (!filterDescriptor.hasParameter(parameterName))
			{
				continue;
			}
			if (!poInfo.hasColumnName(parameterName))
			{
				// shall not happen in case of DefaultView(s)
				continue;
			}

			final DocumentFilterParamDescriptor parameterDescriptor = filterDescriptor.getParameterByName(parameterName);
			final DocumentFieldWidgetType widgetType = parameterDescriptor.getWidgetType();
			final Class<?> poValueClass = poInfo.getColumnClass(parameterName);
			final Object value = parameterValues.get(parameterName);
			final Object valueNorm = SqlValueConverters.convertToPOValue(value, parameterName, widgetType, poValueClass);

			if (valueNorm != null)
			{
				result.put(parameterName, valueNorm);
			}
		}

		return result.build();
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return false;
	}

	@Override
	public Stream<? extends IViewRow> streamByIds(@NonNull final DocumentIdsSelection rowIds)
	{
		return streamByIds(rowIds, QueryLimit.ONE_THOUSAND);
	}

	@Override
	public Stream<? extends IViewRow> streamByIds(@NonNull final DocumentIdsSelection rowIds, @NonNull final QueryLimit suggestedLimit)
	{
		if (rowIds.isEmpty())
		{
			return Stream.empty();
		}
		else if (rowIds.isAll())
		{
			assertNotClosed();
			checkChangedRows(AddRemoveChangedRowIdsCollector.NOT_RECORDING);

			final ViewEvaluationCtx evalCtx = getViewEvaluationCtx();
			final ViewRowIdsOrderedSelection orderedSelection = selectionsRef.getDefaultSelection();

			return IteratorUtils.<IViewRow>newPagedIterator()
					.firstRow(0)
					.maxRows(suggestedLimit.toIntOrZero()) // MAX rows to fetch
					.pageSize(100) // fetch 100items/chunk
					.pageFetcher((firstRow, pageSize) -> Page.ofRowsOrNull(viewDataRepository.retrievePage(evalCtx, orderedSelection, firstRow, pageSize)))
					.build()
					.stream();
		}
		else
		{
			// NOTE: we get/retrieve one by one because we assume the "selected documents" were recently retrieved,
			// and the records recently retrieved have a big chance to be cached.
			return rowIds.stream()
					.distinct()
					.limit(suggestedLimit.isLimited() ? suggestedLimit.toInt() : Long.MAX_VALUE)
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
					.filter(Objects::nonNull);
		}
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		return viewDataRepository.retrieveModelsByIds(getViewId(), rowIds, modelClass);
	}

	@NonNull
	@Override
	public <T> Stream<T> streamModelsByIds(
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final Class<T> modelClass)
	{
		return viewDataRepository.retrieveModelsByIdsAsStream(getViewId(), rowIds, modelClass);
	}

	@Override
	public void notifyRecordsChanged(
			@NonNull final TableRecordReferenceSet recordRefs,
			final boolean watchedByFrontend)
	{
		Set<DocumentId> rowIds = viewInvalidationAdvisor.findAffectedRowIds(recordRefs, watchedByFrontend, this);
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

		checkCollectHeaderPropertiesChanged(rowIds, watchedByFrontend);

		// If the view is watched by a frontend browser, make sure we will notify only for rows which are part of that view
		// TODO: introduce a SysConfig to be able to disable this feature
		boolean hasViewAdditions = false;
		if (watchedByFrontend)
		{
			// Process changed rows because if not, "retainExistingRowIds" will consider only the changed rows. The added or removed rowIds will be "discarded".
			// And for the particular case when we have only new rows to be added the rowIds will get empty a websocket event will be sent to frontend.
			if (refreshViewOnChangeEvents)
			{
				final AddRemoveChangedRowIdsCollector changesCollector = AddRemoveChangedRowIdsCollector.newRecording();
				checkChangedRows(changesCollector);

				if (changesCollector.hasAddedRows())
				{
					hasViewAdditions = true;
				}

				rowIds = changesCollector.getChangedOrRemovedRowIds();
			}
			else
			{
				rowIds = selectionsRef.retainExistingRowIds(rowIds);
			}
		}

		// Collect event
		if (!hasViewAdditions && rowIds.isEmpty())
		{
			// do nothing
		}
		// IMPORTANT: atm in case many rows were changed, avoid sending them to frontend.
		// Better notify the frontend that the whole view changed,
		// so the frontend would fetch the whole page instead of querying 1k of changed rows.
		//
		// Also, in case the view has additions we have to ask for page reload,
		// because frontend does not react to rowIds which are not in its list.
		// To optimize, it would be better to tell frontend that it was a row addition.
		else if (hasViewAdditions || rowIds.size() >= 20)
		{
			ViewChangesCollector.getCurrentOrAutoflush().collectFullyChanged(this);
		}
		else
		{
			ViewChangesCollector.getCurrentOrAutoflush().collectRowsChanged(this, rowIds);
		}
	}

	private void checkCollectHeaderPropertiesChanged(
			@NonNull final Set<DocumentId> rowIds,
			final boolean watchedByFrontend)
	{
		final OldAndNewValues<ViewHeaderProperties> oldAndNewHeaderProperties = headerPropertiesHolder
				.computeIfNotNullReturningOldAndNew(currentHeaderProperties -> {
					final ViewHeaderPropertiesIncrementalResult newHeaderPropertiesResult = headerPropertiesProvider.computeIncrementallyOnRowsChanged(
							currentHeaderProperties,
							this,
							rowIds,
							watchedByFrontend);
					if (newHeaderPropertiesResult.isComputed())
					{
						return newHeaderPropertiesResult.getComputeHeaderProperties();
					}
					else if (newHeaderPropertiesResult.isFullRecomputeRequired())
					{
						return null; // reset
					}
					else
					{
						throw new AdempiereException("Unknown result type: " + newHeaderPropertiesResult);
					}
				});

		if (oldAndNewHeaderProperties.isValueChanged())
		{
			ViewChangesCollector.getCurrentOrAutoflush().collectHeaderPropertiesChanged(this);
		}
	}

	private void checkChangedRows(@NonNull final AddRemoveChangedRowIdsCollector changesCollector)
	{
		changedRowIdsToCheck.process(rowIds -> selectionsRef.updateChangedRows(rowIds, changesCollector));
	}

	@Override
	public void patchViewRow(final RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final DocumentId rowId = ctx.getRowId();
		final DocumentCollection documentsCollection = ctx.getDocumentsCollection();
		final DocumentPath documentPath = getRowDocumentPath(rowId);
		final DocumentFieldLogicExpressionResultRevaluator readonlyRevaluator = DocumentFieldLogicExpressionResultRevaluator.using(ctx.getUserRolePermissions());

		Services.get(ITrxManager.class)
				.runInThreadInheritedTrx(
						() -> documentsCollection.forDocumentWritable(
								documentPath,
								NullDocumentChangesCollector.instance,
								document -> {
									patchDocument(document, fieldChangeRequests, readonlyRevaluator);
									return null;
								}));

		invalidateRowById(rowId);
		ViewChangesCollector.getCurrentOrAutoflush().collectRowChanged(this, rowId);

		documentsCollection.invalidateRootDocument(documentPath);
	}

	@NonNull
	private DocumentPath getRowDocumentPath(final DocumentId rowId)
	{
		return Check.assumeNotNull(getById(rowId).getDocumentPath(), "No documentPath for {}", rowId);
	}

	private void patchDocument(
			@NonNull final Document document,
			@NonNull final List<JSONDocumentChangedEvent> fieldChangeRequests,
			@NonNull final DocumentFieldLogicExpressionResultRevaluator readonlyRevaluator)
	{
		//
		// Process changes and the save the document
		document.processValueChanges(fieldChangeRequests, ReasonSupplier.NONE, readonlyRevaluator);
		document.saveIfValidAndHasChanges();

		//
		// Important: before allowing the document to be stored back in documents collection,
		// we need to make sure it's valid and saved.
		document.getValidStatus().throwIfInvalid();
		document.getSaveStatus().throwIfNotSavedNorDelete();
	}

	@Override
	public LookupValuesPage getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		final DocumentCollection documentsCollection = ctx.getDocumentsCollection();
		final DocumentPath documentPath = getRowDocumentPath(ctx.getRowId());

		return documentsCollection.forDocumentReadonly(documentPath, document -> document.getFieldLookupValuesForQuery(fieldName, query));
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		final DocumentCollection documentsCollection = ctx.getDocumentsCollection();
		final DocumentPath documentPath = getRowDocumentPath(ctx.getRowId());

		return documentsCollection.forDocumentReadonly(documentPath, document -> document.getFieldLookupValues(fieldName));
	}

	//
	//
	//
	//
	//
	@ToString
	private static class ChangedRowIdsCollector
	{
		private final HashSet<DocumentId> _rowIds = new HashSet<>();

		public void process(@NonNull final Consumer<Set<DocumentId>> consumer)
		{
			final ImmutableSet<DocumentId> rowIds = getRowIdsAndClear();
			if (rowIds.isEmpty())
			{
				return;
			}

			consumer.accept(rowIds);
		}

		private synchronized ImmutableSet<DocumentId> getRowIdsAndClear()
		{
			if (_rowIds.isEmpty())
			{
				return ImmutableSet.of();
			}

			final ImmutableSet<DocumentId> rowIdsCopy = ImmutableSet.copyOf(_rowIds);
			_rowIds.clear();

			return rowIdsCopy;
		}

		public synchronized void addChangedRows(@NonNull final Collection<DocumentId> rowIdsToAdd)
		{
			_rowIds.addAll(rowIdsToAdd);
		}
	}

	//
	//
	// Builder
	//
	//
	@SuppressWarnings("UnusedReturnValue")
	public static final class Builder
	{
		private ViewId viewId;
		private JSONViewDataType viewType;
		private ViewProfileId profileId;
		private ViewHeaderPropertiesProvider headerPropertiesProvider;
		private Set<DocumentPath> referencingDocumentPaths;
		private WebuiDocumentReferenceId documentReferenceId;
		private ViewId parentViewId;
		private DocumentId parentRowId;
		private final SqlViewDataRepository viewDataRepository;

		private final Set<DocumentFilter> _addedFiltersNoDuplicates = new HashSet<>();
		private LinkedHashMap<String, DocumentFilter> _stickyFiltersById;
		private final LinkedHashMap<String, DocumentFilter> _filtersById = new LinkedHashMap<>();
		private boolean refreshViewOnChangeEvents = false;

		private IViewInvalidationAdvisor viewInvalidationAdvisor = DefaultViewInvalidationAdvisor.instance;

		private boolean applySecurityRestrictions = true;

		private Builder(@NonNull final SqlViewDataRepository viewDataRepository)
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

		public Builder setProfileId(final ViewProfileId profileId)
		{
			this.profileId = profileId;
			return this;
		}

		public ViewProfileId getProfileId()
		{
			return profileId;
		}

		public Builder setHeaderPropertiesProvider(@NonNull final ViewHeaderPropertiesProvider headerPropertiesProvider)
		{
			this.headerPropertiesProvider = headerPropertiesProvider;
			return this;
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

		public Builder setDocumentReferenceId(final WebuiDocumentReferenceId documentReferenceId)
		{
			this.documentReferenceId = documentReferenceId;
			return this;
		}

		private WebuiDocumentReferenceId getDocumentReferenceId()
		{
			return documentReferenceId;
		}

		private ViewId getParentViewId()
		{
			return parentViewId;
		}

		private SqlViewDataRepository getViewDataRepository()
		{
			return viewDataRepository;
		}

		private DocumentFilterDescriptorsProvider getViewFilterDescriptors()
		{
			return viewDataRepository.getViewFilterDescriptors();
		}

		public Builder addStickyFilterSkipDuplicates(@Nullable final DocumentFilter stickyFilter)
		{
			if (stickyFilter == null)
			{
				return this;
			}

			if (_stickyFiltersById == null)
			{
				_stickyFiltersById = new LinkedHashMap<>();
			}

			final boolean notDuplicate = isNotDuplicateDocumentFilter(stickyFilter);
			if (notDuplicate)
			{
				_stickyFiltersById.put(stickyFilter.getFilterId(), stickyFilter);
			}

			return this;
		}

		/**
		 * Check if the filter was already added in this view either as a normal or a sticky filter.
		 * <p>
		 * This can be seen as "equals ignoring filterId".
		 *
		 * @return true if the filter is a new one
		 */
		private boolean isNotDuplicateDocumentFilter(@NonNull final DocumentFilter filterToAdd)
		{
			return _addedFiltersNoDuplicates.add(filterToAdd.withId("AvoidDuplicateFiltersThatOnlyDifferInTheirId"));
		}

		public Builder addStickyFilters(final DocumentFilterList stickyFilters)
		{
			if (stickyFilters == null || stickyFilters.isEmpty())
			{
				return this;
			}

			stickyFilters.forEach(this::addStickyFilterSkipDuplicates);

			return this;
		}

		private DocumentFilterList getStickyFilters()
		{
			return _stickyFiltersById == null ? DocumentFilterList.EMPTY : DocumentFilterList.ofList(_stickyFiltersById.values());
		}

		public Builder setFilters(final DocumentFilterList filters)
		{
			_filtersById.clear();
			filters.forEach(filter -> {
				final boolean notDuplicate = isNotDuplicateDocumentFilter(filter);
				if (notDuplicate)
				{
					_filtersById.put(filter.getFilterId(), filter);
				}
			});
			return this;
		}

		private DocumentFilterList getFilters()
		{
			return _filtersById.isEmpty() ? DocumentFilterList.EMPTY : DocumentFilterList.ofList(_filtersById.values());
		}

		public Builder addFiltersIfAbsent(final Collection<DocumentFilter> filters)
		{
			filters.forEach(filter -> {
				final boolean notDuplicate = isNotDuplicateDocumentFilter(filter);
				if (notDuplicate)
				{
					_filtersById.putIfAbsent(filter.getFilterId(), filter);
				}
			});
			return this;
		}

		public Builder refreshViewOnChangeEvents(final boolean refreshViewOnChangeEvents)
		{
			this.refreshViewOnChangeEvents = refreshViewOnChangeEvents;
			return this;
		}

		private boolean isRefreshViewOnChangeEvents()
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
