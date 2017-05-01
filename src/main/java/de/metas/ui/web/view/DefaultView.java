package de.metas.ui.web.view;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;
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
class DefaultView implements IView
{
	public static final Builder builder(final IViewDataRepository viewDataRepository)
	{
		return new Builder(viewDataRepository);
	}

	private static final Logger logger = LogManager.getLogger(DefaultView.class);

	private final IViewDataRepository viewDataRepository;

	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final ViewId parentViewId;

	// private final transient IViewRowIdsOrderedSelectionFactory orderedSelectionFactory;
	private final transient ViewRowIdsOrderedSelection defaultSelection;
	private final transient ConcurrentHashMap<ImmutableList<DocumentQueryOrderBy>, ViewRowIdsOrderedSelection> selectionsByOrderBys = new ConcurrentHashMap<>();

	//
	// Filters
	private final transient DocumentFilterDescriptorsProvider viewFilterDescriptors;
	/** Sticky filters (i.e. active filters which cannot be changed) */
	private final List<DocumentFilter> stickyFilters;
	/** Active filters */
	private final List<DocumentFilter> filters;

	//
	// Misc
	private transient String _toString;

	//
	// Caching
	private final transient CCache<DocumentId, IViewRow> cache_rowsById;

	private DefaultView(final Builder builder)
	{
		viewDataRepository = builder.getViewDataRepository();
		parentViewId = builder.getParentViewId();

		//
		// Filters
		viewFilterDescriptors = builder.getViewFilterDescriptors();
		stickyFilters = ImmutableList.copyOf(builder.getStickyFilters());
		filters = ImmutableList.copyOf(builder.getFilters());

		//
		// Selection
		{
			final ViewEvaluationCtx evalCtx = ViewEvaluationCtx.of(Env.getCtx());
			// orderedSelectionFactory = viewDataRepository.createOrderedSelectionFactory(evalCtx);

			defaultSelection = viewDataRepository.createOrderedSelection(
					evalCtx //
					, builder.getWindowId() //
					, ImmutableList.<DocumentFilter> builder().addAll(stickyFilters).addAll(filters).build() //
			);
			selectionsByOrderBys.put(defaultSelection.getOrderBys(), defaultSelection);
		}

		//
		// Cache
		cache_rowsById = CCache.newLRUCache( //
				viewDataRepository.getTableName() + "#rowById#viewId=" + defaultSelection.getSelectionId() // cache name
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
	public ViewId getViewId()
	{
		return defaultSelection.getViewId();
	}

	@Override
	public String getTableName()
	{
		return viewDataRepository.getTableName();
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
	public ViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		assertNotClosed();

		final ViewEvaluationCtx evalCtx = ViewEvaluationCtx.of(Env.getCtx());
		final ViewRowIdsOrderedSelection orderedSelection = getOrderedSelection(orderBys);

		final List<IViewRow> page = viewDataRepository.retrievePage(evalCtx, orderedSelection, firstRow, pageLength);

		// Add to cache
		page.forEach(row -> cache_rowsById.put(row.getId(), row));

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderedSelection.getOrderBys(), page);
	}

	@Override
	public IViewRow getById(final DocumentId rowId)
	{
		assertNotClosed();
		return getOrRetrieveById(rowId);
	}

	private final IViewRow getOrRetrieveById(final DocumentId rowId)
	{
		return cache_rowsById.getOrLoad(rowId, () -> {
			final ViewEvaluationCtx evalCtx = ViewEvaluationCtx.of(Env.getCtx());
			return viewDataRepository.retrieveById(evalCtx, getViewId(), rowId);
		});
	}

	private ViewRowIdsOrderedSelection getOrderedSelection(final List<DocumentQueryOrderBy> orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return defaultSelection;
		}

		if (Objects.equals(defaultSelection.getOrderBys(), orderBys))
		{
			return defaultSelection;
		}

		return selectionsByOrderBys.computeIfAbsent(ImmutableList.copyOf(orderBys), orderBysImmutable -> viewDataRepository.createOrderedSelectionFromSelection(ViewEvaluationCtx.of(Env.getCtx()), defaultSelection, orderBysImmutable));
	}

	@Override
	public String getSqlWhereClause(final Collection<DocumentId> rowIds)
	{
		return viewDataRepository.getSqlWhereClause(getViewId(), rowIds);
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		assertNotClosed();

		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.findEntities(ctx);
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		assertNotClosed();

		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.findEntities(ctx, query);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return false;
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
		return viewDataRepository.retrieveModelsByIds(getViewId(), rowIds, modelClass);
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		final String viewTableName = getTableName();

		final Set<DocumentId> rowIds = recordRefs.stream()
				.filter(recordRef -> Objects.equals(viewTableName, recordRef.getTableName()))
				.map(recordRef -> DocumentId.of(recordRef.getRecord_ID()))
				.collect(GuavaCollectors.toImmutableSet());

		if (rowIds.isEmpty())
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
		private WindowId windowId;
		private ViewId parentViewId;
		private final IViewDataRepository viewDataRepository;

		private List<DocumentFilter> _stickyFilters;
		private List<DocumentFilter> _filters;

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

		public Builder setWindowId(final WindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		public WindowId getWindowId()
		{
			return windowId;
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
			setFilters(JSONDocumentFilter.unwrapList(jsonFilters, getViewFilterDescriptors()));
			return this;
		}

		private List<DocumentFilter> getFilters()
		{
			return _filters;
		}
	}
}
