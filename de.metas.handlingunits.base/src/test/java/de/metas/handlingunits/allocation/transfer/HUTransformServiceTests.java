package de.metas.handlingunits.allocation.transfer;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.w3c.dom.Node;

import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationLoadTests;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import mockit.Mocked;

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
public class HUTransformServiceTests
{
	/**
	 * This dataPoint shall enable us to test with both values of {@code isOwnPackingMaterials}.
	 */
	@DataPoints("isOwnPackingMaterials")
	public static boolean[] isOwnPackingMaterials =
		{ true, false };

	private IHandlingUnitsBL handlingUnitsBL;
	
	private HUTransformTestsBase testsBase;

	@Mocked
	private IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> noopPackingMaterialsCollector;
	
	@Before
	public void init()
	{
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		
		testsBase = new HUTransformTestsBase(noopPackingMaterialsCollector);
	}

	/**
	 * Tests {@link HUTransformService#cuToNewCU(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal)}
	 * and verifies that the method does nothing if the given CU has no parent and if the given qty is equal or greater than the CU's full quantity.
	 */
	@Test
	public void testCU_To_NewCU_MaxValueNoParent()
	{
		testsBase.testCU_To_NewCU_MaxValueNoParent_DoIt();
	}

	/**
	 * Tests {@link HUTransformService#cuToNewCU(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal)}
	 * and verifies that the method removes the given CU from its parent, if it has a parent and if the given qty is equal or greater than the CU's full quantity.
	 */
	@Test
	public void testCU_To_NewCU_MaxValueParent()
	{
		testsBase.testCU_To_NewCU_MaxValueParent_DoIt();
	}

	/**
	 * Tests {@link HUTransformService#cuToNewCU(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal)} by splitting one tomato onto a new CU.
	 * Also verifies that the new CU has the same C_BPartner, M_Locator etc as the old CU.
	 */
	@Test
	public void testCU_To_NewCU_1Tomato()
	{
		testsBase.testCU_To_NewCU_1Tomato_DoIt();
	}

