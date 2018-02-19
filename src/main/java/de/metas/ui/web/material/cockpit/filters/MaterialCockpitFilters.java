package de.metas.ui.web.material.cockpit.filters;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

@Service
public class MaterialCockpitFilters
{
	private static final String MSG_PRODUCT = "Product";
	private static final String MSG_DATE = "Date";

	private DocumentFilterDescriptorsProvider filterDescriptors;

	public DocumentFilterDescriptorsProvider getFilterDescriptors()
	{
		if (filterDescriptors == null)
		{
			filterDescriptors = createFilterDescriptors();
		}
		return filterDescriptors;
	}

	private DocumentFilterDescriptorsProvider createFilterDescriptors()
	{
		final DocumentFilterDescriptor dateOnlyfilter = createDateOnlyFilter();
		final DocumentFilterDescriptor nonParamsFilter = createAllParamsFilter();
		return ImmutableDocumentFilterDescriptorsProvider.of(dateOnlyfilter, nonParamsFilter);
	}

	private DocumentFilterDescriptor createDateOnlyFilter()
	{
		final DocumentFilterParamDescriptor.Builder standaloneParamDescriptor = DocumentFilterParamDescriptor.builder()
				.setFieldName(DateFilterVO.PARAM_Date)
				.setDisplayName(Services.get(IMsgBL.class).translatable(DateFilterVO.PARAM_Date))
				.setWidgetType(DocumentFieldWidgetType.Date)
				.setOperator(Operator.EQUAL)
				.setMandatory(true)
				.setShowIncrementDecrementButtons(true);

		return DocumentFilterDescriptor.builder()
				.setFrequentUsed(true)
				.setFilterId(DateFilterVO.FILTER_ID)
				.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_DATE))
				.addParameter(standaloneParamDescriptor)
				.build();
	}

	private DocumentFilterDescriptor createAllParamsFilter()
	{
		final DocumentFilterParamDescriptor.Builder productNameParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(ProductFilterVO.PARAM_ProductName)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_MD_Cockpit.COLUMNNAME_ProductName))
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setOperator(Operator.LIKE_I);

		final DocumentFilterParamDescriptor.Builder productValueParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_MD_Cockpit.COLUMNNAME_ProductValue)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_MD_Cockpit.COLUMNNAME_ProductValue))
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setOperator(Operator.LIKE_I);

		final DocumentFilterParamDescriptor.Builder productCategoryParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_MD_Cockpit.COLUMNNAME_M_Product_Category_ID)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_MD_Cockpit.COLUMNNAME_M_Product_Category_ID))
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptor(SqlLookupDescriptor.searchInTable(I_M_Product_Category.Table_Name).provideForScope(LookupScope.DocumentFilter))
				.setOperator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder isPurchasedParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_M_Product.COLUMNNAME_IsPurchased)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_M_Product.COLUMNNAME_IsPurchased))
				.setWidgetType(DocumentFieldWidgetType.YesNo)
				.setOperator(Operator.EQUAL);

		final DocumentFilterParamDescriptor.Builder isSoldParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_M_Product.COLUMNNAME_IsSold)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_M_Product.COLUMNNAME_IsSold))
				.setWidgetType(DocumentFieldWidgetType.YesNo)
				.setOperator(Operator.EQUAL);

		return DocumentFilterDescriptor.builder()
				.setFrequentUsed(true)
				.setFilterId(ProductFilterVO.FILTER_ID)
				.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_PRODUCT))
				.addParameter(productNameParameter)
				.addParameter(productValueParameter)
				.addParameter(productCategoryParameter)
				.addParameter(isPurchasedParameter)
				.addParameter(isSoldParameter)
				.build();
	}

	public ImmutableList<DocumentFilter> createAutoFilters()
	{
		final DocumentFilter dateFilter = DocumentFilter.builder()
				.setFilterId(DateFilterVO.FILTER_ID)
				.setCaption(Services.get(IMsgBL.class).translatable(DateFilterVO.PARAM_Date))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(DateFilterVO.PARAM_Date, Operator.EQUAL, Env.getDate(Env.getCtx())))
				.build();

		return ImmutableList.of(dateFilter);
	}

	public ImmutableList<DocumentFilter> extractDocumentFilters(@NonNull final CreateViewRequest request)
	{
		final DocumentFilterDescriptorsProvider provider = getFilterDescriptors();
		final List<DocumentFilter> filters = request.getOrUnwrapFilters(provider);
		return ImmutableList.copyOf(filters);
	}

	public IQuery<I_MD_Cockpit> createQuery(@NonNull final List<DocumentFilter> filters)
	{
		final IQueryBuilder<I_MD_Cockpit> queryBuilder = createInitialQueryBuilder();

		boolean anyRestrictionAdded = false;
		if (augmentQueryBuilder(queryBuilder, extractDateFilterVO(filters)))
		{
			anyRestrictionAdded = true;
		}
		if (augmentQueryBuilder(queryBuilder, extractProductFilterVO(filters)))
		{
			anyRestrictionAdded = true;
		}

		if (anyRestrictionAdded)
		{
			final IQuery<I_MD_Cockpit> query = augmentQueryBuilderWithOrderBy(queryBuilder).create();
			return query;
		}
		else
		{
			// avoid memory problems in case the filters are accidentally empty
			return queryBuilder.filter(ConstantQueryFilter.of(false)).create();
		}
	}

	private IQueryBuilder<I_MD_Cockpit> createInitialQueryBuilder()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Cockpit> queryBuilder = queryBL
				.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter();
		return queryBuilder;
	}

	private boolean augmentQueryBuilder(final IQueryBuilder<I_MD_Cockpit> queryBuilder, final DateFilterVO dateFilterVO)
	{
		if (dateFilterVO == null)
		{
			return false;
		}

		final Date date = dateFilterVO.getDate();
		if (date == null)
		{
			return false;
		}

		final Timestamp dayTimestamp = TimeUtil.getDay(date);
		queryBuilder.addCompareFilter(I_MD_Cockpit.COLUMN_DateGeneral, CompareQueryFilter.Operator.GREATER_OR_EQUAL, dayTimestamp);
		queryBuilder.addCompareFilter(I_MD_Cockpit.COLUMN_DateGeneral, CompareQueryFilter.Operator.LESS, TimeUtil.addDays(dayTimestamp, 1));

		return true;
	}

	private boolean augmentQueryBuilder(final IQueryBuilder<I_MD_Cockpit> queryBuilder, final ProductFilterVO productFilterVO)
	{
		if (productFilterVO == null)
		{
			return false;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_Product> productQuery = queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter();
		boolean anyRestrictionAdded = false;

		final String productName = productFilterVO.getProductName();
		if (!Check.isEmpty(productName, true))
		{
			final boolean ignoreCase = true;
			productQuery.addStringLikeFilter(I_M_Product.COLUMN_Name, productName, ignoreCase);
			anyRestrictionAdded = true;
		}

		final String productValue = productFilterVO.getProductValue();
		if (!Check.isEmpty(productValue, true))
		{
			final boolean ignoreCase = true;
			productQuery.addStringLikeFilter(I_M_Product.COLUMN_Value, productValue, ignoreCase);
			anyRestrictionAdded = true;
		}

		final int productCategoryId = productFilterVO.getProductCategoryId();
		if (productCategoryId > 0)
		{
			productQuery.addEqualsFilter(I_M_Product.COLUMN_M_Product_Category_ID, productCategoryId);
			anyRestrictionAdded = true;
		}

		final Boolean isPurchased = productFilterVO.getIsPurchased();
		if (isPurchased != null)
		{
			productQuery.addEqualsFilter(I_M_Product.COLUMN_IsPurchased, isPurchased);
			anyRestrictionAdded = true;
		}

		final Boolean isSold = productFilterVO.getIsSold();
		if (isSold != null)
		{
			productQuery.addEqualsFilter(I_M_Product.COLUMN_IsSold, isSold);
			anyRestrictionAdded = true;
		}

		//
		if (!anyRestrictionAdded)
		{
			return false;
		}
		queryBuilder.addInSubQueryFilter(I_MD_Cockpit.COLUMN_M_Product_ID, I_M_Product.COLUMN_M_Product_ID, productQuery.create());
		return true;
	}

	private IQueryBuilder<I_MD_Cockpit> augmentQueryBuilderWithOrderBy(@NonNull final IQueryBuilder<I_MD_Cockpit> queryBuilder)
	{
		return queryBuilder
				.orderBy()
				.addColumn(I_MD_Cockpit.COLUMN_DateGeneral)
				.addColumn(I_MD_Cockpit.COLUMN_M_Product_ID)
				.addColumn(I_MD_Cockpit.COLUMN_AttributesKey)
				.endOrderBy();
	}

	public boolean doesProductMatchFilters(
			@NonNull final I_M_Product product,
			@NonNull final List<DocumentFilter> filters)
	{
		final ProductFilterVO productFilterVO = extractProductFilterVO(filters);
		return doesProductMatchFilter(product, productFilterVO);
	}

	private boolean doesProductMatchFilter(
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

		return true;
	}

	private ProductFilterVO extractProductFilterVO(final Collection<DocumentFilter> filters)
	{
		return filters.stream()
				.filter(filter -> ProductFilterVO.FILTER_ID.equals(filter.getFilterId()))
				.map(this::extractProductFilterVO)
				.findFirst()
				.orElse(ProductFilterVO.EMPTY);
	}

	private ProductFilterVO extractProductFilterVO(final DocumentFilter filter)
	{
		Check.assume(ProductFilterVO.FILTER_ID.equals(filter.getFilterId()), "Filter ID is {} but it was {}", ProductFilterVO.FILTER_ID, filter);

		return ProductFilterVO.builder()
				.productName(filter.getParameterValueAsString(ProductFilterVO.PARAM_ProductName, null))
				.productValue(filter.getParameterValueAsString(ProductFilterVO.PARAM_ProductValue, null))
				.productCategoryId(filter.getParameterValueAsInt(ProductFilterVO.PARAM_M_Product_Category_ID, -1))
				.isPurchased(filter.getParameterValueAsBoolean(ProductFilterVO.PARAM_IsPurchased, null))
				.isSold(filter.getParameterValueAsBoolean(ProductFilterVO.PARAM_IsSold, null))
				.build();
	}

	public DateFilterVO extractDateFilterVO(final Collection<DocumentFilter> filters)
	{
		return filters.stream()
				.filter(filter -> DateFilterVO.FILTER_ID.equals(filter.getFilterId()))
				.map(this::extractDateFilterVO)
				.findFirst()
				.orElse(DateFilterVO.EMPTY);
	}

	private DateFilterVO extractDateFilterVO(final DocumentFilter filter)
	{
		Check.assume(DateFilterVO.FILTER_ID.equals(filter.getFilterId()), "Filter ID is {} but it was {}", DateFilterVO.FILTER_ID, filter);
		return DateFilterVO.builder()
				.date(filter.getParameterValueAsDate(DateFilterVO.PARAM_Date, null))
				.build();
	}

	@lombok.Value
	@lombok.Builder
	public static class DateFilterVO
	{
		public static final DateFilterVO EMPTY = DateFilterVO.builder().build();

		private static final String FILTER_ID = "materialCockpitDateOnlyFilter";

		public static final String PARAM_Date = I_MD_Cockpit.COLUMNNAME_DateGeneral;
		Date date;
	}

	@lombok.Value
	@lombok.Builder
	private static class ProductFilterVO
	{
		public static final ProductFilterVO EMPTY = builder().build();

		public static final String FILTER_ID = "materialCockpitAllParamsFilter";

		public static final String PARAM_ProductValue = I_MD_Cockpit.COLUMNNAME_ProductValue;
		String productValue;

		public static final String PARAM_ProductName = I_MD_Cockpit.COLUMNNAME_ProductName;
		String productName;

		public static final String PARAM_M_Product_Category_ID = I_M_Product.COLUMNNAME_M_Product_Category_ID;
		int productCategoryId;

		public static final String PARAM_IsPurchased = I_M_Product.COLUMNNAME_IsPurchased;
		Boolean isPurchased;

		public static final String PARAM_IsSold = I_M_Product.COLUMNNAME_IsSold;
		Boolean isSold;
	}
}
