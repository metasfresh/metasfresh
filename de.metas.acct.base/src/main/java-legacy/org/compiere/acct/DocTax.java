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
import java.util.Properties;

import org.adempiere.acct.api.ITaxAcctBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.MAccount;

/**
 * Document Tax Line
 *
 * @author Jorg Janke
 * @version $Id: DocTax.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public final class DocTax
{
	// services
	private final transient ITaxAcctBL taxAcctBL = Services.get(ITaxAcctBL.class);

	// private static final transient Logger log = CLogMgt.getLogger(DocTax.class);

	/**
	 * Create Tax
	 * 
	 * @param C_Tax_ID tax
	 * @param taxName tax name
	 * @param taxRate rate
	 * @param taxBaseAmt tax base amount
	 * @param taxAmt tax amount
	 * @param salesTax sales tax flag
	 * @param taxIncluded
	 */
	public DocTax(final Properties ctx,
			final int C_Tax_ID,
			final String taxName,
			final BigDecimal taxRate,
			final BigDecimal taxBaseAmt, final BigDecimal taxAmt,
			final boolean salesTax,
			final boolean taxIncluded)
	{
		super();

		m_ctx = ctx;
		m_C_Tax_ID = C_Tax_ID;
		m_taxName = taxName;
		m_taxRate = taxRate;
		m_taxBaseAmt = taxBaseAmt;
		m_taxAmt = taxAmt;
		m_salesTax = salesTax;
		this.m_taxIncluded = taxIncluded;
	}	// DocTax

	private final Properties m_ctx;
	/** Tax ID */
	private final int m_C_Tax_ID;
	/** Amount */
	private final BigDecimal m_taxAmt;
	/** Tax Rate */
	private final BigDecimal m_taxRate;
	/** Name */
	private final String m_taxName;
	/** Base Tax Amt */
	private final BigDecimal m_taxBaseAmt;
	/** Included Tax */
	private BigDecimal m_includedTax = BigDecimal.ZERO;
	/** Sales Tax */
	private final boolean m_salesTax;
	private final boolean m_taxIncluded;

	/** Tax Due Acct */
	public static final int ACCTTYPE_TaxDue = ITaxAcctBL.ACCTTYPE_TaxDue;
	/** Tax Liability */
	public static final int ACCTTYPE_TaxLiability = ITaxAcctBL.ACCTTYPE_TaxLiability;
	/** Tax Credit */
	public static final int ACCTTYPE_TaxCredit = ITaxAcctBL.ACCTTYPE_TaxCredit;
	/** Tax Receivables */
	public static final int ACCTTYPE_TaxReceivables = ITaxAcctBL.ACCTTYPE_TaxReceivables;
	/** Tax Expense */
	public static final int ACCTTYPE_TaxExpense = ITaxAcctBL.ACCTTYPE_TaxExpense;

	private final Properties getCtx()
	{
		return m_ctx;
	}

	/**
	 * Get Account
	 * 
	 * @param AcctType see ACCTTYPE_*
	 * @param as account schema
	 * @return Account
	 */
	public MAccount getAccount(final int AcctType, final I_C_AcctSchema as)
	{
		return taxAcctBL.getAccount(getCtx(), getC_Tax_ID(), as, AcctType);
	}   // getAccount

	public MAccount getAccount(final I_C_AcctSchema as)
	{
		return taxAcctBL.getAccount(getCtx(), getC_Tax_ID(), as, getAPTaxType());
	}

	public MAccount getTaxDueAcct(final I_C_AcctSchema as)
	{
		return taxAcctBL.getAccount(getCtx(), getC_Tax_ID(), as, ACCTTYPE_TaxDue);
	}

	/**
	 * @return tax amount
	 */
	public BigDecimal getTaxAmt()
	{
		return m_taxAmt;
	}

	/**
	 * Get Base Amount
	 * 
	 * @return net amount
	 */
	public BigDecimal getTaxBaseAmt()
	{
		return m_taxBaseAmt;
	}

	/**
	 * Get Rate
	 * 
	 * @return tax rate in percent (0 to 100)
	 */
	public BigDecimal getRate()
	{
		return m_taxRate;
	}

	/**
	 * Get Name of Tax
	 * 
	 * @return name
	 */
	public String getTaxName()
	{
		return m_taxName;
	}

	/**
	 * Get C_Tax_ID
	 *
	 * @return tax id
	 */
	public int getC_Tax_ID()
	{
		return m_C_Tax_ID;
	}	// getC_Tax_ID

	/**
	 * Get Description (Tax Name and Base Amount)
	 * 
	 * @return tax anme and base amount
	 */
	public String getDescription()
	{
		return getTaxName() + " " + getTaxBaseAmt();
	}   // getDescription

	/**
	 * Add to Included Tax
	 *
	 * @param amt amount
	 */
	public void addIncludedTax(final BigDecimal amt)
	{
		m_includedTax = m_includedTax.add(amt);
	}	// addIncludedTax

	/**
	 * Get Included Tax Difference
	 *
	 * @return tax ampunt - included amount
	 */
	public BigDecimal getIncludedTaxDifference()
	{
		return m_taxAmt.subtract(m_includedTax);
	}	// getIncludedTaxDifference

	/**
	 * Included Tax differs from tax amount
	 *
	 * @return true if difference
	 */
	public boolean isIncludedTaxDifference()
	{
		return BigDecimal.ZERO.compareTo(getIncludedTaxDifference()) != 0;
	}	// isIncludedTaxDifference

	/**
	 * Get AP Tax Type
	 *
	 * @return AP tax type (Credit or Expense)
	 */
	public int getAPTaxType()
	{
		if (isSalesTax())
			return ACCTTYPE_TaxExpense;
		return ACCTTYPE_TaxCredit;
	}	// getAPTaxAcctType

	/**
	 * Is Sales Tax
	 *
	 * @return sales tax
	 */
	public boolean isSalesTax()
	{
		return m_salesTax;
	}	// isSalesTax

	public boolean isTaxIncluded()
	{
		return m_taxIncluded;
	}

	/**
	 * Return String representation
	 * 
	 * @return tax anme and base amount
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("Tax=(");
		sb.append(m_taxName);
		sb.append(" TaxAmt=").append(m_taxAmt);
		sb.append(")");
		return sb.toString();
	}	// toString

}	// DocTax
