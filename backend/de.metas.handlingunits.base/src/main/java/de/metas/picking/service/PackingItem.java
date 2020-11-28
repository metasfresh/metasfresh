/**
 *
 */
package de.metas.picking.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Item to be packed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class PackingItem implements IPackingItem
{
	private final PackingItemParts parts;
	private final PackingItemGroupingKey groupingKey;
	private final I_C_UOM uom;

	PackingItem(final PackingItemParts parts)
	{
		Check.assume(!parts.isEmpty(), "parts not empty");

		this.parts = parts.copy();
		this.groupingKey = parts.mapReduce(PackingItem::computeGroupingKey).get();
		uom = parts.getCommonUOM();
	}

	PackingItem(@NonNull final PackingItem copyFromItem)
	{
		parts = copyFromItem.parts.copy();
		groupingKey = copyFromItem.groupingKey;
		uom = copyFromItem.uom;
	}

	private static PackingItemGroupingKey computeGroupingKey(final PackingItemPart part)
	{
		final ProductId productId = part.getProductId();

		final TableRecordReference documentLineRef;
		final IProductBL productBL = Services.get(IProductBL.class);
		if (productBL.isDiverse(productId))
		{
			// Diverse/misc products can't be merged into one pi because they could represent totally different products.
			// So we are using (AD_Table_ID, Record_ID) (which are unique) to make the group unique.
			documentLineRef = part.getSourceDocumentLineRef();
		}
		else
		{
			documentLineRef = null;
		}

		// #100 FRESH-435: in FreshPackingItem we rely on all scheds having the same effective C_BPartner_Location_ID, so we need to include that in the key
		final BPartnerLocationId bpLocationId = part.getBpartnerLocationId();

		final HUPIItemProductId packingMaterialId = part.getPackingMaterialId();

		return PackingItemGroupingKey.builder()
				.productId(productId)
				.bpartnerLocationId(bpLocationId)
				.documentLineRef(documentLineRef)
				.packingMaterialId(packingMaterialId)
				.build();
	}

	public void updateFrom(@NonNull final PackingItem from)
	{
		parts.setFrom(from.parts);
		// this.groupingKey = from.groupingKey;
		// this.uom = from.uom;
	}

	@Override
	public final Quantity getQtySum()
	{
		return parts.getQtySum()
				.orElseGet(() -> Quantity.zero(getC_UOM()));
	}

	@Override
	public final PackingItemParts getParts()
	{
		return parts.copy();
	}

	@Override
	public final PackingItemParts subtract(final Quantity subtrahent)
	{
		final Predicate<PackingItemPart> acceptShipmentSchedulePredicate = null; // no filter, i.e. accept all
		return subtract(subtrahent, acceptShipmentSchedulePredicate);
	}

	private final PackingItemParts subtract(
			@NonNull final Quantity subtrahent,
			@Nullable final Predicate<PackingItemPart> partsFilter)
	{
		final PackingItemParts result = PackingItemParts.newInstance();

		//
		// Qty that needs to be subtracted
		Quantity qtyToSubtract = subtrahent;
		boolean allowRemainingQtyToSubtract = false;

		//
		// Create a copy of parts and work on it
		// Later, after everything is validated we will copy it back.
		// We are doing this because we want to avoid inconsistencies in case an exception popups
		final PackingItemParts remainingParts = parts.copy();

		//
		// Iterate all parts and subtract requested qty
		for (final PackingItemPart part : remainingParts.toList())
		{
			//
			// If there is no qty to subtract, stop here
			if (qtyToSubtract.signum() <= 0)
			{
				break;
			}

			//
			// Make sure current shipment schedule is accepted by our predicate (if any)
			if (partsFilter != null && !partsFilter.test(part))
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

			// final Quantity partQtySubtracted;
			final PackingItemPart partSubtracted;

			//
			// Current qtyToSubtract is bigger then current schedule's available Qty
			// => subtract only schedule's available Qty
			if (qtyToSubtract.compareTo(part.getQty()) > 0)
			{
				remainingParts.removePart(part);
				partSubtracted = part;
			}
			// Current QtyToSubtract is lower or equal with current schedule's available Qty
			// => subtract the whole qtyToSubtract
			else
			{
				remainingParts.updatePart(part.subtractQty(qtyToSubtract));
				partSubtracted = part.withQty(qtyToSubtract);
			}

			//
			// Update qtyToSubtract
			qtyToSubtract = qtyToSubtract.subtract(partSubtracted.getQty());

			//
			// Add our subtracted schedule/qty pair to result to be returned
			result.updatePart(partSubtracted);
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
			parts.setFrom(remainingParts);
		}

		//
		// Return the result
		return result;
	}

	@Override
	public final void addParts(final PackingItemParts toAdd)
	{
		final boolean removeExistingOnes = false;
		addParts(toAdd, removeExistingOnes);
	}

	private final void addParts(
			@NonNull final PackingItemParts partsToAdd,
			final boolean removeExistingOnes)
	{
		partsToAdd.forEach(this::assertCanAddPart);

		// NOTE: we remove existing ones AFTER we validate because "canAddSchedule" always returns true in case there are no schedules
		if (removeExistingOnes)
		{
			parts.clear();
		}

		parts.addQtys(partsToAdd);
	}

	@Override
	public final void addParts(final IPackingItem packingItem)
	{
		addParts(packingItem.getParts());
	}
	@Override
	public final IPackingItem addPartsAndReturn(final IPackingItem packingItem)
	{
		addParts(packingItem.getParts());
		return this;
	}


	@Override
	public final void setPartsFrom(final IPackingItem packingItem)
	{
		final PackingItemParts toAdd = packingItem.getParts();
		final boolean removeExistingOnes = true;
		addParts(toAdd, removeExistingOnes);
	}

	private final void assertCanAddPart(final PackingItemPart part)
	{
		if (parts.isEmpty())
		{
			return;
		}

		if (!PackingItemGroupingKey.equals(groupingKey, computeGroupingKey(part)))
		{
			throw new AdempiereException(part + " can't be added to " + this);
		}
	}

	@Override
	public final PackingItemGroupingKey getGroupingKey()
	{
		return groupingKey;
	}

	@Override
	public final ProductId getProductId()
	{
		return groupingKey.getProductId();
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
		return getBPartnerLocationId().getBpartnerId();
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId()
	{
		return groupingKey.getBpartnerLocationId();
	}

	@Override
	public HUPIItemProductId getPackingMaterialId()
	{
		return groupingKey.getPackingMaterialId();
	}

	@Override
	public Set<WarehouseId> getWarehouseIds()
	{
		return parts.map(PackingItemPart::getWarehouseId).collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public Set<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return parts.map(PackingItemPart::getShipmentScheduleId).collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public IPackingItem subtractToPackingItem(
			@NonNull final Quantity subtrahent,
			@Nullable final Predicate<PackingItemPart> acceptPartPredicate)
	{
		final PackingItemParts subtractedParts = subtract(subtrahent, acceptPartPredicate);
		return PackingItems.newPackingItem(subtractedParts);
	}

	@Override
	public PackingItem copy()
	{
		return new PackingItem(this);
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
