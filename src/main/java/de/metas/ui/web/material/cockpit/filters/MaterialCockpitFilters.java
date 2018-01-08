package de.metas.ui.web.material.cockpit.filters;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.material.cockpit.model.I_MD_Cockpit;
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
	private static final String MSG_PRODUCT = "Product";
	private static final String MATERIAL_COCKPIT_ALL_PARAMS_FILTER = "materialCockpitAllParamsFilter";

	private static final String MSG_DATE = "Date";
	private static final String MATERIAL_COCKPIT_DATE_ONLY_FILTER = "materialCockpitDateOnlyFilter";

	private ImmutableList<DocumentFilterDescriptor> filterDescriptors;

	public List<DocumentFilterDescriptor> getFilterDescriptors()
	{
		if (filterDescriptors == null)
		{
			filterDescriptors = createFilterDescriptors();
		}
		return filterDescriptors;
	}

	private ImmutableList<DocumentFilterDescriptor> createFilterDescriptors()
	{
		final DocumentFilterDescriptor dateOnlyfilter = createDateOnlyFilter();
		final DocumentFilterDescriptor nonParamsFilter = createAllParamsFilter();

		final ImmutableList<DocumentFilterDescriptor> filterDescriptors = ImmutableList.of(
				dateOnlyfilter,
				nonParamsFilter);
		return filterDescriptors;
	}

	private DocumentFilterDescriptor createDateOnlyFilter()
	{
		final Builder standaloneParamDescriptor = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_MD_Cockpit.COLUMNNAME_DateGeneral)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_MD_Cockpit.COLUMNNAME_DateGeneral))
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

	private DocumentFilterDescriptor createAllParamsFilter()
	{
		final Builder productNameParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_MD_Cockpit.COLUMNNAME_ProductName)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_MD_Cockpit.COLUMNNAME_ProductName))
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setOperator(Operator.LIKE_I);

		final Builder productValueParameter = DocumentFilterParamDescriptor.builder()
				.setFieldName(I_MD_Cockpit.COLUMNNAME_ProductValue)
				.setDisplayName(Services.get(IMsgBL.class).translatable(I_MD_Cockpit.COLUMNNAME_ProductValue))
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setOperator(Operator.LIKE_I);

		final DocumentFilterDescriptor filterDescriptor = DocumentFilterDescriptor.builder()
				.setFrequentUsed(true)
				.setFilterId(MATERIAL_COCKPIT_ALL_PARAMS_FILTER)
				.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_PRODUCT))
				.addParameter(productNameParameter)
				.addParameter(productValueParameter)
				.build();
		return filterDescriptor;
	}

	public ImmutableList<DocumentFilter> createAutoFilters()
	{
		final String columnName = I_MD_Cockpit.COLUMNNAME_DateGeneral;
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
		final ImmutableDocumentFilterDescriptorsProvider provider = ImmutableDocumentFilterDescriptorsProvider
				.of(getFilterDescriptors());
		final List<DocumentFilter> filters = request.getOrUnwrapFilters(provider);
		return ImmutableList.copyOf(filters);
	}

	public IQuery<I_MD_Cockpit> createQuery(@NonNull final List<DocumentFilter> filters)
	{
		final IQueryBuilder<I_MD_Cockpit> queryBuilder = createInitialQueryBuilder();

		boolean anyRestrictionAdded = false;
		for (final DocumentFilter filter : filters)
		{
			final List<DocumentFilterParam> filterParameters = filter.getParameters();
			for (final DocumentFilterParam filterParameter : filterParameters)
			{
				augmentQueryBuilderWithFilterParam(queryBuilder, filterParameter);
				anyRestrictionAdded = true;
			}
		}
		if (anyRestrictionAdded)
		{
			final IQuery<I_MD_Cockpit> query = augmentqueryBuildWithOrderBy(queryBuilder).create();
			return query;
		}

		// avoid memory problems in case the filters are accidentally empty
		return queryBuilder.filter(ConstantQueryFilter.of(false)).create();
	}

	private IQueryBuilder<I_MD_Cockpit> createInitialQueryBuilder()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Cockpit> queryBuilder = queryBL
				.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter();
		return queryBuilder;
	}

	private void augmentQueryBuilderWithFilterParam(
			@NonNull final IQueryBuilder<I_MD_Cockpit> queryBuilder,
			@NonNull final DocumentFilterParam filterParameter)
	{
		final String fieldName = filterParameter.getFieldName();
		final Object value = filterParameter.getValue();
		if (I_MD_Cockpit.COLUMNNAME_DateGeneral.equals(fieldName))
		{
			addDateToQueryBuilder(value, queryBuilder);
		}
		else if (I_MD_Cockpit.COLUMNNAME_ProductName.equals(fieldName))
		{
			final String productName = (String)value;
			addLikeFilterToQueryBuilder(I_M_Product.COLUMN_Name, productName, queryBuilder);
		}
		else if (I_MD_Cockpit.COLUMNNAME_ProductValue.equals(fieldName))
		{
			final String productValue = (String)value;
			addLikeFilterToQueryBuilder(I_M_Product.COLUMN_Value, productValue, queryBuilder);
		}
	}

	private void addDateToQueryBuilder(
			@NonNull final Object value,
			@NonNull final IQueryBuilder<I_MD_Cockpit> queryBuilder)
	{
		final Date date = JSONDate.fromObject(value, DocumentFieldWidgetType.Date);

		final Timestamp dayTimestamp = TimeUtil.getDay(date);
		queryBuilder.addCompareFilter(I_MD_Cockpit.COLUMN_DateGeneral, org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.GREATER_OR_EQUAL, dayTimestamp);
		queryBuilder.addCompareFilter(I_MD_Cockpit.COLUMN_DateGeneral, org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.LESS, TimeUtil.addDays(dayTimestamp, 1));
	}

	private void addLikeFilterToQueryBuilder(
			@NonNull final ModelColumn<I_M_Product, Object> column,
			@NonNull final String value,
			@NonNull final IQueryBuilder<I_MD_Cockpit> queryBuilder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Product> productByValueQuery = queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addStringLikeFilter(column, value, true)
				.create();

		queryBuilder.addInSubQueryFilter(I_MD_Cockpit.COLUMN_M_Product_ID, I_M_Product.COLUMN_M_Product_ID, productByValueQuery);
	}

	private IQueryBuilder<I_MD_Cockpit> augmentqueryBuildWithOrderBy(
			@NonNull final IQueryBuilder<I_MD_Cockpit> queryBuilder)
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
		for (final DocumentFilter filter : filters)
		{
			if (!doesProductMatchFilter(product, filter))
			{
				return false;
			}
		}
		return true;
	}

	private boolean doesProductMatchFilter(
			@NonNull final I_M_Product product,
			@NonNull final DocumentFilter filter)
	{
		if (MATERIAL_COCKPIT_DATE_ONLY_FILTER.equals(filter.getFilterId()))
		{
			return true;
		}

		final List<DocumentFilterParam> filterParameters = filter.getParameters();
		for (final DocumentFilterParam filterParameter : filterParameters)
		{
			if (!doesProductMatchFilterParam(product, filterParameter))
			{
				return false;
			}
		}
		return true;
	}

	private boolean doesProductMatchFilterParam(
			@NonNull final I_M_Product product,
			@NonNull final DocumentFilterParam filterParameter)
	{
		if (I_MD_Cockpit.COLUMNNAME_ProductName.equals(filterParameter.getFieldName()))
		{
			return doesProductNameMatchFilterParam(product, filterParameter);
		}
		else if (I_MD_Cockpit.COLUMNNAME_ProductValue.equals(filterParameter.getFieldName()))
		{
			return doesProductValueMatchFilterParam(product, filterParameter);
		}
		return true;
	}

	private boolean doesProductNameMatchFilterParam(
			@NonNull final I_M_Product product,
			@NonNull final DocumentFilterParam filterParameter)
	{
		final boolean ignoreCase = true;
		final StringLikeFilter<I_M_Product> likeFilter = new StringLikeFilter<>(I_M_Product.COLUMNNAME_Name, filterParameter.getValueAsString(), ignoreCase);

		return likeFilter.accept(product);
	}

	private boolean doesProductValueMatchFilterParam(
			@NonNull final I_M_Product product,
			@NonNull final DocumentFilterParam filterParameter)
	{
		final boolean ignoreCase = true;
		final StringLikeFilter<I_M_Product> likeFilter = new StringLikeFilter<>(I_M_Product.COLUMNNAME_Value, filterParameter.getValueAsString(), ignoreCase);

		return likeFilter.accept(product);
	}

	public Timestamp extractDateOrNull(@NonNull final List<DocumentFilter> filters)
	{
		for (final DocumentFilter filter : filters)
		{
			final Timestamp dateOrNull = extractDateOrNullFromFilter(filter);
			if (dateOrNull != null)
			{
				return dateOrNull;
			}
		}
		return null;
	}

	public Timestamp extractDateOrNullFromFilter(@NonNull final DocumentFilter filter)
	{
		if (!MATERIAL_COCKPIT_DATE_ONLY_FILTER.equals(filter.getFilterId()))
		{
			return null;
		}

		final List<DocumentFilterParam> dateFilterParameters = filter.getParameters();
		for (final DocumentFilterParam dateFilterParameter : dateFilterParameters)
		{
			final Timestamp dateOrNull = extractDateOrNullFromParam(dateFilterParameter);
			if (dateOrNull != null)
			{
				return dateOrNull;
			}
		}
		return null;
	}

	public Timestamp extractDateOrNullFromParam(@NonNull final DocumentFilterParam dateFilterParameter)
	{
		if (!I_MD_Cockpit.COLUMNNAME_DateGeneral.equals(dateFilterParameter.getFieldName()))
		{
			return null;
		}
		final Object value = dateFilterParameter.getValue();
		final Date date = JSONDate.fromObject(value, DocumentFieldWidgetType.Date);
		return new Timestamp(date.getTime());
	}
}
