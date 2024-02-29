package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationRepository;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.printing.DoNothingMassPrintingService;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
public class HUTransformServiceReceiptCandidatesTests
{
	/**
	 * This dataPoint shall enable us to test with both values of {@code isOwnPackingMaterials}.
	 */
	@DataPoints("isOwnPackingMaterials")
	public static boolean[] isOwnPackingMaterials = { true, false };

	private LUTUProducerDestinationTestSupport data;
	private BPartnerId bpartnerId;
	private WarehouseId warehouseId;

	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;
	private IHUDocumentFactoryService huDocumentFactoryService;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		bpartnerId = BPartnerId.ofRepoId(BusinessTestHelper.createBPartner("test").getC_BPartner_ID());
		warehouseId = WarehouseId.ofRepoId(BusinessTestHelper.createWarehouse("test").getM_Warehouse_ID());

		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		huDocumentFactoryService = Services.get(IHUDocumentFactoryService.class);

		final QRCodeConfigurationService qrCodeConfigurationService = new QRCodeConfigurationService(new QRCodeConfigurationRepository());
		SpringContextHolder.registerJUnitBean(qrCodeConfigurationService);
		SpringContextHolder.registerJUnitBean(new HUQRCodesService(new HUQRCodesRepository(), new GlobalQRCodeService(DoNothingMassPrintingService.instance), qrCodeConfigurationService));
	}

	private I_M_ReceiptSchedule create_receiptSchedule_for_CU(final I_M_HU cu, final String cuQtyStr)
	{
		final List<IHUProductStorage> storages = data.helper.getHUContext().getHUStorageFactory().getStorage(cu).getProductStorages();
		Check.errorUnless(storages.size() == 1, "Param' cuHU' needs to have *one* storage; storages={}; cuHU={};", storages, cu);

		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		receiptSchedule.setM_Warehouse_ID(warehouseId.getRepoId());
		receiptSchedule.setC_BPartner_ID(bpartnerId.getRepoId());
		receiptSchedule.setM_Product_ID(storages.get(0).getProductId().getRepoId());
		receiptSchedule.setC_UOM_ID(storages.get(0).getC_UOM().getC_UOM_ID());
		InterfaceWrapperHelper.save(receiptSchedule);

		final I_M_HU lu;
		final I_M_HU tu;
		final I_M_HU topLevelHU;

		final I_M_HU cuParent = handlingUnitsDAO.retrieveParent(cu);
		if (handlingUnitsBL.isAggregateHU(cu))
		{
			tu = cu;
			lu = cuParent; // can't be null at this point
			topLevelHU = lu;
		}
		else
		{
			if (handlingUnitsBL.isTransportUnit(cuParent))
			{
				tu = cuParent;
				lu = handlingUnitsDAO.retrieveParent(tu);
				topLevelHU = lu != null ? lu : tu;
			}
			else if (cuParent == null)
			{
				tu = null;
				lu = null;
				topLevelHU = cu;
			}
			else
			{
				tu = null;
				lu = null;
				topLevelHU = null;
				fail("unhandeled case");
			}
		}

		final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule_Alloc.class);
		receiptScheduleAlloc.setM_ReceiptSchedule(receiptSchedule);

		receiptScheduleAlloc.setVHU(cu);
		receiptScheduleAlloc.setM_TU_HU(tu);
		receiptScheduleAlloc.setM_LU_HU(lu);

		receiptScheduleAlloc.setHU_QtyAllocated(new BigDecimal(cuQtyStr));
		InterfaceWrapperHelper.save(receiptScheduleAlloc);

		final I_M_HU_Assignment huAssignment = InterfaceWrapperHelper.newInstance(I_M_HU_Assignment.class);
		huAssignment.setM_HU(topLevelHU);

		final TableRecordReference rsTableRef = TableRecordReference.of(receiptSchedule);
		huAssignment.setAD_Table_ID(rsTableRef.getAD_Table_ID());
		huAssignment.setRecord_ID(rsTableRef.getRecord_ID());
		InterfaceWrapperHelper.save(huAssignment);

		return receiptSchedule;
	}

	@Theory
	public void testRealCU_To_NewTUs_1Tomato_TU_Capacity_2(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkRealStandAloneCUToSplit("40");

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.builderForHUcontext()
				.huContext(data.helper.getHUContext()).build()
				.cuToNewTUs(cuToSplit, Quantity.of(BigDecimal.ONE, data.helper.uomKg), data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

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

	@Theory
	public void testRealStandaloneTU_To_NewLU(
			@FromDataPoints("isOwnPackingMaterials") final boolean isOwnPackingMaterials)
	{
		// prepare the existing TU
		final I_M_HU cuHU = mkRealCUWithTUToSplit("20");
		final I_M_HU tuToSplit = handlingUnitsDAO.retrieveParent(cuHU);

		final I_M_ReceiptSchedule rs1 = create_receiptSchedule_for_CU(cuHU, "20");
		final TableRecordReference rs1TableRef = TableRecordReference.of(rs1);
		{ // verify that cuHU and rs1 are properly linked
			final List<IHUDocument> rs1HuDocument = huDocumentFactoryService.createHUDocuments(data.helper.getHUContext().getCtx(), rs1TableRef.getTableName(), rs1TableRef.getRecord_ID());
			assertThat(rs1HuDocument.size(), is(1));
			assertThat(rs1HuDocument.get(0).getAssignedHandlingUnits().stream().anyMatch(hu -> hu.getM_HU_ID() == cuHU.getM_HU_ID() || hu.getM_HU_ID() == tuToSplit.getM_HU_ID()), is(true));
		}

		assertThat(handlingUnitsBL.isAggregateHU(tuToSplit), is(false)); // guard; make sure it's "real"

		// invoke the method under test
		final List<I_M_HU> newLUs = HUTransformService.newInstance(data.helper.getHUContext())
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
	 * <ul>
	 * <li>create a standalone CU with 2kg tomatoes and add it to a new TU
	 * <li>create a standalone CU with 3kg salad
	 * <li><move 1.6kg of the salad to the TU
	 * </ul>
	 *
	 * @task https://github.com/metasfresh/metasfresh-webui/issues/237 Transform CU on existing TU not working
	 */
	@Test
	public void test_CUToExistingTU_create_mixed_TU_partialCU()
	{
		final I_M_HU cu1 = mkRealCUWithTUToSplit("2");
		final I_M_HU existingTU = handlingUnitsDAO.retrieveParent(cu1);

		final I_M_ReceiptSchedule rs1 = create_receiptSchedule_for_CU(cu1, "2");
		final TableRecordReference rs1TableRef = TableRecordReference.of(rs1);
		{ // verify that cuHU and rs1 are properly linked
			final List<IHUDocument> rs1HuDocument = huDocumentFactoryService.createHUDocuments(data.helper.getHUContext().getCtx(), rs1TableRef.getTableName(), rs1TableRef.getRecord_ID());
			assertThat(rs1HuDocument.size(), is(1));
			assertThat(rs1HuDocument.get(0).getAssignedHandlingUnits().stream().anyMatch(hu -> hu.getM_HU_ID() == cu1.getM_HU_ID() || hu.getM_HU_ID() == existingTU.getM_HU_ID()), is(true));
		}

		final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI()
				.setLocatorId(data.defaultLocatorId);
		data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal("3"), data.helper.uomKg);
		final I_M_HU cu2 = producer.getCreatedHUs().get(0);
		final I_M_ReceiptSchedule rs2 = create_receiptSchedule_for_CU(cu2, "3");
		final TableRecordReference rs2TableRef = TableRecordReference.of(rs2);
		{ // verify that secondCU and rs2 are properly linked
			final List<IHUDocument> rs2HuDocument = huDocumentFactoryService.createHUDocuments(data.helper.getHUContext().getCtx(), rs2TableRef.getTableName(), rs2TableRef.getRecord_ID());
			assertThat(rs2HuDocument.size(), is(1));
			assertThat(rs2HuDocument.get(0).getAssignedHandlingUnits().stream().anyMatch(hu -> hu.getM_HU_ID() == cu2.getM_HU_ID() || hu.getM_HU_ID() == existingTU.getM_HU_ID()), is(true));
		}

		// invoke the method under test.
		HUTransformService.builderForHUcontext()
				.huContext(data.helper.getHUContext())
				.referencedObjects(ImmutableList.of(rs1TableRef, rs2TableRef))
				.build()
				.cuToExistingTU(cu2, Quantity.of(new BigDecimal("1.6"), data.helper.uomKg), existingTU);

		// secondCU is still there, with the remaining 1.4kg
		final Node secondCUXML = HUXmlConverter.toXml(cu2);
		assertThat(secondCUXML, hasXPath("string(HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/@HUStatus)", is("P")));
		assertThat(secondCUXML, hasXPath("string(HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.400")));

		final Node existingLUXML = HUXmlConverter.toXml(existingTU);
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
		assertThat(existingLUXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("1.600")));

		// verify that the receipt M_ReceiptSchedule_Allocs are also OK
		final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
		{
			// verify cu1 that was effectively unchanged
			final I_M_ReceiptSchedule receiptScheduleForCU1 = huReceiptScheduleDAO.retrieveReceiptScheduleForVHU(cu1);
			assertThat(receiptScheduleForCU1, notNullValue());
			assertThat(receiptScheduleForCU1.getM_ReceiptSchedule_ID(), is(rs1.getM_ReceiptSchedule_ID()));

			final List<I_M_ReceiptSchedule_Alloc> rsas1 = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptScheduleForCU1, data.helper.getHUContext().getTrxName());
			final List<I_M_ReceiptSchedule_Alloc> rsas1ForCu1 = rsas1.stream().filter(rsa -> rsa.getM_TU_HU_ID() == existingTU.getM_HU_ID() && rsa.getVHU_ID() == cu1.getM_HU_ID()).collect(Collectors.toList());
			assertThat(rsas1ForCu1.size(), is(1));
			assertThat(rsas1ForCu1.get(0).getHU_QtyAllocated(), comparesEqualTo(new BigDecimal("2")));
		}
		{
			// // verify c2 which got 1.6 kg of salad chopped off
			final I_M_ReceiptSchedule receiptScheduleForCU2 = huReceiptScheduleDAO.retrieveReceiptScheduleForVHU(cu2);
			assertThat(receiptScheduleForCU2, notNullValue());
			assertThat(receiptScheduleForCU2.getM_ReceiptSchedule_ID(), is(rs2.getM_ReceiptSchedule_ID()));

			// TODO this doesn't work and i'm unsure why it doesen't work, but also how it should work..cu2 is split be HULoader, so there is alot going on with hu-transaction stuff.
			// final List<I_M_ReceiptSchedule_Alloc> rsas2 = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptScheduleForCU2, data.helper.getHUContext().getTrxName());
			// final List<I_M_ReceiptSchedule_Alloc> rsas2ForCu2 = rsas2.stream().filter(rsa -> rsa.getM_TU_HU_ID() == existingTU.getM_HU_ID() && rsa.getVHU_ID() == cu2.getM_HU_ID()).collect(Collectors.toList());
			// assertThat(rsas2ForCu2.size(), is(1));
			// assertThat(rsas2ForCu2.get(0).getHU_QtyAllocated(), comparesEqualTo(new BigDecimal("1.6")));
		}
		{
			final List<I_M_HU> siblingsOfCu1 = handlingUnitsDAO.retrieveIncludedHUs(existingTU).stream().filter(hu -> hu.getM_HU_ID() != cu1.getM_HU_ID()).collect(Collectors.toList());
			assertThat(siblingsOfCu1.size(), is(1));
			final I_M_HU newlySplitOffCU = siblingsOfCu1.get(0);
			// verify the new cu that was split off cu2 and is now below existingTU
			final I_M_ReceiptSchedule receiptScheduleForCU2_2 = huReceiptScheduleDAO.retrieveReceiptScheduleForVHU(newlySplitOffCU);
			assertThat(receiptScheduleForCU2_2, notNullValue());
			assertThat(receiptScheduleForCU2_2.getM_ReceiptSchedule_ID(), is(rs2.getM_ReceiptSchedule_ID()));

			final List<I_M_ReceiptSchedule_Alloc> rsas2 = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptScheduleForCU2_2, data.helper.getHUContext().getTrxName());
			final List<I_M_ReceiptSchedule_Alloc> rsas2ForCu2 = rsas2.stream().filter(rsa -> rsa.getM_TU_HU_ID() == existingTU.getM_HU_ID() && rsa.getVHU_ID() == newlySplitOffCU.getM_HU_ID()).collect(Collectors.toList());
			assertThat(rsas2ForCu2.size(), is(1));
			assertThat(rsas2ForCu2.get(0).getHU_QtyAllocated(), comparesEqualTo(new BigDecimal("1.6")));
		}
	}

	/**
	 * Similar to {@link #test_CUToExistingTU_create_mixed_TU_partialCU()}, but move all the salad
	 */
	@Test
	public void test_CUToExistingTU_create_mixed_TU_completeCU()
	{
		final BigDecimal four = new BigDecimal("4");

		final I_M_HU cu1 = mkRealCUWithTUToSplit("5");
		final I_M_HU tuWithMixedCUs = handlingUnitsDAO.retrieveParent(cu1);
		final I_M_ReceiptSchedule rs1 = create_receiptSchedule_for_CU(cu1, "5");
		final TableRecordReference rs1TableRef = TableRecordReference.of(rs1);
		{ // verify that cu1 and rs1 are properly linked
			final List<IHUDocument> rs1HuDocument = huDocumentFactoryService.createHUDocuments(data.helper.getHUContext().getCtx(), rs1TableRef.getTableName(), rs1TableRef.getRecord_ID());
			assertThat(rs1HuDocument.size(), is(1));
			assertThat(rs1HuDocument.get(0).getAssignedHandlingUnits().stream().anyMatch(hu -> hu.getM_HU_ID() == cu1.getM_HU_ID() || hu.getM_HU_ID() == tuWithMixedCUs.getM_HU_ID()), is(true));
		}
		// create a standalone-CU
		final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI()
				.setLocatorId(data.defaultLocatorId);
		data.helper.load(producer, data.helper.pSaladProductId, four, data.helper.uomKg);

		final I_M_HU cu2 = producer.getCreatedHUs().get(0);

		final I_M_ReceiptSchedule rs2 = create_receiptSchedule_for_CU(cu2, "4");
		final TableRecordReference rs2TableRef = TableRecordReference.of(rs2);
		{ // verify that rs2 and cu2 are properly linked
			final List<IHUDocument> rs2HuDocument = huDocumentFactoryService.createHUDocuments(data.helper.getHUContext().getCtx(), rs2TableRef.getTableName(), rs2TableRef.getRecord_ID());
			assertThat(rs2HuDocument.size(), is(1));
			assertThat(rs2HuDocument.get(0).getAssignedHandlingUnits().stream().anyMatch(hu -> hu.getM_HU_ID() == cu2.getM_HU_ID()), is(true));
		}

		HUTransformService.builderForHUcontext()
				.huContext(data.helper.getHUContext())
				.referencedObjects(ImmutableList.of(
						rs1TableRef,
						TableRecordReference.of(rs2)))
				.build()
				.cuToExistingTU(cu2, new Quantity(four, data.helper.uomKg), tuWithMixedCUs);

		// data.helper.commitAndDumpHU(tuWithMixedCUs);
		final Node tuWithMixedCUsXML = HUXmlConverter.toXml(tuWithMixedCUs);
		assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));
		assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));

		assertThat(tuWithMixedCUsXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "])", is("1")));
		assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu1.getM_HU_ID() + "]/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("5.000")));

		assertThat(tuWithMixedCUsXML, hasXPath("count(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "])", is("1")));
		assertThat(tuWithMixedCUsXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI[@M_HU_ID=" + cu2.getM_HU_ID() + "]/Storage[@M_Product_Value='Salad' and @C_UOM_Name='Kg']/@Qty)", is("4.000")));

		// verify that the receipt M_ReceiptSchedule_Allocs are also OK
		final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
		{
			final I_M_ReceiptSchedule receiptScheduleForCU1 = huReceiptScheduleDAO.retrieveReceiptScheduleForVHU(cu1);
			assertThat(receiptScheduleForCU1, notNullValue());
			assertThat(receiptScheduleForCU1.getM_ReceiptSchedule_ID(), is(rs1.getM_ReceiptSchedule_ID()));

			final List<I_M_ReceiptSchedule_Alloc> rsas1 = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptScheduleForCU1, data.helper.getHUContext().getTrxName());
			final List<I_M_ReceiptSchedule_Alloc> rsas1ForCu1 = rsas1.stream().filter(rsa -> rsa.getM_TU_HU_ID() == tuWithMixedCUs.getM_HU_ID() && rsa.getVHU_ID() == cu1.getM_HU_ID()).collect(Collectors.toList());
			assertThat(rsas1ForCu1.size(), is(1));
			assertThat(rsas1ForCu1.get(0).getHU_QtyAllocated(), comparesEqualTo(new BigDecimal("5")));
		}
		{
			final I_M_ReceiptSchedule receiptScheduleForCU2 = huReceiptScheduleDAO.retrieveReceiptScheduleForVHU(cu2);
			assertThat(receiptScheduleForCU2, notNullValue());
			assertThat(receiptScheduleForCU2.getM_ReceiptSchedule_ID(), is(rs2.getM_ReceiptSchedule_ID()));

			final List<I_M_ReceiptSchedule_Alloc> rsas2 = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptScheduleForCU2, data.helper.getHUContext().getTrxName());
			final List<I_M_ReceiptSchedule_Alloc> rsas2ForCu2 = rsas2.stream().filter(rsa -> rsa.getM_TU_HU_ID() == tuWithMixedCUs.getM_HU_ID() && rsa.getVHU_ID() == cu2.getM_HU_ID()).collect(Collectors.toList());
			assertThat(rsas2ForCu2.size(), is(1));
			assertThat(rsas2ForCu2.get(0).getHU_QtyAllocated(), comparesEqualTo(four));
		}
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1177
	 *
	 */
	@Test
	public void testMultipleActionsIssue1177()
	{
		//
		// set up packing instructions as described in the issue (TU that holds 4kg, LU that can hold 10 TUs)
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(data.defaultLocatorId);
		final I_M_HU_PI_Item piLU_Item_10_IFCOs;
		{
			final I_M_HU_PI piTU_IFCO = data.helper.createHUDefinition("TU_IFCO-4kg-tomatoes", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			final I_M_HU_PI_Item piTU_Item_IFCO = data.helper.createHU_PI_Item_Material(piTU_IFCO);

			data.helper.assignProduct(piTU_Item_IFCO, data.helper.pTomatoProductId, new BigDecimal("4"), data.helper.uomKg);
			data.helper.createHU_PI_Item_PackingMaterial(piTU_IFCO, data.helper.pmIFCO);
			piLU_Item_10_IFCOs = data.helper.createHU_PI_Item_IncludedHU(data.piLU, piTU_IFCO, new BigDecimal("10"));

			lutuProducer.setLUItemPI(piLU_Item_10_IFCOs);
			lutuProducer.setLUPI(data.piLU);
			lutuProducer.setTUPI(piTU_IFCO);
			lutuProducer.setMaxTUsPerLU(Integer.MAX_VALUE); // allow as many TUs on that one pallet as we want
		}
		//
		// create our initial HU hierarchy
		data.helper.load(lutuProducer, data.helper.pTomatoProductId, new BigDecimal("40"), data.helper.uomKg);
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();

		//
		// verify that the initial HU hierarchy is the way we expect it to be
		final I_M_HU firstLU;
		final I_M_HU aggregateTU;
		{
			assertThat(createdLUs.size(), is(1));
			firstLU = createdLUs.get(0);
			final List<I_M_HU> aggregateTUs = handlingUnitsDAO.retrieveIncludedHUs(firstLU);
			assertThat(aggregateTUs.size(), is(1));
			aggregateTU = aggregateTUs.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(aggregateTU), is(true));
			assertThat(handlingUnitsDAO.retrieveParentItem(aggregateTU).getQty(), comparesEqualTo(new BigDecimal("10")));

			final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
			final IHUStorageDAO storageDAO = storageFactory.getHUStorageDAO();
			final I_M_HU_Storage luTomatoStorage = storageDAO.retrieveStorage(firstLU, data.helper.pTomatoProductId);
			assertThat(luTomatoStorage.getQty(), comparesEqualTo(new BigDecimal("40")));

			final I_M_HU_Storage tuTomatoStorage = storageDAO.retrieveStorage(aggregateTU, data.helper.pTomatoProductId);
			assertThat(tuTomatoStorage.getQty(), comparesEqualTo(new BigDecimal("40")));
		}

		final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);

		final I_M_ReceiptSchedule rs = create_receiptSchedule_for_CU(aggregateTU, "40");
		final TableRecordReference rsTableRef = TableRecordReference.of(rs);
		{
			// verify that aggregateTU and rs are linked via firstLU
			final List<IHUDocument> rsHuDocument = huDocumentFactoryService.createHUDocuments(data.helper.getHUContext().getCtx(), rsTableRef.getTableName(), rsTableRef.getRecord_ID());
			assertThat(rsHuDocument.size(), is(1));
			assertThat(rsHuDocument.get(0).getAssignedHandlingUnits().stream().anyMatch(hu -> hu.getM_HU_ID() == firstLU.getM_HU_ID()), is(true));

			// verify that aggregateTU is linked to one I_M_ReceiptSchedule_Alloc with qty=40
			final I_M_ReceiptSchedule receiptScheduleForAggregateTU = huReceiptScheduleDAO.retrieveReceiptScheduleForVHU(aggregateTU);
			assertThat(receiptScheduleForAggregateTU, notNullValue());
			assertThat(receiptScheduleForAggregateTU.getM_ReceiptSchedule_ID(), is(rs.getM_ReceiptSchedule_ID()));

			final List<I_M_ReceiptSchedule_Alloc> rsas1 = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptScheduleForAggregateTU, data.helper.getHUContext().getTrxName());
			final List<I_M_ReceiptSchedule_Alloc> rsas1ForAggregateTUs = rsas1.stream()
					.filter(rsa -> rsa.getM_TU_HU_ID() == aggregateTU.getM_HU_ID() && rsa.getVHU_ID() == aggregateTU.getM_HU_ID()) // aggregateTU acts both as TU and VHU
					.collect(Collectors.toList());
			assertThat(rsas1ForAggregateTUs.size(), is(1));
			assertThat(rsas1ForAggregateTUs.get(0).getHU_QtyAllocated(), comparesEqualTo(new BigDecimal("40")));
		}

		//
		// now start
		verifyQuantities(new BigDecimal("40"), new BigDecimal("10"), firstLU);
		verifyQuantities(new BigDecimal("40"), new BigDecimal("10"), aggregateTU);

		// "Split off 5 CU on their own, without new TU"
		final List<I_M_HU> newCUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewCU(aggregateTU, new Quantity(BigDecimal.valueOf(5), data.helper.uomKg));
		assertThat(newCUs.size(), is(1));
		final I_M_HU newCU = newCUs.get(0);
		// newCU does not have any TU component
		verifyQuantities(new BigDecimal("5"), new BigDecimal("0"), newCU);
		// the first action destroyed one TU, because one TU is 4kg and we took out 5kg
		verifyQuantities(new BigDecimal("40"), new BigDecimal("9"), firstLU, aggregateTU, newCU);

		// aggregateTU now contains 8 x 4kg = 32kg; below lu there is also now another (non-aggregate) IFCO with 3kg
		verifyQuantities(new BigDecimal("32"), new BigDecimal("8"), aggregateTU);

		// "Split off 2 TUs on new LU" (from the screenshot we know that the new LU has the same PI as the existing one)
		final List<I_M_HU> secondLUs = HUTransformService.newInstance(data.helper.getHUContext())
				.tuToNewLUs(aggregateTU, new BigDecimal("2"), piLU_Item_10_IFCOs, false);
		assertThat(secondLUs.size(), is(1));
		final I_M_HU secondLU = secondLUs.get(0);
		// secondLU contains 2 x 4kg = 8kg
		verifyQuantities(new BigDecimal("8"), new BigDecimal("2"), secondLU);
		// aggregateTU now contains 6 x 4kg = 24kg
		verifyQuantities(new BigDecimal("24"), new BigDecimal("6"), aggregateTU);
		// ..but all in all the qtys are unchanged
		verifyQuantities(new BigDecimal("40"), new BigDecimal("9"), firstLU, secondLU, newCU);
		verifyQuantities(new BigDecimal("40"), new BigDecimal("9"), firstLU, aggregateTU, newCU, secondLU);

		// "Split off 1 TU on its own, without new LU"
		final List<I_M_HU> singleNewTUs = HUTransformService.newInstance(data.helper.getHUContext()).tuToNewTUs(aggregateTU, BigDecimal.ONE);
		assertThat(singleNewTUs.size(), is(1));
		final I_M_HU singleNewTU = singleNewTUs.get(0);
		verifyQuantities(new BigDecimal("4"), new BigDecimal("1"), singleNewTU);
		// aggregateTU now contains 5 x 4kg = 20kg
		verifyQuantities(new BigDecimal("20"), new BigDecimal("5"), aggregateTU);

		// ..but all in all the qtys are unchanged
		verifyQuantities(new BigDecimal("40"), new BigDecimal("9"), firstLU, aggregateTU, newCU, secondLU, singleNewTU);

		// "Select the 5 CUs, Transform and put 1 CU on the free TU without LU"
		HUTransformService.newInstance(data.helper.getHUContext()).cuToExistingTU(newCU, new Quantity(BigDecimal.ONE, data.helper.uomKg), singleNewTU);
		// ..but all in all the qtys are unchanged
		verifyQuantities(new BigDecimal("40"), new BigDecimal("9"), firstLU, aggregateTU, newCU, secondLU, singleNewTU);
	}

	/**
	 * Iterates the given {@code hus} and verifies that the contained CU and TU quantities match the given {@code expectedQtyCU} and {@code expectedQtyTU}.
	 * The method makes sure to count each HU and HU-storage only once.
	 * The method also verifies that {@link I_M_ReceiptSchedule_Alloc} quantities match the CU qtys
	 *
	 * @param expectedQtyCU
	 * @param expectedQtyTU
	 * @param hus
	 */
	private void verifyQuantities(final BigDecimal expectedQtyCU, final BigDecimal expectedQtyTU, final I_M_HU... hus)
	{
		final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);

		final Mutable<BigDecimal> actualStorageQtyCU = new Mutable<>(BigDecimal.ZERO); // from HU item storage
		final Mutable<BigDecimal> actualAllocQtyCU = new Mutable<>(BigDecimal.ZERO); // from receipt schedule allocation

		final Mutable<BigDecimal> actualQtyTU = new Mutable<>(BigDecimal.ZERO);
		// final Mutable<BigDecimal> actualAllocQtyTU = new Mutable<>(BigDecimal.ZERO); // from receipt schedule allocation

		final Set<Integer> huIDsSeen = new HashSet<>();
		final Set<Integer> huItemIDsSeen = new HashSet<>();
		final Set<Integer> rsaIDsSeen = new HashSet<>(); // receipt schedule allocates we have already seem

		for (final I_M_HU hu : hus)
		{
			new HUIterator()
					.setEnableStorageIteration(true)
					.setHUContext(data.helper.getHUContext())
					.setDate(SystemTime.asZonedDateTime())
					.setListener(new HUIteratorListenerAdapter()
					{
						@Override
						public Result afterHU(final I_M_HU hu)
						{
							if (!huIDsSeen.add(hu.getM_HU_ID()))
							{
								return Result.CONTINUE;
							}

							// update the TU quantity from hu
							final BigDecimal augentTU;
							if (handlingUnitsBL.isTransportUnit(hu))
							{
								augentTU = BigDecimal.ONE;
							}
							else if (handlingUnitsBL.isAggregateHU(hu))
							{
								augentTU = handlingUnitsDAO.retrieveParentItem(hu).getQty();
							}
							else
							{
								augentTU = BigDecimal.ZERO;
							}
							actualQtyTU.setValue(actualQtyTU.getValue().add(augentTU));

							if (!handlingUnitsBL.isVirtual(hu))
							{
								return Result.CONTINUE; // we are done
							}

							final I_M_ReceiptSchedule receiptScheduleForHU = huReceiptScheduleDAO.retrieveReceiptScheduleForVHU(hu);
							final List<I_M_ReceiptSchedule_Alloc> rsas1 = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptScheduleForHU, data.helper.getHUContext().getTrxName());
							final List<I_M_ReceiptSchedule_Alloc> rsas1ForAggregateTUs = rsas1.stream()
									.filter(rsa -> rsa.getVHU_ID() == hu.getM_HU_ID())
									.filter(rsa -> rsaIDsSeen.add(rsa.getM_ReceiptSchedule_Alloc_ID()))
									.collect(Collectors.toList());

							for (final I_M_ReceiptSchedule_Alloc rsa : rsas1ForAggregateTUs)
							{
								actualAllocQtyCU.setValue(actualAllocQtyCU.getValue().add(rsa.getHU_QtyAllocated()));
							}

							return Result.CONTINUE;
						}

						@Override
						public Result afterHUItemStorage(final IHUItemStorage itemStorage)
						{
							if (!huItemIDsSeen.add(itemStorage.getM_HU_Item().getM_HU_Item_ID()))
							{
								return Result.CONTINUE;
							}
							actualStorageQtyCU.setValue(actualStorageQtyCU.getValue().add(itemStorage.getQty(data.helper.pTomatoProductId, data.helper.uomKg)));
							return Result.CONTINUE;
						}
					})
					.iterate(hu);
		}

		assertThat(actualStorageQtyCU.getValue(), comparesEqualTo(expectedQtyCU));
		assertThat(actualAllocQtyCU.getValue(), comparesEqualTo(expectedQtyCU));
		assertThat(actualQtyTU.getValue(), comparesEqualTo(expectedQtyTU));
	}

	private I_M_HU mkRealStandAloneCUToSplit(final String strCuQty)
	{
		final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI()
				.setLocatorId(data.defaultLocatorId);
		data.helper.load(producer, data.helper.pTomatoProductId, new BigDecimal(strCuQty), data.helper.uomKg);

		final List<I_M_HU> createdCUs = producer.getCreatedHUs();
		assertThat(createdCUs.size(), is(1));

		final I_M_HU cuToSplit = createdCUs.get(0);
		return cuToSplit;
	}

	private I_M_HU mkRealCUWithTUToSplit(final String strCuQty)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(data.defaultLocatorId);
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);

		final BigDecimal cuQty = new BigDecimal(strCuQty);
		data.helper.load(lutuProducer, data.helper.pTomatoProductId, cuQty, data.helper.uomKg);
		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

		assertThat(createdTUs.size(), is(1));

		final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
		assertThat(createdCUs.size(), is(1));

		final I_M_HU cuToSplit = createdCUs.get(0);

		return cuToSplit;
	}

}
