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
public class Doc_Invoice extends Doc<DocLine_Invoice>
{
	private final IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
	private final OrderGroupRepository orderGroupRepo;

	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_Invoice.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;

	/**
	 * Contained Optional Tax Lines
	 */
	private List<DocTax> _taxes = null;
	/**
	 * All lines are Service
	 */
	private boolean m_allLinesService = true;
	/**
	 * All lines are product item
	 */
	private boolean m_allLinesItem = true;

	public Doc_Invoice(@NonNull final AcctDocContext ctx)
	{
		this(ctx, SpringContextHolder.instance.getBean(OrderGroupRepository.class));
	}

	public Doc_Invoice(@NonNull final AcctDocContext ctx, @NonNull final OrderGroupRepository orderGroupRepo)
	{
		super(ctx);
		this.orderGroupRepo = orderGroupRepo;
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

		setDocLines(loadLines(invoice));
	}

	private List<DocTax> getTaxes()
	{
		if (_taxes == null)
		{
			_taxes = loadTaxes();
		}
		return _taxes;
	}

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
				final TaxId taxId = docLine.getTaxId().orElse(null);
				final DocTax docTax = getDocTaxOrNull(taxId);
				if (docTax != null)
				{
					docTax.addIncludedTax(lineIncludedTaxAmt);
				}
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
		final String docBaseType = getDocumentType();
		final boolean cm = Doc.DOCTYPE_ARCredit.equals(docBaseType)
				|| Doc.DOCTYPE_APCredit.equals(docBaseType);
		return cm;
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
		final String docBaseType = getDocumentType();
		if (DOCTYPE_ARInvoice.equals(docBaseType)
				|| DOCTYPE_ARProForma.equals(docBaseType))
		{
			return createFacts_SalesInvoice(as);
		}
		// ARC
		else if (DOCTYPE_ARCredit.equals(docBaseType))
		{
			return createFacts_SalesCreditMemo(as);
		}

