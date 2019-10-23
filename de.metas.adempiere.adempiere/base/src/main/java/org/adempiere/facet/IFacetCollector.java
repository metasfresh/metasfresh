package org.adempiere.facet;

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


import java.util.List;

/**
 * Implementations of this interface are responsible of collecting {@link IFacet}s from different sources.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacetCollector<ModelType>
{
	/**
	 * Returns a new request builder which will assist you to collect the facets.
	 * 
	 * @return facets collecting request builder
	 */
	FacetCollectorRequestBuilder<ModelType> collect();

	/**
	 * Execute the request and collect the result.
	 * 
	 * NOTE: don't call it directly. It's called only by API.
	 * 
	 * @param request
	 * @return facet collect result
	 */
	IFacetCollectorResult<ModelType> collect(FacetCollectorRequestBuilder<ModelType> request);

	/**
	 * Gets all facet categories of which this collector is aware.
	 * 
	 * @return all categories (using collector's desired order)
	 */
	List<IFacetCategory> getAllFacetCategories();
}
