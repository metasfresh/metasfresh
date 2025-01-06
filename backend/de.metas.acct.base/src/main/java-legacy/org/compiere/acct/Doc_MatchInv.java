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
import de.metas.acct.accounts.BPartnerGroupAccountType;
import de.metas.acct.accounts.CostElementAccountType;
import de.metas.acct.accounts.InvoiceAccountProviderExtension;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocBaseType;
import de.metas.document.dimension.Dimension;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvCostPart;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.material.MovementType;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.costs.inout.InOutCost;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Post MatchInv Documents.
 *
 * <pre>
 *  Table:              M_MatchInv (472)
 *  Document Types:     MXI
 * </pre>
 * <p>
 * Update Costing Records
 *
 * @author Jorg Janke
 * <p>
 * FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual Avoid posting if both accounts Not Invoiced Receipts and Inventory Clearing are equal BF [
 * 2789949 ] Multicurrency in matching posting
 */
public class Doc_MatchInv extends Doc<DocLine_MatchInv>
{
	// services
	private static final Logger logger = LogManager.getLogger(Doc_MatchInv.class);
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	@Getter(AccessLevel.PACKAGE)
	private final MatchInv matchInv;
	/**
	 * pseudo line
	 */
	private DocLine_MatchInv docLine = null;

	private I_C_InvoiceLine _invoiceLine = null;
	private I_C_Invoice _invoice = null;
	@Getter(AccessLevel.PRIVATE)
	private BPartnerId invoiceBPartnerId;
	private boolean isCreditMemoInvoice;
	private boolean isReversal;
	/**
	 * Invoice line net amount, excluding taxes, in invoice's currency
	 */
	private Money invoiceLineNetAmt = null;

	/**
	 * Material Receipt
	 */
	private I_M_InOutLine _receiptLine = null;
	private I_M_InOut _receipt = null;
	@Getter(AccessLevel.PRIVATE)
	private BPartnerId receiptBPartnerId = null;

	private CurrencyConversionContext _invoiceCurrencyConversionCtx = null;
	private CurrencyConversionContext _inoutCurrencyConversionCtx = null;

	public Doc_MatchInv(final AcctDocContext ctx)
	{
		super(ctx, DocBaseType.MatchInvoice);
		this.matchInv = MatchInvoiceRepository.fromRecord(ctx.getDocumentModel().unboxAs(I_M_MatchInv.class));

	}

	@Nullable
	@Override
	protected InvoiceAccountProviderExtension createAccountProviderExtension()
	{
		final InvoiceAndLineId invoiceAndLineId = getInvoiceLineId();
		return services.getInvoiceAcct(invoiceAndLineId.getInvoiceId())
				.map(invoiceAccounts -> InvoiceAccountProviderExtension.builder()
						.accountDAO(services.getAccountDAO())
						.invoiceAccounts(invoiceAccounts)
						.clientId(getClientId())
						.invoiceAndLineId(invoiceAndLineId)
						.build())
				.orElse(null);
	}

	@Override
	protected void loadDocumentDetails()
	{
		setNoCurrency();
		setDateDoc(InstantAndOrgId.ofInstant(matchInv.getDateTrx(), matchInv.getOrgId()));

		docLine = new DocLine_MatchInv(this);

		// Invoice Info
		{
			_invoiceLine = invoiceBL.getLineById(matchInv.getInvoiceAndLineId());
			_invoice = invoiceBL.getById(InvoiceId.ofRepoId(_invoiceLine.getC_Invoice_ID()));
			this.invoiceBPartnerId = BPartnerId.ofRepoId(_invoice.getC_BPartner_ID());
			this.isCreditMemoInvoice = invoiceBL.isCreditMemo(_invoice);
			this.isReversal = invoiceBL.isReversal(_invoice);
			this.invoiceLineNetAmt = computeInvoiceLineNetAmt(_invoiceLine, _invoice);

			// BP for NotInvoicedReceipts
			setBPartnerId(this.invoiceBPartnerId);
		}

		// Receipt info
		_receiptLine = inOutBL.getLineByIdInTrx(matchInv.getInoutLineId());
		_receipt = inOutBL.getById(InOutId.ofRepoId(_receiptLine.getM_InOut_ID()));

		final MatchInvType type = matchInv.getType();
		if (type.isCost())
		{
			final InOutCost inoutCost = services.getOrderCostService().getInOutCostsById(matchInv.getCostPartNotNull().getInoutCostId());
			this.receiptBPartnerId = inoutCost.getBpartnerId();
		}
		else
		{
			this.receiptBPartnerId = BPartnerId.ofRepoId(_receipt.getC_BPartner_ID());
		}
	}

