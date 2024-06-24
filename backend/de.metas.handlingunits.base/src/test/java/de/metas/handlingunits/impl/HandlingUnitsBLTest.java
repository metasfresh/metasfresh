package de.metas.handlingunits.impl;

import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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

public class HandlingUnitsBLTest
{
	private HUTestHelper helper;
	private IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@BeforeEach
	void beforeEach() {helper = HUTestHelper.newInstanceOutOfTrx();}

	/**
	 * Verifies that {@link HandlingUnitsBL#isAggregateHU(I_M_HU)} returns {@code false} for a null param. This is a trivial test, but we rely on that behavior of the isAggregateHU() method.
	 */
	@Test
	void testIsAggregateHUWithNull()
	{
		assertThat(new HandlingUnitsBL().isAggregateHU(null)).isFalse();
	}

	@Nested
	class getPackingInstructionsId
	{
		@NonNull
		private I_M_HU createHU(final I_M_HU_PI pi)
		{
			return helper.createSingleHU(pi, helper.pTomatoProductId, Quantity.of("100", helper.uomKg))
					.orElseThrow(() -> new AdempiereException("Failed creating HU for " + pi));
		}

		@Test
		void vhu()
		{
			final I_M_HU hu = createHU(helper.huDefVirtual);
			assertThat(new HandlingUnitsBL().getPackingInstructionsId(hu)).isSameAs(HuPackingInstructionsId.VIRTUAL);
		}

		@Test
		void tu()
		{
			final I_M_HU_PI tuPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			final I_M_HU_PI_Item tuPIItem = helper.createHU_PI_Item_Material(tuPI);
			helper.assignProduct(tuPIItem, helper.pTomatoProductId, Quantity.of("100", helper.uomKg));

			@NonNull final I_M_HU tu = createHU(tuPI);
			assertThat(new HandlingUnitsBL().getPackingInstructionsId(tu)).isEqualTo(HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID()));
		}

		@Test
		void lu()
		{
			final I_M_HU_PI tuPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			final I_M_HU_PI_Item tuPIItem = helper.createHU_PI_Item_Material(tuPI);
			helper.assignProduct(tuPIItem, helper.pTomatoProductId, Quantity.of("100", helper.uomKg));

			final I_M_HU_PI luPI = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("999"));

