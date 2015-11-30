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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.currency.ICurrencyConversionContext;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.product.service.IProductBL;
import org.adempiere.service.ICurrencyConversionBL;
import org.adempiere.tax.api.ITaxBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MTax;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_InvoiceLine;

/**
 * Post MatchInv Documents.
 * 
 * <pre>
 *  Table:              M_MatchInv (472)
 *  Document Types:     MXI
 * </pre>
 * 
 * Update Costing Records
 * 
 * @author Jorg Janke
 * @version $Id: Doc_MatchInv.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 * 
 *          FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual Avoid posting if both accounts Not Invoiced Receipts and Inventory Clearing are equal BF [
 *          2789949 ] Multicurrency in matching posting
 */
public class Doc_MatchInv extends Doc
{
	// services
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient ITaxBL taxBL = Services.get(ITaxBL.class);

	public Doc_MatchInv(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_MatMatchInv);
	}   // Doc_MatchInv

	/** Invoice Line */
	private I_C_InvoiceLine m_invoiceLine = null;
	private int invoiceCurrencyId;
	/** Invoice line net amount, excluding taxes, in invoice's currency */
	private BigDecimal invoiceLineNetAmt = null;
	private ICurrencyConversionContext invoiceCurrencyConversionCtx;
	/** Material Receipt */
	private I_M_InOutLine m_receiptLine = null;

	private ProductCost m_pc = null;

	/** Commitments */
	// private DocLine[] m_commitments = null;

	/**
	 * Load Specific Document Details
	 * 
	 * @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		final I_M_MatchInv matchInv = getM_MatchInv();
		setC_Currency_ID(Doc.NO_CURRENCY);
		setDateDoc(matchInv.getDateTrx());
		setQty(matchInv.getQty());

		final String trxName = getTrxName();

		// Invoice Info
		{
			m_invoiceLine = InterfaceWrapperHelper.create(matchInv.getC_InvoiceLine(), I_C_InvoiceLine.class);
			final I_C_Invoice invoice = m_invoiceLine.getC_Invoice();
			
			// BP for NotInvoicedReceipts
			final int C_BPartner_ID = invoice.getC_BPartner_ID();
			setC_BPartner_ID(C_BPartner_ID);
			
			invoiceCurrencyId = invoice.getC_Currency_ID();
			invoiceLineNetAmt = m_invoiceLine.getLineNetAmt();

			// Correct included Tax
			final boolean taxIncluded = invoiceBL.isTaxIncluded(m_invoiceLine);
			final int C_Tax_ID = m_invoiceLine.getC_Tax_ID();
			if (taxIncluded && C_Tax_ID > 0)
			{
				final MTax tax = MTax.get(getCtx(), C_Tax_ID);
				if (!tax.isZeroTax())
				{
					final int taxPrecision = getStdPrecision();
					final BigDecimal lineTaxAmt = taxBL.calculateTax(tax, invoiceLineNetAmt, true, taxPrecision);
					log.log(Level.FINE, "LineNetAmt={0} - LineTaxAmt={1}", new Object[] { invoiceLineNetAmt, lineTaxAmt });
					invoiceLineNetAmt = invoiceLineNetAmt.subtract(lineTaxAmt);
				}
			}	// correct included Tax

		}
		
		// Receipt info
		m_receiptLine = matchInv.getM_InOutLine();
		
		// Product costing
		m_pc = new ProductCost(getCtx(),
				getM_Product_ID(), matchInv.getM_AttributeSetInstance_ID(),
				trxName);
		m_pc.setQty(getQty());

		return null;
	}   // loadDocumentDetails

	public I_M_MatchInv getM_MatchInv()
	{
		final I_M_MatchInv matchInv = InterfaceWrapperHelper.create(getPO(), I_M_MatchInv.class);
		return matchInv;
	}

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 * 
	 * @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		return Env.ZERO;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for MXI. (single line)
	 * 
	 * <pre>
	 *      NotInvoicedReceipts     DR			(Receipt Org)
	 *      InventoryClearing               CR
	 *      InvoicePV               DR      CR  (difference)
	 *  Commitment
	 * 		Expense							CR
	 * 		Offset					DR
	 * </pre>
	 * 
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();

		//
		// Match invoice on sales side has no accounting consequences
		// because there are no intermediare accounting to be cleared (08529)
		if (isSOTrx())
		{
			return facts;
		}

		//
		// Skip not stockable (e.g. service products) because they have no cost
		final IProductBL productBL = Services.get(IProductBL.class);
		final I_M_Product product = getM_MatchInv().getM_Product();
		if (!productBL.isStocked(product))
		{
			return facts;
		}


		//
		// Nothing to do
		if (getM_Product_ID() <= 0								// no Product
				|| getQty().signum() == 0
				|| m_receiptLine.getMovementQty().signum() == 0	// Qty = 0
				|| m_invoiceLine.getQtyInvoiced().signum() == 0) // 08643 avoid division by zero further down; note that we don't really know if and what to book in this case.
		{
			log.fine("No Product/Qty - M_Product_ID=" + getM_Product_ID()
					+ ",Qty=" + getQty() + ",InOutQty=" + m_receiptLine.getMovementQty() + ",InvoiceQty=" + m_invoiceLine.getQtyInvoiced());
			return facts;
		}

		// create Fact Header
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		/**
		 * Needs to be handled in PO Matching as no Receipt info if (m_pc.isService()) { log.fine("Service - skipped"); return fact; }
		 **/

		// NotInvoicedReceipt DR
		// From Receipt
		final BigDecimal receiptQtyMultiplier = getQty()
				.divide(m_receiptLine.getMovementQty(), 12, BigDecimal.ROUND_HALF_UP)
				.abs();
		final FactLine dr = fact.createLine(null,
				getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as),
				as.getC_Currency_ID(), Env.ONE, null);			// updated below
		if (dr == null)
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setFact(fact)
					.setDetailMessage("No Product Costs");
		}
		dr.setQty(getQty());
		// dr.setM_Locator_ID(m_receiptLine.getM_Locator_ID());
		// MInOut receipt = m_receiptLine.getParent();
		// dr.setLocationFromBPartner(receipt.getC_BPartner_Location_ID(), true); // from Loc
		// dr.setLocationFromLocator(m_receiptLine.getM_Locator_ID(), false); // to Loc
		BigDecimal temp = dr.getAcctBalance();
		// Set AmtAcctCr/Dr from Receipt (sets also Project)
		if (!dr.updateReverseLine(I_M_InOut.Table_ID, 		// Amt updated
				m_receiptLine.getM_InOut_ID(), m_receiptLine.getM_InOutLine_ID(),
				receiptQtyMultiplier))
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setFact(fact)
					.setPostingStatus(PostingStatus.NotPosted)
					// Posted status shall not be changed, we just have to postpone this booking
					.setPreserveDocumentPostedStatus()
					// NOTE: there could be quite a lot of M_MatchInvs which don't have the M_InOut already posted,
					// and we want to avoid filling the log file with all these issues
					.setLogLevel(Level.INFO)
					.setDetailMessage("Mat.Receipt not posted yet");
		}
		log.fine("CR - Amt(" + temp + "->" + dr.getAcctBalance() + ") - " + dr.toString());

		// InventoryClearing CR
		// From Invoice
		MAccount expense = m_pc.getAccount(ProductCost.ACCTTYPE_P_InventoryClearing, as);
		if (m_pc.isService())
			expense = m_pc.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
		BigDecimal LineNetAmt = getInvoiceLineNetAmt();

		final BigDecimal invoiceQtyMultiplier = getQty()
				.divide(m_invoiceLine.getQtyInvoiced(), 12, BigDecimal.ROUND_HALF_UP)
				.abs();

		if (invoiceQtyMultiplier.compareTo(Env.ONE) != 0)
		{
			LineNetAmt = LineNetAmt.multiply(invoiceQtyMultiplier);
		}
		if (m_pc.isService())
			LineNetAmt = dr.getAcctBalance();	// book out exact receipt amt
		
		final FactLine cr;
		final int invoiceCurrencyId = getInvoiceCurrencyId();
		if (as.isAccrual())
		{
			cr = fact.createLine()
					.setAccount(expense)
					.setC_Currency_ID(invoiceCurrencyId)
					.setCurrencyConversionCtx(getInvoiceCurrencyConversionCtx())
					.setAmtSource(null, LineNetAmt)
					// NOTE: the other fields and dimensions will be updated below
					.buildAndAdd();
			if (cr == null)
			{
				log.fine("Line Net Amt=0 - M_Product_ID=" + getM_Product_ID() + ",Qty=" + getQty() + ",InOutQty=" + m_receiptLine.getMovementQty());

				// Invoice Price Variance
				final BigDecimal ipv = dr.getSourceBalance().negate();
				if (ipv.signum() != 0)
				{
					final FactLine pv = fact.createLine(null, m_pc.getAccount(ProductCost.ACCTTYPE_P_IPV, as), invoiceCurrencyId, ipv);
					pv.setC_Activity_ID(m_invoiceLine.getC_Activity_ID());
					pv.setC_Campaign_ID(m_invoiceLine.getC_Campaign_ID());
					pv.setC_Project_ID(m_invoiceLine.getC_Project_ID());
					pv.setC_UOM_ID(m_invoiceLine.getPrice_UOM_ID());
					pv.setUser1_ID(m_invoiceLine.getUser1_ID());
					pv.setUser2_ID(m_invoiceLine.getUser2_ID());
				}
				log.fine("IPV=" + ipv + "; Balance=" + fact.getSourceBalance());
				facts.add(fact);
				return facts;
			}
			cr.setQty(getQty().negate());

			// task 09403
			// NOTE: we shall NOT update the amount from Invoice posting
			// because now there we have 2 lines for each invoice line: the line about matched qty and the line about not matched qty.
			// So now, in invoice posting, for our invoice line, the amount which is booked on P_InventoryClearing account is the total amount which was matched.
			// Updating from there and applying the multiplier would lead to incorrect figures.
			//
			// temp = cr.getAcctBalance();
			// // Set AmtAcctCr/Dr from Invoice (sets also Project)
			// if (as.isAccrual() && !cr.updateReverseLine(I_C_Invoice.Table_ID, // Amt updated
			// m_invoiceLine.getC_Invoice_ID(), m_invoiceLine.getC_InvoiceLine_ID(), invoiceQtyMultiplier))
			// {
			// throw newPostingException()
			// .setC_AcctSchema(as)
			// .setFact(fact)
			// .setPostingStatus(PostingStatus.NotPosted)
			// // Posted status shall not be changed, we just have to postpone this booking
			// .setPreserveDocumentPostedStatus()
			// // NOTE: there could be quite a lot of M_MatchInvs which don't have the C_Invoice already posted,
			// // and we want to avoid filling the log file with all these issues
			// .setLogLevel(Level.INFO)
			// .setDetailMessage("Invoice not posted yet");
			// }

			log.fine("CR - Amt(" + temp + "->" + cr.getAcctBalance() + ") - " + cr.toString());
		}
		else
		// Cash Acct
		{
			if (as.getC_Currency_ID() != invoiceCurrencyId)
			{
				LineNetAmt = currencyConversionBL.convert(getInvoiceCurrencyConversionCtx(), LineNetAmt, invoiceCurrencyId, as.getC_Currency_ID())
						.getAmount();
			}
			cr = fact.createLine(null, expense, as.getC_Currency_ID(), null, LineNetAmt);
			cr.setQty(getQty().multiply(invoiceQtyMultiplier).negate());
		}
		cr.setC_Activity_ID(m_invoiceLine.getC_Activity_ID());
		cr.setC_Campaign_ID(m_invoiceLine.getC_Campaign_ID());
		cr.setC_Project_ID(m_invoiceLine.getC_Project_ID());
		cr.setC_UOM_ID(m_invoiceLine.getPrice_UOM_ID());
		cr.setUser1_ID(m_invoiceLine.getUser1_ID());
		cr.setUser2_ID(m_invoiceLine.getUser2_ID());

		// AZ Goodwill
		// Desc: Source Not Balanced problem because Currency is Difference - PO=CNY but AP=USD
		// see also Fact.java: checking for isMultiCurrency()
		if (dr.getC_Currency_ID() != cr.getC_Currency_ID())
			setIsMultiCurrency(true);
		// end AZ

		// Avoid usage of clearing accounts
		// If both accounts Not Invoiced Receipts and Inventory Clearing are equal
		// then remove the posting
		{
			MAccount acct_db = dr.getAccount(); // not_invoiced_receipts
			MAccount acct_cr = cr.getAccount(); // inventory_clearing
	
			if ((!as.isPostIfClearingEqual()) && acct_db.equals(acct_cr) && (!isInterOrg(as)))
			{
				BigDecimal debit = dr.getAmtAcctDr();
				BigDecimal credit = cr.getAmtAcctCr();
				if (debit.compareTo(credit) == 0)
				{
					fact.remove(dr);
					fact.remove(cr);
				}
			}
		}

		//
		// Invoice Price Variance difference
		final BigDecimal ipv = cr.getAcctBalance().add(dr.getAcctBalance()).negate();
		if (ipv.signum() != 0)
		{
			final FactLine pv = fact.createLine(null, m_pc.getAccount(ProductCost.ACCTTYPE_P_IPV, as), as.getC_Currency_ID(), ipv);
			pv.setC_Activity_ID(m_invoiceLine.getC_Activity_ID());
			pv.setC_Campaign_ID(m_invoiceLine.getC_Campaign_ID());
			pv.setC_Project_ID(m_invoiceLine.getC_Project_ID());
			pv.setC_UOM_ID(m_invoiceLine.getPrice_UOM_ID());
			pv.setUser1_ID(m_invoiceLine.getUser1_ID());
			pv.setUser2_ID(m_invoiceLine.getUser2_ID());
		}
		log.fine("IPV=" + ipv + "; Balance=" + fact.getSourceBalance());

		// Update Costing
		updateProductInfo(as.getC_AcctSchema_ID(), MAcctSchema.COSTINGMETHOD_StandardCosting.equals(as.getCostingMethod()));
		//
		facts.add(fact);

		/** Commitment release ****/
		if (as.isAccrual() && as.isCreatePOCommitment())
		{
			final Fact factCommitment = Doc_Order.getCommitmentRelease(as, this,
					getQty(), m_invoiceLine.getC_InvoiceLine_ID(), Env.ONE);
			if (factCommitment == null)
				return null;
			facts.add(factCommitment);
		}	// Commitment

		return facts;
	}   // createFact

	/**
	 * Verify if the posting involves two or more organizations
	 * 
	 * @return true if there are more than one org involved on the posting
	 */
	private boolean isInterOrg(final MAcctSchema as)
	{
		final I_C_AcctSchema_Element elementorg = as.getAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_Organization);
		if (elementorg == null || !elementorg.isBalanced())
		{
			// no org element or not need to be balanced
			return false;
		}

		// verify if org of receipt line is different from org of invoice line
		if (m_receiptLine != null && m_invoiceLine != null && m_receiptLine.getAD_Org_ID() != m_invoiceLine.getAD_Org_ID())
			return true;

		return false;
	}

	/**
	 * Update Product Info (old). - Costing (CostStandardCumQty, CostStandardCumAmt, CostAverageCumQty, CostAverageCumAmt)
	 * 
	 * @param C_AcctSchema_ID accounting schema
	 * @param standardCosting true if std costing
	 * @return true if updated
	 * @deprecated old costing
	 */
	@Deprecated
	private boolean updateProductInfo(int C_AcctSchema_ID, boolean standardCosting)
	{
		if (isSOTrx())
		{
			return true; // task 08529: we extend the use of matchInv to also keep track of the SoTrx side. However, currently we don't need the accounting of that side to work
		}
		log.fine("M_MatchInv_ID=" + get_ID());

		// update Product Costing Qty/Amt
		// requires existence of currency conversion !!
		StringBuilder sql = new StringBuilder(
				"UPDATE M_Product_Costing pc "
						+ "SET (CostStandardCumQty,CostStandardCumAmt, CostAverageCumQty,CostAverageCumAmt) = "
						+ "(SELECT pc.CostStandardCumQty + m.Qty,"
						+ "pc.CostStandardCumAmt + currencyConvert(il.PriceActual,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID)*m.Qty, "
						+ "pc.CostAverageCumQty + m.Qty,"
						+ "pc.CostAverageCumAmt + currencyConvert(il.PriceActual,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID)*m.Qty "
						+ "FROM M_MatchInv m"
						+ " INNER JOIN C_InvoiceLine il ON (m.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
						+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID),"
						+ " C_AcctSchema a "
						+ "WHERE pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
						+ " AND pc.M_Product_ID=m.M_Product_ID"
						+ " AND m.M_MatchInv_ID=").append(get_ID()).append(")"
				//
				+ "WHERE pc.C_AcctSchema_ID=").append(C_AcctSchema_ID).append(
				" AND EXISTS (SELECT 1 FROM M_MatchInv m "
						+ "WHERE pc.M_Product_ID=m.M_Product_ID"
						+ " AND m.M_MatchInv_ID=").append(get_ID()).append(")");
		final String sqlNative = DB.convertSqlToNative(sql.toString());
		int no = DB.executeUpdate(sqlNative, getTrxName());
		log.fine("M_Product_Costing - Qty/Amt Updated #=" + no);

		// Update Average Cost
		sql = new StringBuilder(
				"UPDATE M_Product_Costing "
						// + "SET CostAverage = CostAverageCumAmt/DECODE(CostAverageCumQty, 0,1, CostAverageCumQty) "
						+ "SET CostAverage = CostAverageCumAmt/(CASE WHEN CostAverageCumQty=0 THEN 1 ELSE CostAverageCumQty END)"
						+ "WHERE C_AcctSchema_ID=").append(C_AcctSchema_ID)
				.append(" AND M_Product_ID=").append(getM_Product_ID());
		no = DB.executeUpdate(sql.toString(), getTrxName());
		log.fine("M_Product_Costing - AvgCost Updated #=" + no);

		// Update Current Cost
		if (!standardCosting)
		{
			sql = new StringBuilder(
					"UPDATE M_Product_Costing "
							+ "SET CurrentCostPrice = CostAverage "
							+ "WHERE C_AcctSchema_ID=").append(C_AcctSchema_ID)
					.append(" AND M_Product_ID=").append(getM_Product_ID());
			no = DB.executeUpdate(sql.toString(), getTrxName());
			log.fine("M_Product_Costing - CurrentCost Updated=" + no);
		}
		return true;
	}   // updateProductInfo
	
	private final int getInvoiceCurrencyId()
	{
		return this.invoiceCurrencyId;
	}

	/** @return Invoice line net amount, excluding taxes, in invoice's currency */
	private final BigDecimal getInvoiceLineNetAmt()
	{
		return this.invoiceLineNetAmt;
	}
	
	public final ICurrencyConversionContext getInvoiceCurrencyConversionCtx()
	{
		if (invoiceCurrencyConversionCtx == null)
		{
			final I_C_Invoice invoice = m_invoiceLine.getC_Invoice();
			Check.assumeNotNull(invoice, "invoice not null");
			final ICurrencyConversionBL currencyConversionBL = Services.get(ICurrencyConversionBL.class);
			invoiceCurrencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
					invoice.getDateAcct(),
					invoice.getC_ConversionType_ID(),
					invoice.getAD_Client_ID(),
					invoice.getAD_Org_ID());
		}
		return invoiceCurrencyConversionCtx;
	}

}   // Doc_MatchInv
