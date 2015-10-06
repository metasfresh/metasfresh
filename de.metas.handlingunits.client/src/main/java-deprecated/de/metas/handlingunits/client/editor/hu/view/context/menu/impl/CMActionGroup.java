package de.metas.handlingunits.client.editor.hu.view.context.menu.impl;

import org.adempiere.util.Check;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMActionGroup;

public class CMActionGroup implements ICMActionGroup
{
	private final String id;
	private final String name;
	private final int seqNo;

	public CMActionGroup(final String name)
	{
		this(name, SEQNO_Middle);
	}

	public CMActionGroup(final String name, final int seqNo)
	{
		super();
		Check.assumeNotEmpty(name, "name not empty");

		this.name = name;
		this.seqNo = seqNo;

		this.id = "CMActionGroup#" + name;
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(name)
				.append(seqNo)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final CMActionGroup other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.name, other.name)
				.append(this.seqNo, other.seqNo)
				.isEqual();
	}

	@Override
	public int compareTo(final ICMActionGroup o)
	{
		if (o == null)
		{
			return 1; // nulls first
		}

		int cmp = this.getSeqNo() - o.getSeqNo();
		if (cmp != 0)
		{
			return cmp;
		}

		cmp = this.getName().compareTo(o.getName());
		if (cmp != 0)
		{
			return cmp;
		}

		return 0;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getSeqNo()
	{
		return seqNo;
	}
}
