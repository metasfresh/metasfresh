/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.inout.IInOutBL;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MTax;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

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
public class Doc_MatchInv extends Doc<DocLine_MatchInv>
{
	// services
	private static final Logger logger = LogManager.getLogger(Doc_MatchInv.class);
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient ITaxBL taxBL = Services.get(ITaxBL.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

	/** pseudo line */
	private DocLine_MatchInv docLine = null;

	private I_C_InvoiceLine _invoiceLine = null;
	private CurrencyId invoiceCurrencyId;
	/** Invoice line net amount, excluding taxes, in invoice's currency */
	private BigDecimal invoiceLineNetAmt = null;
	private CurrencyConversionContext invoiceCurrencyConversionCtx;
	private boolean isCreditMemoInvoice;

	/** Material Receipt */
	private I_M_InOutLine _receiptLine = null;

	public Doc_MatchInv(final AcctDocContext ctx)
	{
		super(ctx, DOCTYPE_MatMatchInv);
	}

	@Override
	protected void loadDocumentDetails()
	{
		final I_M_MatchInv matchInv = getM_MatchInv();
		setNoCurrency();
		setDateDoc(matchInv.getDateTrx());

		docLine = new DocLine_MatchInv(matchInv, this);

		// Invoice Info
		{
			_invoiceLine = InterfaceWrapperHelper.create(matchInv.getC_InvoiceLine(), I_C_InvoiceLine.class);
			final I_C_Invoice invoice = _invoiceLine.getC_Invoice();
			this.isCreditMemoInvoice = invoiceBL.isCreditMemo(invoice);

			// BP for NotInvoicedReceipts
			setBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()));

			invoiceCurrencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
			invoiceLineNetAmt = _invoiceLine.getLineNetAmt();

			// Correct included Tax
			final boolean taxIncluded = invoiceBL.isTaxIncluded(_invoiceLine);
			final int C_Tax_ID = _invoiceLine.getC_Tax_ID();
			if (taxIncluded && C_Tax_ID > 0)
			{
				final MTax tax = MTax.get(Env.getCtx(), C_Tax_ID);
				if (!tax.isZeroTax())
				{
					final int taxPrecision = getStdPrecision();
					final BigDecimal lineTaxAmt = taxBL.calculateTax(tax, invoiceLineNetAmt, true, taxPrecision);
					logger.debug("LineNetAmt={} - LineTaxAmt={}", invoiceLineNetAmt, lineTaxAmt);
					invoiceLineNetAmt = invoiceLineNetAmt.subtract(lineTaxAmt);
				}
			}	// correct included Tax

		}

