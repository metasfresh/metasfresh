package de.metas.handlingunits.allocation.transfer.impl;

import static de.metas.business.BusinessTestHelper.createBPartner;
import static de.metas.business.BusinessTestHelper.createBPartnerLocation;
import static de.metas.business.BusinessTestHelper.createLocator;
import static de.metas.business.BusinessTestHelper.createWarehouse;
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.w3c.dom.Node;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_LUTU_Configuration;

/**
 * Note the "load" means "to create HUs and load qty into them from somewhere else". It's not about performance and stuff.
 */
@RunWith(Theories.class)
public class LUTUProducerDestinationLoadTests
{
	/**
	 * This dataPoint shall enable us to test with both values of {@code isOwnPackingMaterials}.
	 */
	@DataPoints()
	public static boolean[] isOwnPackingMaterials = { true, false };

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
		// System.out.println(HUXmlConverter.toString(createdHuXMLs));

		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXMLs, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Tests the lutuProducer with a palet that contains a partially filled IFCO. Also tests whether {@code C_BPartner}, {@code C_BPartner_Location} and {@code M_Locator} end up in the loaded HUs.
	 * 
	 * @param isOwnPackingMaterials this value is passed to the {@link LUTUProducerDestination} under test and we expect it to show up in the created HUs.
	 */
	@Theory
	public void testLUWithPartiallyLoadedTU(final boolean isOwnPackingMaterials)
	{
		final I_C_BPartner bpartner = createBPartner("testVendor");
		final I_C_BPartner_Location bPartnerLocation = createBPartnerLocation(bpartner);

		final I_M_Warehouse warehouse = createWarehouse("testWarehouse");
		final I_M_Locator locator = createLocator("testLocator", warehouse);

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setIsHUPlanningReceiptOwnerPM(isOwnPackingMaterials);

		lutuProducer.setC_BPartner(bpartner);
		lutuProducer.setM_Locator(locator);
		lutuProducer.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());

