package de.metas.order.modelvalidator;

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


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.order.service.IOrderPA;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderDAO;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.adempiere.service.impl.OrderLineBL;
import de.metas.interfaces.I_C_OrderLine;

@Interceptor(I_C_Order.class)
public class C_Order
{
	@Init
	public void setupCallouts()
	{
		// Setup callout C_Order
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.callout.C_Order());
	}

	// 03409: Context menu fixes (2012101810000086)
	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					// I checked the code of OrderBL.updateAddresses() and MOrderLine.setHeaderInfo() to get this list
					I_C_Order.COLUMNNAME_C_BPartner_ID,
					I_C_Order.COLUMNNAME_C_BPartner_Location_ID,
					I_C_Order.COLUMNNAME_DropShip_BPartner_ID,
					I_C_Order.COLUMNNAME_DropShip_Location_ID,
					I_C_Order.COLUMNNAME_M_PriceList_ID,
					I_C_Order.COLUMNNAME_IsSOTrx,
					I_C_Order.COLUMNNAME_C_Currency_ID })
	public void updateOrderLineAddresses(final I_C_Order order) throws Exception
	{
		Services.get(IOrderBL.class).updateAddresses(order);
	}

	// 04579 Cannot change order's warehouse (2013071510000103)
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void unreserveStock(final I_C_Order order) throws Exception
	{
		for (final I_C_OrderLine orderLine : Services.get(IOrderPA.class).retrieveOrderLines(order, I_C_OrderLine.class))
		{
			if (orderLine.getQtyReserved().signum() <= 0)
			{
				continue; // nothing to do
			}

			final BigDecimal qtyOrdered = orderLine.getQtyOrdered();
			final BigDecimal qtyEntered = orderLine.getQtyEntered();
			final BigDecimal lineNetAmt = orderLine.getLineNetAmt();

			// just setting this one to zero would result in negative reservations in case of (partial) deliveries.
			orderLine.setQtyOrdered(orderLine.getQtyDelivered());
			orderLine.setQtyEntered(BigDecimal.ZERO);
			orderLine.setLineNetAmt(BigDecimal.ZERO);

			// task 08002
			InterfaceWrapperHelper.setDynAttribute(orderLine, OrderLineBL.DYNATTR_DoNotRecalculatePrices, Boolean.TRUE);

			InterfaceWrapperHelper.save(orderLine);

			Services.get(IOrderPA.class).reserveStock(orderLine.getC_Order(), orderLine);

			orderLine.setQtyOrdered(qtyOrdered);
			orderLine.setQtyEntered(qtyEntered);
			orderLine.setLineNetAmt(lineNetAmt);
			InterfaceWrapperHelper.save(orderLine);

			// task 08002
			InterfaceWrapperHelper.setDynAttribute(orderLine, OrderLineBL.DYNATTR_DoNotRecalculatePrices, Boolean.FALSE);
		}
	}

	/**
	 * Updates <code>C_OrderLine.QtyReserved</code> of the given order's lines when the Doctype or DocStatus changes.
	 *
	 * @param orderLine
	 * @task http://dewiki908/mediawiki/index.php/09358_OrderLine-QtyReserved_sometimes_not_updated_%28108061810375%29
	 */
	@ModelChange(
			timings = { 
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { 
					I_C_Order.COLUMNNAME_C_DocType_ID,
					I_C_Order.COLUMNNAME_DocStatus })
	public void updateReserved(final I_C_Order order)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			orderLineBL.updateQtyReserved(orderLine);
			InterfaceWrapperHelper.save(orderLine);
		}
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_C_Order.COLUMNNAME_C_BPartner_ID })
	public void setDeliveryViaRule(final I_C_Order order) throws Exception
	{
		final String deliveryViaRule = Services.get(IOrderBL.class).evaluateOrderDeliveryViaRule(order);

		if (!Check.isEmpty(deliveryViaRule, true))
		{
			order.setDeliveryViaRule(deliveryViaRule);
		}
	}
}
