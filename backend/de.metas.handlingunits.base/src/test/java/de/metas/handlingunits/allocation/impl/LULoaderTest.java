package de.metas.handlingunits.allocation.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.business.BusinessTestHelper.createBPartner;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public class LULoaderTest extends AbstractHUTest
{
	private final I_C_BPartner bpartner_NULL = null;
	private BPartnerId bpartner1;
	private BPartnerId bpartner2;

	private I_M_HU_PI tuPI1;
	private I_M_HU_PI tuPI2;

	private I_M_HU_PI luPI1;

	private LULoader luLoader;

	/**
	 * Creates a bunch of packing instructions and stuff, to give the code under test a change to choose the right ones.
	 */
	@Override
	protected void initialize()
	{
		bpartner1 = BPartnerId.ofRepoId(createBPartner("BPartner1").getC_BPartner_ID());
		bpartner2 = BPartnerId.ofRepoId(createBPartner("BPartner2").getC_BPartner_ID());
		createBPartner("BPartner3");

		tuPI1 = helper.createHUDefinition("TU1", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		tuPI2 = helper.createHUDefinition("TU2", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		helper.createHUDefinition("TU3", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		luPI1 = helper.createHUDefinition("LU1", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		helper.createHUDefinition("LU2", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		helper.createHUDefinition("LU3", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		luLoader = new LULoader(helper.getHUContext());
	}

	/**
	 * Creates four TUs and one-by-one adds them to the {@link LULoader} under test.<br>
	 * Uses an {@link I_M_HU_PI_Item} with a capacity of {@code 2}, so adding the third TU is expected to result in a second LU being created.
	 * Also, the fourth TU is created with a different BPartner, so adding in is expected to result in a third LU being created.
	 */
	@Test
	public void test()
	{
		final BigDecimal luCapacity = new BigDecimal("2");
		final I_M_HU_PI_Item luPI1_item1 = helper.createHU_PI_Item_IncludedHU(luPI1, tuPI1, luCapacity, bpartner1);
		final I_M_HU_PI_Item luPI1_item2 = helper.createHU_PI_Item_IncludedHU(luPI1, tuPI1, luCapacity, (BPartnerId)null);

		helper.createHU_PI_Item_IncludedHU(luPI1, tuPI2, new BigDecimal("10"), bpartner_NULL); // dummy

		// add the first and second TU
		{
			final I_M_HU tu1 = createHU(tuPI1, bpartner1);
			luLoader.addTU(tu1);
			assertLUsCount(1);
			assertLUTURelation(0, luPI1_item1, tu1);
		}

		{
			final I_M_HU tu2 = createHU(tuPI1, bpartner1);
			luLoader.addTU(tu2);
			assertLUsCount(1);
			assertLUTURelation(0, luPI1_item1, tu2);
		}

		// add the third TU. because of luCapacity=2, there shall be a new LU.
		{
			final I_M_HU tu3 = createHU(tuPI1, bpartner1);
			luLoader.addTU(tu3);
			assertLUsCount(2);
			assertLUTURelation(1, luPI1_item1, tu3);
		}

		// add the fourth TU. Because of the different bPartner, it shall be yet another lu, and because luPI1_item1 was specific to bpartner1, it shall use luPI1_item2
		{
			final I_M_HU tu4 = createHU(tuPI1, bpartner2);
			luLoader.addTU(tu4);
			assertLUsCount(3);
			assertLUTURelation(2, luPI1_item2, tu4);
		}

	}

	/**
	 * Simply creates an {@link I_M_HU} manually.
	 */
	private final I_M_HU createHU(final I_M_HU_PI huPI, final BPartnerId bpartnerId)
	{
		final I_M_HU_PI_Version piVersion = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(huPI);
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.contextProvider);
		hu.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		hu.setM_HU_PI_Version(piVersion);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);

		InterfaceWrapperHelper.save(hu);
		return hu;

	}

	private void assertLUTURelation(final int luHUIndex, final I_M_HU_PI_Item luPIItem, final I_M_HU tuHU)
	{
		final List<I_M_HU> luHUs = luLoader.getLU_HUs();
		final I_M_HU luHU = luHUs.get(luHUIndex);

		final I_M_HU_Item tuParentHUItem = tuHU.getM_HU_Item_Parent();
		final I_M_HU_PI_Item tuParentPIItem = Services.get(IHandlingUnitsBL.class).getPIItem(tuParentHUItem);
		Assertions.assertEquals(luPIItem,// expected,
				tuParentPIItem, // actual
				"TU was not added to right LU PI Item: " + tuHU
		);

		final I_M_HU tuParentHU = tuParentHUItem.getM_HU();
		Assertions.assertEquals(luHU,// expected,
				tuParentHU,// actual
				"Invalid LU for TU=" + tuHU
		);
	}

	private void assertLUsCount(final int expectedCount)
	{
		final List<I_M_HU> luHUs = luLoader.getLU_HUs();
		Assertions.assertEquals(expectedCount, luHUs.size(), "Invalid LUs count: " + luHUs);

	}
}
