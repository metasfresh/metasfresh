package de.metas.handlingunits.impl;

import static de.metas.business.BusinessTestHelper.createBPartner;

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
import java.util.Arrays;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Assert;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.Services;
import org.junit.jupiter.api.Test;

public class HandlingUnitsDAOTest extends AbstractHUTest
{
	protected HandlingUnitsDAO dao;

	@Override
	protected void initialize()
	{
		dao = new HandlingUnitsDAO();
		Services.registerService(IHandlingUnitsDAO.class, dao);
	}

	@Test
	public void test_retrieveItems_from_HU()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.contextProvider);
		InterfaceWrapperHelper.save(hu);

		final I_M_HU_Item huItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class, helper.contextProvider);
		huItem.setM_HU(hu);
		InterfaceWrapperHelper.save(huItem);

		final List<I_M_HU_Item> items = dao.retrieveItems(hu);

		Assert.assertEquals("Invalid size: " + items, 1, items.size());
		Assert.assertEquals("Invalid item: " + items, huItem.getM_HU_Item_ID(), items.get(0).getM_HU_Item_ID());
	}

	@Test
	public void test_retrivePIItems()
	{
		final BPartnerId bpartner_NULL = null;
		final BPartnerId bpartner1 = BPartnerId.ofRepoId(createBPartner("BP1").getC_BPartner_ID());
		final BPartnerId bpartner2 = BPartnerId.ofRepoId(createBPartner("BP2").getC_BPartner_ID());
		final BPartnerId bpartner3 = BPartnerId.ofRepoId(createBPartner("BP3").getC_BPartner_ID());
		final I_M_HU_PI piIncluded = null; // not relevant
		final I_M_HU_PI pi = helper.createHUDefinition("PI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		final I_M_HU_PI_Item item1 = helper.createHU_PI_Item_IncludedHU(pi, piIncluded, BigDecimal.ONE, bpartner_NULL);
		final I_M_HU_PI_Item item2 = helper.createHU_PI_Item_IncludedHU(pi, piIncluded, BigDecimal.ONE, bpartner1);
		final I_M_HU_PI_Item item3 = helper.createHU_PI_Item_IncludedHU(pi, piIncluded, BigDecimal.ONE, bpartner2);
		final I_M_HU_PI_Item item4 = helper.createHU_PI_Item_IncludedHU(pi, piIncluded, BigDecimal.ONE, bpartner_NULL);

		Assert.assertEquals("Invalid result for BPartner=" + bpartner_NULL
				, Arrays.asList(item1, item4)
				, dao.retrievePIItems(pi, bpartner_NULL)
				);

		Assert.assertEquals("Invalid result for BPartner=" + bpartner1
				, Arrays.asList(item1, item2, item4)
				, dao.retrievePIItems(pi, bpartner1)
				);

		Assert.assertEquals("Invalid result for BPartner=" + bpartner2
				, Arrays.asList(item1, item3, item4)
				, dao.retrievePIItems(pi, bpartner2)
				);

		Assert.assertEquals("Invalid result for BPartner=" + bpartner3
				, Arrays.asList(item1, item4) // same as for bpartner_NULL
				, dao.retrievePIItems(pi, bpartner3)
				);
	}

}
