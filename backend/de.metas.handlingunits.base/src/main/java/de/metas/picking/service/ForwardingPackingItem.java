package de.metas.picking.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import java.util.Set;
import java.util.function.Predicate;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Abstract {@link IPackingItem} implementation which forwards all calls to {@link #getDelegate()}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
abstract class ForwardingPackingItem implements IPackingItem
{
	protected abstract IPackingItem getDelegate();

	// Following methods are quite critical and in most cases would be overwritten, so it's better to not implement them here.
	//@formatter:off
	@Override
	public abstract boolean isSameAs(final IPackingItem item);
	@Override
	public abstract IPackingItem copy();
	//@formatter:on

	@Override
	public I_C_UOM getC_UOM()
	{
		return getDelegate().getC_UOM();
	}

	@Override
	public PackingItemGroupingKey getGroupingKey()
	{
		return getDelegate().getGroupingKey();
	}

	@Override
	public BPartnerId getBPartnerId()
	{
		return getDelegate().getBPartnerId();
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId()
	{
		return getDelegate().getBPartnerLocationId();
	}

	@Override
	public void setPartsFrom(final IPackingItem packingItem)
	{
		getDelegate().setPartsFrom(packingItem);
	}

	@Override
	public HUPIItemProductId getPackingMaterialId()
	{
		return getDelegate().getPackingMaterialId();
	}

	@Override
	public void addParts(final IPackingItem packingItem)
	{
		getDelegate().addParts(packingItem);
	}

	public final IPackingItem addPartsAndReturn(final IPackingItem packingItem)
	{
		return getDelegate().addPartsAndReturn(packingItem);
	}

	@Override
	public void addParts(final PackingItemParts toAdd)
	{
		getDelegate().addParts(toAdd);
	}

	@Override
	public Set<WarehouseId> getWarehouseIds()
	{
		return getDelegate().getWarehouseIds();
	}

	@Override
	public Set<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return getDelegate().getShipmentScheduleIds();
	}

	@Override
	public IPackingItem subtractToPackingItem(
			final Quantity subtrahent,
			final Predicate<PackingItemPart> acceptPartPredicate)
	{
		return getDelegate().subtractToPackingItem(subtrahent, acceptPartPredicate);
	}

	@Override
	public PackingItemParts subtract(final Quantity subtrahent)
	{
		return getDelegate().subtract(subtrahent);
	}

	@Override
	public PackingItemParts getParts()
	{
		return getDelegate().getParts();
	}

	@Override
	public ProductId getProductId()
	{
		return getDelegate().getProductId();
	}

	@Override
	public Quantity getQtySum()
	{
		return getDelegate().getQtySum();
	}
}
