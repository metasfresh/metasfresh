package de.metas.handlingunits.util;

import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
	private final HuId topLevelHUId;
	private final HuId luHUId;
	@Getter
	private final HuId tuHUId;
	private final HuId vhuId;

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

		topLevelHUId = extractHuId(topLevelHU);
		luHUId = extractHuId(luHU);
		tuHUId = extractHuId(tuHU);
		vhuId = extractHuId(vhu);

		hashKey = Util.mkKey(topLevelHUId, luHUId, tuHUId, vhuId);
	}

	private static final HuId extractHuId(final I_M_HU hu)
	{
		return hu != null ? HuId.ofRepoIdOrNull(hu.getM_HU_ID()) : null;
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

		return hashKey.compareTo(other.hashKey);
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

	public I_M_HU getVHU()
	{
		return vhu;
	}
}
