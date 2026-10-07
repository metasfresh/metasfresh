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
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.util.HUTracerInstance;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.xmlunit.assertj3.XmlAssert;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.handlingunits.HUAssertions.assertThat;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link HUTransformService} that are especially geared at the sort of use which {@link HUReservationService} makes of it.
 */
public class HUTransformServiceReservationTests
{
	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal FOUR = new BigDecimal("4");
	private static final BigDecimal FIVE = new BigDecimal("5");

	private static final BigDecimal TWOHUNDRET = new BigDecimal("200");
	private IHandlingUnitsBL handlingUnitsBL;
	private HUTransformTestsBase testsBase;

	private HUTransformService huTransformService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		testsBase = new HUTransformTestsBase();

		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		huTransformService = HUTransformService.newInstance(data.helper.getHUContext());

		SpringContextHolder.registerJUnitBean(HUQRCodesService.newInstanceForUnitTesting());
	}

	/**
	 * Create CU that is inside a TU. Then split out 5, but *keep* the resulting new VHU below the same TU
	 */
	@Test
	public void split_within_CU_TU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// prepare the CU to split
		final I_M_HU cuToSplit = data.mkRealCUWithTUandQtyCU("40");

		final I_M_HU existingTU = Services.get(IHandlingUnitsDAO.class).retrieveParent(cuToSplit);

		// invoke the method under test
		HUTransformService.newInstance(data.helper.getHUContext())
				.cuToExistingTU(cuToSplit, Quantity.of(new BigDecimal("5"), data.helper.uomKg), existingTU);

		// data.helper.commitAndDumpHU(handlingUnitsBL.getTopLevelParent(cuToSplit));

		// the cu we split from is *not* destroyed but was attached to the parent TU
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_ID()).isEqualTo(existingTU.getM_HU_ID());
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(cuToSplitXML).valueByXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("35.000");

		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		XmlAssert.assertThat(existingTUXML).doesNotHaveXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"); // verify that there is still no parent HU
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-TU_IFCO[@HUStatus='A'])").isEqualTo("1");
		XmlAssert.assertThat(existingTUXML).valueByXPath("HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("40.000");

		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
		XmlAssert.assertThat(existingTUXML).valueByXPath("count(HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and  @Qty='5.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
	}

	@Test
	public void husToNewCUs_aggregate_HU_create_standalone_CU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU cuToSplit = data.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("500", 5); // represents 100 TUs with 5 CUs each

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.build();

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
	public void husToNewCUs_aggregate_HU_keep_CU_below_sourceHU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU cuToSplit = data.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("500", 5); // represents 100 TUs with 5 CUs each

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.keepNewCUsUnderSameParent(true)
				.build();

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();
		// data.helper.commitAndDumpHU(topLevelParent);

		final Node existingLUXML = HUXmlConverter.toXml(topLevelParent);
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet[@HUStatus='A'])").isEqualTo("1");

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		XmlAssert.assertThat(existingLUXML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("500.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("495.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("5.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='4.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])").isEqualTo("1");

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		XmlAssert.assertThat(newCuXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(newCuXML).valueByXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("1.000");
	}

	@Test
	public void husToNewCUs_aggregate_HU_keep_CU_below_sourceHU_2()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU cuToSplit = data.mkAggregateHUWithTotalQtyCU("200");

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);
		new HUTracerInstance().dump("topLevelParent - before husToNewCUs", topLevelParent);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.keepNewCUsUnderSameParent(true)
				.build();

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();
		// data.helper.commitAndDumpHU(topLevelParent);
		new HUTracerInstance().dump("topLevelParent - after husToNewCUs", topLevelParent);
		new HUTracerInstance().dump("newCUs", newCUs);

		final Node existingLUXML = HUXmlConverter.toXml(topLevelParent);
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet[@HUStatus='A'])").isEqualTo("1");

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		XmlAssert.assertThat(existingLUXML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("200.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("160.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("40.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])").isEqualTo("1");

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		XmlAssert.assertThat(newCuXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(newCuXML).valueByXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("1.000");
	}

	@Test
	public void husToNewCUs_aggregate_HU_keep_CU_below_sourceHU_extract_all()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		// 1 LU with 5 TUs of 40kg each
		final I_M_HU cuToSplit = data.mkAggregateHUWithTotalQtyCU("200");

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(TWOHUNDRET, data.helper.uomKg))
				.keepNewCUsUnderSameParent(true)
				.build();

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();
		// data.helper.commitAndDumpHU(topLevelParent);
		// data.helper.commitAndDumpHUs(newCUs);

		assertThat(newCUs).hasSize(5);
		final ImmutableList<Integer> distinctIds = newCUs.stream().map(I_M_HU::getM_HU_ID).distinct().collect(ImmutableList.toImmutableList());
		assertThat(distinctIds).hasSize(5);

		final Node existingLUXML = HUXmlConverter.toXml(topLevelParent);
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet[@HUStatus='A'])").isEqualTo("1");

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		XmlAssert.assertThat(existingLUXML).valueByXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("200.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HA'])").isEqualTo("1");
		XmlAssert.assertThat(existingLUXML).valueByXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage/@Qty").isEqualTo("0.000");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU'])").isEqualTo("1");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO)").isEqualTo("5");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])").isEqualTo("5");
		XmlAssert.assertThat(existingLUXML).valueByXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])").isEqualTo("5");
	}

	@Test
	public void husToNewCUs_real_CU_keep_CU_below_sourceHU()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU cuToSplit = data.mkRealCUWithTUandQtyCU("40");

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.keepNewCUsUnderSameParent(true)
				.build();

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();

		final Node tuXML = HUXmlConverter.toXml(topLevelParent);
		XmlAssert.assertThat(tuXML).valueByXPath("HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("40.000");
		XmlAssert.assertThat(tuXML).valueByXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])").isEqualTo("1");
		XmlAssert.assertThat(tuXML).valueByXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])").isEqualTo("1");

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		XmlAssert.assertThat(newCuXML).valueByXPath("string(HU-VirtualPI/@HUStatus)").isEqualTo("A");
		XmlAssert.assertThat(newCuXML).valueByXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty").isEqualTo("1.000");
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
				.keepNewCUsUnderSameParent(true)
				.build();

		// invoke the method under test
		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();

		assertThat(newCUs).hasSize(1);
		final I_M_HU newSaladCU = newCUs.get(0);

		assertThat(tuWithMixedCUs)
				.hasStorage(data.helper.pSaladProductId, Quantity.of(FOUR, data.helper.uomKg))
				.hasStorage(data.helper.pTomatoProductId, Quantity.of(FIVE, data.helper.uomKg))
				.includesHU(saladCU)
				.includesHU(newSaladCU)
				.includesHU(tomatoCU);

		assertThat(saladCU).hasStorage(data.helper.pSaladProductId, Quantity.of(THREE, data.helper.uomKg));
		assertThat(newSaladCU).hasStorage(data.helper.pSaladProductId, Quantity.of(ONE, data.helper.uomKg));
		assertThat(tomatoCU).hasStorage(data.helper.pTomatoProductId, Quantity.of(FIVE, data.helper.uomKg));
	}

	@Test
	public void husToNewCUs_different_product()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU lu = handlingUnitsBL.getTopLevelParent(data.mkAggregateHUWithTotalQtyCU("200"));

		final HUTestHelper helper = data.helper;
		assertThat(lu).hasStorage(helper.pTomatoProductId, Quantity.of(TWOHUNDRET, helper.uomKg)); // guard

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(lu)
				.productId(helper.pSaladProductId)
				.qtyCU(Quantity.of(ONE, helper.uomKg))
				.keepNewCUsUnderSameParent(true)
				.build();

		// invoke the method under test
		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest).getNewCUs();

		assertThat(newCUs).isEmpty(); // nothing was extracted, because lu does not contain any salad.
	}

	// =========================================================
	// Reservation guard tests
	// =========================================================

	/**
	 * A VHU that is directly reserved must not be transformable via cuToNewCU.
	 */
	@Test
	public void cuToNewCU_reservedVHU_shouldThrow()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuHU = data.mkRealCUWithTUandQtyCU("10");

		// mark the VHU as reserved
		cuHU.setIsReserved(true);
		InterfaceWrapperHelper.save(cuHU);

		assertThatThrownBy(() -> huTransformService.cuToNewCU(cuHU, Quantity.of(ONE, data.helper.uomKg)))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * A VHU that is directly reserved must not be moved to another TU.
	 */
	@Test
	public void cuToExistingTU_reservedVHU_shouldThrow()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuHU = data.mkRealCUWithTUandQtyCU("10");
		final I_M_HU targetTU = Services.get(IHandlingUnitsDAO.class).retrieveParent(cuHU);

		cuHU.setIsReserved(true);
		InterfaceWrapperHelper.save(cuHU);

		assertThatThrownBy(() -> huTransformService.cuToExistingTU(cuHU, Quantity.of(ONE, data.helper.uomKg), targetTU))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * husToNewCUs with CONSIDER_ALL policy must fail when a source HU contains a reserved VHU descendant.
	 */
	@Test
	public void husToNewCUs_considerAll_withReservedVHUChild_shouldThrow()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuHU = data.mkRealCUWithTUandQtyCU("10");
		final I_M_HU tuHU = Services.get(IHandlingUnitsDAO.class).retrieveParent(cuHU);

		// mark the inner VHU as reserved (TU itself is NOT reserved)
		cuHU.setIsReserved(true);
		InterfaceWrapperHelper.save(cuHU);

		final HUsToNewCUsRequest request = HUsToNewCUsRequest.builder()
				.sourceHU(tuHU)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ALL)
				.build();

		assertThatThrownBy(() -> huTransformService.husToNewCUs(request))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * husToNewCUs with CONSIDER_ONLY_NOT_RESERVED policy must NOT fail even when the source TU
	 * contains a reserved VHU child — the policy already excludes those VHUs from processing.
	 * This is the path used by {@link HUReservationService}.
	 */
	@Test
	public void husToNewCUs_considerOnlyNotReserved_withReservedVHUChild_shouldSucceed()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuHU = data.mkRealCUWithTUandQtyCU("10");
		final I_M_HU tuHU = Services.get(IHandlingUnitsDAO.class).retrieveParent(cuHU);

		// mark only the inner VHU as reserved (TU itself is NOT reserved)
		cuHU.setIsReserved(true);
		InterfaceWrapperHelper.save(cuHU);

		final HUsToNewCUsRequest request = HUsToNewCUsRequest.builder()
				.sourceHU(tuHU)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED)
				.build();

		// The reserved VHU is excluded by the policy, so there is nothing to extract → empty result, no exception
		final List<I_M_HU> result = huTransformService.husToNewCUs(request).getNewCUs();
		assertThat(result).isEmpty();
	}

	/**
	 * A TU whose VHU children are reserved (but the TU itself is NOT reserved) must be packable
	 * onto an existing LU — i.e. tuToExistingLU must NOT throw.
	 * This covers the use-case: "pack a reserved TU on an LU before creating the shipment."
	 */
	@Test
	public void tuToExistingLU_tuWithReservedVHUChild_shouldSucceed()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuHU = data.mkRealCUWithTUandQtyCU("10");
		final I_M_HU sourceTU = Services.get(IHandlingUnitsDAO.class).retrieveParent(cuHU);

		// mark only the inner VHU as reserved — the TU container itself is NOT reserved
		cuHU.setIsReserved(true);
		InterfaceWrapperHelper.save(cuHU);
		assertThat(sourceTU.isReserved()).isFalse();

		// Create a proper LU by using mkAggregateHUWithTotalQtyCU (returns an aggregate VHU inside an LU)
		final I_M_HU existingLU = handlingUnitsBL.getTopLevelParent(data.mkAggregateHUWithTotalQtyCU("200"));

		// Must NOT throw any reservation-guard exception — the TU itself is not reserved.
		huTransformService.tuToExistingLU(sourceTU, QtyTU.ONE, existingLU);
	}

	/**
	 * A TU that is itself reserved (whole-TU reservation) must NOT be packable onto an LU.
	 */
	@Test
	public void tuToExistingLU_reservedTU_shouldThrow()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuHU = data.mkRealCUWithTUandQtyCU("10");
		final I_M_HU sourceTU = Services.get(IHandlingUnitsDAO.class).retrieveParent(cuHU);
		final I_M_HU lu = data.createLU(1, 20);

		// mark the TU itself as reserved
		sourceTU.setIsReserved(true);
		InterfaceWrapperHelper.save(sourceTU);

		assertThatThrownBy(() -> huTransformService.tuToExistingLU(sourceTU, QtyTU.ONE, lu))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * When the caller <em>owns</em> the reservation it passes the reserved VHU IDs via
	 * {@code allowedReservedVhuIds}. The guard must be bypassed for those specific VHUs,
	 * so the transformation succeeds (or fails for reasons unrelated to the reservation guard).
	 * <p>
	 * Scenario: sales-order picks its own reserved CU (e.g. splits it before creating the shipment).
	 */
	@Test
	public void cuToNewCU_allowedReservedVhuIds_bypassesGuard()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuHU = data.mkRealCUWithTUandQtyCU("10");

		// mark the VHU as reserved
		cuHU.setIsReserved(true);
		InterfaceWrapperHelper.save(cuHU);

		// Build a service instance that declares this VHU as "allowed" (reservation owner)
		final de.metas.handlingunits.HuId reservedVhuId = de.metas.handlingunits.HuId.ofRepoId(cuHU.getM_HU_ID());
		final HUTransformService ownerService = HUTransformService.builderForHUcontext()
				.huContext(data.helper.getHUContext())
				.allowedReservedVhuIds(java.util.Collections.singleton(reservedVhuId))
				.build();

		ownerService.cuToNewCU(cuHU, Quantity.of(ONE, data.helper.uomKg));
	}

	// =========================================================
	// Target HU reservation guard tests
	// =========================================================

	/**
	 * Moving a CU into a TU that is itself reserved must throw.
	 */
	@Test
	public void cuToExistingTU_reservedTargetTU_shouldThrow()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU sourceCU = data.mkRealCUWithTUandQtyCU("10");

		// create a separate TU that will serve as target, then mark it as reserved
		final I_M_HU targetCU = data.mkRealCUWithTUandQtyCU("5");
		final I_M_HU targetTU = Services.get(IHandlingUnitsDAO.class).retrieveParent(targetCU);
		targetTU.setIsReserved(true);
		InterfaceWrapperHelper.save(targetTU);

		assertThatThrownBy(() -> huTransformService.cuToExistingTU(sourceCU, Quantity.of(ONE, data.helper.uomKg), targetTU))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * Packing a TU onto an LU that is itself reserved must throw.
	 */
	@Test
	public void tuToExistingLU_reservedTargetLU_shouldThrow()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU cuHU = data.mkRealCUWithTUandQtyCU("10");
		final I_M_HU sourceTU = Services.get(IHandlingUnitsDAO.class).retrieveParent(cuHU);

		final I_M_HU existingLU = handlingUnitsBL.getTopLevelParent(data.mkAggregateHUWithTotalQtyCU("200"));

		// mark the target LU as reserved
		existingLU.setIsReserved(true);
		InterfaceWrapperHelper.save(existingLU);

		assertThatThrownBy(() -> huTransformService.tuToExistingLU(sourceTU, QtyTU.ONE, existingLU))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * Adding CUs to a TU that is itself reserved must throw.
	 */
	@Test
	public void addCUsToTU_reservedTargetTU_shouldThrow()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU sourceCU = data.mkRealCUWithTUandQtyCU("10");

		// create a separate target TU and mark it as reserved
		final I_M_HU targetCU = data.mkRealCUWithTUandQtyCU("5");
		final I_M_HU targetTU = Services.get(IHandlingUnitsDAO.class).retrieveParent(targetCU);
		targetTU.setIsReserved(true);
		InterfaceWrapperHelper.save(targetTU);

		assertThatThrownBy(() -> huTransformService.addCUsToTU(ImmutableList.of(sourceCU), targetTU))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * Merging CUs into an existing CU that is reserved must throw.
	 */
	@Test
	public void cusToExistingCU_reservedTargetCU_shouldThrow()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU sourceCU = data.mkRealCUWithTUandQtyCU("10");

		final I_M_HU targetCU = data.mkRealCUWithTUandQtyCU("5");
		targetCU.setIsReserved(true);
		InterfaceWrapperHelper.save(targetCU);

		assertThatThrownBy(() -> huTransformService.cusToExistingCU(ImmutableList.of(sourceCU), targetCU))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * When the caller owns the reservation on the target TU (via {@code allowedReservedVhuIds}),
	 * the guard for the reserved target must be bypassed.
	 */
	@Test
	public void cuToExistingTU_allowedReservedTargetTU_bypassesGuard()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		final I_M_HU sourceCU = data.mkRealCUWithTUandQtyCU("10");

		final I_M_HU targetCU = data.mkRealCUWithTUandQtyCU("5");
		final I_M_HU targetTU = Services.get(IHandlingUnitsDAO.class).retrieveParent(targetCU);
		targetTU.setIsReserved(true);
		InterfaceWrapperHelper.save(targetTU);

		// Build a service that declares the target TU as allowed
		final de.metas.handlingunits.HuId targetTUId = de.metas.handlingunits.HuId.ofRepoId(targetTU.getM_HU_ID());
		final HUTransformService ownerService = HUTransformService.builderForHUcontext()
				.huContext(data.helper.getHUContext())
				.allowedReservedVhuIds(java.util.Collections.singleton(targetTUId))
				.build();

		// Must NOT throw — the caller owns the reservation on the target TU
		ownerService.cuToExistingTU(sourceCU, Quantity.of(ONE, data.helper.uomKg), targetTU);
	}
}
