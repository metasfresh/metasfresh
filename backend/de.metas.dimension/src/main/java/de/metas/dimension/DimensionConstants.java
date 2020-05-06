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


public final class DimensionConstants
{
	private DimensionConstants()
	{
	}

	/**
	 * Entity type, also database schema name.
	 */
	public static final String ENTITY_TYPE = "\"de.metas.dimension\"";

	/**
	 * NAme of a view that selects all values for a given attribute dimension specification.
	 */
	public static final String VIEW_DIM_Dimension_Spec_Attribute_AllValue = ENTITY_TYPE + ".DIM_Dimension_Spec_Attribute_AllValues";


	/**
	 * Placeholder for empty attribute value in dimension
	 */
	public static final String DIM_EMPTY = "DIM_EMPTY";

	/**
	 * Message for non or empty attribute value.
	 */
	public static final String MSG_NoneOrEmpty = "NoneOrEmpty";

}
