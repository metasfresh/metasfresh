package de.metas.acct.callout;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.acct.Account;
import de.metas.acct.accounts.TaxAccountsRepository;
import de.metas.acct.accounts.TaxAcctType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.tax.ITaxAccountable;
import de.metas.currency.CurrencyPrecision;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccount;

import java.math.BigDecimal;

/**
 * Callout for {@link ITaxAccountable} records
 *
 * @author tsa
 * @implSpec task <a href="http://dewiki908/mediawiki/index.php/08351_Automatikibuchung_Steuer_in_Hauptbuchjournal_%28106598648165%29">...</a>
 */
/* package */class TaxAccountableCallout
{
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final TaxAccountsRepository taxAccountsRepository = SpringContextHolder.instance.getBean(TaxAccountsRepository.class);

	// NOTE: no status fields are allowed because it's assume this is stateless

	/**
	 * Called when Tax Base Account is set.
	 */
	public void onTaxBaseAccount(final ITaxAccountable taxAccountable)
	{
		final BigDecimal taxTotalAmt = taxAccountable.getTaxTotalAmt();

		final I_C_Tax tax = getTaxOrNull(taxAccountable.getTaxBase_Acct());
		taxAccountable.setC_Tax(tax);
		taxAccountable.setTaxTotalAmt(taxTotalAmt);
	}

	/**
	 * Called when TaxBaseAmt is changed.
	 * <p>
	 * Sets TaxAmt and TaxTotalAmt.
	 */
	public void onTaxBaseAmt(final ITaxAccountable taxAccountable)
	{
		final Tax tax = taxDAO.getTaxById(taxAccountable.getC_Tax_ID());
		if (tax == null)
		{
			return;
		}
		if (tax.isReverseCharge())
		{
			throw new AdempiereException("Reverse Charge Tax is not supported");
		}

		//
		// Calculate Tax Amt
		final BigDecimal taxBaseAmt = taxAccountable.getTaxBaseAmt();
		final boolean taxIncluded = false;
		final CurrencyPrecision precision = taxAccountable.getPrecision();
		final BigDecimal taxAmt = tax.calculateTax(taxBaseAmt, taxIncluded, precision.toInt()).getTaxAmount();

		final BigDecimal totalAmt = taxBaseAmt.add(taxAmt);

		taxAccountable.setTaxAmt(taxAmt);
		taxAccountable.setTaxTotalAmt(totalAmt);
	}

	/**
	 * Called when TaxAmt is changed.
	 * <p>
	 * Sets TaxTotalAmt.
	 */
	public void onTaxAmt(final ITaxAccountable taxAccountable)
	{
		final BigDecimal taxBaseAmt = taxAccountable.getTaxBaseAmt();
		final BigDecimal taxAmt = taxAccountable.getTaxAmt();
		final BigDecimal totalAmt = taxBaseAmt.add(taxAmt);
		taxAccountable.setTaxTotalAmt(totalAmt);
	}

	/**
	 * Called when TaxTotalAmt is changed.
	 * <p>
	 * Sets TaxAmt and TaxBaseAmt.
	 */
	public void onTaxTotalAmt(final ITaxAccountable taxAccountable)
	{
		final Tax tax = taxDAO.getTaxById(taxAccountable.getC_Tax_ID());
		if (tax == null)
		{
			return;
		}
		if (tax.isReverseCharge())
		{
			throw new AdempiereException("Reverse Charge Tax is not supported");
		}

		//
		// Calculate TaxAmt
		final BigDecimal taxTotalAmt = taxAccountable.getTaxTotalAmt();
		final boolean taxIncluded = true;
		final CurrencyPrecision precision = taxAccountable.getPrecision();
		final BigDecimal taxAmt = tax.calculateTax(taxTotalAmt, taxIncluded, precision.toInt()).getTaxAmount();

		final BigDecimal taxBaseAmt = taxTotalAmt.subtract(taxAmt);

		taxAccountable.setTaxAmt(taxAmt);
		taxAccountable.setTaxBaseAmt(taxBaseAmt);
	}

	/**
	 * Called when C_Tax_ID is changed.
	 * <p>
	 * Sets Tax_Acct, TaxAmt.
	 */
	public void onC_Tax_ID(final ITaxAccountable taxAccountable)
	{
		final TaxAcctType taxAcctType;
		if (taxAccountable.isAccountSignDR())
		{
			taxAcctType = TaxAcctType.TaxCredit;
			// taxAcctType = TaxAcctType.TaxExpense; // used for booking services tax
		}
		else if (taxAccountable.isAccountSignCR())
		{
			taxAcctType = TaxAcctType.TaxDue;
		}
		else
		{
			return;
		}

		//
		// Set DR/CR Tax Account
		final TaxId taxId = TaxId.ofRepoIdOrNull(taxAccountable.getC_Tax_ID());
		if (taxId != null)
		{
			final AcctSchemaId acctSchemaId = taxAccountable.getAcctSchemaId();
			final MAccount taxAccount = taxAccountsRepository.getAccounts(taxId, acctSchemaId)
					.getAccount(taxAcctType)
					.map(Account::getAccountId)
					.map(accountDAO::getById)
					.orElseThrow(() -> new AdempiereException("@NotFound@ " + taxAcctType + " (" + taxId + ", " + acctSchemaId + ")"));
			taxAccountable.setTax_Acct(taxAccount);
		}
		else
		{
			taxAccountable.setTax_Acct(null);
		}

		//
		// Set TaxAmt based on TaxBaseAmt and C_Tax_ID
		onTaxBaseAmt(taxAccountable);
	}

	private I_C_Tax getTaxOrNull(final I_C_ValidCombination accountVC)
	{
		if (accountVC == null)
		{
			return null;
		}

		final I_C_ElementValue account = accountVC.getAccount();
		if (account == null)
		{
			return null;
		}

		if (!account.isAutoTaxAccount())
		{
			return null;
		}

		final int taxId = account.getC_Tax_ID();
		if (taxId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.load(taxId, I_C_Tax.class);
	}

}
