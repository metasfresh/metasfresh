/**
 *
 */
package de.metas.picking.service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.UomId;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Util;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.legacy.form.PackingItemGroupingKey;
import de.metas.picking.legacy.form.PackingItemSubtractException;
import de.metas.picking.legacy.form.ShipmentScheduleQtyPickedMap;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Item to be packed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class FreshPackingItem implements IFreshPackingItem
{
	private final ShipmentScheduleQtyPickedMap sched2qty;
	private final PackingItemGroupingKey groupingKey;
	private final I_C_UOM uom;

	FreshPackingItem(final ShipmentScheduleQtyPickedMap sched2qtyParam)
	{
		Check.assume(!sched2qtyParam.isEmpty(), "scheds2Qtys not empty");
		this.sched2qty = sched2qtyParam.copy();

		this.groupingKey = sched2qty.mapReduce(FreshPackingItem::computeGroupingKey).get();

		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
		final UomId uomId = sched2qty.mapReduce(shipmentScheduleBL::getUomIdOfProduct).get();
		uom = uomsRepo.getById(uomId);
	}

	FreshPackingItem(@NonNull final FreshPackingItem copyFromItem)
	{
		sched2qty = copyFromItem.sched2qty.copy();
		groupingKey = copyFromItem.groupingKey;
		uom = copyFromItem.uom;
	}

	private static PackingItemGroupingKey computeGroupingKey(final I_M_ShipmentSchedule sched)
	{
		final ProductId productId = ProductId.ofRepoId(sched.getM_Product_ID());

		final TableRecordReference documentLineRef;
		final IProductBL productBL = Services.get(IProductBL.class);
		if (productBL.isDiverse(productId))
		{
			// Diverse/misc products can't be merged into one pi because they could represent totally different products.
			// So we are using (AD_Table_ID, Record_ID) (which are unique) to make the group unique.
			documentLineRef = TableRecordReference.of(sched.getAD_Table_ID(), sched.getRecord_ID());
		}
		else
		{
			documentLineRef = null;
		}

		// #100 FRESH-435: in FreshPackingItem we rely on all scheds having the same effective C_BPartner_Location_ID, so we need to include that in the key
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final BPartnerLocationId bpLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(sched);

		return PackingItemGroupingKey.builder()
				.productId(productId)
				.bpartnerLocationId(bpLocationId)
				.documentLineRef(documentLineRef)
				.build();
	}

	public void updateFrom(@NonNull final FreshPackingItem item)
	{
		sched2qty.setFrom(item.sched2qty);

		// this.groupingKey = item.groupingKey;
		// this.uom = item.uom;
	}

	@Override
	public final List<I_M_ShipmentSchedule> getShipmentSchedules()
	{
		return sched2qty.getShipmentSchedules();
	}

	@Override
	public final Quantity getQtySum()
	{
		return sched2qty.getQtySum()
				.orElseGet(() -> Quantity.zero(getC_UOM()));
	}

	@Override
	public final ProductId getProductId()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = getShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return null;
		}

		// all scheds must have the same product
		final I_M_ShipmentSchedule firstShipmentSchedule = shipmentSchedules.get(0);
		return ProductId.ofRepoId(firstShipmentSchedule.getM_Product_ID());
	}

	@Override
	public final Quantity getQtyForSched(final I_M_ShipmentSchedule sched)
	{
		return sched2qty.getQty(sched);
	}

	@Override
	public final ShipmentScheduleQtyPickedMap getQtys()
	{
		return sched2qty.copy();
	}

	@Override
	public final ShipmentScheduleQtyPickedMap subtract(final Quantity subtrahent)
	{
		final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate = null; // no filter, i.e. accept all
		return subtract(subtrahent, acceptShipmentSchedulePredicate);
	}

	protected final ShipmentScheduleQtyPickedMap subtract(
			@NonNull final Quantity subtrahent,
			@Nullable final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate)
	{
		final ShipmentScheduleQtyPickedMap result = ShipmentScheduleQtyPickedMap.newInstance();

		//
		// Qty that needs to be subtracted
		Quantity qtyToSubtract = subtrahent;
		boolean allowRemainingQtyToSubtract = false;

		//
		// Create a copy of sched2qty and work on it
		// Later, after everything is validated we will copy it back.
		// We are doing this because we want to avoid inconsistencies in case an exception popups
		final ShipmentScheduleQtyPickedMap sched2qtyCopy = sched2qty.copy();

		//
		// Iterate all schedule/qty entries and subtract requested qty
		// for (final Iterator<Entry<I_M_ShipmentSchedule, Quantity>> it = sched2qtyCopy.entrySet().iterator(); it.hasNext();)
		for (final I_M_ShipmentSchedule sched : sched2qtyCopy.getShipmentSchedules())
		{
			//
			// If there is no qty to subtract, stop here
			if (qtyToSubtract.signum() <= 0)
			{
				break;
			}

			//
			// Make sure current shipment schedule is accepted by our predicate (if any)
			if (acceptShipmentSchedulePredicate != null && !acceptShipmentSchedulePredicate.test(sched))
			{
				// NOTE: we are not removing from map because remaining items will be copied back at the end
				// it.remove();

				// In case we excluded a shipment schedule, we cannot enforce to always have QtyToSubtract=0 at the end.
				// NOTE: in future we could add a parameter or something to enforce this or not.
				// Then please check which is calling this method, because there is BL which rely on this logic
				// (e.g. Kommissioner Terminal, when we pack the qty which was not found in HUs, but we are doing this only for those shipment schedules which have Force delivery rule)
				allowRemainingQtyToSubtract = true;

				// Skip this record
				continue;
			}

			final Quantity schedQty = sched2qtyCopy.getQty(sched);
			final Quantity schedQtySubtracted;

			//
			// Current qtyToSubtract is bigger then current schedule's available Qty
			// => subtract only schedule's available Qty
			if (qtyToSubtract.compareTo(schedQty) > 0)
			{
				schedQtySubtracted = schedQty;
				sched2qtyCopy.remove(sched);
			}
			// Current QtyToSubtract is lower or equal with current schedule's available Qty
			// => subtract the whole qtyToSubtract
			else
			{
				schedQtySubtracted = qtyToSubtract;
				final Quantity schedQtyRemaining = schedQty.subtract(schedQtySubtracted);
				sched2qtyCopy.setQty(sched, schedQtyRemaining);
			}

			//
			// Update qtyToSubtract
			qtyToSubtract = qtyToSubtract.subtract(schedQtySubtracted);

			//
			// Add our subtracted schedule/qty pair to result to be returned
			result.setQty(sched, schedQtySubtracted);
		}

		//
		// If we could not subtract the whole qty that was asked, throw an exception
		if (!allowRemainingQtyToSubtract && qtyToSubtract.signum() != 0)
		{
			throw new PackingItemSubtractException(this, subtrahent, qtyToSubtract);
		}

		//
		// If there were changes (i.e. result is not empty) then we need to copy back our modified sched2qty map
		if (!result.isEmpty())
		{
			sched2qty.setFrom(sched2qtyCopy);
		}

		//
		// Return the result
		return result;
	}

	@Override
	public final void addSchedules(final ShipmentScheduleQtyPickedMap toAdd)
	{
		final boolean removeExistingOnes = false;
		addSchedules(toAdd, removeExistingOnes);
	}

	private final void addSchedules(
			@NonNull final ShipmentScheduleQtyPickedMap toAdd,
			final boolean removeExistingOnes)
	{
		//
		// Make sure we are allowed to add those shipment schedules
		for (final I_M_ShipmentSchedule schedToAdd : toAdd.getShipmentSchedules())
		{
			if (!canAddSchedule(schedToAdd))
			{
				throw new AdempiereException(schedToAdd + " can't be added to " + this);
			}
		}

		//
		// Remove existing schedules (if asked)
		// NOTE: we remove existing ones AFTER we validate because "canAddSchedule" always returns true in case there are no schedules
		if (removeExistingOnes)
		{
			sched2qty.clear();
		}

		//
		// Add shipment schedules
		sched2qty.add(toAdd);
	}

	@Override
	public final void addSchedules(final IPackingItem packingItem)
	{
		addSchedules(packingItem.getQtys());
	}

	@Override
	public final void setSchedules(final IPackingItem packingItem)
	{
		final ShipmentScheduleQtyPickedMap toAdd = packingItem.getQtys();
		final boolean removeExistingOnes = true;
		addSchedules(toAdd, removeExistingOnes);
	}

	private final boolean canAddSchedule(final I_M_ShipmentSchedule schedToAdd)
	{
		if (sched2qty.isEmpty())
		{
			return true;
		}

		return PackingItemGroupingKey.equals(groupingKey, computeGroupingKey(schedToAdd));
	}

	@Override
	public final PackingItemGroupingKey getGroupingKey()
	{
		return groupingKey;
	}

	@Override
	public final I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public boolean isSameAs(final IPackingItem item)
	{
		return Util.same(this, item);
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
	public FreshPackingItem copy()
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
