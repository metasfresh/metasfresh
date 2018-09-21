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
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.util.Services;

public class HUItemAssertsBuilder
{
	private final String name;
	private final String assertPrefix;
	private final I_M_HU_Item huItem;

	private final HUAssertsBuilder parent;

	public HUItemAssertsBuilder(final HUAssertsBuilder parent, final String name, final I_M_HU_Item huItem)
	{
		this.huItem = huItem;
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

	public HUAssertsBuilder backToHandlingUnit()
	{
		return parent;
	}

	public HUListAssertsBuilder includedHUs()
	{
		final List<I_M_HU> includedHUs = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(huItem);
		return new HUListAssertsBuilder(this, name, includedHUs);
	}

	public HUItemAssertsBuilder assumeQty(final I_M_Product product, final int qty, final I_C_UOM uom)
	{
		return assumeQty(product, new BigDecimal(qty), uom);
	}

	private HUItemAssertsBuilder assumeQty(final I_M_Product product, final BigDecimal qty, final I_C_UOM uom)
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
		final IHUItemStorage storage = storageFactory.getStorage(huItem);
		final BigDecimal qtyActual = storage.getQty(product, uom);

		Assert.assertThat(assertPrefix + "Invalid qty for product " + product.getValue() + " uom=" + uom.getUOMSymbol(),
				qtyActual,
				Matchers.comparesEqualTo(qty));

		return this;
	}
}
