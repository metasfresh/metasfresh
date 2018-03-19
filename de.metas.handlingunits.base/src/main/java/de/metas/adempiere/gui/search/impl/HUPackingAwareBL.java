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

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.quantity.Capacity;
import de.metas.quantity.CapacityInterface;
import lombok.NonNull;

public class HUPackingAwareBL implements IHUPackingAwareBL
{
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
	public void setQtyCUFromQtyTU(final IHUPackingAware record, final int qtyPacks)
	{
		final BigDecimal qty = calculateQty(record, qtyPacks);
		if (qty == null)
		{
			return;
		}

		record.setQty(qty);
		// record.setQtyPacks(qtyPacks); // we assume was already set
	}

	@Override
	public void updateQtyIfNeeded(final IHUPackingAware record, final int qtyTU, final BigDecimal qtyCU)
	{
		final BigDecimal maxQty = calculateQty(record, qtyTU);

		if (maxQty == null)
		{
			return;
		}

		if (maxQty.compareTo(qtyCU) <= 0)

		{
			record.setQty(maxQty);

			return;
		}

		final BigDecimal minQty = calculateQty(record, qtyTU - 1);

		if (qtyCU.compareTo(minQty) <= 0)
		{
			record.setQty(maxQty);

			return;
		}
	}

	private BigDecimal calculateQty(final IHUPackingAware record, final int qtyPacks)
	{
		if (qtyPacks < 0)
		{
			throw new AdempiereException("@QtyPacks@ < 0");
		}

		final I_M_HU_PI_Item_Product huPIItemProduct = record.getM_HU_PI_Item_Product();
		if (huPIItemProduct == null)
		{
			return null;
		}

		final I_M_Product product = record.getM_Product();
		final I_C_UOM uom = record.getC_UOM();
		final IHUCapacityBL capacityBL = Services.get(IHUCapacityBL.class);
		final Capacity capacity = capacityBL.getCapacity(huPIItemProduct, product, uom);
		final CapacityInterface capacityMult = capacity.multiply(qtyPacks);

		if (capacityMult.isInfiniteCapacity())
		{
			return null;
		}

		return capacityMult.getCapacityQty();
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
			final BigDecimal qtyPacks = calculateQtyTU(record);
			record.setQtyTU(qtyPacks);
		}
	}

	@Override
	public BigDecimal calculateQtyTU(final IHUPackingAware record)
	{
		final BigDecimal qty = record.getQty();
		if (qty == null || qty.signum() <= 0)
		{
			return null;
		}

		final I_M_HU_PI_Item_Product huPiItemProduct = record.getM_HU_PI_Item_Product();
		if (huPiItemProduct == null)
		{
			return null;
		}

		final I_M_Product product = record.getM_Product();

		if (product == null)
		{
			// nothing to do; shall not happen
			return null;
		}
		final I_C_UOM uom = record.getC_UOM();

		// task 08583: this may happen in case of manually entered M_HU_PI_Item_Products in manually created document lines.
		// It has to be possible for the user to set the M_HU_PI_Item_product before setting the uom.
		if (uom == null)
		{
			return null;
		}

		final IHUCapacityBL capacityBL = Services.get(IHUCapacityBL.class);
		final Capacity capacityDef = capacityBL.getCapacity(huPiItemProduct, product, uom);

		final Integer qtyTU = capacityDef.calculateQtyTU(qty, uom);
		if (qtyTU == null)
		{
			return null;
		}

		return BigDecimal.valueOf(qtyTU);
	}

	@Override
	public void computeAndSetQtysForNewHuPackingAware(
			@NonNull final PlainHUPackingAware huPackingAware,
			@NonNull final BigDecimal quickInputQty)
	{
		final I_M_HU_PI_Item_Product piItemProduct = huPackingAware.getM_HU_PI_Item_Product();
		final IHUPIItemProductBL piPIItemProductBL = Services.get(IHUPIItemProductBL.class);
		if (piItemProduct == null || piPIItemProductBL.isVirtualHUPIItemProduct(piItemProduct) || piItemProduct.isInfiniteCapacity())
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

}
