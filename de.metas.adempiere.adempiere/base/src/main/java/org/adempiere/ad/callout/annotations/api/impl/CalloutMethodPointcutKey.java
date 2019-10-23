package org.adempiere.ad.callout.annotations.api.impl;

import java.util.Objects;

import com.google.common.base.MoreObjects;

import de.metas.util.Check;

/* package */final class CalloutMethodPointcutKey
{
	public static final CalloutMethodPointcutKey of(final String columnName)
	{
		return new CalloutMethodPointcutKey(columnName);
	}

	private final String columnName;

	private CalloutMethodPointcutKey(final String columnName)
	{
		super();

		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("columnName", columnName)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(columnName);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}

		final CalloutMethodPointcutKey other = (CalloutMethodPointcutKey)obj;
		return columnName.equals(other.columnName);
	}

	public String getColumnName()
	{
		return columnName;
	}
}
