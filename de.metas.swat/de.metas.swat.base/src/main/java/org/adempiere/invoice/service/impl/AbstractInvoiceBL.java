package org.adempiere.invoice.service.impl;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceCreditContext;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.adempiere.util.comparator.ComparatorChain;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_RMA;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Tax;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IInvoiceLineBL;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocCopyHandler;
import de.metas.document.IDocLineCopyHandler;
import de.metas.document.IDocTypeDAO;
import de.metas.document.IDocumentPA;
import de.metas.document.engine.IDocActionBL;
import de.metas.invoice.IMatchInvBL;
import de.metas.invoice.IMatchInvDAO;
import de.metas.logging.LogManager;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;

/**
 * Implements those methods that are DB decoupled
 *
 * @author ts
 *
 */
public abstract class AbstractInvoiceBL implements IInvoiceBL
{
	/** Logger */
	protected final transient Logger log = LogManager.getLogger(getClass());

	/**
	 * See {@link #setHasFixedLineNumber(I_C_InvoiceLine)}.
	 */
	private static final ModelDynAttributeAccessor<I_C_InvoiceLine, Boolean> HAS_FIXED_LINE_NUMBER = new ModelDynAttributeAccessor<>(Boolean.class);

	//
	// System configurations (public for testing)
	public static final String SYSCONFIG_AutoPayZeroAmt = "org.compiere.model.MInvoice.AutoPayZeroAmt";
	public static final String SYSCONFIG_SortILsByShipmentLineOrders = "org.compiere.model.MInvoice.SortILsByShipmentLineOrders";

	//FRESH-488: Payment rule from sys config
	public static final String SYSCONFIG_C_Invoice_PaymentRule = "de.metas.invoice.C_Invoice_PaymentRule";

	@Override
	public final I_C_Invoice creditInvoice(final I_C_Invoice invoice, final IInvoiceCreditContext creditCtx)
	{
		Check.assumeNotNull(invoice, "Param 'invoice' is not null");
		Check.errorIf(isCreditMemo(invoice), "Param 'invoice'={} may not be a credit memo");
		Check.errorIf(invoice.isPaid(), "Param 'invoice'={} may not yet be paid");

		Check.assume(invoice.getGrandTotal().signum() != 0, "GrandTotal!=0 for {}", invoice);

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);

