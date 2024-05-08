package de.metas.pricing.interceptor;

import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.pricing.M_ProductPrice_POCopyRecordSupport;
import de.metas.pricing.service.ProductPrices;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
 * @author metas-dev <dev@metasfresh.com>
 * @task Prevent users from creating duplicate main prices https://github.com/metasfresh/metasfresh/issues/2510
 */
@Interceptor(I_M_ProductPrice.class)
@Component
public class M_ProductPrice
{

	@Init
	public void init(final IModelValidationEngine engine)
	{
		CopyRecordFactory.enableForTableName(I_M_ProductPrice.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_M_ProductPrice.Table_Name, M_ProductPrice_POCopyRecordSupport.class);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void assertMainProductPriceIsNotDuplicate(@NonNull final I_M_ProductPrice productPrice)
	{
		ProductPrices.assertMainProductPriceIsNotDuplicate(productPrice);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_M_ProductPrice.COLUMNNAME_C_UOM_ID, I_M_ProductPrice.COLUMNNAME_IsInvalidPrice, I_M_ProductPrice.COLUMNNAME_IsActive })
	public void assertUomConversionExists(@NonNull final I_M_ProductPrice productPrice)
	{
		ProductPrices.assertUomConversionExists(productPrice);
	}
}
