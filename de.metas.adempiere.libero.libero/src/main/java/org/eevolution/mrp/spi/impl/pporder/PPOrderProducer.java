
package org.eevolution.mrp.spi.impl.pporder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Order;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;

import de.metas.material.planning.pporder.PPOrder;

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

public class PPOrderProducer
{
	public I_PP_Order createPPOrder(final PPOrder pojo,
			final IMRPCreateSupplyRequest request,
			final int docTypeMO_ID)
	{
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		
		//
		// Create PP Order
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, request.getMRPContext());
		ppOrder.setMRP_Generated(true);
		ppOrder.setMRP_AllowCleanup(true);
		ppOrder.setLine(10);

		//
		// Planning dimension
		ppOrder.setAD_Org_ID(pojo.getOrgId());
		ppOrder.setS_Resource_ID(pojo.getPlantId());
		ppOrder.setM_Warehouse_ID(pojo.getWarehouseId());
		ppOrder.setPlanner_ID(pojo.getPlannerId());

		//
		// Document Type & Status

		ppOrder.setC_DocTypeTarget_ID(docTypeMO_ID);
		ppOrder.setC_DocType_ID(docTypeMO_ID);
		ppOrder.setDocStatus(X_PP_Order.DOCSTATUS_Drafted);
		ppOrder.setDocAction(X_PP_Order.DOCACTION_Complete);

		//
		// Product, UOM, ASI
		ppOrder.setM_Product_ID(pojo.getProductId());
		ppOrder.setC_UOM_ID(pojo.getUomId());
		ppOrder.setM_AttributeSetInstance_ID(0);

		//
		// BOM & Workflow
		ppOrder.setPP_Product_BOM_ID(pojo.getProductBomId());
		ppOrder.setAD_Workflow_ID(pojo.getWorkflowId());

		//
		// Dates
		ppOrder.setDateOrdered(new Timestamp(pojo.getDateOrdered().getTime()));

		final Timestamp dateFinishSchedule = new Timestamp(pojo.getDatePromised().getTime());
		ppOrder.setDatePromised(dateFinishSchedule);
		ppOrder.setDateFinishSchedule(dateFinishSchedule);

		final Timestamp dateStartSchedule = new Timestamp(pojo.getDateStartSchedule().getTime());
		ppOrder.setDateStartSchedule(dateStartSchedule);

		//
		// Qtys
		ppOrderBL.setQty(ppOrder, pojo.getQuantity());
		// QtyBatchSize : do not set it, let the MO to take it from workflow
		ppOrder.setYield(BigDecimal.ZERO);

		ppOrder.setScheduleType(X_PP_MRP.TYPEMRP_Demand);
		ppOrder.setPriorityRule(X_PP_Order.PRIORITYRULE_Medium);

		//
		// Inherit values from MRP demand
		ppOrder.setC_OrderLine_ID(request.getMRPDemandOrderLineSOId());
		ppOrder.setC_BPartner_ID(request.getMRPDemandBPartnerId());

		//
		// Save the manufacturing order
		// I_PP_Order_BOM and I_PP_Order_BOMLines are created via a model interceptor
		InterfaceWrapperHelper.save(ppOrder);
		
		return ppOrder;
	}
	
	public void createPPOrderLines()
	{
		
	}
	
}
