package de.metas.ui.web.picking.pickingslot;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.i18n.ITranslatableString;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.picking.packageable.PackageableView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Builder;
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

/**
 * Picking editor's view right-hand side view which lists {@link PickingSlotRow}s.
 * <p>
 * Note that technically this is contained in the left-hand side {@link PackageableView}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
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
	private final int currentShipmentScheduleId;
	private final PickingSlotRowsCollection rows;
	private final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;
	private final List<DocumentFilter> filters;

	@Builder
	private PickingSlotView(
			@NonNull final ViewId viewId,
			@Nullable final ViewId parentViewId,
			@Nullable final DocumentId parentRowId,
			@Nullable final ITranslatableString description,
			@Nullable final int currentShipmentScheduleId,
			@NonNull final Supplier<List<PickingSlotRow>> rowsSupplier,
			@Nullable final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors,
			@Nullable final List<DocumentFilter> filters)
	{
		Check.assume(currentShipmentScheduleId > 0, "shipmentScheduleId > 0");

		this.viewId = viewId;
		this.parentViewId = parentViewId;
		this.parentRowId = parentRowId;
		this.description = ITranslatableString.nullToEmpty(description);
		this.currentShipmentScheduleId = currentShipmentScheduleId;
		this.rows = PickingSlotRowsCollection.ofSupplier(rowsSupplier);
		this.additionalRelatedProcessDescriptors = additionalRelatedProcessDescriptors != null ? ImmutableList.copyOf(additionalRelatedProcessDescriptors) : ImmutableList.of();
		this.filters = filters != null ? ImmutableList.copyOf(filters) : ImmutableList.of();
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
	public ViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final List<PickingSlotRow> pageRows = rows.getPage(firstRow, pageLength);
		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, pageRows);
	}

	@Override
	public PickingSlotRow getById(@NonNull final DocumentId id) throws EntityNotFoundException
	{
		return rows.getById(id);
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return ImmutableList.of();
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
	public String getSqlWhereClause(final DocumentIdsSelection rowIds, final SqlOptions sqlOpts)
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
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return additionalRelatedProcessDescriptors;
	}

	/**
	 * Returns the {@code M_ShipmentSchedule_ID} of the packageable line that is currently selected within the {@link PackageableView}.
	 *
	 * @return never returns a value {@code <= 0} (see constructor code).
	 */
	public int getCurrentShipmentScheduleId()
	{
		return currentShipmentScheduleId;
	}

	/**
	 * Convenience method. See {@link #getCurrentShipmentScheduleId()}.
	 *
	 * @return never returns {@code null} (see constructor code).
	 */
	public I_M_ShipmentSchedule getCurrentShipmentSchedule()
	{
		final I_M_ShipmentSchedule shipmentSchedule = load(getCurrentShipmentScheduleId(), I_M_ShipmentSchedule.class);
		return shipmentSchedule;
	}

	@Override
	public void invalidateAll()
	{
		rows.invalidateAll();
	}
}
