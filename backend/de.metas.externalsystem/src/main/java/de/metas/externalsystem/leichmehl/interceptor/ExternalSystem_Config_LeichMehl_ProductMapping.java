/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.leichmehl.interceptor;

import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl_ProductMapping;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_ExternalSystem_Config_LeichMehl_ProductMapping.class)
@Component
public class ExternalSystem_Config_LeichMehl_ProductMapping
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	public final ExternalSystemConfigRepo externalSystemConfigRepo;

	public ExternalSystem_Config_LeichMehl_ProductMapping(@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_M_Product_ID)
	public void syncProductCategory(final I_ExternalSystem_Config_LeichMehl_ProductMapping configRecord)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(configRecord.getM_Product_ID());

		if (productId == null)
		{
			return;
		}

		final ProductCategoryId productCategoryId = productDAO.retrieveProductCategoryByProductId(productId);

		configRecord.setM_Product_Category_ID(ProductCategoryId.toRepoId(productCategoryId));
	}
}