		// Receipt info
		_receiptLine = matchInv.getM_InOutLine();
	}

	public I_M_MatchInv getM_MatchInv()
	{
		return getModel(I_M_MatchInv.class);
	}

	private Quantity getQty()
	{
		return docLine.getQty();
	}

	/** @return zero (always balanced) */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for MXI. (single line)
	 *
	 * <pre>
	 *      NotInvoicedReceipts     DR			(Receipt Org)
	 *      InventoryClearing               CR
	 *      InvoicePV               DR      CR  (difference)
	 * </pre>
	 *
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		//
		// Match invoice on sales side has no accounting consequences
		// because there are no intermediare accounting to be cleared (08529)
		if (isSOTrx())
		{
			return ImmutableList.of();
		}

		//
		// Cash based accounting not supported
		if (!as.isAccrual())
		{
			throw newPostingException().setAcctSchema(as).setDetailMessage("Cash based accounting not supported");
		}

		//
		// Skip not stockable (e.g. service products) because they have no cost
		final ProductId productId = ProductId.ofRepoIdOrNull(getM_MatchInv().getM_Product_ID());
		if (!productBL.isStocked(productId))
		{
			return ImmutableList.of();
		}

		//
		// Zero quantity
		if (getQty().signum() == 0
				|| getQtyReceived().signum() == 0	// Qty = 0
				|| getQtyInvoiced().signum() == 0) // 08643 avoid division by zero further down; note that we don't really know if and what to book in this case.
		{
			return ImmutableList.of();
		}

		// create Fact Header
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, PostingType.Actual);
		facts.add(fact);
		setC_Currency_ID(as.getCurrencyId());

		final CostAmount costs = getCreateCostDetails(as);

		//
		// NotInvoicedReceipt DR
		// From Receipt
		final FactLine dr_NotInvoicedReceipts = fact.createLine()
				.setAccount(getAccount(AccountType.NotInvoicedReceipts, as))
				.setCurrencyId(costs.getCurrencyId())
				.setAmtSource(costs.getValue(), null)
				.setQty(getQty())
				.buildAndAdd();
		updateFromReceiptLine(dr_NotInvoicedReceipts);

		//
		// InventoryClearing CR
		// From Invoice
		final FactLine cr_InventoryClearing = fact.createLine()
				.setAccount(docLine.getInventoryClearingAccount(as))
				.setCurrencyId(getInvoiceCurrencyId())
				.setCurrencyConversionCtx(getInvoiceCurrencyConversionCtx())
				.setAmtSource(null, getInvoiceLineMatchedAmt())
				.setQty(getQty().negate())
				.buildAndAdd();
		updateFromInvoiceLine(cr_InventoryClearing);

		//
		// AZ Goodwill
		// Desc: Source Not Balanced problem because Currency is Difference - PO=CNY but AP=USD
		// see also Fact.java: checking for isMultiCurrency()
		if (dr_NotInvoicedReceipts != null
				&& cr_InventoryClearing != null
				&& !CurrencyId.equals(dr_NotInvoicedReceipts.getCurrencyId(), cr_InventoryClearing.getCurrencyId()))
		{
			setIsMultiCurrency(true);
		}

		//
		// Avoid usage of clearing accounts
		// If both accounts Not Invoiced Receipts and Inventory Clearing are equal
		// then remove the posting
		PostingEqualClearingAccontsUtils.removeFactLinesIfEqual(fact, dr_NotInvoicedReceipts, cr_InventoryClearing, this::isInterOrg);

		//
		// Invoice Price Variance difference
		createFacts_InvoicePriceVariance(fact, dr_NotInvoicedReceipts, cr_InventoryClearing);

		return facts;
	}   // createFact

	/**
	 * Create the InvoicePriceVariance fact line
	 *
	 * @param fact
	 * @param dr_NotInvoicedReceipts
	 * @param cr_InventoryClearing
	 */
	private final void createFacts_InvoicePriceVariance(
			@NonNull final Fact fact,
			@Nullable final FactLine dr_NotInvoicedReceipts,
			@Nullable final FactLine cr_InventoryClearing)
	{
		if (dr_NotInvoicedReceipts == null && cr_InventoryClearing == null)
		{
			return;
		}

		final AcctSchema as = fact.getAcctSchema();

		//
		// Determine the InvoicePriceVariance Amount and currency
		final BigDecimal ipvAmount;
		final CurrencyId ipvCurrencyId;

		// Case: the not invoiced receipts line is null (i.e. ZERO costs)
		if (dr_NotInvoicedReceipts == null)
		{
			ipvAmount = cr_InventoryClearing.getSourceBalance().toBigDecimal();
			ipvCurrencyId = cr_InventoryClearing.getCurrencyId();
		}
		// Case: the inventory clearing line is null (i.e. ZERO invoiced amount)
		else if (cr_InventoryClearing == null)
		{
			ipvAmount = dr_NotInvoicedReceipts.getSourceBalance().toBigDecimal().negate();
			ipvCurrencyId = dr_NotInvoicedReceipts.getCurrencyId();
		}
		// Case: both lines are not null and same currency
		else if (CurrencyId.equals(dr_NotInvoicedReceipts.getCurrencyId(), cr_InventoryClearing.getCurrencyId()))
		{
			ipvAmount = cr_InventoryClearing.getSourceBalance().add(dr_NotInvoicedReceipts.getSourceBalance()).toBigDecimal().negate();
			ipvCurrencyId = cr_InventoryClearing.getCurrencyId();
		}
		// Case: both lines are not null but different currency
		else
		{
			ipvAmount = cr_InventoryClearing.getAcctBalance().add(dr_NotInvoicedReceipts.getAcctBalance()).toBigDecimal().negate();
			ipvCurrencyId = as.getCurrencyId();
		}

		// If there is no invoice price variance => do nothing
		if (ipvAmount.signum() == 0)
		{
			return;
		}

		//
		// Create the invoice price variance fact line, if needed
		// InvoicePriceVariance DR/CR
		final FactLine ipvFactLine = fact.createLine(
				null,
				docLine.getInvoicePriceVarianceAccount(as),
				ipvCurrencyId,
				ipvAmount);

		//
		// In case the DR line (InOut - NotInvoicedReceipts) is zero,
		// make sure sure our IPV line is not on the same DR/CR side as the CR line (Invoice - InventoryClearing)
		if (dr_NotInvoicedReceipts.isZeroAmtSource()
				&& cr_InventoryClearing != null
				&& cr_InventoryClearing.isSameAmtSourceDrCrSideAs(ipvFactLine))
		{
			ipvFactLine.invertDrAndCrAmounts();
			ipvFactLine.negateDrAndCrAmounts();
		}

		updateFromInvoiceLine(ipvFactLine);
	}

	/**
	 * Verify if the posting involves two or more organizations
	 *
	 * @return true if there are more than one org involved on the posting
	 */
	private boolean isInterOrg(final AcctSchema as)
	{
		final AcctSchemaElement orgElement = as.getSchemaElementByType(AcctSchemaElementType.Organization);
		if (orgElement == null || !orgElement.isBalanced())
		{
			// no org element or not need to be balanced
			return false;
		}

		// verify if org of receipt line is different from org of invoice line
		return getInvoice_Org_ID() != getReceipt_Org_ID();
	}

	private I_C_InvoiceLine getInvoiceLine()
	{
		return _invoiceLine;
	}

	private final int getInvoice_Org_ID()
	{
		return getInvoiceLine().getAD_Org_ID();
	}

	private final CurrencyId getInvoiceCurrencyId()
	{
		return this.invoiceCurrencyId;
	}

	/** @return total invoice line net amount, excluding taxes, in invoice's currency */
	private final BigDecimal getInvoiceLineNetAmt()
	{
		return this.invoiceLineNetAmt;
	}

	private final BigDecimal getInvoiceLineMatchedAmt()
	{
		BigDecimal lineNetAmt = getInvoiceLineNetAmt();
		final BigDecimal qtyInvoicedMultiplier = getQtyInvoicedMultiplier();
		if (qtyInvoicedMultiplier.compareTo(BigDecimal.ONE) != 0)
		{
			lineNetAmt = lineNetAmt.multiply(qtyInvoicedMultiplier);
		}

		// In case we are dealing with a credit memo invoice, we need to negate the amount, else the inventory clearing account won't be balanced.
		if (isCreditMemoInvoice())
		{
			lineNetAmt = lineNetAmt.negate();
		}

		return lineNetAmt;
	}

	private BigDecimal getQtyInvoicedMultiplier()
	{
		final BigDecimal qtyInvoiced = getQtyInvoiced();
		if (qtyInvoiced.signum() != 0) // task 08337: guard against division by zero
		{
			return getQty().divide(qtyInvoiced, 12, RoundingMode.HALF_UP).toBigDecimal();
		}
		else
		{
			return BigDecimal.ZERO;
		}
	}

	/** @return total qty that was invoiced by linked invoice line */
	private final BigDecimal getQtyInvoiced()
	{
		return getInvoiceLine().getQtyInvoiced();
	}

	private final I_M_InOutLine getReceiptLine()
	{
		return _receiptLine;
	}

	private final int getReceipt_Org_ID()
	{
		return getReceiptLine().getAD_Org_ID();
	}

	/** @return total qty that was received by linked receipt line */
	private final BigDecimal getQtyReceived()
	{
		return getReceiptLine().getMovementQty();
	}

	public final CurrencyConversionContext getInvoiceCurrencyConversionCtx()
	{
		if (invoiceCurrencyConversionCtx == null)
		{
			final I_C_InvoiceLine invoiceLine = getInvoiceLine();
			final I_C_Invoice invoice = invoiceLine.getC_Invoice();
			Check.assumeNotNull(invoice, "invoice not null");
			invoiceCurrencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
					TimeUtil.asLocalDate(invoice.getDateAcct()),
					CurrencyConversionTypeId.ofRepoIdOrNull(invoice.getC_ConversionType_ID()),
					ClientId.ofRepoId(invoice.getAD_Client_ID()),
					OrgId.ofRepoId(invoice.getAD_Org_ID()));
		}
		return invoiceCurrencyConversionCtx;
	}

	private final boolean isCreditMemoInvoice()
	{
		return isCreditMemoInvoice;
	}

	/** Updates dimensions and UOM of given FactLine from invoice line */
	private final void updateFromInvoiceLine(@Nullable final FactLine fl)
	{
		if (fl == null)
		{
			return;
		}

		final I_C_InvoiceLine invoiceLine = getInvoiceLine();
		fl.setC_Activity_ID(invoiceLine.getC_Activity_ID());
		fl.setC_Campaign_ID(invoiceLine.getC_Campaign_ID());
		fl.setC_Project_ID(invoiceLine.getC_Project_ID());
		fl.setC_UOM_ID(firstGreaterThanZero(invoiceLine.getPrice_UOM_ID(), invoiceLine.getC_UOM_ID()));
		fl.setUser1_ID(invoiceLine.getUser1_ID());
		fl.setUser2_ID(invoiceLine.getUser2_ID());
	}

	private final void updateFromReceiptLine(@Nullable FactLine fl)
	{
		if (fl == null)
		{
			return;
		}

		final I_M_InOutLine receiptLine = getReceiptLine();
		fl.setAD_OrgTrx_ID(receiptLine.getAD_OrgTrx_ID());
		fl.setC_Project_ID(receiptLine.getC_Project_ID());
		fl.setC_Activity_ID(receiptLine.getC_Activity_ID());
		fl.setC_Campaign_ID(receiptLine.getC_Campaign_ID());
		// fl.setC_SalesRegion_ID(receiptLine.getC_SalesRegion_ID());
		// fl.setC_LocFrom_ID(receiptLine.getC_LocFrom_ID());
		// fl.setC_LocTo_ID(receiptLine.getC_LocTo_ID());
		// fl.setM_Product_ID(receiptLine.getM_Product_ID());
		fl.setM_Locator_ID(receiptLine.getM_Locator_ID());
		fl.setUser1_ID(receiptLine.getUser1_ID());
		fl.setUser2_ID(receiptLine.getUser2_ID());
		fl.setC_UOM_ID(receiptLine.getC_UOM_ID());
		// Org for cross charge
		fl.setAD_Org_ID(receiptLine.getAD_Org_ID());
	}

	private CostAmount getCreateCostDetails(final AcctSchema as)
	{
		Check.assume(!isSOTrx(), "Cannot create cost details for sales match invoice");

		final IInOutBL inOutBL = Services.get(IInOutBL.class);

		final BigDecimal matchAmt = getInvoiceLineMatchedAmt();
		final CurrencyId currentId = getInvoiceCurrencyId();
		final CurrencyConversionContext currencyConvCtx = getInvoiceCurrencyConversionCtx();

		final I_M_InOutLine receiptLine = getReceiptLine();
		final I_M_InOut receipt = receiptLine.getM_InOut();
		final boolean isReturnTrx = inOutBL.isReturnMovementType(receipt.getMovementType());
		final Quantity matchQty = isReturnTrx ? getQty().negate() : getQty();

		final I_M_MatchInv matchInv = getM_MatchInv();

		return services
				.createCostDetail(CostDetailCreateRequest.builder()
						.acctSchemaId(as.getId())
						.clientId(getClientId())
						.orgId(getOrgId())
						.productId(getProductId())
						.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(matchInv.getM_AttributeSetInstance_ID()))
						.documentRef(CostingDocumentRef.ofMatchInvoiceId(matchInv.getM_MatchInv_ID()))
						.qty(matchQty)
						.amt(CostAmount.of(matchAmt, currentId))
						.currencyConversionTypeId(currencyConvCtx.getConversionTypeId())
						.date(currencyConvCtx.getConversionDate())
						.description(getDescription())
						.build())
				.getTotalAmountToPost(as);
	}

}   // Doc_MatchInv
