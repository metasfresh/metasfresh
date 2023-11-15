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

	/**
	 * Included Tax differs from tax amount
	 *
	 * @return true if difference
	 */
	public boolean isIncludedTaxDifference() {return getIncludedTaxDifference().signum() != 0;}
}

