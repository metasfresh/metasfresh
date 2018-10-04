package de.metas.handlingunits.client.terminal.editor.model.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.compiere.model.I_M_Product;

import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.util.Check;

/**
 * Comparator for HUKeys, generally used in key layouts when adding children (i.e on {@link RootHUKey})
 *
 * @author al
 */
public final class HUKeyComparator implements Comparator<IHUKey>
{
	private final WeakReference<IHUKeyFactory> keyFactoryRef;

	public HUKeyComparator(final IHUKeyFactory keyFactory)
	{
		super();

		keyFactoryRef = new WeakReference<IHUKeyFactory>(keyFactory);
	}

	@Override
	public int compare(final IHUKey key1, final IHUKey key2)
	{
		final IHUKeyFactory keyFactory = keyFactoryRef.get();
		if (keyFactory == null)
		{
			return 0; // always return equal if reference is lost
		}

		//
		// Treat key groupings first
		if (key1.isGrouping() && key2.isGrouping())
		{
			//
			// Get highest groupings first
			return key1.getChildren().size() - key2.getChildren().size();
		}
		else if (key1.isGrouping() && !key2.isGrouping())
		{
			//
			// Always put groupings first (key1 is grouping) => 1 (see compare() JavaDoc)
			return 1;
		}
		else if (!key1.isGrouping() && key2.isGrouping())
		{
			//
			// Always put groupings first (key2 is grouping) => -1 (see compare() JavaDoc)
			return -1;
		}

		//
		// Treat normal keys
		final HUKey huKey1 = HUKey.castIfPossible(key1);
		Check.assumeNotNull(huKey1, "huKey1 not null");

		final HUKey huKey2 = HUKey.castIfPossible(key2);
		Check.assumeNotNull(huKey2, "huKey2 not null");

		//
		// Get full qty of first HU
		final I_M_HU hu1 = huKey1.getM_HU();
		final IHUStorage hu1Storage = keyFactory.getStorageFactory().getStorage(hu1);
		final BigDecimal hu1Qty = hu1Storage.getQtyForProductStorages().getQty();

		final List<IHUProductStorage> hu1ProductStorages = hu1Storage.getProductStorages();
		final String hu1FullProductName = getFullProductName(hu1ProductStorages);

		//
		// Get full qty of second HU
		final I_M_HU hu2 = huKey2.getM_HU();
		final IHUStorage hu2Storage = keyFactory.getStorageFactory().getStorage(hu2);
		final BigDecimal hu2Qty = hu2Storage.getQtyForProductStorages().getQty();

		final List<IHUProductStorage> hu2ProductStorages = hu2Storage.getProductStorages();
		final String hu2FullProductName = getFullProductName(hu2ProductStorages);

		//
		// If both productNames are equal, sort by Qty. Otherwise, comparison result is enough
		final int productNameComparisonResult = hu1FullProductName.compareToIgnoreCase(hu2FullProductName);
		if (productNameComparisonResult == 0)
		{
			//
			// If both qtys are equal, sort by HU Value. Otherwise, comparison result is enough
			final int qtyComparisonResult = hu2Qty.compareTo(hu1Qty); // reverse comparison (descending)
			if (qtyComparisonResult == 0)
			{
				return hu1.getValue().compareTo(hu2.getValue());
			}
			else
			{
				return qtyComparisonResult;
			}
		}
		else
		{
			return productNameComparisonResult;
		}
	}

	private final String getFullProductName(final List<IHUProductStorage> productStorages)
	{
		final Set<String> uniqueProductNames = new TreeSet<String>(new Comparator<String>()
		{
			@Override
			public int compare(final String o1, final String o2)
			{
				return o1.compareToIgnoreCase(o2);
			}
		});

		for (final IHUProductStorage productStorage : productStorages)
		{
			final I_M_Product product = productStorage.getM_Product();
			uniqueProductNames.add(product.getName());
		}

		final StringBuilder fullProductNameBuilder = new StringBuilder();
		for (final String productName : uniqueProductNames)
		{
			fullProductNameBuilder.append(productName);
		}
		return fullProductNameBuilder.toString();
	}
}
