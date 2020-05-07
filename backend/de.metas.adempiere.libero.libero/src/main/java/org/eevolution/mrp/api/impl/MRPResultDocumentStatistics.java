package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import de.metas.material.planning.pporder.LiberoException;

final class MRPResultDocumentStatistics
{
	private final String tableName;
	private int countCreated = 0;
	private int countDeleted = 0;

	public MRPResultDocumentStatistics(final String tableName)
	{
		super();
		Check.assumeNotEmpty(tableName, LiberoException.class, "tableName not empty");
		this.tableName = tableName;
	}

	public boolean isZero()
	{
		return countCreated == 0 && countDeleted == 0;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void addCountCreated(final int countToAdd)
	{
		Check.assumeNotNull(countToAdd >= 0, LiberoException.class, "countToAdd >= 0 but it was {}", countToAdd);
		countCreated += countToAdd;
	}

	public int getCountCreated()
	{
		return countCreated;
	}

	public void addCountDeleted(final int countToAdd)
	{
		Check.assumeNotNull(countToAdd >= 0, LiberoException.class, "countToAdd >= 0 but it was {}", countToAdd);
		countDeleted += countToAdd;
	}

	public int getCountDeleted()
	{
		return countDeleted;
	}
}
