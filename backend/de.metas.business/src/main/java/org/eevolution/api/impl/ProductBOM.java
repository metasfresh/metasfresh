/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.material.event.commons.ProductDescriptor;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOMLine;

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

	@NonNull
	List<ProductBOMLine> coProducts;

	public ProductId getProductId()
	{
		return ProductId.ofRepoId(productDescriptor.getProductId());
	}
}
