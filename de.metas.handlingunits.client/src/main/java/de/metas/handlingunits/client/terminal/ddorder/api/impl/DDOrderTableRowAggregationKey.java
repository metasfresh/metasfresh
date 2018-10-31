package de.metas.handlingunits.client.terminal.ddorder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.client.terminal.ddorder.model.DDOrderTableRow;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Check;
import de.metas.util.Pair;

/**
 * Immutable {@link DDOrderTableRow}'s aggregation key
 *
 * @author tsa
 *
 */
public final class DDOrderTableRowAggregationKey
{
	private final I_C_BPartner bpartner;
	private final I_M_Locator locatorFrom;
	private final I_M_Locator locatorTo;
	private final I_M_HU_PI_Item_Product piItemProduct;
	private final List<I_M_Product> products;
	private final Set<Integer> productIds;
	private final ArrayKey hashKey;

	/* package */DDOrderTableRowAggregationKey(final I_C_BPartner bpartner,
			final I_M_Locator locatorFrom,
			final I_M_Locator locatorTo,
			final I_M_HU_PI_Item_Product piItemProduct,
			final SortedMap<Integer, Pair<I_M_Product, Integer>> productAndUOMs)
	{
		super();

		Check.assumeNotNull(bpartner, "bpartner not null");
		this.bpartner = bpartner;

		Check.assumeNotNull(locatorFrom, "locatorFrom not null");
		this.locatorFrom = locatorFrom;

		Check.assumeNotNull(locatorTo, "locatorTo not null");
		this.locatorTo = locatorTo;

		this.piItemProduct = piItemProduct;

		//
		// Build hashKey and collect products
		final List<I_M_Product> products = new ArrayList<>();
		final Set<Integer> productIds = new HashSet<>();
		final ArrayKeyBuilder hashKeyBuilder = Util.mkKey()
				.appendId(bpartner.getC_BPartner_ID())
				.appendId(locatorFrom.getM_Locator_ID())
				.appendId(locatorTo.getM_Locator_ID())
				.appendModelId(piItemProduct);

		for (final Pair<I_M_Product, Integer> productAndUOM : productAndUOMs.values())
		{
			final I_M_Product product = productAndUOM.getFirst();
			final int productId = product.getM_Product_ID();
			final int uomId = productAndUOM.getSecond();

			// Add productId/uomId to hash key builder
			final ArrayKey productAndUOMKey = Util.mkKey(productId, uomId);
			hashKeyBuilder.append(productAndUOMKey);

			// collect product
			productIds.add(productId);
			products.add(product);
		}
		hashKey = hashKeyBuilder.build();
		this.products = Collections.unmodifiableList(products);
		this.productIds = Collections.unmodifiableSet(productIds);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (hashKey == null ? 0 : hashKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final DDOrderTableRowAggregationKey other = (DDOrderTableRowAggregationKey)obj;
		if (hashKey == null)
		{
			if (other.hashKey != null)
			{
				return false;
			}
		}
		else if (!hashKey.equals(other.hashKey))
		{
			return false;
		}
		return true;
	}

	public I_C_BPartner getC_BPartner()
	{
		return bpartner;
	}

	public I_M_Locator getM_LocatorFrom()
	{
		return locatorFrom;
	}

	public I_M_Locator getM_LocatorTo()
	{
		return locatorTo;
	}

	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return piItemProduct;
	}

	public List<I_M_Product> getProducts()
	{
		return products;
	}

	public Set<Integer> getProductIds()
	{
		return productIds;
	}
}
