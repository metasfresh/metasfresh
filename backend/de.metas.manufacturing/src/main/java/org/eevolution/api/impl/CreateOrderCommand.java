
package org.eevolution.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Order;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

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
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
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
		final ProductPlanning productPlanning = productPlanningId != null
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
		ppOrderRecord.setWorkStation_ID(ResourceId.toRepoId(request.getWorkstationId()));
		ppOrderRecord.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		ppOrderRecord.setPlanner_ID(UserId.toRepoId(getPlannerIdOrNull(request, productPlanning)));

		//
		// Document Type & Status
		final DocTypeId docTypeId = getDocTypeId(request.getDocBaseType(), request.getClientAndOrgId());

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
		ppOrderRecord.setPP_Product_BOM_ID(getBOMId(productPlanning).getRepoId());
		ppOrderRecord.setAD_Workflow_ID(getRoutingId(productPlanning).getRepoId());

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
		// Dimensions / References
		ppOrderRecord.setC_OrderLine_ID(OrderLineId.toRepoId(request.getSalesOrderLineId()));
		ppOrderRecord.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(request.getShipmentScheduleId()));
		ppOrderRecord.setC_BPartner_ID(BPartnerId.toRepoId(getCustomerIdOrNull(request)));
		ppOrderRecord.setC_Project_ID(ProjectId.toRepoId(request.getProjectId()));

		ppOrderRecord.setIsPickingOrder(productPlanning != null && productPlanning.isPickingOrder());
		ppOrderRecord.setM_HU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(request.getPackingMaterialId()));

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
			documentBL.processEx(ppOrderRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
			Loggables.addLog(
					"Completed ppOrder; PP_Order_ID={}; DocumentNo={}",
					ppOrderRecord.getPP_Order_ID(), ppOrderRecord.getDocumentNo());
		}

		//
		return ppOrderRecord;
	}

	private boolean isCompleteDocument(
			@NonNull final PPOrderCreateRequest request,
			@Nullable final ProductPlanning productPlanning)
	{
		if (request.getCompleteDocument() != null)
		{
			return request.getCompleteDocument();
		}

		return productPlanning != null && productPlanning.isDocComplete();
	}

	@Nullable
	private static UserId getPlannerIdOrNull(
			@NonNull final PPOrderCreateRequest request,
			@Nullable final ProductPlanning productPlanning)
	{
		if (request.getPlannerId() != null)
		{
			return request.getPlannerId();
		}

		if (productPlanning != null)
		{
			final UserId plannerId = productPlanning.getPlannerId();
			if (plannerId != null)
			{
				return plannerId;
			}
		}

		// default:
		return null;
	}

	@NonNull
	private ProductBOMId getBOMId(@Nullable final ProductPlanning productPlanning)
	{
		if (request.getBomId() != null)
		{
			return request.getBomId();
		}

		final Optional<ProductBOMId> productBOMIdFromPlanning = Optional.ofNullable(productPlanning)
				.map(ProductPlanning::getBomVersionsId)
				.filter(Objects::nonNull)
				.flatMap(bomsRepo::getLatestBOMByVersion);

		if (productBOMIdFromPlanning.isPresent())
		{
			return productBOMIdFromPlanning.get();
		}

		final ProductId productId = request.getProductId();

		return bomsRepo.getDefaultBOMIdByProductId(productId)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @PP_Product_BOM_ID@")
						.appendParametersToMessage()
						.setParameter("request", request)
						.setParameter("productPlanning", productPlanning));
	}

	private PPRoutingId getRoutingId(@Nullable final ProductPlanning productPlanning)
	{
		if (request.getRoutingId() != null)
		{
			return request.getRoutingId();
		}

		if (productPlanning != null)
		{
			final PPRoutingId routingId = productPlanning.getWorkflowId();
			if (routingId != null)
			{
				return routingId;
			}
		}

		return PPRoutingId.NONE;
	}

	@Nullable
	private BPartnerId getCustomerIdOrNull(@NonNull final PPOrderCreateRequest request)
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

	private DocTypeId getDocTypeId(
			@NonNull final PPOrderDocBaseType docBaseType,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return docTypesRepo.getDocTypeId(DocTypeQuery.builder()
												 .docBaseType(docBaseType.getCode())
												 .adClientId(clientAndOrgId.getClientId().getRepoId())
												 .adOrgId(clientAndOrgId.getOrgId().getRepoId())
												 .build());
	}

	private void setQtyRequired(
			@NonNull final I_PP_Order order,
			@NonNull final Quantity qty)
	{
		final Quantity qtyRounded = qty.roundToUOMPrecision();
		order.setQtyEntered(qtyRounded.toBigDecimal());
		ppOrderBOMBL.setQuantities(order, PPOrderQuantities.ofQtyRequiredToProduce(qtyRounded));
	}

}
