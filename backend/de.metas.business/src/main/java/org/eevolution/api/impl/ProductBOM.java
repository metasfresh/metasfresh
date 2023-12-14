/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.eevolution.api.impl;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder(toBuilder = true)
public class ProductBOM
{
	@NonNull
	ProductBOMId productBOMId;

	@NonNull
	ProductDescriptor productDescriptor;

	@NonNull
	UomId uomId;

	@NonNull
	List<I_PP_Product_BOMLine> components;

	@NonNull
	Map<ProductDescriptor, ProductBOM> componentsProductBOMs;

	public Map<ProductDescriptor, Quantity> calculateRequiredQtyInStockUOMForComponents(@NonNull final Quantity qty)
	{
		final Map<ProductDescriptor, Quantity> result = new HashMap<>();
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		final IUOMConversionBL uomConversionBL =  Services.get(IUOMConversionBL.class);

		final ProductId productId = ProductId.ofRepoId(productDescriptor.getProductId());
		final Quantity qtyInBomUom = uomConversionBL.convertQuantityTo(qty, productId, uomId);

		for (final I_PP_Product_BOMLine component : components)
		{
			final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(component.getM_Product_ID(), AttributesKey.NONE, component.getM_AttributeSetInstance_ID());
			final ProductId componentProductId = ProductId.ofRepoId(component.getM_Product_ID());
			final I_C_UOM componentUOM = uomDAO.getById(component.getC_UOM_ID());
			final Quantity componentQty = Quantity.of(productBOMBL.computeQtyRequired(component, productId, qtyInBomUom.toBigDecimal()), componentUOM);
			final Quantity componentQtyInStockUOM = uomConversionBL.convertToProductUOM(componentQty, ProductId.ofRepoId(component.getM_Product_ID()));
			result.merge(productDescriptor, componentQtyInStockUOM, Quantity::add);

			if (componentsProductBOMs.containsKey(productDescriptor))
			{
				final ProductBOM componentProductBOM = componentsProductBOMs.get(productDescriptor);
				final Quantity componentQtyInBomUom = uomConversionBL.convertQuantityTo(componentQtyInStockUOM, componentProductId, componentProductBOM.getUomId());
				componentProductBOM.calculateRequiredQtyInStockUOMForComponents(componentQtyInBomUom).forEach((key, value) -> result.merge(key, value, Quantity::add));
			}
		}

		return result;
	}
}
