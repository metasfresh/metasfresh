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


import org.adempiere.ad.dao.IQueryFilter;

/**
 * The Facet.
 * 
 * A facet is a caracteristic of a model which can be categorized (see {@link IFacetCategory}) and which can be used to filter existing documents.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface IFacet<ModelType>
{
	/** @return facet unique identifier */
	String getId();

	/** @return user friendly name */
	String getDisplayName();

	/** @return query filter implementation of this facet */
	IQueryFilter<ModelType> getFilter();

	/** @return facet's category; never returns null */
	IFacetCategory getFacetCategory();
}
