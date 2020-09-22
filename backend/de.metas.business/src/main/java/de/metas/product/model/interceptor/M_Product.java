package de.metas.product.model.interceptor;

import de.metas.organization.OrgId;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaSelector;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.product.ProductPOCopyRecordSupport;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Component()
public class M_Product
{
	private final IProductPlanningSchemaBL productPlanningSchemaBL = Services.get(IProductPlanningSchemaBL.class);

	@Init
	public void init(final IModelValidationEngine engine)
	{
		CopyRecordFactory.enableForTableName(I_M_Product.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_M_Product.Table_Name, ProductPOCopyRecordSupport.class);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void afterNew(final @NonNull I_M_Product product, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isNew())
		{
			createOrUpdateProductPlanningsForSelector(product);
		}
	}

	private void createOrUpdateProductPlanningsForSelector(final @NonNull I_M_Product product)
	{
		final ProductPlanningSchemaSelector productPlanningSchemaSelector = ProductPlanningSchemaSelector.ofNullableCode(product.getM_ProductPlanningSchema_Selector());
		if (productPlanningSchemaSelector == null)
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());

		productPlanningSchemaBL.createOrUpdateProductPlanningsForSelector(productId, orgId, productPlanningSchemaSelector);
	}

}
