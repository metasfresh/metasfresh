package de.metas.ui.web.material.cockpit.filters;

import com.google.common.base.Predicates;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/*
 * #%L
 * metasfresh-webui-api
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

@UtilityClass
public class ProductFilterUtil
{
	private static final AdMessageKey MSG_FILTER_CAPTION = AdMessageKey.of("Product");

	public static DocumentFilterDescriptor createFilterDescriptor()
	{
		final DocumentFilterParamDescriptor.Builder productNameParameter = DocumentFilterParamDescriptor.builder()
				.fieldName(ProductFilterVO.PARAM_ProductName)
				.displayName(Services.get(IMsgBL.class).translatable(I_MD_Cockpit.COLUMNNAME_ProductName))
				.widgetType(DocumentFieldWidgetType.Text)
				.operator(Operator.LIKE_I);

		final DocumentFilterParamDescriptor.Builder productValueParameter = DocumentFilterParamDescriptor.builder()
				.fieldName(I_MD_Cockpit.COLUMNNAME_ProductValue)
				.displayName(Services.get(IMsgBL.class).translatable(I_MD_Cockpit.COLUMNNAME_ProductValue))
				.widgetType(DocumentFieldWidgetType.Text)
				.operator(Operator.LIKE_I);

		final DocumentFilterParamDescriptor.Builder productParameter = DocumentFilterParamDescriptor.builder()
				.fieldName(I_M_Product.COLUMNNAME_M_Product_ID)
				.displayName(Services.get(IMsgBL.class).translatable(I_M_Product.COLUMNNAME_M_Product_ID))
				.widgetType(DocumentFieldWidgetType.Lookup)
				.lookupDescriptor(LookupDescriptorProviders.sharedInstance().searchInTable(I_M_Product.Table_Name).provideForFilter())
				.operator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder productCategoryParameter = DocumentFilterParamDescriptor.builder()
				.fieldName(I_M_Product.COLUMNNAME_M_Product_Category_ID)
				.displayName(Services.get(IMsgBL.class).translatable(I_M_Product.COLUMNNAME_M_Product_Category_ID))
				.widgetType(DocumentFieldWidgetType.Lookup)
				.lookupDescriptor(LookupDescriptorProviders.sharedInstance().searchInTable(I_M_Product_Category.Table_Name).provideForFilter())
				.operator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder isPurchasedParameter = DocumentFilterParamDescriptor.builder()
				.fieldName(I_M_Product.COLUMNNAME_IsPurchased)
				.displayName(Services.get(IMsgBL.class).translatable(I_M_Product.COLUMNNAME_IsPurchased))
				.widgetType(DocumentFieldWidgetType.YesNo)
				.operator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder isSoldParameter = DocumentFilterParamDescriptor.builder()
				.fieldName(I_M_Product.COLUMNNAME_IsSold)
				.displayName(Services.get(IMsgBL.class).translatable(I_M_Product.COLUMNNAME_IsSold))
				.widgetType(DocumentFieldWidgetType.YesNo)
				.operator(Operator.EQUAL);

		return DocumentFilterDescriptor.builder()
				.setFrequentUsed(true)
				.setFilterId(ProductFilterVO.FILTER_ID)
				.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_FILTER_CAPTION))
				.addParameter(productNameParameter)
				.addParameter(productValueParameter)
				.addParameter(productParameter)
				.addParameter(productCategoryParameter)
				.addParameter(isPurchasedParameter)
				.addParameter(isSoldParameter)
				.build();
	}

	public static ProductFilterVO extractProductFilterVO(@NonNull final DocumentFilterList filters)
	{
		return filters.getFilterById(ProductFilterVO.FILTER_ID)
				.map(ProductFilterUtil::extractProductFilterVO)
				.orElse(ProductFilterVO.EMPTY);
	}

	public static ProductFilterVO extractProductFilterVO(@NonNull final DocumentFilter filter)
	{
		Check.assume(ProductFilterVO.FILTER_ID.equals(filter.getFilterId()), "Filter ID is {} but it was {}", ProductFilterVO.FILTER_ID, filter);

		return ProductFilterVO.builder()
				.productName(filter.getParameterValueAsString(ProductFilterVO.PARAM_ProductName, null))
				.productValue(filter.getParameterValueAsString(ProductFilterVO.PARAM_ProductValue, null))
				.productId(filter.getParameterValueAsInt(ProductFilterVO.PARAM_M_Product_ID, -1))
				.productCategoryId(filter.getParameterValueAsInt(ProductFilterVO.PARAM_M_Product_Category_ID, -1))
				.isPurchased(filter.getParameterValueAsBoolean(ProductFilterVO.PARAM_IsPurchased, null))
				.isSold(filter.getParameterValueAsBoolean(ProductFilterVO.PARAM_IsSold, null))
				.build();
	}

	public static IQuery<I_M_Product> createProductQueryOrNull(@Nullable final ProductFilterVO productFilterVO)
	{
		if (productFilterVO == null)
		{
			return null;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryFilter<I_M_Product> productFilterOrNull = createProductQueryFilterOrNull(
				productFilterVO,
				true/* nullForEmptyFilterVO */);
		if (productFilterOrNull == null)
		{
			return null;
		}

		return queryBL
				.createQueryBuilder(I_M_Product.class)
				.filter(productFilterOrNull)
				.create();
	}

	public static IQueryFilter<I_M_Product> createProductQueryFilterOrNull(
			@Nullable final ProductFilterVO productFilterVO,
			final boolean nullForEmptyFilterVO)
	{
		if (productFilterVO == null)
		{
			return null;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_M_Product> productFilter = queryBL
				.createCompositeQueryFilter(I_M_Product.class)
				.addOnlyActiveRecordsFilter();
		boolean anyRestrictionAdded = false;

		final String productName = productFilterVO.getProductName();
		if (!Check.isEmpty(productName, true))
		{
			final boolean ignoreCase = true;
			productFilter.addStringLikeFilter(I_M_Product.COLUMN_Name, productName, ignoreCase);
			anyRestrictionAdded = true;
		}

		final String productValue = productFilterVO.getProductValue();
		if (!Check.isEmpty(productValue, true))
		{
			final boolean ignoreCase = true;
			productFilter.addStringLikeFilter(I_M_Product.COLUMN_Value, productValue, ignoreCase);
			anyRestrictionAdded = true;
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(productFilterVO.getProductId());
		if (productId != null)
		{
			productFilter.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_ID, productId);
			anyRestrictionAdded = true;
		}

		final int productCategoryId = productFilterVO.getProductCategoryId();
		if (productCategoryId > 0)
		{
			productFilter.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, productCategoryId);
			anyRestrictionAdded = true;
		}

		final Boolean isPurchased = productFilterVO.getIsPurchased();
		if (isPurchased != null)
		{
			productFilter.addEqualsFilter(I_M_Product.COLUMN_IsPurchased, isPurchased);
			anyRestrictionAdded = true;
		}

		final Boolean isSold = productFilterVO.getIsSold();
		if (isSold != null)
		{
			productFilter.addEqualsFilter(I_M_Product.COLUMN_IsSold, isSold);
			anyRestrictionAdded = true;
		}

		//
		if (!anyRestrictionAdded && nullForEmptyFilterVO)
		{
			return null;
		}
		return productFilter;
	}

	public static Predicate<I_M_Product> toPredicate(@NonNull final DocumentFilterList filters)
	{
		if (filters.isEmpty())
		{
			return Predicates.alwaysTrue();
		}

		final ProductFilterVO productFilterVO = extractProductFilterVO(filters);
		return product -> doesProductMatchFilter(product, productFilterVO);
	}

	public static boolean doesProductMatchFilter(
			@NonNull final I_M_Product product,
			@NonNull final ProductFilterVO filterVO)
	{
		final boolean ignoreCase = true;

		// ProductName
		final String productName = filterVO.getProductName();
		if (!Check.isEmpty(productName, true))
		{
			final StringLikeFilter<I_M_Product> likeFilter = new StringLikeFilter<>(I_M_Product.COLUMNNAME_Name, productName, ignoreCase);
			if (!likeFilter.accept(product))
			{
				return false;
			}
		}

		// ProductValue
		final String productValue = filterVO.getProductValue();
		if (!Check.isEmpty(productValue, true))
		{
			final StringLikeFilter<I_M_Product> likeFilter = new StringLikeFilter<>(I_M_Product.COLUMNNAME_Value, productValue, ignoreCase);
			if (!likeFilter.accept(product))
			{
				return false;
			}
		}

		// Product
		if (filterVO.getProductId() > 0 && product.getM_Product_ID() != filterVO.getProductId())
		{
			return false;
		}

		// Product Category
		if (filterVO.getProductCategoryId() > 0 && product.getM_Product_Category_ID() != filterVO.getProductCategoryId())
		{
			return false;
		}

		// IsPurchase
		if (filterVO.getIsPurchased() != null && filterVO.getIsPurchased() != product.isPurchased())
		{
			return false;
		}

		// IsSold
		if (filterVO.getIsSold() != null && filterVO.getIsSold() != product.isSold())
		{
			return false;
		}

		return true;
	}
}
