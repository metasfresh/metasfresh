package de.metas.handlingunits.test;

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

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.hamcrest.Matchers;
import org.junit.Assert;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;

public class HUAssertsBuilder
{
	private final String name;
	private final String assertPrefix;
	private final I_M_HU hu;

	private final Object parent;

	public HUAssertsBuilder(final Object parent, final String name, final I_M_HU hu)
	{
		this.hu = hu;
		if (name == null || name.isEmpty())
		{
			this.name = "";
			assertPrefix = "";
		}
		else
		{
			this.name = name.trim();
			assertPrefix = this.name + ": ";
		}

		this.parent = parent;
	}

	public HUAssertsBuilder assumeQty(final I_M_Product product, final int qty)
	{
		return assumeQty(product, new BigDecimal(qty));
	}

	public HUAssertsBuilder assumeQty(final I_M_Product product, final BigDecimal qty)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		final I_C_UOM uom = uomDAO.getById(product.getC_UOM_ID());

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
		final IHUStorage storage = storageFactory.getStorage(hu);
		final BigDecimal qtyActual = storage.getQty(productId, uom);

		Assert.assertThat(assertPrefix + "Invalid qty for product " + product.getValue(),
				qtyActual,
				Matchers.comparesEqualTo(qty));

		return this;
	}

	public HUItemAssertsBuilder handlingUnitItemAt(final String itemType, final int index)
	{
		int currentIndex = 0;
		final List<I_M_HU_Item> items = Services.get(IHandlingUnitsDAO.class).retrieveItems(hu);
		for (final I_M_HU_Item item : items)
		{
			final String currentItemType = Services.get(IHandlingUnitsBL.class).getItemType(item);
			if (currentItemType.equals(itemType))
			{
				if (currentIndex == index)
				{
					// found
					final String name = this.name + "/type=" + itemType + ",index=" + index;
					return new HUItemAssertsBuilder(this, name, item);
				}
				currentIndex++;
			}
		}

		Assert.fail(assertPrefix + "No item was found for itemType=" + itemType + ", index=" + index);
		return null;
	}

	public HUListAssertsBuilder backToList()
	{
		return (HUListAssertsBuilder)parent;
	}
}
