package de.metas.acct.gljournal_sap.service;

import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SAPGLJournalCurrencyConverter
{
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	public Money convertToAcctCurrency(
			@NonNull final Money amount,
			@NonNull final SAPGLJournalCurrencyConversionCtx ctx)
	{
		if (!CurrencyId.equals(amount.getCurrencyId(), ctx.getCurrencyId()))
		{
			throw new AdempiereException("Amount `" + amount + "` has unexpected currency. Expected was the context currency: " + ctx);
		}

		return convertToAcctCurrency(amount.toBigDecimal(), ctx);
	}

	public Money convertToAcctCurrency(
			@NonNull final BigDecimal amountBD,
			@NonNull final SAPGLJournalCurrencyConversionCtx ctx)
	{
		final CurrencyConversionResult result = currencyBL.convert(toCurrencyConversionContext(ctx), amountBD, ctx.getCurrencyId(), ctx.getAcctCurrencyId());
		return result.getAmountAsMoney();
	}

	public CurrencyConversionContext toCurrencyConversionContext(@NonNull final SAPGLJournalCurrencyConversionCtx ctx)
	{
		CurrencyConversionContext currencyConversionContext = currencyBL.createCurrencyConversionContext(
				ctx.getDate(),
				ctx.getConversionTypeId(),
				ctx.getClientAndOrgId().getClientId(),
				ctx.getClientAndOrgId().getOrgId());

		if (ctx.getFixedConversionRate() != null)
		{
			currencyConversionContext = currencyConversionContext.withFixedConversionRate(ctx.getFixedConversionRate());
		}

		return currencyConversionContext;
	}

}
