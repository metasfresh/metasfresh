package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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

import static org.compiere.model.X_C_Order.DELIVERYRULE_CompleteOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Order;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IInOutCandHandler;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;
import de.metas.product.IProductBL;

/**
 * Default implementation for sales order lines.
 *
 * @author ts
 *
 */
public class OrderLineInOutCandHandler implements IInOutCandHandler
{

	private static final Logger logger = LogManager.getLogger(OrderLineInOutCandHandler.class);

	@Override
	public List<I_M_ShipmentSchedule> createCandidatesFor(final Object model)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		// sanity check ol.getM_Product_ID()
		if (orderLine.getQtyOrdered().signum() <= 0 || orderLine.getQtyReserved().signum() < 0)
		{
			throw new AdempiereException(orderLine.toString() + " has QtyOrdered<=0 or QtyReserved<0");
		}

		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		final I_M_ShipmentSchedule newSched = InterfaceWrapperHelper.create(ctx, I_M_ShipmentSchedule.class, trxName);

		final BigDecimal qtyOrdered_Effective = orderLine.getQtyOrdered();

		newSched.setC_Order_ID(orderLine.getC_Order_ID());
		newSched.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		newSched.setQtyReserved(BigDecimal.ZERO.max(orderLine.getQtyReserved())); // task 09358: making sure that negative qtyOrdered are not proagated to the shipment sched
		newSched.setQtyOrdered_Calculated(qtyOrdered_Effective);
		newSched.setDateOrdered(orderLine.getDateOrdered());
		Services.get(IAttributeSetInstanceBL.class).cloneASI(newSched, orderLine);
		;

		//
		// 08255 : initialize the qty order calculated
		newSched.setQtyOrdered_Calculated(qtyOrdered_Effective);

		newSched.setPriorityRule(order.getPriorityRule());

