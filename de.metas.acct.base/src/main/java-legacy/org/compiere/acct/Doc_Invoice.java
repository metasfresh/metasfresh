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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.DBException;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MPeriod;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.ProductAcctType;

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
 *         <li>BF: 2797257 Landed Cost Detail is not using allocation qty
 *
 * @version $Id: Doc_Invoice.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Invoice extends Doc<DocLine_Invoice>
{
	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_Invoice.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;

	/** Contained Optional Tax Lines */
	private List<DocTax> _taxes = null;
	/** All lines are Service */
	private boolean m_allLinesService = true;
	/** All lines are product item */
	private boolean m_allLinesItem = true;

	public Doc_Invoice(final IDocBuilder docBuilder)
	{
		super(docBuilder);
	}	// Doc_Invoice

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
			pstmt = DB.prepareStatement(sql, getTrxName());
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			//
			final ImmutableList.Builder<DocTax> docTaxes = ImmutableList.builder();
			while (rs.next())
			{
				final int C_Tax_ID = rs.getInt(1);
				final String taxName = rs.getString(2);
				final BigDecimal rate = rs.getBigDecimal(3);
				final BigDecimal taxBaseAmt = rs.getBigDecimal(4);
				final BigDecimal taxAmt = rs.getBigDecimal(5);
				final boolean salesTax = DisplayType.toBoolean(rs.getString(6));
				final boolean taxIncluded = DisplayType.toBoolean(rs.getString(7));
				//
				final DocTax taxLine = new DocTax(getCtx(),
						C_Tax_ID, taxName, rate,
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
	}	// loadTaxes

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

			final DocLine_Invoice docLine = new DocLine_Invoice(line, this);

			//
			// Collect included tax (if any)
			final BigDecimal lineIncludedTaxAmt = docLine.getIncludedTaxAmt();
			if (lineIncludedTaxAmt.signum() != 0)
			{
				final DocTax docTax = getDocTaxOrNull(docLine.getC_Tax_ID());
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
					if (docLine.getC_Tax_ID() == docTax.getC_Tax_ID())
					{
						docLine.setLineNetAmtDifference(diff);
						break;
					}
				} 	// for all lines
			} 	// tax difference
		} 	// for all taxes

		//
		return docLines;
	}	// loadLines

	final boolean isCreditMemo()
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

	final DocTax getDocTaxOrNull(final int taxId)
	{
		return getTaxes()
				.stream()
				.filter(docTax -> docTax.getC_Tax_ID() == taxId)
				.findFirst()
				.orElse(null);
	}

	/**
	 * Create Facts (the accounting logic) for
	 * ARI, ARC, ARF, API, APC.
	 *
	 * <pre>
	 *  ARI, ARF
	 *      Receivables     DR
	 *      Charge                  CR
	 *      TaxDue                  CR
	 *      Revenue                 CR
	 *
	 *  ARC
	 *      Receivables             CR
	 *      Charge          DR
	 *      TaxDue          DR
	 *      Revenue         RR
	 *
	 *  API
	 *      Payables                CR
	 *      Charge          DR
	 *      TaxCredit       DR
	 *      Expense         DR
	 *
	 *  APC
	 *      Payables        DR
	 *      Charge                  CR
	 *      TaxCredit               CR
	 *      Expense                 CR
	 * </pre>
	 *
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final MAcctSchema as)
	{
		// Cash based accounting
		if (!as.isAccrual())
		{
			return new ArrayList<>();
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
				|| Constants.DOCBASETYPE_AEInvoice.equals(docBaseType)  // metas-ts: treating commission/salary invoice like AP invoice
				|| Constants.DOCBASETYPE_AVIinvoice.equals(docBaseType))   // metas-ts: treating invoice for recurrent payment like AP invoice
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
					.setC_AcctSchema(as)
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
	private List<Fact> createFacts_SalesInvoice(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		// Header Charge CR
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as), getC_Currency_ID(), null, chargeAmt);
		}

		// TaxDue CR
		for (final DocTax docTax : getTaxes())
		{
			final BigDecimal taxAmt = docTax.getTaxAmt();
			if (taxAmt != null && taxAmt.signum() != 0)
			{
				final FactLine tl = fact.createLine(null, docTax.getTaxDueAcct(as),
						getC_Currency_ID(), null, taxAmt);
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
			if (as.isTradeDiscountPosted())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					lineAmt = lineAmt.add(discount);
					dAmt = discount;
					fact.createLine(line,
							line.getAccount(ProductAcctType.TDiscountGrant, as),
							getC_Currency_ID(), dAmt, null);
				}
			}
			fact.createLine(line,
					line.getAccount(ProductAcctType.Revenue, as),
					getC_Currency_ID(), null, lineAmt);
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
		final int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable, as);
		final int receivablesServices_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| receivables_ID == receivablesServices_ID)
		{
			grossAmt = getAmount(Doc.AMTTYPE_Gross);
			serviceAmt = BigDecimal.ZERO;
		}
		else if (m_allLinesService)
		{
			serviceAmt = getAmount(Doc.AMTTYPE_Gross);
			grossAmt = BigDecimal.ZERO;
		}
		if (grossAmt.signum() != 0)
		{
			fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), grossAmt, null);
		}
		if (serviceAmt.signum() != 0)
		{
			fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), serviceAmt, null);
		}

		return facts;
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
	private List<Fact> createFacts_SalesCreditMemo(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		// Header Charge DR
		final BigDecimal chargeAmt = getAmount(Doc.AMTTYPE_Charge);
		if (chargeAmt != null && chargeAmt.signum() != 0)
		{
			fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), chargeAmt, null);
		}

		// TaxDue DR
		for (final DocTax docTax : getTaxes())
		{
			final BigDecimal taxAmt = docTax.getTaxAmt();
			if (taxAmt != null && taxAmt.signum() != 0)
			{
				final FactLine tl = fact.createLine(null, docTax.getTaxDueAcct(as),
						getC_Currency_ID(), taxAmt, null);
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
			if (as.isTradeDiscountPosted())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					lineAmt = lineAmt.add(discount);
					dAmt = discount;
					fact.createLine(line,
							line.getAccount(ProductAcctType.TDiscountGrant, as),
							getC_Currency_ID(), null, dAmt);
				}
			}
			fact.createLine(line,
					line.getAccount(ProductAcctType.Revenue, as),
					getC_Currency_ID(), lineAmt, null);
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
		final int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable, as);
		final int receivablesServices_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| receivables_ID == receivablesServices_ID)
		{
			grossAmt = getAmount(Doc.AMTTYPE_Gross);
			serviceAmt = BigDecimal.ZERO;
		}
		else if (m_allLinesService)
		{
			serviceAmt = getAmount(Doc.AMTTYPE_Gross);
			grossAmt = BigDecimal.ZERO;
		}
		if (grossAmt.signum() != 0)
		{
			fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), null, grossAmt);
		}
		if (serviceAmt.signum() != 0)
		{
			fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), null, serviceAmt);
		}

		return facts;
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
	private List<Fact> createFacts_PurchaseInvoice(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;

		// Charge DR
		fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
				getC_Currency_ID(), getAmount(Doc.AMTTYPE_Charge), null);

		// TaxCredit DR
		for (final DocTax docTax : getTaxes())
		{
			final FactLine tl = fact.createLine(null,
					docTax.getAccount(as),  // account
					getC_Currency_ID(),
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
			if (as.isTradeDiscountPosted() && !line.isItem())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					amt = amt.add(discount);
					dAmt = discount;
					final MAccount tradeDiscountReceived = line.getAccount(ProductAcctType.TDiscountRec, as);
					fact.createLine(line, tradeDiscountReceived, getC_Currency_ID(), null, dAmt);
				}
			}

			if (line.isItem())  // stockable item
			{
				final BigDecimal amtReceived = line.calculateAmtOfQtyReceived(amt);
				fact.createLine(line,
						line.getAccount(ProductAcctType.InventoryClearing, as),
						getC_Currency_ID(),
						amtReceived, null,  // DR/CR
						line.getQtyReceivedAbs());

				final BigDecimal amtNotReceived = amt.subtract(amtReceived);
				fact.createLine(line,
						line.getAccount(ProductAcctType.Expense, as),
						getC_Currency_ID(),
						amtNotReceived, null,  // DR/CR
						line.getQtyNotReceivedAbs());
			}
			else // service
			{
				fact.createLine(line, line.getAccount(ProductAcctType.Expense, as), getC_Currency_ID(), amt, null);
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
		final int payables_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Liability, as);
		final int payablesServices_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Liability_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| payables_ID == payablesServices_ID)
		{
			grossAmt = getAmount(Doc.AMTTYPE_Gross);
			serviceAmt = BigDecimal.ZERO;
		}
		else if (m_allLinesService)
		{
			serviceAmt = getAmount(Doc.AMTTYPE_Gross);
			grossAmt = BigDecimal.ZERO;
		}
		if (grossAmt.signum() != 0)
		{
			fact.createLine(null, MAccount.get(getCtx(), payables_ID), getC_Currency_ID(), null, grossAmt);
		}
		if (serviceAmt.signum() != 0)
		{
			fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID), getC_Currency_ID(), null, serviceAmt);
		}

		return facts;
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
	private List<Fact> createFacts_PurchaseCreditMemo(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
		BigDecimal serviceAmt = BigDecimal.ZERO;
		// Charge CR
		fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
				getC_Currency_ID(), null, getAmount(Doc.AMTTYPE_Charge));
		// TaxCredit CR
		for (final DocTax docTax : getTaxes())
		{
			final FactLine tl = fact.createLine(null, docTax.getAccount(as),
					getC_Currency_ID(), null, docTax.getTaxAmt());
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
			if (as.isTradeDiscountPosted() && !line.isItem())
			{
				final BigDecimal discount = line.getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					amt = amt.add(discount);
					dAmt = discount;
					final MAccount tradeDiscountReceived = line.getAccount(ProductAcctType.TDiscountRec, as);
					fact.createLine(line, tradeDiscountReceived, getC_Currency_ID(), dAmt, null);
				}
			}

			if (line.isItem())  // stockable item
			{
				final BigDecimal amtReceived = line.calculateAmtOfQtyReceived(amt);
				fact.createLine(line,
						line.getAccount(ProductAcctType.InventoryClearing, as),
						getC_Currency_ID(),
						null, amtReceived,  // DR/CR
						line.getQtyReceivedAbs());

				final BigDecimal amtNotReceived = amt.subtract(amtReceived);
				fact.createLine(line,
						line.getAccount(ProductAcctType.Expense, as),
						getC_Currency_ID(),
						null, amtNotReceived,  // DR/CR
						line.getQtyNotReceivedAbs());
			}
			else // service
			{
				fact.createLine(line, line.getAccount(ProductAcctType.Expense, as), getC_Currency_ID(), null, amt);
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
		final int payables_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Liability, as);
		final int payablesServices_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Liability_Services, as);
		if (m_allLinesItem
				|| !as.isPostServices()
				|| payables_ID == payablesServices_ID)
		{
			grossAmt = getAmount(Doc.AMTTYPE_Gross);
			serviceAmt = BigDecimal.ZERO;
		}
		else if (m_allLinesService)
		{
			serviceAmt = getAmount(Doc.AMTTYPE_Gross);
			grossAmt = BigDecimal.ZERO;
		}
		if (grossAmt.signum() != 0)
		{
			fact.createLine(null, MAccount.get(getCtx(), payables_ID),
					getC_Currency_ID(), grossAmt, null);
		}
		if (serviceAmt.signum() != 0)
		{
			fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID),
					getC_Currency_ID(), serviceAmt, null);
		}

		return facts;
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

		final Set<Integer> invoiceLineIds = new HashSet<>();
		for (final DocLine_Invoice line : getDocLines())
		{
			invoiceLineIds.add(line.get_ID());
		}

		// 08643
		// Do nothing in case there are no invoice lines
		if (invoiceLineIds.isEmpty())
		{
			return;
		}

		final List<I_M_MatchInv> matchInvs = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_MatchInv.class, getCtx(), getTrxName())
				.addInArrayOrAllFilter(I_M_MatchInv.COLUMN_C_InvoiceLine_ID, invoiceLineIds)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_MatchInv.COLUMN_Processed, true)
				.addNotEqualsFilter(I_M_MatchInv.COLUMN_Posted, true)
				.create()
				.list();

		postDependingDocuments(matchInvs);

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
