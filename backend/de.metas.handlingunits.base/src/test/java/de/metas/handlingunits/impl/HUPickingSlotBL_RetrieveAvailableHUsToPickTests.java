package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.impl.HUPickingSlotBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleUpdater;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;
import de.metas.storage.spi.hu.impl.HUStorageEngine;
import de.metas.storage.spi.hu.impl.HUStorageRecord;
import de.metas.storage.spi.hu.impl.HUStorageRecord_HUPart;
import de.metas.util.Services;
import lombok.Value;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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
public class HUPickingSlotBL_RetrieveAvailableHUsToPickTests
{
	private HUStorageEngine storageEngine;
	private PickingCandidateRepository pickingCandidatesRepo;

	private I_C_UOM uom;
	private I_M_Product product;

	private final int LOCATOR_ID = 10;
	private I_M_Locator locator;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		storageEngine = Mockito.spy(new HUStorageEngine());
		Services.get(IStorageEngineService.class).registerStorageEngine(storageEngine);

		Services.registerService(IShipmentScheduleUpdater.class, ShipmentScheduleUpdater.newInstanceForUnitTesting());

		pickingCandidatesRepo = new PickingCandidateRepository();
		SpringContextHolder.registerJUnitBean(pickingCandidatesRepo);

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);

		locator = newInstance(I_M_Locator.class);
		locator.setM_Locator_ID(LOCATOR_ID);
		saveRecord(locator);
	}

	@Test
	public void testEmtpySchedulesList()
	{
		final List<I_M_HU> result = new HUPickingSlotBL().retrieveAvailableHUsToPick(PickingHUsQuery.builder().shipmentSchedules(Collections.emptyList()).build());
		assertThat(result).isEmpty();
	}

	/**
	 * There is a HU but no picking candidate, so the HU shall be returned.
	 */
	@Test
	public void testNoPickingCandidate()
	{
		testNoPickingCandidate(false);
		testNoPickingCandidate(true);
	}

	private void testNoPickingCandidate(final boolean onlyTopLevelHUs)
	{
		final I_M_HU vhu = createHU();

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		assertThat(result).isNotEmpty();
	}

	@Test
	public void testExistingPickingCandidate()
	{
		testExistingPickingCandidate(false);
		testExistingPickingCandidate(true);
	}

	private void testExistingPickingCandidate(final boolean onlyTopLevelHUs)
	{
		final I_M_HU vhu = createHU();

		createPickingCandidate(vhu);

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		assertThat(result)
				.as("HUs referenced by a picking candidate should be filtered out")
				.isEmpty();
	}

	@Test
	public void testVhuWithTuNoExistingPickingCandidate()
	{
		testVhuWithTuNoExistingPickingCandidate(false);
		testVhuWithTuNoExistingPickingCandidate(true);
	}

	private void testVhuWithTuNoExistingPickingCandidate(final boolean onlyTopLevelHUs)
	{
		final I_M_HU tu = createHU();

		final I_M_HU_Item item = newInstance(I_M_HU_Item.class);
		item.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		item.setM_HU(tu);
		saveRecord(item);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		vhu.setM_HU_Item_Parent(item);
		saveRecord(vhu);

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		if (onlyTopLevelHUs)
		{
			assertThat(result).containsExactly(tu);
		}
		else
		{
			assertThat(result).hasSize(2);
		}
	}

	@Test
	public void testVhuWithTuExistingPickingCandidateForVhu()
	{
		testVhuWithTuExistingPickingCandidateForVhu(false);
		testVhuWithTuExistingPickingCandidateForVhu(true);
	}

	private void testVhuWithTuExistingPickingCandidateForVhu(final boolean onlyTopLevelHUs)
	{
		final I_M_HU tu = createHU();

		final I_M_HU_Item item = newInstance(I_M_HU_Item.class);
		item.setM_HU(tu);
		saveRecord(item);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		vhu.setM_HU_Item_Parent(item);
		saveRecord(vhu);

		createPickingCandidate(vhu);

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		assertThat(result)
				.as("VHUs referenced by a picking candidate should be filtered out, just like TUs, if no children are left")
				.isEmpty();
	}

	@Test
	public void testTuWithVhuExistingPickingCandidateForTu()
	{
		testTuWithVhuExistingPickingCandidateForTu(false);
		testTuWithVhuExistingPickingCandidateForTu(true);
	}

	private void testTuWithVhuExistingPickingCandidateForTu(final boolean onlyTopLevelHUs)
	{
		final I_M_HU tu = createHU();
		POJOWrapper.setInstanceName(tu, "tu");

		createPickingCandidate(tu);

		final I_M_HU_Item item = newInstance(I_M_HU_Item.class);
		item.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		item.setM_HU(tu);
		saveRecord(item);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		vhu.setM_HU_Item_Parent(item);
		saveRecord(vhu);
		POJOWrapper.setInstanceName(vhu, "vhu");

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		assertThat(result)
				.as("TUs referenced by a picking candidate should be filtered out, together with their child-HUs")
				.isEmpty();
	}

	@Test
	public void testLuWithTuAndVhuAndPickingCandidateForVhu()
	{
		testLuWithTuAndVhuAndPickingCandidateForVhu(false);
		testLuWithTuAndVhuAndPickingCandidateForVhu(true);
	}

	private void testLuWithTuAndVhuAndPickingCandidateForVhu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();
		createPickingCandidate(luTuVhu.getVhu());

		final List<I_M_HU> result = common(luTuVhu.getVhu(), onlyTopLevelHUs);
		assertThat(result).isEmpty();
	}

	@Test
	public void testLuWithTuAndVhuAndPickingCandidateForTu()
	{
		testLuWithTuAndVhuAndPickingCandidateForTu(false);
		testLuWithTuAndVhuAndPickingCandidateForTu(true);
	}

	private void testLuWithTuAndVhuAndPickingCandidateForTu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();
		createPickingCandidate(luTuVhu.getTu());

		final List<I_M_HU> result = common(luTuVhu.getTu(), onlyTopLevelHUs);
		assertThat(result).isEmpty();
	}

	@Test
	public void testVhuWithTuAndLuAndPickingCandidateForLu()
	{
		testVhuWithTuAndLuAndPickingCandidateForLu(false);
		testVhuWithTuAndLuAndPickingCandidateForLu(true);
	}

	private void testVhuWithTuAndLuAndPickingCandidateForLu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();
		createPickingCandidate(luTuVhu.getLu());

		final List<I_M_HU> result = common(luTuVhu.getVhu(), onlyTopLevelHUs);
		assertThat(result).isEmpty();
	}

	/**
	 * Like {@link #testVhuWithTuAndLuAndPickingCandidateForLu(boolean)}, but the LU is not flagged by a picking candidate but by a {@link I_M_Source_HU}.<br>
	 * Because of the source HU, LU shall still not be returned.
	 *
	 * @param onlyTopLevelHUs
	 */
	@Test
	public void testVhuWithTuAndLuAndSourceHuForLu()
	{
		testVhuWithTuAndLuAndSourceHuForLu(false);
		testVhuWithTuAndLuAndSourceHuForLu(true);
	}

	private void testVhuWithTuAndLuAndSourceHuForLu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();

		final I_M_Source_HU sourceHU = newInstance(I_M_Source_HU.class);
		sourceHU.setM_HU(luTuVhu.getLu());
		saveRecord(sourceHU);

		final List<I_M_HU> result = common(luTuVhu.getVhu(), onlyTopLevelHUs);
		assertThat(result).isEmpty();
	}

	/**
	 * Creates a LU with two TUs. One of the two TUs is already picked. Verifies that the LU, the not-picked TU and the not-picked TU's VHU are returned.
	 */
	@Test
	public void testLuWithTwoTusAndVhusAndPickingCandidateForOneTu()
	{
		testLuWithTwoTusAndVhusAndPickingCandidateForOneTu(false);
		testLuWithTwoTusAndVhusAndPickingCandidateForOneTu(true);
	}

	private void testLuWithTwoTusAndVhusAndPickingCandidateForOneTu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();

		final I_M_HU_Item secondLuItem = newInstance(I_M_HU_Item.class);
		secondLuItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		secondLuItem.setM_HU(luTuVhu.getLu());
		saveRecord(secondLuItem);

		final I_M_HU secondTu = newInstance(I_M_HU.class);
		secondTu.setHUStatus(X_M_HU.HUSTATUS_Active);
		secondTu.setM_Locator_ID(LOCATOR_ID);
		secondTu.setM_HU_Item_Parent(secondLuItem);
		saveRecord(secondTu);

		final I_M_HU_Item secondTuItem = newInstance(I_M_HU_Item.class);
		secondTuItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		secondTuItem.setM_HU(secondTu);
		saveRecord(secondTuItem);

		final I_M_HU secondVhu = newInstance(I_M_HU.class);
		secondVhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		secondVhu.setM_Locator_ID(LOCATOR_ID);
		secondVhu.setM_HU_Item_Parent(secondTuItem);
		saveRecord(secondVhu);

		createPickingCandidate(secondTu);

		final List<I_M_HU> result = common(luTuVhu.getVhu(), onlyTopLevelHUs);
		if (onlyTopLevelHUs)
		{
			assertThat(result)
					.containsExactlyInAnyOrder(luTuVhu.getLu());
		}
		else
		{
			assertThat(result)
					.containsExactlyInAnyOrder(luTuVhu.getLu(), luTuVhu.getTu(), luTuVhu.getVhu());
		}
	}

	private void createPickingCandidate(final I_M_HU packedToHU)
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		saveRecord(shipmentSchedule);

		final HuId packedHUId = HuId.ofRepoId(packedToHU.getM_HU_ID());

		final PickingCandidate pickingCandidate = PickingCandidate.builder()
				.processingStatus(PickingCandidateStatus.Processed) // not relevant
				.qtyPicked(Quantity.zero(uom)) // not relevant
				.pickFrom(PickFrom.ofHuId(packedHUId))
				.packedToHuId(packedHUId)
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()))
				.build();
		pickingCandidatesRepo.save(pickingCandidate);
	}

	private I_M_HU createHU()
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		hu.setM_Locator_ID(LOCATOR_ID);
		saveRecord(hu);
		return hu;
	}

	private LuTuVhu createLuTuVhu()
	{
		final I_M_HU lu = createHU();
		POJOWrapper.setInstanceName(lu, "lu");

		final I_M_HU_Item luItem = newInstance(I_M_HU_Item.class);
		luItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		luItem.setM_HU(lu);
		saveRecord(luItem);

		final I_M_HU tu = newInstance(I_M_HU.class);
		tu.setHUStatus(X_M_HU.HUSTATUS_Active);
		tu.setM_Locator_ID(LOCATOR_ID);
		tu.setM_HU_Item_Parent(luItem);
		saveRecord(tu);
		POJOWrapper.setInstanceName(tu, "tu");

		final I_M_HU_Item tuItem = newInstance(I_M_HU_Item.class);
		tuItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		tuItem.setM_HU(tu);
		saveRecord(tuItem);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		vhu.setM_HU_Item_Parent(tuItem);
		saveRecord(vhu);
		POJOWrapper.setInstanceName(vhu, "vhu");

		return new LuTuVhu(lu, tu, vhu);
	}

	@Value
	private static class LuTuVhu
	{
		I_M_HU lu;
		I_M_HU tu;
		I_M_HU vhu;
	}

	/**
	 * Set up the storage engine to return the given {@code vhu} and call the method under test.
	 *
	 * @param vhu
	 * @param onlyTopLevelHUs TODO
	 * @return
	 */
	private List<I_M_HU> common(
			final I_M_HU vhu,
			final boolean onlyTopLevelHUs)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		saveRecord(warehouse);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		saveRecord(bpartner);

		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		shipmentSchedule.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		shipmentSchedule.setM_Product_ID(product.getM_Product_ID());

		saveRecord(shipmentSchedule);

		final I_M_HU_Storage huStorage = newInstance(I_M_HU_Storage.class);
		huStorage.setM_Product_ID(product.getM_Product_ID());
		huStorage.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(huStorage);

		{
			final HUStorageRecord_HUPart huPart = new HUStorageRecord_HUPart(vhu, NullAttributeStorage.instance);
			final HUStorageRecord storageRecord = new HUStorageRecord(huPart, huStorage);

			Mockito.doReturn(ImmutableList.of(storageRecord))
					.when(storageEngine)
					.retrieveStorageRecords(Matchers.any(IContextAware.class), Matchers.anyListOf(IStorageQuery.class));
		}

		final List<I_M_HU> result = new HUPickingSlotBL()
				.retrieveAvailableHUsToPick(PickingHUsQuery.builder()
						.shipmentSchedule(shipmentSchedule)
						.onlyTopLevelHUs(onlyTopLevelHUs)
						.build());
		return result;
	}

}
