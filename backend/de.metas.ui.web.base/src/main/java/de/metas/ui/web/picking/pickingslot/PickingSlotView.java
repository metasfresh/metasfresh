package de.metas.ui.web.picking.pickingslot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.picking.packageable.PackageableView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewFilterParameterLookupEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowsOrderBy;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.List;
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

/**
 * Picking editor's view right-hand side view which lists {@link PickingSlotRow}s.
 * <p>
 * Note that technically this is contained in the left-hand side {@link PackageableView}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class PickingSlotView implements IView
{
	public static PickingSlotView cast(final IView pickingSlotView)
	{
		return (PickingSlotView)pickingSlotView;
	}

	private final ViewId viewId;
	private final ViewId parentViewId;
	private final DocumentId parentRowId;
	private final ITranslatableString description;
	private final ShipmentScheduleId currentShipmentScheduleId;
	private final PickingSlotRowsCollection rows;
	private final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;
	private final DocumentFilterList filters;

	@Builder
	private PickingSlotView(
			@NonNull final ViewId viewId,
			@Nullable final ViewId parentViewId,
			@Nullable final DocumentId parentRowId,
			@Nullable final ITranslatableString description,
			@NonNull final ShipmentScheduleId currentShipmentScheduleId,
			@NonNull final Supplier<List<PickingSlotRow>> rowsSupplier,
			@Nullable final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors,
			@Nullable final DocumentFilterList filters)
	{
		this.viewId = viewId;
		this.parentViewId = parentViewId;
		this.parentRowId = parentRowId;
		this.description = TranslatableStrings.nullToEmpty(description);
		this.currentShipmentScheduleId = currentShipmentScheduleId;
		this.rows = PickingSlotRowsCollection.ofSupplier(rowsSupplier);
		this.additionalRelatedProcessDescriptors = additionalRelatedProcessDescriptors != null ? ImmutableList.copyOf(additionalRelatedProcessDescriptors) : ImmutableList.of();
		this.filters = filters != null ? filters : DocumentFilterList.EMPTY;
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public ITranslatableString getDescription()
	{
		return description;
	}

	@Override
	public JSONViewDataType getViewType()
	{
		return JSONViewDataType.grid;
	}

	@Override
	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return ImmutableSet.of();
	}

	/**
	 * Always returns {@link I_M_PickingSlot#Table_Name}
	 */
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId ignored)
	{
		return I_M_PickingSlot.Table_Name;
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
	public ViewResult getPage(
			final int firstRow,
			final int pageLength,
			@NonNull final ViewRowsOrderBy orderBys)
	{
		final List<PickingSlotRow> pageRows = rows.getPage(firstRow, pageLength);
		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys.toDocumentQueryOrderByList(), pageRows);
	}

	@Override
	public PickingSlotRow getById(@NonNull final DocumentId id) throws EntityNotFoundException
	{
		return rows.getById(id);
	}

	@Override
	public LookupValuesPage getFilterParameterDropdown(final String filterId, final String filterParameterName, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesPage getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final ViewFilterParameterLookupEvaluationCtx ctx)
	{
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
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
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return additionalRelatedProcessDescriptors;
	}

	/**
	 * @return the {@code M_ShipmentSchedule_ID} of the packageable line that is currently selected within the {@link PackageableView}.
	 */
	@NonNull
	public ShipmentScheduleId getCurrentShipmentScheduleId()
	{
		return currentShipmentScheduleId;
	}

	@Override
	public void invalidateAll()
	{
		rows.invalidateAll();
	}
}
