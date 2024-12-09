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
<<<<<<< HEAD
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.doc.DocLine_Invoice;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.MatchInvId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MAccount;
import org.compiere.model.MPeriod;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
public class Doc_Invoice extends Doc<DocLine_Invoice>
{
	private final IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
=======
@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
public class Doc_Invoice extends Doc<DocLine_Invoice>
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final MatchInvoiceService matchInvoiceService;
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private final OrderGroupRepository orderGroupRepo;

	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_Invoice.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;

	/**
	 * Contained Optional Tax Lines
	 */
<<<<<<< HEAD
	private List<DocTax> _taxes = null;
=======
	private DocTaxesList _taxes = null; // lazy
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**
	 * All lines are Service
	 */
	private boolean m_allLinesService = true;
	/**
	 * All lines are product item
	 */
	private boolean m_allLinesItem = true;
<<<<<<< HEAD
=======
	private Optional<InvoiceAcct> _invoiceAccounts = null; // lazy

	private CurrencyConversionContext _invoiceCurrencyConversionCtx = null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	public Doc_Invoice(@NonNull final AcctDocContext ctx)
	{
		this(ctx, SpringContextHolder.instance.getBean(OrderGroupRepository.class));
	}

	public Doc_Invoice(@NonNull final AcctDocContext ctx, @NonNull final OrderGroupRepository orderGroupRepo)
	{
		super(ctx);
		this.orderGroupRepo = orderGroupRepo;
<<<<<<< HEAD
=======
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD

		setDocLines(loadLines(invoice));
	}

	private List<DocTax> getTaxes()
=======
		setAmount(Doc.AMTTYPE_CashRounding, BigDecimal.ZERO);

		setDocLines(loadLines());
	}

	private DocTaxesList getTaxes()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (_taxes == null)
		{
			_taxes = loadTaxes();
		}
		return _taxes;
	}

<<<<<<< HEAD
	private List<DocTax> loadTaxes()
	{
		final String sql = "SELECT it.C_Tax_ID, t.Name, t.Rate, it.TaxBaseAmt, it.TaxAmt, t.IsSalesTax " // 1..6
				+ ", it." + I_C_InvoiceTax.COLUMNNAME_IsTaxIncluded // 7
				+ " FROM C_Tax t, C_InvoiceTax it "
				+ " WHERE t.C_Tax_ID=it.C_Tax_ID AND it.C_Invoice_ID=?";
		final Object[] sqlParams = new Object[] { get_ID() };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			//
			final ImmutableList.Builder<DocTax> docTaxes = ImmutableList.builder();
			while (rs.next())
			{
				final TaxId taxId = TaxId.ofRepoId(rs.getInt(1));
				final String taxName = rs.getString(2);
				final BigDecimal rate = rs.getBigDecimal(3);
				final BigDecimal taxBaseAmt = rs.getBigDecimal(4);
				final BigDecimal taxAmt = rs.getBigDecimal(5);
				final boolean salesTax = DisplayType.toBoolean(rs.getString(6));
				final boolean taxIncluded = DisplayType.toBoolean(rs.getString(7));
				//
				final DocTax taxLine = new DocTax(
						taxId, taxName, rate,
						taxBaseAmt, taxAmt, salesTax, taxIncluded);
				docTaxes.add(taxLine);
			}

			return docTaxes.build();
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}    // loadTaxes

	private List<DocLine_Invoice> loadLines(final I_C_Invoice invoice)
	{
		final List<DocLine_Invoice> docLines = new ArrayList<>();
		//
		for (final I_C_InvoiceLine line : Services.get(IInvoiceDAO.class).retrieveLines(invoice))
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				final TaxId taxId = docLine.getTaxId().orElse(null);
				final DocTax docTax = getDocTaxOrNull(taxId);
				if (docTax != null)
				{
					docTax.addIncludedTax(lineIncludedTaxAmt);
				}
=======
				final DocTaxesList taxes = getTaxes();
				docLine.getTaxId()
						.flatMap(taxes::getByTaxId)
						.ifPresent(docTax -> docTax.addIncludedTax(lineIncludedTaxAmt));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		final String docBaseType = getDocumentType();
		final boolean cm = Doc.DOCTYPE_ARCredit.equals(docBaseType)
				|| Doc.DOCTYPE_APCredit.equals(docBaseType);
		return cm;
=======
		return InvoiceDocBaseType.ofDocBaseType(getDocBaseType()).isCreditMemo();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	final DocTax getDocTaxOrNull(final TaxId taxId)
	{
		if (taxId == null)
		{
			return null;
		}

		return getTaxes()
				.stream()
				.filter(docTax -> docTax.getC_Tax_ID() == taxId.getRepoId())
				.findFirst()
				.orElse(null);
=======
	private Fact newFact(@NonNull final AcctSchema as)
	{
		return new Fact(this, as, PostingType.Actual)
				.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance)
				.setCurrencyConversionContext(getCurrencyConversionContext(as));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		final String docBaseType = getDocumentType();
		if (DOCTYPE_ARInvoice.equals(docBaseType)
				|| DOCTYPE_ARProForma.equals(docBaseType))
=======
		final DocBaseType docBaseType = getDocBaseType();
		if (DocBaseType.SalesInvoice.equals(docBaseType)
				|| DocBaseType.SalesProformaInvoice.equals(docBaseType))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return createFacts_SalesInvoice(as);
		}
		// ARC
<<<<<<< HEAD
		else if (DOCTYPE_ARCredit.equals(docBaseType))
=======
		else if (DocBaseType.SalesCreditMemo.equals(docBaseType))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return createFacts_SalesCreditMemo(as);
		}

		// ** API
