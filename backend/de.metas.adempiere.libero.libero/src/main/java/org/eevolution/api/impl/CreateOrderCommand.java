
package org.eevolution.api.impl;

import static de.metas.document.engine.IDocument.ACTION_Complete;
import static de.metas.document.engine.IDocument.STATUS_Completed;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Order;

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
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

/**
 * Creates manufacturing order from {@link PPOrderCreateRequest}.
 */
final class CreateOrderCommand
{
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IUOMConversionBL uomConversionService = Services.get(IUOMConversionBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private final PPOrderCreateRequest request;

	@Builder
	private CreateOrderCommand(@NonNull final PPOrderCreateRequest request)
	{
		this.request = request;
	}

	public I_PP_Order execute()
	{
		final ProductPlanningId productPlanningId = request.getProductPlanningId();
		final I_PP_Product_Planning productPlanning = productPlanningId != null
				? productPlanningsRepo.getById(productPlanningId)
				: null;

		//
		// Create PP Order
		final I_PP_Order ppOrderRecord = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		PPOrderPojoConverter.setMaterialDispoGroupId(ppOrderRecord, request.getMaterialDispoGroupId());

		ppOrderRecord.setPP_Product_Planning_ID(ProductPlanningId.toRepoId(productPlanningId));

		ppOrderRecord.setMRP_Generated(true);
		ppOrderRecord.setMRP_AllowCleanup(true);
		ppOrderRecord.setLine(10);

		//
		// Planning dimension
		ppOrderRecord.setAD_Org_ID(request.getClientAndOrgId().getOrgId().getRepoId());
		ppOrderRecord.setS_Resource_ID(request.getPlantId().getRepoId());
		ppOrderRecord.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		ppOrderRecord.setPlanner_ID(UserId.toRepoId(getPlannerIdOrNull(request, productPlanning)));

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
		ppOrderRecord.setPP_Product_BOM_ID(getBOMId(request, productPlanning).getRepoId());
		ppOrderRecord.setAD_Workflow_ID(getRoutingId(request, productPlanning).getRepoId());

		//
		// Dates
		ppOrderRecord.setDateOrdered(TimeUtil.asTimestamp(request.getDateOrdered()));

		final Timestamp dateFinishSchedule = TimeUtil.asTimestamp(request.getDatePromised());
		ppOrderRecord.setDatePromised(dateFinishSchedule);
		ppOrderRecord.setDateFinishSchedule(dateFinishSchedule);
		ppOrderRecord.setPreparationDate(dateFinishSchedule);
		ppOrderRecord.setDateStartSchedule(TimeUtil.asTimestamp(request.getDateStartSchedule()));

		// Qty/UOM
		setQtyRequired(ppOrderRecord, request.getQtyRequired());
		// QtyBatchSize : do not set it, let the MO to take it from workflow
		ppOrderRecord.setYield(BigDecimal.ZERO);

		ppOrderRecord.setScheduleType(X_PP_MRP.TYPEMRP_Demand);
		ppOrderRecord.setPriorityRule(X_PP_Order.PRIORITYRULE_Medium);

		//
		// Inherit values from MRP demand
		ppOrderRecord.setC_OrderLine_ID(OrderLineId.toRepoId(request.getSalesOrderLineId()));
		ppOrderRecord.setC_BPartner_ID(BPartnerId.toRepoId(getCustomerIdOrNull(request)));
		
		ppOrderRecord.setIsPickingOrder(productPlanning.isPickingOrder());

		//
		// Save the manufacturing order
		// I_PP_Order_BOM and I_PP_Order_BOMLines are created via a model interceptor
		ppOrdersRepo.save(ppOrderRecord);

		Loggables.addLog(
				"Created ppOrder; PP_Order_ID={}; DocumentNo={}",
				ppOrderRecord.getPP_Order_ID(), ppOrderRecord.getDocumentNo());

		//
		// Complete if requested
		if (isCompleteDocument(request, productPlanning))
		{
			documentBL.processEx(ppOrderRecord, ACTION_Complete, STATUS_Completed);
			Loggables.addLog(
					"Completed ppOrder; PP_Order_ID={}; DocumentNo={}",
					ppOrderRecord.getPP_Order_ID(), ppOrderRecord.getDocumentNo());
		}

		//
		return ppOrderRecord;
	}

	private boolean isCompleteDocument(
			@NonNull final PPOrderCreateRequest request,
			@Nullable final I_PP_Product_Planning productPlanning)
	{
		if (request.getCompleteDocument() != null)
		{
			return request.getCompleteDocument();
		}

		return productPlanning != null && productPlanning.isDocComplete();
	}

	private static UserId getPlannerIdOrNull(
			@NonNull final PPOrderCreateRequest request,
			@Nullable final I_PP_Product_Planning productPlanning)
	{
		if (request.getPlannerId() != null)
		{
			return request.getPlannerId();
		}

		if (productPlanning != null)
		{
			final UserId plannerId = UserId.ofRepoIdOrNull(productPlanning.getPlanner_ID());
			if (plannerId != null)
			{
				return plannerId;
			}
		}

		// default:
		return null;
	}

	private ProductBOMId getBOMId(
			@NonNull final PPOrderCreateRequest request,
			@Nullable final I_PP_Product_Planning productPlanning)
	{
		if (request.getBomId() != null)
		{
			return request.getBomId();
		}

		if (productPlanning != null)
		{
			final ProductBOMId bomId = ProductBOMId.ofRepoIdOrNull(productPlanning.getPP_Product_BOM_ID());
			if (bomId != null)
			{
				return bomId;
			}
		}

		final ProductId productId = request.getProductId();
		final ProductBOMId defaultBOMId = bomsRepo.getDefaultBOMIdByProductId(productId).orElse(null);
		if (defaultBOMId != null)
		{
			return defaultBOMId;
		}

		throw new AdempiereException("@NotFound@ @PP_Product_BOM_ID@")
				.appendParametersToMessage()
				.setParameter("request", request)
				.setParameter("productPlanning", productPlanning);
	}

	private static PPRoutingId getRoutingId(
			@NonNull final PPOrderCreateRequest request,
			@Nullable final I_PP_Product_Planning productPlanning)
	{
		if (productPlanning != null)
		{
			final PPRoutingId routingId = PPRoutingId.ofRepoIdOrNull(productPlanning.getAD_Workflow_ID());
			if (routingId != null)
			{
				return routingId;
			}
		}

		return PPRoutingId.NONE;
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

	private void setQtyRequired(@NonNull final I_PP_Order order, @NonNull final Quantity qty)
	{
		final Quantity qtyRounded = qty.roundToUOMPrecision();
		order.setQtyEntered(qtyRounded.toBigDecimal());
		order.setC_UOM_ID(qtyRounded.getUomId().getRepoId());

		final ProductId productId = ProductId.ofRepoId(order.getM_Product_ID());
		final Quantity qtyInSockingUOM = uomConversionService.convertToProductUOM(qtyRounded, productId);
		order.setQtyOrdered(qtyInSockingUOM.toBigDecimal());
	}

}
