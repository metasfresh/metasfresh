package org.adempiere.ad.persistence.modelgen;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

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
