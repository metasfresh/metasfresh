package de.metas.ui.web.material.cockpit.filters;

import com.google.common.base.Predicates;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
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

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	
	public static DocumentFilterDescriptor createFilterDescriptor()
	{
		final DocumentFilterParamDescriptor.Builder productNameParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(ProductFilterVO.PARAM_ProductName)
				.setDisplayName(msgBL.translatable(I_MD_Cockpit.COLUMNNAME_ProductName))
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setOperator(Operator.LIKE_I);

		final DocumentFilterParamDescriptor.Builder productValueParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_MD_Cockpit.COLUMNNAME_ProductValue)
				.setDisplayName(msgBL.translatable(I_MD_Cockpit.COLUMNNAME_ProductValue))
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setOperator(Operator.LIKE_I);

		final DocumentFilterParamDescriptor.Builder productCategoryParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_M_Product.COLUMNNAME_M_Product_Category_ID)
				.setDisplayName(msgBL.translatable(I_M_Product.COLUMNNAME_M_Product_Category_ID))
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptor(SqlLookupDescriptor.searchInTable(I_M_Product_Category.Table_Name).provideForFilter())
				.setOperator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder isPurchasedParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_M_Product.COLUMNNAME_IsPurchased)
				.setDisplayName(msgBL.translatable(I_M_Product.COLUMNNAME_IsPurchased))
				.setWidgetType(DocumentFieldWidgetType.YesNo)
				.setOperator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder isSoldParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_M_Product.COLUMNNAME_IsSold)
				.setDisplayName(msgBL.translatable(I_M_Product.COLUMNNAME_IsSold))
				.setWidgetType(DocumentFieldWidgetType.YesNo)
				.setOperator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder isActive = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_M_Product.COLUMNNAME_IsActive)
				.setDisplayName(msgBL.translatable(I_M_Product.COLUMNNAME_IsActive))
				.setWidgetType(DocumentFieldWidgetType.YesNo)
				.setOperator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder isDiscontinued = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_M_Product.COLUMNNAME_Discontinued)
				.setDisplayName(msgBL.translatable(I_M_Product.COLUMNNAME_Discontinued))
				.setWidgetType(DocumentFieldWidgetType.YesNo)
				.setOperator(Operator.EQUAL);

		return DocumentFilterDescriptor.builder()
				.setFrequentUsed(true)
				.setFilterId(ProductFilterVO.FILTER_ID)
				.setDisplayName(msgBL.getTranslatableMsgText(MSG_FILTER_CAPTION))
				.addParameter(productNameParameter)
				.addParameter(productValueParameter)
				.addParameter(productCategoryParameter)
				.addParameter(isPurchasedParameter)
				.addParameter(isSoldParameter)
				.addParameter(isActive)
				.addParameter(isDiscontinued)
				.build();
	}

	public static ProductFilterVO extractProductFilterVO(@NonNull final DocumentFilterList filters)
	{
		return filters.getFilterById(ProductFilterVO.FILTER_ID)
				.map(ProductFilterUtil::extractProductFilterVO)
				.orElse(ProductFilterVO.EMPTY);
	}

	@NonNull
	public static DocumentFilter createFilterActiveProducts()
	{
		return DocumentFilter.builder()
				.setFilterId(ProductFilterVO.FILTER_ID)
				.setCaption(msgBL.translatable(ProductFilterVO.PARAM_IsActive))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(ProductFilterVO.PARAM_IsActive, Operator.EQUAL, true))
				.build();
	}

	public static ProductFilterVO extractProductFilterVO(@NonNull final DocumentFilter filter)
	{
		Check.assume(ProductFilterVO.FILTER_ID.equals(filter.getFilterId()), "Filter ID is {} but it was {}", ProductFilterVO.FILTER_ID, filter);

		return ProductFilterVO.builder()
				.productName(filter.getParameterValueAsString(ProductFilterVO.PARAM_ProductName, null))
				.productValue(filter.getParameterValueAsString(ProductFilterVO.PARAM_ProductValue, null))
				.productCategoryId(filter.getParameterValueAsInt(ProductFilterVO.PARAM_M_Product_Category_ID, -1))
				.isPurchased(filter.getParameterValueAsBoolean(ProductFilterVO.PARAM_IsPurchased, null))
				.isSold(filter.getParameterValueAsBoolean(ProductFilterVO.PARAM_IsSold, null))
				.isActive(filter.getParameterValueAsBoolean(ProductFilterVO.PARAM_IsActive, null))
				.isDiscontinued(filter.getParameterValueAsBoolean(ProductFilterVO.PARAM_IsDiscontinued, null))
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
			@Nullable final boolean nullForEmptyFilterVO)
	{
		if (productFilterVO == null)
		{
			return null;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_M_Product> productFilter = queryBL
				.createCompositeQueryFilter(I_M_Product.class);
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

		final Boolean isActive = productFilterVO.getIsActive();
		if (isActive != null)
		{
			productFilter.addEqualsFilter(I_M_Product.COLUMN_IsActive, isActive);
			anyRestrictionAdded = true;
		}

		final Boolean isDiscontinued = productFilterVO.getIsDiscontinued();
		if (isDiscontinued != null)
		{
			productFilter.addEqualsFilter(I_M_Product.COLUMN_Discontinued, isDiscontinued);
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

		// IsActive
		if (filterVO.getIsActive() != null && filterVO.getIsActive() != product.isActive())
		{
			return false;
		}

		// Discontinued
		if (filterVO.getIsDiscontinued() != null && filterVO.getIsDiscontinued() != product.isDiscontinued())
		{
			return false;
		}
		
		return true;
	}
}
