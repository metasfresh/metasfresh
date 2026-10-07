/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CacheMgt;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationLoadTests;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Node;
import org.xmlunit.assertj3.XmlAssert;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static de.metas.handlingunits.HUAssertions.assertThat;
import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@ExtendWith(AdempiereTestWatcher.class)
public class HUTransformServiceTests
{
	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal FOUR = new BigDecimal("4");
	private static final BigDecimal FIVE = new BigDecimal("5");
	private static final BigDecimal ELEVEN = new BigDecimal("11");

	private IHandlingUnitsBL handlingUnitsBL;
	private IHUStatusBL huStatusBL;
	private HUTransformTestsBase testsBase;

	private HUTransformService huTransformService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		huStatusBL = Services.get(IHUStatusBL.class);
		testsBase = new HUTransformTestsBase();

		huTransformService = HUTransformService.newInstance(testsBase.getData().helper.getHUContext());

		SpringContextHolder.registerJUnitBean(HUQRCodesService.newInstanceForUnitTesting());
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

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testRealCU_To_NewTUs_1Tomato_TU_Capacity_2(final boolean isOwnPackingMaterials)
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

		assertThat(newTUs).hasSize(1);

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("HU-VirtualPI/@HUStatus").isEqualTo("A");
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])").isEqualTo("1");

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		XmlAssert.assertThat(newTUXML).valueByXPath("HU-TU_Bag/@HUStatus").isEqualTo("A");
		XmlAssert.assertThat(newTUXML).valueByXPath("HU-TU_Bag/@HUPlanningReceiptOwnerPM").isEqualTo(Boolean.toString(isOwnPackingMaterials));
		XmlAssert.assertThat(newTUXML).valueByXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
	}

	/**
	 * Tests {@link HUTransformService#cuToNewTUs(I_M_HU, Quantity, I_M_HU_PI_Item_Product, boolean)}
	 * by creating an <b>aggregate</b> HU with a qty of 80 (representing two IFCOs) and then splitting one kg.
	 */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testAggregateCU_To_NewTUs_1Tomato(final boolean isOwnPackingMaterials)
	{
		testsBase.testAggregateCU_To_NewTUs_1Tomato_DoIt(isOwnPackingMaterials);
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testRealCU_To_NewTUs_1Tomato_TU_Capacity_40(final boolean isOwnPackingMaterials)
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

		assertThat(newTUs).hasSize(1);

		// data.helper.commitAndDumpHU(newTUs.get(0));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("count(HU-VirtualPI[@HUStatus='D'])").isEqualTo("1");
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])").isEqualTo("1");

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		XmlAssert.assertThat(newTUXML).valueByXPath("count(HU-TU_IFCO[@HUStatus='A'])").isEqualTo("1");
		XmlAssert.assertThat(newTUXML).valueByXPath("string(HU-TU_IFCO/@HUPlanningReceiptOwnerPM)").isEqualTo(Boolean.toString(isOwnPackingMaterials));
		XmlAssert.assertThat(newTUXML).valueByXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
	}

	/**
	 * Run {@link HUTransformService#cuToNewTUs(I_M_HU, Quantity, I_M_HU_PI_Item_Product, boolean)}
	 * by splitting a CU-quantity of 40 onto new TUs with a CU-capacity of 8 each.
	 */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testRealCU_To_NewTUs_40Tomatoes_TU_Capacity_8(final boolean isOwnPackingMaterials)
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

		assertThat(newTUs).hasSize(5);

		// data.helper.commitAndDumpHU(newTUs.get(0));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("count(HU-VirtualPI[@HUStatus='D'])").isEqualTo("1");
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])").isEqualTo("1");

		for (final I_M_HU newTU : newTUs)
		{
			final Node newTUXML = HUXmlConverter.toXml(newTU);

			XmlAssert.assertThat(newTUXML).valueByXPath("count(HU-TU_Bag[@HUStatus='A'])").isEqualTo("1");
			XmlAssert.assertThat(newTUXML).valueByXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)").isEqualTo(Boolean.toString(isOwnPackingMaterials));
			XmlAssert.assertThat(newTUXML).valueByXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
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
		assertThat(existingTUs).hasSize(1);
		final I_M_HU existingTU = existingTUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(existingTU)).isFalse();

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		XmlAssert.assertThat(existingTUBeforeXML).doesNotHaveXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"); // verify that there is still no parent HU
		XmlAssert.assertThat(existingTUBeforeXML).valueByXPath("count(HU-TU_IFCO[@HUStatus='A'])").isEqualTo("1");
		XmlAssert.assertThat(existingTUBeforeXML).valueByXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])").isEqualTo("1");

		// prepare the CU to split
		final I_M_HU cuToSplit = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

		// invoke the method under test
		huTransformService
				.cuToExistingTU(cuToSplit, Quantity.of(new BigDecimal("20"), data.helper.uomKg), existingTU);

		// the cu we split from is *not* destroyed but was attached to the parent TU
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_ID()).isEqualTo(existingTU.getM_HU_ID());
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])").isEqualTo("1");

		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		XmlAssert.assertThat(existingTUXML).doesNotHaveXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"); // verify that there is still no parent HU
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-TU_IFCO[@HUStatus='A'])").isEqualTo("1");
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
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

			assertThat(existingTUs).hasSize(1);
			existingTU = existingTUs.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(existingTU)).isFalse();

			final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
			XmlAssert.assertThat(existingTUBeforeXML).doesNotHaveXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"); // verify that there is still no parent HU
			XmlAssert.assertThat(existingTUBeforeXML).valueByXPath("count(HU-TU_IFCO[@HUStatus='A'])").isEqualTo("1");
			XmlAssert.assertThat(existingTUBeforeXML).valueByXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='30.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
		}
		// prepare the CU to split
		final I_M_HU cuToSplit = testsBase.getData().mkRealStandAloneCuWithCuQty("20");

		// invoke the method under test
		huTransformService
				.cuToExistingTU(cuToSplit, Quantity.of(new BigDecimal("20"), data.helper.uomKg), existingTU);

		// data.helper.commitAndDumpHU(existingTU);

		// existingTU now contains 30 + 20 = 50kg, despite its capacity is just 40kg according to the master data.
		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		XmlAssert.assertThat(existingTUXML).doesNotHaveXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"); // verify that there is still no parent HU
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-TU_IFCO[@HUStatus='A'])").isEqualTo("1");
		XmlAssert.assertThat(existingTUXML).valueByXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("50.000");

		// the cu we split from is *not* destroyed, but it was attached as-is to the existingTU
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_ID()).isEqualTo(existingTU.getM_HU_ID());
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='20.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
	}

	@Test
	public void testRealCU_To_ExistingAggregateTU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU existingTU = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");

		final Node existingTUBeforeXML = HUXmlConverter.toXml(existingTU);
		XmlAssert.assertThat(existingTUBeforeXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(existingTUBeforeXML).valueByXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("80.000");

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
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("D");
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("0.000");

		// the existing TU to which we wanted to add stuff is unchanged, but it now has a "real-TU" sibling
		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		XmlAssert.assertThat(existingTUXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(existingTUXML).valueByXPath("string(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("80.000");

		final I_M_HU fullTargetHU = existingTU.getM_HU_Item_Parent().getM_HU();
		final Node fullTargetHUXML = HUXmlConverter.toXml(fullTargetHU);
		// data.helper.commitAndDumpHU(fullTargetHU);
		XmlAssert.assertThat(fullTargetHUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_HU_ID)").isEqualTo(Integer.toString(existingTU.getM_HU_ID())); // fullTargetHU contains existingTU
		XmlAssert.assertThat(fullTargetHUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("20.000"); // fullTargetHU also contains a real IFCO with 20

	}

	/**
	 * Verifies that if {@link HUTransformService#tuToNewTUs(I_M_HU, QtyTU)}  is run with the source TU's full qty or more and since .
	 */
	@Test
	public void testAggregateTU_To_NewTUs_MaxValueParent()
	{
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");
		assertThat(testsBase.retrieveParentItem(tuToSplit)).isNotNull(); // guard: tuToSplit shall have a parent

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(testsBase.getData().helper.getHUContext())
				.tuToNewTUs(tuToSplit, QtyTU.ofString("4")) // tuQty=4; we only have 2 TUs in the source
				.getAllTURecords();
		assertThat(newTUs).hasSize(2);

		assertThat(testsBase.retrieveParentItem(newTUs.get(0))).isNull();
		assertThat(testsBase.retrieveParentItem(newTUs.get(1))).isNull();
	}

	@Test
	public void testAggregateTU_To_NewTUs()
	{
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(testsBase.getData().helper.getHUContext())
				.tuToNewTUs(tuToSplit, QtyTU.ofString("1")) // tuQty=1; we have 2 TUs in the source, so we will will only expect 1x40 to be actually loaded
				.getAllTURecords();
		assertThat(newTUs).hasSize(1);

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));
		XmlAssert.assertThat(newTUXML).doesNotHaveXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"); // verify that there is no parent HU
		XmlAssert.assertThat(newTUXML).valueByXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("40.000");
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1516
	 */
	@Test
	public void test_TakeOutTUsFromCustomLU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// Make sure the standard CU-TU capacity it's not 13Kg
		assertThat(data.piLU_Item_IFCO.getQty()).isNotEqualByComparingTo(BigDecimal.valueOf(13));

		// Create an LU with 10TUs with 13Kg each.
		final I_M_HU tu = testsBase.getData().mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("130", 13);

		// Actually take out 2 TUs
		final List<I_M_HU> newTUs = huTransformService
				.tuToNewTUs(tu, QtyTU.ofString("2"))
				.getAllTURecords();
		assertThat(newTUs).hasSize(2);

		// Make sure each TU is valid
		// * it's top level
		// * the "HUPlanningReceiptOwnerPM" flag was correctly set
		// * it's capacity it's 13Kg and not how much was defined in CU-TU
		for (final I_M_HU newTU : newTUs)
		{
			final Node newTUXML = HUXmlConverter.toXml(newTU);
			XmlAssert.assertThat(newTUXML).doesNotHaveXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"); // verify that there is no parent HU
			XmlAssert.assertThat(newTUXML).valueByXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("13.000");
		}

	}

	/**
	 * Verifies the nothing is changed if {@link HUTransformService#tuToNewTUs(I_M_HU, de.metas.handlingunits.QtyTU)}  is run with the source TU's full qty or more.
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

		assertThat(tusToSplit).hasSize(1);
		final I_M_HU tuToSplit = tusToSplit.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit)).isFalse(); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newTUs = huTransformService
				.tuToNewTUs(tuToSplit, QtyTU.ofString("4")) // tuQty=4; we only have 1 TU in the source which only holds 20kg
				.getAllTURecords();
		assertThat(newTUs).containsExactly(tuToSplit);
	}

	/**
	 * Similar to testSplitAggregateTU_To_NewTUs_MaxValue(), but here the source TU is on a pallet.<br>
	 * So this time, it shall be taken off the pallet.
	 */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testRealTU_To_NewTUs(final boolean isOwnPackingMaterials)
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
			assertThat(tusToSplit).hasSize(1);
			tuToSplit = tusToSplit.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(tuToSplit)).isFalse(); // guard; make sure it's "real"

			data.disableHUPackingMaterialsCollector("when the new LU is created, the system would want to generate a packing material movement");

			final List<I_M_HU> lus = huTransformService
					.tuToNewLUs(tuToSplit, QtyTU.ONE, data.piLU_Item_IFCO, isOwnPackingMaterials)
					.getLURecords();
			// get the LU and verify that it's properly linked with toToSplit
			{
				assertThat(lus).hasSize(1);
				lu = lus.get(0);
				final List<I_M_HU> includedHUs = testsBase.retrieveIncludedHUs(lu);
				assertThat(includedHUs).hasSize(1);
				assertThat(includedHUs.get(0).getM_HU_ID()).isEqualTo(tuToSplit.getM_HU_ID());

				assertThat(tuToSplit.getM_HU_Item_Parent().getM_HU_ID()).isEqualTo(lu.getM_HU_ID());
			}
		}

		// when tuToSplit is added, then its old parent-LU is destroyed, but that's not part of this test's scope
		data.helper.getHUContext()
				.getHUPackingMaterialsCollector()
				.disable();

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService
				.newInstance(data.helper.getHUContext())
				.tuToNewTUs(tuToSplit, QtyTU.ONE)
				.getAllTURecords();

		assertThat(newTUs).hasSize(1); // we transfer 20kg, one IFCO holds 40kg, so we expect 1 IFCO
		assertThat(newTUs.get(0).getM_HU_ID()).isEqualTo(tuToSplit.getM_HU_ID());
		assertThat(newTUs.get(0).getM_HU_Item_Parent()).isNull();

		assertThat(lu.getHUStatus()).isEqualTo("D");
		assertThat(testsBase.retrieveIncludedHUs(lu).isEmpty()).isTrue();
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testAggregateTU_To_OneNewLU(final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit)).isTrue(); // guard; make sure it's aggregate
		assertThat(tuToSplit.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// when tuToSplit is added, then its old parent-LU is destroyed, but that's not part of this test's scope
		data.helper.getHUContext()
				.getHUPackingMaterialsCollector()
				.disable();

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService
				.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						QtyTU.ofString("4"), // tuQty=4; we only have 2 TUs in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						data.piLU_Item_IFCO,
						isOwnPackingMaterials)
				.getLURecords();

		assertThat(newLUs).hasSize(1); // we transfered 80kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU to suffice

		final Node luXML = HUXmlConverter.toXml(newLUs.get(0));
		XmlAssert.assertThat(luXML).doesNotHaveXPath("HU-LU_Palet/M_HU_Item_Parent_ID"); // verify that the LU has no parent HU
		XmlAssert.assertThat(luXML).valueByXPath("HU-LU_Palet/@HUStatus").isEqualTo("A"); // gh #1975
		XmlAssert.assertThat(luXML).valueByXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM").isEqualTo(Boolean.toString(isOwnPackingMaterials));
		XmlAssert.assertThat(luXML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("80.000");

		// the pallet's included aggregate HU is 'tuToSplit'
		XmlAssert.assertThat(luXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus").isEqualTo("A"); // gh #1975
		XmlAssert.assertThat(luXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@M_HU_ID").isEqualTo(Integer.toString(tuToSplit.getM_HU_ID()));
		XmlAssert.assertThat(luXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID").isEqualTo(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()));

		XmlAssert.assertThat(luXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='2'])").isEqualTo("1");
		XmlAssert.assertThat(luXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA' and @Qty='2']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("80.000");
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testAggregateTU_To_MultipleNewLUs(final boolean isOwnPackingMaterials)
	{
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("240"); // 6 TUs
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit)).isTrue(); // guard; make sure it's aggregate
		assertThat(tuToSplit.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// invoke the method under test
		final List<I_M_HU> newLUs = huTransformService
				.tuToNewLUs(tuToSplit,
						QtyTU.ofString("6"), // tuQty=6;
						data.piLU_Item_IFCO,
						isOwnPackingMaterials)
				.getLURecords();

		assertThat(newLUs).hasSize(2); // we have 6 TUs in the source; one pallet can old 5 IFCOS, to we expect two pallets.
		{
			final Node lu1XML = HUXmlConverter.toXml(newLUs.get(0));

			XmlAssert.assertThat(lu1XML).valueByXPath("HU-LU_Palet/@HUStatus").isEqualTo("A"); // gh #1975
			XmlAssert.assertThat(lu1XML).doesNotHaveXPath("HU-LU_Palet/M_HU_Item_Parent_ID"); // verify that the LU has no parent HU
			XmlAssert.assertThat(lu1XML).valueByXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM").isEqualTo(Boolean.toString(isOwnPackingMaterials));

			XmlAssert.assertThat(lu1XML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID").isEqualTo(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()));
			XmlAssert.assertThat(lu1XML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus").isEqualTo("A"); // gh #1975
			XmlAssert.assertThat(lu1XML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("200.000");
			XmlAssert.assertThat(lu1XML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HA' and @Qty='5'])").isEqualTo("1");
			XmlAssert.assertThat(lu1XML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA' and @Qty='5']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("200.000");
		}
		{
			final Node lu2XML = HUXmlConverter.toXml(newLUs.get(1));

			XmlAssert.assertThat(lu2XML).valueByXPath("HU-LU_Palet/@HUStatus").isEqualTo("A"); // gh #1975
			XmlAssert.assertThat(lu2XML).doesNotHaveXPath("HU-LU_Palet/M_HU_Item_Parent_ID"); // verify that the LU has no parent HU
			XmlAssert.assertThat(lu2XML).valueByXPath("HU-LU_Palet/@HUPlanningReceiptOwnerPM").isEqualTo(Boolean.toString(isOwnPackingMaterials));

			XmlAssert.assertThat(lu2XML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("40.000");

			XmlAssert.assertThat(lu2XML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/@HUStatus").isEqualTo("A"); // gh #1975
		}
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testRealStandaloneTU_To_NewLU(final boolean isOwnPackingMaterials)
	{
		// prepare the existing TU
		final I_M_HU cuHU = testsBase.getData().mkRealCUWithTUandQtyCU("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU tuToSplit = testsBase.retrieveParent(cuHU);

		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit)).isFalse(); // guard; make sure it's "real"

		data.disableHUPackingMaterialsCollector("when the new LU is created, the system would want to generate a packing material movement");

		// invoke the method under test
		final List<I_M_HU> newLUs = huTransformService
				.tuToNewLUs(tuToSplit,
						QtyTU.ofString("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
						data.piLU_Item_IFCO,
						isOwnPackingMaterials)
				.getLURecords();

		assertThat(newLUs).hasSize(1); // we transfered 20kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU with one IFCO to suffice
		// data.helper.commitAndDumpHU(newLUs.get(0));
		// the LU shall contain 'tuToSplit'
		final Node newLUXML = HUXmlConverter.toXml(newLUs.get(0));
		XmlAssert.assertThat(newLUXML).doesNotHaveXPath("HU-LU_Palet/M_HU_Item_Parent_ID"); // verify that the LU has no parent HU
		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)").isEqualTo(Boolean.toString(isOwnPackingMaterials));

		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HU']/@M_HU_PI_Item_ID)").isEqualTo(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()));
		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@M_HU_ID)").isEqualTo(Integer.toString(tuToSplit.getM_HU_ID()));

		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("20.000");
		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("20.000");
	}

	/**
	 * Similar to {@link #testRealStandaloneTU_To_NewLU(boolean)}, but the source TU is moved from an old LU to a new one
	 *
	 * @param isOwnPackingMaterials
	 */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testRealTUwithLU_To_NewLU(final boolean isOwnPackingMaterials)
	{
		// prepare the existing TU
		final I_M_HU cuHU = testsBase.getData().mkRealCUWithTUandQtyCU("20");

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU tuToSplit = cuHU.getM_HU_Item_Parent().getM_HU();
		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit)).isFalse(); // guard; make sure it's "real"

		data.disableHUPackingMaterialsCollector("when the new LUs are created, the system would want to generate packing material movemens");

		// prepare tuToSplit onto a LU. This assumes that #testRealStandaloneTU_To_NewLU was green
		final List<I_M_HU> oldLUs = HUTransformService
				.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit, QtyTU.ONE, data.piLU_Item_IFCO, isOwnPackingMaterials)
				.getLURecords();
		assertThat(oldLUs).hasSize(1); // guard
		assertThat(tuToSplit.getM_HU_Item_Parent().getM_HU_ID()).isEqualTo(oldLUs.get(0).getM_HU_ID());
		assertThat(oldLUs.get(0).getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);

		// when tuToSplit is added, then its old parent-LU is destroyed, but that's not part of this test's scope
		data.helper.getHUContext()
				.getHUPackingMaterialsCollector()
				.disable();

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService
				.newInstance(data.helper.getHUContext())
				.tuToNewLUs(tuToSplit,
						QtyTU.ofString("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
						data.piLU_Item_IFCO,
						isOwnPackingMaterials)
				.getLURecords();

		// the old LU shall now be destroyed
		assertThat(oldLUs.get(0).getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Destroyed);

		assertThat(newLUs).hasSize(1); // we transfered 20kg, the target TUs are still IFCOs one IFCO still holds 40kg, one LU holds 5 IFCOS, so we expect one LU with one IFCO to suffice

		// the LU shall contain 'tuToSplit'
		final Node newLUXML = HUXmlConverter.toXml(newLUs.get(0));
		XmlAssert.assertThat(newLUXML).doesNotHaveXPath("HU-LU_Palet/M_HU_Item_Parent_ID"); // verify that the LU has no parent HU
		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/@HUPlanningReceiptOwnerPM)").isEqualTo(Boolean.toString(isOwnPackingMaterials));

		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HU']/@M_HU_PI_Item_ID)").isEqualTo(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()));
		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/@M_HU_ID)").isEqualTo(Integer.toString(tuToSplit.getM_HU_ID()));

		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("20.000");
		XmlAssert.assertThat(newLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("20.000");
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
			assertThat(existingTUs).hasSize(1);
			final I_M_HU exitingTu = existingTUs.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(exitingTu)).isFalse(); // guard; make sure it's "real"

			final List<I_M_HU> existingLUs = HUTransformService.newInstance(localHuContextCopy)
					.tuToNewLUs(exitingTu,
							QtyTU.ofString("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will will expect 1x20 to be actually loaded
							data.piLU_Item_IFCO,
							false)
					.getLURecords();
			assertThat(existingLUs).hasSize(1);
			// data.helper.commitAndDumpHU(existingLUs.get(0));
			existingLU = existingLUs.get(0); //

			// guard: the contained TU is "real"
			final Node existingLUXML = HUXmlConverter.toXml(existingLU);
			XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("20.000");
		}

		// now create the aggregation TU we are going to split
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");

		// invoke the method under test
		huTransformService
				.tuToExistingLU(tuToSplit,
						QtyTU.ofString("4"), // tuQty=4; we only have 2 TU in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						existingLU);

		// we had 20 and loaded 80, so we now expect 100
		final Node existingLUXML = HUXmlConverter.toXml(existingLU);
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("100.000");
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
			assertThat(handlingUnitsBL.isAggregateHU(exitingTu)).isTrue(); // guard; make sure it's "aggregate"

			final IMutableHUContext localCopy = huContext.copyAsMutable();
			localCopy.getHUPackingMaterialsCollector().disable().errorIfAnyHuIsAdded(); // adding the new (aggregate) TU to a new LU will destroy the aggregate's current parent-LU, and we don't want to set up

			final List<I_M_HU> existingLUs = HUTransformService.newInstance(localCopy)
					.tuToNewLUs(exitingTu,
							QtyTU.ofString("4"), // tuQty=4; we only have 2 TUs in the source which only holds 80kg, so we will will expect 2x40 to be actually loaded onto one LU
							data.piLU_Item_IFCO,
							false)
					.getLURecords();
			assertThat(existingLUs).hasSize(1);
			// data.helper.commitAndDumpHU(existingLUs.get(0));
			existingLU = existingLUs.get(0); //

			// guard: the contained TU is "real"
			final Node existingLUXML = HUXmlConverter.toXml(existingLU);
			XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("80.000"); // the LU has 80kg
			XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("80.000"); // those 80kg are contained in one aggreagate HU

			// that aggregate HU represents two IFCOS
			XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)").isEqualTo("2");
			XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID)").isEqualTo(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()));
		}

		// now create the aggregation TU we are going to split
		final I_M_HU tuToSplit = testsBase.getData().mkAggregateHUWithTotalQtyCU("80");

		// invoke the method under test
		HUTransformService.newInstance(huContext)
				.tuToExistingLU(tuToSplit,
						QtyTU.ofString("4"), // tuQty=4; we only have 2 TU in the source which hold 40kg each, so we will will expect 2x40 to be actually loaded
						existingLU);

		// we had 80 and loaded 80, so we now expect 160
		final Node existingLUXML = HUXmlConverter.toXml(existingLU);
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("160.000");
		// the original aggreagate HU is still intact
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)").isEqualTo("2");
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@M_HU_PI_Item_ID)").isEqualTo(Integer.toString(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()));

		// the aggregate 80kg TU which we moved in was de-aggregated into two 40kg TUs
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg' and @Qty='40.000'])").isEqualTo("2");
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
		final I_M_HU cu1 = testsBase.getData().mkRealCUWithTUandQtyCU("2");
		assertThat(cu1.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);

		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU existingTU = testsBase.retrieveParent(cu1);
		assertThat(existingTU.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);

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
		XmlAssert.assertThat(secondCUXML).valueByXPath("HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/@HUStatus").isEqualTo("A");
		XmlAssert.assertThat(secondCUXML).valueByXPath("HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty").isEqualTo("1.400");

		final Node existingLUXML = HUXmlConverter.toXml(existingTU);
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("2.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("1.600");
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("2.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("1.600");
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("2.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("1.600");
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
		XmlAssert.assertThat(tuWithMixedCUsXML).valueByXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("5.000");
		XmlAssert.assertThat(tuWithMixedCUsXML).valueByXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("4.000");

		XmlAssert.assertThat(tuWithMixedCUsXML).valueByXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "])").isEqualTo("1");
		XmlAssert.assertThat(tuWithMixedCUsXML).valueByXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("5.000");

		XmlAssert.assertThat(tuWithMixedCUsXML).valueByXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "])").isEqualTo("1");
		XmlAssert.assertThat(tuWithMixedCUsXML).valueByXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("4.000");
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
		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();

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
	 * @task https://github.com/metasfresh/metasfresh/issues/1237, but this even worked before the issue came up.
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
		assertThat(lutuProducer.getCreatedHUs()).hasSize(1);
		final I_M_HU createdLU = lutuProducer.getCreatedHUs().get(0);

		final List<I_M_HU> aggregateTUs = testsBase.retrieveIncludedHUs(createdLU);
		assertThat(aggregateTUs).hasSize(1);
		final I_M_HU aggregateTU = aggregateTUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(aggregateTU)).isTrue();

		final List<I_M_HU> newTUs = huTransformService
				.tuToNewTUs(aggregateTU, QtyTU.ONE)
				.getTopLevelTURecords();
		assertThat(newTUs).hasSize(1);
		final I_M_HU newTU = newTUs.get(0);
		// data.helper.commitAndDumpHU(newTU);

		final Node newTuXML = HUXmlConverter.toXml(newTU);
		XmlAssert.assertThat(newTuXML).valueByXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("5.470");

		// the original aggregate HU is still intact, it just represents one less TU

		final Node createdLuXML = HUXmlConverter.toXml(createdLU);

		// there shall still be no "real" HU
		XmlAssert.assertThat(createdLuXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU'])").isEqualTo("0");

		// the aggregate HU shall contain the full remaining quantity and represent 19 IFCOs; 5.47 x 19 = 103,93
		XmlAssert.assertThat(createdLuXML).valueByXPath("string(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("103.930");

		XmlAssert.assertThat(createdLuXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HA']/@Qty)").isEqualTo("19");
		XmlAssert.assertThat(createdLuXML).valueByXPath("string(HU-LU_Palet/Item[@ItemType='HA']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)").isEqualTo("103.930");
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

		final List<I_M_HU> extractedTUs = huTransformService.husToNewTUs(request).toHURecords();

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

		final List<I_M_HU> extractedTUs = huTransformService.husToNewTUs(request).toHURecords();
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
		final List<I_M_HU> extractedTUs = huTransformService.husToNewTUs(request).toHURecords();

		assertThat(extractedTUs).hasSize(2);
		assertThat(extractedTUs).allSatisfy(tu -> {
			assertThat(handlingUnitsBL.isAggregateHU(tu)).isFalse();
		});
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void huToNewTUs_source_is_not_aggregated(final boolean isOwnPackingMaterials)
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
						QtyTU.ofString("4"), // tuQty=4; we only have 1 TU in the source which only holds 20kg, so we will expect the TU to be moved
						data.piLU_Item_IFCO,
						isOwnPackingMaterials)
				.getLURecords();

		assertThat(newLUs).hasSize(1);
		final I_M_HU luHU = newLUs.get(0);
		assertThat(luHU.isHUPlanningReceiptOwnerPM()).isEqualTo(isOwnPackingMaterials); // guard

		huTransformService.tuToExistingLU(realTu2, QtyTU.ONE, luHU);
		refresh(luHU);
		huTransformService.tuToExistingLU(realTu3, QtyTU.ONE, luHU);
		refresh(luHU);

		// invoke method under test
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder().sourceHU(luHU).qtyTU(2).build();
		final List<I_M_HU> extractedTUs = huTransformService.husToNewTUs(request).toHURecords();

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
		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();

		// data.helper.commitAndDumpHU(topLevelParent);

		final Node existingTUXML = HUXmlConverter.toXml(topLevelParent);
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-LU_Palet[@HUStatus='A'])").isEqualTo("1");

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("499.000");
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("495.000");
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("4.000");

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		XmlAssert.assertThat(newCuXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(newCuXML).valueByXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("1.000");
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
		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();
		// data.helper.commitAndDumpHU(topLevelParent);
		// data.helper.commitAndDumpHUs(newCUs);

		final Node existingTUXML = HUXmlConverter.toXml(topLevelParent);
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-LU_Palet[@HUStatus='A'])").isEqualTo("1");

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("489.000");
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("485.000");
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("4.000");

		assertThat(newCUs).hasSize(3);

		final Node newCuXML = HUXmlConverter.toXml("HUs", newCUs);
		XmlAssert.assertThat(newCuXML).valueByXPath("count(HUs/HU-VirtualPI[@HUStatus='A'])").isEqualTo("3");
		XmlAssert.assertThat(newCuXML).valueByXPath("count(HUs/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='5.000' and @C_UOM_Name='Kg'])").isEqualTo("2");
		XmlAssert.assertThat(newCuXML).valueByXPath("count(HUs/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
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

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();

		final Node existingTUXML = HUXmlConverter.toXml(topLevelParent);
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-LU_Palet[@HUStatus='A'])").isEqualTo("1");
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("4.000");

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		XmlAssert.assertThat(newCuXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(newCuXML).valueByXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("1.000");
	}

	@Test
	public void husToNewCUs_LU_CU_QtyMoreThanPer1VirtualTU()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());

		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuToSplit = data.makeLU_CU("500", 5); // represents 100 TUs with 5 CUs each

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(6, data.helper.uomKg))
				.build();

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();

		final Node existingTUXML = HUXmlConverter.toXml(topLevelParent);
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-LU_Palet[@HUStatus='D'])").isEqualTo("1");
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("0.000");

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		XmlAssert.assertThat(newCuXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(newCuXML).valueByXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("5.000");
	}

	// ------------------------------------------------------------------------------------------------
	// Taking whole TUs out of a pallet that holds an aggregate TU, onto a new/existing pallet, via luExtractTUs(...)
	// (the route used by mobile picking). Pallet->TU items may be bound to a partner.
	// ------------------------------------------------------------------------------------------------

	private static final String ERR_LU_HAS_NO_TU_SUB_PACK_INSTR = "ERR_LU_HAS_NO_TU_SUB_PACK_INSTR";
	private static final String MSG_TU_NOT_CONFIGURED_FOR_LU = "is not configured to be stored into LU";
	private static final String MSG_LU_CANNOT_STACK_TU = "cannot stack TU";

	private static class PalletFixture
	{
		I_M_HU_PI piLU;
		I_M_HU_PI piTU;
		I_M_HU_PI_Item luItem;
	}

	private BPartnerId createBPartner(@NonNull final String name)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setValue(name);
		bpartner.setName(name);
		saveRecord(bpartner);
		return BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
	}

	/**
	 * @param itemPartner partner the Pallet to TU item is bound to; {@code null} for a generic item
	 */
	private PalletFixture createPalletFixture(@Nullable final BPartnerId itemPartner)
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final PalletFixture fixture = new PalletFixture();
		fixture.piTU = data.helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuMaterialItem = data.helper.createHU_PI_Item_Material(fixture.piTU);
		data.helper.assignProduct(tuMaterialItem, data.helper.pSaladProductId, BigDecimal.ONE, data.helper.uomEach);
		data.helper.createHU_PI_Item_PackingMaterial(fixture.piTU, data.helper.pmIFCO);

		fixture.piLU = data.helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		fixture.luItem = data.helper.createHU_PI_Item_IncludedHU(fixture.piLU, fixture.piTU, new BigDecimal("100"), itemPartner);
		data.helper.createHU_PI_Item_PackingMaterial(fixture.piLU, data.helper.pmPalet);
		return fixture;
	}

	/**
	 * Creates an active pallet that holds {@code qtyTUs} TUs of 1 piece each (as one aggregate TU).
	 *
	 * @param luPartner partner of the pallet; {@code null} for none
	 * @param tuPartner partner of the TUs (and the HUs below them); {@code null} for none
	 */
	private I_M_HU createPallet(
			@NonNull final PalletFixture fixture,
			@Nullable final BPartnerId luPartner,
			@Nullable final BPartnerId tuPartner,
			final int qtyTUs)
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final LUTUProducerDestination producer = new LUTUProducerDestination();
		producer.setLocatorId(data.defaultLocatorId);
		producer.setLUItemPI(fixture.luItem);
		producer.setLUPI(fixture.piLU);
		producer.setTUPI(fixture.piTU);
		producer.setMaxTUsPerLU(Integer.MAX_VALUE);
		producer.addCUPerTU(data.helper.pSaladProductId, BigDecimal.ONE, data.helper.uomEach);
		producer.setBPartnerId(luPartner);
		data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal(qtyTUs), data.helper.uomEach);

		final I_M_HU lu = producer.getCreatedHUs().get(0);
		huStatusBL.setHUStatus(data.helper.createMutableHUContextOutOfTransaction(), lu, X_M_HU.HUSTATUS_Active);
		lu.setC_BPartner_ID(BPartnerId.toRepoId(luPartner));
		saveRecord(lu);
		for (final I_M_HU tu : handlingUnitsDAO.retrieveIncludedHUs(lu))
		{
			tu.setC_BPartner_ID(BPartnerId.toRepoId(tuPartner));
			tu.setHUStatus(X_M_HU.HUSTATUS_Active);
			saveRecord(tu);
			for (final I_M_HU vhu : handlingUnitsDAO.retrieveIncludedHUs(tu))
			{
				vhu.setC_BPartner_ID(BPartnerId.toRepoId(tuPartner));
				vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
				saveRecord(vhu);
			}
		}
		return lu;
	}

	private static void deactivate(@NonNull final I_M_HU_PI_Item piItem)
	{
		piItem.setIsActive(false);
		saveRecord(piItem);
		// unit tests have no model-change cache invalidation; without this the cached PI items still list the item as active
		CacheMgt.get().reset();
	}

	private int countTUs(@NonNull final I_M_HU lu)
	{
		int count = 0;
		for (final I_M_HU child : Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(lu))
		{
			if (handlingUnitsBL.isTransportUnitOrAggregate(child))
			{
				count += handlingUnitsBL.getTUsCount(child).toInt();
			}
		}
		return count;
	}

	private Set<Integer> retrieveChildHUIds(@NonNull final I_M_HU lu)
	{
		return Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(lu).stream()
				.map(I_M_HU::getM_HU_ID)
				.collect(Collectors.toSet());
	}

	/**
	 * Asserts the partner of the TUs that the move added below the LU. The partner is only used to find the LU item; it must not be stamped onto them.
	 * Their partner is the one the HU builder inherits from the target LU (none if the target LU has no partner), never the partner resolved from the source.
	 */
	private void assertNewTUsHavePartner(@NonNull final I_M_HU lu, @NonNull final Set<Integer> childHUIdsBeforeMove, @Nullable final BPartnerId expectedPartnerId)
	{
		final List<I_M_HU> newTUs = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(lu).stream()
				.filter(tu -> !childHUIdsBeforeMove.contains(tu.getM_HU_ID()))
				.collect(Collectors.toList());
		Assertions.assertThat(newTUs).as("TUs added to the target LU").isNotEmpty();
		for (final I_M_HU tu : newTUs)
		{
			refresh(tu);
			Assertions.assertThat(BPartnerId.ofRepoIdOrNull(tu.getC_BPartner_ID()))
					.as("C_BPartner_ID of new TU " + tu.getM_HU_ID() + " (the fix must not stamp the resolved partner onto moved TUs)")
					.isEqualTo(expectedPartnerId);
		}
	}

	private void luExtractTUs(@NonNull final I_M_HU sourceLU, final int qtyTUs, @NonNull final HUTransformService.TargetLU targetLU)
	{
		huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ofInt(qtyTUs))
				.targetLU(targetLU)
				.build());
	}

	private void assertLuExtractTUsFails(
			@NonNull final I_M_HU sourceLU,
			final int qtyTUs,
			@NonNull final HUTransformService.TargetLU targetLU,
			@NonNull final String expectedMessagePart)
	{
		Assertions.assertThatThrownBy(() -> luExtractTUs(sourceLU, qtyTUs, targetLU))
				.hasMessageContaining(expectedMessagePart);
	}

	/**
	 * TC1 a: existing target pallet with partner P; item bound to P only; the TU itself has no partner.
	 */
	@Test
	public void luExtractTUs_toExistingLU_withPartner_itemBoundToThatPartner()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);
		final I_M_HU targetLU = createPallet(fixture, partnerP, null, 1);

		final Set<Integer> childrenBefore = retrieveChildHUIds(targetLU);
		luExtractTUs(sourceLU, 1, HUTransformService.TargetLU.ofExistingLU(targetLU));

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(2);
		assertNewTUsHavePartner(targetLU, childrenBefore, partnerP); // inherited from the target LU (partner P), as before the fix
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(4);
	}

	/**
	 * TC1 b: existing target pallet without partner -> falls back to the source pallet's partner P; item bound to P only.
	 */
	@Test
	public void luExtractTUs_toExistingLU_withoutPartner_fallsBackToSourceLUPartner()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);
		final I_M_HU targetLU = createPallet(fixture, null, null, 1);

		final Set<Integer> childrenBefore = retrieveChildHUIds(targetLU);
		luExtractTUs(sourceLU, 1, HUTransformService.TargetLU.ofExistingLU(targetLU));

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(2);
		assertNewTUsHavePartner(targetLU, childrenBefore, null); // target LU has no partner; the partner P resolved from the source LU must NOT be stamped
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(4);
	}

	/**
	 * TC1 c: new target pallet of the same PI -> source pallet's partner P; item bound to P only; the TU itself has no partner.
	 */
	@Test
	public void luExtractTUs_toNewLU_fallsBackToSourceLUPartner()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);

		final LUTUResult result = huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ONE)
				.targetLU(HUTransformService.TargetLU.ofNewLU(fixture.piLU))
				.build());

		Assertions.assertThat(result.getLURecords()).hasSize(1);
		Assertions.assertThat(countTUs(result.getLURecords().get(0))).as("TUs on new target LU").isEqualTo(1);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(4);
	}

	/**
	 * TC1 d: precedence. TU with its own partner X on a source pallet with partner Y; item bound to X; new target pallet -> the TU's own partner wins (as before).
	 */
	@Test
	public void luExtractTUs_toNewLU_tuOwnPartnerWinsOverSourceLUPartner()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerX = createBPartner("Customer");
		final BPartnerId partnerY = createBPartner("OtherCustomer");
		final PalletFixture fixture = createPalletFixture(partnerX);
		final I_M_HU sourceLU = createPallet(fixture, partnerY, partnerX, 5);

		final LUTUResult result = huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ONE)
				.targetLU(HUTransformService.TargetLU.ofNewLU(fixture.piLU))
				.build());

		Assertions.assertThat(result.getLURecords()).hasSize(1);
		Assertions.assertThat(countTUs(result.getLURecords().get(0))).as("TUs on new target LU").isEqualTo(1);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(4);
	}

	/**
	 * TC1 e: precedence. Existing target pallet with partner X; TU (and source pallet) with partner Y; item bound to X -> the target pallet's partner wins.
	 */
	@Test
	public void luExtractTUs_toExistingLU_targetLUPartnerWinsOverTUPartner()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerX = createBPartner("Customer");
		final BPartnerId partnerY = createBPartner("OtherCustomer");
		final PalletFixture fixture = createPalletFixture(partnerX);
		final I_M_HU sourceLU = createPallet(fixture, partnerY, partnerY, 5);
		final I_M_HU targetLU = createPallet(fixture, partnerX, null, 1);

		luExtractTUs(sourceLU, 1, HUTransformService.TargetLU.ofExistingLU(targetLU));

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(2);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(4);
	}

	/**
	 * TC1 f: extract ALL remaining TUs of the aggregate onto an existing pallet without partner (attach path); source pallet partner P; item bound to P only.
	 */
	@Test
	public void luExtractTUs_allTUsOfAggregate_toExistingLUWithoutPartner()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 3);
		final I_M_HU targetLU = createPallet(fixture, null, null, 1);

		luExtractTUs(sourceLU, 3, HUTransformService.TargetLU.ofExistingLU(targetLU));

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(4);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(0);
	}

	/**
	 * Pallet PI with BOTH a generic item (created first, so the lowest id) and an item bound to {@code partner}; returns the partner-bound one in {@code partnerItemHolder[0]}.
	 */
	private PalletFixture createPalletFixtureWithGenericAndPartnerItem(@NonNull final BPartnerId partner, final I_M_HU_PI_Item[] partnerItemHolder)
	{
		final PalletFixture fixture = createPalletFixture(null); // generic item, Qty 100
		partnerItemHolder[0] = testsBase.getData().helper.createHU_PI_Item_IncludedHU(fixture.piLU, fixture.piTU, BigDecimal.TEN, partner);
		return fixture;
	}

	private Set<Integer> retrieveLUItemPIItemIds(@NonNull final I_M_HU lu)
	{
		return Services.get(IHandlingUnitsDAO.class).retrieveItems(lu).stream()
				.filter(huItem -> huItem.getM_HU_PI_Item_ID() > 0)
				.map(I_M_HU_Item::getM_HU_PI_Item_ID)
				.collect(Collectors.toSet());
	}

	/**
	 * Both a generic and a partner-bound item exist, new target pallet -> the item bound to the resolved partner (the source pallet's) wins, deterministically.
	 */
	@Test
	public void luExtractTUs_toNewLU_prefersPartnerBoundItemOverGenericItem()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final I_M_HU_PI_Item[] partnerItem = new I_M_HU_PI_Item[1];
		final PalletFixture fixture = createPalletFixtureWithGenericAndPartnerItem(partnerP, partnerItem);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);

		final LUTUResult result = huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ONE)
				.targetLU(HUTransformService.TargetLU.ofNewLU(fixture.piLU))
				.build());

		Assertions.assertThat(result.getLURecords()).hasSize(1);
		Assertions.assertThat(retrieveLUItemPIItemIds(result.getLURecords().get(0)))
				.as("M_HU_PI_Item_IDs of the new LU's items")
				.contains(partnerItem[0].getM_HU_PI_Item_ID())
				.doesNotContain(fixture.luItem.getM_HU_PI_Item_ID());
	}

	/**
	 * Both a generic and a partner-bound item exist, existing target pallet without partner (aggregate branch) -> the item created on the target LU is the partner-bound one.
	 */
	@Test
	public void luExtractTUs_toExistingLUWithoutPartner_prefersPartnerBoundItemOverGenericItem()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final I_M_HU_PI_Item[] partnerItem = new I_M_HU_PI_Item[1];
		final PalletFixture fixture = createPalletFixtureWithGenericAndPartnerItem(partnerP, partnerItem);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);
		final I_M_HU targetLU = createPallet(fixture, null, null, 1);
		Assertions.assertThat(retrieveLUItemPIItemIds(targetLU)).as("precondition: target LU only has the generic item").doesNotContain(partnerItem[0].getM_HU_PI_Item_ID());

		luExtractTUs(sourceLU, 1, HUTransformService.TargetLU.ofExistingLU(targetLU));

		Assertions.assertThat(retrieveLUItemPIItemIds(targetLU))
				.as("M_HU_PI_Item_IDs of the target LU's items")
				.contains(partnerItem[0].getM_HU_PI_Item_ID());
	}

	/**
	 * TC3 a: generic item (no partner) -> existing target pallet works.
	 */
	@Test
	public void luExtractTUs_toExistingLU_genericItem()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(null);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);
		final I_M_HU targetLU = createPallet(fixture, null, null, 1);

		luExtractTUs(sourceLU, 1, HUTransformService.TargetLU.ofExistingLU(targetLU));

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(2);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(4);
	}

	/**
	 * TC3 a: generic item (no partner) -> new target pallet works.
	 */
	@Test
	public void luExtractTUs_toNewLU_genericItem()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(null);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);

		final LUTUResult result = huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ONE)
				.targetLU(HUTransformService.TargetLU.ofNewLU(fixture.piLU))
				.build());

		Assertions.assertThat(result.getLURecords()).hasSize(1);
		Assertions.assertThat(countTUs(result.getLURecords().get(0))).as("TUs on new target LU").isEqualTo(1);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(4);
	}

	/**
	 * TC3 b: the only item is bound to a different partner -> existing target pallet: error, nothing moved.
	 */
	@Test
	public void luExtractTUs_toExistingLU_itemBoundToOtherPartnerOnly_fails()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final BPartnerId otherPartner = createBPartner("OtherCustomer");
		final PalletFixture fixture = createPalletFixture(otherPartner);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);
		final I_M_HU targetLU = createPallet(fixture, partnerP, null, 1);

		assertLuExtractTUsFails(sourceLU, 1, HUTransformService.TargetLU.ofExistingLU(targetLU), ERR_LU_HAS_NO_TU_SUB_PACK_INSTR);

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(1);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(5);
	}

	/**
	 * TC3 b: the only item is bound to a different partner -> new target pallet: error, nothing moved.
	 */
	@Test
	public void luExtractTUs_toNewLU_itemBoundToOtherPartnerOnly_fails()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final BPartnerId otherPartner = createBPartner("OtherCustomer");
		final PalletFixture fixture = createPalletFixture(otherPartner);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);

		assertLuExtractTUsFails(sourceLU, 1, HUTransformService.TargetLU.ofNewLU(fixture.piLU), MSG_TU_NOT_CONFIGURED_FOR_LU);

		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(5);
	}

	/**
	 * TC3 b: no item at all for the TU -> existing target pallet: error, nothing moved.
	 */
	@Test
	public void luExtractTUs_toExistingLU_noItem_fails()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);
		final I_M_HU targetLU = createPallet(fixture, partnerP, null, 1);
		deactivate(fixture.luItem); // the pallet has no (active) item for the TU anymore

		assertLuExtractTUsFails(sourceLU, 1, HUTransformService.TargetLU.ofExistingLU(targetLU), ERR_LU_HAS_NO_TU_SUB_PACK_INSTR);

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(1);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(5);
	}

	/**
	 * TC3 b: no item at all for the TU -> new target pallet: error, nothing moved.
	 */
	@Test
	public void luExtractTUs_toNewLU_noItem_fails()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 5);
		deactivate(fixture.luItem); // the pallet has no (active) item for the TU anymore

		assertLuExtractTUsFails(sourceLU, 1, HUTransformService.TargetLU.ofNewLU(fixture.piLU), MSG_TU_NOT_CONFIGURED_FOR_LU);

		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(5);
	}

	/**
	 * Standalone TU (not on any pallet) moved onto an existing pallet: the partner resolves only from the target pallet (none here),
	 * so a generic item is found and the move works as before.
	 */
	@Test
	public void tuToExistingLU_standaloneTU_genericItem_works()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final PalletFixture fixture = createPalletFixture(null);
		final I_M_HU targetLU = createPallet(fixture, null, null, 1);
		final I_M_HU standaloneTU = createStandaloneTU(fixture);

		huTransformService.tuToExistingLU(standaloneTU, QtyTU.ONE, targetLU);

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(2);
		Assertions.assertThat(handlingUnitsBL.getTopLevelParent(standaloneTU).getM_HU_ID()).isEqualTo(targetLU.getM_HU_ID());
	}

	/**
	 * Standalone TU (no pallet to take a partner from) onto an existing pallet without partner, where the only item is partner-bound: unchanged error, nothing moved.
	 */
	@Test
	public void tuToExistingLU_standaloneTU_itemBoundToPartnerOnly_fails()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU targetLU = createPallet(fixture, null, null, 1);
		final I_M_HU standaloneTU = createStandaloneTU(fixture);

		Assertions.assertThatThrownBy(() -> huTransformService.tuToExistingLU(standaloneTU, QtyTU.ONE, targetLU))
				.hasMessageContaining(MSG_LU_CANNOT_STACK_TU);

		Assertions.assertThat(countTUs(targetLU)).as("TUs on target LU").isEqualTo(1);
	}

	/**
	 * A real (non-aggregate) TU that sits on a pallet with partner P (TU without partner; item bound to P only), moved whole onto a NEW pallet.
	 */
	@Test
	public void tuToNewLU_realTUOnPartnerPallet_itemBoundToPartnerOnly_works()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 1);
		final I_M_HU realTU = createStandaloneTU(fixture);
		huTransformService.tuToExistingLU(realTU, QtyTU.ONE, sourceLU); // the pallet partner P is used to find the item
		Assertions.assertThat(handlingUnitsBL.isAggregateHU(realTU)).as("guard: real TU").isFalse();
		Assertions.assertThat(handlingUnitsBL.getTopLevelParent(realTU).getM_HU_ID()).as("guard: TU on source pallet").isEqualTo(sourceLU.getM_HU_ID());
		Assertions.assertThat(countTUs(sourceLU)).as("guard: TUs on source LU").isEqualTo(2);

		final LUTUResult result = huTransformService.tuToNewLU(realTU, QtyTU.ONE, HuPackingInstructionsId.ofRepoId(fixture.piLU.getM_HU_PI_ID()));

		Assertions.assertThat(result.getLURecords()).hasSize(1);
		final I_M_HU newLU = result.getLURecords().get(0);
		Assertions.assertThat(handlingUnitsBL.getTopLevelParent(realTU).getM_HU_ID()).as("TU is under the new LU").isEqualTo(newLU.getM_HU_ID());
		Assertions.assertThat(countTUs(newLU)).as("TUs on new LU").isEqualTo(1);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(1);
	}

	/**
	 * Take ALL TUs of an aggregate onto a NEW pallet (whole-move branch); source pallet partner P; item bound to P only.
	 * Calls {@code tuToNewLU} for the aggregate directly: via {@code luExtractTUs}, taking all TUs of a pallet is "take the pallet as is".
	 */
	@Test
	public void tuToNewLU_allTUsOfAggregate_itemBoundToPartnerOnly_works()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 3);
		final I_M_HU aggregateTU = CollectionUtils.singleElement(Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(sourceLU).stream()
				.filter(handlingUnitsBL::isAggregateHU)
				.collect(Collectors.toList()));

		final LUTUResult result = huTransformService.tuToNewLU(aggregateTU, QtyTU.ofInt(3), HuPackingInstructionsId.ofRepoId(fixture.piLU.getM_HU_PI_ID()));

		Assertions.assertThat(result.getLURecords()).hasSize(1);
		Assertions.assertThat(countTUs(result.getLURecords().get(0))).as("TUs on new LU").isEqualTo(3);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(0);
	}

	/**
	 * Same as {@link #tuToNewLU_realTUOnPartnerPallet_itemBoundToPartnerOnly_works()}, but through {@code luExtractTUs} (the mobile picking route).
	 */
	@Test
	public void luExtractTUs_realTUOnPartnerPallet_toNewLU_itemBoundToPartnerOnly()
	{
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());
		final BPartnerId partnerP = createBPartner("Customer");
		final PalletFixture fixture = createPalletFixture(partnerP);
		final I_M_HU sourceLU = createPallet(fixture, partnerP, null, 2);
		huTransformService.tuToExistingLU(createStandaloneTU(fixture), QtyTU.ONE, sourceLU);
		huTransformService.tuToExistingLU(createStandaloneTU(fixture), QtyTU.ONE, sourceLU);
		Assertions.assertThat(countTUs(sourceLU)).as("guard: TUs on source LU").isEqualTo(4);

		// 3 of 4 TUs (not the whole pallet): whichever TUs are taken first (aggregated or real), they must fit a new pallet
		final LUTUResult result = huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ofInt(3))
				.targetLU(HUTransformService.TargetLU.ofNewLU(fixture.piLU))
				.build());

		final int tusOnNewLUs = result.getLURecords().stream().mapToInt(this::countTUs).sum();
		Assertions.assertThat(tusOnNewLUs).as("TUs on new LU(s)").isEqualTo(3);
		Assertions.assertThat(countTUs(sourceLU)).as("TUs remaining on source LU").isEqualTo(1);
	}

	private I_M_HU createStandaloneTU(@NonNull final PalletFixture fixture)
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final LUTUProducerDestination producer = new LUTUProducerDestination();
		producer.setLocatorId(data.defaultLocatorId);
		producer.setNoLU();
		producer.setTUPI(fixture.piTU);
		producer.addCUPerTU(data.helper.pSaladProductId, BigDecimal.ONE, data.helper.uomEach);
		data.helper.load(producer, data.helper.pSaladProductId, BigDecimal.ONE, data.helper.uomEach);

		final I_M_HU tu = producer.getCreatedHUs().get(0);
		huStatusBL.setHUStatus(data.helper.createMutableHUContextOutOfTransaction(), tu, X_M_HU.HUSTATUS_Active);
		saveRecord(tu);
		Assertions.assertThat(handlingUnitsBL.getTopLevelParent(tu).getM_HU_ID()).as("guard: standalone").isEqualTo(tu.getM_HU_ID());
		return tu;
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
