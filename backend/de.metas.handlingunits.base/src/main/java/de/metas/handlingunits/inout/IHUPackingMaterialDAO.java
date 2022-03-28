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

import de.metas.handlingunits.HuPackingMaterial;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.mpackage.PackageId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.util.List;

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

	@Nullable
	static I_M_Product extractProductOrNull(@NonNull final I_M_HU_PackingMaterial pm)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(pm.getM_Product_ID());
		return productId != null
				? Services.get(IProductDAO.class).getById(productId)
				: null;
	}

	/**
	 * Retrieve the packing material of a package by package Id.
	 * <p>
	 * If there's no packing material, null is returned.
	 * <p>
	 * sql:
	 *
	 * <pre>{@code
	 * SELECT pack.*
	 * FROM m_package_hu phu
	 * 		INNER JOIN m_hu_item huitem ON phu.m_hu_id = huitem.m_hu_id
	 * 		INNER JOIN m_hu_packingmaterial pack ON huitem.m_hu_packingmaterial_id = pack.m_hu_packingmaterial_id
	 * WHERE phu.m_package_id = 1000023
	 * LIMIT 1;
	 * }</pre>
	 * <p>
	 * thx to ruxi for transforming this sql query into "metasfresh"
	 */
	@Nullable
	I_M_HU_PackingMaterial retrievePackingMaterialOrNull(@NonNull final PackageId packageId);

	@Nullable
	I_M_HU_PackingMaterial retrieveHUPackingMaterialOrNull(@NonNull I_M_HU_Item huItem);

	/**
	 * Return the dimensions of the packing material, or a default with all dimensions set to 1
	 * <p>
	 * This method should not be here, it should belong to de.metas.shipper.gateway.commons.DeliveryOrderUtil.
	 * However it is here, as adding it there will create a circular dependency between de.metas.handlingunits.base and de.metas.shipper.gateway.commons, because I_M_HU_PackingMaterial must be imported.
	 */
	@NonNull
	PackageDimensions retrievePackageDimensions(@NonNull final I_M_HU_PackingMaterial packingMaterial, @NonNull final UomId toUomId);

	List<HuPackingMaterial> retrieveBy(@NonNull final HuPackingMaterialQuery query);

}
