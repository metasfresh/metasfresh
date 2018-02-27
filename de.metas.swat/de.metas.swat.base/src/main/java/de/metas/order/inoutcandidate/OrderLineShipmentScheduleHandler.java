package de.metas.order.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.compiere.model.X_C_Order.DELIVERYRULE_CompleteOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

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
import de.metas.inoutcandidate.spi.IShipmentScheduleHandler;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.product.IProductBL;
import lombok.NonNull;

/**
 * Default implementation for sales order lines.
 *
 * @author ts
 *
 */
public class OrderLineShipmentScheduleHandler implements IShipmentScheduleHandler
{

	private static final Logger logger = LogManager.getLogger(OrderLineShipmentScheduleHandler.class);

	@Override
	public List<I_M_ShipmentSchedule> createCandidatesFor(@NonNull final Object model)
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

		updateShipmentScheduleFromOrderLine(newSched, orderLine);

		updateShipmentScheduleFromOrder(newSched, order);

		Services.get(IAttributeSetInstanceBL.class).cloneASI(newSched, orderLine);

		Check.errorUnless(newSched.getAD_Client_ID() == orderLine.getAD_Client_ID(),
				"The new M_ShipmentSchedule needs to have the same AD_Client_ID as " + orderLine + ", i.e." + newSched.getAD_Client_ID() + " == " + orderLine.getAD_Client_ID());

		// 04290
		newSched.setM_Warehouse(Services.get(IWarehouseAdvisor.class).evaluateWarehouse(orderLine));

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

		// only display item products
		final boolean display = Services.get(IProductBL.class).isItem(orderLine.getM_Product());
		newSched.setIsDisplayed(display);

		InterfaceWrapperHelper.save(newSched);

		// Note: AllowConsolidateInOut and PostageFreeAmt is set on the first update of this schedule
		return Collections.singletonList(newSched);
	}

	private static void updateShipmentScheduleFromOrderLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_OrderLine orderLine)
	{
		shipmentSchedule.setC_Order_ID(orderLine.getC_Order_ID());
		shipmentSchedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());

		shipmentSchedule.setAD_Table_ID(getTableId(I_C_OrderLine.class));
		shipmentSchedule.setRecord_ID(orderLine.getC_OrderLine_ID());

		shipmentSchedule.setQtyReserved(BigDecimal.ZERO.max(orderLine.getQtyReserved())); // task 09358: making sure that negative qtyOrdered are not propagated to the shipment sched

		// 08255 : initialize the qty order calculated
		shipmentSchedule.setQtyOrdered_Calculated(orderLine.getQtyOrdered());
		shipmentSchedule.setDateOrdered(orderLine.getDateOrdered());

		shipmentSchedule.setProductDescription(orderLine.getProductDescription());

		shipmentSchedule.setC_BPartner_Location_ID(orderLine.getC_BPartner_Location_ID());
		shipmentSchedule.setC_BPartner_ID(orderLine.getC_BPartner_ID());

		Check.assume(orderLine.getM_Product_ID() > 0, orderLine + " has M_Product_ID>0");
		shipmentSchedule.setM_Product_ID(orderLine.getM_Product_ID());

		shipmentSchedule.setAD_User_ID(orderLine.getAD_User_ID());

		shipmentSchedule.setAD_Org_ID(orderLine.getAD_Org_ID());
	}

	private static void updateShipmentScheduleFromOrder(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_Order order)
	{
		shipmentSchedule.setPriorityRule(order.getPriorityRule());
		shipmentSchedule.setBill_BPartner_ID(order.getBill_BPartner_ID());
		shipmentSchedule.setDeliveryRule(order.getDeliveryRule());
		shipmentSchedule.setDeliveryViaRule(order.getDeliveryViaRule());

		shipmentSchedule.setC_DocType_ID(order.getC_DocType_ID());
		shipmentSchedule.setDocSubType(order.getC_DocType().getDocSubType());
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
			for (final I_C_OrderLine ol : Services.get(IOrderDAO.class).retrieveOrderLines(order, I_C_OrderLine.class))
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
		// Note: still, this polling sucks, but that's a bigger task
		final String wc = " C_OrderLine_ID IN ( select C_OrderLine_ID from C_OrderLine_ID_With_Missing_ShipmentSchedule_v ) ";

		final List<I_C_OrderLine> ols = new Query(ctx, getSourceTable(), wc, trxName)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.list(I_C_OrderLine.class);

		logger.debug("Identified {} C_OrderLines that need a shipment schedule", ols.size());

		return new ArrayList<>(ols);
	}

	/**
	 * Returns an instance which in turn just returns as
	 * <ul>
	 * <li><code>QtyOrdered</code>: the sched's order line's QtyOrdered value
	 * </ul>
	 */
	@Override
	public IDeliverRequest createDeliverRequest(final I_M_ShipmentSchedule sched)
	{
		return () -> sched.getC_OrderLine().getQtyOrdered();
	}
}
