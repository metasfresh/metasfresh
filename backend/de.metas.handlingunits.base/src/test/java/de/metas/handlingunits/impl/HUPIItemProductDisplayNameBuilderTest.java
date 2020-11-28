package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.util.Check;

/**
 * Test {@link HUPIItemProductDisplayNameBuilder}
 *
 * @author tsa
 *
 */
public class HUPIItemProductDisplayNameBuilderTest
{
	private PlainContextAware context;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final Properties ctx = Env.getCtx();
		context = new PlainContextAware(ctx, ITrx.TRXNAME_None);
	}

	@Test
	public void test_FiniteCapacityItemProduct_NoQtys()
	{
		final HUPIItemProductDisplayNameBuilder builder = new HUPIItemProductDisplayNameBuilder();
		final I_M_HU_PI_Item_Product piItemProduct = createM_HU_PI_Item_Product("IFCO", "10", false, "Kg");

		final String displayNameActual = builder
				.setM_HU_PI_Item_Product(piItemProduct)
				.build();

		final String displayNameExpected = "IFCO x 10 Kg";
		Assert.assertEquals(displayNameExpected, displayNameActual);
	}

	@Test
	public void test_FiniteCapacityItemProduct_QtyPlanned_NoQtyMoved()
	{
		final HUPIItemProductDisplayNameBuilder builder = new HUPIItemProductDisplayNameBuilder();
		final I_M_HU_PI_Item_Product piItemProduct = createM_HU_PI_Item_Product("IFCO", "10", false, "Kg");

		final String displayNameActual = builder
				.setM_HU_PI_Item_Product(piItemProduct)
				.setQtyTUPlanned(new BigDecimal("33"))
				.build();

		final String displayNameExpected = "33 x [IFCO x 10 Kg]";
		Assert.assertEquals(displayNameExpected, displayNameActual);
	}

	@Test
	public void test_FiniteCapacityItemProduct_QtyPlanned_QtyMoved()
	{
		final HUPIItemProductDisplayNameBuilder builder = new HUPIItemProductDisplayNameBuilder();
		final I_M_HU_PI_Item_Product piItemProduct = createM_HU_PI_Item_Product("IFCO", "10", false, "Kg");

		final String displayNameActual = builder
				.setM_HU_PI_Item_Product(piItemProduct)
				.setQtyTUPlanned(new BigDecimal("33"))
				.setQtyTUMoved(new BigDecimal("22"))
				.build();

		final String displayNameExpected = "22 / 33 x [IFCO x 10 Kg]";
		Assert.assertEquals(displayNameExpected, displayNameActual);
	}

	@Test
	public void test_FiniteCapacityItemProduct_NoQtyPlanned_QtyMoved()
	{
		final HUPIItemProductDisplayNameBuilder builder = new HUPIItemProductDisplayNameBuilder();
		final I_M_HU_PI_Item_Product piItemProduct = createM_HU_PI_Item_Product("IFCO", "10", false, "Kg");

		final String displayNameActual = builder
				.setM_HU_PI_Item_Product(piItemProduct)
				.setQtyTUMoved(new BigDecimal("22"))
				.build();

		final String displayNameExpected = "22 / ? x [IFCO x 10 Kg]";
		Assert.assertEquals(displayNameExpected, displayNameActual);
	}

	@Test
	public void test_InfineCapacityItemProduct_NoQtys()
	{
		final HUPIItemProductDisplayNameBuilder builder = new HUPIItemProductDisplayNameBuilder();
		final I_M_HU_PI_Item_Product piItemProduct = createM_HU_PI_Item_Product("IFCO", "10", true, "Kg");

		final String displayNameActual = builder
				.setM_HU_PI_Item_Product(piItemProduct)
				.build();

		final String displayNameExpected = "IFCO";
		Assert.assertEquals(displayNameExpected, displayNameActual);
	}

	private I_M_HU_PI_Item_Product createM_HU_PI_Item_Product(final String huPIName, final String qtyStr, final boolean isInfiniteCapacity, final String uomSymbol)
	{
		final BigDecimal qty = Check.isEmpty(qtyStr, true) ? null : new BigDecimal(qtyStr);

		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class, context);
		uom.setUOMSymbol(uomSymbol);
		InterfaceWrapperHelper.save(uom);

		final I_M_HU_PI pi = InterfaceWrapperHelper.newInstance(I_M_HU_PI.class, context);
		pi.setName(huPIName);
		InterfaceWrapperHelper.save(pi);

		final I_M_HU_PI_Version piVersion = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class, context);
		piVersion.setM_HU_PI(pi);
		InterfaceWrapperHelper.save(piVersion);

		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class, context);
		piItem.setM_HU_PI_Version(piVersion);
		InterfaceWrapperHelper.save(piItem);

		final I_M_HU_PI_Item_Product piItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class, context);
		piItemProduct.setM_HU_PI_Item(piItem);
		piItemProduct.setIsInfiniteCapacity(isInfiniteCapacity);
		piItemProduct.setQty(qty);
		piItemProduct.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.save(piItemProduct);

		return piItemProduct;
	}
}
