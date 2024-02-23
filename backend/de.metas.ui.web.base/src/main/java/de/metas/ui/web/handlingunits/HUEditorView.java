package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.report.HUReportAwareView;
import de.metas.ui.web.process.ProcessHandlerType;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowsOrderBy;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

public class HUEditorView implements IView, HUReportAwareView
{
	public static HUEditorViewBuilder builder()
	{
		return new HUEditorViewBuilder();
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
	private final boolean considerTableRelatedProcessDescriptors;
	private final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;

	private final HUEditorViewBuffer rowsBuffer;

	private final transient DocumentFilterDescriptorsProvider filterDescriptors;
	private final DocumentFilterList filters;

	private final ImmutableMap<String, Object> parameters;

	/* package */ HUEditorView(@NonNull final HUEditorViewBuilder builder)
	{
		parentViewId = builder.getParentViewId();
		parentRowId = builder.getParentRowId();
		viewType = builder.getViewType();
		viewId = builder.getViewId();
		filterDescriptors = builder.getFilterDescriptors();
		filters = builder.getFilters();
		referencingDocumentPaths = builder.getReferencingDocumentPaths();
		actions = builder.getActions();
		considerTableRelatedProcessDescriptors = builder.isConsiderTableRelatedProcessDescriptors();
		additionalRelatedProcessDescriptors = builder.getAdditionalRelatedProcessDescriptors();
		parameters = builder.getParameters();
		rowsBuffer = builder.createRowsBuffer(SqlDocumentFilterConverterContext.builder()
				.viewId(builder.getViewId())
				.parameters(parameters)
				.build());
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

	/**
	 * Always returns {@link I_M_HU#Table_Name}, even if the underlying {@link HUEditorRow}'s type is {@link HUEditorRowType#HUStorage}.<br>
	 * (because I don't understand it well enough)
	 */
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId)
	{
		return I_M_HU.Table_Name;
	}

	@Override
	public long size()
	{
		return rowsBuffer.size();
	}

	@Override
	public void afterDestroy()
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
	public ViewResult getPage(
			final int firstRow,
			final int pageLength,
			@NonNull final ViewRowsOrderBy orderBys)
	{
		final List<HUEditorRow> page = rowsBuffer
				.streamPage(firstRow, pageLength, HUEditorRowFilter.ALL, orderBys)
				.collect(GuavaCollectors.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys.toDocumentQueryOrderByList(), page);
	}

	@Override
	public boolean isConsiderTableRelatedProcessDescriptors(@NonNull final ProcessHandlerType processHandlerType, @NonNull final DocumentIdsSelection selectedRowIds)
	{
		return considerTableRelatedProcessDescriptors;
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
	public ImmutableMap<String, Object> getParameters()
	{
		return parameters;
	}

	public boolean getParameterAsBoolean(final String name, final boolean defaultValue)
	{
		final Boolean value = (Boolean)parameters.get(name);
		return value != null ? value : defaultValue;
	}

	public <T extends RepoIdAware> Optional<T> getParameterAsId(final String name)
	{
		@SuppressWarnings("unchecked") final T value = (T)parameters.get(name);
		return Optional.ofNullable(value);
	}

	@NonNull
	public <T> Optional<T> getParameter(@NonNull final String name, @NonNull final Class<T> type)
	{
		final Object value = parameters.get(name);
		return value != null ? Optional.of(type.cast(value)) : Optional.empty();
	}

	@Override
	public HUEditorRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		return rowsBuffer.getById(rowId);
	}

	@Nullable
	public HUEditorRow getParentRowByChildIdOrNull(final DocumentId childId) throws EntityNotFoundException
	{
		return rowsBuffer.getParentRowByChildIdOrNull(childId).orElse(null);
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		return filterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.orElseThrow(() -> new AdempiereException("No lookup source for filterId=" + filterId + ", parameterName=" + filterParameterName))
				.findEntities(ctx)
				.getValues();
	}

	@Override
	public LookupValuesPage getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		return filterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.orElseThrow(() -> new AdempiereException("No lookup source for filterId=" + filterId + ", parameterName=" + filterParameterName))
				.findEntities(ctx, query);
	}

