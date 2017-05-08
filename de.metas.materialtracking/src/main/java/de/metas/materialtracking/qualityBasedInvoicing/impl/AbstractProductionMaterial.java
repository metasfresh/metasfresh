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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;

/* package */abstract class AbstractProductionMaterial implements IProductionMaterial
{
	// Services
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	protected final IUOMConversionContext getUOMConversionContext()
	{
		final IUOMConversionContext conversionCtx = uomConversionBL.createConversionContext(getM_Product());
		return conversionCtx;
	}

	@Override
	public final BigDecimal getQty(final I_C_UOM uomTo)
	{
		Check.assumeNotNull(uomTo, "uomTo not null");
		final BigDecimal qty = getQty();
		final I_C_UOM qtyUOM = getC_UOM();

		final IUOMConversionContext conversionCtx = getUOMConversionContext();
		final BigDecimal qtyInUOMTo = uomConversionBL.convertQty(conversionCtx, qty, qtyUOM, uomTo);
		return qtyInUOMTo;
	}

	@Override
	public final BigDecimal getQM_QtyDeliveredAvg(final I_C_UOM uomTo)
	{
		Check.assumeNotNull(uomTo, "uomTo not null");
		final IUOMConversionContext conversionCtx = getUOMConversionContext();
		final BigDecimal qty = getQM_QtyDeliveredAvg();
		final I_C_UOM qtyUOM = getC_UOM();
		final BigDecimal qtyInUOMTo = uomConversionBL.convertQty(conversionCtx, qty, qtyUOM, uomTo);
		return qtyInUOMTo;
	}

	@Override
	public boolean isByProduct()
	{
		return PPOrderUtil.isByProduct(getComponentType());
	}

}
