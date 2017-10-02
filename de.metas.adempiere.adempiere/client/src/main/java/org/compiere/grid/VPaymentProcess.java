package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MCash;
import org.compiere.model.MCashLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.slf4j.Logger;

import de.metas.document.engine.IDocument;
import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import de.metas.logging.MetasfreshLastError;

public final class VPaymentProcess
{
	private final Logger log = LogManager.getLogger(getClass());

	public static BigDecimal getAmount(ProcessingCtx pctx, IVPaymentPanel panel)
	{
		BigDecimal amt = panel.getAmount();
		if (pctx.isNegateAmt)
			amt = amt.negate();
		return amt;
	}

	private final ProcessingCtx pctx;
	private final IPayableDocument doc;
	private final IVPaymentPanel panel;

	public VPaymentProcess(ProcessingCtx pctx, IPayableDocument doc, IVPaymentPanel panel)
	{
		this.pctx = pctx;
		this.doc = doc;
		this.panel = panel;
	}

	public void process()
	{
		boolean validated = panel.validate(true);
		if (!validated)
			throw new AdempiereException(); // should not happen

		final String targetTableName = panel.getTargetTableName();
		if (targetTableName == null)
		{
			// do nothing: don't generate, don't reverse
		}
		else if (I_C_Payment.Table_Name.equals(targetTableName))
		{
			processAsPayment();
		}
		else if (I_C_CashLine.Table_Name.equals(targetTableName))
		{
			processAsCash();
		}
	}

	public BigDecimal getPayAmount()
	{
		BigDecimal amt = panel.getAmount();
		if (pctx.isNegateAmt)
			amt = amt.negate();
		return amt;
	}

	private void processAsPayment()
	{
		if (pctx.oldPayment == null)
		{
			// nothing
		}
		else if (isDraft(pctx.oldPayment))
		{
			pctx.newPayment = pctx.oldPayment;
		}
		else
		{
			reversePayment();
		}

		if (pctx.oldCashLine != null)
		{
			reverseCashLine();
		}

		if (pctx.newPayment == null)
		{
			createPayment();
		}

		updatePayment();
	}

	private void createPayment()
	{
		assert pctx.newPayment == null : "newPayment should be null";

		pctx.newPayment = new MPayment(pctx.ctx, 0, pctx.trxName);
		pctx.newPayment.setAD_Org_ID(doc.getAD_Org_ID());
	}

	private void updatePayment()
	{
		final I_C_Order order = getC_Order();
		final I_C_Invoice invoice = getC_Invoice();

		final BigDecimal payAmount = getPayAmount();
		final int currencyId = panel.getC_Currency_ID();

		log.debug("Payment - " + pctx.newPaymentRule);
		pctx.newPayment.setAmount(currencyId, payAmount);
		pctx.newPayment.setDateTrx(panel.getDate());
		pctx.newPayment.setDateAcct(panel.getDate());

		panel.updatePayment(pctx, pctx.newPayment);

		pctx.newPayment.setC_BPartner_ID(doc.getC_BPartner_ID());
		if (invoice != null)
		{
			pctx.newPayment.setC_Invoice_ID(invoice.getC_Invoice_ID());
		}
		if (order != null)
		{
			pctx.newPayment.setC_Order_ID(order.getC_Order_ID());
			pctx.isParentNeedSave = true;
		}
		pctx.newPayment.saveEx();

		boolean ok = pctx.newPayment.processIt(IDocument.ACTION_Complete);
		pctx.newPayment.saveEx();
		if (!ok)
			throw new AdempiereException(pctx.newPayment.getProcessMsg());
		pctx.info.add("@Created@ " + pctx.newPayment.getSummary());

		if (pctx.isOnline && !pctx.newPayment.isOnlineApproved())
		{
			if (!MPayment.DOCSTATUS_Completed.equals(pctx.newPayment.getDocStatus()))
			{
				throw new AdempiereException("@C_Payment_ID@ " + pctx.newPayment + " - @DocStatus@<>" + MPayment.DOCSTATUS_Completed);
			}
			if (!pctx.newPayment.processOnline())
			{
				throw new AdempiereException("@PaymentNotProcessed@ " + pctx.newPayment.getR_RespMsg());
			}
			pctx.newPayment.saveEx();
			if (!pctx.newPayment.isOnlineApproved())
			{
				throw new AdempiereException("@IsOnlineApproved@: @Y@ - " + pctx.newPayment.getR_RespMsg());
			}

			pctx.info.add("@IsOnlineApproved@: @Y@ - " + pctx.newPayment.getR_RespMsg());
		}
	}

