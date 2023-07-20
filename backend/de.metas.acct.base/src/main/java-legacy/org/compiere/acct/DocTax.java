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

import de.metas.acct.Account;
import de.metas.acct.accounts.AccountProvider;
import de.metas.acct.accounts.TaxAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

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

	@Getter private final TaxId taxId;
	@Getter private final BigDecimal taxAmt;
	private final String taxName;
	private final BigDecimal taxBaseAmt;
	private BigDecimal includedTaxAmt = BigDecimal.ZERO;
	private final boolean salesTax;
	@Getter private final boolean taxIncluded;
	@Getter private final boolean isReverseCharge;
	@Getter private final BigDecimal reverseChargeTaxAmt;

	@Builder
	private DocTax(
			@NonNull final AccountProvider accountProvider,
			@NonNull final TaxId taxId,
			final String taxName,
			@NonNull final BigDecimal taxBaseAmt,
			@NonNull final BigDecimal taxAmt,
			final boolean salesTax,
			final boolean taxIncluded,
			final boolean isReverseCharge,
			@NonNull final BigDecimal reverseChargeTaxAmt)
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

	/**
	 * Included Tax differs from tax amount
	 *
	 * @return true if difference
	 */
	public boolean isIncludedTaxDifference() {return getIncludedTaxDifference().signum() != 0;}
}

