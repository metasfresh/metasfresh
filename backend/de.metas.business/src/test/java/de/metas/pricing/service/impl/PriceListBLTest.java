package de.metas.pricing.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_PriceList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.util.Services;
import lombok.Builder;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class PriceListBLTest
{
	private PriceListBL priceListBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		priceListBL = (PriceListBL)Services.get(IPriceListBL.class);
	}

	@Builder(builderMethodName = "preparePriceList", builderClassName = "PriceListBuilder")
	private PriceListId createPriceList(
			final int currencyPrecision,
			final int pricePrecision,
			final boolean roundNetAmountToCurrencyPrecision)
	{
		final CurrencyId currencyId = createCurrencyId("EUR", currencyPrecision);

		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setC_Currency_ID(currencyId.getRepoId());
		priceList.setIsRoundNetAmountToCurrencyPrecision(roundNetAmountToCurrencyPrecision);
		priceList.setPricePrecision(pricePrecision);
		saveRecord(priceList);
		return PriceListId.ofRepoId(priceList.getM_PriceList_ID());
	}

	private CurrencyId createCurrencyId(final String code, final int precision)
	{
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code(code);
		currency.setStdPrecision(precision);
		saveRecord(currency);
		return CurrencyId.ofRepoId(currency.getC_Currency_ID());
	}

	@Nested
	public class precisions
	{
		@Test
		public void pricePrecision_greaterThan_currencyPrecision_doNotRoundNetAmountToCurrencyPrecision()
		{
			final PriceListId priceListId = preparePriceList()
					.currencyPrecision(2)
					.pricePrecision(6)
					.roundNetAmountToCurrencyPrecision(false)
					.build();

			assertThat(priceListBL.getPricePrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(6));
			assertThat(priceListBL.getTaxPrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(2));

			// amount precision shall NEVER be above currency precision even if roundNetAmountToCurrencyPrecision=false
			assertThat(priceListBL.getAmountPrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(2));
		}

		@Test
		public void pricePrecision_greaterThan_currencyPrecision_roundNetAmountToCurrencyPrecision()
		{
			final PriceListId priceListId = preparePriceList()
					.currencyPrecision(2)
					.pricePrecision(6)
					.roundNetAmountToCurrencyPrecision(true)
					.build();

			assertThat(priceListBL.getPricePrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(6));
			assertThat(priceListBL.getTaxPrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(2));

			assertThat(priceListBL.getAmountPrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(2));
		}

		@Test
		public void pricePrecision_lessThan_currencyPrecision_doNotRoundNetAmountToCurrencyPrecision()
		{
			final PriceListId priceListId = preparePriceList()
					.currencyPrecision(2)
					.pricePrecision(1)
					.roundNetAmountToCurrencyPrecision(false)
					.build();

			assertThat(priceListBL.getPricePrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(1));
			assertThat(priceListBL.getTaxPrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(2));

			assertThat(priceListBL.getAmountPrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(1));
		}

		@Test
		public void pricePrecision_lessThan_currencyPrecision_roundNetAmountToCurrencyPrecision()
		{
			final PriceListId priceListId = preparePriceList()
					.currencyPrecision(2)
					.pricePrecision(1)
					.roundNetAmountToCurrencyPrecision(true)
					.build();

			assertThat(priceListBL.getPricePrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(1));
			assertThat(priceListBL.getTaxPrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(2));

			assertThat(priceListBL.getAmountPrecision(priceListId)).isEqualTo(CurrencyPrecision.ofInt(2));
		}
	}
}