	private boolean isDraft(I_C_Payment payment)
	{
		return !payment.isProcessed() && MPayment.DOCSTATUS_Drafted.equals(payment.getDocStatus());
	}

	private void processAsCash()
	{
		final I_C_Order order = getC_Order();
		final I_C_Invoice invoice = getC_Invoice();
		final BigDecimal payAmount = getPayAmount();

		log.debug("Cash");
		if (invoice == null && order == null)
		{
			log.info("No Invoice!");
			throw new AdempiereException("@CashNotCreated@. @NotFound@ @C_Invoice_ID@ / @C_Order_ID@");
		}

		final int oldC_CashBook_ID;
		if (pctx.oldCashLine != null)
			oldC_CashBook_ID = pctx.oldCashLine.getC_Cash().getC_CashBook_ID();
		else
			oldC_CashBook_ID = -1;

		final int newC_CashBook_ID = panel.getC_CashBook_ID();

		//
		// Cash Line
		if (pctx.oldCashLine == null)
		{
			// nothing
		}
		// Different Date/CashBook
		else if (newC_CashBook_ID != oldC_CashBook_ID
				|| !TimeUtil.isSameDay(pctx.oldCashLine.getStatementDate(), panel.getDate()))
		{
			log.info("Changed CashBook/Date: " + oldC_CashBook_ID + "->" + newC_CashBook_ID);
			reverseCashLine();
		}
		// Changed Amount
		else if (payAmount.compareTo(pctx.oldCashLine.getAmount()) != 0)
		{
			if (pctx.oldCashLine.isProcessed())
			{
				reverseCashLine();
			}
			else
			{
				pctx.newCashLine = pctx.oldCashLine;
			}
		}

		//
		// Payment
		if (pctx.oldPayment != null)
		{
			reversePayment();
		}

		// Create new
		if (pctx.newCashLine == null)
		{
			createCashLine();
		}

		updateCashLine();
	}

	private I_C_Invoice getC_Invoice()
	{
		if (invoice != null)
			return invoice;

		if (I_C_Invoice.Table_Name.equals(doc.get_TableName()))
		{
			invoice = InterfaceWrapperHelper.create(doc, I_C_Invoice.class);
			return invoice;
		}

		I_C_Order order = getC_Order();
		if (order == null)
			return null;

		List<I_C_Invoice> list = new Query(pctx.ctx, I_C_Invoice.Table_Name, I_C_Invoice.COLUMNNAME_C_Order_ID + "=?", pctx.trxName)
				.setParameters(order.getC_Order_ID())
				.setOrderBy(I_C_Invoice.COLUMNNAME_C_Invoice_ID + " DESC")
				.list(I_C_Invoice.class);

		if (list.isEmpty())
		{
			return null;
		}
		else if (list.size() == 1)
		{
			invoice = list.get(0);
			return invoice;
		}
		else
		{
			invoice = list.get(0);
			log.warn("More then one invoice found for order " + order + ". Returning first.");
			return invoice;
		}
	}

	private I_C_Invoice invoice;

	private I_C_Order getC_Order()
	{
		if (order != null)
			return order;

		if (I_C_Order.Table_Name.equals(doc.get_TableName()))
		{
			order = InterfaceWrapperHelper.create(doc, I_C_Order.class);
			return order;
		}
		else if (I_C_Invoice.Table_Name.equals(doc.get_TableName()))
		{
			I_C_Invoice invoice = getC_Invoice();
			order = invoice.getC_Order();
			return order;
		}
		return null;
	}

	private I_C_Order order;

