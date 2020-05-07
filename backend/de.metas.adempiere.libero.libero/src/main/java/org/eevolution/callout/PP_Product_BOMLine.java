package org.eevolution.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import de.metas.material.planning.pporder.LiberoException;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Callout(I_PP_Product_BOMLine.class)
public class PP_Product_BOMLine
{
	/**
	 * Validates and them updates the BOM line fields from selected product, if any.
	 *
	 * @param bomLine
	 */
	@CalloutMethod(columnNames = I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID)
	public void onProductChanged(final I_PP_Product_BOMLine bomLine)
	{
		final int M_Product_ID = bomLine.getM_Product_ID();
		if (M_Product_ID <= 0)
		{
			return;
		}

		final I_PP_Product_BOM bom = bomLine.getPP_Product_BOM();
		if (bom.getM_Product_ID() == bomLine.getM_Product_ID())
		{
			throw new AdempiereException("@ValidComponent@ - selected product cannot be a BOM component because it's actually the BOM product");
		}

		// Set BOM Line defaults
		final I_M_Product product = bomLine.getM_Product();
		bomLine.setDescription(product.getDescription());
		bomLine.setHelp(product.getHelp());
		bomLine.setC_UOM_ID(product.getC_UOM_ID());
	}

	@CalloutMethod(columnNames = { I_PP_Product_BOMLine.COLUMNNAME_VariantGroup})
	public void validateVariantGroup(final I_PP_Product_BOMLine bomLine)
	{
		final boolean valid = Services.get(IProductBOMBL.class).isValidVariantGroup(bomLine);
		if (!valid)
		{
			throw new LiberoException("@NoSuchVariantGroup@");
		}
	}

}
