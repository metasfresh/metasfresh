package de.metas.ui.web.order.sales.purchasePlanning.view;

import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class PurchaseRowTestTools
{
	public Currency createCurrency()
	{
		return Currency
				.builder()
				.id(CurrencyId.ofRepoId(30))
				.precision(2)
				.build();
	}

	public PurchaseProfitInfo createProfitInfo(@NonNull final Currency currency)
	{
		return PurchaseProfitInfo.builder()
				.salesNetPrice(Money.of(11, currency))
				.purchaseNetPrice(Money.of(9, currency))
				.purchaseGrossPrice(Money.of(10, currency))
				.build();
	}
}
