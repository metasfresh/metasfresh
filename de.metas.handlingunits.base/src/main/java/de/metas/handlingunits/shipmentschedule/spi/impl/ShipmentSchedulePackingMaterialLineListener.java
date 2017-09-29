package de.metas.handlingunits.shipmentschedule.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer;
import lombok.NonNull;

/**
 * Makes sure that there are no shipment schedules for picking material lines.
 * <p>
 * Background: we need them in the order document, but not in the shipment schedule. See task 07042.
 *
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29
 */
public class ShipmentSchedulePackingMaterialLineListener implements ModelWithoutShipmentScheduleVetoer
{

	/**
	 * @see #ShipmentSchedulePackingMaterialLineListener()
	 */
	@Override
	public OnMissingCandidate foundModelWithoutInOutCandidate(@NonNull final Object model)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		if (ol.isPackagingMaterial())
		{
			return OnMissingCandidate.I_VETO; // don't create the shipment schedule
		}
		return OnMissingCandidate.I_DONT_CARE; // let the others decide
	}

}
