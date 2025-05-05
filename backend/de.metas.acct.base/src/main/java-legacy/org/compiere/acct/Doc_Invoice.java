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
import de.metas.acct.Account;
import de.metas.acct.accounts.BPartnerCustomerAccountType;
import de.metas.acct.accounts.BPartnerVendorAccountType;
import de.metas.acct.accounts.InvoiceAccountProviderExtension;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.factacct_userchanges.FactAcctChangesApplier;
import de.metas.costing.ChargeId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceTax;
import de.metas.invoice.acct.InvoiceAcct;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MPeriod;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * Post Invoice Documents.
 *
 * <pre>
 *  Table:              C_Invoice (318)
 *  Document Types:     ARI, ARC, ARF, API, APC
 * </pre>
 *
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 * <li>BF: 2797257 Landed Cost Detail is not using allocation qty
 * @version $Id: Doc_Invoice.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
public class Doc_Invoice extends Doc<DocLine_Invoice>
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final MatchInvoiceService matchInvoiceService;
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final OrderGroupRepository orderGroupRepo;

	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_Invoice.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;

	/**
	 * Contained Optional Tax Lines
	 */
	private DocTaxesList _taxes = null; // lazy
	/**
	 * All lines are Service
	 */
	private boolean m_allLinesService = true;
	/**
	 * All lines are product item
	 */
	private boolean m_allLinesItem = true;
	private Optional<InvoiceAcct> _invoiceAccounts = null; // lazy

	private CurrencyConversionContext _invoiceCurrencyConversionCtx = null;

	public Doc_Invoice(@NonNull final AcctDocContext ctx)
	{
		this(ctx, SpringContextHolder.instance.getBean(OrderGroupRepository.class));
	}

	public Doc_Invoice(@NonNull final AcctDocContext ctx, @NonNull final OrderGroupRepository orderGroupRepo)
	{
		super(ctx);
		this.orderGroupRepo = orderGroupRepo;
		this.matchInvoiceService = ctx.getServices().getMatchInvoiceService();
	}

	Optional<InvoiceAcct> getInvoiceAccounts()
	{
		Optional<InvoiceAcct> invoiceAccounts = this._invoiceAccounts;
		if (invoiceAccounts == null)
		{
			invoiceAccounts = this._invoiceAccounts = services.getInvoiceAcct(getInvoiceId());
		}
		return invoiceAccounts;
	}

	@Nullable
	@Override
	protected InvoiceAccountProviderExtension createAccountProviderExtension()
	{
		return createInvoiceAccountProviderExtension(null);
	}

	InvoiceAccountProviderExtension createInvoiceAccountProviderExtension(@Nullable final InvoiceAndLineId invoiceAndLineId)
	{
		return getInvoiceAccounts()
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
		final I_C_Invoice invoice = getModel(I_C_Invoice.class);
		setDateDoc(invoice.getDateInvoiced());
		// Amounts
		setAmount(Doc.AMTTYPE_Gross, invoice.getGrandTotal());
		setAmount(Doc.AMTTYPE_Net, invoice.getTotalLines());
		setAmount(Doc.AMTTYPE_Charge, invoice.getChargeAmt());

		setDocLines(loadLines());
	}

	private DocTaxesList getTaxes()
	{
		if (_taxes == null)
		{
			_taxes = loadTaxes();
		}
		return _taxes;
	}

	private DocTaxesList loadTaxes()
	{
		final InvoiceId invoiceId = getInvoiceId();
		final ArrayList<DocTax> docTaxes = new ArrayList<>();
		for (final InvoiceTax invoiceTax : invoiceBL.getTaxes(invoiceId))
		{
			final Tax tax = services.getTaxById(invoiceTax.getTaxId());
			docTaxes.add(DocTax.builderFrom(tax)
					.accountProvider(getAccountProvider())
					.taxBaseAmt(invoiceTax.getTaxBaseAmt())
					.taxAmt(invoiceTax.getTaxAmt())
					.taxIncluded(invoiceTax.isTaxIncluded())
					.isReverseCharge(invoiceTax.isReverseCharge())
					.reverseChargeTaxAmt(invoiceTax.getReverseChargeTaxAmt())
					.build());
		}

		return new DocTaxesList(docTaxes);
	}    // loadTaxes

	private List<DocLine_Invoice> loadLines()
	{
		final InvoiceId invoiceId = getInvoiceId();

		final ArrayList<DocLine_Invoice> docLines = new ArrayList<>();
		for (final I_C_InvoiceLine line : invoiceBL.getLines(invoiceId))
		{
			// Skip invoice description lines
			if (line.isDescription())
			{
				continue;
			}

			final DocLine_Invoice docLine = new DocLine_Invoice(orderGroupRepo, line, this);

			//
			// Collect included tax (if any)
			final BigDecimal lineIncludedTaxAmt = docLine.getIncludedTaxAmt();
			if (lineIncludedTaxAmt.signum() != 0)
			{
				final DocTaxesList taxes = getTaxes();
				docLine.getTaxId()
						.flatMap(taxes::getByTaxId)
						.ifPresent(docTax -> docTax.addIncludedTax(lineIncludedTaxAmt));
			}

			//
			// Update all lines are services/all lines are items flags
			if (docLine.isItem())
			{
				m_allLinesService = false;
			}
			else
			{
				m_allLinesItem = false;
			}

			docLines.add(docLine);
		}

		// Included Tax - make sure that no difference
		for (final DocTax docTax : getTaxes())
		{
			if (!docTax.isTaxIncluded())
			{
				continue;
			}

			if (docTax.isIncludedTaxDifference())
			{
				final BigDecimal diff = docTax.getIncludedTaxDifference();
				for (final DocLine_Invoice docLine : docLines)
				{
					final TaxId taxId = docLine.getTaxId().orElse(null);
					if (taxId != null && taxId.getRepoId() == docTax.getC_Tax_ID())
					{
						docLine.setLineNetAmtDifference(diff);
						break;
					}
				}    // for all lines
			}    // tax difference
		}    // for all taxes

		//
		return docLines;
	}    // loadLines

	public InvoiceId getInvoiceId()
	{
		return InvoiceId.ofRepoId(get_ID());
	}

	public final boolean isCreditMemo()
	{
		return InvoiceDocBaseType.ofDocBaseType(getDocBaseType()).isCreditMemo();
	}

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *
	 * @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = BigDecimal.ZERO;

		// Total
		retValue = retValue.add(getAmount(Doc.AMTTYPE_Gross));

		// - Header Charge
		retValue = retValue.subtract(getAmount(Doc.AMTTYPE_Charge));

		// - Tax
		for (final DocTax docTax : getTaxes())
		{
			retValue = retValue.subtract(docTax.getTaxAmt());
		}

		// - Lines
		for (final DocLine_Invoice line : getDocLines())
		{
			retValue = retValue.subtract(line.getAmtSource());
		}

		return retValue;
	}

	private Fact newFact(@NonNull final AcctSchema as)
	{
		return new Fact(this, as, getPostingType())
				.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance)
				.setCurrencyConversionContext(getCurrencyConversionContext(as));
	}

	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		// Cash based accounting
		if (!as.isAccrual())
		{
			throw newPostingException().setAcctSchema(as).setDetailMessage("Cash based accounting is not supported");
		}

		// ** ARI, ARF
		final DocBaseType docBaseType = getDocBaseType();
		if (DocBaseType.ARInvoice.equals(docBaseType)
				|| DocBaseType.ARProFormaInvoice.equals(docBaseType))
		{
			return createFacts_SalesInvoice(as);
		}
		// ARC
		else if (DocBaseType.ARCreditMemo.equals(docBaseType))
		{
			return createFacts_SalesCreditMemo(as);
		}

		// ** API
		else if (DocBaseType.APInvoice.equals(docBaseType)
				|| InvoiceDocBaseType.AEInvoice.getDocBaseType().equals(docBaseType)  // metas-ts: treating commission/salary invoice like AP invoice
				|| InvoiceDocBaseType.AVInvoice.getDocBaseType().equals(docBaseType))   // metas-ts: treating invoice for recurrent payment like AP invoice
		{
			return createFacts_PurchaseInvoice(as);
		}
		// APC
		else if (DocBaseType.APCreditMemo.equals(docBaseType))
		{
			return createFacts_PurchaseCreditMemo(as);
		}
		else
		{
			throw newPostingException()
					.setAcctSchema(as)
					.setPostingStatus(PostingStatus.Error)
					.setDetailMessage("DocumentType unknown: " + docBaseType);
		}
	}

	/**
	 * <pre>
	 *  ARI, ARF
	 *      Receivables     DR
	 *      Charge                  CR
	 *      TaxDue                  CR
	 *      Revenue                 CR
	 * </pre>
	 */
	private List<Fact> createFacts_SalesInvoice(final AcctSchema as)
	{
		final Fact fact = newFact(as);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		//
		// Header Charge CR
		final ChargeId chargeId = getC_Charge_ID().orElse(null);
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeId != null && chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getAccountProvider().getChargeAccount(chargeId, as.getId(), chargeAmt))
					.setCurrencyId(getCurrencyId())
					.setAmtSource(null, chargeAmt)
					.buildAndAdd();
		}

		//
		// TaxDue CR
		for (final DocTax docTax : getTaxes())
		{
			final BigDecimal taxAmt = docTax.getTaxAmt();
			if (taxAmt != null && taxAmt.signum() != 0)
			{
				final FactLine tl = fact.createLine(null, docTax.getTaxDueAcct(as),
						getCurrencyId(), null, taxAmt);
				if (tl != null)
				{
					tl.setTaxIdAndUpdateVatCode(docTax.getTaxId());
				}
			}
		}

		// Revenue CR
		for (final DocLine_Invoice line : getDocLines())
		{
			BigDecimal lineAmt = line.getAmtSource();
			BigDecimal dAmt = null;
			if (as.isPostTradeDiscount())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					lineAmt = lineAmt.add(discount);
					dAmt = discount;
					fact.createLine(line,
							line.getAccount(ProductAcctType.P_TradeDiscountGrant_Acct, as),
							getCurrencyId(), dAmt, null);
				}
			}
			fact.createLine(line,
					line.getAccount(ProductAcctType.P_Revenue_Acct, as),
					getCurrencyId(), null, lineAmt);
			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(lineAmt);
				serviceAmt = serviceAmt.add(lineAmt);
			}
		}

		// Set Locations
		fact.forEach(fl -> {
			fl.setLocationFromOrg(fl.getOrgId(), true);      // from Loc
			fl.setLocationFromBPartner(getBPartnerLocationId(), false);  // to Loc
		});

		// Receivables DR
		final Account receivablesAccount = getCustomerAccount(BPartnerCustomerAccountType.C_Receivable, as);
		final Account receivablesServices = getCustomerAccount(BPartnerCustomerAccountType.C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| receivablesAccount.equals(receivablesServices))
		{
			grossAmt = getAmount(Doc.AMTTYPE_Gross);
			serviceAmt = BigDecimal.ZERO;
		}
		else if (m_allLinesService)
		{
			serviceAmt = getAmount(Doc.AMTTYPE_Gross);
			grossAmt = BigDecimal.ZERO;
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// we need this line later, even if it is zero
		fact.createLine()
				.setAccount(receivablesAccount)
				.setAmtSource(getCurrencyId(), grossAmt, null)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(receivablesServices)
					.setAmtSource(getCurrencyId(), serviceAmt, null)
					.alsoAddZeroLine()
					.buildAndAdd();
		}

		return ImmutableList.of(fact);
	}

	/**
	 * <pre>
	 *  ARC
	 *      Receivables             CR
	 *      Charge          DR
	 *      TaxDue          DR
	 *      Revenue         RR
	 * </pre>
	 */
	private List<Fact> createFacts_SalesCreditMemo(final AcctSchema as)
	{
		final Fact fact = newFact(as);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		//
		// Header Charge DR
		final ChargeId chargeId = getC_Charge_ID().orElse(null);
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeId != null && chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getAccountProvider().getChargeAccount(chargeId, as.getId(), chargeAmt))
					.setCurrencyId(getCurrencyId())
					.setAmtSource(chargeAmt, null)
					.buildAndAdd();
		}

		//
		// TaxDue DR
		for (final DocTax docTax : getTaxes())
		{
			final BigDecimal taxAmt = docTax.getTaxAmt();
			if (taxAmt != null)
			{
				fact.createLine()
						.setDocLine(null)
						.setAccount(docTax.getTaxDueAcct(as))
						.setAmtSource(getCurrencyId(), taxAmt, null)
						.setC_Tax_ID(docTax.getTaxId())
						.alsoAddZeroLine()
						.buildAndAdd();
			}
		}
		// Revenue CR
		for (final DocLine_Invoice line : getDocLines())
		{
			BigDecimal lineAmt = line.getAmtSource();
			BigDecimal dAmt;
			if (as.isPostTradeDiscount())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					lineAmt = lineAmt.add(discount);
					dAmt = discount;
					fact.createLine(line,
							line.getAccount(ProductAcctType.P_TradeDiscountGrant_Acct, as),
							getCurrencyId(), null, dAmt);
				}
			}
			fact.createLine(line,
					line.getAccount(ProductAcctType.P_Revenue_Acct, as),
					getCurrencyId(), lineAmt, null);
			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(lineAmt);
				serviceAmt = serviceAmt.add(lineAmt);
			}
		}
		// Set Locations
		fact.forEach(fl -> {
			fl.setLocationFromOrg(fl.getOrgId(), true);      // from Loc
			fl.setLocationFromBPartner(getBPartnerLocationId(), false);  // to Loc
		});

		// Receivables CR
		final Account receivables = getCustomerAccount(BPartnerCustomerAccountType.C_Receivable, as);
		final Account receivablesServices = getCustomerAccount(BPartnerCustomerAccountType.C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| receivables.equals(receivablesServices))
		{
			grossAmt = getAmount(Doc.AMTTYPE_Gross);
			serviceAmt = BigDecimal.ZERO;
		}
		else if (m_allLinesService)
		{
			serviceAmt = getAmount(Doc.AMTTYPE_Gross);
			grossAmt = BigDecimal.ZERO;
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// we need this line later, even if it is zero
		fact.createLine()
				.setAccount(receivables)
				.setAmtSource(getCurrencyId(), null, grossAmt)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(receivablesServices)
					.setAmtSource(getCurrencyId(), null, serviceAmt)
					.alsoAddZeroLine()
					.buildAndAdd();
		}

		return ImmutableList.of(fact);
	}

	/**
	 * <pre>
	 *  API
	 *      Payables                CR
	 *      Charge          DR
	 *      TaxCredit       DR
	 *      TaxDue          DR               (if reverse charge)
	 *      Expense         DR
	 * </pre>
	 */
	private List<Fact> createFacts_PurchaseInvoice(final AcctSchema as)
	{
		final Fact fact = newFact(as);

		final CurrencyId currencyId = getCurrencyId();
		Money grossAmt = Money.of(getAmount(Doc.AMTTYPE_Gross), currencyId);
		Money serviceAmt = Money.zero(currencyId);

		//
		// Charge DR
		final ChargeId chargeId = getC_Charge_ID().orElse(null);
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeId != null && chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getAccountProvider().getChargeAccount(chargeId, as.getId(), chargeAmt))
					.setCurrencyId(currencyId)
					.setAmtSource(chargeAmt, null)
					.buildAndAdd();
		}

		//
		// Expense/InventoryClearing DR
		for (final DocLine_Invoice line : getDocLines())
		{
			Money amt = Money.of(line.getAmtSource(), currencyId);
			if (as.isPostTradeDiscount() && !line.isItem())
			{
				final BigDecimal discountBD = line.getDiscount();
				if (discountBD != null && discountBD.signum() != 0)
				{
					final Money discount = Money.of(discountBD, currencyId);
					amt = amt.add(discount);
					fact.createLine()
							.setDocLine(line)
							.setAccount(line.getAccount(ProductAcctType.P_TradeDiscountRec_Acct, as))
							.setAmtSource((Money)null, discount)
							.buildAndAdd();
				}
			}

			if (line.isItem())  // stockable item
			{
				final Money amtReceived = line.calculateAmtOfQtyReceived(amt);
				fact.createLine()
						.setDocLine(line)
						.setAccount(line.getAccount(ProductAcctType.P_InventoryClearing_Acct, as))
						.setAmtSource(amtReceived, null)
						.setQty(line.getQtyReceivedAbs())
						.buildAndAdd();

				final Money amtNotReceived = amt.subtract(amtReceived);
				fact.createLine()
						.setDocLine(line)
						.setAccount(line.getAccount(ProductAcctType.P_Expense_Acct, as))
						.setAmtSource(amtNotReceived, null)
						.setQty(line.getQtyNotReceivedAbs())
						.buildAndAdd();
			}
			else // service
			{
				final Money costAmountMatched = line.getCostAmountMatched();
				if (!costAmountMatched.isZero())
				{
					fact.createLine()
							.setDocLine(line)
							.setAccount(line.getAccount(ProductAcctType.P_InventoryClearing_Acct, as))
							.setAmtSource(costAmountMatched, null)
							.setQty(line.getQtyReceivedAbs())
							.buildAndAdd();
				}

				final Money expenseAmt = amt.subtract(costAmountMatched);
				fact.createLine()
						.setDocLine(line)
						.setAccount(line.getAccount(ProductAcctType.P_Expense_Acct, as))
						.setAmtSource(expenseAmt, null)
						.buildAndAdd();
			}

			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(amt);
				serviceAmt = serviceAmt.add(amt);
			}
		}

		final DocTaxesList taxes = applyUserChangesAndRecomputeTaxes(fact);

		//
		// TaxCredit DR
		for (final DocTax docTax : taxes)
		{
			if (docTax.isReverseCharge())
			{
				fact.createLine()
						.setAccount(docTax.getTaxCreditOrExpense(as))
						.setAmtSource(currencyId, docTax.getReverseChargeTaxAmt(), null)
						.setC_Tax_ID(docTax.getTaxId())
						.alsoAddZeroLine()
						.buildAndAdd();
				fact.createLine()
						.setAccount(docTax.getTaxDueAcct(as))
						.setAmtSource(currencyId, docTax.getReverseChargeTaxAmt().negate(), null)
						.setC_Tax_ID(docTax.getTaxId())
						.alsoAddZeroLine()
						.buildAndAdd();
			}
			else
			{
				fact.createLine()
						.setAccount(docTax.getTaxCreditOrExpense(as))
						.setAmtSource(currencyId, docTax.getTaxAmt(), null)
						.setC_Tax_ID(docTax.getTaxId())
						.alsoAddZeroLine()
						.buildAndAdd();
			}
		}

		// Set Locations
		fact.forEach(fl -> {
			fl.setLocationFromBPartner(getBPartnerLocationId(), true);  // from Loc
			fl.setLocationFromOrg(fl.getOrgId(), false);    // to Loc
		});

		//
		// Liability CR
		final Account payablesId = getVendorAccount(BPartnerVendorAccountType.V_Liability, as);
		final Account payablesServicesId = getVendorAccount(BPartnerVendorAccountType.V_Liability_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| payablesId.equals(payablesServicesId))
		{
			grossAmt = Money.of(getAmount(Doc.AMTTYPE_Gross), currencyId);
			serviceAmt = Money.zero(currencyId);
		}
		else if (m_allLinesService)
		{
			serviceAmt = Money.of(getAmount(Doc.AMTTYPE_Gross), currencyId);
			grossAmt = Money.zero(currencyId);
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// we need this line later, even if it is zero
		fact.createLine()
				.setAccount(payablesId)
				.setAmtSource((Money)null, grossAmt)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(payablesServicesId)
					.setAmtSource((Money)null, serviceAmt)
					.alsoAddZeroLine()
					.buildAndAdd();
		}

		return ImmutableList.of(fact);
	}

	/**
	 * <pre>
	 *  APC
	 *      Payables        DR
	 *      Charge                  CR
	 *      TaxCredit               CR
	 *      TaxDue                  CR  (if reverse charge)
	 *      Expense                 CR
	 * </pre>
	 */
	private List<Fact> createFacts_PurchaseCreditMemo(final AcctSchema as)
	{
		final Fact fact = newFact(as);

		final CurrencyId currencyId = getCurrencyId();
		Money grossAmt = Money.of(getAmount(Doc.AMTTYPE_Gross), currencyId);
		Money serviceAmt = Money.zero(currencyId);

		//
		// Charge CR
		final ChargeId chargeId = getC_Charge_ID().orElse(null);
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeId != null && chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getAccountProvider().getChargeAccount(chargeId, as.getId(), chargeAmt))
					.setCurrencyId(currencyId)
					.setAmtSource(null, chargeAmt)
					.buildAndAdd();
		}

		//
		// Expense/InventoryClearing CR
		for (final DocLine_Invoice line : getDocLines())
		{
			Money amt = Money.of(line.getAmtSource(), currencyId);
			if (as.isPostTradeDiscount() && !line.isItem())
			{
				final BigDecimal discountBD = line.getDiscount();
				if (discountBD != null && discountBD.signum() != 0)
				{
					final Money discount = Money.of(discountBD, currencyId);
					amt = amt.add(discount);

					fact.createLine()
							.setDocLine(line)
							.setAccount(line.getAccount(ProductAcctType.P_TradeDiscountRec_Acct, as))
							.setAmtSource(discount, null)
							.buildAndAdd();
				}
			}

			if (line.isItem())  // stockable item
			{
				final Money amtReceived = line.calculateAmtOfQtyReceived(amt);
				fact.createLine()
						.setDocLine(line)
						.setAccount(line.getAccount(ProductAcctType.P_InventoryClearing_Acct, as))
						.setAmtSource((Money)null, amtReceived)
						.setQty(line.getQtyReceivedAbs())
						.buildAndAdd();

				final Money amtNotReceived = amt.subtract(amtReceived);
				fact.createLine()
						.setDocLine(line)
						.setAccount(line.getAccount(ProductAcctType.P_Expense_Acct, as))
						.setAmtSource((Money)null, amtNotReceived)
						.setQty(line.getQtyNotReceivedAbs())
						.buildAndAdd();
			}
			else // service
			{
				fact.createLine()
						.setDocLine(line)
						.setAccount(line.getAccount(ProductAcctType.P_Expense_Acct, as))
						.setAmtSource((Money)null, amt)
						.buildAndAdd();
			}

			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(amt);
				serviceAmt = serviceAmt.add(amt);
			}
		}

		final DocTaxesList taxes = applyUserChangesAndRecomputeTaxes(fact);

		//
		// TaxCredit CR
		for (final DocTax docTax : taxes)
		{
			if (docTax.isReverseCharge())
			{
				fact.createLine()
						.setAccount(docTax.getTaxCreditOrExpense(as))
						.setAmtSource(currencyId, null, docTax.getReverseChargeTaxAmt())
						.setC_Tax_ID(docTax.getTaxId())
						.alsoAddZeroLine()
						.buildAndAdd();
				fact.createLine()
						.setAccount(docTax.getTaxDueAcct(as))
						.setAmtSource(currencyId, null, docTax.getReverseChargeTaxAmt().negate())
						.setC_Tax_ID(docTax.getTaxId())
						.alsoAddZeroLine()
						.buildAndAdd();
			}
			else
			{
				fact.createLine()
						.setAccount(docTax.getTaxCreditOrExpense(as))
						.setAmtSource(currencyId, null, docTax.getTaxAmt())
						.setC_Tax_ID(docTax.getTaxId())
						.alsoAddZeroLine()
						.buildAndAdd();
			}
		}

		// Set Locations
		fact.forEach(fl -> {
			fl.setLocationFromBPartner(getBPartnerLocationId(), true);  // from Loc
			fl.setLocationFromOrg(fl.getOrgId(), false);    // to Loc
		});

		// Liability DR
		final Account payables = getVendorAccount(BPartnerVendorAccountType.V_Liability, as);
		final Account payablesServices = getVendorAccount(BPartnerVendorAccountType.V_Liability_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| payables.equals(payablesServices))
		{
			grossAmt = Money.of(getAmount(Doc.AMTTYPE_Gross), currencyId);
			serviceAmt = Money.zero(currencyId);
		}
		else if (m_allLinesService)
		{
			serviceAmt = Money.of(getAmount(Doc.AMTTYPE_Gross), currencyId);
			grossAmt = Money.zero(currencyId);
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// we need this line later, even if it is zero
		fact.createLine()
				.setAccount(payables)
				.setAmtSource(grossAmt, null)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(payablesServices)
					.setAmtSource(serviceAmt, null)
					.alsoAddZeroLine()
					.buildAndAdd();
		}

		return ImmutableList.of(fact);
	}

	private DocTaxesList applyUserChangesAndRecomputeTaxes(@NonNull final Fact fact)
	{
		final DocTaxesList taxes = getTaxes();

		final FactAcctChangesApplier changesApplier = getFactAcctChangesApplier();

		// If there are no user changes, we don't have to recompute the taxes,
		// accept them as they come from C_InvoiceTax
		if (!changesApplier.hasChangesToApply())
		{
			return taxes;
		}

		changesApplier.applyUpdatesTo(fact);
		final DocTaxUpdater docTaxUpdater = new DocTaxUpdater(services, getAccountProvider(), InvoiceDocBaseType.ofDocBaseType(getDocBaseType()));
		docTaxUpdater.collect(fact);
		docTaxUpdater.updateDocTaxes(taxes);
		return taxes;
	}

	@Override
	public CurrencyConversionContext getCurrencyConversionContext(final AcctSchema ignoredAcctSchema)
	{
		CurrencyConversionContext invoiceCurrencyConversionCtx = this._invoiceCurrencyConversionCtx;
		if (invoiceCurrencyConversionCtx == null)
		{
			final I_C_Invoice invoice = getModel(I_C_Invoice.class);
			invoiceCurrencyConversionCtx = this._invoiceCurrencyConversionCtx = invoiceBL.getCurrencyConversionCtx(invoice);
		}
		return invoiceCurrencyConversionCtx;
	}

	@Override
	protected void afterPost()
	{
		postDependingMatchInvsIfNeeded();
	}

	private void postDependingMatchInvsIfNeeded()
	{
		if (!services.getSysConfigBooleanValue(SYSCONFIG_PostMatchInvs, DEFAULT_PostMatchInvs))
		{
			return;
		}

		final Set<InvoiceAndLineId> invoiceAndLineIds = new HashSet<>();
		for (final DocLine_Invoice line : getDocLines())
		{
			invoiceAndLineIds.add(line.getInvoiceAndLineId());
		}

		// 08643
		// Do nothing in case there are no invoice lines
		if (invoiceAndLineIds.isEmpty())
		{
			return;
		}

		final Set<MatchInvId> matchInvIds = matchInvoiceService.getIdsProcessedButNotPostedByInvoiceLineIds(invoiceAndLineIds);
		postDependingDocuments(I_M_MatchInv.Table_Name, matchInvIds);
	}

	@Nullable
	@Override
	protected OrderId getSalesOrderId()
	{
		final Optional<OrderId> optionalSalesOrderId = CollectionUtils.extractSingleElementOrDefault(
				getDocLines(),
				docLine -> Optional.ofNullable(docLine.getSalesOrderId()),
				Optional.empty());

		//noinspection DataFlowIssue
		return optionalSalesOrderId.orElse(null);
	}

	public static void unpostIfNeeded(final I_C_Invoice invoice)
	{
		if (!invoice.isPosted())
		{
			return;
		}

		// Make sure the period is open
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		MPeriod.testPeriodOpen(ctx, invoice.getDateAcct(), invoice.getC_DocType_ID(), invoice.getAD_Org_ID());

		Services.get(IFactAcctDAO.class).deleteForDocumentModel(invoice);

		invoice.setPosted(false);
		InterfaceWrapperHelper.save(invoice);
	}

	@NonNull
	private PostingType getPostingType()
	{
		final DocTypeId docTypeId = getC_DocType_ID();

		if(docTypeId == null)
		{
			return PostingType.Actual;
		}

		if(docTypeBL.isInternalVendorInvoice(docTypeId))
		{
			return PostingType.Statistical;
		}

		return PostingType.Actual;
	}
}   // Doc_Invoice
