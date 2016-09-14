/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/
package org.eevolution.model;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

/**
 * Cost Collector Callout
 *
 * @author Victor Perez www.e-evolution.com     
 * @author Teo Sarca, www.arhipac.ro
 */
public class CalloutCostCollector extends CalloutEngine
{
	public String order (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer PP_Order_ID = (Integer)value;
		if (PP_Order_ID == null || PP_Order_ID <= 0)
			return "";
		I_PP_Cost_Collector cc = InterfaceWrapperHelper.create(mTab, I_PP_Cost_Collector.class);
		//
		MPPOrder pp_order =  new MPPOrder(ctx, PP_Order_ID, null);
		MPPCostCollector.setPP_Order(cc, pp_order);
		//
		return "";
	}

	public String node (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer PP_Order_Node_ID = (Integer)value;
		if (PP_Order_Node_ID == null || PP_Order_Node_ID <= 0)
			return "";
		I_PP_Cost_Collector cc = InterfaceWrapperHelper.create(mTab, I_PP_Cost_Collector.class);
		//
		MPPOrderNode node = getPP_Order_Node(ctx, PP_Order_Node_ID);
		cc.setS_Resource_ID(node.getS_Resource_ID());
		cc.setIsSubcontracting(node.isSubcontracting());
		cc.setMovementQty(node.getQtyToDeliver());
		//
		duration(ctx, WindowNo, mTab, mField, value);
		//
		return "";
	}
	
	public String duration (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_PP_Cost_Collector cc = InterfaceWrapperHelper.create(mTab, I_PP_Cost_Collector.class);
		if (cc.getPP_Order_Node_ID() <= 0)
			return "";
		
		RoutingService routingService = RoutingServiceFactory.get().getRoutingService(ctx);
		BigDecimal durationReal = routingService.estimateWorkingTime(cc);
		// If Activity Control Duration should be specified
		// FIXME: this message is really anoying. We need to find a proper solution - teo_sarca
//		if(durationReal.signum() == 0)
//		{
//			throw new FillMandatoryException(MPPOrderNode.COLUMNNAME_SetupTimeReal, MPPOrderNode.COLUMNNAME_DurationReal);
//		}
		//
		cc.setDurationReal(durationReal);
		//
		return "";
	}
	

	private MPPOrderNode m_node = null;
	private MPPOrderNode getPP_Order_Node(Properties ctx, int PP_Order_Node_ID)
	{
		if (m_node != null && m_node.get_ID() == PP_Order_Node_ID)
		{
			return m_node;
		}
		m_node = new MPPOrderNode(ctx, PP_Order_Node_ID, null);
		return m_node;
	}

}


