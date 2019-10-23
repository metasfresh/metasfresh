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


import java.util.Collection;
import java.util.function.Predicate;

import org.adempiere.ad.dao.IQueryBuilder;

/**
 * A data model which is can be filtered by {@link IFacet}s.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacetFilterable<ModelType>
{
	/** Reset filterable to its initial state */
	void reset();

	/** Filter underlying data by given facets */
	void filter(Collection<IFacet<ModelType>> facets);

	/**
	 * Creates an {@link IQueryBuilder} which will retrieve the underlying records of this filterable.
	 * 
	 * @param onlyFacetCategoriesPredicate optional predicate which specifies which {@link IFacetCategory} filters to be considered when retrieving the data; If null, all current
	 *            {@link IFacetCategory} filters will be applied.
	 * @return filtered data
	 */
	IQueryBuilder<ModelType> createQueryBuilder(Predicate<IFacetCategory> onlyFacetCategoriesPredicate);
}
