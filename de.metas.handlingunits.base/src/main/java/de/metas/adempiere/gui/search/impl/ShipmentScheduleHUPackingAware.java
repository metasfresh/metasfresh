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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import lombok.NonNull;

public class ShipmentScheduleHUPackingAware implements IHUPackingAware
{

	private final I_M_ShipmentSchedule shipmentSchedule;
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	public ShipmentScheduleHUPackingAware(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		this.shipmentSchedule = shipmentSchedule;
	}

	@Override
	public int getM_Product_ID()
	{
		return shipmentSchedule.getM_Product_ID();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return shipmentSchedule.getM_Product();
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		shipmentSchedule.setM_Product_ID(productId);

	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return shipmentSchedule.getM_AttributeSetInstance_ID();
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		shipmentSchedule.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return shipmentSchedule.getC_UOM();
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		// we assume inoutLine's UOM is correct
		if (uom != null)
		{
			shipmentSchedule.setC_UOM_ID(uom.getC_UOM_ID());
		}
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		shipmentSchedule.setQtyOrdered_Override(qty);
	}

	@Override
	public BigDecimal getQty()
	{
		// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
		final BigDecimal qtyOrdered = Services.get(IShipmentScheduleEffectiveBL.class).computeQtyOrdered(shipmentSchedule);

		return qtyOrdered;
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		return shipmentSchedule.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return shipmentSchedule.getM_HU_PI_Item_Product();
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		values.setM_HU_PI_Item_Product(huPiItemProduct);

	}

	@Override
	public BigDecimal getQtyTU()
	{
		return shipmentSchedule.getQtyOrdered_TU();
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		shipmentSchedule.setQtyOrdered_TU(qtyPacks);

	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		values.setC_BPartner(partner);

	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return values.getC_BPartner();
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
		return values.isInDispute();
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		values.setInDispute(inDispute);
	}

}