		// ** API
		else if (DOCTYPE_APInvoice.equals(docBaseType)
				|| InvoiceDocBaseType.AEInvoice.getDocBaseType().equals(docBaseType)  // metas-ts: treating commission/salary invoice like AP invoice
				|| InvoiceDocBaseType.AVInvoice.getDocBaseType().equals(docBaseType))   // metas-ts: treating invoice for recurrent payment like AP invoice
		{
			return createFacts_PurchaseInvoice(as);
		}
		// APC
		else if (DOCTYPE_APCredit.equals(docBaseType))
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
		final Fact fact = new Fact(this, as, PostingType.Actual)
				.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		//
		// Header Charge CR
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getValidCombinationId(AccountType.Charge, as))
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
					tl.setC_Tax_ID(docTax.getC_Tax_ID());
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
							line.getAccount(ProductAcctType.TDiscountGrant, as),
							getCurrencyId(), dAmt, null);
				}
			}
			fact.createLine(line,
					line.getAccount(ProductAcctType.Revenue, as),
					getCurrencyId(), null, lineAmt);
			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(lineAmt);
				serviceAmt = serviceAmt.add(lineAmt);
			}
		}

		// Set Locations
		fact.forEach(fl -> {
			fl.setLocationFromOrg(fl.getAD_Org_ID(), true);      // from Loc
			fl.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
		});

		// Receivables DR
		final AccountId receivablesId = getValidCombinationId(AccountType.C_Receivable, as);
		final AccountId receivablesServicesId = getValidCombinationId(AccountType.C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| AccountId.equals(receivablesId, receivablesServicesId))
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
				.setAccount(receivablesId)
				.setAmtSource(getCurrencyId(), grossAmt, null)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(receivablesServicesId)
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
		final Fact fact = new Fact(this, as, PostingType.Actual)
				.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		//
		// Header Charge DR
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(getValidCombinationId(AccountType.Charge, as))
					.setCurrencyId(getCurrencyId())
					.setAmtSource(chargeAmt, null)
					.buildAndAdd();
		}

		//
		// TaxDue DR
		for (final DocTax docTax : getTaxes())
		{
			final BigDecimal taxAmt = docTax.getTaxAmt();
			if (taxAmt != null && taxAmt.signum() != 0)
			{
				final FactLine tl = fact.createLine(null, docTax.getTaxDueAcct(as),
						getCurrencyId(), taxAmt, null);
				if (tl != null)
				{
					tl.setC_Tax_ID(docTax.getC_Tax_ID());
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
							line.getAccount(ProductAcctType.TDiscountGrant, as),
							getCurrencyId(), null, dAmt);
				}
			}
			fact.createLine(line,
					line.getAccount(ProductAcctType.Revenue, as),
					getCurrencyId(), lineAmt, null);
			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(lineAmt);
				serviceAmt = serviceAmt.add(lineAmt);
			}
		}
		// Set Locations
		fact.forEach(fl -> {
			fl.setLocationFromOrg(fl.getAD_Org_ID(), true);      // from Loc
			fl.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
		});

		// Receivables CR
		final AccountId receivablesId = getValidCombinationId(AccountType.C_Receivable, as);
		final AccountId receivablesServicesId = getValidCombinationId(AccountType.C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| AccountId.equals(receivablesId, receivablesServicesId))
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
				.setAccount(receivablesId)
				.setAmtSource(getCurrencyId(), null, grossAmt)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(receivablesServicesId)
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
	 *      Expense         DR
	 * </pre>
	 */
	private List<Fact> createFacts_PurchaseInvoice(final AcctSchema as)
	{
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
					.setAmtSource(chargeAmt, null)
					.buildAndAdd();
		}

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
				}
			}

			if (line.isItem())  // stockable item
			{
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
			}

			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(amt);
				serviceAmt = serviceAmt.add(amt);
			}
		}
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
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// we need this line later, even if it is zero
		fact.createLine()
				.setAccount(payablesId)
				.setAmtSource(getCurrencyId(), null, grossAmt)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(payablesServicesId)
					.setAmtSource(getCurrencyId(), null, serviceAmt)
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
	 *      Expense                 CR
	 * </pre>
	 */
	private List<Fact> createFacts_PurchaseCreditMemo(final AcctSchema as)
	{
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
					.setAmtSource(null, chargeAmt)
					.buildAndAdd();
		}

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
				}
			}

			if (line.isItem())  // stockable item
			{
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
			}

			if (!line.isItem())
			{
				grossAmt = grossAmt.subtract(amt);
				serviceAmt = serviceAmt.add(amt);
			}
		}
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
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// we need this line later, even if it is zero
		fact.createLine()
				.setAccount(payablesId)
				.setAmtSource(getCurrencyId(), grossAmt, null)
				.alsoAddZeroLine()
				.buildAndAdd();
		if (serviceAmt.signum() != 0)
		{
			fact.createLine()
					.setAccount(payablesServicesId)
					.setAmtSource(getCurrencyId(), serviceAmt, null)
					.alsoAddZeroLine()
					.buildAndAdd();
		}

		return ImmutableList.of(fact);
	}

	@Override
	protected void afterPost()
	{
		postDependingMatchInvsIfNeeded();
	}

	private final void postDependingMatchInvsIfNeeded()
	{
		if (!Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_PostMatchInvs, DEFAULT_PostMatchInvs))
		{
			return;
		}

		final Set<InvoiceLineId> invoiceLineIds = new HashSet<>();
		for (final DocLine_Invoice line : getDocLines())
		{
			invoiceLineIds.add(line.getInvoiceLineId());
		}

		// 08643
		// Do nothing in case there are no invoice lines
		if (invoiceLineIds.isEmpty())
		{
			return;
		}

		final Set<MatchInvId> matchInvIds = matchInvDAO.retrieveIdsProcessedButNotPostedForInvoiceLines(invoiceLineIds);
		postDependingDocuments(I_M_MatchInv.Table_Name, matchInvIds);
	}

	public static void unpost(final I_C_Invoice invoice)
	{
		// Make sure the period is open
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		MPeriod.testPeriodOpen(ctx, invoice.getDateAcct(), invoice.getC_DocType_ID(), invoice.getAD_Org_ID());

		Services.get(IFactAcctDAO.class).deleteForDocumentModel(invoice);

		invoice.setPosted(false);
		InterfaceWrapperHelper.save(invoice);
	}
}   // Doc_Invoice
