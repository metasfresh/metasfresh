/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import java.util.Optional;

@Value
public class ZebraConfigId implements RepoIdAware
{
	@JsonCreator
	public static ZebraConfigId ofRepoId(final int repoId)
	{
		return new ZebraConfigId(repoId);
	}

	public static ZebraConfigId ofRepoIdOrNull(int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<ZebraConfigId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private ZebraConfigId(final int zebraConfigId)
	{
		this.repoId = Check.assumeGreaterThanZero(zebraConfigId, "zebraConfigId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final ZebraConfigId zebraConfigId)
	{
		return zebraConfigId != null ? zebraConfigId.getRepoId() : -1;
	}

}
