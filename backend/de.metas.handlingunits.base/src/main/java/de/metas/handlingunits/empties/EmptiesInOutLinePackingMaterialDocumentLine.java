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
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.impl.AbstractPackingMaterialDocumentLine;
import de.metas.handlingunits.model.I_M_InOutLine;

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
	public int getM_Product_ID()
	{
		return inoutLine.getM_Product_ID();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return inoutLine.getM_Product();
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

		final Properties ctx = InterfaceWrapperHelper.getCtx(inoutLine);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final BigDecimal qtyEntered = uomConversionBL.convertFromProductUOM(
				ctx, getM_Product(), inoutLine.getC_UOM(), qty);
		inoutLine.setQtyEntered(qtyEntered);
	}
}
