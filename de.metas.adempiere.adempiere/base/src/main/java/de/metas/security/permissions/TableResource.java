package de.metas.security.permissions;

import de.metas.util.Check;
import lombok.Value;

@Value
public final class TableResource implements Resource
{
	/** Any table */
	public static final TableResource ANY_TABLE = new TableResource();

	public static final TableResource ofAD_Table_ID(final int adTableId)
	{
		return new TableResource(adTableId);
	}

	private final int adTableId;

	private TableResource(final int adTableId)
	{
		Check.assume(adTableId > 0, "AD_Table_ID > 0");
		this.adTableId = adTableId;
	}

	/** "Any table" constructor */
	private TableResource()
	{
		this.adTableId = -1;
	}

	@Override
	public String toString()
	{
		if (this == ANY_TABLE)
		{
			return "@AD_Table_ID@=*";
		}
		else
		{
			return "@AD_Table_ID@=" + adTableId;
		}
	}
}
