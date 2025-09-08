package de.metas.ui.web.pickingslotsClearing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowId;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowsCollection;
import de.metas.ui.web.pickingslotsClearing.PackingHUsViewsCollection.PackingHUsViewSupplier;
import de.metas.ui.web.pickingslotsClearing.process.HUExtractedFromPickingSlotEvent;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowOverrides;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFilterParameterLookupEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowsOrderBy;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
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

public class PickingSlotsClearingView implements IView, IViewRowOverrides
{
	private final ViewId viewId;
	private final ITranslatableString description;
	private final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;

	private final PickingSlotRowsCollection rows;

	private final PackingHUsViewsCollection packingHUsViewsCollection = new PackingHUsViewsCollection();

	private final DocumentFilterDescriptorsProvider filterDescriptors;
	private final DocumentFilterList filters;

	@Builder
	private PickingSlotsClearingView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final Supplier<List<PickingSlotRow>> rows,
			@Nullable final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors,
			@NonNull final DocumentFilterDescriptorsProvider filterDescriptors,
			@NonNull final DocumentFilterList filters)
	{
		this.viewId = viewId;
		this.description = TranslatableStrings.nullToEmpty(description);
		this.rows = PickingSlotRowsCollection.ofSupplier(rows);

		this.additionalRelatedProcessDescriptors = additionalRelatedProcessDescriptors != null ? ImmutableList.copyOf(additionalRelatedProcessDescriptors) : ImmutableList.of();

		this.filterDescriptors = filterDescriptors;
		this.filters = filters;
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public JSONViewDataType getViewType()
	{
		return JSONViewDataType.grid;
	}

	@Override
	public ITranslatableString getDescription()
	{
		return description;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return additionalRelatedProcessDescriptors;
	}

	@Override
	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return ImmutableSet.of();
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return I_M_PickingSlot.Table_Name;
	}

	@Override
	public ViewId getParentViewId()
	{
		return null;
	}

	@Override
	public DocumentId getParentRowId()
	{
		return null;
	}

	@Override
	public long size()
	{
		return rows.size();
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
	public void invalidateAll()
	{
		rows.invalidateAll();
		ViewChangesCollector.getCurrentOrAutoflush().collectFullyChanged(this);
	}

	@Override
	public ViewResult getPage(
			final int firstRow,
			final int pageLength,
			@NonNull final ViewRowsOrderBy orderBys)
	{
		final List<PickingSlotRow> pageRows = rows.getPage(firstRow, pageLength);

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys.toDocumentQueryOrderByList(), pageRows);
	}

	@Override
	public PickingSlotRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		return rows.getById(rowId);
	}

	public PickingSlotRow getById(@NonNull final PickingSlotRowId rowId) throws EntityNotFoundException
	{
		return getById(rowId.toDocumentId());
	}

	public PickingSlotRow getRootRowWhichIncludesRowId(final PickingSlotRowId rowId)
	{
		return rows.getRootRowWhichIncludes(rowId);
	}

	public PickingSlotRowId getRootRowIdWhichIncludesRowId(final PickingSlotRowId rowId)
	{
		return rows.getRootRowIdWhichIncludes(rowId);
	}

	@Override
	public LookupValuesPage getFilterParameterDropdown(final String filterId, final String filterParameterName, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		return filterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.orElseThrow(() -> new AdempiereException("No lookup found for filterId=" + filterId + ", filterParameterName=" + filterParameterName))
				.findEntities(ctx.toEvaluatee());
	}

	@Override
	public LookupValuesPage getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		return filterDescriptors.getByFilterId(filterId)
				.getParameterByName(filterParameterName)
				.getLookupDataSource()
				.orElseThrow(() -> new AdempiereException("No lookup found for filterId=" + filterId + ", filterParameterName=" + filterParameterName))
				.findEntities(ctx.toEvaluatee(), query);
	}

	@Override
	public DocumentFilterList getStickyFilters()
	{
		return DocumentFilterList.EMPTY;
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
	public SqlViewRowsWhereClause getSqlWhereClause(final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		return ImmutableList.of();
	}

	@Override
	public Stream<PickingSlotRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		return rows.streamByIds(rowIds);
	}

	@Override
	public void notifyRecordsChanged(
			@NonNull final TableRecordReferenceSet recordRefs,
			final boolean watchedByFrontend)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ViewId getIncludedViewId(final IViewRow row)
	{
		final PackingHUsViewKey key = extractPackingHUsViewKey(PickingSlotRow.cast(row));
		return key.getPackingHUsViewId();
	}

	private PackingHUsViewKey extractPackingHUsViewKey(final PickingSlotRow row)
	{
		final PickingSlotRow rootRow = getRootRowWhichIncludesRowId(row.getPickingSlotRowId());
		return PackingHUsViewKey.builder()
				.pickingSlotsClearingViewIdPart(getViewId().getViewIdPart())
				.bpartnerId(rootRow.getBPartnerId())
				.bpartnerLocationId(rootRow.getBPartnerLocationId())
				.build();
	}

	public Optional<HUEditorView> getPackingHUsViewIfExists(final ViewId packingHUsViewId)
	{
		final PackingHUsViewKey key = PackingHUsViewKey.ofPackingHUsViewId(packingHUsViewId);
		return packingHUsViewsCollection.getByKeyIfExists(key);
	}

	public Optional<HUEditorView> getPackingHUsViewIfExists(final PickingSlotRowId rowId)
	{
		final PickingSlotRow rootRow = getRootRowWhichIncludesRowId(rowId);
		final PackingHUsViewKey key = extractPackingHUsViewKey(rootRow);
		return packingHUsViewsCollection.getByKeyIfExists(key);
	}

	public HUEditorView computePackingHUsViewIfAbsent(@NonNull final ViewId packingHUsViewId, @NonNull final PackingHUsViewSupplier packingHUsViewFactory)
	{
		return packingHUsViewsCollection.computeIfAbsent(PackingHUsViewKey.ofPackingHUsViewId(packingHUsViewId), packingHUsViewFactory);
	}

	public void setPackingHUsView(@NonNull final HUEditorView packingHUsView)
	{
		final ViewId packingHUsViewId = packingHUsView.getViewId();
		packingHUsViewsCollection.put(PackingHUsViewKey.ofPackingHUsViewId(packingHUsViewId), packingHUsView);
	}

	void closePackingHUsView(final ViewId packingHUsViewId, final ViewCloseAction closeAction)
	{
		final PackingHUsViewKey key = PackingHUsViewKey.ofPackingHUsViewId(packingHUsViewId);
		packingHUsViewsCollection.removeIfExists(key)
				.ifPresent(packingHUsView -> packingHUsView.close(closeAction));
	}

	public void handleEvent(final HUExtractedFromPickingSlotEvent event)
	{
		packingHUsViewsCollection.handleEvent(event);
	}
}
