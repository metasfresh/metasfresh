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

import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.IFacetCollectorResult;
import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

/**
 * Immutable {@link IFacetCollectorResult}.
 * 
 * @author tsa
 *
 */
public class FacetCollectorResult<ModelType> implements IFacetCollectorResult<ModelType>
{
	public static final <ModelType> Builder<ModelType> builder()
	{
		return new Builder<>();
	}

	private final List<IFacetCategory> facetCategories;
	private final Set<IFacet<ModelType>> facets;

	private FacetCollectorResult(final Builder<ModelType> builder)
	{
		super();

		this.facetCategories = ImmutableList.copyOf(builder.facetCategories);
		this.facets = ImmutableSet.copyOf(builder.facets);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public List<IFacetCategory> getFacetCategories()
	{
		return facetCategories;
	}

	@Override
	public Set<IFacet<ModelType>> getFacets()
	{
		return facets;
	}

	@Override
	public Set<IFacet<ModelType>> getFacetsByCategory(IFacetCategory facetCategory)
	{
		Check.assumeNotNull(facetCategory, "facetCategory not null");
		// TODO optimize
		final ImmutableSet.Builder<IFacet<ModelType>> facetsForCategory = ImmutableSet.builder();
		for (final IFacet<ModelType> facet : facets)
		{
			if (!facetCategory.equals(facet.getFacetCategory()))
			{
				continue;
			}

			facetsForCategory.add(facet);
		}

		return facetsForCategory.build();
	}

	public static class Builder<ModelType>
	{
		private final Set<IFacetCategory> facetCategories = new LinkedHashSet<>();
		private final LinkedHashSet<IFacet<ModelType>> facets = new LinkedHashSet<>();

		private Builder()
		{
			super();
		}

		public IFacetCollectorResult<ModelType> build()
		{
			// If there was nothing added to this builder, return the empty instance (optimization)
			if (isEmpty())
			{
				return EmptyFacetCollectorResult.getInstance();
			}

			return new FacetCollectorResult<>(this);
		}

		/** @return true if this builder is empty */
		private final boolean isEmpty()
		{
			return facets.isEmpty() && facetCategories.isEmpty();
		}

		public Builder<ModelType> addFacetCategory(final IFacetCategory facetCategory)
		{
			Check.assumeNotNull(facetCategory, "facetCategory not null");
			facetCategories.add(facetCategory);
			return this;
		}

		public Builder<ModelType> addFacet(final IFacet<ModelType> facet)
		{
			Check.assumeNotNull(facet, "facet not null");
			facets.add(facet);
			facetCategories.add(facet.getFacetCategory());
			return this;
		}

		public Builder<ModelType> addFacets(final Iterable<IFacet<ModelType>> facets)
		{
			Check.assumeNotNull(facets, "facet not null");
			for (final IFacet<ModelType> facet : facets)
			{
				addFacet(facet);
			}
			return this;
		}

		/** @return true if there was added at least one facet */
		public boolean hasFacets()
		{
			return !facets.isEmpty();
		}

		public Builder<ModelType> addFacetCollectorResult(final IFacetCollectorResult<ModelType> facetCollectorResult)
		{
			// NOTE: we need to add the categories first, to make sure we preserve the order of categories

			this.facetCategories.addAll(facetCollectorResult.getFacetCategories());
			this.facets.addAll(facetCollectorResult.getFacets());

			return this;
		}

	}
}
