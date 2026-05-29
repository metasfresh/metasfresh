package de.metas.pricing.interceptor;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.pricing.service.ProductPrices;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
 * @implSpec Prevent users from creating duplicate main prices <a href="https://github.com/metasfresh/metasfresh/issues/2510">2510</a>
 */
@Interceptor(I_M_ProductPrice.class)
@Component
public class M_ProductPrice
{
	private static final AdMessageKey MSG_NO_C_TAX_CATEGORY_FOR_PRODUCT_PRICE = AdMessageKey.of("MissingTaxCategoryForProductPrice");

	private final ProductTaxCategoryService productTaxCategoryService;
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public M_ProductPrice(@NonNull final ProductTaxCategoryService productTaxCategoryService)
	{
		this.productTaxCategoryService = productTaxCategoryService;
	}

	@Init
	public void init(final IModelValidationEngine engine)
	{
		CopyRecordFactory.enableForTableName(I_M_ProductPrice.Table_Name);
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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID })
	public void assertProductTaxCategoryExists(@NonNull final I_M_ProductPrice productPrice)
	{
		if (productPrice.getC_TaxCategory_ID() <= 0)
		{
			final Optional<TaxCategoryId> taxCategoryId = productTaxCategoryService.getTaxCategoryIdOptional(productPrice);

			if (!taxCategoryId.isPresent())
			{
				final ITranslatableString message = msgBL.getTranslatableMsgText(MSG_NO_C_TAX_CATEGORY_FOR_PRODUCT_PRICE);
				throw new AdempiereException(message).markAsUserValidationError();
			}
		}
	}
}
