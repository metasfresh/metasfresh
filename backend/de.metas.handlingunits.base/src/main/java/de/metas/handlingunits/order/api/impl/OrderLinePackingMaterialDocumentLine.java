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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.impl.AbstractPackingMaterialDocumentLine;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;

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
	public ProductId getProductId()
	{
		return ProductId.ofRepoId(orderLine.getM_Product_ID());
	}

	private I_C_UOM getUOM()
	{
		return Services.get(IUOMDAO.class).getById(orderLine.getC_UOM_ID());
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
	 * @param qtyOrdered ordered quantity in stock UOM, which is also converted to qtyEntered.
	 */
	@Override
	protected void setQty(final BigDecimal qtyOrdered)
	{
		orderLine.setQtyOrdered(qtyOrdered);

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final Quantity qtyInStockUOM = Quantitys.of(qtyOrdered, ProductId.ofRepoId(orderLine.getM_Product_ID()));

		final Quantity qtyEntered = orderLineBL.convertQtyToUOM(qtyInStockUOM, orderLine);
		orderLine.setQtyEntered(qtyEntered.toBigDecimal());
	}
}
