/*
 * #%L
 * de.metas.business
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

package de.metas.pricing.interceptor;

import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_M_Product_TaxCategory;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Interceptor(I_M_Product_TaxCategory.class)
@Component
public class M_Product_TaxCategory
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	private final ProductTaxCategoryService productTaxCategoryService;

	public M_Product_TaxCategory(final @NonNull ProductTaxCategoryService productTaxCategoryService)
	{
		this.productTaxCategoryService = productTaxCategoryService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE },
			ifColumnsChanged = { I_M_Product_TaxCategory.COLUMNNAME_IsActive })
	public void forbidRemovingTaxCategoryCoverage(@NonNull final I_M_Product_TaxCategory productTaxCategory, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isChange() && productTaxCategory.isActive())
		{
			return;
		}

		if (hasProductPricesDependingOnIt(productTaxCategory))
		{
			throw new AdempiereException("Product Price with no tax category matches product tax category!")
					.appendParametersToMessage()
					.setParameter("M_Product_ID", productTaxCategory.getM_Product_ID());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_M_Product_TaxCategory.COLUMNNAME_ValidFrom,
			I_M_Product_TaxCategory.COLUMNNAME_M_Product_ID,
			I_M_Product_TaxCategory.COLUMNNAME_C_Country_ID
	})
	public void forbidChangingValues(@NonNull final I_M_Product_TaxCategory productTaxCategory)
	{
		final I_M_Product_TaxCategory oldProductTaxCategory = InterfaceWrapperHelper.createOld(productTaxCategory, I_M_Product_TaxCategory.class);

		if (hasProductPricesDependingOnIt(oldProductTaxCategory))
		{
			throw new AdempiereException("Product Price with no tax category matches product tax category")
					.appendParametersToMessage()
					.setParameter("M_Product_ID", productTaxCategory.getM_Product_ID());
		}
	}

	private boolean hasProductPricesDependingOnIt(@NonNull final I_M_Product_TaxCategory productTaxCategory)
	{
		final IQuery<I_M_PriceList_Version> priceListVersionQuery;
		if (productTaxCategory.getC_Country_ID() > 0)
		{
			priceListVersionQuery = queryBL.createQueryBuilder(I_M_PriceList.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Country_ID, productTaxCategory.getC_Country_ID())
					.andCollectChildren(I_M_PriceList_Version.COLUMN_M_PriceList_ID)
					.addOnlyActiveRecordsFilter()
					.addCompareFilter(I_M_PriceList_Version.COLUMNNAME_ValidFrom, CompareQueryFilter.Operator.GREATER_OR_EQUAL, productTaxCategory.getValidFrom())
					.create();
		}
		else
		{
			priceListVersionQuery = queryBL.createQueryBuilder(I_M_PriceList_Version.class)
					.addOnlyActiveRecordsFilter()
					.addCompareFilter(I_M_PriceList_Version.COLUMNNAME_ValidFrom, CompareQueryFilter.Operator.GREATER_OR_EQUAL, productTaxCategory.getValidFrom())
					.create();
		}

		final Iterator<I_M_ProductPrice> productPriceIterator = queryBL.createQueryBuilder(I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID, null)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productTaxCategory.getM_Product_ID())
				.addInSubQueryFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, priceListVersionQuery)
				.create()
				.iterate(I_M_ProductPrice.class);

		if (!productPriceIterator.hasNext())
		{
			return false;
		}

		while (productPriceIterator.hasNext())
		{
			final boolean isSolelyDependentOnTheChangedTax = productTaxCategoryService.findAllFor(productPriceIterator.next()).count() <= 1;

			if (isSolelyDependentOnTheChangedTax)
			{
				return true;
			}
		}

		return false;
	}
}
