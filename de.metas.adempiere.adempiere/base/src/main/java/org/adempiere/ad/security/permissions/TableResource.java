package org.adempiere.ad.security.permissions;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.util.Check;

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
