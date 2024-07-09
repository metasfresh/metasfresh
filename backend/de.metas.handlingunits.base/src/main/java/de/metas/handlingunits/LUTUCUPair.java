/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits;

import com.google.common.base.MoreObjects;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.model.I_M_HU;
import lombok.NonNull;

import javax.annotation.Nullable;

public final class LUTUCUPair
{
	@Nullable private final I_M_HU luHU;
	@Nullable private final I_M_HU tuHU;
	@Nullable private final I_M_HU vhu;

	public static LUTUCUPair ofLU(@NonNull I_M_HU luHU) {return new LUTUCUPair(luHU, null, null);}

	public static LUTUCUPair ofTU(@NonNull I_M_HU tuHU, @Nullable final I_M_HU luHU) {return new LUTUCUPair(luHU, tuHU, null);}

	public static LUTUCUPair ofVHU(@NonNull I_M_HU vhu, @Nullable final I_M_HU tuHU, @Nullable final I_M_HU luHU) {return new LUTUCUPair(luHU, tuHU, vhu);}

	private LUTUCUPair(@Nullable final I_M_HU luHU, @Nullable final I_M_HU tuHU, @Nullable final I_M_HU vhu)
	{
		this.luHU = luHU;
		this.tuHU = tuHU;
		this.vhu = vhu;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("LU", luHU)
				.add("TU", tuHU)
				.add("VHU", vhu)
				.toString();
	}

	public I_M_HU getM_TU_HU()
	{
		return tuHU;
	}

	public I_M_HU getM_LU_HU()
	{
		return luHU;
	}

	public I_M_HU getVHU()
	{
		return vhu;
	}

	public I_M_HU getTopLevelHU()
	{
		return CoalesceUtil.coalesce(luHU, tuHU, vhu);
	}
}
