package de.metas.handlingunits.empties;

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

import de.metas.handlingunits.impl.AbstractPackingMaterialDocumentLine;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;

/* package */class EmptiesInOutLinePackingMaterialDocumentLine extends AbstractPackingMaterialDocumentLine
{
	private final I_M_InOutLine inoutLine;

	EmptiesInOutLinePackingMaterialDocumentLine(final I_M_InOutLine inoutLine)
	{
		super();

		Check.assumeNotNull(inoutLine, "inoutLine not null");
		this.inoutLine = inoutLine;
	}

	public I_M_InOutLine getM_InOutLine()
	{
		return inoutLine;
	}

	@Override
	public ProductId getProductId()
	{
		return ProductId.ofRepoId(inoutLine.getM_Product_ID());
	}

	/**
	 * @returns MovementQty of the wrapped inout line
	 */
	@Override
	public BigDecimal getQty()
	{
		return inoutLine.getMovementQty();
	}

	/**
	 * Sets both MovementQty and QtyEntered of the wrapped order line.
	 *
	 * @param qty MovementQty which will also be converted to QtyEntered.
	 */
	@Override
	protected void setQty(final BigDecimal qty)
	{
		inoutLine.setMovementQty(qty);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final BigDecimal qtyEntered = uomConversionBL.convertFromProductUOM(getProductId(), UomId.ofRepoId(inoutLine.getC_UOM_ID()), qty);
		inoutLine.setQtyEntered(qtyEntered);
	}
}
