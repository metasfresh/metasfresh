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

import org.compiere.model.MAccount;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.tax.ITaxAcctBL;
import de.metas.acct.tax.TaxAcctType;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;

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

	public DocTax(
			@NonNull final TaxId taxId,
			final String taxName,
			final BigDecimal taxRate,
			final BigDecimal taxBaseAmt,
			final BigDecimal taxAmt,
			final boolean salesTax,
			final boolean taxIncluded)
	{
		this.taxId = taxId;
		m_taxName = taxName;
		m_taxRate = taxRate;
		m_taxBaseAmt = taxBaseAmt;
		m_taxAmt = taxAmt;
		m_salesTax = salesTax;
		this.m_taxIncluded = taxIncluded;
	}	// DocTax

	private final TaxId taxId;
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

	public MAccount getAccount(final AcctSchema as)
	{
		return taxAcctBL.getAccount(getTaxId(), as.getId(), getAPTaxType());
	}

	public MAccount getTaxDueAcct(final AcctSchema as)
	{
		return taxAcctBL.getAccount(getTaxId(), as.getId(), TaxAcctType.TaxDue);
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

	public TaxId getTaxId()
	{
		return taxId;
	}

	public int getC_Tax_ID()
	{
		return getTaxId().getRepoId();
	}

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
	 * @return AP tax type (Credit or Expense)
	 */
	private TaxAcctType getAPTaxType()
	{
		return isSalesTax() ? TaxAcctType.TaxExpense : TaxAcctType.TaxCredit;
	}

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
