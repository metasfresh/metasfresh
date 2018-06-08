package de.metas.handlingunits.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.IContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.handlingunits.picking.impl.HUPickingSlotBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;
import de.metas.storage.spi.hu.impl.HUStorageRecord;
import lombok.Value;
import mockit.Expectations;
import mockit.Injectable;
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
public class HUPickingSlotBL_RetrieveAvailableHUsToPickTests
{
	@Injectable
	private IStorageEngine storageEngine;

	@Mocked
	private HUStorageRecord storageRecord;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.get(IStorageEngineService.class).registerStorageEngine(storageEngine);
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
	@Theory
	public void testNoPickingCandidate(final boolean onlyTopLevelHUs)
	{
		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		save(vhu);

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		assertThat(result).isNotEmpty();
	}

	@Theory
	public void testExistingPickingCandidate(final boolean onlyTopLevelHUs)
	{
		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		save(vhu);

		final I_M_Picking_Candidate pickingCandidate = newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setM_HU(vhu);
		save(pickingCandidate);

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		assertThat(result)
				.as("HUs referenced by a picking candidate should be filtered out")
				.isEmpty();
	}

	@Theory
	public void testVhuWithTuNoExistingPickingCandidate(final boolean onlyTopLevelHUs)
	{
		final I_M_HU tu = newInstance(I_M_HU.class);
		tu.setHUStatus(X_M_HU.HUSTATUS_Active);
		tu.setM_Locator_ID(LOCATOR_ID);
		save(tu);

		final I_M_HU_Item item = newInstance(I_M_HU_Item.class);
		item.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		item.setM_HU(tu);
		save(item);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		vhu.setM_HU_Item_Parent(item);
		save(vhu);

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

	@Theory
	public void testVhuWithTuExistingPickingCandidateForVhu(final boolean onlyTopLevelHUs)
	{
		final I_M_HU tu = newInstance(I_M_HU.class);
		tu.setHUStatus(X_M_HU.HUSTATUS_Active);
		tu.setM_Locator_ID(LOCATOR_ID);
		save(tu);

		final I_M_HU_Item item = newInstance(I_M_HU_Item.class);
		item.setM_HU(tu);
		save(item);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		vhu.setM_HU_Item_Parent(item);
		save(vhu);

		final I_M_Picking_Candidate pickingCandidate = newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setM_HU(vhu);
		save(pickingCandidate);

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		assertThat(result)
				.as("VHUs referenced by a picking candidate should be filtered out, just like TUs, if no children are left")
				.isEmpty();
	}

	@Theory
	public void testTuWithVhuExistingPickingCandidateForTu(final boolean onlyTopLevelHUs)
	{
		final I_M_HU tu = newInstance(I_M_HU.class);
		tu.setHUStatus(X_M_HU.HUSTATUS_Active);
		tu.setM_Locator_ID(LOCATOR_ID);
		save(tu);
		POJOWrapper.setInstanceName(tu, "tu");

		final I_M_Picking_Candidate pickingCandidate = newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setM_HU(tu);
		save(pickingCandidate);

		final I_M_HU_Item item = newInstance(I_M_HU_Item.class);
		item.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		item.setM_HU(tu);
		save(item);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		vhu.setM_HU_Item_Parent(item);
		save(vhu);
		POJOWrapper.setInstanceName(vhu, "vhu");

		final List<I_M_HU> result = common(vhu, onlyTopLevelHUs);
		assertThat(result)
				.as("TUs referenced by a picking candidate should be filtered out, together with their child-HUs")
				.isEmpty();
	}

	@Theory
	public void testLuWithTuAndVhuAndPickingCandidateForVhu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();

		final I_M_Picking_Candidate pickingCandidate = newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setM_HU(luTuVhu.getVhu());
		save(pickingCandidate);

		final List<I_M_HU> result = common(luTuVhu.getVhu(), onlyTopLevelHUs);
		assertThat(result).isEmpty();
	}

	@Theory
	public void testLuWithTuAndVhuAndPickingCandidateForTu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();

		final I_M_Picking_Candidate pickingCandidate = newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setM_HU(luTuVhu.getTu());
		save(pickingCandidate);

