package de.metas.dimension;

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.KeyNamePair;

import de.metas.dimension.model.I_DIM_Dimension_Spec;

/*
 * #%L
 * de.metas.dimension
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IDimensionSpecAttributeBL extends ISingletonService
{

	/**
	 * Use this method any time you want to get DimensionConstants.DIM_EMPTY in case of empty attribute value
	 *
	 * @param attribute
	 * @param asi can be null
	 * @return the value of the given attribute in the given ASI if found, DIM_EMPTY otherwise
	 */
	String getAttrValueFromASI(I_M_Attribute attribute, I_M_AttributeSetInstance asi);

	/**
	 * Create {@link KeyNamePair}s of attribute IDs and values taken from the given <code>asi</code> that are relevant for the given dimensionSpec.
	 * In case of <code>null</code> asi or attributes not found or attributes with non relevant values, their values will be set to {@link DimensionConstants#DIM_EMPTY}.
	 *
	 * @param asi
	 * @param dimensionSpec
	 * @return
	 */
	List<KeyNamePair> createAttrToValue(I_M_AttributeSetInstance asi, I_DIM_Dimension_Spec dimensionSpec);

	/**
	 * Create a new {@link I_M_AttributeSetInstance} containing instances for relevant attributes in dimensionSpec and values from the given asi.<br>
	 * In other words, create a "projection" of the given asi, with respect to the given dimensionSpec.
	 *
	 * @param asi
	 * @param dimensionSpec
	 * @return the new ASI if at least one of the relevant attribute/value couple in the given ASI, null otherwise
	 */
	I_M_AttributeSetInstance createASIForDimensionSpec(I_M_AttributeSetInstance asi, I_DIM_Dimension_Spec dimensionSpec);
}
