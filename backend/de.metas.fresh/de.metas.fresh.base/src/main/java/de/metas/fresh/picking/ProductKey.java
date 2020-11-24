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
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.fresh.picking.form.PackingStates;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemGroupingKey;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * @author cg
 *
 */
public class ProductKey extends TerminalKey
{
	public static ProductKey cast(final ITerminalKey key)
	{
		return (ProductKey)key;
	}

	private final String id;
	private final KeyNamePair value;
	private final ProductId productId;
	private final I_C_UOM uom;
	private final BPartnerId bpartnerId;
	private final BPartnerLocationId bpartnerLocationId;

	private IPackingItem packingItem;
	/** this is the packing item which contains unallocated scheds and unallocated qty */
	private IPackingItem _unallocatedPackingItem;

	public ProductKey(@NonNull final ITerminalContext terminalContext, @NonNull final IPackingItem packingItem)
	{
		super(terminalContext);

		this.packingItem = packingItem;
		this.productId = packingItem.getProductId();
		this.uom = packingItem.getC_UOM();
		this.bpartnerId = packingItem.getBPartnerId();
		this.bpartnerLocationId = packingItem.getBPartnerLocationId();

		final String displayName = buildDisplayName(productId, bpartnerId, bpartnerLocationId);
		this.value = KeyNamePair.of(productId, displayName);
		this.status = FreshProductKeyStatus.packed();

		this.id = buildId(productId, bpartnerId, bpartnerLocationId);
	}

	private static final String buildId(final ProductId productId, final BPartnerId bpartnerId, final BPartnerLocationId bpartnerLocationId)
	{
		return Util.mkKey(
				ProductId.toRepoId(productId),
				BPartnerId.toRepoId(bpartnerId),
				BPartnerLocationId.toRepoId(bpartnerLocationId))
				.toString();
	}

	private static String buildDisplayName(final ProductId productId, final BPartnerId bpartnerId, final BPartnerLocationId bpartnerLocationId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

		final I_M_Product product = productsRepo.getById(productId);

		// Max text length
		// NOTE: please think twice before changing this number because it was tuned for 1024x740 resolution
		// see http://dewiki908/mediawiki/index.php/05863_Fenster_Kommissionierung_-_bessere_Ausnutzung_Kn%C3%B6pfefelder_f%C3%BCr_Textausgabe_%28102244669218%29
		final int maxLength = 25;

		final String pValue = truncatedString(product.getValue(), maxLength);
		final String pName = truncatedString(product.getName(), maxLength);
		final String bpName = truncatedString(bpartnersRepo.getBPartnerNameById(bpartnerId), maxLength);
		final String bplName = truncatedString(bpartnersRepo.getBPartnerLocationById(bpartnerLocationId).getName(), maxLength);
		return pValue
				+ "<br>"
				+ pName
				+ "<br>"
				+ bpName
				+ "<br>"
				+ bplName;
	}

