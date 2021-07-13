package de.metas.inoutcandidate.agg.key.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.bpartner.BPartnerContactId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.agg.key.IAggregationKeyValueHandler;
import org.adempiere.util.lang.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * AggregationKey value handler for shipment schedules.
 *
 * @author al
 * @see ShipmentScheduleHeaderAggregationKeyBuilder
 */
public class ShipmentScheduleKeyValueHandler implements IAggregationKeyValueHandler<I_M_ShipmentSchedule>
{
	private static final String VERSION = "1";

	@Override
	public List<Object> getValues(@NonNull final I_M_ShipmentSchedule sched)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final List<Object> values = new ArrayList<>();

		values.add(VERSION);

		values.add(sched.getC_DocType_ID());
		values.add(shipmentScheduleEffectiveBL.getBPartnerId(sched).getRepoId());
		values.add(shipmentScheduleEffectiveBL.getC_BP_Location_ID(sched));
		if (!shipmentScheduleBL.isSchedAllowsConsolidate(sched))
		{
			values.add(sched.getC_Order_ID());
		}
		values.add(shipmentScheduleEffectiveBL.getWarehouseId(sched));
		final BPartnerContactId adUserID = shipmentScheduleEffectiveBL.getBPartnerContactId(sched);
		if (adUserID != null)
		{
			values.add(BPartnerContactId.toRepoId(adUserID));
		}
		values.add(sched.getAD_Org_ID());

		if (sched.getM_Shipper_ID() > 0)
		{
			values.add(sched.getM_Shipper_ID());
		}

		return values;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
