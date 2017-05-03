package org.eevolution.mrp.spi.impl.ddorder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.X_DD_Order;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDAO;

import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ddorder.DDOrder;
import de.metas.material.planning.ddorder.DDOrderLine;

/*
 * #%L
 * metasfresh-mrp
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

public class DDOrderProducer
{
	public I_DD_Order createDDOrder(final DDOrder pojo,
			final IMRPCreateSupplyRequest request,
			final int docTypeDO_ID)
	{
		final IMaterialPlanningContext mrpContext = request.getMRPContext();

		final I_DD_Order order = InterfaceWrapperHelper.newInstance(I_DD_Order.class, mrpContext);
		order.setMRP_Generated(true);
		order.setMRP_AllowCleanup(true);
		order.setAD_Org_ID(pojo.getOrgId());
		order.setPP_Plant_ID(pojo.getPlantId());
		order.setC_BPartner_ID(pojo.getBPartnerId());
		order.setC_BPartner_Location_ID(pojo.getBPartnerLocationId());
		// order.setAD_User_ID(productPlanningData.getPlanner_ID()); // FIXME: improve performances/cache and retrive Primary BP's User
		order.setSalesRep_ID(pojo.getPlannerId());

		order.setC_DocType_ID(docTypeDO_ID);
		order.setM_Warehouse_ID(pojo.getInTransitWarehouseId());
		order.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		order.setDocAction(X_DD_Order.DOCACTION_Complete);
		order.setDateOrdered(new Timestamp(pojo.getDateOrdered().getTime()));
		order.setDatePromised(new Timestamp(pojo.getDatePromised().getTime()));
		order.setM_Shipper_ID(pojo.getShipperId());
		order.setIsInDispute(false);
		order.setIsInTransit(false);

		InterfaceWrapperHelper.save(order);

		for (final DDOrderLine linePojo : pojo.getDdOrderLines())
		{
			//
			// Create DD Order Line
			final I_DD_OrderLine ddOrderline = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, order);
			ddOrderline.setAD_Org_ID(pojo.getOrgId());
			ddOrderline.setDD_Order(order);
			ddOrderline.setC_BPartner_ID(linePojo.getDemandBPartnerId());
			ddOrderline.setC_OrderLineSO_ID(linePojo.getSalesOrderLineId());

			//
			// Locator From/To
			ddOrderline.setM_Locator_ID(linePojo.getFromLocatorId());
			ddOrderline.setM_LocatorTo_ID(linePojo.getToLocatorId());

			//
			// Product, UOM, Qty
			// NOTE: we assume qtyToMove is in "mrpContext.getC_UOM()" which shall be the Product's stocking UOM

			final I_M_Product product = InterfaceWrapperHelper.create(mrpContext.getCtx(), linePojo.getProductId(), I_M_Product.class, mrpContext.getTrxName());

			ddOrderline.setM_Product_ID(linePojo.getProductId());
			ddOrderline.setC_UOM_ID(product.getC_UOM_ID());
			ddOrderline.setQtyEntered(linePojo.getQty());
			ddOrderline.setQtyOrdered(linePojo.getQty());
			ddOrderline.setTargetQty(linePojo.getQty());

			//
			// Dates
			ddOrderline.setDateOrdered(order.getDateOrdered());
			ddOrderline.setDatePromised(order.getDatePromised());

			//
			// Other flags
			ddOrderline.setIsInvoiced(false);
			ddOrderline.setDD_AllowPush(linePojo.getAllowPush());
			ddOrderline.setIsKeepTargetPlant(linePojo.getKeepTargetPlant());

			//
			// Save DD Order Line
			InterfaceWrapperHelper.save(ddOrderline);

			final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

			//
			// Create DD_OrderLine_Alternatives
			// NOTE: demand MRP line is available only in lot-for-lot order policy
			// TODO: i think we shall get all MRP demands, retrieve their alternatives, aggregate them and create DD_OrderLine_Alternatives
			final I_PP_MRP parentMRPDemand = request.getMRPDemandRecordOrNull();
			if (parentMRPDemand != null)
			{

				final List<I_PP_MRP_Alternative> mrpAlternatives = mrpDAO.retrieveMRPAlternativesQuery(parentMRPDemand).create().list();
				for (final I_PP_MRP_Alternative mrpAlternative : mrpAlternatives)
				{
					createDD_OrderLine_Alternative(mrpContext, ddOrderline, mrpAlternative);
				}
			}

			final Timestamp supplyDateFinishSchedule = TimeUtil.asTimestamp(request.getDemandDate());

			//
			// Set Correct Planning Dates
			final Timestamp supplyDateStartSchedule = TimeUtil.addDays(supplyDateFinishSchedule, 0 - linePojo.getDurationDays());

			final List<I_PP_MRP> mrpList = mrpDAO.retrieveMRPRecords(ddOrderline);
			for (final I_PP_MRP mrp : mrpList)
			{
				mrp.setDateStartSchedule(supplyDateStartSchedule);
				mrp.setDateFinishSchedule(supplyDateFinishSchedule);
				InterfaceWrapperHelper.save(mrp);
			}
		}

		return order;
	}

	private void createDD_OrderLine_Alternative(final IMaterialPlanningContext mrpContext, final I_DD_OrderLine ddOrderLine, final I_PP_MRP_Alternative mrpAlternative)
	{
		final IMRPBL mrpBL = Services.get(IMRPBL.class);

		final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_Alternative.class, ddOrderLine);
		ddOrderLineAlt.setAD_Org_ID(mrpAlternative.getAD_Org_ID());
		ddOrderLineAlt.setDD_OrderLine(ddOrderLine);

		final I_M_Product product = mrpAlternative.getM_Product();
		final I_C_UOM uom = mrpBL.getC_UOM(mrpAlternative);
		final BigDecimal qty = mrpAlternative.getQty();

		ddOrderLineAlt.setM_Product(product);
		ddOrderLineAlt.setM_AttributeSetInstance(ddOrderLine.getM_AttributeSetInstance());
		ddOrderLineAlt.setC_UOM(uom);
		ddOrderLineAlt.setQtyOrdered(qty);
		ddOrderLineAlt.setQtyDelivered(BigDecimal.ZERO);
		ddOrderLineAlt.setQtyInTransit(BigDecimal.ZERO);

		InterfaceWrapperHelper.save(ddOrderLineAlt);
	}
}
