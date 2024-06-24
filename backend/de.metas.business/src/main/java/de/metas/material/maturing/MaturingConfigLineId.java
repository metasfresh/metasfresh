package de.metas.material.maturing;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * metasfresh-material-planning
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

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class MaturingConfigLineId implements RepoIdAware
{
	@JsonCreator
	@NonNull
	public static MaturingConfigLineId ofRepoId(final int repoId)
	{
		return new MaturingConfigLineId(repoId);
	}

	@Nullable
	public static MaturingConfigLineId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final MaturingConfigLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private MaturingConfigLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_MaturingConfig_Line_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final MaturingConfigLineId id1, @Nullable final MaturingConfigLineId id2)
	{
		return Objects.equals(id1, id2);
	}

}
