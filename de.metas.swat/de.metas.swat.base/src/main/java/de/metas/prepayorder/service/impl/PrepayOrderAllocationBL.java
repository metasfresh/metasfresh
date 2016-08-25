package de.metas.prepayorder.service.impl;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.acct.api.IDocFactory;
import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MFactAcct;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MPeriod;
import org.compiere.model.Query;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;
import de.metas.prepayorder.interfaces.I_C_PaymentAllocate;
import de.metas.prepayorder.service.IPrepayOrderAllocationBL;
import de.metas.prepayorder.service.IPrepayOrderBL;

public class PrepayOrderAllocationBL implements IPrepayOrderAllocationBL
{

	private static Logger logger = LogManager.getLogger(PrepayOrderAllocationBL.class);

	@Override
	public void paymentAfterAllocateIt(final I_C_Payment payment, final boolean allocationLineWasCreated)
	{
		// final boolean result = (Boolean)inv.invokeNext();

		// final MPayment payment = (MPayment)inv.getTargetObject();
		if (!payment.isReceipt())
		{
			logger.debug(payment + " is no receipt, but we only deal with sales orders. Nothing to do");
			return;
		}

		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);

		if (allocationLineWasCreated)
		{
			// result==true means that a MAllocationLine line has been created for each MPaymentAllocate.

			// set to collect the orders that where allocated
			final Map<Integer, MOrder> orderId2order = new HashMap<Integer, MOrder>();

			// check if the allocation lines that have just been created need to be augmented with C_Order_ID
			for (final MPaymentAllocate paPO : MPaymentAllocate.get(payment))
			{
				final I_C_PaymentAllocate pa = InterfaceWrapperHelper.create(paPO, I_C_PaymentAllocate.class);
				final int orderId = pa.getC_PrepayOrder_ID();
				if (orderId <= 0)
				{
					logger.debug(pa + " has no C_Order. Nothing to do.");
					continue;
				}
				assert prepayOrderBL.isPrepayOrder(ctx, orderId, trxName) : "Has non-prepay-order: " + pa;

				orderId2order.put(orderId, (MOrder)pa.getC_PrepayOrder());

				final MAllocationLine allocLine = (MAllocationLine)pa.getC_AllocationLine();
				logger.debug("Setting C_Order_ID of " + allocLine + "=" + orderId + " (C_PaymentAllocate_ID=" + pa.getC_PaymentAllocate_ID() + ")");
				allocLine.setC_Order_ID(orderId);
				allocLine.saveEx();
			}

			// check if the orders are now completely paid for
			for (final int allocatedOrderId : orderId2order.keySet())
			{
				final MOrder order = orderId2order.get(allocatedOrderId);

				final BigDecimal allocatedAmt = prepayOrderBL.retrieveAllocatedAmt(ctx, allocatedOrderId, trxName);

				if (allocatedAmt.compareTo(order.getGrandTotal()) >= 0)
				{
					order.setC_Payment_ID(payment.getC_Payment_ID());
					order.setDocAction(X_C_Order.DOCACTION_WaitComplete);

					// metas: Prepayment Order shall receive their DateAcct from
					// Payment, because the Order Dateacct could be far back
					order.setDateAcct(payment.getDateAcct());

					if (order.processIt(X_C_Order.DOCACTION_WaitComplete))
					{
						order.saveEx();
					}
					else
					{
						final String processMsg = order.getProcessMsg();
						throw new AdempiereException(processMsg);
					}
				}
			}
			return;
		}
		else
		{
			// check if C_Payment.C_Order_ID!=0, which is a typical reason for MPayment.allocateIt() to return false
			final int orderId = payment.getC_Order_ID();
			if (orderId == 0)
			{
				logger.debug("MPayment.allocateIt() returned 'false' for " + payment + ", not because of C_Order_ID!=0, but for another reason. Nothing to do.");
				return;
			}

			if (!prepayOrderBL.isPrepayOrder(ctx, orderId, trxName))
			{
				logger.debug("MPayment.allocateIt() returned 'false' for " + payment + " probably because the referenced order is not a prepay order. Nothing to do");
				return;
			}

			Check.assume(payment.isReceipt(), payment + "is a receipt");
			// create an allocation for the payment's order
			final MAllocationHdr alloc = new MAllocationHdr(
					ctx,
					false,
					payment.getDateTrx(),
					payment.getC_Currency_ID(),
					Services.get(IMsgBL.class).translate(ctx, "C_Payment_ID") + ": " + payment.getDocumentNo(),
					trxName);
			alloc.setAD_Org_ID(payment.getAD_Org_ID());
			alloc.saveEx();

			// calculate actual allocation
			// 04614 OverUnderAmt shall not be part of any allocation amount. It's just an information!
			final BigDecimal allocationAmt = payment.getPayAmt();
			// if (payment.getOverUnderAmt().signum() < 0 && payment.getPayAmt().signum() > 0)
			// {
			// allocationAmt = allocationAmt.add(payment.getOverUnderAmt()); // overpayment
			// }
			final MAllocationLine aLine =
					new MAllocationLine(
							alloc,
							allocationAmt,
							payment.getDiscountAmt(),
							payment.getWriteOffAmt(),
							payment.getOverUnderAmt());

			aLine.setDocInfo(payment.getC_BPartner_ID(), orderId, 0);
			aLine.setC_Payment_ID(payment.getC_Payment_ID());
			aLine.saveEx();
			// Should start WF
			if (!alloc.processIt(DocAction.ACTION_Complete))
			{
				throw new AdempiereException(alloc.getProcessMsg());
			}
			alloc.saveEx();
		}

	}


	@Override
	public String orderBeforeCompleteIt(final MOrder order)
	{
		if (X_C_Order.DOCACTION_Prepare.equals(order.getDocAction()))
		{
			return null;
		}

		// tsa: better use the direct method then reflection:
		final boolean forceCreation = order.is_ForceCreation();
		// // get the private field value using reflection
		// final Field forceCreationField = MOrder.class.getDeclaredField("m_forceCreation");
		// forceCreationField.setAccessible(true);
		// final boolean forceCreation = forceCreationField.getBoolean(order);
		// forceCreationField.setAccessible(false);

		final Properties ctx = order.getCtx();
		final String trxName = order.get_TrxName();

		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);

		if (!forceCreation && prepayOrderBL.isPrepayOrder(ctx, order.get_ID(), trxName))
		{
			// check if there are sufficient allocations for this order
			final BigDecimal allocated = prepayOrderBL.retrieveAllocatedAmt(order.getCtx(), order.get_ID(), order.get_TrxName());
			if (allocated.compareTo(order.getGrandTotal()) < 0)
			{
				order.setProcessed(true);
				return DocAction.STATUS_WaitingPayment;
			}
		}
		return null;
	}

	@Override
	public void invoiceBeforeReverseCorrectIt(final MInvoice invoice)
	{
		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);

		final Properties ctx = invoice.getCtx();
		final String trxName = invoice.get_TrxName();

		if (prepayOrderBL.isPrepayOrder(ctx, invoice.getC_Order_ID(), trxName))
		{
			// load the prepay order's allocation lines
			final String wc = I_C_AllocationLine.COLUMNNAME_C_Order_ID + "=?";
			final List<MAllocationLine> allocLines =
					new Query(ctx, I_C_AllocationLine.Table_Name, wc, trxName)
							.setParameters(invoice.getC_Order_ID())
							.setOnlyActiveRecords(true)
							.setClient_ID()
							.list();

			final Map<Integer, MAllocationHdr> id2Hdr = new HashMap<Integer, MAllocationHdr>();

			for (final MAllocationLine allocLine : allocLines)
			{
				// Note: MAllocationLine.beforeSave() prohibits this operation.
				// This is why we use this dirty direct update.
				final int no = DB.executeUpdateEx("UPDATE " + I_C_AllocationLine.Table_Name
						+ " SET " + I_C_AllocationLine.COLUMNNAME_C_Invoice_ID + "=NULL"
						+ " WHERE " + I_C_AllocationLine.COLUMNNAME_C_AllocationLine_ID + "=" + allocLine.get_ID(),
						trxName);
				Check.errorUnless(no == 1, "no=" + no + "; C_Invoice_ID=" + invoice.get_ID() + "; I_C_AllocationLine_ID=" + allocLine.get_ID());

				if (!id2Hdr.containsKey(allocLine.getC_AllocationHdr_ID()))
				{
					id2Hdr.put(allocLine.getC_AllocationHdr_ID(), allocLine.getParent());
				}
			}

			if (invoice.testAllocation())
			{
				invoice.saveEx();
			}
			repostAllocationHdr(ctx, id2Hdr, trxName);
		}
		else
		{
			logger.debug(invoice + " does not reference a prepay order. Nothing to do.");
		}
	}


	@Override
	public void invoiceAfterCompleteIt(final MInvoice invoice)
	{
		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);

		final Properties ctx = invoice.getCtx();
		final String trxName = invoice.get_TrxName();

		if (invoice.isReversal())
		{
			logger.debug(invoice + " is a reversal. Nothing to do.");
			return;
		}

		// start: metas: c.ghita@metas.ro : rma and order are on same level
		if (invoice.getM_RMA_ID() > 0 && invoice.getC_Order_ID() <= 0)
		{
			logger.debug(invoice + " does not reference a prepay order. Is a RMA invoice. Nothing to do.");
			return;
		}
		// end: metas: c.ghita@metas.ro

		if (!prepayOrderBL.isPrepayOrder(ctx, invoice.getC_Order_ID(), trxName))
		{
			logger.debug(invoice + " does not reference a prepay order. Nothing to do.");
			return;
		}

		// load the prepay order's allocation lines
		final String wc = I_C_AllocationLine.COLUMNNAME_C_Order_ID + "=?";
		final List<MAllocationLine> allocLines =
				new Query(ctx, I_C_AllocationLine.Table_Name, wc, trxName)
						.setParameters(invoice.getC_Order_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.list();

		final Map<Integer, MAllocationHdr> id2Hdr = new HashMap<Integer, MAllocationHdr>();

		// add the invoice's ID to those lines and collect the C_AllocationHdrs for reposting
		for (final MAllocationLine allocLine : allocLines)
		{
			logger.debug("Setting C_Invoice_ID=" + invoice.get_ID() + " for " + allocLine);

			// Note: MAllocationLine.beforeSave() prohibits this operation.
			// This is why we use this dirty direct update.
			final I_C_Invoice oldInvoice = allocLine.getC_Invoice();

			// cg: task 06491: DO NOT REASSIGN allocation line for an existent invoice
			if (oldInvoice != null && oldInvoice.getC_Invoice_ID() > 0
					&& (X_C_Invoice.DOCSTATUS_Reversed.equals(oldInvoice.getDocStatus()) || X_C_Invoice.DOCSTATUS_Voided.equals(oldInvoice.getDocStatus())))
			{
				continue;
			}
			else
			{
				final int no = DB.executeUpdateEx("UPDATE " + I_C_AllocationLine.Table_Name
						+ " SET " + I_C_AllocationLine.COLUMNNAME_C_Invoice_ID + "=" + invoice.get_ID()
						+ " WHERE " + I_C_AllocationLine.COLUMNNAME_C_AllocationLine_ID + "=" + allocLine.get_ID(),
						trxName);
				Check.errorUnless(no == 1, "no=" + no + "; C_Invoice_ID=" + invoice.get_ID() + "; I_C_AllocationLine_ID=" + allocLine.get_ID());

				if (!id2Hdr.containsKey(allocLine.getC_AllocationHdr_ID()))
				{
					id2Hdr.put(allocLine.getC_AllocationHdr_ID(), allocLine.getParent());
				}
			}
		}

		//
		// metas: c.ghita@metas.ro : start: US025b
		// repostAllocationHdr(ctx, id2Hdr, trxName);
		unpostAllocationHdr(ctx, id2Hdr, trxName);
		setDateAllocation(id2Hdr);
		// metas: c.ghita@metas.ro : end: US025b
		//

		logger.debug("Checking if " + invoice + " is paid by order's allocation lines");
		invoice.testAllocation();

		return;
	}

	private void repostAllocationHdr(final Properties ctx, final Map<Integer, MAllocationHdr> id2Hdr, final String trxName)
	{
		final String whereClause = I_Fact_Acct.COLUMNNAME_AD_Table_ID + "=? AND " + I_Fact_Acct.COLUMNNAME_Record_ID + "=?";

		for (final MAllocationHdr hdr : id2Hdr.values())
		{
			if (!hdr.isPosted())
			{
				// nothing to do, hdr will be posted the default way
				continue;
			}

			final List<MFactAcct> existingFacts =
					new Query(ctx, I_Fact_Acct.Table_Name, whereClause, trxName)
							.setParameters(hdr.get_Table_ID(), hdr.get_ID())
							.setClient_ID()
							.setOnlyActiveRecords(true)
							.list();

			final Map<Integer, MAcctSchema> ass = new HashMap<Integer, MAcctSchema>();

			for (final MFactAcct fact : existingFacts)
			{
				if (!ass.containsKey(fact.getC_AcctSchema_ID()))
				{
					ass.put(fact.getC_AcctSchema_ID(), (MAcctSchema)fact.getC_AcctSchema());
				}
			}

			final IDocFactory docFactory = Services.get(IDocFactory.class);
			for (final MAcctSchema acctSchema : ass.values())
			{
				docFactory.getOrNull(ctx, new MAcctSchema[] { acctSchema }, hdr.get_Table_ID(), hdr.get_ID(), trxName).post(false, true);
			}
		}
	}

	// metas: c.ghita@metas.ro : start: US025b

	private void setDateAllocation(final Map<Integer, MAllocationHdr> id2Hdr)
	{

		for (final MAllocationHdr hdr : id2Hdr.values())
		{
			MAllocationLine[] lines = hdr.getLines(true);
			if (lines == null || (lines != null && lines.length == 0))
				throw new AdempiereException("There is no allocation!");
			if (lines.length > 1)
			{
				throw new AdempiereException("Too many Lines!");
				// TODO: need to split cases
			}
			else if (lines.length == 1)
			{
				MAllocationLine al = lines[0];
				if (al.getC_Invoice_ID() > 0)
				{
					hdr.setDateAcct(al.getC_Invoice().getDateAcct());
					hdr.setDateTrx(al.getC_Invoice().getDateAcct());
					al.setDateTrx(al.getC_Invoice().getDateAcct());
					hdr.saveEx();
					al.saveEx();
				}
			}
		}
	}

	/**
	 * unpost allocation and reset date
	 */
	private void unpostAllocationHdr(final Properties ctx, final Map<Integer, MAllocationHdr> id2Hdr, final String trxName)
	{
		final String whereClause = I_Fact_Acct.COLUMNNAME_AD_Table_ID + "=? AND " + I_Fact_Acct.COLUMNNAME_Record_ID + "=?";
		final int allocationTableId = InterfaceWrapperHelper.getTableId(I_C_AllocationHdr.class);

		for (final MAllocationHdr hdr : id2Hdr.values())
		{
			if (hdr.isPosted())
			{
				//
				final List<MFactAcct> existingFacts =
						new Query(ctx, I_Fact_Acct.Table_Name, whereClause, trxName)
								.setParameters(allocationTableId, hdr.getC_AllocationHdr_ID())
								.setClient_ID()
								.setOnlyActiveRecords(true)
								.list();

				if (!existingFacts.isEmpty())
				{

					MAllocationLine[] lines = hdr.getLines(true);
					if (lines == null || (lines != null && lines.length == 0))
						throw new AdempiereException("There is no allocation!");
					if (lines.length > 1)
					{
						throw new AdempiereException("Too many Lines!");
						// TODO: need to split cases
					}
					else if (lines.length == 1)
					{
						MAllocationLine al = lines[0];
						if (al.getC_Order_ID() > 0 && al.getC_Invoice_ID() < 0)
							MPeriod.testPeriodOpen(ctx, hdr.getDateAcct(), al.getC_Order().getC_DocTypeTarget_ID(), al.getAD_Org_ID());
						if (al.getC_Invoice_ID() > 0)
							MPeriod.testPeriodOpen(ctx, hdr.getDateAcct(), al.getC_Invoice().getC_DocTypeTarget_ID(), al.getAD_Org_ID());
						//
						Services.get(IFactAcctDAO.class).deleteForDocument(hdr);
						hdr.setPosted(false);
						InterfaceWrapperHelper.save(hdr);
					}
				}
				//
			}
		}
	}

	// metas: c.ghita@metas.ro : end: US025b

	@Override
	public void allocationLineAfterProcessIt(final MAllocationLine allocLine, final boolean reverse)
	{
		// final Object result = inv.invokeNext();

		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);

		final Properties ctx = allocLine.getCtx();
		final int orderId = allocLine.getC_Order_ID();
		final String trxName = allocLine.get_TrxName();

		if (orderId <= 0 || !prepayOrderBL.isPrepayOrder(ctx, orderId, trxName))
		{
			logger.debug(allocLine + " does not reference a prepay order. Nothing to do");
			return;
		}

		final I_C_Order order = allocLine.getC_Order();

		// final boolean reverse = (Boolean)inv.getArguments()[0];
		if (reverse)
		{
			assert !allocLine.isActive() : allocLine;

			final BigDecimal allocatedAmt = prepayOrderBL.retrieveAllocatedAmt(ctx, orderId, trxName);

			if (allocatedAmt.compareTo(order.getGrandTotal()) < 0)
			{
				// now this order is not paid anymore (at least not completely)

				// The invoice has already been set to 'unpaid' (testAllocation() in MAllocationLine.processIt())
				Check.assume(allocLine.getInvoice() == null || !allocLine.getInvoice().isPaid(), allocLine + " has no invoice or an unpaid invoice");

				// changing order status to "waiting payment", to make sure that nothing more is delivered
				order.setDocStatus(X_C_Order.DOCSTATUS_WaitingPayment);

				// setting the order's payment-id to 0, so to "detach" the order from the payment
				order.setC_Payment_ID(0);
				InterfaceWrapperHelper.save(order);
			}
		}
		else
		{
			Check.assume(allocLine.isActive(), allocLine + " is active");

			final String docStatus = order.getDocStatus();
			if (X_C_Order.DOCSTATUS_WaitingPayment.equals(docStatus))
			{
				Services.get(IDocActionBL.class).processEx(order, DocAction.ACTION_WaitComplete, null);
				InterfaceWrapperHelper.save(order, trxName);
			}
			else
			{
				logger.debug(order + " has docStatus=" + docStatus + ". Nothing to do");
			}
		}
	}

}
