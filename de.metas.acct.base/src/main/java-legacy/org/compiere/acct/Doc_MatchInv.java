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

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
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
import org.compiere.util.Env;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.product.IProductBL;
import de.metas.tax.api.ITaxBL;

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
	private boolean isCreditMemoInvoice;

	/** Material Receipt */
	private I_M_InOutLine m_receiptLine = null;

	private ProductCost m_pc = null;

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
			this.isCreditMemoInvoice = invoiceBL.isCreditMemo(invoice);

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
					log.debug("LineNetAmt={} - LineTaxAmt={}", new Object[] { invoiceLineNetAmt, lineTaxAmt });
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
				|| getQtyInvoiced().signum() == 0) // 08643 avoid division by zero further down; note that we don't really know if and what to book in this case.
		{
			log.debug("No Product/Qty - M_Product_ID=" + getM_Product_ID()
					+ ",Qty=" + getQty() + ",InOutQty=" + m_receiptLine.getMovementQty() + ",InvoiceQty=" + getQtyInvoiced());
			return facts;
		}

		// create Fact Header
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		/**
		 * Needs to be handled in PO Matching as no Receipt info if (m_pc.isService()) { log.debug("Service - skipped"); return fact; }
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
		// Set AmtAcctCr/Dr from Receipt (sets also Project)
		if (!dr.updateReverseLine(I_M_InOut.Table_ID, 		// Amt updated
				m_receiptLine.getM_InOut_ID(),
				m_receiptLine.getM_InOutLine_ID(),
				receiptQtyMultiplier)
				)
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
					.setDetailMessage("Material Receipt (Line M_InOutLine_ID=" + m_receiptLine.getM_InOutLine_ID() + ") not posted yet");
		}
		if (log.isDebugEnabled())
			log.debug("DR - Amt(" + dr.getAcctBalance() + ") - " + dr.toString());

		//
		// InventoryClearing CR
		// From Invoice
		final MAccount expense = m_pc.getAccount(m_pc.isService() ? ProductCost.ACCTTYPE_P_Expense : ProductCost.ACCTTYPE_P_InventoryClearing, as);
		BigDecimal LineNetAmt = getInvoiceLineNetAmt();

		final BigDecimal invoiceQtyMultiplier = getQty()
				.divide(getQtyInvoiced(), 12, BigDecimal.ROUND_HALF_UP);

		if (invoiceQtyMultiplier.compareTo(Env.ONE) != 0)
		{
			LineNetAmt = LineNetAmt.multiply(invoiceQtyMultiplier);
		}
		if (m_pc.isService())
		{
			// TODO: evaluate if this logic is correct
			LineNetAmt = dr.getAcctBalance();	// book out exact receipt amt
		}
		// In case we are dealing with a credit memo invoice, we need to negate the amount, else the inventory clearing account won't be balanced.
		if (isCreditMemoInvoice())
		{
			LineNetAmt = LineNetAmt.negate();
		}

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
				log.debug("Line Net Amt=0 - M_Product_ID=" + getM_Product_ID() + ",Qty=" + getQty() + ",InOutQty=" + m_receiptLine.getMovementQty());
				createFacts_InvoicePriceVariance(fact, dr, cr);

				facts.add(fact);
				return facts;
			}
			cr.setQty(getQty().negate());

			if (log.isDebugEnabled())
				log.debug("CR - Amt(" + cr.getAcctBalance() + ") - " + cr.toString());
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
			cr.setQty(getQty().negate());
		}
		updateFromInvoiceLine(cr);

		// AZ Goodwill
		// Desc: Source Not Balanced problem because Currency is Difference - PO=CNY but AP=USD
		// see also Fact.java: checking for isMultiCurrency()
		if (dr.getC_Currency_ID() != cr.getC_Currency_ID())
		{
			setIsMultiCurrency(true);
		}

		// Avoid usage of clearing accounts
		// If both accounts Not Invoiced Receipts and Inventory Clearing are equal
		// then remove the posting
		if (!as.isPostIfClearingEqual())
		{
			final MAccount acct_dr = dr.getAccount(); // not_invoiced_receipts
			final MAccount acct_cr = cr.getAccount(); // inventory_clearing

			if (acct_dr.equals(acct_cr) && (!isInterOrg(as)))
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
		createFacts_InvoicePriceVariance(fact, dr, cr);

		//
		facts.add(fact);

		//
		// Commitment release
		if (as.isAccrual() && as.isCreatePOCommitment())
		{
			final Fact factCommitment = Doc_Order.createFact_CommitmentPurchaseRelease(as, this,
					getQty(), m_invoiceLine.getC_InvoiceLine_ID(), Env.ONE);
			if (factCommitment == null)
				return null;
			facts.add(factCommitment);
		}	// Commitment

		return facts;
	}   // createFact

	/**
	 * Create the InvoicePriceVariance fact line
	 * @param fact
	 * @param dr {@link Doc#ACCTTYPE_NotInvoicedReceipts} line (InOut)
	 * @param cr {@link ProductCost#ACCTTYPE_P_InventoryClearing} line (Invoice)
	 */
	private final void createFacts_InvoicePriceVariance(final Fact fact, final FactLine dr, final FactLine cr)
	{
		Check.assumeNotNull(fact, "fact not null");
		Check.assumeNotNull(dr, "dr not null");
		// Check.assumeNotNull(cr, "cr not null"); // CR can be null

		final MAcctSchema as = fact.getAcctSchema();

		//
		// Determine the InvoicePriceVariance Amount and currency
		final BigDecimal ipvAmount;
		final int ipvCurrencyId;
		// Case: the inventory clearing line is null (i.e. ZERO invoiced amount)
		if (cr == null)
		{
			ipvAmount = dr.getSourceBalance().negate();
			ipvCurrencyId = getInvoiceCurrencyId();
		}
		// Case: both lines are not null
		else
		{
			ipvAmount = cr.getAcctBalance().add(dr.getAcctBalance()).negate();
			ipvCurrencyId = as.getC_Currency_ID();
		}

		// If there is no invoice price variance => do nothing
		if (ipvAmount.signum() == 0)
		{
			return;
		}

		//
		// Create the invoice price variance fact line, if needed
		final FactLine ipvFactLine = fact.createLine(null, m_pc.getAccount(ProductCost.ACCTTYPE_P_IPV, as), ipvCurrencyId, ipvAmount);

		//
		// In case the DR line (InOut - NotInvoicedReceipts) is zero,
		// make sure sure our IPV line is not on the same DR/CR side as the CR line (Invoice - InventoryClearing)
		if(dr.isZeroAmtSource()
				&& cr != null && cr.isSameAmtSourceDrCrSideAs(ipvFactLine))
		{
			ipvFactLine.invertDrAndCrAmounts();
			ipvFactLine.negateDrAndCrAmounts();
		}

		updateFromInvoiceLine(ipvFactLine);

		if (log.isDebugEnabled())
			log.debug("IPV=" + ipvAmount + "; Balance=" + fact.getSourceBalance());
	}

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

	private final int getInvoiceCurrencyId()
	{
		return this.invoiceCurrencyId;
	}

	/** @return Invoice line net amount, excluding taxes, in invoice's currency */
	private final BigDecimal getInvoiceLineNetAmt()
	{
		return this.invoiceLineNetAmt;
	}

	/** @return total qty that was invoiced by linked invoice line */
	private final BigDecimal getQtyInvoiced()
	{
		return m_invoiceLine.getQtyInvoiced();
	}

	public final ICurrencyConversionContext getInvoiceCurrencyConversionCtx()
	{
		if (invoiceCurrencyConversionCtx == null)
		{
			final I_C_Invoice invoice = m_invoiceLine.getC_Invoice();
			Check.assumeNotNull(invoice, "invoice not null");
			final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
			invoiceCurrencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
					invoice.getDateAcct(),
					invoice.getC_ConversionType_ID(),
					invoice.getAD_Client_ID(),
					invoice.getAD_Org_ID());
		}
		return invoiceCurrencyConversionCtx;
	}

	private final boolean isCreditMemoInvoice()
	{
		return isCreditMemoInvoice;
	}

	/**
	 * Updates dimensions and UOM of given FactLine from invoice line.
	 *
	 * @param fl
	 */
	private final void updateFromInvoiceLine(final FactLine fl)
	{
		if (fl == null)
		{
			return;
		}

		fl.setC_Activity_ID(m_invoiceLine.getC_Activity_ID());
		fl.setC_Campaign_ID(m_invoiceLine.getC_Campaign_ID());
		fl.setC_Project_ID(m_invoiceLine.getC_Project_ID());
		fl.setC_UOM_ID(m_invoiceLine.getPrice_UOM_ID());
		fl.setUser1_ID(m_invoiceLine.getUser1_ID());
		fl.setUser2_ID(m_invoiceLine.getUser2_ID());

	}

}   // Doc_MatchInv