<<<<<<< HEAD
		else if (DOCTYPE_APInvoice.equals(docBaseType)
=======
		else if (DocBaseType.PurchaseInvoice.equals(docBaseType)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				|| InvoiceDocBaseType.AEInvoice.getDocBaseType().equals(docBaseType)  // metas-ts: treating commission/salary invoice like AP invoice
				|| InvoiceDocBaseType.AVInvoice.getDocBaseType().equals(docBaseType))   // metas-ts: treating invoice for recurrent payment like AP invoice
		{
			return createFacts_PurchaseInvoice(as);
		}
		// APC
<<<<<<< HEAD
		else if (DOCTYPE_APCredit.equals(docBaseType))
=======
		else if (DocBaseType.PurchaseCreditMemo.equals(docBaseType))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		final Fact fact = new Fact(this, as, PostingType.Actual)
				.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance);
=======
		final Fact fact = newFact(as);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		//
		// Header Charge CR
<<<<<<< HEAD
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getValidCombinationId(AccountType.Charge, as))
=======
		final ChargeId chargeId = getC_Charge_ID().orElse(null);
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeId != null && chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getAccountProvider().getChargeAccount(chargeId, as.getId(), chargeAmt))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					.setCurrencyId(getCurrencyId())
					.setAmtSource(null, chargeAmt)
					.buildAndAdd();
		}

<<<<<<< HEAD
=======
		final BigDecimal cashRoundingAmt = getAmount(Doc.AMTTYPE_CashRounding);
		if (cashRoundingAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(as.getGeneralLedger().getCashRoundingAcct())
					.setCurrencyId(getCurrencyId())
					.setAmtSource(null, cashRoundingAmt)
					.buildAndAdd();
		}


>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
					tl.setC_Tax_ID(docTax.getC_Tax_ID());
