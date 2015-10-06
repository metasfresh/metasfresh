/**
 * 
 */
package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.fresh.base
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import de.metas.adempiere.form.AbstractPackingItem;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * Item to be packed.
 * 
 * @author cg
 * 
 */
public class FreshPackingItem extends AbstractPackingItem
{
	private I_C_BPartner partner; // lazy loaded
	private I_C_BPartner_Location bpLocation; // lazy loaded

	/**
	 * Copy constructor
	 * 
	 * @param orig
	 */
	public FreshPackingItem(final AbstractPackingItem orig)
	{
		this(orig.getQtys(), orig.getGroupingKey(), orig.getTrxName());
	}

	public FreshPackingItem(final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys, final String trxName)
	{
		super(scheds2Qtys, trxName);
	}

	private FreshPackingItem(final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys, final int groupingKey, final String trxName)
	{
		super(scheds2Qtys, groupingKey, trxName);
	}

	@Override
	protected int computeGroupingKey(final I_M_ShipmentSchedule sched)
	{
		final boolean includeBPartner = true;
		return Services.get(IShipmentScheduleBL.class).mkKeyForGrouping(sched, includeBPartner).hashCode();
	}

	public I_C_BPartner getC_BPartner()
	{
		if (partner == null)
		{
			final int partnerId = getBpartnerId();
			if (partnerId > 0)
			{
				partner = InterfaceWrapperHelper.create(Env.getCtx(), partnerId, I_C_BPartner.class, ITrx.TRXNAME_None);
			}
		}
		return partner;
	}

	public int getBpartnerId()
	{
		final Set<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return -1;
		}

		// all scheds must have the same partner
		return shipmentSchedules.iterator().next().getC_BPartner_ID();
	}

	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final Set<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return null;
		}

		// all scheds must have the same pip
		final de.metas.handlingunits.model.I_M_ShipmentSchedule ss = InterfaceWrapperHelper.create(shipmentSchedules.iterator().next(), de.metas.handlingunits.model.I_M_ShipmentSchedule.class);
		I_M_HU_PI_Item_Product pip = ss.getM_HU_PI_Item_Product();
		if (pip != null)
		{
			return pip;
		}

		// if is not set, return the one form order line
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(ss.getC_OrderLine(), I_C_OrderLine.class);
		return ol.getM_HU_PI_Item_Product();
	}

	public I_C_BPartner_Location getC_BPartner_Location()
	{
		if (bpLocation == null)
		{
			final int partnerLocId = getBpartnerLocationId();
			if (partnerLocId > 0)
			{
				bpLocation = InterfaceWrapperHelper.create(Env.getCtx(), partnerLocId, I_C_BPartner_Location.class, ITrx.TRXNAME_None);
			}
		}
		return bpLocation;
	}

	public int getBpartnerLocationId()
	{
		final Set<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return -1;
		}

		// all scheds must have the same partner
		return shipmentSchedules.iterator().next().getC_BPartner_Location_ID();
	}

	public Set<I_M_Warehouse> getWarehouses()
	{
		final Set<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return Collections.emptySet();
		}

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final Set<Integer> warehouseIds = new HashSet<Integer>();
		final Set<I_M_Warehouse> warehouses = new HashSet<I_M_Warehouse>();
		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			final I_M_Warehouse warehouse = shipmentScheduleEffectiveBL.getWarehouse(shipmentSchedule);
			if (warehouse == null)
			{
				// shall not be the case, but just to make sure
				continue;
			}

			final int warehouseId = warehouse.getM_Warehouse_ID();
			if (!warehouseIds.add(warehouseId))
			{
				// already added
			}
			warehouses.add(warehouse);
		}

		return warehouses;
	}

	public Set<Integer> getWarehouseIds()
	{
		final Set<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return Collections.emptySet();
		}

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final Set<Integer> warehouseIds = new HashSet<Integer>();
		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			final I_M_Warehouse warehouse = shipmentScheduleEffectiveBL.getWarehouse(shipmentSchedule);
			if (warehouse == null)
			{
				// shall not be the case, but just to make sure
				continue;
			}

			final int warehouseId = warehouse.getM_Warehouse_ID();
			warehouseIds.add(warehouseId);
		}

		return warehouseIds;
	}

	/**
	 * Subtracts the given quantity from this packing item and create a new packing item with it.
	 * 
	 * @param subtrahent
	 * @param acceptShipmentSchedulePredicate
	 * @return
	 */
	public FreshPackingItem subtractToPackingItem(final BigDecimal subtrahent, final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate)
	{
		final Map<I_M_ShipmentSchedule, BigDecimal> sched2qty = subtract(subtrahent, acceptShipmentSchedulePredicate);
		return new FreshPackingItem(sched2qty, getTrxName());
	}

	public FreshPackingItem copy()
	{
		return new FreshPackingItem(this);
	}

	@Override
	public String toString()
	{
		return "FreshPackingItem [partner=" + partner + ", bpLocation=" + bpLocation + ", isClosed()=" + isClosed() + ", getQtySum()=" + getQtySum() + ", getM_Product()=" + getM_Product()
				+ ", getC_UOM()=" + getC_UOM() + "]";
	}

}
