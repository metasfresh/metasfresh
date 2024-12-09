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

<<<<<<< HEAD
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
=======
import de.metas.acct.Account;
import de.metas.acct.accounts.AccountProvider;
import de.metas.acct.accounts.TaxAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

final class DocTax
{
	// services
	private final AccountProvider accountProvider;

	@NonNull @Getter private final TaxId taxId;
	@NonNull private final String taxName;

	@NonNull @Setter private BigDecimal taxBaseAmt;
	@NonNull @Getter @Setter private BigDecimal taxAmt;
	@NonNull @Getter @Setter private BigDecimal reverseChargeTaxAmt;
	@NonNull private BigDecimal includedTaxAmt = BigDecimal.ZERO;

	private final boolean salesTax;
	@Getter private final boolean taxIncluded;
	@Getter private final boolean isReverseCharge;

	@Builder
	private DocTax(
			@NonNull final AccountProvider accountProvider,
			@NonNull final TaxId taxId,
			@NonNull final String taxName,
			@NonNull final BigDecimal taxBaseAmt,
			@NonNull final BigDecimal taxAmt,
			@NonNull final BigDecimal reverseChargeTaxAmt,
			final boolean salesTax,
			final boolean taxIncluded,
			final boolean isReverseCharge)
	{
		this.accountProvider = accountProvider;
		this.taxId = taxId;
		this.taxName = taxName;
		this.taxBaseAmt = taxBaseAmt;
		this.taxAmt = taxAmt;
		this.salesTax = salesTax;
		this.taxIncluded = taxIncluded;
		this.isReverseCharge = isReverseCharge;
		this.reverseChargeTaxAmt = reverseChargeTaxAmt;
	}

	public static DocTaxBuilder builderFrom(@NonNull final Tax tax)
	{
		return DocTax.builder()
				.taxId(tax.getTaxId())
				.taxName(tax.getName())
				.salesTax(tax.isSalesTax())
				.isReverseCharge(tax.isReverseCharge());
	}

	@Override
	public String toString()
	{
		return "Tax=(" + taxName + " TaxAmt=" + taxAmt + ")";
	}

	@NonNull
	public Account getTaxCreditOrExpense(@NonNull final AcctSchema as)
	{
		return accountProvider.getTaxAccount(as.getId(), taxId, salesTax ? TaxAcctType.TaxExpense : TaxAcctType.TaxCredit);
	}

	@NonNull
	public Account getTaxDueAcct(@NonNull final AcctSchema as)
	{
		return accountProvider.getTaxAccount(as.getId(), taxId, TaxAcctType.TaxDue);
	}

	public int getC_Tax_ID() {return taxId.getRepoId();}

	public String getDescription() {return taxName + " " + taxBaseAmt;}

	public void addIncludedTax(final BigDecimal amt)
	{
		includedTaxAmt = includedTaxAmt.add(amt);
	}

	/**
	 * @return tax amount - included tax amount
	 */
	public BigDecimal getIncludedTaxDifference()
	{
		return taxAmt.subtract(includedTaxAmt);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Included Tax differs from tax amount
	 *
	 * @return true if difference
	 */
<<<<<<< HEAD
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
=======
	public boolean isIncludedTaxDifference() {return getIncludedTaxDifference().signum() != 0;}
}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
