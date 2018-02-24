package de.metas.ui.web.picking.packageable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ui.web.document.filter.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.picking.pickingslot.PickingSlotView;
import de.metas.ui.web.view.AbstractCustomView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewCloseReason;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
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
 * Picking editor's view left-hand side view which lists one or more {@link PackageableRow} records to be picked.
 * <p>
 * Note that technically this view also contains the right-hand side {@link PickingSlotView}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PackageableView extends AbstractCustomView<PackageableRow>
{
	private final PickingCandidateService pickingCandidateService;

	public static PackageableView cast(final IView view)
	{
		return (PackageableView)view;
	}

	private final ConcurrentHashMap<DocumentId, PickingSlotView> pickingSlotsViewByRowId = new ConcurrentHashMap<>();

	@Builder
	private PackageableView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final PackageableRowsData rowsData,
			@NonNull final PickingCandidateService pickingCandidateService)
	{
		super(viewId, description, rowsData, NullDocumentFilterDescriptorsProvider.instance);

		this.pickingCandidateService = pickingCandidateService;
	}

	@Override
	public IRowsData<PackageableRow> getRowsData()
	{
		return PackageableRowsData.cast(super.getRowsData());
	}

	@Override
	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return ImmutableSet.of();
	}

	/**
	 * Always returns {@link I_M_Packageable_V#Table_Name}.
	 */
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId ignored)
	{
		return I_M_Packageable_V.Table_Name;
	}

	@Override
	public void close(final ViewCloseReason reason)
	{
		if (reason == ViewCloseReason.USER_REQUEST)
		{
			closePickingCandidatesFromRackSystemPickingSlots();
		}
	}

	private void closePickingCandidatesFromRackSystemPickingSlots()
	{
		final List<Integer> shipmentScheduleIds = getRows()
				.values().stream()
				.map(PackageableRow::getShipmentScheduleId)
				.collect(Collectors.toList());

		// Close all picking candidates which are on a rack system picking slot (gh2740)
		pickingCandidateService.prepareCloseForShipmentSchedules(shipmentScheduleIds)
				.pickingSlotIsRackSystem(true)
				.failOnError(false) // close as much candidates as it's possible
				.build()
				.perform();
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		final Set<Integer> shipmentScheduleIds = rowIds.toIntSet();
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_M_Packageable_V> packables = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.addInArrayFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.list(I_M_Packageable_V.class);
		return InterfaceWrapperHelper.createList(packables, modelClass);
	}

	public void setPickingSlotView(@NonNull final DocumentId rowId, @NonNull final PickingSlotView pickingSlotView)
	{
		pickingSlotsViewByRowId.put(rowId, pickingSlotView);
	}

	public void removePickingSlotView(@NonNull final DocumentId rowId, @NonNull final ViewCloseReason viewCloseReason)
	{
		final PickingSlotView view = pickingSlotsViewByRowId.remove(rowId);
		if (view != null)
		{
			view.close(viewCloseReason);
		}
	}

	public PickingSlotView getPickingSlotViewOrNull(@NonNull final DocumentId rowId)
	{
		return pickingSlotsViewByRowId.get(rowId);
	}

	public PickingSlotView computePickingSlotViewIfAbsent(@NonNull final DocumentId rowId, @NonNull final Supplier<PickingSlotView> pickingSlotViewFactory)
	{
		return pickingSlotsViewByRowId.computeIfAbsent(rowId, id -> pickingSlotViewFactory.get());
	}

	@Override
	protected Stream<DocumentId> extractDocumentIdsToInvalidate(final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();
		if (I_M_ShipmentSchedule.Table_Name.equals(tableName))
		{
			final int shipmentScheduleId = recordRef.getRecord_ID();
			final TableRecordReference recordRefEffective = PackageableRow.createTableRecordReferenceFromShipmentScheduleId(shipmentScheduleId);
			return super.extractDocumentIdsToInvalidate(recordRefEffective);
		}
		else
		{
			return Stream.empty();
		}
	}
}
