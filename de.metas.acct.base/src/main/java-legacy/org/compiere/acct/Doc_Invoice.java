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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLandedCostAllocation;
import org.compiere.model.MPeriod;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailService;

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
public class Doc_Invoice extends Doc
{
	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_Invoice.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;

	/**
	 * Constructor
	 *
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_Invoice(final IDocBuilder docBuilder)
	{
		super(docBuilder);
	}	// Doc_Invoice

	/** Contained Optional Tax Lines */
	private List<DocTax> _taxes = null;
	/** All lines are Service */
	private boolean m_allLinesService = true;
	/** All lines are product item */
	private boolean m_allLinesItem = true;

	/**
	 * Load Specific Document Details
	 *
	 * @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		final MInvoice invoice = (MInvoice)getPO();
		setDateDoc(invoice.getDateInvoiced());
		// Amounts
		setAmount(Doc.AMTTYPE_Gross, invoice.getGrandTotal());
		setAmount(Doc.AMTTYPE_Net, invoice.getTotalLines());
		setAmount(Doc.AMTTYPE_Charge, invoice.getChargeAmt());

		// Contained Objects
		p_lines = loadLines(invoice);
		return null;
	}   // loadDocumentDetails

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

	/**
	 * Load Invoice Line
	 *
	 * @param invoice invoice
	 * @return DocLine Array
	 */
	private DocLine[] loadLines(final MInvoice invoice)
	{
		final ArrayList<DocLine> list = new ArrayList<>();
		//
		final MInvoiceLine[] lines = invoice.getLines(false);
		for (final MInvoiceLine line : lines)
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

			//
			log.debug("{}", docLine);
			list.add(docLine);
		}

