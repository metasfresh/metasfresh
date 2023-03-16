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

import de.metas.acct.accounts.AccountProvider;
import de.metas.acct.accounts.TaxAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.tax.api.TaxId;
import lombok.NonNull;
import de.metas.acct.Account;

import java.math.BigDecimal;

/**
 * Document Tax Line
 *
 * @author Jorg Janke
 * @version $Id: DocTax.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public final class DocTax
{
	// services
	private final AccountProvider accountProvider;

	public DocTax(
			@NonNull final AccountProvider accountProvider,
			@NonNull final TaxId taxId,
			final String taxName,
			final BigDecimal taxRate,
			final BigDecimal taxBaseAmt,
			final BigDecimal taxAmt,
			final boolean salesTax,
			final boolean taxIncluded)
	{
		this.accountProvider = accountProvider;
		this.taxId = taxId;
		m_taxName = taxName;
		m_taxRate = taxRate;
		m_taxBaseAmt = taxBaseAmt;
		m_taxAmt = taxAmt;
		m_salesTax = salesTax;
		this.m_taxIncluded = taxIncluded;
	}    // DocTax

	private final TaxId taxId;
	/**
	 * Amount
	 */
	private final BigDecimal m_taxAmt;
	/**
	 * Tax Rate
	 */
	private final BigDecimal m_taxRate;
	/**
	 * Name
	 */
	private final String m_taxName;
	/**
	 * Base Tax Amt
	 */
	private final BigDecimal m_taxBaseAmt;
	/**
	 * Included Tax
	 */
	private BigDecimal m_includedTax = BigDecimal.ZERO;
	/**
	 * Sales Tax
	 */
	private final boolean m_salesTax;
	private final boolean m_taxIncluded;

	@NonNull
	public Account getAccount(@NonNull final AcctSchema as)
	{
		return accountProvider.getTaxAccount(as.getId(), getTaxId(), getAPTaxType());
	}

	@NonNull
	public Account getTaxDueAcct(@NonNull final AcctSchema as)
	{
		return accountProvider.getTaxAccount(as.getId(), getTaxId(), TaxAcctType.TaxDue);
	}

	public BigDecimal getTaxAmt()
	{
		return m_taxAmt;
	}

	public BigDecimal getTaxBaseAmt()
	{
		return m_taxBaseAmt;
	}

	/**
	 * @return tax rate in percent (0 to 100)
	 */
	public BigDecimal getRate()
	{
		return m_taxRate;
	}

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

	public String getDescription()
	{
		return getTaxName() + " " + getTaxBaseAmt();
	}

	public void addIncludedTax(final BigDecimal amt)
	{
		m_includedTax = m_includedTax.add(amt);
	}

	/**
	 * @return tax amount - included tax amount
	 */
	public BigDecimal getIncludedTaxDifference()
	{
		return m_taxAmt.subtract(m_includedTax);
	}

	/**
	 * Included Tax differs from tax amount
	 *
	 * @return true if difference
	 */
	public boolean isIncludedTaxDifference()
	{
		return BigDecimal.ZERO.compareTo(getIncludedTaxDifference()) != 0;
	}

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
	}

	public boolean isTaxIncluded()
	{
		return m_taxIncluded;
	}

	@Override
	public String toString()
	{
		return "Tax=(" + m_taxName + " TaxAmt=" + m_taxAmt + ")";
	}
}

