package org.adempiere.facet;

import java.util.function.Predicate;

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


import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.facet.impl.AbstractFacetCollector;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;
import de.metas.util.collections.IncludeExcludeListPredicate;

public final class FacetCollectorRequestBuilder<ModelType>
{
	private final AbstractFacetCollector<ModelType> facetCollector;

	private IFacetFilterable<ModelType> source_filterable;
	private final IncludeExcludeListPredicate.Builder<IFacetCategory> facetCategoryIncludesExcludes = IncludeExcludeListPredicate.builder();
	private boolean onlyEagerRefreshCategories = false;

	public FacetCollectorRequestBuilder(final AbstractFacetCollector<ModelType> facetCollector)
	{
		super();
		this.facetCollector = facetCollector;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * Collect the facets and return them.
	 * 
	 * @return facets collecting result
	 */
	public IFacetCollectorResult<ModelType> collect()
	{
		final IFacetCollectorResult<ModelType> result = facetCollector.collect(this);
		return result;
	}

	/**
	 * Collect the facets, reset the give {@link IFacetsPool}.
	 * All the facets which were previously set to given pool will be discarded and only the newly collected ones will be present.
	 * 
	 * @param facetsPool
	 * @see #collectAndUpdate(IFacetsPool)
	 */
	public void collectAndSet(final IFacetsPool<ModelType> facetsPool)
	{
		final IFacetCollectorResult<ModelType> result = collect();
		facetsPool.setFacetCategories(result.getFacetCategories());
		facetsPool.setFacets(result.getFacets());
	}

	/**
	 * Collect the facets, and update the given {@link IFacetsPool}.
	 * Compared with {@link #collectAndSet(IFacetsPool)} which is fully reseting the pool, this method will reset only the collected facet categories.
	 * 
	 * @param facetsPool
	 * @see #collectAndSet(IFacetsPool)
	 */
	public void collectAndUpdate(final IFacetsPool<ModelType> facetsPool)
	{
		final IFacetCollectorResult<ModelType> result = collect();
		facetsPool.updateFrom(result);
	}

	/**
	 * Sets the source from where the facets will be collected.
	 * 
	 * @param dataSource
	 */
	public FacetCollectorRequestBuilder<ModelType> setSource(final IFacetFilterable<ModelType> dataSource)
	{
		this.source_filterable = dataSource;
		return this;
	}

	/**
	 * @param onlyFacetCategoriesPredicate
	 * @return facets collecting source to be used when collecting facets.
	 */
	public IQueryBuilder<ModelType> getSourceToUse(final Predicate<IFacetCategory> onlyFacetCategoriesPredicate)
	{
		Check.assumeNotNull(source_filterable, "source_filterable not null");
		final IQueryBuilder<ModelType> queryBuilder = source_filterable.createQueryBuilder(onlyFacetCategoriesPredicate);
		return queryBuilder;
	}

	/**
	 * Advice to collect facets only from given included facet categories. All other facet categories will be excluded.
	 * 
	 * @param facetCategory
	 */
	public FacetCollectorRequestBuilder<ModelType> includeFacetCategory(final IFacetCategory facetCategory)
	{
		facetCategoryIncludesExcludes.include(facetCategory);
		return this;
	}

	/**
	 * Advice to NOT collect facets from given exclude facet categories, even if they were added to include list.
	 * 
	 * @param facetCategory
	 */
	public FacetCollectorRequestBuilder<ModelType> excludeFacetCategory(final IFacetCategory facetCategory)
	{
		facetCategoryIncludesExcludes.exclude(facetCategory);
		return this;
	}

	/**
	 * 
	 * @param facetCategory
	 * @return true if given facet category shall be considered when collecting facets
	 */
	public boolean acceptFacetCategory(final IFacetCategory facetCategory)
	{
		// Don't accept it if is excluded by includes/excludes list
		if (!facetCategoryIncludesExcludes.build().test(facetCategory))
		{
			return false;
		}

		// Only categories which have "eager refresh" set (if asked)
		if (onlyEagerRefreshCategories && !facetCategory.isEagerRefresh())
		{
			return false;
		}

		// accept the facet category
		return true;
	}

	/**
	 * Collect facets only for categories which have {@link IFacetCategory#isEagerRefresh()}.
	 */
	public FacetCollectorRequestBuilder<ModelType> setOnlyEagerRefreshCategories()
	{
		this.onlyEagerRefreshCategories = true;
		return this;
	}
}
