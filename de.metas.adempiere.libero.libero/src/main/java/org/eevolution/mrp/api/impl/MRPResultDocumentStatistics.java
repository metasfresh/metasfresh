package org.eevolution.mrp.api.impl;

import de.metas.material.planning.pporder.LiberoException;
import de.metas.util.Check;

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
