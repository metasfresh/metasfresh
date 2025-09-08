package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;

/* package */abstract class AbstractProductionMaterial implements IProductionMaterial
{
	// Services
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	protected final UOMConversionContext getUOMConversionContext()
	{
		final UOMConversionContext conversionCtx = UOMConversionContext.of(getM_Product());
		return conversionCtx;
	}

	@Override
	public final BigDecimal getQty(@NonNull final I_C_UOM uomTo)
	{
		final BigDecimal qty = getQty();
		final I_C_UOM qtyUOM = getC_UOM();

		final UOMConversionContext conversionCtx = getUOMConversionContext();
		final BigDecimal qtyInUOMTo = uomConversionBL.convertQty(conversionCtx, qty, qtyUOM, uomTo);
		return qtyInUOMTo;
	}

	@Override
	public final BigDecimal getQM_QtyDeliveredAvg(@NonNull final I_C_UOM uomTo)
	{
		final UOMConversionContext conversionCtx = getUOMConversionContext();
		final BigDecimal qty = getQM_QtyDeliveredAvg();
		final I_C_UOM qtyUOM = getC_UOM();
		final BigDecimal qtyInUOMTo = uomConversionBL.convertQty(conversionCtx, qty, qtyUOM, uomTo);
		return qtyInUOMTo;
	}

	@Override
	public boolean isByProduct()
	{
		return getComponentType() != null && getComponentType().isByProduct();
	}

}
