package de.metas.handlingunits.util;

import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.model.I_M_HU;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Contains definition of top-level handling units and LU-TU-VHUs
 *
 * @author al
 */
@ToString(of = { "topLevelHU", "luHU", "tuHU", "vhu" })
@EqualsAndHashCode(of = "hashKey")
public final class HUTopLevel implements Comparable<HUTopLevel>
{
	private final I_M_HU topLevelHU;

	private final I_M_HU luHU;
	private final I_M_HU tuHU;
	private final I_M_HU vhu;

	// pre-built:
	private final ArrayKey hashKey;
	private final int topLevelHUId;
	private final int luHUId;
	private final int tuHUId;
	private final int vhuId;

	public HUTopLevel(
			@NonNull final I_M_HU topLevelHU,
			final I_M_HU luHU,
			final I_M_HU tuHU,
			final I_M_HU vhu)
	{
		this.topLevelHU = topLevelHU;

		this.luHU = luHU;
		this.tuHU = tuHU;
		this.vhu = vhu;

		topLevelHUId = topLevelHU == null || topLevelHU.getM_HU_ID() <= 0 ? -1 : topLevelHU.getM_HU_ID();

		luHUId = luHU == null || luHU.getM_HU_ID() <= 0 ? -1 : luHU.getM_HU_ID();

		tuHUId = tuHU == null || tuHU.getM_HU_ID() <= 0 ? -1 : tuHU.getM_HU_ID();

		vhuId = vhu == null || vhu.getM_HU_ID() <= 0 ? -1 : vhu.getM_HU_ID();

		hashKey = Util.mkKey(topLevelHUId, luHUId, tuHUId, vhuId);
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
