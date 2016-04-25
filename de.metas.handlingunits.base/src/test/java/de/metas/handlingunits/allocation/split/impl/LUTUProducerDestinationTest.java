package de.metas.handlingunits.allocation.split.impl;

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
import java.util.List;

import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

public class LUTUProducerDestinationTest
{
	private HUTestHelper helper;

	private I_M_HU_PI piTU;
	private I_M_HU_PI_Item piTU_Item;
	@SuppressWarnings("unused")
	private I_M_HU_PI_Item_Product piTU_Item_Product_Tomato;
	@SuppressWarnings("unused")
	private I_M_HU_PI_Item_Product piTU_Item_Product_Salad;

	private I_M_HU_PI piLU;
	private I_M_HU_PI_Item piLU_Item_TU;

	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();

		piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		piTU_Item = helper.createHU_PI_Item_Material(piTU);
		piTU_Item_Product_Tomato = helper.assignProduct(piTU_Item, helper.pTomato, new BigDecimal("10"), helper.uomKg);
		piTU_Item_Product_Salad = helper.assignProduct(piTU_Item, helper.pSalad, new BigDecimal("7"), helper.uomEach);

		piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		piLU_Item_TU = helper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("5"));

	}

	@Test
	public void test_1TU_With_Tomatoes_and_Salads()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(piLU);
		lutuProducer.setLUItemPI(piLU_Item_TU);
		lutuProducer.setTUPI(piTU);
		// TU capacity
		lutuProducer.addTUCapacity(helper.pTomato, new BigDecimal("40"), helper.uomKg);
		lutuProducer.addTUCapacity(helper.pSalad, new BigDecimal("7"), helper.uomEach);

		{
			load(lutuProducer, helper.pTomato, new BigDecimal("60"));
			// TraceUtils.dump(lutuProducer.getCreatedHUs());
			final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
			final I_M_HU luHU = createdHUs.get(0);
			HUStorageExpectation.newExpectation()
					.product(helper.pTomato).uom(helper.uomKg)
					// TU 1
					.tuIndex(0)
					.qty(4 * 10)
					.assertExpected(luHU)
					// TU 2
					.tuIndex(1)
					.qty(2 * 10)
					.assertExpected(luHU);
		}

		{
			load(lutuProducer, helper.pSalad, new BigDecimal("10"));
			final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
			// TraceUtils.dump(createdHUs);

			final I_M_HU luHU = createdHUs.get(0);
			HUStorageExpectation.newExpectation()
					.product(helper.pSalad).uom(helper.uomEach)
					// TU 1
					.tuIndex(0)
					.qty(7)
					.assertExpected(luHU)
					// TU 2
					.tuIndex(1)
					.qty(3)
					.assertExpected(luHU);
		}

	}

	private final void load(final ILUTUProducerAllocationDestination lutuProducer, final I_M_Product cuProduct, final BigDecimal qty)
	{
		final IHUCapacityDefinition tuCapacity = lutuProducer.getTUCapacity(cuProduct);
		final I_C_UOM cuUOM = tuCapacity.getC_UOM();

		final IAllocationSource source = helper.createDummySourceDestination(cuProduct, IHUCapacityDefinition.INFINITY, helper.uomEach, true);

		final HULoader huLoader = new HULoader(source, lutuProducer);
		huLoader.setAllowPartialUnloads(false);
		huLoader.setAllowPartialLoads(false);

		final IMutableHUContext huContext0 = helper.createMutableHUContextOutOfTransaction();
		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext0,
				cuProduct, // product
				qty, // qty
				cuUOM, // uom
				SystemTime.asTimestamp()
				);

		huLoader.load(request);
	}
}
