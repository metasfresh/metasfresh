package de.metas.handlingunits.attribute.impl;

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


import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_UOM;

import de.metas.handlingunits.attribute.IWeightableBL;
import de.metas.product.IProductBL;

public class WeightableBL implements IWeightableBL
{
	@Override
	public boolean isWeightableUOMType(final String uomType)
	{
		if (!X_C_UOM.UOMTYPE_Weigth.equals(uomType))
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isWeightable(final I_C_UOM uom)
	{
		// guard against null, be tolerant, just return "not weightable"
		if (uom == null)
		{
			return false;
		}

		final String uomType = uom.getUOMType();
		return isWeightableUOMType(uomType);
	}

	@Override
	public boolean isWeightable(final I_M_Product product)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final I_C_UOM stockingUOM = productBL.getStockingUOM(product);
		return isWeightable(stockingUOM);
	}
}
