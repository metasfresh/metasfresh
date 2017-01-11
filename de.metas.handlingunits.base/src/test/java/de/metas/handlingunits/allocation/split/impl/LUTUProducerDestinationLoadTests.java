package de.metas.handlingunits.allocation.split.impl;

import static org.junit.Assert.assertThat;

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

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.util.TraceUtils;

/**
 * Note the "load" means "to create HUs and load qty into them from somehwere else". It's not about performance and stuff.
 */
public class LUTUProducerDestinationLoadTests
{
	private LUTUProducerDestinationTestSupport data;
	
	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
	}
	
	@Test
	public void test_1LU_With_Tomatoes_and_Salads()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);

		// TU capacity
		lutuProducer.addTUCapacity(data.helper.pTomato, new BigDecimal("40"), data.helper.uomKg);
		lutuProducer.addTUCapacity(data.helper.pSalad, new BigDecimal("7"), data.helper.uomEach);

		{
			// load 10 salad into HUs
			data.helper.load(lutuProducer, data.helper.pSalad, new BigDecimal("10"), data.helper.uomEach);

			final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
			TraceUtils.dump(createdHUs);

			final I_M_HU luHU = createdHUs.get(0);
			HUStorageExpectation.newExpectation()
					.product(data.helper.pSalad).uom(data.helper.uomEach)
					.qty(10)
					.assertExpected(luHU);
		}

		{
			// load 60kg tomatos into HUs
			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("60"), data.helper.uomEach);

			final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
			TraceUtils.dump(createdHUs);

			final I_M_HU luHU = createdHUs.get(1);
			HUStorageExpectation.newExpectation()
					.product(data.helper.pTomato).uom(data.helper.uomEach)
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
						.huPI(data.piLU)
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
							.newIncludedHUExpectation()
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_Material)
									.newItemStorageExpectation()
										.product(data.helper.pTomato)
										.qty("199") // the 199 kg tomatos
										.uom(data.helper.uomKg)
									.endExpectation() // itemStorageExcpectation
								.endExpectation() // newHUItemExpectation;
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
									.qty("5") // 5 IFCOs. four full (40 kg tomatos) and one with the remaining 39kg
									.packingMaterial(data.helper.pmIFCO)
								.endExpectation()
							.endExpectation() // includedHUExpectation
						.endExpectation() // huItemExpectation - HUAggregate
						.newHUItemExpectation()
							.noIncludedHUs()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
							.qty("1")
							.packingMaterial(data.helper.pmPalet)
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
						.huPI(data.piLU)
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
							.newIncludedHUExpectation()
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_Material)
									.newItemStorageExpectation()
										.product(data.helper.pTomato)
										.qty("50") // the ramining 50 kg tomatos
										.uom(data.helper.uomKg)
									.endExpectation() // itemStorageExcpectation
								.endExpectation() // newHUItemExpectation;
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
									.qty("2") // 2 more IFCOs. one full (40 kg tomatos) and one with the remaining 10kg
									.packingMaterial(data.helper.pmIFCO)
								.endExpectation()								
							.endExpectation() // includedHUExpectation
						.endExpectation() // huItemExpectation - HUAggregate
						.newHUItemExpectation()
							.noIncludedHUs()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
							.qty("1")
							.packingMaterial(data.helper.pmPalet)
						.endExpectation()
					.endExpectation(); // huExpectation
					//@formatter:on
				});
	}

	private void performTest(final int cuQty, final int expectedFullLUs, final Consumer<HUsExpectation> lastExpectation)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);

		// TU capacity
		lutuProducer.addTUCapacity(data.helper.pTomato, new BigDecimal("40"), data.helper.uomKg);

		// load the tomatoes into HUs
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal(cuQty), data.helper.uomKg);

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
					.huPI(data.piLU)
					.newHUItemExpectation()
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
						.newIncludedHUExpectation()
							.newHUItemExpectation()
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_Material)
								.newItemStorageExpectation()
									.product(data.helper.pTomato)
									.qty("200")
									.uom(data.helper.uomKg)
								.endExpectation() // itemStorageExcpectation
							.endExpectation() // newHUItemExpectation;
							.newHUItemExpectation()
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
								.qty("5") // 5 ifcos per palet
								.packingMaterial(data.helper.pmIFCO)
							.endExpectation()
						.endExpectation() // includedHUExpectation
					.endExpectation() // huItemExpectation - HUAggregate
					.newHUItemExpectation()
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.qty("1")
						.packingMaterial(data.helper.pmPalet)
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
		
		HUAssert.assertAllStoragesAreValid();
	}
	
	/**
	 * Test loading tomatoes into a TU with unlimited capacity.
	 */
	@Test
	public void testLoadIntoTUWithUnlimitedCapacity()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

		lutuProducer.setTUPI(data.piTruckUnlimitedCapacity); // it's important to note that this PI was set up with unlimited capacity
		lutuProducer.setLUPI(null);
		lutuProducer.setLUItemPI(null);
		lutuProducer.setMaxLUs(0);
		lutuProducer.setCreateTUsForRemainingQty(true);
			
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("999999"), data.helper.uomKg);
		final List<I_M_HU> huTruck = lutuProducer.getCreatedHUs();
		
		//
		// Validate HUs
		{
			final Node huPaletsXML = HUXmlConverter.toXml("Truck", huTruck);
			System.out.println("" + HUXmlConverter.toString(huPaletsXML));

			// We're asserting that only ONE truck was created, and that it has all 23 products.
			// The truck's product item has the allowed qty limited to 6, BUT it has IsInfiniteCapacity checked
			// As such, the entire qty will be allocated to this particular item
			assertThat(huPaletsXML, Matchers.hasXPath("count(/Truck/HU-Truck)", Matchers.equalTo("1")));
			assertThat(huPaletsXML, Matchers.hasXPath("/Truck/HU-Truck[1]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg' and @Qty='999999.000']"));

			HUAssert.assertAllStoragesAreValid();
		}

		// TraceUtils.dump(huPalets);
		//TraceUtils.dumpTransactions();
	}
}
