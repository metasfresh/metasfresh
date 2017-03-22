package de.metas.dimension.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;

import de.metas.dimension.IDimensionSpecAttributeDAO;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;

public class DimensionSpecAttributeDAO implements IDimensionSpecAttributeDAO
{

	@Override
	public List<I_M_Attribute> retrieveAttributesForDimensionSpec(final I_DIM_Dimension_Spec dimensionSpec)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_DIM_Dimension_Spec_Attribute.class, dimensionSpec)
				.addEqualsFilter(I_DIM_Dimension_Spec_Attribute.COLUMNNAME_DIM_Dimension_Spec_ID, dimensionSpec.getDIM_Dimension_Spec_ID())
				.addOnlyActiveRecordsFilter()
				.andCollect(I_DIM_Dimension_Spec_Attribute.COLUMN_M_Attribute_ID, I_M_Attribute.class)
				.create()
				.list(I_M_Attribute.class);
	}

	@Override
	public List<I_DIM_Dimension_Spec_Attribute> retrieveDimensionSpecAttributes(final I_DIM_Dimension_Spec dimensionSpec)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_DIM_Dimension_Spec_Attribute.class, dimensionSpec)
				.addEqualsFilter(I_DIM_Dimension_Spec_Attribute.COLUMNNAME_DIM_Dimension_Spec_ID, dimensionSpec.getDIM_Dimension_Spec_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_DIM_Dimension_Spec_Attribute.class);
	}
}
