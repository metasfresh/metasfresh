
package org.eevolution.mrp.spi.impl.pporder;

import static de.metas.document.engine.IDocument.ACTION_Complete;
import static de.metas.document.engine.IDocument.STATUS_Completed;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Order;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Loggables;
import de.metas.util.Services;
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
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	public I_PP_Order createPPOrder(@NonNull final PPOrderCreateRequest request)
	{
		final I_PP_Product_Planning productPlanning = productPlanningsRepo.getById(request.getProductPlanningId());

		//
		// Create PP Order
		final I_PP_Order ppOrderRecord = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		PPOrderPojoConverter.setMaterialDispoGroupId(ppOrderRecord, request.getMaterialDispoGroupId());

		ppOrderRecord.setPP_Product_Planning_ID(productPlanning.getPP_Product_Planning_ID());

		ppOrderRecord.setMRP_Generated(true);
		ppOrderRecord.setMRP_AllowCleanup(true);
		ppOrderRecord.setLine(10);

		//
		// Planning dimension
		ppOrderRecord.setAD_Org_ID(request.getClientAndOrgId().getOrgId().getRepoId());
		ppOrderRecord.setS_Resource_ID(request.getPlantId().getRepoId());
		ppOrderRecord.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		ppOrderRecord.setPlanner_ID(productPlanning.getPlanner_ID());

		//
		// Document Type & Status
		final DocTypeId docTypeId = getDocTypeId(request.getClientAndOrgId());

		ppOrderRecord.setC_DocTypeTarget_ID(docTypeId.getRepoId());
		ppOrderRecord.setC_DocType_ID(docTypeId.getRepoId());
		ppOrderRecord.setDocStatus(X_PP_Order.DOCSTATUS_Drafted);
		ppOrderRecord.setDocAction(X_PP_Order.DOCACTION_Complete);

		//
		// Product, ASI, UOM
		ppOrderRecord.setM_Product_ID(request.getProductId().getRepoId());
		ppOrderRecord.setM_AttributeSetInstance_ID(request.getAttributeSetInstanceId().getRepoId());

		//
		// BOM & Workflow
		ppOrderRecord.setPP_Product_BOM_ID(productPlanning.getPP_Product_BOM_ID());
		ppOrderRecord.setAD_Workflow_ID(productPlanning.getAD_Workflow_ID());

		//
		// Dates
		ppOrderRecord.setDateOrdered(TimeUtil.asTimestamp(request.getDateOrdered()));

		final Timestamp dateFinishSchedule = TimeUtil.asTimestamp(request.getDatePromised());
		ppOrderRecord.setDatePromised(dateFinishSchedule);
		ppOrderRecord.setDateFinishSchedule(dateFinishSchedule);
		ppOrderRecord.setPreparationDate(dateFinishSchedule);
		ppOrderRecord.setDateStartSchedule(TimeUtil.asTimestamp(request.getDateStartSchedule()));

		// Qty/UOM
		ppOrderBL.setQtyRequired(ppOrderRecord, request.getQtyRequired());
		// QtyBatchSize : do not set it, let the MO to take it from workflow
		ppOrderRecord.setYield(BigDecimal.ZERO);

		ppOrderRecord.setScheduleType(X_PP_MRP.TYPEMRP_Demand);
		ppOrderRecord.setPriorityRule(X_PP_Order.PRIORITYRULE_Medium);

		//
		// Inherit values from MRP demand
		ppOrderRecord.setC_OrderLine_ID(OrderLineId.toRepoId(request.getSalesOrderLineId()));
		ppOrderRecord.setC_BPartner_ID(BPartnerId.toRepoId(getCustomerIdOrNull(request)));

		//
		// Save the manufacturing order
		// I_PP_Order_BOM and I_PP_Order_BOMLines are created via a model interceptor
		ppOrdersRepo.save(ppOrderRecord);

		Loggables.addLog(
				"Created ppOrder; PP_Order_ID={}; DocumentNo={}",
				ppOrderRecord.getPP_Order_ID(), ppOrderRecord.getDocumentNo());

		//
		// Complete if requested
		if (productPlanning.isDocComplete())
		{
			documentBL.processEx(ppOrderRecord, ACTION_Complete, STATUS_Completed);
			Loggables.addLog(
					"Completed ppOrder; PP_Order_ID={}; DocumentNo={}",
					ppOrderRecord.getPP_Order_ID(), ppOrderRecord.getDocumentNo());
		}

		//
		return ppOrderRecord;
	}

	private BPartnerId getCustomerIdOrNull(final PPOrderCreateRequest request)
	{
		if (request.getCustomerId() != null)
		{
			return request.getCustomerId();
		}
		else if (request.getSalesOrderLineId() != null)
		{
			final I_C_OrderLine salesOrderLine = ordersRepo.getOrderLineById(request.getSalesOrderLineId());
			return BPartnerId.ofRepoIdOrNull(salesOrderLine.getC_BPartner_ID());
		}
		else
		{
			return null;
		}
	}

	private DocTypeId getDocTypeId(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return docTypesRepo.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_ManufacturingOrder)
				.adClientId(clientAndOrgId.getClientId().getRepoId())
				.adOrgId(clientAndOrgId.getOrgId().getRepoId())
				.build());
	}
}
