package de.metas.handlingunits.attribute.impl;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_UOM;

import de.metas.handlingunits.attribute.IWeightableBL;
import de.metas.product.IProductBL;
import de.metas.util.Services;

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
