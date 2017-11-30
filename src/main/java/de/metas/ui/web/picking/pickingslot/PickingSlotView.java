package de.metas.ui.web.picking.pickingslot;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.i18n.ITranslatableString;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.picking.packageable.PackageableView;
import de.metas.ui.web.view.AbstractCustomView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
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
public class PickingSlotView extends AbstractCustomView<PickingSlotRow>
{
	public static PickingSlotView cast(final IView pickingSlotView)
	{
		return (PickingSlotView)pickingSlotView;
	}

	private final ViewId parentViewId;
	private final DocumentId parentRowId;
	private final int currentShipmentScheduleId;
	private final PickingSlotRowsCollection rows;
	private final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;

	@Builder
	private PickingSlotView(
			@NonNull final ViewId viewId,
			@Nullable final ViewId parentViewId,
			@Nullable final DocumentId parentRowId,
			@Nullable final ITranslatableString description,
			@Nullable final int currentShipmentScheduleId,
			@NonNull final Supplier<List<PickingSlotRow>> rowsSupplier,
			@Nullable final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors)
	{
		super(viewId,
				ITranslatableString.nullToEmpty(description),
				rowsSupplier);

		Preconditions.checkArgument(currentShipmentScheduleId > 0, "shipmentScheduleId > 0");

		this.parentViewId = parentViewId;
		this.parentRowId = parentRowId;

		this.currentShipmentScheduleId = currentShipmentScheduleId;
		this.rows = PickingSlotRowsCollection.ofSupplier(rowsSupplier);
		this.additionalRelatedProcessDescriptors = additionalRelatedProcessDescriptors != null ? ImmutableList.copyOf(additionalRelatedProcessDescriptors) : ImmutableList.of();
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
