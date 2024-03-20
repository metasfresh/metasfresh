/*
 * #%L
 * de.metas.dunning
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

package de.metas.dunning;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

@Value
public class DunningLevelId implements RepoIdAware
{
	@JsonCreator
	public static DunningLevelId ofRepoId(final int repoId)
	{
		return new DunningLevelId(repoId);
	}

	public static DunningLevelId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new DunningLevelId(repoId) : null;
	}

	@JsonValue
	int repoId;

	private DunningLevelId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_DunningLevel_ID");
	}

}