	private Money computeInvoiceLineNetAmt(
			@NonNull final org.compiere.model.I_C_InvoiceLine invoiceLine,
			@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		BigDecimal invoiceLineNetAmtBD = invoiceLine.getLineNetAmt();

		// Correct included Tax
		final Tax tax = services.getTaxById(TaxId.ofRepoId(invoiceLine.getC_Tax_ID()));
		if (invoiceBL.isTaxIncluded(invoice, tax))
		{
			if (!tax.isZeroTax())
			{
				final CurrencyPrecision taxPrecision = getStdPrecision();
				final BigDecimal lineTaxAmt = tax.calculateTax(invoiceLineNetAmtBD, true, taxPrecision.toInt()).getTaxAmount();
				logger.debug("LineNetAmt={} - LineTaxAmt={}", invoiceLineNetAmtBD, lineTaxAmt);
				invoiceLineNetAmtBD = invoiceLineNetAmtBD.subtract(lineTaxAmt);
			}
		}

		return Money.of(invoiceLineNetAmtBD, CurrencyId.ofRepoId(invoice.getC_Currency_ID()));
	}

	I_M_MatchInv getMatchInvRecord()
	{
		return getModel(I_M_MatchInv.class);
	}

	private Quantity getQty()
	{
		return docLine.getQty();
	}

	/**
	 * @return zero (always balanced)
	 */
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
		// because there are no clearing accounts to be cleared (08529)
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
		// Skip not stocked (e.g. service products) because they have no cost
		if (!productBL.isStocked(matchInv.getProductId()))
		{
			return ImmutableList.of();
		}

		final Fact fact = new Fact(this, as, PostingType.Actual);
		setC_Currency_ID(as.getCurrencyId());

		final CostElementId costElementId = getCostElementId();
		final CostAmountDetailed costs = getCreateCostDetails(as);

		//
		// NotInvoicedReceipt DR
		// From Receipt
		final Money receiptAmt = costs.getAmountBeforeAdjustment().toMoney();
		final I_M_InOutLine receiptLine = getReceiptLine();
		final FactLine dr_NotInvoicedReceipts = fact.createLine()
				.setAccount(costElementId != null
						? getCostElementAccount(as, costElementId, CostElementAccountType.P_CostClearing_Acct)
						: getBPGroupAccount(BPartnerGroupAccountType.NotInvoicedReceipts, as))
				.setCurrencyConversionCtx(getInOutCurrencyConversionCtx())
				.setAmtSource(receiptAmt, null)
				.setQty(getQty())
				.costElement(costElementId)
				.bpartnerId(getReceiptBPartnerId())
				.orgId(OrgId.ofRepoIdOrAny(receiptLine.getAD_Org_ID()))
				.orgTrxId(OrgId.ofRepoIdOrAny(receiptLine.getAD_OrgTrx_ID()))
				.locatorId(receiptLine.getM_Locator_ID())
				.buildAndAdd();

		//dr_NotInvoicedReceipts.setC_UOM_ID(UomId.ofRepoId(receiptLine.getC_UOM_ID()));
		dr_NotInvoicedReceipts.setFromDimension(services.extractDimensionFromModel(receiptLine));

		//
		// InventoryClearing CR
		// From Invoice
		final Money invoiceLineMatchedAmt = getInvoiceLineMatchedAmt();
		final FactLine cr_InventoryClearing = fact.createLine()
				.setAccount(docLine.getInventoryClearingAccount(as))
				.setCurrencyConversionCtx(getInvoiceCurrencyConversionCtx())
				.setAmtSource((Money)null, invoiceLineMatchedAmt)
				.setQty(getQty())
				.costElement(costElementId)
				.bpartnerId(getInvoiceBPartnerId())
				.buildAndAdd();
		updateFromInvoiceLine(cr_InventoryClearing);

