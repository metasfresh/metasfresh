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


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.facet.FacetCollectorRequestBuilder;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.IFacetCollector;
import org.adempiere.facet.IFacetCollectorResult;

import com.google.common.collect.ImmutableList;

public class CompositeFacetCollector<ModelType> extends AbstractFacetCollector<ModelType>
{
	private static final transient Logger logger = LogManager.getLogger(CompositeFacetCollector.class);

	private final Set<IFacetCollector<ModelType>> facetCollectors = new LinkedHashSet<>();

	public void addFacetCollector(final IFacetCollector<ModelType> facetCollector)
	{
		Check.assumeNotNull(facetCollector, "facetCollector not null");
		facetCollectors.add(facetCollector);
	}

	/** @return true if this composite has at least one collector */
	public boolean hasCollectors()
	{
		return !facetCollectors.isEmpty();
	}

	@Override
	public IFacetCollectorResult<ModelType> collect(final FacetCollectorRequestBuilder<ModelType> request)
	{
		final FacetCollectorResult.Builder<ModelType> aggregatedResult = FacetCollectorResult.builder();

		for (final IFacetCollector<ModelType> facetCollector : facetCollectors)
		{
			try
			{
				final IFacetCollectorResult<ModelType> result = facetCollector.collect(request);
				aggregatedResult.addFacetCollectorResult(result);
			}
			catch (Exception e)
			{
				final AdempiereException ex = new AdempiereException("Failed to collect facets from collector"
						+ "\n Collector: " + facetCollector
						+ "\n Request: " + request
						, e);
				logger.warn("Skip collector because it failed", ex);
			}
		}
		return aggregatedResult.build();
	}

	@Override
	public List<IFacetCategory> getAllFacetCategories()
	{
		// NOTE: teoretically we could cache this list, but i am thinking to Composite in Composite case, on which, caching approach will fail.

		final ImmutableList.Builder<IFacetCategory> aggregatedFacetCategories = ImmutableList.builder();
		for (final IFacetCollector<ModelType> facetCollector : facetCollectors)
		{
			final List<IFacetCategory> facetCategories = facetCollector.getAllFacetCategories();
			facetCategories.addAll(facetCategories);
		}
		return aggregatedFacetCategories.build();
	}
}
