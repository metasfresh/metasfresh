package de.metas.material.planning.pporder;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.compiere.util.TimeUtil.asInstant;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-planning
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
public class PPOrderPojoConverter
{
	private final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final ModelProductDescriptorExtractor productDescriptorFactory;

	private static final ModelDynAttributeAccessor<I_PP_Order, MaterialDispoGroupId> //
			ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID = new ModelDynAttributeAccessor<>(I_PP_Order.class.getName(), "PPOrderRequestedEvent_GroupId", MaterialDispoGroupId.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	public PPOrderPojoConverter(@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.productDescriptorFactory = productDescriptorFactory;
	}

	public PPOrder getById(final int ppOrderId)
	{
		Check.assumeGreaterThanZero(ppOrderId, "ppOrderId");

		// FIXME: use IPPOrderDAO.... but now we cannot because the interface is not visible in this project
		final I_PP_Order ppOrderRecord = load(ppOrderId, I_PP_Order.class);
		return toPPOrder(ppOrderRecord);
	}

	public PPOrder toPPOrder(@NonNull final I_PP_Order ppOrderRecord)
	{
		final UomId uomId = UomId.ofRepoId(ppOrderRecord.getC_UOM_ID());
		final ProductId productId = ProductId.ofRepoId(ppOrderRecord.getM_Product_ID());
		final Quantity qtyRequired = uomConversionBL.convertToProductUOM(Quantitys.create(ppOrderRecord.getQtyOrdered(), uomId), productId);
		final Quantity qtyDelivered = uomConversionBL.convertToProductUOM(Quantitys.create(ppOrderRecord.getQtyDelivered(), uomId), productId);

		return PPOrder.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ppOrderRecord.getAD_Client_ID(), ppOrderRecord.getAD_Org_ID()))
				.datePromised(asInstant(ppOrderRecord.getDatePromised()))
				.dateStartSchedule(asInstant(ppOrderRecord.getDateStartSchedule()))
				.docStatus(DocStatus.ofCode(ppOrderRecord.getDocStatus()))
				.plantId(ResourceId.ofRepoId(ppOrderRecord.getS_Resource_ID()))
				.ppOrderId(ppOrderRecord.getPP_Order_ID())
				.productDescriptor(productDescriptorFactory.createProductDescriptor(ppOrderRecord))
				.productPlanningId(ppOrderRecord.getPP_Product_Planning_ID())
				.qtyRequired(qtyRequired.toBigDecimal())
				.qtyDelivered(qtyDelivered.toBigDecimal())
				.warehouseId(WarehouseId.ofRepoId(ppOrderRecord.getM_Warehouse_ID()))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(ppOrderRecord.getC_BPartner_ID()))
				.orderLineId(ppOrderRecord.getC_OrderLine_ID())
				.materialDispoGroupId(getMaterialDispoGroupIdOrNull(ppOrderRecord))
				//
				.lines(toPPOrderLinesList(ppOrderRecord))
				//
				.build();
	}

	private List<PPOrderLine> toPPOrderLinesList(@NonNull final I_PP_Order ppOrderRecord)
	{
		final List<PPOrderLine> lines = new ArrayList<>();
		for (final I_PP_Order_BOMLine ppOrderLineRecord : ppOrderBOMsRepo.retrieveOrderBOMLines(ppOrderRecord))
		{
			final PPOrderLine ppOrderLinePojo = toPPOrderLine(ppOrderLineRecord, ppOrderRecord);
			lines.add(ppOrderLinePojo);
		}
		return lines;
	}

	private PPOrderLine toPPOrderLine(@NonNull final I_PP_Order_BOMLine ppOrderLineRecord, @NonNull final I_PP_Order ppOrderRecord)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(ppOrderLineRecord.getComponentType());
		final boolean receipt = PPOrderUtil.isReceipt(componentType);
		final Instant issueOrReceiveDate = asInstant(receipt ? ppOrderRecord.getDatePromised() : ppOrderRecord.getDateStartSchedule());

		final UomId lineUomId = UomId.ofRepoId(ppOrderLineRecord.getC_UOM_ID());
		final ProductId lineProductId = ProductId.ofRepoId(ppOrderLineRecord.getM_Product_ID());
		final Quantity qtyRequired = uomConversionBL.convertToProductUOM(Quantitys.create(ppOrderLineRecord.getQtyRequiered(), lineUomId), lineProductId);
		final Quantity qtyDelivered = uomConversionBL.convertToProductUOM(Quantitys.create(ppOrderLineRecord.getQtyDelivered(), lineUomId), lineProductId);

		return PPOrderLine.builder()
				.productDescriptor(productDescriptorFactory.createProductDescriptor(ppOrderLineRecord))
				.description(ppOrderLineRecord.getDescription())
				.ppOrderLineId(ppOrderLineRecord.getPP_Order_BOMLine_ID())
				.productBomLineId(ppOrderLineRecord.getPP_Product_BOMLine_ID())
				.qtyRequired(qtyRequired.toBigDecimal())
				.qtyDelivered(qtyDelivered.toBigDecimal())
				.issueOrReceiveDate(issueOrReceiveDate)
				.receipt(receipt)
				.build();
	}

	@VisibleForTesting
	public static MaterialDispoGroupId getMaterialDispoGroupIdOrNull(@NonNull final I_PP_Order ppOrderRecord)
	{
		return ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.getValue(ppOrderRecord);
	}

	public static void setMaterialDispoGroupId(@NonNull final I_PP_Order ppOrderRecord, final MaterialDispoGroupId materialDispoGroupId)
	{
		ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.setValue(ppOrderRecord, materialDispoGroupId);
	}
}