	private static String truncatedString(final String text, final int length)
	{
		final String[] bits = text.split("\\\\n");
		final StringBuilder newtxt = new StringBuilder();
		for (final String s : bits)
		{
			if (newtxt.length() > 0)
			{
				newtxt.append("<br>");
			}

			if (s.length() > length)
			{
				newtxt.append(s.substring(0, length - 3).concat("..."));
			}
			else
			{
				newtxt.append(s);
			}
		}

		if (newtxt.length() == 0)
		{
			return text;
		}
		return newtxt.toString();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public String getTableName()
	{
		return I_M_Product.Table_Name;
	}

	public ProductId getProductId()
	{
		return productId;
	}

	public BPartnerId getBPartnerId()
	{
		return bpartnerId;
	}

	public BPartnerLocationId getBPartnerLocationId()
	{
		return bpartnerLocationId;
	}

	/**
	 * @return infos regarding packing material and capacity
	 */
	private String retrievePackingMaterialInfos()
	{
		final HUPIItemProductId pipId = getHUPIItemProductId();
		if (pipId == null)
		{
			return "";
		}

		final String pipStr = Services.get(IHUPIItemProductBL.class).buildDisplayName()
				.setM_HU_PI_Item_Product(pipId)
				.build();

		return "<br>" + pipStr;
	}

	/**
	 * @return default/suggested PI item product or <code>null</code>
	 */
	public HUPIItemProductId getHUPIItemProductId()
	{
		final IPackingItem allocatedPackingItem = getPackingItem();
		if (allocatedPackingItem != null)
		{
			return allocatedPackingItem.getPackingMaterialId();
		}

		final IPackingItem unallocatedPackingItem = getUnAllocatedPackingItemOrNull();
		if (unallocatedPackingItem != null)
		{
			return unallocatedPackingItem.getPackingMaterialId();
		}

		return null;
	}

	/**
	 * @return packing item for unallocated quantity; never returns <code>null</code>
	 */
	public IPackingItem getUnAllocatedPackingItem()
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
	private final IPackingItem getUnAllocatedPackingItemOrNull()
	{
		return _unallocatedPackingItem;
	}

	public void setUnAllocatedPackingItem(IPackingItem pck)
	{
		this._unallocatedPackingItem = pck;
	}

	public PackingItemGroupingKey getGroupingKey()
	{
		return getPackingItem().getGroupingKey();
	}

	public IPackingItem getPackingItem()
	{
		return packingItem;
	}

	public void resetPackingItem()
	{
		this.packingItem = null;
	}

	@Override
	public FreshProductKeyStatus getStatus()
	{
		return FreshProductKeyStatus.cast(super.getStatus());
	}

	public void setStatus(final PackingStates packStatus)
	{
		if (packStatus == null)
		{
			return;
		}

		final FreshProductKeyStatus statusOld = getStatus();
		final FreshProductKeyStatus statusNew = FreshProductKeyStatus.of(packStatus);
		if (Objects.equals(statusOld, statusNew))
		{
			// nothing changed
			return;
		}

		setStatus(statusNew);
	}

	@Override
	public Object getName()
	{
		final BigDecimal unallocQty = getQtyUnallocated(2);

		BigDecimal allocQty = getQty().toBigDecimal();
		if (allocQty.scale() > 2)
		{
			allocQty = allocQty.setScale(2, BigDecimal.ROUND_HALF_UP);
		}

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
		sb.append("M_Product:").append(productId);
		sb.append("\nC_BPartner: ").append(bpartnerId);
		sb.append("\nC_BPartner_Location: ").append(bpartnerLocationId);
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

		return qtyUnallocated.toBigDecimal();
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
	 * @return qty inside this Product key (i.e. sum of Qtys from underlying shipment schedules)
	 */
	public Quantity getQty()
	{
		if (packingItem == null)
		{
			return Quantity.zero(uom);
		}

		// TODO: check if is really needed because it might be that the conversion is pointless!
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);
		return uomConversionBL.convertQuantityTo(packingItem.getQtySum(), conversionCtx, uom);
	}

	/**
	 * Search for available HUs to be picked.
	 *
	 * @param considerAttributes true if we shall consider the HU attributes while searching for matching HUs
	 * @return matching HUs
	 */
	public List<I_M_HU> findAvailableHUs(final boolean considerAttributes)
	{
		final IPackingItem unallocatedPackingItem = getUnAllocatedPackingItem();
		final Set<ShipmentScheduleId> shipmentScheduleIds = unallocatedPackingItem.getShipmentScheduleIds();

		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedules = shipmentSchedulesRepo.getByIdsOutOfTrx(shipmentScheduleIds);

		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		return huPickingSlotBL.retrieveAvailableHUsToPick(PickingHUsQuery.builder()
				.shipmentSchedules(shipmentSchedules.values())
				.onlyIfAttributesMatchWithShipmentSchedules(considerAttributes)
				.onlyTopLevelHUs(true)
				.build());
	}
}
