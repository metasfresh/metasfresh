/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.I_C_DocType;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MCashLine;
import org.compiere.model.MConversionRate;
import org.compiere.model.MFactAcct;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MOrder;
import org.compiere.model.MPayment;
import org.compiere.model.MTax;
import org.compiere.model.X_C_DocType;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Post Allocation Documents.
 * 
 * <pre>
 *  Table:              C_AllocationHdr
 *  Document Types:     CMA
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: Doc_Allocation.java,v 1.6 2006/07/30 00:53:33 jjanke Exp $
 * 
 *          FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual Avoid posting if Receipt and both accounts Unallocated Cash and Receivable are equal Avoid
 *          posting if Payment and both accounts Payment Select and Liability are equal
 * 
 * @author phib BF [ 2019262 ] Allocation posting currency gain/loss omits line reference
 * 
 */
public class Doc_AllocationHdr extends Doc
{
	/**
	 * Constructor
	 * 
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_AllocationHdr(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_Allocation);
	}   // Doc_Allocation

	/** Tolerance G&L */
	private static final BigDecimal TOLERANCE = new BigDecimal(0.02);
	/** Facts */
	private ArrayList<Fact> m_facts = null;

	/**
	 * Load Specific Document Details
	 * 
	 * @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		MAllocationHdr alloc = (MAllocationHdr)getPO();
		setDateDoc(alloc.getDateTrx());
		// Contained Objects
		p_lines = loadLines(alloc);
		return null;
	}   // loadDocumentDetails

	/**
	 * Load Invoice Line
	 *
	 * @param alloc header
	 * @return DocLine Array
	 */
	private DocLine[] loadLines(MAllocationHdr alloc)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MAllocationLine[] lines = alloc.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MAllocationLine line = lines[i];
			DocLine_Allocation docLine = new DocLine_Allocation(line, this);

