package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationRepository;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.printing.DoNothingMassPrintingService;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.handlingunits.HUAssertions.assertThat;
import static java.math.BigDecimal.ONE;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		testsBase = new HUTransformTestsBase();

		final LUTUProducerDestinationTestSupport data = testsBase.getData();
		huTransformService = HUTransformService.newInstance(data.helper.getHUContext());

		final QRCodeConfigurationService qrCodeConfigurationService = new QRCodeConfigurationService(new QRCodeConfigurationRepository());
		SpringContextHolder.registerJUnitBean(qrCodeConfigurationService);
		SpringContextHolder.registerJUnitBean(new HUQRCodesService(new HUQRCodesRepository(), new GlobalQRCodeService(DoNothingMassPrintingService.instance), qrCodeConfigurationService));
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
		Assert.assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_ID(), is(existingTU.getM_HU_ID()));
		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		Assert.assertThat(cuToSplitXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(cuToSplitXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("35.000")));

		final Node existingTUXML = HUXmlConverter.toXml(existingTU);
		Assert.assertThat(existingTUXML, not(hasXPath("HU-TU_IFCO/M_HU_Item_Parent_ID"))); // verify that there is still no parent HU
		Assert.assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='A'])", is("1")));
		Assert.assertThat(existingTUXML, hasXPath("HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("40.000")));

		Assert.assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg'])", is("1")));
		Assert.assertThat(existingTUXML, hasXPath("count(HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and  @Qty='5.000' and @C_UOM_Name='Kg'])", is("1")));
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

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest);
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

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest);
		// data.helper.commitAndDumpHU(topLevelParent);

		final Node existingLUXML = HUXmlConverter.toXml(topLevelParent);
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet[@HUStatus='A'])", is("1")));

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		Assert.assertThat(existingLUXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("500.000")));
		Assert.assertThat(existingLUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("495.000")));
		Assert.assertThat(existingLUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("5.000")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='4.000' and @C_UOM_Name='Kg'])", is("1")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		Assert.assertThat(newCuXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(newCuXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("1.000")));
	}

	@Test
	public void husToNewCUs_aggregate_HU_keep_CU_below_sourceHU_2()
	{
		final LUTUProducerDestinationTestSupport data = testsBase.getData();

		final I_M_HU cuToSplit = data.mkAggregateHUWithTotalQtyCU("200");

		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(cuToSplit);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHU(topLevelParent)
				.productId(data.helper.pTomatoProductId)
				.qtyCU(Quantity.of(ONE, data.helper.uomKg))
				.keepNewCUsUnderSameParent(true)
				.build();

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest);
		// data.helper.commitAndDumpHU(topLevelParent);

		final Node existingLUXML = HUXmlConverter.toXml(topLevelParent);
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet[@HUStatus='A'])", is("1")));

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		Assert.assertThat(existingLUXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("200.000")));
		Assert.assertThat(existingLUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("160.000")));
		Assert.assertThat(existingLUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("40.000")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		Assert.assertThat(newCuXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(newCuXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("1.000")));
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

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest);
		// data.helper.commitAndDumpHU(topLevelParent);
		// data.helper.commitAndDumpHUs(newCUs);

		assertThat(newCUs).hasSize(5);
		final ImmutableList<Integer> distinctIds = newCUs.stream().map(I_M_HU::getM_HU_ID).distinct().collect(ImmutableList.toImmutableList());
		assertThat(distinctIds).hasSize(5);

		final Node existingLUXML = HUXmlConverter.toXml(topLevelParent);
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet[@HUStatus='A'])", is("1")));

		// the LU now has an aggregate (ItemType='HA'), representing 99 TUs with 5 CUs each and a "real" (ItemType='HU') TU with 4
		Assert.assertThat(existingLUXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("200.000")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA'])", is("1")));
		Assert.assertThat(existingLUXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage/@Qty", is("0.000")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU'])", is("1")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO)", is("5")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("5")));
		Assert.assertThat(existingLUXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Item/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("5")));
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

		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest);

		final Node tuXML = HUXmlConverter.toXml(topLevelParent);
		Assert.assertThat(tuXML, hasXPath("HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("40.000")));
		Assert.assertThat(tuXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));
		Assert.assertThat(tuXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newCuXML = HUXmlConverter.toXml(CollectionUtils.singleElement(newCUs));
		Assert.assertThat(newCuXML, hasXPath("string(HU-VirtualPI/@HUStatus)", is("A")));
		Assert.assertThat(newCuXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("1.000")));
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
		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest);

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
		final List<I_M_HU> newCUs = huTransformService.husToNewCUs(husToNewCUsRequest);

		assertThat(newCUs).isEmpty(); // nothing was extracted, because lu does not contain any salad.
	}
}
