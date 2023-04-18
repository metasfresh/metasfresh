package de.metas.costing.interceptors;

import de.metas.costing.ICostDetailService;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;

/*
 * #%L
 * de.metas.business
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

@Interceptor(I_M_Product.class)
class M_Product
{
	private final ICurrentCostsRepository currentCostsRepository;
	private final ICostDetailService costDetailsService;

	public M_Product(
			@NonNull final ICurrentCostsRepository currentCostsRepository,
			@NonNull final ICostDetailService costDetailsService)
	{
		this.currentCostsRepository = currentCostsRepository;
		this.costDetailsService = costDetailsService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_Product.COLUMNNAME_C_UOM_ID)
	public void assertNoCosts(final I_M_Product product)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		if (costDetailsService.hasCostDetailsForProductId(productId))
		{
			throw new AdempiereException("@CannotDeleteProductsWithCostDetails@");
		}

	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_M_Product.COLUMNNAME_M_Product_Category_ID)
	public void createDefaultProductCosts(final I_M_Product product)
	{
		currentCostsRepository.createDefaultProductCosts(product);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteCosts(final I_M_Product product)
	{
		currentCostsRepository.deleteForProduct(product);
	}
}