=======
					tl.setTaxIdAndUpdateVatCode(docTax.getTaxId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
							line.getAccount(ProductAcctType.TDiscountGrant, as),
=======
							line.getAccount(ProductAcctType.P_TradeDiscountGrant_Acct, as),
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
							getCurrencyId(), dAmt, null);
				}
			}
			fact.createLine(line,
<<<<<<< HEAD
					line.getAccount(ProductAcctType.Revenue, as),
=======
					line.getAccount(ProductAcctType.P_Revenue_Acct, as),
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					getCurrencyId(), null, lineAmt);
			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(lineAmt);
				serviceAmt = serviceAmt.add(lineAmt);
			}
		}

		// Set Locations
		fact.forEach(fl -> {
<<<<<<< HEAD
			fl.setLocationFromOrg(fl.getAD_Org_ID(), true);      // from Loc
			fl.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
		});

		// Receivables DR
		final AccountId receivablesId = getValidCombinationId(AccountType.C_Receivable, as);
		final AccountId receivablesServicesId = getValidCombinationId(AccountType.C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| AccountId.equals(receivablesId, receivablesServicesId))
=======
			fl.setLocationFromOrg(fl.getOrgId(), true);      // from Loc
			fl.setLocationFromBPartner(getBPartnerLocationId(), false);  // to Loc
		});

		// Receivables DR
		final Account receivablesAccount = getCustomerAccount(BPartnerCustomerAccountType.C_Receivable, as);
		final Account receivablesServices = getCustomerAccount(BPartnerCustomerAccountType.C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| receivablesAccount.equals(receivablesServices))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				.setAccount(receivablesId)
=======
				.setAccount(receivablesAccount)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.setAmtSource(getCurrencyId(), grossAmt, null)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
<<<<<<< HEAD
					.setAccount(receivablesServicesId)
=======
					.setAccount(receivablesServices)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		final Fact fact = new Fact(this, as, PostingType.Actual)
				.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance);
=======
		final Fact fact = newFact(as);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		//
		// Header Charge DR
<<<<<<< HEAD
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getValidCombinationId(AccountType.Charge, as))
=======
		final ChargeId chargeId = getC_Charge_ID().orElse(null);
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeId != null && chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getAccountProvider().getChargeAccount(chargeId, as.getId(), chargeAmt))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					.setCurrencyId(getCurrencyId())
					.setAmtSource(chargeAmt, null)
					.buildAndAdd();
		}

<<<<<<< HEAD
=======
		final BigDecimal cashRoundingAmt = getAmount(Doc.AMTTYPE_CashRounding);
		if (cashRoundingAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(as.getGeneralLedger().getCashRoundingAcct())
					.setCurrencyId(getCurrencyId())
					.setAmtSource(cashRoundingAmt, null)
					.buildAndAdd();
		}


>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		//
		// TaxDue DR
		for (final DocTax docTax : getTaxes())
		{
			final BigDecimal taxAmt = docTax.getTaxAmt();
<<<<<<< HEAD
			if (taxAmt != null && taxAmt.signum() != 0)
			{
				final FactLine tl = fact.createLine(null, docTax.getTaxDueAcct(as),
						getCurrencyId(), taxAmt, null);
				if (tl != null)
				{
					tl.setC_Tax_ID(docTax.getC_Tax_ID());
				}
=======
			if (taxAmt != null)
			{
				fact.createLine()
						.setDocLine(null)
						.setAccount(docTax.getTaxDueAcct(as))
						.setAmtSource(getCurrencyId(), taxAmt, null)
						.setC_Tax_ID(docTax.getTaxId())
						.alsoAddZeroLine()
						.buildAndAdd();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}
		}
		// Revenue CR
		for (final DocLine_Invoice line : getDocLines())
		{
			BigDecimal lineAmt = line.getAmtSource();
<<<<<<< HEAD
			BigDecimal dAmt = null;
=======
			BigDecimal dAmt;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			if (as.isPostTradeDiscount())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					lineAmt = lineAmt.add(discount);
					dAmt = discount;
					fact.createLine(line,
<<<<<<< HEAD
							line.getAccount(ProductAcctType.TDiscountGrant, as),
=======
							line.getAccount(ProductAcctType.P_TradeDiscountGrant_Acct, as),
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
							getCurrencyId(), null, dAmt);
				}
			}
			fact.createLine(line,
<<<<<<< HEAD
					line.getAccount(ProductAcctType.Revenue, as),
=======
					line.getAccount(ProductAcctType.P_Revenue_Acct, as),
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					getCurrencyId(), lineAmt, null);
			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(lineAmt);
				serviceAmt = serviceAmt.add(lineAmt);
			}
		}
		// Set Locations
		fact.forEach(fl -> {
<<<<<<< HEAD
			fl.setLocationFromOrg(fl.getAD_Org_ID(), true);      // from Loc
			fl.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
		});

		// Receivables CR
		final AccountId receivablesId = getValidCombinationId(AccountType.C_Receivable, as);
		final AccountId receivablesServicesId = getValidCombinationId(AccountType.C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| AccountId.equals(receivablesId, receivablesServicesId))
