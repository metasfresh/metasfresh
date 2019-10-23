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
import java.util.Set;

/**
 * Result of an {@link IFacetCollector} execution.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacetCollectorResult<ModelType>
{
	/**
	 * Gets all facet categories.
	 * 
	 * NOTE:
	 * <ul>
	 * <li>collector can report more (or all categories) and NOT only the categories on which facets were found
	 * <li>we return a list because we want to preverve collector's desired order of sorting them
	 * </ul>
	 * 
	 * @return all facet categories
	 */
	List<IFacetCategory> getFacetCategories();

	/**
	 * @return currently collected facets
	 */
	Set<IFacet<ModelType>> getFacets();

	/**
	 * @return currently collected facets which are of given <code>facetCategory</code>
	 */
	Set<IFacet<ModelType>> getFacetsByCategory(final IFacetCategory facetCategory);
}
