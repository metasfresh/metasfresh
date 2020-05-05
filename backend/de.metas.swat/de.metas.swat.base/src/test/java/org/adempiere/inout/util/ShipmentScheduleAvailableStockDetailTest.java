package org.adempiere.inout.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.inout.util.ChristmasPackTestData.ChristmasPackTestDataBuilder;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/*
 * #%L
 * de.metas.swat.base
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

public class ShipmentScheduleAvailableStockDetailTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Nested
	public class ChristmasPack_5chocolates_3socks
	{
		private ChristmasPackTestDataBuilder newTestData()
		{
			return ChristmasPackTestData.builder()
					.chocolate_qtyPerPack(5)
					.socks_qtyPerPack(3);
		}

		@Nested
		public class getQtyOnHand
		{
			@Test
			public void for_11chocolates_4socks()
			{
				final ShipmentScheduleAvailableStockDetail christmasPack = newTestData()
						.chocolate_qtyOnHand(11)
						.socks_qtyOnHand(4)
						.build()
						.getChristmasPackStockDetail();
				assertThat(christmasPack.getQtyAvailable()).isEqualByComparingTo("1");
			}

			@Test
			public void for_11chocolates_6socks()
			{
				final ShipmentScheduleAvailableStockDetail christmasPack = newTestData()
						.chocolate_qtyOnHand(11)
						.socks_qtyOnHand(6)
						.build()
						.getChristmasPackStockDetail();
				assertThat(christmasPack.getQtyAvailable()).isEqualByComparingTo("2");
			}

			@Test
			public void for_11chocolates_6socks_100packs()
			{
				final ShipmentScheduleAvailableStockDetail christmasPack = newTestData()
						.chocolate_qtyOnHand(11)
						.socks_qtyOnHand(6)
						.christmasPack_qtyOnHand(100)
						.build()
						.getChristmasPackStockDetail();
				assertThat(christmasPack.getQtyAvailable()).isEqualByComparingTo("102");
			}
		}

		@Nested
		public class subtractQtyOnHand
		{
			@Test
			public void for_12chocolates_4socks_subtract_1pack()
			{
				final ChristmasPackTestData testData = newTestData()
						.chocolate_qtyOnHand(12)
						.socks_qtyOnHand(4)
						.build();

				testData.getChristmasPackStockDetail().subtractQtyOnHand(new BigDecimal("1"));

				assertThat(testData.getChristmasPackStockDetail().getQtyAvailable()).isEqualByComparingTo("0");
				assertThat(testData.getChocolateStockDetail().getQtyAvailable()).isEqualByComparingTo("7");
				assertThat(testData.getSocksStockDetail().getQtyAvailable()).isEqualByComparingTo("1");
			}

			@Test
			public void for_12chocolates_4socks_1pack_subtract_2packs()
			{
				final ChristmasPackTestData testData = newTestData()
						.chocolate_qtyOnHand(12)
						.socks_qtyOnHand(4)
						.christmasPack_qtyOnHand(1)
						.build();

				testData.getChristmasPackStockDetail().subtractQtyOnHand(new BigDecimal("2"));

				assertThat(testData.getChristmasPackStockDetail().getQtyAvailable()).isEqualByComparingTo("0");
				assertThat(testData.getChocolateStockDetail().getQtyAvailable()).isEqualByComparingTo("7");
				assertThat(testData.getSocksStockDetail().getQtyAvailable()).isEqualByComparingTo("1");
			}

			@Test
			public void for_12chocolates_4socks_1pack_subtract_3packs()
			{
				final ChristmasPackTestData testData = newTestData()
						.chocolate_qtyOnHand(12)
						.socks_qtyOnHand(4)
						.christmasPack_qtyOnHand(1)
						.build();

				testData.getChristmasPackStockDetail().subtractQtyOnHand(new BigDecimal("3"));

				assertThat(testData.getChristmasPackStockDetail().getQtyAvailable()).isEqualByComparingTo("-1");
				assertThat(testData.getChocolateStockDetail().getQtyAvailable()).isEqualByComparingTo("7");
				assertThat(testData.getSocksStockDetail().getQtyAvailable()).isEqualByComparingTo("1");
			}

			@Test
			public void for_100pack_subtract_2packs()
			{
				final ChristmasPackTestData testData = newTestData()
						.chocolate_qtyOnHand(0)
						.socks_qtyOnHand(0)
						.christmasPack_qtyOnHand(100)
						.build();

				testData.getChristmasPackStockDetail().subtractQtyOnHand(new BigDecimal("2"));

				assertThat(testData.getChristmasPackStockDetail().getQtyAvailable()).isEqualByComparingTo("98");
				assertThat(testData.getChocolateStockDetail().getQtyAvailable()).isEqualByComparingTo("0");
				assertThat(testData.getSocksStockDetail().getQtyAvailable()).isEqualByComparingTo("0");
			}
		}
	}
}