=======
			fl.setLocationFromOrg(fl.getOrgId(), true);      // from Loc
			fl.setLocationFromBPartner(getBPartnerLocationId(), false);  // to Loc
		});

		// Receivables CR
		final Account receivables = getCustomerAccount(BPartnerCustomerAccountType.C_Receivable, as);
		final Account receivablesServices = getCustomerAccount(BPartnerCustomerAccountType.C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| receivables.equals(receivablesServices))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				.setAccount(receivablesId)
=======
				.setAccount(receivables)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.setAmtSource(getCurrencyId(), null, grossAmt)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
<<<<<<< HEAD
					.setAccount(receivablesServicesId)
=======
					.setAccount(receivablesServices)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======
	 *      TaxDue          DR               (if reverse charge)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *      Expense         DR
	 * </pre>
	 */
	private List<Fact> createFacts_PurchaseInvoice(final AcctSchema as)
	{
<<<<<<< HEAD
		final Fact fact = new Fact(this, as, PostingType.Actual)
				.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		//
		// Charge DR
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getValidCombinationId(AccountType.Charge, as))
					.setCurrencyId(getCurrencyId())
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					.setAmtSource(chargeAmt, null)
					.buildAndAdd();
		}

<<<<<<< HEAD
		//
		// TaxCredit DR
		for (final DocTax docTax : getTaxes())
		{
			final FactLine tl = fact.createLine(null,
					docTax.getAccount(as),  // account
					getCurrencyId(),
					docTax.getTaxAmt(), null); // DR/CR
			if (tl != null)
			{
				tl.setC_Tax_ID(docTax.getC_Tax_ID());
			}
		}

		// Expense/InventoryClearing DR
		for (final DocLine_Invoice line : getDocLines())
		{
			BigDecimal amt = line.getAmtSource();
			BigDecimal dAmt = null;
			if (as.isPostTradeDiscount() && !line.isItem())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					amt = amt.add(discount);
					dAmt = discount;
					final MAccount tradeDiscountReceived = line.getAccount(ProductAcctType.TDiscountRec, as);
					fact.createLine(line, tradeDiscountReceived, getCurrencyId(), null, dAmt);
=======
		final BigDecimal cashRoundingAmt = getAmount(Doc.AMTTYPE_CashRounding);
		if (cashRoundingAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(as.getGeneralLedger().getCashRoundingAcct())
					.setCurrencyId(getCurrencyId())
					.setAmtSource(cashRoundingAmt, null)
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				}
			}

			if (line.isItem())  // stockable item
			{
<<<<<<< HEAD
				final BigDecimal amtReceived = line.calculateAmtOfQtyReceived(amt);
				fact.createLine(line,
						line.getAccount(ProductAcctType.InventoryClearing, as),
						getCurrencyId(),
						amtReceived, null,  // DR/CR
						line.getQtyReceivedAbs());

				final BigDecimal amtNotReceived = amt.subtract(amtReceived);
				fact.createLine(line,
						line.getAccount(ProductAcctType.Expense, as),
						getCurrencyId(),
						amtNotReceived, null,  // DR/CR
						line.getQtyNotReceivedAbs());
			}
			else // service
			{
				fact.createLine(line, line.getAccount(ProductAcctType.Expense, as), getCurrencyId(), amt, null);
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}

			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(amt);
				serviceAmt = serviceAmt.add(amt);
			}
		}
