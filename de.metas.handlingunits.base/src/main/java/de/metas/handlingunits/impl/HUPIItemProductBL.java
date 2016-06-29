package de.metas.handlingunits.impl;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductDisplayNameBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

public class HUPIItemProductBL implements IHUPIItemProductBL
{
	@Override
	public List<I_M_HU_PI_Item_Product> getCompatibleItemDefProducts(final I_M_HU_PI_Version version, final I_M_Product product)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final List<I_M_HU_PI_Item_Product> result = new ArrayList<I_M_HU_PI_Item_Product>();

		final I_C_BPartner bpartner = null;
		final List<I_M_HU_PI_Item> versionPIItems = handlingUnitsDAO.retrievePIItems(version, bpartner);

		final List<I_M_HU_PI_Item> itemDefs = getNestedMaterialPIItems(versionPIItems);
		for (final I_M_HU_PI_Item itemDef : itemDefs)
		{
			Check.assume(X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemDef.getItemType()), "{} item type is Material", itemDef);
			final I_M_HU_PI_Item_Product itemProduct = Services.get(IHUPIItemProductDAO.class).retrievePIMaterialItemProduct(itemDef, product, SystemTime.asDate());
			if (itemProduct != null && itemProduct.getM_HU_PI_Item_Product_ID() > 0)
			{
				result.add(itemProduct);
			}
		}

		return result;
	}

	@Override
	public I_M_HU_PI_Item_Product getCompatibleItemDefProduct(final I_M_HU_PI_Version version, final I_M_Product product)
	{
		final List<I_M_HU_PI_Item_Product> compatiblePIItemProducts = getCompatibleItemDefProducts(version, product);
		return compatiblePIItemProducts.get(0); // FIXME criteria
	}

	private List<I_M_HU_PI_Item> getNestedMaterialPIItems(final List<I_M_HU_PI_Item> itemDefs)
	{
		if (itemDefs == null || itemDefs.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<I_M_HU_PI_Item> nestedItemDefinitions = new ArrayList<I_M_HU_PI_Item>();
		for (final I_M_HU_PI_Item itemDef : itemDefs)
		{
			final String itemType = itemDef.getItemType();
			if (X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemType))
			{
				nestedItemDefinitions.add(itemDef);
			}
			else if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
			{
				// get nested items for included HU PI
				final List<I_M_HU_PI_Item> nestedItems = Services.get(IHandlingUnitsDAO.class).retrievePIItems(itemDef.getIncluded_HU_PI(), null);

				// recursively get nested items
				final List<I_M_HU_PI_Item> filteredAndNestedItems = getNestedMaterialPIItems(nestedItems);
				nestedItemDefinitions.addAll(filteredAndNestedItems);
			}
		}
		return nestedItemDefinitions;
	}

	@Override
	public boolean isCompatibleProduct(final I_M_HU_PI_Version version, final I_M_Product product)
	{
		final List<I_M_HU_PI_Item_Product> compatiblePIDefProducts = getCompatibleItemDefProducts(version, product);
		if (compatiblePIDefProducts == null || compatiblePIDefProducts.isEmpty())
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isVirtualHUPIItemProduct(final I_M_HU_PI_Item_Product piip)
	{
		return piip.getM_HU_PI_Item_Product_ID() == HUPIItemProductDAO.VIRTUAL_HU_PI_Item_Product_ID;
	}

	@Override
	public boolean isCompatibleProduct(final I_M_HU hu, final I_M_Product product)
	{
		Check.assumeNotNull(hu, "hu not null");
		Check.assumeNotNull(product, "product not null");

		final I_M_HU_PI_Version piVersion = hu.getM_HU_PI_Version();
		return isCompatibleProduct(piVersion, product);
	}

	@Override
	public void deleteForItem(final I_M_HU_PI_Item packingInstructionsItem)
	{
		final List<I_M_HU_PI_Item_Product> products = Services.get(IHUPIItemProductDAO.class).retrievePIMaterialItemProducts(packingInstructionsItem);

		for (final I_M_HU_PI_Item_Product product : products)
		{
			InterfaceWrapperHelper.delete(product);
		}
	}

	@Override
	public void setNameAndDescription(final I_M_HU_PI_Item_Product itemProduct)
	{
		//
		// Build itemProduct's name from scratch
		final String nameBuilt = buildDisplayName()
				.setM_HU_PI_Item_Product(itemProduct)
				.buildItemProductDisplayName(); // build it from scratch

		// Set it as Name and Description
		itemProduct.setName(nameBuilt);
		itemProduct.setDescription(nameBuilt);
	}

	@Override
	public IHUPIItemProductDisplayNameBuilder buildDisplayName()
	{
		return new HUPIItemProductDisplayNameBuilder();
	}
}
