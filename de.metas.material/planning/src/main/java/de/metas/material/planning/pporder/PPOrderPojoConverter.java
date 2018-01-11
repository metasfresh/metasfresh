package de.metas.material.planning.pporder;

import java.util.List;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Service;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrder.PPOrderBuilder;
import de.metas.material.event.pporder.PPOrderLine;
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
	public static final ModelDynAttributeAccessor<I_PP_Order, Integer> ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID = //
			new ModelDynAttributeAccessor<>(I_PP_Order.class.getName(), "PPOrderRequestedEvent_GroupId", Integer.class);

	private final ModelProductDescriptorExtractor productDescriptorFactory;

	public PPOrderPojoConverter(@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.productDescriptorFactory = productDescriptorFactory;
	}

	public PPOrderLine asPPOrderLinePojo(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(ppOrderBOMLine);

		return PPOrderLine.builder()
				.productBomLineId(ppOrderBOMLine.getPP_Product_BOMLine_ID())
				.productDescriptor(productDescriptor)
				.description(ppOrderBOMLine.getDescription())
				.qtyRequired(ppOrderBOMLine.getQtyRequiered())
				.build();
	}

	public PPOrder asPPOrderPojo(@NonNull final I_PP_Order ppOrderRecord)
	{
		final PPOrderBuilder ppOrderPojoBuilder = createPPorderPojoBuilder(ppOrderRecord);

		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);

		final List<I_PP_Order_BOMLine> orderBOMLines = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrderRecord);
		for (final I_PP_Order_BOMLine line : orderBOMLines)
		{
			final boolean receipt = PPOrderUtil.isReceipt(line.getComponentType());

			ppOrderPojoBuilder.line(PPOrderLine.builder()
					.productDescriptor(productDescriptorFactory.createProductDescriptor(line))
					.description(line.getDescription())
					.ppOrderLineId(line.getPP_Order_BOMLine_ID())
					.productBomLineId(line.getPP_Product_BOMLine_ID())
					.qtyRequired(line.getQtyRequiered())
					.issueOrReceiveDate(receipt ? ppOrderRecord.getDatePromised() : ppOrderRecord.getDateStartSchedule())
					.receipt(receipt)
					.build());
		}
		return ppOrderPojoBuilder.build();
	}

	private PPOrderBuilder createPPorderPojoBuilder(@NonNull final I_PP_Order ppOrder)
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);

		final int groupIdFromPPOrderRequestedEvent = ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.getValue(ppOrder, 0);

		final PPOrderBuilder ppOrderPojoBuilder = PPOrder.builder()
				.datePromised(ppOrder.getDatePromised())
				.dateStartSchedule(ppOrder.getDateStartSchedule())
				.docStatus(ppOrder.getDocStatus())
				.orderLineId(ppOrder.getC_OrderLine_ID())
				.orgId(ppOrder.getAD_Org_ID())
				.plantId(ppOrder.getS_Resource_ID())
				.ppOrderId(ppOrder.getPP_Order_ID())
				.productDescriptor(productDescriptorFactory.createProductDescriptor(ppOrder))
				.productPlanningId(ppOrder.getPP_Product_Planning_ID())
				.quantity(ppOrder.getQtyOrdered())
				.warehouseId(ppOrder.getM_Warehouse_ID())
				.bPartnerId(ppOrder.getC_BPartner_ID())
				.orderLineId(ppOrder.getC_OrderLine_ID())
				.materialDispoGroupId(groupIdFromPPOrderRequestedEvent);
		return ppOrderPojoBuilder;
	}
}
