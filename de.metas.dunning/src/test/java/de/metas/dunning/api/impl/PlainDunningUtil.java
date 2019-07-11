package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.junit.Ignore;

import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;

@Ignore
public class PlainDunningUtil extends DunningUtil
{
	private final PlainCurrencyDAO currencyDAO;

	public PlainDunningUtil()
	{
		currencyDAO = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);
	}

	public void setRate(final CurrencyId currencyFromId, final CurrencyId currencyToId, final BigDecimal rate)
	{
		currencyDAO.setRate(currencyFromId, currencyToId, rate);
	}

}
