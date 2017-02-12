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
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.util.Services;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

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
	public void copy(final IHUPackingAware to, final IHUPackingAware from)
	{
		copy(to, from, true);
	}

	@Override
	public void copy(final IHUPackingAware to, final IHUPackingAware from, final boolean overridePartner)
	{
		to.setM_Product_ID(from.getM_Product_ID());

		final int asiId = from.getM_AttributeSetInstance_ID();
		if (asiId >= 0)
		{
			Services.get(IAttributeSetInstanceBL.class).cloneASI(to, from);
		}
		to.setC_UOM(from.getC_UOM());
		to.setQty(from.getQty());
		to.setM_HU_PI_Item_Product(from.getM_HU_PI_Item_Product());
		to.setQtyPacks(from.getQtyPacks());

		// 08276
		// do not modify the partner in the orderline if it was already set

		if (to.getC_BPartner() == null || overridePartner)
		{
			to.setC_BPartner(from.getC_BPartner());
		}
	}

	@Override
	public boolean isValid(final IHUPackingAware record)
	{
		if (record == null)
		{
			return false;
		}

		return record.getM_Product_ID() > 0
				&& record.getM_HU_PI_Item_Product_ID() > 0
				&& isValidQty(record)
				&& record.getQtyPacks() != null && record.getQtyPacks().signum() != 0;
	}

	@Override
	public boolean isValidQty(final IHUPackingAware record)
	{
		if (record == null)
		{
			return false;
		}
		return record.getQty() != null && record.getQty().signum() != 0;
	}

	@Override
	public void setQty(final IHUPackingAware record, final int qtyPacks)
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
	public void updateQtyIfNeeded(final IHUPackingAware record, final int qtyPacks, final BigDecimal qtyCU)
	{
		final BigDecimal maxQty = calculateQty(record, qtyPacks);

		if (maxQty == null)
		{
			return;
		}

		if (maxQty.compareTo(qtyCU) <= 0)

		{
			record.setQty(maxQty);

			return;
		}

		final BigDecimal minQty = calculateQty(record, qtyPacks - 1);

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
		final IHUCapacityDefinition capacity = capacityBL.getCapacity(huPIItemProduct, product, uom);
		final IHUCapacityDefinition capacityMult = capacityBL.multiply(capacity, qtyPacks);

		if (capacityMult.isInfiniteCapacity())
		{
			return null;
		}

		return capacityMult.getCapacity();
	}

	@Override
	public void setQtyPacks(final IHUPackingAware record)
	{
		// Only set Qty Packs for entries that are not in dispute.
		if (record.isInDispute())
		{
			record.setQtyPacks(BigDecimal.ZERO);
		}
		else
		{
			final BigDecimal qtyPacks = calculateQtyPacks(record);
			record.setQtyPacks(qtyPacks);
		}
	}

	@Override
	public BigDecimal calculateQtyPacks(final IHUPackingAware record)
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
		final IHUCapacityDefinition capacityDef = capacityBL.getCapacity(huPiItemProduct, product, uom);

		final Integer qtyPacks = capacityBL.calculateQtyPacks(qty, uom, capacityDef);
		if (qtyPacks == null)
		{
			return null;
		}

		return BigDecimal.valueOf(qtyPacks);
	}
}
