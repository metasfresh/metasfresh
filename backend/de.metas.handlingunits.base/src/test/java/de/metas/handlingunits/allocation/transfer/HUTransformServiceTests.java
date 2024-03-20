package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.ProductActivityProvider;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationLoadTests;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationRepository;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.product.IProductActivityProvider;
import de.metas.printing.DoNothingMassPrintingService;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static de.metas.handlingunits.HUAssertions.assertThat;
import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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
	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal FOUR = new BigDecimal("4");
	private static final BigDecimal FIVE = new BigDecimal("5");
	private static final BigDecimal ELEVEN = new BigDecimal("11");

	@Rule
	public AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	/**
	 * This dataPoint shall enable us to test with both values of {@code isOwnPackingMaterials}.
	 */
	@DataPoints("isOwnPackingMaterials")
	public static boolean[] isOwnPackingMaterials = { true, false };

	private IHandlingUnitsBL handlingUnitsBL;
	private IHUStatusBL huStatusBL;
	private HUTransformTestsBase testsBase;

	private HUTransformService huTransformService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		huStatusBL = Services.get(IHUStatusBL.class);
		testsBase = new HUTransformTestsBase();

		huTransformService = HUTransformService.newInstance(testsBase.getData().helper.getHUContext());

		Services.registerService(IProductActivityProvider.class, ProductActivityProvider.createInstanceForUnitTesting());

		final QRCodeConfigurationService qrCodeConfigurationService = new QRCodeConfigurationService(new QRCodeConfigurationRepository());
		SpringContextHolder.registerJUnitBean(qrCodeConfigurationService);
		SpringContextHolder.registerJUnitBean(new HUQRCodesService(new HUQRCodesRepository(), new GlobalQRCodeService(DoNothingMassPrintingService.instance), qrCodeConfigurationService));
	}

	/**
	 * Tests {@link HUTransformService#cuToNewCU(I_M_HU, Quantity)}
	 * and verifies that the method does nothing if the given CU has no parent and if the given qty is equal or greater than the CU's full quantity.
	 */
	@Test
	public void testCU_To_NewCU_MaxValueNoParent()
	{
		testsBase.testCU_To_NewCU_MaxValueNoParent_DoIt();
	}

	/**
	 * Tests {@link HUTransformService#cuToNewCU(I_M_HU, Quantity)}
	 * and verifies that the method does nothing if the given CU has no parent and if the given qty is equal or greater than the CU's full quantity.
	 */
	@Test
	public void testCU_To_NewCU_ExceedMaxValueNoParent()
	{
		testsBase.testCU_To_NewCU_ExceedMaxValueNoParent_DoIt();
	}

	/**
	 * Tests {@link HUTransformService#cuToNewCU(I_M_HU, Quantity)}
	 * and verifies that the method removes the given CU from its parent, if it has a parent and if the given qty is equal or greater than the CU's full quantity.
	 */
	@Test
	public void testCU_To_NewCU_MaxValueParent()
	{
		testsBase.testCU_To_NewCU_MaxValueParent_DoIt();
	}

	/**
	 * Tests {@link HUTransformService#cuToNewCU(I_M_HU, Quantity)}  by splitting one tomato onto a new CU.
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
		final I_M_HU cuToSplit = testsBase.getData().mkRealStandAloneCuWithCuQty("40");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		data.disableHUPackingMaterialsCollector("when the new TU is created, the system would want to generate a packing material movement");

		// invoke the method under test
		final List<I_M_HU> newTUs = huTransformService
				.cuToNewTUs(
						cuToSplit,
						Quantity.of(ONE, data.helper.uomKg),
						data.piTU_Item_Product_Bag_8KgTomatoes,
						isOwnPackingMaterials);

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
	 * Tests {@link HUTransformService#cuToNewTUs(I_M_HU, Quantity, I_M_HU_PI_Item_Product, boolean)}
	 * by creating an <b>aggregate</b> HU with a qty of 80 (representing two IFCOs) and then splitting one kg.
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
		final I_M_HU cuToSplit = testsBase.getData().mkRealStandAloneCuWithCuQty("2");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		data.disableHUPackingMaterialsCollector("when 'cuToSplit' is moved to the new TU, then its old parents are destroyed");

		// invoke the method under test
		final List<I_M_HU> newTUs = huTransformService
				.cuToNewTUs(
						cuToSplit,
						Quantity.of(new BigDecimal("2"), data.helper.uomKg),
						data.piTU_Item_Product_IFCO_40KgTomatoes,
						isOwnPackingMaterials);

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
	 * Run {@link HUTransformService#cuToNewTUs(I_M_HU, Quantity, I_M_HU_PI_Item_Product, boolean)}
	 * by splitting a CU-quantity of 40 onto new TUs with a CU-capacity of 8 each.
	 */
	@Theory
	public void testRealCU_To_NewTUs_40Tomatoes_TU_Capacity_8(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = testsBase.getData().mkRealStandAloneCuWithCuQty("40");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		data.disableHUPackingMaterialsCollector("when the new TUs are created, the system would want to generate a packing material movemen");

		// invoke the method under test
		final List<I_M_HU> newTUs = huTransformService
				.cuToNewTUs(
						cuToSplit,
						Quantity.of(new BigDecimal("40"), data.helper.uomKg),
						data.piTU_Item_Product_Bag_8KgTomatoes,
						isOwnPackingMaterials);

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
		final I_M_HU cuHU = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final IMutableHUContext localHuContextCopy = data.helper.getHUContext().copyAsMutable();
		localHuContextCopy.getHUPackingMaterialsCollector().disable(); // the system would other try to create a material movement for the new TU.

		final List<I_M_HU> existingTUs = HUTransformService.newInstance(localHuContextCopy)
				.cuToNewTUs(cuHU, Quantity.of(new BigDecimal("20"), data.helper.uomKg), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
		Assert.assertThat(existingTUs.size(), is(1));
		final I_M_HU existingTU = existingTUs.get(0);
		Assert.assertThat(handlingUnitsBL.isAggregateHU(existingTU), is(false));

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingTUBeforeXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		Assert.assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		Assert.assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])", is("1")));

		// prepare the CU to split
		final I_M_HU cuToSplit = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

		// invoke the method under test
		huTransformService
				.cuToExistingTU(cuToSplit, Quantity.of(new BigDecimal("20"), data.helper.uomKg), existingTU);

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
	 * Like {@link #testRealCU_To_ExistingRealTU()} , but the existing TU already contains 30kg (with a capacity of 40kg). Then add another 20kg. shall work.
	 */
	@Test
	public void testRealCU_To_ExistingRealTU_overfill()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU existingTU;
		{
			final I_M_HU cuHU = testsBase.getData().mkRealStandAloneCuWithCuQty("30");

			final IMutableHUContext localHuContextCopy = data.helper.getHUContext().copyAsMutable();
			localHuContextCopy.getHUPackingMaterialsCollector().disable(); // the system would other try to create a material movement for the new TU.
			final List<I_M_HU> existingTUs = HUTransformService.newInstance(localHuContextCopy)
					.cuToNewTUs(cuHU, Quantity.of(new BigDecimal("30"), data.helper.uomKg), data.piTU_Item_Product_IFCO_40KgTomatoes, false);

			Assert.assertThat(existingTUs.size(), is(1));
			existingTU = existingTUs.get(0);
			Assert.assertThat(handlingUnitsBL.isAggregateHU(existingTU), is(false));

			final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
			Assert.assertThat(existingTUBeforeXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
			Assert.assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
			Assert.assertThat(existingTUBeforeXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='30.000' and @C_UOM_Name='Kg'])", is("1")));
		}
		// prepare the CU to split
		final I_M_HU cuToSplit = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

		// invoke the method under test
		huTransformService
				.cuToExistingTU(cuToSplit, Quantity.of(new BigDecimal("20"), data.helper.uomKg), existingTU);

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
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU existingTU = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingTUBeforeXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(existingTUBeforeXML, hasXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("80.000")));

		final I_M_HU cuToSplit = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

		testsBase.getData().disableHUPackingMaterialsCollector("cuToSplit will actually not be added to existingTU, but instead a new TU will be created");

		// invoke the method under test
		HUTransformService.newInstance(testsBase.getData().helper.getHUContext())
				.cuToExistingTU(
						cuToSplit,
						Quantity.of(new BigDecimal("20"), data.helper.uomKg),
						existingTU);

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
	 * Verifies that if {@link HUTransformService#tuToNewTUs(I_M_HU, BigDecimal)}  is run with the source TU's full qty or more and since .
	 */
	@Test
	public void testAggregateTU_To_NewTUs_MaxValueParent()
	{
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");
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
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");

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
		final I_M_HU tu = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("130", 13);

		// Actually take out 2 TUs
		final List<I_M_HU> newTUs = huTransformService
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
	 * Verifies the nothing is changed if {@link HUTransformService#tuToNewTUs(I_M_HU, BigDecimal)}  is run with the source TU's full qty or more.
	 */
	@Test
	public void testRealTU_To_NewTUs_MaxValue()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// prepare the existing TU
		// just use the testee as a tool here, to create our "real" TU.
		final I_M_HU cuHU = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

		testsBase.getData().disableHUPackingMaterialsCollector("when creating new TUs, the system will try to make material movements");

		final List<I_M_HU> tusToSplit = huTransformService
				.cuToNewTUs(
						cuHU,
						Quantity.of(new BigDecimal("20"), data.helper.uomKg),
						data.piTU_Item_Product_IFCO_40KgTomatoes, false);

		Assert.assertThat(tusToSplit.size(), is(1));
		final I_M_HU tuToSplit = tusToSplit.get(0);
		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newTUs = huTransformService
				.tuToNewTUs(tuToSplit,
							new BigDecimal("4")); // tuQty=4; we only have 1 TU in the source which only holds 20kg
		assertThat(newTUs).containsExactly(tuToSplit);
	}

	/**
	 * Similar to {@link #testRealTU_To_NewTUs_MaxValue()}, but here the source TU is on a pallet.<br>
	 * So this time, it shall be taken off the pallet.
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
			final I_M_HU cuHU = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

			final IMutableHUContext huContextCopy = data.helper.getHUContext().copyAsMutable();
			huContextCopy.getHUPackingMaterialsCollector().disable(); // cuHU's parent will be destroyed

			final List<I_M_HU> tusToSplit = HUTransformService
					.newInstance(huContextCopy)
					.cuToNewTUs(cuHU, Quantity.of(new BigDecimal("20"), data.helper.uomKg), data.piTU_Item_Product_IFCO_40KgTomatoes, false);
			Assert.assertThat(tusToSplit.size(), is(1));
			tuToSplit = tusToSplit.get(0);
			Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

			data.disableHUPackingMaterialsCollector("when the new LU is created, the system would want to generate a packing material movement");

			final List<I_M_HU> lus = huTransformService
					.tuToNewLUs(tuToSplit, ONE, data.piLU_Item_IFCO, isOwnPackingMaterials);
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

		// when tuToSplit is added, then its old parent-LU is destroyed, but that's not part of this test's scope
		data.helper.getHUContext()
				.getHUPackingMaterialsCollector()
				.disable();

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService
				.newInstance(data.helper.getHUContext())
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
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");
		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(true)); // guard; make sure it's aggregate
		Assert.assertThat(tuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// when tuToSplit is added, then its old parent-LU is destroyed, but that's not part of this test's scope
		data.helper.getHUContext()
				.getHUPackingMaterialsCollector()
				.disable();

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService
				.newInstance(data.helper.getHUContext())
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
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("240"); // 6 TUs
		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(true)); // guard; make sure it's aggregate
		Assert.assertThat(tuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newLUs = huTransformService
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
		final I_M_HU cuHU = testsBase.getData().mkRealCUWithTUandQtyCU("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU tuToSplit = testsBase.retrieveParent(cuHU);

		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		data.disableHUPackingMaterialsCollector("when the new LU is created, the system would want to generate a packing material movement");

		// invoke the method under test
		final List<I_M_HU> newLUs = huTransformService
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
		final I_M_HU cuHU = testsBase.getData().mkRealCUWithTUandQtyCU("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU tuToSplit = cuHU.getM_HU_Item_Parent().getM_HU();
		Assert.assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		data.disableHUPackingMaterialsCollector("when the new LUs are created, the system would want to generate packing material movemens");

		// prepare tuToSplit onto a LU. This assumes that #testRealStandaloneTU_To_NewLU was green
		final List<I_M_HU> oldLUs = HUTransformService
				.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit, ONE, data.piLU_Item_IFCO, isOwnPackingMaterials);
		Assert.assertThat(oldLUs.size(), is(1)); // guard
		Assert.assertThat(tuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(oldLUs.get(0).getM_HU_ID()));
		Assert.assertThat(oldLUs.get(0).getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		// when tuToSplit is added, then its old parent-LU is destroyed, but that's not part of this test's scope
		data.helper.getHUContext()
				.getHUPackingMaterialsCollector()
				.disable();

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService
				.newInstance(data.helper.getHUContext())
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
			final I_M_HU cuHU = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

			final IMutableHUContext localHuContextCopy = data.helper.getHUContext().copyAsMutable();
			localHuContextCopy.getHUPackingMaterialsCollector().disable(); // the system would other try to create a material movement for the new TU.

			final List<I_M_HU> existingTUs = HUTransformService.newInstance(localHuContextCopy)
					.cuToNewTUs(cuHU,
								Quantity.of(new BigDecimal("20"), data.helper.uomKg),
								data.piTU_Item_Product_IFCO_40KgTomatoes,
								false);
			Assert.assertThat(existingTUs.size(), is(1));
			final I_M_HU exitingTu = existingTUs.get(0);
			Assert.assertThat(handlingUnitsBL.isAggregateHU(exitingTu), is(false)); // guard; make sure it's "real"

			final List<I_M_HU> existingLUs = HUTransformService.newInstance(localHuContextCopy)
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
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");

		// invoke the method under test
		huTransformService
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
		final IMutableHUContext huContext = data.helper.getHUContext();
		{
			final I_M_HU exitingTu = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");
			Assert.assertThat(handlingUnitsBL.isAggregateHU(exitingTu), is(true)); // guard; make sure it's "aggregate"

			final IMutableHUContext localCopy = huContext.copyAsMutable();
			localCopy.getHUPackingMaterialsCollector().disable().errorIfAnyHuIsAdded(); // adding the new (aggregate) TU to a new LU will destroy the aggregate's current parent-LU, and we don't want to set up

			final List<I_M_HU> existingLUs = HUTransformService.newInstance(localCopy)
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
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");

		// invoke the method under test
		HUTransformService.newInstance(huContext)
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
	 * @implSpec task https://github.com/metasfresh/metasfresh-webui/issues/237 Transform CU on existing TU not working
	 */
	@Test
	public void test_CUToExistingTU_create_mixed_TU_partialCU()
	{
		final I_M_HU cu1 = testsBase.getData().mkRealCUWithTUandQtyCU("2");
		Assert.assertThat(cu1.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU existingTU = testsBase.retrieveParent(cu1);
		Assert.assertThat(existingTU.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal("3"), data.helper.uomKg);
		final I_M_HU cu2 = producer.getCreatedHUs().get(0);
		huStatusBL.setHUStatus(data.helper.getHUContext(), cu2, X_M_HU.HUSTATUS_Active);
		save(cu2);

		// invoke the method under test.
		huTransformService
				.cuToExistingTU(cu2, Quantity.of(new BigDecimal("1.6"), data.helper.uomKg), existingTU);

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

		final I_M_HU cu1 = testsBase.getData().mkRealCUWithTUandQtyCU("5");
		final I_M_HU tuWithMixedCUs = testsBase.retrieveParent(cu1);

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// create a standalone-CU
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		data.helper.load(producer, data.helper.pSaladProductId, four, data.helper.uomKg);

		final I_M_HU cu2 = producer.getCreatedHUs().get(0);

		huTransformService
				.cuToExistingTU(cu2, Quantity.of(four, data.helper.uomKg), tuWithMixedCUs);

		// data.helper.commitAndDumpHU(tuWithMixedCUs);
		final Node tuWithMixedCUsXML = HUXmlConverter.toXml(tuWithMixedCUs);
		Assert.assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));
		Assert.assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));

		Assert.assertThat(tuWithMixedCUsXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "])", is("1")));
		Assert.assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));

		Assert.assertThat(tuWithMixedCUsXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "])", is("1")));
		Assert.assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));
	}

	@Test
	public void husToNewCUs_mixed_source_HU()
	{
		final I_M_HU tomatoCU = testsBase.getData().mkRealCUWithTUandQtyCU("5");
		final I_M_HU tuWithMixedCUs = testsBase.retrieveParent(tomatoCU);

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// create a standalone-CU
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		data.helper.load(producer, data.helper.pSaladProductId, FOUR, data.helper.uomKg);

		final I_M_HU saladCU = producer.getCreatedHUs().get(0);

		// add the standalone-CU to get a mixed TU
		huTransformService
				.cuToExistingTU(saladCU, Quantity.of(FOUR, data.helper.uomKg), tuWithMixedCUs);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(tuWithMixedCUs)
				.productId(data.helper.pSaladProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.keepNewCUsUnderSameParent(false)
				.build();

		// invoke the method under test
		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest);

		assertThat(newCUs).hasSize(1);
		final I_M_HU newSaladCU = newCUs.get(0);

		assertThat(tuWithMixedCUs)
				.hasStorage(data.helper.pSaladProductId, Quantity.of(THREE, data.helper.uomKg))
				.hasStorage(data.helper.pTomatoProductId, Quantity.of(FIVE, data.helper.uomKg))
				.includesHU(saladCU)
				.includesHU(tomatoCU);

		assertThat(saladCU).hasStorage(data.helper.pSaladProductId, Quantity.of(THREE, data.helper.uomKg));
		assertThat(tomatoCU).hasStorage(data.helper.pTomatoProductId, Quantity.of(FIVE, data.helper.uomKg));

		assertThat(newSaladCU).isTopLevelHU();
		assertThat(newSaladCU).hasStorage(data.helper.pSaladProductId, Quantity.of(ONE, data.helper.uomKg));
	}

	/**
	 * Verifies the splitting off an aggregate HU with a non-int storage value.
	 * If this test shows problems, also see {@link LUTUProducerDestinationLoadTests#testAggregateSingleLUFullyLoaded_non_int()}.
	 *
	 * @implNote task https://github.com/metasfresh/metasfresh/issues/1237, but this even worked before the issue came up.
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
		lutuProducer.addCUPerTU(data.helper.pTomatoProductId, new BigDecimal("5.47"), data.helper.uomKg); // set the TU capacity to be 109.4 / 20

		// load the tomatoes into HUs
		data.helper.load(lutuProducer, data.helper.pTomatoProductId, new BigDecimal("109.4"), data.helper.uomKg);
		Assert.assertThat(lutuProducer.getCreatedHUs().size(), is(1));
		final I_M_HU createdLU = lutuProducer.getCreatedHUs().get(0);

		final List<I_M_HU> aggregateTUs = testsBase.retrieveIncludedHUs(createdLU);
		Assert.assertThat(aggregateTUs.size(), is(1));
		final I_M_HU aggregateTU = aggregateTUs.get(0);
		Assert.assertThat(handlingUnitsBL.isAggregateHU(aggregateTU), is(true));

		final List<I_M_HU> newTUs = huTransformService
				.tuToNewTUs(aggregateTU, ONE);
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

		final I_M_HU aggregateHU1 = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("16", 8); // 2 TUs
		final I_M_HU aggregateHU2 = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("24", 8); // 3 TUs

		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder()
				.sourceHU(aggregateHU1)
				.sourceHU(aggregateHU2)
				.qtyTU(3).build();

		final List<I_M_HU> extractedTUs = huTransformService.husToNewTUs(request);

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
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_M_HU aggregateHU1 = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("16", 8); // 2 TUs
		final I_M_HU realTU2 = handlingUnitsDAO.retrieveParent(testsBase.getData().mkRealCUWithTUandQtyCU("6")); // 1 TUs
		final I_M_HU aggregateHU3 = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("16", 8); // 2 TUs

		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder()
				.sourceHU(aggregateHU1)
				.sourceHU(realTU2)
				.sourceHU(aggregateHU3)
				.qtyTU(4).build();

		final List<I_M_HU> extractedTUs = huTransformService.husToNewTUs(request);
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
		final I_M_HU aggregateHU = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("24", 8);

		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder().sourceHU(aggregateHU).qtyTU(2).build();
		final List<I_M_HU> extractedTUs = huTransformService.husToNewTUs(request);

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
		final I_M_HU realTu1 = handlingUnitsDAO.retrieveParent(data.mkRealCUWithTUandQtyCU("8"));
		final I_M_HU realTu2 = handlingUnitsDAO.retrieveParent(data.mkRealCUWithTUandQtyCU("8"));
		final I_M_HU realTu3 = handlingUnitsDAO.retrieveParent(data.mkRealCUWithTUandQtyCU("8"));

		assertThat(ImmutableList.of(realTu1, realTu2, realTu3))
				.allSatisfy(tu -> assertThat(tu.isHUPlanningReceiptOwnerPM()).isFalse());

		data.disableHUPackingMaterialsCollector("when the new LU is created, the system would want to generate a packing material movement");

		final List<I_M_HU> newLUs = huTransformService
				.tuToNewLUs(realTu1,
							new BigDecimal("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
							data.piLU_Item_IFCO,
							isOwnPackingMaterials);

		assertThat(newLUs).hasSize(1);
		final I_M_HU luHU = newLUs.get(0);
		assertThat(luHU.isHUPlanningReceiptOwnerPM()).isEqualTo(isOwnPackingMaterials); // guard

		huTransformService.tuToExistingLU(realTu2, ONE, luHU);
		refresh(luHU);
		huTransformService.tuToExistingLU(realTu3, ONE, luHU);
		refresh(luHU);

		// invoke method under test
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder().sourceHU(luHU).qtyTU(2).build();
		final List<I_M_HU> extractedTUs = huTransformService.husToNewTUs(request);

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
		final I_M_HU aggregateHU1 = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("16", 8); // 2 TUs

		final Map<Integer, Integer> huId2listenerFiredCounters = new HashMap<>();
		final Consumer<I_M_HU> consumer = hu -> {
			huId2listenerFiredCounters.compute(hu.getM_HU_ID(), (k, v) -> (v == null) ? 1 : v + 1);
		};

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final IMutableHUContext mutableHUContext = data.helper.getHUContext().copyAsMutable();
		mutableHUContext.addEmptyHUListener(EmptyHUListener.doBeforeDestroyed(consumer, "just increments a counter"));

		// invoke the method under test
		HUTransformService.newInstance(mutableHUContext)
				.husToNewTUs(HUsToNewTUsRequest.forSourceHuAndQty(aggregateHU1, 2));

		refresh(aggregateHU1);
		assertThat(aggregateHU1.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Destroyed);
		assertThat(huId2listenerFiredCounters.get(aggregateHU1.getM_HU_ID())).isEqualTo(1);
	}

	@Test
	public void husToNewCUs_with_aggregate_source_HU_partial_TU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuToSplit = data.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("500", 5); // represents 100 TUs with 5 CUs each

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.build();

		// invoke the method under test
		final List<I_M_HU> newCUs = huTransformService
				.husToNewCUs(husToNewCUsRequest);

		// data.helper.commitAndDumpHU(topLevelParent);

		final Node existingTUXML = HUXmlConverter.toXml(topLevelParent);
		Assert.assertThat(existingTUXML, hasXPath("count(HU-LU_Palet[@HUStatus='A'])", is("1")));

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		Assert.assertThat(existingTUXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("499.000")));
		Assert.assertThat(existingTUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("495.000")));
		Assert.assertThat(existingTUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("4.000")));

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		Assert.assertThat(newCuXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(newCuXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("1.000")));
	}

	@Test
	public void husToNewCUs_with_aggregate_source_multiple_TUs()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuToSplit = data.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("500", 5); // represents 100 TUs with 5 CUs each

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ELEVEN, data.helper.uomKg)) // i.e. two TUs and one CU
				.build();

		data.disableHUPackingMaterialsCollector("the system will need to create one dedicated new TU for the 11th tomato");

		// invoke the method under test
		final List<I_M_HU> newCUs = huTransformService
				.husToNewCUs(husToNewCUsRequest);
		// data.helper.commitAndDumpHU(topLevelParent);
		// data.helper.commitAndDumpHUs(newCUs);

		final Node existingTUXML = HUXmlConverter.toXml(topLevelParent);
		Assert.assertThat(existingTUXML, hasXPath("count(HU-LU_Palet[@HUStatus='A'])", is("1")));

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		Assert.assertThat(existingTUXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("489.000")));
		Assert.assertThat(existingTUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("485.000")));
		Assert.assertThat(existingTUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("4.000")));

		assertThat(newCUs).hasSize(3);

		final Node newCuXML = HUXmlConverter.toXml("HUs", newCUs);
		Assert.assertThat(newCuXML, hasXPath("count(HUs/HU-VirtualPI[@HUStatus='A'])", is("3")));
		Assert.assertThat(newCuXML, hasXPath("count(HUs/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='5.000' and @C_UOM_Name='Kg'])", is("2")));
		Assert.assertThat(newCuXML, hasXPath("count(HUs/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Test
	public void husToNewCUs_LU_CU_QtyLessThanPer1VirtualTU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuToSplit = data.makeLU_CU("500", 5); // represents 100 TUs with 5 CUs each

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.build();

		final List<I_M_HU> newCUs = huTransformService
				.husToNewCUs(husToNewCUsRequest);

		final Node existingTUXML = HUXmlConverter.toXml(topLevelParent);
		Assert.assertThat(existingTUXML, hasXPath("count(HU-LU_Palet[@HUStatus='A'])", is("1")));
		Assert.assertThat(existingTUXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("4.000")));

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		Assert.assertThat(newCuXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(newCuXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("1.000")));
	}

	@Test
	public void husToNewCUs_LU_CU_QtyMoreThanPer1VirtualTU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuToSplit = data.makeLU_CU("500", 5); // represents 100 TUs with 5 CUs each

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(6, data.helper.uomKg))
				.build();

		final List<I_M_HU> newCUs = huTransformService
				.husToNewCUs(husToNewCUsRequest);

		final Node existingTUXML = HUXmlConverter.toXml(topLevelParent);
		Assert.assertThat(existingTUXML, hasXPath("count(HU-LU_Palet[@HUStatus='D'])", is("1")));
		Assert.assertThat(existingTUXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("0.000")));

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		Assert.assertThat(newCuXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(newCuXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("5.000")));
	}

	@SuppressWarnings("deprecation")
	private BigDecimal extractQty(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL
				.getStorageFactory()
				.getStorage(hu)
				.getQtyForProductStorages()
				.getQty();
	}
}
