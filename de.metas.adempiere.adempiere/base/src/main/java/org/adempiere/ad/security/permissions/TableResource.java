package org.adempiere.ad.security.permissions;

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
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

public final class TableResource implements Resource
{
	/** Any table */
	public static final TableResource ANY_TABLE = new TableResource();

	public static final TableResource ofAD_Table_ID(final int adTableId)
	{
		return new TableResource(adTableId);
	}

	private final int AD_Table_ID;
	private int hashcode = 0;

	private TableResource(final int AD_Table_ID)
	{
		super();

		Check.assume(AD_Table_ID > 0, "AD_Table_ID > 0");
		this.AD_Table_ID = AD_Table_ID;
	}

	/** "Any table" constructor */
	private TableResource()
	{
		super();
		this.AD_Table_ID = -1;
	}

	@Override
	public String toString()
	{
		return "@AD_Table_ID@="
				+ (this == ANY_TABLE ? "*" : AD_Table_ID);
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(31) // seed
					.append(AD_Table_ID)
					.toHashcode();
		}
		return hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final TableResource other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(AD_Table_ID, other.AD_Table_ID)
				.isEqual();
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}
}
