/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.eevolution.api.BOMCreateRequest;
import org.eevolution.api.BOMVersionsCreateRequest;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOM;
import org.springframework.stereotype.Service;

@Service
public class ProductBOMService
{
	private final IProductBOMDAO bomRepo = Services.get(IProductBOMDAO.class);
	private final ProductBOMVersionsDAO bomVersionsDAO;

	public ProductBOMService(@NonNull final ProductBOMVersionsDAO bomVersionsDAO)
	{
		this.bomVersionsDAO = bomVersionsDAO;
	}

	@NonNull
	public I_PP_Product_BOM createBOM(@NonNull final BOMCreateRequest request)
	{
		final ProductId productId = request.getProductId();

		final ProductBOMVersionsId bomVersionsId = bomVersionsDAO.retrieveBOMVersionsId(productId)
				.orElseGet(() -> bomVersionsDAO.createBOMVersions(BOMVersionsCreateRequest.of(request)));

		return bomRepo.createBOM(bomVersionsId, request);
	}
}
