package de.metas.ui.web.material.cockpit;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.fresh.model.I_X_MRP_ProductInfo_V;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor.Builder;
import de.metas.ui.web.document.filter.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
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
	private static final String MSG_FILTER = "Filter";
	private static final String MATERIAL_COCKPIT_ALL_PARAMS_FILTER = "materialCockpitAllParamsFilter";

	private static final String MSG_DATE = "Date";
	private static final String MATERIAL_COCKPIT_DATE_ONLY_FILTER = "materialCockpitDateOnlyFilter";

	private final ImmutableList<DocumentFilterDescriptor> filterDescriptors;

	public MaterialCockpitFilters()
	{
		filterDescriptors = createFilterDescriptors();
	}

	private ImmutableList<DocumentFilterDescriptor> createFilterDescriptors()
	{
		final de.metas.ui.web.document.filter.DocumentFilterDescriptor dateOnlyfilter = createDateOnlyFilter();
		final de.metas.ui.web.document.filter.DocumentFilterDescriptor nonParamsFilter = createAllParamsFilter();

		final ImmutableList<DocumentFilterDescriptor> filterDescriptors = ImmutableList.of(
				dateOnlyfilter,
				nonParamsFilter);
		return filterDescriptors;
	}

	private de.metas.ui.web.document.filter.DocumentFilterDescriptor createDateOnlyFilter()
	{
		final Builder standaloneParamDescriptor = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_X_MRP_ProductInfo_V.COLUMNNAME_DateGeneral)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_X_MRP_ProductInfo_Detail_MV.COLUMNNAME_DateGeneral))
				.setWidgetType(DocumentFieldWidgetType.Date)
				.setOperator(Operator.EQUAL)
				.setMandatory(true)
				.setShowIncrementDecrementButtons(true);

		final de.metas.ui.web.document.filter.DocumentFilterDescriptor dateOnlyfilterDescriptor = DocumentFilterDescriptor.builder()
				.setFrequentUsed(true)
				.setFilterId(MATERIAL_COCKPIT_DATE_ONLY_FILTER)
				.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_DATE))
				.addParameter(standaloneParamDescriptor)
				.build();
		return dateOnlyfilterDescriptor;
	}

	private de.metas.ui.web.document.filter.DocumentFilterDescriptor createAllParamsFilter()
	{
		final de.metas.ui.web.document.filter.DocumentFilterParamDescriptor.Builder productNameParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_X_MRP_ProductInfo_V.COLUMNNAME_ProductName)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_X_MRP_ProductInfo_V.COLUMNNAME_ProductName))
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setOperator(Operator.LIKE_I);

		final de.metas.ui.web.document.filter.DocumentFilterParamDescriptor.Builder productValueParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_X_MRP_ProductInfo_V.COLUMNNAME_Value)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_X_MRP_ProductInfo_V.COLUMNNAME_Value))
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setOperator(Operator.LIKE_I);

		final de.metas.ui.web.document.filter.DocumentFilterDescriptor filterDescriptor = DocumentFilterDescriptor.builder()
				.setFrequentUsed(true)
				.setFilterId(MATERIAL_COCKPIT_ALL_PARAMS_FILTER)
				.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_FILTER))
				.addParameter(productNameParameter)
				.addParameter(productValueParameter)
				.build();
		return filterDescriptor;
	}

	public List<DocumentFilterDescriptor> getFilterDescriptors()
	{
		return filterDescriptors;
	}

	public ImmutableList<DocumentFilter> createAutoFilters()
	{
		final String columnName = I_X_MRP_ProductInfo_V.COLUMNNAME_DateGeneral;
		final ITranslatableString filterCaption = Services.get(IMsgBL.class).translatable(columnName);
		final DocumentFilter dateFilter = DocumentFilter.builder()
				.setFilterId(MATERIAL_COCKPIT_DATE_ONLY_FILTER)
				.setCaption(filterCaption)
				.addParameter(DocumentFilterParam.ofNameOperatorValue(columnName, Operator.EQUAL, Env.getDate(Env.getCtx())))
				.build();

		return ImmutableList.of(dateFilter);
	}

	public ImmutableList<DocumentFilter> extractDocumentFilters(@NonNull final CreateViewRequest request)
	{
		final ImmutableDocumentFilterDescriptorsProvider provider = ImmutableDocumentFilterDescriptorsProvider.of(getFilterDescriptors());
		final List<DocumentFilter> filters = request.getOrUnwrapFilters(provider);
		return ImmutableList.copyOf(filters);
	}

	public IQuery<I_X_MRP_ProductInfo_Detail_MV> createQuery(@NonNull final List<DocumentFilter> filters)
	{
		final IQueryBuilder<I_X_MRP_ProductInfo_Detail_MV> queryBuilder = createInitialQueryBuilder();

		for (final DocumentFilter filter : filters)
		{
			final List<DocumentFilterParam> filterParameters = filter.getParameters();
			for (final DocumentFilterParam filterParameter : filterParameters)
			{
				augmentQueryBuilderWithFilterParam(queryBuilder, filterParameter);
			}
		}

		final IQuery<I_X_MRP_ProductInfo_Detail_MV> query = augmentqueryBuildWithOrderBy(queryBuilder).create();
		return query;
	}

	private IQueryBuilder<I_X_MRP_ProductInfo_Detail_MV> createInitialQueryBuilder()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_X_MRP_ProductInfo_Detail_MV> queryBuilder = queryBL
				.createQueryBuilder(I_X_MRP_ProductInfo_Detail_MV.class)
				.addOnlyActiveRecordsFilter();
		return queryBuilder;
	}

	private void augmentQueryBuilderWithFilterParam(
			@NonNull final IQueryBuilder<I_X_MRP_ProductInfo_Detail_MV> queryBuilder,
			@NonNull final DocumentFilterParam filterParameter)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final String fieldName = filterParameter.getFieldName();
		final Object value = filterParameter.getValue();
		if (I_X_MRP_ProductInfo_V.COLUMNNAME_DateGeneral.equals(fieldName))
		{
			final Date date = JSONDate.fromObject(value, DocumentFieldWidgetType.Date);
			final Timestamp dayTimestamp = TimeUtil.getDay(date);
			queryBuilder.addBetweenFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMNNAME_DateGeneral, dayTimestamp, TimeUtil.addDays(dayTimestamp, 1));
		}
		else if (I_X_MRP_ProductInfo_V.COLUMNNAME_ProductName.equals(fieldName))
		{
			final String name = (String)value;
			final IQuery<I_M_Product> productByNameQuery = queryBL.createQueryBuilder(I_M_Product.class)
					.addOnlyActiveRecordsFilter()
					.addStringLikeFilter(I_M_Product.COLUMN_Name, name, true)
					.create();

			queryBuilder.addInSubQueryFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMN_M_Product_ID, I_M_Product.COLUMN_M_Product_ID, productByNameQuery);
		}
		else if (I_X_MRP_ProductInfo_V.COLUMNNAME_Value.equals(fieldName))
		{
			final String productValue = (String)value;
			final IQuery<I_M_Product> productByValueQuery = queryBL.createQueryBuilder(I_M_Product.class)
					.addOnlyActiveRecordsFilter()
					.addStringLikeFilter(I_M_Product.COLUMN_Value, productValue, true)
					.create();

			queryBuilder.addInSubQueryFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMN_M_Product_ID, I_M_Product.COLUMN_M_Product_ID, productByValueQuery);
		}
	}

	private IQueryBuilder<I_X_MRP_ProductInfo_Detail_MV> augmentqueryBuildWithOrderBy(
			@NonNull final IQueryBuilder<I_X_MRP_ProductInfo_Detail_MV> queryBuilder)
	{
		return queryBuilder
				.orderBy()
				.addColumn(I_X_MRP_ProductInfo_Detail_MV.COLUMN_DateGeneral)
				.addColumn(I_X_MRP_ProductInfo_Detail_MV.COLUMN_M_Product_ID)
				.addColumn(I_X_MRP_ProductInfo_Detail_MV.COLUMN_ASIKey)
				.endOrderBy();
	}
}