		// one IFCO can hold 40kg tomatoes
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("35"), data.helper.uomKg);

		final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();
		assertThat(createdHUs.size(), is(1));
		final Node createdHuXML = HUXmlConverter.toXml(createdHUs.get(0));
		// System.out.println(HUXmlConverter.toString(createdHuXML));

		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/@C_BPartner_ID)", is(Integer.toString(bpartner.getC_BPartner_ID())))); // verify that the bpartner is propagated
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/@C_BPartner_Location_ID)", is(Integer.toString(bPartnerLocation.getC_BPartner_Location_ID())))); // verify that the bpartner location is propagated
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/@M_Locator_ID)", is(Integer.toString(locator.getM_Locator_ID())))); // verify that the locator is propagated

		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='Palet'])", is("1")));

		// the aggregate HU that is not really used in this case. It has no storage, and its PM item has a quantity of zero
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@C_BPartner_ID)", is(Integer.toString(bpartner.getC_BPartner_ID())))); // verify that the bpartner is propagated
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@C_BPartner_Location_ID)", is(Integer.toString(bPartnerLocation.getC_BPartner_Location_ID())))); // verify that the bpartner location is propagated
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_Locator_ID)", is(Integer.toString(locator.getM_Locator_ID())))); // verify that the locator is propagated

		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXML, not(hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI']/Storage")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));

		// the "real" partially filled IFCO
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@C_BPartner_ID)", is(Integer.toString(bpartner.getC_BPartner_ID())))); // verify that the bpartner is propagated
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@C_BPartner_Location_ID)", is(Integer.toString(bPartnerLocation.getC_BPartner_Location_ID())))); // verify that the bpartner location is propagated
		assertThat(createdHuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@M_Locator_ID)", is(Integer.toString(locator.getM_Locator_ID())))); // verify that the locator is propagated

		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(createdHuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Verifies that the value set to {@link LUTUProducerDestination#setIsHUPlanningReceiptOwnerPM(boolean)} makes it into top level TUs when they are created
	 * 
	 * @param isOwnPackingMaterials this value is passed to the {@link LUTUProducerDestination} under test and we expect it to show up in the created HUs.
	 */
	@Theory
	public void testHUPlanningReceiptOwnerPMWithTopLevelTU(final boolean isOwnPackingMaterials)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setIsHUPlanningReceiptOwnerPM(isOwnPackingMaterials);

		// load the tomatoes into HUs
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("20"), data.helper.uomKg);
		assertThat(lutuProducer.getCreatedLUsCount(), is(0));
		assertThat(lutuProducer.getCreatedHUsCount(), is(1));
		final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();

		// data.helper.commitAndDumpHU(createdHUs.get(0));
		final Node createdHuXML = HUXmlConverter.toXml(createdHUs.get(0));
		assertThat(createdHuXML, hasXPath("string(HU-TU_IFCO/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));

		// reach far down, to check if everything is as expected also in general
		assertThat(createdHuXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));

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
	public void testAggregateSingleLUFullyLoaded()
	{
		final int cuQty = 200;
		final int expectedFullLUsCount = 1;
		performTest(cuQty, expectedFullLUsCount, null, null);
	}

	/**
	 * Verifies the creation of an aggregate HU with a non-int storage value.
	 * Related to issue https://github.com/metasfresh/metasfresh/issues/1237, but this even worked before the issue came up.
	 */
	@Test
	public void testAggregateSingleLUFullyLoaded_non_int()
	{
		// create a special hu pi item that says "on LU can hold 20 IFCOs"
		final I_M_HU_PI_Item piLU_Item_20_IFCO = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("20"));

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(piLU_Item_20_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.addCUPerTU(data.helper.pTomato, new BigDecimal("5.47"), data.helper.uomKg); // set the TU capacity to be 109.4 / 20

		// load the tomatoes into HUs
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("109.4"), data.helper.uomKg);
		assertThat(lutuProducer.getCreatedHUs().size(), is(1));
		final I_M_HU createdLU = lutuProducer.getCreatedHUs().get(0);

		final Node createdLuXML = HUXmlConverter.toXml(createdLU);

		// there shall be no "real" HU
		assertThat(createdLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("0")));

		// the aggregate HU shall contain the full quantity and represent 20 IFCOs
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("109.400")));

		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)", is("20")));
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("109.400")));
	}

	/**
	 * Similar to {@link #testAggregateSingleLUFullyLoaded()}, but just loads 199kg tomatoes. Still, expect one LU and one "aggregated" TU.
	 */
	@Test
	public void testAggregateSingleLUPartiallyLoaded()
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
	 * Similar to {@link #testAggregateSingleLUFullyLoaded()}, but loads 6000kg of tomatoes. Therefore we expect not one LU, but 30 LU with each one holding 200kg tomatoes.
	 */
	@Test
	public void testAggregate30LUsFullyLoadedExplicitTUCapacity()
	{
		performTest(6000, 30, null, new BigDecimal("40"));
	}

	@Test
	public void testAggregate30LUsFullyLoaded_UseTUCapacityFromPI()
	{
		performTest(6000, 30, null, null);
	}

	/**
	 * Similar to {@link #testAggregate31LUsPartiallyLoaded()}, but loads 6050kg tomatoes. Therefore we expect the 30 LU from the other test,
	 * plus a 31st LU that contains just 2 "aggregated" TUs (similar to {@link #testAggregateSingleLUPartiallyLoaded()}) with two aggregated IFCOs.
	 */
	@Test
	public void testAggregate31LUsPartiallyLoaded()
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
	 *            If set, then call {@link LUTUProducerDestination#addCUPerTU(org.compiere.model.I_M_Product, BigDecimal, org.compiere.model.I_C_UOM)} to explicitly set a capacity.
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
			lutuProducer.addCUPerTU(data.helper.pTomato, tuCapacityOverride, data.helper.uomKg);
		}

		// load the tomatoes into HUs
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal(cuQty), data.helper.uomKg);

		final List<I_M_HU> createdHUs = lutuProducer.getCreatedHUs();

		new de.metas.handlingunits.util.HUTracerInstance().dump(createdHUs.get(0));

		// example: for 6000 CUs:
		// there shall be 30 Aggregate HU
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
			// System.out.println("" + HUXmlConverter.toString(huPaletsXML));

			// We're asserting that only ONE truck was created, and that it has all 23 products.
			// The truck's product item has the allowed qty limited to 6, BUT it has IsInfiniteCapacity checked
			// As such, the entire qty will be allocated to this particular item
			assertThat(huPaletsXML, hasXPath("count(/Truck/HU-Truck)", is("1")));
			assertThat(huPaletsXML, hasXPath("/Truck/HU-Truck[1]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg' and @Qty='999999.000']"));

			HUAssert.assertAllStoragesAreValid();
		}
	}

	/**
	 * Verifies that the loader will not try to create an aggregate HU for the case of a CU that shall be put right onto an LU.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1194
	 */
	@Test
	public void testLoadCUonLU()
	{
		final I_M_Warehouse wh = createWarehouse("testWarehouse");
		final I_M_Locator l = createLocator("testLocator", wh);

		final I_C_BPartner bpartner = createBPartner("testPartner");
		final I_C_BPartner_Location bpLocation = createBPartnerLocation(bpartner);

		final I_M_HU_PI_Item piLU_Item_Virtual = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.helper.huDefVirtual, BigDecimal.ONE);

		final I_M_HU_LUTU_Configuration lutuConfiguration = InterfaceWrapperHelper.newInstance(I_M_HU_LUTU_Configuration.class);
		lutuConfiguration.setM_LU_HU_PI(data.piLU);
		lutuConfiguration.setM_LU_HU_PI_Item(piLU_Item_Virtual);
		lutuConfiguration.setIsInfiniteQtyLU(false);
		lutuConfiguration.setQtyLU(BigDecimal.ONE);
		lutuConfiguration.setM_TU_HU_PI(data.helper.huDefVirtual);
		lutuConfiguration.setIsInfiniteQtyTU(false);
		lutuConfiguration.setQtyTU(BigDecimal.ONE);
		lutuConfiguration.setM_Product(data.helper.pSalad); // differs from real world
		lutuConfiguration.setC_UOM(data.helper.uomEach);
		lutuConfiguration.setIsInfiniteQtyCU(false);
		lutuConfiguration.setQtyCU(new BigDecimal("252"));
		lutuConfiguration.setHUStatus(X_M_HU_LUTU_Configuration.HUSTATUS_Planning);
		lutuConfiguration.setM_Locator(l);
		lutuConfiguration.setC_BPartner(bpartner);
		lutuConfiguration.setC_BPartner_Location(bpLocation);
		InterfaceWrapperHelper.save(lutuConfiguration);

		final ILUTUProducerAllocationDestination lutuProducer = Services.get(ILUTUConfigurationFactory.class).createLUTUProducerAllocationDestination(lutuConfiguration);

		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("252"), data.helper.uomEach);

		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();
		assertThat(createdLUs.size(), is(1));
		final I_M_HU createdLU = createdLUs.get(0);

		// data.helper.commitAndDumpHU(createdLU);

		final Node createdLuXML = HUXmlConverter.toXml(createdLU);

		// the aggregate HU that is not really used in this case. It has no storage, and its PM item has a quantity of zero
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@C_BPartner_ID)", is(Integer.toString(bpartner.getC_BPartner_ID())))); // verify that the bpartner is propagated
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@C_BPartner_Location_ID)", is(Integer.toString(bpLocation.getC_BPartner_Location_ID())))); // verify that the bpartner location is propagated
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_Locator_ID)", is(Integer.toString(l.getM_Locator_ID())))); // verify that the locator is propagated

		// the "real" virtual PI
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-VirtualPI/@C_BPartner_ID)", is(Integer.toString(bpartner.getC_BPartner_ID())))); // verify that the bpartner is propagated
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-VirtualPI/@C_BPartner_Location_ID)", is(Integer.toString(bpLocation.getC_BPartner_Location_ID())))); // verify that the bpartner location is propagated
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-VirtualPI/@M_Locator_ID)", is(Integer.toString(l.getM_Locator_ID())))); // verify that the locator is propagated
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-VirtualPI/Item/@ItemType)", is("MI")));
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-VirtualPI/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Ea']/@Qty)", is("252")));
	}
}
