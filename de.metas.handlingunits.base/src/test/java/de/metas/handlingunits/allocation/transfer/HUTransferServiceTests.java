package de.metas.handlingunits.allocation.transfer;

import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.w3c.dom.Node;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.interfaces.I_M_Warehouse;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@RunWith(Theories.class)
public class HUTransferServiceTests
{
	/**
	 * This dataPoint shall enable us to test with both values of {@code isOwnPackingMaterials}.
	 */
	@DataPoints("isOwnPackingMaterials")
	public static boolean[] isOwnPackingMaterials = { true, false };

	@DataPoints("isAggregateCU")
	public static boolean[] isAggregateCU = { true, false };

	private LUTUProducerDestinationTestSupport data;

	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	/**
	 * Tests {@link HUTransferService#action_SplitCU_To_NewCU(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal)} by splitting one tomato onto a new CU.
	 */
	@Test
	public void testSplitCU_To_NewCU_1Tomato()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_C_BPartner bpartner = data.helper.createBPartner("testVendor");
		final I_C_BPartner_Location bPartnerLocation = data.helper.createBPartnerLocation(bpartner);

		final I_M_Warehouse warehouse = data.helper.createWarehouse("testWarehouse");
		final I_M_Locator locator = data.helper.createLocator("testLocator", warehouse);

		final I_M_HU sourceTU;
		final I_M_HU cuToSplit;
		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

