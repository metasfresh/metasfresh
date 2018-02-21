package de.metas.ui.web.material.cockpit.filters;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.CreateViewRequest;
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
		return ImmutableDocumentFilterDescriptorsProvider.of(
				DateFilterUtil.createFilterDescriptor(),
				ProductFilterUtil.createFilterDescriptor());
	}

	public ImmutableList<DocumentFilter> createAutoFilters()
	{
		return ImmutableList.of(DateFilterUtil.createFilterToday());
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
		if (augmentQueryBuilder(queryBuilder, DateFilterUtil.extractDateFilterVO(filters)))
		{
			anyRestrictionAdded = true;
		}
		if (augmentQueryBuilder(queryBuilder, ProductFilterUtil.extractProductFilterVO(filters)))
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
		final IQuery<I_M_Product> productQuery = ProductFilterUtil.createProductQueryOrNull(productFilterVO);
		if (productQuery == null)
		{
			return false;
		}

		queryBuilder.addInSubQueryFilter(I_MD_Cockpit.COLUMN_M_Product_ID, I_M_Product.COLUMN_M_Product_ID, productQuery);
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

	public Date getFilterByDate(@NonNull final List<DocumentFilter> filters)
	{
		final DateFilterVO dateFilterVO = DateFilterUtil.extractDateFilterVO(filters);
		return dateFilterVO.getDate();
	}

	public Predicate<I_M_Product> toProductFilterPredicate(@NonNull final List<DocumentFilter> filters)
	{
		return ProductFilterUtil.toPredicate(filters);
	}

}