		//
		// P_Asset CR/DR
		// (cost adjustment)
		if (!costs.getCostAdjustmentAmt().isZero())
		{
			final Balance costAdjustmentBalance = toDebitBalanceIfPositive(costs.getCostAdjustmentAmt());
			fact.createLine()
					.setAccount(docLine.getAccount(ProductAcctType.P_Asset_Acct, as))
					.setCurrencyConversionCtx(getInvoiceCurrencyConversionCtx())
					.setAmtSource(costAdjustmentBalance)
					.setQty(getQty())
					.costElement(costElementId)
					.bpartnerId(getReceiptBPartnerId())
					.buildAndAdd();
		}

		//
		// P_COGS CR/DR
		// (already shipped)
		if (!costs.getAlreadyShippedAmt().isZero())
		{
			final Balance alreadyShippedBalance = toDebitBalanceIfPositive(costs.getAlreadyShippedAmt());
			fact.createLine()
					.setAccount(docLine.getAccount(ProductAcctType.P_COGS_Acct, as))
					.setCurrencyConversionCtx(getInvoiceCurrencyConversionCtx())
					.setAmtSource(alreadyShippedBalance)
					.setQty(getQty())
					.costElement(costElementId)
					.bpartnerId(getReceiptBPartnerId())
					.buildAndAdd();
		}

		//
		// AZ Goodwill
		// Desc: Source Not Balanced problem because Currency is Difference - PO=CNY but AP=USD
		// see also Fact.java: checking for isMultiCurrency()
		if (dr_NotInvoicedReceipts != null
				&& cr_InventoryClearing != null
				&& !CurrencyId.equals(dr_NotInvoicedReceipts.getCurrencyId(), cr_InventoryClearing.getCurrencyId()))
		{
			setIsMultiCurrency();
		}

		//
		// Avoid usage of clearing accounts
		// If both accounts Not Invoiced Receipts and Inventory Clearing are equal
		// then remove the posting
		PostingEqualClearingAccontsUtils.removeFactLinesIfEqual(fact, dr_NotInvoicedReceipts, cr_InventoryClearing, this::isInterOrg);
		//

		//
		// Invoice Price Variance difference
		createFact_InvoicePriceVariance(fact);

