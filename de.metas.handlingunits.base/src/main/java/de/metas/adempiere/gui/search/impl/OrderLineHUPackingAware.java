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
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Wraps an {@link I_C_OrderLine} and makes it behave like an {@link IHUPackingAware}.
 *
 * @author tsa
 *
 */
public class OrderLineHUPackingAware implements IHUPackingAware
{
	public static final OrderLineHUPackingAware of(final org.compiere.model.I_C_OrderLine orderLine)
	{
		return new OrderLineHUPackingAware(InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class));
	}

	private final I_C_OrderLine orderLine;

	/**
	 * Plain delegate for fields which are not available in order line
	 */
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	public OrderLineHUPackingAware(final I_C_OrderLine orderLine)
	{
		super();

		Check.assumeNotNull(orderLine, "orderLine not null");
		this.orderLine = orderLine;
	}

	@Override
	public int getM_Product_ID()
	{
		return orderLine.getM_Product_ID();
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		orderLine.setM_Product_ID(productId);

		values.setM_Product_ID(productId);
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		orderLine.setQtyEntered(qty);

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		orderLine.setQtyOrdered(orderLineBL.convertQtyEnteredToStockUOM(orderLine).toBigDecimal());

		values.setQty(qty);
	}

	/**
	 * @return QtyEntered of the wrapped order line. Note that qtyEntered is the qty that corresponds the UOM returned by {@link #getC_UOM()}.
	 */
	@Override
	public BigDecimal getQty()
	{
		return orderLine.getQtyEntered();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		return orderLine.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public void setM_HU_PI_Item_Product_ID(final int huPiItemProductId)
	{
		orderLine.setM_HU_PI_Item_Product_ID(huPiItemProductId);
		values.setM_HU_PI_Item_Product_ID(huPiItemProductId);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return orderLine.getM_AttributeSetInstance_ID();
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		orderLine.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);

		values.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}

	@Override
	public int getC_UOM_ID()
	{
		return orderLine.getC_UOM_ID();
	}

	@Override
	public void setC_UOM_ID(final int uomId)
	{
		values.setC_UOM_ID(uomId);

		// NOTE: uom is mandatory
		// we assume orderLine's UOM is correct
		if (uomId > 0)
		{
			orderLine.setC_UOM_ID(uomId);
		}
	}

	@Override
	public BigDecimal getQtyTU()
	{
		return orderLine.getQtyEnteredTU();
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		orderLine.setQtyEnteredTU(qtyPacks);
		values.setQtyTU(qtyPacks);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return orderLine.getC_BPartner_ID();
	}

	@Override
	public void setC_BPartner_ID(final int bpartnerId)
	{
		orderLine.setC_BPartner_ID(bpartnerId);
		values.setC_BPartner_ID(bpartnerId);
	}

	@Override
	public boolean isInDispute()
	{
		// order line has no IsInDispute flag
		return values.isInDispute();
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		values.setInDispute(inDispute);
	}

	@Override
	public String toString()
	{
		return String
				.format("OrderLineHUPackingAware [orderLine=%s, getM_Product_ID()=%s, getM_Product()=%s, getQty()=%s, getM_HU_PI_Item_Product()=%s, getM_AttributeSetInstance_ID()=%s, getC_UOM()=%s, getQtyPacks()=%s, getC_BPartner()=%s, getM_HU_PI_Item_Product_ID()=%s, isInDispute()=%s]",
						orderLine, getM_Product_ID(), getM_Product_ID(), getQty(), getM_HU_PI_Item_Product_ID(), getM_AttributeSetInstance_ID(), getC_UOM_ID(), getQtyTU(), getC_BPartner_ID(),
						getM_HU_PI_Item_Product_ID(), isInDispute());
	}
}
