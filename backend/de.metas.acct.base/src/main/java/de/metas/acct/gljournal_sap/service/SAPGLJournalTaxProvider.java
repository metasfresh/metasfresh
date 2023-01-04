package de.metas.acct.gljournal_sap.service;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.tax.ITaxAcctBL;
import de.metas.acct.tax.TaxAcctType;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SAPGLJournalTaxProvider
{
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final ITaxAcctBL taxAcctBL = Services.get(ITaxAcctBL.class);
	private final MoneyService moneyService;

	public SAPGLJournalTaxProvider(
			@NonNull final MoneyService moneyService)
	{
		this.moneyService = moneyService;
	}

	public AccountId getTaxAccountId(
			@NonNull final TaxId taxId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final PostingSign postingSign)
	{
		final TaxAcctType taxAcctType = postingSign.isDebit()
				? TaxAcctType.TaxCredit
				: TaxAcctType.TaxDue;

		return taxAcctBL.getAccountId(taxId, acctSchemaId, taxAcctType)
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
}
