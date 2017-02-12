package test.integration.commission.sales;

/*
 * #%L
 * de.metas.commission.ait
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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.Query;

import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEvent;
import de.metas.adempiere.ait.test.annotation.ITEventListener;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.process.WriteCommissionAccount;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.prepayorder.service.IPrepayOrderBL;
import junit.framework.Assert;
import test.integration.commission.helper.Helper;
import test.integration.swat.sales.SalesTestDriver;

public class SalesTestEventListener
{

	private Helper getHelper(final TestEvent evt)
	{
		return new Helper(evt.getSource().getHelper());
	}

	/**
	 * Makes sure that the commission system is in a consistent state after a prepay order (docCubTypeSO='PM') has been
	 * created. This includes invoking the {@link WriteCommissionAccount} process.
	 */
	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.ORDER_PREPAY_COMPLETE_AFTER)
	public void prepayOrderCreated(final TestEvent evt)
	{
		final I_C_Order prepayOrder = InterfaceWrapperHelper.create(evt.getObj(), I_C_Order.class);
		final MOrder prepayOrderPO = InterfaceWrapperHelper.getPO(prepayOrder);
		Assert.assertNotNull("No PO found for " + prepayOrder, prepayOrderPO);

		// Guard: asserting that all order lines have a positive LineNetAmt
		for (final MOrderLine ol : prepayOrderPO.getLines(true, null))
		{
			assertTrue(ol.getLineNetAmt().signum() > 0);
		}

		final List<IAdvComInstance> instances = getHelper(evt).retrieveInstances(prepayOrderPO);

		// Asserting that the sums are ok (no points "to calculate", only "predicted")
		for (final IAdvComInstance instance : instances)
		{
			if (getHelper(evt).isCommissionCalculated(instance))
			{
				getHelper(evt).assertSumPredictedOnly(instance);
			}
		}
	}

	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.ORDER_POS_REACTIVATE_AFTER)
	public void posOrderReactivated(final TestEvent evt)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(evt.getObj(), I_C_Order.class);
		final MOrder orderPO = InterfaceWrapperHelper.getPO(order);
		Assert.assertNotNull("No PO found for " + order, orderPO);

		final List<IAdvComInstance> instances = getHelper(evt).retrieveInstances(orderPO);

		for (final IAdvComInstance instance : instances)
		{
			if (getHelper(evt).isCommissionCalculated(instance))
			{
				// Asserting that the sums are in order (no points "to calculate", only "predicted")
				getHelper(evt).assertSumPredictedOnly(instance);
			}
			getHelper(evt).checkOrderAndUnpaidInvoiceReversed(instance);
		}
	}

	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.ORDER_WITH_DIRECT_INVOICE_COMPLETE_AFTER)
	public void posOrderCreated(final TestEvent evt)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(evt.getObj(), I_C_Order.class);
		final MOrder orderPO = (MOrder)InterfaceWrapperHelper.getPO(order);
		Assert.assertNotNull("No PO found for " + order, orderPO);

		final List<IAdvComInstance> instances = getHelper(evt).retrieveInstances(order);

		for (final IAdvComInstance instance : instances)
		{
			if (getHelper(evt).isCommissionCalculated(instance))
			{
				// Asserting that the sums are ok (only points "to calculate")
				// Note: since us025b, POS-orders get their own invoice and payment right on completion
				getHelper(evt).assertSumToCalculateOnly(instance);
			}
			getHelper(evt).checkOrderAndUnPaidInvoice(instance);
		}
	}

	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.INVOICE_UNPAID_REVERSE_AFTER)
	public void invoiceUnpaidReversed(final TestEvent evt)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(evt.getObj(), I_C_Invoice.class);
		final MInvoice invoicePO = InterfaceWrapperHelper.getPO(invoice);
		Assert.assertNotNull("No PO found for " + invoice, invoicePO);

		for (final MInvoiceLine il : invoicePO.getLines(true))
		{
			final I_C_OrderLine orderLine = il.getC_OrderLine();
			final MOrderLine orderLinePO = InterfaceWrapperHelper.getPO(orderLine);
			Assert.assertNotNull("No PO found for " + orderLine, orderLinePO);

			final List<IAdvComInstance> instances = Services.get(ICommissionInstanceDAO.class).retrieveAllFor(orderLinePO, null);
			for (final IAdvComInstance instance : instances)
			{
				if (getHelper(evt).isCommissionCalculated(instance))
				{
					// Asserting that the sums are in order (no points "to calculate", only "predicted")
					getHelper(evt).assertSumPredictedOnly(instance);
				}
				getHelper(evt).checkOrderAndUnpaidInvoiceReversed(instance);
			}
		}
	}

	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.INVOICE_PAID_REVERSE_AFTER)
	public void invoicePaidReversed(final TestEvent evt)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(evt.getObj(), I_C_Invoice.class);
		final MInvoice invoicePO = InterfaceWrapperHelper.getPO(invoice);
		Assert.assertNotNull("No PO found for " + invoice, invoicePO);

		for (final MInvoiceLine il : invoicePO.getLines(true))
		{
			final I_C_OrderLine orderLine = il.getC_OrderLine();
			final MOrderLine orderLinePO = InterfaceWrapperHelper.getPO(orderLine);
			Assert.assertNotNull("No PO found for " + orderLine, orderLinePO);

			final List<IAdvComInstance> instances = Services.get(ICommissionInstanceDAO.class).retrieveAllFor(orderLinePO, null);
			for (final IAdvComInstance instance : instances)
			{
				if (getHelper(evt).isCommissionCalculated(instance))
				{
					// Asserting that the sums are in order (no points "to calculate", only "predicted")
					getHelper(evt).assertSumPredictedOnly(instance);
				}
				getHelper(evt).checkOrderAndPaidInvoiceReversed(instance);
			}
		}
	}

	/**
	 * Makes sure that the commission system is in a consistent state after a payment has been created. This includes
	 * invoking the {@link WriteCommissionAccount} process.
	 */
	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.PAYMENT_ORDER_COMPLETE_AFTER)
	public void orderPaymentCreated(final TestEvent evt)
	{
		final I_C_Payment payment = InterfaceWrapperHelper.create(evt.getObj(), I_C_Payment.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);

		final String wcAllocLine = I_C_AllocationLine.COLUMNNAME_C_Order_ID + "=?";
		final List<I_C_AllocationLine> allocLines =
				new Query(ctx, I_C_AllocationLine.Table_Name, wcAllocLine, trxName)
						.setParameters(payment.getC_Order_ID())
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.list(I_C_AllocationLine.class);
		// Asserting that the processing was successful
		for (final I_C_AllocationLine allocLine : allocLines)
		{
			final I_C_AllocationHdr alloc = allocLine.getC_AllocationHdr();
			getHelper(evt).assertFactCandsForPOProcessed(alloc);
		}

		final I_C_Order order = payment.getC_Order();
		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);

		final BigDecimal allocatedAmt = prepayOrderBL.retrieveAllocatedAmt(ctx, order.getC_Order_ID(), trxName);
		final boolean orderPaid = allocatedAmt.compareTo(order.getGrandTotal()) >= 0;

		//
		// asserting that we now have commission instances with correct facts
		final List<IAdvComInstance> instances = getHelper(evt).retrieveInstances(order);
		assertFalse(instances.isEmpty());

		for (final IAdvComInstance instance : instances)
		{
			if (orderPaid)
			{
				getHelper(evt).checkPrepayOrderWithSufficientPayment(instance);
			}
			else
			{
				getHelper(evt).checkPrepayOrderWithInsufficientPayment(instance);
			}
			if (!getHelper(evt).isCommissionCalculated(instance))
			{
				continue;
			}

			if (orderPaid)
			{
				// Asserting that the sums are OK (no points "predicted", only "to calculate")
				assertTrue(instance + " has Points_Predicted=" + instance.getPoints_Predicted(),
						instance.getPoints_Predicted().signum() == 0);
				assertTrue(instance + " has Points_ToCalculate=" + instance.getPoints_ToCalculate(),
						instance.getPoints_ToCalculate().signum() > 0);
			}
			else
			{
				// Asserting that the sums are OK (only points "predicted", no "to calculate")
				assertTrue(instance + " has Points_Predicted=" + instance.getPoints_Predicted(),
						instance.getPoints_Predicted().signum() > 0);
				assertTrue(instance + " has Points_ToCalculate=" + instance.getPoints_ToCalculate(),
						instance.getPoints_ToCalculate().signum() == 0);
			}
		}
	}

	@ITEventListener(
			driver = SalesTestDriver.class,
			eventTypes = EventType.PAYMENT_INVOICE_CREATE_AFTER)
	public void invoicePaymentCreated(final TestEvent evt)
	{
		final I_C_Payment payment = InterfaceWrapperHelper.create(evt.getObj(), I_C_Payment.class);

		getHelper(evt).runProcess_WriteCommissionAccount();
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);

		final String wcAllocLine = I_C_AllocationLine.COLUMNNAME_C_Order_ID + "=?";
		final List<I_C_AllocationLine> allocLines =
				new Query(ctx, I_C_AllocationLine.Table_Name, wcAllocLine, trxName)
						.setParameters(payment.getC_Invoice_ID())
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.list(I_C_AllocationLine.class);
		// Asserting that the processing was successful
		for (final I_C_AllocationLine allocLine : allocLines)
		{
			getHelper(evt).assertFactCandsForPOProcessed(allocLine.getC_AllocationHdr());
		}

		final I_C_Invoice invoice = payment.getC_Invoice();
		final MInvoice invoicePO = InterfaceWrapperHelper.getPO(invoice);
		Assert.assertNotNull("No PO found for " + invoice, invoicePO);

		for (final MInvoiceLine il : invoicePO.getLines(true))
		{
			final I_C_OrderLine orderLine = il.getC_OrderLine();
			final MOrderLine orderLinePO = InterfaceWrapperHelper.getPO(orderLine);
			Assert.assertNotNull("No PO found for " + orderLine, orderLinePO);

			final List<IAdvComInstance> instances = Services.get(ICommissionInstanceDAO.class).retrieveAllFor(orderLinePO, null);
			for (final IAdvComInstance instance : instances)
			{
				getHelper(evt).checkOrderAndPaidInvoice(instance);
			}
		}
	}
}
