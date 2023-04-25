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

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.gljournal.IGLJournalLineBL;
import de.metas.acct.tax.ITaxAccountable;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.X_GL_JournalLine;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Callout(value = I_GL_JournalLine.class, recursionAvoidanceLevel = Callout.RecursionAvoidanceLevel.CalloutMethod)
public class GL_JournalLine
{
	private static final String MSG_AutoTaxAccountDrCrNotAllowed = "AutoTaxAccountDrCrNotAllowed";
	private static final String MSG_AutoTaxNotAllowedOnPartialTrx = "AutoTaxNotAllowedOnPartialTrx";

	private static final boolean ACCTSIGN_Debit = true;
	private static final boolean ACCTSIGN_Credit = false;

	private final TaxAccountableCallout taxAccountableCallout = new TaxAccountableCallout();

	@CalloutMethod(columnNames = {
			I_GL_JournalLine.COLUMNNAME_DateAcct,
			I_GL_JournalLine.COLUMNNAME_C_Currency_ID,
			I_GL_JournalLine.COLUMNNAME_C_ConversionType_ID })
	public void updateCurrencyRate(final I_GL_JournalLine glJournalLine)
	{
		//
		// Extract data from source Journal
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(glJournalLine.getC_Currency_ID());
		if (currencyId == null)
		{
			// not set yet
			return;
		}

		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(glJournalLine.getC_ConversionType_ID());
		LocalDate dateAcct = TimeUtil.asLocalDate(glJournalLine.getDateAcct());
		if (dateAcct == null)
		{
			dateAcct = SystemTime.asLocalDate();
		}
		final ClientId adClientId = ClientId.ofRepoId(glJournalLine.getAD_Client_ID());
		final OrgId adOrgId = OrgId.ofRepoId(glJournalLine.getAD_Org_ID());
		final I_GL_Journal glJournal = glJournalLine.getGL_Journal();
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(glJournal.getC_AcctSchema_ID());
		final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);