		return ImmutableList.of(fact);
	}

	private void createFact_InvoicePriceVariance(@NonNull final Fact fact)
	{
		final Balance balance = fact.getAcctBalance();
		// If there is no invoice price variance => do nothing
		if (balance.isBalanced())
		{
			return;
		}

		final AcctSchema as = fact.getAcctSchema();

		//
		// Create the invoice price variance fact line, if needed
		// InvoicePriceVariance DR/CR
		final FactLine ipvFactLine = fact.createLine()
				.setDocLine(null)
				.setAccount(docLine.getInvoicePriceVarianceAccount(as))
				.setAmtSource(balance.toSingleSide().invert())
				.bpartnerId(getInvoiceBPartnerId())
				.costElement(getCostElementId())
				.buildAndAdd();

		updateFromInvoiceLine(ipvFactLine);
	}

	private Balance toDebitBalanceIfPositive(@NonNull final CostAmount amt)
	{
		return amt.signum() > 0
				? Balance.ofDebit(amt.toMoney()).negateAndInvertIf(isReversal)
				: Balance.ofCredit(amt.toMoney().negate()).negateAndInvertIf(isReversal);
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
		return !OrgId.equals(getInvoice_Org_ID(), getReceipt_Org_ID());
	}

	private InvoiceAndLineId getInvoiceLineId()
	{
		final I_C_InvoiceLine invoiceLine = getInvoiceLine();
		return InvoiceAndLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID());
	}

	private I_C_InvoiceLine getInvoiceLine()
	{
		return _invoiceLine;
	}

	private I_C_Invoice getInvoice()
	{
		return _invoice;
	}

	private OrgId getInvoice_Org_ID()
	{
		return OrgId.ofRepoId(getInvoiceLine().getAD_Org_ID());
	}

	/**
	 * @return total invoice line net amount, excluding taxes, in invoice's currency
	 */
	private Money getInvoiceLineNetAmt() {return this.invoiceLineNetAmt;}

	private Money getInvoiceLineMatchedAmt()
	{
		final MatchInvType type = matchInv.getType();
		if (type.isMaterial())
		{
			return getInvoiceLineNetAmt()
					.multiply(getQtyInvoicedMultiplier())
					.negateIf(isCreditMemoInvoice()); // In case we are dealing with a credit memo invoice, we need to negate the amount, else the inventory clearing account won't be balanced.
		}
		else if (type.isCost())
		{
			return matchInv.getCostPartNotNull().getCostAmountInvoiced();
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}
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

	/**
	 * @return total qty that was invoiced by linked invoice line
	 */
	private BigDecimal getQtyInvoiced()
	{
		return getInvoiceLine().getQtyInvoiced();
	}

	private I_M_InOutLine getReceiptLine()
	{
		return _receiptLine;
	}

	private I_M_InOut getReceipt()
	{
		return _receipt;
	}

	private OrgId getReceipt_Org_ID()
	{
		return OrgId.ofRepoId(getReceiptLine().getAD_Org_ID());
	}

	public final CurrencyConversionContext getInvoiceCurrencyConversionCtx()
	{
		CurrencyConversionContext invoiceCurrencyConversionCtx = this._invoiceCurrencyConversionCtx;
		if (invoiceCurrencyConversionCtx == null)
		{
			invoiceCurrencyConversionCtx = this._invoiceCurrencyConversionCtx = invoiceBL.getCurrencyConversionCtx(getInvoice());
		}
		return invoiceCurrencyConversionCtx;
	}

	public final CurrencyConversionContext getInOutCurrencyConversionCtx()
	{
		CurrencyConversionContext inoutCurrencyConversionCtx = this._inoutCurrencyConversionCtx;
		if (inoutCurrencyConversionCtx == null)
		{
			inoutCurrencyConversionCtx = this._inoutCurrencyConversionCtx = inOutBL.getCurrencyConversionContext(getReceipt());
		}
		return inoutCurrencyConversionCtx;
	}

	private boolean isCreditMemoInvoice()
	{
		return isCreditMemoInvoice;
	}

	/**
	 * Updates dimensions and UOM of given FactLine from invoice line
	 */
	private void updateFromInvoiceLine(@Nullable final FactLine fl)
	{
		if (fl == null)
		{
			return;
		}

		final I_C_InvoiceLine invoiceLine = getInvoiceLine();
		//fl.setC_UOM_ID(firstGreaterThanZero(invoiceLine.getPrice_UOM_ID(), invoiceLine.getC_UOM_ID()));

		final Dimension invoiceLineDimension = services.extractDimensionFromModel(invoiceLine);
		fl.setFromDimension(invoiceLineDimension);
	}

	private CostAmountDetailed getCreateCostDetails(final AcctSchema as)
	{
		Check.assume(!isSOTrx(), "Cannot create cost details for sales match invoice");

		final I_M_InOut receipt = getReceipt();
		final MovementType movementType = MovementType.ofCode(receipt.getMovementType());
		final Quantity qtyMatched = getQty().negateIf(movementType.isMaterialReturn());

		final MatchInvType type = matchInv.getType();
		final Money amtMatched;
		final CostElement costElement;
		if (type.isMaterial())
		{
			amtMatched = getInvoiceLineMatchedAmt();
			costElement = null;
		}
		else if (type.isCost())
		{
			final MatchInvCostPart costPart = matchInv.getCostPartNotNull();
			amtMatched = costPart.getCostAmountInvoiced();
			costElement = services.getCostElementById(costPart.getCostElementId());
		}
		else
		{
			throw new AdempiereException("Unhandled type: " + type);
		}

		return services.createCostDetail(
						CostDetailCreateRequest.builder()
								.acctSchemaId(as.getId())
								.clientId(matchInv.getClientId())
								.orgId(matchInv.getOrgId())
								.productId(matchInv.getProductId())
								.attributeSetInstanceId(matchInv.getAsiId())
								.documentRef(CostingDocumentRef.ofMatchInvoiceId(matchInv.getId()))
								.costElement(costElement)
								.qty(qtyMatched)
								.amt(CostAmount.ofMoney(amtMatched))
								.currencyConversionContext(inOutBL.getCurrencyConversionContext(receipt))
								.date(getDateAcctAsInstant())
								.description(getDescription())
								.build())
				.getTotalAmountToPost(as);
	}

	@Nullable
	private CostElementId getCostElementId()
	{
		final MatchInvType type = matchInv.getType();
		if (type.isMaterial())
		{
			return null;
		}
		else if (type.isCost())
		{
			return matchInv.getCostPartNotNull().getCostElementId();
		}
		else
		{
			throw new AdempiereException("Unhandled type: " + type);
		}
	}

}   // Doc_MatchInv
