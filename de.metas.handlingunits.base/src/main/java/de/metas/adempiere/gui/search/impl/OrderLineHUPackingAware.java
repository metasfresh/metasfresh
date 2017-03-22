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
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

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
	public I_M_Product getM_Product()
	{
		return orderLine.getM_Product();
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
		orderLine.setQtyOrdered(Services.get(IOrderLineBL.class).convertQtyEnteredToInternalUOM(orderLine));

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
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return orderLine.getM_HU_PI_Item_Product();
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		orderLine.setM_HU_PI_Item_Product(huPiItemProduct);

		values.setM_HU_PI_Item_Product(huPiItemProduct);
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
	public I_C_UOM getC_UOM()
	{
		return orderLine.getC_UOM();
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		values.setC_UOM(uom);

		// NOTE: uom is mandatory
		// we assume orderLine's UOM is correct
		if (uom != null)
		{
			orderLine.setC_UOM(uom);
		}
	}

	@Override
	public BigDecimal getQtyPacks()
	{
		return orderLine.getQtyEnteredTU();
	}

	@Override
	public void setQtyPacks(final BigDecimal qtyPacks)
	{
		orderLine.setQtyEnteredTU(qtyPacks);
		values.setQtyPacks(qtyPacks);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return orderLine.getC_BPartner();
	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		orderLine.setC_BPartner(partner);
		values.setC_BPartner(partner);
	}

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		orderLine.setDateOrdered(dateOrdered);
		values.setDateOrdered(dateOrdered);
	}

	@Override
	public Timestamp getDateOrdered()
	{
		return orderLine.getDateOrdered();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		return orderLine.getM_HU_PI_Item_Product_ID();
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
				.format("OrderLineHUPackingAware [orderLine=%s, getM_Product_ID()=%s, getM_Product()=%s, getQty()=%s, getM_HU_PI_Item_Product()=%s, getM_AttributeSetInstance_ID()=%s, getC_UOM()=%s, getQtyPacks()=%s, getC_BPartner()=%s, getDateOrdered()=%s, getM_HU_PI_Item_Product_ID()=%s, isInDispute()=%s]",
						orderLine, getM_Product_ID(), getM_Product(), getQty(), getM_HU_PI_Item_Product(), getM_AttributeSetInstance_ID(), getC_UOM(), getQtyPacks(), getC_BPartner(),
						getDateOrdered(), getM_HU_PI_Item_Product_ID(), isInDispute());
	}
}
