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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCostDetail;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLandedCostAllocation;
import org.compiere.model.MPeriod;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;

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
	private DocTax[] m_taxes = null;
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
		MInvoice invoice = (MInvoice)getPO();
		setDateDoc(invoice.getDateInvoiced());
		// Amounts
		setAmount(Doc.AMTTYPE_Gross, invoice.getGrandTotal());
		setAmount(Doc.AMTTYPE_Net, invoice.getTotalLines());
		setAmount(Doc.AMTTYPE_Charge, invoice.getChargeAmt());

		// Contained Objects
		m_taxes = loadTaxes();
		p_lines = loadLines(invoice);
		log.debug("Lines=" + p_lines.length + ", Taxes=" + m_taxes.length);
		return null;
	}   // loadDocumentDetails

	/**
	 * Load Invoice Taxes
	 * 
	 * @return DocTax Array
	 */
	private DocTax[] loadTaxes()
	{
		final List<DocTax> list = new ArrayList<DocTax>();
		final String sql = "SELECT it.C_Tax_ID, t.Name, t.Rate, it.TaxBaseAmt, it.TaxAmt, t.IsSalesTax " // 1..6
				+ ", it." + I_C_InvoiceTax.COLUMNNAME_IsTaxIncluded // 7
				+ " FROM C_Tax t, C_InvoiceTax it "
				+ " WHERE t.C_Tax_ID=it.C_Tax_ID AND it.C_Invoice_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			pstmt.setInt(1, get_ID());
			rs = pstmt.executeQuery();
			//
			while (rs.next())
			{
				final int C_Tax_ID = rs.getInt(1);
				final String taxName = rs.getString(2);
				final BigDecimal rate = rs.getBigDecimal(3);
				final BigDecimal taxBaseAmt = rs.getBigDecimal(4);
				final BigDecimal taxAmt = rs.getBigDecimal(5);
				final boolean salesTax = "Y".equals(rs.getString(6));
				final boolean taxIncluded = "Y".equals(rs.getString(7));
				//
				final DocTax taxLine = new DocTax(getCtx(),
						C_Tax_ID, taxName, rate,
						taxBaseAmt, taxAmt, salesTax, taxIncluded);
				log.debug(taxLine.toString());
				list.add(taxLine);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Return Array
		DocTax[] tl = new DocTax[list.size()];
		list.toArray(tl);
		return tl;
	}	// loadTaxes

	/**
	 * Load Invoice Line
	 *
	 * @param invoice invoice
	 * @return DocLine Array
	 */
	private DocLine[] loadLines(MInvoice invoice)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
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
				m_allLinesService = false;
			else
				m_allLinesItem = false;

			//
			log.debug("{}", docLine);
			list.add(docLine);
		}

		// Convert to Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);

		// Included Tax - make sure that no difference
		for (final DocTax docTax : m_taxes)
		{
			if (!docTax.isTaxIncluded())
			{
				continue;
			}

			if (docTax.isIncludedTaxDifference())
			{
				BigDecimal diff = docTax.getIncludedTaxDifference();
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
		BigDecimal retValue = Env.ZERO;
		StringBuilder sb = new StringBuilder(" [");
		// Total
		retValue = retValue.add(getAmount(Doc.AMTTYPE_Gross));
		sb.append(getAmount(Doc.AMTTYPE_Gross));
		// - Header Charge
		retValue = retValue.subtract(getAmount(Doc.AMTTYPE_Charge));
		sb.append("-").append(getAmount(Doc.AMTTYPE_Charge));
		// - Tax
		for (int i = 0; i < m_taxes.length; i++)
		{
			retValue = retValue.subtract(m_taxes[i].getTaxAmt());
			sb.append("-").append(m_taxes[i].getTaxAmt());
		}
		// - Lines
		for (int i = 0; i < p_lines.length; i++)
		{
			retValue = retValue.subtract(p_lines[i].getAmtSource());
			sb.append("-").append(p_lines[i].getAmtSource());
		}
		sb.append("]");
		//
		log.debug(toString() + " Balance=" + retValue + sb.toString());
		return retValue;
	}   // getBalance

	final DocTax getDocTaxOrNull(final int taxId)
	{
		for (final DocTax docTax : m_taxes)
		{
			if (docTax.getC_Tax_ID() == taxId)
			{
				return docTax;
			}
		}

		return null;
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
		//
		final List<Fact> facts = new ArrayList<>();
		// create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// Cash based accounting
		if (!as.isAccrual())
		{
			return facts;
		}

		// ** ARI, ARF
		if (getDocumentType().equals(DOCTYPE_ARInvoice)
				|| getDocumentType().equals(DOCTYPE_ARProForma))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			// Header Charge CR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0)
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
						getC_Currency_ID(), null, amt);
			// TaxDue CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getTaxAmt();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
							getC_Currency_ID(), null, amt);
					if (tl != null)
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
				}
			}
			// Revenue CR
			for (int i = 0; i < p_lines.length; i++)
			{
				amt = p_lines[i].getAmtSource();
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amt = amt.add(discount);
						dAmt = discount;
						fact.createLine(p_lines[i],
								p_lines[i].getAccount(ProductCost.ACCTTYPE_P_TDiscountGrant, as),
								getC_Currency_ID(), dAmt, null);
					}
				}
				fact.createLine(p_lines[i],
						p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as),
						getC_Currency_ID(), null, amt);
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}
			}
			// Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      // from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
				}
			}

			// Receivables DR
			int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable, as);
			int receivablesServices_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable_Services, as);
			if (m_allLinesItem || !as.isPostServices()
					|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
						getC_Currency_ID(), grossAmt, null);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
						getC_Currency_ID(), serviceAmt, null);
		}
		// ARC
		else if (getDocumentType().equals(DOCTYPE_ARCredit))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			// Header Charge DR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0)
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
						getC_Currency_ID(), amt, null);
			// TaxDue DR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getTaxAmt();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
							getC_Currency_ID(), amt, null);
					if (tl != null)
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
				}
			}
			// Revenue CR
			for (int i = 0; i < p_lines.length; i++)
			{
				amt = p_lines[i].getAmtSource();
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amt = amt.add(discount);
						dAmt = discount;
						fact.createLine(p_lines[i],
								p_lines[i].getAccount(ProductCost.ACCTTYPE_P_TDiscountGrant, as),
								getC_Currency_ID(), null, dAmt);
					}
				}
				fact.createLine(p_lines[i],
						p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as),
						getC_Currency_ID(), amt, null);
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}
			}
			// Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      // from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
				}
			}
			// Receivables CR
			int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable, as);
			int receivablesServices_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable_Services, as);
			if (m_allLinesItem || !as.isPostServices()
					|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
						getC_Currency_ID(), null, grossAmt);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
						getC_Currency_ID(), null, serviceAmt);
		}

		// ** API
		else if (getDocumentType().equals(DOCTYPE_APInvoice)
				|| getDocumentType().equals(Constants.DOCBASETYPE_AEInvoice) // metas-ts: treating commission/salary invoice like AP invoice
				|| getDocumentType().equals(Constants.DOCBASETYPE_AVIinvoice))  // metas-ts: treating invoice for recurrent payment like AP invoice
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			// Charge DR
			fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), getAmount(Doc.AMTTYPE_Charge), null);
			// TaxCredit DR
			for (int i = 0; i < m_taxes.length; i++)
			{
				final FactLine tl = fact.createLine(null,
						m_taxes[i].getAccount(m_taxes[i].getAPTaxType(), as),  // account
						getC_Currency_ID(),
						m_taxes[i].getTaxAmt(), null // DR/CR
				);
				if (tl != null)
					tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
			}
			// Expense/InventoryClearing DR
			for (int i = 0; i < p_lines.length; i++)
			{
				final DocLine_Invoice line = (DocLine_Invoice)p_lines[i];
				boolean landedCost = landedCost(as, fact, line, true);
				if (landedCost && as.isExplicitCostAdjustment())
				{
					fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
							getC_Currency_ID(), line.getAmtSource(), null);
					//
					final FactLine fl = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
							getC_Currency_ID(), null, line.getAmtSource());
					String desc = line.getDescription();
					if (desc == null)
						desc = "100%";
					else
						desc += " 100%";
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
						MCostDetail.createInvoice(as, line.getAD_Org_ID(),
								line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
								line.get_ID(),  // C_InvoiceLine_ID
								0, 		// No Cost Element
								line.getAmtSource(), line.getQty(),
								line.getDescription(), getTrxName());
					}
				}
			}
			// Set Locations
			final FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  // from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    // to Loc
				}
			}

			// Liability CR
			int payables_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Liability, as);
			int payablesServices_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Liability_Services, as);
			if (m_allLinesItem || !as.isPostServices()
					|| payables_ID == payablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payables_ID), getC_Currency_ID(), null, grossAmt);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID), getC_Currency_ID(), null, serviceAmt);
			//
			updateProductPO(as);	// Only API
			updateProductInfo(as.getC_AcctSchema_ID());    // only API
		}
		// APC
		else if (getDocumentType().equals(DOCTYPE_APCredit))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;
			// Charge CR
			fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), null, getAmount(Doc.AMTTYPE_Charge));
			// TaxCredit CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				final FactLine tl = fact.createLine(null, m_taxes[i].getAccount(m_taxes[i].getAPTaxType(), as),
						getC_Currency_ID(), null, m_taxes[i].getTaxAmt());
				if (tl != null)
					tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
			}
			// Expense CR
			for (int i = 0; i < p_lines.length; i++)
			{
				final DocLine_Invoice line = (DocLine_Invoice)p_lines[i];
				boolean landedCost = landedCost(as, fact, line, false);
				if (landedCost && as.isExplicitCostAdjustment())
				{
					fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
							getC_Currency_ID(), null, line.getAmtSource());
					//
					FactLine fl = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
							getC_Currency_ID(), line.getAmtSource(), null);
					String desc = line.getDescription();
					if (desc == null)
						desc = "100%";
					else
						desc += " 100%";
					fl.setDescription(desc);
				}
				if (!landedCost)
				{
					BigDecimal amt = line.getAmtSource();
					BigDecimal dAmt = null;
					if (as.isTradeDiscountPosted() && !line.isItem())
					{
						BigDecimal discount = line.getDiscount();
						if (discount != null && discount.signum() != 0)
						{
							amt = amt.add(discount);
							dAmt = discount;
							MAccount tradeDiscountReceived = line.getAccount(ProductCost.ACCTTYPE_P_TDiscountRec, as);
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
						MCostDetail.createInvoice(as, line.getAD_Org_ID(),
								line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
								line.get_ID(),  // C_InvoiceLine_ID
								0, 		// No Cost Element
								line.getAmtSource().negate(), line.getQty(),
								line.getDescription(), getTrxName());
					}
				}
			}
			// Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  // from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    // to Loc
				}
			}
			// Liability DR
			int payables_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Liability, as);
			int payablesServices_ID = getValidCombination_ID(Doc.ACCTTYPE_V_Liability_Services, as);
			if (m_allLinesItem || !as.isPostServices()
					|| payables_ID == payablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payables_ID),
						getC_Currency_ID(), grossAmt, null);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID),
						getC_Currency_ID(), serviceAmt, null);
		}
		else
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setFact(fact)
					.setPostingStatus(PostingStatus.Error)
					.setDetailMessage("DocumentType unknown: " + getDocumentType());
		}
		//
		facts.add(fact);
		return facts;
	}   // createFact

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
	public BigDecimal createFactCash(MAcctSchema as, Fact fact, BigDecimal multiplier)
	{
		boolean creditMemo = getDocumentType().equals(DOCTYPE_ARCredit)
				|| getDocumentType().equals(DOCTYPE_APCredit);
		boolean payables = getDocumentType().equals(DOCTYPE_APInvoice)
				|| getDocumentType().equals(Constants.DOCBASETYPE_AVIinvoice) // metas-ts: treating invoice for recurrent payment like AP invoice
				|| getDocumentType().equals(Constants.DOCBASETYPE_AEInvoice) // metas-ts: treating commission/salary invoice like AP invoice
				|| getDocumentType().equals(DOCTYPE_APCredit);
		BigDecimal acctAmt = Env.ZERO;
		FactLine fl = null;
		// Revenue/Cost
		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine line = p_lines[i];
			boolean landedCost = false;
			if (payables)
				landedCost = landedCost(as, fact, line, false);
			if (landedCost && as.isExplicitCostAdjustment())
			{
				fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), null, line.getAmtSource());
				//
				fl = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						getC_Currency_ID(), line.getAmtSource(), null);
				String desc = line.getDescription();
				if (desc == null)
					desc = "100%";
				else
					desc += " 100%";
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
						acct = line.getAccount(ProductCost.ACCTTYPE_P_InventoryClearing, as);
				}
				BigDecimal amt = line.getAmtSource().multiply(multiplier);
				BigDecimal amt2 = null;
				if (creditMemo)
				{
					amt2 = amt;
					amt = null;
				}
				if (payables) 	// Vendor = DR
					fl = fact.createLine(line, acct,
							getC_Currency_ID(), amt, amt2);
				else
					// Customer = CR
					fl = fact.createLine(line, acct,
							getC_Currency_ID(), amt2, amt);
				if (fl != null)
					acctAmt = acctAmt.add(fl.getAcctBalance());
			}
		}
		// Tax
		for (int i = 0; i < m_taxes.length; i++)
		{
			BigDecimal amt = m_taxes[i].getTaxAmt();
			BigDecimal amt2 = null;
			if (creditMemo)
			{
				amt2 = amt;
				amt = null;
			}
			FactLine tl = null;
			if (payables)
				tl = fact.createLine(null, m_taxes[i].getAccount(m_taxes[i].getAPTaxType(), as),
						getC_Currency_ID(), amt, amt2);
			else
				tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
						getC_Currency_ID(), amt2, amt);
			if (tl != null)
				tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
		}
		// Set Locations
		FactLine[] fLines = fact.getLines();
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
	private boolean landedCost(MAcctSchema as, Fact fact, DocLine line, boolean dr)
	{
		int C_InvoiceLine_ID = line.get_ID();
		MLandedCostAllocation[] lcas = MLandedCostAllocation.getOfInvoiceLine(
				getCtx(), C_InvoiceLine_ID, getTrxName());
		if (lcas.length == 0)
			return false;

		// Calculate Total Base
		double totalBase = 0;
		for (int i = 0; i < lcas.length; i++)
			totalBase += lcas[i].getBase().doubleValue();

		// Create New
		MInvoiceLine il = new MInvoiceLine(getCtx(), C_InvoiceLine_ID, getTrxName());
		for (int i = 0; i < lcas.length; i++)
		{
			MLandedCostAllocation lca = lcas[i];
			if (lca.getBase().signum() == 0)
				continue;
			double percent = lca.getBase().doubleValue() / totalBase;
			String desc = il.getDescription();
			if (desc == null)
				desc = percent + "%";
			else
				desc += " - " + percent + "%";
			if (line.getDescription() != null)
				desc += " - " + line.getDescription();

			// Accounting
			ProductCost pc = new ProductCost(getCtx(),
					lca.getM_Product_ID(), lca.getM_AttributeSetInstance_ID(), getTrxName());
			BigDecimal drAmt = null;
			BigDecimal crAmt = null;
			if (dr)
				drAmt = lca.getAmt();
			else
				crAmt = lca.getAmt();
			FactLine fl = fact.createLine(line, pc.getAccount(ProductCost.ACCTTYPE_P_CostAdjustment, as),
					getC_Currency_ID(), drAmt, crAmt);
			fl.setDescription(desc);
			fl.setM_Product_ID(lca.getM_Product_ID());

			// Cost Detail - Convert to AcctCurrency
			BigDecimal allocationAmt = lca.getAmt();
			if (getC_Currency_ID() != as.getC_Currency_ID())
				allocationAmt = currencyConversionBL.convert(getCtx(), allocationAmt,
						getC_Currency_ID(), as.getC_Currency_ID(),
						getDateAcct(), getC_ConversionType_ID(),
						getAD_Client_ID(), getAD_Org_ID());
			if (allocationAmt.scale() > as.getCostingPrecision())
				allocationAmt = allocationAmt.setScale(as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
			if (!dr)
				allocationAmt = allocationAmt.negate();
			// AZ Goodwill
			// use createInvoice to create/update non Material Cost Detail
			MCostDetail.createInvoice(as, lca.getAD_Org_ID(),
					lca.getM_Product_ID(), lca.getM_AttributeSetInstance_ID(),
					C_InvoiceLine_ID, lca.getM_CostElement_ID(),
					allocationAmt, lca.getQty(),
					desc, getTrxName());
			// end AZ
		}

		log.info("Created #" + lcas.length);
		return true;
	}	// landedCosts

	/**
	 * Update ProductPO PriceLastInv
	 *
	 * @param as accounting schema
	 */
	private void updateProductPO(final MAcctSchema as)
	{
		final I_AD_ClientInfo ci = Services.get(IClientDAO.class).retrieveClientInfo(getCtx(), as.getAD_Client_ID());
		if (ci.getC_AcctSchema1_ID() != as.getC_AcctSchema_ID())
			return;

		StringBuilder sql = new StringBuilder(
				"UPDATE M_Product_PO po "
						+ "SET PriceLastInv = "
						// select
						+ "(SELECT currencyConvert(il.PriceActual,i.C_Currency_ID,po.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) "
						+ "FROM C_Invoice i, C_InvoiceLine il "
						+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
						+ " AND po.M_Product_ID=il.M_Product_ID AND po.C_BPartner_ID=i.C_BPartner_ID");
		// jz + " AND ROWNUM=1 AND i.C_Invoice_ID=").append(get_ID()).append(") ")

		sql.append(" AND il.C_InvoiceLine_ID = (SELECT MIN(il1.C_InvoiceLine_ID) "
				+ "FROM C_Invoice i1, C_InvoiceLine il1 "
				+ "WHERE i1.C_Invoice_ID=il1.C_Invoice_ID"
				+ " AND po.M_Product_ID=il1.M_Product_ID AND po.C_BPartner_ID=i1.C_BPartner_ID")
				.append("  AND i1.C_Invoice_ID=").append(get_ID()).append(") ");

		sql.append("  AND i.C_Invoice_ID=").append(get_ID()).append(") ")
				// update
				.append("WHERE EXISTS (SELECT * "
						+ "FROM C_Invoice i, C_InvoiceLine il "
						+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
						+ " AND po.M_Product_ID=il.M_Product_ID AND po.C_BPartner_ID=i.C_BPartner_ID"
						+ " AND i.C_Invoice_ID=")
				.append(get_ID()).append(")");
		int no = DB.executeUpdate(sql.toString(), getTrxName());
		log.debug("Updated=" + no);
	}	// updateProductPO

	/**
	 * Update Product Info (old).
	 * - Costing (PriceLastInv)
	 * - PO (PriceLastInv)
	 * 
	 * @param C_AcctSchema_ID accounting schema
	 * @deprecated old costing
	 */
	private void updateProductInfo(int C_AcctSchema_ID)
	{
		log.debug("C_Invoice_ID=" + get_ID());

		/**
		 * @todo Last.. would need to compare document/last updated date
		 *       would need to maintain LastPriceUpdateDate on _PO and _Costing
		 */

		// update Product Costing
		// requires existence of currency conversion !!
		// if there are multiple lines of the same product last price uses first
		// -> TotalInvAmt is sometimes NULL !! -> error
		// begin globalqss 2005-10-19
		// postgresql doesn't support LIMIT on UPDATE or DELETE statements
		/*
		 * StringBuilder sql = new StringBuilder (
		 * "UPDATE M_Product_Costing pc "
		 * + "SET (PriceLastInv, TotalInvAmt,TotalInvQty) = "
		 * // select
		 * + "(SELECT currencyConvert(il.PriceActual,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID),"
		 * + " currencyConvert(il.LineNetAmt,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID),il.QtyInvoiced "
		 * + "FROM C_Invoice i, C_InvoiceLine il, C_AcctSchema a "
		 * + "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
		 * + " AND pc.M_Product_ID=il.M_Product_ID AND pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
		 * + " AND ROWNUM=1"
		 * + " AND pc.C_AcctSchema_ID=").append(C_AcctSchema_ID).append(" AND i.C_Invoice_ID=")
		 * .append(get_ID()).append(") ")
		 * // update
		 * .append("WHERE EXISTS (SELECT * "
		 * + "FROM C_Invoice i, C_InvoiceLine il, C_AcctSchema a "
		 * + "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
		 * + " AND pc.M_Product_ID=il.M_Product_ID AND pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
		 * + " AND pc.C_AcctSchema_ID=").append(C_AcctSchema_ID).append(" AND i.C_Invoice_ID=")
		 * .append(get_ID()).append(")");
		 */
		// the next command is equivalent and works in postgresql and oracle
		final StringBuilder sql = new StringBuilder(
				"UPDATE M_Product_Costing pc "
						+ "SET (PriceLastInv, TotalInvAmt,TotalInvQty) = "
						// select
						+ "(SELECT currencyConvert(il.PriceActual,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID),"
						+ " currencyConvert(il.LineNetAmt,i.C_Currency_ID,a.C_Currency_ID,i.DateInvoiced,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID),il.QtyInvoiced "
						+ "FROM C_Invoice i, C_InvoiceLine il, C_AcctSchema a "
						+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
						+ " AND il.c_invoiceline_id = (SELECT MIN(C_InvoiceLine_ID) FROM C_InvoiceLine il2" +
						" WHERE  il2.M_PRODUCT_ID=il.M_PRODUCT_ID AND C_Invoice_ID=")
								.append(get_ID()).append(")"
										+ " AND pc.M_Product_ID=il.M_Product_ID AND pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
										+ " AND pc.C_AcctSchema_ID=")
								.append(C_AcctSchema_ID).append(" AND i.C_Invoice_ID=")
								.append(get_ID()).append(") ")
								// update
								.append("WHERE EXISTS (SELECT * "
										+ "FROM C_Invoice i, C_InvoiceLine il, C_AcctSchema a "
										+ "WHERE i.C_Invoice_ID=il.C_Invoice_ID"
										+ " AND pc.M_Product_ID=il.M_Product_ID AND pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
										+ " AND pc.C_AcctSchema_ID=")
								.append(C_AcctSchema_ID).append(" AND i.C_Invoice_ID=")
								.append(get_ID()).append(")");
		// end globalqss 2005-10-19
		final String sqlNative = DB.convertSqlToNative(sql.toString());
		final int no = DB.executeUpdate(sqlNative, getTrxName());
		log.debug("M_Product_Costing - Updated=" + no);
	}   // updateProductInfo

	public static void unpost(final I_C_Invoice invoice)
	{
		// Make sure the period is open
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		MPeriod.testPeriodOpen(ctx, invoice.getDateAcct(), invoice.getC_DocType_ID(), invoice.getAD_Org_ID());

		Services.get(IFactAcctDAO.class).deleteForDocument(invoice);

		invoice.setPosted(false);
		InterfaceWrapperHelper.save(invoice);
	}
}   // Doc_Invoice
