package de.metas.dimension;

/*
 * #%L
 * de.metas.dimension
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

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;

import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;

public interface IDimensionSpecAttributeDAO extends ISingletonService
{

	/**
	 * @param dimensionSpec
	 * @return all M_Attributes of the Dim_Dimension_Spec_Attribute entries that have DIM_Dimension_Spec_ID of the given dimensionSpec
	 */
	List<I_M_Attribute> retrieveAttributesForDimensionSpec(I_DIM_Dimension_Spec dimensionSpec);

	/**
	 * @param dimensionSpec
	 * @return all the Dim_Dimension_Spec_Attribute entries that have DIM_Dimension_Spec_ID of the given dimensionSpec
	 */
	List<I_DIM_Dimension_Spec_Attribute> retrieveDimensionSpecAttributes(I_DIM_Dimension_Spec dimensionSpec);

}
