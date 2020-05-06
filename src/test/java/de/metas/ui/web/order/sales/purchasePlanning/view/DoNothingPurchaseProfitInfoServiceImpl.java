package de.metas.ui.web.order.sales.purchasePlanning.view;

import de.metas.money.CurrencyId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoRequest;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoService;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DoNothingPurchaseProfitInfoServiceImpl implements PurchaseProfitInfoService
{

	@Override
	public PurchaseProfitInfo calculate(final PurchaseProfitInfoRequest request)
	{
		return null;
	}

	@Override
	public PurchaseProfitInfo calculateNoFail(final PurchaseProfitInfoRequest request)
	{
		return null;
	}

	@Override
	public PurchaseProfitInfo convertToCurrency(final PurchaseProfitInfo profitInfo, final CurrencyId currencyIdTo)
	{
		throw new UnsupportedOperationException("profitInfo=" + profitInfo + ", currencyIdTo=" + currencyIdTo);
	}

}
