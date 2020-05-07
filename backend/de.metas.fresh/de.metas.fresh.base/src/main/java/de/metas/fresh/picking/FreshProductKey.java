/**
 *
 */
package de.metas.fresh.picking;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.service.FreshPackingItemHelper;
import de.metas.picking.service.IFreshPackingItem;
import de.metas.picking.terminal.ProductKey;
import de.metas.quantity.Quantity;

/**
 * @author cg
 *
 */
public class FreshProductKey extends ProductKey
{
	/**
	 * retrieve infos regarding packing material and capacity
	 *
	 * @return
	 */
	private String retrievePackingMaterialInfos()
	{
		final I_M_HU_PI_Item_Product pip = getM_HU_PI_Item_Product();
		if (pip == null)
		{
			return "";
		}

		final String pipStr = Services.get(IHUPIItemProductBL.class).buildDisplayName()
				.setM_HU_PI_Item_Product(pip)
				.build();

		return "<br>" + pipStr;
	}

	/**
	 * @return default/suggested PI item product or <code>null</code>
	 */
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final IFreshPackingItem unallocatedPackingItem = getUnAllocatedPackingItemOrNull();
		final IFreshPackingItem allocatedPackingItem = getPackingItem();
		if (allocatedPackingItem != null || unallocatedPackingItem != null)
		{
			final IFreshPackingItem pck = allocatedPackingItem != null ? allocatedPackingItem : unallocatedPackingItem;

			final I_M_HU_PI_Item_Product pip = pck.getM_HU_PI_Item_Product();
			return pip;
		}

		return null;
	}

	/**
	 * this is the packing item which contains unallocated scheds and unallocated qty
	 */
	private IFreshPackingItem _unallocatedPackingItem;

	public FreshProductKey(final ITerminalContext terminalContext, final IFreshPackingItem pck, final int boxNo)
	{
		super(terminalContext,
				pck,
				boxNo,
				pck.getC_BPartner(),   // BPartner
				pck.getC_BPartner_Location() // BPartner Location
		);
	}

	/**
	 * @return packing item for unallocated quantity; never returns <code>null</code>
	 */
	public IFreshPackingItem getUnAllocatedPackingItem()
	{
		if (_unallocatedPackingItem == null)
		{
			throw new AdempiereException("@ProductKeyAlreadyAllocated@");
		}
		return _unallocatedPackingItem;
	}

	/**
	 * @return packing item for unallocated quantity or <code>null</code>
	 */
	private final IFreshPackingItem getUnAllocatedPackingItemOrNull()
	{
		return _unallocatedPackingItem;
	}

	public void setUnAllocatedPackingItem(IFreshPackingItem pck)
	{
		this._unallocatedPackingItem = pck;
	}

	@Override
	public IFreshPackingItem getPackingItem()
	{
		return FreshPackingItemHelper.cast(super.getPackingItem());
	}

	@Override
	public Object getName()
	{
		final BigDecimal unallocQty = getQtyUnallocated(2);
		final BigDecimal allocQty = getQtyAllocated(2);
		final BigDecimal totalQty = allocQty.add(unallocQty);

		final StringBuilder sb = new StringBuilder();
		sb
				// Qty Allocated / Qty Total
				.append("<font size=\"5\">")
				.append(allocQty).append("/").append(totalQty)
				.append("</font>")
				.append("<br>")
				//
				.append("<font size=\"3\">")
				.append(getValue().getName())
				.append(retrievePackingMaterialInfos())
				.append("</font>");
		return sb.toString();
	}

	@Override
	public String getDebugInfo()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("M_Product:").append(getM_Product());
		sb.append("\nC_BPartner: ").append(getC_BPartner());
		sb.append("\nC_BPartner_Location: ").append(getC_BPartner_Location());
		return sb.toString();

	}

	public BigDecimal getQtyUnallocated()
	{
		final IPackingItem unallocPackingItem = getUnAllocatedPackingItemOrNull();
		if (unallocPackingItem == null)
		{
			return BigDecimal.ZERO;
		}
		final Quantity qtyUnallocated = unallocPackingItem.getQtySum();

		return qtyUnallocated.getQty();
	}

	public I_C_UOM getQtyUnallocatedUOM()
	{
		final IPackingItem unallocPackingItem = getUnAllocatedPackingItemOrNull();
		Check.assumeNotNull(unallocPackingItem, "unallocPackingItem not null");
		return unallocPackingItem.getC_UOM();
	}

	/**
	 * Gets Qty Unallocated and adjust the scale if necessary
	 *
	 * NOTE: talked with Cristina why we need this and she told me that in some cases underlying Qty Unallocated is retrieved with a lot of 0 decimals and looks like shit in Qty Field
	 *
	 * @param maxScale
	 * @return qty unallocated
	 */
	public BigDecimal getQtyUnallocated(final int maxScale)
	{
		BigDecimal qtyUnallocated = getQtyUnallocated();
		if (maxScale >= 0 && qtyUnallocated.scale() > maxScale)
		{
			qtyUnallocated = qtyUnallocated.setScale(maxScale, BigDecimal.ROUND_HALF_UP);
		}

		return qtyUnallocated;
	}

	/**
	 * Gets Qty Allocated and adjust the scale if necessary
	 *
	 * @param maxScale
	 * @return
	 *
	 * @see #getQty()
	 */
	public BigDecimal getQtyAllocated(final int maxScale)
	{
		BigDecimal qtyAllocated = getQty();
		if (maxScale >= 0 && qtyAllocated.scale() > maxScale)
		{
			qtyAllocated = qtyAllocated.setScale(maxScale, BigDecimal.ROUND_HALF_UP);
		}

		return qtyAllocated;
	}

	/**
	 * Search for available HUs to be picked.
	 *
	 * @param considerAttributes true if we shall consider the HU attributes while searching for matching HUs
	 * @return matching HUs
	 */
	public List<I_M_HU> findAvailableHUs(final boolean considerAttributes)
	{
		final IFreshPackingItem unallocatedPackingItem = getUnAllocatedPackingItem();
		final List<I_M_ShipmentSchedule> shipmentSchedules = unallocatedPackingItem.getShipmentSchedules();

		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		return huPickingSlotBL.retrieveAvailableHUsToPick(PickingHUsQuery.builder()
				.shipmentSchedules(shipmentSchedules)
				.onlyIfAttributesMatchWithShipmentSchedules(considerAttributes)
				.onlyTopLevelHUs(true)
				.build());
	}
}
