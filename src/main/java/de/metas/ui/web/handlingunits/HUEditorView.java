package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
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

public class HUEditorView implements IView
{
	public static final Builder builder(final SqlViewBinding sqlViewBinding)
	{
		return new Builder(sqlViewBinding);
	}

	public static HUEditorView cast(final IView view)
	{
		return (HUEditorView)view;
	}

	private final ViewId parentViewId;
	private final DocumentId parentRowId;

	private final ViewId viewId;
	private final JSONViewDataType viewType;

	private final Set<DocumentPath> referencingDocumentPaths;

	private final ViewActionDescriptorsList actions;
	private final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;

	private final HUEditorViewBuffer rowsBuffer;
	private final HUEditorRowAttributesProvider huAttributesProvider;

	private final transient DocumentFilterDescriptorsProvider viewFilterDescriptors;
	private final ImmutableList<DocumentFilter> filters;

	private HUEditorView(final Builder builder)
	{
		parentViewId = builder.getParentViewId();
		parentRowId = builder.getParentRowId();
		viewType = builder.getViewType();

		final List<DocumentFilter> stickyFilters = builder.getStickyFilters();
		final boolean isHighVolume = HUIdsFilterHelper.isHighVolume(stickyFilters);
		//
		// Build the attributes provider
		huAttributesProvider = HUEditorRowAttributesProvider.builder()
				.readonly(isHighVolume)
				.build();

		//
		// Build the repository
		final HUEditorViewRepository huEditorRepo = HUEditorViewRepository.builder()
				.windowId(builder.getWindowId())
				.referencingTableName(builder.getReferencingTableName())
				.attributesProvider(huAttributesProvider)
				.sqlViewBinding(builder.getSqlViewBinding())
				.build();

		viewFilterDescriptors = builder.getSqlViewBinding().getViewFilterDescriptors();

		//
		// Build filters
		filters = ImmutableList.copyOf(builder.getFilters());

		//
		// Build rowsBuffer
		rowsBuffer = createRowsBuffer(builder.getWindowId(), isHighVolume, huEditorRepo, stickyFilters, filters);
		viewId = rowsBuffer.getViewId();

		referencingDocumentPaths = builder.getReferencingDocumentPaths();

		actions = builder.getActions();
		additionalRelatedProcessDescriptors = builder.getAdditionalRelatedProcessDescriptors();
	}

	private static final HUEditorViewBuffer createRowsBuffer( //
			final WindowId windowId //
			, final boolean isHighVolume //
			, final HUEditorViewRepository huEditorRepo //
			, final List<DocumentFilter> stickyFilters //
			, final List<DocumentFilter> filters //
	)
	{
		if (isHighVolume)
		{
			return new HUEditorViewBuffer_HighVolume(windowId, huEditorRepo, stickyFilters, filters);
		}
		else
		{
			return new HUEditorViewBuffer_FullyCached(windowId, huEditorRepo, stickyFilters, filters);
		}

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
	public ITranslatableString getDescription()
	{
		return ImmutableTranslatableString.empty();
	}

	@Override
	public String getTableName()
	{
		return I_M_HU.Table_Name;
	}

	@Override
	public long size()
	{
		return rowsBuffer.size();
	}

	@Override
	public void close()
	{
		invalidateAllNoNotify();
	}

	@Override
	public int getQueryLimit()
	{
		return -1;
	}

	@Override
	public boolean isQueryLimitHit()
	{
		return false;
	}

	@Override
	public ViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final List<HUEditorRow> page = rowsBuffer
				.streamPage(firstRow, pageLength, orderBys)
				.collect(GuavaCollectors.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, page);
	}

	@Override
	public ViewActionDescriptorsList getActions()
	{
		return actions;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return additionalRelatedProcessDescriptors;
	}

