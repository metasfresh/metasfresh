package de.metas.inoutcandidate.api.impl;

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


import org.adempiere.util.agg.key.AbstractHeaderAggregationKeyBuilder;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inoutcandidate.agg.key.impl.HUShipmentScheduleKeyValueHandler;

/**
 * Header aggregation key builder for picked HU structures (LU-TU-VHU) of scheduled shipments.<br>
 * Note that when creating shipment lines, the method {@code ShipmentScheduleWithHU.createAttributesAggregationKey()} creates its own key!

 * @see HUShipmentScheduleKeyValueHandler for which properties go into the key.
 */
public class HUShipmentScheduleHeaderAggregationKeyBuilder extends AbstractHeaderAggregationKeyBuilder<ShipmentScheduleWithHU>
{
	public static final String REGISTRATION_KEY = I_M_ShipmentSchedule_QtyPicked.Table_Name;

	public HUShipmentScheduleHeaderAggregationKeyBuilder()
	{
		super(REGISTRATION_KEY);
	}
}
