package de.metas.handlingunits.impl;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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

public class HandlingUnitsBLTests
{
	private HUTestHelper helper;

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
}
