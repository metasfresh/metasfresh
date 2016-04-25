package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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



public class MRPQtyToSupplyTest
{
//	@Before
//	public void init()
//	{
//		AdempiereTestHelper.get().init();
//	}
//
//	@Test
//	public void test_StandardCase()
//	{
//		final BigDecimal qtyGrossReq = new BigDecimal("30");
//		final BigDecimal qtyOnHand = new BigDecimal("10");
//
//		final MRPQtyToSupply qtys = new MRPQtyToSupply(qtyGrossReq);
//		qtys.addQtyOnHandReserved(qtyOnHand);
//
//		//
//		// Add some supplies and validate
//		qtys.addQtySupplied(new BigDecimal("3"), true); // supply 3 items, firm supply
//		qtys.addQtySupplied(new BigDecimal("4"), false); // supply 4 items, NOT firm supply
//		Assert.assertThat("Invalid qty to supply for " + qtys, qtys.getQtyGrossReq(), Matchers.comparesEqualTo(new BigDecimal("30")));
//		Assert.assertThat("Invalid qty supplied for " + qtys, qtys.getQtySupplied(), Matchers.comparesEqualTo(new BigDecimal("7")));
//		Assert.assertThat("Invalid qty to supply remaining for " + qtys, qtys.getQtyNetReqRemaining(), Matchers.comparesEqualTo(new BigDecimal("13")));
//		Assert.assertThat("Invalid qty scheduled receipts for " + qtys, qtys.getQtyScheduledReceipts(), Matchers.comparesEqualTo(new BigDecimal("3")));
//		Assert.assertEquals("Invalid IsFullfilled for " + qtys, false, qtys.isFullfilled());
//
//		//
//		// Try to over-supply
//		{
//			final BigDecimal qtySuppliedActual = qtys.addQtySupplied(new BigDecimal("50"), true);
//			Assert.assertThat("Invalid qty supplied actual for " + qtys, qtySuppliedActual, Matchers.comparesEqualTo(new BigDecimal("13")));
//		}
//		Assert.assertThat("Invalid qty to supply for " + qtys, qtys.getQtyGrossReq(), Matchers.comparesEqualTo(new BigDecimal("30")));
//		Assert.assertThat("Invalid qty supplied for " + qtys, qtys.getQtySupplied(), Matchers.comparesEqualTo(new BigDecimal("20")));
//		Assert.assertThat("Invalid qty to supply remaining for " + qtys, qtys.getQtyNetReqRemaining(), Matchers.comparesEqualTo(new BigDecimal("0")));
//		Assert.assertThat("Invalid qty scheduled receipts for " + qtys, qtys.getQtyScheduledReceipts(), Matchers.comparesEqualTo(new BigDecimal("16")));
//		Assert.assertEquals("Invalid IsFullfilled for " + qtys, true, qtys.isFullfilled());
//	}
}
