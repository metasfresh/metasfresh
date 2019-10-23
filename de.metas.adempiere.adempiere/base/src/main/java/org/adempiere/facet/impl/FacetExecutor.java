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


import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.facet.FacetsPoolListenerAdapter;
import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCollector;
import org.adempiere.facet.IFacetFilterable;
import org.adempiere.facet.IFacetsPool;
import org.adempiere.facet.IFacetsPoolListener;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

/**
 * Facets executor: acts like a mediator between {@link IFacetsPool} and {@link IFacetFilterable}.
 *
 * This class is responsible of:
 * <ul>
 * <li>listen the {@link IFacetsPool}
 * <li>on {@link IFacetsPool} changes, filter the {@link IFacetFilterable}
 * </ul>
 *
 * @author tsa
 *
 * @param <ModelType>
 */
public class FacetExecutor<ModelType>
{
	private IFacetFilterable<ModelType> _facetFilterable;

	private final AtomicBoolean queryRunning = new AtomicBoolean(false);

	private IFacetsPool<ModelType> _facetsPool;
	private final IFacetsPoolListener facetsPoolListener = new FacetsPoolListenerAdapter()
	{
		@Override
		public void onFacetsInit()
		{
			// Reset filterable state because the facets were completelly changed
			getFacetFilterable().reset();
		};

		@Override
		public void onFacetExecute(IFacet<?> facet)
		{
			executeQuery(facet);
		};
	};

	private final CompositeFacetCollector<ModelType> facetCollectors = new CompositeFacetCollector<ModelType>();

	public FacetExecutor(final Class<ModelType> modelClass)
	{
		super();

		Check.assumeNotNull(modelClass, "modelClass not null");
		// this.modelClass = modelClass;
	}

	public FacetExecutor<ModelType> setFacetFilterable(final IFacetFilterable<ModelType> facetFilterable)
	{
		this._facetFilterable = facetFilterable;
		if (_facetFilterable != null)
		{
			_facetFilterable.reset();
		}
		return this;
	}

	private final IFacetFilterable<ModelType> getFacetFilterable()
	{
		Check.assumeNotNull(_facetFilterable, "_facetFilterable not null");
		return _facetFilterable;
	}

	/**
	 * Sets the facets pool.
	 * In this pool the executor will set or update the facets which it collects.
	 * 
	 * @param facetsPool
	 */
	public void setFacetsPool(final IFacetsPool<ModelType> facetsPool)
	{
		if (this._facetsPool == facetsPool)
		{
			return;
		}

		if (this._facetsPool != null)
		{
			this._facetsPool.removeListener(facetsPoolListener);
		}

		this._facetsPool = facetsPool;

		if (this._facetsPool != null)
		{
			this._facetsPool.addListener(facetsPoolListener);
		}
	}

	private final IFacetsPool<ModelType> getFacetsPool()
	{
		Check.assumeNotNull(_facetsPool, "_facetsPool not null");
		return _facetsPool;
	}

	/**
	 * Adds an {@link IFacetCollector} which will be used as a source of facets, each time when it's needed.
	 * 
	 * @param facetCollector
	 */
	public void addFacetCollector(final IFacetCollector<ModelType> facetCollector)
	{
		facetCollectors.addFacetCollector(facetCollector);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * @return true if there is a query that is currently running because user enabled/disabled some of the facets
	 */
	public final boolean isQueryRunning()
	{
		return queryRunning.get();
	}

	/**
	 * Filter underlying data model using the active filters,
	 * 
	 * i.e.
	 * <ul>
	 * <li>gets currently active facets from {@link IFacetsPool}
	 * <li>filter registered {@link IFacetFilterable} using those
	 * </ul>
	 * 
	 * @param triggeringFacet facet which triggered this query.
	 */
	private final void executeQuery(final IFacet<?> triggeringFacet)
	{
		final boolean alreadyRunning = queryRunning.getAndSet(true);
		if (alreadyRunning)
		{
			// do nothing if query is already running
			return;
		}

		try
		{
			//
			// Get current active facets and filter the data source
			final IFacetsPool<ModelType> facetsPool = getFacetsPool();
			final Set<IFacet<ModelType>> facets = facetsPool.getActiveFacets();
			final IFacetFilterable<ModelType> facetFilterable = getFacetFilterable();
			facetFilterable.filter(facets);

			//
			// After filtering the datasource, we need to update all facet categories which are eager to refresh.
			facetCollectors.collect()
					.setSource(facetFilterable)
					// Don't update facets from the same category as our triggering facets
					.excludeFacetCategory(triggeringFacet.getFacetCategory())
					// Update only those facet groups on which we also "eager refreshing"
					.setOnlyEagerRefreshCategories()
					.collectAndUpdate(facetsPool);
		}
		finally
		{
			queryRunning.set(false);
		}
	}

	/**
	 * Collect all facets from registered {@link IFacetCollector}s and then set them to {@link IFacetsPool}.
	 * 
	 * If this executor has already a query which is running ({@link #isQueryRunning()}), this method will do nothing.
	 */
	public void collectFacetsAndResetPool()
	{
		// Do nothing if we have query which is currently running,
		// because it most of the cases this happends because some business logic
		// was triggered when the underlying facet filterable was changed due to our current running query
		// and which triggered this method.
		// If we would not prevent this, we could run in endless recursive calls.
		if (isQueryRunning())
		{
			return;
		}

		//
		// Before collecting ALL facets from this data source, make sure the database source is reset to it's inital state.  
		final IFacetFilterable<ModelType> facetFilterable = getFacetFilterable();
		facetFilterable.reset();
		
		//
		// Collect the facets from facet filterable's current selection
		// and set them to facets pool
		facetCollectors.collect()
				.setSource(facetFilterable)
				.collectAndSet(getFacetsPool());
	}
}
