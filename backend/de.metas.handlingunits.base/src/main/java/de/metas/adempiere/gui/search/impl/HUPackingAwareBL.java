package de.metas.adempiere.gui.search.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import com.google.common.annotations.VisibleForTesting;
import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class HUPackingAwareBL implements IHUPackingAwareBL
{
	private final transient IHUPIItemProductBL piPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final transient IHUCapacityBL capacityBL = Services.get(IHUCapacityBL.class);
	private final transient IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@Override
	public IHUPackingAware create(final IInfoSimple infoWindow, final int rowIndexModel)
	{
		return new HUPackingAwareInfoWindowAdapter(infoWindow, rowIndexModel);
	}

	@Override
	public IHUPackingAware createPlain()
	{
		return new PlainHUPackingAware();
	}

	@Override
	public void setQtyCUFromQtyTU(@NonNull final IHUPackingAware record, final int qtyPacks)
	{
		final Quantity qty = calculateQty(record, qtyPacks);
		if (qty == null)
		{
			return;
		}

		record.setQty(qty.toBigDecimal());
		record.setC_UOM_ID(qty.getUomId().getRepoId());
	}

	@Override
	public void updateQtyIfNeeded(final IHUPackingAware record, final int qtyTU, final Quantity qtyCU)
	{
		final Quantity maxQty = calculateQty(record, qtyTU);
		if (maxQty == null)
		{
			return;
		}

		if (maxQty.compareTo(qtyCU) <= 0)
		{
			record.setQty(maxQty.toBigDecimal());
			record.setC_UOM_ID(maxQty.getUomId().getRepoId());
			return;
		}

		final Quantity minQty = calculateQty(record, qtyTU - 1);

		if (qtyCU.compareTo(minQty) <= 0)
		{
			record.setQty(maxQty.toBigDecimal());
			record.setC_UOM_ID(maxQty.getUomId().getRepoId());
			return;
		}
	}

	private Quantity calculateQty(@NonNull final IHUPackingAware record, final int qtyPacks)
	{
		if (qtyPacks < 0)
		{
			throw new AdempiereException("@QtyPacks@ < 0")
					.appendParametersToMessage()
					.setParameter("huPackingAware", record);
		}

		final Capacity capacity = calculateCapacity(record);
		if (capacity == null)
		{
			return null;
		}
		if (capacity.isInfiniteCapacity())
		{
			return null;
		}

		final Capacity capacityMult = capacity.multiply(qtyPacks);
		return capacityMult.toQuantity();
	}

	@Override
	public void setQtyTU(final IHUPackingAware record)
	{
		// Only set Qty Packs for entries that are not in dispute.
		if (record.isInDispute())
		{
			record.setQtyTU(BigDecimal.ZERO);
		}
		else
		{
			final QuantityTU qtyTUs = calculateQtyTU(record);
			record.setQtyTU(qtyTUs != null ? qtyTUs.toBigDecimal() : null);
		}
	}

	@VisibleForTesting
	@Nullable
	QuantityTU calculateQtyTU(@NonNull final IHUPackingAware record)
	{
		if (uomDAO.isUOMForTUs(UomId.ofRepoId(record.getC_UOM_ID())))
		{
			return QuantityTU.ofBigDecimal(record.getQty()); // the quantity *is* already the number of TUs
		}

		final Capacity capacity = calculateCapacity(record);
		if (capacity == null)
		{
			return null;
		}
		final BigDecimal qty = record.getQty();
		if (qty == null || qty.signum() <= 0)
		{
			return null;
		}

		final QuantityTU qtyTU = capacity.calculateQtyTU(record.getQty(), extractUOMOrNull(record), uomConversionBL).orElse(null);
		if (qtyTU == null)
		{
			return null;
		}

		return qtyTU;
	}

	@Override
	public Capacity calculateCapacity(@NonNull final IHUPackingAware record)
	{
		final I_M_HU_PI_Item_Product huPiItemProduct = extractHUPIItemProductOrNull(record);
		if (huPiItemProduct == null)
		{
			return null;
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(record.getM_Product_ID());
		if (productId == null)
		{
			// nothing to do; shall not happen
			return null;
		}

		// task 08583: this may happen in case of manually entered M_HU_PI_Item_Products in manually created document lines.
		// It has to be possible for the user to set the M_HU_PI_Item_product before setting the uom.
		final I_C_UOM uom = extractUOMOrNull(record);
		if (uom == null)
		{
			return null;
		}

		final Capacity capacityDef = capacityBL.getCapacity(huPiItemProduct, productId, uom);
		return capacityDef;
	}

	@Override
	public void computeAndSetQtysForNewHuPackingAware(
			@NonNull final PlainHUPackingAware huPackingAware,
			@NonNull final BigDecimal quickInputQty)
	{
		if (isInfiniteCapacityTU(huPackingAware))
		{
			huPackingAware.setQty(quickInputQty);
			huPackingAware.setQtyTU(BigDecimal.ONE);
		}
		else
		{
			final BigDecimal qtyTU = quickInputQty;
			huPackingAware.setQtyTU(qtyTU);
			setQtyCUFromQtyTU(huPackingAware, qtyTU.intValue());
		}
	}

	private I_M_HU_PI_Item_Product extractHUPIItemProductOrNull(@NonNull final IHUPackingAware huPackingAware)
	{
		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNull(huPackingAware.getM_HU_PI_Item_Product_ID());
		return piItemProductId != null
				? piPIItemProductBL.getRecordById(piItemProductId)
				: null;
	}

	@Override
	public boolean isInfiniteCapacityTU(final IHUPackingAware huPackingAware)
	{
		final IHUPIItemProductBL piItemProductBL = Services.get(IHUPIItemProductBL.class);

		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNone(huPackingAware.getM_HU_PI_Item_Product_ID());
		return piItemProductBL.isInfiniteCapacity(piItemProductId);
	}

	private I_C_UOM extractUOMOrNull(final IHUPackingAware huPackingAware)
	{
		final int uomId = huPackingAware.getC_UOM_ID();
		return uomId > 0
				? uomDAO.getById(uomId)
				: null;
	}
}
