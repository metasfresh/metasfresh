/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.async.processor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class QueuePackageProcessorId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static QueuePackageProcessorId ofRepoId(final int repoId)
	{
		return new QueuePackageProcessorId(repoId);
	}

	@Nullable
	public static QueuePackageProcessorId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new QueuePackageProcessorId(repoId) : null;
	}

	public static int toRepoId(@Nullable final QueuePackageProcessorId queuePackageProcessorId)
	{
		return queuePackageProcessorId != null ? queuePackageProcessorId.getRepoId() : -1;
	}

	private QueuePackageProcessorId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