	@Override
	public DocumentFilterList getStickyFilters()
	{
		return rowsBuffer.getStickyFilters();
	}

	@Override
	public DocumentFilterList getFilters()
	{
		return filters;
	}

	@Override
	public DocumentQueryOrderByList getDefaultOrderBys()
	{
		return DocumentQueryOrderByList.EMPTY;
	}

	@Override
	public SqlViewRowsWhereClause getSqlWhereClause(@NonNull final DocumentIdsSelection rowIds, final SqlOptions sqlOpts_NOTUSED)
	{
		return rowsBuffer.getSqlWhereClause(rowIds);
	}

	@Override
	public TableRecordReference getTableRecordReferenceOrNull(final @NonNull DocumentId rowId)
	{
		final HUEditorRowId huRowId = HUEditorRowId.ofDocumentId(rowId);
		if (huRowId.isHU())
		{
			return TableRecordReference.of(I_M_HU.Table_Name, huRowId.getHuId());
		}
		else
		{
			return null;
		}
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
		rowsBuffer.invalidateAll();
	}

	public void addHUIdAndInvalidate(@NonNull final HuId huId)
	{
		addHUIdsAndInvalidate(ImmutableSet.of(huId));
	}

	public void addHUIdsAndInvalidate(final Collection<HuId> huIdsToAdd)
	{
		if (addHUIds(huIdsToAdd))
		{
			invalidateAll();
		}
	}

	public void addHUId(@NonNull final HuId huIdToAdd)
	{
		rowsBuffer.addHUIds(ImmutableSet.of(huIdToAdd));
	}

	public boolean addHUIds(final Collection<HuId> huIdsToAdd)
	{
		return rowsBuffer.addHUIds(huIdsToAdd);
	}

	public void removeHUsAndInvalidate(final Collection<I_M_HU> husToRemove)
	{
		final Set<HuId> huIdsToRemove = extractHUIds(husToRemove);
		removeHUIdsAndInvalidate(huIdsToRemove);
	}

	public void removeHUIdsAndInvalidate(final Collection<HuId> huIdsToRemove)
	{
		if (removeHUIds(huIdsToRemove))
		{
			invalidateAll();
		}
	}

	public boolean removeHUIds(final Collection<HuId> huIdsToRemove)
	{
		return rowsBuffer.removeHUIds(huIdsToRemove);
	}

	private static Set<HuId> extractHUIds(final Collection<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return ImmutableSet.of();
		}

		return hus.stream()
				.filter(Objects::nonNull)
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(Collectors.toSet());
	}

	@Override
	public void notifyRecordsChanged(
			@NonNull final TableRecordReferenceSet recordRefs,
			final boolean watchedByFrontend)
	{
		// TODO: notifyRecordsChanged:
		// get M_HU_IDs from recordRefs,
		// find the top level records from this view which contain our HUs
		// invalidate those top levels only

		final Set<HuId> huIdsToCheck = recordRefs
				.streamIds(I_M_HU.Table_Name, HuId::ofRepoId)
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
		if (rowIds.isEmpty())
		{
			return Stream.empty();
		}
		return streamByIds(HUEditorRowFilter.onlyRowIds(rowIds));
	}

	public Stream<HUEditorRow> streamByIds(final HUEditorRowFilter filter)
	{
		return rowsBuffer.streamByIdsExcludingIncludedRows(filter);
	}

	/**
	 * @return top level rows and included rows recursive stream which are matching the given filter
	 */
	public Stream<HUEditorRow> streamAllRecursive(final HUEditorRowFilter filter)
	{
		return rowsBuffer.streamAllRecursive(filter);
	}

	/**
	 * @return true if there is any top level or included row which is matching given filter
	 */
	public boolean matchesAnyRowRecursive(final HUEditorRowFilter filter)
	{
		return rowsBuffer.matchesAnyRowRecursive(filter);
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		final Set<HuId> huIds = streamByIds(rowIds)
				.filter(HUEditorRow::isPureHU)
				.map(HUEditorRow::getHuId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_M_HU> hus = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.list(I_M_HU.class);

		return InterfaceWrapperHelper.createList(hus, modelClass);
	}
}
