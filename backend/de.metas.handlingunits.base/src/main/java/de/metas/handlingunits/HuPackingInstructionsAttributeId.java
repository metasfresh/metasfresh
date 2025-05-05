package de.metas.handlingunits;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * Aka {@code M_HU_PI_Attribute_ID}.
 */
@Value
public class HuPackingInstructionsAttributeId implements RepoIdAware
{
	@JsonCreator
	public static HuPackingInstructionsAttributeId ofRepoId(final int repoId)
	{
		return new HuPackingInstructionsAttributeId(repoId);
	}

	@Nullable
	public static HuPackingInstructionsAttributeId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	private HuPackingInstructionsAttributeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_HU_PI_Attribute_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final HuPackingInstructionsAttributeId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(final HuPackingInstructionsAttributeId o1, final HuPackingInstructionsAttributeId o2)
	{
		return Objects.equals(o1, o2);
	}
}
