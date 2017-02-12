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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Column;

import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;

public interface IDimensionspecDAO extends ISingletonService
{

	/**
	 * Deletes all dimension spec associations (including inactive ones) that reference the given column.
	 *
	 * @param column
	 */
	void deleteAllAssociations(I_AD_Column column);

	void deleteAllSpecAttributeValues(I_DIM_Dimension_Spec_Attribute specAttr);

	void deleteAllAssociations(I_DIM_Dimension_Spec spec);

	void deleteAllSpecAttributes(I_DIM_Dimension_Spec spec);

	/**
	 *
	 * @param column not <code>null</code>
	 *
	 * @return the assigned specs, ordered by <code>SeqNo</code>, or an empty list if column has <code>IsDimension=N</code> or if there are no active specs assigned.
	 */
	List<I_DIM_Dimension_Spec> retrieveForColumn(I_AD_Column column);

	/**
	 *
	 * @param internalName not empty or <code>null</code>
	 * @return
	 */
	I_DIM_Dimension_Spec retrieveForInternalName(String internalName, IContextAware ctxAware);

	/**
	 * Retrieves a list with all attribute values' <code>ValueName</code>s that are defined by the {@link I_DIM_Dimension_Spec} with the given internal name and groupName (a.k.a. ValueAggregateName).
	 * The values are in no particular order. Leans on the view {@value DimensionConstants#VIEW_DIM_Dimension_Spec_Attribute_AllValue}.
	 *
	 * @param dimensionSpectInternalName
	 * @param groupName
	 * @param ctxAware
	 * @return
	 */
	List<String> retrieveAttributeValueForGroup(String dimensionSpectInternalName, String groupName, IContextAware ctxAware);
}