			lutuProducer.setNoLU();
			lutuProducer.setTUPI(data.piTU_IFCO);
			lutuProducer.setC_BPartner(bpartner);
			lutuProducer.setM_Locator(locator);
			lutuProducer.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());

			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("2"), data.helper.uomKg);
			final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

			assertThat(createdTUs.size(), is(1));
			sourceTU = createdTUs.get(0);

			final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
			assertThat(createdCUs.size(), is(1));

			cuToSplit = createdCUs.get(0);
		}

		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewCU(cuToSplit, data.helper.pTomato, data.helper.uomKg, BigDecimal.ONE);

		assertThat(newCUs.size(), is(1));

		final Node sourceTUXML = HUXmlConverter.toXml(sourceTU);
		assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='P'])", is("1")));
		assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newCUXML = HUXmlConverter.toXml(newCUs.get(0));
		assertThat(newCUXML, not(hasXPath("HU-VirtualPI/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
		assertThat(newCUXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(newCUXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(newCUXML, hasXPath("string(HU-VirtualPI/@C_BPartner_ID)", is(Integer.toString(bpartner.getC_BPartner_ID())))); // verify that the bpartner is propagated
		assertThat(newCUXML, hasXPath("string(HU-VirtualPI/@C_BPartner_Location_ID)", is(Integer.toString(bPartnerLocation.getC_BPartner_Location_ID())))); // verify that the bpartner location is propagated
		assertThat(newCUXML, hasXPath("string(HU-VirtualPI/@M_Locator_ID)", is(Integer.toString(locator.getM_Locator_ID())))); // verify that the locator is propagated
	}

	/**
	 * Similar to {@link #testSplitCU_To_NewCU_1Tomato()}, but here we split off both tomatoes, i.e. the full quantity of the source CU.<br>
	 * After this bold move we expect the source CU to be empty and destroyed.
	 */
	@Test
	public void testSplitCU_To_NewCU_2Tomatoes()
	{
		final I_M_HU sourceTU;
		final I_M_HU cuToSplit;
		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

			lutuProducer.setNoLU();
			lutuProducer.setTUPI(data.piTU_IFCO);

			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("2"), data.helper.uomKg);
			final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

			assertThat(createdTUs.size(), is(1));
			sourceTU = createdTUs.get(0);

			final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
			assertThat(createdCUs.size(), is(1));

			cuToSplit = createdCUs.get(0);
		}

		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewCU(cuToSplit, data.helper.pTomato, data.helper.uomKg, new BigDecimal("2"));

		assertThat(newCUs.size(), is(1));

		final Node sourceTUXML = HUXmlConverter.toXml(sourceTU);
		assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='D'])", is("1")));
		assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newCUXML = HUXmlConverter.toXml(newCUs.get(0));

		assertThat(newCUXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(newCUXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Theory
	public void testSplitRealCU_To_NewTUs_1Tomato_TU_Capacity_2(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkRealCUToSplit("40");

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuToSplit, data.helper.pTomato, data.helper.uomKg, BigDecimal.ONE, data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(1));

		// data.helper.commitAndDumpHU(cuToSplit);

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("count(HU-TU_Bag[@HUStatus='P'])", is("1")));
		assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Tests {@link HUTransferService#action_SplitCU_To_NewTUs(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal, I_M_HU_PI_Item_Product, boolean)}
	 * by creating an <b>aggregate</b> HU with a qty of 80 (representing two IFCOs) and then splitting one.
	 * 
	 * @param isOwnPackingMaterials
	 */
	@Theory
	public void testSplitAggregateCU_To_NewTUs_1Tomato(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkAggregateCUToSplit("80"); // match the IFCOs capacity

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuToSplit, data.helper.pTomato, data.helper.uomKg, BigDecimal.ONE, data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(1));

		// data.helper.commitAndDumpHU(cuToSplit);

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));

		final I_M_HU parentOfCUToSplit = cuToSplit.getM_HU_Item_Parent().getM_HU();
		// data.helper.commitAndDumpHU(parentOfCUToSplit);
		// the source TU now needs to contain one haggregate HU that represent the remaining "untouched" IFCO with a quantity of 40 and a new "real" IFCO with a qunatity of 39.
		final Node parentOfCUToSplitXML = HUXmlConverter.toXml(parentOfCUToSplit);
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet[@HUStatus='P'])", is("1")));
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='79.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("count(HU-TU_Bag[@HUStatus='P'])", is("1")));
		assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Theory
	public void testSplitRealCU_To_NewTUs_1Tomato_TU_Capacity_40(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkRealCUToSplit("2");

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuToSplit, data.helper.pTomato, data.helper.uomKg, new BigDecimal("2"), data.piTU_Item_Product_IFCO_40KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(1));

		// data.helper.commitAndDumpHU(newTUs.get(0));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='P'])", is("1")));
		assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Run {@link HUTransferService#action_SplitCU_To_NewTUs(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal, I_M_HU_PI_Item_Product, boolean)}
	 * by splitting a CU-quantity of 40 onto new TUs with a CU-capacity of 8 each.
	 * 
	 * @param isOwnPackingMaterials
	 */
	@Theory
	public void testSplitRealCU_To_NewTUs_40Tomatoes_TU_Capacity_8(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		// TODO talk about this behavior with mark
		final I_M_HU cuToSplit = mkRealCUToSplit("40");

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuToSplit, data.helper.pTomato, data.helper.uomKg, new BigDecimal("40"), data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(5));

		// data.helper.commitAndDumpHU(newTUs.get(0));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		for (final I_M_HU newTU : newTUs)
		{
			final Node newTUXML = HUXmlConverter.toXml(newTU);

			assertThat(newTUXML, hasXPath("count(HU-TU_Bag[@HUStatus='P'])", is("1")));
			assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg'])", is("1")));
		}
	}

	@Theory
	public void testSplitRealCU_To_ExistingRealTU()
	{
		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU cuHU = mkRealCUToSplit("20");
		final List<I_M_HU> existingTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuHU, data.helper.pTomato, data.helper.uomKg, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
		assertThat(existingTUs.size(), is(1));
		final I_M_HU existingTU = existingTUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(existingTU), is(false));

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUBeforeXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO[@HUStatus='P'])", is("1")));
		assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));

		// prepare the CU to split
		final I_M_HU cuToSplit = mkRealCUToSplit("20");

		// invoke the method under test
		HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_ExistingTU(cuToSplit, data.helper.pTomato, data.helper.uomKg, new BigDecimal("20"), existingTU);

		// the cu we split from is destroyed
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='P'])", is("1")));
		assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Test
	public void testSplitRealCU_To_ExistingAggregateTU()
	{
		final I_M_HU existingTU = mkAggregateCUToSplit("80");

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUBeforeXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("P")));
		assertThat(existingTUBeforeXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000")));

		final I_M_HU cuToSplit = mkRealCUToSplit("20");

		// invoke the method under test
		HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_ExistingTU(cuToSplit, data.helper.pTomato, data.helper.uomKg, new BigDecimal("20"), existingTU);

		// the cu we split from is destroyed
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("D")));
		assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("0.000")));

		// the existing TU to which we wanted to add stuff is unchanged, but it now has a "real-TU" sibling
		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("P")));
		assertThat(existingTUXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000")));

		final I_M_HU fullTargetHU = existingTU.getM_HU_Item_Parent().getM_HU();
		final Node fullTargetHUXML = HUXmlConverter.toXml(fullTargetHU);
		// data.helper.commitAndDumpHU(fullTargetHU);
		assertThat(fullTargetHUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_HU_ID)", is(Integer.toString(existingTU.getM_HU_ID())))); // fullTargetHU contains existingTU
		assertThat(fullTargetHUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000"))); // fullTargetHU also contains a real IFCO with 20

	}

	@Theory
	public void testSplitAggregateTU_To_NewTUs(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = mkAggregateCUToSplit("80");

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitTU_To_NewTUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TUs in the source, so we will will only expect 2x40 to be actually loaded
						data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);
		assertThat(newTUs.size(), is(10)); // we transfer 80kg, one bag holds 8kg, so we expect 10 full bags

		newTUs.forEach(newTU -> {

			final Node newTUXML = HUXmlConverter.toXml(newTU);
			assertThat(newTUXML, not(hasXPath("HU-TU_Bag/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
			assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			assertThat(newTUXML, hasXPath("string(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("8.000")));
		});
	}

	@Theory
	public void testSplitRealTU_To_NewTUs(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU cuHU = mkRealCUToSplit("20");
		final List<I_M_HU> tusToSplit = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuHU, data.helper.pTomato, data.helper.uomKg, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
		assertThat(tusToSplit.size(), is(1));
		final I_M_HU tuToSplit = tusToSplit.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitTU_To_NewTUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only hold 20kg, so we will will only expect 1x20 to be actually loaded
						data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);
		assertThat(newTUs.size(), is(3)); // we transfer 20kg, one bag holds 8kg, so we expect 2 full bags and one partially filled bag

		{
			final Node newTU1XML = HUXmlConverter.toXml(newTUs.get(0));
			assertThat(newTU1XML, not(hasXPath("HU-TU_Bag/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
			assertThat(newTU1XML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			assertThat(newTU1XML, hasXPath("string(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("8.000")));
		}
		{
			final Node newTU2XML = HUXmlConverter.toXml(newTUs.get(1));
			assertThat(newTU2XML, not(hasXPath("HU-TU_Bag/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
			assertThat(newTU2XML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			assertThat(newTU2XML, hasXPath("string(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("8.000")));
		}
		{
			final Node newTU3XML = HUXmlConverter.toXml(newTUs.get(2));
			assertThat(newTU3XML, not(hasXPath("HU-TU_Bag/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
			assertThat(newTU3XML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			assertThat(newTU3XML, hasXPath("string(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));
		}
	}

	@Theory
	public void testSplitAggregateTU_To_NewLU(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = mkAggregateCUToSplit("80");

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitTU_To_NewLU(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TU in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						data.piTU_Item_Product_Bag_8KgTomatoes,
						data.piLU_Item_Bag,
						isOwnPackingMaterials);

		assertThat(newLUs.size(), is(5)); // we transfer 80kg, one bag holds 8kg, one LU holds two bags so we expect 10 full bags and therefore 5 LUs

		newLUs.forEach(newLU -> {
			final Node newTU3XML = HUXmlConverter.toXml(newLU);
			assertThat(newTU3XML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
			assertThat(newTU3XML, hasXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));

			assertThat(newTU3XML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("16.000")));
			assertThat(newTU3XML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2'])", is("1")));
			assertThat(newTU3XML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("16.000")));
		});
	}

	@Theory
	public void testSplitRealTU_To_NewLU(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU cuHU = mkRealCUToSplit("20");
		final List<I_M_HU> tusToSplit = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuHU, data.helper.pTomato, data.helper.uomKg, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
		assertThat(tusToSplit.size(), is(1));
		final I_M_HU tuToSplit = tusToSplit.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitTU_To_NewLU(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will will expect 1x20 to be actually loaded
						data.piTU_Item_Product_Bag_8KgTomatoes,
						data.piLU_Item_Bag,
						isOwnPackingMaterials);

		assertThat(newLUs.size(), is(2)); // we transfer 20kg, one bag holds 8kg, one LU holds two bags so we expect one LU with two full bags and one LU with one partially loaded bag

		{
			final Node newLU1XML = HUXmlConverter.toXml(newLUs.get(0));
			assertThat(newLU1XML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
			assertThat(newLU1XML, hasXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));

			assertThat(newLU1XML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("16.000")));
			assertThat(newLU1XML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2'])", is("1")));
			assertThat(newLU1XML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("16.000")));
		}

		{
			// the second LU shall contain a "real" TU
			final Node newLU2XML = HUXmlConverter.toXml(newLUs.get(1));
			assertThat(newLU2XML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
			assertThat(newLU2XML, hasXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));

			assertThat(newLU2XML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));
			assertThat(newLU2XML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));
		}
	}

	@Test
	public void testSplitAggregateTU_To_existingLU()
	{
		final I_M_HU existingLU;
		{
			// use the testee as a tool to get our existing LU
			final I_M_HU cuHU = mkRealCUToSplit("20");
			final List<I_M_HU> existingTUs = HUTransferService.get(data.helper.getHUContext())
					.action_SplitCU_To_NewTUs(cuHU, data.helper.pTomato, data.helper.uomKg, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
			assertThat(existingTUs.size(), is(1));
			final I_M_HU exitingTu = existingTUs.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(exitingTu), is(false)); // guard; make sure it's "real"

			final List<I_M_HU> existingLUs = HUTransferService.get(data.helper.getHUContext())
					.action_SplitTU_To_NewLU(exitingTu,
							new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will will expect 1x20 to be actually loaded
							data.piTU_Item_Product_Bag_8KgTomatoes,
							data.piLU_Item_Bag,
							false);
			existingLU = existingLUs.get(1); // this is the partially filled LU from the last test; it contains a qty of 4
		}
		
		
		final I_M_HU tuToSplit = mkAggregateCUToSplit("80");

		// invoke the method under test
		HUTransferService.get(data.helper.getHUContext())
				.action_SplitTU_To_ExistingLU(tuToSplit, 
						new BigDecimal("4"), // tuQty=4; we only have 2 TU in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded 
						existingLU);
		
		final Node existingLUXML = HUXmlConverter.toXml(existingLU);
		assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("84.000")));
		//data.helper.commitAndDumpHU(existingLU);
	}

	private I_M_HU mkRealCUToSplit(final String strCuQty)
	{

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);

		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal(strCuQty), data.helper.uomKg);
		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

		assertThat(createdTUs.size(), is(1));

		final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
		assertThat(createdCUs.size(), is(1));

		final I_M_HU cuToSplit = createdCUs.get(0);

		return cuToSplit;
	}

	private I_M_HU mkAggregateCUToSplit(final String strCuQty)
	{

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setTUPI(data.piTU_IFCO);

		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal(strCuQty), data.helper.uomKg);
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();

		assertThat(createdLUs.size(), is(1));
		// data.helper.commitAndDumpHU(createdLUs.get(0));

		final List<I_M_HU> createdAggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(createdLUs.get(0));
		assertThat(createdAggregateHUs.size(), is(1));

		final I_M_HU cuToSplit = createdAggregateHUs.get(0);
		assertThat(Services.get(IHandlingUnitsBL.class).isAggregateHU(cuToSplit), is(true));

		return cuToSplit;
	}
}