			// Get Payment Conversion Rate
			if (line.getC_Payment_ID() != 0)
			{
				MPayment payment = new MPayment(getCtx(), line.getC_Payment_ID(), getTrxName());
				int C_ConversionType_ID = payment.getC_ConversionType_ID();
				docLine.setC_ConversionType_ID(C_ConversionType_ID);
			}
			//
			log.fine(docLine.toString());
			list.add(docLine);
		}

		// Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	// loadLines

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 * 
	 * @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for CMA.
	 * 
	 * <pre>
	 *  AR_Invoice_Payment
	 *      UnAllocatedCash DR
	 *      or C_Prepayment
	 *      DiscountExp     DR
	 *      WriteOff        DR
	 *      Receivables             CR
	 *  AR_Invoice_Cash
	 *      CashTransfer    DR
	 *      DiscountExp     DR
	 *      WriteOff        DR
	 *      Receivables             CR
	 * 
	 *  AP_Invoice_Payment
	 *      Liability       DR
	 *      DiscountRev             CR
	 *      WriteOff                CR
	 *      PaymentSelect           CR
	 *      or V_Prepayment
	 *  AP_Invoice_Cash
	 *      Liability       DR
	 *      DiscountRev             CR
	 *      WriteOff                CR
	 *      CashTransfer            CR
	 *  CashBankTransfer
	 *      -
	 *  ==============================
	 *  Realized Gain & Loss
	 * 		AR/AP			DR		CR
	 * 		Realized G/L	DR		CR
	 * 
	 *
	 * </pre>
	 * 
	 * Tax needs to be corrected for discount & write-off; Currency gain & loss is realized here.
	 * 
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		m_facts = new ArrayList<Fact>();

		// create Fact Header
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		final Fact factForRGL = new Fact(this, as, Fact.POST_Actual); // dummy fact (not posted) to calculate Realized Gain & Loss
		boolean isInterOrg = isInterOrg(as);

		for (int i = 0; i < p_lines.length; i++)
		{
			final DocLine_Allocation line = (DocLine_Allocation)p_lines[i];
			setC_BPartner_ID(line.getC_BPartner_ID());

			// CashBankTransfer - all references null and Discount/WriteOff = 0
			if (line.getC_Payment_ID() > 0
					&& line.getC_Invoice_ID() <= 0 && line.getC_Order_ID() <= 0
					&& line.getC_CashLine_ID() <= 0 && line.getC_BPartner_ID() <= 0
					&& Env.ZERO.compareTo(line.getDiscountAmt()) == 0
					&& Env.ZERO.compareTo(line.getWriteOffAmt()) == 0)
			{
				continue;
			}

			// Receivables/Liability Amount
			BigDecimal allocationSource = line.getAmtSource()
					.add(line.getDiscountAmt())
					.add(line.getWriteOffAmt());
			BigDecimal allocationSourceForRGL = allocationSource; // for realized gain & loss purposes
			BigDecimal allocationAccounted = Env.ZERO;	// AR/AP balance corrected
			BigDecimal allocationAccountedForRGL = Env.ZERO; // for realized gain & loss purposes

			FactLine fl = null;
			FactLine flForRGL = null;
			MAccount bpAcct = null;		// Liability/Receivables
			//
			MPayment payment = null;
			if (line.getC_Payment_ID() > 0)
				payment = new MPayment(getCtx(), line.getC_Payment_ID(), getTrxName());
			MInvoice invoice = null;
			if (line.getC_Invoice_ID() > 0)
				invoice = new MInvoice(getCtx(), line.getC_Invoice_ID(), getTrxName());

			//
			// No Invoice
			if (invoice == null)
			{
				// Payment Only
				// metas: begin
				if (line.getC_Invoice_ID() <= 0 && line.getC_Payment_ID() > 0 && line.getC_Order_ID() > 0)
				{
					// Case: we have an allocation to an order => Prepayment
					continue;
				}
				else
				// metas: end
				if (line.getC_Invoice_ID() <= 0 && line.getC_Payment_ID() > 0)
				{
					// metas: change:
//				
					if (line.getPaymentWriteOffAmt().signum() > 0)
					{
						
						if (payment.isReceipt())
						{
							final MAccount acct_unallocated_cash = getPaymentAcct(as, line.getC_Payment_ID());
							final MAccount acct_discount = getAccount(Doc.ACCTTYPE_DiscountExp, as);
							
							fl = fact.createLine(line, acct_unallocated_cash , getC_Currency_ID(), line.getPaymentWriteOffAmt(), null);
							if (fl != null )
							{
								fl.setAD_Org_ID(payment.getAD_Org_ID());
								fl.setC_BPartner_ID(payment.getC_BPartner_ID());
							}
							
							fl = fact.createLine(line, acct_discount, getC_Currency_ID(), null, line.getPaymentWriteOffAmt());
							if (fl != null )
							{
								fl.setAD_Org_ID(payment.getAD_Org_ID());
								fl.setC_BPartner_ID(payment.getC_BPartner_ID());
							}
							
						}
						else
						{
							final MAccount acct_paymentSelect = getAccount(Doc.ACCTTYPE_PaymentSelect, as);
							final MAccount acct_revenue = getAccount(Doc.ACCTTYPE_DiscountRev, as);

							fl = fact.createLine(line, acct_revenue, getC_Currency_ID(), null, line.getPaymentWriteOffAmt().negate());
							if (fl != null)
							{
								fl.setAD_Org_ID(payment.getAD_Org_ID());
								fl.setC_BPartner_ID(payment.getC_BPartner_ID());
							}
							
							fl = fact.createLine(line, acct_paymentSelect, getC_Currency_ID(), line.getPaymentWriteOffAmt().negate(), null);
							if (fl != null)
							{
								fl.setAD_Org_ID(payment.getAD_Org_ID());
								fl.setC_BPartner_ID(payment.getC_BPartner_ID());
							}
							
						}
						
						// ende changed
					}
				}
				else
				{
					// metas: this situation (neither payment nor invoice) can be legal ok,
					// e.g. if a payed metas-prepay order is closed
					// p_Error = "Cannot determine SO/PO";
					// log.log(Level.SEVERE, p_Error);
					// return null;
					assert line.getC_OrderLine_ID() > 0 || line.getC_Order_ID() > 0 : line;
					return m_facts;
					// metas end
				}
			}
			//
			// Sales Invoice
			else if (invoice.isSOTrx())
			{

				// Avoid usage of clearing accounts
				// If both accounts Unallocated Cash and Receivable are equal
				// then don't post

				final MAccount acct_unallocated_cash;
				if (line.getC_Payment_ID() > 0)
					acct_unallocated_cash = getPaymentAcct(as, line.getC_Payment_ID());
				else if (line.getC_CashLine_ID() > 0)
					acct_unallocated_cash = getCashAcct(as, line.getC_CashLine_ID());
				else
					acct_unallocated_cash = null;
				//
				MAccount acct_receivable = getAccount(Doc.ACCTTYPE_C_Receivable, as);

				//
				// Payment/Cash Amount DR
				// Skip clearing accounts (if configured) 
				if ((!as.isPostIfClearingEqual()) && acct_unallocated_cash != null && acct_unallocated_cash.equals(acct_receivable) && (!isInterOrg))
				{

					// if not using clearing accounts, then don't post amtsource
					// change the allocationsource to be writeoff + discount
					allocationSource = allocationSource.subtract(line.getAmtSource());
				}
				// Normal behavior -- unchanged if using clearing accounts
				else
				{
					if (line.getC_Payment_ID() > 0)
					{
						fl = fact.createLine(line, acct_unallocated_cash, getC_Currency_ID(), line.getAmtSource(), null);
						if (fl != null && payment != null)
						{
							fl.setAD_Org_ID(payment.getAD_Org_ID());
							fl.setC_BPartner_ID(payment.getC_BPartner_ID());
						}
					}
					else if (line.getC_CashLine_ID() > 0)
					{
						fl = fact.createLine(line, acct_unallocated_cash, getC_Currency_ID(), line.getAmtSource(), null);
						final MCashLine cashLine = new MCashLine(getCtx(), line.getC_CashLine_ID(), getTrxName());
						if (fl != null && cashLine.getC_CashLine_ID() > 0)
							fl.setAD_Org_ID(cashLine.getAD_Org_ID());
					}
					else
					{
						// No Payment/Cash => we are not posting the bookings for the amount
						// so we subtract that from allocationSource
						allocationSource = allocationSource.subtract(line.getAmtSource());
						
						fl = null;
					}
				}

				//
				// Discount DR
				if (Env.ZERO.compareTo(line.getDiscountAmt()) != 0)
				{
					// metas: CHANGED: get discount account dependent on tax
					createDiscountFacts(as, fact, line, payment, invoice);
					// ende changed
				}
				
				//
				// Write off DR
				if (Env.ZERO.compareTo(line.getWriteOffAmt()) != 0)
				{
					fl = fact.createLine(line, getAccount(Doc.ACCTTYPE_WriteOff, as),
							getC_Currency_ID(), line.getWriteOffAmt(), null);
					if (fl != null && payment != null)
					{
						fl.setAD_Org_ID(payment.getAD_Org_ID());
						fl.setC_BPartner_ID(payment.getC_BPartner_ID());
					}
				}

				//
				// AR Invoice Amount CR
				if (as.isAccrual())
				{
					bpAcct = getAccount(Doc.ACCTTYPE_C_Receivable, as);
					fl = fact.createLine(line, bpAcct, getC_Currency_ID(), null, allocationSource);		// payment currency
					if (fl != null)
						allocationAccounted = fl.getAcctBalance().negate();
					if (fl != null && invoice != null)
						fl.setAD_Org_ID(invoice.getAD_Org_ID());

					// for Realized Gain & Loss
					flForRGL = factForRGL.createLine(line, bpAcct, getC_Currency_ID(), null, allocationSourceForRGL);		// payment currency
					if (flForRGL != null)
						allocationAccountedForRGL = flForRGL.getAcctBalance().negate();
				}
				else
				// Cash Based
				{
					allocationAccounted = createCashBasedAcct(as, fact, invoice, allocationSource);
					allocationAccountedForRGL = allocationAccounted;
				}
			}
			//
			// Purchase Invoice
			else
			{
				final boolean isCreditMemo = isCreditMemo(payment, invoice);
				
				final MAccount acct_payment_select;
				if (line.getC_Payment_ID() != 0)
					acct_payment_select = getPaymentAcct(as, line.getC_Payment_ID());
				else if (line.getC_CashLine_ID() != 0)
					acct_payment_select = getCashAcct(as, line.getC_CashLine_ID());
				else
					acct_payment_select = null;
				//
				final MAccount acct_liability = getAccount(Doc.ACCTTYPE_V_Liability, as);
				boolean isUsingClearing = true;

				// Save original allocation source for realized gain & loss purposes
				allocationSourceForRGL = allocationSourceForRGL.negate();

				// Avoid usage of clearing accounts
				// If both accounts Payment Select and Liability are equal
				// then don't post
				if ((!as.isPostIfClearingEqual()) && acct_payment_select != null && acct_payment_select.equals(acct_liability) && (!isInterOrg))
				{

					// if not using clearing accounts, then don't post amtsource
					// change the allocationsource to be writeoff + discount
					allocationSource = allocationSource.subtract(line.getAmtSource());
					isUsingClearing = false;
				}
				// Case: there is no Payment/Cash => we are not booking the "Amount" but just the DiscountAmt and WriteoffAmt (if any)
				else if (line.getC_Payment_ID() <= 0 && line.getC_CashLine_ID() <= 0)
				{
					allocationSource = allocationSource.subtract(line.getAmtSource());
				}
				
				allocationSource = allocationSource.negate();	// allocation is negative
				
				// AP Invoice Amount DR
				if (as.isAccrual())
				{
					bpAcct = getAccount(Doc.ACCTTYPE_V_Liability, as);
					
					BigDecimal amtSourceDr = allocationSource;
					BigDecimal amtSourceCr = null;
					if (isCreditMemo)
					{
						amtSourceDr = null;
						amtSourceCr = allocationSource.negate();
					}
					fl = fact.createLine(line, bpAcct, getC_Currency_ID(), amtSourceDr, amtSourceCr);		// payment currency
					if (fl != null)
						allocationAccounted = fl.getAcctBalance();
					if (fl != null && invoice != null)
					{
						fl.setAD_Org_ID(invoice.getAD_Org_ID());
						fl.setC_BPartner_ID(invoice.getC_BPartner_ID());
					}

					// for Realized Gain & Loss
					flForRGL = factForRGL.createLine(line, bpAcct, getC_Currency_ID(), allocationSourceForRGL, null);		// payment currency
					if (flForRGL != null)
						allocationAccountedForRGL = flForRGL.getAcctBalance();
				}
				else
				// Cash Based
				{
					allocationAccounted = createCashBasedAcct(as, fact, invoice, allocationSource);
					allocationAccountedForRGL = allocationAccounted;
				}

				//
				// Discount CR
				if (Env.ZERO.compareTo(line.getDiscountAmt()) != 0)
				{
					// metas: CHANGED
					createDiscountFacts(as, fact, line, payment, invoice);
					// fl = fact.createLine (line, getAccount(Doc.ACCTTYPE_DiscountRev, as),
					// getC_Currency_ID(), null, line.getDiscountAmt().negate());
					// if (fl != null && payment != null)
					// fl.setAD_Org_ID(payment.getAD_Org_ID());
				}
				
				//
				// Write off CR
				if (Env.ZERO.compareTo(line.getWriteOffAmt()) != 0)
				{
					fl = fact.createLine(line, getAccount(Doc.ACCTTYPE_WriteOff, as), getC_Currency_ID(), null, line.getWriteOffAmt().negate());
					if (fl != null && payment != null)
					{
						fl.setAD_Org_ID(payment.getAD_Org_ID());
						fl.setC_BPartner_ID(payment.getC_BPartner_ID());
					}
				}
				
				//
				// Payment/Cash CR
				if (isUsingClearing && line.getC_Payment_ID() > 0) // Avoid usage of clearing accounts
				{
					// 07573: In case of credit memo, do not negate the amount
					BigDecimal amtSourceDr = null;
					BigDecimal amtSourceCr = line.getAmtSource().negate();
					if (isCreditMemo)
					{
						amtSourceDr = line.getAmtSource();
						amtSourceCr = null;
					}
					fl = fact.createLine(line, getPaymentAcct(as, line.getC_Payment_ID()), getC_Currency_ID(), amtSourceDr, amtSourceCr);
					
					if (fl != null && payment != null)
					{
						fl.setAD_Org_ID(payment.getAD_Org_ID());
						fl.setC_BPartner_ID(payment.getC_BPartner_ID());
					}
				}
				else if (isUsingClearing && line.getC_CashLine_ID() > 0) // Avoid usage of clearing accounts
				{
					fl = fact.createLine(line, getCashAcct(as, line.getC_CashLine_ID()),
							getC_Currency_ID(), null, line.getAmtSource().negate());
					MCashLine cashLine = new MCashLine(getCtx(), line.getC_CashLine_ID(), getTrxName());
					if (fl != null && cashLine.get_ID() != 0)
						fl.setAD_Org_ID(cashLine.getAD_Org_ID());
				}
			}

			//
			// VAT Tax Correction
			if (invoice != null && as.isTaxCorrection())
			{
				BigDecimal taxCorrectionAmt = Env.ZERO;
				if (as.isTaxCorrectionDiscount())
					taxCorrectionAmt = line.getDiscountAmt();
				if (as.isTaxCorrectionWriteOff())
					taxCorrectionAmt = taxCorrectionAmt.add(line.getWriteOffAmt());
				//
				if (taxCorrectionAmt.signum() != 0)
				{
					createTaxCorrection(as, fact, line,
							getAccount(invoice.isSOTrx() ? Doc.ACCTTYPE_DiscountExp : Doc.ACCTTYPE_DiscountRev, as),
							getAccount(Doc.ACCTTYPE_WriteOff, as), invoice.isSOTrx());
				}
			}

			//
			// Realized Gain & Loss
			if (invoice != null
					&& (getC_Currency_ID() != as.getC_Currency_ID()			// payment allocation in foreign currency
					|| getC_Currency_ID() != line.getInvoiceC_Currency_ID()))	// allocation <> invoice currency
			{
				createRealizedGainLoss(line, as, fact, bpAcct, invoice, allocationSource, allocationAccounted);
			}

		}	// for all lines

		// FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual
		if ((!as.isPostIfClearingEqual()) && p_lines.length > 0 && (!isInterOrg))
		{
			boolean allEquals = true;
			// more than one line (i.e. crossing one payment+ with a payment-, or an invoice against a credit memo)
			// verify if the sum of all facts is zero net
			FactLine[] factlines = fact.getLines();
			BigDecimal netBalance = Env.ZERO;
			FactLine prevFactLine = null;
			for (FactLine factLine : factlines)
			{
				netBalance = netBalance.add(factLine.getAmtSourceDr()).subtract(factLine.getAmtSourceCr());
				if (prevFactLine != null)
				{
					if (!equalFactLineIDs(prevFactLine, factLine))
					{
						allEquals = false;
						break;
					}
				}
				prevFactLine = factLine;
			}
			if (netBalance.compareTo(Env.ZERO) == 0 && allEquals)
			{
				// delete the postings
				for (FactLine factline : factlines)
					fact.remove(factline);
			}
		}

		// reset line info
		setC_BPartner_ID(0);
		//
		m_facts.add(fact);
		return m_facts;
	}   // createFact

	private boolean isCreditMemo(MPayment payment, MInvoice invoice)
	{
		if (payment == null)
		{
			return false;
		}

		if (invoice == null)
		{
			return false;
		}

		final I_C_DocType paymentDocType = payment.getC_DocType();
		final I_C_DocType invoiceDocType = invoice.getC_DocType();

		// We only look for Zahlungseingang
		if (!paymentDocType.isSOTrx())
		{
			return false;
		}

		// we only look for credit memo
		if (!X_C_DocType.DOCBASETYPE_APCreditMemo.equals(invoiceDocType.getDocBaseType()))
		{
			return false;
		}
		return true;
	}

	/**
	 * Verify if the posting involves two or more organizations
	 * 
	 * @return true if there are more than one org involved on the posting
	 */
	private boolean isInterOrg(MAcctSchema as)
	{
		MAcctSchemaElement elementorg = as.getAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_Organization);
		if (elementorg == null || !elementorg.isBalanced())
		{
			// no org element or not need to be balanced
			return false;
		}

		if (p_lines.length <= 0)
		{
			// no lines
			return false;
		}

		int startorg = p_lines[0].getAD_Org_ID();
		// validate if the allocation involves more than one org
		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine_Allocation line = (DocLine_Allocation)p_lines[i];
			int orgpayment = startorg;
			MPayment payment = null;
			if (line.getC_Payment_ID() != 0)
			{
				payment = new MPayment(getCtx(), line.getC_Payment_ID(), getTrxName());
				orgpayment = payment.getAD_Org_ID();
			}
			int orginvoice = startorg;
			MInvoice invoice = null;
			if (line.getC_Invoice_ID() != 0)
			{
				invoice = new MInvoice(getCtx(), line.getC_Invoice_ID(), getTrxName());
				orginvoice = invoice.getAD_Org_ID();
			}
			int orgcashline = startorg;
			MCashLine cashline = null;
			if (line.getC_CashLine_ID() != 0)
			{
				cashline = new MCashLine(getCtx(), line.getC_CashLine_ID(), getTrxName());
				orgcashline = cashline.getAD_Org_ID();
			}
			int orgorder = startorg;
			MOrder order = null;
			if (line.getC_Order_ID() != 0)
			{
				order = new MOrder(getCtx(), line.getC_Order_ID(), getTrxName());
				orgorder = order.getAD_Org_ID();
			}

			if (line.getAD_Org_ID() != startorg
					|| orgpayment != startorg
					|| orginvoice != startorg
					|| orgcashline != startorg
					|| orgorder != startorg)
				return true;
		}

		return false;
	}

	/**
	 * Compare the dimension ID's from two factlines
	 * 
	 * @param allEquals
	 * @param prevFactLine
	 * @param factLine
	 * @return boolean indicating if both dimension ID's are equal
	 */
	private boolean equalFactLineIDs(FactLine prevFactLine, FactLine factLine)
	{
		return (factLine.getA_Asset_ID() == prevFactLine.getA_Asset_ID()
				&& factLine.getAccount_ID() == prevFactLine.getAccount_ID()
				&& factLine.getAD_Client_ID() == prevFactLine.getAD_Client_ID()
				&& factLine.getAD_Org_ID() == prevFactLine.getAD_Org_ID()
				&& factLine.getAD_OrgTrx_ID() == prevFactLine.getAD_OrgTrx_ID()
				&& factLine.getC_AcctSchema_ID() == prevFactLine.getC_AcctSchema_ID()
				&& factLine.getC_Activity_ID() == prevFactLine.getC_Activity_ID()
				&& factLine.getC_BPartner_ID() == prevFactLine.getC_BPartner_ID()
				&& factLine.getC_Campaign_ID() == prevFactLine.getC_Campaign_ID()
				&& factLine.getC_Currency_ID() == prevFactLine.getC_Currency_ID()
				&& factLine.getC_LocFrom_ID() == prevFactLine.getC_LocFrom_ID()
				&& factLine.getC_LocTo_ID() == prevFactLine.getC_LocTo_ID()
				&& factLine.getC_Period_ID() == prevFactLine.getC_Period_ID()
				&& factLine.getC_Project_ID() == prevFactLine.getC_Project_ID()
				&& factLine.getC_ProjectPhase_ID() == prevFactLine.getC_ProjectPhase_ID()
				&& factLine.getC_ProjectTask_ID() == prevFactLine.getC_ProjectTask_ID()
				&& factLine.getC_SalesRegion_ID() == prevFactLine.getC_SalesRegion_ID()
				&& factLine.getC_SubAcct_ID() == prevFactLine.getC_SubAcct_ID()
				&& factLine.getC_Tax_ID() == prevFactLine.getC_Tax_ID()
				&& factLine.getC_UOM_ID() == prevFactLine.getC_UOM_ID()
				&& factLine.getGL_Budget_ID() == prevFactLine.getGL_Budget_ID()
				&& factLine.getGL_Category_ID() == prevFactLine.getGL_Category_ID()
				&& factLine.getM_Locator_ID() == prevFactLine.getM_Locator_ID()
				&& factLine.getM_Product_ID() == prevFactLine.getM_Product_ID()
				&& factLine.getUserElement1_ID() == prevFactLine.getUserElement1_ID()
				&& factLine.getUserElement2_ID() == prevFactLine.getUserElement2_ID()
				&& factLine.getUser1_ID() == prevFactLine.getUser1_ID()
				&& factLine.getUser2_ID() == prevFactLine.getUser2_ID());
	}

	// metas: CHANGED method createDiscountFacts added
	/**
	 * Create accounting facts for early payment discounts. Seperate for each tax.
	 *
	 * @param as
	 * @param fact
	 * @param line
	 * @param payment
	 */
	private void createDiscountFacts(MAcctSchema as, Fact fact, DocLine_Allocation line,
			MPayment payment, MInvoice invoice)
	{
		final BigDecimal discountAmt = line.getDiscountAmt();
		
		MInvoiceTax[] taxes = invoice.getTaxes(true);
		if (taxes == null || taxes.length == 0)
		{
			// old behaviour
			FactLine fl = null;
			if (invoice.isSOTrx())
			{
				fl = fact.createLine(line, getAccount(Doc.ACCTTYPE_DiscountExp, as),
						getC_Currency_ID(), discountAmt, null);
			}
			else
			{
				fl = fact.createLine(line, getAccount(Doc.ACCTTYPE_DiscountRev, as),
						getC_Currency_ID(), null, discountAmt.negate());
			}
			if (fl != null && payment != null)
			{
				fl.setAD_Org_ID(payment.getAD_Org_ID());
				fl.setC_BPartner_ID(payment.getC_BPartner_ID());
			}

		}
		else if (taxes != null)
		{
			final BigDecimal discountFactor = calculateDiscountFactor(discountAmt, invoice);
			
			// split discount for different taxes
			BigDecimal discountSum = Env.ZERO;
			for (int i = 0; i < taxes.length; i++)
			{
				// TaxDiscountAmt = TaxBaseAmt * Skonto * (1+TaxRate)
				// but we are calculating differently to avoid division by zero when calculating TaxRate=TaxAmt/TaxBaseAmt
				final BigDecimal taxAmt = taxes[i].getTaxAmt();
				final BigDecimal taxBaseAmt = taxes[i].getTaxBaseAmt();
				final BigDecimal baseAndTaxAmt = taxBaseAmt.add(taxAmt);
				BigDecimal taxDiscountAmt = baseAndTaxAmt.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
				if (taxAmt.signum() == 0)
				{
					taxDiscountAmt = BigDecimal.ZERO;
				}
				
				discountSum = discountSum.add(taxDiscountAmt);
				if (i == taxes.length - 1)
				{
					// last tax, check amounts
					if (discountSum.compareTo(discountAmt) != 0)
					{
						taxDiscountAmt = taxDiscountAmt.add(discountAmt.subtract(discountSum));
					}
				}
				
				// get tax specific discount account
				final int taxId = taxes[i].getC_Tax_ID();
				MAccount account = getTaxDiscountAccount(taxId, invoice.isSOTrx(), as);
				if (account == null)
				{
					// no taxDiscountAcct found, use standard account...
					if (invoice.isSOTrx())
					{
						account = getAccount(Doc.ACCTTYPE_DiscountExp, as);
					}
					else
					{
						account = getAccount(Doc.ACCTTYPE_DiscountRev, as);
					}
				}
				// create acct facts
				FactLine fl = null;
				if (invoice.isSOTrx())
				{
					fl = fact.createLine(line, account, getC_Currency_ID(), taxDiscountAmt, null);
				}
				else
				{
					fl = fact.createLine(line, account, getC_Currency_ID(), null, taxDiscountAmt.negate());
				}
				if (fl != null)
				{
					fl.setC_Tax_ID(taxId);
					if (payment != null)
					{
						fl.setAD_Org_ID(payment.getAD_Org_ID());
						fl.setC_BPartner_ID(payment.getC_BPartner_ID());
					}
				}
			}
		}
	}

	/**
	 * Calculates the discount factor (percentage of discountAmt from invoice's grand total)
	 * 
	 * @param discountAmt discount amount (absolute amount)
	 * @param invoice
	 * @return discount factor, percentage between 0...1, high precision
	 */
	private final BigDecimal calculateDiscountFactor(final BigDecimal discountAmt, final MInvoice invoice)
	{
		if (discountAmt.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		BigDecimal invoiceGrandTotalAbs = invoice.getGrandTotal(true); // creditMemoAdjusted=true
		if (!invoice.isSOTrx())
		{
			invoiceGrandTotalAbs = invoiceGrandTotalAbs.negate();
		}

		if (invoiceGrandTotalAbs.signum() == 0)
		{
			final PostingException ex = newPostingException()
					.addDetailMessage("Cannot calculate the discount factor when invoice grand total is ZERO")
					.setParameter("DiscountAmt", discountAmt)
					.setParameter("C_Invoice_ID", invoice);
			log.log(Level.WARNING, "Cannot calculate the discount factor when invoice grand total is ZERO. Considering ZERO", ex);
			return BigDecimal.ZERO;
		}

		final BigDecimal discountFactor = discountAmt.divide(invoiceGrandTotalAbs, 8, RoundingMode.HALF_UP);
		return discountFactor;
	}

	/**
	 * Returns early payment discount account for given tax.
	 *
	 * @param taxId
	 * @return
	 */
	public MAccount getTaxDiscountAccount(int taxId, boolean isSOTrx, MAcctSchema as)
	{
		if (taxId == 0)
		{
			return null;
		}
		return MTax.getDiscountAccount(taxId, isSOTrx, as);
	}

	// ende changed

	/**
	 * Create Cash Based Acct
	 * 
	 * @param as accounting schema
	 * @param fact fact
	 * @param invoice invoice
	 * @param allocationSource allocation amount (incl discount, writeoff)
	 * @return Accounted Amt
	 */
	private BigDecimal createCashBasedAcct(MAcctSchema as, Fact fact, MInvoice invoice,
			BigDecimal allocationSource)
	{
		BigDecimal allocationAccounted = Env.ZERO;
		// Multiplier
		double percent = invoice.getGrandTotal().doubleValue() / allocationSource.doubleValue();
		if (percent > 0.99 && percent < 1.01)
			percent = 1.0;
		log.config("Multiplier=" + percent + " - GrandTotal=" + invoice.getGrandTotal()
				+ " - Allocation Source=" + allocationSource);

		// Get Invoice Postings
		Doc_Invoice docInvoice = (Doc_Invoice)getDocFactory().getOrNull(
				getCtx(),
				new MAcctSchema[] { as },
				MInvoice.Table_ID, invoice.getC_Invoice_ID(),
				getTrxName());
		docInvoice.loadDocumentDetails();
		allocationAccounted = docInvoice.createFactCash(as, fact, new BigDecimal(percent));
		log.config("Allocation Accounted=" + allocationAccounted);

		// Cash Based Commitment Release
		if (as.isCreatePOCommitment() && !invoice.isSOTrx())
		{
			MInvoiceLine[] lines = invoice.getLines();
			for (int i = 0; i < lines.length; i++)
			{
				Fact factC = Doc_Order.getCommitmentRelease(as, this,
						lines[i].getQtyInvoiced(), lines[i].getC_InvoiceLine_ID(), new BigDecimal(percent));
				if (factC == null)
					return null;
				m_facts.add(factC);
			}
		}	// Commitment

		return allocationAccounted;
	}	// createCashBasedAcct

	/**
	 * Get Payment (Unallocated Payment or Payment Selection) Acct of Bank Account
	 *
	 * @param as accounting schema
	 * @param C_Payment_ID payment
	 * @return acct
	 */
	private MAccount getPaymentAcct(MAcctSchema as, int C_Payment_ID)
	{
		setC_BP_BankAccount_ID(0);
		// Doc.ACCTTYPE_UnallocatedCash (AR) or C_Prepayment
		// or Doc.ACCTTYPE_PaymentSelect (AP) or V_Prepayment
		int accountType = Doc.ACCTTYPE_UnallocatedCash;
		//
		String sql = "SELECT p.C_BP_BankAccount_ID, d.DocBaseType, p.IsReceipt, p.IsPrepayment "
				+ "FROM C_Payment p INNER JOIN C_DocType d ON (p.C_DocType_ID=d.C_DocType_ID) "
				+ "WHERE C_Payment_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			pstmt.setInt(1, C_Payment_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				setC_BP_BankAccount_ID(rs.getInt(1));
				if (DOCTYPE_APPayment.equals(rs.getString(2)))
					accountType = Doc.ACCTTYPE_PaymentSelect;
				// Prepayment
				if ("Y".equals(rs.getString(4)))		// Prepayment
				{
					if ("Y".equals(rs.getString(3)))	// Receipt
						accountType = Doc.ACCTTYPE_C_Prepayment;
					else
						accountType = Doc.ACCTTYPE_V_Prepayment;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		if (getC_BP_BankAccount_ID() <= 0)
		{
			log.log(Level.SEVERE, "NONE for C_Payment_ID=" + C_Payment_ID);
			return null;
		}
		return getAccount(accountType, as);
	}	// getPaymentAcct

	/**
	 * Get Cash (Transfer) Acct of CashBook
	 *
	 * @param as accounting schema
	 * @param C_CashLine_ID
	 * @return acct
	 */
	private MAccount getCashAcct(MAcctSchema as, int C_CashLine_ID)
	{
		String sql = "SELECT c.C_CashBook_ID "
				+ "FROM C_Cash c, C_CashLine cl "
				+ "WHERE c.C_Cash_ID=cl.C_Cash_ID AND cl.C_CashLine_ID=?";
		setC_CashBook_ID(DB.getSQLValue(null, sql, C_CashLine_ID));
		if (getC_CashBook_ID() <= 0)
		{
			log.log(Level.SEVERE, "NONE for C_CashLine_ID=" + C_CashLine_ID);
			return null;
		}
		return getAccount(Doc.ACCTTYPE_CashTransfer, as);
	}	// getCashAcct

	/**************************************************************************
	 * Create Realized Gain & Loss. Compares the Accounted Amount of the Invoice to the Accounted Amount of the Allocation
	 *
	 * @param as accounting schema
	 * @param fact fact
	 * @param acct account
	 * @param invoice invoice
	 * @param allocationSource source amt
	 * @param allocationAccounted acct amt
	 * @return Error Message or null if OK
	 */
	private void createRealizedGainLoss(DocLine line, MAcctSchema as, Fact fact, MAccount acct,
			MInvoice invoice, BigDecimal allocationSource, BigDecimal allocationAccounted)
	{
		BigDecimal invoiceSource = null;
		BigDecimal invoiceAccounted = null;
		//
		String sql = "SELECT "
				+ (invoice.isSOTrx()
						? "SUM(AmtSourceDr), SUM(AmtAcctDr)"	// so
						: "SUM(AmtSourceCr), SUM(AmtAcctCr)")	// po
				+ " FROM Fact_Acct "
				+ "WHERE AD_Table_ID=318 AND Record_ID=?"	// Invoice
				+ " AND C_AcctSchema_ID=?"
				+ " AND PostingType='A'";
		// AND C_Currency_ID=102
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			pstmt.setInt(1, invoice.getC_Invoice_ID());
			pstmt.setInt(2, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				invoiceSource = rs.getBigDecimal(1);
				invoiceAccounted = rs.getBigDecimal(2);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// Requires that Invoice is Posted
		if (invoiceSource == null || invoiceAccounted == null)
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setFact(fact)
					.setDocLine(line)
					.setDetailMessage("Gain/Loss - Invoice not posted yet");
		}
		//
		String description = "Invoice=(" + invoice.getC_Currency_ID() + ")" + invoiceSource + "/" + invoiceAccounted
				+ " - Allocation=(" + getC_Currency_ID() + ")" + allocationSource + "/" + allocationAccounted;
		log.fine(description);
		// Allocation not Invoice Currency
		if (getC_Currency_ID() != invoice.getC_Currency_ID())
		{
			BigDecimal allocationSourceNew = MConversionRate.convert(getCtx(),
					allocationSource, getC_Currency_ID(),
					invoice.getC_Currency_ID(), getDateAcct(),
					invoice.getC_ConversionType_ID(), invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
			if (allocationSourceNew == null)
			{
				// return "Gain/Loss - No Conversion from Allocation->Invoice";
				throw newPostingException()
						.setC_AcctSchema(as)
						.setFact(fact)
						.setDocLine(line)
						.setDetailMessage("Gain/Loss - No Conversion from Allocation->Invoice");
			}
			String d2 = "Allocation=(" + getC_Currency_ID() + ")" + allocationSource
					+ "->(" + invoice.getC_Currency_ID() + ")" + allocationSourceNew;
			log.fine(d2);
			description += " - " + d2;
			allocationSource = allocationSourceNew;
		}

		BigDecimal acctDifference = null;	// gain is negative
		// Full Payment in currency
		if (allocationSource.compareTo(invoiceSource) == 0)
		{
			acctDifference = invoiceAccounted.subtract(allocationAccounted);	// gain is negative
			String d2 = "(full) = " + acctDifference;
			log.fine(d2);
			description += " - " + d2;
		}
		else
		// partial or MC
		{
			// percent of total payment
			double multiplier = allocationSource.doubleValue() / invoiceSource.doubleValue();
			// Reduce Orig Invoice Accounted
			invoiceAccounted = invoiceAccounted.multiply(new BigDecimal(multiplier));
			// Difference based on percentage of Orig Invoice
			acctDifference = invoiceAccounted.subtract(allocationAccounted);	// gain is negative
			// ignore Tolerance
			if (acctDifference.abs().compareTo(TOLERANCE) < 0)
				acctDifference = Env.ZERO;
			// Round
			int precision = as.getStdPrecision();
			if (acctDifference.scale() > precision)
				acctDifference = acctDifference.setScale(precision, BigDecimal.ROUND_HALF_UP);
			String d2 = "(partial) = " + acctDifference + " - Multiplier=" + multiplier;
			log.fine(d2);
			description += " - " + d2;
		}

		if (acctDifference.signum() == 0)
		{
			log.fine("No Difference");
			return;
		}

		MAccount gain = MAccount.get(as.getCtx(), as.getAcctSchemaDefault().getRealizedGain_Acct());
		MAccount loss = MAccount.get(as.getCtx(), as.getAcctSchemaDefault().getRealizedLoss_Acct());
		//
		if (invoice.isSOTrx())
		{
			FactLine fl = fact.createLine(line, loss, gain,
					as.getC_Currency_ID(), acctDifference);
			fl.setDescription(description);
			fact.createLine(line, acct,
					as.getC_Currency_ID(), acctDifference.negate());
			fl.setDescription(description);
		}
		else
		{
			fact.createLine(line, acct,
					as.getC_Currency_ID(), acctDifference);
			fact.createLine(line, loss, gain,
					as.getC_Currency_ID(), acctDifference.negate());
		}
		
		// return null; // OK
	}	// createRealizedGainLoss

	/**************************************************************************
	 * Create Tax Correction. Requirement: Adjust the tax amount, if you did not receive the full amount of the invoice (payment discount, write-off). Applies to many countries with VAT. Example:
	 * Invoice: Net $100 + Tax1 $15 + Tax2 $5 = Total $120 Payment: $115 (i.e. $5 underpayment) Tax Adjustment = Tax1 = 0.63 (15/120*5) Tax2 = 0.21 (5/120/5)
	 * 
	 * @param as accounting schema
	 * @param fact fact
	 * @param line Allocation line
	 * @param DiscountAccount discount acct
	 * @param WriteOffAccoint write off acct
	 * @return true if created
	 */
	private void createTaxCorrection(MAcctSchema as, Fact fact,
			DocLine_Allocation line,
			MAccount DiscountAccount, MAccount WriteOffAccoint, boolean isSOTrx)
	{
		log.info(line.toString());
		BigDecimal discount = Env.ZERO;
		if (as.isTaxCorrectionDiscount())
			discount = line.getDiscountAmt();
		BigDecimal writeOff = Env.ZERO;
		if (as.isTaxCorrectionWriteOff())
			writeOff = line.getWriteOffAmt();

		Doc_AllocationTax tax = new Doc_AllocationTax(this,
				DiscountAccount, discount, WriteOffAccoint, writeOff, isSOTrx);

		// FIXME: metas-tsa: fix how we retrieve the tax bookings of the invoice, i.e.
		// * here we retrieve all Fact_Acct records which are not on line level.
		// * the code is assuming that it will get the Tax bookings and the invoice gross amount booking
		// * later on org.compiere.acct.Doc_AllocationTax.createEntries(MAcctSchema, Fact, DocLine_Allocation), we skip the gross amount Fact_Acct line
		// Get Source Amounts with account
		String sql = "SELECT * "
				+ "FROM Fact_Acct "
				+ "WHERE AD_Table_ID=318 AND Record_ID=?"	// Invoice
				+ " AND C_AcctSchema_ID=?"
				+ " AND Line_ID IS NULL";	// header lines like tax or total
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			pstmt.setInt(1, line.getC_Invoice_ID());
			pstmt.setInt(2, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				tax.addInvoiceFact(new MFactAcct(getCtx(), rs, fact.get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// Invoice Not posted
		if (tax.getLineCount() == 0)
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setFact(fact)
					.setDocLine(line)
					.setDetailMessage("Invoice not posted yet - " + line);
		}
		// size = 1 if no tax
		if (tax.getLineCount() < 2)
		{
			//return true;
			return; // OK
		}
		
		tax.createEntries(as, fact, line);
	}	// createTaxCorrection
}   // Doc_Allocation

/**
 * Allocation Document Tax Handing
 *
 * @author Jorg Janke
 * @version $Id: Doc_Allocation.java,v 1.6 2006/07/30 00:53:33 jjanke Exp $
 */
/* package */class Doc_AllocationTax
{
	/**
	 * Allocation Tax Adjustment
	 * @param doc_AllocationHdr 
	 *
	 * @param DiscountAccount discount acct
	 * @param DiscountAmt discount amt
	 * @param WriteOffAccount write off acct
	 * @param WriteOffAmt write off amt
	 */
	public Doc_AllocationTax(Doc_AllocationHdr doc, MAccount DiscountAccount, BigDecimal DiscountAmt,
			MAccount WriteOffAccount, BigDecimal WriteOffAmt, boolean isSOTrx)
	{
		super();
		// metas: CHANGED: standardDiscountAccount
		m_doc = doc;
		m_StandardDiscountAccount = DiscountAccount;
		m_DiscountAccount = DiscountAccount;
		m_DiscountAmt = DiscountAmt;
		m_WriteOffAccount = WriteOffAccount;
		m_WriteOffAmt = WriteOffAmt;
		m_IsSOTrx = isSOTrx;
	}	// Doc_AllocationTax

	private final CLogger log = CLogger.getCLogger(getClass());
	private final Doc_AllocationHdr m_doc;
	// metas: CHANGED: standardDiscountAccount (fallback)
	private final MAccount m_StandardDiscountAccount;
	private MAccount m_DiscountAccount;
	private final BigDecimal m_DiscountAmt;
	private final MAccount m_WriteOffAccount;
	private final BigDecimal m_WriteOffAmt;
	private final boolean m_IsSOTrx;

	private List<I_Fact_Acct> m_facts = new ArrayList<>();
	private int m_totalIndex = 0;

	/**
	 * Add Invoice Fact Line
	 *
	 * @param fact fact line
	 */
	public void addInvoiceFact(I_Fact_Acct fact)
	{
		m_facts.add(fact);
	}	// addInvoiceLine

	/**
	 * Get Line Count
	 *
	 * @return number of lines
	 */
	public int getLineCount()
	{
		return m_facts.size();
	}	// getLineCount

	/**
	 * Create Accounting Entries
	 *
	 * @param as account schema
	 * @param fact fact to add lines
	 * @param line line
	 * @return true if created
	 */
	public void createEntries(final MAcctSchema as, final Fact fact, final DocLine_Allocation line)
	{
		// get total index (the Receivables/Liabilities line)
		BigDecimal total = Env.ZERO;
		for (int i = 0; i < m_facts.size(); i++)
		{
			final MFactAcct factAcct = (MFactAcct)m_facts.get(i);
			if (factAcct.getAmtSourceDr().compareTo(total) > 0)
			{
				total = factAcct.getAmtSourceDr();
				m_totalIndex = i;
			}
			if (factAcct.getAmtSourceCr().compareTo(total) > 0)
			{
				total = factAcct.getAmtSourceCr();
				m_totalIndex = i;
			}
		}

		MFactAcct factAcct = (MFactAcct)m_facts.get(m_totalIndex);
		log.info("Total Invoice = " + total + " - " + factAcct);
		int precision = as.getStdPrecision();
		for (int i = 0; i < m_facts.size(); i++)
		{
			// No Tax Line
			if (i == m_totalIndex)
				continue;

			factAcct = (MFactAcct)m_facts.get(i);
			log.info(i + ": " + factAcct);

			// metas: CHANGED: find discountAccount for Tax
			final int taxId = factAcct.getC_Tax_ID();
			m_DiscountAccount = MTax.getDiscountAccount(taxId, m_IsSOTrx, as);
			if (m_DiscountAccount == null)
			{
				m_DiscountAccount = m_StandardDiscountAccount;
			}
			// ende changed

			// Create Tax Account
			final MAccount taxAcct = factAcct.getMAccount();
			if (taxAcct == null || taxAcct.get_ID() <= 0)
			{
//				log.severe("Tax Account not found/created");
//				return false;
				throw m_doc.newPostingException()
						.setC_AcctSchema(as)
						.setFact(fact)
						.setDocLine(line)
						.setDetailMessage("Tax Account not found/created");
			}

			// Discount Amount
			if (m_DiscountAmt.signum() != 0)
			{
				// Original Tax is DR - need to correct it CR
				if (Env.ZERO.compareTo(factAcct.getAmtSourceDr()) != 0)
				{
					BigDecimal amount = calcAmount(factAcct.getAmtSourceDr(),
							total, m_DiscountAmt, precision);
					if (amount.signum() != 0)
					{
						// for sales actions
						if (m_IsSOTrx)
						{
							final FactLine flDR = fact.createLine(line, m_DiscountAccount, as.getC_Currency_ID(), amount, null);
							setC_Tax_ID(flDR, taxId);
							final FactLine flCR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), null, amount);
							setC_Tax_ID(flCR, taxId);
						}
						else
						{
							// for purchase actions
							final FactLine flDR = fact.createLine(line, m_DiscountAccount, as.getC_Currency_ID(), amount.negate(), null);
							setC_Tax_ID(flDR, taxId);
							final FactLine flCR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), null, amount.negate());
							setC_Tax_ID(flCR, taxId);
						}

					}
				}
				// Original Tax is CR - need to correct it DR
				else
				{
					final BigDecimal amount = calcAmount(factAcct.getAmtSourceCr(), total, m_DiscountAmt, precision);
					if (amount.signum() != 0)
					{
						// for sales actions
						if (m_IsSOTrx)
						{
							final FactLine flDR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), amount, null);
							setC_Tax_ID(flDR, taxId);
							final FactLine flCR = fact.createLine(line, m_DiscountAccount, as.getC_Currency_ID(), null, amount);
							setC_Tax_ID(flCR, taxId);
						}
						else
						{
							// TODO - metas: muessen taxAcct und m_DiscountAccount vertauscht werden?
							final FactLine flDR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), amount.negate(), null);
							setC_Tax_ID(flDR, taxId);
							final FactLine flCR = fact.createLine(line, m_DiscountAccount, as.getC_Currency_ID(), null, amount.negate());
							setC_Tax_ID(flCR, taxId);
						}
					}
				}
			}	// Discount

			// WriteOff Amount
			if (m_WriteOffAmt.signum() != 0)
			{
				// Original Tax is DR - need to correct it CR
				if (Env.ZERO.compareTo(factAcct.getAmtSourceDr()) != 0)
				{
					BigDecimal amount = calcAmount(factAcct.getAmtSourceDr(), total, m_WriteOffAmt, precision);
					if (amount.signum() != 0)
					{
						fact.createLine(line, m_WriteOffAccount, as.getC_Currency_ID(), amount, null);
						fact.createLine(line, taxAcct, as.getC_Currency_ID(), null, amount);
					}
				}
				// Original Tax is CR - need to correct it DR
				else
				{
					BigDecimal amount = calcAmount(factAcct.getAmtSourceCr(),
							total, m_WriteOffAmt, precision);
					if (amount.signum() != 0)
					{
						fact.createLine(line, taxAcct, as.getC_Currency_ID(), amount, null);
						fact.createLine(line, m_WriteOffAccount, as.getC_Currency_ID(), null, amount);
					}
				}
			}	// WriteOff

		}	// for all lines
		
		// return true; // OK
	}	// createEntries

	/**
	 * Calc Amount tax / total * amt
	 *
	 * @param tax tax
	 * @param total total
	 * @param amt reduction amt
	 * @param precision precision
	 * @return tax / total * amt
	 */
	private final BigDecimal calcAmount(BigDecimal tax, BigDecimal total, BigDecimal amt, int precision)
	{
		log.fine("Amt=" + amt + " - Total=" + total + ", Tax=" + tax);
		if (tax.signum() == 0
				|| total.signum() == 0
				|| amt.signum() == 0)
			return Env.ZERO;
		//
		BigDecimal multiplier = tax.divide(total, 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal retValue = multiplier.multiply(amt);
		if (retValue.scale() > precision)
			retValue = retValue.setScale(precision, BigDecimal.ROUND_HALF_UP);
		log.fine(retValue + " (Mult=" + multiplier + "(Prec=" + precision + ")");
		return retValue;
	}	// calcAmount

	/**
	 * Convenient method to set {@link FactLine}'s C_Tax_ID if the line is not null
	 * @param fl
	 * @param taxId
	 */
	private final void setC_Tax_ID(final FactLine fl, final int taxId)
	{
		if (fl == null)
		{
			return;
		}
		fl.setC_Tax_ID(taxId);
	}
}	// Doc_AllocationTax
