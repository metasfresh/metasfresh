package de.metas.material.planning.pporder;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Service;

import de.metas.material.event.EventDescr;
import de.metas.material.event.ProductionOrderRequested;
import de.metas.material.event.ProductionPlanEvent;
import de.metas.material.event.ProductionPlanEvent.ProductionPlanEventBuilder;
import de.metas.material.event.pporder.PPOrder;
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

//	public PPOrder asPPOrderPojo(@NonNull final I_PP_Order ppOrder)
//	{
//		return PPOrder.builder()
//				.datePromised(ppOrder.getDatePromised())
//				.dateStartSchedule(ppOrder.getDateStartSchedule())
//				.orgId(ppOrder.getAD_Org_ID())
//
//				// .productPlanningId(productPlanningId)
//				.plantId(ppOrder.getS_Resource_ID())
//							.productId(ppOrder.getM_Product_ID())
//				.quantity(ppOrder.getQtyOrdered())
//				.uomId(ppOrder.getC_UOM_ID())
//				.warehouseId(ppOrder.getM_Warehouse_ID())
//
//				.build();
//	}

	public PPOrderLine asPPOrderLinePojo(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		return PPOrderLine.builder()
				.productBomLineId(ppOrderBOMLine.getPP_Product_BOMLine_ID())
				.attributeSetInstanceId(ppOrderBOMLine.getM_AttributeSetInstance_ID())
				.description(ppOrderBOMLine.getDescription())
				.productId(ppOrderBOMLine.getM_Product_ID())
				.qtyRequired(ppOrderBOMLine.getQtyRequiered())
				.build();
	}

	public PPOrder asPPOrderPojo(@NonNull final ProductionOrderRequested event)
	{
		return event.getPpOrder();
	}

	public ProductionPlanEvent asProductionPlanEvent(
			@NonNull final PPOrder ppOrder,
			@NonNull final TableRecordReference reference)
	{
		final Integer orgId = ppOrder.getOrgId();
		final Integer warehouseId = ppOrder.getWarehouseId();

		final BigDecimal productQty = convertQtyToProductUOM(ppOrder.getProductId(), ppOrder.getUomId(), ppOrder.getQuantity());

		final ProductionPlanEventBuilder builder = ProductionPlanEvent.builder();
		builder
				.eventDescr(new EventDescr())
				.reference(reference)
				.ppOrder(ppOrder);

		return builder.build();
	}

	private BigDecimal convertQtyToProductUOM(final Integer productId, final Integer uomId, final BigDecimal quantity)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
		final I_C_UOM uomSource = InterfaceWrapperHelper.create(Env.getCtx(), uomId, I_C_UOM.class, ITrx.TRXNAME_None);

		// in material-dispo we currently don't care for UOMs, so it always has to be the product's UOM.
		final BigDecimal productQty = uomConversionBL.convertToProductUOM(Env.getCtx(), product, uomSource, quantity);
		return productQty;
	}
}