	@Override
	public HUEditorRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		return rowsBuffer.getById(rowId);
	}

	@Override
	public List<HUEditorRow> getByIds(final DocumentIdsSelection rowId)
	{
		return streamByIds(rowId).collect(ImmutableList.toImmutableList());
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.findEntities(ctx);
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		return viewFilterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.findEntities(ctx, query);
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return rowsBuffer.getStickyFilters();
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return filters;
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return ImmutableList.of();
	}

	@Override
	public String getSqlWhereClause(@NonNull final DocumentIdsSelection rowIds)
	{
		return rowsBuffer.getSqlWhereClause(rowIds);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return true;
	}

	@Override
	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return referencingDocumentPaths;
	}

	@Override
	public void invalidateAll()
	{
		invalidateAllNoNotify();

		ViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(this);
	}

	private void invalidateAllNoNotify()
	{
		huAttributesProvider.invalidateAll();
		rowsBuffer.invalidateAll();
	}

	public void addHUsAndInvalidate(final Collection<I_M_HU> husToAdd)
	{
		if (rowsBuffer.addHUIds(extractHUIds(husToAdd)))
		{
			invalidateAll();
		}
	}

	public void removesHUsAndInvalidate(final Collection<I_M_HU> husToRemove)
	{
		final Set<Integer> huIdsToRemove = extractHUIds(husToRemove);
		removesHUIdsAndInvalidate(huIdsToRemove);
	}

	public void removesHUIdsAndInvalidate(final Collection<Integer> huIdsToRemove)
	{
		if (rowsBuffer.removeHUIds(huIdsToRemove))
		{
			invalidateAll();
		}
	}

	private static final Set<Integer> extractHUIds(final Collection<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return ImmutableSet.of();
		}

		return hus.stream().filter(hu -> hu != null).map(I_M_HU::getM_HU_ID).collect(Collectors.toSet());
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		// TODO: notifyRecordsChanged:
		// get M_HU_IDs from recordRefs,
		// find the top level records from this view which contain our HUs
		// invalidate those top levels only

		final Set<Integer> huIdsToCheck = recordRefs.stream()
				.filter(recordRef -> I_M_HU.Table_Name.equals(recordRef.getTableName()))
				.map(recordRef -> recordRef.getRecord_ID())
				.collect(ImmutableSet.toImmutableSet());
		if (huIdsToCheck.isEmpty())
		{
			return;
		}

		final boolean containsSomeRecords = rowsBuffer.containsAnyOfHUIds(huIdsToCheck);
		if (!containsSomeRecords)
		{
			return;
		}

		invalidateAll();
	}

	@Override
	public Stream<HUEditorRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		return rowsBuffer.streamByIdsExcludingIncludedRows(rowIds);
	}

	/** @return top level rows and included rows recursive stream */
	public Stream<HUEditorRow> streamAllRecursive()
	{
		return rowsBuffer.streamAllRecursive();
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		final Set<Integer> huIds = streamByIds(rowIds)
				.filter(HUEditorRow::isPureHU)
				.map(HUEditorRow::getM_HU_ID)
				.collect(GuavaCollectors.toImmutableSet());
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_M_HU> hus = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.list(I_M_HU.class);

		return InterfaceWrapperHelper.createList(hus, modelClass);
	}

	//
	//
	//

	public static final class Builder
	{
		private final SqlViewBinding sqlViewBinding;
		private ViewId parentViewId;
		private DocumentId parentRowId;
		private WindowId windowId;
		private JSONViewDataType viewType;

		private String referencingTableName;
		private Set<DocumentPath> referencingDocumentPaths;

		private ViewActionDescriptorsList actions = ViewActionDescriptorsList.EMPTY;
		private List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors = null;

		private List<DocumentFilter> stickyFilters;
		private List<DocumentFilter> filters;

		private Builder(@NonNull final SqlViewBinding sqlViewBinding)
		{
			this.sqlViewBinding = sqlViewBinding;
		}

		public HUEditorView build()
		{
			return new HUEditorView(this);
		}

		private SqlViewBinding getSqlViewBinding()
		{
			return sqlViewBinding;
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

		public Builder setParentRowId(final DocumentId parentRowId)
		{
			this.parentRowId = parentRowId;
			return this;
		}
		
		private DocumentId getParentRowId()
		{
			return parentRowId;
		}

		public Builder setWindowId(final WindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		private WindowId getWindowId()
		{
			return windowId;
		}

		public Builder setViewType(final JSONViewDataType viewType)
		{
			this.viewType = viewType;
			return this;
		}

		private JSONViewDataType getViewType()
		{
			return viewType;
		}

		public Builder setReferencingDocumentPaths(final String referencingTableName, final Set<DocumentPath> referencingDocumentPaths)
		{
			this.referencingTableName = referencingTableName;
			this.referencingDocumentPaths = referencingDocumentPaths;
			return this;
		}

		private String getReferencingTableName()
		{
			return referencingTableName;
		}

		private Set<DocumentPath> getReferencingDocumentPaths()
		{
			return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
		}

		public Builder setActions(@NonNull final ViewActionDescriptorsList actions)
		{
			this.actions = actions;
			return this;
		}

		private ViewActionDescriptorsList getActions()
		{
			return actions;
		}

		public Builder setAdditionalRelatedProcessDescriptors(@NonNull final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors)
		{
			this.additionalRelatedProcessDescriptors = additionalRelatedProcessDescriptors;
			return this;
		}

		private ImmutableList<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
		{
			return additionalRelatedProcessDescriptors != null && !additionalRelatedProcessDescriptors.isEmpty() ? ImmutableList.copyOf(additionalRelatedProcessDescriptors) : ImmutableList.of();
		}

		public Builder setStickyFilters(final List<DocumentFilter> stickyFilters)
		{
			this.stickyFilters = stickyFilters;
			return this;
		}

		private List<DocumentFilter> getStickyFilters()
		{
			return stickyFilters != null ? stickyFilters : ImmutableList.of();
		}

		public Builder setFilters(final List<DocumentFilter> filters)
		{
			this.filters = filters;
			return this;
		}

		private List<DocumentFilter> getFilters()
		{
			return filters != null ? filters : ImmutableList.of();
		}

	}
}
