package de.metas.handlingunits.order.api.impl;

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
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.impl.AbstractPackingMaterialDocumentLine;
import de.metas.handlingunits.model.I_C_OrderLine;
import lombok.NonNull;

/* package */class OrderLinePackingMaterialDocumentLine extends AbstractPackingMaterialDocumentLine
{
	private final I_C_OrderLine orderLine;

	public OrderLinePackingMaterialDocumentLine(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		this.orderLine = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);

		Check.assume(this.orderLine.isPackagingMaterial(), "Orderline shall have PackingMaterial flag set: {}", this.orderLine);
	}

	public I_C_OrderLine getC_OrderLine()
	{
		return orderLine;
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

	/**
	 * @returns QtyOrdered of the wrapped order line
	 */
	@Override
	public BigDecimal getQty()
	{
		return orderLine.getQtyOrdered();
	}

	/**
	 * Sets both QtyOrdered and QtyEntered of the wrapped order line.
	 *
	 * @param qty QtyOrdered which will also be converted to qtyEntered.
	 */
	@Override
	protected void setQty(final BigDecimal qty)
	{
		orderLine.setQtyOrdered(qty);

		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final BigDecimal qtyEntered = uomConversionBL.convertFromProductUOM(
				ctx, getM_Product(), orderLine.getC_UOM(), qty);

		orderLine.setQtyEntered(qtyEntered);
	}
}
