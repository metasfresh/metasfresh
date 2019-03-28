package de.metas.vertical.pharma;

import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_M_Product;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class PharmaProductRepository
{
	public PharmaProduct getById(@NonNull final ProductId productId)
	{
		final IProductDAO productRepo = Services.get(IProductDAO.class);

		final I_M_Product product = InterfaceWrapperHelper.create(productRepo.getById(productId.getRepoId()), I_M_Product.class);

		return PharmaProduct.builder()
				.productId(productId)
				.prescriptionRequired(product.isPrescription())
				.value(product.getValue())
				.build();
	}
}
