package de.metas.ui.web.picking.pickingslot.process;

import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.process.IProcessPrecondition;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Services;

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
	private I_M_ShipmentSchedule _shipmentSchedule; // lazy

	@Override
	protected final PickingSlotView getView()
	{
		return PickingSlotView.cast(super.getView());
	}

	@Override
	protected final PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

	protected final void invalidatePickingSlotsView()
	{
		invalidateView();
	}

	protected final void invalidatePackablesView()
	{
		invalidateParentView();
	}

	protected final ShipmentScheduleId getCurrentShipmentScheduleId()
	{
		return getView().getCurrentShipmentScheduleId();
	}

	protected final I_M_ShipmentSchedule getCurrentShipmentSchedule()
	{
		I_M_ShipmentSchedule shipmentSchedule = _shipmentSchedule;
		if (shipmentSchedule == null)
		{
			final ShipmentScheduleId shipmentScheduleId = getCurrentShipmentScheduleId();
			final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
			_shipmentSchedule = shipmentSchedule = shipmentSchedulesRepo.getById(shipmentScheduleId, I_M_ShipmentSchedule.class);
		}
		return shipmentSchedule;
	}

	protected final I_C_UOM getCurrentShipmentScheuduleUOM()
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();
		final I_C_UOM uom = Services.get(IShipmentScheduleBL.class).getUomOfProduct(shipmentSchedule);
		return uom;
	}

}
