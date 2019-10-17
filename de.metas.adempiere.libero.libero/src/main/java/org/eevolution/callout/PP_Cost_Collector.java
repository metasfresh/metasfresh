package org.eevolution.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import de.metas.material.planning.WorkingTime;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * Manufacturing cost collector callout
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version developed by Victor Perez, Teo Sarca under ADempiere project
 */
@Callout(I_PP_Cost_Collector.class)
public class PP_Cost_Collector
{
	@CalloutMethod(columnNames = I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID)
	public void onPP_Order_ID(final I_PP_Cost_Collector cc)
	{
		final I_PP_Order ppOrder = cc.getPP_Order();
		if (ppOrder == null)
		{
			return;
		}

		Services.get(IPPCostCollectorBL.class).updateCostCollectorFromOrder(cc, ppOrder);
	}

	@CalloutMethod(columnNames = I_PP_Cost_Collector.COLUMNNAME_PP_Order_Node_ID)
	public void onPP_Order_Node_ID(final I_PP_Cost_Collector cc)
	{
		final PPOrderId orderId = PPOrderId.ofRepoIdOrNull(cc.getPP_Order_ID());
		if (orderId == null)
		{
			return;
		}
		final PPOrderRoutingActivityId orderRoutingActivityId = PPOrderRoutingActivityId.ofRepoIdOrNull(orderId, cc.getPP_Order_Node_ID());
		if (orderRoutingActivityId == null)
		{
			return;
		}

		final PPOrderRoutingActivity orderActivity = Services.get(IPPOrderRoutingRepository.class).getOrderRoutingActivity(orderRoutingActivityId);

		cc.setS_Resource_ID(orderActivity.getResourceId().getRepoId());
		cc.setIsSubcontracting(orderActivity.isSubcontracting());

		final Quantity qtyToDeliver = orderActivity.getQtyToDeliver();
		cc.setMovementQty(qtyToDeliver.toBigDecimal());
		// updateDurationReal(cc); // shall be automatically triggered
	}

	@CalloutMethod(columnNames = I_PP_Cost_Collector.COLUMNNAME_MovementQty)
	public void onMovementQty(final I_PP_Cost_Collector cc)
	{
		updateDurationReal(cc);
	}

	/** Calculates and sets DurationReal based on selected PP_Order_Node */
	private void updateDurationReal(final I_PP_Cost_Collector cc)
	{
		final PPOrderId orderId = PPOrderId.ofRepoIdOrNull(cc.getPP_Order_ID());
		if (orderId == null)
		{
			return;
		}

		final PPOrderRoutingActivityId activityId = PPOrderRoutingActivityId.ofRepoId(orderId, cc.getPP_Order_Node_ID());
		if (activityId == null)
		{
			return;
		}

		final PPOrderRoutingActivity activity = Services.get(IPPOrderRoutingRepository.class).getOrderRoutingActivity(activityId);

		final WorkingTime durationReal = WorkingTime.builder()
				.durationPerOneUnit(activity.getDurationPerOneUnit())
				.unitsPerCycle(activity.getUnitsPerCycle())
				.qty(cc.getMovementQty())
				.activityTimeUnit(activity.getDurationUnit())
				.build();

		cc.setDurationReal(durationReal.toBigDecimalUsingActivityTimeUnit());
	}
}
