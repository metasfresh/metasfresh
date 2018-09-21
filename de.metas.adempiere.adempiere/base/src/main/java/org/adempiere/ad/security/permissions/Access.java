package org.adempiere.ad.security.permissions;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.util.Check;

/**
 * Defines permission access
 * 
 * @author tsa
 *
 */
public class Access
{
	public static final Access LOGIN = ofName("LOGIN");
	public static final Access READ = ofName("READ");
	public static final Access WRITE = ofName("WRITE");
	public static final Access REPORT = ofName("REPORT");
	public static final Access EXPORT = ofName("EXPORT");

	public static Access ofName(final String accessName)
	{
		return new Access(accessName);
	}

	private String name;
	private int hashcode = 0;

	private Access(final String name)
	{
		super();
		Check.assumeNotEmpty(name, "name not empty");
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(31) // seed
					.append(name)
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
		final Access other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(this.name, other.name)
				.isEqual();
	}

	public String getName()
	{
		return name;
	}
}
