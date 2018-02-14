package org.eevolution.callout;

import java.math.BigDecimal;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderNodeBL;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Node;

import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;

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

		Services.get(IPPCostCollectorBL.class).setPP_Order(cc, ppOrder);
	}

	@CalloutMethod(columnNames = I_PP_Cost_Collector.COLUMNNAME_PP_Order_Node_ID)
	public void onPP_Order_Node_ID(final I_PP_Cost_Collector cc)
	{
		final I_PP_Order_Node node = cc.getPP_Order_Node();
		if (node == null)
		{
			return;
		}
		//
		cc.setS_Resource_ID(node.getS_Resource_ID());
		cc.setIsSubcontracting(node.isSubcontracting());

		final BigDecimal qtyToDeliver = Services.get(IPPOrderNodeBL.class).getQtyToDeliver(node);
		cc.setMovementQty(qtyToDeliver);
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
		if (cc.getPP_Order_Node_ID() <= 0)
		{
			return;
		}

		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(Env.getCtx());
		final BigDecimal durationReal = routingService.estimateWorkingTime(cc);
		// If Activity Control Duration should be specified
		// FIXME: this message is really anoying. We need to find a proper solution - teo_sarca
		// if(durationReal.signum() == 0)
		// {
		// throw new FillMandatoryException(MPPOrderNode.COLUMNNAME_SetupTimeReal, MPPOrderNode.COLUMNNAME_DurationReal);
		// }
		//
		cc.setDurationReal(durationReal);
	}
}
