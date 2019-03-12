package de.metas.security.permissions;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.util.Check;

/**
 * Application Dictionary element (e.g. Window, Process etc).
 *
 * @author tsa
 *
 */
@Immutable
public final class ElementResource implements Resource
{
	public static final ElementResource of(final String tableName, final int elementId)
	{
		return new ElementResource(tableName, elementId);
	}

	private final String tableName;
	private final String tableNameUC;
	private final int elementId;
	private int hashcode = 0;

	private ElementResource(final String tableName, final int elementId)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assume(elementId > 0, "elementId > 0");

		this.tableName = tableName.trim();
		this.tableNameUC = this.tableName.toUpperCase();
		this.elementId = elementId;
	}

	@Override
	public String toString()
	{
		return tableName + "-" + elementId;
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(31) // seed
					.append(tableNameUC)
					.append(elementId)
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

		final ElementResource other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(tableNameUC, other.tableNameUC)
				.append(elementId, other.elementId)
				.isEqual();
	}

	public String getElementTableName()
	{
		return tableName;
	}

	public int getElementId()
	{
		return elementId;
	}
}
