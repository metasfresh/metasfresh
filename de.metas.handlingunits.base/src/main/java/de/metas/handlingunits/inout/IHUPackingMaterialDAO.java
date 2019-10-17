/**
 *
 */
package de.metas.handlingunits.inout;

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

import java.util.List;

import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.NonNull;

public interface IHUPackingMaterialDAO extends ISingletonService
{
	/**
	 * Retrieve packing materials from same HU as given <code>pip</code>
	 *
	 * @param pip may be null in which case an empty list is returned
	 */
	List<I_M_HU_PackingMaterial> retrievePackingMaterials(final I_M_HU_PI_Item_Product pip);

	/**
	 * get packing material for a certain product
	 */
	I_M_HU_PackingMaterial retrivePackingMaterialOfProduct(final I_M_Product product);

	static I_M_Product extractProductOrNull(@NonNull final I_M_HU_PackingMaterial pm)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(pm.getM_Product_ID());
		return productId != null
				? Services.get(IProductDAO.class).getById(productId)
				: null;
	}
}
