/**
 *
 */
package de.metas.picking.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.legacy.form.AbstractPackingItem;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/**
 * Item to be packed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class FreshPackingItem extends AbstractPackingItem implements IFreshPackingItem
{
	private I_C_BPartner partner; // lazy loaded
	private I_C_BPartner_Location bpLocation; // lazy loaded

	FreshPackingItem(final Map<I_M_ShipmentSchedule, Quantity> scheds2Qtys)
	{
		super(scheds2Qtys);
	}

	/**
	 * Copy constructor.
	 *
	 * @param item must be <code>instanceof</code> {@link FreshPackingItem}.
	 */
	FreshPackingItem(final IPackingItem item)
	{
		super(item);

		final FreshPackingItem itemCasted = cast(item);
		partner = itemCasted.partner;
		bpLocation = itemCasted.bpLocation;
	}

	/**
	 * Similar to {@link #FreshPackingItem(IPackingItem)}, but updates this existing instance instead of creawting a new one.
	 *
	 * @param item must be <code>instanceof</code> {@link FreshPackingItem}.
	 */
	public void updateFrom(final IFreshPackingItem item)
	{
		super.updateFrom(item);
		final FreshPackingItem itemCasted = cast(item);
		partner = itemCasted.partner;
		bpLocation = itemCasted.bpLocation;
	}

	@Override
	protected int computeGroupingKey(final I_M_ShipmentSchedule sched)
	{
		final boolean includeBPartner = true;
		return Services.get(IShipmentScheduleBL.class).mkKeyForGrouping(sched, includeBPartner).hashCode();
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		if (partner == null)
		{
			final int partnerId = getC_BPartner_ID();
			if (partnerId > 0)
			{
				partner = InterfaceWrapperHelper.create(Env.getCtx(), partnerId, I_C_BPartner.class, ITrx.TRXNAME_None);
			}
		}
		return partner;
	}

	@Override
	public int getC_BPartner_ID()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return -1;
		}

		// all scheds must have the same partner, so it's enough to only look at the first one
		return shipmentSchedules.iterator().next().getC_BPartner_ID();
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return null;
		}

		// all scheds must have the same pip
		final de.metas.handlingunits.model.I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(shipmentSchedules.iterator().next(), de.metas.handlingunits.model.I_M_ShipmentSchedule.class);
		final I_M_HU_PI_Item_Product pip = shipmentSchedule.getM_HU_PI_Item_Product();
		if (pip != null)
		{
			return pip;
		}

		if (shipmentSchedule.getC_OrderLine_ID() > 0)
		{
			// if is not set, return the one form order line
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);
			return ol.getM_HU_PI_Item_Product();
		}

		return null;
	}

	@Override
	public I_C_BPartner_Location getC_BPartner_Location()
	{
		if (bpLocation == null)
		{
			final int partnerLocId = getC_BPartner_Location_ID();
			if (partnerLocId > 0)
			{
				bpLocation = InterfaceWrapperHelper.create(Env.getCtx(), partnerLocId, I_C_BPartner_Location.class, ITrx.TRXNAME_None);
			}
		}
		return bpLocation;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return -1;
		}

		// all scheds must have the same partner, so it's enough to only look at the first one

		// #100 FRESH-435: use the schedule's *effective* location, just as everywhere else.
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final int bpartnerLocationId = shipmentScheduleEffectiveBL.getC_BP_Location_ID(shipmentSchedules.iterator().next());
		return bpartnerLocationId;
	}

	@Override
	public Set<I_M_Warehouse> getWarehouses()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return Collections.emptySet();
		}

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final Set<Integer> warehouseIds = new HashSet<>();
		final Set<I_M_Warehouse> warehouses = new HashSet<>();
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

	@Override
	public Set<Integer> getWarehouseIds()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return Collections.emptySet();
		}

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final Set<Integer> warehouseIds = new HashSet<>();
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

	@Override
	public IFreshPackingItem subtractToPackingItem(
			@NonNull final Quantity subtrahent,
			@Nullable final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate)
	{
		final Map<I_M_ShipmentSchedule, Quantity> sched2qty = subtract(subtrahent, acceptShipmentSchedulePredicate);
		return FreshPackingItemHelper.create(sched2qty);
	}

	@Override
	public IFreshPackingItem copy()
	{
		return new FreshPackingItem(this);
	}

	@Override
	public String toString()
	{
		return "FreshPackingItem [partner=" + partner
				+ ", bpLocation=" + bpLocation
				+ ", isClosed()=" + isClosed()
				+ ", getQtySum()=" + getQtySum()
				+ ", getM_Product()=" + getM_Product()
				+ ", getC_UOM()=" + getC_UOM() + "]";
	}

	/**
	 *
	 * @param item
	 * @return
	 * @throws IllegalArgumentException if the given <code>item</code> is not a {@link FreshPackingItem}
	 */
	private static final FreshPackingItem cast(final IPackingItem item)
	{
		if (!(item instanceof FreshPackingItem))
		{
			throw new IllegalArgumentException("Item " + item + " does not implement " + FreshPackingItem.class);
		}
		return (FreshPackingItem)item;
	}
}
