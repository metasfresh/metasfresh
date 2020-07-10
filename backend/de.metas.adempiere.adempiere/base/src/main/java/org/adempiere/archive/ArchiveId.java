/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.archive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

@Value
public class ArchiveId implements RepoIdAware
{
	@JsonCreator
	public static ArchiveId ofRepoId(final int repoId)
	{
		return new ArchiveId(repoId);
	}

	public static ArchiveId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ArchiveId(repoId) : null;
	}

	public static int toRepoId(final ArchiveId printingQueueItemId)
	{
		return printingQueueItemId != null ? printingQueueItemId.getRepoId() : -1;
	}
	int repoId;

	private ArchiveId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
