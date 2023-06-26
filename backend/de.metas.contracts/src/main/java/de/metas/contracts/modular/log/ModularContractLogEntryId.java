/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ModularContractLogEntryId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ModularContractLogEntryId ofRepoId(final int repoId)
	{
		return new ModularContractLogEntryId(repoId);
	}

	@Nullable
	public static ModularContractLogEntryId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ModularContractLogEntryId(repoId) : null;
	}

	private ModularContractLogEntryId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "modularContractLogEntryId");
	}

	public static int toRepoId(@Nullable final ModularContractLogEntryId modularContractLogEntryId)
	{
		return modularContractLogEntryId != null ? modularContractLogEntryId.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
