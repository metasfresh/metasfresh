package de.metas.handlingunits.impl;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;

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

public class HUItemsLocalCacheTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Verifies that the items retrieved by {@link HUItemsLocalCache#retrieveItems(org.adempiere.model.IContextAware, I_M_HU)} are neatly ordered.
	 * <p>
	 * The ordering is important: when we unload thing from a HU, we first want to unload from its included "real" HUs and only afterwards from its "aggregate"/compressed HU. 
	 */
	@Test
	public void testRetrieveItemsOrdered()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.save(hu);

		final I_M_HU_Item item1 = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		item1.setItemType(X_M_HU_Item.ITEMTYPE_PackingMaterial);
		item1.setM_HU(hu);
		InterfaceWrapperHelper.save(item1);

		final I_M_HU_Item item2 = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		item2.setItemType(X_M_HU_Item.ITEMTYPE_PackingMaterial);
		item2.setM_HU(hu);
		InterfaceWrapperHelper.save(item2);

		final I_M_HU_Item item3 = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		item3.setItemType(X_M_HU_Item.ITEMTYPE_Material);
		item3.setM_HU(hu);
		InterfaceWrapperHelper.save(item3);

		final I_M_HU_Item item4 = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		item4.setItemType(X_M_HU_Item.ITEMTYPE_HandlingUnit);
		item4.setM_HU(hu);
		InterfaceWrapperHelper.save(item4);

		final I_M_HU_Item item5 = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		item5.setItemType(X_M_HU_Item.ITEMTYPE_HUAggregate);
		item5.setM_HU(hu);
		InterfaceWrapperHelper.save(item5);

		final List<I_M_HU_Item> items = HUItemsLocalCache.getCreate(hu).retrieveItems(InterfaceWrapperHelper.getContextAware(hu), hu);
		assertThat(items.size(), is(5));
		assertThat(items.get(0).getItemType(), is(X_M_HU_Item.ITEMTYPE_Material));
		assertThat(items.get(1).getItemType(), is(X_M_HU_Item.ITEMTYPE_HandlingUnit));
		assertThat(items.get(2).getItemType(), is(X_M_HU_Item.ITEMTYPE_HUAggregate));

		assertThat(items.get(3).getItemType(), is(X_M_HU_Item.ITEMTYPE_PackingMaterial));
		assertThat(items.get(4).getItemType(), is(X_M_HU_Item.ITEMTYPE_PackingMaterial));
		assertThat(items.get(4).getM_HU_Item_ID(), greaterThan(items.get(3).getM_HU_Item_ID()));
	}

}
