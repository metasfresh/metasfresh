package de.metas.fresh.picking;

import java.time.ZonedDateTime;
import java.util.Set;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.warehouse.WarehouseId;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItems;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.NonNull;

/**
 * 
 * @author cg
 */
public class PackingDetailsModel
{
	private final ImmutableList<IPackingItem> unallocatedLines;
	private final ImmutableList<PickingSlotKey> availablePickingSlots;
	private final ImmutableList<PackingMaterialKey> availablePackingMaterialKeys;

	public PackingDetailsModel(
			@NonNull final ITerminalContext terminalContext,
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		this.unallocatedLines = PackingItems.createPackingItems(shipmentScheduleIds);
		if (unallocatedLines.isEmpty())
		{
			throw new AdempiereException("Nothing to pack")
					.setParameter("shipmentScheduleIds", shipmentScheduleIds)
					.appendParametersToMessage();
		}

		final ZonedDateTime date = SystemTime.asZonedDateTimeAtStartOfDay();
		final PackingMaterialKeyBuilder packingMaterialKeysBuilder = new PackingMaterialKeyBuilder(terminalContext, date);
		final PickingSlotKeyBuilder pickingSlotKeysBuilder = new PickingSlotKeyBuilder(terminalContext);
		for (final IPackingItem freshPackingItem : this.unallocatedLines)
		{
			final ProductId productId = freshPackingItem.getProductId();
			final BPartnerId bpartnerId = freshPackingItem.getBPartnerId();
			final BPartnerLocationId bpartnerLocationId = freshPackingItem.getBPartnerLocationId();
			final Set<WarehouseId> warehouseIds = freshPackingItem.getWarehouseIds();

			//
			// Get/Create PackingMaterialKeys
			packingMaterialKeysBuilder.addProduct(productId, bpartnerId, bpartnerLocationId);

			//
			// Get PickingSlots
			pickingSlotKeysBuilder.addBPartner(bpartnerId, bpartnerLocationId, warehouseIds);
		}

		this.availablePickingSlots = pickingSlotKeysBuilder.getPickingSlotKeys();
		if (availablePickingSlots.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @" + I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID + "@");
		}

		//
		// Build available packing materials
		this.availablePackingMaterialKeys = packingMaterialKeysBuilder.getPackingMaterialKeys();
		if (availablePackingMaterialKeys.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_HU_PI_ID@");
		}
	}

	/**
	 * 
	 * @return unallocated lines
	 */
	public ImmutableList<IPackingItem> getUnallocatedLines()
	{
		return unallocatedLines;
	}

	public ImmutableList<PackingMaterialKey> getAvailablePackingMaterialKeys()
	{
		return availablePackingMaterialKeys;
	}

	public ImmutableList<PickingSlotKey> getAvailablePickingSlots()
	{
		return availablePickingSlots;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

}
