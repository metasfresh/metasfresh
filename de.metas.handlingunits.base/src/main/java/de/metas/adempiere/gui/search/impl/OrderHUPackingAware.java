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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

public class OrderHUPackingAware implements IHUPackingAware
{
	private final I_C_Order order;

	public OrderHUPackingAware(final org.compiere.model.I_C_Order order)
	{
		super();

		Check.assumeNotNull(order, "order not null");
		this.order = InterfaceWrapperHelper.create(order, I_C_Order.class);
	}

	@Override
	public int getM_Product_ID()
	{
		return order.getM_Product_ID();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return order.getM_Product();
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		order.setM_Product_ID(productId);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return -1; // N/A
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		order.setQty_FastInput(qty);
	}

	@Override
	public BigDecimal getQty()
	{
		return order.getQty_FastInput();
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return order.getM_HU_PI_Item_Product();
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		order.setM_HU_PI_Item_Product(huPiItemProduct);

	}

	@Override
	public BigDecimal getQtyTU()
	{
		return order.getQty_FastInput_TU();
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		order.setQty_FastInput_TU(qtyPacks);
	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		order.setC_BPartner_ID(partner.getC_BPartner_ID());
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return order.getC_BPartner();
	}

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		order.setDateOrdered(dateOrdered);

	}

	@Override
	public Timestamp getDateOrdered()
	{
		return order.getDateOrdered();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		return order.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public boolean isInDispute()
	{
		return false;
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString()
	{
		return String
				.format("OrderHUPackingAware [order=%s, getM_Product_ID()=%s, getM_Product()=%s, getM_AttributeSetInstance_ID()=%s, getC_UOM()=%s, getQty()=%s, getM_HU_PI_Item_Product()=%s, getQtyPacks()=%s, getC_BPartner()=%s, getDateOrdered()=%s, getM_HU_PI_Item_Product_ID()=%s, isInDispute()=%s]",
						order, getM_Product_ID(), getM_Product(), getM_AttributeSetInstance_ID(), getC_UOM(), getQty(), getM_HU_PI_Item_Product(), getQtyTU(), getC_BPartner(), getDateOrdered(),
						getM_HU_PI_Item_Product_ID(), isInDispute());
	}
}
