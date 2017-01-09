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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

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
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.util.TraceUtils;

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

	/**
	 * States that there can be <code>n</code> TUs be put into one LU.
	 */
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

		helper.createHU_PI_Item_PackingMaterial(piTU, helper.pmIFCO);

		piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		piLU_Item_TU = helper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("5"));

		helper.createHU_PI_Item_PackingMaterial(piLU, helper.pmPalet);

	}

	@Test
	public void test_1LU_With_Tomatoes_and_Salads()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(piLU);
		lutuProducer.setLUItemPI(piLU_Item_TU);
		lutuProducer.setTUPI(piTU);

		// TU capacity
		lutuProducer.addTUCapacity(helper.pTomato, new BigDecimal("40"), helper.uomKg);
		lutuProducer.addTUCapacity(helper.pSalad, new BigDecimal("7"), helper.uomEach);

		{
			// load 10 salad into HUs
			load(lutuProducer, helper.pSalad, new BigDecimal("10"));

			final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
			TraceUtils.dump(createdHUs);

			final I_M_HU luHU = createdHUs.get(0);
			HUStorageExpectation.newExpectation()
					.product(helper.pSalad).uom(helper.uomEach)
					.qty(10)
					.assertExpected(luHU);
		}

		{
			// load 60kg tomatos into HUs
			load(lutuProducer, helper.pTomato, new BigDecimal("60"));

			final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
			TraceUtils.dump(createdHUs);

			final I_M_HU luHU = createdHUs.get(1);
			HUStorageExpectation.newExpectation()
					.product(helper.pTomato).uom(helper.uomKg)
					.qty(6 * 10)
					.assertExpected(luHU);
		}
	}

	/**
	 * Configure a {@link LUTUProducerDestination} to load 200kg of tomatoes. Further:
	 * <ul>
	 * <li>one LU can hold 5 IFCOs
	 * <li>one IFCO can hold 40 kg of tomatoes
	 * </ul>
	 * Verify that the loader produces one LU with 200 kg tomatoes and an aggregated HU that contains both an item-storage with the 200kg tomatoes and an packing material item with one IFCO.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/460
	 */
	@Test
	public void testCompressedSingleLUFullyLoaded()
	{
		performTest(200, 1, null);
	}

	/**
	 * Similar to {@link #testCompressedSingleLUFullyLoaded()}, but just loads 199kg tomatoes. Still, expect one LU and one "aggregated" TU.
	 */
	@Test
	public void testCompressedSingleLUPartiallyLoaded()
	{
		performTest(199, 0,
				husExpectation -> {
					//@formatter:off
					husExpectation.newHUExpectation()
						.huPI(piLU)
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
							.newIncludedHUExpectation()
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
									.qty("5") // 5 IFCOs. four full (40 kg tomatos) and one with the remaining 39kg
									.packingMaterial(helper.pmIFCO)
								.endExpectation()
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_Material)
									.newItemStorageExpectation()
										.product(helper.pTomato)
										.qty("199") // the 199 kg tomatos
										.uom(helper.uomKg)
									.endExpectation() // itemStorageExcpectation
								.endExpectation() // newHUItemExpectation;
							.endExpectation() // includedHUExpectation
						.endExpectation() // huItemExpectation - HUAggregate
						.newHUItemExpectation()
							.noIncludedHUs()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
							.qty("1")
							.packingMaterial(helper.pmPalet)
						.endExpectation()
					.endExpectation(); // huExpectation
					//@formatter:on
				});
	}

	/**
	 * Similar to {@link #testCompressedSingleLUFullyLoaded()}, but loads 6000kg of tomatoes. Therefore we expect not one LU, but 30 LU with each one holding 200kg tomatoes.
	 */
	@Test
	public void testCompressed30LUsFullyLoaded()
	{
		performTest(6000, 30, null);
	}

	/**
	 * Similar to {@link #testCompressed31LUsPartiallyLoaded()}, but loads 6050kg tomatoes. Therefore we expect the 30 LU from the other test, 
	 * plus a 31st LU that contains just 2 "aggregated" TUs (similar to {@link #testCompressedSingleLUPartiallyLoaded()}) with two aggregated IFCOs. 
	 */
	@Test
	public void testCompressed31LUsPartiallyLoaded()
	{
		performTest(6050, 30,
				husExpectation -> {
					//@formatter:off
					husExpectation
						.newHUExpectation()
						.huPI(piLU)
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
							.newIncludedHUExpectation()
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
									.qty("2") // 2 more IFCOs. one full (40 kg tomatos) and one with the remaining 10kg
									.packingMaterial(helper.pmIFCO)
								.endExpectation()
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_Material)
									.newItemStorageExpectation()
										.product(helper.pTomato)
										.qty("50") // the ramining 50 kg tomatos
										.uom(helper.uomKg)
									.endExpectation() // itemStorageExcpectation
								.endExpectation() // newHUItemExpectation;
							.endExpectation() // includedHUExpectation
						.endExpectation() // huItemExpectation - HUAggregate
						.newHUItemExpectation()
							.noIncludedHUs()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
							.qty("1")
							.packingMaterial(helper.pmPalet)
						.endExpectation()
					.endExpectation(); // huExpectation
					//@formatter:on
				});
	}

	public void performTest(final int cuQty, final int expectedFullLUs, final Consumer<HUsExpectation> lastExpectation)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(piLU);
		lutuProducer.setLUItemPI(piLU_Item_TU);
		lutuProducer.setTUPI(piTU);

		// TU capacity
		lutuProducer.addTUCapacity(helper.pTomato, new BigDecimal("40"), helper.uomKg);

		// load 6 tons of tomatoes into HUs
		load(lutuProducer, helper.pTomato, new BigDecimal(cuQty));

		final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();

		new de.metas.handlingunits.util.HUTracerInstance().dump(createdHUs.get(0));

		// example: for 6000 CUs:
		// there shall be 30 compressed HU
		// * each one with with 6000kg / 30 = 200kg tomatos
		// * each one with 5 TUs
		// * each one consisting of 1 palete

		final HUsExpectation husExpectation = new HUsExpectation();
		for (int i = 1; i <= expectedFullLUs; i++)
		{
			//@formatter:off
			husExpectation
				.newHUExpectation()
					.huPI(piLU)
					.newHUItemExpectation()
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
						.newIncludedHUExpectation()
							.newHUItemExpectation()
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
								.qty("5") // 5 ifcos per palet
								.packingMaterial(helper.pmIFCO)
							.endExpectation()
							.newHUItemExpectation()
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_Material)
								.newItemStorageExpectation()
									.product(helper.pTomato)
									.qty("200")
									.uom(helper.uomKg)
								.endExpectation() // itemStorageExcpectation
							.endExpectation() // newHUItemExpectation;
						.endExpectation() // includedHUExpectation
					.endExpectation() // huItemExpectation - HUAggregate
					.newHUItemExpectation()
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.qty("1")
						.packingMaterial(helper.pmPalet)
					.endExpectation()
				.endExpectation() // huExpectation
			;
			//@formatter:on
		}
		if (lastExpectation != null)
		{
			lastExpectation.accept(husExpectation);
		}

		husExpectation.assertExpected(createdHUs);
	}

	private final void load(final LUTUProducerDestination lutuProducer, final I_M_Product cuProduct, final BigDecimal qty)
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
				SystemTime.asTimestamp());

		huLoader.load(request);
	}
}
