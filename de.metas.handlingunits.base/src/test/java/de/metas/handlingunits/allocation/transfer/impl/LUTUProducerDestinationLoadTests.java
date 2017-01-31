package de.metas.handlingunits.allocation.transfer.impl;

import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;

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

	/**
	 * Uses the lutuProcuder to create a top-level TU that is only partially filled
	 */
	@Test
	public void partiallyLoadedTU()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

		lutuProducer.setLUPI(null);
		lutuProducer.setLUItemPI(null);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setMaxLUs(0);
		lutuProducer.setCreateTUsForRemainingQty(true);

		// one IFCO can hold 40kg tomatoes
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("35"), data.helper.uomKg);

		final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
		assertThat(createdHUs.size(), is(1));
		final Node createdHuXMLs = HUXmlConverter.toXml(createdHUs.get(0));
		//System.out.println(HUXmlConverter.toString(createdHuXMLs));
		
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
	}
	
	/**
	 * Tests the lutuProducer with a palet that contains a partially filled IFCO.
	 */
	@Test
	public void testLUWithPartiallyLoadedTU()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);
		
		// one IFCO can hold 40kg tomatoes
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("35"), data.helper.uomKg);

		final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
		assertThat(createdHUs.size(), is(1));
		final Node createdHuXML = HUXmlConverter.toXml(createdHUs.get(0));
		System.out.println(HUXmlConverter.toString(createdHuXML));
		
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));
		
		// the aggregate HU that is not really used in this case. It has no storage, and its PM item has a quantity of zero
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXML, not(hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		
		// the "real" partially filled IFCO
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
	}
	
	/**
	 * Configure a {@link LUTUProducerDestination} to load 200kg of tomatoes. Further:
	 * <ul>
	 * <li>one LU can hold 5 IFCOs
	 * <li>one IFCO can hold 40 kg of tomatoes
	 * </ul>
	 * Verify that the loader produces one LU with 200 kg tomatoes and an aggregated HU that contains both an item-storage with the 200kg tomatoes and one packing material item with packaging material "IFCO" and a quantity of 5.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/460
	 */
	@Test
	public void testCompressedSingleLUFullyLoaded()
	{
		final int cuQty = 200;
		final int expectedFullLUsCount = 1;
		performTest(cuQty, expectedFullLUsCount, null, null);
	}

	/**
	 * Similar to {@link #testCompressedSingleLUFullyLoaded()}, but just loads 199kg tomatoes. Still, expect one LU and one "aggregated" TU.
	 */
	@Test
	public void testCompressedSingleLUPartiallyLoaded()
	{
		final int cuQty = 199;
		final int expectedFullLUsCount = 0;

		final Consumer<HUsExpectation> lastExpectation = husExpectation -> {
			//@formatter:off
			husExpectation.newHUExpectation()
				.huPI(data.piLU)

				.newHUItemExpectation() // start of the "remaining" part
					.itemType(X_M_HU_Item.ITEMTYPE_HandlingUnit)
					.huPIItem(data.piLU_Item_IFCO) // the HU PI item that belongs to the palet-PI and does the linking to the IFCOs (sub-)PI
					
					.newIncludedHUExpectation() // the not-aggregated IFCO with 39kg
						.huPI(data.piTU_IFCO)
						.newHUItemExpectation() //
							.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.huPIItem(data.piTU_Item_IFCO) 
							.newIncludedVirtualHU()
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.qty("39").uom(data.helper.uomKg).product(data.helper.pTomato)
									.endExpectation()
								.endExpectation()
							.endExpectation()
						.endExpectation() // end of the VHU that actually contains the 39kg of tomato
						.newHUItemExpectation()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
							.packingMaterial(data.helper.pmIFCO)
							// .qty("") we don't care; "qty" doesn't play a role with PM-items
						.endExpectation() // end of the IFCO packing material item
					.endExpectation() // end of IFCO-HU
				.endExpectation() // end of the "remaining" part
				
				.newHUItemExpectation() // start of the "aggregate" part
					.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
					.huPIItem(data.piLU_Item_IFCO)
					.qty("4") // 4 IFCOs each one full (40 kg tomatos)
					.newIncludedHUExpectation()
						.newHUItemExpectation()
							.noIncludedHUs()
							.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.newItemStorageExpectation()
								// the 160 kg tomatoes that completely fill their respective IFCOS
								.qty("160").uom(data.helper.uomKg).product(data.helper.pTomato)
							.endExpectation() // itemStorageExcpectation
						.endExpectation() // newHUItemExpectation;
						.newHUItemExpectation()
							.noIncludedHUs()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
							// .qty("") we don't care; "qty" doesn't play a role with PM-items
							.packingMaterial(data.helper.pmIFCO)
						.endExpectation()
					.endExpectation() // includedHUExpectation
				.endExpectation() // end of the "aggregate" part
				
				.newHUItemExpectation()
					.noIncludedHUs()
					.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
					// .qty("") we don't care; "qty" doesn't play a role with PM-items
					.packingMaterial(data.helper.pmPalet)
				.endExpectation()
			.endExpectation(); // huExpectation
			//@formatter:on
		};

		performTest(cuQty, expectedFullLUsCount, lastExpectation, null);
	}

	/**
	 * Similar to {@link #testCompressedSingleLUFullyLoaded()}, but loads 6000kg of tomatoes. Therefore we expect not one LU, but 30 LU with each one holding 200kg tomatoes.
	 */
	@Test
	public void testCompressed30LUsFullyLoadedExplicitTUCapacity()
	{
		performTest(6000, 30, null, new BigDecimal("40"));
	}

	@Test
	public void testCompressed30LUsFullyLoaded_UseTUCapacityFromPI()
	{
		performTest(6000, 30, null, null);
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

						.newHUItemExpectation() // start of the "remaining" part that was created for the partially filled IFCOs
							.itemType(X_M_HU_Item.ITEMTYPE_HandlingUnit)
							.huPIItem(data.piLU_Item_IFCO) // the HU PI item that belongs to the palet-PI and does the linking to the IFCOs (sub-)PI
							
							.newIncludedHUExpectation() // the not-aggregated IFCO with 10kg
								.huPI(data.piTU_IFCO)
								.newHUItemExpectation() //
									.itemType(X_M_HU_Item.ITEMTYPE_Material)
									.huPIItem(data.piTU_Item_IFCO) 
									.newIncludedVirtualHU()
										.newVirtualHUItemExpectation()
											.newItemStorageExpectation()
												.qty("10").uom(data.helper.uomKg).product(data.helper.pTomato)
											.endExpectation()
										.endExpectation()
									.endExpectation()
								.endExpectation() // end of the VHU that actually contains the 39kg of tomato
								.newHUItemExpectation()
									.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
									.packingMaterial(data.helper.pmIFCO)
									// .qty("") we don't care; "qty" doesn't play a role with PM-items
								.endExpectation() // end of the IFCO packing material item
							.endExpectation() // end of IFCO-HU
						.endExpectation() // end of the "remaining" part
						
						.newHUItemExpectation() // start of the "aggregate" part
							.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
							.huPIItem(data.piLU_Item_IFCO)
							.qty("1") // 1 more full (40 kg tomatos) IFCO
							.newIncludedHUExpectation()
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_Material)
									.newItemStorageExpectation()
										.product(data.helper.pTomato)
										.qty("40") // the 40 kg tomatos that went into a full aggregated IFCO-VHU
										.uom(data.helper.uomKg)
									.endExpectation() // itemStorageExcpectation
								.endExpectation() // newHUItemExpectation;
								.newHUItemExpectation()
									.noIncludedHUs()
									.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
									// .qty("") we don't care; "qty" doesn't play a role with PM-items
									.packingMaterial(data.helper.pmIFCO)
								.endExpectation()								
							.endExpectation() // includedHUExpectation
						.endExpectation() // end of the "aggregate" part
						
						.newHUItemExpectation()
							.noIncludedHUs()
							.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
							// .qty("") we don't care; "qty" doesn't play a role with PM-items
							.packingMaterial(data.helper.pmPalet)
						.endExpectation()
					.endExpectation(); // huExpectation
					//@formatter:on
				}, null);
	}

	/**
	 * 
	 * @param cuQty the overall qty of tomatoes per fully loaded LU.
	 * @param expectedFullLUsCount expected number of LUs that are fully loaded with 5 IFCOs (aggregated) and 200kg of tomatoes
	 * @param lastExpectation
	 * @param tuCapacityOverride optional, may be {@code null}.
	 *            If set, then call {@link LUTUProducerDestination#addTUCapacity(org.compiere.model.I_M_Product, BigDecimal, org.compiere.model.I_C_UOM)} to explicitly set a capacity.
	 *            If not set, then expect the LUTUProducerDestination to get the capacity from the PI.
	 */
	private void performTest(final int cuQty,
			final int expectedFullLUsCount,
			final Consumer<HUsExpectation> lastExpectation,
			final BigDecimal tuCapacityOverride)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);

		// TU capacity
		if (tuCapacityOverride != null)
		{
			lutuProducer.addTUCapacity(data.helper.pTomato, tuCapacityOverride, data.helper.uomKg);
		}

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
		for (int i = 1; i <= expectedFullLUsCount; i++)
		{
			//@formatter:off
			husExpectation
				.newHUExpectation()
					.huPI(data.piLU)
					.newHUItemExpectation()
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
						.huPIItem(data.piLU_Item_IFCO) // this aggregate represents IFCOs
						.qty("5") // 5 IFCOs per palet
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
								.packingMaterial(data.helper.pmIFCO)
								// .qty("") we don't care; "qty" doesn'T play a role with PM-items
							.endExpectation()
						.endExpectation() // includedHUExpectation
					.endExpectation() // huItemExpectation - HUAggregate
					.newHUItemExpectation()
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						// .qty("") we don't care; "qty" doesn'T play a role with PM-items 
						.packingMaterial(data.helper.pmPalet)
					.endExpectation()
				.endExpectation() // huExpectation
			;
			//@formatter:on
		}

		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml("result", createdHUs)));

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
			//System.out.println("" + HUXmlConverter.toString(huPaletsXML));

			// We're asserting that only ONE truck was created, and that it has all 23 products.
			// The truck's product item has the allowed qty limited to 6, BUT it has IsInfiniteCapacity checked
			// As such, the entire qty will be allocated to this particular item
			assertThat(huPaletsXML, hasXPath("count(/Truck/HU-Truck)", is("1")));
			assertThat(huPaletsXML, hasXPath("/Truck/HU-Truck[1]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg' and @Qty='999999.000']"));

			HUAssert.assertAllStoragesAreValid();
		}

		// TraceUtils.dump(huPalets);
		// TraceUtils.dumpTransactions();
	}
}
