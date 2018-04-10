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
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOutLine;
import lombok.NonNull;

public class InOutLineHUPackingAware implements IHUPackingAware
{
	private final I_M_InOutLine inoutLine;
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	public InOutLineHUPackingAware(@NonNull final I_M_InOutLine inoutLine)
	{
		this.inoutLine = inoutLine;
	}

	@Override
	public int getM_Product_ID()
	{
		return inoutLine.getM_Product_ID();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return inoutLine.getM_Product();
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		inoutLine.setM_Product_ID(productId);
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		inoutLine.setQtyEntered(qty);

		final Properties ctx = InterfaceWrapperHelper.getCtx(inoutLine);
		final BigDecimal movementQty = Services.get(IUOMConversionBL.class).convertToProductUOM(ctx, getM_Product(), getC_UOM(), qty);
		inoutLine.setMovementQty(movementQty);
	}

	@Override
	public BigDecimal getQty()
	{
		return inoutLine.getQtyEntered();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		if (inoutLine.isManualPackingMaterial())
		{
			return inoutLine.getM_HU_PI_Item_Product_ID();
		}

		final I_C_OrderLine orderline = InterfaceWrapperHelper.create(inoutLine.getC_OrderLine(), I_C_OrderLine.class);
		return orderline.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);

		final org.compiere.model.I_M_InOut inOut = inoutLine.getM_InOut();

		// Applied only to customer return inout lines.
		final boolean isCustomerReturnInOutLine = huInOutBL.isCustomerReturn(inOut);

		if (inoutLine.isManualPackingMaterial() || isCustomerReturnInOutLine)
		{
			return inoutLine.getM_HU_PI_Item_Product();
		}

		final I_C_OrderLine orderline = InterfaceWrapperHelper.create(inoutLine.getC_OrderLine(), I_C_OrderLine.class);

		return orderline == null ? null : orderline.getM_HU_PI_Item_Product();
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		values.setM_HU_PI_Item_Product(huPiItemProduct);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return inoutLine.getM_AttributeSetInstance_ID();
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		inoutLine.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return inoutLine.getC_UOM();
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		// we assume inoutLine's UOM is correct
		if (uom != null)
		{
			inoutLine.setC_UOM_ID(uom.getC_UOM_ID());
		}
	}

	@Override
	public BigDecimal getQtyTU()
	{
		return inoutLine.getQtyEnteredTU();
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		inoutLine.setQtyEnteredTU(qtyPacks);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return values.getC_BPartner();
	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		values.setC_BPartner(partner);
	}

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		values.setDateOrdered(dateOrdered);
	}

	@Override
	public Timestamp getDateOrdered()
	{
		return values.getDateOrdered();
	}

	@Override
	public boolean isInDispute()
	{
		return inoutLine.isInDispute();
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		inoutLine.setIsInDispute(inDispute);
	}
}
