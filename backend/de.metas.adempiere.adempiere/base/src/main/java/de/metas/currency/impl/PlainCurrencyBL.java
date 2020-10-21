package de.metas.currency.impl;

import org.adempiere.service.ClientId;
import org.compiere.Adempiere;

import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;

public class PlainCurrencyBL extends CurrencyBL
{
	private CurrencyCode defaultCurrencyISOCode = CurrencyCode.CHF;

	PlainCurrencyBL()
	{
		Adempiere.assertUnitTestMode();
	}

	@Override
	public CurrencyId getBaseCurrencyId(final @NonNull ClientId adClientId, final @NonNull OrgId adOrgId)
	{
		Adempiere.assertUnitTestMode();

		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		return currenciesRepo.getByCurrencyCode(defaultCurrencyISOCode).getId();
	}

	public void setDefaultCurrencyISOCode(@NonNull final CurrencyCode defaultCurrencyISOCode)
	{
		Adempiere.assertUnitTestMode();

		this.defaultCurrencyISOCode = defaultCurrencyISOCode;
	}
}
