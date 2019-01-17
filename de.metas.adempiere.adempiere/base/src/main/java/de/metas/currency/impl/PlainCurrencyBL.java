package de.metas.currency.impl;

import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;

import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;

public class PlainCurrencyBL extends CurrencyBL
{
	private String defaultCurrencyISOCode = "CHF";

	@Override
	public CurrencyId getBaseCurrencyId(final ClientId adClientId, final OrgId adOrgId)
	{
		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(Env.getCtx(), defaultCurrencyISOCode);
		return CurrencyId.ofRepoId(currency.getC_Currency_ID());
	}

	public void setDefaultCurrencyISOCode(String defaultCurrencyISOCode)
	{
		this.defaultCurrencyISOCode = defaultCurrencyISOCode;
	}
}
