package de.metas.handlingunits.allocation.transfer;

import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.w3c.dom.Node;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationLoadTests;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.storage.EmptyHUListener;
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
	@Rule
	public AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	/**
	 * This dataPoint shall enable us to test with both values of {@code isOwnPackingMaterials}.
	 */
	@DataPoints("isOwnPackingMaterials")
	public static boolean[] isOwnPackingMaterials = { true, false };

	private IHandlingUnitsBL handlingUnitsBL;

	private HUTransformTestsBase testsBase;

	@Mocked
	private IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> noopPackingMaterialsCollector;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
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
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCuWithCuQty("40");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewTUs(cuToSplit, BigDecimal.ONE, data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		Assert.assertThat(newTUs.size(), is(1));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		Assert.assertThat(cuToSplitXML, hasXPath("HU-VirtualPI/@HUStatus", is("A")));
		Assert.assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		Assert.assertThat(newTUXML, hasXPath("HU-TU_Bag/@HUStatus", is("A")));
		Assert.assertThat(newTUXML, hasXPath("HU-TU_Bag/@HUPlanningReceiptOwnerPM", is(Boolean.toString(isOwnPackingMaterials))));
		Assert.assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
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
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCuWithCuQty("2");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewTUs(cuToSplit, new BigDecimal("2"), data.piTU_Item_Product_IFCO_40KgTomatoes, isOwnPackingMaterials);

		Assert.assertThat(newTUs.size(), is(1));

		// data.helper.commitAndDumpHU(newTUs.get(0));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		Assert.assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		Assert.assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		Assert.assertThat(newTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		Assert.assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		Assert.assertThat(newTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg'])", is("1")));
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
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCuWithCuQty("40");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewTUs(cuToSplit, new BigDecimal("40"), data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		Assert.assertThat(newTUs.size(), is(5));

		// data.helper.commitAndDumpHU(newTUs.get(0));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		Assert.assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		Assert.assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		for (final I_M_HU newTU : newTUs)
		{
			final Node newTUXML = HUXmlConverter.toXml(newTU);

			Assert.assertThat(newTUXML, hasXPath("count(HU-TU_Bag[@HUStatus='A'])", is("1")));
			Assert.assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			Assert.assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg'])", is("1")));
		}
	}

	@Test
	public void testRealCU_To_ExistingRealTU()
	{
		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU cuHU = testsBase.mkRealStandAloneCuWithCuQty("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final List<I_M_HU> existingTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewTUs(cuHU, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
		Assert.assertThat(existingTUs.size(), is(1));
		final I_M_HU existingTU = existingTUs.get(0);
		Assert.assertThat(handlingUnitsBL.isAggregateHU(existingTU), is(false));

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingTUBeforeXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		Assert.assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		Assert.assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));

		// prepare the CU to split
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCuWithCuQty("20");

		// invoke the method under test
		HUTransformService.newInstance(data.helper.getHUContext())
				.cuToExistingTU(cuToSplit, new BigDecimal("20"), existingTU);

		// the cu we split from is *not* destroyed but was attached to the parent TU
		Assert.assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(existingTU.getM_HU_ID()));
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		Assert.assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		Assert.assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		Assert.assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
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
			final I_M_HU cuHU = testsBase.mkRealStandAloneCuWithCuQty("30");
			final List<I_M_HU> existingTUs = HUTransformService.newInstance(data.helper.getHUContext())
					.cuToNewTUs(cuHU, new BigDecimal("30"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
			Assert.assertThat(existingTUs.size(), is(1));
			existingTU = existingTUs.get(0);
			Assert.assertThat(handlingUnitsBL.isAggregateHU(existingTU), is(false));

			final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
			Assert.assertThat(existingTUBeforeXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
			Assert.assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
			Assert.assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='30.000' and @C_UOM_Name='Kg'])", is("1")));
		}
		// prepare the CU to split
		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCuWithCuQty("20");

		// invoke the method under test
		HUTransformService.newInstance(data.helper.getHUContext())
				.cuToExistingTU(cuToSplit, new BigDecimal("20"), existingTU);

		// data.helper.commitAndDumpHU(existingTU);

		// existingTU now contains 30 + 20 = 50kg, despite its capacity is just 40kg according to the master data.
		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		Assert.assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		Assert.assertThat(existingTUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("50.000")));

		// the cu we split from is *not* destroyed, but it was attached as-is to the existingTU
		Assert.assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(existingTU.getM_HU_ID()));
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		Assert.assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Test
	public void testRealCU_To_ExistingAggregateTU()
	{
		final I_M_HU existingTU = testsBase.mkAggregateHUWithTotalQtyCU("80");

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingTUBeforeXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(existingTUBeforeXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000")));

		final I_M_HU cuToSplit = testsBase.mkRealStandAloneCuWithCuQty("20");

		// invoke the method under test
		HUTransformService.newInstance(testsBase.getData().helper.getHUContext())
				.cuToExistingTU(cuToSplit, new BigDecimal("20"), existingTU);

		// the cu we split from is destroyed
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		Assert.assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("D")));
		Assert.assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("0.000")));

		// the existing TU to which we wanted to add stuff is unchanged, but it now has a "real-TU" sibling
		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingTUXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(existingTUXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000")));

		final I_M_HU fullTargetHU = existingTU.getM_HU_Item_Parent().getM_HU();
		final Node fullTargetHUXML = HUXmlConverter.toXml(fullTargetHU);
		// data.helper.commitAndDumpHU(fullTargetHU);
		Assert.assertThat(fullTargetHUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_HU_ID)", is(Integer.toString(existingTU.getM_HU_ID())))); // fullTargetHU contains existingTU
		Assert.assertThat(fullTargetHUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000"))); // fullTargetHU also contains a real IFCO with 20

	}

	/**
	 * Verifies that if {@link HUTransformService#tuToNewTUs(I_M_HU, BigDecimal, boolean)} is run with the source TU's full qty or more and since .
	 *
	 * @param isOwnPackingMaterials
	 */
	@Test
	public void testAggregateTU_To_NewTUs_MaxValueParent()
	{
		final I_M_HU tuToSplit = testsBase.mkAggregateHUWithTotalQtyCU("80");
		Assert.assertThat(testsBase.retrieveParentItem(tuToSplit), notNullValue()); // guard: tuToSplit shall have a parent

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(testsBase.getData().helper.getHUContext())
				.tuToNewTUs(tuToSplit,
						new BigDecimal("4")); // tuQty=4; we only have 2 TUs in the source
		Assert.assertThat(newTUs.size(), is(2));

		Assert.assertThat(testsBase.retrieveParentItem(newTUs.get(0)), nullValue());
		Assert.assertThat(testsBase.retrieveParentItem(newTUs.get(1)), nullValue());
	}

	@Test
	public void testAggregateTU_To_NewTUs()
	{
		final I_M_HU tuToSplit = testsBase.mkAggregateHUWithTotalQtyCU("80");

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(testsBase.getData().helper.getHUContext())
				.tuToNewTUs(tuToSplit,
						new BigDecimal("1")); // tuQty=1; we have 2 TUs in the source, so we will will only expect 1x40 to be actually loaded
		Assert.assertThat(newTUs.size(), is(1));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));
		Assert.assertThat(newTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
		Assert.assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("40.000")));
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1516
	 */
	@Test
	public void test_TakeOutTUsFromCustomLU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// Make sure the standard CU-TU capacity it's not 13Kg
		Assert.assertThat(data.piLU_Item_IFCO.getQty(), not(comparesEqualTo(BigDecimal.valueOf(13))));

		// Create an LU with 10TUs with 13Kg each.
		final I_M_HU tu = testsBase.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("130", 13);

		// Actually take out 2 TUs
		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewTUs(tu, BigDecimal.valueOf(2));
		Assert.assertThat(newTUs.size(), is(2));

		// Make sure each TU is valid
		// * it's top level
		// * the "HUPlanningReceiptOwnerPM" flag was correctly set
		// * it's capacity it's 13Kg and not how much was defined in CU-TU
		for (final I_M_HU newTU : newTUs)
		{
			final Node newTUXML = HUXmlConverter.toXml(newTU);
			Assert.assertThat(newTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
			Assert.assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("13.000")));
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
		final I_M_HU cuHU = testsBase.mkRealStandAloneCuWithCuQty("20");
		final List<I_M_HU> tusToSplit = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewTUs(cuHU, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
		Assert.assertThat(tusToSplit.size(), is(1));
		final I_M_HU tuToSplit = tusToSplit.get(0);
		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewTUs(tuToSplit,
						new BigDecimal("4")); // tuQty=4; we only have 1 TU in the source which only holds 20kg
		assertThat(newTUs).containsExactly(tuToSplit);
	}

	/**
	 * Similar to {@link #testSplitAggregateTU_To_NewTUs_MaxValue()}, but here the source TU is on a pallet.<br>
	 * So this time, it shall be taken off the pallet.
	 *
	 * @param isOwnPackingMaterials
	 */
	@Theory
	public void testRealTU_To_NewTUs(@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU tuToSplit;
		final I_M_HU lu; // the parent LU of the TU to split;
		{
			final I_M_HU cuHU = testsBase.mkRealStandAloneCuWithCuQty("20");
			final List<I_M_HU> tusToSplit = HUTransformService.newInstance(data.helper.getHUContext())
					.cuToNewTUs(cuHU, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
			Assert.assertThat(tusToSplit.size(), is(1));
			tuToSplit = tusToSplit.get(0);
			Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

			final List<I_M_HU> lus = HUTransformService.newInstance(data.helper.getHUContext())
					.tuToNewLUs(tuToSplit, BigDecimal.ONE, data.piLU_Item_IFCO, isOwnPackingMaterials);
			// get the LU and verify that it's properly linked with toToSplit
			{
				Assert.assertThat(lus.size(), is(1));
				lu = lus.get(0);
				final List<I_M_HU> includedHUs = testsBase.retrieveIncludedHUs(lu);
				Assert.assertThat(includedHUs.size(), is(1));
				Assert.assertThat(includedHUs.get(0).getM_HU_ID(), is(tuToSplit.getM_HU_ID()));

				Assert.assertThat(tuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(lu.getM_HU_ID()));
			}
		}
		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewTUs(tuToSplit,
						new BigDecimal("1")); // tuQty=1;
		Assert.assertThat(newTUs.size(), is(1)); // we transfer 20kg, one IFCO holds 40kg, so we expect 1 IFCO
		Assert.assertThat(newTUs.get(0).getM_HU_ID(), is(tuToSplit.getM_HU_ID()));
		Assert.assertThat(newTUs.get(0).getM_HU_Item_Parent(), nullValue());

		Assert.assertThat(lu.getHUStatus(), is("D"));
		Assert.assertThat(testsBase.retrieveIncludedHUs(lu).isEmpty(), is(true));
	}

	@Theory
	public void testAggregateTU_To_OneNewLU(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = testsBase.mkAggregateHUWithTotalQtyCU("80");
		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(true)); // guard; make sure it's aggregate
		Assert.assertThat(tuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TUs in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);
		Assert.assertThat(newLUs.size(), is(1)); // we transfered 80kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU to suffice

		final Node luXML = HUXmlConverter.toXml(newLUs.get(0));
		Assert.assertThat(luXML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
		Assert.assertThat(luXML, hasXPath("HU-LU_Palet/@HUStatus", is("A"))); // gh #1975
		Assert.assertThat(luXML, hasXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM", is(Boolean.toString(isOwnPackingMaterials))));
		Assert.assertThat(luXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("80.000")));

		// the pallet's included aggregate HU is 'tuToSplit'
		Assert.assertThat(luXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus", is("A"))); // gh #1975
		Assert.assertThat(luXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_HU_ID", is(Integer.toString(tuToSplit.getM_HU_ID()))));
		Assert.assertThat(luXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));

		Assert.assertThat(luXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2'])", is("1")));
		Assert.assertThat(luXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA' and @Qty='2']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("80.000")));
	}

	@Theory
	public void testAggregateTU_To_MultipleNewLUs(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = testsBase.mkAggregateHUWithTotalQtyCU("240"); // 6 TUs
		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(true)); // guard; make sure it's aggregate
		Assert.assertThat(tuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						new BigDecimal("6"), // tuQty=6;
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);

		Assert.assertThat(newLUs.size(), is(2)); // we have 6 TUs in the source; one pallet can old 5 IFCOS, to we expect two pallets.
		{
			final Node lu1XML = HUXmlConverter.toXml(newLUs.get(0));

			Assert.assertThat(lu1XML, hasXPath("HU-LU_Palet/@HUStatus", is("A"))); // gh #1975
			Assert.assertThat(lu1XML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
			Assert.assertThat(lu1XML, hasXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM", is(Boolean.toString(isOwnPackingMaterials))));

			Assert.assertThat(lu1XML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));
			Assert.assertThat(lu1XML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus", is("A"))); // gh #1975
			Assert.assertThat(lu1XML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("200.000")));
			Assert.assertThat(lu1XML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='5'])", is("1")));
			Assert.assertThat(lu1XML, hasXPath("HU-LU_Palet/Item[@ItemType='HA' and @Qty='5']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("200.000")));
		}
		{
			final Node lu2XML = HUXmlConverter.toXml(newLUs.get(1));

			Assert.assertThat(lu2XML, hasXPath("HU-LU_Palet/@HUStatus", is("A"))); // gh #1975
			Assert.assertThat(lu2XML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
			Assert.assertThat(lu2XML, hasXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM", is(Boolean.toString(isOwnPackingMaterials))));

			Assert.assertThat(lu2XML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("40.000")));

			Assert.assertThat(lu2XML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus", is("A"))); // gh #1975
		}
	}

	@Theory
	public void testRealStandaloneTU_To_NewLU(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		// prepare the existing TU
		final I_M_HU cuHU = testsBase.mkRealCUWithTUandQtyCU("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU tuToSplit = testsBase.retrieveParent(cuHU);

		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);

		Assert.assertThat(newLUs.size(), is(1)); // we transfered 20kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU with one IFCO to suffice
		// data.helper.commitAndDumpHU(newLUs.get(0));
		// the LU shall contain 'tuToSplit'
		final Node newLUXML = HUXmlConverter.toXml(newLUs.get(0));
		Assert.assertThat(newLUXML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));

		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/@M_HU_PI_Item_ID)", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));
		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@M_HU_ID)", is(Integer.toString(tuToSplit.getM_HU_ID()))));

		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
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
		final I_M_HU cuHU = testsBase.mkRealCUWithTUandQtyCU("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU tuToSplit = cuHU.getM_HU_Item_Parent().getM_HU();
		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// prepare tuToSplit onto a LU. This assumes that #testRealStandaloneTU_To_NewLU was green
		final List<I_M_HU> oldLUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit, BigDecimal.ONE, data.piLU_Item_IFCO, isOwnPackingMaterials);
		Assert.assertThat(oldLUs.size(), is(1)); // guard
		Assert.assertThat(tuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(oldLUs.get(0).getM_HU_ID()));
		Assert.assertThat(oldLUs.get(0).getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);

		// the old LU shall now be destroyed
		Assert.assertThat(oldLUs.get(0).getHUStatus(), is(X_M_HU.HUSTATUS_Destroyed));

		Assert.assertThat(newLUs.size(), is(1)); // we transfered 20kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU with one IFCO to suffice

		// the LU shall contain 'tuToSplit'
		final Node newLUXML = HUXmlConverter.toXml(newLUs.get(0));
		Assert.assertThat(newLUXML, not(hasXPath("HU-LU_Palet/M_HU_Item_Parent_ID"))); // verify that the LU has no parent HU
		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));

		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/@M_HU_PI_Item_ID)", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));
		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@M_HU_ID)", is(Integer.toString(tuToSplit.getM_HU_ID()))));

		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
		Assert.assertThat(newLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
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
			final I_M_HU cuHU = testsBase.mkRealStandAloneCuWithCuQty("20");

			final List<I_M_HU> existingTUs = HUTransformService.newInstance(data.helper.getHUContext())
					.cuToNewTUs(cuHU, new BigDecimal("20"), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
			Assert.assertThat(existingTUs.size(), is(1));
			final I_M_HU exitingTu = existingTUs.get(0);
			Assert.assertThat(handlingUnitsBL.isAggregateHU(exitingTu), is(false)); // guard; make sure it's "real"

			final List<I_M_HU> existingLUs = HUTransformService.newInstance(data.helper.getHUContext())
					.tuToNewLUs(exitingTu,
							new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will will expect 1x20 to be actually loaded
							data.piLU_Item_IFCO,
							false);
			Assert.assertThat(existingLUs.size(), is(1));
			// data.helper.commitAndDumpHU(existingLUs.get(0));
			existingLU = existingLUs.get(0); //

			// guard: the contained TU is "real"
			final Node existingLUXML = HUXmlConverter.toXml(existingLU);
			Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("20.000")));
		}

		// now create the aggregation TU we are going to split
		final I_M_HU tuToSplit = testsBase.mkAggregateHUWithTotalQtyCU("80");

		// invoke the method under test
		HUTransformService.newInstance(data.helper.getHUContext())
				.tuToExistingLU(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TU in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						existingLU);

		// we had 20 and loaded 80, so we now expect 100
		final Node existingLUXML = HUXmlConverter.toXml(existingLU);
		Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("100.000")));
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
			final I_M_HU exitingTu = testsBase.mkAggregateHUWithTotalQtyCU("80");
			Assert.assertThat(handlingUnitsBL.isAggregateHU(exitingTu), is(true)); // guard; make sure it's "aggregate"

			final List<I_M_HU> existingLUs = HUTransformService.newInstance(data.helper.getHUContext())
					.tuToNewLUs(exitingTu,
							new BigDecimal("4"), // tuQty=4; we only have 2 TUs in the source which only holds 80kg, so we will will expect 2x40 to be actually loaded onto one LU
							data.piLU_Item_IFCO,
							false);
			Assert.assertThat(existingLUs.size(), is(1));
			// data.helper.commitAndDumpHU(existingLUs.get(0));
			existingLU = existingLUs.get(0); //

			// guard: the contained TU is "real"
			final Node existingLUXML = HUXmlConverter.toXml(existingLU);
			Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000"))); // the LU has 80kg
			Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000"))); // those 80kg are contained in one aggreagate HU

			// that aggregate HU represents two IFCOS
			Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)", is("2")));
			Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID)", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));
		}

		// now create the aggregation TU we are going to split
		final I_M_HU tuToSplit = testsBase.mkAggregateHUWithTotalQtyCU("80");

		// invoke the method under test
		HUTransformService.newInstance(data.helper.getHUContext())
				.tuToExistingLU(tuToSplit,
						new BigDecimal("4"), // tuQty=4; we only have 2 TU in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						existingLU);

		// we had 80 and loaded 80, so we now expect 160
		final Node existingLUXML = HUXmlConverter.toXml(existingLU);
		Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("160.000")));
		// the original aggreagate HU is still intact
		Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)", is("2")));
		Assert.assertThat(existingLUXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID)", is(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()))));

		// the aggregate 80kg TU which we moved in was de-aggregated into two 40kg TUs
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg' and @Qty='40.000'])", is("2")));
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
		final I_M_HU cu1 = testsBase.mkRealCUWithTUandQtyCU("2");
		Assert.assertThat(cu1.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU existingTU = testsBase.retrieveParent(cu1);
		Assert.assertThat(existingTU.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		data.helper.load(producer, data.helper.pSalad, new BigDecimal("3"), data.helper.uomKg);
		final I_M_HU cu2 = producer.getCreatedHUs().get(0);
		handlingUnitsBL.setHUStatus(data.helper.getHUContext(), cu2, X_M_HU.HUSTATUS_Active);
		save(cu2);

		// invoke the method under test.
		HUTransformService.newInstance(data.helper.getHUContext())
				.cuToExistingTU(cu2, new BigDecimal("1.6"), existingTU);

		// secondCU is still there, with the remaining 1.4kg
		final Node secondCUXML = HUXmlConverter.toXml(cu2);
		Assert.assertThat(secondCUXML, hasXPath("HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/@HUStatus", is("A")));
		Assert.assertThat(secondCUXML, hasXPath("HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty", is("1.400")));

		final Node existingLUXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		Assert.assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));
		Assert.assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		Assert.assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));
		Assert.assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		Assert.assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));
	}

	/**
	 * Similar to {@link #test_CUToExistingTU_create_mixed_TU_partialCU()}, but move all the salad
	 */
	@Test
	public void test_CUToExistingTU_create_mixed_TU_completeCU()
	{
		final BigDecimal four = new BigDecimal("4");

		final I_M_HU cu1 = testsBase.mkRealCUWithTUandQtyCU("5");
		final I_M_HU tuWithMixedCUs = testsBase.retrieveParent(cu1);

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// create a standalone-CU
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		data.helper.load(producer, data.helper.pSalad, four, data.helper.uomKg);

		final I_M_HU cu2 = producer.getCreatedHUs().get(0);

		HUTransformService.newInstance(data.helper.getHUContext())
				.cuToExistingTU(cu2, four, tuWithMixedCUs);

		// data.helper.commitAndDumpHU(tuWithMixedCUs);
		final Node tuWithMixedCUsXML = HUXmlConverter.toXml(tuWithMixedCUs);
		Assert.assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));
		Assert.assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));

		Assert.assertThat(tuWithMixedCUsXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "])", is("1")));
		Assert.assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));

		Assert.assertThat(tuWithMixedCUsXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "])", is("1")));
		Assert.assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));
	}

	/**
	 * Verifies the splitting off an aggregate HU with a non-int storage value.
	 * If this test shows problems, also see {@link LUTUProducerDestinationLoadTests#testAggregateSingleLUFullyLoaded_non_int()}.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1237, but this even worked before the issue came up.
	 *
	 */
	@Test
	public void testAggregateSingleLUFullyLoaded_non_int()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// create a special hu pi item that says "one LU can hold 20 IFCOs"
		final I_M_HU_PI_Item piLU_Item_20_IFCO = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("20"));

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(piLU_Item_20_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.addCUPerTU(data.helper.pTomato, new BigDecimal("5.47"), data.helper.uomKg); // set the TU capacity to be 109.4 / 20

		// load the tomatoes into HUs
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("109.4"), data.helper.uomKg);
		Assert.assertThat(lutuProducer.getCreatedHUs().size(), is(1));
		final I_M_HU createdLU = lutuProducer.getCreatedHUs().get(0);

		final List<I_M_HU> aggregateTUs = testsBase.retrieveIncludedHUs(createdLU);
		Assert.assertThat(aggregateTUs.size(), is(1));
		final I_M_HU aggregateTU = aggregateTUs.get(0);
		Assert.assertThat(handlingUnitsBL.isAggregateHU(aggregateTU), is(true));

		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewTUs(aggregateTU, BigDecimal.ONE);
		Assert.assertThat(newTUs.size(), is(1));
		final I_M_HU newTU = newTUs.get(0);
		// data.helper.commitAndDumpHU(newTU);

		final Node newTuXML = HUXmlConverter.toXml(newTU);
		Assert.assertThat(newTuXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.470")));

		// the original aggregate HU is still intact, it just represents one less TU

		final Node createdLuXML = HUXmlConverter.toXml(createdLU);

		// there shall still be no "real" HU
		Assert.assertThat(createdLuXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("0")));

		// the aggregate HU shall contain the full remaining quantity and represent 19 IFCOs; 5.47 x 19 = 103,93
		Assert.assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("103.930")));

		Assert.assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)", is("19")));
		Assert.assertThat(createdLuXML, hasXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("103.930")));
	}

	@Test
	public void husToNewTUs_source_is_aggregated_aggregate_never()
	{

		final I_M_HU aggregateHU1 = testsBase.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("16", 8); // 2 TUs
		final I_M_HU aggregateHU2 = testsBase.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("24", 8); // 3 TUs

		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder()
				.sourceHU(aggregateHU1)
				.sourceHU(aggregateHU2)
				.qtyTU(3).build();

		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final List<I_M_HU> extractedTUs = HUTransformService.newInstance(data.helper.getHUContext()).husToNewTUs(request);

		assertThat(extractedTUs).hasSize(3);
		assertThat(extractQty(extractedTUs.get(0))).isEqualByComparingTo("8");
		assertThat(extractQty(extractedTUs.get(1))).isEqualByComparingTo("8");
		assertThat(extractQty(extractedTUs.get(2))).isEqualByComparingTo("8");

		refresh(aggregateHU1);
		refresh(aggregateHU2);
		assertThat(extractQty(aggregateHU1)).isZero();
		assertThat(extractQty(aggregateHU2)).isEqualByComparingTo("16");

		assertThat(aggregateHU1.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Destroyed);
		assertThat(aggregateHU2.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);
	}

	@Test
	public void husToNewTUs_mix_homogenouse_aggregate_and_non_aggregate()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_M_HU aggregateHU1 = testsBase.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("16", 8); // 2 TUs
		final I_M_HU realTU2 = handlingUnitsDAO.retrieveParent(testsBase.mkRealCUWithTUandQtyCU("6")); // 1 TUs
		final I_M_HU aggregateHU3 = testsBase.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("16", 8); // 2 TUs

		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder()
				.sourceHU(aggregateHU1)
				.sourceHU(realTU2)
				.sourceHU(aggregateHU3)
				.qtyTU(4).build();

		final List<I_M_HU> extractedTUs = HUTransformService.newInstance(data.helper.getHUContext()).husToNewTUs(request);
		assertThat(extractedTUs).hasSize(4);
		assertThat(extractedTUs).allSatisfy(tu -> {
			assertThat(handlingUnitsBL.isAggregateHU(tu)).isFalse();
		});

		assertThat(extractedTUs).as("realTU2 was just \"moved\" into the result set")
				.anySatisfy(tu -> assertThat(tu.getM_HU_ID()).as("tu.getM_HU_ID()=%s", realTU2.getM_HU_ID()).isEqualTo(realTU2.getM_HU_ID()));
		refresh(aggregateHU1);
		refresh(realTU2);
		refresh(aggregateHU3);

		assertThat(extractQty(aggregateHU1)).isZero();
		assertThat(extractQty(realTU2)).isEqualByComparingTo("6");
		assertThat(extractQty(aggregateHU3)).isEqualByComparingTo("8");

		assertThat(aggregateHU1.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Destroyed);
		assertThat(realTU2.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(aggregateHU3.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);
	}

	@Test
	public void huToNewTUs_source_is_aggregated()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU aggregateHU = testsBase.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("24", 8);

		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder().sourceHU(aggregateHU).qtyTU(2).build();
		final List<I_M_HU> extractedTUs = HUTransformService.newInstance(data.helper.getHUContext()).husToNewTUs(request);

		assertThat(extractedTUs).hasSize(2);
		assertThat(extractedTUs).allSatisfy(tu -> {
			assertThat(handlingUnitsBL.isAggregateHU(tu)).isFalse();
		});
	}

	@Theory
	public void huToNewTUs_source_is_not_aggregated(@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		// setup
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU realTu1 = handlingUnitsDAO.retrieveParent(testsBase.mkRealCUWithTUandQtyCU("8"));
		final I_M_HU realTu2 = handlingUnitsDAO.retrieveParent(testsBase.mkRealCUWithTUandQtyCU("8"));
		final I_M_HU realTu3 = handlingUnitsDAO.retrieveParent(testsBase.mkRealCUWithTUandQtyCU("8"));

		assertThat(ImmutableList.of(realTu1, realTu2, realTu3))
				.allSatisfy(tu -> assertThat(tu.isHUPlanningReceiptOwnerPM()).isFalse());

		final List<I_M_HU> newLUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewLUs(realTu1,
						new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
						data.piLU_Item_IFCO,
						isOwnPackingMaterials);

		assertThat(newLUs).hasSize(1);
		final I_M_HU luHU = newLUs.get(0);
		assertThat(luHU.isHUPlanningReceiptOwnerPM()).isEqualTo(isOwnPackingMaterials); // guard

		HUTransformService.newInstance(data.helper.getHUContext()).tuToExistingLU(realTu2, BigDecimal.ONE, luHU);
		refresh(luHU);
		HUTransformService.newInstance(data.helper.getHUContext()).tuToExistingLU(realTu3, BigDecimal.ONE, luHU);
		refresh(luHU);

		// invoke method under test
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder().sourceHU(luHU).qtyTU(2).build();
		final List<I_M_HU> extractedTUs = HUTransformService.newInstance(data.helper.getHUContext()).husToNewTUs(request);

		assertThat(extractedTUs).hasSize(2);
		assertThat(extractedTUs).allSatisfy(tu -> {

			assertThat(handlingUnitsBL.isAggregateHU(tu))
					.as("Extracted TU shall not be aggregate; tu=%s", tu)
					.isFalse();

			assertThat(tu.isHUPlanningReceiptOwnerPM())
					.as("Extracted TU shall have given the orginal TU's isOwnPackingMaterials=false; tu=%s", isOwnPackingMaterials, tu)
					.isFalse();
		});
	}

	@Test
	public void doBeforeDestroyed()
	{
		final I_M_HU aggregateHU1 = testsBase.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("16", 8); // 2 TUs

		final Map<Integer, Integer> huId2listenerFiredCounters = new HashMap<>();
		final Consumer<I_M_HU> consumer = hu -> {
			huId2listenerFiredCounters.compute(hu.getM_HU_ID(), (k, v) -> (v == null) ? 1 : v + 1);
		};

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final IMutableHUContext mutableHUContext = data.helper.getHUContext().copyAsMutable();
		mutableHUContext.addEmptyHUListener(EmptyHUListener.doBeforeDestroyed(consumer, "just increments a counter"));

		HUTransformService.builderForHUcontext().huContext(mutableHUContext).build()
				.husToNewTUs(HUsToNewTUsRequest.forSourceHuAndQty(aggregateHU1, 2));
		refresh(aggregateHU1);
		assertThat(aggregateHU1.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Destroyed);
		assertThat(huId2listenerFiredCounters.get(aggregateHU1.getM_HU_ID())).isEqualTo(1);
	}

	private BigDecimal extractQty(final I_M_HU hu)
	{
		return handlingUnitsBL.getStorageFactory().getStorage(hu).getQtyForProductStorages();
	}
}
