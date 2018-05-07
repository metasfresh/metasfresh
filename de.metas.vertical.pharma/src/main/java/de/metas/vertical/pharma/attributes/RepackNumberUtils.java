package de.metas.vertical.pharma.attributes;

import org.adempiere.mm.attributes.api.IAttributeSet;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
class RepackNumberUtils
{
	public static final String ATTR_IsRepackNumberRequired = "IsRepackNumberRequired";
	private static final String ATTRVALUE_IsRepackNumberRequired_Yes = "Y";
	// private static final String ATTRVALUE_IsRepackNumberRequired_No = "N";

	public static final String ATTR_RepackNumber = "RepackNumber";

	public static boolean isRepackNumberRequired(final IAttributeSet attributeSet)
	{
		if (!attributeSet.hasAttribute(ATTR_IsRepackNumberRequired))
		{
			return false;
		}

		return ATTRVALUE_IsRepackNumberRequired_Yes.equals(attributeSet.getValueAsString(ATTR_IsRepackNumberRequired));
	}

	public static String getRepackNumber(final IAttributeSet attributeSet)
	{
		if (!attributeSet.hasAttribute(ATTR_RepackNumber))
		{
			return null;
		}
		return attributeSet.getValueAsString(ATTR_RepackNumber);
	}

}