		//
		// 'openAmt is the amount that shall end up in the credit memo's GrandTotal
		final BigDecimal openAmt = Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice,
				false); // creditMemoAdjusted = false

		// 'invoice' is not paid, so the open amount won't be zero
		Check.assume(openAmt.signum() != 0, "OpenAmt != zero for {}", invoice);

		final int targetDocTypeID = getTarget_DocType_ID(ctx, invoice, creditCtx.getC_DocType_ID());
		//
		// create the credit memo as a copy of the original invoice
		final I_C_Invoice creditMemo =
				InterfaceWrapperHelper.create(
						copyFrom(invoice, SystemTime.asTimestamp(), targetDocTypeID, invoice.isSOTrx(),
								false, // counter == false
								creditCtx.isReferenceOriginalOrder(), // setOrderRef == creditCtx.isReferenceOriginalOrder()
								creditCtx.isReferenceInvoice(), // setInvoiceRef == creditCtx.isReferenceInvoice()
								true, // copyLines == true
								new CreditMemoInvoiceCopyHandler(ctx, creditCtx, openAmt, trxName)),
						I_C_Invoice.class);
		return creditMemo;
	}

	private int getTarget_DocType_ID(final Properties ctx, final I_C_Invoice invoice, final int C_DocType_ID)
	{
		if (C_DocType_ID > 0)
		{
			return C_DocType_ID;
		}

		//
		// decide on which C_DocType to use for the credit memo
		final String docBaseType;
		if (invoice.isSOTrx())
		{
			docBaseType = X_C_DocType.DOCBASETYPE_ARCreditMemo;
		}
		else
		{
			docBaseType = X_C_DocType.DOCBASETYPE_APCreditMemo;
		}
		//
		// TODO: What happens when we have multiple DocTypes per DocBaseType and nothing was selected by the user?
		return Services.get(IDocumentPA.class).retriveDocTypeId(ctx, invoice.getAD_Org_ID(), docBaseType);
	}

	public static final IDocCopyHandler<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_InvoiceLine> defaultDocCopyHandler =
			new DefaultDocCopyHandler<>(org.compiere.model.I_C_Invoice.class, org.compiere.model.I_C_InvoiceLine.class);

	@Override
	public final org.compiere.model.I_C_Invoice copyFrom(
			final org.compiere.model.I_C_Invoice from,
			final Timestamp dateDoc,
			final int C_DocTypeTarget_ID,
			final boolean isSOTrx,
			final boolean counter,
			final boolean setOrderRef,
			final boolean setInvoiceRef,
			final boolean copyLines)
	{
		return copyFrom(from, dateDoc, C_DocTypeTarget_ID, isSOTrx, counter, setOrderRef, setInvoiceRef, copyLines, AbstractInvoiceBL.defaultDocCopyHandler);
	}

	private final org.compiere.model.I_C_Invoice copyFrom(
			final org.compiere.model.I_C_Invoice from,
			final Timestamp dateDoc,
			final int C_DocTypeTarget_ID,
			final boolean isSOTrx,
			final boolean counter,
			final boolean setOrderRef,
			final boolean setInvoiceRef,
			final boolean copyLines,
			final IDocCopyHandler<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_InvoiceLine> additionalDocCopyHandler)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(from);
		final String trxName = InterfaceWrapperHelper.getTrxName(from);

		final I_C_Invoice to = InterfaceWrapperHelper.create(ctx, I_C_Invoice.class, trxName);
		to.setAD_Org_ID(from.getAD_Org_ID());

		// copy original values using the specified handler algorithm
		if (additionalDocCopyHandler != null)
		{
			additionalDocCopyHandler.copyPreliminaryValues(from, to);
		}
		Services.get(ICopyHandlerBL.class).copyPreliminaryValues(from, to);

		Check.errorUnless(from.getAD_Client_ID() == to.getAD_Client_ID(), "from.AD_Client_ID={}, to.AD_Client_ID={}", from.getAD_Client_ID(), to.getAD_Client_ID());
		Check.errorUnless(from.getAD_Org_ID() == to.getAD_Org_ID(), "from.AD_Org_ID={}, to.AD_Org_ID={}", from.getAD_Org_ID(), to.getAD_Org_ID());

		to.setDocStatus(DocAction.STATUS_Drafted);		// Draft
		to.setDocAction(DocAction.ACTION_Complete);
		//
		to.setC_DocType_ID(0);
		to.setC_DocTypeTarget_ID(C_DocTypeTarget_ID);
		to.setIsSOTrx(isSOTrx);
		//
		to.setDateInvoiced(dateDoc);
		to.setDateAcct(dateDoc);
		to.setDatePrinted(null);
		to.setIsPrinted(false);
		to.setPOReference(from.getPOReference());  // cg: task 05721
		//
		to.setIsApproved(false);
		to.setC_Payment_ID(0);
		to.setC_CashLine_ID(0);
		to.setIsPaid(false);
		to.setIsInDispute(false);
		//
		// Amounts are updated by trigger when adding lines
		to.setGrandTotal(Env.ZERO);
		to.setTotalLines(Env.ZERO);
		//
		to.setIsTransferred(false);
		to.setPosted(false);
		to.setProcessed(false);
		// [ 1633721 ] Reverse Documents- Processing=Y
		to.setProcessing(false);
		// delete references
		to.setIsSelfService(false);
		if (setOrderRef)
		{
			to.setC_Order_ID(from.getC_Order_ID());
		}
		else
		{
			to.setC_Order_ID(0);
		}

		if (counter)
		{
			to.setRef_Invoice_ID(from.getC_Invoice_ID());
			from.setRef_Invoice_ID(to.getC_Invoice_ID());
		}
		else
		{
			to.setRef_Invoice_ID(0);
		}

		if (counter)
		{
			// Try to find Order link
			if (from.getC_Order_ID() != 0)
			{
				final I_C_Order peer = InterfaceWrapperHelper.create(ctx, from.getC_Order_ID(), I_C_Order.class, trxName);
				if (peer.getRef_Order_ID() != 0)
				{
					to.setC_Order_ID(peer.getRef_Order_ID());
				}
			}
			// Try to find RMA link
			if (from.getM_RMA_ID() != 0)
			{
				final I_M_RMA peer = InterfaceWrapperHelper.create(ctx, from.getM_RMA_ID(), I_M_RMA.class, trxName);
				if (peer.getRef_RMA_ID() > 0)
				{
					to.setM_RMA_ID(peer.getRef_RMA_ID());
				}
			}
		}

		InterfaceWrapperHelper.save(to);

		final IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> additionalDocLineCopyHandler;
		if (additionalDocCopyHandler == null)
		{
			additionalDocLineCopyHandler = null;
		}
		else
		{
			additionalDocLineCopyHandler = additionalDocCopyHandler.getDocLineCopyHandler();
		}

		// Lines
		if (copyLines && copyLinesFrom(from, to, counter, setOrderRef, setInvoiceRef, additionalDocLineCopyHandler) == 0)
		{
			throw new IllegalStateException("Could not create Invoice Lines");
		}

		// copyValues override of the handler & save
		InterfaceWrapperHelper.refresh(to);

		if (additionalDocCopyHandler != null)
		{
			additionalDocCopyHandler.copyValues(from, to);
		}
		Services.get(ICopyHandlerBL.class).copyValues(from, to);
		InterfaceWrapperHelper.save(to);

		return to;
	}

	@Override
	public final void writeOffInvoice(final org.compiere.model.I_C_Invoice invoice, final BigDecimal openAmt, final String description)
	{
		if (openAmt.signum() == 0)
		{
			return;
		}

		final BigDecimal openAmtAbs;
		if (!invoice.isSOTrx())
		{
			// API
			openAmtAbs = openAmt.negate();
		}
		else
		{
			// ARI
			openAmtAbs = openAmt;
		}

		// @formatter:off
		Services.get(IAllocationBL.class).newBuilder(InterfaceWrapperHelper.getContextAware(invoice))
			.setAD_Org_ID(invoice.getAD_Org_ID())
			.setC_Currency_ID(invoice.getC_Currency_ID())
			.setDateAcct(invoice.getDateAcct())
			.setDateTrx(invoice.getDateInvoiced())
			.addLine()
				.setAD_Org_ID(invoice.getAD_Org_ID())
				.setC_BPartner_ID(invoice.getC_BPartner_ID())
				.setC_Invoice_ID(invoice.getC_Invoice_ID())
				.setAmount(Env.ZERO)
				.setWriteOffAmt(openAmtAbs)
			.lineDone()
			.create(true);
		// @formatter:on
	}

	@Override
	public final boolean testAllocation(final org.compiere.model.I_C_Invoice invoice, boolean ignoreProcessed)
	{
		boolean change = false;

		if (invoice.isProcessed() || ignoreProcessed)
		{
			BigDecimal alloc = Services.get(IAllocationDAO.class).retrieveAllocatedAmt(invoice); // absolute
			final boolean hasAllocations = alloc != null; // metas: tsa: 01955
			if (alloc == null)
			{
				alloc = Env.ZERO;
			}
			BigDecimal total = invoice.getGrandTotal();
			// metas: tsa: begin: 01955:
			// If is an zero invoice, it has no allocations and the AutoPayZeroAmt is not set
			// then don't touch the invoice
			if (total.signum() == 0 && !hasAllocations
					&& !Services.get(ISysConfigBL.class).getBooleanValue(AbstractInvoiceBL.SYSCONFIG_AutoPayZeroAmt, true, invoice.getAD_Client_ID()))
			{
				// don't touch the IsPaid flag, return not changed
				return false;
			}
			// metas: tsa: end: 01955
			if (!invoice.isSOTrx())
			{
				total = total.negate();
			}
			if (isCreditMemo(invoice))
			{
				total = total.negate();
			}

			final boolean test = total.compareTo(alloc) == 0;
			change = test != invoice.isPaid();
			if (change)
			{
				invoice.setIsPaid(test);
			}
			log.debug("Paid=" + test + " (" + alloc + "=" + total + ")");
		}

		return change;
	}	// testAllocation

	/**
	 * Gets Invoice Grand Total (absolute value).
	 *
	 * @param invoice
	 * @return
	 */
	public final BigDecimal getGrandTotalAbs(final org.compiere.model.I_C_Invoice invoice)
	{
		BigDecimal grandTotal = invoice.getGrandTotal();
		if (grandTotal.signum() == 0)
		{
			return grandTotal;
		}

		// AP/AR adjustment
		if (!invoice.isSOTrx())
		{
			grandTotal = grandTotal.negate();
		}

		// CM adjustment
		if (isCreditMemo(invoice))
		{
			grandTotal = grandTotal.negate();
		}

		return grandTotal;
	}

	@Override
	public final I_C_Invoice createInvoiceFromOrder(final org.compiere.model.I_C_Order order,
			final int C_DocTypeTarget_ID,
			final Timestamp dateInvoiced,
			final Timestamp dateAcct)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, order);
		invoice.setAD_Org_ID(order.getAD_Org_ID());
		setFromOrder(invoice, order);	// set base settings

		//
		int docTypeId = C_DocTypeTarget_ID;
		if (docTypeId <= 0)
		{
			final I_C_DocType odt = order.getC_DocType();
			if (odt != null)
			{
				docTypeId = odt.getC_DocTypeInvoice_ID();
				if (docTypeId <= 0)
				{
					throw new AdempiereException("@NotFound@ @C_DocTypeInvoice_ID@ - @C_DocType_ID@:" + odt.getName());
				}
			}
		}

		invoice.setC_DocTypeTarget_ID(docTypeId);
		if (dateInvoiced != null)
		{
			invoice.setDateInvoiced(dateInvoiced);
		}

		if (dateAcct != null)
		{
			invoice.setDateAcct(dateAcct); // task 08437
		}
		else
		{
			invoice.setDateAcct(invoice.getDateInvoiced());
		}

		//
		invoice.setSalesRep_ID(order.getSalesRep_ID());
		//
		invoice.setC_BPartner_ID(order.getBill_BPartner_ID());
		invoice.setC_BPartner_Location_ID(order.getBill_Location_ID());
		invoice.setAD_User_ID(order.getBill_User_ID());

		return invoice;
	}

	@Override
	public final I_C_InvoiceLine createLine(final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, invoice);
		invoiceLine.setC_Invoice(invoice);

		return invoiceLine;
	}

	@Override
	public final void setFromOrder(final org.compiere.model.I_C_Invoice invoice, final org.compiere.model.I_C_Order order)
	{
		if (order == null)
		{
			return;
		}

		invoice.setC_Order_ID(order.getC_Order_ID());
		invoice.setIsSOTrx(order.isSOTrx());
		invoice.setIsDiscountPrinted(order.isDiscountPrinted());
		invoice.setIsSelfService(order.isSelfService());
		invoice.setSendEMail(order.isSendEMail());
		//
		invoice.setM_PriceList_ID(order.getM_PriceList_ID());
		invoice.setIsTaxIncluded(order.isTaxIncluded());
		invoice.setC_Currency_ID(order.getC_Currency_ID());
		invoice.setC_ConversionType_ID(order.getC_ConversionType_ID());
		//
		invoice.setPaymentRule(order.getPaymentRule());
		invoice.setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
		invoice.setPOReference(order.getPOReference());
		invoice.setDescription(order.getDescription());
		invoice.setDateOrdered(order.getDateOrdered());
		//
		invoice.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		invoice.setC_Project_ID(order.getC_Project_ID());
		invoice.setC_Campaign_ID(order.getC_Campaign_ID());
		invoice.setC_Activity_ID(order.getC_Activity_ID());
		invoice.setUser1_ID(order.getUser1_ID());
		invoice.setUser2_ID(order.getUser2_ID());

		// metas
		final I_C_Invoice invoice2 = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);
		final I_C_Order order2 = InterfaceWrapperHelper.create(order, I_C_Order.class);

		invoice2.setIncoterm(order2.getIncoterm());
		invoice2.setIncotermLocation(order2.getIncotermLocation());
		invoice2.setDescriptionBottom(order2.getDescriptionBottom());

		invoice2.setBPartnerAddress(order2.getBillToAddress());
		invoice2.setIsUseBPartnerAddress(order2.isUseBillToAddress());
		// metas end

		// metas (2009-0027-G5)
		invoice.setC_Payment_ID(order.getC_Payment_ID());
	}

	@Override
	public final boolean setC_DocTypeTarget(final I_C_Invoice invoice, final String docBaseType)
	{
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final int adClientId = invoice.getAD_Client_ID();
		final int adOrgId = invoice.getAD_Org_ID();
		final int C_DocType_ID = docTypeDAO.getDocTypeIdOrNull(ctx, docBaseType, adClientId, adOrgId, trxName);
		if (C_DocType_ID <= 0)
		{
			log.error("Not found for AD_Client_ID=" + adClientId + " - " + docBaseType);
			return false;
		}
		else
		{
			log.debug(docBaseType);
			invoice.setC_DocTypeTarget_ID(C_DocType_ID);
			final boolean isSOTrx = X_C_DocType.DOCBASETYPE_ARInvoice.equals(docBaseType)
					|| X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(docBaseType);
			invoice.setIsSOTrx(isSOTrx);
			return true;
		}
	}

	@Override
	public final void renumberLines(final I_C_Invoice invoice, final int step)
	{

		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(invoice, InterfaceWrapperHelper.getTrxName(invoice));
		renumberLines(lines, step);
	}

	@Override
	public final void renumberLines(final List<I_C_InvoiceLine> lines, final int step)
	{
		// collect those line numbers that are already "taken"
		final Set<Integer> fixedNumbers = new HashSet<>();
		for (final I_C_InvoiceLine line : lines)
		{
			if (isHasFixedLineNumber(line))
			{
				fixedNumbers.add(line.getLine());
			}
		}

		// 02139: Sort InvoiceLines before renumbering.
		final List<I_C_InvoiceLine> linesToReorder = new ArrayList<I_C_InvoiceLine>(lines);
		sortLines(linesToReorder);

		int number = step;
		int lineIdx = 0;

		while (lineIdx < linesToReorder.size())
		{
			final I_C_InvoiceLine invoiceLine = linesToReorder.get(lineIdx);

			if (invoiceLine.getLine() % step == 0)
			{
				if (!isHasFixedLineNumber(invoiceLine) && !fixedNumbers.contains(number))
				{
					// only give this line a (new) number, if its current number is not fixed
					// and only give it a number that is not yet taken as some line's fixed number
					invoiceLine.setLine(number);
					lineIdx++;
				}
				else if (isHasFixedLineNumber(invoiceLine))
				{
					// this line already has a number
					lineIdx++;
				}
				else
				{
					// this line has *no* fixed number and the current 'number' value is one of the fixed ones, so just increase 'number', but not 'lineIdx'.
					// I.e. try this invoice again, with the next 'number' value
				}

				if (!isHasFixedLineNumber(invoiceLine) || fixedNumbers.contains(number))
				{
					// this number value was just used, or is already used as a fixed-number by some line => one step forward
					number += step;
				}
			}
			else
			{
				if (invoiceLine.getLine() % 2 == 0)
				{
					if (!isHasFixedLineNumber(invoiceLine) && !fixedNumbers.contains(number - 2))
					{
						invoiceLine.setLine(number - 2);
					}
				}
				else
				{
					if (!isHasFixedLineNumber(invoiceLine) && !fixedNumbers.contains(number - 1))
					{
						invoiceLine.setLine(number - 1);
					}
				}
				lineIdx++;
			}
			InterfaceWrapperHelper.save(invoiceLine);
		}
	} // renumberLinesWithoutComment

	@Override
	public void setHasFixedLineNumber(final I_C_InvoiceLine line, final boolean value)
	{
		HAS_FIXED_LINE_NUMBER.setValue(line, value);
	}

	private boolean isHasFixedLineNumber(final I_C_InvoiceLine line)
	{
		return Boolean.TRUE.equals(HAS_FIXED_LINE_NUMBER.getValue(line));
	}

	/**
	 * Orders the InvoiceLines by their InOut. For each InOut, the FreightCostLine comes last. Lines whose M_InOut_ID equals 0, will get the M_InOut_ID of the next Line whose InOut_ID is not 0.
	 *
	 * @param lines - The unsorted array of InvoiceLines - is sorted by this method
	 */
	@VisibleForTesting
	/* package */final void sortLines(final List<I_C_InvoiceLine> lines)
	{
		final Comparator<I_C_InvoiceLine> cmp = getInvoiceLineComparator(lines);

		Collections.sort(lines, cmp);
	}

	private final Comparator<I_C_InvoiceLine> getInvoiceLineComparator(final List<I_C_InvoiceLine> lines)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final ComparatorChain<I_C_InvoiceLine> ilComparator = new ComparatorChain<>();

		//
		// Use order line comparator if configured
		final boolean sortILsByShipmentLineOrders = sysConfigBL.getBooleanValue(SYSCONFIG_SortILsByShipmentLineOrders, false); // fallback false (if not configured)
		if (sortILsByShipmentLineOrders)
		{
			final Comparator<I_C_InvoiceLine> orderLineComparator = getShipmentLineOrderComparator(lines);
			ilComparator.addComparator(orderLineComparator);
		}

		//
		// Default comparator (original one, covered by tests)
		//
		// Note: add this at the end (first comparators are first served)
		{
			final Comparator<I_C_InvoiceLine> inOutLineComparator = getDefaultInvoiceLineComparator(lines);
			ilComparator.addComparator(inOutLineComparator);
		}

		return ilComparator;
	}

	/**
	 * Set M_InOut_ID for comment lines: The Invoice Lines are initially ordered by their M_InOut_ID, so that there is a "Block" of invoice lines for each InOut. There are 2 comment lines in front of
	 * every block, which are supposed to increase the clear arrangement in the Invoice window. None of these lines are attached to a M_InOutLine which means that the Virtual Column M_InOut_ID is
	 * NULL. This causes Problems when trying to order the lines, so first we need to allocate an InOut_ID to each InvoiceLine. To do this a hash map is used.
	 *
	 * @param lines
	 * @return comparator
	 */
	private final Comparator<I_C_InvoiceLine> getDefaultInvoiceLineComparator(final List<I_C_InvoiceLine> lines)
	{
		final HashMap<Integer, Integer> invoiceLineId2inOutId = new HashMap<Integer, Integer>();

		for (int i = 0; i < lines.size(); i++)
		{
			final I_C_InvoiceLine il = lines.get(i);

			final int currentInOutID = il.getM_InOutLine_ID() > 0
					? il.getM_InOutLine().getM_InOut_ID()
					: 0;

			final int currentLineID = il.getC_InvoiceLine_ID();

			// if this is not a comment line:
			if (currentInOutID != 0)
			{
				invoiceLineId2inOutId.put(currentLineID, currentInOutID);
				continue;
			}

			int valueIdToUse = -1;

			// If this is a comment line: Get next line with a valid ID
			for (int j = 1; i + j < lines.size(); j++)
			{
				final I_C_InvoiceLine nextLine = lines.get(i + j);

				final int nextID = nextLine.getM_InOutLine_ID() > 0
						? nextLine.getM_InOutLine().getM_InOut_ID()
						: 0;

				if (nextID != 0) // If this is a valid ID, put it into the Map.
				{
					valueIdToUse = nextID;
					break;
				}
			}

			invoiceLineId2inOutId.put(currentLineID, valueIdToUse);
		}

		Check.assume(invoiceLineId2inOutId.size() == lines.size(), "Every line's id has been added to map '" + invoiceLineId2inOutId + "'");

		// create Comparator
		final Comparator<I_C_InvoiceLine> cmp = new Comparator<I_C_InvoiceLine>()
		{
			@Override
			public int compare(final I_C_InvoiceLine line1, final I_C_InvoiceLine line2)
			{
				// InOut_ID
				final int InOut_ID1 = invoiceLineId2inOutId.get(line1.getC_InvoiceLine_ID());
				final int InOut_ID2 = invoiceLineId2inOutId.get(line2.getC_InvoiceLine_ID());

				if (InOut_ID1 > InOut_ID2)
				{
					return 1;
				}
				if (InOut_ID1 < InOut_ID2)
				{
					return -1;
				}

				// Freight cost
				final boolean fc1 = line1.isFreightCostLine();
				final boolean fc2 = line2.isFreightCostLine();

				if (fc1 && !fc2)
				{
					return 1;
				}
				if (!fc1 && fc2)
				{
					return -1;
				}

				// LineNo
				final int line1No = line1.getLine();
				final int line2No = line2.getLine();

				if (line1No > line2No)
				{
					return 1;
				}
				if (line1No < line2No)
				{
					return -1;
				}

				return 0;
			}
		};
		return cmp;
	}

	private final Comparator<I_C_InvoiceLine> getShipmentLineOrderComparator(final List<I_C_InvoiceLine> lines)
	{
		final Comparator<I_C_InvoiceLine> comparator = new Comparator<I_C_InvoiceLine>()
		{
			@Override
			public int compare(final I_C_InvoiceLine line1, final I_C_InvoiceLine line2)
			{

				final I_M_InOutLine iol1 = line1.getM_InOutLine();
				final I_M_InOutLine iol2 = line2.getM_InOutLine();
				if (Util.same(line1.getM_InOutLine_ID(), line2.getM_InOutLine_ID()))
				{
					return line1.getLine() - line2.getLine(); // keep IL order
				}
				else if (line1.getM_InOutLine_ID() <= 0 || iol1 == null)
				{
					return 1; // second line not null, put it first
				}
				else if (line2.getM_InOutLine_ID() <= 0 || iol2 == null)
				{
					return -1; // first line not null, put it first
				}

				final I_C_OrderLine ol1 = iol1.getC_OrderLine();
				final I_C_OrderLine ol2 = iol2.getC_OrderLine();
				if (Util.same(ol1, ol2))
				{
					return iol1.getLine() - iol2.getLine(); // keep IOL order
				}
				else if (ol1 == null)
				{
					return 1; // second line not null, put it first
				}
				else if (ol2 == null)
				{
					return -1; // first line not null, put it first
				}

				final I_C_Order o1 = InterfaceWrapperHelper.create(ol1.getC_Order(), I_C_Order.class);
				final I_C_Order o2 = InterfaceWrapperHelper.create(ol2.getC_Order(), I_C_Order.class);
				if (o1.getC_Order_ID() != o2.getC_Order_ID())
				{
					return o1.getC_Order_ID() - o2.getC_Order_ID(); // first orders go first
				}

				return ol1.getLine() - ol2.getLine(); // keep OL order
			}
		};
		return comparator;
	}

	@Override
	public final void setProductAndUOM(final I_C_InvoiceLine invoiceLine, final int productId)
	{
		if (productId > 0)
		{
			invoiceLine.setM_Product_ID(productId);
			invoiceLine.setC_UOM_ID(invoiceLine.getM_Product().getC_UOM_ID());
		}
		else
		{
			invoiceLine.setM_Product_ID(0);
			invoiceLine.setC_UOM_ID(0);

		}
		invoiceLine.setM_AttributeSetInstance_ID(0);
	}

	@Override
	public final void setQtys(final I_C_InvoiceLine invoiceLine, final BigDecimal qtyInvoiced)
	{
		// for now we are lenient, because i'm not sure because strict doesn't break stuff
		// Check.assume(invoiceLine.getM_Product_ID() > 0, "invoiceLine {} has M_Product_ID > 0", invoiceLine);
		// Check.assume(invoiceLine.getC_UOM_ID() > 0, "invoiceLine {} has C_UOM_ID > 0", invoiceLine);
		// Check.assume(invoiceLine.getPrice_UOM_ID() > 0, "invoiceLine {} has Price_UOM_ID > 0", invoiceLine);

		invoiceLine.setQtyInvoiced(qtyInvoiced);

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLine);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		boolean fallback = false;
		if (invoiceLine.getM_Product_ID() <= 0)
		{
			fallback = true;
		}

		final I_M_Product product = invoiceLine.getM_Product();
		if (product.getC_UOM_ID() < 0)
		{
			fallback = true;
		}
		if (fallback)
		{
			invoiceLine.setQtyEntered(qtyInvoiced);
			invoiceLine.setQtyInvoicedInPriceUOM(qtyInvoiced);
			return;
		}

		if (invoiceLine.getC_UOM_ID() <= 0)
		{
			invoiceLine.setQtyEntered(qtyInvoiced);
		}
		else
		{
			final BigDecimal qtyEntered = uomConversionBL.convertFromProductUOM(ctx, product, invoiceLine.getC_UOM(), qtyInvoiced);
			invoiceLine.setQtyEntered(qtyEntered);
		}

		final BigDecimal qtyInvoicedInPriceUOM = uomConversionBL.convertFromProductUOM(ctx, product, invoiceLine.getPrice_UOM(), qtyInvoiced);
		invoiceLine.setQtyInvoicedInPriceUOM(qtyInvoicedInPriceUOM);
	}

	@Override
	public final void setLineNetAmt(final I_C_InvoiceLine invoiceLine)
	{
		// services
		final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
		final ITaxBL taxBL = Services.get(ITaxBL.class);

		// // Make sure QtyInvoicedInPriceUOM is up2date
		invoiceLineBL.setQtyInvoicedInPriceUOM(invoiceLine);

		// Calculations & Rounding
		BigDecimal lineNetAmt = invoiceLine.getPriceActual().multiply(invoiceLine.getQtyInvoicedInPriceUOM());

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoiceLine);
		final I_C_Tax invoiceTax = InterfaceWrapperHelper.create(ctx, invoiceLine.getC_Tax_ID(), I_C_Tax.class, trxName);
		final boolean isTaxIncluded = isTaxIncluded(invoiceLine);
		final int taxPrecision = getPrecision(invoiceLine);

		// ts: note: our taxes are always on document, so currently the following if-block doesn't apply to us
		final boolean documentLevel = invoiceTax != null // guard against NPE
				&& invoiceTax.isDocumentLevel();

		// juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		// http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded && !documentLevel)
		{
			BigDecimal taxStdAmt = Env.ZERO, taxThisAmt = Env.ZERO;

			I_C_Tax stdTax = null;

			if (invoiceLine.getM_Product() != null)
			{
				if (invoiceLine.getC_Charge() != null)	// Charge
				{
					stdTax = createTax(ctx, taxDAO.getDefaultTax(invoiceLine.getC_Charge().getC_TaxCategory()).getC_Tax_ID(), trxName);
				}

			}
			else
			// Product
			{
				// FIXME metas 05129 need proper concept (link between M_Product and C_TaxCategory_ID was removed!!!!!)
				throw new AdempiereException("Unsupported tax calculation when tax is included, but it's not on document level");
				// stdTax = createTax(ctx, taxDAO.getDefaultTax(invoiceLine.getM_Product().getC_TaxCategory()).getC_Tax_ID(), trxName);
			}
			if (stdTax != null)
			{
				log.debug("stdTax rate is " + stdTax.getRate());
				log.debug("invoiceTax rate is " + invoiceTax.getRate());

				taxThisAmt = taxThisAmt.add(taxBL.calculateTax(invoiceTax, lineNetAmt, isTaxIncluded, taxPrecision));
				taxStdAmt = taxThisAmt.add(taxBL.calculateTax(stdTax, lineNetAmt, isTaxIncluded, taxPrecision));

				lineNetAmt = lineNetAmt.subtract(taxStdAmt).add(taxThisAmt);

				log.debug("Price List includes Tax and Tax Changed on Invoice Line: New Tax Amt: "
						+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + lineNetAmt);
			}
		}

		if (lineNetAmt.scale() > taxPrecision)
		{
			lineNetAmt = lineNetAmt.setScale(taxPrecision, BigDecimal.ROUND_HALF_UP);
		}

		invoiceLine.setLineNetAmt(lineNetAmt);

	}// setLineNetAmt

	@Override
	public final void setTaxAmt(final I_C_InvoiceLine invoiceLine)
	{
		final int taxID = invoiceLine.getC_Tax_ID();
		if (taxID <= 0)
		{
			return;
		}

		// setLineNetAmt();
		final I_C_Tax tax = invoiceLine.getC_Tax();
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();
		if (tax.isDocumentLevel() && invoice.isSOTrx())
		{
			return;
		}
		//
		final boolean isTaxIncluded = isTaxIncluded(invoiceLine);
		final BigDecimal lineNetAmt = invoiceLine.getLineNetAmt();
		final int taxPrecision = getPrecision(invoiceLine);
		final BigDecimal TaxAmt = Services.get(ITaxBL.class).calculateTax(tax, lineNetAmt, isTaxIncluded, taxPrecision);
		if (isTaxIncluded)
		{
			invoiceLine.setLineTotalAmt(lineNetAmt);
		}
		else
		{
			invoiceLine.setLineTotalAmt(lineNetAmt.add(TaxAmt));
		}
		invoiceLine.setTaxAmt(TaxAmt);
	}	// setTaxAmt

	private I_C_Tax createTax(final Properties ctx, final int taxId, final String trxName)
	{
		final I_C_Tax tax = InterfaceWrapperHelper.create(ctx, taxId, I_C_Tax.class, trxName);

		if (taxId == 0)
		{
			tax.setIsDefault(false);
			tax.setIsDocumentLevel(true);
			tax.setIsSummary(false);
			tax.setIsTaxExempt(false);
			tax.setRate(Env.ZERO);
			tax.setRequiresTaxCertificate(false);
			tax.setSOPOType(X_C_Tax.SOPOTYPE_Both);
			tax.setValidFrom(TimeUtil.getDay(1990, 1, 1));
			tax.setIsSalesTax(false);
		}

		return tax;
	}

	/**
	 * Calls {@link #isTaxIncluded(I_C_Invoice, I_C_Tax)} for the given <code>invoiceLine</code>'s <code>C_Invoice</code> and <code>C_Tax</code>.
	 *
	 * @param invoiceLine
	 * @return
	 */
	@Override
	public final boolean isTaxIncluded(final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		Check.assumeNotNull(invoiceLine, "invoiceLine not null");

		final I_C_Tax tax = invoiceLine.getC_Tax();
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();

		return isTaxIncluded(invoice, tax);
	}

	@Override
	public final boolean isTaxIncluded(final org.compiere.model.I_C_Invoice invoice, final I_C_Tax tax)
	{
		if (tax != null && tax.isWholeTax())
		{
			return true;
		}

		return invoice.isTaxIncluded(); // 08486: use the invoice's flag, not whatever the PL sais right now
	}

	@Override
	public final I_C_DocType getC_DocType(final org.compiere.model.I_C_Invoice invoice)
	{
		if (invoice.getC_DocType_ID() > 0)
		{
			return invoice.getC_DocType();
		}
		else if (invoice.getC_DocTypeTarget_ID() > 0)
		{
			return invoice.getC_DocTypeTarget();
		}

		return null;
	}

	@Override
	public final boolean isCreditMemo(final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_DocType docType = getC_DocType(invoice);
		final String docBaseType = docType.getDocBaseType();
		return isCreditMemo(docBaseType);
	}

	@Override
	public final boolean isCreditMemo(final String docBaseType)
	{
		return X_C_DocType.DOCBASETYPE_APCreditMemo.equals(docBaseType)
				|| X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(docBaseType);
	}

	@Override
	public final boolean isARCreditMemo(final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_DocType docType = getC_DocType(invoice);
		return X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(docType.getDocBaseType());
	}

	@Override
	public final boolean isAdjustmentCharge(final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_DocType docType = getC_DocType(invoice);

		return isAdjustmentCharge(docType);
	}

	@Override
	public final boolean isAdjustmentCharge(final I_C_DocType docType)
	{
		final String docBaseType = docType.getDocBaseType();

		// only ARI base type
		if (!X_C_DocType.DOCBASETYPE_ARInvoice.equals(docBaseType))
		{
			return false;
		}

		final String docSubType = docType.getDocSubType();

		// Must have a subtype
		if (docSubType == null)
		{
			return false;
		}

		// must be one of Mengendifferenz or Preisdifferenz
		if (X_C_DocType.DOCSUBTYPE_NB_Mengendifferenz.compareTo(docSubType) != 0
				&& X_C_DocType.DOCSUBTYPE_NB_Preisdifferenz.compareTo(docSubType) != 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isReversal(org.compiere.model.I_C_Invoice invoice)
	{
		if (invoice == null)
		{
			return false;
		}
		if (invoice.getReversal_ID() <= 0)
		{
			return false;
		}
		// the reversal is always younger than the original document
		return invoice.getC_Invoice_ID() > invoice.getReversal_ID();
	}

	@Override
	public final boolean isComplete(final org.compiere.model.I_C_Invoice invoice)
	{
		final String docStatus = invoice.getDocStatus();
		return Services.get(IDocActionBL.class).isStatusCompletedOrClosedOrReversed(docStatus);
	}

	@Override
	public final int getPrecision(final org.compiere.model.I_C_Invoice invoice)
	{
		return invoice.getC_Currency().getStdPrecision();
	}

	@Override
	public final int getPrecision(final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();
		return getPrecision(invoice);
	}

	@Override
	public final de.metas.adempiere.model.I_C_Invoice adjustmentCharge(final org.compiere.model.I_C_Invoice invoice, final String docSubType)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String docbasetype = X_C_DocType.DOCBASETYPE_ARInvoice;
		final int targetDocTypeID = Services.get(IDocumentPA.class).retriveDocTypeIdForDocSubtype(ctx, docbasetype, docSubType);
		final I_C_Invoice adjustmentCharge =
				InterfaceWrapperHelper.create(
						copyFrom(invoice, SystemTime.asTimestamp(), targetDocTypeID, invoice.isSOTrx(),
								false, // counter == false
								true, // setOrderRef == true
								true, // setInvoiceRef == true
								true), // copyLines == true
						I_C_Invoice.class);

		adjustmentCharge.setDescription("Nachbelastung zu Rechnung " + invoice.getDocumentNo() + ", Order-Referenz " + invoice.getPOReference() + "\n\nUrsprünglicher Rechnungstext:\n"
				+ invoice.getDescription());

		adjustmentCharge.setRef_AdjustmentCharge_ID(invoice.getC_Invoice_ID());
		InterfaceWrapperHelper.save(adjustmentCharge);
		final I_C_Invoice i = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);
		i.setRef_AdjustmentCharge_ID(adjustmentCharge.getC_Invoice_ID());
		InterfaceWrapperHelper.save(invoice);

		return adjustmentCharge;
	}

	@Override
	public final void updateInvoiceLineIsReadOnlyFlags(final I_C_Invoice invoice, final I_C_InvoiceLine... invoiceLines)
	{
		Check.assumeNotNull(invoice, "Param 'invoice' is not null");

		final boolean saveLines;
		final List<I_C_InvoiceLine> linesToUpdate;
		if (Check.isEmpty(invoiceLines))
		{
			linesToUpdate = Services.get(IInvoiceDAO.class).retrieveLines(invoice);
			saveLines = true;
		}
		else
		{
			linesToUpdate = Arrays.asList(invoiceLines);
			saveLines = false;
		}

		final String docSubType;
		if (invoice.getC_DocTypeTarget_ID() > 0)
		{
			docSubType = invoice.getC_DocTypeTarget().getDocSubType();
		}
		else
		{
			docSubType = null;
		}
		final boolean qtyReadOnly;
		final boolean priceReadOnly;
		final boolean orderLineReadOnly; // task 09182

		if (I_C_Invoice.DOC_SUBTYPE_ARI_AQ.equals(docSubType) || I_C_Invoice.DOC_SUBTYPE_ARC_CQ.equals(docSubType))
		{
			priceReadOnly = true;
			qtyReadOnly = false;
			orderLineReadOnly = true;
		}
		// if DocType is "Nachbelastung - Preisdifferenz" set Qty readonly
		else if (I_C_Invoice.DOC_SUBTYPE_ARI_AP.equals(docSubType) || I_C_Invoice.DOC_SUBTYPE_ARC_CR.equals(docSubType))
		{
			qtyReadOnly = true;
			priceReadOnly = false;
			orderLineReadOnly = true;
		}
		else if (I_C_Invoice.DOC_SUBTYPE_ARC_CS.equals(docSubType))
		{
			qtyReadOnly = false;
			priceReadOnly = false;
			orderLineReadOnly = false; // task 09182: the user needs to specify (on invoice line level!) the order lines referenced by the invoice
		}
		// for other doc types, we let both fields be read-write, and the orderline be read-only/not shown as usual
		else
		{
			qtyReadOnly = false;
			priceReadOnly = false;
			orderLineReadOnly = true;
		}

		for (final I_C_InvoiceLine lineToUpdate : linesToUpdate)
		{
			lineToUpdate.setIsPriceReadOnly(priceReadOnly);
			lineToUpdate.setIsQtyReadOnly(qtyReadOnly);
			lineToUpdate.setIsOrderLineReadOnly(orderLineReadOnly);

			if (saveLines)
			{
				InterfaceWrapperHelper.save(lineToUpdate);
			}
		}
	}

	@Override
	public final void registerCopyHandler(
			final IQueryFilter<Pair<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_Invoice>> filter,
			final IDocCopyHandler<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_InvoiceLine> copyhandler)
	{
		Services.get(ICopyHandlerBL.class).registerCopyHandler(org.compiere.model.I_C_Invoice.class, filter, copyhandler);
	}

	@Override
	public final void registerLineCopyHandler(
			final IQueryFilter<Pair<org.compiere.model.I_C_InvoiceLine, org.compiere.model.I_C_InvoiceLine>> filter,
			final IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> copyhandler)
	{
		Services.get(ICopyHandlerBL.class).registerCopyHandler(org.compiere.model.I_C_InvoiceLine.class, filter, copyhandler);

	}

	@Override
	public final int getTaxCategory(final I_C_InvoiceLine invoiceLine)
	{
		// In case we have a charge, use the tax category from charge
		if (invoiceLine.getC_Charge_ID() > 0)
		{
			return invoiceLine.getC_Charge().getC_TaxCategory_ID();
		}

		final IPricingContext pricingCtx = Services.get(IInvoiceLineBL.class).createPricingContext(invoiceLine);
		final IPricingResult pricingResult = Services.get(IPricingBL.class).calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			return -1;
		}
		return pricingResult.getC_TaxCategory_ID();
	}

	@Override
	public final void handleReversalForInvoice(final org.compiere.model.I_C_Invoice invoice)
	{
		final int reversalInvoiceId = invoice.getReversal_ID();
		Check.assume(reversalInvoiceId > invoice.getC_Invoice_ID(), "Invoice {} shall be the original invoice and not it's reversal", invoice);
		final org.compiere.model.I_C_Invoice reversalInvoice = invoice.getReversal();

		// services
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final IMatchInvBL matchInvBL = Services.get(IMatchInvBL.class);
		final IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		for (final I_C_InvoiceLine il : invoiceDAO.retrieveLines(invoice))
		{
			// task 08627: unlink possible inOutLines because the inOut might now be reactivated and they might be deleted.
			// Unlinking them now is more performant than selecting an unlinking them when the inOutLine is actually deleted.
			il.setM_InOutLine(null);
			InterfaceWrapperHelper.save(il);

			//
			// Retrieve the reversal invoice line
			final I_C_InvoiceLine reversalLine = invoiceDAO.retrieveReversalLine(il, reversalInvoiceId);

			// 08809
			// Also set the Attribute Set Instance in the reversal line
			attributeSetInstanceBL.cloneASI(reversalLine, il);
			InterfaceWrapperHelper.save(reversalLine);

			//
			// Create M_MatchInv reversal records, linked to reversal invoice line and original inout line.
			final List<I_M_MatchInv> matchInvs = matchInvDAO.retrieveForInvoiceLine(il);
			for (final I_M_MatchInv matchInv : matchInvs)
			{
				final I_M_InOutLine inoutLine = matchInv.getM_InOutLine();

				matchInvBL.createMatchInvBuilder()
						.setContext(reversalLine)
						.setC_InvoiceLine(reversalLine)
						.setM_InOutLine(inoutLine)
						.setDateTrx(reversalInvoice.getDateInvoiced())
						.setQtyToMatchExact(matchInv.getQty().negate())
						.build();
			}
		}
	}

	@Override
	public final void allocateCreditMemo(final I_C_Invoice invoice,
			final I_C_Invoice creditMemo,
			final BigDecimal openAmt)
	{
		final Timestamp dateTrx = TimeUtil.max(invoice.getDateInvoiced(), creditMemo.getDateInvoiced());
		final Timestamp dateAcct = TimeUtil.max(invoice.getDateAcct(), creditMemo.getDateAcct());

		//
		// allocate the invoice against the credit memo
		// @formatter:off
		Services.get(IAllocationBL.class)
			.newBuilder(InterfaceWrapperHelper.getContextAware(invoice))
			.setAD_Org_ID(invoice.getAD_Org_ID())
			.setDateTrx(dateTrx)
			.setDateAcct(dateAcct)
			.setC_Currency_ID(invoice.getC_Currency_ID())
			.addLine()
				.setAD_Org_ID(invoice.getAD_Org_ID())
				.setC_BPartner_ID(invoice.getC_BPartner_ID())
				.setC_Invoice_ID(invoice.getC_Invoice_ID())
				.setAmount(openAmt)
			.lineDone()
			.addLine()
				.setAD_Org_ID(creditMemo.getAD_Org_ID())
				.setC_BPartner_ID(creditMemo.getC_BPartner_ID())
				.setC_Invoice_ID(creditMemo.getC_Invoice_ID())
				.setAmount(openAmt.negate())
			.lineDone()
			.create(true); // completeIt = true
		// @formatter:on
	}

	@Override
	public String getDefaultPaymentRule()
	{
		return Services.get(ISysConfigBL.class).getValue(SYSCONFIG_C_Invoice_PaymentRule, X_C_Invoice.PAYMENTRULE_OnCredit);
	}
}
