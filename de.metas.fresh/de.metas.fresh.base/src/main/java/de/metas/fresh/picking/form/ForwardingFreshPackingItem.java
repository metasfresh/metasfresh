package de.metas.fresh.picking.form;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

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
	public I_C_BPartner getC_BPartner()
	{
		return getDelegate().getC_BPartner();
	}

	@Override
	public int getC_BPartner_ID()
	{
		return getDelegate().getC_BPartner_ID();
	}

	@Override
	public void setSchedules(final IPackingItem packingItem)
	{
		getDelegate().setSchedules(packingItem);
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return getDelegate().getM_HU_PI_Item_Product();
	}

	@Override
	public void addSchedules(final IPackingItem packingItem)
	{
		getDelegate().addSchedules(packingItem);
	}

	@Override
	public I_C_BPartner_Location getC_BPartner_Location()
	{
		return getDelegate().getC_BPartner_Location();
	}

	@Override
	public void addSchedules(final Map<I_M_ShipmentSchedule, BigDecimal> toAdd)
	{
		getDelegate().addSchedules(toAdd);
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return getDelegate().getC_BPartner_Location_ID();
	}

	@Override
	public Set<I_M_Warehouse> getWarehouses()
	{
		return getDelegate().getWarehouses();
	}

	@Override
	public Map<I_M_ShipmentSchedule, BigDecimal> subtract(final BigDecimal subtrahent, final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate)
	{
		return getDelegate().subtract(subtrahent, acceptShipmentSchedulePredicate);
	}

	@Override
	public Set<Integer> getWarehouseIds()
	{
		return getDelegate().getWarehouseIds();
	}

	@Override
	public IFreshPackingItem subtractToPackingItem(final BigDecimal subtrahent, final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate)
	{
		return getDelegate().subtractToPackingItem(subtrahent, acceptShipmentSchedulePredicate);
	}

	@Override
	public Map<I_M_ShipmentSchedule, BigDecimal> subtract(final BigDecimal subtrahent)
	{
		return getDelegate().subtract(subtrahent);
	}

	@Override
	public Map<I_M_ShipmentSchedule, BigDecimal> getQtys()
	{
		return getDelegate().getQtys();
	}

	@Override
	public BigDecimal getQtyForSched(final I_M_ShipmentSchedule sched)
	{
		return getDelegate().getQtyForSched(sched);
	}

	@Override
	public void addSingleSched(final I_M_ShipmentSchedule sched)
	{
		getDelegate().addSingleSched(sched);
	}

	@Override
	public int getProductId()
	{
		return getDelegate().getProductId();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return getDelegate().getM_Product();
	}

	@Override
	public BigDecimal retrieveVolumeSingle(final String trxName)
	{
		return getDelegate().retrieveVolumeSingle(trxName);
	}

	@Override
	public BigDecimal computeWeight()
	{
		return getDelegate().computeWeight();
	}

	@Override
	public BigDecimal retrieveWeightSingle(final String trxName)
	{
		return getDelegate().retrieveWeightSingle(trxName);
	}

	@Override
	public void setQtyForSched(final I_M_ShipmentSchedule sched, final BigDecimal qty)
	{
		getDelegate().setQtyForSched(sched, qty);
	}

	@Override
	public BigDecimal getQtySum()
	{
		return getDelegate().getQtySum();
	}

	@Override
	public Set<I_M_ShipmentSchedule> getShipmentSchedules()
	{
		return getDelegate().getShipmentSchedules();
	}

	@Override
	public void setClosed(final boolean isClosed)
	{
		getDelegate().setClosed(isClosed);
	}

	@Override
	public boolean isClosed()
	{
		return getDelegate().isClosed();
	}
}