		final List<I_M_HU> result = common(luTuVhu.getTu(), onlyTopLevelHUs);
		assertThat(result).isEmpty();
	}

	@Theory
	public void testVhuWithTuAndLuAndPickingCandidateForLu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();

		final I_M_Picking_Candidate pickingCandidate = newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setM_HU(luTuVhu.getLu());
		save(pickingCandidate);

		final List<I_M_HU> result = common(luTuVhu.getVhu(), onlyTopLevelHUs);
		assertThat(result).isEmpty();
	}

	/**
	 * Like {@link #testVhuWithTuAndLuAndPickingCandidateForLu(boolean)}, but the LU is not flagged by a picking candidate but by a {@link I_M_Source_HU}.<br>
	 * Because of the source HU, LU shall still not be returned.
	 *
	 * @param onlyTopLevelHUs
	 */
	@Theory
	public void testVhuWithTuAndLuAndSourceHuForLu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();

		final I_M_Source_HU sourceHU = newInstance(I_M_Source_HU.class);
		sourceHU.setM_HU(luTuVhu.getLu());
		save(sourceHU);

		final List<I_M_HU> result = common(luTuVhu.getVhu(), onlyTopLevelHUs);
		assertThat(result).isEmpty();
	}

	/**
	 * Creates a LU with two TUs. One of the two TUs is already picked. Verifies that the LU, the not-picked TU and the not-picked TU's VHU are returned.
	 */
	@Theory
	public void testLuWithTwoTusAndVhusAndPickingCandidateForOneTu(final boolean onlyTopLevelHUs)
	{
		final LuTuVhu luTuVhu = createLuTuVhu();

		final I_M_HU_Item secondLuItem = newInstance(I_M_HU_Item.class);
		secondLuItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		secondLuItem.setM_HU(luTuVhu.getLu());
		save(secondLuItem);

		final I_M_HU secondTu = newInstance(I_M_HU.class);
		secondTu.setHUStatus(X_M_HU.HUSTATUS_Active);
		secondTu.setM_Locator_ID(LOCATOR_ID);
		secondTu.setM_HU_Item_Parent(secondLuItem);
		save(secondTu);

		final I_M_HU_Item secondTuItem = newInstance(I_M_HU_Item.class);
		secondTuItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		secondTuItem.setM_HU(secondTu);
		save(secondTuItem);

		final I_M_HU secondVhu = newInstance(I_M_HU.class);
		secondVhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		secondVhu.setM_Locator_ID(LOCATOR_ID);
		secondVhu.setM_HU_Item_Parent(secondTuItem);
		save(secondVhu);

		final I_M_Picking_Candidate pickingCandidate = newInstance(I_M_Picking_Candidate.class);
		pickingCandidate.setM_HU(secondTu);
		save(pickingCandidate);

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

	private LuTuVhu createLuTuVhu()
	{
		final I_M_HU lu = newInstance(I_M_HU.class);
		lu.setHUStatus(X_M_HU.HUSTATUS_Active);
		lu.setM_Locator_ID(LOCATOR_ID);
		save(lu);
		POJOWrapper.setInstanceName(lu, "lu");

		final I_M_HU_Item luItem = newInstance(I_M_HU_Item.class);
		luItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		luItem.setM_HU(lu);
		save(luItem);

		final I_M_HU tu = newInstance(I_M_HU.class);
		tu.setHUStatus(X_M_HU.HUSTATUS_Active);
		tu.setM_Locator_ID(LOCATOR_ID);
		tu.setM_HU_Item_Parent(luItem);
		save(tu);
		POJOWrapper.setInstanceName(tu, "tu");

		final I_M_HU_Item tuItem = newInstance(I_M_HU_Item.class);
		tuItem.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		tuItem.setM_HU(tu);
		save(tuItem);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		vhu.setM_Locator_ID(LOCATOR_ID);
		vhu.setM_HU_Item_Parent(tuItem);
		save(vhu);
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

	private final int LOCATOR_ID = 10;

	/**
	 * Set up the storage engine to return the given {@code vhu} and call the method under test.
	 *
	 * @param vhu
	 * @param onlyTopLevelHUs TODO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<I_M_HU> common(
			final I_M_HU vhu,
			final boolean onlyTopLevelHUs)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);

		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setM_Warehouse(warehouse);
		save(shipmentSchedule);

		// @formatter:off
		new Expectations()
		{{
				storageEngine.retrieveStorageRecords((IContextAware)any, (Set<IStorageQuery>)any);
				result = ImmutableSet.of(storageRecord);

				storageRecord.getVHU();	result = vhu;
				storageRecord.getLocator().getM_Locator_ID(); result = LOCATOR_ID;
		}};
		// @formatter:on

		final List<I_M_HU> result = new HUPickingSlotBL()
				.retrieveAvailableHUsToPick(PickingHUsQuery.builder()
						.shipmentSchedules(ImmutableList.of(shipmentSchedule))
						.onlyTopLevelHUs(onlyTopLevelHUs)
						.build());
		return result;
	}

}
