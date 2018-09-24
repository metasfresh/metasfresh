package de.metas.picking.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.legacy.form.ShipmentScheduleQtyPickedMap;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;

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
 * Abstract {@link IFreshPackingItem} implementation which forwards all calls to {@link #getDelegate()}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class ForwardingFreshPackingItem implements IFreshPackingItem
{
	protected abstract IFreshPackingItem getDelegate();

	// Following methods are quite critical and in most cases would be overwritten, so it's better to not implement them here.
	//@formatter:off
	@Override
	public abstract boolean isSameAs(final IPackingItem item);
	@Override
	public abstract IFreshPackingItem copy();
	//@formatter:on

	@Override
	public I_C_UOM getC_UOM()
	{
		return getDelegate().getC_UOM();
	}

	@Override
	public int getGroupingKey()
	{
		return getDelegate().getGroupingKey();
	}

	@Override
	public void setWeightSingle(final BigDecimal piWeightSingle)
	{
		getDelegate().setWeightSingle(piWeightSingle);
	}

	@Override
	public boolean canAddSchedule(final I_M_ShipmentSchedule schedToAdd)
	{
		return getDelegate().canAddSchedule(schedToAdd);
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
	public void setSchedules(final IPackingItem packingItem)
	{
		getDelegate().setSchedules(packingItem);
	}

	@Override
	public HUPIItemProductId getHUPIItemProductId()
	{
		return getDelegate().getHUPIItemProductId();
	}

	@Override
	public void addSchedules(final IPackingItem packingItem)
	{
		getDelegate().addSchedules(packingItem);
	}

	@Override
	public void addSchedules(final ShipmentScheduleQtyPickedMap toAdd)
	{
		getDelegate().addSchedules(toAdd);
	}

	@Override
	public ShipmentScheduleQtyPickedMap subtract(
			final Quantity subtrahent,
			final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate)
	{
		return getDelegate().subtract(subtrahent, acceptShipmentSchedulePredicate);
	}

	@Override
	public Set<WarehouseId> getWarehouseIds()
	{
		return getDelegate().getWarehouseIds();
	}

	@Override
	public IFreshPackingItem subtractToPackingItem(
			final Quantity subtrahent,
			final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate)
	{
		return getDelegate().subtractToPackingItem(subtrahent, acceptShipmentSchedulePredicate);
	}

	@Override
	public ShipmentScheduleQtyPickedMap subtract(final Quantity subtrahent)
	{
		return getDelegate().subtract(subtrahent);
	}

	@Override
	public ShipmentScheduleQtyPickedMap getQtys()
	{
		return getDelegate().getQtys();
	}

	@Override
	public Quantity getQtyForSched(final I_M_ShipmentSchedule sched)
	{
		return getDelegate().getQtyForSched(sched);
	}

	@Override
	public void addSingleSched(final I_M_ShipmentSchedule sched)
	{
		getDelegate().addSingleSched(sched);
	}

	@Override
	public ProductId getProductId()
	{
		return getDelegate().getProductId();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return getDelegate().getM_Product();
	}

	@Override
	public Quantity retrieveVolumeSingle()
	{
		return getDelegate().retrieveVolumeSingle();
	}

	@Override
	public BigDecimal computeWeightInProductUOM()
	{
		return getDelegate().computeWeightInProductUOM();
	}

	@Override
	public BigDecimal retrieveWeightSingle()
	{
		return getDelegate().retrieveWeightSingle();
	}

	@Override
	public void setQtyForSched(final I_M_ShipmentSchedule sched, final Quantity qty)
	{
		getDelegate().setQtyForSched(sched, qty);
	}

	@Override
	public Quantity getQtySum()
	{
		return getDelegate().getQtySum();
	}

	@Override
	public List<I_M_ShipmentSchedule> getShipmentSchedules()
	{
		return getDelegate().getShipmentSchedules();
	}
}
