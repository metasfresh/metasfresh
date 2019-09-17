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

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.util.Check;

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
	public int getC_UOM_ID()
	{
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void setC_UOM_ID(final int uomId)
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
	public int getM_HU_PI_Item_Product_ID()
	{
		return order.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public void setM_HU_PI_Item_Product_ID(final int huPiItemProductId)
	{
		order.setM_HU_PI_Item_Product_ID(huPiItemProductId);

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
	public void setC_BPartner_ID(final int bpartnerId)
	{
		order.setC_BPartner_ID(bpartnerId);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return order.getC_BPartner_ID();
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
				.format("OrderHUPackingAware [order=%s, getM_Product_ID()=%s, getM_Product()=%s, getM_AttributeSetInstance_ID()=%s, getC_UOM()=%s, getQty()=%s, getM_HU_PI_Item_Product()=%s, getQtyPacks()=%s, getC_BPartner()=%s, getM_HU_PI_Item_Product_ID()=%s, isInDispute()=%s]",
						order, getM_Product_ID(), getM_Product_ID(), getM_AttributeSetInstance_ID(), getC_UOM_ID(), getQty(), getM_HU_PI_Item_Product_ID(), getQtyTU(), getC_BPartner_ID(),
						getM_HU_PI_Item_Product_ID(), isInDispute());
	}
}
