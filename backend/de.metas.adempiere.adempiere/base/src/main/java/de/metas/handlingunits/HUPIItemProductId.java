package de.metas.handlingunits;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Aka {@code M_HU_PI_Item_Product_ID}
 */
@Value
public class HUPIItemProductId implements RepoIdAware
{
	@JsonCreator
	public static HUPIItemProductId ofRepoId(final int repoId)
	{
		if (repoId == VIRTUAL_HU.repoId)
		{
			return VIRTUAL_HU;
		}
		else if (repoId == TEMPLATE_HU.repoId)
		{
			return TEMPLATE_HU;
		}
		else
		{
			return new HUPIItemProductId(repoId);
		}
	}

	public static HUPIItemProductId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static HUPIItemProductId ofRepoIdOrNone(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : VIRTUAL_HU;
	}

	public static Optional<HUPIItemProductId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final HUPIItemProductId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean isRegular(@Nullable final HUPIItemProductId id)
	{
		return id != null && id.isRegular();
	}

	public static final HUPIItemProductId TEMPLATE_HU = new HUPIItemProductId(100);
	public static final HUPIItemProductId VIRTUAL_HU = new HUPIItemProductId(101);

	int repoId;

	private HUPIItemProductId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_HU_PI_Item_Product_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final HUPIItemProductId id1, @Nullable final HUPIItemProductId id2)
	{
		return Objects.equals(id1, id2);
	}

	public static HUPIItemProductId nullToVirtual(final HUPIItemProductId id)
	{
		return id != null ? id : VIRTUAL_HU;
	}

	public boolean isVirtualHU()
	{
		return isVirtualHU(repoId);
	}

	public static boolean isVirtualHU(final int repoId)
	{
		return repoId == VIRTUAL_HU.repoId;
	}

	public boolean isRegular()
	{
		return repoId != VIRTUAL_HU.repoId
				&& repoId != TEMPLATE_HU.repoId;
	}

}
