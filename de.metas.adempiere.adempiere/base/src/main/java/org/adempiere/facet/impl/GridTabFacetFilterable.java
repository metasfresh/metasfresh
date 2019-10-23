package org.adempiere.facet.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderDAO;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.IFacetFilterable;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.GridTab;
import org.compiere.model.MQuery;

import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Wraps a {@link GridTab} and make it behave like an {@link IFacetFilterable}.
 *
 * @author tsa
 *
 * @param <ModelType>
 */
public class GridTabFacetFilterable<ModelType> implements IFacetFilterable<ModelType>
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IQueryBuilderDAO queryBuilderDAO = Services.get(IQueryBuilderDAO.class);

	private final Class<ModelType> modelClass;
	private final GridTab gridTab;

	/**
	 * {@link GridTab}'s seed filter.
	 * This is the filter which will be applied all the time, and on top of it, the facets filters will be applied.
	 */
	private MQuery gridTabBaseQuery;
	private IQueryFilter<ModelType> gridTabBaseQueryFilter = ConstantQueryFilter.of(false);

	/**
	 * Current facet categories filters that were applied to underlying GridTab
	 */
	private Map<IFacetCategory, IQueryFilter<ModelType>> facetCategoryFilters = Collections.emptyMap();

	public GridTabFacetFilterable(final Class<ModelType> modelClass, final GridTab gridTab)
	{
		super();

		Check.assumeNotNull(modelClass, "modelClass not null");
		this.modelClass = modelClass;

		Check.assumeNotNull(gridTab, "gridTab not null");
		Check.assume(InterfaceWrapperHelper.getTableName(modelClass).equals(gridTab.getTableName()),
				"{} and {} shall have the same TableName", gridTab, modelClass);

		this.gridTab = gridTab;

		reset();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public final GridTab getGridTab()
	{
		return gridTab;
	}

	private final Properties getCtx()
	{
		return gridTab.getCtx();
	}

	@Override
	public final void reset()
	{
		// Initialize the seed query/filter which will be applied all the time.
		this.gridTabBaseQuery = gridTab.getQuery().deepCopy();
		this.gridTabBaseQueryFilter = gridTab.createCurrentRecordsQueryFilter(modelClass);

		// Reset current facet category filters,
		// because in inital state, this facet filterable is not filtered at all by any facet.
		this.facetCategoryFilters = Collections.emptyMap();
	}

	/**
	 * @return base/seed query which needs to be applied all the time
	 */
	private final MQuery getBaseQuery()
	{
		return gridTabBaseQuery;
	}

	/** Executes given query on underlying {@link GridTab} */
	@Override
	public final void filter(final Collection<IFacet<ModelType>> facets)
	{
		//
		// Initialize current filters
		this.facetCategoryFilters = createFacetCategoryQueryFilters(facets);

		//
		// Create the GridTab query
		// starting from our seed/base query
		final MQuery query = getBaseQuery().deepCopy();
		query.setUserQuery(true); // flag it as a user query, because mainly, this method is triggered when user is selecting/deselecting some facets.

		//
		// Add facet category filters
		// NOTE: we use sqlParamsOut=null because we are not supporting sql parameters
		// Only if any of the underlying facet filters will have some sql parameters, an exception could be thrown.
		final ICompositeQueryFilter<ModelType> filters = createFacetCategoriesFilter(null); // onlyFacetCategoriesPredicate=null => all facet categories
		final List<Object> sqlParamsOut = null;
		final String filtersWhereClause = queryBuilderDAO.getSql(getCtx(), filters, sqlParamsOut);
		if (!Check.isEmpty(filtersWhereClause, true))
		{
			query.addRestriction(filtersWhereClause);
		}

		//
		// Execute the query on GridTab
		gridTab.setQuery(query);
		final boolean onlyCurrentRows = false;
		gridTab.query(onlyCurrentRows);
	}

	private final Map<IFacetCategory, IQueryFilter<ModelType>> createFacetCategoryQueryFilters(final Collection<IFacet<ModelType>> facets)
	{
		final Map<IFacetCategory, IQueryFilter<ModelType>> facetCategory2filters = new LinkedHashMap<>();

		for (final IFacet<ModelType> facet : facets)
		{
			final IFacetCategory facetCategory = facet.getFacetCategory();

			//
			// Get create one composite filter per facet category
			// (because we join facet filters by "OR" but between categories we do "AND")
			ICompositeQueryFilter<ModelType> facetCategoryFilter = (ICompositeQueryFilter<ModelType>)facetCategory2filters.get(facetCategory);
			if (facetCategoryFilter == null)
			{
				facetCategoryFilter = queryBL.createCompositeQueryFilter(modelClass)
						// Facet filters of same facet category are joined by "OR"
						.setJoinOr()
						// If there is no facet filters in this facet category, we are accepting everything
						.setDefaultAccept(true);
				facetCategory2filters.put(facetCategory, facetCategoryFilter);
			}

			//
			// Add facet filter to facet category filters
			final IQueryFilter<ModelType> filter = facet.getFilter();
			if (filter != null)
			{
				facetCategoryFilter.addFilter(filter);
			}
		}

		// TODO: make the facet category's composite query filters immutable, just to prevent somebody from changing them
		// because those are part of internal state of this object.

		return facetCategory2filters;
	}

	@Override
	public IQueryBuilder<ModelType> createQueryBuilder(final Predicate<IFacetCategory> onlyFacetCategoriesPredicate)
	{
		final IQueryBuilder<ModelType> queryBuilder = queryBL.createQueryBuilder(modelClass, getCtx(), ITrx.TRXNAME_None);

		//
		queryBuilder.filter(gridTabBaseQueryFilter);

		final IQueryFilter<ModelType> facetCategoriesFilter = createFacetCategoriesFilter(onlyFacetCategoriesPredicate);
		queryBuilder.filter(facetCategoriesFilter);

		return queryBuilder;
	}

	private final ICompositeQueryFilter<ModelType> createFacetCategoriesFilter(final Predicate<IFacetCategory> onlyFacetCategoriesPredicate)
	{
		final ICompositeQueryFilter<ModelType> facetCategoriesFilter = queryBL.createCompositeQueryFilter(modelClass)
				// We are joining facet category filters by "AND"
				.setJoinAnd()
				// If there is no facet category filters, we accept everything
				.setDefaultAccept(true);

		for (final Map.Entry<IFacetCategory, IQueryFilter<ModelType>> facetCategory2filter : facetCategoryFilters.entrySet())
		{
			final IFacetCategory facetCategory = facetCategory2filter.getKey();
			if (onlyFacetCategoriesPredicate != null && !onlyFacetCategoriesPredicate.test(facetCategory))
			{
				continue;
			}

			final IQueryFilter<ModelType> filter = facetCategory2filter.getValue();
			facetCategoriesFilter.addFilter(filter);
		}

		return facetCategoriesFilter;
	}
}