	@Theory
	public void testRealCU_To_NewTUs_1Tomato_TU_Capacity_2(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCUToSplit("40");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.get(data.helper.getHUContext())
				.cuToNewTUs(cuToSplit, BigDecimal.ONE, data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(1));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("HU-VirtualPI/@HUStatus", is("A")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("HU-TU_Bag/@HUStatus", is("A")));
		assertThat(newTUXML, hasXPath("HU-TU_Bag/@HUPlanningReceiptOwnerPM", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Tests {@link HUTransformService#cuToNewTUs(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal, I_M_HU_PI_Item_Product, boolean)}
	 * by creating an <b>aggregate</b> HU with a qty of 80 (representing two IFCOs) and then splitting one kg.
	 *
	 * @param isOwnPackingMaterials
	 */
	@Theory
	public void testAggregateCU_To_NewTUs_1Tomato(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		testsBase.testAggregateCU_To_NewTUs_1Tomato_DoIt(isOwnPackingMaterials);
	}

	@Theory
	public void testRealCU_To_NewTUs_1Tomato_TU_Capacity_40(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCUToSplit("2");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.get(data.helper.getHUContext())
				.cuToNewTUs(cuToSplit, new BigDecimal("2"), data.piTU_Item_Product_IFCO_40KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(1));

		// data.helper.commitAndDumpHU(newTUs.get(0));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Run {@link HUTransformService#cuToNewTUs(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal, I_M_HU_PI_Item_Product, boolean)}
	 * by splitting a CU-quantity of 40 onto new TUs with a CU-capacity of 8 each.
	 *
	 * @param isOwnPackingMaterials
	 */
	@Theory
	public void testRealCU_To_NewTUs_40Tomatoes_TU_Capacity_8(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCUToSplit("40");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.get(data.helper.getHUContext())
				.cuToNewTUs(cuToSplit, new BigDecimal("40"), data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(5));

		// data.helper.commitAndDumpHU(newTUs.get(0));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		for (final I_M_HU newTU : newTUs)
		{
			final Node newTUXML = HUXmlConverter.toXml(newTU);

			assertThat(newTUXML, hasXPath("count(HU-TU_Bag[@HUStatus='A'])", is("1")));
			assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg'])", is("1")));
		}
	}

	@Test
	public void testRealCU_To_ExistingRealTU()
	{
		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU cuHU = testsBase.mkRealStandAloneCUToSplit("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final List<I_M_HU> existingTUs = HUTransformService.get(data.helper.getHUContext())
				.cuToNewTUs(cuHU, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
		assertThat(existingTUs.size(), is(1));
		final I_M_HU existingTU = existingTUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(existingTU), is(false));

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUBeforeXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));

		// prepare the CU to split
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCUToSplit("20");

		// invoke the method under test
		HUTransformService.get(data.helper.getHUContext())
				.cuToExistingTU(cuToSplit, new BigDecimal("20"), existingTU);

		// the cu we split from is *not* destroyed but was attached to the parent TU
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(existingTU.getM_HU_ID()));
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Like {@link #testSplitRealCU_To_ExistingRealTU()}, but the existing TU already contains 30kg (with a capacity of 40kg). Then add another 20kg. shall work.
	 */
	@Test
	public void testRealCU_To_ExistingRealTU_overfill()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU existingTU;
		{
			final I_M_HU cuHU = testsBase.mkRealStandAloneCUToSplit("30");
			final List<I_M_HU> existingTUs = HUTransformService.get(data.helper.getHUContext())
					.cuToNewTUs(cuHU, new BigDecimal("30"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
			assertThat(existingTUs.size(), is(1));
			existingTU = existingTUs.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(existingTU), is(false));

			final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
			assertThat(existingTUBeforeXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
			assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
			assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='30.000' and @C_UOM_Name='Kg'])", is("1")));
		}
		// prepare the CU to split
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCUToSplit("20");

		// invoke the method under test
		HUTransformService.get(data.helper.getHUContext())
				.cuToExistingTU(cuToSplit, new BigDecimal("20"), existingTU);

		// data.helper.commitAndDumpHU(existingTU);

		// existingTU now contains 30 + 20 = 50kg, despite its capacity is just 40kg according to the master data.
		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		assertThat(existingTUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("50.000")));

		// the cu we split from is *not* destroyed, but it was attached as-is to the existingTU
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(existingTU.getM_HU_ID()));
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Test
	public void testRealCU_To_ExistingAggregateTU()
	{
		final I_M_HU existingTU = testsBase.mkAggregateCUToSplit("80");

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUBeforeXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		assertThat(existingTUBeforeXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000")));

		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCUToSplit("20");

		// invoke the method under test
		HUTransformService.get(testsBase.getData().helper.getHUContext())
				.cuToExistingTU(cuToSplit, new BigDecimal("20"), existingTU);

		// the cu we split from is destroyed
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("D")));
		assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("0.000")));

		// the existing TU to which we wanted to add stuff is unchanged, but it now has a "real-TU" sibling
		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingTUXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		assertThat(existingTUXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000")));

		final I_M_HU fullTargetHU = existingTU.getM_HU_Item_Parent().getM_HU();
		final Node fullTargetHUXML = HUXmlConverter.toXml(fullTargetHU);
		// data.helper.commitAndDumpHU(fullTargetHU);
		assertThat(fullTargetHUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_HU_ID)", is(Integer.toString(existingTU.getM_HU_ID())))); // fullTargetHU contains existingTU
		assertThat(fullTargetHUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000"))); // fullTargetHU also contains a real IFCO with 20

	}

	/**
	 * Verifies that if {@link HUTransformService#tuToNewTUs(I_M_HU, BigDecimal, boolean)} is run with the source TU's full qty or more and since .
	 *
	 * @param isOwnPackingMaterials
	 */
	@Test
	public void testAggregateTU_To_NewTUs_MaxValueParent()
	{
		final I_M_HU tuToSplit = testsBase.mkAggregateCUToSplit("80");
		assertThat(testsBase.retrieveParentItem(tuToSplit), notNullValue()); // guard: tuToSplit shall have a parent

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.get(testsBase.getData().helper.getHUContext())
				.tuToNewTUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TUs in the source
						false); // true/false, doesn't matter
		assertThat(newTUs.size(), is(2));

		assertThat(testsBase.retrieveParentItem(newTUs.get(0)), nullValue());
		assertThat(testsBase.retrieveParentItem(newTUs.get(1)), nullValue());
	}

	@Theory
	public void testAggregateTU_To_NewTUs(@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = testsBase.mkAggregateCUToSplit("80");

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.get(testsBase.getData().helper.getHUContext())
				.tuToNewTUs(tuToSplit,
						new BigDecimal("1"), // tuQty=1; we have 2 TUs in the source, so we will will only expect 1x40 to be actually loaded
						isOwnPackingMaterials);
		assertThat(newTUs.size(), is(1));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));
		assertThat(newTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
		assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("40.000")));
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1516
	 */
	@Theory
	public void test_TakeOutTUsFromCustomLU(@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// Make sure the standard CU-TU capacity it's not 13Kg
		assertThat(data.piLU_Item_IFCO.getQty(), not(comparesEqualTo(BigDecimal.valueOf(13))));

		// Create an LU with 10TUs with 13Kg each.
		final I_M_HU lu = testsBase.mkAggregateCUToSplit("130", 13);

		// Actually take out 2 TUs
		final List<I_M_HU> newTUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewTUs(lu, BigDecimal.valueOf(2), isOwnPackingMaterials);
		assertThat(newTUs.size(), is(2));

		// Make sure each TU is valid
		// * it's top level
		// * the "HUPlanningReceiptOwnerPM" flag was correctly set
		// * it's capacity it's 13Kg and not how much was defined in CU-TU
		for (final I_M_HU newTU : newTUs)
		{
			final Node newTUXML = HUXmlConverter.toXml(newTU);
			assertThat(newTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
			assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("13.000")));
		}

	}

	/**
	 * Verifies the nothing is changed if {@link HUTransformService#tuToNewTUs(I_M_HU, BigDecimal, boolean)} is run with the source TU's full qty or more.
	 *
	 * @param isOwnPackingMaterials
	 */
	@Test
	public void testRealTU_To_NewTUs_MaxValue()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU cuHU = testsBase.mkRealStandAloneCUToSplit("20");
		final List<I_M_HU> tusToSplit = HUTransformService.get(data.helper.getHUContext())
				.cuToNewTUs(cuHU, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
		assertThat(tusToSplit.size(), is(1));
		final I_M_HU tuToSplit = tusToSplit.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewTUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg
						false); // true/false, doesn't matter
		assertThat(newTUs.size(), is(0)); // we transfer 20kg, one bag holds 8kg, so we expect 2 full bags and one partially filled bag
	}

	/**
	 * Similar to {@link #testSplitAggregateTU_To_NewTUs_MaxValue()}, but here the source TU is on a pallet.<br>
	 * So this time, it shall be taken off the pallet.
	 *
	 * @param isOwnPackingMaterials
	 */
	@Theory
	public void testRealTU_To_NewTUs(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU tuToSplit;
		final I_M_HU lu; // the parent LU of the TU to split;
		{
			final I_M_HU cuHU = testsBase.mkRealStandAloneCUToSplit("20");
			final List<I_M_HU> tusToSplit = HUTransformService.get(data.helper.getHUContext())
					.cuToNewTUs(cuHU, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
			assertThat(tusToSplit.size(), is(1));
			tuToSplit = tusToSplit.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

			final List<I_M_HU> lus = HUTransformService.get(data.helper.getHUContext())
					.tuToNewLUs(tuToSplit, BigDecimal.ONE, data.piLU_Item_IFCO, isOwnPackingMaterials);
			// get the LU and verify that it's properly linked with toToSplit
			{
				assertThat(lus.size(), is(1));
				lu = lus.get(0);
				final List<I_M_HU> includedHUs = testsBase.retrieveIncludedHUs(lu);
				assertThat(includedHUs.size(), is(1));
				assertThat(includedHUs.get(0).getM_HU_ID(), is(tuToSplit.getM_HU_ID()));

				assertThat(tuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(lu.getM_HU_ID()));
			}
		}
		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewTUs(tuToSplit,
						new BigDecimal("1"), // tuQty=1;
						isOwnPackingMaterials);
		assertThat(newTUs.size(), is(1)); // we transfer 20kg, one IFCO holds 40kg, so we expect 1 IFCO
		assertThat(newTUs.get(0).getM_HU_ID(), is(tuToSplit.getM_HU_ID()));
		assertThat(newTUs.get(0).getM_HU_Item_Parent(), nullValue());

		assertThat(lu.getHUStatus(), is("D"));
		assertThat(testsBase.retrieveIncludedHUs(lu).isEmpty(), is(true));
	}

	@Theory
	public void testAggregateTU_To_OneNewLU(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = testsBase.mkAggregateCUToSplit("80");
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(true)); // guard; make sure it's aggregate
		assertThat(tuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TUs in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);
		assertThat(newLUs.size(), is(1)); // we transfered 80kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU to suffice

		final Node luXML = HUXmlConverter.toXml(newLUs.get(0));
		assertThat(luXML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
		assertThat(luXML, hasXPath("HU-LU_Palet/@HUStatus", is("A"))); // gh #1975
		assertThat(luXML, hasXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(luXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("80.000")));

		// the pallet's included aggregate HU is 'tuToSplit'
		assertThat(luXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus", is("A"))); // gh #1975
		assertThat(luXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_HU_ID", is(Integer.toString(tuToSplit.getM_HU_ID()))));
		assertThat(luXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));

		assertThat(luXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2'])", is("1")));
		assertThat(luXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA' and @Qty='2']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("80.000")));
	}

	@Theory
	public void testAggregateTU_To_MultipleNewLUs(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = testsBase.mkAggregateCUToSplit("240"); // 6 TUs
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(true)); // guard; make sure it's aggregate
		assertThat(tuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						new BigDecimal("6"), // tuQty=6;
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);

		assertThat(newLUs.size(), is(2)); // we have 6 TUs in the source; one pallet can old 5 IFCOS, to we expect two pallets.
		{
			final Node lu1XML = HUXmlConverter.toXml(newLUs.get(0));

			assertThat(lu1XML, hasXPath("HU-LU_Palet/@HUStatus", is("A"))); // gh #1975
			assertThat(lu1XML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
			assertThat(lu1XML, hasXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM", is(Boolean.toString(isOwnPackingMaterials))));

			assertThat(lu1XML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));
			assertThat(lu1XML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus", is("A"))); // gh #1975
			assertThat(lu1XML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("200.000")));
			assertThat(lu1XML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='5'])", is("1")));
			assertThat(lu1XML, hasXPath("HU-LU_Palet/Item[@ItemType='HA' and @Qty='5']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("200.000")));
		}
		{
			final Node lu2XML = HUXmlConverter.toXml(newLUs.get(1));

			assertThat(lu2XML, hasXPath("HU-LU_Palet/@HUStatus", is("A"))); // gh #1975
			assertThat(lu2XML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
			assertThat(lu2XML, hasXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM", is(Boolean.toString(isOwnPackingMaterials))));

			assertThat(lu2XML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("40.000")));

			assertThat(lu2XML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus", is("A"))); // gh #1975
		}
	}

	@Theory
	public void testRealStandaloneTU_To_NewLU(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		// prepare the existing TU
		final I_M_HU cuHU = testsBase.mkRealCUWithTUToSplit("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU tuToSplit = testsBase.retrieveParent(cuHU);

		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);

		assertThat(newLUs.size(), is(1)); // we transfered 20kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU with one IFCO to suffice
		// data.helper.commitAndDumpHU(newLUs.get(0));
		// the LU shall contain 'tuToSplit'
		final Node newLUXML = HUXmlConverter.toXml(newLUs.get(0));
		assertThat(newLUXML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));

		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/@M_HU_PI_Item_ID)", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));
		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@M_HU_ID)", is(Integer.toString(tuToSplit.getM_HU_ID()))));

		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
	}

	/**
	 * Similar to {@link #testRealStandaloneTU_To_NewLU(boolean)}, but the source TU is moved from an old LU to a new one
	 *
	 * @param isOwnPackingMaterials
	 */
	@Theory
	public void testRealTUwithLU_To_NewLU(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		// prepare the existing TU
		final I_M_HU cuHU = testsBase.mkRealCUWithTUToSplit("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		
		final I_M_HU tuToSplit = cuHU.getM_HU_Item_Parent().getM_HU();
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// prepare tuToSplit onto a LU. This assumes that #testRealStandaloneTU_To_NewLU was green
		final List<I_M_HU> oldLUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit, BigDecimal.ONE, data.piLU_Item_IFCO, isOwnPackingMaterials);
		assertThat(oldLUs.size(), is(1)); // guard
		assertThat(tuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(oldLUs.get(0).getM_HU_ID()));
		assertThat(oldLUs.get(0).getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);

		// the old LU shall now be destroyed
		assertThat(oldLUs.get(0).getHUStatus(), is(X_M_HU.HUSTATUS_Destroyed));

		assertThat(newLUs.size(), is(1)); // we transfered 20kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU with one IFCO to suffice

		// the LU shall contain 'tuToSplit'
		final Node newLUXML = HUXmlConverter.toXml(newLUs.get(0));
		assertThat(newLUXML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));

		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/@M_HU_PI_Item_ID)", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));
		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@M_HU_ID)", is(Integer.toString(tuToSplit.getM_HU_ID()))));

		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
		assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
	}

	/**
	 * Split an aggregate TU to a LU that contains a "real" TU
	 */
	@Test
	public void testAggregateTU_to_existingLU_withRealTU()
	{
		// use the testee as a tool to get our existing LU
		final I_M_HU existingLU;
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		{
			final I_M_HU cuHU = testsBase.mkRealStandAloneCUToSplit("20");
						
			final List<I_M_HU> existingTUs = HUTransformService.get(data.helper.getHUContext())
					.cuToNewTUs(cuHU, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
			assertThat(existingTUs.size(), is(1));
			final I_M_HU exitingTu = existingTUs.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(exitingTu), is(false)); // guard; make sure it's "real"

			final List<I_M_HU> existingLUs = HUTransformService.get(data.helper.getHUContext())
					.tuToNewLUs(exitingTu,
							new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will will expect 1x20 to be actually loaded
							data.piLU_Item_IFCO,
							false);
			assertThat(existingLUs.size(), is(1));
			// data.helper.commitAndDumpHU(existingLUs.get(0));
			existingLU = existingLUs.get(0); //

			// guard: the contained TU is "real"
			final Node existingLUXML = HUXmlConverter.toXml(existingLU);
			assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
		}

		// now create the aggregation TU we are going to split
		final I_M_HU tuToSplit = testsBase.mkAggregateCUToSplit("80");

		// invoke the method under test
		HUTransformService.get(data.helper.getHUContext())
				.tuToExistingLU(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TU in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						existingLU);

		// we had 20 and loaded 80, so we now expect 100
		final Node existingLUXML = HUXmlConverter.toXml(existingLU);
		assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("100.000")));
		// data.helper.commitAndDumpHU(existingLU);
	}

	/**
	 * Split an aggregate TU to a LU that already contains an aggregated TU
	 */
	@Test
	public void testAggregateTU_To_existingLU_withAggregateTU()
	{
		// use the testee as a tool to get our existing LU
		final I_M_HU existingLU;
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		{
			final I_M_HU exitingTu = testsBase.mkAggregateCUToSplit("80");
			assertThat(handlingUnitsBL.isAggregateHU(exitingTu), is(true)); // guard; make sure it's "aggregate"

			final List<I_M_HU> existingLUs = HUTransformService.get(data.helper.getHUContext())
					.tuToNewLUs(exitingTu,
							new BigDecimal("4"), // tuQty=4; we only have 2 TUs in the source which only holds 80kg, so we will will expect 2x40 to be actually loaded onto one LU
							data.piLU_Item_IFCO,
							false);
			assertThat(existingLUs.size(), is(1));
			// data.helper.commitAndDumpHU(existingLUs.get(0));
			existingLU = existingLUs.get(0); //

			// guard: the contained TU is "real"
			final Node existingLUXML = HUXmlConverter.toXml(existingLU);
			assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000"))); // the LU has 80kg
			assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000"))); // those 80kg are contained in one aggreagate HU

			// that aggregate HU represents two IFCOS
			assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)", is("2")));
			assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID)", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));
		}

		// now create the aggregation TU we are going to split
		final I_M_HU tuToSplit = testsBase.mkAggregateCUToSplit("80");

		// invoke the method under test
		HUTransformService.get(data.helper.getHUContext())
				.tuToExistingLU(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TU in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						existingLU);

		// we had 80 and loaded 80, so we now expect 160
		final Node existingLUXML = HUXmlConverter.toXml(existingLU);
		assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("160.000")));
		// the original aggreagate HU is still intact
		assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)", is("2")));
		assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID)", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));

		// the aggregate 80kg TU which we moved in was de-aggregated into two 40kg TUs
		assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg' and @Qty='40.000'])", is("2")));
		// data.helper.commitAndDumpHU(existingLU);
	}

	/**
	 * <ul>
	 * <li>create a standalone CU with 2kg tomatoes and add it to a new TU
	 * <li>create a standalone CU with 3kg salad
	 * <li>move 1.6kg of the salad to the TU
	 * </ul>
	 *
	 * @task https://github.com/metasfresh/metasfresh-webui/issues/237 Transform CU on existing TU not working
	 */
	@Test
	public void test_CUToExistingTU_create_mixed_TU_partialCU()
	{
		final I_M_HU cu1 = testsBase.mkRealCUWithTUToSplit("2");
		assertThat(cu1.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		
		final I_M_HU existingTU = testsBase.retrieveParent(cu1);
		assertThat(existingTU.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		data.helper.load(producer, data.helper.pSalad, new BigDecimal("3"), data.helper.uomKg);
		final I_M_HU cu2 = producer.getCreatedHUs().get(0);
		handlingUnitsBL.setHUStatus(data.helper.getHUContext(), cu2, X_M_HU.HUSTATUS_Active);
		save(cu2);

		// invoke the method under test.
		HUTransformService.get(data.helper.getHUContext())
				.cuToExistingTU(cu2, new BigDecimal("1.6"), existingTU);

		// secondCU is still there, with the remaining 1.4kg
		final Node secondCUXML = HUXmlConverter.toXml(cu2);
		assertThat(secondCUXML, hasXPath("HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/@HUStatus", is("A")));
		assertThat(secondCUXML, hasXPath("HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty", is("1.400")));

		final Node existingLUXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));
	}

	/**
	 * Similar to {@link #test_CUToExistingTU_create_mixed_TU_partialCU()}, but move all the salad
	 */
	@Test
	public void test_CUToExistingTU_create_mixed_TU_completeCU()
	{
		final BigDecimal four = new BigDecimal("4");

		final I_M_HU cu1 = testsBase.mkRealCUWithTUToSplit("5");
		final I_M_HU tuWithMixedCUs = testsBase.retrieveParent(cu1);
		
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// create a standalone-CU
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		data.helper.load(producer, data.helper.pSalad, four, data.helper.uomKg);

		final I_M_HU cu2 = producer.getCreatedHUs().get(0);

		HUTransformService.get(data.helper.getHUContext())
				.cuToExistingTU(cu2, four, tuWithMixedCUs);

		// data.helper.commitAndDumpHU(tuWithMixedCUs);
		final Node tuWithMixedCUsXML = HUXmlConverter.toXml(tuWithMixedCUs);
		assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));
		assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));

		assertThat(tuWithMixedCUsXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "])", is("1")));
		assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));

		assertThat(tuWithMixedCUsXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "])", is("1")));
		assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));
	}

	/**
	 * Verifies the splitting off an aggregate HU with a non-int storage value.
	 * If this test shows problems, also see {@link LUTUProducerDestinationLoadTests#testAggregateSingleLUFullyLoaded_non_int()}.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1237, but this even worked before the issue came up.
	 * 
	 */
	@Theory
	public void testAggregateSingleLUFullyLoaded_non_int(@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final LUTUProducerDestinationTestSupport  data = testsBase.getData();
		
		// create a special hu pi item that says "on LU can hold 20 IFCOs"
		final I_M_HU_PI_Item piLU_Item_20_IFCO = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("20"));

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(piLU_Item_20_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.addTUCapacity(data.helper.pTomato, new BigDecimal("5.47"), data.helper.uomKg); // set the TU capacity to be 109.4 / 20

		// load the tomatoes into HUs
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("109.4"), data.helper.uomKg);
		assertThat(lutuProducer.getCreatedHUs().size(), is(1));
		final I_M_HU createdLU = lutuProducer.getCreatedHUs().get(0);

		final List<I_M_HU> aggregateTUs = testsBase.retrieveIncludedHUs(createdLU);
		assertThat(aggregateTUs.size(), is(1));
		final I_M_HU aggregateTU = aggregateTUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(aggregateTU), is(true));

		final List<I_M_HU> newTUs = HUTransformService.get(data.helper.getHUContext())
				.tuToNewTUs(aggregateTU, BigDecimal.ONE, isOwnPackingMaterials);
		assertThat(newTUs.size(), is(1));
		final I_M_HU newTU = newTUs.get(0);
		// data.helper.commitAndDumpHU(newTU);

		final Node newTuXML = HUXmlConverter.toXml(newTU);
		assertThat(newTuXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.470")));

		// the original aggregate HU is still intact, it just represents one less TU

		final Node createdLuXML = HUXmlConverter.toXml(createdLU);

		// there shall still be no "real" HU
		assertThat(createdLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("0")));

		// the aggregate HU shall contain the full remaining quantity and represent 19 IFCOs; 5.47 x 19 = 103,93
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("103.930")));

		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)", is("19")));
		assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("103.930")));
	}
}
