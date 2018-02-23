package de.metas.ui.web.picking.pickingslot.process;

import java.util.List;

import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

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
 * contains common code of the two fine picking process classes that we have.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */abstract class WEBUI_Picking_With_M_Source_HU_Base extends PickingSlotViewBasedProcess
{
	protected final boolean checkSourceHuPrecondition()
	{
		final I_M_ShipmentSchedule shipmentSchedule = getView().getCurrentShipmentSchedule();

		final PickingHUsQuery query = PickingHUsQuery.builder()
				.onlyIfAttributesMatchWithShipmentSchedules(true)
				.shipmentSchedules(ImmutableList.of(shipmentSchedule))
				.onlyTopLevelHUs(true)
				.build();

		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		final List<I_M_HU> sourceHUs = huPickingSlotBL.retrieveAvailableSourceHUs(query);
		return !sourceHUs.isEmpty();
	}
}
