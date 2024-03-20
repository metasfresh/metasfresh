/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem.leichmehl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class LeichMehlPluFileConfigGroupId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	@NonNull
	public static LeichMehlPluFileConfigGroupId ofRepoId(final int repoId)
	{
		return new LeichMehlPluFileConfigGroupId(repoId);
	}

	@Nullable
	public static LeichMehlPluFileConfigGroupId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new LeichMehlPluFileConfigGroupId(repoId) : null;
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	private LeichMehlPluFileConfigGroupId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_LeichMehl_PluFile_ConfigGroup.COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID);
	}
}
