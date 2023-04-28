package de.metas.ui.web.picking.pickingslot.process;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.process.IProcessPrecondition;
import de.metas.ui.web.picking.packageable.PackageableView;
import de.metas.ui.web.picking.packageable.filters.ProductBarcodeFilterData;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import org.compiere.model.I_C_UOM;

import java.util.Optional;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

abstract class PickingSlotViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);

	private I_M_ShipmentSchedule _shipmentSchedule; // lazy

	@Override
	protected final PickingSlotView getView()
	{
		return PickingSlotView.cast(super.getView());
	}

	protected PickingSlotView getPickingSlotView()
	{
		return getView();
	}

	@Override
	protected final PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

	protected final PickingSlotRow getSingleSelectedPickingSlotRow()
	{
		return getSingleSelectedRow();
	}

	protected final void invalidatePickingSlotsView()
	{
		invalidateView();
	}

	protected final ShipmentScheduleId getCurrentShipmentScheduleId()
	{
		return getPickingSlotView().getCurrentShipmentScheduleId();
	}

	protected final I_M_ShipmentSchedule getCurrentShipmentSchedule()
	{
		I_M_ShipmentSchedule shipmentSchedule = _shipmentSchedule;
		if (shipmentSchedule == null)
		{
			final ShipmentScheduleId shipmentScheduleId = getCurrentShipmentScheduleId();
			_shipmentSchedule = shipmentSchedule = shipmentSchedulesRepo.getById(shipmentScheduleId, I_M_ShipmentSchedule.class);
		}
		return shipmentSchedule;
	}

	protected final I_C_UOM getCurrentShipmentScheuduleUOM()
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();
		return shipmentScheduleBL.getUomOfProduct(shipmentSchedule);
	}

	protected PackageableView getPackageableView()
	{
		final ViewId packageableViewId = getPickingSlotView().getParentViewId();
		return PackageableView.cast(getViewsRepo().getView(packageableViewId));
	}

	protected final void invalidatePackablesView()
	{
		invalidateParentView();
	}

	protected Optional<ProductBarcodeFilterData> getBarcodeFilterData()
	{
		return Optional.ofNullable(getPackageableView().getBarcodeFilterData());
	}
}