	private void createCashLine()
	{
		assert pctx.newCashLine == null : "newCashLine should be null";

		log.info("New CashBook");
		final MCash cash = MCash.get(pctx.ctx, panel.getC_CashBook_ID(), panel.getDate(), pctx.trxName);

		if (cash == null || cash.getC_Cash_ID() <= 0)
			throw new AdempiereException(MetasfreshLastError.retrieveErrorString("CashNotCreated"));

		pctx.newCashLine = new MCashLine(cash);
	}

	private void updateCashLine()
	{
		final I_C_Order order = getC_Order();
		final I_C_Invoice invoice = getC_Invoice();
		final BigDecimal payAmount = getPayAmount();

		if (invoice != null)
		{
			pctx.newCashLine.setInvoice((MInvoice)invoice); // overrides amount
		}
		if (order != null)
		{
			pctx.newCashLine.setOrder((MOrder)order, pctx.trxName); // overrides amount
			pctx.isParentNeedSave = true;
		}
		pctx.newCashLine.setAmount(payAmount);
		pctx.newCashLine.saveEx();
		log.info("CashCreated");

		if (invoice != null)
		{
			invoice.setC_CashLine_ID(pctx.newCashLine.getC_CashLine_ID());
			InterfaceWrapperHelper.save(invoice);
		}
		if (order != null)
		{
			order.setC_CashLine_ID(pctx.newCashLine.getC_CashLine_ID());
			InterfaceWrapperHelper.save(order);
		}
		log.info("Update Order & Invoice with CashLine");

		pctx.info.add("@Created@ " + pctx.newCashLine.getSummary());
	}

	private void reverseCashLine()
	{
		if (pctx.oldCashLine == null)
			return;

		log.debug("Reversion Old Cash - " + pctx.oldCashLine);
		MCashLine reverseLine = pctx.oldCashLine.createReversal();
		reverseLine.saveEx();

		pctx.info.add("@Reversed@ " + pctx.oldCashLine.getSummary() + " -> " + reverseLine.getSummary());
		pctx.oldCashLine = null;
	}

	private void reversePayment()
	{
		if (pctx.oldPayment == null)
			return;

		// Cancel Payment
		pctx.oldPayment.setDocAction(IDocument.ACTION_Reverse_Correct);
		boolean ok = pctx.oldPayment.processIt(IDocument.ACTION_Reverse_Correct);
		pctx.oldPayment.saveEx();
		if (!ok)
			throw new AdempiereException("@PaymentNotCancelled@ " + pctx.oldPayment.getSummary());

		log.debug("PaymentCancelled " + pctx.oldPayment.getDocumentNo());
		// m_mTab.getTableModel().dataSave(true); // TODO: why we need this?
		pctx.info.add("@Reversed@ " + pctx.oldPayment.getSummary());
	}

	public void updatePayableDocument()
	{
		assert !Util.isEmpty(pctx.newPaymentRule) : "new PaymentRule should not be null";

		// Make sure we did not modified the document from other place
		// e.g. a model validator which changed the order that we are processing right now
		InterfaceWrapperHelper.refresh(doc);

		log.info("Updating payable document");
		//
		// this.m_needSave = pctx.isParentNeedSave;
		doc.setPaymentRule(pctx.newPaymentRule);
		if (pctx.isOnlyRule)
		{
			return;
		}

		// doc.setDateAcct(pctx.newDateAcct);
		if (pctx.newC_PaymentTerm_ID > 0)
			doc.setC_PaymentTerm_ID(pctx.newC_PaymentTerm_ID);
		if (pctx.newPayment != null)
			doc.setC_Payment_ID(pctx.newPayment.getC_Payment_ID());
		if (pctx.newCashLine != null)
			doc.setC_CashLine_ID(pctx.newCashLine.getC_CashLine_ID());
		
		// Save document after changes, so no changes that needs to be saved will be shown to user
		InterfaceWrapperHelper.save(doc);
	}

	public String getInfo()
	{
		StringBuffer sb = new StringBuffer();
		if (pctx == null || pctx.info == null || pctx.info.isEmpty())
			return sb.toString();

		for (String line : pctx.info)
		{
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(Msg.parseTranslation(pctx.ctx, line));
		}
		return sb.toString();
	}
}