		// Convert to Array
		final DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);

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
				for (int j = 0; j < dls.length; j++)
				{
					if (dls[j].getC_Tax_ID() == docTax.getC_Tax_ID())
					{
						dls[j].setLineNetAmtDifference(diff);
						break;
					}
				} 	// for all lines
			} 	// tax difference
		} 	// for all taxes

		// Return Array
		return dls;
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
		for (final DocLine docLine : p_lines)
		{
			retValue = retValue.subtract(docLine.getAmtSource());
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
		for (int i = 0; i < p_lines.length; i++)
		{
			BigDecimal lineAmt = p_lines[i].getAmtSource();
			BigDecimal dAmt = null;
			if (as.isTradeDiscountPosted())
			{
				final BigDecimal discount = p_lines[i].getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					lineAmt = lineAmt.add(discount);
					dAmt = discount;
					fact.createLine(p_lines[i],
							p_lines[i].getAccount(ProductCost.ACCTTYPE_P_TDiscountGrant, as),
							getC_Currency_ID(), dAmt, null);
				}
			}
			fact.createLine(p_lines[i],
					p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as),
					getC_Currency_ID(), null, lineAmt);
			if (!p_lines[i].isItem())
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
		if (m_allLinesItem || !as.isPostServices()
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
		for (int i = 0; i < p_lines.length; i++)
		{
			BigDecimal lineAmt = p_lines[i].getAmtSource();
			BigDecimal dAmt = null;
			if (as.isTradeDiscountPosted())
			{
				final BigDecimal discount = p_lines[i].getDiscount();
				if (discount != null && discount.signum() != 0)
				{
					lineAmt = lineAmt.add(discount);
					dAmt = discount;
					fact.createLine(p_lines[i],
							p_lines[i].getAccount(ProductCost.ACCTTYPE_P_TDiscountGrant, as),
							getC_Currency_ID(), null, dAmt);
				}
			}
			fact.createLine(p_lines[i],
					p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as),
					getC_Currency_ID(), lineAmt, null);
			if (!p_lines[i].isItem())
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
		if (m_allLinesItem || !as.isPostServices()
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
		for (int i = 0; i < p_lines.length; i++)
		{
			final DocLine_Invoice line = (DocLine_Invoice)p_lines[i];
			final boolean landedCost = landedCost(as, fact, line, true);
			if (landedCost && as.isExplicitCostAdjustment())
			{
				fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), line.getAmtSource(), null);
				//
				final FactLine fl = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), null, line.getAmtSource());
				String desc = line.getDescription();
				if (desc == null)
				{
					desc = "100%";
				}
				else
				{
					desc += " 100%";
				}
				fl.setDescription(desc);
			}

			if (!landedCost)
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
						final MAccount tradeDiscountReceived = line.getAccount(ProductCost.ACCTTYPE_P_TDiscountRec, as);
						fact.createLine(line, tradeDiscountReceived, getC_Currency_ID(), null, dAmt);
					}
				}

				if (line.isItem())  // stockable item
				{
					final BigDecimal amtReceived = line.calculateAmtOfQtyReceived(amt);
					fact.createLine(line,
							line.getAccount(ProductCost.ACCTTYPE_P_InventoryClearing, as),
							getC_Currency_ID(),
							amtReceived, null,  // DR/CR
							line.getQtyReceivedAbs());

					final BigDecimal amtNotReceived = amt.subtract(amtReceived);
					fact.createLine(line,
							line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
							getC_Currency_ID(),
							amtNotReceived, null,  // DR/CR
							line.getQtyNotReceivedAbs());
				}
				else // service
				{
					fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as), getC_Currency_ID(), amt, null);
				}

				if (!line.isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}
				//
				if (line.getM_Product_ID() > 0 && line.isService())  // otherwise Inv Matching
				{
					Services.get(ICostDetailService.class)
							.createCostDetail(CostDetailCreateRequest.builder()
									.acctSchemaId(as.getC_AcctSchema_ID())
									.clientId(line.getAD_Client_ID())
									.orgId(line.getAD_Org_ID())
									.productId(line.getM_Product_ID())
									.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
									.documentRef(CostingDocumentRef.ofPurchaseInvoiceLineId(line.get_ID()))
									.costElementId(0)
									.qty(line.getQty())
									.amt(line.getAmtSource())
									.currencyId(line.getC_Currency_ID())
									.currencyConversionTypeId(getC_ConversionType_ID())
									.date(TimeUtil.asLocalDate(getDateAcct()))
									.description(line.getDescription())
									.build());
				}
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
		if (m_allLinesItem || !as.isPostServices()
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
		for (int i = 0; i < p_lines.length; i++)
		{
			final DocLine_Invoice line = (DocLine_Invoice)p_lines[i];
			final boolean landedCost = landedCost(as, fact, line, false);
			if (landedCost && as.isExplicitCostAdjustment())
			{
				fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), null, line.getAmtSource());
				//
				final FactLine fl = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), line.getAmtSource(), null);
				String desc = line.getDescription();
				if (desc == null)
				{
					desc = "100%";
				}
				else
				{
					desc += " 100%";
				}
				fl.setDescription(desc);
			}
			if (!landedCost)
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
						final MAccount tradeDiscountReceived = line.getAccount(ProductCost.ACCTTYPE_P_TDiscountRec, as);
						fact.createLine(line, tradeDiscountReceived, getC_Currency_ID(), dAmt, null);
					}
				}

				if (line.isItem())  // stockable item
				{
					final BigDecimal amtReceived = line.calculateAmtOfQtyReceived(amt);
					fact.createLine(line,
							line.getAccount(ProductCost.ACCTTYPE_P_InventoryClearing, as),
							getC_Currency_ID(),
							null, amtReceived,  // DR/CR
							line.getQtyReceivedAbs());

					final BigDecimal amtNotReceived = amt.subtract(amtReceived);
					fact.createLine(line,
							line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
							getC_Currency_ID(),
							null, amtNotReceived,  // DR/CR
							line.getQtyNotReceivedAbs());
				}
				else // service
				{
					fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as), getC_Currency_ID(), null, amt);
				}

				if (!line.isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}

				//
				if (line.getM_Product_ID() > 0 && line.isService()) 	// otherwise Inv Matching
				{
					Services.get(ICostDetailService.class)
							.createCostDetail(CostDetailCreateRequest.builder()
									.acctSchemaId(as.getC_AcctSchema_ID())
									.clientId(line.getAD_Client_ID())
									.orgId(line.getAD_Org_ID())
									.productId(line.getM_Product_ID())
									.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
									.documentRef(CostingDocumentRef.ofPurchaseInvoiceLineId(line.get_ID()))
									.costElementId(0)
									.qty(line.getQty().negate())
									.amt(line.getAmtSource().negate())
									.currencyId(line.getC_Currency_ID())
									.currencyConversionTypeId(getC_ConversionType_ID())
									.date(TimeUtil.asLocalDate(getDateAcct()))
									.description(line.getDescription())
									.build());
				}
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
		if (m_allLinesItem || !as.isPostServices()
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
		for (final DocLine docLine : getDocLines())
		{
			invoiceLineIds.add(docLine.get_ID());
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

	/**
	 * Create Fact Cash Based (i.e. only revenue/expense)
	 *
	 * @param as accounting schema
	 * @param fact fact to add lines to
	 * @param multiplier source amount multiplier
	 * @return accounted amount
	 */
	BigDecimal createFactCash(final MAcctSchema as, final Fact fact, final BigDecimal multiplier)
	{
		final boolean creditMemo = getDocumentType().equals(DOCTYPE_ARCredit)
				|| getDocumentType().equals(DOCTYPE_APCredit);
		final boolean payables = getDocumentType().equals(DOCTYPE_APInvoice)
				|| getDocumentType().equals(Constants.DOCBASETYPE_AVIinvoice) // metas-ts: treating invoice for recurrent payment like AP invoice
				|| getDocumentType().equals(Constants.DOCBASETYPE_AEInvoice) // metas-ts: treating commission/salary invoice like AP invoice
				|| getDocumentType().equals(DOCTYPE_APCredit);
		BigDecimal acctAmt = BigDecimal.ZERO;
		FactLine fl = null;
		// Revenue/Cost
		for (int i = 0; i < p_lines.length; i++)
		{
			final DocLine line = p_lines[i];
			boolean landedCost = false;
			if (payables)
			{
				landedCost = landedCost(as, fact, line, false);
			}
			if (landedCost && as.isExplicitCostAdjustment())
			{
				fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), null, line.getAmtSource());
				//
				fl = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), line.getAmtSource(), null);
				String desc = line.getDescription();
				if (desc == null)
				{
					desc = "100%";
				}
				else
				{
					desc += " 100%";
				}
				fl.setDescription(desc);
			}
			if (!landedCost)
			{
				MAccount acct = line.getAccount(
						payables ? ProductCost.ACCTTYPE_P_Expense : ProductCost.ACCTTYPE_P_Revenue, as);
				if (payables)
				{
					// if Fixed Asset
					if (line.isItem())
					{
						acct = line.getAccount(ProductCost.ACCTTYPE_P_InventoryClearing, as);
					}
				}
				BigDecimal amt = line.getAmtSource().multiply(multiplier);
				BigDecimal amt2 = null;
				if (creditMemo)
				{
					amt2 = amt;
					amt = null;
				}
				if (payables)
				{
					fl = fact.createLine(line, acct,
							getC_Currency_ID(), amt, amt2);
				}
				else
				{
					// Customer = CR
					fl = fact.createLine(line, acct,
							getC_Currency_ID(), amt2, amt);
				}
				if (fl != null)
				{
					acctAmt = acctAmt.add(fl.getAcctBalance());
				}
			}
		}
		// Tax
		for (final DocTax docTax : getTaxes())
		{
			BigDecimal amt = docTax.getTaxAmt();
			BigDecimal amt2 = null;
			if (creditMemo)
			{
				amt2 = amt;
				amt = null;
			}
			FactLine tl = null;
			if (payables)
			{
				tl = fact.createLine(null, docTax.getAccount(as),
						getC_Currency_ID(), amt, amt2);
			}
			else
			{
				tl = fact.createLine(null, docTax.getTaxDueAcct(as),
						getC_Currency_ID(), amt2, amt);
			}
			if (tl != null)
			{
				tl.setC_Tax_ID(docTax.getC_Tax_ID());
			}
		}
		// Set Locations
		final FactLine[] fLines = fact.getLines();
		for (int i = 0; i < fLines.length; i++)
		{
			if (fLines[i] != null)
			{
				if (payables)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  // from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    // to Loc
				}
				else
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);    // from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
				}
			}
		}
		return acctAmt;
	}	// createFactCash

	/**
	 * Create Landed Cost accounting & Cost lines
	 *
	 * @param as accounting schema
	 * @param fact fact
	 * @param line document line
	 * @param dr DR entry (normal api)
	 * @return true if landed costs were created
	 */
	private boolean landedCost(final MAcctSchema as, final Fact fact, final DocLine line, final boolean dr)
	{
		final int C_InvoiceLine_ID = line.get_ID();
		final MLandedCostAllocation[] lcas = MLandedCostAllocation.getOfInvoiceLine(
				getCtx(), C_InvoiceLine_ID, getTrxName());
		if (lcas.length == 0)
		{
			return false;
		}

		// Calculate Total Base
		double totalBase = 0;
		for (int i = 0; i < lcas.length; i++)
		{
			totalBase += lcas[i].getBase().doubleValue();
		}

		// Create New
		final MInvoiceLine il = new MInvoiceLine(getCtx(), C_InvoiceLine_ID, getTrxName());
		for (int i = 0; i < lcas.length; i++)
		{
			final MLandedCostAllocation lca = lcas[i];
			if (lca.getBase().signum() == 0)
			{
				continue;
			}
			final double percent = lca.getBase().doubleValue() / totalBase;
			String desc = il.getDescription();
			if (desc == null)
			{
				desc = percent + "%";
			}
			else
			{
				desc += " - " + percent + "%";
			}
			if (line.getDescription() != null)
			{
				desc += " - " + line.getDescription();
			}

			// Accounting
			final ProductCost pc = new ProductCost(getCtx(),
					lca.getM_Product_ID(), lca.getM_AttributeSetInstance_ID(), getTrxName());
			BigDecimal drAmt = null;
			BigDecimal crAmt = null;
			if (dr)
			{
				drAmt = lca.getAmt();
			}
			else
			{
				crAmt = lca.getAmt();
			}
			final FactLine fl = fact.createLine(line, pc.getAccount(ProductCost.ACCTTYPE_P_CostAdjustment, as),
					getC_Currency_ID(), drAmt, crAmt);
			fl.setDescription(desc);
			fl.setM_Product_ID(lca.getM_Product_ID());

			// Cost Detail - Convert to AcctCurrency
			BigDecimal allocationAmt = lca.getAmt();
			if (!dr)
			{
				allocationAmt = allocationAmt.negate();
			}
			// AZ Goodwill
			// use createInvoice to create/update non Material Cost Detail
			Services.get(ICostDetailService.class)
					.createCostDetail(CostDetailCreateRequest.builder()
							.acctSchemaId(as.getC_AcctSchema_ID())
							.clientId(lca.getAD_Client_ID())
							.orgId(lca.getAD_Org_ID())
							.productId(lca.getM_Product_ID())
							.attributeSetInstanceId(lca.getM_AttributeSetInstance_ID())
							.documentRef(CostingDocumentRef.ofPurchaseInvoiceLineId(C_InvoiceLine_ID))
							.costElementId(lca.getM_CostElement_ID())
							.qty(lca.getQty())
							.amt(allocationAmt)
							.currencyId(getC_Currency_ID())
							.currencyConversionTypeId(getC_ConversionType_ID())
							.date(TimeUtil.asLocalDate(getDateAcct()))
							.description(desc)
							.build());
			// end AZ
		}

		log.info("Created #{}", lcas.length);
		return true;
	}	// landedCosts

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
