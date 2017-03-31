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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.IContextAware;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;

import de.metas.dimension.DimensionConstants;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Assignment;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.dimension.model.I_DIM_Dimension_Spec_AttributeValue;

public class DimensionspecDAO implements IDimensionspecDAO
{

	@Override
	public void deleteAllAssociations(final I_AD_Column column)
	{
		createDimAssignmentQueryBuilderFor(column)
				.create()
				.delete();
	}

	private IQueryBuilder<I_DIM_Dimension_Spec_Assignment> createDimAssignmentQueryBuilderFor(final I_AD_Column column)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_DIM_Dimension_Spec_Assignment.class, column)
				.addEqualsFilter(I_DIM_Dimension_Spec_Assignment.COLUMN_AD_Column_ID, column.getAD_Column_ID());
	}

	@Override
	public void deleteAllSpecAttributeValues(final I_DIM_Dimension_Spec_Attribute specAttr)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_DIM_Dimension_Spec_AttributeValue.class, specAttr)
				.addEqualsFilter(I_DIM_Dimension_Spec_AttributeValue.COLUMN_DIM_Dimension_Spec_Attribute_ID, specAttr.getDIM_Dimension_Spec_Attribute_ID())
				.create()
				.delete();
	}

	@Override
	public void deleteAllAssociations(final I_DIM_Dimension_Spec spec)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_DIM_Dimension_Spec_Assignment.class, spec)
				.addEqualsFilter(I_DIM_Dimension_Spec_Assignment.COLUMN_DIM_Dimension_Spec_ID, spec.getDIM_Dimension_Spec_ID())
				.create()
				.delete();
	}

	@Override
	public void deleteAllSpecAttributes(final I_DIM_Dimension_Spec spec)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_DIM_Dimension_Spec_Attribute.class, spec)
				.addEqualsFilter(I_DIM_Dimension_Spec_Attribute.COLUMN_DIM_Dimension_Spec_ID, spec.getDIM_Dimension_Spec_ID())
				.create()
				.delete();
	}

	@Override
	public List<I_DIM_Dimension_Spec> retrieveForColumn(final I_AD_Column column)
	{
		final IQuery<I_DIM_Dimension_Spec_Assignment> assignmentQuery = createDimAssignmentQueryBuilderFor(column)
				.addOnlyActiveRecordsFilter()
				.create();

		return Services.get(IQueryBL.class).createQueryBuilder(I_DIM_Dimension_Spec.class, column)
				.addInSubQueryFilter(I_DIM_Dimension_Spec.COLUMN_DIM_Dimension_Spec_ID, I_DIM_Dimension_Spec_Assignment.COLUMN_DIM_Dimension_Spec_ID, assignmentQuery)
				.create()
				.list(I_DIM_Dimension_Spec.class);
	}

	@Override
	public I_DIM_Dimension_Spec retrieveForInternalName(final String internalName, final IContextAware ctxAware)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_DIM_Dimension_Spec.class, ctxAware)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DIM_Dimension_Spec.COLUMN_InternalName, internalName)
				.create()
				.firstOnly(I_DIM_Dimension_Spec.class);
	}

	@Override
	public List<String> retrieveAttributeValueForGroup(final String dimensionSpectInternalName,
			final String groupName,
			final IContextAware ctxAware)
	{
		final KeyNamePair[] keyNamePairs = DB.getKeyNamePairs("SELECT M_AttributeValue_ID, ValueName "
				+ "FROM " + DimensionConstants.VIEW_DIM_Dimension_Spec_Attribute_AllValue + " WHERE InternalName=? AND GroupName=?",
				false,
				dimensionSpectInternalName, groupName);

		final List<String> result = new ArrayList<String>(keyNamePairs.length);
		for (final KeyNamePair keyNamePair : keyNamePairs)
		{
			result.add(keyNamePair.getName());
		}
		return result;
	}
}
