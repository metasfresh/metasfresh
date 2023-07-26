package org.eevolution.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOM;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

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

/**
 * Manufacturing Product BOM header callout
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version developed by Victor Perez, Teo Sarca under ADempiere project
 */
@Callout(I_PP_Product_BOM.class)
public class PP_Product_BOM
{
	private final IProductBL productsService = Services.get(IProductBL.class);

	/**
	 * Updates BOM fields from selected product, if any.
	 *
	 * @param bom
	 */
	@CalloutMethod(columnNames = I_PP_Product_BOM.COLUMNNAME_M_Product_ID)
	public void onProductChanged(final I_PP_Product_BOM bom)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(bom.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final I_M_Product product = productsService.getById(productId);

		bom.setValue(product.getValue());
		bom.setName(product.getName());
		bom.setDescription(product.getDescription());
		bom.setHelp(product.getHelp());
		bom.setC_UOM_ID(product.getC_UOM_ID());
	}
}
