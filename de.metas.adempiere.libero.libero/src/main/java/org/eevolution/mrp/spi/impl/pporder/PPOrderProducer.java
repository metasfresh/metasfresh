
package org.eevolution.mrp.spi.impl.pporder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Order;
import org.springframework.stereotype.Service;

import de.metas.document.IDocTypeDAO;
import de.metas.material.event.pporder.PPOrder;
import lombok.NonNull;

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
@Service
public class PPOrderProducer
{
	public I_PP_Order createPPOrder(
			@NonNull final PPOrder pojo,
			@NonNull final Date dateOrdered)
	{
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

		final I_PP_Product_Planning productPlanning = InterfaceWrapperHelper
				.create(Env.getCtx(), pojo.getProductPlanningId(), I_PP_Product_Planning.class, ITrx.TRXNAME_None);

		//
		// Create PP Order
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		ppOrder.setPP_Product_Planning(productPlanning);

		ppOrder.setMRP_Generated(true);
		ppOrder.setMRP_AllowCleanup(true);
		ppOrder.setLine(10);

		//
		// Planning dimension
		ppOrder.setAD_Org_ID(pojo.getOrgId());
		ppOrder.setS_Resource_ID(pojo.getPlantId());
		ppOrder.setM_Warehouse_ID(pojo.getWarehouseId());
		ppOrder.setPlanner_ID(productPlanning.getPlanner_ID());

		//
		// Document Type & Status
		final int docTypeId = getC_DocType_ID(pojo.getOrgId());

		ppOrder.setC_DocTypeTarget_ID(docTypeId);
		ppOrder.setC_DocType_ID(docTypeId);
		ppOrder.setDocStatus(X_PP_Order.DOCSTATUS_Drafted);
		ppOrder.setDocAction(X_PP_Order.DOCACTION_Complete);

		//
		// Product, UOM, ASI
		ppOrder.setM_Product_ID(pojo.getProductId());
		ppOrder.setC_UOM_ID(pojo.getUomId());
		ppOrder.setM_AttributeSetInstance_ID(0);

		//
		// BOM & Workflow
		ppOrder.setPP_Product_BOM(productPlanning.getPP_Product_BOM());
		ppOrder.setAD_Workflow(productPlanning.getAD_Workflow());

		//
		// Dates
		ppOrder.setDateOrdered(new Timestamp(dateOrdered.getTime()));

		final Timestamp dateFinishSchedule = new Timestamp(pojo.getDatePromised().getTime());
		ppOrder.setDatePromised(dateFinishSchedule);
		ppOrder.setDateFinishSchedule(dateFinishSchedule);
		ppOrder.setPreparationDate(dateFinishSchedule);

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
		ppOrder.setC_OrderLine_ID(pojo.getOrderLineId());
		if (pojo.getOrderLineId() > 0)
		{
			ppOrder.setC_BPartner_ID(ppOrder.getC_OrderLine().getC_BPartner_ID());
		}

		//
		// Save the manufacturing order
		// I_PP_Order_BOM and I_PP_Order_BOMLines are created via a model interceptor
		InterfaceWrapperHelper.save(ppOrder);

		return ppOrder;
	}

	private int getC_DocType_ID(final int orgId)
	{
		final Properties ctx = Env.getCtx();

		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		final I_AD_Org org = orgDAO.retrieveOrg(ctx, orgId);

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		return docTypeDAO.getDocTypeId(ctx, X_C_DocType.DOCBASETYPE_ManufacturingOrder, org.getAD_Client_ID(), orgId, ITrx.TRXNAME_None);
	}
}
