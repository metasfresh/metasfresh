package org.adempiere.mm.attributes.api;

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


public enum AttributeAction
{
	Error("E"),
	GenerateNew("N"),
	Ignore("I");

	private final String code;

	AttributeAction(final String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return this.code;
	}
	
	public static AttributeAction forCode(final String code)
	{
		for (final AttributeAction value : values())
		{
			if (value.getCode().equals(code))
			{
				return value;
			}
		}
		
		throw new IllegalArgumentException("No AttributeAction was found for '"+code+"'");
	}
	
}
