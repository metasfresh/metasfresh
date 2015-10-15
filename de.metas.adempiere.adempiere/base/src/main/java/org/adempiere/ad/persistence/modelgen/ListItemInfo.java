package org.adempiere.ad.persistence.modelgen;

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


import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

/**
 * AD_Ref_List related meta data.
 * 
 * @author tsa
 *
 */
class ListItemInfo
{
	private final String value;
	private final String name;
	private final String valueName;

	public ListItemInfo(final String value, final String name, final String valueName)
	{
		super();

		Check.assumeNotNull(value, "value not null");
		this.value = value;

		Check.assumeNotEmpty(name, "name not empty");
		this.name = name;

		// ok to be null
		this.valueName = valueName;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public String getValue()
	{
		return value;
	}

	public String getName()
	{
		return name;
	}

	public String getValueName()
	{
		return valueName;
	}
}
