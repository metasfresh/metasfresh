package de.metas.ui.web.picking;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.i18n.ITranslatableString;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.picking.PickingSlotRow.PickingSlotRowId;
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
 * Note that technically this is contained the left-hand side {@link PackageableView}.
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
	private final int shipmentScheduleId;
	private final ExtendedMemorizingSupplier<Map<PickingSlotRowId, PickingSlotRow>> rowsSupplier;
	private final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;

	@Builder
	private PickingSlotView(@NonNull final ViewId viewId,
			final ViewId parentViewId,
			final DocumentId parentRowId,
			final ITranslatableString description,
			final int shipmentScheduleId,
			final Supplier<List<PickingSlotRow>> rows,
			final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors)
	{
		Preconditions.checkArgument(shipmentScheduleId > 0, "shipmentScheduleId > 0");

		this.viewId = viewId;
		this.parentViewId = parentViewId;
		this.parentRowId = parentRowId;
		this.description = description != null ? description : ITranslatableString.empty();
		this.shipmentScheduleId = shipmentScheduleId;
		this.rowsSupplier = ExtendedMemorizingSupplier.of(() -> Maps.uniqueIndex(rows.get(), PickingSlotRow::getPickingSlotRowId));
		this.additionalRelatedProcessDescriptors = additionalRelatedProcessDescriptors != null ? ImmutableList.copyOf(additionalRelatedProcessDescriptors) : ImmutableList.of();
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

	@Override
	public String getTableName()
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
		return rowsSupplier.get().size();
	}

	@Override
	public void close()
	{
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
		final List<PickingSlotRow> pageRows = rowsSupplier.get().values().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : 30)
				.collect(ImmutableList.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, pageRows);
	}

	@Override
	public PickingSlotRow getById(final DocumentId id) throws EntityNotFoundException
	{
		PickingSlotRowId rowId = PickingSlotRowId.fromDocumentId(id);

		final PickingSlotRowId pickingSlotRowId = rowId.toPickingSlotId();
		final PickingSlotRow pickingSlotRow = rowsSupplier.get().get(pickingSlotRowId);
		if (pickingSlotRow == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("pickingSlotRowId", pickingSlotRowId);
		}

		if (pickingSlotRowId.equals(rowId))
		{
			return pickingSlotRow;
		}
		else
		{
			return pickingSlotRow.findIncludedRowById(rowId)
					.orElseThrow(() -> new EntityNotFoundException("Row not found").setParameter("pickingSlotRow", pickingSlotRow).setParameter("rowId", rowId));
		}
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		// TODO Auto-generated method stub
		return ImmutableList.of();
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return ImmutableList.of();
	}

	@Override
	public String getSqlWhereClause(final DocumentIdsSelection rowIds)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return false;
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return rowsSupplier.get().values().stream();
		}
		else
		{
			return rowIds.stream().map(this::getById);
		}
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
	 * 
	 * @return never returns a value {@code <= 0} (see constructor code).
	 */
	public int getShipmentScheduleId()
	{
		return shipmentScheduleId;
	}

	/**
	 * Convenience method.
	 * 
	 * @return never returns {@code null} (see constructor code).
	 */
	public I_M_ShipmentSchedule getShipmentSchedule()
	{
		final I_M_ShipmentSchedule shipmentSchedule = create(Env.getCtx(), shipmentScheduleId, I_M_ShipmentSchedule.class, ITrx.TRXNAME_ThreadInherited);
		return shipmentSchedule;
	}

	@Override
	public void invalidateAll()
	{
		rowsSupplier.forget();
	}
}
