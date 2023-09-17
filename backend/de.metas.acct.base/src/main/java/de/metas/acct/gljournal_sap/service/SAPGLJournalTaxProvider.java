package de.metas.acct.gljournal_sap.service;

import de.metas.acct.accounts.TaxAccountsRepository;
import de.metas.acct.accounts.TaxAcctType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SAPGLJournalTaxProvider
{
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final TaxAccountsRepository taxAccountsRepository;
	private final MoneyService moneyService;

	public SAPGLJournalTaxProvider(
			@NonNull final TaxAccountsRepository taxAccountsRepository,
			@NonNull final MoneyService moneyService)
	{
		this.taxAccountsRepository = taxAccountsRepository;
		this.moneyService = moneyService;
	}

	@NonNull
	public Account getTaxAccount(
			@NonNull final TaxId taxId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final PostingSign postingSign)
	{
		final TaxAcctType taxAcctType = postingSign.isDebit()
				? TaxAcctType.TaxCredit
				: TaxAcctType.TaxDue;

		return taxAccountsRepository.getAccounts(taxId, acctSchemaId)
				.getAccount(taxAcctType)
				.orElseThrow(() -> new AdempiereException("No account found for " + taxId + ", " + acctSchemaId + ", " + taxAcctType));
	}

	public Money calculateTaxAmt(
			@NonNull final Money baseAmt,
			@NonNull final TaxId taxId)
	{
		final CurrencyId currencyId = baseAmt.getCurrencyId();
		final CurrencyPrecision precision = moneyService.getStdPrecision(currencyId);
		final Tax tax = taxBL.getTaxById(taxId);
		final BigDecimal taxAmtBD = tax.calculateTax(baseAmt.toBigDecimal(), false, precision.toInt());

		return Money.of(taxAmtBD, currencyId);
	}

	public Money calculateTaxBaseAmt(
			@NonNull final Money grossAmt,
			@NonNull final TaxId taxId)
	{
		final CurrencyId currencyId = grossAmt.getCurrencyId();
		final CurrencyPrecision precision = moneyService.getStdPrecision(currencyId);
		final Tax tax = taxBL.getTaxById(taxId);
		final BigDecimal taxAmtBD = tax.calculateBaseAmt(grossAmt.toBigDecimal(), true, precision.toInt());

		return Money.of(taxAmtBD, currencyId);
	}

	public Money calculateTaxAmt(
			@NonNull final Money lineAmt,
			@NonNull final TaxId taxId,
			@NonNull final boolean isTaxIncluded)
	{
		return isTaxIncluded
				? calculateTaxAmt(calculateTaxBaseAmt(lineAmt, taxId), taxId) // lineAmt is a GrossAmt
				: calculateTaxAmt(lineAmt, taxId);

	}
}
