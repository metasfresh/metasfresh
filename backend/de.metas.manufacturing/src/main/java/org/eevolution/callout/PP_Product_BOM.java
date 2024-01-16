package org.eevolution.callout;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_M_Product;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMVersions;

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

	private final ProductBOMVersionsDAO bomVersionsDAO;

	public PP_Product_BOM(final ProductBOMVersionsDAO bomVersionsDAO)
	{
		this.bomVersionsDAO = bomVersionsDAO;
	}

	/**
	 * Updates BOM fields from selected bom versions, if any.
	 *
	 * @param bom
	 */
	@CalloutMethod(columnNames = I_PP_Product_BOM.COLUMNNAME_PP_Product_BOMVersions_ID)
	public void onBOMVersionsChanged(final I_PP_Product_BOM bom)
	{
		final ProductBOMVersionsId bomVersionsId = ProductBOMVersionsId.ofRepoId(bom.getPP_Product_BOMVersions_ID());

		final I_PP_Product_BOMVersions bomVersions = bomVersionsDAO.getBOMVersions(bomVersionsId);

		final ProductId productId = ProductId.ofRepoId(bomVersions.getM_Product_ID());

		final I_M_Product product = productsService.getById(productId);

		bom.setM_Product_ID(product.getM_Product_ID());
		bom.setValue(product.getValue());
		bom.setName(product.getName());
		bom.setDescription(product.getDescription());
		bom.setHelp(product.getHelp());
		bom.setC_UOM_ID(product.getC_UOM_ID());
	}
}
