package de.metas.dataentry;

import static de.metas.util.Check.assumeGreaterThanZero;

import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Value
public class DataEntrySubTabId implements RepoIdAware
{
	public static DataEntrySubTabId ofRepoId(final int repoId)
	{
		return new DataEntrySubTabId(repoId);
	}

	public static DataEntrySubTabId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	@JsonCreator
	public DataEntrySubTabId(final int repoId)
	{
		this.repoId = assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue // note: annotating just the repoId member worked "often" which was very annoying
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final DataEntrySubTabId id1, @Nullable final DataEntrySubTabId id2)
	{
		return Objects.equals(id1, id2);
	}
}