			@NonNull final I_M_HU lu = createHU(luPI);
			assertThat(new HandlingUnitsBL().getPackingInstructionsId(lu)).isEqualTo(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()));
		}
	}

	@Nested
	class setClearanceStatus
	{

		@Test
		public void givenLU_OfA_LU_TU_CU_Hierarchy_whenSetClearanceStatus_thenUpdateAllHierarchy()
		{
			//given
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu = createHU(lu);
			final I_M_HU_Item cu = createHU(tu);

			//when
			final ClearanceStatusInfo clearanceStatusInfo = ClearanceStatusInfo.builder()
					.clearanceStatus(ClearanceStatus.Locked)
					.clearanceNote("LockedNote")
					.clearanceDate(InstantAndOrgId.ofTimestamp(helper.getTodayTimestamp(), OrgId.ofRepoId(lu.getAD_Org_ID())))
					.build();
			handlingUnitsBL.setClearanceStatusRecursively(HuId.ofRepoId(lu.getM_HU_ID()), clearanceStatusInfo);

			//then
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU = InterfaceWrapperHelper.load(tu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU = InterfaceWrapperHelper.load(cu.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.getClearanceStatus()).isEqualTo(ClearanceStatus.Locked.getCode());
			assertThat(updatedTU.getClearanceStatus()).isEqualTo(ClearanceStatus.Locked.getCode());
			assertThat(updatedCU.getClearanceStatus()).isEqualTo(ClearanceStatus.Locked.getCode());

			assertThat(updatedLU.getClearanceNote()).isEqualTo("LockedNote");
			assertThat(updatedTU.getClearanceNote()).isEqualTo("LockedNote");
			assertThat(updatedCU.getClearanceNote()).isEqualTo("LockedNote");
		}

		@Test
		public void givenTU_OfA_LU_TU_CU_Hierarchy_whenSetClearanceStatus_thenUpdateTU_and_CU()
		{
			//given
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu = createHU(lu);
			final I_M_HU_Item cu = createHU(tu);

			//when
			final ClearanceStatusInfo clearanceStatusInfo = ClearanceStatusInfo.builder()
					.clearanceStatus(ClearanceStatus.Locked)
					.clearanceNote("LockedNote")
					.clearanceDate(InstantAndOrgId.ofTimestamp(helper.getTodayTimestamp(), OrgId.ofRepoId(lu.getAD_Org_ID())))
					.build();
			handlingUnitsBL.setClearanceStatusRecursively(HuId.ofRepoId(tu.getM_HU_ID()), clearanceStatusInfo);

			//then
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU = InterfaceWrapperHelper.load(tu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU = InterfaceWrapperHelper.load(cu.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.getClearanceStatus()).isNull();
			assertThat(updatedTU.getClearanceStatus()).isEqualTo(ClearanceStatus.Locked.getCode());
			assertThat(updatedCU.getClearanceStatus()).isEqualTo(ClearanceStatus.Locked.getCode());

			assertThat(updatedLU.getClearanceNote()).isNull();
			assertThat(updatedTU.getClearanceNote()).isEqualTo("LockedNote");
			assertThat(updatedCU.getClearanceNote()).isEqualTo("LockedNote");
		}
	}

	@Nested
	class isHUHierarchyCleared
	{
		@Test
		public void givenTU_OfA_LU_TU_CU_Cleared_Hierarchy_whenIsHUHierarchyCleared_thenReturnTrue()
		{
			//given
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu = createHU(lu);
			final I_M_HU_Item cu = createHU(tu);

			//when
			final boolean isWholeHierarchyCleared_LU = handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(lu.getM_HU_ID()));
			final boolean isWholeHierarchyCleared_TU = handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(tu.getM_HU_ID()));
			final boolean isWholeHierarchyCleared_CU = handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(cu.getM_HU_ID()));

			//then
			assertThat(isWholeHierarchyCleared_LU).isTrue();
			assertThat(isWholeHierarchyCleared_TU).isTrue();
			assertThat(isWholeHierarchyCleared_CU).isTrue();
		}

		@Test
		public void givenTU_OfA_LU_TU_CU_Hierarchy_With_LockedCU_whenIsHUHierarchyCleared_thenFalse()
		{
			//given
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu = createHU(lu);
			final I_M_HU_Item cu = createHU(tu);

			final ClearanceStatusInfo clearanceStatusInfo = ClearanceStatusInfo.builder()
					.clearanceStatus(ClearanceStatus.Locked)
					.clearanceNote("Locked HU")
					.clearanceDate(InstantAndOrgId.ofTimestamp(helper.getTodayTimestamp(), OrgId.ofRepoId(lu.getAD_Org_ID())))
					.build();
			handlingUnitsBL.setClearanceStatusRecursively(HuId.ofRepoId(lu.getM_HU_ID()), clearanceStatusInfo);

			//when
			final boolean isWholeHierarchyCleared_LU = handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(lu.getM_HU_ID()));
			final boolean isWholeHierarchyCleared_TU = handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(tu.getM_HU_ID()));
			final boolean isWholeHierarchyCleared_CU = handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(cu.getM_HU_ID()));

			//then
			assertThat(isWholeHierarchyCleared_LU).isFalse();
			assertThat(isWholeHierarchyCleared_TU).isFalse();
			assertThat(isWholeHierarchyCleared_CU).isFalse();
		}
	}

	private static I_M_HU_Item createHU(@Nullable final I_M_HU_Item parent)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		hu.setM_HU_Item_Parent(parent);
		saveRecord(hu);

		final I_M_HU_Item item = newInstance(I_M_HU_Item.class);
		item.setM_HU(hu);
		saveRecord(item);

		return item;
	}
}