<<<<<<< HEAD
		// Set Locations
		fact.forEach(fl -> {
			fl.setLocationFromBPartner(getC_BPartner_Location_ID(), true);  // from Loc
			fl.setLocationFromOrg(fl.getAD_Org_ID(), false);    // to Loc
		});

		// Liability CR
		final AccountId payablesId = getValidCombinationId(AccountType.V_Liability, as);
		final AccountId payablesServicesId = getValidCombinationId(AccountType.V_Liability_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| AccountId.equals(payablesId, payablesServicesId))
		{
			grossAmt = getAmount(Doc.AMTTYPE_Gross);
			serviceAmt = BigDecimal.ZERO;
		}
		else if (m_allLinesService)
		{
			serviceAmt = getAmount(Doc.AMTTYPE_Gross);
			grossAmt = BigDecimal.ZERO;
=======

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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// we need this line later, even if it is zero
		fact.createLine()
				.setAccount(payablesId)
<<<<<<< HEAD
				.setAmtSource(getCurrencyId(), null, grossAmt)
=======
				.setAmtSource((Money)null, grossAmt)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(payablesServicesId)
<<<<<<< HEAD
					.setAmtSource(getCurrencyId(), null, serviceAmt)
=======
					.setAmtSource((Money)null, serviceAmt)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======
	 *      TaxDue                  CR  (if reverse charge)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *      Expense                 CR
	 * </pre>
	 */
	private List<Fact> createFacts_PurchaseCreditMemo(final AcctSchema as)
	{
<<<<<<< HEAD
		final Fact fact = new Fact(this, as, PostingType.Actual)
				.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		//
		// Charge CR
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getValidCombinationId(AccountType.Charge, as))
					.setCurrencyId(getCurrencyId())
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					.setAmtSource(null, chargeAmt)
					.buildAndAdd();
		}

<<<<<<< HEAD
		//
		// TaxCredit CR
		for (final DocTax docTax : getTaxes())
		{
			final FactLine tl = fact.createLine(null, docTax.getAccount(as),
					getCurrencyId(), null, docTax.getTaxAmt());
			if (tl != null)
			{
				tl.setC_Tax_ID(docTax.getC_Tax_ID());
			}
		}
		// Expense CR
		for (final DocLine_Invoice line : getDocLines())
		{
			BigDecimal amt = line.getAmtSource();
			BigDecimal dAmt = null;
			if (as.isPostTradeDiscount() && !line.isItem())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					amt = amt.add(discount);
					dAmt = discount;
					final MAccount tradeDiscountReceived = line.getAccount(ProductAcctType.TDiscountRec, as);
					fact.createLine(line, tradeDiscountReceived, getCurrencyId(), dAmt, null);
=======
		final BigDecimal cashRoundingAmt = getAmount(Doc.AMTTYPE_CashRounding);
		if (cashRoundingAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(as.getGeneralLedger().getCashRoundingAcct())
					.setCurrencyId(getCurrencyId())
					.setAmtSource(null, cashRoundingAmt)
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				}
			}

			if (line.isItem())  // stockable item
			{
<<<<<<< HEAD
				final BigDecimal amtReceived = line.calculateAmtOfQtyReceived(amt);
				fact.createLine(line,
						line.getAccount(ProductAcctType.InventoryClearing, as),
						getCurrencyId(),
						null, amtReceived,  // DR/CR
						line.getQtyReceivedAbs());

				final BigDecimal amtNotReceived = amt.subtract(amtReceived);
				fact.createLine(line,
						line.getAccount(ProductAcctType.Expense, as),
						getCurrencyId(),
						null, amtNotReceived,  // DR/CR
						line.getQtyNotReceivedAbs());
			}
			else // service
			{
				fact.createLine(line, line.getAccount(ProductAcctType.Expense, as), getCurrencyId(), null, amt);
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}

			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(amt);
				serviceAmt = serviceAmt.add(amt);
			}
		}