		Check.assume(newSched.getAD_Client_ID() == orderLine.getAD_Client_ID(),
				"The new M_ShipmentSchedule haas the same AD_Client_ID as " + orderLine + ", i.e." + newSched.getAD_Client_ID() + " == " + orderLine.getAD_Client_ID());

		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_OrderLine.Table_Name);
		newSched.setAD_Table_ID(adTableId);
		newSched.setRecord_ID(orderLine.getC_OrderLine_ID());

		// 03152 begin
		// the following fields used to be SQL columns. Now setting them programatically

		Check.assume(orderLine.getM_Product_ID() > 0, orderLine + " has M_Product_ID>0");
		newSched.setM_Product_ID(orderLine.getM_Product_ID());

		newSched.setProductDescription(orderLine.getProductDescription());

		// 04290
		newSched.setM_Warehouse(Services.get(IWarehouseAdvisor.class).evaluateWarehouse(orderLine));

		newSched.setC_DocType_ID(order.getC_DocType_ID());
		newSched.setDocSubType(order.getC_DocType().getDocSubType());

		newSched.setC_BPartner_Location_ID(orderLine.getC_BPartner_Location_ID());
		newSched.setC_BPartner_ID(orderLine.getC_BPartner_ID());

		final String bPartnerAddress;
		if (!Check.isEmpty(orderLine.getBPartnerAddress()))
		{
			bPartnerAddress = orderLine.getBPartnerAddress();
		}
		else if (!Check.isEmpty(order.getDeliveryToAddress()))
		{
			bPartnerAddress = order.getDeliveryToAddress();
		}
		else
		{
			bPartnerAddress = order.getBPartnerAddress();
		}
		newSched.setBPartnerAddress(bPartnerAddress);

		newSched.setAD_User_ID(orderLine.getAD_User_ID());

		newSched.setDeliveryRule(order.getDeliveryRule());
		newSched.setDeliveryViaRule(order.getDeliveryViaRule());

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final BigDecimal qtyReservedInPriceUOM = uomConversionBL.convertFromProductUOM(ctx, orderLine.getM_Product(), orderLine.getPrice_UOM(), orderLine.getQtyReserved());
		newSched.setLineNetAmt(qtyReservedInPriceUOM.multiply(orderLine.getPriceActual()));

		final String groupingOrderLineLabel = DB
				.getSQLValueStringEx(
						trxName,
						" select ( o.DocumentNo || ' - ' || spt.Line ) " +
								" from C_OrderLine ol INNER JOIN C_OrderLine spt ON spt.C_OrderLine_ID=ol.SinglePriceTag_ID INNER JOIN C_Order o ON o.C_Order_ID=spt.C_Order_ID " +
								" where ol.C_OrderLine_ID=?",
						orderLine.getC_OrderLine_ID());
		// TODO column SinglePriceTag_ID has entity type de.metas.orderlineGrouping
		// either "adopt" it into the core or into de.metas.inoutcandidate or add a way to let external modules set
		// tthier values
		newSched.setSinglePriceTag_ID(groupingOrderLineLabel);
		// 03152 end

		newSched.setAD_Org_ID(orderLine.getAD_Org_ID());

		// only display item products
		final boolean display = Services.get(IProductBL.class).isItem(orderLine.getM_Product());
		newSched.setIsDisplayed(display);

		InterfaceWrapperHelper.save(newSched);

		// FIXME: disabled invalidation for
		// invalidateForOrderLine(orderLine, order, trxName);

		// Note: AllowConsolidateInOut and PostageFreeAmt is set on the first update of this schedule
		return Collections.singletonList(newSched);
	}

	/**
	 * For a given order line this method invalidates all shipment schedule lines that have the same product or (if the given order has a "complete order" delivery rule) even does the same with all
	 * order lines of the given order.
	 *
	 * @param orderLine
	 * @param order
	 * @param trxName
	 */
	@Override
	public void invalidateCandidatesFor(Object model)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		invalidateForOrderLine(orderLine, order, trxName);
	}

	public void invalidateForOrderLine(final I_C_OrderLine orderLine, final I_C_Order order, final String trxName)
	{
		final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);

		if (DELIVERYRULE_CompleteOrder.equals(order.getDeliveryRule()))
		{
			final IOrderPA orderPA = Services.get(IOrderPA.class);
			for (final I_C_OrderLine ol : orderPA.retrieveOrderLines(order, I_C_OrderLine.class))
			{
				shipmentScheduleInvalidateBL.invalidateJustForOrderLine(ol);
				shipmentScheduleInvalidateBL.invalidateSegmentForOrderLine(ol);
			}
		}
		else
		{
			shipmentScheduleInvalidateBL.invalidateJustForOrderLine(orderLine);
			shipmentScheduleInvalidateBL.invalidateSegmentForOrderLine(orderLine);
		}
	}

	@Override
	public String getSourceTable()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public List<Object> retrieveModelsWithMissingCandidates(
			final Properties ctx,
			final String trxName)
	{
		// task 08896: don't use the where clause with all those INs.
		// It's performance can turn catastrophic for large numbers or orderlines and orders.
		// Instead use an efficient view (i.e. C_OrderLine_ID_With_Missing_ShipmentSchedule) and (bad enough) use an in because we still need to select from *one* table.
		// i keep the original where for reference. The view is similar, but used inner joins to do the job. See the screeshot of this task to see how bad it was with that where-clause
		// Note: still, this polling sucks, but that's a bigger task
		final String wc = " C_OrderLine_ID IN ( select C_OrderLine_ID from C_OrderLine_ID_With_Missing_ShipmentSchedule_v ) ";
		// // only if the ol is not yet fully delivered
		// "    QtyOrdered <> QtyDelivered \n" +
		//
		// // only if the schedule doesn't exist yet
		// "   AND NOT EXISTS ( select * from M_ShipmentSchedule s where s.C_OrderLine_ID=C_OrderLine.C_OrderLine_ID )" +
		//
		// "	AND C_Order_ID IN (" +
		// "      select o.C_Order_ID " +
		// "      from C_Order o" +
		// "      where " +
		//
		// // only for sales orders
		// "      o.IsSOTrx='Y' \n" +
		//
		// // not for Proposals, Quotations and POS-Orders
		// "      AND o.C_DocType_ID IN ( \n" +
		// "        SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='SOO' AND DocSubType NOT IN ('ON','OB','WR') \n" +
		// "      )\n" +
		//
		// // only for completed orders
		// "      AND o.docstatus='CO' \n" +
		// "   )" +
		//
		// // only if we didn't yet look at the order
		// "   AND NOT EXISTS (" +
		// "      select 1 from " + I_M_IolCandHandler_Log.Table_Name + " log " +
		// "      where log." + I_M_IolCandHandler_Log.COLUMNNAME_M_IolCandHandler_ID + "=?" +
		// "        and log." + I_M_IolCandHandler_Log.COLUMNNAME_AD_Table_ID + "=?" +
		// "        and log." + I_M_IolCandHandler_Log.COLUMNNAME_Record_ID + "=C_OrderLine.C_OrderLine_ID" +
		// "        and log." + I_M_IolCandHandler_Log.COLUMNNAME_IsActive + "='Y'" +
		// "   )";

		final List<I_C_OrderLine> ols = new Query(ctx, getSourceTable(), wc, trxName)
				// task 08896: no params anymore, they are hardcoded in the view.
				// .setParameters(
				// Services.get(IInOutCandHandlerBL.class).retrieveHandlerRecordOrNull(ctx, this.getClass().getName(), trxName).getM_IolCandHandler_ID(),
				// MTable.getTable_ID(I_C_OrderLine.Table_Name))
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.list(I_C_OrderLine.class);

		logger.debug("Identified {} C_OrderLines that need a shipment schedule", ols.size());

		return new ArrayList<Object>(ols);
	}

	/**
	 * Returns an instance which in turn just returns as <ul>
	 * <li><code>QtyOrdered</code>: the sched's order line's QtyOrdered value
	 * </ul>
	 */
	@Override
	public IDeliverRequest createDeliverReques(final I_M_ShipmentSchedule sched)
	{
		return new IDeliverRequest()
		{
			@Override
			public BigDecimal getQtyOrdered()
			{
				return sched.getC_OrderLine().getQtyOrdered();
			}
		};
	}
}
