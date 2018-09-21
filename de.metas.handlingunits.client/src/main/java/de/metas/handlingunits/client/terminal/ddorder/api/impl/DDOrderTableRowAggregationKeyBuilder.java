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


import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine_Alternative;

import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Check;
import de.metas.util.Pair;

/**
 * @author al
 */
/* package */class DDOrderTableRowAggregationKeyBuilder
{
	private I_DD_OrderLine _ddOrderLine;
	private I_C_BPartner bpartner;
	private I_M_Locator locatorFrom;
	private I_M_Locator locatorTo;
	private I_M_HU_PI_Item_Product piItemProduct;
	private final SortedMap<Integer, Pair<I_M_Product, Integer>> productAndUoms = new TreeMap<>();

	public DDOrderTableRowAggregationKeyBuilder()
	{
		super();
	}

	public DDOrderTableRowAggregationKey buildKey()
	{
		Check.assumeNotNull(_ddOrderLine, "ddOrderLine was set");

		return new DDOrderTableRowAggregationKey(bpartner, locatorFrom, locatorTo, piItemProduct, productAndUoms);
	}

	public DDOrderTableRowAggregationKeyBuilder setDD_OrderLine(final org.eevolution.model.I_DD_OrderLine ddOrderLine0)
	{
		Check.assumeNotNull(ddOrderLine0, "ddOrderLine not null");
		Check.assumeNull(_ddOrderLine, "ddOrderLine was not set before");

		_ddOrderLine = InterfaceWrapperHelper.create(ddOrderLine0, I_DD_OrderLine.class);

		//
		// C_BPartner
		final I_DD_Order ddOrder = _ddOrderLine.getDD_Order();
		bpartner = ddOrder.getC_BPartner();

		//
		// Locator From
		locatorFrom = _ddOrderLine.getM_Locator();

		//
		// Locator To
		locatorTo = _ddOrderLine.getM_LocatorTo();

		//
		// PI Item Product
		piItemProduct = _ddOrderLine.getM_HU_PI_Item_Product();

		//
		// Products
		addProductAndUOM(_ddOrderLine.getM_Product(), _ddOrderLine.getC_UOM_ID());

		return this;
	}

	private final I_DD_OrderLine getDD_OrderLine()
	{
		Check.assumeNotNull(_ddOrderLine, "ddOrderLine not null");
		return _ddOrderLine;
	}

	private final void addProductAndUOM(final I_M_Product product, final int uomId)
	{
		final int productId = product.getM_Product_ID();
		final Pair<I_M_Product, Integer> productAndUOM = new Pair<>(product, uomId);
		productAndUoms.put(productId, productAndUOM);
	}

	public DDOrderTableRowAggregationKeyBuilder addAlternative(final I_DD_OrderLine_Alternative alternative)
	{
		if (alternative == null)
		{
			return this;
		}

		final I_DD_OrderLine ddOrderLine = getDD_OrderLine();
		Check.assume(ddOrderLine.getDD_OrderLine_ID() == alternative.getDD_OrderLine_ID(), "Alternative is for another DD_OrderLine"); // shall not happen

		addProductAndUOM(alternative.getM_Product(), alternative.getC_UOM_ID());

		return this;
	}

	public DDOrderTableRowAggregationKeyBuilder addAlternatives(final Collection<I_DD_OrderLine_Alternative> alternatives)
	{
		if (alternatives == null || alternatives.isEmpty())
		{
			return this;
		}

		for (final I_DD_OrderLine_Alternative alternative : alternatives)
		{
			addAlternative(alternative);
		}

		return this;
	}
}
