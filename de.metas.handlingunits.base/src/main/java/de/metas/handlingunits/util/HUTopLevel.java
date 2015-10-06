package de.metas.handlingunits.util;

/*
 * #%L
 * de.metas.handlingunits.base
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
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.model.I_M_HU;

/**
 * Contains definition of top-level handling units and LU-TU-VHUs
 *
 * @author al
 */
public final class HUTopLevel implements Comparable<HUTopLevel>
{
	private final I_M_HU topLevelHU;

	private final I_M_HU luHU;
	private final I_M_HU tuHU;
	private final I_M_HU vhu;

	@ToStringBuilder(skip = true)
	private final ArrayKey hashKey;
	@ToStringBuilder(skip = true)
	private final int topLevelHUId;
	@ToStringBuilder(skip = true)
	private final int luHUId;
	@ToStringBuilder(skip = true)
	private final int tuHUId;
	@ToStringBuilder(skip = true)
	private final int vhuId;

	public HUTopLevel(final I_M_HU topLevelHU,
			final I_M_HU luHU,
			final I_M_HU tuHU,
			final I_M_HU vhu)
	{
		super();

		Check.assumeNotNull(topLevelHU, "topLevelHU not null");
		this.topLevelHU = topLevelHU;

		this.luHU = luHU;
		this.tuHU = tuHU;
		this.vhu = vhu;

		topLevelHUId = topLevelHU == null || topLevelHU.getM_HU_ID() <= 0 ? -1 : topLevelHU.getM_HU_ID();
		luHUId = luHU == null || luHU.getM_HU_ID() <= 0 ? -1 : luHU.getM_HU_ID();
		tuHUId = tuHU == null || tuHU.getM_HU_ID() <= 0 ? -1 : tuHU.getM_HU_ID();
		vhuId = vhu == null || vhu.getM_HU_ID() <= 0 ? -1 : vhu.getM_HU_ID();
		hashKey = Util.mkKey(
				topLevelHUId
				, luHUId
				, tuHUId
				, vhuId
				);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int hashCode()
	{
		return hashKey.hashCode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final HUTopLevel other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(hashKey, other.hashKey)
				.isEqual();
	}

	@Override
	public int compareTo(final HUTopLevel other)
	{
		if (this == other)
		{
			return 0;
		}
		if (other == null)
		{
			return +1; // nulls last
		}

		if (topLevelHUId != other.topLevelHUId)
		{
			return topLevelHUId - other.topLevelHUId;
		}
		if (luHUId != other.luHUId)
		{
			return luHUId - other.luHUId;
		}
		if (tuHUId != other.tuHUId)
		{
			return tuHUId - other.tuHUId;
		}
		if (vhuId != other.vhuId)
		{
			return vhuId - other.vhuId;
		}

		return 0;
	}

	/**
	 * @return top level HU; never return <code>null</code>
	 */
	public I_M_HU getM_HU_TopLevel()
	{
		return topLevelHU;
	}

	public I_M_HU getM_LU_HU()
	{
		return luHU;
	}

	public I_M_HU getM_TU_HU()
	{
		return tuHU;
	}

	public int getM_TU_HU_ID()
	{
		return tuHUId;
	}

	public I_M_HU getVHU()
	{
		return vhu;
	}
}
