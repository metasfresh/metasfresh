/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.order;

import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link IOrderBL#syncCurrencyFromPriceList(I_C_Order)}, which is the method
 * extracted from {@code MOrder.beforeSave()} to contain the currency-sync logic.
 *
 * <p>The key contract: when an order has a price list, the currency is always taken
 * from the price list — even if the order already has a non-zero currency set.
 * This prevents the pre-fix bug where an order pre-set with USD would not be corrected
 * to EUR when a EUR price list was attached.
 */
@ExtendWith(AdempiereTestWatcher.class)
class MOrderCurrencySyncTest
{
	private IOrderBL orderBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		orderBL = Services.get(IOrderBL.class);
	}

	@Test
	void beforeSave_alwaysSyncsCurrencyFromPriceList_evenWhenAlreadySet()
	{
		// given
		final CurrencyId usdId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.USD);
		final CurrencyId eurId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// Create a price list with EUR currency (stored in the POJO lookup map)
		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setC_Currency_ID(eurId.getRepoId());
		priceList.setIsSOPriceList(true);
		saveRecord(priceList);

		// Create an order with USD already set, and the EUR price list attached
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setM_PriceList_ID(priceList.getM_PriceList_ID());
		// Pre-set USD — this is the key: currency is already non-zero before sync runs
		order.setC_Currency_ID(usdId.getRepoId());

		// when — syncCurrencyFromPriceList mirrors the logic extracted from MOrder.beforeSave()
		orderBL.syncCurrencyFromPriceList(order);

		// then — currency must be EUR (from price list), not USD (the pre-set value)
		assertThat(order.getC_Currency_ID())
				.as("C_Currency_ID must be synced from price list (EUR), not left as pre-set USD")
				.isEqualTo(eurId.getRepoId());
	}
}