		//
		// Calculate currency rate
		final BigDecimal currencyRate = Services.get(ICurrencyBL.class).getCurrencyRateIfExists(
				currencyId,
				acctSchema.getCurrencyId(),
				dateAcct,
				conversionTypeId,
				adClientId,
				adOrgId)
				.map(CurrencyRate::getConversionRate)
				.orElse(BigDecimal.ZERO);
		glJournalLine.setCurrencyRate(currencyRate);
	}

	@CalloutMethod(columnNames = { I_GL_JournalLine.COLUMNNAME_AmtSourceDr, I_GL_JournalLine.COLUMNNAME_AmtSourceCr, I_GL_JournalLine.COLUMNNAME_CurrencyRate })
	public void updateAmtAcctDrAndCr(final I_GL_JournalLine journalLine)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(journalLine.getGL_Journal().getC_AcctSchema_ID());
		final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
		final CurrencyPrecision precision = acctSchema.getStandardPrecision();

		final BigDecimal currencyRate = journalLine.getCurrencyRate();
		final BigDecimal parentCurrencyRate = journalLine.getGL_Journal().getCurrencyRate();
		if(currencyRate.signum() == 0 && parentCurrencyRate.signum() != 0)
		{
			journalLine.setCurrencyRate(parentCurrencyRate);
		}

		// AmtAcct = AmtSource * CurrencyRate ==> Precision
		final BigDecimal amtSourceDr = journalLine.getAmtSourceDr();
		final BigDecimal amtAcctDr = amtSourceDr.multiply(journalLine.getCurrencyRate()).setScale(precision.toInt(), RoundingMode.HALF_UP);
		journalLine.setAmtAcctDr(amtAcctDr);

		final BigDecimal amtSourceCr = journalLine.getAmtSourceCr();
		final BigDecimal amtAcctCr = amtSourceCr.multiply(journalLine.getCurrencyRate()).setScale(precision.toInt(), RoundingMode.HALF_UP);
		journalLine.setAmtAcctCr(amtAcctCr);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_IsSplitAcctTrx)
	public void onIsSplitAcctTrx(final I_GL_JournalLine glJournalLine)
	{
		//
		// After user changed the IsSplitAcctTrx make sure the new state is compatible with the other settings
		try
		{
			Services.get(IGLJournalLineBL.class).checkValidSplitAcctTrxFlag(glJournalLine);
		}
		catch (final Exception e)
		{
			// put the flag back
			final I_GL_JournalLine glJournalLineOld = InterfaceWrapperHelper.createOld(glJournalLine, I_GL_JournalLine.class);
			glJournalLine.setIsSplitAcctTrx(glJournalLineOld.isSplitAcctTrx());

			throw AdempiereException.wrapIfNeeded(e);
		}

		//
		// Sync AmtSourceDr <-> AmtSourceCr based on which one is not zero
		final String fromAmtSourceColumnName = null; // i.e. auto-detect
		syncSourceAmountsIfSplitAcctTrx(glJournalLine, fromAmtSourceColumnName);
	}

	/**
	 * Copy AmtSourceDr/Cr to AmtSourceCr/Dr based on which is the source column.
	 * 
	 * If the given GL Journal Line has "Split accounting transaction" enabled this method will do nothing
	 * because in that case the amounts does not have to be synchronized.
	 * 
	 * @param glJournalLine
	 * @param fromAmtSourceColumnName source column from where we shall copy the amount. It can be:
	 *            <ul>
	 *            <li>{@link I_GL_JournalLine#COLUMNNAME_AmtSourceDr} to copy from AmtSourceDr to AmtSourceCr
	 *            <li>{@link I_GL_JournalLine#COLUMNNAME_AmtSourceCr} to copy from AmtSourceCr to AmtSourceDr
	 *            <li><code>null</code> - it will copy from AmtSourceDr if it's not zero else from AmtSourceCr
	 *            </ul>
	 */
	private final void syncSourceAmountsIfSplitAcctTrx(final I_GL_JournalLine glJournalLine, String fromAmtSourceColumnName)
	{
		// Do nothing if split accounting transaction is enabled
		if (glJournalLine.isSplitAcctTrx())
		{
			return;
		}

		// Autodetect the "From AmtSource column name" (i.e. the one which is not ZERO)
		if (fromAmtSourceColumnName == null)
		{
			if (glJournalLine.getAmtSourceDr().signum() != 0)
			{
				fromAmtSourceColumnName = I_GL_JournalLine.COLUMNNAME_AmtSourceDr;
			}
			else
			{
				fromAmtSourceColumnName = I_GL_JournalLine.COLUMNNAME_AmtSourceCr;
			}
		}

		//
		// Copy amount from source column to destination column
		if (I_GL_JournalLine.COLUMNNAME_AmtSourceDr.equals(fromAmtSourceColumnName))
		{
			glJournalLine.setAmtSourceCr(glJournalLine.getAmtSourceDr());
		}
		else if (I_GL_JournalLine.COLUMNNAME_AmtSourceCr.equals(fromAmtSourceColumnName))
		{
			glJournalLine.setAmtSourceDr(glJournalLine.getAmtSourceCr());
		}
		else
		{
			throw new AdempiereException("AmtSource column name not supported: " + fromAmtSourceColumnName); // shall not happen
		}
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_Account_DR_ID)
	public void onAccount_DR_ID(final I_GL_JournalLine glJournalLine)
	{
		onTaxBaseAccount(glJournalLine);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_Account_CR_ID)
	public void onAccount_CR_ID(final I_GL_JournalLine glJournalLine)
	{
		onTaxBaseAccount(glJournalLine);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_AmtSourceDr)
	public void onAmtSourceDr(final I_GL_JournalLine glJournalLine)
	{
		// Copy AmtSourceDr -> AmtSourceCr, if split accounting transaction
		syncSourceAmountsIfSplitAcctTrx(glJournalLine, I_GL_JournalLine.COLUMNNAME_AmtSourceDr);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_AmtSourceCr)
	public void onAmtSourceCr(final I_GL_JournalLine glJournalLine)
	{
		// Copy AmtSourceDr -> AmtSourceCr, if split accounting transaction
		syncSourceAmountsIfSplitAcctTrx(glJournalLine, I_GL_JournalLine.COLUMNNAME_AmtSourceCr);
	}

	private final void onTaxBaseAccount(final I_GL_JournalLine glJournalLine)
	{
		//
		// Take a snapshot of the old values
		final boolean autoTaxOld = isAutoTax(glJournalLine);
		final BigDecimal amtSourceDr = glJournalLine.getAmtSourceDr();
		final BigDecimal amtSourceCr = glJournalLine.getAmtSourceCr();

		//
		// Update Type and AutoTax flags
		// => could throw exception in case it's not valid
		setAutoTaxType(glJournalLine);

		final boolean autoTaxNew = isAutoTax(glJournalLine);
		final boolean autoTaxJustEnabled = autoTaxNew && !autoTaxOld;

		//
		// Get the autoTax record
		final ITaxAccountable taxAccountable;
		final BigDecimal amtSource;
		if (glJournalLine.isDR_AutoTaxAccount())
		{
			taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Debit);
			amtSource = amtSourceDr;
		}
		else if (glJournalLine.isCR_AutoTaxAccount())
		{
			taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Credit);
			amtSource = amtSourceCr;
		}
		else
		{
			return;
		}

		//
		// Fire tax base account changed
		taxAccountableCallout.onTaxBaseAccount(taxAccountable);

		//
		// If we just switched to tax accounting, set the TaxTotalAmt as current source amount
		// This is the reverse-operation of setting a TaxTotalAmt which sets AmtSourceDr/Cr.
		if (autoTaxJustEnabled)
		{
			taxAccountable.setTaxTotalAmt(amtSource);
		}
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_DR_Tax_ID)
	public void onDR_Tax_ID(final I_GL_JournalLine glJournalLine)
	{
		final ITaxAccountable taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Debit);
		taxAccountableCallout.onC_Tax_ID(taxAccountable);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_CR_Tax_ID)
	public void onCR_Tax_ID(final I_GL_JournalLine glJournalLine)
	{
		final ITaxAccountable taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Credit);
		taxAccountableCallout.onC_Tax_ID(taxAccountable);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_DR_TaxBaseAmt)
	public void onDR_TaxBaseAmt(final I_GL_JournalLine glJournalLine)
	{
		final ITaxAccountable taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Debit);
		taxAccountableCallout.onTaxBaseAmt(taxAccountable);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_CR_TaxBaseAmt)
	public void onCR_TaxBaseAmt(final I_GL_JournalLine glJournalLine)
	{
		final ITaxAccountable taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Credit);
		taxAccountableCallout.onTaxBaseAmt(taxAccountable);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_DR_TaxAmt)
	public void onDR_TaxAmt(final I_GL_JournalLine glJournalLine)
	{
		final ITaxAccountable taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Debit);
		taxAccountableCallout.onTaxAmt(taxAccountable);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_CR_TaxAmt)
	public void onCR_TaxAmt(final I_GL_JournalLine glJournalLine)
	{
		final ITaxAccountable taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Credit);
		taxAccountableCallout.onTaxAmt(taxAccountable);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_DR_TaxTotalAmt)
	public void onDR_TaxTotalAmt(final I_GL_JournalLine glJournalLine)
	{
		final ITaxAccountable taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Debit);
		taxAccountableCallout.onTaxTotalAmt(taxAccountable);
	}

	@CalloutMethod(columnNames = I_GL_JournalLine.COLUMNNAME_CR_TaxTotalAmt)
	public void onCR_TaxTotalAmt(final I_GL_JournalLine glJournalLine)
	{
		final ITaxAccountable taxAccountable = asTaxAccountable(glJournalLine, ACCTSIGN_Credit);
		taxAccountableCallout.onTaxTotalAmt(taxAccountable);
	}

	private void setAutoTaxType(final I_GL_JournalLine glJournalLine)
	{
		final boolean drAutoTax = isAutoTaxAccount(glJournalLine.getAccount_DR());
		final boolean crAutoTax = isAutoTaxAccount(glJournalLine.getAccount_CR());

		//
		// We can activate tax accounting only for one side
		if (drAutoTax && crAutoTax)
		{
			throw new AdempiereException("@" + MSG_AutoTaxAccountDrCrNotAllowed + "@");
		}
		final boolean taxRecord = drAutoTax || crAutoTax;

		//
		// Tax accounting can be allowed only if in a new transaction
		final boolean isPartialTrx = !glJournalLine.isAllowAccountDR() || !glJournalLine.isAllowAccountCR();
		if (taxRecord && isPartialTrx)
		{
			throw new AdempiereException("@" + MSG_AutoTaxNotAllowedOnPartialTrx + "@");
		}

		//
		// Set AutoTaxaAcount flags
		glJournalLine.setDR_AutoTaxAccount(drAutoTax);
		glJournalLine.setCR_AutoTaxAccount(crAutoTax);

		//
		// Update journal line type
		final String type = taxRecord ? X_GL_JournalLine.TYPE_Tax : X_GL_JournalLine.TYPE_Normal;
		glJournalLine.setType(type);
	}

	private final ITaxAccountable asTaxAccountable(final I_GL_JournalLine glJournalLine, final boolean accountSignDR)
	{
		final IGLJournalLineBL glJournalLineBL = Services.get(IGLJournalLineBL.class);
		return glJournalLineBL.asTaxAccountable(glJournalLine, accountSignDR);
	}

	private final boolean isAutoTaxAccount(final I_C_ValidCombination accountVC)
	{
		if (accountVC == null)
		{
			return false;
		}

		final I_C_ElementValue account = accountVC.getAccount();
		if (account == null)
		{
			return false;
		}

		return account.isAutoTaxAccount();
	}

	private boolean isAutoTax(final I_GL_JournalLine glJournalLine)
	{
		return glJournalLine.isDR_AutoTaxAccount() || glJournalLine.isCR_AutoTaxAccount();
	}
}
