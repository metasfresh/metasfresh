/**
 *
 */
package de.metas.picking.service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.legacy.form.AbstractPackingItem;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.legacy.form.ShipmentScheduleQtyPickedMap;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Item to be packed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class FreshPackingItem extends AbstractPackingItem implements IFreshPackingItem
{
	FreshPackingItem(final ShipmentScheduleQtyPickedMap scheds2Qtys)
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
	}

	/**
	 * Similar to {@link #FreshPackingItem(IPackingItem)}, but updates this existing instance instead of creawting a new one.
	 *
	 * @param item must be <code>instanceof</code> {@link FreshPackingItem}.
	 */
	public void updateFrom(final IFreshPackingItem item)
	{
		super.updateFrom(item);
	}

	@Override
	public BPartnerId getBPartnerId()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return null;
		}

		// all scheds must have the same partner, so it's enough to only look at the first one
		final I_M_ShipmentSchedule firstShipmentSchedule = shipmentSchedules.get(0);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		return shipmentScheduleEffectiveBL.getBPartnerId(firstShipmentSchedule);
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return null;
		}

		// all scheds must have the same partner, so it's enough to only look at the first one
		// #100 FRESH-435: use the schedule's *effective* location, just as everywhere else.
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final I_M_ShipmentSchedule firstShipmentSchedule = shipmentSchedules.get(0);
		return shipmentScheduleEffectiveBL.getBPartnerLocationId(firstShipmentSchedule);
	}

	@Override
	public HUPIItemProductId getHUPIItemProductId()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return null;
		}

		// all scheds must have the same pip
		final de.metas.handlingunits.model.I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(shipmentSchedules.iterator().next(), de.metas.handlingunits.model.I_M_ShipmentSchedule.class);
		final HUPIItemProductId pip = HUPIItemProductId.ofRepoIdOrNull(shipmentSchedule.getM_HU_PI_Item_Product_ID());
		if (pip != null)
		{
			return pip;
		}

		if (shipmentSchedule.getC_OrderLine_ID() > 0)
		{
			// if is not set, return the one form order line
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);
			return HUPIItemProductId.ofRepoIdOrNull(ol.getM_HU_PI_Item_Product_ID());
		}

		return null;
	}

	@Override
	public Set<WarehouseId> getWarehouseIds()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableSet.of();
		}

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		return shipmentSchedules.stream()
				.map(shipmentScheduleEffectiveBL::getWarehouseId)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public IFreshPackingItem subtractToPackingItem(
			@NonNull final Quantity subtrahent,
			@Nullable final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate)
	{
		final ShipmentScheduleQtyPickedMap sched2qty = subtract(subtrahent, acceptShipmentSchedulePredicate);
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
		return "FreshPackingItem ["
				+ "qtySum=" + getQtySum()
				+ ", productId=" + getProductId()
				+ ", uom=" + getC_UOM() + "]";
	}
}
