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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

@SuppressWarnings("unused")
public class LULoaderTest extends AbstractHUTest
{
	private final I_C_BPartner bpartner_NULL = null;
	private I_C_BPartner bpartner1;
	private I_C_BPartner bpartner2;
	private I_C_BPartner bpartner3;
	private final I_M_HU_PI tuPI_NULL = null;
	private I_M_HU_PI tuPI1;
	private I_M_HU_PI tuPI2;
	private I_M_HU_PI tuPI3;
	private I_M_HU_PI luPI1;
	private I_M_HU_PI luPI2;
	private I_M_HU_PI luPI3;

	private LULoader luLoader;

	@Override
	protected void initialize()
	{
		bpartner1 = helper.createBPartner("BPartner1");
		bpartner2 = helper.createBPartner("BPartner2");
		bpartner3 = helper.createBPartner("BPartner3");

		tuPI1 = helper.createHUDefinition("TU1", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		tuPI2 = helper.createHUDefinition("TU2", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		tuPI3 = helper.createHUDefinition("TU3", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		luPI1 = helper.createHUDefinition("LU1", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		luPI2 = helper.createHUDefinition("LU2", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		luPI3 = helper.createHUDefinition("LU3", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		luLoader = new LULoader(helper.getHUContext());
	}

	@Test
	public void test()
	{
		final I_M_HU_PI_Item luPI1_item1 = helper.createHU_PI_Item_IncludedHU(luPI1, tuPI1, new BigDecimal("2"), bpartner1);
		helper.createHU_PI_Item_IncludedHU(luPI1, tuPI2, new BigDecimal("10"), bpartner_NULL); // dummy

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

		{
			final I_M_HU tu3 = createHU(tuPI1, bpartner1);
			luLoader.addTU(tu3);
			assertLUsCount(2);
			assertLUTURelation(1, luPI1_item1, tu3);
		}

	}

	private final I_M_HU createHU(final I_M_HU_PI huPI, final I_C_BPartner bpartner)
	{
		final I_M_HU_PI_Version piVersion = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(huPI);
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.contextProvider);
		hu.setC_BPartner(bpartner);
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
		final I_M_HU_PI_Item tuParentPIItem = tuParentHUItem.getM_HU_PI_Item();
		Assert.assertEquals("TU was not added to right LU PI Item: " + tuHU,
				luPIItem,// expected,
				tuParentPIItem// actual
		);

		final I_M_HU tuParentHU = tuParentHUItem.getM_HU();
		Assert.assertEquals("Invalid LU for TU=" + tuHU,
				luHU,// expected,
				tuParentHU// actual
		);
	}

	private void assertLUsCount(final int expectedCount)
	{
		final List<I_M_HU> luHUs = luLoader.getLU_HUs();
		Assert.assertEquals("Invalid LUs count: " + luHUs, expectedCount, luHUs.size());

	}
}
