package de.metas.order.model.validator;

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
import java.util.logging.Level;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutOrder;
import org.compiere.model.ModelValidator;
import org.compiere.util.CLogger;

import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;

@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{

	private static final CLogger logger = CLogger.getCLogger(C_OrderLine.class);

	public static final String ERR_NEGATIVE_QTY_RESERVED = "MSG_NegativeQtyReserved";

	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.ordercandidate.callout.C_OrderLine());
	}

	/**
	 * Method is fired before an order line is deleted. It deletes all {@link I_C_Order_Line_Alloc} records referencing the order line and sets <code>Processed='N'</code> for all {@link I_C_OLCand}s
	 * that were originally aggregated into the order line.
	 * 
	 * @param ol
	 */
	// 03472
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteOlas(final I_C_OrderLine ol)
	{
		final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
		final List<I_C_Order_Line_Alloc> olasToDelete = olCandDAO.retrieveAllOlas(ol);

		for (final I_C_Order_Line_Alloc ola : olasToDelete)
		{
			final I_C_OLCand olCand = ola.getC_OLCand();
			olCand.setProcessed(false);
			InterfaceWrapperHelper.save(olCand);

			InterfaceWrapperHelper.delete(ola);
		}
	}

	/**
	 * If a purchase order line is deleted, then all sales order lines need to un-reference it to avoid an FK-constraint-error
	 *
	 * @param orderLine
	 * @task http://dewiki908/mediawiki/index.php/09557_Wrong_aggregation_on_OrderPOCreate_%28109614894753%29
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void unlinkSalesOrderLines(final I_C_OrderLine orderLine)
	{
		final List<I_C_OrderLine> referencingOrderLines = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class)
				.setContext(orderLine)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_Link_OrderLine_ID, orderLine.getC_OrderLine_ID())
				.create().list(I_C_OrderLine.class);
		for (final I_C_OrderLine referencingOrderLine : referencingOrderLines)
		{
			referencingOrderLine.setLink_OrderLine(null);
			InterfaceWrapperHelper.save(referencingOrderLine);
		}
	}

	/**
	 * Set QtyOrderedInPriceUOM, just to make sure is up2date.
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyEntered,
					I_C_OrderLine.COLUMNNAME_Price_UOM_ID,
					I_C_OrderLine.COLUMNNAME_C_UOM_ID,
					I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void setQtyEnteredInPriceUOM(final I_C_OrderLine orderLine)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final BigDecimal qtyEnteredInPriceUOM = orderLineBL.calculateQtyEnteredInPriceUOM(orderLine);
		orderLine.setQtyEnteredInPriceUOM(qtyEnteredInPriceUOM);
	}

	/**
	 * Set qtyOrdered, to make sure is up2date. Note that this value is also set in
	 * {@link CalloutOrder#amt(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, Object)}.
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID,
					I_C_OrderLine.COLUMNNAME_QtyEntered,
					I_C_OrderLine.COLUMNNAME_C_UOM_ID })
	public void setQtyOrdered(final I_C_OrderLine orderLine)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final BigDecimal qtyOrdered = orderLineBL.calculateQtyOrdered(orderLine);
		orderLine.setQtyOrdered(qtyOrdered);
	}

	/**
	 * 
	 * @param orderLine	
	 * @task http://dewiki908/mediawiki/index.php/09358_OrderLine-QtyReserved_sometimes_not_updated_%28108061810375%29
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyOrdered,
					I_C_OrderLine.COLUMNNAME_QtyDelivered,
					I_C_OrderLine.COLUMNNAME_C_Order_ID })
	public void updateReserved(final I_C_OrderLine orderLine)
	{
		Services.get(IOrderLineBL.class).updateQtyReserved(orderLine);
	}

	/**
	 * Ported this method from de.metas.adempiere.modelvalidator.OrderLine
	 * 
	 * @param ol
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyReserved })
	public void checkQtyReserved(final I_C_OrderLine ol)
	{
		if (ol.getQtyReserved().signum() >= 0)
		{
			return; // nothing to do
		}
		// task: 08665
		// replace the negative qty with 0, logging a warning message
		ol.setQtyReserved(BigDecimal.ZERO);

		// task: 08665 ts: but only log when in developer mode. i don't see how this warning should help the user
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			final AdempiereException ex = new AdempiereException("@" + ERR_NEGATIVE_QTY_RESERVED + "@. Setting QtyReserved to ZERO."
					+ "\nStorage: " + ol);
			logger.log(Level.WARNING, ex.getLocalizedMessage(), ex);
		}
	}

	// task 06727
	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_PriceEntered,
					I_C_OrderLine.COLUMNNAME_Discount
			})
	public void setIsManual(final I_C_OrderLine olPO)
	{
		// urgent-no-taskname available yet: commenting out the code that sets IsManual bacause where that, attriibute changes don'z update the price anymore (checking for InterfaceWrapperHelper.isUI
		// doesn't help, because it actuall *was* a UI action..the action ust didn't change one of these two)
		// olPO.setIsManualPrice(true);
		// olPO.setIsManualDiscount(true);
	}
	
	/**
	 * 
	 * @param orderLine
	 * @task http://dewiki908/mediawiki/index.php/09285_add_deliver_and_invoice_status_to_order_window
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }
			, ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_QtyOrdered,
					I_C_OrderLine.COLUMNNAME_QtyInvoiced,
					I_C_OrderLine.COLUMNNAME_QtyDelivered
			})
	public void updateQuantities(final I_C_OrderLine orderLine) 
	{
		Services.get(IOrderBL.class).updateOrderQtySums(orderLine.getC_Order());
	}
}