<<<<<<< HEAD
		// Set Locations
		fact.forEach(fl -> {
			fl.setLocationFromBPartner(getC_BPartner_Location_ID(), true);  // from Loc
			fl.setLocationFromOrg(fl.getAD_Org_ID(), false);    // to Loc
		});

		// Liability DR
		final AccountId payablesId = getValidCombinationId(AccountType.V_Liability, as);
		final AccountId payablesServicesId = getValidCombinationId(AccountType.V_Liability_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| AccountId.equals(payablesId, payablesServicesId))
		{
			grossAmt = getAmount(Doc.AMTTYPE_Gross);
			serviceAmt = BigDecimal.ZERO;
		}
		else if (m_allLinesService)
		{
			serviceAmt = getAmount(Doc.AMTTYPE_Gross);
			grossAmt = BigDecimal.ZERO;
=======

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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// we need this line later, even if it is zero
		fact.createLine()
<<<<<<< HEAD
				.setAccount(payablesId)
				.setAmtSource(getCurrencyId(), grossAmt, null)
=======
				.setAccount(payables)
				.setAmtSource(grossAmt, null)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
<<<<<<< HEAD
					.setAccount(payablesServicesId)
					.setAmtSource(getCurrencyId(), serviceAmt, null)
=======
					.setAccount(payablesServices)
					.setAmtSource(serviceAmt, null)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					.alsoAddZeroLine()
					.buildAndAdd();
		}

		return ImmutableList.of(fact);
	}

<<<<<<< HEAD
=======
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Override
	protected void afterPost()
	{
		postDependingMatchInvsIfNeeded();
	}

<<<<<<< HEAD
	private final void postDependingMatchInvsIfNeeded()
	{
		if (!Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_PostMatchInvs, DEFAULT_PostMatchInvs))
=======
	private void postDependingMatchInvsIfNeeded()
	{
		if (!services.getSysConfigBooleanValue(SYSCONFIG_PostMatchInvs, DEFAULT_PostMatchInvs))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return;
		}

<<<<<<< HEAD
		final Set<InvoiceLineId> invoiceLineIds = new HashSet<>();
		for (final DocLine_Invoice line : getDocLines())
		{
			invoiceLineIds.add(line.getInvoiceLineId());
=======
		final Set<InvoiceAndLineId> invoiceAndLineIds = new HashSet<>();
		for (final DocLine_Invoice line : getDocLines())
		{
			invoiceAndLineIds.add(line.getInvoiceAndLineId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		// 08643
		// Do nothing in case there are no invoice lines
<<<<<<< HEAD
		if (invoiceLineIds.isEmpty())
=======
		if (invoiceAndLineIds.isEmpty())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return;
		}

<<<<<<< HEAD
		final Set<MatchInvId> matchInvIds = matchInvDAO.retrieveIdsProcessedButNotPostedForInvoiceLines(invoiceLineIds);
		postDependingDocuments(I_M_MatchInv.Table_Name, matchInvIds);
	}

	public static void unpost(final I_C_Invoice invoice)
	{
=======
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		// Make sure the period is open
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		MPeriod.testPeriodOpen(ctx, invoice.getDateAcct(), invoice.getC_DocType_ID(), invoice.getAD_Org_ID());

		Services.get(IFactAcctDAO.class).deleteForDocumentModel(invoice);

		invoice.setPosted(false);
		InterfaceWrapperHelper.save(invoice);
	}
<<<<<<< HEAD
=======

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}   // Doc_Invoice
