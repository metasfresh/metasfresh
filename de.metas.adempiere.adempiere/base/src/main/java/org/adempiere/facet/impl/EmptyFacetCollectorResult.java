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


import java.util.List;
import java.util.Set;

import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.IFacetCollectorResult;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Empty immutable {@link IFacetCollectorResult}.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
class EmptyFacetCollectorResult<ModelType> implements IFacetCollectorResult<ModelType>
{
	public static final transient EmptyFacetCollectorResult<Object> instance = new EmptyFacetCollectorResult<>();

	public static final <ModelType> EmptyFacetCollectorResult<ModelType> getInstance()
	{
		@SuppressWarnings("unchecked")
		final EmptyFacetCollectorResult<ModelType> instanceCasted = (EmptyFacetCollectorResult<ModelType>)instance;
		return instanceCasted;
	}

	@Override
	public List<IFacetCategory> getFacetCategories()
	{
		return ImmutableList.of();
	}

	@Override
	public Set<IFacet<ModelType>> getFacets()
	{
		return ImmutableSet.of();
	}

	@Override
	public Set<IFacet<ModelType>> getFacetsByCategory(IFacetCategory facetCategory)
	{
		return ImmutableSet.of();
	}

}
